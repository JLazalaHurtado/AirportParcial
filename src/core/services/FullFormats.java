/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.modelo.Passenger;

/**
 *
 * @author Juan Manuel
 */
public class FullFormats {
    public static String getFullname(Passenger passenger) {
        String firstname = passenger.getFirstname();
        String lastname = passenger.getLastname();
        return firstname + " " + lastname;
    }
}
