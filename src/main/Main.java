package main;

import java.io.File;
import java.io.IOException;

public class Main {
	private String REFERENCE_GENOME_LOCATION = "D:\\Scripts\\PredictingCrossoverSitesDataFiles\\Reference";
	private String OUT_PATH;
	private String IN_PATH;
	private String RSCRIPT_PATH;
	private ArgChecker argChecker;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
				try {
					 new Main(args);
				} catch (Exception e) {
					e.printStackTrace();
				}
			

}
	
	public Main(String[] argGroup) {
	//	argChecker = new ArgChecker(argGroup);
	//	declarePaths();
		checkGenomeFiles();
	}
	private void declarePaths() {
		REFERENCE_GENOME_LOCATION = argChecker.getREFERENCE_GENOME_LOCATION();
		OUT_PATH = argChecker.getOUT_PATH();
		IN_PATH = argChecker.getIN_PATH();
		RSCRIPT_PATH = argChecker.getRSCRIPT_PATH();
		
	}
	
	private void checkGenomeFiles() {
		StringBuilder nnBed =  new StringBuilder(REFERENCE_GENOME_LOCATION);
		nnBed.append("NN.bed");
		StringBuilder lengthsGenome =  new StringBuilder(REFERENCE_GENOME_LOCATION);
		lengthsGenome.append("lengths.genome");
		
		File nnBedFile = new File(nnBed.toString());
		if (!nnBedFile.exists()) {
			NnBedMaker nnBedMaker = new NnBedMaker(REFERENCE_GENOME_LOCATION);
			try {
				nnBedMaker.makeBed();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
