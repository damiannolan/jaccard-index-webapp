package ie.gmit.sw.minhash;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ie.gmit.sw.shingles.Shingle;

public class MinHash {
	
	private Set<Integer> hashes = new HashSet<Integer>(200);
	private Set<Shingle> shingles;
	
	public MinHash(Set<Shingle> shingles) {
		this.shingles = shingles;
		initHashes();
	}
	
	private void initHashes() {
		Random r = new Random();
		for(int i = 0; i < 200; i++) {
			hashes.add(r.nextInt());
		}
	}
	
	public void process() {
		Set<Integer> results = new HashSet<Integer>();
		for(Integer hash : hashes) {
			int min = Integer.MAX_VALUE;
			
			for(Shingle shingle : shingles) {
				int minHash = shingle.hashCode() ^ hash;
				if(minHash < min) {
					min = minHash;
				}
			}
			results.add(min);

		}
		
		System.out.println(Arrays.toString(results.toArray()));
	}
}
