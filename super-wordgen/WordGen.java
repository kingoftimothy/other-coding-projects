import java.util.Scanner;
import java.util.Random;
import structure5.*;

public class WordGen {
    public static final int OUTPUT_LENGTH = 500;

    /**
     * Try Catch for determing if the input is parasable as an Integer
     * @param input a String to check if Parsable
     * @pre none
     * @post returns boolean value of whether input is parseable as an int
     * @return returns boolean of whether input can be parsed as int
     */
    public static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    //Whichever way you decide to choose a length, be sure to tell us in a comment at the top of your main method.
    /**
     * Main method that generates an output lenght of 500 characters based on user input of a k-level analysis
     * and a specific text file.
     * @param args[0] is the k value that the user inputs
     * @pre args[0] is a valid input that could be converted from a String to an integer and is non-negative
     * and input.txt is a valid input file
     * @post prints the generated 500 words from k-level analysis on input
     */
    public static void main(String[] args) {
        // usage message
        System.out.println("Usage: java WordGen <k> < <input.txt>");

        // Asserts pre-conditions
        Assert.condition(WordGen.isParsable(args[0]), "Please enter k as a valid nonnegative integer.");
        int k = Integer.parseInt(args[0]);
        Assert.condition(k >= 0, "k has to be nonnegative.");
        
        // creates table and analyzes through text
        Table table = new Table();
        Scanner in = new Scanner(System.in);
        StringBuffer textBuffer = new StringBuffer();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            textBuffer.append(line);
            textBuffer.append("\n");
        }
        String input = textBuffer.toString();
        // 'input' now contains the full contents of the input.

        // fills Table with k letters as prefix and the next letter as a possible letter in FrequencyTable
        for (int i = 0; i < input.length(); i++) {
            if (input.length() - 1 >= i + k) {
                table.add(input.substring(i, i+k),input.charAt(i+k));
            } else {
                int num = k - (input.length() - i - 1) - 1;
                String temp = input.substring(i) + input.substring(0, num);
                table.add(temp, input.charAt(num));
            }
        }
        
        // gets first k characters as a seed and adds to the String based on the last k characters
        String output = input.substring(0,k);
        for (int i = 0; i < WordGen.OUTPUT_LENGTH; i++) {
            output += table.choose(output.substring(output.length() - k));
        }
        System.out.println(output);
    }
}