package com.pp.weather.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pp.weather.domain.vo.WeatherVO;
import com.pp.weather.service.WeatherService;
import com.pp.weather.service.dto.WeatherDTO;
import com.pp.weather.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WeatherVO.
 */
@RestController
@RequestMapping("/api")
public class WeatherResource {

    private final Logger log = LoggerFactory.getLogger(WeatherResource.class);

    private static final String ENTITY_NAME = "weather";

    private final WeatherService weatherService;

    private final String uri = "http://api.wunderground.com/api/0febb2c6dfdd1e46/conditions/q/";
//        String uri = "http://api.wunderground.com/api/0febb2c6dfdd1e46/conditions/q/Miami.json";

    @GetMapping("/weather/cities/{cities}")
    @Timed
    public List<WeatherVO> getWeather(@PathVariable String cities) throws IOException, JAXBException {
        log.info("REST request to get weather : {}", cities);

        String[] citiesList = cities.split(",");
        String newUri = uri + citiesList[0] + ".json";

        URL url = new URL(newUri);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");

        JAXBContext weather = JAXBContext.newInstance(WeatherVO.class);
        InputStream json = connection.getInputStream();

        List<WeatherVO> result = new ArrayList<>();

        return result;
    }

    public WeatherResource(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * POST  /weathers : Create a new weather.
     *
     * @param weatherDTO the weatherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weatherDTO, or with status 400 (Bad Request) if the weather has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/weathers")
    @Timed
    public ResponseEntity<WeatherDTO> createWeather(@Valid @RequestBody WeatherDTO weatherDTO) throws URISyntaxException {
        log.debug("REST request to save WeatherVO : {}", weatherDTO);
        if (weatherDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new weather cannot already have an ID")).body(null);
        }
        WeatherDTO result = weatherService.save(weatherDTO);
        return ResponseEntity.created(new URI("/api/weathers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /weathers : Updates an existing weather.
     *
     * @param weatherDTO the weatherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weatherDTO,
     * or with status 400 (Bad Request) if the weatherDTO is not valid,
     * or with status 500 (Internal Server Error) if the weatherDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/weathers")
    @Timed
    public ResponseEntity<WeatherDTO> updateWeather(@Valid @RequestBody WeatherDTO weatherDTO) throws URISyntaxException {
        log.debug("REST request to update WeatherVO : {}", weatherDTO);
        if (weatherDTO.getId() == null) {
            return createWeather(weatherDTO);
        }
        WeatherDTO result = weatherService.save(weatherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, weatherDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /weathers : get all the weathers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of weathers in body
     */
    @GetMapping("/weathers")
    @Timed
    public List<WeatherDTO> getAllWeathers() {
        log.debug("REST request to get all Weathers");
        return weatherService.findAll();
        }

    /**
     * GET  /weathers/:id : get the "id" weather.
     *
     * @param id the id of the weatherDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weatherDTO, or with status 404 (Not Found)
     */
    @GetMapping("/weathers/{id}")
    @Timed
    public ResponseEntity<WeatherDTO> getWeather(@PathVariable Long id) {
        log.debug("REST request to get WeatherVO : {}", id);
        WeatherDTO weatherDTO = weatherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(weatherDTO));
    }

    /**
     * DELETE  /weathers/:id : delete the "id" weather.
     *
     * @param id the id of the weatherDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/weathers/{id}")
    @Timed
    public ResponseEntity<Void> deleteWeather(@PathVariable Long id) {
        log.debug("REST request to delete WeatherVO : {}", id);
        weatherService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/weathers?query=:query : search for the weather corresponding
     * to the query.
     *
     * @param query the query of the weather search
     * @return the result of the search
     */
    @GetMapping("/_search/weathers")
    @Timed
    public List<WeatherDTO> searchWeathers(@RequestParam String query) {
        log.debug("REST request to search Weathers for query {}", query);
        return weatherService.search(query);
    }

}
