package com.pp.weather.repository;

import com.pp.weather.domain.Weather;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WeatherVO entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

}
