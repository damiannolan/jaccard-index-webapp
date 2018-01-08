package ie.gmit.sw;

import ie.gmit.sw.documents.Document;

public class JaccardWorker implements Runnable {
	
	private Document doc;
	private IJaccardFacade facade;
	
	public JaccardWorker(Document doc) {
		this.doc = doc;
		this.facade = new JaccardFacade();
	}
	
	public void run() {
		
		/*
		 * 1. Create a Jaccard Facade Object
		 * 2. Compose Jaccard Workers with Facade
		 * 3. Workers use the Facade to process documents
		 * 4. Add results to an Out Queue
		 */
		
	}

}
