import java.util.Random;
import java.util.Scanner;
import structure5.*;
/* Uses the TwoPlayerGame interface to implement the
 * "Silver Dollar Game". You may add as many additional
 * methods as you need.
 */
public class CoinStrip implements TwoPlayerGame {
  // Creates all the private variables used for the rest of the class implementation
  private int currentPlayer;
  private int totalCoins;
  private int totalPositions;
  private Vector<Boolean> gameBoard;

  /**
   * Initializes a new CoinStrip board game. Sets up the player number,
   * and the random Coins and Positions in a random game board.
   */
  public CoinStrip() {
    // Sets the current player to Player 1 (as the first player).
    // Sets local variable randomIndex used for creating random index for coins
    currentPlayer = 1;
    int randomIndex = 0;

    // Creates Random object to create random number of positions on the board (9-14)
    // and random number of coins (3-6) used for the game
    // Creates Boolean Vector gameboard.
    Random r = new Random();
    totalPositions = r.nextInt(6) + 9;
    totalCoins = r.nextInt(4) + 3;
    gameBoard = new Vector<Boolean>();
    
    // Sets all the positions on the gameBoard to default (false) in which no coin is there.
    for (int i = 0; i < totalPositions; i++) {
      gameBoard.add(false);
    }

    // Randomizes for the total amount of Coins needed various positions to change
    // the vector indexes to true to represent coins on the gameboard.
    for (int j = 0; j < totalCoins; j++) {
      randomIndex = r.nextInt(totalPositions);
      while (gameBoard.get(randomIndex)) {
        randomIndex = r.nextInt(totalPositions);
      }
      gameBoard.set(randomIndex, true);
    }

    // Prevents game from winning by the start by checking if it's over.
    // If it's over it changes one random coin to another position that's not within the first positions.
    if (isGameOver()) {
      gameBoard.set(r.nextInt(totalCoins), false);
      gameBoard.set(r.nextInt(totalPositions - totalCoins) + totalCoins, true);
    }
  }

  /**
    * Returns the current value for a given resource.
    *
    * @param resource Describes the game element.
    * @returns The current value of the resource.
    */
  public int getResource(int resource) {
    // Returns 1 if there is a coin found at the index corresponding to the resource int (gameBoard position)
    // and 0 if no coin found
    if (gameBoard.get(resource)) {
      return 1;
    }
    return 0;
  }
  
  /**
    * Sets the game state. Should not check whether the given
    * parameters are valid. isValidMove should be called before
    * calling this method to ensure that a move is legal.
    *
    * @param resource Which resource to alter (e.g., position, gamepiece, pile of matches, etc.)
    * @param updatedValue The updated value of the resource (e.g., how many matches remain, the updated position of a piece, etc.)
    */
  public void setResource(int resource, int updatedValue) {
    // Checks if updatedValue is 1 to set the index corresponding to the resource int (gameBoard position) to true which
    // means a coin is now in that position. If the updatedValue is 0 or any other number besides 1, then the value
    // at the index corresponding to the resource int is false which means no coins found.
    if (updatedValue == 1) {
      gameBoard.set(resource, true);
    } else {
      gameBoard.set(resource, false);
    }
  }
  
  /**
   *   Returns the number of positions on the gameBoard
   */
  public int getTotalPositions() {
    // Returns the number of positions on the gameBoard
    return totalPositions;
  }

  /**
   * Returns the number representing the current player.
   * @returns The current player.
   */
  public int getPlayer() {
    // Returns the current Player number (1 or 2) depending on whose turn it is
    return (currentPlayer + 1) % 2 + 1;
  }
  
  /**
    * Changes the current player to the given player.
    * @param player A player number.
    */
  public void setPlayer(int player) {
    // Sets the current Player to the updated player in order to switch from player to player within turns
    currentPlayer = player;
  }
  
  /**
   * Returns true if the specification of a move describes a leagal move
   * given all the rules of the game. Note: this does not check whether the
   * move is *good* move, only whether it is legal.
   *
   * @param resource Which resource to alter (e.g., position, gamepiece, pile of matches, etc.)
   * @param updatedValue The updated value of the resource (e.g., how many matches remain, the updated position of a piece, etc.)
   * @return True iff the move is valid.
   */
  public boolean isValidMove(int resource, int updatedValue) {
    // Creates targetPosition local var used to represent the index of the position that the user wants to move the coin to
    int targetPosition = resource - updatedValue;

    // Checks if the position the user wants to move the coin is somewhere off the gameBoard or is 0 or is to the right
    // in which it returns not Valid move.
    if (targetPosition >= resource || targetPosition < 0) {
      return false;
    }

    // If the move is valid still, checks if the user is trying to move the piece on top of another coin or jumping over
    // another coin in which it returns not Valid move.
    for (int i = targetPosition; i < resource; i++) {
      if (gameBoard.get(i)) {
        return false;
      }
    }

    // Returns true if leagal
    return true;
    
  }
  
  /**
   * Returns true if the game is over.
   * @returns True if the game is over, false otherwise.
   */
  public boolean isGameOver() {
    // Checks if the game is done by checking if the total amount of Coins are each in the consecutive positions
    // from 1 to n (where n represents the totalCoins number)
    boolean winBool = true;
    for (int i = 0; i < totalCoins; i++) {
      winBool = winBool && gameBoard.get(i);
    }
    return winBool;
  }
  
  /**
   * Displays the board on screen.
   */
  public void displayBoard() {
    // Loops through the gameBoard array and prints out the game board where it is separated with "|" and
    // a coin is represented by "O" so a coin in the first and second position
    // be | O | O |  |  | in a gameboard with 4 positions.
    System.out.print("|");
    for (int i = 0; i < gameBoard.size(); i++) {
      if (gameBoard.get(i)) {
        System.out.print(" O |");
      } else {
        System.out.print("   |");
      }
    }
    System.out.println();

    // This part of the program creates a second row to list the numbers that correspond to the gameboard
    // that makes it really easy for the user to select a piece without having to count the gameBoard
    // themselves.
    System.out.print("  ");
    for (int j = 0; j < gameBoard.size(); j++) {
      if (j < 9) {
        System.out.print((j + 1) + "   ");
      } else {
        System.out.print((j + 1) + "  ");
      }
    }
    System.out.println();
  }
  
  public static void main(String[] args) {
    // Creates the game object of the type CoinStrip object.
    CoinStrip game = new CoinStrip();
    // Creates the sc object of the Scanner class for user input
    Scanner sc = new Scanner(System.in);
    
    // Runs the game while the game isn't over
    while (!game.isGameOver()) {
      // creates local variables used to store the user input in selecting the coins and how much to move
      // the coins for their turns
      int userPosition;
      int userMove;

      // Sets up the game by printing which player's turn and displaying the gameboard
      System.out.println("Player: " + game.getPlayer());
      game.displayBoard();

      // This part of the code asks the user for input and gets only a integer (returns error message if a string given instead)
      System.out.print("Which piece do you want to move, and how many spaces to the left [<coin> <numberOfSpaces>]? ");
      // did the user enter an integer?
      if (sc.hasNextInt()) {
      // return the number
        userPosition = sc.nextInt();
      } else {
        System.out.println("Not an integer! Try again!");
        // discards the string if user typed a string
        sc.nextLine();
        continue;
      }

      // This checks if the user selected a coin that is valid and if the coin exists in that index and if not, it resets
      // the while loop to ask for a valid move
      if (userPosition <= game.getTotalPositions() && userPosition > 0) {
        // If the coin exists, the program proceeds to get the input from the user to how much they want to move the coin
        if (game.getResource(userPosition - 1) == 1) {
          // did the user enter an integer?
          if (sc.hasNextInt()) {
          // return the number
            userMove = sc.nextInt();
          } else {
            System.out.println("Not an integer! Try again!");
            // discards the string if user typed a string
            sc.nextLine();
            continue;
          }

          // Changes the gameBoard if the the user's move is valid, by changing the current position of the coin
          // to false and changing the desired position that the user wants to move the coin to to true
          if (game.isValidMove(userPosition - 1, userMove)) {
            game.setResource(userPosition - 1, 0);
            game.setResource(userPosition - 1 - userMove, 1);
          } else {
            System.out.println("Not a Valid move! Try again!");
            continue;
          }
        } else {
          System.out.println("Not a Valid piece to move!");
          sc.nextLine();
          continue;
        }
      } else {
        System.out.println("Not a Valid piece to move!");
        sc.nextLine();
        continue;
      }
      game.setPlayer(game.getPlayer() + 1);
      System.out.println();
    }

    // Displays Winning Board and Prints winner of the Game
    game.setPlayer(game.getPlayer() + 1);
    game.displayBoard();
    System.out.println("Player " + game.getPlayer() + " wins!");
  }
}
