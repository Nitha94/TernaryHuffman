import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.lang.Math;
// Ternary Huffman Tree
// Only print statements are commented out. May be uncommented for more information
abstract class HuffmanTree1 implements Comparable<HuffmanTree1> {
    public int frequency; // the frequency of this tree
    public HuffmanTree1(int freq) { frequency = freq; }
 
    // compares on the frequency
    public int compareTo(HuffmanTree1 tree) {
        return frequency - tree.frequency;
    }
}
 
class HuffmanLeaf extends HuffmanTree1 {
    public final char value; // the character this leaf represents
    
    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
        System.out.println("value "+ value+"Frequency "+freq);
    }
}
 
class HuffmanNode extends HuffmanTree1 {
    public  HuffmanTree1 left=null, right=null, middle=null; // subtrees
 
    public HuffmanNode(HuffmanTree1 l,HuffmanTree1 m, HuffmanTree1 r) {   	
        
    	super(l.frequency + r.frequency+m.frequency);  
    
    	left = l;
        right = r;
        middle=m;
       // System.out.println("Leftn "+left.toString()+"Rightn "+right+"middlen " +middle);
    }
}

public class rough {
	static Hashtable <Character,String>finalData = 
            new Hashtable<Character,String>();
	static Hashtable<Integer,Integer>lengthData = new Hashtable<Integer,Integer>();
	
    // input is an array of frequencies, indexed by character code
    public static HuffmanTree1 buildTree(int[] charFreqs) {
        PriorityQueue<HuffmanTree1> trees = new PriorityQueue<HuffmanTree1>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        // All leaves are created here !
        for (int i = 0; i < charFreqs.length; i++)
            if (charFreqs[i] > 0){            	
                trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));}
 
        assert trees.size() > 0;
        // loop until there is only one tree left
        HuffmanTree1 a=null,b=null,c=null;
        while (trees.size() > 1) {
            // two trees with least frequency
        	if(trees.size()!=0)  
        		a = trees.poll(); 
        	else a.frequency=0;
        	if(trees.size()!=0)  
        		b = trees.poll();
        	else b.frequency=0;
        	if(trees.size()!=0)  
        		c = trees.poll();
        	else c.frequency=0;
            
            // put into new node and re-insert into queue
            
            trees.offer(new HuffmanNode(a,b,c));
          //  System.out.println(trees);
        }
       
        return trees.poll();
    }
 
    public static void printCodes(HuffmanTree1 tree, StringBuffer prefix) {
    	  int sum=0;
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
 
            // print out character, frequency, and code for this leaf (which is just the prefix)
            //System.out.println("Checking Print...");
            //System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);
            //sum=sum+leaf.frequency;
            //System.out.println("Sum is:"+sum);
            // Insert it into HashMap
            int prefixlength = prefix.length();
            //System.out.println("Frequency"+leaf.frequency);
            //System.out.println("Freq*leng:"+leaf.frequency*prefixlength);
            sum=sum+(leaf.frequency)*(prefixlength);
            finalData.put(leaf.value, prefix.toString());
            lengthData.put(leaf.frequency,prefixlength);
 
        } 
        //System.out.println(sum);
        else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;
 
            // traverse left
            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);

            // traverse right
            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
            
         // traverse middle
            prefix.append('2');
            printCodes(node.middle, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
        //System.out.println("BLLLAAAAHHHH:"+sum);
        
    }
 
    public static void main(String[] args) throws FileNotFoundException {
    	
    	//String test = new Scanner(new File("/Users/nivethamahalakshmibalasamy/Desktop/Text/text_1mb.txt")).useDelimiter("\\A").next(); 
	    
        String test = "mississipi river";
//        int finalbits;
//        int initialbits= test.length()*8;
//        System.out.println("Test length:"+test.length()*8);
        
        double sum=0;
        int res;
        int count=0;
        double  probability=0.0;
        double entropy=0.0;
        double finalentropy=0.0;
        double H=0.0;
        // we will assume that all our characters will have
        // code less than 256, for simplicity
        int[] charFreqs = new int[8500];
        // read each character and record the frequencies
        for (char c : test.toCharArray())
            charFreqs[c]++;
         for(int i=0;i<charFreqs.length;i++){
        	 if(charFreqs[i]!=0){
        	 sum=sum+charFreqs[i];
        	 count++;
        	 }
         }
         System.out.println("Sum is:"+sum);
         System.out.println("Count is:"+count);
         for(int j=0;j<charFreqs.length;j++){
        	 //double entropy=0.0;
        	 if(charFreqs[j]!=0){
        		 res=charFreqs[j];
        		 //System.out.println(res);
        		 probability = res/sum;
        		//System.out.println(charFreqs[j]); 
        		 // COMMENTED
        		//System.out.println(probability);
        		double intermediate = probability*Math.log(probability);
        		entropy=entropy+intermediate;
        		//System.out.println("Entropy is:"+entropy);
        	 }
        	}
         finalentropy=-(entropy);
         System.out.println("Added Entropy is:"+finalentropy);
         //double intermediate = probability*Math.log(probability);
         
         // Finding Average number of Bits needed
         
         for(int j=0;j<charFreqs.length;j++){
        	 //double entropy=0.0;
        	 if(charFreqs[j]!=0){
        		 res=charFreqs[j];
        		 //System.out.println(res);
        		 probability = res/sum;
        		//System.out.println(charFreqs[j]); 
        		 // COMMENTED
        		//System.out.println(probability);
        		double intermediate = probability*logofBase(3,1/probability);
        		H=H+intermediate;
        		//System.out.println("H inter"+intermediate);
        		//System.out.println("Entropy is:"+entropy);
        	 }
        	}
         System.out.println("H is :"+H);
         
        // build tree
        long startTimeconst = System.nanoTime();
        HuffmanTree1 tree = buildTree(charFreqs);
        long endTimeconst = System.nanoTime();
		System.out.println("\nTime taken for Tree Construction: "+(endTimeconst - startTimeconst) + " ns");
        
 
        // print out results
        System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
        printCodes(tree, new StringBuffer());
        System.out.println("Final Data:");
        //System.out.println(finalData);
        System.out.println(finalData.keySet());
        System.out.println(finalData.values());
        System.out.println(lengthData.keySet());
        System.out.println(lengthData.values());
        ArrayList<Integer> list = new ArrayList<Integer>(lengthData.keySet());
        ArrayList<Integer> list1 = new ArrayList<Integer> (lengthData.values());
        ArrayList<Integer> mergedlist = new ArrayList<Integer>();
       //System.out.println(list);
       //System.out.println(list1);
        for(int i=0;i<list1.size();i++){
        	mergedlist.add(new Integer(list.get(i).intValue()*list1.get(i).intValue()));
        }
        System.out.println(mergedlist);
        int addedvalue=0;
        for(int j=0;j<mergedlist.size();j++){
        	addedvalue+=mergedlist.get(j);
        }
        System.out.println("Added Value:"+addedvalue);
        double averagecodewordlength= (double)addedvalue/(double)sum;
        System.out.println("Average Code Word Length:"+averagecodewordlength +" trits");
        
        //System.out.println("Value:");
        //System.out.println(val);
        
        //System.out.println(lengthData.keySet()*lengthData.values());
        long startTime = System.nanoTime();
        binarycode(test);
        long endTime = System.nanoTime();
		System.out.println("\nTime taken for Encoding: "+(endTime - startTime) + " ns");
        
    }
   

	static void binarycode(String test){
    	int initialbits= test.length()*8;
    	System.out.println("Initial Bits:"+initialbits);
    	//long startTime = System.nanoTime();
    	StringBuffer tempEncoding = new StringBuffer();
    	for(int i=0;i<test.length();i++){
    		char search=test.charAt(i);
    		tempEncoding.append(finalData.get(search));
    		//long endTime = System.nanoTime();
    		//System.out.println("\nTime taken "+(endTime - startTime) + " ns");
    	}
    	//System.out.println("Encoded String");
    	//System.out.println(tempEncoding);
    	System.out.println("Encoded String length:"+ tempEncoding.length());
    	int finalbits=tempEncoding.length();
    	double CompressionRatio = initialbits/(double)finalbits;
    	System.out.println("Compression Ratio: "+ CompressionRatio);
    	double CompressionPercentage = ((double)(initialbits-finalbits)/(double)initialbits)*100;
    	System.out.println("Percentage of Compression:"+ CompressionPercentage);
    	//System.out.println(tempEncoding);
    }
    
  public static double logofBase(int base,double number){
    	return Math.log(number)/Math.log(base);
    }
 }






