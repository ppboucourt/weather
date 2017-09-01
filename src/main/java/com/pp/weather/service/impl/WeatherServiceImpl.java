package com.pp.weather.service.impl;

import com.pp.weather.service.WeatherService;
import com.pp.weather.domain.Weather;
import com.pp.weather.repository.WeatherRepository;
import com.pp.weather.repository.search.WeatherSearchRepository;
import com.pp.weather.service.dto.WeatherDTO;
import com.pp.weather.service.mapper.WeatherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing WeatherVO.
 */
@Service
@Transactional
public class WeatherServiceImpl implements WeatherService{

    private final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);

    private final WeatherRepository weatherRepository;

    private final WeatherMapper weatherMapper;

    private final WeatherSearchRepository weatherSearchRepository;
    public WeatherServiceImpl(WeatherRepository weatherRepository, WeatherMapper weatherMapper, WeatherSearchRepository weatherSearchRepository) {
        this.weatherRepository = weatherRepository;
        this.weatherMapper = weatherMapper;
        this.weatherSearchRepository = weatherSearchRepository;
    }

    /**
     * Save a weather.
     *
     * @param weatherDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WeatherDTO save(WeatherDTO weatherDTO) {
        log.debug("Request to save WeatherVO : {}", weatherDTO);
        Weather weather = weatherMapper.toEntity(weatherDTO);
        weather = weatherRepository.save(weather);
        WeatherDTO result = weatherMapper.toDto(weather);
        weatherSearchRepository.save(weather);
        return result;
    }

    /**
     *  Get all the weathers.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<WeatherDTO> findAll() {
        log.debug("Request to get all Weathers");
        return weatherRepository.findAll().stream()
            .map(weatherMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one weather by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WeatherDTO findOne(Long id) {
        log.debug("Request to get WeatherVO : {}", id);
        Weather weather = weatherRepository.findOne(id);
        return weatherMapper.toDto(weather);
    }

    /**
     *  Delete the  weather by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WeatherVO : {}", id);
        weatherRepository.delete(id);
        weatherSearchRepository.delete(id);
    }

    /**
     * Search for the weather corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<WeatherDTO> search(String query) {
        log.debug("Request to search Weathers for query {}", query);
        return StreamSupport
            .stream(weatherSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(weatherMapper::toDto)
            .collect(Collectors.toList());
    }
}
