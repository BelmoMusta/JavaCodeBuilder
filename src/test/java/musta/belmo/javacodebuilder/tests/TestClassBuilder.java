package musta.belmo.javacodebuilder.tests;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.Type;
import musta.belmo.javacodebuilder.service.ClassBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by DELL on 14/03/2018.
 */
public class TestClassBuilder {

    @Test
    public void testBuilder() throws IOException, URISyntaxException {
        ClassBuilder classBuilder = new ClassBuilder();
        URL resource = TestClassBuilder.class.getClassLoader().getResource("Person.java");
        CompilationUnit compilationUnit = classBuilder.buildFormFile(Paths.get(resource.toURI()).toFile());

        List<ClassOrInterfaceDeclaration> all = compilationUnit.findAll(ClassOrInterfaceDeclaration.class);
        Assert.assertEquals(1, all.size());
        ClassOrInterfaceDeclaration classDest = all.get(0);
        String classBuilderName = classDest.getNameAsString();
        Assert.assertEquals("PersonBuilder", classBuilderName);

        List<MethodDeclaration> methods = classDest.findAll(MethodDeclaration.class);

        Assert.assertEquals(4, methods.size());
        int i = 0;
        for (; i < methods.size() - 1; i++) {
            MethodDeclaration method = methods.get(i);
            Type type = method.getType();
            Assert.assertTrue("PersonBuilder".equals(type.toString()));
        }

        MethodDeclaration method = methods.get(i);
        Assert.assertTrue("build".equals(method.getNameAsString()));
        Assert.assertTrue("Person".equals(method.getType().toString()));


    }
}
