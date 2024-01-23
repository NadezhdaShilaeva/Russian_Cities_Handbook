package com.shilaeva;

import com.shilaeva.algorithms.CitiesSortingByDistrictAndName;
import com.shilaeva.algorithms.CitiesSortingByName;
import com.shilaeva.algorithms.ReverseSorting;
import com.shilaeva.dao.CSVReader;
import com.shilaeva.dto.CityDto;
import com.shilaeva.dto.IndexCityDto;
import com.shilaeva.exceptions.CityException;
import com.shilaeva.interfaces.ICitiesReader;
import com.shilaeva.interfaces.ICitiesService;
import com.shilaeva.interfaces.ICitiesSorting;
import com.shilaeva.services.CitiesService;

import java.util.List;

public class Main {
    private final ICitiesService citiesService;

    public Main() {
        String filePath = "data/Задача ВС Java Сбер.csv";
        String csvDelimiterPattern = ";|\r\n";
        ICitiesReader csvReader = new CSVReader(filePath, csvDelimiterPattern);

        citiesService = new CitiesService(csvReader);
    }

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        try {
            readData();
            //sortData();
            findCity();
            countCities();
        } catch (CityException e) {
            System.err.println("CityException: " + e.getMessage());
        }
    }

    public void readData() {
        List<CityDto> cities = citiesService.readCities();

        System.out.println("Cities received from csv-file:");
        cities.forEach(System.out::println);
        System.out.println();
    }

    public void sortData() {
        ICitiesSorting sortingByName = new CitiesSortingByName();
        citiesService.sortCitiesList(sortingByName);

        System.out.println("Cities sorted by name:");
        citiesService.getCities().forEach(System.out::println);
        System.out.println();

        ICitiesSorting sortingByDistrictAndName = new CitiesSortingByDistrictAndName();
        citiesService.sortCitiesList(sortingByDistrictAndName);

        System.out.println("Cities sorted by district and then by name:");
        citiesService.getCities().forEach(System.out::println);
        System.out.println();

        citiesService.sortCitiesList(new ReverseSorting(sortingByName));

        System.out.println("Cities sorted by name in reverse order:");
        citiesService.getCities().forEach(System.out::println);
        System.out.println();

        citiesService.sortCitiesList(new ReverseSorting(sortingByDistrictAndName));

        System.out.println("Cities sorted by district and then by name in reverse order:");
        citiesService.getCities().forEach(System.out::println);
        System.out.println();
    }

    public void findCity() {
        IndexCityDto city = citiesService.findTheMostPopulatedCity();

        System.out.println("The most populated city in Russia:");
        System.out.printf("[%d] %s: %,d people%n", city.index(), city.cityDto().name(), city.cityDto().population());
        System.out.println();
    }

    public void countCities() {
        System.out.println("The number of cities in each region of Russia:");
        citiesService.countCitiesInRegions().forEach(System.out::println);
        System.out.println();
    }
}
