package org.vazhenin.xmlcalendar.enums;

public enum DayType {
    ONE(1, "выходной день"),
    TWO(2, "рабочий и сокращенный день"),
    THREE(3, "рабочий день");

    private int id;
    private String description;

    DayType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Method to find the enum by id
    public static DayType getById(int id) {
        for (DayType dayType : DayType.values()) {
            if (dayType.getId() == id) {
                return dayType;
            }
        }
        throw new IllegalArgumentException("No DayType with id " + id);
    }

    public static DayType getById(String id) {
        return getById(Integer.valueOf(id));
    }
}
