package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

		Stream<Path> matches = Files.find(p,maxDepth,(path, basicFileAttributes) -> String.valueOf(path).endsWith(".txt"));
		

		List<Path> actualMatches = matches.collect(Collectors.toList());
		for (Path match:actualMatches) {
			System.out.println(match.toString());
		}
		
	}
}
