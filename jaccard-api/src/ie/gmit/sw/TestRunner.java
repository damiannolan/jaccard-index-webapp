package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import ie.gmit.sw.documents.Document;
import ie.gmit.sw.documents.TextDocument;
import ie.gmit.sw.minhash.MinHash;
import ie.gmit.sw.shingles.Shingle;
import ie.gmit.sw.shingles.Shinglizer;
import ie.gmit.sw.shingles.TextShinglizer;

public class TestRunner {
	public static final String FILENAME = "test.txt";
	public static final String FILENAME2 = "test2.txt";
	
	public static void main(String[] args) throws IOException {
		
		/*
		 * Read First Document
		 * ===========================================================================================
		 */
		FileReader fr = new FileReader(FILENAME);
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		Document doc =  new TextDocument("test", sb.toString());
		
		/*
		 * Read Second Document
		 * ============================================================================================
		 */
		
		fr = new FileReader(FILENAME2);
		br = new BufferedReader(fr);
		
		
		sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		Document doc2 =  new TextDocument("test2", sb.toString());
		
		/*
		 * Shinglize Both Documents
		 * ============================================================================================
		 */
		Shinglizer shinglizer = new TextShinglizer(3);
		
		Set<Shingle> shingles = shinglizer.shinglizeDocument(doc);
		System.out.println(Arrays.toString(shingles.toArray()));
		
		Set<Shingle> shingles2 = shinglizer.shinglizeDocument(doc2);
		System.out.println(Arrays.toString(shingles2.toArray()));
		
		MinHash hashGen = new MinHash();
		
		Set<Integer> hashedResults = hashGen.process(shingles);	
		Set<Integer> hashedResults2 = hashGen.process(shingles2);
				
		// J(A, B) = |A intersect B| / |A union B|
		
		Set<Integer> temp = hashedResults;
		boolean res = temp.retainAll(hashedResults2);
		System.out.println(res);
		
		double a = (double) temp.size();
		System.out.println(a);
		
		hashedResults.addAll(hashedResults2);
		double b = (double) hashedResults.size();
		System.out.println(b);
		
		double jaccard = a / b;
		System.out.println(jaccard);
			}

}
