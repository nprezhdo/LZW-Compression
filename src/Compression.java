
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;



public class Compression {
	
	private HashMap<String, Integer> table = new HashMap<String, Integer>();
	private ArrayList<Integer> asciiValues = new ArrayList<Integer>();
	private File inputFile;
	
	public Compression (File originalFile) {
		// adds ascii table
		for (int i = 0; i<256; i++) {
			table.put( "" + (char)i , i);
		}
		inputFile = originalFile;
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<Integer> compress () throws IOException{

			BufferedReader reader = new BufferedReader (new FileReader(inputFile));
			
			int number = 256;
			String current = "" + (char)reader.read();
			String next = "";
			
			while(reader.ready()) {
				next = "" + (char)reader.read();
				String cAndN = current+ next;
				
				if (table.containsKey(cAndN)){
					current = current + next;
				}
				else
				{
					table.put(cAndN, number);
					number++;
					asciiValues.add(table.get(current));
					current = next;
				}
			}
			asciiValues.add(table.get(current));
			reader.close();
			return asciiValues;
			/*for (int i = 0; i < asciiValues.size(); i++)
			{
				System.out.println (asciiValues.get(i));
			}
			
			reader.close();*/
			
		}
	
	public void decompress (ArrayList<Integer> encodedValues) throws IOException
	{
		HashMap<Integer,String> reconstructedDict = new HashMap <Integer,String>();
		//initializes new dictionary
		for (int i = 0; i<256; i++) {
			reconstructedDict.put(i, "" + (char)i);
		}
		
		int dictLength = 256;
		BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/Alex/Desktop/Advanced-Topics-CS/LZW-Compression/decompressedFile.txt"));
		
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
			int val = encodedValues.get(k);
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
			firstOfNext = "" + next.charAt(0);
			
			//puts previous+firstOfNext string into the dictionary
			reconstructedDict.put(dictLength, previous+firstOfNext);
			dictLength++;
			oldVal = val;
		}
		System.out.println ("File Decompressed");
		writer.close();
	}
		
	public static void main (String[] args) throws IOException {
		Compression lolW = new Compression (new File("/Users/Alex/Desktop/Advanced-Topics-CS/LZW-Compression/lzw-file3.txt"));
		ArrayList<Integer> result = lolW.compress();
		lolW.decompress(result);
	}
	
}
