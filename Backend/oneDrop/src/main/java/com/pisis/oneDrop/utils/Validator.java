package com.pisis.oneDrop.utils;

import com.pisis.oneDrop.exceptions.customsExceptions.InvalidValueException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validator {
    // *************** VALIDACION DATOS PUROS ***************

    public String stringMinSize(String field, Integer minSize, String value){
        if(value.length() < minSize) throw new InvalidValueException(field + " debe tener al menos "+minSize+" caracteres!");
        return value;
    }
    public String stringOnlyLettersAndNumbers (String field, String value){
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        // "[^a-zA-Z0-9]" => CUALQUIER CARACTER MENOS LOS ENUMERADOS, osea si encuentra un caracter distinto, lanza excepcion
        if(pattern.matcher(value).find()) throw new InvalidValueException(field + " solo puede contener numeros y letras!");
        return value;
    }
    public String stringText (String field, String value){
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\s.,:*?!/]");  // \s es para espacio en blanco
        if(pattern.matcher(value).find()) throw new InvalidValueException(field + " solo puede contener numeros, letras, espacios en blanco, puntos, comas, asteriscos, signos de pregunta o exclamacion!");
        return value;
    }
    public String stringOnlyLetters (String field, String value){
        Pattern pattern = Pattern.compile("[^a-zA-Z\s]");
        // "[^a-zA-Z]" => CUALQUIER CARACTER MENOS LOS ENUMERADOS, osea si encuentra un caracter distinto, lanza excepcion
        if(pattern.matcher(value).find()) throw new InvalidValueException(field + " solo puede contener letras!");
        return value;
    }
    public String stringOnlyNumbers (String field, String value){
        Pattern pattern = Pattern.compile("[^0-9]");
        if(pattern.matcher(value).find()) throw new InvalidValueException(field + " solo puede contener numeros!");
        return value;
    }
    public String stringOnlyIntegerPositiveNumbers (String field, String value){
        Pattern pattern = Pattern.compile("[^0-9]");
        if(pattern.matcher(value).find()) throw new InvalidValueException(field + " solo puede contener numeros enteros, mayores a cero!");
        if (Integer.parseInt(value) < 0) throw new InvalidValueException(field + " solo puede contener numeros enteros, mayores a cero!");
        return value;
    }

    public void validPhoneNumber(String phone){
        Pattern pattern = Pattern.compile("^\\+?[0-9]{9,14}");
        if(!pattern.matcher(phone).matches()) throw new InvalidValueException("Telefono solo puede contener numeros, un signo + al principio y debe tener entre 9 y 14 caracteres!");
    }

    /* validacion datos negocio */


}
