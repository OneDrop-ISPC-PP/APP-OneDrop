package com.pisis.oneDrop.controllers;

import com.pisis.oneDrop.services.ReportesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/reportes/", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()")
public class ReportesController {

    @Autowired
    ReportesService reportesService;

    @GetMapping("{idUser}/glucemia")
    public ResponseEntity<byte[]> descargarResumenGlucemia(@PathVariable Integer idUser) throws JRException, IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        LocalDate fechaCreacion = LocalDate.now();
        headers.setContentDispositionFormData("Registro Glucemia", "Registro glucemia usuario "+idUser+", "+fechaCreacion+".pdf");
        return ResponseEntity.ok().headers(headers).body(reportesService.crearResumenGlucemia(idUser));
    }


    // todo => reporte completo
    // todo => reporte glucemia
    // todo => reporte tension arterial
    // todo => reporte peso
    // todo => reporte solo ficha medica, osea datos
    // todo => reporte compras?

}
