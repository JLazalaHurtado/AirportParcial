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
public interface DelayService { 
    void delay(Flight flight, int hours, int minutes);
}

