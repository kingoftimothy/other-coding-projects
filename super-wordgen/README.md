<!--
---
layout: page
title: 'Lab 9: Random Writing'
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
	font-weight: 900;
  }
</style>
-->

# Lab 9: Random Writing

This week's lab program will analyze letter frequencies in text documents and then generate new documents based on those frequencies.
Each class implements unique functionality and together they will solve a larger problem.
This lab also makes repeated use of the `structure5.Hashtable<K,V>` implementation, which is a kind of `Map` maintained using hashing techniques.

Finally, this lab makes extensive use of what we call a _has a_ relationship:
where one class _has a_ member variable that stores an instance of another class.
Careful planning will help tremendously, so take the time to write a well thought-out design document _before_ arriving for lab.

<hr style="border-color: purple;" />

## Partner Lab

This is a partner lab.
You may work with <em>one</em> other person of your choosing, or if you are looking for an extra challenge you may work entirely by yourself.
Although you are permitted to jointly develop code with your partner, each of you must independently submit your code.
_No copying of code is permitted._
_You must independently retype any code you develop with your partner._

Indicate your partnering arrangement (including those flying solo) by filling out [the following form](https://forms.gle/UZ9FeTZRkqTKdJfH7).

You would help finding a partner, please indicate so on the form and we will do our best to find you one.

<hr style="border-color: purple;" />

## PRE-LAB Step 1: Design Document

Please read through the rest of this document before lab.
You should prepare a written design for your program before lab on Wednesday.
You must think about this lab before writing code!
In the past, students have found it very helpful to draw a picture showing the relationship between each of the classes.

<hr style="border-color: purple;" />

## Step 1: Lab Program

 Consider the following three quotes:
 
     Call me Ishmael.  Some years ago--never mind how long
     precisely--having little or no money in my purse, and nothing
     particular to interest me on shore, I thought I would sail about a
     little and see the watery part of the world.

     Call me Ishmael.  Some years ago--never mind how long
     precisely--having repeatedly smelt the spleen respectfully, not to
     say reverentially, of a bad cold in his grego pockets, and throwing
     grim about with his tomahawk from the bowsprit?

     Call me Ishmael, said I to myself.  We hast seen that the lesser man is
     far more prevalent than winds from the fishery.

The first quote is the first sentence of Herman Melville's 1851 novel, _Moby Dick_.
The other two quotes were generated "in Melville's style" using a simple algorithm developed by Claude Shannon in 1948 [[footnote: 1](#footnote1)].
You will implement Shannon's algorithm in this week's lab.

In addition to producing mildly entertaining output, this lab introduces you to:

* the `Hashtable` class,
* building nested structures out of this building block, and
* the design and implementation of several new classes.

<hr style="border-color: purple;" />

### Rationale

In the following three sections (_Level 0_, _Level 1_, and _Level k_), we build up the intuition for what the algorithm does.
You will ultimately write a _level k_ analyzer and text generator.
A _level k analysis_ "learns" from some training text, creating a big table of learned facts along the way.
That big table can then be used to _generate_ some text.
We begin with small values of _k_...

<hr class="style12" />

#### Level 0 analysis

The following algorithm is based on letter probability distributions.
Imagine taking the novel _Tom Sawyer_ and counting the frequency with which each character occurs.
You'd probably find that spaces are the most common, that the character `'e'` is fairly common, and that the character `'q'` is rather uncommon.
After completing this _level 0_ analysis, you'd be able to produce _Tom Sawyer_-esque text based on character frequencies.
The idea is to randomly select each character based on the probability of it's occurrence in the text.
This random text wouldn't have much in common with the real novel, but characters would tend to appear with the same proportions.
In fact, here's an example of what a _level 0_ analysis might produce:

    rla bsht eS ststofo hhfosdsdewno oe wee h .mr ae irii ela
    iad o r te u t mnyto onmalysnce, ifu en c fDwn oee iteo

<hr class="style12" />

#### Level 1 analysis

Now imagine doing a slightly more sophisticated _level 1_ analysis by determining _the frequency with which each character follows every other character_.
You would probably discover that `'h'` is more likely to follow `'t'` than `'x'`, and you would probably discover that a space follows `'.'` more frequently than `','` does.

More concretely, if you are analyzing the text `"the theater is their thing"`, `'e'` appears after '`h'` three times, and `'i'` appears after `'h'` one time;
no other letters appear after `'h'`.
So, given this example,

* the _probability_ that `'e'` follows `'h'` is `0.75`,
* the _probability_ that `'i'` follows `'h'` is `0.25`,
* the _probability_ that any other letter follows `'h'` is `0`.

Using a _level 1_ analysis, you could produce randomly generated _Tom Sawyer_-esque text by 1) picking a character, and then 2) always choosing the next character based on the probability of the next character given the chosen character.
Here's an example of _level 1_ random text:

    "Shand tucthiney m?" le ollds mind Theybooure
    He, he s whit Pereg lenigabo Jodind alllld ashanthe ainofevids tre
    lin--p asto oun theanthadomoere

<hr class="style12" />

#### Level _k_ analysis

Now imagine doing a _level k_ analysis by determining the probability with which each character _follows every possible sequence of characters of length k_.
For example, a _level 5_ analysis of _Tom Sawyer_ would reveal that `'r'` follows `"Sawye"` more frequently than any other character.
After a _level k_ analysis, you would be able to generate _Tom Sawyer_-esque text by always choosing the next character based on the previous _k_ characters and the probabilities revealed by the analysis.

At only a moderate level of analysis (levels 5—7), randomly-generated text begins to take on many of the characteristics of the source text.
It probably won't make complete sense, but you'll be able to tell that it was derived from Tom Sawyer as opposed to, say, Moby Dick.
Here are some more examples:

         (Level 2:) "Yess been." for gothin, Tome oso; ing, in to
             weliss of an'te cle - armit. Papper a comeasione, and smomenty,
             fropeck hinticer, sid, a was Tom, be suck tied. He sis tred a
             youck to themen

         (Level 4) en themself, Mr. Welshman, but him awoke, the
             balmy shore. I'll give him that he couple overy because in the
             slated snufflindeed structure's kind was rath. She said that the
             wound the door a fever eyes that WITH him.

         (Level 6:) people had eaten, leaving. Come - didn't stand it
             better judgment; His hands and bury it again, tramped herself!
             She'd never would be. He found her spite of anything the one was a
             prime feature sunset, and hit upon that of the forever.

         (Level 8:) look-a-here - I told you before, Joe. I've heard
             a pin drop. The stillness was complete, however, this is awful
             crime, beyond the village was sufficient. He would be a good
             enough to get that night, Tom and Becky.

         (Level 10:) you understanding that they don't come around in
             the cave should get the word "beauteous" was over-fondled, and
             that together" and decided that he might as we used to do - it's
             nobby fun. I'll learn you."

Once we have processed input text and stored it in a table structure that allows us to check probabilities, we pick _k_ letters (for example, the first _k_ in the input text) to use as a "seed" for our new text.
Then we choose subsequent characters based on the preceding _k_ characters and their probability information.

<hr class="style12" />

#### Program Design

To start, we focus on implementing a _level 2_ analysis.
That is, we will compute the next character to print based on the previous two characters only.
If you design your _level 2_ analysis correctly, it will not be difficult to change it to an arbitrary _level k_.

Think about the design and prepare a written design description of this program.
Just like last week, you should have your design prepared when you come to lab.
We will briefly discuss the general structure of the classes together at the beginning of lab.
We have provided implementation strategies below to help you decide which classes to include and what functionality each class should implement.

When thinking about the design, focus on what would constitute a good data structure for this problem.
Your data structure needs to store a _table_ of information and be able to support two key operations:

* update the probabilities in the table, given a string of 2 characters and the succeeding character.
* select a next character, given a string 2 characters and the probabilities stored in your table.

To support those operation, you will develop a `Table` class which is implemented as a `Hashtable<String,FrequencyTable>`.
The `String` represents the _key_ in the `Hashtable`;
the key represents the _k_ character _prefix_ used in a _level-k_ analysis.

Since many possible characters can follow a length-_k_ prefix, that's what the `FrequencyTable` stores.
A `FrequencyTable` is also implemented using a hash table, in this case, a `Hashtable<Character,Integer>`.
The key in the `FrequencyTable`'s hash table represents a one-character _suffix_;
the value represents a count of how many times the given prefix-suffix combination occurs so far.
Think carefully about what methods the frequency table needs and any instance variables that might be useful.

The data structure design built from these two classes has the benefit of requiring only as many entries as are necessary for the given input text.
You may find it helpful to look carefully at the word frequency program in Section 3.3 of _Bailey_.

Your `main` method for the program should be written in a third class, `WordGen`, which reads the input text, builds the table, and prints out a randomly-generated string based on the character sequence probabilities from the input text.
The length of the output is up to you, but it should be long enough to demonstrate that your code works.
For example, you could define a constant in your `WordGen` class,

```
public static final int OUTPUT_LENGTH = 500;
```

and generate an output of that length.
Or, you could choose some other length, like asking for a `length` parameter on the command line.
Whichever way you decide to choose a length, _be sure to tell us in a comment at the top of your `main` method._
You might also consider printing out a helpful message if the user does not call your `WordGen` program correctly, e.g.,

```
Usage: java WordGen <k> < <input.txt>
```

To summarize, you will write three classes for this lab: `Table`, `FrequencyTable`, and `WordGen`.
<u>The design doc you prepare should include a description of each of them.
(In addition to text, you may also find it helpful to illustrate how the three classes interact with each other by creating a drawing or diagram.)</u>

<hr style="border-color: purple;" />

## Importing Class Definitions

This class must use the `Hashtable` class from `structure5` as well as the `Random` and `Scanner` classes from `java.util`.

<hr style="border-color: purple;" />

## Implementation Strategy

You should build your program in stages that you have planned out _ahead of time_.
While writing and debugging your code, try using a fixed `String` constant as the input (e.g., `"the theater is their thing"`) and use a fixed _k_ = 2.
Using these fixed parameters will help you debug your code since you can verify the correctness of your program's outputs by hand on paper.

After the input is processed you should generate and print out new text using the frequencies in the table.
You may start with a fixed sequence of letters that appears in the table or choose starting characters randomly.
Generate and print about 500 letters of randomly-generated text so that we can see how your program works.
Be sure to handle the special case where you encounter a sequence that has no known successor character in a "reasonable" way (i.e., your program should not throw an exception).

Once the basic program is working, change it to accept input from the keyboard using the `Scanner` class.
When using the `Scanner`, build up a string of the entire input line by line before performing any frequency analysis.
You can use the `hasNextLine()` method to find out if there is another line of input ready and the `nextLine()` to read the next line if it exists, as in the following code snippet.
The following code uses a `StringBuffer` to create large strings efficiently.

    Scanner in = new Scanner(System.in);
    StringBuffer textBuffer = new StringBuffer();
    while (in.hasNextLine()) {
        String line = in.nextLine();
        textBuffer.append(line);
        textBuffer.append("\n");
    }
    String text = textBuffer.toString();
    // 'text' now contains the full contents of the input.

You can signal the end of the input for Java on the Mac or on any Unix system by typing `Ctrl-d` on a new line.
(Windows is a bit tricker&mdash;if `Ctrl-d` doesn't work, try `Ctrl-z`.
 You may need to input it twice before it works.)

You may also read your input from any text file, e.g. `whosonfirst.txt`, with this command:

    $ java WordGen < texts/whosonfirst.txt

This way of reading files is not specific to Java.
"Input redirection" can be used with any program running on any Unix system.

Finally, change `WordGen` to support any arbitrary level of analysis _k_ other than 2.
If you have designed the structure well, simply passing longer strings to `Table`'s methods should be sufficient, and you should not need to change any code except in the `WordGen` class.

You can also change your `main` method to use a command line parameter to specify the level of analysis.
For example, it would be convenient to be able to run the program like so:

    $ java WordGen 5 < whosonfirst.txt

to specify a _level 5_ analysis.
Be careful to ensure that there is at least one argument and to convert the first argument to an `int`.

<hr style="border-color: purple;" />

## Style

As we continue to develop our sense of programming style,
we will continue to add and refine the rules in our `checkstyle` tool.
This week, we fixed some of the issues with the first week's `checkstyle`
rules. To do so, we *refined* our "[ERROR]" rule from Lab 1, as explained below.
We are also adding one style requirement this week
*that does not involve checkstyle*; it involves the standard Java compiler,
`javac`.

To earn full style points this week, your code must do two things:
 * Pass all of the `checkstyle` "[ERROR]" checks
 * Compile using `javac` without any messages about "unchecked or unsafe operations".

<hr style="border-color: purple;" />

## Lab Deliverables

Your repository should have the files:

    lab09-super-wordgen/
        checkstyle
        FrequencyTable.java
        README.md
        Table.java
        Wordgen.java
        texts/mobydick.txt
        texts/Seuss.txt
        texts/whosonfirst.txt

<hr style="border-color: purple;" />


## Submitting Your Lab

As you complete portions of this lab, you should `commit` your changes and `push` them. <u>Commit early and often.</u>  

* <u>Be sure to push your changes to GitLab</u>.
* <u>Verify your changes on GitLab.</u> Navigate in your web browser to your private repository on GitLab. You should see all changes reflected in the files that you `push`. If not, go back and make sure you have both committed and pushed.

We will know that the files are yours because they are in _your_ `git` repository. <u>Do not include identifying information in the code that you submit.</u>

<hr style="border-color: purple;" />

## Bonus: Word-level Analysis.

Instead of working at the character level, you could work at the word level.
Only attempt this after you get the required work finished and be sure to submit it as a separate program called `WholeWordgen.java` so that we can grade your character-level analysis separately.
Does this change make the results better or worse?

<hr style="border-color: purple;" />

<a id="footnote1">[1] Claude Shannon, "A mathematical theory of communication", _Bell System Technical Journal_, 1948. Shannon's technique was popularized by A.K. Dewdney's [A potpourri of programmed prose and prosody](https://www.clear.rice.edu/comp200/09fall/textriff/sci_am_paper.htm) that appeared in _Scientific American_, 122-TK, 1989.</a>
