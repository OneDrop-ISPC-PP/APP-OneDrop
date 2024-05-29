package com.example.one_drop_cruds.entities.dtos.records;

public class PressureRecordReadDto {
    private Integer id;
    private Long fecha;
    private Integer diastolica;
    private Integer sistolica;
    private String comentario;

    public PressureRecordReadDto() {
    }

    public PressureRecordReadDto(Integer id, Long fecha, Integer diastolica, Integer sistolica, String comentario) {
        this.id = id;
        this.fecha = fecha;
        this.diastolica = diastolica;
        this.sistolica = sistolica;
        this.comentario = comentario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    public Integer getDiastolica() {
        return diastolica;
    }

    public void setDiastolica(Integer diastolica) {
        this.diastolica = diastolica;
    }

    public Integer getSistolica() {
        return sistolica;
    }

    public void setSistolica(Integer sistolica) {
        this.sistolica = sistolica;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "PressureRecordReadDto{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", diastolica=" + diastolica +
                ", sistolica=" + sistolica +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
