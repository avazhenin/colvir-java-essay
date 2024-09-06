package org.vazhenin.xmlcalendar.background;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.vazhenin.xmlcalendar.domain.Calendar;
import org.vazhenin.xmlcalendar.enums.Language;
import org.vazhenin.xmlcalendar.rabbitmq.RabbitMQService;
import org.vazhenin.xmlcalendar.repository.CalendarRepository;
import org.vazhenin.xmlcalendar.service.CalendarService;
import org.vazhenin.xmlcalendar.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class SyncronizeCalendar {

    Logger log = Logger.getLogger(SyncronizeCalendar.class);

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    Utils utils;

    @Autowired
    CalendarService calendarService;

    XmlMapper xmlMapper;

    @Autowired
    RabbitMQService rabbitMQService;

    public SyncronizeCalendar() {
        xmlMapper = new XmlMapper();
    }

    @Scheduled(cron = "${update.calendar.scheduler}")
    public void update_calendar() {
        try {
            rabbitMQService.send("Update calendar background task started");
            List<CompletableFuture<Void>> futureList = new ArrayList<>();
            for (int i = 0; i <= 11; i++) {
                for (Language lng : Language.values()) {
                    int finalI = i;
                    futureList.add(CompletableFuture.runAsync(() -> {
                        try {
                            processUpdate(String.valueOf(2013 + finalI), lng);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }));
                }
            }
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
            allOf.get();
            rabbitMQService.send("Update calendar background task finished");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Transactional
    void processUpdate(String year, Language language) throws JsonProcessingException {
        boolean calendarExists = false;
        boolean hashCodeEquals = true;
        Optional<String> getCalendarResultOptional = calendarService.getCalendar(language, year);
        if (!getCalendarResultOptional.isPresent()) return;
        String getCalendarResult = getCalendarResultOptional.get();
        Optional<Calendar> calendarFindOptional = Optional.ofNullable(calendarRepository.findByCountryAndYear(language.getCode(), year));
        calendarExists = calendarFindOptional.isPresent();
        Calendar calendar = utils.prepareCalendar(getCalendarResult, language.getCode());
        hashCodeEquals = calendarFindOptional.isPresent()
            ? Long.compareUnsigned(calendar.getHashcode(), calendarFindOptional.get().getHashcode()) == 0
            : true;
        if (calendarFindOptional.isPresent() && !hashCodeEquals || !calendarFindOptional.isPresent()) {
            calendarRepository.save(calendar);
            log.debugf("calendar saved (id=%s)", calendar.getId());
        }
        log.debugf("Process calendar update. Calendar %s, hash code %s changed",
            calendarExists ? "exists" : "not exists",
            hashCodeEquals ? "hasn't" : "has been"
        );
    }
}
