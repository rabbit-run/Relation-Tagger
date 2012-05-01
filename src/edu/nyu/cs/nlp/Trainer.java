package edu.nyu.cs.nlp;

import java.io.File;
import java.io.FileReader;

import opennlp.maxent.BasicEventStream;
import opennlp.maxent.GIS;
import opennlp.maxent.GISModel;
import opennlp.maxent.PlainTextByLineDataStream;
import opennlp.maxent.io.GISModelWriter;
import opennlp.maxent.io.SuffixSensitiveGISModelWriter;
import opennlp.model.EventStream;


public class Trainer {
	private FeatureComputer fc;
	private FileProcessor fp;
	
	public Trainer(String fileName){
		fp = new FileProcessor(fileName);
		fc = new FeatureComputer(true);
	}
	
	public void train() {
		if (!(new File("trainModel.txt")).exists()) {
			String featureFileName = "train.dat";
			fc.makeFeatureFile(fp.getAllSentences(), featureFileName);
			String modelFileName = "trainModel.txt";
			try {
				FileReader datafr = new FileReader(new File(featureFileName));
				EventStream es = new BasicEventStream(
						new PlainTextByLineDataStream(datafr));
				GISModel model = GIS.trainModel(es, 100, 4);
				File outputFile = new File(modelFileName);
				GISModelWriter writer = new SuffixSensitiveGISModelWriter(
						model, outputFile);
				writer.persist();
			} catch (Exception e) {
				System.out.print("Unable to create model due to exception: ");
				System.out.println(e);
			}
		}
	}
}
