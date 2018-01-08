package ie.gmit.sw;

import ie.gmit.sw.documents.Document;
import ie.gmit.sw.minhash.MinHashResult;
import ie.gmit.sw.shingles.ShingleResult;

public interface IJaccardFacade {
	
	public ShingleResult shinglizeDocument(Document doc);
	
	public MinHashResult hashDocument(ShingleResult shingleResult);
	
	public void storeHashedResult(MinHashResult hashedResult);
	
	public double averageJaccardIndex(MinHashResult minhash);
}
