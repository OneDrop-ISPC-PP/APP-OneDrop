package com.pisis.oneDrop.services;

import com.pisis.oneDrop.auth.entities.User;
import com.pisis.oneDrop.exceptions.customsExceptions.InvalidValueException;
import com.pisis.oneDrop.model.entities.FichaMedica;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
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

    private JasperPrint generarReporteGlucemia (FichaMedica ficha) throws FileNotFoundException {
        cargarRotuloReporte(ficha);
        // registros glucemia


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
