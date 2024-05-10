package com.example.one_drop_cruds.entities;

public class DTOmedicalRecord {
    String username;
    String name ;
    String last_name ;
    Integer age ;
    String birth ;
    Double weight ;
    String dbType ;
    String dbTherapy ;

    public DTOmedicalRecord() {
    }

    public DTOmedicalRecord(String username, String name, String last_name, Integer age, String birth, Double weight, String dbType, String dbTherapy) {
        this.username = username;
        this.name = name;
        this.last_name = last_name;
        this.age = age;
        this.birth = birth;
        this.weight = weight;
        this.dbType = dbType;
        this.dbTherapy = dbTherapy;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbTherapy() {
        return dbTherapy;
    }

    public void setDbTherapy(String dbTherapy) {
        this.dbTherapy = dbTherapy;
    }
}
