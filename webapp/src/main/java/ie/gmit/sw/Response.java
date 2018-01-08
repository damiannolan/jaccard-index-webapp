package ie.gmit.sw;

import java.io.Serializable;

public class Response implements Serializable {
	private static final long serialVersionUID = 42L;
	
	private String taskNumber;
	private double averageJaccardIndex;
	
	public Response(String taskNumber, double averageJaccardIndex) {
		this.taskNumber = taskNumber;
		this.averageJaccardIndex = averageJaccardIndex;
	}
	
	public double getAverageJaccardIndex() {
		return averageJaccardIndex;
	}
	
	public String getTaskNumber() {
		return taskNumber;
	}
	
	public void setAverageJaccardIndex(double averageJaccardIndex) {
		this.averageJaccardIndex = averageJaccardIndex;
	}
	
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	
	@Override
	public String toString() {
		return taskNumber + ": Average Jaccard Index = " + averageJaccardIndex;
	}
}
