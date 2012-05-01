package edu.nyu.cs.nlp;

import java.util.List;

public class driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Trainer trainer = new Trainer("%-training");
		trainer.train();
		Predictor predictor = new Predictor("%-dev","trainModel.txt");
		predictor.predict();
//		List<List<String[]>> l = processor.getAllSentences();
//		FeatureComputer fc = new FeatureComputer(false);
//		List<String> vector = fc.computeFeatures(l.get(0));
//		for (String s : vector) {
//			System.out.println(s);
//		}
	}

}
