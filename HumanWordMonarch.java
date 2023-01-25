import java.util.Scanner;

/**
 * Type of word monarch that gives hints based off user inputs
 */
public class HumanWordMonarch implements WordMonarch {
    /** source for user inputs */
    Scanner sc;

    /**
     * constructor for this wordmonarch that takes in human inputs for hints
     * @param scanner - source for human inputs
     */
    public HumanWordMonarch(Scanner scanner){
        this.sc=scanner;
    }

    /**
     * Fetches a hint from the user given a guess
     * PRECONDITION: guess ia a valid Jotto guess
     * @param validGuess - A valid Jotto guess from the guesser
     * @return - A number inputted by the user which represents the number of common letters between their hidden word and guess
     */
    public int giveHint(String validGuess){
        System.out.println("hint: ");
        String hint = sc.nextLine();
        return Integer.valueOf(hint);
    }

}
