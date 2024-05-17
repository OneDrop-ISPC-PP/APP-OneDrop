package com.example.one_drop_cruds.entities.user;

import com.example.one_drop_cruds.entities.user.enums.Terapia_insulina;
import com.example.one_drop_cruds.entities.user.enums.Terapia_pastillas;
import com.example.one_drop_cruds.entities.user.enums.Tipo_diabetes;
import com.example.one_drop_cruds.entities.user.enums.Tipo_glucometro;
import com.example.one_drop_cruds.entities.user.enums.Tipo_sensor;
import com.google.gson.annotations.SerializedName;

public class FichaMedicaUsuario {
    @SerializedName("id")
    private Integer id;
    @SerializedName("tipo_diabetes")
    private Tipo_diabetes tipo_diabetes;
    @SerializedName("terapia_insulina")
    private Terapia_insulina terapia_insulina;
    @SerializedName("terapia_pastillas")
    private Terapia_pastillas terapia_pastillas;
    @SerializedName("tipo_glucometro")
    private Tipo_glucometro tipo_glucometro;
    @SerializedName("tipo_sensor")
    private Tipo_sensor tipo_sensor;
    @SerializedName("objetivo_glucosa")
    private String objetivo_glucosa;
    @SerializedName("comorbilidades")
    private String comorbilidades;
    @SerializedName("peso")
    private Double peso;

    public FichaMedicaUsuario() {
    }

    public FichaMedicaUsuario(Integer id, Tipo_diabetes tipo_diabetes, Terapia_insulina terapia_insulina, Terapia_pastillas terapia_pastillas, Tipo_glucometro tipo_glucometro, Tipo_sensor tipo_sensor, String objetivo_glucosa, String comorbilidades, Double peso) {
        this.id = id;
        this.tipo_diabetes = tipo_diabetes;
        this.terapia_insulina = terapia_insulina;
        this.terapia_pastillas = terapia_pastillas;
        this.tipo_glucometro = tipo_glucometro;
        this.tipo_sensor = tipo_sensor;
        this.objetivo_glucosa = objetivo_glucosa;
        this.comorbilidades = comorbilidades;
        this.peso = peso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tipo_diabetes getTipo_diabetes() {
        return tipo_diabetes;
    }

    public void setTipo_diabetes(Tipo_diabetes tipo_diabetes) {
        this.tipo_diabetes = tipo_diabetes;
    }

    public Terapia_insulina getTerapia_insulina() {
        return terapia_insulina;
    }

    public void setTerapia_insulina(Terapia_insulina terapia_insulina) {
        this.terapia_insulina = terapia_insulina;
    }

    public Terapia_pastillas getTerapia_pastillas() {
        return terapia_pastillas;
    }

    public void setTerapia_pastillas(Terapia_pastillas terapia_pastillas) {
        this.terapia_pastillas = terapia_pastillas;
    }

    public Tipo_glucometro getTipo_glucometro() {
        return tipo_glucometro;
    }

    public void setTipo_glucometro(Tipo_glucometro tipo_glucometro) {
        this.tipo_glucometro = tipo_glucometro;
    }

    public Tipo_sensor getTipo_sensor() {
        return tipo_sensor;
    }

    public void setTipo_sensor(Tipo_sensor tipo_sensor) {
        this.tipo_sensor = tipo_sensor;
    }

    public String getObjetivo_glucosa() {
        return objetivo_glucosa;
    }

    public void setObjetivo_glucosa(String objetivo_glucosa) {
        this.objetivo_glucosa = objetivo_glucosa;
    }

    public String getComorbilidades() {
        return comorbilidades;
    }

    public void setComorbilidades(String comorbilidades) {
        this.comorbilidades = comorbilidades;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "FichaMedicaUsuario{" +
                "id=" + id +
                ", tipo_diabetes=" + tipo_diabetes +
                ", terapia_insulina=" + terapia_insulina +
                ", terapia_pastillas=" + terapia_pastillas +
                ", tipo_glucometro=" + tipo_glucometro +
                ", tipo_sensor=" + tipo_sensor +
                ", objetivo_glucosa='" + objetivo_glucosa + '\'' +
                ", comorbilidades='" + comorbilidades + '\'' +
                ", peso=" + peso +
                '}';
    }
}
