class PriorityQueueNode {
  HuffmanNode data;
  PriorityQueueNode heapLeft;
  PriorityQueueNode heapRight;
  PriorityQueueNode heapParent;

  public PriorityQueueNode(HuffmanNode data) {
    this.data = data;
    this.heapLeft = null;
    this.heapRight = null;
    this.heapParent = null;
  }

  public boolean isLeaf() {
    return (heapLeft == null && heapRight == null);
  }

  public int getCount(){ // get HuffmanNode's count
    return data.getCount();
  }
}
