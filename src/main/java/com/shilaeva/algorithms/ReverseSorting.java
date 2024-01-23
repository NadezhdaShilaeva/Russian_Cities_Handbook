package com.shilaeva.algorithms;

import com.shilaeva.entities.City;
import com.shilaeva.interfaces.ICitiesSorting;

import java.util.Collections;
import java.util.List;

public class ReverseSorting implements ICitiesSorting {
    ICitiesSorting sortingAlgorithm;

    public ReverseSorting(ICitiesSorting sortingAlgorithm) {
        this.sortingAlgorithm = sortingAlgorithm;
    }

    @Override
    public void execute(List<City> cities) {
        sortingAlgorithm.execute(cities);
        Collections.reverse(cities);
    }
}
