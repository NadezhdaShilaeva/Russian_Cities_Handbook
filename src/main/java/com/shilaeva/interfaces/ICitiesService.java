package com.shilaeva.interfaces;

import com.shilaeva.dto.CityDto;
import com.shilaeva.dto.IndexCityDto;
import com.shilaeva.dto.RegionDto;

import java.util.List;

public interface ICitiesService {
    List<CityDto> getCities();
    List<CityDto> readCities();
    void sortCitiesList(ICitiesSorting sortingAlgorithm);
    IndexCityDto findTheMostPopulatedCity();
    List<RegionDto> countCitiesInRegions();
}
