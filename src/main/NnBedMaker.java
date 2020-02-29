package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;




public class NnBedMaker extends ReferenceUtils {
	String referenceGenomeLocation;
	
	public NnBedMaker(String referenceGenomeLocation) {
		this.referenceGenomeLocation = referenceGenomeLocation;
		try {
			makeBed();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void makeBed() throws IOException {
		Path p = Paths.get(referenceGenomeLocation);
		final int maxDepth = 10;
		Stream<Path> matches = Files.find(p,maxDepth,(path, basicFileAttributes) -> String.valueOf(path).endsWith(".genome.fa"));


		List<Path> actualMatches = matches.collect(Collectors.toList());

    	Scanner referenceFastaFile = getFastaFile(actualMatches.get(0));
    	Map<String,String> fileMap = getMapFromFile(referenceFastaFile);
    	Map<String, List<List<Integer>>> indexMap =  getIndexFromMap(fileMap);
    	// Like I said we need to fix this stupid three input function
    	indexMapWriter(indexMap, "NN.bed", referenceGenomeLocation);
	}
	

	
	private Map<String, List<List<Integer>>> getIndexFromMap(Map<String, String> fileMap){
		Map<String, List<List<Integer>>> indexMap = new LinkedHashMap();
		char theBadChar = 'N';
		for (Map.Entry<String, String> chromosome : fileMap.entrySet()) {
		    String name = chromosome.getKey();
		    String sequence = chromosome.getValue();
		    
		    List<List<Integer>> indicesOfN_OfChr = new ArrayList<List<Integer>>();
		    boolean newEntry = false;
		    List<Integer> newIndice = null;
		    
		    for (int i = 0; i < sequence.length(); i ++  ) {
		    	// this function |compare| returns 0 if the chars are at the smae index in alphabeth
		    	if ((Character.compare(sequence.charAt(i), theBadChar) == 0) && (newEntry == false)) {
		    		newIndice = new ArrayList<Integer>();
		    		newIndice.add(i);
		    		newEntry = true;
		    	} else if ((newEntry==true) && (Character.compare(sequence.charAt(i), theBadChar) != 0)) {
		    		newIndice.add(i);
		    		indicesOfN_OfChr.add(newIndice);
		    		newEntry = false; 
		    	}
		    }
		    indexMap.put(name, indicesOfN_OfChr);   
		}
		return(indexMap);
	}
}
