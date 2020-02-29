package main;

import java.io.File;
import java.io.IOException;

public class Main {
	private String REFERENCE_GENOME_LOCATION = "D:\\Scripts\\PredictingCrossoverSitesDataFiles\\Reference";
	private String OUT_PATH;
	private String IN_PATH = "D:\\Scripts\\PredictingCrossoverSitesDataFiles\\Input";
	private String GENERAL_PATH = "D:\\Scripts\\PredictingCrossoverSitesDataFiles";
	private String RSCRIPT_PATH;
	private ArgChecker argChecker;
	private Integer CROSS_OVER_SIZE = 5000;
	
	
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
		preparePositiveSets();
		
	}
	private void declarePaths() {
		REFERENCE_GENOME_LOCATION = argChecker.getREFERENCE_GENOME_LOCATION();
		OUT_PATH = argChecker.getOUT_PATH();
		IN_PATH = argChecker.getIN_PATH();
		RSCRIPT_PATH = argChecker.getRSCRIPT_PATH();
		
	}
	
	private void preparePositiveSets() {
		PositiveBedPreparation positiveBedPreparation = new PositiveBedPreparation(GENERAL_PATH, CROSS_OVER_SIZE);
		
		
	}
	
	private void checkGenomeFiles() {
		checkNNBed();
		checkLengthGenome();

	}
	private void checkNNBed() {
		StringBuilder nnBed =  new StringBuilder(REFERENCE_GENOME_LOCATION);
		nnBed.append("\\NN.bed");

		
		File nnBedFile = new File(nnBed.toString());
		if (!nnBedFile.exists()) {
			NnBedMaker nnBedMaker = new NnBedMaker(REFERENCE_GENOME_LOCATION);
			try {
				nnBedMaker.makeBed();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(nnBedFile.exists()) {
			System.out.println("NN file exist you dummy");
		}
	}
	
	private void checkLengthGenome() {
		StringBuilder lengthGenome =  new StringBuilder(REFERENCE_GENOME_LOCATION);
		lengthGenome.append("\\lengths.genome");
		File lengthGenomeFile = new File(lengthGenome.toString());
		if (!lengthGenomeFile.exists()) {
			LengthGenomeMaker lengthGenomeMaker = new LengthGenomeMaker(REFERENCE_GENOME_LOCATION);
			try {
				System.out.println("enter try");
				lengthGenomeMaker.makeBed();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(lengthGenomeFile.exists()) {
			System.out.println("length.genome file exist you dummy");
		}
	}
	

}
