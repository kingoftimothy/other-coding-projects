import structure5.*;
import java.util.Iterator;

/**
 * LexiconNode.java
 * 
 * An implementation of a Node that has a vector of Children Nodes
 * In which words can be formed. It's a representation of a Node
 * in a Lexicon Tree
 */

class LexiconNode implements Comparable<LexiconNode> {

    /* single letter stored in this node */
    protected char letter;

    /* true if this node ends some path that defines a valid word */
    protected boolean isWord;

    /* a data structure for keeping track of the children of
    this LexiconNode */
    protected Vector<LexiconNode> vec;

    /**
     * Creates a LexiconNode that sets up its letter that it refers to
     * and if it is a word. Creates vector for the children Nodes
     * @param letter is a char that associated to the node (lowercase)
     * @param isWord is a boolean that determines if the node forms a word
     * or not with previous nodes
     * @pre letter and isWord are char and boolean respectively
     * @post defines instance variables
     */
    public LexiconNode(char letter, boolean isWord) {
        vec = new Vector<LexiconNode>();
        this.letter = letter;
        this.isWord = isWord;
    }

    /**
     * Accessor method for isWord
     * @pre isWord exists
     * @post isWord is returend
     * @return returns isWord if it is a Word at the node
     */
    public boolean getIsWord() {
        return isWord;
    }

    /**
     * Accessor method for vec
     * @pre vec exists
     * @post vec is returned
     * @return returns Vector<LexiconNode> of the vec used to store children
     */
    public Vector<LexiconNode> getVec() {
        return vec;
    }

    /**
     * Accessor method for letter
     * @pre letter exists
     * @post letter is returned
     * @return returns letter which is the char stored on the node
     */
    public char getLetter() {
        return letter;
    }

    /**
     * Modifier method for isWord
     * @param isWord a boolean that user sets whether the node is a word
     * @pre isWord exists and isWord parameter is a boolean
     * @post isWord is changed to the boolean in the parameter
     */
    public void setIsWord(boolean isWord) {
        this.isWord = isWord;
    }

    /**
     * compareTo method to compare this LexiconNode to another
     * @param o is a LexiconNode that repreresents the node of comparison
     * @pre o is a LexiconNode
     * @post the value of whether the current node's letter is smaller of bigger is returned
     * @return an integer that is 0 if equal, negative if less than, positive if greater
     */
    public int compareTo(LexiconNode o) {
        return letter - o.letter;
    }

    /**
     * Add LexiconNode child to correct position in child data structure
     * @param ln is a LexiconNode that is being added to the vector of Childrens
     * @pre ln is a LexiconNode
     * @post the node ln is added to the vector vec in the right arrangement
     */
    public void addChild(LexiconNode ln) {
        // checks if vector already has ln
        if (vec.contains(ln)){
            return;
        } else {
            // loops through to find correct order to place node within vector
            // in comparison to other vectors
            for (int i = 0; i <= vec.size(); i++) {
                if (vec.size() == 0 || i >= vec.size()) {
                    vec.add(ln);
                    break;
                } else {
                    if (ln.compareTo(vec.get(i)) > 0) {
                    } else {
                    vec.add(i, ln);
                    break;
                    }
                }
            }
        }
    }

    /**
     * Get LexiconNode child for 'ch' out of child data structure
     * @param ch a char that refers to a letter to get corresponding LexciondNode
     * @pre ch is a char
     * @post the LexiconNode is returned or null if not found
     * @return returns the LexiconNode with ch as the letter or if not found returns null
     */
    public LexiconNode getChild(char ch) {
        // loops through vector
        for (LexiconNode node : vec) {
            // chceks if vector has node with the letter
            if (node.letter == ch)
                return node;
        }
        return null;
    }

    /**
     * Remove LexiconNode child for 'ch' from child data structure
     * @pre ch is a char
     * @post removes the Child Node with the correspond ch letter
     * @param ch a char that corresponds to the Lexicon Node that needs to be removed
     */
    public void removeChild(char ch) {
        vec.remove(getChild(ch));
    }

    /**
     * create an Iterator that iterates over children in alphabetical order
     * @pre vec exists as a Vector<LexiconNode>
     * @post the iterator of vec is returned
     * @return a Iterator<LexiconNode> from the Vector<LexiconNode> is returned
     * for a iteration to be allowed through the vector
     */
    public Iterator<LexiconNode> iterator() {
        return vec.iterator();
    }
}
