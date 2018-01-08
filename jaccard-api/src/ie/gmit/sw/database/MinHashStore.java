package ie.gmit.sw.database;

import java.io.IOException;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ta.TransparentActivationSupport;
import com.db4o.ta.TransparentPersistenceSupport;

import ie.gmit.sw.minhash.MinHashResult;
import xtea_db4o.XTEA;
import xtea_db4o.XTeaEncryptionStorage;

public class MinHashStore {
	private static MinHashStore instance;
	
	private ObjectContainer db = null;

	private MinHashStore() throws IOException {

		// Configurations
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().add(new TransparentActivationSupport());
		config.common().add(new TransparentPersistenceSupport());
		config.common().updateDepth(7);

		// Use the XTea lib for encryption.
		config.file().storage(new XTeaEncryptionStorage("password", XTEA.ITERATIONS64));

		// Open a local database
		this.db = Db4oEmbedded.openFile(config, "minhash.data");
	}
	
	public static MinHashStore getInstance() {
		if(instance == null) {
			try {
				instance = new MinHashStore();
			} catch (IOException e) {
				System.out.println("Could not instantiate new MinHashStore");
				e.printStackTrace();
			}
		}
		return instance;
	}

	public void addMinHashedDocument(MinHashResult minhashDocument) {
		// Query by example to check if the document already existed
		ObjectSet<MinHashResult> result = db.queryByExample(new MinHashResult(minhashDocument.getTitle(), minhashDocument.getHashes()));

		if (result.hasNext()) {
			System.out.println("[WARNING] Document already exists within DB4O");
		} else {
			db.store(minhashDocument);
			db.commit();
		}
	}

	public ObjectSet<MinHashResult> getMinHashedDocuments() {
		ObjectSet<MinHashResult> results = db.query(MinHashResult.class);

		return results;
	}

}
