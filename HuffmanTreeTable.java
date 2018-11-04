import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.io.PrintWriter;
//HuffmanTreeTable that maps text:huffmanCode, or huffmanCode:text

//since the text and huffmanCode in a Huffman tree table are one-to-one, we can use this class to create a text:huffmanCode table to do compression, or a huffmanCode:text table to do decompression

public class HuffmanTreeTable extends HashMap{

  public HuffmanTreeTable() {
    super();
  }    


  //get the value using the key
  public String get(String key) {  
    String value = (String) super.get(key);
    return value;
  }

  public void put(String key, String value){
    super.put(key, value);
  }

  public String remove(String key){
    String value = (String) super.remove(key);
    return value;
  }

  public boolean containsKey(String key){
    return super.containsKey(key);
  }
	
  public void printTable(){
    System.out.println("\n===Huffman Code Table===\n");
    HashSet<String> keySet = new HashSet<String>((Set<String>)super.keySet()); //create a set that contains all the keys in the table
    for (String key : keySet) {
    System.out.println (key + ":" + get(key));  //print
    }
  }

  //make the table to an encoding chart, e.g. a:0,M:10,n:11,MTAwMTE=
  public void makeFile(PrintWriter pw, String base64Str){
    HashSet<String> keySet = new HashSet<String>((Set<String>)super.keySet()); //create a set that contains all the keys in the table
    try {
      for (String key : keySet) {
      pw.print(key+":"+get(key)+",");  //write to file in format text:huffmanCode
      }
      pw.print(base64Str); //last item, the base64 string
    }catch (NullPointerException np) {
      System.out.println("Must first initiate the file writer");
    }
    
  }





}