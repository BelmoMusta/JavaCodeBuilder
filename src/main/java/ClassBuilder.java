import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import mustabelmo.exception.handler.TryCatcher;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by DELL on 12/03/2018.
 */
public class ClassBuilder {


    private String path;
    private String destination;
    private List<Class<?>> classes;
    private String suffix;

    public void buildFromClass(CompilationUnit compilationUnit) {
        CompilationUnit resultUnit = new CompilationUnit();

        compilationUnit.findAll(ClassOrInterfaceDeclaration.class).stream().filter(classDef ->
                !classDef.isAbstract()
                        && !classDef.isInterface()
                        && !classDef.isInnerClass()).
                forEach(classDef -> {
                    final ClassOrInterfaceDeclaration classOrInterfaceDeclaration = resultUnit.addClass(classDef.getNameAsString() + "Builder");
                    FieldDeclaration mInstance = classOrInterfaceDeclaration.addField(classDef.getName().asString(), "mInstance");

                    classDef.findAll(MethodDeclaration.class).stream().filter(methodDeclaration ->
                            !methodDeclaration.isAnnotationPresent("Override"))
                            .filter(methodDeclaration ->
                                    methodDeclaration.getName().asString().startsWith("set"))
                            .forEach(methodDeclaration -> {
                                MethodDeclaration addedMethod = classOrInterfaceDeclaration
                                        .addMethod(methodDeclaration.getName().asString().substring(3),
                                                com.github.javaparser.ast.Modifier.PUBLIC);
                                addedMethod.setType(classOrInterfaceDeclaration.toString());
                                addedMethod.setBody(new BlockStmt());
                                addedMethod.getBody().get().addStatement(new MethodCallExpr(mInstance,methodDeclaration,null));


                            });

                });

    }

    public void build() {
        classes.stream()
                .filter(cls -> !isAbstract(cls))
                .forEach(aClass -> {
                    String pathname = aClass.getSimpleName() + suffix;
                    TryCatcher.of(() -> Files.createDirectories(Paths.get(path))).execute();

                    final File file = new File(destination, pathname + ".java");
                    TryCatcher.of(file::createNewFile).execute();

                    final FileWriter[] fw = {null};

                    TryCatcher.of(() -> fw[0] = new FileWriter(file),
                            exception -> {
                            })
                            .execute();


                    CustomStringBuilder body = new CustomStringBuilder();
                    CustomStringBuilder imports = new CustomStringBuilder();

                    final Set<String> importsList = new TreeSet<>();

                    imports.appendLn("package")
                            .spaceAppend("moveToAWantedPackage")
                            .appendLn(';');
                    body.append("public class")
                            .spaceAppend(pathname)
                            .spaceAppend('{')
                            .carriageReturn()
                            .spaceIndentBy(4)
                            .append("private ");
                    importsList.add(aClass.getCanonicalName());
                    body.append(aClass.getSimpleName())
                            .spaceAppend("instance = new")
                            .spaceAppend(aClass.getSimpleName())
                            .appendLn("();")
                            .carriageReturn();

                    Stream.of(aClass.getMethods())
                            .forEach(method -> {
                                String name = method.getName();
                                importsList.addAll(Stream.of(method.getParameterTypes())
                                        .filter(cls -> !cls.isPrimitive())
                                        .map(Class::getCanonicalName)
                                        .filter(ss -> !ss.startsWith("java.lang"))
                                        .collect(Collectors.toList()));

                                executeIf(name.startsWith("set"), () -> {
                                    body.spaceIndentBy(4)
                                            .append("public")
                                            .spaceAppend(pathname)
                                            .spaceAppend()
                                            .append(name.substring(3).toLowerCase())
                                            .append('(')
                                            .append(method.getParameters()[0]
                                                    .getType().getSimpleName())
                                            .spaceAppend()
                                            .append(name.substring(3)
                                                    .toLowerCase().charAt(0))
                                            .appendLn(") {")
                                            .spaceIndentBy(8)
                                            .append("instance.")
                                            .append(name)
                                            .append('(')
                                            .append(name.substring(3)
                                                    .toLowerCase().charAt(0))
                                            .appendLn(");")
                                            .spaceIndentBy(8)
                                            .appendLn("return this;")
                                            .spaceIndentBy(4)
                                            .appendLn('}')
                                            .carriageReturn();
                                    return true;
                                });
                            });

                    body.spaceIndentBy(4)
                            .append("public")
                            .spaceAppend(aClass.getSimpleName())
                            .spaceAppend("build() {")
                            .carriageReturn()
                            .spaceIndentBy(8)
                            .appendLn("return instance;")
                            .spaceIndentBy(4)
                            .appendLn('}');
                    importsList.forEach(imp ->
                            imports.append("import")
                                    .spaceAppend(imp)
                                    .appendLn(';'));
                    body.appendLn('}');

                    TryCatcher.of(() -> fw[0].append(imports
                            .carriageReturn())
                            .append(body), exception -> {
                    }).finallyBlock(() ->
                            TryCatcher.of(fw[0]::close)
                                    .execute())
                            .execute();
                });
    }

    private static boolean isAbstract(Class<?> cls) {
        return cls != null && Modifier.isAbstract(cls.getModifiers());
    }

    public static boolean executeIf(boolean test, BooleanSupplier supplier) {
        return test && supplier.getAsBoolean();
    }


    public List<Class<?>> getClasses() {
        return classes;
    }

    public void setClasses(List<Class<?>> classes) {
        this.classes = classes;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
