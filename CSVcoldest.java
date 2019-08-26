import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.io.File;
import java.util.Scanner;

public class CSVcoldest {
    public CSVRecord coldestHourInFile(CSVParser parser) {
        //start with smallestSoFar as nothing
        CSVRecord smallestSoFar = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            smallestSoFar = getsmallestOfTwo(currentRow, smallestSoFar);
        }
        //The smallestSoFar is the answer
        return smallestSoFar;
    }
    
    public void testColdestHourInFile() {
        FileResource fr = new FileResource();
        CSVRecord smallest = coldestHourInFile(fr.getCSVParser());
        System.out.println("coldest temperature was " + smallest.get("TemperatureF") +
                   " at " + smallest.get("DateUTC") + " with wind speed being " + smallest.get("Wind SpeedMPH") + "\n" );
    }

    public CSVRecord getsmallestOfTwo (CSVRecord currentRow, CSVRecord smallestSoFar) {
        //If smallestSoFar is nothing
        if (smallestSoFar == null) {
            smallestSoFar = currentRow;
        }
        //Otherwise
        else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
            //Check if currentRow’s temperature < smallestSoFar’s
            if ((currentTemp < smallestTemp)&&(currentTemp != -9999)) {
                //If so update smallestSoFar to currentRow
                smallestSoFar = currentRow;
            }
        }
        return smallestSoFar;
    }
    
    public String fileWithColdestTemperature() {
        CSVRecord smallestSoFar = null;
        CSVRecord smallest = null ;
        String smallestSoFarFilePath = null ;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            // use method to get smallest in file.
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            // use method to compare two records
            smallest = getsmallestOfTwo(currentRow, smallestSoFar);
            if (smallest!=smallestSoFar) {
                smallestSoFarFilePath = f.getAbsolutePath() ;
                smallestSoFar = smallest ;
            }
            
            
        }
        //The largestSoFarFileName is the answer
        return smallestSoFarFilePath;
    }
    
    
    public void testFileWithColdestTemperature() {
        String coldestFilePath = fileWithColdestTemperature();
        File f = new File(coldestFilePath);
        System.out.println("coldest day was in file " + f.getName() );
        FileResource fr = new FileResource(coldestFilePath);
        CSVRecord coldestInFile =coldestHourInFile(fr.getCSVParser()) ;
        System.out.println("coldest temperature on that day was " + coldestInFile.get("TemperatureF") ) ;
        System.out.println("All the Temperatures on the coldest day were:") ;
        for (CSVRecord currentRow : fr.getCSVParser()) {
            // use method to compare two records
             System.out.println(currentRow.get("DateUTC") + " : " + currentRow.get("TemperatureF") ) ;
        }
        System.out.println("\n");
    }
    
    public CSVRecord getsmallestHumidityOfTwo (CSVRecord currentRow, CSVRecord smallestSoFar) {
        //If smallestSoFar is nothing
        if (smallestSoFar == null) {
            smallestSoFar = currentRow;
        }
        //Otherwise
        else {
            try {
                double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
                double smallestHumidity = Double.parseDouble(smallestSoFar.get("Humidity"));
                //Check if currentRow’s Humidity < smallestSoFar’s
                if (currentHumidity < smallestHumidity) {
                    //If so update smallestSoFar to currentRow
                    smallestSoFar = currentRow;
                }
            }
            catch (NumberFormatException e){}
        }
        return smallestSoFar;
    }

    public CSVRecord lowestHumidityInFile (CSVParser parser) {
        //start with smallestSoFar as nothing
        CSVRecord smallestSoFar = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            smallestSoFar = getsmallestHumidityOfTwo(currentRow, smallestSoFar);
        }
        //The smallestSoFar is the answer
        return smallestSoFar;
    }
    
    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVRecord csv = lowestHumidityInFile(fr.getCSVParser());
        System.out.println("lowest humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC") + "\n" );
    }
    
    public CSVRecord lowestHumidityInManyFiles() {
        CSVRecord lowestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            // use method to get largest in file.
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            // use method to compare two records
            lowestSoFar = getsmallestHumidityOfTwo(currentRow, lowestSoFar);
        }
        //The largestSoFar is the answer
        return lowestSoFar;
        }
        
    public void testLowestHumidityInManyFiles() {
    CSVRecord lowest = lowestHumidityInManyFiles() ;
    System.out.println("lowest humidity was " + lowest.get("Humidity") + " at " + lowest.get("DateUTC") + "\n" );
    }
    
    public double averageTemperatureInFile (CSVParser parser) {
        double sum = 0 ;
        int count = 0 ;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            if (currentTemp != -9999) {
            sum = sum + currentTemp ;
            count = count + 1 ;
        }
        }
        //The smallestSoFar is the answer
        return sum/count ;
    }
    
    public void testAverageTemperatureInFile() {
        FileResource fr = new FileResource();
        double avg = averageTemperatureInFile(fr.getCSVParser());
        System.out.println("Average temprature in file is " + avg + "\n" );
    }
    
    public double averageTemperatureWithHighHumidityInFile (CSVParser parser, Integer value) {
        double sum = 0 ;
        int count = 0 ;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            try {
                double humidity = Double.parseDouble(currentRow.get("Humidity"));
                if (humidity >= value) {
                    double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                    if (currentTemp != -9999) {
                    sum = sum + currentTemp ;
                    count = count + 1 ;
                    }
                }
            }
            catch (NumberFormatException e){}
        }
        //The smallestSoFar is the answer
        if (count==0) return 0 ;
        return sum/count ;
    }
    
    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fr = new FileResource();
        System.out.println("\nNow type your selected humidity then press Enter ..");
        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        String input = scan.nextLine();
        double avg = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(),Integer.parseInt(input) );
        if (avg==0) { 
            System.out.println("No temperatures with that humidity \n");
        }
        else System.out.println("Average Temp when high Humidity is " + avg + "\n");
    }
    
    public static void main(String[] args) {
        CSVcoldest run = new CSVcoldest();
        String input = "none" ;
        while (input != "7") {
            System.out.println("This Program has certain functions to analyze weather Big Data records stored as CSV files, each file includes one full day record \nin the data folder for the years 2012-2015 in the United States, choose what function you want to apply: ");
            System.out.println("press 1 + Enter : To check what is the coldest temprature and time + wind speed at that time in a specific file");
            System.out.println("press 2 + Enter : To check among a number of files what is the day that has the coldest temprature, what is that temprature & a list of all tempratures on that day");
            System.out.println("press 3 + Enter : To check what is the lowest humidity and the time of it in a specific file");
            System.out.println("press 4 + Enter : To check among a number of files what is the lowest humidity and the time it happened");
            System.out.println("press 5 + Enter : To caluclate the average temprature on a specific day represented by a specefic file");
            System.out.println("press 6 + Enter : To caluclate the average temprature of only those temperatures when the humidity was greater than or equal to your selected value in a specefic file");
            System.out.println("press 7 + Enter : To end the program") ;
            Scanner scan = new Scanner(System.in);  // Create a Scanner object
            input = scan.nextLine();
            switch(input) {
              case "1":
                run.testColdestHourInFile();
                break;
              case "2":
                run.testFileWithColdestTemperature() ;
                break;
              case "3":
                run.testLowestHumidityInFile() ;
                break;
              case "4":
                run.testLowestHumidityInManyFiles() ;
                break;
              case "5":
                run.testAverageTemperatureInFile() ;
                break;
              case "6":
                run.testAverageTemperatureWithHighHumidityInFile() ;
                break;
              case "7":
                System.out.println("The program is stopped") ;
                return;
              default:
                System.out.println("Input " + input + " is not valid please choose a valid input \n");
            }
        }
    }
}

