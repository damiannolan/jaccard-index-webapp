package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.Part;

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
	public Document newDocument(String title, Part part) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream()));
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		
		return new TextDocument(title, sb.toString());
	}
}
