package objects;

/**
 * Created by DELL on 14/03/2018.
 */
public class Student extends Person {

    String studentID;
    String school;


    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
