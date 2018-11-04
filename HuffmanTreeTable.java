import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
//HuffmanTreeTable that maps text:huffmanCode, or huffmanCode:text

public class HuffmanTreeTable extends HashMap{

  public HuffmanTreeTable() {
    super();
  }    


  //get the value using the key
  public String get(String text) {  
    String huffmanCode = (String) super.get(text);
    return huffmanCode;
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

  public void put(String text, String huffmanCode){
    super.put(text, huffmanCode);
  }

  public String remove(String text){
    String huffmanCode = (String) super.remove(text);
    return huffmanCode;
  }

  public boolean containsKey(String text){
    return super.containsKey(text);
  }
	
  public void printTable(){
    System.out.println("\n===Huffman Code Table===\n");
    HashSet<String> keySet = new HashSet<String>((Set<String>)super.keySet()); //create a set that contains all the keys in the table
    for (String key : keySet) {
    System.out.println (key + ":" + get(key));  //print
    }
  }





}