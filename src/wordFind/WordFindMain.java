package wordFind;

public class WordFindMain {

	public static void main(String[] args) {
		WordFindView theView = new WordFindView();
		WordFindModel theModel = new WordFindModel();
		@SuppressWarnings("unused")
		WordFindController theController = new WordFindController(theView, theModel);
		theView.setVisible(true);
	}

}
