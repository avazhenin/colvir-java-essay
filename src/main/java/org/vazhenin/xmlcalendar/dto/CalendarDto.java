package org.vazhenin.xmlcalendar.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * A DTO for the {@link org.vazhenin.xmlcalendar.domain.Calendar} entity
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalendarDto implements Serializable {
    private Long row_id;
    private Long id;
    private String year;
    private String lang;
    private String country;
    private String date;
    private List<HolidayDto> holidays;
    private List<DayDto> days;
    private Long hashcode;
    private Timestamp updated;
}
