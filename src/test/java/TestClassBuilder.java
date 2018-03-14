import objects.Person;
import objects.Student;
import org.junit.Test;

/**
 * Created by DELL on 14/03/2018.
 */
public class TestClassBuilder {

    @Test
    public void testBuilder() {
        final String path = "src/main/java/objects";
        final String destination = "output";
        Class[] classes = new Class[]{Person.class, Student.class};
        ClassBuilder.build(path, destination, classes);

    }
}
