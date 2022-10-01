import java.util.Random;
import java.util.Scanner;

public class Nim implements TwoPlayerGame {

    // Creates all the private variables used for the rest of the class implementation
    private int currentPlayer, numPiles, minMatches, maxMatches, totalMatches;
    private int[] gameBoard;

    /**
     * Initializes a new Nim board game.
     *
     * @param numPiles The number of matchstick piles.
     * @param minMatches The smallest number of matches per pile.
     * @param maxMatches The largest number of matches per pile.
     */
    public Nim(int numPiles, int minMatches, int maxMatches) {
        // Sets the values of the parameters in the constructor to the respective class variables.
        this.numPiles = numPiles;
        this.minMatches = minMatches;
        this.maxMatches = maxMatches;

        // Sets the current player to Player 1 (as the first player).
        currentPlayer = 1;

        // Creates a gameBoard integer array that sets the size of the array to the number of piles and sets each index to a
        // random integer value within the inclusive minMatches and maxMatches (inclusive).
        // Adds up each of the index values (which are the number of matches for each pile) to get the total Matches in the game.
        gameBoard = new int[numPiles];
        Random r = new Random();
        for(int i = 0; i < numPiles; i++)
        {
            gameBoard[i] = r.nextInt((maxMatches - minMatches) + 1) + minMatches;
            totalMatches += gameBoard[i]; 
        }
    }
  
    /**
     * Returns the current value for a given resource.
     *
     * @param resource Describes the game element.
     * @returns The current value of the resource.
     */  
    public int getResource(int resource) {
        // Returns the number of sticks in the resource (the pile number) from gameBoard
        return gameBoard[resource];
    }

    /**
     *   Returns the current number of piles in the game
     */
    public int getNumPiles() {
        // Returns the number of piles in the game
        return numPiles;
    }
    
    /**
     *   Returns the total number of matches in the game
     */
    public int getTotalMatches() {
        // Returns the number of matches in the game
        return totalMatches;
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
        // Finds the resource (the pile number in the gameBoard array) and replaces the current value of that index (the number
        // of sticks in that pile) to the updatedValue (the new number of sticks that should be in the pile after the Player has
        // removed some number of sticks).
        // Subtracts the amount of sticks the Player took from the total amount of Matches to update the amount of matches in the game
        totalMatches = totalMatches - (gameBoard[resource] - updatedValue);
        gameBoard[resource] = updatedValue;
    }
    
    /**
     * Returns the number representing the current player.
     * 
     * @returns The current player.
     */
    public int getPlayer() {
        // Returns the current Player number (1 or 2) depending on whose turn it is
        return (currentPlayer + 1) % 2 + 1;
    }
    
    /**
     * Changes the current player to the given player.
     * 
     * @param player A player number.
     */
    public void setPlayer(int player) {
        // Sets the current Player to the updated player in order to switch from player to player within turns
        currentPlayer = player;
    }
    
    /**
     * Returns true if the specification of a move describes a legal move
     * given all the rules of the game. Note: this does not check whether the
     * move is *good* move, only whether it is legal.
     *
     * @param resource Which resource to alter (e.g., position, gamepiece, pile of matches, etc.)
     * @param updatedValue The updated value of the resource (e.g., how many matches remain, the updated position of a piece, etc.)
     * @return True iff the move is valid.
     */
    public boolean isValidMove(int resource, int updatedValue) {
        // Determines if the number of sticks is greater than or equal to the amount of the sticks the player wants to take and 
        // greater than 0 sticks. This is to help make sure the Player does a legal move which is 1 or more sticks within the
        // range of the sticks available.
        return gameBoard[resource] >= updatedValue && updatedValue > 0;
    }
    
    /**
     * Returns true if the game is over.
     * @returns True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        // Determines that the Game is Over when the total matches left in the game is 1 or less than 1.
        return totalMatches <= 1;
    }
    
    /**
     * Displays the board on screen.
     */
    public void displayBoard() {
        // Loops through the gameBoard array and prints out [x] where x is the pile number from 1 to the total number of piles.
        // Following [x], the amount of sticks in the pile is printed out where each "|" represents one stick.
        for(int i = 0; i < gameBoard.length; i++)
        {
            System.out.print("[" + (i + 1) + "]");
            for(int j = 0; j < gameBoard[i]; j++)
            {
                System.out.print(" |");
            }
            System.out.print("\n");
        }
    }

    /**
     * The entry point to the program.  It currently ignores
     * the contents of the args array.
     * @param args
     */
    public static void main(String[] args) {
        // Creates the game object of the type Nim class with 5 number of Piles and the min and max Sticks being 1-8
        Nim game = new Nim(5, 1, 8);
        // Creates the sc object of the Scanner class for user input
        Scanner sc = new Scanner(System.in);
        // Creates the method specific variables for program use
        int userPile, userSticks;

        // A while loop to keep running the game if it isn't over.
        while(!game.isGameOver())
        {
            // Sets up the game by printing which player's turn and displaying the gameboard
            System.out.println("Player: " + game.getPlayer());
            game.displayBoard();
            
            // Displays the available piles that the Player can select from and requests an integer input from the user.
            // Protects the game from breaking by checking if the user selected a pile within the range of pile numbers.
            // If the user selected a pile out of the range, gives error signs and restarts to the top of the while loop.
            System.out.print("Which pile [1-" + game.getNumPiles() + "]? ");
        
            // did the user enter an integer?
            if (sc.hasNextInt()) {
            // return the number
                userPile = sc.nextInt();
            } else {
            System.out.println("Not an integer! Try again!");
        
            // discards the string if user typed a string
                sc.next();
                continue;
            }
            if (userPile > game.getNumPiles() || userPile < 1)
            {
                System.out.println("Sorry, this is not a valid pile! Choose a different pile!");
                continue;
            }
            
            // Displays the available matches that the Player can select from and requests an integer input from the user.
            // Protects the game from breaking by checking if the user selected an amount of matches within the range of
            // the total matches in that pile.
            // If the user selected a pile with 0 matcches, it gives error signs and restarts to the top of the while loop.
            // If the user gives an invalid move, it gives error sign and restarts to the top of the while loop.
            if (game.getResource(userPile - 1) > 0 )
            {
                System.out.print("How many matches [1-" + game.getResource(userPile - 1) + "]? ");
                if (sc.hasNextInt()) {
                // return the number
                    userSticks = sc.nextInt();
                } else {
                System.out.println("Not an integer! Try again!");
                // discards the string if user typed a string
                    sc.next();
                    continue;
                }
            } else {
                System.out.println("Sorry, this pile has 0 matches! Choose a different pile!");
                continue;
            }
            if (!game.isValidMove(userPile - 1, userSticks))
            {
                System.out.println("Sorry, this is not a valid move!");
                continue;
            }
            
            // Adjusts the game board by updating the amount of sticks in the pile after the player takes the sticks.
            game.setResource(userPile - 1, game.getResource(userPile - 1) - userSticks);
            
            // Checks if the game is Over, and it breaks out of the while loop. If the total matches are at 0, this 
            // means the current player was the last to take the sticks meaning they lose and the other player wins.
            // The other case of the game ending is if the total matches is at 1 meaning that the other player is 
            // forced to take the last stick and loses so the current player wins.  
            if(game.isGameOver())
            {
                if(game.getTotalMatches() == 0)
                {
                    game.setPlayer(game.getPlayer() + 1);
                }
                break;
            }
            game.setPlayer(game.getPlayer() + 1);
        }
        // Prints winner of the Game
        System.out.println("Player " + game.getPlayer() + " wins!");
    }
  }
  