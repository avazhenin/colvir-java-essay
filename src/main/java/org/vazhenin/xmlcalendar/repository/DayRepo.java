package org.vazhenin.xmlcalendar.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.vazhenin.xmlcalendar.domain.Day;

import java.util.List;

interface DayRepo extends CrudRepository<Day, Long> {
    @Query(value = "select d from Day d, Calendar c where d.calendar_id=c.id and c.year=?1 and c.country=?2 and d.d=?3")
    Day findByYearCountryDay(String year, String country, String day);

    @Query(value = "select d from Day d join Calendar c on d.calendar_id = c.id and c.year = ?1 and c.country = ?2")
    List<Day> findByYearCountry(String year, String country);
}
