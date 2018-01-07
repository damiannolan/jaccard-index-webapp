package ie.gmit.sw.minhash;

import java.util.Arrays;
import java.util.Set;

public class HashedResult {
	private String title;
	private Set<Integer> hashes;
	
	public HashedResult(String title, Set<Integer> hashes) {
		this.title = title;
		this.hashes = hashes;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Set<Integer> getHashes() {
		return hashes;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setHashes(Set<Integer> hashes) {
		this.hashes = hashes;
	}
	
	@Override
	public String toString() {
		return "[" + title + "] -"+ Arrays.toString(hashes.toArray());
	}
	
}
