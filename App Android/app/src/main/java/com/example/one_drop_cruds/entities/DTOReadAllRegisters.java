package com.example.one_drop_cruds.entities;

import java.util.ArrayList;

public class DTOReadAllRegisters {
    ArrayList<Integer> reg_ids = new ArrayList<Integer>();
    ArrayList<String> reg_dates = new ArrayList<String>();
    ArrayList<Double> reg_values = new ArrayList<Double>();
    ArrayList<String> reg_notes = new ArrayList<String>();

    public DTOReadAllRegisters() {
    }

    public ArrayList<Integer> getReg_ids() {
        return reg_ids;
    }

    public void setReg_ids(ArrayList<Integer> reg_ids) {
        this.reg_ids = reg_ids;
    }

    public ArrayList<String> getReg_dates() {
        return reg_dates;
    }

    public void setReg_dates(ArrayList<String> reg_dates) {
        this.reg_dates = reg_dates;
    }

    public ArrayList<Double> getReg_values() {
        return reg_values;
    }

    public void setReg_values(ArrayList<Double> reg_values) {
        this.reg_values = reg_values;
    }

    public ArrayList<String> getReg_notes() {
        return reg_notes;
    }

    public void setReg_notes(ArrayList<String> reg_notes) {
        this.reg_notes = reg_notes;
    }
}
