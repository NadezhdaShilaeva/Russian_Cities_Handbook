package com.shilaeva.algorithms;

import com.shilaeva.entities.City;
import com.shilaeva.interfaces.ICitiesSorting;

import java.util.Comparator;
import java.util.List;

public class CitiesSortingByDistrictAndName implements ICitiesSorting {
    @Override
    public void execute(List<City> cities) {
        cities.sort(Comparator.comparing(City::getDistrict).thenComparing(City::getName));
    }
}
