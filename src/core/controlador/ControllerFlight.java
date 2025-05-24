/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador;

import core.controlador.utils.Response;
import core.controlador.utils.Status;
import core.modelo.Flight;
import core.modelo.Location;

import core.modelo.Plane;
import core.modelo.storage.StorageFlight;
import core.modelo.storage.StoragePlane;
import javax.swing.JComboBox;

public class ControllerFlight {

    public static Response createFlight(String id, String planeId, String departureLocationId, String arrivalLocationId, String scaleLocationId, String year, String month, int day, int hour, int minutes, int hoursDurationsArrival, int minutesDurationsArrival, int hoursDurationsScale, int minutesDurationsScale) {
        StoragePlane airplaneStorage = StoragePlane.getInstance();
        Plane plane = airplaneStorage.getPlane(planeId);
        if (plane == null) {
            return new Response("El avión no existe en el sistema", Status.BAD_REQUEST);
        }                                                                               // validate if the airplane exists in the system or not
        try {
            boolean checkId;
            boolean checkYear;
            checkId = verifyFormatIdAirplane(id);
            checkYear = verifyLenghtYear(year);
            if (!checkId) {
                return new Response("Id tiene un formato incorrecto", Status.BAD_REQUEST);
            }
            if (!checkYear) {
                return new Response("Año invalido", Status.BAD_REQUEST);
            }
            if (scaleLocationId.isEmpty()) {
                if (hoursDurationsScale > 0 && minutesDurationsScale > 0) {
                    return new Response("Horas de escala invalidas, ya que no hay escalas", Status.BAD_REQUEST);
                }
            }  // Verify if it not scale, duration must be zero 

            int monthAux = Integer.parseInt(month);
            int yearAux = Integer.parseInt(year);

            boolean verifyDay = verifyDayWithMonth(monthAux, day, yearAux);
            if (!verifyDay) {
                return new Response("Dia invalido para el mes seleccionado", Status.BAD_REQUEST);
            }
             StorageFlight storageFlight = StorageFlight.getInstance();
             

            return new Response("Vuelo creado de forma exitosa", Status.CREATED);
        } catch (Exception e) {
            return new Response("Error inesperado, contacte al administrador", Status.INTERNAL_SERVER_ERROR);
        }

    }

    public static boolean verifyFormatIdAirplane(String id) {
        int[] ExpectedPattern = {1, 1, 1, 0, 0, 0};
        int[] ActualPattern = new int[6];
        char aux;
        int contAuxCapitalLetters = 0;
        int contAuxNumbers = 0;
        if (id.length() != 6) {
            return false;
        }
        for (int i = 0; i < id.length(); i++) {
            aux = id.charAt(i);
            if (Character.isLetter(aux)) {
                ActualPattern[i] = 1;
            } else {
                ActualPattern[i] = 0;
            }
        }
        for (int i = 0; i < 6; i++) {
            if (ActualPattern[i] != ExpectedPattern[i]) {
                return false;
            }
        }
        for (int i = 0; i < id.length(); i++) { // Verify if the Id have the 
            char auxId = id.charAt(i);
            if (Character.isUpperCase(auxId)) {
                contAuxCapitalLetters++;
            } else if (Character.isDigit(auxId)) {
                contAuxNumbers++;
            }
        }
        if (contAuxCapitalLetters != 3 || contAuxNumbers != 3) {
            return false;
        }

        return true;
    }

    public static boolean verifyDayWithMonth(int month, int day, int year) {
        int[] dayPerMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (verificationLeapYear(year)) {
            dayPerMonth[1] = 29;
        }
        if (day < 1 || day > dayPerMonth[month - 1]) {
            return false;
        }
        return true;
    }

    public static boolean verificationLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static boolean verifyLenghtYear(String year) {
        if (year.length() > 4) {
            return false;
        }

        return true;
    }

    public static Response verifyDelay(String flightId, String minutes, String hours) {
        try {
            if (flightId == null || flightId.isEmpty()) {
                return new Response("Id del vuelo no puede estar vacío", Status.BAD_REQUEST);
            }

            if (minutes == null || minutes.isEmpty()) {
                return new Response("Los minutos no pueden estar vacíos", Status.BAD_REQUEST);
            }

            if (hours == null || hours.isEmpty()) {
                return new Response("La hora no puede estar vacía", Status.BAD_REQUEST);
            }

            int minutesAux = Integer.parseInt(minutes);
            int hoursAux = Integer.parseInt(hours);

            if (minutesAux <= 0) {
                return new Response("Los minutos no pueden ser menores o iguales a cero", Status.BAD_REQUEST);
            }

            if (hoursAux <= 0) {
                return new Response("Las horas no pueden ser menores o iguales a cero", Status.BAD_REQUEST);
            }

            StorageFlight storage = StorageFlight.getInstance();
            Flight flight = storage.getFlight(flightId);

            if (flight == null) {
                return new Response("Id de vuelo no encontrado", Status.BAD_REQUEST);
            }

            flight.delay(hoursAux, minutesAux);
            return new Response("Vuelo retrasado con éxito", Status.CREATED);

        } catch (NumberFormatException e) {
            return new Response("Horas o minutos no son números válidos", Status.BAD_REQUEST);
        } catch (Exception e) {
            return new Response("Error inesperado, contacte al administrador", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static void loadFlightIDSintoComboBox(JComboBox<String> comboBox) {
        StorageFlight storage = StorageFlight.getInstance();
        for (Flight p : storage.getAllFlight()) {
            comboBox.addItem(String.valueOf(p.getId()));
        }
    }

    public static void loadDepartureLocationIntoComboBox(JComboBox<String> comboBox) {
        StorageFlight storage = StorageFlight.getInstance();
        for (Location p : storage.getAllDepartureLocations()) {
            comboBox.addItem(p.toString());
        }
    }

    public static void LoadArrivalLocationIntoComboBox(JComboBox<String> comboBox) {
        StorageFlight storage = StorageFlight.getInstance();
        for (Location p : storage.getAllDepartureLocations()) {
            comboBox.addItem(p.toString());
        }
    }

    public static void loadScaleLocationIntoComboBox(JComboBox<String> comboBox) {
        StorageFlight storage = StorageFlight.getInstance();
        String scaleAux;
        for (Location p : storage.getAllScaleLocations()) {
            if (p != null) {
                comboBox.addItem(p.toString());
            }

        }
    }

}
