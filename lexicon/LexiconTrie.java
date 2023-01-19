import structure5.*;
import java.util.Iterator;
import java.util.Scanner;

/**
 * LexiconTrie.java
 * 
 * An implementation of a Lexicon Tree that has a blank root Node
 * In which a dictionary can be formed from words that build the 
 * Tree (trie) and create different Nodes to  be aepresentation of 
 * a Lexicon Tree.
 */

public class LexiconTrie implements Lexicon {

    // instance variables for root node and number of words in the Trie
    protected LexiconNode node;
    protected int numWords;

    //Creates an Empty LexiconTrie to represent an empty word list.

    /**
     * Creates a LexiconTrie that sets up a Trie built off a blank root node
     * @pre node is a LexiconNode and numWords is an int
     * @post defines instance variables
     */
    public LexiconTrie() {
        node = new LexiconNode(' ', false);
        numWords = 0;
    }

    /**
     * A method that adds a word to the LexiconTrie and adds nodes along
     * the way that aren't there.
     * @param word is a String that needs to be added
     * @pre word should be only letters (still works without but only a pre
     * condition if letters can only exists for this assignment)
     * @post the word and any necessary nodes is added and the last node of
     * the word has a true isWord boolean
     * @return returns boolean whether or not word was added
     */
    public boolean addWord(String word) {
        // lowercase string
        word = word.toLowerCase();
        // returns false if already in trie
        if (containsWord(word)) {
            return false;
        } else {
            // loops through each char in the word
            LexiconNode currentParent = node;
            for (int i = 0; i < word.length(); i++) {
              char ch = word.charAt(i);
              if (currentParent.getChild(ch) == null){
                LexiconNode child = new LexiconNode(ch, false);
                currentParent.addChild(child);
                //addChild already checks for duplicates
                currentParent = child;
                //switches over to looking at the child node
              } else {
                currentParent = currentParent.getChild(ch);
                //since child already exists, switches to existing child node
              }
            }
            currentParent.setIsWord(true);
            //at the end of the word, the flag is set to true
            numWords++;
            return true;
        }
    }

    /**
     * Adds Words from a File to the Trie
     * @param filename is a String that the program should look for to
     * read from a file
     * @pre filename is an existing filename, and file has words listed
     * line by line
     * @post all the words in the files are added
     * @return integer of the count of words added (cuts off duplicates)
     */
    public int addWordsFromFile(String filename) {
        // counts how many words added
        int counter = 0;
        try {
            //scanner to read from file stream
            Scanner in = new Scanner(new FileStream(filename));
    
            //string to hold lines
            String s;
    
            // lops through fiel
            while(in.hasNextLine()) {
                s = in.nextLine();
                if (addWord(s)) {
                    counter++;
                }
            }
            in.close();
        } catch (Exception e) {
            // if file doesnt exist
            System.out.println("Invalid Filename. Couldn't add words to Lexicon!");
        }
        return counter;
    }

    /**
     * A method to remove a word from the Trie. This method completes both removing
     * the flag isWord and removes the necessary nodes (for Extra Credit)
     * @param word is a String that corresponds to the word to be rmeoved
     * @pre word is an existing word to remove (program still handles if its not word)
     * @post the correct return value if String was removed or not and the node at the
     * end of the word is changed to false isWord and the necessary nodes are removed
     * @return returns true if word was removed and false if word wasn't removed
     */
    public boolean removeWord(String word) {
        word = word.toLowerCase();
        // false if no word exists
        if (!containsWord(word))
            return false;
        LexiconNode lastNode = node;
        // counts how many have True isWord on the path of the word
        int trueCounter = 0;
        // loops to the last node
        for (int i = 0; i < word.length(); i++){
            lastNode = lastNode.getChild(word.charAt(i));
            if (lastNode.getIsWord() == true)
                //counts how many nodes for the word being removed
                trueCounter++;
        }
        // makes it not a word
        lastNode.setIsWord(false);
        numWords--;
        // if the next node (children) exists, than no need to remove any additional nodes
        if (lastNode.getVec().size() > 0) {
            return true;
        } else {
            // loops from the start of path and finds the point after the second to last word
            // to start removing nodes from that point on
            LexiconNode current = node;
            for (int i = 0; i < word.length() - 1; i++){
                current = current.getChild(word.charAt(i));
                if (current.getIsWord() == true) {
                    trueCounter--;
                    if (trueCounter == 1) {
                        // when trueCounter is 1, the nodes were only for the word removed
                        current.removeChild(word.charAt(i + 1));
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method for how many words in Trie
     * @pre numWords variable exists
     * @post the number of Words is returned
     * @return an integer of the numWords is returned
     */
    public int numWords() {
        return numWords;
    }

    /**
     * A helper method to find the last node in a word and if it exists in the Trie
     * @param word a String to be found if it is contained in the Trie
     * @pre word is a String
     * @post the last node correspond to the last char of the word is returned or null
     * @return null if there's no child coressponding to the last node in the word
     * or returns the LexiconNode that does coresponds
     */
    protected LexiconNode find(String word) {
        LexiconNode current = node;
        // loops through to see if there exists a child with the correspond character
        for (int i = 0; i < word.length(); i++) {
            if (current.getChild(word.charAt(i)) == null)
                return null;
            else {
                current = current.getChild(word.charAt(i));
            }
        }
        return current;
    }

    /**
     * A method to find if the Trie contains the word
     * @param word a String to be found if contained in Trie
     * @pre word is a String
     * @post boolean returned for whether word exists
     * @return returns whether or not the word existed in the Trie
     */
    public boolean containsWord(String word){
        word = word.toLowerCase();
        // uses helper method to return if there exists word
        if(find(word) != null) {
            return find(word).getIsWord();
        }
        return false;
    }

    /**
     * A method to find if the Trie contains any words with the prefix
     * @param prefix a String to be found if contained as a prefix in Trie
     * @pre prefix is a String
     * @post boolean returned for whether prefix exists
     * @return returns whether or not the prefix existed in the words of Trie
     */
    public boolean containsPrefix(String prefix) {
        prefix = prefix.toLowerCase();
        return find(prefix) != null;
    }

    /**
     * A helper method for the Iterator that recursively goes through the Children
     * @param vec a Vector<LexiconNode> to traverse through to check for words
     * @param words a Vector<String> to add the words created from the vector
     * @param runWord a String that is a runWordword to formulate the strings
     * until the word reaches an isWord flag
     * @pre vec exists as a Vector of LexiconNode and words is a String Vector
     * @post the words Vector is changed to have all the words listed alphabetically
     * in the Trie
     */
    protected void getWordHelper(Vector<LexiconNode> vec, Vector<String> words, String runWord) {
        String runWordVal = runWord;
        for(LexiconNode node : vec) {
            // adds letter of child node to runWord WOrd
            runWord = runWord + node.letter;
            /// checks if word and if it is, it adds to the words vector
            if (node.getIsWord())
                words.add(runWord);
            // recursive call onto the the children of the current node
            getWordHelper(node.getVec(), words, runWord);
            runWord = runWordVal;
        }
    }

    /**
     * A method of an Iterator that iterates over children in alphabetical order
     * @pre vector for the childrens of node exists
     * @post the iterator of the Vector Strings of the list of words in the Trie
     * is returned
     * @return Iterator<String> is retuned to iterate through the Trie words 
     */
    public Iterator<String> iterator() {
        Vector<String> words = new Vector<>(); 
        String runWord = "";
        getWordHelper(node.getVec(), words, runWord);
        return words.iterator();
    }

    /**
     * Recursively creates a set containing possible corrections with the given parameters (Extra Credit)
     * @param target a String that checks for suggested corrections
     * @param maxDistance a maximum Distance of differnece between two strings for the correctiosn
     * @pre target isn't null and the maxDistance is greater than zero
     * @post a set containing possible corrections is returend
     * @return a SetVector<Strings> consisting of the suggested Corrections
     */
    public SetVector<String> suggestCorrections(String target, int maxDistance) {
        // starting node
        LexiconNode curr = node;
        // iterator that iterates through vector children of the node
        Iterator<LexiconNode> iter = curr.iterator();
        // dictionary of the SetVector that holds corrections
        SetVector<String> dict = new SetVector<String>();
        String word = "";
        // calls helper function
        correctionsHelper(dict, curr, iter, target, word, maxDistance);
        return dict;
    }
  
    /**
     * A helper method for Corrections Helper
     * @param dict dictionary of SetVector<String>
     * @param curr LexiconNode for current node
     * @param iter Iterator<LexciconNode> for children vector iterator of node
     * @param target String from target from user
     * @param word String that adds words to dict
     * @param maxDistance integer for maxDistacne of corrections
     * @pre target isn't null and the maxDistance is greater than zero
     * @post a set containing possible corrections is modified
     */
    protected void correctionsHelper(SetVector<String> dict, LexiconNode curr, Iterator<LexiconNode> iter, String target, String word, int maxDistance) {
        // builds onto the dict dictionary
        while (iter.hasNext()){ 
            // loops through children if children nodes exist
            // next child
            LexiconNode child = iter.next();
            // builds word with child's letter
            word = word + child.getLetter();
            int currDistance = maxDistance;
            // loops through the word if less than target String length
            for (int i = 0; i < word.length() && i < target.length(); ++i) {
                if (word.charAt(i) != target.charAt(i)) {
                    // substracts distance everytime a wrong letter is matched
                    --currDistance;
                }
            }
            if (word.length() == target.length() && currDistance >= 0 && child.isWord == true) {
                // checks possible suggestion
                // only checks for combinations of letters that are also words
                // if its a word, adds to the dict
                dict.add(word);
            }
            // iterator for children of child nodes
            Iterator<LexiconNode> childIter = child.iterator();
            if (childIter.hasNext()) {
                // recursive call on children
                correctionsHelper(dict, child, childIter, target, word, maxDistance);
                // resets word
                word = word.substring(0, word.length() - 1);
            } else {
                // new String for a new if not found
                word = word.substring(0, word.length() - 1);
            }
        }
    }

    /**
     * Creates set containing strings that match a given regular expression
     * (Extra Credit)
     * @param pattern a String for the pattern match
     * @pre pattern is a recognizable regex
     * @post a Set<String> returned with all possible regex matches
     * @return a Set<String> with all regex matches
     */
    public Set<String> matchRegex(String pattern){
        // creates regex set to be returned
        Set<String> regex = new SetList<String>();
        if(pattern.equals(null))
            return regex;
        pattern = pattern.toLowerCase();
        // calles helper method
        matchRegexHelper(node, regex, pattern, new String(""));
        // returns set
        return regex;
    }
       
    /**
     * Helper function for regex interpreter
     * @param n LexiconNode to find regex on children
     * @param regex Set<String> to return
     * @param pattern String given from user
     * @param runWord running Word String that adds on to string
     * @pre pattern is a recognizable regex
     * @post a Set<String> modified with all possible regex matches
     */
    private void matchRegexHelper(LexiconNode n, Set<String> regex, String pattern, String runWord) {
        // Base case: if pattern is emptystring and the runWord string is word
        if ((pattern.length() == 0 && n.getIsWord()))
            regex.add(runWord);
        // Checks pattern isn't empty and node has children
        if(pattern.length() > 0 && n.getVec().size() > 0) {
            // gets character at index 0 of patter
            char l = pattern.charAt(0);
            // Checks for ? or *
            if (l == '?' || l == '*') {
            // iterator of children for the node
            Iterator<LexiconNode> i = n.iterator();
            // ? and * leads to removing first letter of patter advancing the next node
            // with the letter pattern, recursive clal 
            matchRegexHelper(n, regex, pattern.substring(1), runWord);
            // loops through iterator for children nodes
            while (i.hasNext()) {
                n = i.next();
                // for *, doesn't change pattern but adds node's letter to runWord
                // recursive call on itself
                if (l == '*') {
                    matchRegexHelper(n, regex, pattern, runWord + n.getLetter());
                }
                // for ?, removes first letter of pattern and adds letter to runWord
                // recursive call with new node
                else { matchRegexHelper(n, regex, pattern.substring(1), runWord + n.getLetter());
                }
            }	
            // it must be letter or other character if child is in node's children
            // if null, no possible words
            } else if (n.getChild(l) != null) {
                matchRegexHelper(n.getChild(l), regex, pattern.substring(1), runWord + n.getChild(l).getLetter());
            }
        }
    }
}
