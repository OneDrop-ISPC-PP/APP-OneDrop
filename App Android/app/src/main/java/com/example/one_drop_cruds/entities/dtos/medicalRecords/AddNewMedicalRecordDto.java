package com.example.one_drop_cruds.entities.dtos.medicalRecords;

public class AddNewMedicalRecordDto {
    private Integer id_paciente;
    private String tipo_diabetes;
    private String terapia_insulina;
    private String terapia_pastillas;
    private String tipo_glucometro;
    private String tipo_sensor;
    private String objetivo_glucosa;
    private String comorbilidades;
    private Double peso;

    public AddNewMedicalRecordDto() {
    }

    public AddNewMedicalRecordDto(Integer id_paciente, String tipo_diabetes, String terapia_insulina, String terapia_pastillas, String tipo_glucometro, String tipo_sensor, String objetivo_glucosa, String comorbilidades, Double peso) {
        this.id_paciente = id_paciente;
        this.tipo_diabetes = tipo_diabetes;
        this.terapia_insulina = terapia_insulina;
        this.terapia_pastillas = terapia_pastillas;
        this.tipo_glucometro = tipo_glucometro;
        this.tipo_sensor = tipo_sensor;
        this.objetivo_glucosa = objetivo_glucosa;
        this.comorbilidades = comorbilidades;
        this.peso = peso;
    }

    public Integer getId_paciente() {
        return id_paciente;
    }

    public String getTipo_diabetes() {
        return tipo_diabetes;
    }

    public String getTerapia_insulina() {
        return terapia_insulina;
    }

    public String getTerapia_pastillas() {
        return terapia_pastillas;
    }

    public String getTipo_glucometro() {
        return tipo_glucometro;
    }

    public String getTipo_sensor() {
        return tipo_sensor;
    }

    public String getObjetivo_glucosa() {
        return objetivo_glucosa;
    }

    public String getComorbilidades() {
        return comorbilidades;
    }

    public Double getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return "AddNewMedicalRecordDto{" +
                "id_paciente=" + id_paciente +
                ", tipo_diabetes='" + tipo_diabetes + '\'' +
                ", terapia_insulina='" + terapia_insulina + '\'' +
                ", terapia_pastillas='" + terapia_pastillas + '\'' +
                ", tipo_glucometro='" + tipo_glucometro + '\'' +
                ", tipo_sensor='" + tipo_sensor + '\'' +
                ", objetivo_glucosa='" + objetivo_glucosa + '\'' +
                ", comorbilidades='" + comorbilidades + '\'' +
                ", peso=" + peso +
                '}';
    }

    public void setId_paciente(Integer id_paciente) {
        this.id_paciente = id_paciente;
    }

    public void setTipo_diabetes(String tipo_diabetes) {
        this.tipo_diabetes = tipo_diabetes;
    }

    public void setTerapia_insulina(String terapia_insulina) {
        this.terapia_insulina = terapia_insulina;
    }

    public void setTerapia_pastillas(String terapia_pastillas) {
        this.terapia_pastillas = terapia_pastillas;
    }

    public void setTipo_glucometro(String tipo_glucometro) {
        this.tipo_glucometro = tipo_glucometro;
    }

    public void setTipo_sensor(String tipo_sensor) {
        this.tipo_sensor = tipo_sensor;
    }

    public void setObjetivo_glucosa(String objetivo_glucosa) {
        this.objetivo_glucosa = objetivo_glucosa;
    }

    public void setComorbilidades(String comorbilidades) {
        this.comorbilidades = comorbilidades;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }
}
