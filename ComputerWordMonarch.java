/**
 * Generates, keeps track, and gives hints for a hidden word
 */
public class ComputerWordMonarch implements WordMonarch {

    /** The hidden valid Jotto word for a game of Jotto */
    private String hiddenWord;


    /**
     * Default Constructor that sets hiddenWord to BREAD
     */
    public ComputerWordMonarch(){
        hiddenWord = "BREAD";
    }

    /**
     * Constructor that sets hiddenWord to the parameter
     * @param hiddenWord - word to use
     */
    public ComputerWordMonarch(String hiddenWord){
        this.hiddenWord=hiddenWord;
    }

    /**
     * Constructor that generates a random hiddenWord from dictionary
     * @param dictionary - All the possible words for hiddenWord
     */
    public ComputerWordMonarch(Dictionary dictionary){
        this.hiddenWord=dictionary.getRandomWord();

    }

    /**
     * Returns a hint given a guess
     * PRECONDITION: guess ia a valid Jotto guess
     * @param validGuess - A valid Jotto guess from the guesser
     * @return - The number of letters in common between validGuess and hiddenWord
     */
    public int giveHint(String validGuess){
        int numOfCommonLetters = 0;
        for(int i=0;i<validGuess.length();i++)
            if(hiddenWord.contains(validGuess.substring(i,i+1))) numOfCommonLetters++;
        return numOfCommonLetters;
    }
}
