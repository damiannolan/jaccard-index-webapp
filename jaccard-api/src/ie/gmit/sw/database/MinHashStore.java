package ie.gmit.sw.database;

import java.io.IOException;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ta.TransparentActivationSupport;
import com.db4o.ta.TransparentPersistenceSupport;

import xtea_db4o.XTEA;
import xtea_db4o.XTeaEncryptionStorage;

public class MinHashStore {
	
	private ObjectContainer db = null;
	
	public MinHashStore() throws IOException {
		
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
}
