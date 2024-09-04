package org.vazhenin.xmlcalendar.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCalendarResponse {
    private String date;
    private String dateType;
    private String holiday;
    private String forward;
}
