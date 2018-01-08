package ie.gmit.sw.shingles;

import ie.gmit.sw.documents.Document;

public interface Shinglizer {
	
	public ShingleResult shinglizeDocument(Document doc);
}
