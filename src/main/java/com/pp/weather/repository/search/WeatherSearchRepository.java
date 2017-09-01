package com.pp.weather.repository.search;

import com.pp.weather.domain.Weather;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WeatherVO entity.
 */
public interface WeatherSearchRepository extends ElasticsearchRepository<Weather, Long> {
}
