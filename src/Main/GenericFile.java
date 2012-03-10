package Main;

import java.io.File;


public class GenericFile extends File{
	private String type;
	private String name;
	private String location;
	private long size;
	
        public GenericFile(String path){
            super(path);
            this.type=path.substring(path.lastIndexOf("."));
            this.type=path.substring(path.lastIndexOf("/"),path.lastIndexOf("."));
            this.location=path;
            this.size=length();
        }
	
	public GenericFile(String type, String name, String location,long size){
		super(location);
		this.type=type;
		this.name=name;
		this.location=location;
		this.size=size;
	}


	public String getType() {
		return type;
	}


	public String getName() {
		return name;
	}


	public String getLocation() {
		return location;
	}


	public float getSize() {
		return size;
	}
	
	public String toString(){
		String str="";
		return name;
	}


	public int compareTo(GenericFile arg0) {
		GenericFile gf= (GenericFile)arg0;
		return this.getName().compareTo(gf.getName());
	}


	
	
	
}
