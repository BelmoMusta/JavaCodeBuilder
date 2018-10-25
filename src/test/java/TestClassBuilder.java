import org.junit.Assert;
import org.junit.Test;

/**
 * Created by DELL on 14/03/2018.
 */
public class TestClassBuilder {

    public static final String PATH = "PATH_TO_FILE";

    @Test
    public void testBuilder() throws Exception {
        ClassBuilder classBuilder = new ClassBuilder();
        classBuilder.buildFormFile(new java.io.File(PATH), System.out);
    }


}
