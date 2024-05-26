package com.example.one_drop_cruds.entities.dtos;


public class RecordReadDto {
    private Integer id;
    private Long fecha;
    private Double valor;
    private String comentario;

    public RecordReadDto(Integer id, Long fecha, Double valor, String comentario) {
        this.id = id;
        this.fecha = fecha;
        this.valor = valor;
        this.comentario = comentario;
    }

    public Integer getId() {
        return id;
    }

    public Long getFecha() {
        return fecha;
    }

    public Double getValor() {
        return valor;
    }

    public String getComentario() {
        return comentario;
    }

    @Override
    public String toString() {
        return "RegistroReadDto{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", valor=" + valor +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
