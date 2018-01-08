package ie.gmit.sw.minhash;

import java.util.Arrays;
import java.util.Set;

public class MinHashResult {
	private String title;
	private Set<Integer> minHashes;
	
	public MinHashResult(String title, Set<Integer> hashes) {
		this.title = title;
		this.minHashes = hashes;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Set<Integer> getHashes() {
		return minHashes;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setHashes(Set<Integer> hashes) {
		this.minHashes = hashes;
	}
	
	@Override
	public String toString() {
		return "[" + title + "] -"+ Arrays.toString(minHashes.toArray());
	}
	
}
