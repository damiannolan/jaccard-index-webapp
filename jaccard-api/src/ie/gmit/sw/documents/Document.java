package ie.gmit.sw.documents;

import java.io.Serializable;

public interface Document extends Serializable {
	public String title();
	
	public String text();
	
	public int id();
	
}
