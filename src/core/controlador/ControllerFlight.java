/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador;

import core.controlador.utils.Response;
import core.controlador.utils.Status; 
import core.controlador.validator.FlightValidator;
import core.modelo.Plane;
import core.modelo.storage.StoragePlane;


import javax.swing.JComboBox;

public class ControllerFlight {
    
    public static Response createFlight(String id, String planeId, String departureLocationId, String arrivalLocationId, String scaleLocationId, String year, String month, int day, int hour, int minutes, int hoursDurationsArrival, int minutesDurationsArrival, int hoursDurationsScale, int minutesDurationsScale) {

        Plane plane = StoragePlane.getInstance().getPlane(planeId);
        if (plane == null) {
            return new Response("El avión no existe en el sistema", Status.BAD_REQUEST);
        }

        try {
            if (!FlightValidator.verifyFormatIdAirplane(id)) {
                return new Response("Id tiene un formato incorrecto", Status.BAD_REQUEST);
            }

            if (!FlightValidator.verifyLengthYear(year)) {
                return new Response("Año invalido", Status.BAD_REQUEST);
            }

            if (scaleLocationId.isEmpty() && (hoursDurationsScale > 0 || minutesDurationsScale > 0)) {
                return new Response("Horas de escala invalidas, ya que no hay escalas", Status.BAD_REQUEST);
            }

            int monthAux = Integer.parseInt(month);
            int yearAux = Integer.parseInt(year);

            if (!FlightValidator.verifyDayWithMonth(monthAux, day, yearAux)) {
                return new Response("Dia invalido para el mes seleccionado", Status.BAD_REQUEST);
            }

            // Aquí faltaría crear y guardar el vuelo (no implementado aún)
            return new Response("Vuelo creado de forma exitosa", Status.CREATED);

        } catch (Exception e) {
            return new Response("Error inesperado, contacte al administrador", Status.INTERNAL_SERVER_ERROR);
        }
    }


    public static void loadFlightIDSintoComboBox(JComboBox<String> comboBox) {
        core.vista.helper.FlightViewHelper.loadFlightIDsIntoComboBox(comboBox);
    }

    public static void loadDepartureLocationIntoComboBox(JComboBox<String> comboBox) {
        core.vista.helper.FlightViewHelper.loadDepartureLocations(comboBox);
    }

    public static void LoadArrivalLocationIntoComboBox(JComboBox<String> comboBox) {
        core.vista.helper.FlightViewHelper.loadArrivalLocations(comboBox);
    }

    public static void loadScaleLocationIntoComboBox(JComboBox<String> comboBox) {
        core.vista.helper.FlightViewHelper.loadScaleLocations(comboBox);
    }
}