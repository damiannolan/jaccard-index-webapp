package ie.gmit.sw.documents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DocumentFactory {
	
	// Lazy singleton instance
	private static DocumentFactory instance;
	
	// Private Constructor for encapsulation
	private DocumentFactory() {}
	
	// Static getInstance() singleton handler method
	public static DocumentFactory getInstance() {
		if(instance == null) {
			instance = new DocumentFactory();
		} 	
		return instance;
	}
	
	// Document Factory method
	public Document newDocument(String title, InputStream stream) throws IOException {
		//BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream()));
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		return new TextDocument(title, sb.toString());
	}
}
