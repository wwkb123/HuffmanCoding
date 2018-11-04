import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileParser{
  PriorityQueue q;
  HuffmanTreeTable table;
  BufferedReader br;
  int[] freqTable; // character:frequency table

  public FileParser(String fileName, String mode){
    freqTable = new int[128]; //ASCII has only 128 common used characters
    try {
      br = new BufferedReader(new FileReader(fileName));
      switch(mode){
        case "c": //compress mode

          //---reading file---/
          int asciiValue = br.read(); //read the ascii value of the first character in the file

          //read through the whole file. If reach to end, will return -1
          while (asciiValue > 0) {  
            freqTable[asciiValue]++;  //frequency + 1
            asciiValue = br.read();  //next character
          }
          //---finish reading file---/
          
          printFreqTable(freqTable); //debugging

          q = new PriorityQueue();

          //inserting nodes to the queue
          for(int i = 0; i < freqTable.length; i++){
            if(freqTable[i] == 0) continue; //doesn't contain this character
            HuffmanNode node = new HuffmanNode((char)i + "",freqTable[i]);
            q.insert(node);
          }
          //finish inserting

          makeHuffmanTree(q);



          
          break;



        case "d": //decompress mode
          break;

      }//end of switch

    } catch (IOException e) {
      System.out.println("Error Opening File "+e);
    }




  }


  public void printFreqTable(int[] freqTable){
    for(int i = 0; i < freqTable.length; i++){
      if(freqTable[i] == 0) continue;
      System.out.println((char)i + " : " + freqTable[i]);
    }
  }

  public void makeHuffmanTree(PriorityQueue q){
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

    System.out.println("\n===Huffman Code Table===\n");
    HuffmanNode huffTree = q.removeMin();

    huffTree.makeTable();
  }

}