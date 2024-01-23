package com.shilaeva.dto;

import java.util.Map;

public record RegionDto(String name, int citiesCount) {
    public static RegionDto asDto(Map.Entry<String, Integer> region) {
        return new RegionDto(region.getKey(), region.getValue());
    }

    @Override
    public String toString() {
        return name + ": " + citiesCount +
                (citiesCount == 1 ? " city" : " cities");
    }
}
