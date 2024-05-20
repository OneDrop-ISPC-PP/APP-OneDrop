package com.pisis.oneDrop.services;

import com.pisis.oneDrop.auth.entities.User;
import com.pisis.oneDrop.exceptions.customsExceptions.InvalidValueException;
import com.pisis.oneDrop.model.entities.FichaMedica;
import com.pisis.oneDrop.model.entities.reports.ReporteRegistroGlucemiaItem;
import com.pisis.oneDrop.model.entities.reports.ReporteRegistroPesoItem;
import com.pisis.oneDrop.model.entities.reports.ReporteRegistroTensionArterialItem;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportesService {

    Map<String, Object> parametrosReporte = new HashMap<>();

    @Autowired
    FichaMedicaService fichaMedicaService;


    public byte[] crearResumenGlucemia(Integer idUser) throws JRException, IOException {
        FichaMedica ficha = fichaMedicaService.getFichaMedicaByUserId(idUser);
        return JasperExportManager.exportReportToPdf(generarReporteGlucemia(ficha));
    }
    public byte[] crearReporteCompleto(Integer idUser) throws JRException, IOException {
        FichaMedica ficha = fichaMedicaService.getFichaMedicaByUserId(idUser);
        return JasperExportManager.exportReportToPdf(generarReporteCompleto(ficha));
    }

    private void cargarRotuloReporte(FichaMedica ficha) throws FileNotFoundException {
        User paciente = ficha.getPaciente();
        // Rotulo
        parametrosReporte.put("logoPrincipal", obtenerLogoPrincipal());
        String tituloPrincipal = String.format("FICHA MEDICA de %s %s dni %s", paciente.getNombre().toUpperCase(), paciente.getApellido().toUpperCase(), paciente.getDni());
        parametrosReporte.put("tituloPrincipal", tituloPrincipal);

        // ficha medica
        String sexo = paciente.getSexo();
        Integer edad = LocalDate.now().minusYears(paciente.getNacimiento().getYear()).getYear();
        String diabetes = ficha.getTipo_diabetes().name().replace("_", " ");
        String insulina = ficha.getTerapia_insulina().name().replace("_", " ");
        String pastillas = ficha.getTerapia_pastillas().name().replace("_", " ");
        String comorbilidades = ficha.getComorbilidades();
        String glucometro = ficha.getTipo_glucometro().name().replace("_", " ");
        String sensor = ficha.getTipo_sensor().name().replace("_", " ");
        String objetivo = ficha.getObjetivo_glucosa();

        String textoFichaMedica = String.format("Notas medicas: Paciente %s , de %s años de edad, con diabetes: %s , insulina: %s, terapia de pastillas: %s , comorbilidades: %s . Usa un glucometro: %s , con un sensor: %s y su objetivo de glucosa es: %s ", sexo, edad,diabetes, insulina, pastillas, comorbilidades, glucometro, sensor, objetivo );

        parametrosReporte.put("textoFichaMedica", textoFichaMedica);

    }
    private void cargarGraficoGlucemia(FichaMedica ficha){
        ficha.getRegistros_glucemia();
        List<ReporteRegistroGlucemiaItem> registroGlucemiaItems = new ArrayList<>();
        for (int i =0; i<ficha.getRegistros_glucemia().size(); i++){
            registroGlucemiaItems.add(new ReporteRegistroGlucemiaItem(ficha.getRegistros_glucemia().get(i).getFecha(), ficha.getRegistros_glucemia().get(i).getValor()));
        }
        parametrosReporte.put("dataSetGlucemia", new JRBeanCollectionDataSource(registroGlucemiaItems));
    }
    private void cargarGraficoTensionArterial(FichaMedica ficha){
        ficha.getRegistros_tension_arterial();
        List<ReporteRegistroTensionArterialItem> registroTensionItems = new ArrayList<>();
        for (int i =0; i<ficha.getRegistros_tension_arterial().size(); i++){
            registroTensionItems.add(new ReporteRegistroTensionArterialItem(ficha.getRegistros_tension_arterial().get(i).getFecha(), ficha.getRegistros_tension_arterial().get(i).getDiastolica(), ficha.getRegistros_tension_arterial().get(i).getSistolica()));
        }
        parametrosReporte.put("dataSetTension", new JRBeanCollectionDataSource(registroTensionItems));
    }
    private void cargarGraficoPeso(FichaMedica ficha){
        ficha.getRegistros_peso();
        List<ReporteRegistroPesoItem> registroPesoItems = new ArrayList<>();
        for (int i =0; i<ficha.getRegistros_peso().size(); i++){
            registroPesoItems.add(new ReporteRegistroPesoItem(ficha.getRegistros_peso().get(i).getFecha(), ficha.getRegistros_peso().get(i).getValor()));
        }
        parametrosReporte.put("dataSetPeso", new JRBeanCollectionDataSource(registroPesoItems));
    }
    private JasperPrint generarReporteGlucemia (FichaMedica ficha) throws FileNotFoundException {
        cargarRotuloReporte(ficha);
        cargarGraficoGlucemia(ficha);
        try{
            InputStream jrxmlStream = getClass().getResourceAsStream("/ReporteOneDrop-glucemia.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            JasperPrint report = JasperFillManager.fillReport(jasperReport, parametrosReporte, new JREmptyDataSource());
            return report;
        } catch (Exception e){
            throw new InvalidValueException("Error generando reporte: "+e.getMessage());
        }
    }
    private JasperPrint generarReporteCompleto(FichaMedica ficha) throws FileNotFoundException {
        cargarRotuloReporte(ficha);
        cargarGraficoGlucemia(ficha);
        cargarGraficoTensionArterial(ficha);
        cargarGraficoPeso(ficha);

        try{
            InputStream jrxmlStream = getClass().getResourceAsStream("/ReporteOneDrop.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            JasperPrint report = JasperFillManager.fillReport(jasperReport, parametrosReporte, new JREmptyDataSource());
            return report;
        } catch (Exception e){
            throw new InvalidValueException("Error generando reporte: "+e.getMessage());
        }
    }
    private FileInputStream obtenerLogoPrincipal() throws FileNotFoundException {
        File logoPrincipal = ResourceUtils.getFile("classpath:imagenes/logoPrincipal.png");
        return new FileInputStream(logoPrincipal);
    }


}
