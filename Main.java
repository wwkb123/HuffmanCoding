/**

Project Description Link = https://docs.google.com/document/d/1rW8ipNNEQ7mglGzWcQ8AdL6qvNjBP51uFDXvGwUFldU

 */

class Main {
  //The main routine here. Add other classes as needed
  public static void main(String[] args) {

    

    String mytext = "ashduashdkahwkuahwrkuarh";

    int[] freqTable = new int[128];

    for(int i = 0; i < mytext.length(); i++){
      freqTable[(int)mytext.charAt(i)]++;
    }

    for(int i = 0; i < freqTable.length; i++){
      if(freqTable[i] == 0) continue;
      System.out.println((char)i + " : " + freqTable[i]);
    }


  PriorityQueue q = new PriorityQueue();

  for(int i = 0; i < freqTable.length; i++){
    if(freqTable[i] == 0) continue;
    HuffmanNode node = new HuffmanNode((char)i + "",freqTable[i]);
    q.insert(node);
      
    q.levelOrderPrint();
    //System.out.println("Current size is "+q.getSize()+"\n\n");
  }


  while (q.getSize() > 1) {
    HuffmanNode left = q.removeMin();
    System.out.println("Removed " + left + " from the heap");
    q.levelOrderPrint();
    HuffmanNode right = q.removeMin();
    System.out.println("Removed " + right + " from the heap");
    q.levelOrderPrint();

    // the other constructor takes care of the combination
    HuffmanNode combinedNode = new HuffmanNode(left, right);
    q.insert(combinedNode);
    System.out.println("Inserted " + combinedNode + " into the heap");
    q.levelOrderPrint();
      
      
    System.out.println();
  }

  System.out.println("\n===Printing Huffman Code===\n");
  HuffmanNode huffTree = q.removeMin();
  huffTree.print();
  
  
  }
}