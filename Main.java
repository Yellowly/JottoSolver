import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Dictionary hiddenWords = new Dictionary("HiddenJottoWords.txt");
    static Dictionary allWords = new Dictionary();

    /**
     * Tests out jotto solver and displays the average number of turns
     * @param iterations - number of times to test jotto solver
     */
    private static void tester(int iterations){
        double sum = 0;
        for(int i=0;i<iterations;i++) {
            Jotto instance = new Jotto(new BestEntropyGuesser(hiddenWords,true));
            try{
                sum+=instance.playGame(false);}
            catch (Exception e){
                e.printStackTrace();
                return;
            }
            if(i%10==0) System.out.println(i + " average: "+sum/i);
        }
        System.out.println(sum/iterations);
    }

    private static void help(){
        System.out.println("play hgbm - plays a game with a human guesser and bot monarch");
        System.out.println("play bghm - plays a game with a bot guesser and human monarch");
        System.out.println("test *number* - tests the bot and gives average num of turns after *number* games");
        System.out.println("stop - stops the program");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Jotto solver! \ntype \"help\" for a list of commands");
        boolean run = true;
        while(run){
            String input = sc.nextLine();
            String[] inputs = input.split(" ");
            if(input.equals("exit")||input.equals("leave")||input.equals("stop")||input.equals("end")) run = false;
            else if(input.equals("help")) help();
            else if(inputs.length>1){
                if(inputs[0].equals("test")) tester(Integer.valueOf(inputs[1]));
                else if(inputs[0].equals("play")&&inputs[1].equals("hgbm")){
                    Jotto game = new Jotto(new HumanGuesser(sc));
                    game.playGame(true);
                }
                else if(inputs[0].equals("play")&&inputs[1].equals("bghm")){
                    Jotto game = new Jotto(new HumanWordMonarch(sc));
                    game.playGame(true);
                }
            }
        }
        sc.close();
    }
}
