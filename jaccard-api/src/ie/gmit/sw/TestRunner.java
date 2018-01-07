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
import ie.gmit.sw.shingles.Shingle;
import ie.gmit.sw.shingles.TextShinglizer;

public class TestRunner {
	public static final String FILENAME = "test.txt";
	public static final String FILENAME2 = "test2.txt";
	
	public static void main(String[] args) throws IOException {
		
		FileReader fr = new FileReader(FILENAME);
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		Document doc =  new TextDocument("test", sb.toString());
		
		System.out.println(doc.toString());
		//System.out.println(doc.text());
		
//		String[] strings = doc.text().split("\\W+");
//		StringBuilder str = new StringBuilder();
//		
		TextShinglizer shinglizer = new TextShinglizer(3);
		Set<Shingle> shingles = shinglizer.shinglizeDocument(doc);
		System.out.println(Arrays.toString(shingles.toArray()));
		
		Set<Integer> hashes = new HashSet<Integer>(200);
		Random r = new Random();
		for(int i = 0; i < 200; i++) {
			hashes.add(r.nextInt());
		}
		
		Set<Integer> results = new TreeSet<Integer>();
		for(Integer hash : hashes) {
			int min = Integer.MAX_VALUE;
			
			for(Shingle s : shingles) {
				int minHash = s.hashCode() ^ hash;
				if(minHash < min) {
					min = minHash;
				}
			}
			results.add(min);

		}
		
		System.out.println(Arrays.toString(results.toArray()));
		
		
		/*
		 * ============================================================================================
		 */
		
		fr = new FileReader(FILENAME2);
		br = new BufferedReader(fr);
		
		
		sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		doc =  new TextDocument("test2", sb.toString());
		
		System.out.println(doc.toString());
		//System.out.println(doc.text());
		
//		String[] strings = doc.text().split("\\W+");
//		StringBuilder str = new StringBuilder();
//		
		TextShinglizer shinglizer2 = new TextShinglizer(3);
		Set<Shingle> shingles2 = shinglizer2.shinglizeDocument(doc);
		System.out.println(Arrays.toString(shingles2.toArray()));
		
//		Set<Integer> hashes2 = new HashSet<Integer>(200);
//		Random r = new Random();
//		for(int i = 0; i < 200; i++) {
//			hashes.add(r.nextInt());
//		}
		
		Set<Integer> results2 = new TreeSet<Integer>();
		for(Integer hash : hashes) {
			int min = Integer.MAX_VALUE;
			
			for(Shingle s : shingles2) {
				int minHash = s.hashCode() ^ hash;
				if(minHash < min) {
					min = minHash;
				}		
			}
			results2.add(min);
		}
		
		System.out.println(Arrays.toString(results2.toArray()));
		
		
		// J(A, B) = |A intersect B| / |A union B|
		
		Set<Integer> temp = results;
		boolean res = temp.retainAll(results2);
		System.out.println(res);
		
		double a = (double) temp.size();
		System.out.println(a);
		
		results.addAll(results2);
		double b = (double) results.size();
		System.out.println(b);
		
		double jaccard = a / b;
		System.out.println(jaccard);
	}

}
