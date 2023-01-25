/**
 * Keeps track of a full turn in Jotto
 * Holds a guess and its corresponding hint
 */

public class Turn {

    /** Valid Jotto Word inputted by the guesser */
    private final String guess;
    /** Number of correct letters in guess*/
    private final int hint;

    /**
     * Constructor that creates a Turn object from a guess and a hint
     * @param guess - valid Jotto word from the guesser
     * @param hint - corresponding hint from WordMonarch
     */
    public Turn(String guess, int hint){
        this.guess=guess;
        this.hint=hint;
    }

    /**
     * Accessor method for a valid Jotto guess
     * @return - the Guess
     */
    public String getGuess(){ return guess; }

    /**
     * Accessor method for the corresponding hint from the WordMonarch
     * @return - the hint
     */
    public int getHint() { return hint; }



    /**
     * Accessor that returns a string of the Turn
     * @return - The valid Jotto guess + corresponding String from the WordMonarch
     */
    public String toString(){
        return guess+" "+hint;
    }
}
