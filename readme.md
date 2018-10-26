
## JavaCodeBuilder
JavaCodeBuilder is a java code that helps constructing builder classes for beans.
### Example
suppose we have the following `java` class :
```java
public class Person {
  private String name;
  private String lastName;
  private int age;

  public String getName() {
  return name;
 }
  public void setName(String name) {
  this.name = name;
 }
  public String getLastName() {
  return lastName;
 }
  public void setLastName(String lastName) {
  this.lastName = lastName;
 }
  public int getAge() {
  return age;
 }
  public void setAge(int age) {
  this.age = age;
 }}
```
To build the `Builder` from this class, we perform the lines of code below :
```java
ClassBuilder classBuilder = new ClassBuilder();
classBuilder.buildFormFile(new java.io.File("PATH"), System.out);
```
Where `"PATH"` is the absolute path to the file, and `System.out` is the stream to which the output will be written.

### Result
It will produce a java class in the output, which represents the builder of the given class :
```java
public class PersonBuilder {
    private Person mPerson;
    public PersonBuilder() {
        mPerson = new Person();
    }
    public PersonBuilder name(String name) {
        mPerson.setName(name);
        return this;
    }
    public PersonBuilder lastName(String lastName) {
        mPerson.setLastName(lastName);
        return this;
    }
    public PersonBuilder age(int age) {
        mPerson.setAge(age);
        return this;
    }
    public Person build() {
        return mPerson;
    }
}
```