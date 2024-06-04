package com.example.one_drop_cruds.entities.dtos.records;

import java.util.Date;

public class AddNewRecordDto {
    private String fecha;
    private Double valor;
    private String comentario;

    public AddNewRecordDto(String fecha, Double valor, String comentario) {
        this.fecha = fecha;
        this.valor = valor;
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "AddNewRecordDto{" +
                "fecha=" + fecha +
                ", valor=" + valor +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
