package ie.gmit.sw;

import ie.gmit.sw.documents.Document;
import ie.gmit.sw.minhash.MinHashResult;
import ie.gmit.sw.shingles.ShingleResult;

public class JaccardWorker implements Runnable {
	
	private Document doc;
	private String taskNumber;
	private IJaccardFacade facade;
	
	public JaccardWorker(Request request) {
		this.doc = request.getDoc();
		this.taskNumber = request.getTaskNumber();
		this.facade = new JaccardFacade();
	}
	
	public void run() {
		
		/*
		 * 1. Create a Jaccard Facade Object
		 * 2. Compose Jaccard Workers with Facade
		 * 3. Workers use the Facade to process documents
		 * 4. Add results to an Out Queue
		 */
		
		ShingleResult shingleResult = facade.shinglizeDocument(doc);

		MinHashResult minHashResult = facade.computeMinHash(shingleResult);

		facade.storeHashedResult(minHashResult);

		double averageJaccardIndex = facade.averageJaccardIndex(minHashResult);
		
		Response response = new Response(taskNumber, averageJaccardIndex);
		
	}

}
