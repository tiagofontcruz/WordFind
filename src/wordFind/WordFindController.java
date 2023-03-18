package wordFind;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;


public class WordFindController {
	private WordFindView theView;
	private WordFindModel theModel;
	private String fileName;
	private String selection; 
	
	public WordFindController(WordFindView theView,WordFindModel theModel) {
		this.theView = theView;
		this.theModel = theModel;
		this.theView.addLoadFile(new LoadFile());
		this.theView.addSaveFile(new SaveFile());
		this.theView.help(new HelpMenu());
		this.theView.insertWord(new InsertWord());
		this.theView.removeWord(new RemoveWord());		
		this.theView.insertLetter(new InsertLetter());
		this.theView.keyListener(new KeyTextListener());
		this.theView.keyWordListener(new KeyTextWordListener());
		this.theView.addSolvePuzzleFast(new SolvePuzzleFast());
		this.theView.addSolvePuzzleSlow(new SolvePuzzleSlow());
		this.theView.listSelection(new SelectionListener());
		this.theView.setDisableButtons();
	}
	
	class SelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	valueChanged()
			//
			// Method parameters	:	ListSelectionEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method extracts a word from a text in a jList.							
			//							
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-12-06		Tiago   				ListSelectionListener method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			selection = theView.getJListElement();                                                              
			if (selection != null) {				                                                           
				theView.setTxInsertWord(selection);
			}				
		}		
	}
	
	class LoadFile implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	actionPerformed()
			//
			// Method parameters	:	ActionEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method loads a file.							
			//
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-12-06		Tiago   				ActionListener method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=				
			try {                                                                                                 				                                                           
				JFileChooser chooser = new JFileChooser(".");                                                      
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");         
				chooser.setFileFilter(filter);                                                                     
				chooser.showOpenDialog(null);                                                                      
				if (chooser.getSelectedFile() != null) {					                                       
					File f = chooser.getSelectedFile();					                                           
					fileName = f.getAbsolutePath();				                                               
					theView.resetWordList(theModel.getWordList());
					theModel.resetResultBoard();
					theModel.openFile(fileName, theModel.getBoard(), theModel.getWordList());
					theView.setGridCells(theModel.getBoard(), theModel.getBoardResult());
					theView.setWordList(theModel.getWordList());			
					theModel.populateTree();
					theView.setEnableButtons();
				}
			} catch (NumberFormatException | IOException ex) {                                                     
				theView.displayMessage("Something went wrong!");                                                   
			}
			
		}
	}
	
	class SaveFile implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	actionPerformed()
			//
			// Method parameters	:	ActionEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method saves a file.							
			//
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-12-06		Tiago   				ActionListener method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			try {                                                                                                   
				theModel.saveFile(fileName, theModel.getBoard(), theModel.getWordList());                                             //Saves the file's path
				theView.displayMessage("File saved successfully.");                                                 //Display a message
			} catch (NumberFormatException | IOException ex) {                                                      
				theView.displayMessage("Something went wrong!");                                                    //Display a message
			}                                                                                                       
		}                                                                                                           
	}
	
	class HelpMenu implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	actionPerformed()
			//
			// Method parameters	:	ActionEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method displays the software's instructions.							
			//
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-09-21		Tiago   				ActionListener method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			theView.displayMessage("- To solve the words in the board, you must load a file.\n"                        
					+ "- To switch a puzzle, you must load a file.\n"                                           
					+ "- Words can be added. Letters can be added.\n"					                    
					+ "- The user can save the puzzle in the same file opened.\n"                                                           
					+ "- The board accepts only one letter each insert.");                     
		}		
	}
	
	class KeyTextListener extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	keyTyped()
			//
			// Method parameters	:	KeyEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method verifies if user pressed a key different from 0 to 9 and consumes the character.							
			//
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-12-06		Tiago   				KeyAdapter method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			try {
				if (!String.valueOf(e.getKeyChar()).matches("[0-9]")) {                                            //Verifies key typed matches with a range
					e.consume();                                                                                   //Consumes different key typed
				}				                                                                                   
			} catch (Exception ex) {                                                                               
				theView.displayMessage("Something went wrong!");                                                   //Displays a message
			}                                                                                                      
		}
	}
	
	class KeyTextWordListener extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	keyTyped()
			//
			// Method parameters	:	KeyEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method verifies if user pressed a key different from aA to zZ and consumes the numbers and symbols.							
			//
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-12-06		Tiago   				KeyAdapter method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			try {
				if (!String.valueOf(e.getKeyChar()).matches("[a-zA-Z]")) {
                    e.consume();                                                    
                }   			                                                                                   
			} catch (Exception ex) {                                                                               
				theView.displayMessage("Something went wrong!");                                                   //Displays a message
			}                                                                                                      
		}
	}
	
	class InsertWord implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	actionPerformed()
			//
			// Method parameters	:	ActionEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method inserts a word in the list.							
			//
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-12-06		Tiago   				ActionListener method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			int position  = theView.getJListIndex();
			String word = theView.getTxInsertWord();
			if (position >= 0) {
				theView.editJWordList(word.toUpperCase(), position);
				theModel.setWordList(word.toUpperCase(), position);
				theModel.populateTree();
				theView.clearTxInsertWord();
			}else if (!word.isEmpty()) {
				theView.addJWordList(word.toUpperCase());
				theModel.setWordList(word.toUpperCase());
				theModel.populateTree();
				theView.clearTxInsertWord();
			}
		}
	}
	
	class RemoveWord implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	actionPerformed()
			//
			// Method parameters	:	ActionEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method removes a word in the list.							
			//
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-12-06		Tiago   				ActionListener method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			int position  = theView.getJListIndex();
			if (position >= 0) {
				theView.removeJWordList(position);
				theModel.removeWordList(position);
				theModel.populateTree();
				theView.clearTxInsertWord();
			}
		}
	}
	
	class InsertLetter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	actionPerformed()
			//
			// Method parameters	:	ActionEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method inserts a letter in the list.							
			//
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-12-06		Tiago   				ActionListener method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			String row = theView.getTxInsertLetterRow();
			String col = theView.getTxInsertLetterCol();
			if (!row.isEmpty() && !col.isEmpty()) {
				try {
					int rowPos = Integer.parseInt(row);
					int colPos = Integer.parseInt(col);	
					if (rowPos < WordFindModel.ROW_ARRAY && rowPos >= 0 && colPos < WordFindModel.COL_ARRAY && colPos >= 0) {
						String letter = JOptionPane.showInputDialog("Insert a letter: ");
						if (letter.matches("[a-zA-Z]") && letter.length() == 1) {
							letter = letter.toUpperCase();						
							theModel.setBoard(letter.charAt(0), rowPos, colPos);
							theView.setGridCells(theModel.getBoard(), theModel.getBoardResult());
							theView.setWordList(theModel.getWordList());			
							theModel.populateTree();
						}else {
							theView.displayMessage("You can only type ONE letter!");
						}
					}
				} catch (Exception e2) {
					theView.displayMessage("Something went wrong!");				
				}				
			}else {
				theView.displayMessage("Only numbers between 0 and 24 for columns"
						+ "\nand between 0 and 19 for rows.");				
			}
		}
	}
	
	class SolvePuzzleFast implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	actionPerformed()
			//
			// Method parameters	:	ActionEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method resets all the values and call the method to solve faster the puzzle,							
			//							and updates the list with * in case the word is founded.
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-12-06		Tiago   				ActionListener method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			theModel.resetResultBoard();
			theModel.getHighLightPos().clear();
			theModel.dynamicSearch();
			theModel.populateResultBoard();
			theView.setGridCells(theModel.getBoard(), theModel.getBoardResult());
			for (int i = 0; i < theModel.getWordList().size(); i++) {
				if (theModel.getWordListFound().contains(theModel.getWordList().get(i))) {
					theView.showJWordListFounded(theModel.getWordList().get(i), i);					
				}
			}
		}
	}
	
	class SolvePuzzleSlow implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			// Method				:	actionPerformed()
			//
			// Method parameters	:	ActionEvent e
			//
			// Method return		:	void
			//
			// Synopsis				:   This method resets all the values and call the method to solve slow the puzzle,							
			//							and updates the list with * in case the word is founded.
			// Modifications		:
			//							Date			Developer				Notes
			//							----			---------				-----
			//							2022-12-06		Tiago   				ActionListener method type.
			//
			// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
			theModel.resetResultBoard();
			theModel.getHighLightPos().clear();
			theModel.bruteForceSearch();
			theModel.populateResultBoard();
			theView.setGridCells(theModel.getBoard(), theModel.getBoardResult());
			for (int i = 0; i < theModel.getWordList().size(); i++) {
				if (theModel.getWordListFound().contains(theModel.getWordList().get(i))) {
					theView.showJWordListFounded(theModel.getWordList().get(i), i);					
				}
			}
		}
	}
}
