package de.uni_koeln.jhermes.easysequence.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNode {
	
	private String value;
	private Map<String,TreeNode> successorNodes;
	
	public TreeNode(String value){
		this.value = value;
		successorNodes = new HashMap<String, TreeNode>();
	}
	
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
