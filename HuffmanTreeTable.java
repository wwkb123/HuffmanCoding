import java.util.HashMap;

public class HuffmanTreeTable extends HashMap{

  public HuffmanTreeTable() {
    super();
  }    


  //get the value using the key
  public int getValue(String text) {  
    Integer count = (Integer) super.get(text);
    if (count == null) return -1;
    return count;
  }


  //since the text and count in a Huffman tree table are one-to-one, we can get a key using a value
  public String getKey(int count){ 
    
    // for(String key:super.keySet()){
    //   if(getValue(key) == count){
    //     return key;  //found it
    //   }
    // }

    return null; //not found it

  }

  public void put(String text, int count){
    super.put(text, count);
  }

  public int remove(String text){
    Integer count = (Integer) super.remove(text);
    if (count == null) return -1;
    return count;
  }

  public boolean containsKey(String text){
    return super.containsKey(text);
  }
	





}