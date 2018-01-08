package ie.gmit.sw.shingles;

import java.util.HashSet;
import java.util.Set;

import ie.gmit.sw.documents.Document;

public class TextShinglizer implements Shinglizer {
	
	//private final Document doc;
	private final int numWords;
	
	public TextShinglizer(int numWords) {
		//this.doc = doc;
		this.numWords = numWords;
	}
	
	/*
	 * Split the document into an array of words
	 * Iterate through the array, creating new Shingles
	 * Add them to the List of shingles
	 */
	public ShingleResult shinglizeDocument(Document doc) {
		Set<Shingle> shingles = new HashSet<Shingle>();
		
		// Split the document into an array of words 
		String[] words = doc.text().split("\\W+");
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 1; i <= words.length; i++) {
			sb.append(words[i - 1] + " ");
			
			if(i % numWords == 0) {
				shingles.add(new Shingle(sb.toString().toLowerCase()));
				sb = new StringBuilder();
			}
		}
		
		// Flush
		if(sb.length() > 0) {
			shingles.add(new Shingle(sb.toString().toLowerCase()));
		}
		return new ShingleResult(doc.title(), shingles);
	}
}
