import structure5.*;

public class TwoTowers {
    /**
      * A helper method to calculate the sum of the doubles in a Vector<Double>.
      * @param vec is a Vector<Double> that the sum is trying to be calculated for.
      * @pre There exists a vec that is a Vector of Doubles.
      * @post The sum returned is a number greater than or equal to 0 that is the total of vector.
	  * @return a double sum that is the total of all the doubles added together in the vector.
      */
    protected static double sum(Vector<Double> vec) {
        double sum = 0;
        // calculates sum of each value in vector
        for (int i = 0; i < vec.size(); i++) {
            sum += vec.get(i);
        }
        return sum;
    }

    /**
      * A helper method to replace each double in a Vector<Double> with its square.
      * @param vec is a Vector<Double> that the squares are calculated for.
      * @pre There exists a vec that is a Vector of Doubles.
      * @post The vector returned has doubles that are whole numbers.
	  * @return a Vector<Double> that has the square of the each double in the 
      */
    protected static Vector<Double> square(Vector<Double> vec) {
        int count = 0;
        // sets each value in the vector to its square as a whole number double
        // by removing the repeating decimal from calculator error like .999999999
        for (Double d : vec) {
            vec.set(count, (double) Math.round(Math.pow(d, 2)));
            count ++;
        }
        return vec;
    }

    /**
      * An precondition method that checks for if the input is valid.
      * @param test is a String that checks if the user entered a valid nonnegative Integer
      * @pre none
      * @post returns if the input is valid
	  * @return a boolean for whether the input is valid
      */
    protected static boolean checkInput(String test) {
        try {
            int n = Integer.parseInt(test);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer!!!");
            return false;
        }
    }

    /**
   	  * Main method that takes user input as to the hiehgt of the tower
	  * and caculating the best and second best Subset closest to the
	  * halfHeight value that would solve the problem of finding Two
      * Towers that are closely equal.
	  * @param args is a String[]
      * @pre args[0] is a nonnegative integer
      * @post prints out the Half Height, Best Subset, and Second Best Subset
   	  */
    public static void main(String[] args) {
        // creates best and second Vectors and inital values
        double best = 0.0;
        Vector<Double> bestVec = new Vector<>();
        double sec = 0.0;
        Vector<Double> secVec = new Vector<>();
        // asserts pre condition
        Assert.condition(checkInput(args[0]), "The user input for the Tower height has to be a nonnegative integer!!!");
        Assert.condition(Integer.parseInt(args[0]) >= 0, "The user input for the Tower height has to be a nonnegative integer!!!");
        int n = Integer.parseInt(args[0]);
        // gets sqrt of 1 to n and puts in vector vec
        Vector<Double> vec = new Vector<Double>();
        for (int i = 1; i <= n; i++) {
            vec.add(Math.sqrt(i));
        }
        // initiates iterator
        SubsetIterator<Double> subset = new SubsetIterator<>(vec);
        double halfHeight = sum(vec) / 2;

        // finds best and second best Vector
        for (Vector<Double> v : subset) {
    		if (sum(v) < halfHeight && sum(v) > best)
            {
                best = sum(v);
                secVec = bestVec;
                sec = sum(bestVec);
                bestVec = v;
            } else if (sum(v) < halfHeight && sum(v) > sec) {
                sec = sum(v);
                secVec = v;
            }
        }
        // prints output
        System.out.println("Half height (h/2) is: " + halfHeight);
        System.out.println("The best subset is: " + square(bestVec).toString() +  " = " + best);
        System.out.println("The second best subset is: " + square(secVec).toString() +  " = " + sec);
    }
}