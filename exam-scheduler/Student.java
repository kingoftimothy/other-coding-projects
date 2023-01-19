import java.util.Iterator;
import structure5.*;

/**
 * Student Class of an Object that represents each Student
 */
public class Student {
    // instance variables
    protected String name;
    protected String[] courses;

    /**
     * Constructs a Studnet Object
     * @pre none
     * @param name a String of the Student's name
     * @param courses and String array of the courses the Student takes
     * @post the instance variables are initialized
     */
    public Student(String name, String[] courses) {
        this.name = name;
        this.courses = courses;
    }

    /**
     * Accessor method for the Student's Name
     * @pre none
     * @return String of student's name
     * @post returns string of Student's name
     */
    public String getName() {
        return name;
    }

    /**
     * Accessor method for the Student's Courses
     * @pre none
     * @return String Array of student's courses
     * @post returns string array of Student's courses
     */
    public String[] getCourses() {
        return courses;
    }

}