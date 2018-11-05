import java.util.Scanner;
/**

Project Description Link = https://docs.google.com/document/d/1rW8ipNNEQ7mglGzWcQ8AdL6qvNjBP51uFDXvGwUFldU

 */

class Main {
  //The main routine here. Add other classes as needed
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while(true){
      System.out.println("\n\nType c or C to compress a file, d or D to decompress a file, or exit to exit:");
      String mode = scanner.nextLine().toLowerCase();
      
      checkValidInput(mode);
      
      System.out.println("Enter the file name with extension(e.g. test.txt):");
      String fileName = scanner.nextLine();

      if(mode.equals("c")){
        FileParser compress = new FileParser(fileName,"c");
      }else{
        FileParser decompress = new FileParser(fileName,"d");
      }

      System.out.println("Files will be updated when you exit.");
    }

    // hardcode mode:
    // FileParser compress = new FileParser(fileName,"c");
    // FileParser decompress = new FileParser(fileName,"d");
  }


  //helper method to check input is valid
  public static void checkValidInput(String mode){
    if(mode.equals("exit")){
      System.exit(0);
    }
    while(!(mode.equals("c") || mode.equals("d") || mode.equals("exit"))){
      System.out.println("Invalid input. Type c or C to compress a file, d or D to decompress a file, or exit to exit:");
      Scanner scanner = new Scanner(System.in);
      mode = scanner.nextLine().toLowerCase();
      if(mode.equals("exit")){
        System.exit(0); 
      }
    }
  }
}