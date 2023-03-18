package wordFind;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

class WordFindModel extends WordFindFile {

	public final static int COL_ARRAY = 25;															
	public final static int ROW_ARRAY = 20;                                                         
                                                                                                    
	private static long start;                                                                      
	private static long end;                                                                        
	private static long execution;                                                                  
	                                                                                                
	public static int[] rowDirections = {0, 1, 1, 1, 0, -1, -1, -1};                                //Array with directions that will add or subtract from the positions.
	public static int[] colDirections = {1, 1, 0, -1, -1, -1, 0, 1};                                //Array with directions that will add or subtract from the positions.
	private static int[] insertCoordenates;	                                                        //Array with coordinates to run over the word for highlight purposes. 
	                                                                                                
	private char board[][];                                                                         
	private boolean resultBoard[][];	                                                            //Where is "true" the words will be painted.
                                                                                                    
	private WordFindTrie trie;                                                                      //Declares a tree object of a retrieval data structure class.  
	private ArrayList<String> wordList;                                                             //Declares an ArrayList for the words.
	private ArrayList<String> wordListFound;                                                       	//Declares an ArrayList for the founded words.
	private static ArrayList<int[]> highLightPos;                                                   //Declares an ArrayList for the highlight positions.
                                                                                                    
	//CONSTRUCTOR METHOD *******************************
	public WordFindModel() {                                                                        
		trie = new WordFindTrie();                                                                  
		board = new char[ROW_ARRAY][COL_ARRAY];                                                     
		resultBoard = new boolean[ROW_ARRAY][COL_ARRAY];                                            
		wordList = new ArrayList<String>();                                                         
		wordListFound = new ArrayList<String>();                                                   	
		highLightPos = new ArrayList<>();                                                           
	}                                                                                               
	                                                                                                
	public void populateTree() {                                                                    
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	populateTree()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method populates a tree.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		trie = new WordFindTrie();                                                                  //Instantiates the trie (tree) object.
		for (Iterator<String> iterator = wordList.iterator(); iterator.hasNext();) {                //Iterates through the word list.
			String word = (String) iterator.next();                                                 
			trie.insert(word);			                                                            //Inserts the words into the trie (tree) object.
		}		                                                                                    
	}                                                                                               
	                                                                                                
	public void populateResultBoard() {                                                             
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	populateResultBoard()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method populates a multidimensional array to use the "true" value for painting the board.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=		
		for (Iterator<int[]> iterator = highLightPos.iterator(); iterator.hasNext();) {             //Iterates through the highlight position list.
			int[] pos = (int[]) iterator.next();                                                    
			resultBoard[pos[0]][pos[1]] = true;                                                     //Inserts the value "true" into those positions
		}                                                                                           //that the word was found.
	}                                                                                               
                                                                                                    
	public void resetResultBoard() {                                                                
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	resetResultBoard()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method sets to false a multidimensional array to use the "true" value for painting the board.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		resultBoard = new boolean[ROW_ARRAY][COL_ARRAY];                                            //Instantiates a multidimensional array.
		int row, col;                                                                               
		for (row = 0; row < WordFindModel.ROW_ARRAY; row++) {                                       //Runs the array
			for (col = 0; col < WordFindModel.COL_ARRAY; col++) {                                   
				resultBoard[row][col] = false;                                                      //Inserts "false" to all positions.
			}                                                                                       
		}                                                                                           
	}                                                                                               
                                                                                                    
	public void bruteForceSearch() {		                                                        
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	bruteForceSearch()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method calls the highlightWordsSequencial method to set the highlight words.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		wordListFound = new ArrayList<String>();													//Instantiates an arrayList.
		start = System.nanoTime();                                                                  //
		for (Iterator<String> iterator = wordList.iterator(); iterator.hasNext();) {                //Iterates through the word list.
			String word = (String) iterator.next();                                                 //
			highlightWordsSequencial(board, word);			                                        //Calls the method to set the highlight words.
		}                                                                                           //
		System.out.println();                                                                       //
		end = System.nanoTime();                                                                    //
		execution = end - start;		                                                            //
		JOptionPane.showMessageDialog(null, "It took " + execution + " nanoseconds to solve.");     //
	}                                                                                               //
	                                                                                                //
	public void highlightWordsSequencial(char[][] board, String word){                              //
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	highlightWordsSequencial()
		//
		// Method parameters	:	char[][] board, String word
		//
		// Method return		:	void
		//
		// Synopsis				:   This method calls the solveSequencial method to solve the puzzle.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int direction, row, col;                                                                    //
		for(direction = 0; direction < 8; direction ++){                                       	 	//Goes through all directions. 
			                                                                                        //
	        for(row = 0; row < board.length; row++){                                            	//Runs the board.
	            for(col = 0; col < board[0].length; col++){                                     	//
	            	String dirInfo = solveSequencial(word, board, row, col, direction);             //Calls the method to solve
	                if(dirInfo != ""){                                                              //If a direction get found, means the word was found.
	                	wordListFound.add(word);													//
	                	insertCoordenates = new int[]{row, col};                                    //
	                	highLightPos.add(insertCoordenates);                                        //
	                	wordPainter(word, row, col, dirInfo);                                       //
	                }                                                                               //
	            }                                                                                   //
	        }                                                                                       //
		}                                                                                           //
    }                                                                                               //
                                                                                                    //
    public String solveSequencial(String word, char[][] board, int row, int col, int direction){	//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	solveSequencial()
		//
		// Method parameters	:	String word, char[][] board, int row, int col, int direction
		//
		// Method return		:	void
		//
		// Synopsis				:   This method solves the puzzle running each word in one direction at time.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    	String dirInfo = "";                                                                        //
        if(board[row][col] != word.charAt(0)) {                                                     //Checks if there is a word the matches with the letter in the board
        	return dirInfo;                                                                         //
        }                                                                                           //
        int wordSize = word.length();                                                               //
        boolean flag;                                                                               //
                                                                                                    //
    	flag = true;                                                                                //
    	int rowDir = row + rowDirections[direction];                                                //
        int colDir = col+ colDirections[direction];                                                 //
        int index;                                                                                  //
        for(index = 1; index < wordSize && flag; index++){                                          //Checks each word's letter if it keeps matching.
            if(isDirectionValid(board, rowDir, colDir)                                              //
            		&& board[rowDir][colDir] == word.charAt(index)) {                               //
            	rowDir += rowDirections[direction];                                                 //
            	colDir += colDirections[direction];                                                 //
            }else {                                                                                 //
            	flag = false;                                                                       //
            	index -= 1;                                                                         //
            }                                                                                       //
        }                                                                                           //
        if(index == wordSize){                                                                      //
            dirInfo += coordinateConverter(direction) + " ";                                        //
        }                                                                                           //
        return dirInfo.trim();                                                                      //
    }                                                                                               //
                                                                                                    //
	                                                                                                //
    public void dynamicSearch() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	dynamicSearch()
		//
		// Method parameters	:	none
		//
		// Method return		:	void
		//
		// Synopsis				:   This method calls the highlightWordsDynamic method to set the highlight words.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    	wordListFound = new ArrayList<String>();													//
		start = System.nanoTime();                                                                  //
		String word = "";                                                                           //
		highlightWordsDynamic(board, word);                                                         //
		end = System.nanoTime();                                                                    //
		execution = end - start;		                                                            //
		JOptionPane.showMessageDialog(null, "It took " + execution + " nanoseconds to solve.");     //
	}                                                                                               //
                                                                                                    //
    public void highlightWordsDynamic(char[][] board, String word){                                 //
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	highlightWordsDynamic()
		//
		// Method parameters	:	char[][] board, String word
		//
		// Method return		:	void
		//
		// Synopsis				:   This method calls the solveDynamic method to solve the puzzle.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    	int row, col;																				//
        for(row = 0; row < board.length; row++){                                                	//
            for(col = 0; col < board[0].length; col++){                                         	//
            	WordFindNode node = null;                                                           //
            	word = board[row][col] + "";                                                        //
            	node = trie.search(word);                                                           //
            	if (node != null) {                                                                 //
            		trie.addWords(node, word);                                                      //
            		for (Iterator<String> iterator = trie.getList().iterator(); iterator.hasNext();) {
						String wordList = (String) iterator.next();									//
		            	String dirInfo = solveDynamic(wordList, board, row, col);                   //Calls the method to solve the puzzle.
		                if(dirInfo != ""){                                                          //If a direction get found, means the word was found.
		                	wordListFound.add(wordList);											//
		                	insertCoordenates = new int[]{row, col};                                //
		                	highLightPos.add(insertCoordenates);                                    //
		                    wordPainter(wordList, row, col, dirInfo);                               //
		                    trie.delete(wordList);                                                  //
		                }
	                }
                }
            }
        }
    }
    
    public String solveDynamic(String word, char[][] board, int row, int col){                      
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	solveDynamic()
		//
		// Method parameters	:	String word, char[][] board, int row, int col
		//
		// Method return		:	String
		//
		// Synopsis				:   This method solves the puzzle running each letter in all direction at the "same time".							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    	String dirInfo = "";                                                                        //
        if(board[row][col] != word.charAt(0)) {                                                     //Checks if there is a word the matches with the letter in the board
        	return dirInfo;                                                                         //
        }                                                                                           //
                                                                                                    //
        int wordSize = word.length();                                                               //
        boolean flag;                                                                               //
        int direction, rowDir, colDir;                                                              //
        for(direction = 0; direction < 8; direction ++){                                        	//Goes through all directions
        	flag = true;                                                                            //
        	rowDir = row + rowDirections[direction];                                            	//
            colDir = col+ colDirections[direction];                                             	//
                                                                                                    //
            int index;                                                                              //
            for(index = 1; index < wordSize && flag; index++){                                      //Checks each word's letter if it keeps matching.
                if(isDirectionValid(board, rowDir, colDir)                                          //
                		&& board[rowDir][colDir] == word.charAt(index)) {                           //
                	rowDir += rowDirections[direction];                                             //
                	colDir += colDirections[direction];                                             //
                }else {                                                                             //
                	flag = false;                                                                   //
                	index -= 1;                                                                     //
                }                                                                                   //
            }                                                                                       //
            if(index == wordSize){                                                                  //
                dirInfo += coordinateConverter(direction) + " ";                                    //
            }                                                                                       //
        }                                                                                           //
        return dirInfo.trim();                                                                      //
    }  
    
    public boolean isDirectionValid(char[][] board, int rowDir, int colDir){
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	isDirectionValid()
		//
		// Method parameters	:	char[][] board, int rowDir, int colDir
		//
		// Method return		:	boolean
		//
		// Synopsis				:   This method checks the puzzle's boundaries.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        if(rowDir >= board.length || rowDir < 0 || colDir >= board[0].length || colDir < 0){
            return false;
        }
        return true;
    }
	
	public String coordinateConverter(int direction){
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	coordinateConverter()
		//
		// Method parameters	:	int direction
		//
		// Method return		:	String
		//
		// Synopsis				:   This method checks the directions.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        switch(direction){
            case 0:
                return "H_FORWARD";
            case 1:
                return "DOWN_FORWARD";
            case 2:
                return "V_FORWARD";
            case 3:
                return  "DOWN_REVERSE";
            case 4:
                return  "H_REVERSE";
            case 5:
                return "UP_REVERSE";
            case 6:
            	return "V_REVERSE";
            case 7 :
            	return "UP_FORWARD";
            default:
                return "DEAD_END";
        }
    }
	
	public void wordPainter(String word, int row, int col, String direction) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	wordPainter()
		//
		// Method parameters	:	String word, int row, int col, String direction
		//
		// Method return		:	void
		//
		// Synopsis				:   This method checks the directions and insert the coordinates to paint the words when populates the grid (Graphic side).							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int i;
		for(i = 0; i < word.length(); i++) {			
			switch (direction) {
			case "V_REVERSE":
				insertCoordenates = new int[]{row - i, col}; 
				break;
			case "UP_FORWARD":	            	
				insertCoordenates = new int[]{row - i, col + i}; 
				break;				
			case "H_FORWARD":
				insertCoordenates = new int[]{row, col + i}; 
				break;
			case "DOWN_FORWARD":
				insertCoordenates = new int[]{row + i, col + i}; 
				break;
			case "V_FORWARD":
				insertCoordenates = new int[]{row + i, col}; 
				break;
			case "DOWN_REVERSE":		
				insertCoordenates = new int[]{row + i, col - i}; 
				break;
			case "H_REVERSE":
				insertCoordenates = new int[]{row, col - i}; 
				break;
			case "UP_REVERSE":	
				insertCoordenates = new int[]{row - i, col - i}; 
				break;
			default:
				break;
			}
			highLightPos.add(insertCoordenates); 
		}
	}
	
	//GETTER AND SETTERS *******************************
	public char[][] getBoard() {
		return board;           
	}

	public char getBoard(int row, int col) {
		return board[row][col];
	}
	
	public void setBoard(char letter, int row, int col) {
		board[row][col] = letter;
	}

	public boolean[][] getBoardResult() {
		return resultBoard;           
	}

	public boolean getBoardResult(int row, int col) {
		return resultBoard[row][col];
	}

	public ArrayList<String> getWordList() {
		return wordList;
	}

	public void setWordList(ArrayList<String> wordList) {
		this.wordList = wordList;
	}
	
	public void setWordList(String word) {
		this.wordList.add(word);
	}
	
	public void setWordList(String word, int position) {
		this.wordList.set(position, word);
	}
	
	public void removeWordList(int position) {
		this.wordList.remove(position);
	}

	public ArrayList<int[]> getHighLightPos() {
		return highLightPos;
	}

	public void clearHighLightPos() {
		highLightPos.clear();
	}
	
	public ArrayList<String> getWordListFound() {
		return wordListFound;
	}
	public void setWordListFound(ArrayList<String> wordListFound) {
		this.wordListFound = wordListFound;
	}
}
