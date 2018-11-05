import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

//*****This parser will ignore symbols that are not on the base64 table, e.g. comma, colon etc.*****


public class FileParser{
  PriorityQueue q;
  HuffmanTreeTable table;

  BufferedReader br; //to read txt file
  PrintWriter pw; //to write txt file

  int[] freqTable; // character:frequency table
  

  public FileParser(String fileName, String mode){
    freqTable = new int[128]; //ASCII has only 128 common used characters
    try {
      br = new BufferedReader(new FileReader(fileName));
      switch(mode){
        case "c": //compress mode
          String fileContents = "";
          //---reading file---/
          int asciiValue = br.read(); //read the ascii value of the first character in the file

          //read through the whole file. If reach to end, will return -1
          while (asciiValue > 0) {  
            fileContents = fileContents + (char)asciiValue;
            freqTable[asciiValue]++;  //frequency + 1
            asciiValue = br.read();  //next character
          }
          //---finish reading file---/
          
          printFreqTable(freqTable); //debugging

          q = new PriorityQueue();
          table = new HuffmanTreeTable(); //text:huffmanCode table

          //inserting nodes to the queue
          for(int i = 0; i < freqTable.length; i++){
            if(freqTable[i] == 0) continue; //doesn't contain this character
            HuffmanNode node = new HuffmanNode((char)i + "",freqTable[i]);
            q.insert(node);
          }
          //finish inserting

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
          //makeDecompressTable(encodingChartArr, table);
          //makeDecompressedFile(table);

          break;

      }//end of switch

    } catch (IOException e) {
      System.out.println("Error Opening File "+e);
    }

  }//end of constructor


  public void printFreqTable(int[] freqTable){
    System.out.println("Character : Frequency");
    for(int i = 0; i < freqTable.length; i++){
      if(freqTable[i] == 0) continue;
      System.out.println((char)i + " : " + freqTable[i]);
    }
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

  public void makeCompressedFile(String fileContents, HuffmanTreeTable table){
    String huffmanString = "";
    //System.out.println(fileContents);
    for(char c : fileContents.toCharArray()){
      huffmanString = huffmanString + table.get(c+"");
    }
    System.out.println("\nThe huffman code is "+huffmanString);
    
    try{
      //encode file
      pw = new PrintWriter(new FileWriter("output.txt"));
      table.makeFile(pw, /*base64String*/);
      pw.close();
    }catch(IOException e){
      System.out.println("Error " + e);
    }

  }

  public void makeDecompressedFile(HuffmanTreeTable table){
    try{
      //decode to file

      String huffmanCode = ""; //next huffmanCode to be decompressed
      String decodedString = ""; //result


      for(int i = 0; i < result.length(); i++){
        huffmanCode = huffmanCode + result.charAt(i);
        if(table.containsKey(huffmanCode)){
          decodedString = decodedString + table.get(huffmanCode);
          huffmanCode = ""; //reset
        }
      }
      System.out.println("Result is "+decodedString);
    }catch(IOException e){
      System.out.println("Error " + e);
    }
  }

}