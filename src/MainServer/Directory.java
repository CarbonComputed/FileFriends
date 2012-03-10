package MainServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Directory {
	private ArrayList<GenericFile> directory;
	private String location;
	
	public Directory(String location){
		this.location=location;
		directory=new ArrayList<GenericFile>();
		directory=readDirectory(location);
		if(directory==null){
			System.out.println("Bad directory");
			return;
		}
	}
	public ArrayList<GenericFile> getFiles(){
		return directory;
	}
	
	public void sort(){
		Collections.sort(directory);
	}
	private ArrayList<GenericFile> readDirectory(String location){
		Stack<File> directories = new Stack<File>();
		File dir = new File(location);
		if(!validateDirectory(dir)){
			return null;
		}
		directories.push(dir);
		while(!directories.isEmpty()){
			File files[] = directories.pop().listFiles();
			for(File file:files){
				if(file.isDirectory()){
				
					directories.push(file);
				
			}
				else{
					String name=file.getName();
					String path = file.getPath();
					String type = "";
					if(name.contains(".")){
						type = name.substring(name.lastIndexOf("."));
					}
					
					long size=file.length();
					directory.add(new GenericFile(type,name,location,size));
				
				}
			}
		}
		return directory;
	}
	
	private boolean validateDirectory (File aDirectory)  {
		    if (aDirectory == null) {
		    	
		     // throw new IllegalArgumentException("Directory should not be null.");
		      return false;
		    }
		    if (!aDirectory.exists()) {
		     // throw new FileNotFoundException("Directory does not exist: " + aDirectory);
		     return false;
		    }
		    if (!aDirectory.isDirectory()) {
		      //throw new IllegalArgumentException("Is not a directory: " + aDirectory);
		     return false;
		    }
		    if (!aDirectory.canRead()) {
		      //throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
		     return false;
		    }
		    return true;
		  }
	
	public String toString(){
		
		return location;
	}
	
	public static void main(String args[]){
		Directory d = new Directory("/home/kevin/Music/laura");
		//System.out.println(d);
		d.sort();
		System.out.println(d);
	}
	
	
	
}
