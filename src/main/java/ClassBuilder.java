import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.stream.Stream;

/**
 * Created by DELL on 12/03/2018.
 */
public class ClassBuilder {


    public void buildFormFile(File f, OutputStream outputStream) throws IOException {
        CompilationUnit compilationUnit = JavaParser.parse(f);
        buildFromClass(compilationUnit, outputStream);
    }

    private void buildFromClass(CompilationUnit compilationUnit, OutputStream out) {
        CompilationUnit resultUnit = new CompilationUnit();

        compilationUnit.findAll(ClassOrInterfaceDeclaration.class)
                .stream()
                .filter(classDef ->
                        !classDef.isAbstract()
                                && !classDef.isInterface()
                                && !classDef.isInnerClass()).
                forEach(classDef -> {
                    final ClassOrInterfaceDeclaration classDeclaration =
                            resultUnit.addClass(String.format("%sBuilder", classDef.getNameAsString()));

                    FieldDeclaration fieldDeclaration = classDeclaration.addField(classDef.getName()
                                    .asString(),
                            String.format("m%s", classDef.getName().asString()));
                    fieldDeclaration.addModifier(com.github.javaparser.ast.Modifier.PRIVATE);
                    VariableDeclarator variableDeclarator = fieldDeclaration.getVariable(0);

                    ObjectCreationExpr objectCreationExpr = new ObjectCreationExpr();
                    objectCreationExpr.setType(classDef.getName().asString());

                    AssignExpr assignExpr = new AssignExpr(variableDeclarator.getNameAsExpression(),
                            objectCreationExpr, AssignExpr.Operator.ASSIGN);

                    ConstructorDeclaration constructorDeclaration =
                            classDeclaration.addConstructor(com.github.javaparser.ast.Modifier.PUBLIC);
                    BlockStmt constructorBody = new BlockStmt();
                    constructorBody.addStatement(assignExpr);
                    constructorDeclaration.setBody(constructorBody);

                    Stream<MethodDeclaration> methodDeclarationStream = classDef.findAll(MethodDeclaration.class).stream().filter(methodDeclaration ->
                            !methodDeclaration.isAnnotationPresent("Override"))
                            .filter(methodDeclaration ->
                                    !methodDeclaration.getName().asString().startsWith("get")
                                            && !"clone".equals(methodDeclaration.getName().asString()));

                    methodDeclarationStream.forEach(methodDeclaration -> {
                        String methodName;
                        if (methodDeclaration.getName().asString().startsWith("set"))
                            methodName = Utils.uncapitalize(methodDeclaration.getName().asString().substring(3));
                        else methodName = methodDeclaration.getName().asString();
                        MethodDeclaration addedMethod = classDeclaration
                                .addMethod(methodName,
                                        com.github.javaparser.ast.Modifier.PUBLIC);

                        addedMethod.setType(classDeclaration.getName().asString());
                        addedMethod.setBody(new BlockStmt());

                        NodeList<Parameter> parameters = methodDeclaration.getParameters();
                        addedMethod.setParameters(parameters);
                        MethodCallExpr methodCallExpr = new MethodCallExpr(variableDeclarator.getNameAsExpression(), methodDeclaration.getName().asString());
                        parameters.forEach(parameter -> methodCallExpr.addArgument(parameter.getName().asString()));
                        methodCallExpr.setName(methodDeclaration.getName());

                        addedMethod.getBody().ifPresent(blockStmt -> {
                            blockStmt.addStatement(methodCallExpr);
                            ReturnStmt returnStatement = new ReturnStmt();
                            returnStatement.setExpression(new ThisExpr());
                            blockStmt.addStatement(returnStatement);
                        });
                    });

                    MethodDeclaration buildMethod = classDeclaration.addMethod("build");
                    buildMethod.setType(classDef.getName().asString());
                    ReturnStmt returnStatement = new ReturnStmt();
                    returnStatement.setExpression(variableDeclarator.getNameAsExpression());
                    buildMethod.addModifier(com.github.javaparser.ast.Modifier.PUBLIC);
                    buildMethod.getBody().ifPresent(body -> body.addStatement(returnStatement));
                });

        final PrintWriter printWriter = new PrintWriter(out);
        printWriter.write(resultUnit.toString());
        printWriter.flush();
        printWriter.close();
    }
}

