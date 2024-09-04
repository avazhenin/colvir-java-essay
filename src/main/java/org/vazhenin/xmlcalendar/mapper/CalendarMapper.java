package org.vazhenin.xmlcalendar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.vazhenin.xmlcalendar.domain.Calendar;
import org.vazhenin.xmlcalendar.dto.CalendarDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CalendarMapper extends EntityMapper<CalendarDto, Calendar> {
    @Override
    Calendar toEntity(CalendarDto dto);

    @Override
    CalendarDto toDto(Calendar entity);
}
