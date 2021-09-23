
import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;



public class Compression {
	
	private HashMap<String, Integer> table = new HashMap<String, Integer>();
	private ArrayList<Integer> asciiValues = new ArrayList<Integer>();
	private File inputFile;
	private long compressTime; 
	private long decompressTime; 

	
	public Compression (File originalFile) {
		// adds ascii table
		for (int i = 0; i<256; i++) {
			table.put( "" + (char)i , i);
		}
		inputFile = originalFile;
	}
	
	public long timeCompression() throws IOException
	{
		long startTime = System.nanoTime();
		this.compress(); 
		long endTime = System.nanoTime();

		long duration = (endTime - startTime);
		return duration; 
		
	}
	@SuppressWarnings("deprecation")
	public ArrayList<Integer> compress () throws IOException{
		long startTime = System.nanoTime();
		
			BufferedReader reader = new BufferedReader (new FileReader(inputFile));
			String binaryString = null; 
			String current = "" + (char)reader.read();
			String next = "";
			
			while(reader.ready()) {
				next = "" + (char)reader.read();
				
				if (table.containsKey(current+next)){
					current = current + next;
				}
				else
				{
					table.put(current+next, table.size());
					asciiValues.add(table.get(current));
					current = next;
				}
			}
			asciiValues.add(table.get(current));
			reader.close();
			compressTime = (System.nanoTime() - startTime);
			System.out.println ("File compressed in " + compressTime + " ms");
			
			
			
			for(int i = 0; i < asciiValues.size(); i ++)
				
			{
				String binary = Integer.toBinaryString(asciiValues.get(i)); 
				binaryString += binary;
			}
			BinaryOut out = new BinaryOut("output.dat");
	        for (int i = 0; i < binaryString.length(); i++) {
	            if (binaryString.charAt(i) == '0') {
	                out.write(false);
	            } else {
	                out.write(true);
	            }
	        }
	        out.flush();
	        
	        return asciiValues;
			
			
		}
	
	public void decompress (ArrayList<Integer> encodedValues) throws IOException
	{
		long startTime = System.nanoTime();
		HashMap<Integer,String> reconstructedDict = new HashMap <Integer,String>();
		//initializes new dictionary
		for (int i = 0; i<256; i++) {
			reconstructedDict.put(i, "" + (char)i);
		}
		
		int dictLength = 256;
		BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/josephinetsai/eclipse-workspace/LZW-Compression/lzw-file1.txt"));
		PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
		//decodes first character of int array
		int firstVal = encodedValues.get(0);
		String firstChar = "" + (char)(firstVal);
		writer.write(firstChar);
		
		//initializes values used in the for loop algorithm
		String next = "";
		String firstOfNext = "";
		int oldVal = firstVal;
		
		//decompression algorithm
		for (int k = 1; k < encodedValues.size(); k++)
		{
			//val set to the encoded dictionary index at index k in the ArrayList
			int val = encodedValues.get(k);
			// previous set to String representation mapped with oldVal
			String previous = reconstructedDict.get(oldVal);
			
			//check for if the String representation of value is not in the dictionary
			if (val >= dictLength)
			{
				next = previous + firstOfNext;
			}
			
			//if String representation of value is in dictionary, set next equal to the String mapped with val
			else
			{
				next = reconstructedDict.get(val);
			}
			writer.write(next);
			
			//puts previous+firstOfNext string into the dictionary
			reconstructedDict.put(dictLength, previous+""+next.charAt(0));
			dictLength++;
			oldVal = val;
			
			
			
		}
		for(int i = 0; i < encodedValues.size(); i++)
		{
			out.append(reconstructedDict.get(encodedValues.get(i))); 
		}
		decompressTime = (System.nanoTime() - startTime);
		System.out.println ("File Decompressed in " + decompressTime + " ms");
		writer.close();
	
		
		}
		
	public static void main (String[] args) throws IOException {
		Compression lolW = new Compression (new File("/Users/josephinetsai/eclipse-workspace/LZW-Compression/lzw-file1.txt"));
		ArrayList<Integer> result = lolW.compress();
		lolW.decompress(result);
	}
	
}
