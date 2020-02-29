package main;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReferenceUtils {
	
	
	
    protected Scanner getFastaFile(Path fastaPath) throws IOException{
    	Scanner referenceFastaFile = null;
		try {
			referenceFastaFile = new Scanner(fastaPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(referenceFastaFile);
    }
	protected Map<String, String> getMapFromFile(Scanner referenceFastaFile){
		Map<String, String> fileMap = new LinkedHashMap();
		StringBuilder entryFasta = new StringBuilder();
		String[] lineArray = null;
		boolean firstLinePassed = false;
    	while (referenceFastaFile.hasNextLine()){
    		String line = referenceFastaFile.nextLine();
    	   if (line.startsWith(">") && firstLinePassed) {
    		   // split line on spaces and get first element
    		   // remove the > from string 
    		   fileMap.put(lineArray[0].toString().substring(1), entryFasta.toString());
    		   lineArray = line.split(" ");
    		   entryFasta = new StringBuilder();
    		   
    	   } else if (!line.startsWith(">")) {
    		   entryFasta.append(line);
    		   
    	   } else if (line.startsWith(">") && (firstLinePassed==false)) {
    		   // for the first ">" fasta entry
    		   lineArray = line.split(" ");
    		   firstLinePassed = true;
    		   entryFasta = new StringBuilder();
    	   }
    	}
    	fileMap.put(lineArray[0].toString().substring(1), entryFasta.toString());
    	return(fileMap);
	}
	// I still need to ask Bram how to implement a superclass variable properly
	protected void indexMapWriter(Map<String, List<List<Integer>>> indexMap, String fileName, String referenceGenomeLocation) throws IOException  {
	    BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(referenceGenomeLocation + "\\"+ fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println(referenceGenomeLocation + "\\NN.bed");
	    for (Map.Entry<String, List<List<Integer>>> chromosome : indexMap.entrySet()) {
		    String name = chromosome.getKey();
		    List<List<Integer>> indices = chromosome.getValue();
		    
		    for (List<Integer> index : indices) {
		    	StringBuilder lineToWrite = new StringBuilder();
		    	lineToWrite.append(name +"\t");
		    	lineToWrite.append(index.get(0).toString() + "\t");
		    	lineToWrite.append(index.get(1).toString() + "\n");
		    	writer.write(lineToWrite.toString());
		    }
		}
	    writer.close();
	}

}
