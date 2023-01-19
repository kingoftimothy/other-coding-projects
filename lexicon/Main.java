/*
 * This main program is provided to test the implementation
 * of your Lexicon class.  You are encouraged to modify this
 * file as needed to fit your testing needs and/or include
 * additional tests of your own design.
 */

import structure5.*;
import java.util.Iterator;

class Main {
    boolean done = false;
    Lexicon lex = new LexiconTrie();

    /*
     * Main allocates the starting empty lexicon and then enters into
     * the command loop that allows the user to enter commands until finished.
     *
     * Note that the syntax and structure for this entire class is a little odd.  This is
     * mostly because it was translated from C++ code.  Do not get bogged 
     * down in the details.
     */
    public static void main(String s[]) {	
	Main m = new Main();
	m.run();
    }

    public void run() {
	ReadStream in = new ReadStream();

	System.out.println("Welcome to the Lexicon Tester. " + 
			   "Hit <return> for list of commands.");
	while (!done) {
	    System.out.println("\nEnter command: ");
	    String tokens[] = in.readLine().split(" ");
	    if (!invokeCmd(tokens))
		System.out.println("Unrecognized command \"" + tokens[0] + 
				   "\".  Hit <return> for list of commands.");
	}
    }

    abstract class Command {
	String abbrev, name, usage, help;
	int numArgs;
	Command(String abbrev, String name, String usage, 
		String help, int numArgs) {
	    this.abbrev = abbrev;
	    this.name = name;
	    this.usage = usage;
	    this.help = help;
	    this.numArgs = numArgs;
	}
	abstract public void performOp(String args[]);
	public String toString() {
	    StringBuffer sb = new StringBuffer(abbrev + "  " + name);
	    while (sb.length() < 14)
		sb.append(" ");
	    sb.append(usage);
	    while (sb.length() < 40)
		sb.append(" ");
	    return sb.append(help).toString();
        }
    }

    private Command[] commands = {
	new Command("a", "add", "Add <word>", "Add word to lexicon", 1) {
	    public void performOp(String commandTokens[]) { testAdd(commandTokens); }
	},
	new Command("c", "contains", "Contains <str>", "Search lexicon for word/prefix", 1) {
	    public void performOp(String commandTokens[]) { testContains(commandTokens); }
	},
	new Command("rem", "remove", "Remove <word>", "Removes word from lexicon", 1) {
	    public void performOp(String commandTokens[]) { testRemove(commandTokens); }
	},
	new Command("rea", "read", "Read <filename>", "Add words from named file to lexicon", 1) {
	    public void performOp(String commandTokens[]) { testReadFile(commandTokens); }
	},
	new Command("p", "print", "Print", "Print all words in lexicon ", 0) {
	    public void performOp(String commandTokens[]) { testIterator(commandTokens); }
	},
	new Command("s", "suggest", "Suggest <target> <dist>", 
		    "Find suggestions for target within distance", 2) {
	    public void performOp(String commandTokens[]) { testSuggestions(commandTokens); }
	},
	new Command("m", "match", "Match <regex>", "Find matches for pattern", 1) {
	    public void performOp(String commandTokens[]) { testRegex(commandTokens); }
	},
	new Command("q", "quit", "Quit", "Quit the program", 0) {
	    public void performOp(String commandTokens[]) { done = true; }
	},
	new Command("i", "iter", "iter", "test iter", 0) {
	    public void performOp(String commandTokens[]) { testIterator(commandTokens); }
	}
    };
	
    /*
     * This method looks for a matching command in the list of
     * available options and if found, invokes the function pointer for
     * that option. 
     */
    boolean invokeCmd(String tokens[]) {
	String userCmd = tokens[0];
	if (userCmd.equals("")) {
	    for (int i = 0; i < commands.length; i++) {
		Command c = commands[i];
		System.out.println(c);
	    }
	    return true;
	}
	
	for (int i = 0; i < commands.length; i++) {
	    Command c = commands[i];
	    if (userCmd.startsWith(c.abbrev) ||
		userCmd.equals(c.name)) {
		if (c.numArgs != tokens.length - 1) {
		    System.out.println("The " + c.name + " command expects " + c.numArgs + " arguments.");
		} else { 		    
		    c.performOp(tokens);
		    return true;
		}
	    }
	}
	return false;
    }

    /*
     * This method adds the given word to the lexicon.
     */
    void testAdd(String commandTokens[])
    {
	String word = commandTokens[1];
	if (lex.addWord(word))
	    System.out.println("\"" + word + "\" added to lexicon." );
	else
	    System.out.println("\"" + word + "\" already was in lexicon." );
	System.out.println("Lexicon now contains " + lex.numWords() + " words." );
    }


    /*
     * This method removes the given word from the lexicon.
     */
    void testRemove(String commandTokens[])
    {
	String word = commandTokens[1];
	if (lex.removeWord(word))
	    System.out.println("\"" + word + "\" removed from lexicon." );
	else
	    System.out.println("\"" + word + "\" wasn't in lexicon." );
	System.out.println("Lexicon now contains " + lex.numWords() + " words." );
    }



    /*
     * This method reports whether the given String is a prefix
     * and a word within the lexicon.
     */
    void testContains(String commandTokens[])
    {
	String str = commandTokens[1];
	boolean contains = lex.containsPrefix(str);
	System.out.println("Prefix \"" + str + "\" is" + (contains ? "" : " not") 
			   + " contained in lexicon." );
	contains = lex.containsWord(str);
	System.out.println("Word \"" + str + "\" is" + (contains ? "" : " not") 
			   + " contained in lexicon." );
    }

    /*
     * This method adds all words from the named file to
     * the lexicon.
     */
    void testReadFile(String commandTokens[])
    {
	String filename = commandTokens[1];
	int count = lex.addWordsFromFile(filename);
	System.out.println("Read " + count + " words from file \"" + filename + "\"." );
	System.out.println("Lexicon now contains " + lex.numWords() + " words." );
    }

    /*
     * This method prints all the words in the lexicon using
     * the lexicon's iterator functionality.
     */
    void testIterator(String unused[])
    {
	System.out.println("Lexicon contains " + lex.numWords() + " words.  Here they are:");
	System.out.println("--------------------------------------------" );
	Iterator it = lex.iterator();
	while (it.hasNext())
	    System.out.println(it.next());
    }


    /*
     * This method prints out all the words from the lexicon
     * that are within max distance of the target String.
     * The args parameter is expected to be target<space>dist.
     */
    void testSuggestions(String commandTokens[])
    {
	String target = commandTokens[1];
	String distStr = commandTokens[2];
	int maxDistance = Integer.parseInt(distStr);

	System.out.println("Words that are within distance " + maxDistance + " of \"" + target + "\"" );
	System.out.println("--------------------------------------------" );
	Set corrections = lex.suggestCorrections(target, maxDistance);
	System.out.println(corrections);
    }


    /*
     * This method prints out all the words from the lexicon
     * that match the regular expression pattern.
     */
    void testRegex(String commandTokens[])
    {
	String pattern = commandTokens[1];
	System.out.println("Words that match pattern " + pattern );
	System.out.println("-----------------------------------" );
	Set matches = lex.matchRegex(pattern);
	System.out.println(matches);
    }
    
}
