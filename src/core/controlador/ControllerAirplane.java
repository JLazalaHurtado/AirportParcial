/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador;


import core.controlador.utils.Response;
import core.controlador.utils.Status;
import core.controlador.validator.AirplaneValidator;
import core.modelo.Plane;
import core.services.AirplaneService;



public class ControllerAirplane {

    public static Response createAirplane(String id, String brand, String model, int maxCapacity, String airline) {
       
        try {
            String maxCapacityAux = String.valueOf(maxCapacity);

            if (AirplaneValidator.areFieldsEmpty(id, brand, model, maxCapacityAux, airline)) {
                return new Response("Los campos no deben estar vacios", Status.BAD_REQUEST);
            }

            if (!AirplaneValidator.isValidCapacity(maxCapacity)) {
                return new Response("Capacidad del avion invalida", Status.BAD_REQUEST);
            }

            if (!AirplaneValidator.verifyFormatIdAirplane(id)) {
                return new Response("Id se encuentra en el formato incorrecto", Status.BAD_REQUEST);
            }

            Plane plane = new Plane(id, brand, model, maxCapacity, airline);

            if (!AirplaneService.addAirplane(plane)) {
                return new Response("Avion ya existe con ese ID", Status.BAD_REQUEST);
            }

            return new Response("Avion Registrado de forma exitosa", Status.CREATED);
        } catch (Exception e) {
            return new Response("Error inesperado, contacte al administrador", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static boolean verifyFormatIdAirplane(String id) {
        return AirplaneValidator.verifyFormatIdAirplane(id);
    }

}
