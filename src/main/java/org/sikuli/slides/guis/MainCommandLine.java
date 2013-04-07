/**
Khalid
*/
package org.sikuli.slides.guis;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import org.sikuli.slides.utils.Constants;
import org.sikuli.slides.utils.MyFileFilter;

/**
 * sikuli-slides Command Line tool using POSIX like options.
 * 
 * @author Khalid
 *
 */
public class MainCommandLine {
	private static final String applicationName = "sikuli-slides";
	private static final String versionNumber="1.1.0";
	private static final String commandLineSyntax = "java -jar "+
			applicationName+"-"+versionNumber+".jar "+
			"presentation_file.pptx";
	private static final String fileNotFoundError="No such file.";
	/**
	* Parse the command-line arguments as POSIX like options (one character long option).
	* @param args Command-line arguments
	* @return the .pptx file name
	*/
	private static String usePosixParser(final String[] args)  
	{
		final CommandLineParser parser = new PosixParser();  
	    final Options posixOptions = getPOSIXCommandLineOptions();  
	    CommandLine cmd;  
	    try{
	    	cmd = parser.parse(posixOptions, args);
	        if (cmd.hasOption("h")){
        		printHelp(getPOSIXCommandLineOptions(), 120, 
    					"sikuli-slides -- help", "sikuli-slides -- (END)", 5, 3, true, System.out);
        		return null;
	        }
	    	else if (cmd.hasOption("w")){
	    		int wait=Integer.parseInt(cmd.getOptionValue("w"));
	        	Constants.MaxWaitTime=wait;
	    	}
	        
	        // check arguments
	        final String[] remainingArguments = cmd.getArgs();
	        if(remainingArguments==null||remainingArguments.length==0){
	        	printUsage(applicationName, getPOSIXCommandLineOptions(), System.out);
	        	return null;
	        }
	        else if(remainingArguments.length>0){
	        	MyFileFilter myFileFilter=new MyFileFilter();
	        	String FileName=remainingArguments[0];
	        	File source_file=new File(FileName);
	        	if(myFileFilter.accept(source_file)){
	        		if(source_file.exists()){
	        			showTextHeader(System.out);
	        			displayBlankLine();
	        			return source_file.getAbsolutePath();
	        		}
	        		else{
	        			System.out.write(fileNotFoundError.getBytes()); 
	        			displayBlankLine();
	        			printUsage(applicationName, getPOSIXCommandLineOptions(), System.out);
	        		}
	        	}
	        	else{
	        		printUsage(applicationName, getPOSIXCommandLineOptions(), System.out);
	        	}
	        }
	    }
	    catch (ParseException e){  
			printUsage(applicationName, getPOSIXCommandLineOptions(), System.out);
			displayBlankLine();  
	    }
    	catch(Exception exception){
			printUsage(applicationName, getPOSIXCommandLineOptions(), System.out);
			displayBlankLine();
    	}
		return null;
	}
	
	/**
	 * Return all valid POSIX command-line options
	 * @return valid POSIX command-line options
	 */
	@SuppressWarnings("static-access")
	private static Options getPOSIXCommandLineOptions() {
		final Options posixOptions=new Options();
		
		Option waitOption=OptionBuilder.withArgName( "max_wait_time" )
                .hasArg()
                .withDescription("the maximum time to wait in milliseconds to find a target on the screen (default 15000 ms)." )
                .create( "w" );
		
		Option helpOption=new Option("h", "help");
		
		posixOptions.addOption(helpOption);
		posixOptions.addOption(waitOption);
		
		return posixOptions;
	}
	
	private static void showTextHeader(final OutputStream out){
		String textHeader="sikuli-slides -- accessible visual automation";
		try{
			out.write(textHeader.getBytes());
		}
		catch (IOException ioEx){
			System.out.println(textHeader);
		}
	}
	
	/**
	 * print usage information to provided OutputStream
	 * @param applicationName Name of application to list in usage
	 * @param options Command-line options to be part of usage
	 * @param out OutputStream to which to write the usage information
	 */
	public static void printUsage(final String applicationName, final Options options,final OutputStream out)
	{
		final PrintWriter writer = new PrintWriter(out);  
	    final HelpFormatter usageFormatter = new HelpFormatter();  
	    usageFormatter.printUsage(writer, 120, commandLineSyntax, options);
	    writer.flush();
	}
	/**
	 * Write command-line tool help
	 * @param options the possible options for the command-line
	 * @param printedRowWidth the raw width
	 * @param header the header text at the beginning of the help
	 * @param footer the footer text at the end of the help
	 * @param leftPad the number of white spaces before the option
	 * @param descPad the number of white spaces before the option description
	 * @param autoUsage indicates whether the usage is displayed in the help or not
	 * @param out the OutputStream to write to
	 */
	private static void printHelp(final Options options, final int printedRowWidth,
			final String header, final String footer,
			final int leftPad, final int descPad,
			final boolean autoUsage,final OutputStream out){
		showTextHeader(System.out);
		displayBlankLine();
		final PrintWriter printWriter = new PrintWriter(out);
		final HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.printHelp(printWriter, printedRowWidth, 
				commandLineSyntax, header, options, leftPad, descPad, footer, autoUsage);
		printWriter.flush();
	}
	
	private static void displayBlankLine(){
		try {
			System.out.write("\n".getBytes());
		} catch (IOException e) {
			System.out.println();
		}
	}
	public static String runCommandLineTool(final String[] args){
		if (args.length < 1){
			printUsage(applicationName, getPOSIXCommandLineOptions(), System.out);
			return null;
		}
		else{
			return usePosixParser(args);
		}
	}
	
}
