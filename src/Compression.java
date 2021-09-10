
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class Compression {
	
	private HashMap<String, Integer> table = new HashMap<String, Integer>();
	
	public Compression () {
		// adds ascii table
		for (int i = 0; i<256; i++) {
			table.put( "" + (char)i , i);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void compress (File inputFile) throws IOException{

			BufferedReader reader = new BufferedReader (new FileReader(inputFile));
			
			int number = 256;
			String current = "" + (char)reader.read();
			String next = "";
			
			ArrayList<Integer> numberedValues = new ArrayList<Integer>();
			
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
					numberedValues.add(table.get(current));
					current = next;
				}
			}
			numberedValues.add(table.get(current));
			
			for (int i = 0; i < numberedValues.size(); i++)
			{
				System.out.println (numberedValues.get(i));
			}
			
			reader.close();
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
		lolW.compress(new File("/Users/Alex/Desktop/Advanced-Topics-CS/LZW-Compression/lzw-file1.txt"));
	}
	
}
