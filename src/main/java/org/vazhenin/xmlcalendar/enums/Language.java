package org.vazhenin.xmlcalendar.enums;

public enum Language {
    RU("ru"),
    KZ("kz");

    private String code;

    Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
