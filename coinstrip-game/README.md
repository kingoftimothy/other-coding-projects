<!-- uncomment for jekyll
---
layout: page
title: 'Lab 2: CoinStrip'
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

In this lab, will write a program to play the Silver Dollar Game found at the end of [Bailey Chapter 3 (page 67)](http://dept.cs.williams.edu/~bailey/JavaStructures/Book_files/JavaStructures.pdf).
In addition to gaining experience writing, compiling, and running programs from scratch, this lab will help us to think about how we can efficiently represent data and break our logic into reusable methods.
Careful planning will help tremendously, so take the time to write a well-thought-out design document _before_ arriving to lab. 

<hr style="border-color: purple;" />

## PRE-LAB: Design Documents
Read through this lab handout and sketch out a design for your Silver Dollar Game program.
You should use the sample [Nim Design Document](https://williams-cs.github.io/cs136-f22-www/assets/lectures/dan/03-lecture_2022-09-14_code/design_doc.html) as a guide.
Please bring your design document to lab, be prepared to discuss it with a partner, and be prepared to submit it.
For this lab, it is OK if the design is rough.
The purpose is to ensure that you think about the lab in advance.

<hr style="border-color: purple;" />

## Step 0:  Install the `structure5` package

This class makes extensive use of the `structure5` suite of data strucctures that accompany the course textbook.
These data structures are designed to be logical, consistent, and easy to use.
Their source code is also written with clarity in mind, to help programmers new to Java.
We ask that you use the `Vector<E>` class from `structure5` in your solution to the Silver Dollar Game.
The following explains how to install `structure5`.

1. Download the file `bailey.jar` from [the textbook's website](https://williams-cs.github.io/cs136-f22-www/assets/JavaStructures/Software.html).
2. Put this file someplace you can easily find it.  For example, you might put it in your `Documents` folder.
   We are going to call that location `<BAILEYPATH>`.
   For example, if I put `bailey.jar` in `/Users/dbarowy/Documents/bailey.jar`, then `<BAILEYPATH>` will equal `/Users/dbarowy/Documents/bailey.jar`.
3. Set your computer's `CLASSPATH` environment variable to point at this file.
   For example, under the macOS,
   - Edit the file `~/.zshrc` and add the following to the end of the file:
     ```
     export CLASSPATH=$CLASSPATH:<BAILEYPATH>
     ```
   - Close your `Terminal.app` and then start it again.
   - To test that you have successfully done the above steps, type
     ```
     $ echo $CLASSPATH
     ```
     You should see `<BAILEYPATH>` in the string printed to the screen.  For example, I see:
     ```
     /Users/dbarowy/Documents/bailey.jar:.
     ```
   Under Windows,
   - On the Windows taskbar, right-click the Windows icon and select `System`.
   - In the Settings window, under `Related Settings`, click `Advanced system settings`.
   - On the Advanced tab, click `Environment Variables`.
   - Look in the `System variables` section.
     * If you see a variable called `CLASSPATH`, click `Edit`. Add `;<BAILEYPATH>` to the end of whatever is there.
     * If you don't see that variable, click `New` and name the variable `CLASSPATH`.  Put `<BAILEYPATH>` as the value.
   - Click `Apply` and then `OK`.
   - To check that your `CLASSPATH` was set correctly open `Git Bash` and type the following:
     ```
     $ echo $CLASSPATH
     ```
     You should see `<BAILEYPATH>` in the string printed to the screen.
   If you have trouble with these steps, please ask us for help.
     
<hr class="style12" />
     
## Step 1: Clone your repository

First, you want to pull the starter code onto your local machine.
First, navigate to your `cs136` folder (or whatever folder you're using to store your local lab work) using the `cd` command.
Then, run
```
   $ git clone https://evolene.cs.williams.edu/cs136-labs/<YOUR-USERNAME-HERE>/lab02-coinstrip.git
```

If you run `cd lab02-coinstrip` and then `ls` you will see that we've given you some starter code to help you start writing your program.

<hr class="style12" />

## Step 2: Main Lab Program (CoinStrip.java)

This week, we will write the Silver Dollar Game at the end of [Bailey Chapter 3 (page 67)](http://dept.cs.williams.edu/~bailey/JavaStructures/Book_files/JavaStructures.pdf).
As you think about the design of your game, you should first decide on a representation of the coin strip. 
Your representation should include the `Vector<E>` data structure from the `structure5` package.

Make sure your representation supports all of the operations necessary such as:


-  Generating different game boards. Your game boards must conform to the following rules: 
    - Each game board must have at least three coins, but the exact number of coins on a given game board must be chosen with some randomness. 
    - The locations of coins must be randomly selected. In other words, over the course of playing several games, I should expect to encounter different starting configurations.
    - No game should be “won” before it starts. In other words, there must be at least one legal move.
-  Testing whether a move is legal. The rules are fully described in the textbook, but here is a summary:
    - No “going right”: Coins can only move to the left.
    - No “double occupancy”: At most one coin can occupy a space at a given time.
    - No “jumps”: Coins cannot pass another coin.
    - “Mandatory progress”: A coin must move one or more spaces.
-  Printing the board. Your game board’s appearance should make it clear to the players exactly what the state of the game is at any given time. It should also provide enough information to the player that they can specify the move that they would like to make next. However, you have some flexibility to add your own “style” to how you display the board. Simple is fine. Elaborate is fine. Just make it clear!
-  Testing for a win: Is the game over?
-  Moving pieces. When a legal move is given, you should be able to adjust the state of the game to reflect that move. This includes things like relocating coins, changing the current player, etc.


When we say "representation," what we mean is: what _data structures_ will you use?  For instance, you might use a `Vector` of `bool` values or perhaps a `Vector` of `int` values.  Once you have chosen a representation, write down the set of operations supported by your `CoinStrip` class.  Think about what parameters they should take, what values they will need to return, and what operations they perform. In lab, we will briefly discuss this initial design with a partner. Bring a printed copy of your design to lab for discussion! We will check design documents at the beginning of lab.

A good exercise to do when designing a representation is to consider alternative designs.  For example, do you want a class that stores the spaces on the game board? Or would it be better to use a data structure that stores the coins themselves? Often it helps to think about the trade-offs: which operations become easier to implement if you choose one representation over another? Professional software engineers almost always do this step out on paper before they start coding, which is why we want you to work on a design document before you start.

The `main` method of your `CoinStrip` class should set up the board and then prompt each of two players to make their moves. A move will be of the form:

    <cn> <ns>
	
where `<cn>` specifies the coin to be moved and `<ns>` specifies the number of spaces that coin should be moved.
	
The program should prompt the user for another input if the move is illegal.

To read input from a terminal window, you should use the `Scanner` class, as we have seen in lecture. Consult the [scanner handout](../../handouts/scanner.pdf), [Oracle Scanner](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html) documentation or sample code from class for details.

Since you have seen a two-player game before, all we have given you for this lab are a `TwoPlayerGame.java` file and a `CoinStrip.java` file.
The latter states that it implements the `TwoPlayerGame` interface, but looking at it, it clearly does not yet.
A good place to start is to copy those methods over and to provide just enough implementation so that `javac` compiles without producing compilation errors.
For example, the following method declaration is in `TwoPlayerGame`

```
public boolean isGameOver();
```

and if we copy it over to our `CoinStrip` class, we might add the following very simple implementation:

```
public boolean isGameOver() {
  return false;
}
```

The above is clearly not what we ultimately want (because if it were, the game would never finish), but it does let us run `javac` without producing a compilation warning for that one method.
If you repeat this process for all of the methods, and running `javac` produces no errors, then you know that your code contains all of the methods required by the `TwoPlayerGame` interface.

<hr class="style12" />

## Step 3: Style

Part of being a relatively new programmer
involves
developing an understanding of what it means to have "good style".
This is made difficult by the fact that "good style" is both hard to quantify,
and hard to agree upon.

To help us all along our lifelong quests to write better code,
we have included a tool inside each of our repositories that checks
our code against some industry-standard, agreed-upon best practices.
This tool is called `checkstyle`.


In this first lab, we have configured `checkstyle` to give warnings
for most classes of stylistic "mistakes" that we might make.
We encourage you to review these warnings and follow the suggestions to
improve your code.

However, we have also configured one `checkstyle` rule to output an error.
You *must* fix all checkstyle errors.
Your program will be graded on both correctness and style, and we will use
`checkstyle` errors as our measure of your program's style.

`checkstyle` will report an error if your program:
 * declares a class variable but does not specify the variable's visibility. All class variables must be declared as `public`, `private`, or `protected`. If you do not specify a class variable's visibility, the variable is given "default" visibility, which is very likely *not* what you want.

It's OK if you're not sure about this error&mdash;we'll be talking more about visibility over the coming week.  If all of your variables are local variables (declared within methods), then this error will not appear.

As the semester progresses, we will convert more and more style checks from
warnings to errors. It is in your best interest to fix all warnings&mdash;both
to develop better coding practices, and to prepare yourself for future labs.

To run `checkstyle`, you would type the following command at the terminal:
```
    ./checkstyle
```

The `./` is a Unix thing: it tells the terminal where to look for the
`checkstyle` program. The `.` (dot) tells Unix to look in the current directory.
This will run `checkstyle` on every Java program in your directory.
To run `checkstyle` on just one Java file, you would instead specify:
```
    ./checkstyle SomeFile.java
```

`checkstyle` is a new addition to the course this Spring, based on student
feedback. We hope it helps! If you have any questions about checkstyle
output, please ask an instructor or a TA for guidance.

<hr style="border-color: purple;" />

## Lab Deliverables

When you are finished with your `CoinStrip` program, commit and push your lab files. Your repository should have the files:
    
    lab02-coinstrip/
        CoinStrip.java
        TwoPlayerGame.java
        README.md

We do not need your `.class` files, as we will compile your code ourselves.
_Be sure that your code compiles before you submit it!_

<hr style="border-color: purple;" />

## Submitting Your Lab

As you complete various milestones, you should commit your changes and push them. **Commit early and often.** When the deadline arrives, we will retrieve the latest version of your code. If you are confident that you are done, please include "Lab Submission" as the commit message for your final commit. If you later decide that you have more edits to make, it is OK. We will look at the latest commit before the deadline.

* **Be sure to push your changes to GitLab**
* **Verify your changes on GitLab.** Navigate in your web browser to your private repository on GitLab. It should be available at  `https://evolene.cs.williams.edu/cs136-labs/<YOUR-USERNAME-HERE>/lab02-coinstrip.git`
* You should see all changes reflected in the various files that you submit. If not, go back and make sure you committed and pushed.

We will know that the files are yours because they are in _your_ git repository. Do not include identifying information in the code that you submit! Our goal is to grade the programs anonymously to avoid any bias.
