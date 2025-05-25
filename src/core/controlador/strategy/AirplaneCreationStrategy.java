/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador.strategy;

import core.controlador.utils.Response;

/**
 *
 * @author Juan Manuel
 */
public interface AirplaneCreationStrategy {
    Response createAirplane(String id, String brand, String model, int maxCapacity, String airline);
} 
