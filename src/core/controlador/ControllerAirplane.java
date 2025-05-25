/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador;

import core.controlador.strategy.AirplaneCreationStrategy;
import core.controlador.strategy.DefaultAirplaneCreation;
import core.controlador.utils.Response;
import core.controlador.utils.Status;
import core.controlador.validator.AirplaneValidator;
import core.modelo.Plane;
import core.services.AirplaneService;

public class ControllerAirplane {

    private static AirplaneCreationStrategy strategy = new DefaultAirplaneCreation();

    public static void setStrategy(AirplaneCreationStrategy newStrategy) {
        strategy = newStrategy;
    }

    public static Response createAirplane(String id, String brand, String model, int maxCapacity, String airline) {
        return strategy.createAirplane(id, brand, model, maxCapacity, airline);
    }

    public static boolean verifyFormatIdAirplane(String id) {
        return AirplaneValidator.verifyFormatIdAirplane(id);
    }

}
