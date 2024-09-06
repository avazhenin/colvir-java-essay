package org.vazhenin.xmlcalendar.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id", nullable = false)
    private Long row_id;

    private Long id;
    private String d;
    private int t;
    private int h;
    private String f;
    private Long calendar_id;

    public Long getRow_id() {
        return row_id;
    }

    public void setRow_id(Long row_id) {
        this.row_id = row_id;
    }
}
