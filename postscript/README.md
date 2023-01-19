<!--
---
layout: page
title: 'Lab 6: P.S. It's Just a Stack'
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

# Lab 6: P.S. It's Just a Stack

In this week's lab (described in <u>Section 10.5</u> of _Bailey_), we will implement a small portion of PostScript, the stack-based programming language.
PostScript was designed to describe graphical images.
When you print to a PostScript printer (almost all laser printers use PostScript), your computer converts your document into a program written in this language.
Your printer then interprets that program in order to render the image on paper.

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


## PRE-LAB: Step 0

Before lab, please do the following:

* <u>Read the Javadoc</u> for the [Token](javadoc/Token.html), [Reader](javadoc/Reader.html), and [SymbolTable](javadoc/SymbolTable.html) starter files. These classes will be helpful in your implementation.
* For more implementation details, skim the [Token.java](starter/Token.java.txt), [Reader.java](starter/Reader.java.txt), and [SymbolTable.java](starter/SymbolTable.java.txt) source files (also in your private repository) before coming to lab.
* Consider preparing a <u>design document</u> for the program before lab so that you can start working right away.  

<hr style="border-color: purple;" />

## Lab Assignment

<u>Complete Laboratory Assignment 10.5</u>, which begins on page 247 of _Bailey_.

Below, we print some additional notes that you will find helpful when completing this lab.

<hr class="style12" />

### Repository Contents
This repository contains the starter files for writing and testing
your PostScript interpreter.

 * In this lab, you only need to modify one Java file: `Interpreter.java`.
 * All files that end in `.ps` are postscript files. You should eventually be able to run them using your interpreter.

## Using Test files
There are several testing programs to help you verify your code as you go.
(You can always run your program interactively and type commands from the
command line as well.)

Rather than waiting until you have implemented all of the commands to test your program, consider running small tests.
We supply a number of tests in the `samples` folder.
But you can create even smaller versions of these tests.

You might start with a subset of `basics.ps`.  For example, I might create a file called `test.ps` and put the first line of `basics.ps` in it:

```
0 pstack
```

This program should print

```
0.0
```

When you add a feature to your interpreter, add the associated command to `test.ps`.
This way, we build up _tested_ functionality incrementally.
Eventually, you should be able to run the entire `basics.ps` test, like so:
```
$ java Interpreter < samples/basics.ps
```

It is a bad idea to wait until you've completed all the functionality necessary to run that program before testing your interpreter.
Start small and test incrementally!

## Creating Javadocs
Javadocs look a litle weird, right?
There's a good reason:
if you've formatted your Javadoc correctly, you can automatically generate Javadoc webpages&mdash;just like the ones we've used for `structure5`&mdash;from your source code.

To create Javadoc documentation from the files in this directory, use the `javadoc` command.
By default, `javadoc` generates a webpage in the current directory.
Since we don't want to clutter our repository, we will tell Javadoc to place
its output inside the `javadoc/` folder we created inside our starter repository.
Run `javadoc` like so:
```
$ javadoc -d javadoc/ *.java
```

Notes:
 * The `-d javadoc/` argument tells the `javadoc` program to place its output in the `javadoc/` directory.
 * The `*.java` argument tells Javadoc to create documentation for every single file in your current directory that ends with the extension `.java`. The `*` is what is called a "wildcard character" (`*.java` matches anything that ends in `.java`).

After you are done, you should see many new files in your `javadoc/` directory:
```
$ ls javadoc/
Interpreter.html         help-doc.html            package-tree.html
Reader.html              index-all.html           resources
SymbolTable.html         index.html               script-dir
Token.html               member-search-index.js   script.js
allclasses-index.html    member-search-index.zip  search.js
allpackages-index.html   overview-tree.html       stylesheet.css
constant-values.html     package-search-index.js  system-properties.html
deprecated-list.html     package-search-index.zip type-search-index.js
element-list             package-summary.html     type-search-index.zip
```

To view my new documentation, I would open the file `index.html`.
This webpage is the "root" of my Javadoc website.

In macOS, I might use the `open` command:
```
$ open javadoc/index.html
```

In Windows, I could use the `explorer.exe` command:
```
$ explorer.exe javadoc/index.html
```

I can also open the file from inside my browser of choice (e.g., Firefox, Chrome, Safari, Edge, etc.) by going to `File -> Open...` and navigating to the `javadoc/index.html` file on my disk.


## Helpful Notes

* Name your interpreter class `Interpreter`. You should only need to modify the `Interpreter` class and nothing else.
* Be sure to make use of the functionality of the classes that you are given. Don't spend time developing code that is already there! Take a few minutes to look at the classes we already gave you, and pay special attention to their constructors.
* Your program should read commands from standard input (`System.in`), which means that you can read from a file using input redirection, just like in previous labs.  For example, you can read the `basics.ps` in the `samples` directory into your `Interpreter` program by using a command like:
    ```
    $ java Interpreter < samples/basics.ps
    ```	 
* Keep your main method _short_. All it should do is create an `Interpreter` object and tell that object to process the PostScript program provided on standard input. Create a method `interpret` that takes a single parameter of type `Reader` and processes the PostScript tokens returned by that `Reader`.
* Develop your `interpret` method incrementally. Get `push`, `pop`, and `pstack` operations working, then move on to the arithmetic operators, and finally the definition and usage of symbols. Decompose the program into small, manageable helper methods as you go!
* Your program should report errors when it encounters invalid input, and these should contain meaningful error messages. You can use `Assert.condition()` and `Assert.fail()` for this, or use the Java `assert` statement. Think about the different operations that might share code.  If you find yourself duplicating code, can you create helper methods that meaningfully handle multiple operations?
* After you've implemented `push`, `pop`, and `pstack`, move on to other operations (`add`, `sub`, `mul`, `div`, `dup`, `exch`, `eq`, `ne`, `lt`, `def`, `quit`, and `ptable`).  The `lt` (less than) instruction is similar to the `eq` instruction, but tests whether one number is less than the other.  
* Implementing the operations above will earn a B for the functionality portion of your grade. You can reach an A by implementing the extensions outlined in thought questions 3 and 4 from the book. In particular, you should implement procedure definitions and calls, and you should implement the `if` instruction. These extensions may require a little thought, but if you understand the design of the interpreter, it should not give you much trouvle.
* Lab machines come with a built-in PostScript interpreter called "GhostScript" that you can use to check your own implementation.
  To use it, type the command
    ```
	$  gs -dNODISPLAY
	```
  This will give you a text-only PostScript interpreter. You can type commands at the prompt as they appear in the lab assignment. Type `quit` to exit the interpreter.

<hr style="border-color: purple;" />

## Style

### Changes to checkstyle for this lab

There are no new rules for the `checkstyle` tool;
however, there is one important lab-specific exception.

For this lab, you are likely to have a method with a very large `switch` statement that, given an operation, calls the appropriate interpreter method.
If that method generates a checkstyle error because it is longer than 30 lines, we will not deduct points from your grade.

Generally, breaking methods into smaller pieces is desirable.
But in this case, keeping all the operator-handling logic in one place is more desirable.
If you write this method carefully (e.g., by paying attention to indentation and commenting), it will still be easy to read.

Other parts of your code will still need to abide by the 30-line rule, and be sure to generally apply the principles of good design you've learned.

### Running checkstyle

To run `checkstyle`, type the following command at the terminal:
```
$ ./checkstyle
```

The `./` is peculiar to Unix: it tells the terminal to look for the
`checkstyle` program in the current directory.
This command runs `checkstyle` on every Java program in your directory.
To run `checkstyle` on a specific Java file, type:
```
$ ./checkstyle SomeFile.java
```

<hr style="border-color: purple;" />

## Lab Deliverables

By the start of lab, you should see a new private repository called `lab06-postscript` in your GitLab account (where `USERNAMES` is replaced by your usernames).

For this lab, please submit the following: 

    lab06-postscript/
        README.md
        Interpreter.java
        Reader.java
        SymbolTable.java
        Token.java
        samples/...

The `Reader.java`, `SymbolTable.java`, and `Token.java` files contain starter code.  `Interpreter.java` should contain your well-documented source code.

Recall in previous labs that you had a Java file that contained a convenient `main` method pre-populated with a variety of helpful tests.  It is always a good practice to create a small set of tests to facilitate development, and you are encouraged to do so here.

As in all labs, you will be graded on design, documentation, style, and correctness. Be sure to document your program with appropriate comments, a general description at the top of the file, and a description of each method with pre- and post-conditions where appropriate. Also, use comments and descriptive variable names to clarify sections of the code which may not be clear to someone trying to understand it.

Whenever you see yourself duplicating functionality, consider moving that code to a helper method. There are several opportunities in this lab to simplify your code by using helper methods.

<hr style="border-color: purple;" />

## Submitting Your Lab

As you complete portions of this lab, you should `commit` your changes and `push` them. <u>Commit early and often.</u> When the deadline arrives, we will retrieve the latest version of your code. If you are confident that you are done, please use the phrase `"Lab Submission"` as the commit message for your final commit. If you later decide that you have more edits to make, it is OK. We will look at the latest commit before the deadline.

* <u>Be sure to push your changes to GitLab</u>.
* <u>Verify your changes on GitLab.</u> Navigate in your web browser to your private repository on GitLab. It should be available at [https://evolene.cs.williams.edu/cs136-labs/[your username]/lab06-postscript.git](https://evolene.cs.williams.edu/cs136-labs/[your username]/lab06-postscript.git). You should see all changes reflected in the files that you `push`. If not, go back and make sure you have both committed and pushed.

We will know that the files are yours because they are in _your_ `git` repository. <u>Do not include identifying information in the code that you submit.</u> We grade your lab programs anonymously to avoid bias.

<hr style="border-color: purple;" />
