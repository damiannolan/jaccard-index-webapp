package ie.gmit.sw;

import java.io.Serializable;

import ie.gmit.sw.documents.Document;

public class Request implements Serializable {
	private static final long serialVersionUID = 42L;
	
	private Document doc;
	private String taskNumber;
	
	public Request(Document doc, String taskNumber) {
		this.doc = doc;
		this.taskNumber = taskNumber;	
	}
	
	public Document getDoc() {
		return doc;
	}
	
	public String getTaskNumber() {
		return taskNumber;
	}
	
	public void setDoc(Document doc) {
		this.doc = doc;
	}
	
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	
	@Override
	public String toString() {
		return taskNumber + " " + doc.toString();
	}
}
