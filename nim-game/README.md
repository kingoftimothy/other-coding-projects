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
Main Program (Nim.java)

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
