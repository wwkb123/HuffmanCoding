public class HuffmanNode {
  String text;
  int count;

  HuffmanNode huffLeft;
  HuffmanNode huffRight;
  HuffmanNode huffParent;

  // constructs a single node based on text and count
  public HuffmanNode(String t, int c) {
    text = t;
    count = c;
    huffLeft = null;
    huffRight = null;
    huffParent = null;
  }

  public HuffmanNode(HuffmanNode left, HuffmanNode right) {
    text = left.text + right.text;
    count = left.count + right.count;
    huffLeft = left;
    left.huffParent = this;
    huffRight = right;
    right.huffParent = this;
    
  }

  public String toString() {
    return text + ":" + count;
  }

  public void print() {
    recursivePrint(this, "");
  }

  private void recursivePrint(HuffmanNode n, String s) {
    if (n == null)
      return;
    recursivePrint(n.huffLeft, s + "0");
    if (n.text.length() == 1) { // is leaf
      System.out.println(n.text + " " + s);
    }
    recursivePrint(n.huffRight, s + "1");
  }


  //helper method to make a new HuffmanTreeTable
  public void makeTable(HuffmanTreeTable table){
    recursiveMakeTable(this, "", table);
  }

  public void recursiveMakeTable(HuffmanNode n, String s, HuffmanTreeTable table){
    if (n == null)
      return;
    recursiveMakeTable(n.huffLeft, s + "0", table);
    if (n.text.length() == 1) { // is leaf
      table.put(n.text,s); //make text:huffmanCode pair to the table
      //System.out.println(n.text + ":" + table.get(n.text)); //print table
    }
    recursiveMakeTable(n.huffRight, s + "1", table);
  }

  public boolean lowerCount(HuffmanNode otherNode) {
    return (this.count < otherNode.count);
  }

  public int getCount(){
    return count;
  }
}
