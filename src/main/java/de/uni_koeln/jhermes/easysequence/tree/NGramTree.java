package de.uni_koeln.jhermes.easysequence.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * A very simple nGramTree for Strings
 * @author jhermes
 *
 */
public class NGramTree {
	
	private TreeNode root;
	private int depth;
	
	/** Initializes a new nGramTree of the specified depth
	 * @param depth
	 */
	public NGramTree(int depth){
		root = new TreeNode("ROOT");
		this.depth = depth;
	}
	
	/**
	 * Adds a list of words to the tree
	 * @param wordList List of words
	 */
	public void addSequences(List<String> wordList){
		for(int i=0; i<wordList.size()-(depth+1); i++){
			List<String> nextSequence = wordList.subList(i, i+depth);
			//System.out.println(nextSequence);
			root.add(nextSequence);
		}
	}
	
	/**
	 * Searches a list of words in the existing tree
	 * @param wordList List of words
	 * @return A List of word sequences spanning over the whole tree
	 */
	public List<List<String>> searchSequences(List<String> wordList){
		List<List<String>> found = new ArrayList<List<String>>();
		for(int i=0; i<wordList.size()-(depth+1); i++){
			List<String> nextSequence = wordList.subList(i, i+depth);
			//System.out.println(nextSequence);
			int length = depth-root.search(nextSequence);
			//System.out.println(length);
			
			if(length>=depth){
				//System.out.println("Found: " + nextSequence);
				found.add(nextSequence);
			}			
		}		
		return found;
	}	
	

	/**
	 * Cobines sequences, if they are adjacent and ongoing
	 * @param found A list of sequences
	 * @return A list of combined sequences
	 */
	public List<List<String>> combineSequences(List<List<String>> found) {
		List<List<String>> merged = new ArrayList<List<String>>();
		List<String> before = found.get(0);
		List<String> combined = new ArrayList<String>(before);
		for (int i = 1; i < found.size(); i++) {
			List<String> next = found.get(i);
			if(next.get(0).equals(before.get(1)) && next.get(1).equals(before.get(2)) && next.get(2).equals(before.get(3))){
				combined.add(next.get(next.size()-1));
			}
			else{
				merged.add(new ArrayList<String>(combined));
				combined = new ArrayList<String>(next);
			}
			before = next;
		}
		
		return merged;
	}

}
