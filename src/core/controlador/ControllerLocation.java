/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador;

import core.controlador.utils.Response;
import core.controlador.utils.Status;

import static core.controlador.validator.LocationValidator.verifyDecimalOfLongitudAndLatitude;
import static core.controlador.validator.LocationValidator.verifyIdFormat;
import static core.controlador.validator.LocationValidator.verifyStrings;
import core.modelo.Location;
import core.modelo.storage.StorageLocation;

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
}
