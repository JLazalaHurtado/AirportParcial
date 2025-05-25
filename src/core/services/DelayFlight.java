/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.modelo.Flight;

/**
 *
 * @author Juan Manuel
 */
public class DelayFlight implements DelayService {

    @Override
    public void delay(Flight flight, int hours, int minutes) {
    flight.setDepartureDate(
        flight.getDepartureDate().plusHours(hours).plusMinutes(minutes)
    );
}
    
}
