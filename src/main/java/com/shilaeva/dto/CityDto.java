package com.shilaeva.dto;

import com.shilaeva.entities.City;

public record CityDto(String name, String region, String district, int population, String foundation) {
    public static CityDto asDto(City city) {
        return new CityDto(city.getName(), city.getRegion(), city.getDistrict(), city.getPopulation(),
                city.getFoundation());
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", district='" + district + '\'' +
                ", population=" + population +
                ", foundation='" + foundation + '\'' +
                '}';
    }
}
