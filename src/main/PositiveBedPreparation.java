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

public class PositiveBedPreparation {
	String inputLocation;
	Integer CROSS_OVER_SIZE;
	
	public PositiveBedPreparation(String inputLocation, Integer crossOverSize) {
		this.inputLocation = inputLocation;
		this.CROSS_OVER_SIZE = crossOverSize;
		makeBed();
	}
	private void makeBed() {
		StringBuilder inputLoc =  new StringBuilder(inputLocation);
		inputLoc.append("\\CO_location.bed");
		File crossoverFile = new File(inputLoc.toString());
		if (!crossoverFile.exists()) {
			System.out.println("Crossover file is not in input folder or is not named CO_location.bed");
		} else if (crossoverFile.exists()) {
			Scanner positiveBedScanner = getBedFile(inputLoc.toString());
			Map<String, List<List<Integer>>> crossoverMap = readBedFile(positiveBedScanner);
		}
		
	}
	
	
	private Map<String, List<List<Integer>>> extendBedFile(Map<String, List<List<Integer>>> crossoverMap){
		
		Map<String, List<List<Integer>>>
		for (List<List<Integer>> crossoverSites : crossoverMap.values()) {
			for (List<Integer> singleSite : crossoverSites) {
				
			}
			
		}
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
