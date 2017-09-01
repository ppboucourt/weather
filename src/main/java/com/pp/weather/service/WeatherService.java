package com.pp.weather.service;

import com.pp.weather.service.dto.WeatherDTO;
import java.util.List;

/**
 * Service Interface for managing WeatherVO.
 */
public interface WeatherService {

    /**
     * Save a weather.
     *
     * @param weatherDTO the entity to save
     * @return the persisted entity
     */
    WeatherDTO save(WeatherDTO weatherDTO);

    /**
     *  Get all the weathers.
     *
     *  @return the list of entities
     */
    List<WeatherDTO> findAll();

    /**
     *  Get the "id" weather.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WeatherDTO findOne(Long id);

    /**
     *  Delete the "id" weather.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the weather corresponding to the query.
     *
     *  @param query the query of the search
     *
     *  @return the list of entities
     */
    List<WeatherDTO> search(String query);
}
