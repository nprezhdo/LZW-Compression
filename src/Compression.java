
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.lang.Integer;
import java.io.FileReader;
import java.util.ArrayList;
//import java.math.BigInteger;
//import java.io.FileOutputStream;


public class Compression {
	
	private HashMap<String, Integer> table = new HashMap<String, Integer>();
	
	public Compression () {
		// adds ascii table
		for (int i = 0; i<256; i++) {
			table.put( "" + (char)i , i);
		}
	}
	@SuppressWarnings("deprecation")
	public void compress (String inputFileName) throws IOException {
		
		BufferedReader reader = new BufferedReader (new FileReader (inputFileName));
		
		int number = 256;
		String current = "" + reader.read();
		String next = "";
		
		ArrayList<Integer> numberedValues = new ArrayList<Integer>();
		
		while(reader.ready()) {
			next = "" + reader.read();
			String cAndN = current+ next;
			
			while (table.containsKey(cAndN)){
				current = current + next;
				next = "" + reader.read();
				numberedValues.add(new Integer(table.get(current)));
				cAndN = current+ next;
			}
			
			table.put(cAndN, new Integer(number));
			number++;
			numberedValues.add(new Integer(table.get(current)));
			current = next;
			
			
		}
		
		reader.close();
		
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
	}
	/*
	
	public static void main (String[] args) throws IOException {
		Compression lolW = new Compression ();
		lolW.compress("lzw-test3.txt", "output.txt");
	}
	*/
	
}
