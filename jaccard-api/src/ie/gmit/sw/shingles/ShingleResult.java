package ie.gmit.sw.shingles;

import java.util.Set;

public class ShingleResult {
	private String title;
	private Set<Shingle> shingles;
	
	public ShingleResult(String title, Set<Shingle> shingles) {
		this.title = title;
		this.shingles = shingles;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Set<Shingle> getShingles() {
		return shingles;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setShingles(Set<Shingle> shingles) {
		this.shingles = shingles;
	}
	
}
