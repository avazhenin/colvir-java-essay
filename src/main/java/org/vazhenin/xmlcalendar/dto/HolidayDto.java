package org.vazhenin.xmlcalendar.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link org.vazhenin.xmlcalendar.domain.Holiday} entity
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayDto implements Serializable {
    private Long rowId;
    private Long id;
    private String title;
    private Long calendar_id;
}
