package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PositiveBedPreparation extends ReferenceUtils {
	String generalPath;
	Integer CROSS_OVER_SIZE;
	
	public PositiveBedPreparation(String generalPath, Integer crossOverSize) {
		this.generalPath = generalPath;
		this.CROSS_OVER_SIZE = crossOverSize;
		try {
			makeBed();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void makeBed() throws IOException {
		StringBuilder inputLoc =  new StringBuilder(generalPath);
		inputLoc.append("\\Input\\CO_location.bed");
		StringBuilder outputLoc = new StringBuilder(generalPath);
		outputLoc.append("\\Output");
		File crossoverFile = new File(inputLoc.toString());
		System.out.println(inputLoc.toString());
		if (!crossoverFile.exists()) {
			System.out.println("Crossover file is not in input folder or is not named CO_location.bed");
		} else if (crossoverFile.exists()) {
			Scanner positiveBedScanner = getBedFile(inputLoc.toString());
			Map<String, List<List<Integer>>> crossoverMap = readBedFile(positiveBedScanner);
			Map<String, List<List<Integer>>> extendedCrossoverMap = extendBedFile(crossoverMap);
			indexMapWriter(extendedCrossoverMap, "positive.bed" , outputLoc.toString());
		}
		
	}
	

	
	
	private Map<String, List<List<Integer>>> extendBedFile(Map<String, List<List<Integer>>> crossoverMap){
		
		Map<String, List<List<Integer>>> extendedCrossoverSites = new LinkedHashMap();
		for (Map.Entry<String, List<List<Integer>>> entry : crossoverMap.entrySet()) {
			List<List<Integer>> crossoverSites = entry.getValue();
			String chromosomeName = entry.getKey();
			List<List<Integer>> newChromosomeList = new ArrayList<List<Integer>>();
			for (List<Integer> singleSite : crossoverSites) {
				if (singleSite.get(1)-singleSite.get(0)<2000) {
					Integer half = (singleSite.get(1) - singleSite.get(0))/2;
					Integer lowerbound = (singleSite.get(0) + half)-5000;
					Integer upperbound = (singleSite.get(0) + half)+5000;
					List<Integer> newCrossoverSite = new ArrayList<Integer>();
					newCrossoverSite.add(lowerbound);
					newCrossoverSite.add(upperbound);
					
					newChromosomeList.add(newCrossoverSite);		
				}
			}
			//write here
			extendedCrossoverSites.put(chromosomeName, newChromosomeList);
		}
		return(extendedCrossoverSites);
	}
	
	private Map<String, List<List<Integer>>> readBedFile(Scanner positiveBedScanner) {
		Map<String, List<List<Integer>>> positiveBedMap = new LinkedHashMap();

		while (positiveBedScanner.hasNextLine()){
			String line = positiveBedScanner.nextLine();

			String[] lineArray =  line.split("\\s+");
			if (positiveBedMap.containsKey(lineArray[0])) {
				List<List<Integer>> chrCrossoverList = positiveBedMap.get(lineArray[0]);
				List<Integer> crossoverSite = new ArrayList<Integer>();
				crossoverSite.add(Integer.parseInt(lineArray[1]));
				crossoverSite.add(Integer.parseInt(lineArray[2]));
				chrCrossoverList.add(crossoverSite);
				positiveBedMap.put(lineArray[0], chrCrossoverList);
				
				
			} else if (!positiveBedMap.containsKey(lineArray[0])) {
				List<Integer> crossoverSite = new ArrayList<Integer>();
				crossoverSite.add(Integer.parseInt(lineArray[1]));
				crossoverSite.add(Integer.parseInt(lineArray[2]));
				List<List<Integer>> chrCrossoverList = new ArrayList<List<Integer>>();
				chrCrossoverList.add(crossoverSite);
				positiveBedMap.put(lineArray[0], chrCrossoverList);
			}
		
		}
		return(positiveBedMap);
	}
	
	private Scanner getBedFile(String inputLoc)  {
		System.out.println("inputLoc");
		System.out.println(inputLoc);
		Path path = Paths.get(inputLoc);
		Scanner positiveBedFile = null;
		try {
			positiveBedFile = new Scanner(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(positiveBedFile);
		
	}
	
}
