/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador;

import core.controlador.utils.Response;
import core.controlador.utils.Status;
import core.modelo.Location;
import core.modelo.storage.StorageLocation;
import java.util.ArrayList; 

/**
 *
 * @author jlaza
 */
public class ControllerLocation {

    public static Response createLocation(String id, String name, String city, String country, double latitude, double longitude) {
        try {
            String latitudeAux = String.valueOf(latitude);
            String longitudeAux = String.valueOf(longitude);

            if (city.isEmpty() || name.isEmpty() || country.isEmpty() || id.isEmpty() || latitudeAux.isEmpty() || longitudeAux.isEmpty()) {
                return new Response("Ninguno de los campos puede estar vacío", Status.BAD_REQUEST);
            }
            boolean checkId;
            checkId = verifyIdFormat(id);
            if (!checkId) {
                return new Response("Id esta en el formato correcto", Status.BAD_REQUEST);
            }

            if (!verifyDecimalOfLongitudAndLatitude(latitudeAux)) {
                return new Response("Latitud con formato incorrecto", Status.BAD_REQUEST);
            }
            if (!verifyDecimalOfLongitudAndLatitude(longitudeAux)) {
                return new Response("Longitud con formato incorrecto", Status.BAD_REQUEST);
            }

            if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
                return new Response("Valores de la longitud y latitud están en rangos incorrectos", Status.BAD_REQUEST);
            }

            if (!verifyStrings(city)) {
                return new Response("Ciudad inválida", Status.BAD_REQUEST); // Validate the format of 
            }
            if (!verifyStrings(country)) {
                return new Response("País inválido", Status.BAD_REQUEST);
            }
            StorageLocation storageLocation = StorageLocation.getInstance();
            if (!storageLocation.addLocation(new Location(id, name, city, country, latitude, longitude))) {
            return new Response("Ya hay un Aeropuerto con ese ID", Status.BAD_REQUEST);
        }

        return new Response("Aeropuerto Registrado", Status.CREATED);

        } catch (NumberFormatException e) {
            return new Response("La longitud y latitud deben ser numéricos", Status.BAD_REQUEST);
        } catch (Exception e) {
            return new Response("Error inesperado, contacte al administrador", Status.INTERNAL_SERVER_ERROR);
        }

    }

    public static boolean verifyIdFormat(String idAux) {
        if (idAux.length() != 3) { // Verify the lenght of Id
            return false;
        }

        int upperCaseCount = 0;
        for (int i = 0; i < idAux.length(); i++) {
            if (Character.isUpperCase(idAux.charAt(i))) {
                upperCaseCount++;
            }
        }                                                  // Cont how many capital Letter the word have
        if (upperCaseCount != 3) {
            return false;
        }

        return true;
    }

    public static boolean verifyDecimalOfLongitudAndLatitude(String auxVariable) {
        boolean puntoEncontrado = false;
        int contAux = 0;

        for (int i = 0; i < auxVariable.length(); i++) {
            char caracterAux = auxVariable.charAt(i);
            if (caracterAux == '.' || caracterAux == ',') {
                puntoEncontrado = true;
                continue;
            }
            if (puntoEncontrado && Character.isDigit(caracterAux)) {
                contAux++;
                if (contAux > 4) {
                    return false;
                }
            } else if (puntoEncontrado && !Character.isDigit(caracterAux)) {
                break;
            }
        }

        return true;
    }

    public static Boolean verifyStrings(String variable) {
        for (int i = 0; i < variable.length(); i++) {
            char aux = variable.charAt(i);
            if (!Character.isLetter(aux) && !Character.isWhitespace(aux)) {
                return false;
            }
        }
        return true;
    }
}