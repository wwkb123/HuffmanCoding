import java.util.Queue;
import java.util.LinkedList;

public class PriorityQueue {
  PriorityQueueNode root;
  int nextPosition;

  public PriorityQueue() {
    root = null;
    nextPosition = 0;  //let position starts from 0
  }

  public void insert(HuffmanNode n) {
    PriorityQueueNode huffNode = new PriorityQueueNode(n);
    insert(huffNode);
  }

  // inserting a new node into the heap
  public void insert(PriorityQueueNode n) {

    //System.out.println("Current nextPosition is "+nextPosition);
    //System.out.println("Path is "+path(nextPosition));

    if(root == null){ //first insert
      root = n;
      nextPosition++; //next position to be inserted will be nextPosition++
    }else{
      PriorityQueueNode parent = getNode((nextPosition-1)/2);  //locate the parent which we already have its reference, so we can link a new node to the tree
      if(nextPosition % 2 == 1){ 
        /*left child is always with odd number position, 
        for example:

                  0
                 / \
            ->  1   2

        */
      
        parent.heapLeft = n;  //insert left child
        parent.heapLeft.heapParent = parent; //link the child back to its parent
      }else{  //right child is always with even number position
        parent.heapRight = n;
        parent.heapRight.heapParent = parent;
      }

      nextPosition++;

      //----------bubbling up-------------//


      PriorityQueueNode curr = getNode(nextPosition-1); //get the node we just inserted
      while(curr.heapParent != null){
        if(curr.heapParent.getCount() > curr.getCount()){
          swap(curr.heapParent, curr);
          curr = curr.heapParent;
        }else{
          break;  //finish
        }
      } //end of while loop


      //----------end of bubbling up-------------//

      
    }
    
    
  }

  // removing the min node from the heap
  public HuffmanNode removeMin() {
    /**
      This is for you to implement
      It's considerably more difficult than inserting
      remember to consider all cases

    */
    if(root == null) return null;
    HuffmanNode removedNode = root.data; //min node should be at the top, i.e. the root

    if(nextPosition == 1){ // so previous position is 0, the tree contains only root
      root = null;
      nextPosition--;
      return removedNode;
    }

    swap(root,getNode(nextPosition-1)); //nextPosition is the next position to be inserted, so nextPosition-1 will be the previous inserted node, i.e. the right most node on the lowest level. Now swap root with it

    PriorityQueueNode parent = getNode(((nextPosition-1)-1)/2);  //find the parent of right most node on the lowest level, nextPosition-1 -> previous position, (previous position-1)/2 -> its parent
    
    if((nextPosition-1) % 2 == 1){ //if the right most node is with odd number position, so it is a left child 
      parent.heapLeft = null;  //remove left child
    }else{
      parent.heapRight = null;
    }

    nextPosition--;
    
    //----------bubbling down-------------//

    PriorityQueueNode curr = root;

    while(!curr.isLeaf()){
      PriorityQueueNode left = curr.heapLeft;
      PriorityQueueNode right = curr.heapRight;

      //****  cases to be considered  ****//
      if(left != null && right == null){ // only left is available
        if(curr.getCount() > left.getCount()){
          swap(curr,left);
          curr = left;
        }else{
          break;
        }
      }
      else if(left == null && right != null){ // only right is available
        if(curr.getCount() > right.getCount()){
          swap(curr,right);
          curr = right;
        }else{
          break;
        }
      }
      else{  // both left and right are available
        if(curr.getCount() < left.getCount() && curr.getCount() < right.getCount()){
          break;  //unchange, bubble down is finished
        }
        else if(curr.getCount() > left.getCount() &&  curr.getCount() < right.getCount()){
          swap(curr,left);
          curr = left;
        }
        else if(curr.getCount() < left.getCount() &&  curr.getCount() > right.getCount()){
          swap(curr,right);
          curr = right;
        }
        else{  //curr.getCount() > left's and right's
          if(left.getCount() > right.getCount()){  //right is smaller, swap right with curr
            swap(curr,right);
            curr = right;
          }else{ //left is smaller
            swap(curr,left);
            curr = left;
          }
        }
      }
      
      //****  end of all cases  ****////

    }  //end of while loop

    


    //----------end of bubbling down-------------//
    return removedNode;
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
    while (position > 0) {
      if (position % 2 == 1) { // left branch
        answer = "L" + answer; // prepend
      } else { // right branch
        answer = "R" + answer;
      }
      position = (position-1)/2;
    }
    return answer;
  }

  // helper function: get a pointer to the node at the specified position
  public PriorityQueueNode getNode(int position) {
    if (position < 0)
      return null;
    if (position == 0)
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
    s[0] += "(" + n.data.text + ":" + n.getCount() + ")";
    recursivePrintHeap(n.heapRight, s);
  }

  public boolean isEmpty(){
    return root == null;
  }

  public int getSize(){
    return nextPosition; //next position should be equal to the size
  }

  public void levelOrderPrint(){
    System.out.println("\nLevel Order Print:");
    if(root == null) {
      System.out.print("*");
      return;
    }
    Queue<PriorityQueueNode> q = new LinkedList<PriorityQueueNode>();
		PriorityQueueNode curr = root;
		q.add(curr);
    int height = (int) (Math.log(nextPosition)/Math.log(2));
		int nodeCount = 0;
		int level = 0;

    while(q.size() > 0 && level <= height){
			PriorityQueueNode n = q.remove();
			nodeCount++;
				
			printSpace(height*(height-level)); //print space between nodes on the same level
				
			if(n != null){
					
				System.out.print(n.getCount());
				printSpace(height);   
				q.add(n.heapLeft);
				q.add(n.heapRight);
				
			}
			else{
				// * is a null node
				System.out.print("*");
        printSpace(height);
					
			}
			//if(nodeCount % 2 == 0) printSpace(1); 
				
			if(nodeCount == (int)Math.pow(2, level)){
				nodeCount = 0;  // reset the count for the next level
				System.out.println("");
				level++;  // to the next level
			}
		}
	}
		
	public void printSpace(int number){
		while(number > 0){
			System.out.print(" ");
			number--;
		}
	}

}
