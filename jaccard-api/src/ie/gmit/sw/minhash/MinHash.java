package ie.gmit.sw.minhash;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ie.gmit.sw.shingles.Shingle;
import ie.gmit.sw.shingles.ShingleResult;

public class MinHash {
	
	private static Set<Integer> hashes = new HashSet<Integer>(200);
	
	public MinHash() {
		initHashes();
	}
	
	private void initHashes() {
		Random r = new Random();
		r.setSeed(123456789);
		for(int i = 0; i < 200; i++) {
			hashes.add(r.nextInt());
		}
		System.out.println("randoms: " + Arrays.toString(hashes.toArray()));
	}
	
	public MinHashResult process(ShingleResult shingleResult) {
		Set<Integer> results = new HashSet<Integer>();
		for(Integer hash : hashes) {
			int min = Integer.MAX_VALUE;
			
			for(Shingle shingle : shingleResult.getShingles()) {
				int minHash = shingle.hashCode() ^ hash;
				if(minHash < min) {
					min = minHash;
				}
			}
			results.add(min);
		}
		
		System.out.println(Arrays.toString(results.toArray()));
		return new MinHashResult(shingleResult.getTitle(), results);
	}
}
