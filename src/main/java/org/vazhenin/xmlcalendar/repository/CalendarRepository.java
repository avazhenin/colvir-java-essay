package org.vazhenin.xmlcalendar.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.vazhenin.xmlcalendar.domain.Calendar;

import java.util.List;

interface CalendarRepository extends CrudRepository<Calendar, Long> {
    @Query(value = "select c from Calendar c WHERE c.country=?1 and c.year=?2")
    Calendar findByCountryAndYear(String country, String year);

    List<Calendar> findAll(Sort sort);
}
