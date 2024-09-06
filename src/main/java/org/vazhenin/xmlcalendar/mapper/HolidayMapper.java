package org.vazhenin.xmlcalendar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.vazhenin.xmlcalendar.domain.Holiday;
import org.vazhenin.xmlcalendar.dto.HolidayDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CalendarMapper.class})
public interface HolidayMapper extends EntityMapper<HolidayDto, Holiday>{
    @Override
    Holiday toEntity(HolidayDto dto);

    @Override
    HolidayDto toDto(Holiday entity);
}
