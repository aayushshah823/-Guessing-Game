import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Query tree class
 * @author Aayush shah
 */
public class QueryTree 
{
  
  
  // private root node
  private QueryTreeNode root;
  
  // to track the current node
  private QueryTreeNode current;
  
  /**
   * creating the first node
   */  
  public QueryTree()
  {
    // Setting all the values accordingly
    root = new QueryTreeNode();
    root.data = "computer";
    root.left = null;
    root.right = null;
    current = root;
  }
  
  /**
   *  to go to the top root again, and begin from start
   */
  public void goToStart()
  {
    current = root;
  }
  
  /**
   * checks whether there is next question
   * @return true if found, false otherwise
   */
  public boolean hasNextQuestion() 
  {
    
    // if either ways tree can go, then it is not the final guess
    if( current.left == null || current.right == null)
      return false;
    
    return true;
  }
  
  /**
   * Next question stored in qt
   * @return the questions stored in qt
   */
  public String nextQuestion() 
  {
    
    if(checkIfLeafNode()){
      throw new IllegalStateException();
    }
    
    return current.data;
    
  }
  
  /**
   * 
   * @return whether or not a qt is a final guess
   */
  public boolean checkIfLeafNode(){
    return current.right == null && current.left == null;
  }
  
  
  /**
   * method to move to the next tree node, according to the user input
   * @param input the user input which is either y or n
   */
  public void userResponse(char input)
  { 
    // if user response is yes
    if( input == 'y' || input =='Y')
      current = current.right;
    
    // if user response is no
    else if( input == 'n' || input =='N')
      current = current.left;
    
    /*print message if neither 'y' or 'n' is entered*/
    else{
      throw new IllegalArgumentException("Invalid user input");
    }
  }
  
  
  /**
   * Final guess made
   * @return the final gues of query tree
   */
  public String finalGuess()
  {
    //can't return a question as a final guess
    if(!checkIfLeafNode()){
      throw new IllegalStateException();
    }
    return current.data;
  }
  
  /**
   * Updates the tree based on user input
   * @param q The quetion
   * @param item new item that is being added to the QueryTree
   * @param input yes or no answer that leads from this question to the new item
   */
  public void updateTree(String q, String item,char input)
  {
    // update should take place at leaf
    // what if this node is not the leaf?
    if( current.left != null || current.right != null )
    {
      // There is an error that we are not at the leaf node
    }
    
    // current data changed by query
    String oldData = current.data;
    current.data = q;
    
    /// item is given to right or left child accorind to yes or no
    if( input == 'y' || input =='Y')
    {
      current.right = new QueryTreeNode();
      current.right.data = item;
      current.left = new QueryTreeNode();
      current.left.data = oldData;
    }
    
    
    if( input == 'n' || input =='N')
    {
      current.left = new QueryTreeNode();
      current.left.data = item;
      current.right = new QueryTreeNode();
      current.right.data = oldData;
    }
    
    // Post condition
    goToStart();
  }
  
  /**
   * Reads the data from file
   * @param f File name
   * @throws FileNotFoundException Throws the exception if file isn't found
   */
  public void readIn(File f) throws FileNotFoundException
  {
    /*read from the file*/
    if(f == null )
    {
      throw new FileNotFoundException("File Not Found");
    }
    
    Scanner read = new Scanner(f);
    
    if( root.data.equals("computer") && root.left == null && root.right == null)
    {
      // calling private method
      root = readIn(read);
    }
    read.close();
  }
  
  /**
   * private method for read in
   * @param read reads from file
   * @return new query tree with new info
   */
  private QueryTreeNode readIn( Scanner read){
    
    String line = read.nextLine();
    if( line.charAt(0) == 'Q')
    {
      // Creating new query tree based on character
      String query = read.nextLine();
      // recurse call
      return new QueryTreeNode(query,readIn(read),readIn(read));
    }
    else
    {
      String answer = read.nextLine();
      return new QueryTreeNode(answer,null,null);
    }
  }
  
  /**
   * Write the current set of questions/items in this QueryTree out to the given file
   * @param f name of file
   * @throws FileNotFoundException Throws the exception if file isn't found
   */
  public void writeOut(File f) throws FileNotFoundException
  {
    /*write to the file*/
    if(f == null )
    {
      throw new FileNotFoundException("File Not Found");
    }
    
    
    //create print writer item out of the file 
    PrintWriter pw = new PrintWriter(f);
    QueryTreeNode node = root;
    writeOut(pw,node);
    pw.close();
  }
  
  /**
   * private method for traverse the tree in pre order
   * @param pw print writer
   * @param node query tree node
   */
  private void writeOut(PrintWriter pw,QueryTreeNode node)
  {
    if( node == null )
    {
      
      return;
    }
    
    // print the node data in the format specified
    if( node.left == null && node.right == null)
    {
      pw.println("A:");
      pw.println(node.data); 
      
    }
    else
    {
      pw.println("Q:");
      pw.println(node.data);
      
    }
    
    // go left and then go right
    writeOut(pw,node.left);
    writeOut(pw,node.right);
    
  }
}



/**
 * Inner class to store the node of the tree
 *
 */
class QueryTreeNode
{
  // tree has a string data
  String data;
  // left and right pointer to its children
  QueryTreeNode left; 
  QueryTreeNode right; 
  
  /**
   * constructor
   */
  public QueryTreeNode(){}
  /**
   * Constructor with all info
   * @param data The question stored
   * @param left Left sub stree of root
   * @param right right sub tree of root
   */
  public QueryTreeNode(String data, QueryTreeNode left, QueryTreeNode right){
    this.data = data;
    this.left = left;
    this.right = right;
  }
}