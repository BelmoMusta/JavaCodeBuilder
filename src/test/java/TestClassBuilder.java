import objects.Person;
import objects.Student;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by DELL on 14/03/2018.
 */
public class TestClassBuilder {

    public static final String PATH = "src/main/java/objects";
    public static final String OUTPUT = "output/";

    @Test
    public void testBuilder() {

        List<Class<?>> classes = Arrays.asList(Person.class, Student.class);
        ClassBuilder classBuilder = new ClassBuilder();

        classBuilder.setPath(PATH);
        classBuilder.setClasses(classes);
        classBuilder.setSuffix("Builder");
        classBuilder.setDestination(OUTPUT);
        classBuilder.build();

    }


}
