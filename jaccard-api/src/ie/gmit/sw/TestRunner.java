package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ie.gmit.sw.documents.Document;
import ie.gmit.sw.documents.TextDocument;
import ie.gmit.sw.shingles.Shingle;
import ie.gmit.sw.shingles.Shinglizer;

public class TestRunner {
	public static final String FILENAME = "alice.txt";
	public static final String FILENAME2 = "alice.txt";
	
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
		Shinglizer shinglizer = new Shinglizer(3);
		Set<Shingle> shingles = shinglizer.shinglizeDocument(doc);
		//System.out.println(Arrays.toString(shingles.toArray()));
		
		Set<Integer> hashes = new HashSet<Integer>(200);
		Random r = new Random();
		for(int i = 0; i < 200; i++) {
			hashes.add(r.nextInt());
		}
		
		Set<Integer> results = new HashSet<Integer>();
		for(Integer hash : hashes) {
			int min = Integer.MAX_VALUE;
			
			for(Shingle s : shingles) {
				int minHash = s.hashCode() ^ hash;
				if(minHash < min) {
					min = minHash;
				}
				results.add(min);
			}
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
		Shinglizer shinglizer2 = new Shinglizer(3);
		Set<Shingle> shingles2 = shinglizer2.shinglizeDocument(doc);
		//System.out.println(Arrays.toString(shingles2.toArray()));
		
//		Set<Integer> hashes2 = new HashSet<Integer>(200);
//		Random r = new Random();
//		for(int i = 0; i < 200; i++) {
//			hashes.add(r.nextInt());
//		}
		
		Set<Integer> results2 = new HashSet<Integer>();
		for(Integer hash : hashes) {
			int min = Integer.MAX_VALUE;
			
			for(Shingle s : shingles2) {
				int minHash = s.hashCode() ^ hash;
				if(minHash < min) {
					min = minHash;
				}
				results2.add(min);
			}
		}
		
		System.out.println(Arrays.toString(results2.toArray()));
	}

}
