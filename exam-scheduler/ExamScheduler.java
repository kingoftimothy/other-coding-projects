import structure5.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Random;

/**
 * Exam Scheduler class that creates final exam schedule
 */
public class ExamScheduler {

    // instance variables for the class
    protected Graph<String, String> graph;
    protected Vector<Student> students;
    protected Vector<Vector<String>> schedule;
    protected Vector<String> catalog;

    // Students take 4 courses
    public final int courseNumber = 4;
    
    /**
     * Constructs an ExamScheduler object which has an
     * undeirected graph and the students, schedule, and catalag vectors
     * @pre none
     * @post initializes instance variables
     */
    public ExamScheduler() {
        graph = new GraphListUndirected();
        students = new Vector<>();
        schedule = new Vector<>();
        catalog = new Vector<>();
    }

    /**
     * adds the Courses to the graph and links the edges to right vertex (courses)
     * @param courses String array that consists of 4 courses that each student takes
     * @pre each index in courses should be an actual course the student takes
     * @post adds vertexes of courses and connects vertex of the courses that a student takes
     */
    public void addCourses(String[] courses) {
        // adds each course as vertex
        for (String c : courses) {
            graph.add(c);
        }
        // connects each of the student's courses together with edge
        for (int i = 0; i < courses.length; i++) {
            for (int j = i; j < courses.length; j++) {
                graph.addEdge(courses[i], courses[j], "1");
            }
        }
    }

    /**
     * chooses a course from the list of vertexes
     * @param index an int that determines which index in the vertex Vector
     * @pre index is an integer within 0 to the size of the vector
     * @return returns a String of a course in the vertex Vector
     * @post returns a String course value
     */
    public String chooseCourse(int index) {
        Assert.condition(index >= 0 && index < vertex().size(), "Not a valid Index!");
        return vertex().get(index);
    }

    /**
     * determines the courses that are not connected to a given base course
     * @param firstSlot Vector of String of base courses that can't have connections to other courses
     * @pre none
     * @return returns Vector of Strings of the courses not overlapping for any student
     * @post returns the one slot of the exam schedule
     */
    public Vector<String> notConnected(Vector<String> firstSlot) {
        // loops through each course on the graph
        for (String s : vertex()) {
            boolean connected = false;
            // adds all the courses that are not connnected to the base courses
            for (String course : firstSlot) {
                // checks neighbors of base courses to see if connection between the two courses
                for (String n : neighbors(course)) {
                    if (s.equals(n)) {
                        connected = true;
                    }
                }
            }
            if (!connected) {
                firstSlot.add(s);
            }
            
        }
        return firstSlot;
    }

    /**
     * Removes the Vertexes from the graph
     * @pre Strings in firstSlot are actul courses that exist on the graph
     * @param firstSlot a Vector of String of a slot already created in the schedule
     * @post removes all the vertexes of the Strings in the Vector
     */
    public void markRemoved(Vector<String> firstSlot) {
        for (String course : firstSlot) {
            Assert.condition(graph.contains(course), "The course doesn't exist on the graph!");
            graph.remove(course);
        }
    }

    /**
     * Creates a Vector of Strings from the neighbors Iterator of the graph
     * @param label a String of the course that the neighbors are found for
     * @pre label is an actual course that exists in the graph
     * @return a Vector of all the neighbors to the label course
     * @post returns the String of Vectors of neighbors
     */
    public Vector<String> neighbors(String label) {
        Assert.condition(graph.contains(label), "The course doesn't exist on the graph!");
        Vector<String> neighbors = new Vector<>();
        // uses neighbors iterators and iterates through
        Iterator<String> iter = graph.neighbors(label);
        while(iter.hasNext()) {
            String s = iter.next();
            neighbors.add(s);
        }
        return neighbors;
    }

    /**
     * Creates a Vector of Strings of vertexes from the Iterator of the graph
     * @pre the graph exists
     * @return a Vector of all the vertexes on the graph
     * @post returns the String of Vectors of courses
     */
    public Vector<String> vertex() {
        // uses graphs' iterator and iterates through
        Vector<String> vertex = new Vector<>();
        Iterator<String> iter = graph.iterator();
        while(iter.hasNext()) {
            String s = iter.next();
            vertex.add(s);
        }
        return vertex;
    }

    /**
     * Sorts Vector of Students (increasing order)
     * @param vec vector of Students to be sorted
     * @pre vector contains Students
     * @return a vector of students that was sorted
     * @post the vector is sorted
     */
    public Vector<Student> sort(Vector<Student> vec) {
        // number of values in place
        int numSorted = 1;
        // general index
        int index;

        // loops through MyVector until every value sorted
        while (numSorted < vec.size()) {
            // compares and sorts object of index numSorted with previous value in vector
            Student temp = vec.get(numSorted);
            for (index = numSorted; index > 0; index--) {
                if (temp.getName().compareTo(vec.get(index-1).getName()) < 0) {
                    vec.set(index, vec.get(index - 1));
                } else {
                    break;
                }
            }
            // reinsert value
            vec.set(index, temp);
            numSorted++;
        }
        return vec;
    }

    /**
     * Sorts Vector of Strings (increasing order)
     * @param vec vector of Strings to be sorted
     * @pre vector contains Strings
     * @return a vector of Strings that was sorted
     * @post the vector is sorted
     */
    public Vector<String> sortString(Vector<String> vec) {
        // number of values in place
        int numSorted = 1;
        // general index
        int index;

        // loops through MyVector until every value sorted
        while (numSorted < vec.size()) {
            // compares and sorts object of index numSorted with previous value in vector
            String temp = vec.get(numSorted);
            for (index = numSorted; index > 0; index--) {
                if (temp.compareTo(vec.get(index-1)) < 0) {
                    vec.set(index, vec.get(index - 1));
                } else {
                    break;
                }
            }
            // reinsert value
            vec.set(index, temp);
            numSorted++;
        }
        return vec;
    }

    /**
     * Generates a random integer based on the amount of vertices on the Graph
     * @pre graph exists
     * @return an integer that is a random number between 0 and the amount of vertices
     * @post a random number is generated
     */
    public int randomize() {
        Random random = new Random();
        // 0 to vertex().size() - 1 inclusive
        return random.nextInt(vertex().size());
    }

    /**
     * Returns the integer corresponding to slot number of a course on the Exam Schedule
     * @param course String of the course that is being looked up on the schedule
     * @pre course is an actual String that exists on the schedule
     * @return returns an int of the slot number corresponding to course on Exam
     * If course doesn't exists, returns 0.
     * @post returns the slotnumber of course on the scheudle
     */
    public int slotNumber(String course) {
        // loops through the schedule vector to find a match in each slot
        for (int i = 1; i <= schedule.size(); i++) {
            for (String courseList : schedule.get(i-1)) {
                if (course.equals(courseList)) {
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * Method for Extension One of the Lab
     * @pre none
     * @post Print out a final exam schedule ordered by course name/number
     * and for each course, prints all students taking that course.
     */
    public void extensionOne() {
        System.out.println();
        System.out.println("Extension 1:");
        System.out.println();
        // catalog and students vectors are already sorted
        // loops through catalog and finds which Students are taking a certain course
        for (String c : catalog) {
            System.out.print(c + "(Slot " + slotNumber(c) + "): ");
            for (Student s : students) {
                for (String course : s.getCourses()) {
                    if (c.equals(course)) {
                        System.out.print(s.getName() + "  ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Method for Extension Two of the Lab
     * @pre none
     * @post Print out a final exam schedule for each student, listing students in alphabetical order.
     * For each student, lists which exam slots they should attend.
     */
    public void extensionTwo() {
        System.out.println("Extension 2:");
        System.out.println();
        // students vectors are already sorted
        // loops through students and finds which Courses correspond to a slot number in the schedule
        for (Student s : students) {
            System.out.print(s.getName() + ": ");
            for (String course : s.getCourses()) {
                System.out.print(course + "(Slot " + slotNumber(course) + ") ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Method for Extension Three of the Lab
     * @pre text is the input text
     * @param text a String of the inputted text
     * @post randomizes the schedules on random orderings of the nodes and prints
     * the largest and smallest solutions found in a given run.
     */
    public void extensionThree(String text) {
        System.out.println("Extension 3:");
        // runs through the schedules creator for an intial largest and smallest Schedule
        // used to compare to the other randomly generatoed schedules
        schedule.clear();
        organize(text);
        Vector<Vector<String>> largestSchedule = clone();
        Vector<Vector<String>> smallestSchedule = clone();
        int largestSlots = schedule.size();
        int smallestSlots = schedule.size();

        // runs the schedule creator randomly 10 times more
        for (int i = 0; i < 10; i++) {
            schedule.clear();
            organize(text);
            // checks if the currently random schedule is larger or smaller or neither
            if (largestSlots < schedule.size()) {
                largestSlots = schedule.size();
                largestSchedule = clone();
            } else if (smallestSlots > schedule.size()) {
                smallestSlots = schedule.size();
                smallestSchedule = clone();
            }
        }
        // prints largest and smallest schedule
        System.out.println("Smallest Solution:");
        printSchedule(smallestSchedule);
        System.out.println();
        System.out.println("Largest Solution:");
        printSchedule(largestSchedule);
    }

    /**
     * Reads the Inputted File that the user prompts
     * @pre the File is formatted the correct way of one line for the student
     * and the next four lines as their courses
     * @return String of the textBuffer for the input
     * @post returns a String of the input file
     */
    public String readingFile() {
        Scanner in = new Scanner(System.in);
        StringBuffer textBuffer = new StringBuffer();
        // loops through each line and appends to the textBuffer
        while (in.hasNextLine()) {
            String line = in.nextLine();
            textBuffer.append(line);
            textBuffer.append("\n");
        }
        // a string with the whole inputted file
        String text = textBuffer.toString();
        return text;
    }

    /**
     * Organizes a Final Exam Schedule
     * @pre the Inputted Text file is propperly formatted
     * @param text a String of the inputted text
     * @post creates the exam scheudles and sets the catalog, students vectors
     * and sets the schedules vector
     */
    public void organize(String text) {
        // a String array of the courses one student takes
        String[] courses = new String[courseNumber];

        Scanner scanner = new Scanner(text);
        // reads through each line of the String
        while (scanner.hasNextLine()) {
            String name = scanner.nextLine();
            for (int i = 0; i < courseNumber; i++) {
                courses[i] = scanner.nextLine();
                // adds unique courses to the catalog vector
                if (catalog.indexOf(courses[i]) < 0)
                    catalog.add(new String(courses[i]));
            }
            // adds Students for each student
            students.add(new Student(new String(name), courses.clone()));
            // adds Courses to the graph
            addCourses(courses);
        }

        String baseCourse;
        Vector<String> firstSlot = new Vector<>();
        // creates the schedule slot by slot until the graph has no more vertices
        while(!graph.isEmpty()) {
            baseCourse = chooseCourse(randomize());
            firstSlot.add(baseCourse);
            firstSlot = notConnected(firstSlot);
            schedule.add(new Vector<>(firstSlot));
            markRemoved(firstSlot);
            firstSlot.clear();
        }
        // sorts the students and catalog vectors
        students = sort(students);
        catalog = sortString(catalog);
    }

    /**
     * Prints out the Final Exam Schedule to output
     * @pre none
     * @param schedules vector of vectors of the String courses 
     * @post prints out the exam schedule
     */
    public void printSchedule(Vector<Vector<String>> schedules) {
        for (int i = 1; i <= schedules.size(); i++) {
            System.out.println("Slot " + i + ": " + schedules.get(i-1).toString());
        }
    }

    /**
     * Accessor method for the Exam Schedule Vector
     * @pre none
     * @return Vector of Vector of Strings of the exam schedule
     * @post returns the scheudle Vector
     */
    public Vector<Vector<String>> getSchedule() {
        return schedule;
    }
    
    /**
     * Creates a clone Vector of the schedule
     * @pre schedule is a Vector
     * @return a Vector of Vector of Strings of the exam schedule that is a clone
     * @post returns a clone of hte scheudle Vector
     */
    public Vector<Vector<String>> clone() {
        return (Vector) schedule.clone();
    }

    /**
     * Creates the Final Exam Schedule and Runs the Extensions 1, 2, and 3 for the Lab
     * @pre the user gives a properly formatted input file
     * @param args String array of user input
     * @post Outputs a sample exam schedule and the Extension 1 and 2, and 3
     */
    public static void main(String[] args) {
        ExamScheduler exams = new ExamScheduler();
        
        String text = exams.readingFile();
        exams.organize(text);
        System.out.println("Generating Sample Exam Schedule Used for Extension 1 and 2:");
        exams.printSchedule(exams.getSchedule());

        /// Extension 1:
        exams.extensionOne();

        /// Extension 2:
        exams.extensionTwo();
        
        
        /// Extension 3:
        exams.extensionThree(text);
    }
}