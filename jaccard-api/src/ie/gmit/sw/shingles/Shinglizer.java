package ie.gmit.sw.shingles;

import java.util.Set;

import ie.gmit.sw.documents.Document;

public interface Shinglizer {
	
	public Set<Shingle> shinglizeDocument(Document doc);
}
