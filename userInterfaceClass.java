import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The user interface clss
 * @author aayush
 *
 */
public class userInterfaceClass{
  
  /**
   * Application method
   * @param args The command line arguements
   * @throws FileNotFoundException Checks if file is okay
   */
  public static void main(String[] args) throws FileNotFoundException
  {
    
    // Creting new QT and printing default statements
    QueryTree qt = new QueryTree();
    String fileName = "QueryTree.txt";
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to the csc143 question program.");
    System.out.println();
    System.out.print("Do you want to read in the previous tree? (y/n)?");
    
    
    String answer = scanner.nextLine().trim();
    
    // Checking the answer entered by user
    if( answer.equals("y"))
    {
      System.out.print("Enter name of file");
      answer = scanner.nextLine();
      // reads file and load it on tree
      qt.readIn(new File(answer));
    }
    
    
    boolean playingGame = true;
    
    
    do
    {
      // Going through do while loop to ask questions accordingly && to play games
      qt.goToStart();
      playingGame = playing(qt);
    }
    // while playing game, writes tree in file
    while(playingGame);
    qt.writeOut(new File(fileName));
    
  }
  
  /**
   * private method for playing games
   * @param qt the query tree
   * @return true if guessed correctly, false otherwise
   */
  private static boolean playing(QueryTree qt)
  {
    Scanner scanner = new Scanner(System.in);
    String answer;
    System.out.println("Please think of an object for me to guess.");
    
    while(qt.hasNextQuestion()){
      System.out.println(qt.nextQuestion() + "?");
      answer = scanner.nextLine();
      if( answer.equalsIgnoreCase("Y"))
        qt.userResponse('y');
      else
        qt.userResponse('n');
    }
    
    System.out.print("Would your object happen to be " + qt.finalGuess() + "?");
    answer = scanner.nextLine();
    
    if(answer.equalsIgnoreCase("Y"))
    {
      System.out.println("Great, I got it right!");
    }
    else
    {
      // Printing the questions as asked in assignment
      System.out.print("What is the name of your object?");
      String item = scanner.nextLine();
      System.out.print("Please give me a yes/no question that distinguishes between your object and mine-->");
      String question = scanner.nextLine();  
      System.out.print("And what is the answer for your object? ");
      
      answer = scanner.nextLine();
      
      // Calling update tree method for updating in file
      qt.updateTree(question, item, answer.charAt(0));
    }
    System.out.println();
    System.out.print("Do you want to go again? (y/n)?");
    String next = scanner.next();
    
    // checking user input and returning ans accordingly
    if( next.charAt(0) == 'N' || next.charAt(0) == 'n')
      return false;
    return true;
  }
}