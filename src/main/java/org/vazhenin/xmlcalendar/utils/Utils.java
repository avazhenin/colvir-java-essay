package org.vazhenin.xmlcalendar.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vazhenin.xmlcalendar.domain.Calendar;
import org.vazhenin.xmlcalendar.dto.CalendarDto;
import org.vazhenin.xmlcalendar.mapper.CalendarMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class Utils {

    XmlMapper xmlMapper = new XmlMapper();

    @Autowired
    CalendarMapper calendarMapper;

    public Long get_hash(String s) {
        return UUID.nameUUIDFromBytes(s.getBytes()).getMostSignificantBits();
    }

    public Calendar prepareCalendar(String calendarString, String country) throws JsonProcessingException {
        CalendarDto calendarDTO = xmlMapper.readValue(calendarString, CalendarDto.class);
        calendarDTO.setHashcode(get_hash(calendarString));
        calendarDTO.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        calendarDTO.setCountry(country);
        Calendar calendar = calendarMapper.toEntity(calendarDTO);
        return calendar;
    }
}
