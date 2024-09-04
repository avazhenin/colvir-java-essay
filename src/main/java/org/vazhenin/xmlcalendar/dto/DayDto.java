package org.vazhenin.xmlcalendar.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link org.vazhenin.xmlcalendar.domain.Day} entity
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayDto implements Serializable {
    private Long id;
    private String d;
    private int t;
    private int h;
    private String f;
    private Long calendar_id;
}
