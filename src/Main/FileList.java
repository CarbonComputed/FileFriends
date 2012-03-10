package Main;

import java.io.File;
import java.util.ArrayList;

public class FileList {
	private ArrayList<Directory> filelist;
	private ArrayList<GenericFile> files;
	public static ArrayList<GenericFile> allfiles;
	
	public FileList(){
		filelist=new ArrayList<Directory>();
		files=  new ArrayList<GenericFile>();
		allfiles = new ArrayList<GenericFile>();
		allfiles.addAll(files);
		for(Directory d:filelist){
			allfiles.addAll(d.getFiles());
		}
	}
	
        public int getSize(){
            return allfiles.size();
        }
	public ArrayList<GenericFile> getFiles(){
		return allfiles;
	}
	
	

	public void addFiles(ArrayList<GenericFile> guifiles){
		allfiles.addAll(guifiles);
		
	}
	
	public void addFile(GenericFile file){
		allfiles.add(file);
		
	}

	public void removeDirectory(String location){
		
	}
	

	public static GenericFile search(String filename){
		for(GenericFile file:allfiles){
			if(filename.equals(file.getName())){
				return file;
			}
		}
		return null;
	}
	
	public String toString(){
		String str="";
		for(GenericFile file:allfiles){
			str+=file.toString();
		}
		return str;
	}
	
}
