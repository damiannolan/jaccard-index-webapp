package ie.gmit.sw.jaccard;

import java.util.Set;

import ie.gmit.sw.minhash.MinHashResult;

public class JaccardIndexer implements Indexer {
	
	public double calculateIndex(MinHashResult hashResult, MinHashResult hashResult2) {
		System.out.println("COMPUTE JACCARD INDEX");
		
		Set<Integer> temp = hashResult.getHashes();
		boolean res = temp.retainAll(hashResult2.getHashes());
		System.out.println(res);
		
		double a = (double) temp.size();
		System.out.println(a);
		
		hashResult.getHashes().addAll(hashResult2.getHashes());
		double b = (double) hashResult.getHashes().size();
		System.out.println(b);
		
		return a / b;
	}
}
