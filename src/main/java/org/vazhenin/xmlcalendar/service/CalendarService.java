package org.vazhenin.xmlcalendar.service;

import feign.FeignException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vazhenin.xmlcalendar.domain.Calendar;
import org.vazhenin.xmlcalendar.domain.Day;
import org.vazhenin.xmlcalendar.domain.Holiday;
import org.vazhenin.xmlcalendar.enums.Language;
import org.vazhenin.xmlcalendar.repository.CalendarRepository;
import org.vazhenin.xmlcalendar.repository.DayRepo;
import org.vazhenin.xmlcalendar.repository.HolidayRepo;
import org.vazhenin.xmlcalendar.rest.CalendarResource;
import org.vazhenin.xmlcalendar.rest.CalendarRestClient;

import java.util.List;
import java.util.Optional;

@Service
public class CalendarService {
    Logger log = Logger.getLogger(CalendarResource.class);
    @Autowired
    CalendarRestClient calendarRestClient;
    @Autowired
    DayRepo dayRepo;
    @Autowired
    HolidayRepo holidayRepo;

    @Autowired
    CalendarRepository calendarRepository;

    public Optional<String> getCalendar(Language language, String year) {
        try {
            String calendar = calendarRestClient.getCalendar(language.getCode(), year);
            return Optional.ofNullable(calendar);
        } catch (FeignException.FeignClientException e) {
            log.errorf("Error getting calendar (language = %s, year = %s) HttpError: %s", language, year, e.status());
        } catch (Exception e) {
            log.error(e);
        }
        return Optional.empty();
    }

    public Optional<Day> findDayByYearCountryDay(String year, String country, String day) {
        return Optional.ofNullable(dayRepo.findByYearCountryDay(year, country, day));
    }

    public Long countDaysByYearCountry(String year, String country) {
        return dayRepo.findByYearCountry(year, country).stream().count();
    }

    public Optional<Holiday> findHolidayByIdAndCalendarId(int holiday_id, Long calendar_id) {
        return Optional.ofNullable(holidayRepo.getHolidayByIdAndCalendarId(holiday_id, calendar_id));
    }

    public List<Calendar> findCalendars(Sort sort){
        return calendarRepository.findAll(sort);
    }
}
