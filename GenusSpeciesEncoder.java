import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * This program takes data from a CSV file of genus and species names for animals,
 *     creates a shorthand code for each animal name, and writes the genus, species, 
 *     and code back to the CSV file.
 * @author Mitchell Powell
 */

public class GenusSpeciesEncoder {
	//Lists of genus, species, and codenames for each animal.
	static List<String> genus = new ArrayList<String>();
	static List<String> species = new ArrayList<String>();
	static List<String> code = new ArrayList<String>();
	
	//Path created to read from file.
	static Path p = Paths.get("GenusSpecies.csv");
	
	//Counter used to determine how many generated codenames are unique.
	static int nonuniqueCodenames = 0;
	
	public static void main(String[] args) {	
		try {
			//Initialize startup parameters.
			initialize(args);
			//Read data from file
			read();
			//Create codenames from animal names.
			createCodes();
			//Write animal names and codenames to new file.
			write();
			//Program finished successfully.
			exit(true, null);
		} catch (MyException me) {
			//Program terminated due to error.
			exit(false, me);
		}
	}
	
	/**
	 * Initializes the path to a generic file, or the name of a specified file.
	 *     Terminates the program if too many arguments are given.
	 * @throws MyException
	 */
	private static void initialize(String[] args) throws MyException {
		//File to read from was not specified. Use a generic file name.
		if(args.length == 0) {
			p = Paths.get("GenusSpecies.csv");
		}
		
		//File to read from was specified. Use the specified file name.
		else if(args.length == 1){
			p = Paths.get(args[0]);
		}
		
		//More than one argument. Terminate.
		else {
			throw new MyException("Improper number of arguments.");
		}
	}
	
	/**
	 * Reads genus and species names from file.
	 * @throws MyException
	 */
	private static void read() throws MyException {
		try {
			//Create new scanner from the pathname given by p.
			Scanner s = new Scanner(p);
			//Initialize the delimiter to scan for , or newline characters.
			s.useDelimiter(",|\\r\\n|\\r|\\n");
			
			//Read the genus and species names until the end of the file.
			while(s.hasNext()) {
				genus.add(s.next());
				species.add(s.next());
			}
			
			//Close the scanner.
			s.close();
			
			//Check to see if an equal number of genus and species names were detected. If not, throw exception.
			if(genus.size() != species.size())
				throw new MyException("Illegal entry. Detected unequal number of genus and species names.");
			
		} catch (IOException e) {
			//Print a generic error message and a stack trace for debugging. Throw MyException to terminate program.
			throw new MyException("An I/O Error has occurred while reading the file.\n" + e.getStackTrace().toString());
		}
	}
	
	/**
	 * Creates a list of codenames for each animal, and keeps track of any codenames which aren't unique.
	 */
	private static void createCodes() throws MyException {		
		try {
			//For every genus and species name,
			for(int i = 0; i < genus.size(); i++) {
				//Flag tells if a codename is unique compared to previous codenames.
				boolean unique = false;
				//String holds a potential codename to be checked for uniqueness.
				String potentialCode = null;
				//Number of characters to come from genus name and species name.
				int c = 3;
				//boolean match = false;
				
				//The loop below creates a codename and checks for uniqueness. If codename is unique, it is added to the list
				//of codenames, and loop exits. If codename isn't unique, the last letter is changed to create a new
				//codename, until no more possible codenames can be created.
				
				//While the codename we create is not unique and there are more possible letters in the species name to use,
				while(!unique && c <= species.get(i).length()) {
					//Assume the codename we are about to create is unique.
					unique = true;
					//Create a codename from:
					//              first 3 letters of genus name,
					potentialCode = genus.get(i).substring(0, 3)
				    //              first 2 letters of species name,
							      + species.get(i).substring(0, 2)
					//              the letter at the (c-1)th index of the species name
							      + species.get(i).charAt(c-1);
					//Note:
					//The substring method gets characters from [x,y). That means substring(x,y) returns all characters
					//x through y - 1.
					//The charAt method gets the character from a specific index, starting with 0 and ending with the
					//number of letters in the word - 1.
					
					//Capitalize the potentialCode.
					potentialCode = potentialCode.toUpperCase();
					
					//Run through all previously added codenames to check for uniqueness.
					for(int j = 0; j < i; j++) {
						//If the potential code matches an existing code, set unique flag to false, and stop
						//checking the potential code against other codes.
						if(potentialCode.equalsIgnoreCase(code.get(j))) {
							unique = false;
							j = i;
						}
					}
					
					//Increment c to try other possible codenames.
					c++;
				}
				
				//If a unique codename was not found,
				if(!unique && c == species.get(i).length() + 1) {
					//Add the nonunique codename to the list with 3 asterisks by it.
					code.add(potentialCode + "***");
					//Increment the nonuniqueCodenames counter.
					nonuniqueCodenames++;
				}
				//If a unique codename was found,
				else {
					//Add the potential code to the list of codes.
					code.add(potentialCode);
				}
			}
		} catch (IndexOutOfBoundsException e) {
			//Print a generic error message and a stack trace for debugging. Throw MyException to terminate program.
			throw new MyException("Index out of bounds error has occurred while creating codes.\n" + e.getStackTrace().toString());
		}
	}
	
	/**
	 * Writes genus, species, codes into a new CSV file.
	 * @throws MyException
	 */
	private static void write() throws MyException {
		try {
			//Create a file writer to write to the file given by p, and set the file writer to overwrite mode.
			FileWriter fw = new FileWriter(p.getFileName().toString().substring(0, p.getFileName().toString().length() - 4) 
					                       + ".csv", false);
			
			//Iterate through the genus, species, and code lists to print each entry to the file.
			for(int i = 0; i < genus.size(); i++) {
				fw.write(genus.get(i) + "," + species.get(i) + "," + code.get(i));
				
				//Print the number of unique codenames created vs. the total number of animal names supplied.
				if(i == 0) {
					fw.write(",Unique names generated:");
				}
				else if(i == 1) {
					fw.write(("," + (genus.size() - nonuniqueCodenames)) + " of " + genus.size());
				}
				
				fw.write("\n");
			}
		
			//Close the file writer.
			fw.close();
			
		} catch (IOException e) {
			//Print a generic error message and a stack trace for debugging. Throw MyException to terminate program.
			throw new MyException("An I/O Error has occurred while writing the file.\n" + e.getStackTrace().toString());
		}
	}
	
	/**
	 * Prints successful exit message upon successful completion of the program, or
	 *     error message upon unsuccessful completion of the program
	 *     @param exitSuccessful boolean flag indicates if program successfully completed
	 *     @param me exception containing message to be printed if program was unsuccessful
	 */
	private static void exit(boolean exitSuccessful, MyException me) {
		if(exitSuccessful)
			JOptionPane.showMessageDialog(null, "Program completed successfully.");
		else
			JOptionPane.showMessageDialog(null, me.getMessage() + "\nProgram Terminated.");
	}
	
	/**
	 * When a method throws a MyException, the main method terminates.
	 */
	public static class MyException extends Exception {
		MyException() {super();}
		MyException(String s) {super(s);}
	}
}