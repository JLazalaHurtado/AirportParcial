/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.modelo.Flight;
import java.time.LocalDateTime;

/**
 *
 * @author Juan Manuel
 */
public interface ArrivalCalculator {
    LocalDateTime calculate(Flight flight);
}
