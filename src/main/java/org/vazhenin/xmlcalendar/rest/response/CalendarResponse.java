package org.vazhenin.xmlcalendar.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarResponse {
    private HttpStatus httpStatus;
    private String error;
    private Object details;
}
