package org.vazhenin.xmlcalendar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "calendar")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "year")
    private String year;
    @Column(name = "lang")
    private String lang;
    @Column(name = "date")
    private String date;
    @Column(name = "country")
    private String country;
    @Column(name = "hash_code", unique = true)
    private Long hashcode;
    @Column(name = "updated")
    private Timestamp updated;

    @JacksonXmlElementWrapper(localName = "holidays")
    @JacksonXmlProperty(localName = "holiday")
    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "holidays")
    @JoinColumn(name = "calendar_id")
    private List<Holiday> holidays;

    @JacksonXmlElementWrapper(localName = "days")
    @JacksonXmlProperty(localName = "day")
    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "days")
    @JoinColumn(name = "calendar_id")
    private List<Day> days;
}
