package edu.nyu.cs.nlp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FeatureComputer {
	private Boolean _isTraining;
	
	public FeatureComputer(Boolean isTraining) {
		_isTraining = isTraining;
	}
	
	private class FeatureBuilder {
		private StringBuilder _sb;
		FeatureBuilder() {
			_sb = new StringBuilder();
		}
		
		FeatureBuilder appendFeature(String name, String value) {
			_sb.append(name).append("=").append(value).append(" ");
			return this;
		}
		
		FeatureBuilder appendLabel(String label) {
			_sb.append(label);
			return this;
		}
		
		//@override	
		public String toString() {
			return _sb.toString();
		}
		
		void clear() {
			_sb = new StringBuilder();
		}
	}
	
	public void makeFeatureFile(List<List<String[]>> allSentences, String fileName) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			for (List<String[]> sent : allSentences) {
				List<String> featureString = computeFeatures(sent);
				for (String vector : featureString) {
					out.write(vector);
					out.newLine();
				}
			}
			out.close();
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public List<String> computeFeatures(List<String[]> sent){
		List<String> featureVectors = new LinkedList<String>();
		FeatureBuilder features = this.new FeatureBuilder();
		
		for (int index = 0; index < sent.size(); index++) {
			
			String[] word = sent.get(index);
			features.appendFeature("candToken", word[0]).
			appendFeature("candTokenPOS", word[1]);
			
			if (index != 0) {
				String[] previousWord = sent.get(index-1);
				features.appendFeature("tokenBeforeCand", previousWord[0]).
					appendFeature("posBeforeCand", previousWord[1]);
			}
			
			if (index != sent.size()-1) {
				String[] nextWord = sent.get(index+1);
				features.appendFeature("tokenAfterCand", nextWord[0]).
				appendFeature("posAfterCand", nextWord[1]);
			}
			
			if (_isTraining) {
				if (word.length == 6 && word[5].equals("ARG1")) {
				// If the the word is ARG1, it is labeled as 1
					features.appendLabel("1");
				}
				else {
				// If the the word is not ARG1, it is labeled as 0
					features.appendLabel("0");
				}
			}
			featureVectors.add(features.toString());
			features.clear();
		}
		
		return featureVectors;
	}
}
