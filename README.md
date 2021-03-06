# HuffmanCoding

Yiu Chung Yau // Your Name
23506123 // Your CUNY ID

Description:
This program can take a text file input, compresses/decompresses it (depend on user's option), and output the result to another file. 

Note:
1. *** -> extra things that I implemented
2. To make this documentation more readable, some complex logics may not be explained here as they will take a lot of space. They are explained inside the class instead.
3. The huffman codes generated in this program can be different from other implementations as I didn't ask the nodes to swap when they have the same count. But since this will still make a correct corresponding Huffman tree table, I can still get correct compressions and decompressions.
4. Only repl.it, somehow I cannot compress a file and decompress it immediately. I must exit the program and run it again to decompress the file. Also, sometimes if the input file is too large, repl's buffer will write wrong outputs to the file. Please re-open this repl when this occurs.
5. Originally, I used format a:0101,b:000,  to represent the decoding chart. Since I want to include other symbols like , :  as well so I change the format to a\~0101\`b~000, where \~ acts like colon(\:) and \` acts like comma(,). So in some parts of the documentation/comment, if you see text\:huffmanCode, I meant text\~huffmanCode in the actual decoding chart



This program is consists of 7 classes, Main, Base64, FileParser,HuffmanNode, HuffmanTreeTable, PriorityQueue and PriorityQueueNode. Their functionalities will be explained below.


Main overview:
  ***User chooses a mode(compress/decompress/exit) and enter a file name (with extension).

  -if compress is chosen, call FileParser's constructor with compress mode

  -if decompress is chosen, call FileParser's constructor with decompress mode



***Base64 overview:
  -I implemented my own Base64 encoder/decoder. All the methods are static since there's no need to create an instance of Base64 if we just want to encode/decode.

  -Implemented Base64 Table using a String called characterSet that contains all possible ASCII characters, so I can get a particular base64 bitstring of a character easily by using indexOf(character), to get the index, then convert the index(decimal) to bitstring (binary). 
  
  -Java's Integer class' built-in toString(n,2) method provides a easy way to convert a decimal number, n, to bitstring format like "101001". Vice versa for parseInt(n,2).

  encode(String bitstring): takes a bitstring like "0101" and return an encoded base64 string like "l+lr===A". Handle 6 strings only every time when traversing the string, and do padding if the last group of string has only 1-5 digit(s).

  decode(String encodedString): takes a encoded base64 string like "l+lr===A", decoded it back to bitstring form. More detailed logics are explained inside the class.

  binaryToString(String bitstring): converts a base 64 binary string(no padding) to a string character, e.g. 0 => A. Integer.parseInt is used.

  stringtoBinary(String text): converts a string character to base 64 binary string, e.g. A => 000000. Integer.toString is used.



FileParser overview:
  -A class that parses files. The constructor will perform compression or decompression depends on which mode (c/d) it is given. It should be noted that this parser will ignore symbols that are used as delimiters in the decoding chart, i.e. ` and ~ as they are believed to be the least used symbols.

  https://www.wired.com/2013/08/the-rarity-of-the-ampersand/

  \` acts like a comma, and \~ acts like a colon
  e.g.
   \~001\`a\~1110\`c\~0100\`d\~0101\`e\~1001\`f\~0110\`i\~0111\`n\~1000\`r\~1111\`s\~000\`t\~110\`u\~101\`Xtw31NfkuC1==I

  is equivalent to 

   :001,a:1110,c:0100,d:0101 etc.

  -Compression: Counts the frequency of all the characters appear in the input file, then generates a huffman code tree table(contains text:huffmanCode pairs) by using a heap-implemented priority queue. Then, by using the table, encodes the original text into base 64 format bitstring, and write it to a file, with the decoding chart. For example, 

     ~001`a~1110`c~0100`d~0101`e~1001`f~0110`i~0111`n~1000`r~1111`s~000`t~110`u~101`Xtw31NfkuC1==I

  The last string is the base 64 string, the rest are text:huffmanCode pairs(the decoding chart).

  -Decompression: Takes a decoding chart file as input(like the one above), and generate a huffmanCode:text table. By using the table, decodes the base 64 string (the last string) back to bitstring first, and converts it back to ASCII text using the decoding chart. Writes the result to a file.

  -More detailed logics are explained inside the class

  ***Files that have been compressed will have a "_c" after its original name. "_d" for files that have been decompressed.



HuffmanNode overview:
  -Most of the parts are acquired from Professor Liu's repl. HuffmanNodes that will finally form a Huffman code tree.
  
  ***Added makeTable() and recursiveMakeTable() to use the final HuffmanNode(contains the whole tree) to generate a HuffmanTreeTable (text:huffmanCode pairs).


HuffmanTreeTable overview:
  -Basically is a HashMap. Since the text and huffmanCode in a Huffman tree table are one-to-one, we can use this class to create either a text:huffmanCode table to do compression, or a huffmanCode:text table to do decompression

  -printTable(): displays the content of the table for debugging.

  -makeFile(): makes the table to an decoding chart file, e.g.
   a\~1110\`c\~0100\`d\~0101\`e\~1001\`f\~0110\`i\~0111\`n\~1000\`r\~1111\`s\~000\`t\~110\`u\~101\`Xtw31NfkuC1==I


PriorityQueue overview:
  -Heap-implemented. Most of the parts are acquired from Professor Liu's repl. Aborts the name "size", using nextPosition instead. Size will sill be the same as nextPosition.

  -nextPosition, starts from 0, indicates the position of the next node. Since it is starts from 0, parent of a node, parent(i), will be (i-1)/2 instead of i/2. Left children will be with odd position, even position for right children.

  -Modified path() and Node() a bit to adapt my logics. Rename Node() to getNode().

  ***levelOrderPrint(): A method to print the heap content in level order, to help visualizing what is happening in the heap (If the heap is big, the output may be distorted a bit). For example,
  
```
      2  
    3    2  
  3  *  *  *
  
```
  '*' indicates the node is null. 

  -More detailed logics such as insert/remove/bubble up/bubble down are explained inside the class



PriorityQueueNode overview:
 -Basically most of the parts are acquired from Professor Liu's repl. Add proper initializations in the construtor.
