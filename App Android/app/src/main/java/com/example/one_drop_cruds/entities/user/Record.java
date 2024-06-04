package com.example.one_drop_cruds.entities.user;


public class Record {
    private Integer id;
    private Long fecha;
    private Double valor;
    private String comentario;

    public Record(Integer id, Long fecha, Double valor, String comentario) {
        this.id = id;
        this.fecha = fecha;
        this.valor = valor;
        this.comentario = comentario;
    }

    public Record() {
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
        return "Record{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", valor=" + valor +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
