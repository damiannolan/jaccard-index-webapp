package ie.gmit.sw.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ie.gmit.sw.documents.Document;
import ie.gmit.sw.documents.TextDocument;
import ie.gmit.sw.minhash.MinHashResult;
import ie.gmit.sw.shingles.ShingleResult;
import ie.gmit.sw.shingles.Shinglizer;
import ie.gmit.sw.shingles.TextShinglizer;

public class MockMinHashStore implements IMinHashStore {

	private List<MinHashResult> minHashedResults = new ArrayList<MinHashResult>();

	public MockMinHashStore() throws IOException {
		init();
	}

	private void init() throws IOException {
		FileReader fr = new FileReader("test2");
		BufferedReader br = new BufferedReader(fr);

		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		Document doc = new TextDocument("test", sb.toString());

		Shinglizer shinglizer = new TextShinglizer(3);

		ShingleResult shingleResult = shinglizer.shinglizeDocument(doc);

	}

	@Override
	public void addMinHashedDocument(MinHashResult minhashDocument) {

	}

	@Override
	public List<MinHashResult> getMinHashedDocuments() {
		// TODO Auto-generated method stub
		return null;
	}

}
