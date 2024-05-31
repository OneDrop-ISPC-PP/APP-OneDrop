package com.example.one_drop_cruds.entities.dtos.carts;

import java.util.List;
/*
{
    "id": 1,
    "servicios": [],
    "historialCompras": [
        {
            "id": 3,
            "importe": 11000.0,
            "fecha": 1715876693576,
            "servicios": [
                {
                    "id": 2,
                    "nombre": "Segundo servicio",
                    "descripcion": "Holter",
                    "precio": 3500,
                    "comentarios": "UN POCOo mas caro"
                },
                {
                    "id": 3,
                    "nombre": "UN tercer servicio",
                    "descripcion": "ergometria",
                    "precio": 7500,
                    "comentarios": "solo los cardio"
                }
            ],
            "metodoDePago": "EFECTIVO"
        }
    ]
}
 */
public class CartReadDto {
    private Integer id;
    private List servicios;
    private List historialCompras ;

    public CartReadDto(Integer id, List servicios, List historialCompras) {
        this.id = id;
        this.servicios = servicios;
        this.historialCompras = historialCompras;
    }

    public Integer getId() {
        return id;
    }

    public List getServicios() {
        return servicios;
    }

    public List getHistorialCompras() {
        return historialCompras;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", servicios=" + servicios +
                ", historialCompras=" + historialCompras +
                '}';
    }
}
