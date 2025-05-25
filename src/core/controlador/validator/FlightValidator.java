/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador.validator;

import core.controlador.utils.Response;
import core.controlador.utils.Status;
import core.modelo.Flight;
import core.services.FlightService;

/**
 *
 * @author Juan Manuel
 */
public class FlightValidator {
    public static boolean verifyFormatIdAirplane(String id) {
        int[] ExpectedPattern = {1, 1, 1, 0, 0, 0};
        int[] ActualPattern = new int[6];
        int contAuxCapitalLetters = 0;
        int contAuxNumbers = 0;
        if (id.length() != 6) return false;

        for (int i = 0; i < id.length(); i++) {
            char aux = id.charAt(i);
            ActualPattern[i] = Character.isLetter(aux) ? 1 : 0;
            if (Character.isUpperCase(aux)) contAuxCapitalLetters++;
            else if (Character.isDigit(aux)) contAuxNumbers++;
        }

        for (int i = 0; i < 6; i++) {
            if (ActualPattern[i] != ExpectedPattern[i]) return false;
        }

        return contAuxCapitalLetters == 3 && contAuxNumbers == 3;
    }
    public static Response verifyDelay(String flightId, String minutes, String hours) {
        try {
            if (flightId == null || flightId.isEmpty())
                return new Response("Id del vuelo no puede estar vacío", Status.BAD_REQUEST);
            if (minutes == null || minutes.isEmpty())
                return new Response("Los minutos no pueden estar vacíos", Status.BAD_REQUEST);
            if (hours == null || hours.isEmpty())
                return new Response("La hora no puede estar vacía", Status.BAD_REQUEST);

            int minutesAux = Integer.parseInt(minutes);
            int hoursAux = Integer.parseInt(hours);

            if (minutesAux <= 0)
                return new Response("Los minutos no pueden ser menores o iguales a cero", Status.BAD_REQUEST);
            if (hoursAux <= 0)
                return new Response("Las horas no pueden ser menores o iguales a cero", Status.BAD_REQUEST);

            Flight flight = FlightService.getFlight(flightId);
            if (flight == null)
                return new Response("Id de vuelo no encontrado", Status.BAD_REQUEST);

            FlightService.delayFlight(flight, hoursAux, minutesAux);
            return new Response("Vuelo retrasado con éxito", Status.CREATED);

        } catch (NumberFormatException e) {
            return new Response("Horas o minutos no son números válidos", Status.BAD_REQUEST);
        } catch (Exception e) {
            return new Response("Error inesperado, contacte al administrador", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static boolean verifyDayWithMonth(int month, int day, int year) {
        int[] dayPerMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isLeapYear(year)) dayPerMonth[1] = 29;
        return day >= 1 && day <= dayPerMonth[month - 1];
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static boolean verifyLengthYear(String year) {
        return year.length() <= 4;
    }
}
