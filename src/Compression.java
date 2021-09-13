
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
	
	public Compression () {
		// adds ascii table
		for (int i = 0; i<256; i++) {
			table.put( "" + (char)i , i);
		}
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<Integer> compress (File inputFile) throws IOException{

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
		HashMap<String,Integer> reconstructedDict = new HashMap <String,Integer>();
		//initializes new dictionary
		for (int i = 0; i<256; i++) {
			reconstructedDict.put( "" + (char)i , i);
		}
		
		int dictLength = 256;
		BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/Alex/Desktop/Advanced-Topics-CS/LZW-Compression/decompressedFile.txt"));
		int firstVal = encodedValues.get(0);
		String firstChar = "" + (char)(firstVal);
		writer.write(firstChar);
		for (int k = 1; k < encodedValues.size(); k++)
		{
			int val = encodedValues.get(k);
			String current = "" + (char)(val);
			String next = "";
			String firstOfNext = "";
			if (!reconstructedDict.containsKey(current))
			{
				next = firstChar;
				next += firstOfNext;
			}
			else
			{
				next = current;
				writer.write(next);
				firstOfNext = ""+next.charAt(0);
				reconstructedDict.put(firstChar, dictLength);
				firstChar = current;
			}
		}
		System.out.println ("File Decompressed");
		writer.close();
	}
		
		
		/*
		byte[] byteArray = new byte[numberedValues.size()*3+10];
		for (int i = 0; i< numberedValues.size(); i++) {
			if (numberedValues.get(i)< 256) {
				BigInteger bigInt = new BigInteger("" + numberedValues.get(i).intValue());
				byte[] littleArr = bigInt.toByteArray();
				byteArray[i*3] = 0; 
				for(int j = 1; j<= littleArr.length; j++) {
					byteArray[i*3+j] = littleArr[j];
				}
			}
			else {
				BigInteger bigInt = new BigInteger("" + numberedValues.get(i).intValue());
				byte[] littleArr = bigInt.toByteArray();
				for(int j = 0; j< littleArr.length; j++) {
					byteArray[i*3+j] = littleArr[j];
				}
				
			}
		}
		FileOutputStream writer = new FileOutputStream(outputFileName);
		writer.write(byteArray);
		
		writer.close(); */
	
	
	public static void main (String[] args) throws IOException {
		Compression lolW = new Compression ();
		ArrayList<Integer> result = lolW.compress(new File("/Users/Alex/Desktop/Advanced-Topics-CS/LZW-Compression/lzw-file1.txt"));
		lolW.decompress(result);
	}
	
}
