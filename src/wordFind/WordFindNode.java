package wordFind;

import java.util.HashMap;

public class WordFindNode {

	private char letter;                                                                 //Declares a char that will store in the node.
	private boolean isEndWord;                                                           //Declares a boolean that will set at the end of a branch (entire word). 
	private HashMap <Character, WordFindNode> children;                                  //Declares a hash map that will store the children's node.

	//CONSTRUCTOR METHOD *******************************
    WordFindNode (char letter) {
        this.letter = letter;
        this.isEndWord = false;
        this.children = new HashMap<Character, WordFindNode>();
    }
    
    //GETTER AND SETTERS *******************************
	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public boolean isEndWord() {
		return isEndWord;
	}

	public void setEndWord(boolean isEndWord) {
		this.isEndWord = isEndWord;
	}

	public HashMap<Character, WordFindNode> getChildren() {
		return children;
	}

	public void setChildren(HashMap<Character, WordFindNode> children) {
		this.children = children;
	}
}