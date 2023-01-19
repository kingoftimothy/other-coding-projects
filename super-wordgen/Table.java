import java.util.Scanner;
import java.util.Random;
import structure5.*;

/**
* A Table holds a collection of strings, each of which has an
* associated FrequencyTable
*/
public class Table {
  // instance variable of Hastable
  protected Hashtable<String,FrequencyTable> table;

  /** Construct an empty table 
   * @pre none
   * @post instantiates the Hashtable
  */
  public Table() {
    table = new Hashtable<String,FrequencyTable>();
  }

  /**
  * Updates the table as follows
  * If key already exists in the table, update its FrequencyTable
  * by adding value to it
  * Otherwise, create a FrequencyTable for key and add value to it
  * @param key is the desired k-letter sequence
  * @param value is the character to add to the FrequencyTable of key
  * @pre value is a char
  * @post adds the char to the key's Frequency Table or creates new
  * key and FrequencyTable pair if not originally there.
  */
  public void add(String key, char value) {
    // if key is found, adds value to the FrequencyTable
    if(table.containsKey(key)) {
      table.get(key).add(value);
    } else {
      // if not found, create new FrequencyTable to the key in the table
      FrequencyTable newTab = new FrequencyTable();
      newTab.add(value);
      table.put(key, newTab);
    }
  }

  /**
  * If key is in the table, return one of the characters from
  * its FrequencyTable with probability equal to its relative frequency
  * Otherwise, determine a reasonable value to return
  * @param key is the k-letter sequence whose frequencies we want to use
  * @pre none
  * @post returns random character on probability for the key
  * @return a character selected from the corresponding FrequencyTable
  */
  public char choose(String key) {
    // chooses random letter in Frequency table for the key 
    if(table.containsKey(key)) {
      return table.get(key).choose();
    }
    // return '?' if no character
    return '?';
  }

  /** Produce a string representation of the Table
   * @pre none
   * @post prints out table
   * @return a String representing this Table
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
    Table table = new Table();
    String text = "timothy";
    String text1 = "rainism";
    String text2 = "aaaaaaaaaaas";
      for (int i = 0; i < text.length(); i++) {
        table.add(text,text.charAt(i));
      }
      for (int i = 0; i < text1.length(); i++) {
        table.add(text1,text1.charAt(i));
      }
      for (int i = 0; i < text2.length(); i++) {
        table.add(text2,text2.charAt(i));
      }
      System.out.println(table.choose(text));
      System.out.println(table.choose(text));
      System.out.println(table.choose(text1));
      System.out.println(table.choose(text1));
      System.out.println(table.choose(text2));
      System.out.println(table.choose(text2));
      System.out.println();
      System.out.println(table.toString());
  }

}
