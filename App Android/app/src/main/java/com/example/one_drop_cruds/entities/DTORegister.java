package com.example.one_drop_cruds.entities;

public class DTORegister {
    String date;
    Double value;
    String notes;

    public DTORegister(String date, Double value, String notes) {
        this.date = date;
        this.value = value;
        this.notes = notes;
    }

    public DTORegister() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
