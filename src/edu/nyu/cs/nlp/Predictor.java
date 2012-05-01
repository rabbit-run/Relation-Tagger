package edu.nyu.cs.nlp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import opennlp.maxent.io.SuffixSensitiveGISModelReader;
import opennlp.model.AbstractModel;

public class Predictor {

	private FileProcessor _fp;
	private FeatureComputer _fc;
	private AbstractModel _model;
	
	public Predictor(String fileName, String modelFileName) {
		_fp = new FileProcessor(fileName);
		_fc = new FeatureComputer(false);
		try {
		_model = new SuffixSensitiveGISModelReader(
		        new File(modelFileName)).getModel();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void predict() {
		List<List<String[]>> sentences = _fp.getAllSentences();
		try {
			FileWriter fw = new FileWriter("res.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for (List<String[]> sent : sentences) {
				int ARGIndex = mostLikelyARG(sent);
				bw.write(ARGIndex);
				bw.newLine();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public int mostLikelyARG(List<String[]> sent) {
		List<String> features = _fc.computeFeatures(sent);
		double[] ARGOdds = new double[sent.size()];
		
		//--------------------------------test
		//System.out.println(_model.getOutcome(0) +" :: " + _model.getOutcome(1));
		int index = 0;
		for (String word : features) {
			String[] featureVector = word.split("\\s");
			double[] odds = _model.eval(featureVector);
			// odds[1] is the odd of ARG1
			ARGOdds[index] = odds[1];
			System.out.println("num: "+ index + " : " +odds[0] +" :: " + odds[1]);
			index++;
		}
		return findIndexOfMax(ARGOdds);
	}

	private int findIndexOfMax(double[] aRGOdds) {
		int maxIndex = -1;
		double max = Double.NEGATIVE_INFINITY;
		
		for (int index = 0; index < aRGOdds.length; index++) {
			if (max < aRGOdds[index])
				maxIndex = index;
		}
		
		//--------------------------------test
		//System.out.println(maxIndex);
		return maxIndex;
	}
}
