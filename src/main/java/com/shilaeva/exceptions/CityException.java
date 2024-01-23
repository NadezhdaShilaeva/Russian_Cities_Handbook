package com.shilaeva.exceptions;

public class CityException extends RuntimeException {
    private CityException(String message) {
        super(message);
    }

    public static CityException citiesNotFound() {
        return new CityException("Cannot read file or cities were not found.");
    }

    public static CityException citiesListIsEmpty() {
        return new CityException("List of cities is empty.");
    }
}
