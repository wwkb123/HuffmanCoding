public class Base64{

  final static String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";


  public static String encode(String bitstring){
    String result = "";
    while(bitstring.length() >= 6){ 
      //handle the first 6 digits of bits every time
      result = result + binaryToString(bitstring.substring(0,6));
      bitstring = bitstring.substring(6,bitstring.length());
    }

    //padding the last 1-5 digits
    while(bitstring.length() < 6){
      result = result + "=";
      bitstring = "0" + bitstring;
    }
    result = result + binaryToString(bitstring.substring(0,6));
    
    return result;
  }

  public static String decode(String encodedString){
    String bitstring = "";
    int paddingIndex = -1;
    for(int i = 0; i < encodedString.length(); i++){
      String currChar = encodedString.charAt(i)+""; //cast to string
      if(!currChar.equals("=")){ //normal character
        bitstring = bitstring + stringtoBinary(currChar);
      }else{ //padding character(=)
        paddingIndex = i; //update the index
        break;
      }
    }//end of for loop

    if(paddingIndex != -1){  //if there is a padding

      String lastStr = Integer.toString(characterSet.indexOf(encodedString.charAt(encodedString.length()-1)),2);//get the last string character, convert it into base64 format
      int paddingWeHave = (encodedString.substring(paddingIndex, encodedString.length()-1)).length(); //last string character must be a character other than "=", so the one before it will be the last "=". This line counts how many "=" are there

      int paddingNeeded = 6 - paddingWeHave - lastStr.length(); //6 - # of "=" - length of the string = how many 0s we need to pad

      //padding
      while(paddingNeeded > 0){ 
        bitstring = bitstring + "0";
        paddingNeeded--;
      }

      bitstring = bitstring + lastStr; //add it to the back of the bitstring

    }

    return bitstring;
  }

  public static String binaryToString(String bitstring){
    /*
      Java doc:
        Integer.parseInt(binary,2) // binary to decimal
        e.g. parseInt("1100110", 2) returns 102
    */
    int index = Integer.parseInt(bitstring, 2);
    String result = characterSet.charAt(index) + ""; //cast to string
    return result;
  }


  //convert a string character to base 64 binary string, e.g. A => 000000
  public static String stringtoBinary(String text){
  
    /*
      Java doc:
      Integer.toString(n,8) // decimal to octal
      Integer.toString(n,2) // decimal to binary
      Integer.toString(n,16) //decimal to Hex
      where n = decimal number.
    */

    //convert to base 64 binary string, e.g. A => 000000
    String result = Integer.toString(characterSet.indexOf(text),2);

    //padding
    while(result.length() < 6){
      result = "0" + result;
    }

    return result;
  }
  

}