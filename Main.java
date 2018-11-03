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




/*

    String[] charList = { " ", "s", "t", "a", "c", "e", "r", "i", "n", "u", "m", "o", "p", "d", "l", "v", "y" };
    int[] countList = { 9, 6, 6, 5, 5, 5, 5, 4, 3, 3, 2, 2, 2, 1, 1, 1, 1 };
    // initilize PriorityQueue
    PriorityQueue q = new PriorityQueue();
    // inserting all the single nodes
    System.out.println("===Adding single nodes===\n");
    for (int i = 0; i < charList.length; i++) {
      HuffmanNode singleNode = new HuffmanNode(charList[i], countList[i]);
      System.out.println("Adding \"" + charList[i] + "\"");
      q.insert(singleNode);
      q.printHeap();
    }
    System.out.println("\n===Removing and combining===\n");
    while (q.size > 1) {
      HuffmanNode left = q.removeMin();
      System.out.println("Removed " + left + " from the heap");
      q.printHeap();
      HuffmanNode right = q.removeMin();
      System.out.println("Removed " + right + " from the heap");
      q.printHeap();
      // the other constructor takes care of the combination
      HuffmanNode combinedNode = new HuffmanNode(left, right);
      q.insert(combinedNode);
      System.out.println("Inserted " + combinedNode + " into the heap");
      q.printHeap();
      System.out.println();
    }
    System.out.println("\n===Printing Huffman Code===\n");
    HuffmanNode huffTree = q.removeMin();
    huffTree.print();

*/

  PriorityQueue q = new PriorityQueue();

  for(int i = 0; i < freqTable.length; i++){
      if(freqTable[i] == 0) continue;
      HuffmanNode node = new HuffmanNode((char)i + "",freqTable[i]);
      q.insert(node);
      q.levelOrderPrint();
  }

/*
  q.insert(new HuffmanNode("a",1));
  q.insert(new HuffmanNode("b",2));
  q.insert(new HuffmanNode("c",3));
  q.insert(new HuffmanNode("d",4));
  q.insert(new HuffmanNode("e",5));
  q.insert(new HuffmanNode("f",6));
  q.insert(new HuffmanNode("g",7));
  q.insert(new HuffmanNode("h",8));
  q.insert(new HuffmanNode("i",9));
  q.insert(new HuffmanNode("j",10));
  q.insert(new HuffmanNode("k",11));

 */ 
  q.printHeap();

  System.out.println(q.size);
  q.removeMin();
  q.removeMin();

  q.printHeap();
  
  q.levelOrderPrint();
  }
}