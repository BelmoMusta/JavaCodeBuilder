import org.junit.Test;

/**
 * Created by DELL on 14/03/2018.
 */
public class TestClassBuilder {

    public static final String PATH = "C:\\Users\\mustapha\\Desktop\\javaProjects\\JavaCodeBuilder\\src\\main\\java\\objects\\Person.java";

    @Test
    public void testBuilder() throws Exception {
        ClassBuilder classBuilder = new ClassBuilder();
        classBuilder.buildFormFile(new java.io.File(PATH), System.out);
    }
}
