package wordFind;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordFindTrie {
                                                                                     
	private WordFindNode node;                                                      //Declares a node object.
	private ArrayList<String> list;                                                 //Declares an ArrayList which will be used to
																					//store temporarily the list of found words.
	//CONSTRUCTOR METHOD *******************************
	WordFindTrie () {       
        node = new WordFindNode('0');
        list = new ArrayList<>();
    }

    public void insert(String word) {
    	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	insert()
		//
		// Method parameters	:	String word
		//
		// Method return		:	void
		//
		// Synopsis				:   This method loads the words into an node object.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    	WordFindNode currentNode = node;
    	int index;
    	char letter;
        for (index = 0; index < word.length(); index++) {
            letter = word.charAt(index);
            if (currentNode.getChildren().containsKey(letter)) {
                currentNode = currentNode.getChildren().get(letter);
            } else {
            	WordFindNode newNode = new WordFindNode(letter);
                currentNode.getChildren().put(letter, newNode);
                currentNode = newNode;
            }
        }
        currentNode.setEndWord(true);
    }

    public WordFindNode search(String word) {
    	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	search()
		//
		// Method parameters	:	String word
		//
		// Method return		:	WordFindNode
		//
		// Synopsis				:   This method verifies if letter from a word exists into an node object.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=    	
    	WordFindNode currentNode = node;
    	this.list.clear();
    	int index;
    	char letter;
        for (index = 0; index < word.length(); index++) {
            letter = word.charAt(index);
            if (currentNode.getChildren().containsKey(letter)) {
                currentNode = currentNode.getChildren().get(letter);
            }else {
            	currentNode = null;
                return currentNode;
            }
        }
        return currentNode;
    }
       
    public void addWords(WordFindNode node, String word) {
    	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	addWords()
		//
		// Method parameters	:	WordFindNode node, String word
		//
		// Method return		:	void
		//
		// Synopsis				:   This method adds existing words into a list.							
		//
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        if (node.isEndWord()) {
            this.list.add(word);
        }
        node.getChildren().values().forEach(child -> {
            String letter = String.valueOf(child.getLetter());
            addWords(child, word.concat(letter));
        });
    }
    
    public void delete(String word) {
    	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	delete()
		//
		// Method parameters	:	String word
		//
		// Method return		:	void
		//
		// Synopsis				:   This method removes branches of words.							
		//							(do not delete the entire word because the words share the same nodes).
		// Modifications		:
		//							Date			Developer				Notes
		//							----			---------				-----
		//							2022-12-06		Tiago   				
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        List<WordFindNode> list = new ArrayList<>();
        Map<Character, WordFindNode> children = node.getChildren();
        for (char letter : word.toCharArray()) {
            if (!children.containsKey(letter)) {
                break;
            }
            WordFindNode currentNode = children.get(letter);
            children = currentNode.getChildren();
            list.add(currentNode);
        }
        if (list.isEmpty() || list.size() != word.length()) {
            return;
        }
        list.get(list.size() - 1).setEndWord(false);
        for (int i = list.size() - 1; i > 0; i--) {
        	WordFindNode current = list.get(i);
            if (current.isEndWord()) {
                break;
            } else if (current.getChildren().isEmpty()) {
                list.get(i - 1).getChildren().remove(current.getLetter());
            }
        }
    }

    //GETTER AND SETTERS *******************************
	public WordFindNode getNode() {
		return node;
	}

	public void setNode(WordFindNode node) {
		this.node = node;
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}
}