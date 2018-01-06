package ie.gmit.sw.documents;

public class TextDocument implements Document {
	
	private String title;
	private String text;
	private int id;
	
	public TextDocument(String title, String s) {
		this.title = title;
		this.text = s;
		this.id = title.hashCode(); // hashcode() the title
	}
	
	public String title() {
		return title;
	}
	
	public String text() {
		return text;
	}

	public int id() {
		return id;
	}

	@Override
	public String toString() {
		return "TextDocument [title=" + title + ", id=" + id + "]";
	}

}
