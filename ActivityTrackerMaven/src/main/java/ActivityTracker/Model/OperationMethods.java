package ActivityTracker.Model;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public interface OperationMethods {
	
	public String DataRead();
	
	public String DataDisp();
	
	public void ExcelUpdate(ActivityTrackerModel Aobj) throws IOException,InvalidFormatException;
	
	

}
