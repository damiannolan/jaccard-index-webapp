package ie.gmit.sw;

import java.util.List;

import ie.gmit.sw.database.MinHashStore;
import ie.gmit.sw.documents.Document;
import ie.gmit.sw.jaccard.Indexer;
import ie.gmit.sw.jaccard.JaccardIndexer;
import ie.gmit.sw.minhash.MinHash;
import ie.gmit.sw.minhash.MinHashResult;
import ie.gmit.sw.shingles.ShingleResult;
import ie.gmit.sw.shingles.Shinglizer;
import ie.gmit.sw.shingles.TextShinglizer;

public class JaccardFacade implements IJaccardFacade {
	private final int NUM_WORDS = 3;
	
	private Shinglizer shinglizer;
	private MinHash hashGenerator;
	private MinHashStore datastore;
	private Indexer indexer;
	
	public JaccardFacade() {
		this.shinglizer = new TextShinglizer(NUM_WORDS);
		this.hashGenerator = new MinHash();
		this.datastore = MinHashStore.getInstance();
		this.indexer = new JaccardIndexer();
	}
	
	public ShingleResult shinglizeDocument(Document doc) {
		return shinglizer.shinglizeDocument(doc);
	}
	
	public MinHashResult computeMinHash(ShingleResult shingleResult) {
		return hashGenerator.process(shingleResult);
	}
	
	public void storeHashedResult(MinHashResult hashedResult) {
		datastore.addMinHashedDocument(hashedResult);
	}
	
	public double averageJaccardIndex(MinHashResult minhash) {
		List<MinHashResult> storedResults = datastore.getMinHashedDocuments();
		
		double total = 0;
		for(MinHashResult storedResult : storedResults) {
			double index = indexer.calculateIndex(minhash, storedResult);
			total += index;
		}
		
		return total / storedResults.size();
	}
	
}
