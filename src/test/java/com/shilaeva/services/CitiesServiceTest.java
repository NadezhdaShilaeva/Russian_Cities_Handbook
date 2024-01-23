package com.shilaeva.services;

import com.shilaeva.algorithms.CitiesSortingByDistrictAndName;
import com.shilaeva.algorithms.CitiesSortingByName;
import com.shilaeva.algorithms.ReverseSorting;
import com.shilaeva.dto.CityDto;
import com.shilaeva.dto.IndexCityDto;
import com.shilaeva.dto.RegionDto;
import com.shilaeva.entities.City;
import com.shilaeva.exceptions.CityException;
import com.shilaeva.interfaces.ICitiesReader;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CitiesServiceTest {
    @Mock
    private ICitiesReader csvReader;

    @InjectMocks
    private CitiesService citiesService;
    private MockitoSession mockitoSession;

    private List<City> cities;
    private CityDto cityMoscowDto;
    private CityDto cityPeterDto;
    private CityDto cityIzhevskDto;
    private CityDto cityShcherbinkaDto;
    private RegionDto regionMoscowDto;
    private RegionDto regionPeterDto;
    private RegionDto regionUdmurtiaDto;

    @BeforeEach
    void setUp() {
        mockitoSession = Mockito.mockitoSession().initMocks(this).startMocking();

        City cityMoscow = new City("Москва", "Москва", "Центральный", 11514330, "1147");
        cityMoscowDto = CityDto.asDto(cityMoscow);
        City cityPeter = new City("Санкт-Петербург", "Санкт-Петербург", "Северо-Западный", 4848742, "1703");
        cityPeterDto = CityDto.asDto(cityPeter);
        City cityIzhevsk = new City("Ижевск", "Удмуртия", "Приволжский", 628117, "1760");
        cityIzhevskDto = CityDto.asDto(cityIzhevsk);
        City cityShcherbinka = new City("Щербинка", "Москва", "Центральный", 32836, "14 век");
        cityShcherbinkaDto = CityDto.asDto(cityShcherbinka);

        cities = new ArrayList<>(List.of(cityIzhevsk, cityMoscow, cityPeter, cityShcherbinka));

        regionMoscowDto = RegionDto.asDto(Map.entry("Москва", 2));
        regionPeterDto = RegionDto.asDto(Map.entry("Санкт-Петербург", 1));
        regionUdmurtiaDto = RegionDto.asDto(Map.entry("Удмуртия", 1));
    }

    @AfterEach
    void tearDown() {
        mockitoSession.finishMocking();
    }

    @Test
    void readCitiesFromCSVFile_getListOfCities() {
        Mockito.when(csvReader.readCities()).thenReturn(cities);

        List<CityDto> citiesResult = citiesService.readCities();

        assertEquals(List.of(cityIzhevskDto, cityMoscowDto, cityPeterDto, cityShcherbinkaDto), citiesResult);
        Mockito.verify(csvReader).readCities();
    }

    @Test
    void readCitiesFromCSVFile_throwsException() {
        Mockito.when(csvReader.readCities()).thenReturn(List.of());

        assertThrows(CityException.class, () -> citiesService.readCities());
        Mockito.verify(csvReader).readCities();
    }

    @Test
    void doubleReadingCitiesFromCSVFile_listOfCitiesNotRepeated() {
        Mockito.when(csvReader.readCities()).thenReturn(cities);

        citiesService.readCities();
        List<CityDto> citiesResult = citiesService.readCities();

        assertEquals(List.of(cityIzhevskDto, cityMoscowDto, cityPeterDto, cityShcherbinkaDto), citiesResult);
        Mockito.verify(csvReader, Mockito.times(2)).readCities();
    }

    @Test
    void getCities_getListOfAllCities() {
        Mockito.when(csvReader.readCities()).thenReturn(cities);
        citiesService.readCities();

        List<CityDto> citiesResult = citiesService.getCities();

        assertEquals(List.of(cityIzhevskDto, cityMoscowDto, cityPeterDto, cityShcherbinkaDto), citiesResult);
    }

    @Test
    void getCities_emptyCitiesList_getEmptyListOfCities() {
        List<CityDto> citiesResult = citiesService.getCities();

        assertEquals(List.of(), citiesResult);
    }

    @Test
    void sortCitiesListByName_getSortedListOfCities() {
        Mockito.when(csvReader.readCities()).thenReturn(cities);
        citiesService.readCities();

        citiesService.sortCitiesList(new CitiesSortingByName());

        assertEquals(List.of(cityIzhevskDto, cityMoscowDto, cityPeterDto, cityShcherbinkaDto), citiesService.getCities());
    }

    @Test
    void sortCitiesListByDistrictAndName_getSortedListOfCities() {
        Mockito.when(csvReader.readCities()).thenReturn(cities);
        citiesService.readCities();

        citiesService.sortCitiesList(new CitiesSortingByDistrictAndName());

        assertEquals(List.of(cityIzhevskDto, cityPeterDto, cityMoscowDto, cityShcherbinkaDto), citiesService.getCities());
    }

    @Test
    void sortCitiesListByNameReversed_getSortedListOfCities() {
        Mockito.when(csvReader.readCities()).thenReturn(cities);
        citiesService.readCities();

        citiesService.sortCitiesList(new ReverseSorting(new CitiesSortingByName()));

        assertEquals(List.of(cityShcherbinkaDto, cityPeterDto, cityMoscowDto, cityIzhevskDto), citiesService.getCities());
    }

    @Test
    void sortCitiesListByDistrictAndNameReversed_getSortedListOfCities() {
        Mockito.when(csvReader.readCities()).thenReturn(cities);
        citiesService.readCities();

        citiesService.sortCitiesList(new ReverseSorting(new CitiesSortingByDistrictAndName()));

        assertEquals(List.of(cityShcherbinkaDto, cityMoscowDto, cityPeterDto, cityIzhevskDto), citiesService.getCities());
    }

    @Test
    void sortEmptyCitiesList_throwsException() {
        assertThrows(CityException.class, () -> citiesService.sortCitiesList(new CitiesSortingByName()));
    }

    @Test
    void findTheMostPopulatedCity_theMostPopulatedCityFound() {
        Mockito.when(csvReader.readCities()).thenReturn(cities);
        citiesService.readCities();

        IndexCityDto cityResult = citiesService.findTheMostPopulatedCity();

        assertEquals(IndexCityDto.asDto(1, cities.get(1)), cityResult);
    }


    @Test
    void findTheMostPopulatedCity_afterSorting_theMostPopulatedCityFound() {
        Mockito.when(csvReader.readCities()).thenReturn(cities);
        citiesService.readCities();

        citiesService.sortCitiesList(new CitiesSortingByDistrictAndName());
        IndexCityDto cityResult = citiesService.findTheMostPopulatedCity();

        assertEquals(IndexCityDto.asDto(2, cities.get(2)), cityResult);
    }

    @Test
    void findTheMostPopulatedCity_emptyCitiesList_throwsException() {
        assertThrows(CityException.class, () -> citiesService.findTheMostPopulatedCity());
    }

    @Test
    void countCitiesInRegions_getListOfRegions() {
        Mockito.when(csvReader.readCities()).thenReturn(cities);
        citiesService.readCities();

        List<RegionDto> regionsResult = citiesService.countCitiesInRegions();

        assertTrue(regionsResult.containsAll(List.of(regionMoscowDto, regionPeterDto, regionUdmurtiaDto)));
    }

    @Test
    void countCitiesInRegions_emptyCitiesList_throwsException() {
        assertThrows(CityException.class, () -> citiesService.countCitiesInRegions());
    }
}