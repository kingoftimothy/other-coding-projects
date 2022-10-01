<!-- Uncomment for Github

layout: page
title: "Lab 1: Nim"

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

In this lab, will write a program to play a two-player game called "Nim."
We will partially develop the solution to this game together, in class.
Other parts of the game you will need to develop yourself.
You are free to use our partial solution or to write your own from scratch.
Whichever approach you choose, be sure to do the following:

1. You must _type in_ the solution yourself.
2. You must _submit_ the solution using `git`.
3. You must ensure that the submitted solution _compiles_, _runs_, and _produces the correct output_.

<hr style="border-color: purple;" />

## Learning Objectives

In this lab, we will do the following things:

1. Spend more time practicing the `git` command.
2. Learn more about Java syntax, and spend some time thinking about class-based design.
3. Use the `javac` command to compile the program.
4. Use the `java` command to run our program.

<hr style="border-color: purple;" />

## Step 1: Clone your repository

First, you want to pull the starter code onto your local machine.
Navigate to your `cs136` folder (or whatever folder you're using to store your local lab work) using the `cd` command. Then, run

```
   $ git clone https://evolene.cs.williams.edu/cs136-labs/<YOUR-USERNAME-HERE>/lab01-nim.git
```

If you run `cd lab01-nim` and then `ls` you will see that we've given you some starter code to help you start writing your program.

<hr style="border-color: purple;" />

## Step 2: Main Lab Program (Nim.java)

Nim is a game where two players take turns removing matchsticks from a set of matchstick piles.
Each player can remove as many matchsticks as they want, but only from a single pile at a time.
At least one matchstick must be removed per turn.
The player forced to remove the last matchstick loses.

This game can be visualized in a variety of ways, but here's a way that is straightforward to code up.
The following shows what happens when we run our program.

```
$ java Nim
Player: 1
[1] | | | |
[2] | | | | | | | |
[3] | | | | | | | |
[4] | | | | |
[5] | | | | | | | |

Which pile [1-5]?
```

When I enter in the pile I want and press `Enter`

```
Which pile [1-5]? 3
```

I am prompted to say how many matchsticks I would like to remove from that pile.

```
How many matches [1-8]? 7
```

Then, if I entered a legal move, the game will update the board and prompt the other player to take a turn.

```
Player: 2
[1] | | | |
[2] | | | | | | | |
[3] |
[4] | | | | |
[5] | | | | | | | |

Which pile [1-5]?
```

The game continues in this manner until a player takes the last match.

```
Player: 2
[1]
[2]
[3] |
[4]
[5]

Which pile [1-5]? 3
How many matches [1-1]? 1
Player 1 wins!
$
```

Any user interface that lets two players play the game is acceptable, so the above is sufficient.
However, you should feel free to be creative here.
Also, if you're looking to stretch yourself, the game mechanics can be improved.
For instance, we know that Player 2 is going to lose before they take their turn, since there is only one match remaining.
Why not finish the game early?

<hr class="style12" />

### Where to start

If you look at the code in your starter repository, you will see two files, `Nim.java` and `TwoPlayerGame.java`.
The first file, `Nim.java`, is a _class implementation_.
All of your edits should be to the `Nim` class.
The second file, `TwoPlayerGame.java`, is an _interface_ that specifies what methods a two player game must contain.
You should not modify `TwoPlayerGame.java` but you are welcome to look at it.
Importantly, your `Nim` class _implements_ the `TwoPlayerGame` interface.
We will discuss exactly how classes and interfaces relate to each other throughout the semester.
For now, it suffices to know that an interface is a kind of _contract_ that any implementing class must follow.

When starting a new project, programmers usually approach a problem in one of two ways.
Which approach you take is a matter of personal preference.

1. The "top-down" approach. Start by writing a `main` method. As you need additional methods (e.g., `isGameOver`), write those. The benefit to this approach is that it probably most naturally mirrors how you think about a program.
2. The "bottom-up" approach. Start by writing each of the required methods in the interface, then when they're all done, connect them all together in your `main` method. The benefit to this approach is that the things you implement first are the simplest things. We can implement and test pieces _independently_ of how the game plays as a whole to the user. So even though this might be a counter-intuitive approach, it can sometimes be easier than the top-down approach.

Either way, you are going to want your program in small pieces so that you can compile and test it frequently.
Remember: all submitted code must compile, so doing small amounts of work and frequently checking to see that it does what you want will help you find bugs in your program.

<hr class="style12" />

### When to commit to `git`

This one is easy: _commit early and commit often_.

Students are often hesitant to commit to `git` until their work is complete, thinking that their early work is "embarrassing."
We advise you not to think this way.

First, if you are not committing to `git` frequently, you are losing out on one of its major benefits: the ability to undo mistakes.
Virtually every programmer I know has accidentally deleted important code at some point in their life.
If a version of your code exists in `git`, recovering that code is a minor inconvenience.
Save yourself the pain and run `git commit` whenever you have even a minor accomplishment.
It's totally OK if your code is incomplete or buggy!

Second, if you are really embarrassed by your in-progress work, you can take some solace: we probably won't ever see it.
Instructors rarely look at anything but your very last commit.

<hr class="style12" />

### When to stop

When all of the methods are complete, the code compiles, and the game can be played like in the example above, you are done coding.
But are you really done?

If you have not written any comments or if your code is hard to read (e.g., the indentation is inconsistent), the answer is no.
All code written for this class must be _correct_, _well-designed_, and _well-documented_.
Again, since we gave you a partial solution, a good approach is to imitate our style.
We spend a lot of time writing code and we think ours looks pretty good.

We will expand on what we expect for good design and documentation throughout the course.

<hr style="border-color: purple;" />

## Step 3: Remove Identifying Information

All assignments in this course are graded anonymously whenever possible.
Do not include identifying information in the code that you submit!
We will know that the files are yours because they are in _your_ git repository.
We grade programs anonymously to ensure that every student is graded fairly.

<hr style="border-color: purple;" />

## Step 4: Submit Your Lab

As you complete various milestones, you should commit your changes and push them.
**Commit early and often.**
When the deadline arrives, we will retrieve the latest version of your code.
If you are confident that you are done, please include "Lab 1 Submission" as the commit message for your final commit.
If you later decide that you have more edits to make, doing so is OK.
We always look at the latest commit before the deadline.

- **Be sure to push your changes to GitLab**
- **Verify your changes on GitLab.** Navigate in your web browser to your private repository on GitLab. It should be available at `https://evolene.cs.williams.edu/cs136-labs/<YOUR-USERNAME-HERE>/lab01-nim.git`
- You should see all changes reflected in the various files that you submit. If not, go back and make sure you committed and pushed.
