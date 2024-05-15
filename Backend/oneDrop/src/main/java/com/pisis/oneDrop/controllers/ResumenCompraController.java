package com.pisis.oneDrop.controllers;


import com.pisis.oneDrop.services.ResumenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/resumenCompra/", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()")
public class ResumenCompraController {
    @Autowired
    ResumenCompraService resumenCompraService;


}
