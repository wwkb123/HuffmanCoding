import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

//*****This parser will ignore symbols that are not in the base64 table, e.g. comma, colon etc.*****


public class FileParser{
  PriorityQueue q;
  HuffmanTreeTable table;

  BufferedReader br; //to read txt file
  PrintWriter pw; //to write txt file
  String fileName;
  static final String compressPostFix = "_c.txt"; //to put after a compressed file
  static final String decompressPostFix = "_d.txt";//to put after a decompressed file

  

  

  public FileParser(String fileName, String mode){
    
    try {
      br = new BufferedReader(new FileReader(fileName));
      this.fileName = fileName.substring(0,fileName.lastIndexOf(".")); //get the file name without the extension, e.g. test.txt -> test
      switch(mode){
        case "c": //compress mode

          // character:frequency table
          int[] freqTable = new int[128]; //ASCII has only 128 common used characters, using array can give us O(1) in accessing and inserting

          String fileContents = "";  //the original text in the file
          
          //read the file, make a frequency table, and reconstruct the original text for later use
          fileContents = readFileCountFreq(br, freqTable);

          printFreqTable(freqTable); //debugging

          q = new PriorityQueue();
          

          //inserting nodes to the queue
          for(int i = 0; i < freqTable.length; i++){
            if(freqTable[i] == 0) continue; //doesn't contain this character
            HuffmanNode node = new HuffmanNode((char)i + "",freqTable[i]);
            q.insert(node);
          }
          //finish inserting

          //make a text:huffmanCode table
          table = new HuffmanTreeTable(); 
          makeHuffmanTreeTable(q, table);
          table.printTable(); //debugging
          makeCompressedFile(fileContents,table);
          break;


        case "d": //decompress mode
          table = new HuffmanTreeTable(); //huffmanCode:text table
          String encodingChart = br.readLine();
          String[] encodingChartArr = encodingChart.split(",");
          String base64String = encodingChartArr[encodingChartArr.length-1]; //the last item is the base64 string

          //make a huffmanCode:text table to decompress a base64 format file
          makeDecompressTable(encodingChartArr, table);
          makeDecompressedFile(table, base64String);

          break;

      }//end of switch

    } catch (IOException e) {
      System.out.println("Error Opening File "+e);
    }

  }//end of constructor



  //a method that reads a file, counts frequency of all characters appear in the file, saves it to an int array. Also reconstructs the original text to a string for later use
  public String readFileCountFreq(BufferedReader br, int[] freqTable){
    String fileContents = "";
    //---reading file---/
    try{
      
      int asciiValue = br.read(); //read the ascii value of the first character in the file

      //read through the whole file. If reach to end, will return -1
      while (asciiValue > 0) {
        //ignore those are not in base64 table
        if(isInBase64(asciiValue)){  
          fileContents = fileContents + (char)asciiValue;
          freqTable[asciiValue]++;  //frequency + 1
        }
        asciiValue = br.read();  //next character
      }
      //---finish reading file---/
    }catch(IOException e){
      System.out.println("Error "+e);
    }
    return fileContents;
  }

  public void printFreqTable(int[] freqTable){
    System.out.println("Character : Frequency");
    for(int i = 0; i < freqTable.length; i++){
      if(freqTable[i] == 0) continue;
      System.out.println((char)i + " : " + freqTable[i]);
    }
  }

  //check a value whether is in the base64 table
  public boolean isInBase64(int asciiValue){
    // +:43, /:47, 0-9:48-57, A-Z:65-90, a-z:97-122
    return asciiValue == 43 || asciiValue >= 47 && asciiValue <= 57 || asciiValue >= 65 && asciiValue <= 90 || asciiValue >= 97 && asciiValue <= 122;
  }

  //use the queue to make a text:huffmanCode table
  public void makeHuffmanTreeTable(PriorityQueue q, HuffmanTreeTable table){
    while (q.getSize() > 1) {
      HuffmanNode left = q.removeMin();
      System.out.println("Removed " + left + " from the heap");

      HuffmanNode right = q.removeMin();
      System.out.println("Removed " + right + " from the heap");


      // the other constructor takes care of the combination
      HuffmanNode combinedNode = new HuffmanNode(left, right);
      q.insert(combinedNode);
      System.out.println("Inserted " + combinedNode + " into the heap");
      q.levelOrderPrint();
        
      System.out.println();
    }

    HuffmanNode huffTree = q.removeMin(); //last item in the queue
    huffTree.makeTable(table); //generate a huffman tree table
  }


 

  public void makeCompressedFile(String fileContents, HuffmanTreeTable table){
    System.out.println("\nThe original text is "+fileContents);
    String huffmanString = "";
    
    for(char c : fileContents.toCharArray()){
      huffmanString = huffmanString + table.get(c+"");
    }
    System.out.println("The huffman code is "+huffmanString);
    
    try{
      //encode file
      String encodedStr = Base64.encode(huffmanString);
      System.out.println("The base64 string is "+encodedStr);
      pw = new PrintWriter(new FileWriter(fileName+compressPostFix));
      table.makeFile(pw, encodedStr);
      pw.close();
      System.out.println("\nThe result has been saved in "+fileName+compressPostFix+"\n");
    }catch(IOException e){
      System.out.println("Error " + e);
    }

  }

  

  public void makeDecompressedFile(HuffmanTreeTable table, String base64String){
    try{
      //decode
      String decodedBitString = Base64.decode(base64String);
      System.out.println("\nThe decoded bitstring is "+decodedBitString);

      String huffmanCode = ""; //next huffmanCode to be decompressed
      String decodedString = ""; //result


      for(int i = 0; i < decodedBitString.length(); i++){
        huffmanCode = huffmanCode + decodedBitString.charAt(i);
        if(table.containsKey(huffmanCode)){
          decodedString = decodedString + table.get(huffmanCode);
          huffmanCode = ""; //reset
        }
      }
      System.out.println("The decoded string is "+decodedString);

      pw = new PrintWriter(new FileWriter(fileName+decompressPostFix));
      pw.print(decodedString); //write
      pw.close();
      System.out.println("\nThe result has been saved in "+fileName+decompressPostFix+"\n");

    }catch(IOException e){
      System.out.println("Error " + e);
    }
  }


  //make a huffmanCode:text table to decompress a base64 format file
  public void makeDecompressTable(String[] encodingChartArr,HuffmanTreeTable table){

    /* use size-1 to exclude the last item which is the base64 string*/
    for(int i = 0; i < encodingChartArr.length-1; i++){
      //split every text:huffmanCode pair
      String[] pairArr = encodingChartArr[i].split(":");
      //  0       1
      // text:huffmanCode

      //put huffmanCode:text to the table
      table.put(pairArr[1],pairArr[0]);
    }
  }

}