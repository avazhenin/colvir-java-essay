package org.vazhenin.xmlcalendar.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.vazhenin.xmlcalendar.domain.Holiday;

public interface HolidayRepo extends CrudRepository<Holiday, Long> {
    @Query(value = "select h from Holiday h where h.id=?1 and h.calendar_id=?2")
    public Holiday getHolidayByIdAndCalendarId(int holiday_id, Long calendar_id);
}
