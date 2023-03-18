package wordFind;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class WordFindView extends JFrame{

	private static final long serialVersionUID = 1L;												
	                                                                                                  
	private static JPanel contentPaneGrid;                                                            
	private static JPanel contentPaneList;                                                            
	private static JTextArea gridCells;                                                               
	private static JTextField txInsertWord;                                                           
	private static JTextField txInsertLetterRow;                                                      
	private static JTextField txInsertLetterCol;                                                      
	private static JLabel lblRow;                                                                     
	private static JLabel lblCol;                                                                     
	private static JButton btnInsertLetter;                                                           
	private static JButton btnInsertWord;                                                             
	private static JButton btnRemoveWord;                                                             
	private static JButton btnSolvePuzzleFast;                                                        
	private static JButton btnSolvePuzzleSlow;                                                        
	private Highlighter hl;                                                                           
	                                                                                                  
	private static JMenuBar menuBar;                                                                  
	private static JMenu fileMenu;                                                                    
	private static JMenu helpMenu;                                                                    
	                                                                                                  
	private static JMenuItem loadPuzzle;                                                              
	private static JMenuItem savePuzzle;                                                              
	private static JMenuItem helpPuzzle;                                                              
	                                                                                                  
	private static DefaultListModel<String> modelList;                                                
	private static JList<String> wordList;                                                            
	private static JScrollPane scrollWordList;                                                        
	
	Random rand = new Random();
	//INSTANTIATION AND DEFINITION OF FORM COMPONENTS INTO THE CONSTRUCTOR ******************
	public WordFindView() {
		
		this.setTitle("Word Finder");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(400, 100, 1166, 768);	
		
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("Puzzle");
		helpMenu = new JMenu("Help");
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);	
		
		loadPuzzle = new JMenuItem("Load Puzzle");
		savePuzzle = new JMenuItem("Save Puzzle");
		helpPuzzle = new JMenuItem("How it works");
				
		fileMenu.add(loadPuzzle);
		fileMenu.add(savePuzzle);
		helpMenu.add(helpPuzzle);
		
		setJMenuBar(menuBar);
		
		contentPaneGrid = new JPanel();
		contentPaneGrid = new JPanel();
		contentPaneGrid.setBackground(Color.DARK_GRAY);
		contentPaneGrid.setPreferredSize(new Dimension(800, 0));
		getContentPane().add(contentPaneGrid, BorderLayout.WEST);
		
		contentPaneList = new JPanel();
		contentPaneList.setBackground(Color.GRAY);				
		contentPaneList.setPreferredSize(new Dimension(350, 0));
		getContentPane().add(contentPaneList, BorderLayout.EAST);
		
		modelList = new DefaultListModel<>();
		contentPaneList.setLayout(null);
		
		wordList = new JList<>(modelList);		
		wordList.setBounds(55, 20, 250, 400);
		
		scrollWordList = new JScrollPane(wordList);
		scrollWordList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollWordList.setBounds(55, 20, 250, 400);
		contentPaneList.add(scrollWordList);
		
		btnSolvePuzzleFast = new JButton("SOLVE PUZZLE FAST");
		btnSolvePuzzleFast.setBounds(55, 588, 250, 23);
		contentPaneList.add(btnSolvePuzzleFast);
		
		btnSolvePuzzleSlow = new JButton("SOLVE PUZZLE SLOW");
		btnSolvePuzzleSlow.setBounds(55, 634, 250, 23);
		contentPaneList.add(btnSolvePuzzleSlow);
		
		txInsertWord = new JTextField();
		txInsertWord.setBounds(55, 431, 250, 23);
		contentPaneList.add(txInsertWord);
		txInsertWord.setColumns(10);
		
		btnInsertWord = new JButton("Insert/Edit word");		
		btnInsertWord.setBounds(55, 461, 250, 22);
		contentPaneList.add(btnInsertWord);
		
		txInsertLetterRow = new JTextField();
		txInsertLetterRow.setBounds(55, 543, 43, 20);
		contentPaneList.add(txInsertLetterRow);
		txInsertLetterRow.setColumns(10);
		
		txInsertLetterCol = new JTextField();
		txInsertLetterCol.setBounds(108, 543, 43, 20);
		contentPaneList.add(txInsertLetterCol);
		txInsertLetterCol.setColumns(10);
		
		btnInsertLetter = new JButton("Insert letter");
		btnInsertLetter.setBounds(161, 543, 144, 21);
		contentPaneList.add(btnInsertLetter);
		
		lblRow = new JLabel("ROW");
		lblRow.setHorizontalAlignment(SwingConstants.CENTER);
		lblRow.setBounds(55, 530, 43, 14);
		contentPaneList.add(lblRow);
		
		lblCol = new JLabel("COL");
		lblCol.setHorizontalAlignment(SwingConstants.CENTER);
		lblCol.setBounds(108, 530, 43, 14);
		contentPaneList.add(lblCol);
		
		btnRemoveWord = new JButton("Remove word");
		btnRemoveWord.setBounds(55, 494, 250, 23);
		contentPaneList.add(btnRemoveWord);
	}
	
	public void setGridCells(char[][] board, boolean[][] boardResult) {									//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	setGridCells()
		//
		// Method parameters	:	char[][] board, boolean[][] boardResult
		//
		// Method return		:	void
		//
		// Synopsis				:   This method populates the graphical grid and paints the words.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-09-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		contentPaneGrid = new JPanel(new GridLayout(20, 25, 5, 5));                                     
		contentPaneGrid.setBackground(Color.white);		                                                
		int i, j;		                                                                                
		for (i = 0; i < WordFindModel.ROW_ARRAY; i++) {                                                 
			for (j = 0; j < WordFindModel.COL_ARRAY; j++) {                                             
				gridCells = new JTextArea(" " + board[i][j]);				                            
				gridCells.setFont(new Font("LucidaSans", Font.PLAIN, 22));								
				hl = gridCells.getHighlighter();                                                 	                        
				HighlightPainter colour = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);				
				try {                                                                                   
					if (boardResult[i][j]) {                                                            
						hl.addHighlight(0, 4, colour);                                                  
					}                                                                                   
					contentPaneGrid.add(gridCells);                                                     
				} catch (BadLocationException e) {                                                      
					e.printStackTrace();                                                                
				}                                                                                       
			}                                                                                           
		}                                                                                               
		contentPaneGrid.setPreferredSize(new Dimension(800, 0));		                                
		getContentPane().add(contentPaneGrid, BorderLayout.WEST);		                                
		contentPaneGrid.updateUI();                                                                     
	}                                                                                                   
	                                                                                                                                                                       
	public void setWordList(ArrayList<String> wordList) {  
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	setWordList()
		//
		// Method parameters	:	ArrayList<String> wordList
		//
		// Method return		:	void
		//
		// Synopsis				:   This method populates the jList word's list.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-09-21		Tiago   				none.
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		int i;                                                                                          
		modelList.clear();                                                                              
		for (i = 0; i < wordList.size(); i++) {                                                         
			addJWordList(wordList.get(i));			                                                    
		}                                                                                               
		WordFindView.wordList.setModel(modelList);                                                      
	}                                                                                                   
	                                                                                                    
	//METHODS USED FOR SIMPLE GRAPHIC OBJECTS OPERATIONS *************************************
	public void displayMessage(String errorMessage) {                                                   
		JOptionPane.showMessageDialog(this, errorMessage);												
	}
	
	public void addJWordList(String word) {	
		modelList.addElement(word);																		
		wordList.setModel(modelList);
	}
	
	public void editJWordList(String word, int position) {
		modelList.setElementAt(word, position);		
		wordList.setModel(modelList);
	}
	
	public void removeJWordList(int position) {
		modelList.removeElementAt(position);
	}
	
	public void showJWordListFounded(String word, int position) {
		modelList.setElementAt(word + " *", position);
		wordList.setModel(modelList);
	}
	
	public String getTxInsertWord() {
		return txInsertWord.getText();
	}
	
	public void clearTxInsertWord() {
		txInsertWord.setText("");
	}
	
	public void setTxInsertWord(String word) {
		txInsertWord.setText(word);
	}
	
	public String getTxInsertLetterRow() {
		return txInsertLetterRow.getText();
	}
	
	public void clearTxInsertLetterRow() {
		txInsertLetterRow.setText("");
	}
	
	public String getTxInsertLetterCol() {
		return txInsertLetterCol.getText();
	}
	
	public void clearTxInsertLetterCol() {
		txInsertLetterCol.setText("");
	}
	
	public JTextArea getGridCells() {                                                                   
		return gridCells;                                                                               
	}  
	
	public void resetWordList(ArrayList<String> wordList) {                                             
		wordList.clear();                                                                               
	}  
                                     	
	public String getJListElement() {
		return wordList.getSelectedValue();																
	}
	
	public int getJListIndex() {
		return wordList.getSelectedIndex();
	}
	
	public void clearJList() {
		modelList.clear();																	
	}
	
	public void setDisableButtons() {
		btnInsertWord.setEnabled(false);
		btnInsertLetter.setEnabled(false);
		btnRemoveWord.setEnabled(false);
		btnSolvePuzzleFast.setEnabled(false);
		btnSolvePuzzleSlow.setEnabled(false);
	}
	
	public void setEnableButtons() {
		btnInsertWord.setEnabled(true);
		btnInsertLetter.setEnabled(true);
		btnRemoveWord.setEnabled(true);
		btnSolvePuzzleFast.setEnabled(true);
		btnSolvePuzzleSlow.setEnabled(true);
	}

	//THE METHODS BELLOW ARE IMPLEMENTED IN THE CONTROLLER CLASS.
	public void addLoadFile(ActionListener listenerForLoadFile) {
		loadPuzzle.addActionListener(listenerForLoadFile);
	}
	
	public void addSaveFile(ActionListener listenerForSaveFile) {
		savePuzzle.addActionListener(listenerForSaveFile);
	}
	
	public void help(ActionListener listenerForHelp) {
		helpPuzzle.addActionListener(listenerForHelp);
	}
	
	public void keyListener(KeyListener listenerForKeyTyped) {
		txInsertLetterRow.addKeyListener(listenerForKeyTyped);	
		txInsertLetterCol.addKeyListener(listenerForKeyTyped);
	}
	
	public void keyWordListener(KeyListener listenerForKeyTyped) {
		txInsertWord.addKeyListener(listenerForKeyTyped);	
	}

	public void insertWord(ActionListener listenerForInsertWord) {
		btnInsertWord.addActionListener(listenerForInsertWord);
	}
	
	public void removeWord(ActionListener listenerForRemoveWord) {
		btnRemoveWord.addActionListener(listenerForRemoveWord);
	}
	
	public void insertLetter(ActionListener listenerForInsertLetter) {
		btnInsertLetter.addActionListener(listenerForInsertLetter);
	}
	
	public void listSelection(ListSelectionListener listenerForListSelection) {
		wordList.addListSelectionListener(listenerForListSelection);
	}
	
	public void addSolvePuzzleFast(ActionListener listenerForSolvePuzzleFast) {
		btnSolvePuzzleFast.addActionListener(listenerForSolvePuzzleFast);
	}
	
	public void addSolvePuzzleSlow(ActionListener listenerForSolvePuzzleSlow) {
		btnSolvePuzzleSlow.addActionListener(listenerForSolvePuzzleSlow);
	}
}
