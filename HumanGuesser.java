import java.util.List;
import java.util.Scanner;

/**
 * type of guesser that takes in human inputs for the guess
 */
public class HumanGuesser implements Guesser{

    /** source for user inputted guesses */
    Scanner sc;

    /**
     * constructor for this guesser that uses human inputs for guesses
     * @param scanner - source for user inputted guesses
     */
    public HumanGuesser(Scanner scanner){
        sc=scanner;
    }


    /**
     * Returns a valid guess for a Jotto game inputted by the user
     * Ensures that the user inputs a valid word given the previous turns of guesses and hints
     * @param previousTurns - All of the previous turns of this Jotto game
     * @return - A valid Jotto guess under the conditions
     */
    public String guessValidGuess(List<Turn> previousTurns){
        System.out.println("guess: ");
        boolean guessIsValid = false;
        String guess = "";
        while(!guessIsValid){
            guess = sc.nextLine();
            if(guess.length()==5) guessIsValid = true;
            //duplicate letters check
            for(Turn t : previousTurns){
                if(Jotto.numOfSharedLetters(t.getGuess(),guess)!=t.getHint()) guessIsValid = false;
            }
            if(!guessIsValid) System.out.println("Not a valid guess, try again: ");
        }
        return guess;
    }
}
