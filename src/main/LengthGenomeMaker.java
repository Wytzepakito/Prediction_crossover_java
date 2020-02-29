package main;

import java.io.FileNotFoundException;
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

public class LengthGenomeMaker extends ReferenceUtils {
	String referenceGenomeLocation;
	
	public LengthGenomeMaker(String referenceGenomeLocation) {
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
		System.out.println(p);
		Stream<Path> matches = Files.find(p,maxDepth,(path, basicFileAttributes) -> String.valueOf(path).endsWith(".genome.fa"));


		List<Path> actualMatches = matches.collect(Collectors.toList());

    	Scanner referenceFastaFile = getFastaFile(actualMatches.get(0));
    	Map<String,String> fileMap = getMapFromFile(referenceFastaFile);
    	Map<String, List<List<Integer>>> lengthMap =  getLengthFromChrs(fileMap);
    	indexMapWriter(lengthMap, "lengths.genome", referenceGenomeLocation);
		
	}
	
	private Map<String, List<List<Integer>>> getLengthFromChrs(Map<String, String> fileMap){
		Map<String, List<List<Integer>>> lengthMap = new LinkedHashMap();
		for (Map.Entry<String, String> chromosome : fileMap.entrySet()) {
		    String name = chromosome.getKey();
		    String sequence = chromosome.getValue();
		    List<List<Integer>> indexList = new ArrayList<List<Integer>>();
		    List<Integer> smallIndexList = new ArrayList<Integer>();
		    smallIndexList.add(0);
		    smallIndexList.add(sequence.length());
		    indexList.add(smallIndexList);
		    
		    lengthMap.put(name, indexList);
		}
		return(lengthMap);
	}


}
