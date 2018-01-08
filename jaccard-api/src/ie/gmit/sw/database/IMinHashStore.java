package ie.gmit.sw.database;

import java.util.List;

import ie.gmit.sw.minhash.MinHashResult;

public interface IMinHashStore {
	
	public void addMinHashedDocument(MinHashResult minhashDocument);
	
	public List<MinHashResult> getMinHashedDocuments();
	
}
