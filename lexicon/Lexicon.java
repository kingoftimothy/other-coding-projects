import structure5.*;
import java.util.Iterator;

/*
* This interface is used to represent a lexicon, or word list. The main
* difference between the lexicon abstraction and a dictionary is that
* the lexicon does not provide any mechanism for storing definitions of
* words; a string is just either in the list or not.
*
* The lexicon supports lookup of words and prefixes. You can
* populate the lexicon by adding words one at a time or by reading word
* data from a file.  You can also remove words from the lexicon, and
* write the contents of the lexicon to a file.
*
* Here is sample use of a LexiconTrie object (LexiconTrie implements Lexicon):
*
*	LexiconTrie lex = new LexiconTrie();
*	lex.AddWordsFromFile("lexicon.dat"); {
*	lex.AddWord("doughnut");
*      if (lex.ContainsPrefix("fru") || lex.ContainsWord("ball"))
*		//do something
*/

public interface Lexicon {

    /*
    * This method adds the specified word to this lexicon.
    * It returns true if the word was added (i.e. previously did
    * not appear in this lexicon) and false otherwise. The
    * word is expected to contain only lower/uppercase letters (you choose).
    * This operation runs in time proportional to the length of
    * the word being added.
    */

    public boolean addWord(String word);


    /*
    * This method opens the specified file, expecting a
    * text file containing one word per line, and adds each word
    * to this lexicon. The file must be in the same folder as
    * the executable to be found. The value returned is the count of
    * new words that were added. If the file doesn't exist or was unable
    * to be opened, the function returns 0.
    */

    public int addWordsFromFile(String filename);


    /*
    * This method attempts to remove a specified word from
    * this lexicon. If the word appears in the lexicon, it is removed,
    * and true is returned. If the word was not contained in the lexicon,
    * the lexicon is unchanged and false is returned.  This operation
    * runs in time proportional to the length of the word being removed.
    *
    * Note that you do not need to remove unneeded nodes from the tree
    * (this is an optional extension).
    */

    public boolean removeWord(String word);


    /*
    * This method returns the number of words contained
    * in this lexicon. This operation runs in constant time.
    */
    public int numWords();


    /*
    * This method returns true if the specified
    * word exists in this lexicon, false otherwise. Words are
    * considered case-insensitively.  This operation runs in time
    * proportional to the length of the word being found.
    */

    public boolean containsWord(String word);


    /*
    * This method returns true if any words in
    * the lexicon begin with the specified prefix, false
    * otherwise. A word is defined to be a prefix of itself
    * and the empty string is a prefix of everything.
    * Prefixes are considered case-insensitively. This operation
    * runs in time proportional to the length of the prefix.
    */

    public boolean containsPrefix(String prefix);


    /*
    * This method returns an iterator over all
    * words contained in the lexicon. Accessing the
    * words from the iterator will retrieve them in
    * alpahbetical order. This operations runs in
    * O(N) time where N is the total number of words
    * in the lexicon.
    */
    public Iterator<String> iterator();


    /*
    * This method returns a pointer to a set of strings,
    * where each entry is a suggested correction for the target.
    * All words in the lexicon with a distance to the target that
    * is less than or equal to the parameter distance should be in
    * the returned set. This operation runs in time proportional
    * to the length of the target string.
    */
    public Set<String> suggestCorrections(String target, int maxDistance);


    /*
    * This method returns a pointer to a set of strings,
    * where each entry is a match for the regular expression pattern.
    * All words in the lexicon that match the pattern should be in
    * the returned set.
    */
    public Set<String> matchRegex(String pattern);

}
