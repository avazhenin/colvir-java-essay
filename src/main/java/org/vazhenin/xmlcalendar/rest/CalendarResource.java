package org.vazhenin.xmlcalendar.rest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.vazhenin.xmlcalendar.background.SyncronizeCalendar;
import org.vazhenin.xmlcalendar.domain.Calendar;
import org.vazhenin.xmlcalendar.domain.Day;
import org.vazhenin.xmlcalendar.domain.Holiday;
import org.vazhenin.xmlcalendar.enums.DayType;
import org.vazhenin.xmlcalendar.enums.Language;
import org.vazhenin.xmlcalendar.rest.response.CalendarResponse;
import org.vazhenin.xmlcalendar.rest.response.GetCalendarResponse;
import org.vazhenin.xmlcalendar.service.CalendarService;

import java.sql.Timestamp;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/calendar")
public class CalendarResource {

    Logger log = Logger.getLogger(CalendarResource.class);

    @Autowired
    CalendarService calendarService;

    @Autowired
    SyncronizeCalendar syncronizeCalendar;

    @GetMapping("/{language}/{year}/calendar.xml")
    public Optional<String> getCalendar(Language language, String year) {
        return calendarService.getCalendar(language, year);
    }

    @PostMapping("/forceUpdate")
    public void forceUpdate() {
        log.info("Called /forceUpdate");
        CompletableFuture.runAsync(() -> {
            syncronizeCalendar.update_calendar();
        });
    }

    /**
     * @param date в формате DD.MM.YYYY
     */
    @GetMapping("/getDateInfo")
    public CalendarResponse getDayInfo(@BindParam("date") String date, @BindParam("country") String country) {
        String year = date.split("\\.")[2];
        String monthYear = String.format("%s.%s", date.split("\\.")[1], date.split("\\.")[0]);
        log.infof("Called /getDateInfo/{date} with params {date}=%s, [year=%s, monthYear=%s]", date, year, monthYear);
        // Find day
        Optional<Day> dayOptional = calendarService.findDayByYearCountryDay(year, country, monthYear);
        GetCalendarResponse response = new GetCalendarResponse();
        if (dayOptional.isPresent()) {
            Day day = dayOptional.get();
            // Find holiday type
            Optional<Holiday> holidayOptional = calendarService.findHolidayByIdAndCalendarId(day.getH(), day.getCalendar_id());
            // prepare response
            response.setDate(date);
            if (day.getT() > -1) response.setDateType(DayType.getById(day.getT()).getDescription());
            if (holidayOptional.isPresent()) response.setHoliday(holidayOptional.get().getTitle());
            if (day.getF() != null) response.setForward(day.getF());
        } else {
            response.setDate(date);
            response.setDateType("Отсутствует в производственном календаре");
            response.setForward("информация отсутствует");
            response.setHoliday("информация отсутствует");
        }
        return new CalendarResponse(HttpStatus.OK, null, response);
    }

    @GetMapping("/lastUpdated")
    public CalendarResponse getLastUpdated() {
        log.info("Called /lastUpdated");
        Optional<Calendar> calendarOptional = Optional.ofNullable(calendarService.findCalendars(Sort.by(Sort.Direction.ASC, "updated")).get(0));
        if (calendarOptional.isPresent()) {
            Timestamp timestamp = calendarOptional.get().getUpdated();
            return new CalendarResponse(HttpStatus.OK, "", new HashMap<>() {{
                put("lastUpdated", timestamp);
            }});
        }
        return new CalendarResponse(HttpStatus.INTERNAL_SERVER_ERROR, "no data", null);
    }

    @GetMapping("/statistic")
    public CalendarResponse getStatistic(@BindParam("year") String year) {
        log.infof("Called /statistic year=%s", year);
        if (Optional.ofNullable(year).isPresent()) {
            Long yearDays = Long.valueOf(Year.of(Integer.parseInt(year)).length());
            Long calendarDays = calendarService.countDaysByYearCountry(year, Language.KZ.getCode());
            Map<String, Long> result = new HashMap<>();
            result.put("Work days", yearDays - calendarDays);
            result.put("Holidays", calendarDays);
            return new CalendarResponse(HttpStatus.OK, null, result);
        } else {
            return new CalendarResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Empty year", null);
        }
    }
}
