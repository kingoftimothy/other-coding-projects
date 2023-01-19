<!--
---
layout: page
title: 'Lab 8: Super Lexicon'
---

<style>
  strong {
    font-size: larger;
    font-variant: small-caps;
    font-weight: bold;
  }
  table {
    border: solid 1px grey;
    border-collapse: collapse;
    border-spacing: 0;
  }
  table thead th {
    background-color: grey;
    border: solid 1px grey;
    color: white;
    padding: 10px;
    text-align: left;
  }
  table tbody td {
    border: solid 1px grey;
    color: #333;
    padding: 10px;
    text-shadow: 1px 1px 1px #fff;
  }
  blockquote {
    margin-left: 2em;
    margin-right: 2em;
  }
  .red {
	color: red;
  }
  .blue {
	color: blue;
  }
  hr.style12 {
	height: 6px;
	background: url(../../images/hr-12.png) repeat-x 0 0;
    border: 0;
  }
  b {
    font-family: sans-serif;
	font-weight: 900;
  }
  .center {
	margin: auto;
	width: 100%;
	text-align: center;
  }
</style>
-->

# Lab 8: Super Lexicon

Virtually all modern word processors contain a feature to check the spelling of words in documents. More advanced word processors also provide suggested corrections for misspelled words. In this lab, you will be taking on the fun and challenging task of implementing such a spelling corrector. You are to implement a highly efficient `Lexicon` class (a lexicon is a set of known words).  Optionally, you choose to augment `Lexicon` with functionality to suggest spelling corrections for misspelled words.

<hr style="border-color: purple;" />

## Solo Lab

This is a solo lab.
You may discuss the problem with other students in the class, but you may not develop code together.
_No sharing or copying of code is permitted._

An example of what kind of collaboration is permitted is reading the lab description together with another person.
You may also draw diagrams (e.g., trees that might be created from sample inputs) together.
You may discuss logic only abstractly (e.g., flowcharts are OK); you must not share code _at all_, either visually or orally.
For example, it is not OK to say "In my `search` method, I call `getChild` and then call ..."
In short, your own code is _for your eyes only_.

<hr style="border-color: purple;" />
## PRE-LAB: Step 1

Be sure to remember to do this pre-lab step: we *will be collecting* your design documents this week.

Please read through this entire handout carefully. Construct a design document for the `LexiconNode` class (as described in the [Managing Trie Node Children](#managing) section below).   You will want to read through the comments in the code when writing your design document.

Be sure to also check out the [Lab 8: Tips and Tricks](tips_n_tricks.html) page for some helpful tips.

<hr style="border-color: purple;" />

## The Lexicon Interface

Your first task is to write a basic class that implements the `Lexicon` interface. The `Lexicon` interface describes the functionality necessary to populate a lexicon by adding and removing words, querying the lexicon for a word, suggesting corrections for a (potentially) misspelled word, and finding all words that match a given pattern.

You should implement the `Lexicon` interface in a file called `LexiconTrie.java`. The following methods are in the `Lexicon` interface.

    public interface Lexicon {
        boolean addWord(String word);
        boolean removeWord(String word);
        boolean containsWord(String word);
        boolean containsPrefix(String prefix);
        int addWordsFromFile(String filename);
        int numWords();
        Iterator<String> iterator();
        Set<String> suggestCorrections(String target, int maxDistance);
        Set<String> matchRegex(String pattern);
    }

Most of the method behaviors can be inferred from their names and return types. For more information about the usage and purpose of these methods, refer to the comments in the starter code.

<hr class="style12" />

#### Implementing the Lexicon as a Trie

There are several different data structures you could use to implement a lexicon: a sorted array, a linked list, a binary search tree, and many others. Each of these offers tradeoffs between the speed of lookup/add/remove, the amount of memory required to store the data structure, the ease of writing and debugging the code, and so on. The implementation we will use in this lab is a special kind of tree called a _trie_ (pronounced "try").  Tries are optimized trees designed for applications like spell-checking.

You can think of a trie as a "letter-tree" that efficiently stores a set of strings. Any given node in our lexicon trie will store a single letter. Any given path through the trie will trace out a sequence of letters that represents either a word prefix or a complete word in the lexicon.

Unlike a binary tree, which has just two children nodes, each trie node in our implementation has up to 26 children nodes, one for each letter of the alphabet. When searching a balanced binary search tree, choosing a left or right path eliminates about half of the possibilities.  By contrast, a search in our trie successively locates the subtree for each next letter, dramatically narrowing the search space to just words starting with that prefix.

For example, from the trie root, any words that begin with `'n'` can be found by following the reference to the `'n'` child node. From there, following `'o'` leads to just those words that begin with `"no"` and so on, recursively. If two words have the same prefix, they share the same initial path. This saves space since there are typically many shared prefixes among words.

Each node has a `boolean` `isWord` flag which indicates that the path taken from the root to a given node represents a complete word. Here's a conceptual picture of a small trie:

<div class="center">
        <img src="https://williams-cs.s3.amazonaws.com/lexicon/trie.svg" width="500px" />
</div>

The thick border around a node denotes that its `isWord` flag is `true`. The pictured trie contains the words: `"a"`, `"are"`, `"as"`, `"new"`, `"no"`, `"not"`, and `"zen"`. Strings such as `"ze"` or `"ar"` are not valid words because the path for those strings do not end at a node where `isWord` is `true`. Any path not in the trie is assumed not to exist, so strings such as `"cat"` and  `"astronaut"` are not valid.

Like other trees, a trie is a recursive data structure. All of the children of a given trie node are themselves smaller tries.  Be prepared to make good use of your recursion skills in this lab!

<hr class="style12" />


#### <a name="managing">Managing Trie Node Children</a>

Each trie node maintains a list of references to children nodes. In the sample trie drawn above, the root node has three children, one for each of the letters `'a'`, `'n'`, and `'z'`.   How can we store these references?

One idea for storing child references is a statically-sized array of 26 references, where `array[0]` contains the entry for `'a'`, `array[1]` contains the entry for `'b'`, ..., and `array[25]` contains the entry for `'z'`. When there is no child for a given letter, (such as from `'z'` to `'x'` in the diagram above) an array entry is `null`. This scheme makes for simple child lookups: to find the subtrie for a given letter, use that letter's index in the alphabet.

Another alternative is a dynamically-sized array which can grow and shrink as needed (e.g., a `Vector`), or even a linked list of child references. We leave the choice of storage mechanism up to you.  You may want to consider two items:

* Each node has at most 26 children, so even `O(n)` operations like lookup on a linked list are effectively `O(1)` (i.e., `n` is never "large").
* Trie operations such as iteration require traversing the words in alphabetical order, so keeping the list of child references sorted by letter is advantageous. 

_Begin by constructing a_ `LexiconNode` _class_. `LexiconNode`s should be comparable, so make sure to implement the `Comparable<T>` interface. After implementing `LexiconNode`, you should create a constructor in `LexiconTrie` that creates an empty `LexiconTrie` to represent an empty word list. Be sure to incrementally compile and test your code.

<hr class="style12" />

#### <a name="searching">Searching for Prefixes and Words</a>

Searching the trie for prefixes and words using `containsWord` and `containsPrefix` is a matter of tracing out the path, letter by letter. Let's consider a few examples on the sample trie shown previously. To determine if the string `"new"` is a word, start at the root node and examine its children to find one pointing to `'n'`. Once found, recurse on the remainder of the string: `"ew"`. Find `'e'` among its children, follow its reference, and recurse again to match `'w'`. Once we arrive at the `'w'` node, there are no more letters remaining in the input, so this is the last node. Since the `isWord` field of this node is `true`, we know that the word `"new"` is contained in the lexicon.

Alternatively, search for `"ar"`. The path exists and we can trace our way through all letters, but the `isWord` field on the last node is `false`, which indicates that `"ar"` is not a word. It is, however, a prefix of other words in the trie.

Searching for `"nap"` follows `'n'` from the root, but finds no `'a'` child.  Therefore, `"nap"` does not exist in the lexicon.  In fact, it is neither a word _nor_ a prefix in this trie (`containsWord` and `containsPrefix` both return `false`).

All paths through the trie eventually lead to a valid node (a `LexiconNode` where `isWord` is `true`). Therefore, determining whether a string is a prefix of at least one word in the lexicon is simply a matter of verifying that the prefix is stored in the trie.

<hr class="style12" />

#### Adding Words

Adding a new word into the trie with `addWord` is a matter of tracing out its path starting from the root, as if searching. If any part of the path does not exist, the missing nodes must be added to the trie. Lastly, the `isWord` flag is turned on for the final node. In some situations, adding a new word will necessitate adding a new node for each letter, for example, adding the word `"dot"` to our sample trie will add three new nodes, one for each letter. On the other hand, adding the word `"news"` would only require adding an `'s'` child to the end of an existing path for `"new"`. Adding the word `"do"` after `"dot"` has been added doesn't require any new nodes at all; it just sets the `isWord` flag on an existing node to `true`. Here is the sample trie after those three words have been added:

<div class="center">
	<img src="https://williams-cs.s3.amazonaws.com/lexicon/trie2.svg" width="500px" />
</div>

A trie is an unusual data structure in that its performance can improve as it more data is added. Instead of slowing down as it gets full, it becomes faster to add words when they can share common prefix nodes with words already in the trie.

<hr class="style12" />

#### Removing Words

The first step to removing a word with `removeWord` is tracing out its path and turning off the `isWord` flag on the final node. Doing this is sufficient for full credit.


However, this is not quite enough: to properly upate the trie, you must remove any part of the word that is now a dead end. (This part, described in the next paragraph, is left as an optional extra credit extension. You only need to update the `isWord` flag for full credit.) All paths in the trie must eventually lead to a word. If the word being removed was the only valid word along this path, the nodes along that path must be deleted from the trie along with the word. For example, if you removed the words `"zen"` and `"not"` from the trie shown previously, you should have the result below.

<div class="center">
	<img src="https://williams-cs.s3.amazonaws.com/lexicon/trie3.svg" width="500px" />
</div>

<b>Optional extension.</b> Deleting unneeded nodes is pretty tricky because of the recursive nature of the trie. Think about how we removed the last element of a `SinglyLinkedList` (Chapter 9.4 in _Bailey_). We had to maintain a reference to the second to last element to update the reference appropriately. The same is true in our trie.

As a general observation, there should never be a leaf node whose `isWord` field is false. If a node has no children and does not represent a valid word (i.e., `isWord` is `false`), then this node is not part of any path to a valid word in the trie and such nodes should be deleted when removing a word. In some cases, removing a word from the trie may not require removing any nodes. For example, if we were to remove the word `"new"` from the above trie, it sets `isWord` on `'w'` to false but all nodes along that path are still in use for other words.

_Important note_: when removing a word from the trie, the only nodes that may require removal are nodes on the path to the word that was removed. It would be extremely inefficient to check additional nodes that are not on the path.

<hr class="style12" />

#### <a name="other">Other Trie Operations That Must be Implemented</a>

There are a few remaining odds and ends to the trie implementation:

* You need to keep track of the total number of words stored in the trie.
* You should add support for reading words from a file using a `Scanner`. You may find the [Scanner handout](../../handouts/scanner.pdf) on the course webpage helpful.
* Creating an iterator to traverse the trie involves a <b>recursive</b> exploration of all paths through the trie to find all of the contained words. Remember that it is only words (not prefixes) that you want to operate on and that the iterator needs to access the words in alphabetical order. You may find the approach used in [`ReverseIterator.java`](ReverseIterator.java.txt) helpful.

<hr class="style12" />

#### <a name="optional">Optional Extensions</a>

Once you have a working lexicon and iterator, you're ready to implement snazzy spelling correction features. There are two additional `Lexicon` member functions, one for suggesting simple corrections, and a second for regular expressions matching:

    Set<String> suggestCorrections(String target, int maxDistance);
    Set<String> matchRegex(String pattern);

`Set`s are basically just fancy `Vectors` that do not allow duplicates. Check out the javadocs on [`Set`](http://www.cs.williams.edu/~bailey/JavaStructures/doc/structure5/structure5/Set.html) and [`SetVector`](http://www.cs.williams.edu/~bailey/JavaStructures/doc/structure5/structure5/SetVector.html) in `structure5` for more information.

These extensions are described in the two sections below.

<hr class="style12" />

#### Optional: Spelling Corrections

First, consider the member function `suggestCorrections`. Given a (potentially misspelled) target string and a maximum distance, this function gathers the set of words from the lexicon that have a distance to the target string less than or equal to the given `maxDistance`. We define the distance between two equal-length strings to be the total number of character positions in which the strings differ. For example, `"place"` and `"peace"` have distance `1`, `"place"` and `"plank"` have distance `2`. The returned set contains all words in the lexicon that are the same length as the target string and are within the maximum distance.

For example, consider the original sample trie containing the words `"a"`, `"are"`, `"as"`, `"new"`, `"no"`, `"not"`, and `"zen"`. If we were to call `suggestCorrections` with the following target string and maximum distance, here are the suggested corrections:

|Target String|Max Distance|Suggested Corrections|
|-------------|------------|---------------------|
|`"ben"`|`1`|{`"zen"`}|
|`"nat"`|`2`|{`"new"`, `"not"`}|

For a more rigorous test, we also provide the word file `ospd2.txt`, which lists all of the words in the second edition of the Official Scrabble Player's Dictionary. Here are a few examples of `suggestCorrections` run on a lexicon containing all the words in `ospd2.txt`:

|Target String|Max Distance|Suggested Corrections|
|-------------|------------|---------------------|
|`"crw"`|`1`|{`"caw"`, `"cow"`, `"cry"`}|
|`"zqwp"`|`2`|{`"gawp"`, `"yawp"`}|

Finding appropriate spelling corrections will require a recursive traversal through the trie gathering those "neighbors" that are close to the target path. You should not find suggestions by examining each word in the lexicon and seeing if it is close enough. Instead, think about how you can generate candidate suggestions by traversing the path of the target string taking small "detours" to the neighbors that are within the maximum distance.

<hr class="style12" />

#### Optional: Matching Regular Expressions

The second optional extension is to use recursion to match regular expressions. The `matchRegex` method takes a regular expression as input and gathers the set of lexicon words that match that regular expression.

If you have not encountered them before, regular expressions are a kind of string-matching pattern. Ordinary alphabetic letters within the pattern indicate where a candidate word must exactly match. The pattern may also contain "wildcard" characters, which specify how and where the candidate word is allowed to vary. For now, we will consider a subset of wildcard characters that have the following meanings:

* The `*` wildcard character matches a sequence of zero or more characters.
* The `?` wildcard character matches either zero or one character.

For example, consider the original sample trie containing the words `"a"`, `"are"`, `"as"`, `"new"`, `"no"`, `"not"`, and `"zen"`. Here are the matches for some sample regular expressions:

|Regular Expression|Matching Words from Lexicon|
|-|-|
|`a*`|{`"a"`, `"are"`, `"as"`}|
|`a?`|{`"a"`, `"as"`}|
|`*e*`|{`"are"`, `"new"`, `"zen"`}|
|`not`|{`"not"`}|
|`z*abc?*d`|{}|
|`*o`|{`"no"`}|

Finding the words that match a regular expression will require applying your finest recursive skills. You should <b>not</b> find suggestions by examining each word in the lexicon and seeing if it is a match. Instead, think about how to generate matches by traversing the path of the pattern. For non-wildcard characters, it proceeds just as for traversing ordinary words. On wildcard characters, "fan out" the search to include all possibilities for that wildcard.

<hr class="style12" />

## Suggestions

* Lexicon operations are case-insensitive. Searching for words, suggesting corrections, matching regular expressions, and other operations should have the same behavior for both upper and lowercase inputs. Be sure to take that into consideration when designing your data structure and algorithms.
* Build and test incrementally. Develop your trie one function at a time and continually test as you go. We have provided a handy client program that exercises the lexicon and allows you to drive the testing interactively from the console. It is supplied in source code form (`Main.java`) and you are encouraged to modify and extend it as needed for your purposes.
* Use "method stubs" as placeholders. The testing code we provide makes calls to all of the public member functions on the Lexicon. In order for the program to compile, you must have implementations for all the functions. However, this doesn't imply that you need to write all the code first and then attempt to debug it all at once. You can implement method placeholders, or "stubs," to start. If your lexicon doesn't yet remove words, implement the remove operation to ignore its argument or raise an error. Before you have implemented regular expression match, just return an empty set from the function and so on.
* Test on smaller data first. There are `small.txt` and `small2.txt` data files with just a few words that are especially helpful for testing in the early stages. You can also create word data files of your own that test specific trie configurations. The `ospd2.txt` word file is very large. It will be useful for stress-testing once you have the basics in place, but it can be overwhelming to try to debug using that version.
* Check out the companion page [Lab 8: Tips and Tricks](tips_n_tricks.md).

<hr style="border-color: purple;" />

## Lab Deliverables

We provide basic starter code for this assignment. The starter code contains the following files:

|Filename|Purpose|
|-|-|
|`Lexicon.java`|The interface that you need to implement.|
|`Main.java`|Sample code that you can use to test your `LexiconTrie`.|
|`LexiconTrie.java`|Skeleton for a `LexiconTrie` implementation.|
|`LexiconNode.java`|Skeleton for the class that represents trie nodes.|
|`small.txt`, `small2.txt`, `ospd2.txt`|Data files containing sets of words for testing purposes.|

You should see a new private repository called `/USERNAME/lab08-lexicon` in your Gitlab account (where `USERNAME` is replaced by your username).

For this lab, please submit the following:

    lab08-lexicon/
        Lexicon.java
        Main.java
        LexiconTrie.java
        LexiconNode.java
        inputs/
            small.txt
            small2.txt
            ospd2.txt

where `LexiconTrie.java` and `LexiconNode.java` should contain your well-documented source code (you need not modify the other `.java` files).

Recall in previous labs that you had a Java file that contained a convenient `main` method pre-populated with a variety of helpful tests.  It is always a good practice to create a small set of tests to facilitate development, and you are encouraged to do so here.

As in all labs, you will be graded on design, documentation, and functionality. Be sure to document your program with appropriate comments, a general description at the top of the file, and a description of each method with pre- and post-conditions where appropriate. Also use comments and descriptive variable names to clarify sections of the code which may not be clear to someone trying to understand it.

Whenever you see yourself duplicating functionality, consider moving that code to a helper method. There are several opportunities in this lab to simplify your code by using helper methods.

<hr style="border-color: purple;" />

## Submitting Your Lab

As you complete portions of this lab, you should `commit` your changes and `push` them. <u>Commit early and often.</u>  

* <u>Be sure to push your changes to GitLab</u>.
* <u>Verify your changes on GitLab.</u> Navigate in your web browser to your private repository on GitLab. You should see all changes reflected in the files that you `push`. If not, go back and make sure you have both committed and pushed.

We will know that the files are yours because they are in _your_ `git` repository. <u>Do not include identifying information in the code that you submit.</u>

<hr style="border-color: purple;" />

