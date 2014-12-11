package de.uni_koeln.jhermes.easysequence.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A very simple TreeNode for nGramTrees over Strings
 * @author jhermes
 *
 */
public class TreeNode {
	
	private String value;
	private Map<String,TreeNode> successorNodes;
	
	/**
	 * Initializes a new TreeNode with the specified String value
	 * @param value
	 */
	public TreeNode(String value){
		this.value = value;
		successorNodes = new HashMap<String, TreeNode>();
	}
	
	/**
	 * Adds a new String sequence to this node
	 * @param sequence Sequence to add
	 */
	public void add(List<String> sequence){
		String actual = sequence.get(0);
		TreeNode successor = successorNodes.get(actual);
		if(successor==null){
			successor = new TreeNode(actual);
		}
		successorNodes.put(actual, successor);
		if(sequence.size()>1){
			successor.add(sequence.subList(1, sequence.size()));
		}
		
	}
	
	/**
	 * Searches the sequence within the successors of this node
	 * @param sequence Sequence to search
	 * @return the depth of TreeNode the node the sequence ends 
	 */
	public int search(List<String> sequence){
		if(sequence.isEmpty()){
			return 0;
		}
		String actual = sequence.get(0);
		TreeNode successor = successorNodes.get(actual);
		if(successor==null||sequence.size()<1){		
			return sequence.size();
		}
		else{
			return successor.search(sequence.subList(1, sequence.size()));
		}
	}

}
