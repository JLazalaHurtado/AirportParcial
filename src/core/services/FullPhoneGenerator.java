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
public class FullPhoneGenerator implements GeneratorServices { 

    @Override
    public String generateFullPhone(Passenger passenger) {
        int countryPhoneCode = passenger.getCountryPhoneCode();
        int phone = (int) passenger.getPhone();
        return "+" + countryPhoneCode + " " + phone; 
    }  
}
