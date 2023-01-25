import java.util.LinkedList;
import java.util.List;

/**
 * Jotto is a game Jotto Two players, a Guesser and a WordMonarch The
 * WordMonarch chooses a hidden word with 5 letters and no repeating letters The
 * Guesser tries to guess this word by selecting valid Jotto Guesses Each guess
 * has to have 5 letters that are non-repeating and has to conform to all the
 * previous guesses and hints
 */
public class Jotto {
    /** chooses the hidden word and gives hints */
    public WordMonarch monarch;
    /** player guesses valid Jotto words */
    private Guesser player;
    /** keeps track of all turns played in a game */
    private List<Turn> gameboard;
    /** keeps track of all valid words in Jotto */
    private Dictionary dictionary;

    /** keeps track of all possible hidden words */
    private Dictionary hiddenWords;

    /**
     * Default constructor that uses BREAD for the hidden word and the default
     * dictionary and random guesser
     */
    public Jotto() {
        hiddenWords = new Dictionary("HiddenJottoWords.txt");
        monarch = new ComputerWordMonarch(hiddenWords);
        gameboard = new LinkedList<Turn>();
        dictionary = new Dictionary();
        player = new RandomGuesser(dictionary);
    }

    /**
     * Constructor for a game of jotto that uses a custom guesser
     * @param guesser - guesser to use
     */
    public Jotto(Guesser guesser){
        hiddenWords = new Dictionary("HiddenJottoWords.txt");
        monarch = new ComputerWordMonarch(hiddenWords);
        player = guesser;
        gameboard = new LinkedList<Turn>();
        dictionary = new Dictionary();
    }

    /**
     * Constructor for a game of jotto that uses a custom word monarch
     * @param monarch - type of WordMonarch to use
     */
    public Jotto(WordMonarch monarch){
        hiddenWords = new Dictionary("HiddenJottoWords.txt");
        dictionary = new Dictionary();
        this.monarch = monarch;
        player = new BestEntropyGuesser(dictionary,false);
        gameboard = new LinkedList<Turn>();
    }

    public Dictionary getDictionary() {
        return dictionary;
    }


    /**
     * Plays a full game of Jotto
     */
    public int playGame(boolean print) {
        while (!isGameOver(gameboard)) {
            playTurn(print);
        }
        if(print) {
            printBoard(gameboard);
            System.out.println("won in "+gameboard.size()+" turns");
        }
        return gameboard.size();
    }

    /**
     * Plays a single turn in a game of Jotto
     */
    public void playTurn(boolean print) {
        String guess = player.guessValidGuess(gameboard);
        if(print) System.out.println("guess: "+guess);
        int hint = monarch.giveHint(guess);
        if(print) System.out.println("hint: "+hint);
        Turn thisTurn = new Turn(guess, hint);
        gameboard.add(thisTurn);
        if(print) {
            //printBoard(gameboard); //redundant based on how user-inputted guesses and hints is set up
            System.out.println("*********");
        }
    }

    /**
     * Creates a new Turn array that has turn added to the end of board
     *
     * @param board - the current board state
     * @param turn  - The latest turn
     * @return - a new board with turn added to the old board
     */
    private Turn[] addTurn(Turn[] board, Turn turn) {
        Turn[] newBoard = new Turn[board.length + 1];
        for (int i = 0; i < board.length; i++) {
            newBoard[i] = board[i];
        }
        newBoard[newBoard.length - 1] = turn;
        return newBoard;
    }

    /**
     *
     * @param guess           - guess in question
     * @param previousGuesses - all the previous turns of the game
     * @return - returns true if it is valid, false otherwise
     */
    public static boolean isValidGuess(String guess, List<Turn> previousGuesses) {
        for (Turn t : previousGuesses) {
            if (numOfSharedLetters(guess, t.getGuess()) != t.getHint())
                return false;
        }
        return true;
    }

    /**
     * PRECONDITION : no duplicate letters in either string finds the number of
     * shared letters in two strings
     *
     * @param s1 - first string to check
     * @param s2 - second string to check
     * @return - number of shared letters in two strings
     */
    public static int numOfSharedLetters(String s1, String s2) {
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s2.contains("" + s1.charAt(i)))
                count++;
        }
        return count;
    }

    /**
     * Prints out the Jotto gameboard
     * board - A list of guesses with hints
     * @Param board - The current state of the Jotto Game
     */
    private void printBoard(List<Turn> board) {
        String res = "";
        for (Turn t : gameboard) {
            res += t.toString() + "\n";
        }
        System.out.println(res);
    }

    /**
     * Returns the Jotto gameboard as a string
     * @return - gameboard as String
     */
    public String toString() {
        String res = "";
        for (Turn t : gameboard) {
            res += t.toString() + "\n";
        }
        return res;
    }

    /**
     * Is the game over?
     *
     * @param board - The game in question
     * @return true if the last hint was a 5
     */
    private boolean isGameOver(List<Turn> board) {
        return board.size() > 0 && board.get(board.size() - 1).getHint() == 5;
    }
}
