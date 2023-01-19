import structure5.*;

public class SubsetIterator<E> extends AbstractIterator<Vector<E>> {
    // initializes curr and vec for a Vector Iterator
    protected long curr;
	  protected Vector<E> vec;

    /**
	    * Instantiates class variables
	    *
	    * @post resets curr to 0 and sets parameter vector to class vector
	    */
    public SubsetIterator (Vector<E> vec) {
      this.vec = vec;
      reset();
    }

    /**
      * A method to get the next subset Vector.
      * @pre There exists a vec that is a Vector.
      * @post added 1 to curr and returns a vector
	    * @return a Vector that is the next subset
      */
    public Vector<E> next() {
      Vector<E> v = get();
      curr++;
      return v;
    }

    /**
      * A method to check if there is a nextVector available
      * @pre none
      * @post returns if the curr counter has exceeded the 2^n possible subsets
	    * @return a boolean wheter all the subsets have already been given
      */
    public boolean hasNext() {
      return curr < Math.pow(2, vec.size());
    }

    /**
      * A method to reset curr to 0
      * @pre none
      * @post curr is set to 0
      */
    public void reset() {
      curr = 0;
    }

    /**
      * A method to get a subset from the binary of the counter curr.
      * @pre There exists a vec that is a Vector.
      * @post A subset is returned that consists of the positions in the array that corresponds
      * to the index of 1 in the binary number of curr.
	    * @return a Vector subset corresponding to the binary of curr
      */
    public Vector<E> get() {
      Vector<E> subset = new Vector<E>();
      // loops through the long curr and finds the bitwise and to determine the 1s and 0s
      for (int i = 0; i < 64; i++ ) {
        if ((curr & (1L << i)) == (1L << i)) {
          subset.add(vec.get(i));
        }
      }
      return subset;
    }

    /**
   	  * Main method that tests for the getting all the subsets for a given Vector.
	    * @param args is a String[]
      * @pre none
      * @post prints all the subsets of the Vector
   	  */
    public static void main(String[] args) {
      Vector<Integer> test = new Vector<Integer>(8);
      for(int i = 0; i < 8; i++) {
        test.add(i);
      }
      SubsetIterator<Integer> test1 = new SubsetIterator<>(test);
      for (Vector<Integer> v : test1) {
    		System.out.println(v.toString());
      }
    }
}