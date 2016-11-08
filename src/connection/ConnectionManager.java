package connection;

import java.util.ArrayList;

public class ConnectionManager {
	public ArrayList<Word> words;
	
	public ConnectionManager()
	{
		words = new ArrayList<Word>();
	}
	
	public void addWord(Word word)
	{
		this.words.add(word);
	}
	
	public ArrayList<Word> getWords(String name)
	{
		ArrayList<Word> result = new ArrayList<Word>();
		for(Word word:this.words)
		{
			if(word.isIncluded(name))
				result.add(word);
		}
		return result;
	}
}
