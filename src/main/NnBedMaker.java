package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class NnBedMaker {
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
		System.out.println(p);
		Stream<Path> matches = Files.find(p,maxDepth,(path, basicFileAttributes) -> String.valueOf(path).endsWith(".genome.fa"));
		System.out.println(matches);

		List<Path> actualMatches = matches.collect(Collectors.toList());

		
		
    	Scanner referenceFastaFile = getFastaFile(actualMatches.get(0));
    	Map<String,String> fileMap = getMapFromFile(referenceFastaFile);
		
		

    	

    	
		
	}

	private Map<String, String> getMapFromFile(Scanner referenceFastaFile){
		Map<String,String> fileMap = new LinkedHashMap();
		StringBuilder entryFasta = null;
		String[] lineArray = null;
		boolean firstLinePassed = false;
    	while (referenceFastaFile.hasNextLine())
    	{
    	   String line = referenceFastaFile.nextLine();
    	   System.out.println(line);
    	   
    	   if (line.startsWith(">") && firstLinePassed) {
    		   // split line on spaces and get first element
    		   lineArray = line.split(" ");
    		   // remove the > from string 
    		   fileMap.put(lineArray[0].toString().substring(1), entryFasta.toString());
    		   entryFasta =null;
    		   
    	   } else if (!line.startsWith(">")) {
    		   entryFasta.append(line);
    		   firstLinePassed = false;
    	   }
    	}
    	fileMap.put(lineArray[0].toString().substring(1), entryFasta.toString());
    	return(fileMap);
	}
    private Scanner getFastaFile(Path fastaPath) throws IOException{
    	Scanner referenceFastaFile = null;
		try {
			referenceFastaFile = new Scanner(fastaPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(referenceFastaFile);
    }
}
