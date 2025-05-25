/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;
import core.modelo.Flight;
import core.modelo.storage.StorageFlight;
/**
 *
 * @author Juan Manuel
 */
public class FlightService {
    public static boolean existsFlight(String flightId) {
        return StorageFlight.getInstance().getFlight(flightId) != null;
    }

    public static Flight getFlight(String flightId) {
        return StorageFlight.getInstance().getFlight(flightId);
    }

    public static void delayFlight(Flight flight, int hours, int minutes) {
        DelayFlight delayFlight = new DelayFlight();
        delayFlight.delay(flight, hours, minutes); 
    }
}
