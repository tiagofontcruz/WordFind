package wordFind;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class WordFindFile {

	public void openFile(String fileName, char[][] board, ArrayList<String> wordList) throws IOException {	
    	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	openFile()
		//
		// Method parameters	:	String fileName, char[][] board, ArrayList<String> wordList
		//
		// Method return		:	void
		//
		// Synopsis				:   This method opens a file an loads the text content into an object.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				Second method called.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		FileReader reader = new FileReader(fileName);                                                 
		BufferedReader br = new BufferedReader(reader);
		int i, j;
		String line = null;
		for (i = 0; i < WordFindModel.ROW_ARRAY; i++) {                                              
				line = br.readLine();
				for (j = 0; j < WordFindModel.COL_ARRAY; j++) {				
					board[i][j] = line.charAt(j);					
				}			
		}		
		for (line = br.readLine(); line != null; line = br.readLine()) {                                           
			wordList.add(line);
		}
		br.close();                                                                                                                                             
	}
	                                                                                                   
	public void saveFile(String fileName, char[][] board, ArrayList<String> jList) throws IOException {
    	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	saveFile()
		//
		// Method parameters	:	String fileName, char[][] board, ArrayList<String> jList
		//
		// Method return		:	void
		//
		// Synopsis				:   This method saves a file opened.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				Second method called.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		FileWriter file = new FileWriter(fileName);                                                    		//Declares and instantiates to writes the file's path
		int i, j;
		for (i = 0; i < WordFindModel.ROW_ARRAY; i++) {
			for (j = 0; j < WordFindModel.COL_ARRAY; j++) {
				file.write(board[i][j]);                                                                    //Save the letters into a file
			}
			file.write("\n");
		}
		
		for (Iterator<String> iterator = jList.iterator(); iterator.hasNext();) {							//Iterates the list to get all words
			String string = (String) iterator.next();
			file.write(string + "\n");                                                                      //Save the words into a file
		}
		file.close();	                                                                               		//Closes the file
	}
	
	
}
