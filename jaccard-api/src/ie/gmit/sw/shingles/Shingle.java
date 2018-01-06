package ie.gmit.sw.shingles;

public class Shingle {
	private int hashCode;
	private String text;
	
	public Shingle(String text) {
		this.text = text;
		this.hashCode = text.hashCode();
		//System.out.println("[Created Shingle] " + text);
	}
	
	public String toString() {
		return text;
	}
	
	public int hashCode() {
		return hashCode;
	}
}
