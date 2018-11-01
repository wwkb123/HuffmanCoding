class PriorityQueue {
  PriorityQueueNode root;
  int size;

  public PriorityQueue() {
    root = null;
    size = 1;  //let position starts from 1
  }

  public void insert(HuffmanNode n) {
    PriorityQueueNode huffNode = new PriorityQueueNode(n);
    insert(huffNode);
  }

  // inserting a new node into the heap
  public void insert(PriorityQueueNode n) {
    /**
      This is for you to implement
      remember to consider all cases


    */
   // System.out.println("Current size is "+size);
   // System.out.println("Path is "+path(size));
    if(root == null){
      root = n;
      size++;
    }else{
      PriorityQueueNode parent = Node(size/2);
      if(size % 2 == 0){ //left
        parent.heapLeft = n;
        parent.heapLeft.heapParent = parent;
      }else{
        parent.heapRight = n;
        parent.heapRight.heapParent = parent;
      }
      size++;
    }
    
    
  }

  // removing the min node from the heap
  public HuffmanNode removeMin() {
    /**
      This is for you to implement
      It's considerably more difficult than inserting
      remember to consider all cases







      
    */
    return null;
  }

  // peek at the min node from the heap but do not remove
  public PriorityQueueNode peekMin() {
    return root;
  }

  // helper function: swap the data of 2 nodes
  private void swap(PriorityQueueNode a, PriorityQueueNode b) {
    HuffmanNode temp = a.data;
    a.data = b.data;
    b.data = temp;
  }

  // helper function: find out the path to a position
  public String path(int position) {
    if (position < 0)
      return null;
    
    String answer = "";
    while (position > 1) {
      if (position % 2 == 0) { // left branch
        answer = "L" + answer; // prepend
      } else { // right branch
        answer = "R" + answer;
      }
      position /= 2;
    }
    return answer;
  }

  // helper function: get a pointer to the node at the specified position
  public PriorityQueueNode Node(int position) {
    if (position < 0)
      return null;
    if (position == 1)
      return root;
    
    String path = path(position);
    
    PriorityQueueNode iterator = root;

    for (int i = 0; i < path.length(); i++) {
      if (path.charAt(i) == 'L') {
        iterator = iterator.heapLeft;
      } else {
        iterator = iterator.heapRight;
      }
    }
    return iterator;
  }

  // helper function: print the entire heap inorder for easier debugging
  public void printHeap() {
    String[] s = { "Heap(inorder)=" };
    recursivePrintHeap(root, s);
    System.out.println(s[0]); // array is passed by reference, string is not
  }

  private void recursivePrintHeap(PriorityQueueNode n, String[] s) {
    // prints the heap in order
    if (n == null)
      return;
    recursivePrintHeap(n.heapLeft, s);
    s[0] += "(" + n.data.text + ":" + n.data.count + ")";
    recursivePrintHeap(n.heapRight, s);
  }
}
