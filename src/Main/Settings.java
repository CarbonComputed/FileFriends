package Main;

import java.io.Serializable;
import java.util.ArrayList;

public class Settings implements Serializable {
	private FileList shareLocations;
	private String downLocation=null;

	public Settings(){
		this.shareLocations=new FileList();
		this.downLocation="";
	}
	
	public FileList getShare(){
		return shareLocations;
	}
	
	public void setShare(ArrayList<GenericFile> shareLocations){
                if(shareLocations!=null){
		shareLocations.clear();
		this.shareLocations.getFiles().addAll(shareLocations);
                }
	}

	public String getDownLoadLocation() {
		return downLocation;
	}

	public void setDownLoadLocation(String downLocation) {
		this.downLocation = downLocation;
	}
	
	
	
	
}
