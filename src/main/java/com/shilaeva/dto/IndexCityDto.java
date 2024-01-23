package com.shilaeva.dto;

import com.shilaeva.entities.City;

public record IndexCityDto(int index, CityDto cityDto) {
    public static IndexCityDto asDto(int index, City city) {
        return new IndexCityDto(index, CityDto.asDto(city));
    }
}
