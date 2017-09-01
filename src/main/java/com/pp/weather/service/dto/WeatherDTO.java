package com.pp.weather.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the WeatherVO entity.
 */
public class WeatherDTO implements Serializable {

    private Long id;

    private String value;

    @Size(max = 25000)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WeatherDTO weatherDTO = (WeatherDTO) o;
        if(weatherDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weatherDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WeatherDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
