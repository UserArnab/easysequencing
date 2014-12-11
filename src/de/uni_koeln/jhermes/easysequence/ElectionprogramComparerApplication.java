package de.uni_koeln.jhermes.easysequence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import de.uni_koeln.jhermes.easysequence.preprocessing.FeatureUnitTokenizer;
import de.uni_koeln.jhermes.easysequence.preprocessing.StopwordFilter;
import de.uni_koeln.jhermes.easysequence.tree.NGramTree;

/**
 * An application to find matching sequences between two documents 
 * @author jhermes
 *
 */
public class ElectionprogramComparerApplication {

	public static void main(String[] args) throws IOException {
		
		// Konfigurable items
		String electionmanifesto = "electionprograms/wahlprogramm_hessen.txt";
		String coalitionagreement = "electionprograms/koalitionsvertrag_hessen.txt";
		boolean deleteStopwords = false;
		int minSequenceLength = 15;
		
		
		//Read the coalition agreement 
		BufferedReader in = new BufferedReader(new FileReader(new File(coalitionagreement)));
		StringBuffer buff = new StringBuffer();
		String line = in.readLine();
		while(line!=null){
			buff.append(line + "\n");
			line = in.readLine();
		}
		String ka = buff.toString();
		
		//Read the election manifesto
		in = new BufferedReader(new FileReader(new File(electionmanifesto)));
		buff = new StringBuffer();
		line = in.readLine();
		while(line!=null){
			buff.append(line + "\n");
			line = in.readLine();
		}
		String em = buff.toString();
		
		//Tokenize documents
		FeatureUnitTokenizer tok = new FeatureUnitTokenizer();
		List<String> emTokens = tok.tokenize(em);
		List<String> kaTokens = tok.tokenize(ka);
		System.out.println("Count of tokens em: " + emTokens.size());
		System.out.println("Count of tokens ka: " + kaTokens.size());
		
		//Filter stopwords if requested
		if(deleteStopwords){
			StopwordFilter filter = new StopwordFilter(new File("data/stopwords.txt"));
			emTokens = filter.filterStopwords(emTokens);
			kaTokens = filter.filterStopwords(kaTokens);	
			System.out.println("Count of tokens em without stopwords: " + emTokens.size());
			System.out.println("Count of tokens ka without stopwords: " + kaTokens.size());
		}
		
		//Build a sequence tree from the em tokens
		NGramTree tree = new NGramTree(minSequenceLength);
		tree.addSequences(emTokens);
		
		//Search the ka token sequences in the tree
		List<List<String>> found = tree.searchSequences(kaTokens);
		System.out.println("Sequences found: " +  found.size());
		
		//Combine continuing sequences
		List<List<String>> combined = tree.combineSequences(found);
		System.out.println("Combined sequences found: " + combined.size());
		System.out.println();
		for (List<String> list : combined) {
			System.out.println(list.size()  + ": " + list);
			System.out.println();
		}
		System.out.println();
	}
	
//	@Test
//	public void testEncodingGuesser() throws IOException{
//		byte[] buf = new byte[4096];
//	    String fileName ="electionprograms/koalitionsvertrag_hessen.txt";
//	    java.io.FileInputStream fis = new java.io.FileInputStream(fileName);
//
//	    // (1)
//	    UniversalDetector detector = new UniversalDetector(null);
//
//	    // (2)
//	    int nread;
//	    while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
//	      detector.handleData(buf, 0, nread);
//	    }
//	    // (3)
//	    detector.dataEnd();
//
//	    // (4)
//	    String encoding = detector.getDetectedCharset();
//	    if (encoding != null) {
//	      System.out.println("Detected encoding = " + encoding);
//	    } else {
//	      System.out.println("No encoding detected.");
//	    }
//
//	    // (5)
//	    detector.reset();
//	}

}
