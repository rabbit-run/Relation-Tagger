package edu.nyu.cs.nlp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FileProcessor {
	private String _fileName;
	private List<List<String[]>> _allSentences;
	private ArrayList<String[]> _sent;
	
	public FileProcessor(String fileName) {
		_fileName = fileName;
		_sent = new ArrayList<String[]>();
		_allSentences = new LinkedList<List<String[]>>();
	}
	
	private void preprocess() {
		FileReader fr;
		try{
			fr = new FileReader(_fileName);
			Scanner sc = new Scanner(fr);
			while( sc.hasNextLine()) {
				processLine(sc.nextLine());
			}
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processLine(String line) {
		if (line.isEmpty()) {
			_allSentences.add((List<String[]>) _sent.clone());
			_sent.clear();
		}
		else {
			String[] s = line.trim().split("\\s");
			_sent.add(s);
		}
	}
	
	public List<List<String[]>> getAllSentences() {
		preprocess();
		return _allSentences;
	}
}
