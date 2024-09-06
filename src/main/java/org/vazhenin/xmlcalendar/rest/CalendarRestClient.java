package org.vazhenin.xmlcalendar.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "calendarRestClient", url = "https://xmlcalendar.ru")
public interface CalendarRestClient {
    @GetMapping("/data/{language}/{year}/calendar.xml")
    String getCalendar(@PathVariable("language") String language, @PathVariable("year") String year);
}
