Program: GenusSpeciesEncoder
Author: Mitchell Powell
Date Revised: 8/26/2017

I. INTRODUCTION
This README was created for the purpose of guiding the user through the use of
	the GenusSpeciesEncoder.

II. PURPOSE
The GenusSpeciesEncoder is a script written to allow users to create shorthand
	codes for the scientific name of different species. The GenusSpeciesEncoder
	is formatted to read the scientific names of animals from a Comma Separated
	Value File (a CSV file for short). Once created, the codes, along with the
	scientific names of each species are output to a new CSV file.
	
III. PROGRAM REQUIREMENTS
In order to run the GenusSpeciesEncoder, you will need the 
    'GenusSpeciesEncoder.jar' file (included in the same folder as this README)
	and a CSV file of the proper format detailed below.
	
IV. FORMATTING REQUIREMENTS		
The GenusSpeciesEncoder requires a specific format in order to work properly.
    First, the name of the CSV file must be 'GenusSpecies.csv'. The Genus 
	name of each animal must be in the first column, and the Species name must
	be in the second column. If you open the CSV file in a text editor such as
	Notepad, the file should look like the following:
	
    Genus1,Species1
    Genus1,Species2
    Genus2,Species3
    etc.
	
The CSV file should contain only these two columns, and there should be a Genus
    and a Species on every row.
	
*If your CSV file contains other data, you can open the file with a
*   spreadsheet application (such as Microsoft Excel), and copy the rows
*   containing the genus and species names into a new spreadsheet. Save
*   the new spreadsheet file as a CSV.
	
Failure to follow this format may result in errors during execution.

V. LOCATION REQUIREMENTS
The 'GenusSpeciesEncoder.jar' file must be located in the same folder as the
    'GenusSpecies.csv' file, in order to properly execute.
	
Failure to do so may result in errors during execution.

VI. EXECUTION
Execution of the program is as simple as double clicking the 
    'GenusSpeciesEncoder.jar' file.

VII. OUTPUT
The output will be a file located in the same folder as 'GenusSpecies.csv' and
    'GenusSpeciesEncoder.jar' called 'GenusSpeciesCode.csv'. The file will
	contain three columns. The first will contain a genus name, the second a
	species name, and the third an X digit codename. The GenusSpeciesEncoder
	keeps track of the number of unique codenames created vs. the total number
	of names originally submitted. Duplicate codenames (if they occur) are
	denoted by a '***' at the end of the codename. If opened in a text editor,
	the file would look like the following:
	
	Genus1,Species1,Codename1,14999 of 15000
	Genus1,Species2,Codename2
	Genus2,Species3,Codename2***
	etc.
	
VIII. TROUBLESHOOTING
The following are common error codes that may occur during execution and the most
    likely ways to fix them:
    
    a. Illegal entry. Detected unequal number of genus and species names.
       -The number of genus and species names detected was unequal. Either,
        the number of species and genus names are unequal, or there are more
        than two columns in the CSV files.
    b. An I/O Error has occurred while reading the file.
       -It is possible that the CSV file is named incorrectly. Make sure it
        is named 'GenusSpecies.csv'.
    c. Index out of bounds error has occurred while creating codes.
       -It is possible that either a genus or species name has been left
        blank or a genus or species name is less than 3 characters.

IX. CONTACT
For further questions, you may email me at GenusSpeciesEncoder@gmail.com.
    Please note that this is not an email that I can check regularly, so
	response times may be slow.