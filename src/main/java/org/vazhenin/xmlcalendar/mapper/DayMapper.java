package org.vazhenin.xmlcalendar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.vazhenin.xmlcalendar.domain.Day;
import org.vazhenin.xmlcalendar.dto.DayDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CalendarMapper.class})
interface DayMapper extends EntityMapper<DayDto, Day>{
    @Override
    Day toEntity(DayDto dto);

    @Override
    DayDto toDto(Day entity);
}
