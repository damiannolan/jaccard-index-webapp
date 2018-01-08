package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import ie.gmit.sw.database.MinHashStore;
import ie.gmit.sw.documents.Document;
import ie.gmit.sw.documents.TextDocument;
import ie.gmit.sw.jaccard.Indexer;
import ie.gmit.sw.jaccard.JaccardIndexer;
import ie.gmit.sw.minhash.MinHash;
import ie.gmit.sw.minhash.MinHashResult;
import ie.gmit.sw.shingles.ShingleResult;
import ie.gmit.sw.shingles.Shinglizer;
import ie.gmit.sw.shingles.TextShinglizer;

public class TestRunner {
	public static final String FILENAME = "test.txt";
	public static final String FILENAME2 = "test2.txt";
	
	public static void main(String[] args) throws IOException {
		
		/*
		 * Read First Document
		 * ===========================================================================================
		 */
		FileReader fr = new FileReader(FILENAME);
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		Document doc =  new TextDocument("test", sb.toString());
		
		/*
		 * Read Second Document
		 * ============================================================================================
		 */
		
		fr = new FileReader(FILENAME2);
		br = new BufferedReader(fr);
		
		
		sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		Document doc2 =  new TextDocument("test2", sb.toString());
		
		/*
		 * Shinglize Both Documents
		 * ============================================================================================
		 */
		Shinglizer shinglizer = new TextShinglizer(3);
		
		ShingleResult shingleResult = shinglizer.shinglizeDocument(doc);
		//System.out.println(Arrays.toString(shingleResult.getShingles().toArray()));
		
		ShingleResult shingles2 = shinglizer.shinglizeDocument(doc2);
		//System.out.println(Arrays.toString(shingles2.getShingles().toArray()));
		
		MinHash hashGen = new MinHash();
		
		MinHashResult hashResult = hashGen.process(shingleResult);	
		MinHashResult hashResult2 = hashGen.process(shingles2);
		
		Indexer indexer = new JaccardIndexer();
		
		MinHashStore datastore = MinHashStore.getInstance();
		datastore.addMinHashedDocument(hashResult);
		
		List<MinHashResult> storedResults = datastore.getMinHashedDocuments();
		double total = 0;
		for(MinHashResult r : storedResults) {
			double jaccard = indexer.calculateIndex(hashResult2, r);
			System.out.println("Jaccard Index: " + jaccard);
			total += jaccard;
		}
		
		System.out.println(total / storedResults.size());
	}
}
