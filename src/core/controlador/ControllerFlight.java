/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador;

import core.controlador.utils.Response;
import core.controlador.utils.Status;
import core.modelo.Flight;
import core.modelo.Location;
import core.modelo.Passenger;

import core.modelo.Plane;
import core.modelo.storage.StorageFlight;
import core.modelo.storage.StorageLocation;
import core.modelo.storage.StoragePassenger;
import core.modelo.storage.StoragePlane;
import core.services.DelayFlight;
import java.time.LocalDateTime;
import java.time.Month;
import javax.swing.JComboBox;

public class ControllerFlight {
public static Response createFlight(String idFlight, String planeId, String departureLocationId, String arrivalLocationId, String scaleLocationId,String year, String month, String day, String hour, String minutes,String hoursDurationsArrival, String minutesDurationsArrival,String hoursDurationsScale, String minutesDurationsScale) {

    if (!verifyFormatIdFlight(idFlight)) {
        return new Response("El id del vuelo no es válido", Status.BAD_REQUEST);
    }
    StoragePlane airplaneStorage = StoragePlane.getInstance();
    Plane plane = airplaneStorage.getPlane(planeId);
    if (plane == null) {
        return new Response("El avión no existe en el sistema", Status.BAD_REQUEST);
    }
    if (!verifyString(departureLocationId)) {
        return new Response("Aeropuerto de salida no puede estar vacío", Status.BAD_REQUEST);
    }
    if (!verifyString(arrivalLocationId)) {
        return new Response("Aeropuerto de llegada no puede estar vacío", Status.BAD_REQUEST);
    }
    if (!verifyString(scaleLocationId)) {
        return new Response("La opción de la escala no puede estar vacía", Status.BAD_REQUEST);
    }
    if (!verifyString(year) || !validateYear(year)) {
        return new Response("Año inválido", Status.BAD_REQUEST);
    }
    if (!verifyString(month)) {
        return new Response("Mes no puede estar vacío", Status.BAD_REQUEST);
    }
    if (!verifyString(day)) {
        return new Response("Día no puede estar vacío", Status.BAD_REQUEST);
    }
    if (!verifyString(hour)) {
        return new Response("Hora no puede estar vacía", Status.BAD_REQUEST);
    }
    if (!verifyString(minutes)) {
        return new Response("Minutos no puede estar vacío", Status.BAD_REQUEST);
    }
    if (!verifyString(hoursDurationsArrival)) {
        return new Response("Horas de vuelo no pueden estar vacías", Status.BAD_REQUEST);
    }
    if (!verifyString(minutesDurationsArrival)) {
        return new Response("Minutos de vuelo no pueden estar vacíos", Status.BAD_REQUEST);
    }
    if ((!verifyString(hoursDurationsScale) || !verifyString(minutesDurationsScale)) && !scaleLocationId.equals("")) {
        return new Response("Datos de escala no pueden estar vacíos", Status.BAD_REQUEST);
    }

    int yearAux, monthAux, dayAux, hourAux, minutesAux;
    int hoursDurationsArrivalAux, minutesDurationsArrivalAux;
    int hoursDurationsScaleAux, minutesDurationsScaleAux;

    try {
        yearAux = Integer.parseInt(year);
        monthAux = Integer.parseInt(month);
        dayAux = Integer.parseInt(day);
        hourAux = Integer.parseInt(hour);
        minutesAux = Integer.parseInt(minutes);
        hoursDurationsArrivalAux = Integer.parseInt(hoursDurationsArrival);
        minutesDurationsArrivalAux = Integer.parseInt(minutesDurationsArrival);
        hoursDurationsScaleAux = Integer.parseInt(hoursDurationsScale);
        minutesDurationsScaleAux = Integer.parseInt(minutesDurationsScale);
    } catch (NumberFormatException e) {
        return new Response("Formato de números inválido", Status.BAD_REQUEST);
    }

    if (!verifyDayWithMonth(yearAux, monthAux, dayAux)) {
        return new Response("Día inválido para el mes seleccionado", Status.BAD_REQUEST);
    }

    StorageLocation location = StorageLocation.getInstance();
    Location departureLocation = location.getLocation(departureLocationId);
    Location arrivalLocation = location.getLocation(arrivalLocationId);
    Location scaleLocation = location.getLocation(scaleLocationId);

    if (departureLocation == null || arrivalLocation == null) {
        return new Response("Aeropuertos no encontrados", Status.NOT_FOUND);
    }

    LocalDateTime departureDate = LocalDateTime.of(yearAux, monthAux, dayAux, hourAux, minutesAux);

    StorageFlight storageFlight = StorageFlight.getInstance();
    if (storageFlight.getFlight(idFlight) != null) {
        return new Response("Vuelo ya existe", Status.BAD_REQUEST);
    }

    Flight flight = new Flight(idFlight, plane, departureLocation, scaleLocation, arrivalLocation,departureDate, hoursDurationsArrivalAux, minutesDurationsArrivalAux,hoursDurationsScaleAux, minutesDurationsScaleAux);

    if (!storageFlight.addFlight(flight)) {
        return new Response("Este vuelo ya existe", Status.BAD_REQUEST);
    }

    return new Response("Este vuelo ha sido creado de forma exitosa", Status.CREATED);
}
    public static Response addPassengerToFlight(String PassId, String flightId){
        try {
            long passIdLong;
            try {
                passIdLong = Long.parseLong(PassId);
            } catch (NumberFormatException e) {
                return new Response("Passenger id must be numeric", Status.BAD_REQUEST);
            }
            
            if(flightId.isEmpty()){
                return new Response("There are not flights to add passengers", Status.BAD_REQUEST);
            }
            
            StorageFlight storageflight = StorageFlight.getInstance();
            StoragePassenger storagePass = StoragePassenger.getInstance();  
            
            Flight flight = storageflight.getFlight(flightId);
            if(flight == null){
                return new Response("Flight not found", Status.NOT_FOUND);
            }
            Passenger passenger = storagePass.getPassenger(passIdLong);
            if(passenger == null){
                return new Response("Passenger not found", Status.NOT_FOUND);
            }
            
            if(flight.getNumPassengers() >= flight.getPlane().getMaxCapacity()){
                return new Response("Plane already have max capacity", Status.BAD_REQUEST);
            }
            if(flight.getPassengers().contains(passenger)){  
                return new Response("Paasenger already exits in these flight", Status.BAD_REQUEST);
            }
            flight.addPassenger(passenger);
            passenger.addFlight(flight);
            return new Response("Passenger added successfully", Status.OK);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }



    private static boolean verifyString(String check) {
        if(check==null||check.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean verifyFormatIdFlight(String id) {
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

    public static boolean validateYear(String year) {
        if (year.length() > 4) {
            return false;
        }
        char characterYear;
        for (int i = 0; i < year.length(); i++) {
            characterYear = year.charAt(i);
            if (!Character.isDigit(characterYear)) {
                return false;
            }
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
            
            DelayFlight delayFlight = new DelayFlight();
            delayFlight.delay(flight, hoursAux, minutesAux);  
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