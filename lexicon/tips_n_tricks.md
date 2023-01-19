<!--
---
layout: page
title: 'Lab 8: Tips and Tricks'
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
	background: url(http://ibrahimjabbari.com/english/images/hr-12.png) repeat-x 0 0;
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

# Lab 8: Tips and Tricks

This document should help you to organize your design and plan your implementation for the Super Lexicon Lab. Be sure to read the [lab description](lexicon.html) thoroughly!

<hr style="border-color: purple;" />

## Super Lexicon Classes

* `Main.java`: driver program. No need to modify!
* `Lexicon.java`: Interface. No need to modify!
* `LexiconTrie.java`: implements interface. You will make changes here.
* `LexiconNode.java`: a node in the trie. You will make changes here. Methods here do <b>not</b> need to be recursive! Also, if you use a list-like data structure to store children, then when you add, be sure to add nodes in order. You don't necessarily have to use an `OrderedStructure`, although an `OrderedStructure` could work.

<hr style="border-color: purple;" />

## Using Main.java to Test

```
$ java Main
```

If you hit the `Enter` key, you will be given a list of options.

|shortcut key|command|syntax|description|
|-|-|-|-|
|`a`|`add`|Add &lt;word&gt;|Add word to lexicon|
|`c`|`contains`|Contains &lt;str&gt;|Search lexicon for word/prefix|
|`rem`|`remove`|Remove &lt;word&gt;|Removes word from lexicon|
|`rea`|`read`|Read &lt;filename&gt;|Add words from named file to lexicon|
|`p`|`print`|Print|Print all words in lexicon|
|`s`|`suggest`|Suggest &lt;target&gt; &lt;dist&gt;|Find suggestions for target within distance|
|`m`|`match`|Match &lt;regex&gt;|Find matches for pattern|
|`q`|`quit`|Quit|Quit the program|
|`i`|`iter`|iter|test iter|

After selecting a command, `Main` will execute the corresponding methods in your `LexiconTrie` code. (This is why it is important to have "stubs" for functions that are not yet implemented.) Test your functionality
incrementally!

<hr style="border-color: purple;" />

## Miscellaneous Notes

* Unlike our `BinaryTree` implementation, our `LexiconTrie` consists of `LexiconNode`s. So in some ways, this is similar to our `SinglyLinkedList` implementation.
* Only `matchRegex` and `suggestCorrections` have to be recursive. Other methods can be done recursively, but you will not be penalized in any way if you choose to implement them iteratively. Do not make the other methods overly complicated! Test your code frequently!

<hr class="style12" />

### Suggested Approach

* Start with the section titled [Managing Trie Node Children](lexicon.html#managing) and implement `LexiconNode`. Pick a data structure that will store the children of the node. Work through the methods in `LexiconNode`. Note that a simple way to compare `char`s is to subtract one character from another.
* After completing `LexiconNode.java`, move on to `LexiconTrie.java`. You'll need to add a constructor. The constructor should create a single `LexiconNode` that has the character assigned to be `' '` (a blank space). 
* The section [Searching for Prefixes and Words](lexicon.html#searching) describes `containsWord` and `containsPrefix`. The technique used in both of these methods is basically the same. `containsWord` performs one additional test before returning to see if the `isWord` flag is set to be `true`. You may find that creating a helper method `LexiconNode find(String word)` that returns `null` or a `LexiconNode` to be helpful here. Note that you can implement this method with or without recursion! Either way is acceptable.
* Move on to `addWord` and `addWordsFromFile`. `addWordsFromFile` will use a `Scanner` to parse an input file (line by line, with a single word per line) and call `addWord` for each line. Be sure to update `size`. Convert everything to lowercase, too. 
* `removeWord` may be implemented recursively or iteratively. If you choose to do it recursively, you may want to use a helper method. Either way, be sure to return `true` if the word appeared in the lexicon and was removed, and `false` otherwise. This method is tricky, so think before you type! 
* For the iterator (see [Other Trie Operations](lexicon.html#other)), create a helper method that recursively builds a `Vector` of words. Keep in mind that the `LexiconNode`s already maintain a sequence of their children in sorted order. That will help you iterate over the trie in alphabetical order easily.
* The section [Optional Extensions](lexicon.html#optional) describes optional extensions for the lab. In these sections, you implement two recursive methods for manipulating the trie. You may create helper methods as needed for both of these methods. You may find it helpful to revisit the `printSubsetSum` and `countSubsetSum` methods from the [Recursion Lab](../lab3/recursion.html). 
