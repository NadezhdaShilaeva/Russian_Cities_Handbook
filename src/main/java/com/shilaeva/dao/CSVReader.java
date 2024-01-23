package com.shilaeva.dao;

import com.shilaeva.entities.City;
import com.shilaeva.interfaces.ICitiesReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CSVReader implements ICitiesReader {
    private final String filePath;
    private final String scvDelimiterPattern;

    public CSVReader(String filePath, String scvDelimiterPattern) {
        this.filePath = filePath;
        this.scvDelimiterPattern = scvDelimiterPattern;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getScvDelimiterPattern() {
        return scvDelimiterPattern;
    }

    public List<City> readCities() {
        String name;
        String region;
        String district;
        int population;
        String foundation;
        List<City> cities = new ArrayList<>();

        try(Scanner inputStream = new Scanner(new File(filePath))) {
            inputStream.useDelimiter(scvDelimiterPattern);

            while(inputStream.hasNext()) {
                inputStream.nextInt(); // skipping id of the city
                name = inputStream.next();
                region = inputStream.next();
                district = inputStream.next();
                population = inputStream.nextInt();
                foundation = inputStream.next();

                cities.add(new City(name, region, district, population, foundation));
            }
        } catch (FileNotFoundException e){
            System.err.println("FileNotFoundException: " + e.getMessage());
            e.printStackTrace();
        } catch (InputMismatchException e) {
            System.err.println("InputMismatchException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

        return cities;
    }
}
