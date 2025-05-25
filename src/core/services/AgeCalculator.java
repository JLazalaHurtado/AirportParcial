/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.modelo.Passenger;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author Juan Manuel
 */
public class AgeCalculator implements AgeService{

    @Override
    public int calculateAge(Passenger passenger) { 
        LocalDate birthDate = passenger.getBirthDate();
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
