package com.shilaeva.services;

import com.shilaeva.dto.CityDto;
import com.shilaeva.dto.IndexCityDto;
import com.shilaeva.dto.RegionDto;
import com.shilaeva.entities.City;
import com.shilaeva.exceptions.CityException;
import com.shilaeva.interfaces.ICitiesReader;
import com.shilaeva.interfaces.ICitiesService;
import com.shilaeva.interfaces.ICitiesSorting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CitiesService implements ICitiesService {
    private final ICitiesReader citiesReader;
    private List<City> cities = new ArrayList<>();

    public CitiesService(ICitiesReader citiesReader) {
        this.citiesReader = citiesReader;
    }

    @Override
    public List<CityDto> getCities() {
        return cities.stream().map(CityDto::asDto).toList();
    }

    @Override
    public List<CityDto> readCities() {
        cities = citiesReader.readCities();

        if (cities == null || cities.isEmpty()) {
            throw CityException.citiesNotFound();
        }

        return cities.stream().map(CityDto::asDto).toList();
    }

    @Override
    public void sortCitiesList(ICitiesSorting sortingAlgorithm) {
        if (cities == null || cities.isEmpty()) {
            throw CityException.citiesListIsEmpty();
        }

        sortingAlgorithm.execute(cities);
    }

    @Override
    public IndexCityDto findTheMostPopulatedCity() {
        if (cities == null || cities.isEmpty()) {
            throw CityException.citiesListIsEmpty();
        }

        City[] citiesArray = cities.toArray(City[]::new);
        int resultCityIndex = 0;

        for (int i = 1; i < citiesArray.length; ++i) {
            if (citiesArray[i].getPopulation() > citiesArray[resultCityIndex].getPopulation()) {
                resultCityIndex = i;
            }
        }

        return IndexCityDto.asDto(resultCityIndex, citiesArray[resultCityIndex]);
    }

    @Override
    public List<RegionDto> countCitiesInRegions() {
        if (cities == null || cities.isEmpty()) {
            throw CityException.citiesListIsEmpty();
        }

        Map<String, Integer> regionCities = new HashMap<>();
        cities.forEach(city-> {
            regionCities.put(city.getRegion(), regionCities.getOrDefault(city.getRegion(), 0) + 1);
        });

        return regionCities.entrySet().stream().map(RegionDto::asDto).toList();
    }
}
