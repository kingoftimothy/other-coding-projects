import java.util.Scanner;
import java.util.Random;
import structure5.*;

/**
 * A FrequencyTable stores a set of characters each of which has
 * an associated integer frequency.
 */
public class FrequencyTable {
  // instance variables of Hastable and total count for the class
  protected Hashtable<Character,Integer> table;
  protected int total;

  /**
   * Constructs an empty FrequencyTable.
   * @pre none
   * @post Hashtable table and int total are instantiated
   */
  public FrequencyTable() {
    table = new Hashtable<Character,Integer>();
    total = 0;
  }


  /** add(char ch)
   * If ch is in the FrequencyTable, increment its associated frequency
   * Otherwise add ch to FrequencyTable with frequency 1
   * @param ch is the char to add to the FrequencyTable
   * @pre the char ch exists
   * @post The table increases the Integer Value (frequency) corresponding
   * with the char ch.
   */
  public void add(char ch) {
    // if table has key, add 1 to integer
    if (table.containsKey(ch)) {
      table.put(ch, table.get(ch) + 1);
    } else {
      // if table doesn't have key, make new key and value pair
      table.put(ch, 1);
    }
    total++;
  }

  /** Selects a character from this FrequencyTable with probabality equal to its relative frequency.
   * @pre The table has to contain some key.
   * @post Returns the random character based on proability,
   * @return a character from the FrequencyTable
   */
  public char choose() {
    Set<Character> setOfKeys = table.keySet();
    // asserts pre
    Assert.condition(!setOfKeys.isEmpty(), "Table has to contain a key.");
    // random number generator for the total characters
    Random random = new Random();
    int prob = random.nextInt(total) + 1;

    // finds correspodning character to random number
    for (char key : setOfKeys) {
      prob -= table.get(key);
      if (prob <= 0) {
        return key;
      }
    }
    // default value if none
    return '?';
  }

  /** Produce a string representation of the FrequencyTable 
   * @pre none
   * @post the table is printed out.
   * @return a String representing the FrequencyTable
   */
  public String toString() {
    return table.toString();
  }

  /**
   * Main function used to test functionality of FrequencyTable
   * @param args
   * @pre none
   * @post chooses 6 values and prints String version of Table
   */
  public static void main(String[] args) {
    FrequencyTable test = new FrequencyTable();
    String text = "assdasddddddddccccccccccccccccaaaeeeeeeee";
      for (int i = 0; i < text.length(); i++) {
        test.add(text.charAt(i));
      }
      System.out.println(test.choose());
      System.out.println(test.choose());
      System.out.println(test.choose());
      System.out.println(test.choose());
      System.out.println(test.choose());
      System.out.println(test.choose());
      System.out.println();
      System.out.println(test.toString());
  }
}
