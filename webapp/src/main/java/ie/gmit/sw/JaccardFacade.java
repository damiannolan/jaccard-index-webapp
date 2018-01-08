package ie.gmit.sw;

import ie.gmit.sw.documents.Document;
import ie.gmit.sw.minhash.MinHash;
import ie.gmit.sw.minhash.MinHashResult;
import ie.gmit.sw.shingles.ShingleResult;
import ie.gmit.sw.shingles.Shinglizer;
import ie.gmit.sw.shingles.TextShinglizer;

public class JaccardFacade implements IJaccardFacade {
	private final int NUM_WORDS = 3;
	
	private Shinglizer shinglizer;
	private MinHash hashGenerator;
	
	public JaccardFacade() {
		this.shinglizer = new TextShinglizer(NUM_WORDS);
		this.hashGenerator = new MinHash();
	}
	
	public ShingleResult shinglizeDocument(Document doc) {
		return shinglizer.shinglizeDocument(doc);
	}
	
	public MinHashResult hashDocument(ShingleResult shingleResult) {
		return hashGenerator.process(shingleResult);
	}
	
}
