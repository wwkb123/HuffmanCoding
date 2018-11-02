import java.util.Queue;
import java.util.LinkedList;

public class PriorityQueue {
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

    System.out.println("Current size is "+size);
    System.out.println("Path is "+path(size));


   //to do: up bubble
    if(root == null){ //first insert
      root = n;
      size++; //next position to be inserted will be size++
    }else{
      PriorityQueueNode parent = getNode(size/2);  //locate the parent which we already have its reference, so we can link a new node to the tree
      if(size % 2 == 0){ //left
        parent.heapLeft = n;  //insert left child
        parent.heapLeft.heapParent = parent; //link the child back to its parent
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
    if(root == null) return null;

    HuffmanNode removedNode = root.data; //min node should be at the top, i.e. the root
    swap(root,getNode(size-1)); //size is the next position to be inserted, so size-1 will be the previous inserted node, i.e. the right most node on the lowest level. Now swap root with it

    PriorityQueueNode parent = getNode((size-1)/2);  //find the parent of right most node on the lowest level
    
    if((size-1) % 2 == 0){ //left
      parent.heapLeft = null;  //remove left child
    }else{
      parent.heapRight = null;
    }
    
    //----------bubbling down-------------//

    //TODO  handle equal value case

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

    size--;


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
  public PriorityQueueNode getNode(int position) {
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
    s[0] += "(" + n.data.text + ":" + n.getCount() + ")";
    recursivePrintHeap(n.heapRight, s);
  }

  public void levelOrderPrint(){
    System.out.println("\nLevel Order Print:");
    if(root == null) return;
    Queue<PriorityQueueNode> q = new LinkedList<PriorityQueueNode>();
		PriorityQueueNode curr = root;
		q.add(curr);
    int height = (int) (Math.log(size)/Math.log(2));
		int nodeCount = 0;
		int level = 0;

    while(q.size() > 0 && level <= height){
			PriorityQueueNode n = q.remove();
			nodeCount++;
				
			printSpace(height*(height-level)); //
				
			if(n != null){
					
				System.out.print(n.getCount());
				printSpace(height);   //
				q.add(n.heapLeft);
				q.add(n.heapRight);
				
			}
			else{
					
				System.out.print("*");
        printSpace(height);
					
			}
			//if(nodeCount % 2 == 0) printSpace(0); 
				
			if(nodeCount == (int)Math.pow(2, level)){
				nodeCount = 0;
				System.out.println("");
				level++;
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
