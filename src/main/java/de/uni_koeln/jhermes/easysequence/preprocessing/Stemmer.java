package de.uni_koeln.jhermes.easysequence.preprocessing;

import java.util.ArrayList;
import java.util.List;

import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.GermanStemmer;

/**
 * Replaces tokens by their stems. Uses the snowball stemmer for german words. 
 * @see http://snowball.tartarus.org/ 
 * 
 * @author jhermes
 *
 */
public class Stemmer {

	private SnowballProgram stemmer;

	public Stemmer() {
		stemmer = new GermanStemmer();
	}

	public List<String> getStems(List<String> tokens) {
		List<String> stems = new ArrayList<String>();
		for (String fu : tokens) {
			stemmer.setCurrent(fu.toLowerCase());
			stemmer.stem();
			stems.add(stemmer.getCurrent());
		}
		return stems;
	}

}
