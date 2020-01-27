package CMMNElementsketchRecognitionSystem;

import java.io.File;
import java.util.ArrayList;

/**Contains some methods to list files and folders from a directory
 * @author SaraAmirsardari
 */

public class ReadFolders{

	/**
	 * get the path of main directory
	 * @param f main directory path to be listed
	 */
	
	public void readFile(File f){
		System.out.println(f.getPath());
	}

	/**
	 * List all files from a directory and its sub directories
	 * @param f sub directory paths to be listed
	 * @return pathList of all directory and its sub directories
	 */
	public ArrayList<String> readDir(File f){
		
		File subdir[]=f.listFiles();
		ArrayList<String> pathsList= new ArrayList<>();

		//verify the sub directory is file or is directory
		for(File f_arr:subdir){

			if(f_arr.isFile()){
				
				//if the sub directory is file so read the file path
				pathsList.add(f_arr.getPath());
				this.readFile(f_arr);
			}

			if (f_arr.isDirectory()){
				
				//if the sub directory is a directory, so list all files path inside the directory
				ArrayList<String> dirFiles = this.readDir(f_arr);
				pathsList.addAll(dirFiles);
			}
		}
		return pathsList ;
	}

}
