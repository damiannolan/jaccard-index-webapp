package ie.gmit.sw.jaccard;

import ie.gmit.sw.minhash.MinHashResult;

public interface Indexer {
	
	public double computeJaccardIndex(MinHashResult hashedDoc1, MinHashResult hashedDoc2);
}
