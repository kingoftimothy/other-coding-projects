<!--
---
layout: page
title: 'Lab 10: Exam Scheduling'
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

# Lab 10: Exam Scheduling

This week, you will write a program to schedule final exams for the registrar so that no student has two exams at the same time.
The goals of this lab are to:
* Gain experience using graph construction and traversal operations.
* Develop an algorithm requiring several coordinated data structures.

You will also learn about a special class of algorithm called a _greedy algorithm_.
Greedy algorithms search for a "best" or _optimal_ solution according to some criteria.
An optimal solution is fundamentally a "global" property: it's the one solution that's better than all the others.
What is surprising about greedy algorithms is that they can sometimes find optimal solutions _without_ needing to consider any global information.
Instead, they make lots of little "greedy" or "local" decisions.
Even when greedy algorithms do not produce an optimal solution, as in this problem, the solution may still sometimes be near optimal.

Your algorithm should determine an assignment of classes to exam slots such that:
1. None of the student's scheduled exams will ever be scheduled for the same exam slot.
2. No scheduled slot can be combined with any other slot, otherwise it would violate rule 1.

The second requirement ensures that we don't do something silly like schedule every exam to its own exam slot.
If we did that, students would spend a lot of time waiting around to take their exams!
Therefore, your algorithm should aggressively schedule exams concurrently, as long as doing so does not create a conflict.

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

## PRE-LAB: Step 1

Before lab, read through this entire handout carefully.
You should construct a design document explaining the general operation of your algorithm and which classes you intend to create to represent various objects in the program.
Since you will be working with a partner, you should _each create your own design document_.
Bring your design document to lab on Wednesday.

<hr style="border-color: purple;" />

#### Input

 The input to your program will be a text file containing (fictitious) student class information. For example:
 
    Jim Bern
    CSCI 136
    MATH 251
    ENGL 201
    PHIL 101
    Mark Hopkins
    PSYC 212
    ENGL 201
    HIST 301
    CSCI 136
    Katie Keith
    SOC 201
    CSCI 136
    MATH 251
    PSYC 212

For each student, there are exactly five lines.
The first line contains a student's name, and the next four lines that student's courses.
In our fictional world, we have no over-achievers taking 5 or more courses.

* Jim Bern is taking CSCI 136, MATH 251, ENGL 201, and PHIL 101;
* Mark Hopkins is taking PSYC 212, ENGL 201, HIST 301, and CSCI 136; and
* Katie Keith is taking SOC 201, CSCI 136, MATH 251, and PSYC 212.

We provide small, medium, and large input files in your starter repository.

Whenever you process data in the "real world", your code should carefully handle inputs that may not be properly formatted.
For the purposes of this lab, however, you may assume that all files are properly formatted and that all students take exactly four courses. 

<hr class="style12" />

#### Output

The output of the program should be a list of time slots with the courses whose final will be given at that slot. For example <a href="#footnote1">[1]</a>:

    Slot 1: HIST 301 PHIL 101 SOC 201
    Slot 2: CSCI 136
    Slot 3: ENGL 201
    Slot 4: MATH 251
    Slot 5: PSYC 212

<hr class="style12" />

#### Algorithm

The key to doing this assignment is to build a graph as you read in the file of students and their schedules.
Each node of the graph is a course taken by at least one student in the college.
An edge is drawn betweem two nodes whenever a student takes both courses.
The label of an edge might be the number of students with both classes (although we don't really need the weights for this program).

Therefore, for the three student schedules shown above, the graph would be as given below (edges without a weight label have weight 1). 

<div class="center">
    <img src="https://williams-cs.s3.amazonaws.com/exam-scheduling/Exam-Scheduling-Example.svg" width="75%" />
</div>

A <a href="https://en.wikipedia.org/wiki/Greedy_algorithm">greedy algorithm</a> that finds an exam schedule satisfying our two constraints might work as follows.
Choose a course (e.g., PHIL 101) and assign it to the first time slot.
Search for a course to which it is not connected.
If you find one (e.g., HIST 301), add it to the time slot.
Now try to find another which is not connected to any of those already in the time slot.
If you find one (e.g., SOC 201), add it to the time slot.
Continue until all nodes in the graph are connected to at least one element in the time slot.
When this happens, no more courses can be added to the time slot (you should think about why).
The final set of elements in that time slot is said to be a _maximally independent set_ in the graph.
Once you have this set, you can remove them from the graph or you could mark them as visited and then ignore them in future passes.
Below, we'll assume that we've removed them from the graph.

If there are any remaining nodes in the graph (there should be at least the first time around!), pick one and make it part of a new time slot, then try adding other courses to this new slot as described above.
Continue adding time slots for remaining courses until the entire graph has been deleted.

Finally, print the exam schedule.
For the graph shown above, here is one solution:

    Slot 1: PHIL 101 HIST 301 SOC 201
    Slot 2: MATH 251
    Slot 3: CSCI 136
    Slot 4: ENGL 201
    Slot 5: PSYC 212

Notice that no pair of time slots can be combined without creating a time conflict with a student.
Unfortunately, this is not the minimal schedule as one can be formed with only four time slots.
See if you can find one!
A greedy algorithm of this sort will give you a schedule with $`m`$ slots, no two of which can be combined, but a different selection of courses in slots may result in fewer than $`m`$ slots.
Any schedule satisfying our constraints will be acceptable (although see below for extensions to compute the optimal solution). 

<hr class="style12" />

#### Building the Graph

We recommend that you use a list-based graph implementation rather than a matrix-based one.
Why does that choice make the most sense for this application?
Think about the relative strengths of list-based and matrix-based graph representations as you implement the lab.
Vertex labels should be the course names.

<!--
Here is one possible way to find a collection of maximal independent sets from the graph: represent each slot by some sort of a list (or, better yet, a binary search tree). To find a maximal independent set for a slot, pick any vertex of the graph and add it to the list. Cycle through all other vertices of the graph. If a vertex is not connected to any of the vertices already in the slot, add it to the list. Continue until you have tried all vertices. Now delete all vertices that you added to the slot from the graph <a href="#footnote2">[2]</a>. Fill successive slots in the same way until there are no vertices left in the graph. 
-->

<hr class="style12" />

#### Extensions

For full credit on this lab, please complete at least one interesting extension to the program, from those listed below.
This is also a great opportunity for earning some extra credit by adding any features you find interesting.
If you complete more than one extension, you will receive bonus credit.

* Print out a final exam schedule ordered by course name/number (i.e., AFR 100 would be first, and WGST 999 would be last, if such courses are offered this semester).  For each course, you should print all students taking that course.
* Print out a final exam schedule for each student, listing students in alphabetical order.  For each student, you should list which exam slots they should attend.
* Randomize! To handle large files, you could also repeatedly use the greedy algorithm on random orderings of the nodes. After running for a while, you may get lucky and find a schedule close to the optimal, even if you are not guaranteed to find the true optimal. Feel free to explore other approaches as well. As output, list the largest and smallest solutions found in a given run.
* Arrange the time slots in an order that tries to minimize the number of students who must take exams in three consecutive time slots. This is trickier than the other options!

Be sure to indicate in the heading of your program (in comments) what extras you have included.

<hr style="border-color: purple;" />

## How to Run Your Program

<span class="red">Please ensure that your program can be run as follows (this example assumes the input file is called `input.txt`):</span>

    $ java ExamScheduler < input.txt
	
<div>
	&nbsp;
</div>

<hr style="border-color: purple;" />

## Lab Deliverables

We provide several input files, `small.txt`, `medium.txt`, and `large.txt` to help you design and test your program.
We recommend that you initially develop your code using the `small.txt` file, and then move on to the larger files as you gain confidence in your code.

By the start of lab, you should see a new private repository called `lab10-exam-scheduling` in your GitLab account.

For this lab, please submit the following: 

    lab10-exam-scheduling/
        README.md
        ExamScheduler.java
        Student.java
        {any other classes you created}
        inputs/
            small.txt
            medium.txt
            large.txt

where `ExamScheduler.java`, `Student.java` and `{any other classes you created}` should contain your well-documented source code.

Recall in previous labs that you had a Java file that contained a convenient `main` method pre-populated with a variety of helpful tests.  It is always a good practice to create a small set of tests to facilitate development, and you are encouraged to do so here.

As in all labs, you will be graded on design, documentation, style, and correctness. Be sure to document your program with appropriate comments, a general description at the top of the file, and a description of each method with pre- and post-conditions where appropriate. Also, use comments and descriptive variable names to clarify sections of the code which may not be clear to someone trying to understand it.

Whenever you see yourself duplicating functionality, consider moving that code to a helper method. There are several opportunities in this lab to simplify your code by using helper methods.

<hr style="border-color: purple;" />

## Submitting Your Lab

As you complete portions of this lab, you should `commit` your changes and `push` them. <u>Commit early and often.</u>  

* <u>Be sure to push your changes to GitLab</u>.
* <u>Verify your changes on GitLab.</u> Navigate in your web browser to your private repository on GitLab. You should see all changes reflected in the files that you `push`. If not, go back and make sure you have both committed and pushed.

We will know that the files are yours because they are in _your_ `git` repository. <u>Do not include identifying information in the code that you submit.</u>

<hr style="border-color: purple;" />

## Footnotes

<a name="footnote1">[1]</a> Different implementations of the algorithm may lead to different results!

<a name="footnote2">[2]</a> Or just mark them as visited and ignore them in future passes. Can you think of a reason why this might be better or worse than deleting them? 
