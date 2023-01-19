<!--
---
layout: page
title: 'Lab 7: Two Towers'
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

# Lab 7: The Two Towers

This week's lab explores the use of iterators to help solve difficult computational problems.
Be sure that you have read and understood Chapter 8 of _Bailey_, which introduces the Java `Iterator` interface.

<hr style="border-color: purple;" />

## PRE-LAB: Step 1

To get you started with thinking about the assignment, we would like you to explore iterators with the following warm-up exercise.
It is not required that you submit anything for this part of the lab, but we feel that it is well worth the effort to complete this warm-up task.

<hr class="style12" />

### Warm-up Task
Write an iterator that iterates over the characters of a `String`.
Repeated calls to the iterator's `next()` method will return the first character of the string, then the second character, and so on.

More specifically, you should design and implement a class called `CharacterIterator` that has the following constructor and method signatures (you may provide additional helper methods and instance variables as you see fit):

```
public class CharacterIterator extends AbstractIterator<Character> {
	public CharacterIterator(String str) { ... }
	public Character next() { ... }
	public boolean hasNext() { ... }
	public void reset() { ... }
	public Character get() { ... }
}
```

The `Character` class is a wrapper class for primitive `char` values so that we can use `char` values with the generic `AbstractIterator` class.
You use `Character` much like one uses `Integer` for `int` values.
The Java compiler will _automatically_ convert `char` values to `Character` objects as necessary via a technique called "autoboxing."

(Don't forget to import `structure5.*`.)

You may use the following `main` method to test your code.

```
public static void main(String[] args) {
	CharacterIterator ci = new CharacterIterator("Hello world!");
	for (char c : ci) {
		System.out.println(c);
	}
}
```

When running this program, the output should be the characters of the string `"Hello world!"`, printed one letter per line:
```
> javac CharacterIterator.java
> java CharacterIterator
H
e
l
l
o

w
o
r
l
d
!
```

Note that the syntax in the `for` loop uses the "for thing in things" notation that may be familiar to python programmers.
This notation can be used with any iterable structure:
since the `CharacterIterator` extends the `AbstractIterator` class, and the `AbstractIterator` class implements [`Iterable`](https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html), `CharacterIterator` implements `Iterable`.

To get you started, here is a [template](https://williams-cs.s3.amazonaws.com/two-towers/code/CharacterIterator.java.txt) for the `CharacterIterator` class.

Once you've given it a try, here's a [solution](https://williams-cs.s3.amazonaws.com/two-towers/code/CharacterIteratorSolution.java.txt).

<hr style="border-color: purple;" />

## Lab Assignment

<b>Goal.</b>
To solve a computationally complex problem using iterators.

(Note: This lab is a variation of the one given in Chapter 8, modified to use generic classes.)

<b>Discussion.</b>
Suppose that we are given $`n`$ uniquely-sized cubic blocks and that each block's face has an __area__ between $`1`$ and $`n`$.
If we build two towers by stacking these blocks, how close can we make their heights?
The following two towers, built by stacking 15 blocks, differ in height by only 129 millionths of an inch (each unit is one-tenth of an inch):

<div class="center">
	<img src="https://williams-cs.s3.amazonaws.com/two-towers/images/blockstack.png" />
</div>

Still, this stacking is only the _second-best_ solution!
To find the best stacking, we could consider all the possible configurations.
We do know one thing:
the total height of the two towers is computed by summing the heights of all the blocks.
Since each block's height is the square root the area of one of it's faces, the height $`h`$ is:

$` h = \sum_{i=1}^n \sqrt{i} = \sqrt{1} + \sqrt{2} + ... + \sqrt{n} `$

To find the *best* stacking, we can exhaustively search through every possible combination of the $`n`$ blocks.

We can think of a "configuration" as representing the set of blocks that make up, say, the left tower.
Think about it:
given the full set of blocks and the left tower, we can figure out which blocks comprise the right tower...
Our task, then, is to keep track of the configuration that comes closest to $`\frac{h}{2}`$ without exceeding it (where $`h`$ is the total height of all $`n`$ blocks).

In this lab, we will represent a set of $`n`$ distinct objects with a `Vector<Double>`, and we will construct an `Iterator` that returns each of the $`2^n`$ subsets (configurations of blocks in the left tower).

<hr class="style12" />

#### Understanding the Algorithm

The trick to understanding how to generate a subset of $`n`$ values from a `Vector` is to first consider how to generate a subset of indices of elements from $`0`$ to $`n-1`$.
Once this simpler problem is solved, we can use the indices to help us build a `Vector` (or subset) of values identified by the indices.

There are exactly $`2^n`$ subsets of the values from $`0`$ to $`n−1`$.
To see this, imagine that you have a strip of paper with $`n`$ blanks on it, labeled  $`0`$ to $`n−1`$.
Let $`m`$ be a number $`0 \leq m < n`$.
Choose an arbitrary subset of blocks.
If block $`m`$ is included in a subset, you write a `1` in blank $`m - 1`$ on the paper, otherwise you put a `0` in blank $`m - 1`$.
Since there are $`2 \times 2 \times \ldots \times 2 = 2^n`$ different ways to fill in this strip of paper, there are $`2^n`$ different subsets.

For example, suppose we start with a blank strip of paper.

<div class="center">
    <img src="https://williams-cs.s3.amazonaws.com/two-towers/images/paperstrip.png" />
</div>

Also suppose that we have eight blocks, labeled $`1 \ldots 8`$, and we arbitrarily choose blocks $`1, 4, 5, 6, `$ and $`7`$.

<div class="center">
    <img src="https://williams-cs.s3.amazonaws.com/two-towers/images/smallstack.png" />
</div>

So we fill in the appropriate location in the paper for each chosen block (i.e., block $`m`$ gets a `1` in $`m - 1`$).

<div class="center">
    <img src="https://williams-cs.s3.amazonaws.com/two-towers/images/paperstrip-filled.png" />
</div>

Conveniently, we can think of this strip of paper as determining the digits of an $`n`$-bit binary number.
For example, the binary number `01111001` is the decimal number `121`.

In fact, each number in the range $`0`$ through $`2^n−1`$, when written in binary, uniquely represents a different subset.
Given this observation, we can imagine one solution strategy for exhaustively searching through all possible subsets: just count from $`0`$ to $`2^n−1`$.
For each number, use the values of the binary digits (a binary digit is called a _bit_) to select which blocks are to be included in a subset:
 * If the digit at position $`i`$ in the number is $`1`$, include the block at position $`i`$ in our subset.
 * If the digit at position $`i`$ in the number is $`0`$, omit the block at position $`i`$ from our subset.


<hr class="style12" />

#### Working With Binary Numbers in Java

Computer scientists work with binary numbers frequently, so there are a number of useful things to remember:

* A Java `int` type is represented by 32 bits.
  A Java `long` is represented by 64 bits.
  For maximum flexibility, it is useful to use `long` integers so we can represent sets of up to 64 elements.
* The arithmetic left shift operator `<<` can be used to quickly compute powers of $`2`$.
  The value $`2^i`$ can be computed by shifting the binary value `1` to the left by $`i`$ places.
  In Java we write this as `1L << i`.
  Note that this trick only works for non-negative, integral powers of $`2`$.
  The constant `1L` is the literal value representing the natural number "one" stored as a 64-bit `long` value.
  Using this constant ensures that Java knows we intend to use a 64-bit `long` value instead of a 32-bit `int` value.
  *The `L` is important to ensure that the result has type `long`!*
* The "bitwise and" of two numbers can be used to determine the value of a single bit in a number's binary representation.
  To retrieve bit $`i`$ of a `long` integer `m`, we need only compute `(m & (1L << i)) == (1L << i)`.
  If the expression is `true`, then the bit is `1`, otherwise it is `0`.

<hr class="style12" />

#### Procedure

Armed with this information, the process of generating subsets is fairly straightforward. One approach is the following:

1. Construct a new class that extends the `AbstractIterator` class.
   This new class should have a constructor that takes a `Vector<E>` as its sole argument.
   Subsets of this `Vector` will be returned as the `Iterator` progresses.
   Name this extended class `SubsetIterator`, and be sure to import `structure5.*` at the top of your file.
   Your `SubsetIterator` should be completely generic.
   It should know nothing about the values it is iterating over!
   When we use your `SubsetIterator` class to solve the particular problem posed in this lab, we will instantiate it to iterate over values of a particular type (`Double`).
   But by making `SubsetIterator` a generic class, we could reuse it to solve other problems that requires us to iterate over subsets of different types of objects.
   Thus, the class declaration will resemble this:
   ```
   public class SubsetIterator<E> extends AbstractIterator<Vector<E>> { ... }
   ```
1. Internally, a `long` value should be used to represent the current subset:
   we will use the representation of this `long` value's binary digits to decide which elements are included in the set, and which elements are omitted from the set.
   This `long` value should begin at $`0`$ (all bits are 0, representing the empty set) and increase all the way up to $`2^n−1`$ ($`n`$ bits are 1, so all $`n`$ elements are included in the set) as the `Iterator` progresses.
1. Write a `reset` method that resets the subset counter to $`0`$.
1. Write a `hasNext` method that returns `true` if the current `long` value is a reasonable representation of a subset (i.e., it does not specify that you should include elements in the subset that aren't part of the actual set).
1. Write a `get` method that returns a new `Vector<E>` of values that are part of the current subset.
   If bit $`i`$ of the `long` counter's current value is $`1`$, element $`i`$ of the original `Vector` is included in the `Vector` representing the resulting subset.
1. Write a `next` method.
   Remember that `next` returns the current subset that represented by the state of the counter _before_ incrementing the counter.
1. For an `Iterator`, you would normally have to write a `remove` method.
   If you extend the `AbstractIterator` class, this method is provided and will do nothing (this is reasonable for the subset iterator; you do not need to do anything for `remove`).

Once you have completed your `SubsetIterator` implementation <b>test it!</b>
You can test your `SubsetIterator` by asking it to print all the subsets of a `Vector` of values.
For example, write a `main` method for your `SubsetIterator` class that creates a `Vector<Integer>` with the first $`8`$ Integers ($`0`$ through $`7`$), creates a `SubsetIterator<Integer>` with this `Vector<Integer>`, and then prints out all subsets returned.
Make sure you end up with all $`256`$ different subsets printed.

<b>Two Towers.</b>
To solve the two towers problem, write a `main` method in a new class called `TwoTowers` that takes an argument `n` from the command line.
For example,

```
$ java TwoTowers 15
```

should compute the solution of the two towers problem for blocks labeled 1 through 15.

It is easier to proceed by populating your `Vector` with height values instead of area values.
In other words, insert $`\sqrt{1}`$, $`\sqrt{2}`$, ..., $`\sqrt{n}`$ into a `Vector<Double>` object.
To compute the square root of $`n`$, you can use the `Math.sqrt(n)` method.
A `SubsetIterator` is then used to construct $`2^n`$ subsets of these values.
The values of each subset are summed, and the sum that comes closest to, but does not exceed, the value $`\dfrac{h}{2}`$ is remembered.
After all the subsets have been considered, print the best solution.
Each block's height is a square root, so you should print out the area instead.

In addition to printing the best solution, your program should also print the _second best_ solution (it may be that this has the same value as the best solution).
We are adding this requirement for two reasons:
 * It provides an interesting twist that requires some clever problem-solving (some of the most "obvious" ways of doing this are not correct!)
 * When you are done, you can now check your solution!
   Recall, we have provided the second-best stacking for the 15-block problem above.

The following is a sample run of the tool:

```
$ java TwoTowers 14
Half height (h/2) is: 18.298106626967595
The best subset is: [1, 4, 5, 8, 10, 12, 13] = 18.2964256530161
The second best subset is: [5, 8, 9, 10, 12, 13] = 18.2964256530161
```

<hr style="border-color: purple;" />

## Lab Deliverables

For this lab, please submit the following:

```
lab07-twotowers/
    README.md
    SubsetIterator.java
    TwoTowers.java
    checkstyle
```

where `TwoTowers.java` and `SubsetIterator.java` should contain your well-documented source code.

Recall in previous labs that you had a Java file that contained a convenient `main` method pre-populated with a variety of helpful tests.
It is always a good practice to create a small set of tests to facilitate development, and you are encouraged to do so here.