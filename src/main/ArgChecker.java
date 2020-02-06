package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ArgChecker {
	private String REFERENCE_GENOME_LOCATION;
	private String OUT_PATH;
	private String IN_PATH;
	private String RSCRIPT_PATH;
	private StringBuilder HELP_MESSAGES_PATH;
	
	public ArgChecker(String[] argGroup) {
		setHelpMessagePath();
		argHandler(argGroup);
	}
	
    private void argHandler(String[] argGroup) {
    	//first iterate through list to see if help message was needed
    	boolean displayHelp = true;
    	printHelpMessage();
    	for (String arg: argGroup) {
    		if (arg.equals("-h") || arg.equals("-help")){
    			displayHelp= true;
    			printHelpMessage();
    		}
    	}
    	// if help message was needed then don't do anything
    	if (displayHelp==false){
    		locationHandler(argGroup);
    		optionHandler(argGroup);
    	}
    	
    }
    
    private void locationHandler(String[] argGroup) {
    	for (int i = 1; i< argGroup.length; i++) {
    		String arg = argGroup[i];
    		if (arg.equals("-ref")){
    			REFERENCE_GENOME_LOCATION = argGroup[i+1];
    		}
    		if (arg.equals("-o")){
    			OUT_PATH = argGroup[i+1];
    		}
    		if (arg.equals("-i")){
    			IN_PATH = argGroup[i+1];
    		}
    		if (arg.equals("-r")){
    			RSCRIPT_PATH = argGroup[i+1];
    		}
    	}
    	
    }
    
    private void optionHandler(String[] argGroup) {
    	
    }
    
    private void setHelpMessagePath() {
    	StringBuilder pathToHelpMessages = new StringBuilder(System.getProperty("user.dir"));
    	pathToHelpMessages.append("\\src\\text_files\\");
    	HELP_MESSAGES_PATH = pathToHelpMessages;
    }
    
    
    
    private void printHelpMessage() {
    	
    	StringBuilder pathToHelpMessage = HELP_MESSAGES_PATH;
    	pathToHelpMessage.append("HelpMessage");
  
    	
    	Scanner helpMessageFile = null;
		try {
			helpMessageFile = new Scanner(new File(pathToHelpMessage.toString()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	while (helpMessageFile.hasNextLine())
    	{
    	   System.out.println(helpMessageFile.nextLine());
    	}

    }

	public String getRSCRIPT_PATH() {
		return RSCRIPT_PATH;
	}


	public String getIN_PATH() {
		return IN_PATH;
	}



	public String getOUT_PATH() {
		return OUT_PATH;
	}



	public String getREFERENCE_GENOME_LOCATION() {
		return REFERENCE_GENOME_LOCATION;
	}


}
