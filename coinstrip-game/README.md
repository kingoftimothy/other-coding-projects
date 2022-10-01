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

Main Program (CoinStrip.java)

This is the Silver Dollar Game. As you think about the design of your game, you should first decide on a representation of the coin strip. 
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

The `main` method of your `CoinStrip` class should set up the board and then prompt each of two players to make their moves. A move will be of the form:

    <cn> <ns>
	
where `<cn>` specifies the coin to be moved and `<ns>` specifies the number of spaces that coin should be moved.
	
The program should prompt the user for another input if the move is illegal.
