package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class ReadWriteFile {

	private static String CountrylistFile = "/CRUD-Geography/SE_CountryList.csv";
	private static String SEPARATOR = ";";
	
	//Methode um ein Excel-File (CSV-File) einzulesen
	
	public void loadCountry() {
		
		File file = new File(CountrylistFile); 
		
		//Reader wird initilisiert um File zu lesen
		try(Reader inReader = new FileReader(file)){
			//Reader wird in BufferedReader umgewandelt um zeilenweise zu lesen
			BufferedReader in = new BufferedReader(inReader);
			
			//Loop wird erstellt um Zeilen zu lesen und in Observable list zu speichern
			String line = in.readLine();
			while(line != null) {
				Country country = readCountry(line);
				allData.add(country);
				line = in.readLine();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		//die eingelesenen Zeilen werden zum Country Objekt gemacht
	private Country readCountry(String line) {
		String[] attributes = line.split(SEPARATOR);
		String name = attributes[0];
		double area = Double.parseDouble(attributes[1]);
		int population = Integer.parseInt(attributes[2]);
		FormOfGovernment formOfGovernment = FormOfGovernment.valueOf(attributes[3]);
		Country country = new Country(name, area, population, formOfGovernment);
		
		return country;
	}
	
	public void saveCountry() {
		
		File countryFile = new File(CountrylistFile);
		
		try(Writer out = new FileWriter(countryFile)){
			for(GovernedRegion country : allData) {
				String line = writeCountry(country);
				out.write(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String writeCountry(GovernedRegion country) {
		String line = country.getName() + SEPARATOR + country.getArea() + SEPARATOR +
				country.getPopulation() + SEPARATOR + country.getFormOfGovernment() + "\n";
		return line;
	}
}
}
