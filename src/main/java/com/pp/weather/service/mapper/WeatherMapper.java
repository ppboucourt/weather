package com.pp.weather.service.mapper;

import com.pp.weather.domain.*;
import com.pp.weather.service.dto.WeatherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WeatherVO and its DTO WeatherDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WeatherMapper extends EntityMapper <WeatherDTO, Weather> {


    default Weather fromId(Long id) {
        if (id == null) {
            return null;
        }
        Weather weather = new Weather();
        weather.setId(id);
        return weather;
    }
}
