/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador;

import core.controlador.utils.Response;
import core.controlador.utils.Status;
import core.modelo.Passenger;
import core.modelo.Plane;
import core.modelo.storage.StoragePlane;
import javax.swing.JComboBox;

public class ControllerAirplane {

    public static Response createAirplane(String id, String brand, String model, int maxCapacity, String airline) {
        try {

            int contAuxCapitalLetters = 0;
            int contAuxNumbers = 0;
            boolean checkId = true;
            String maxCapacityAux = String.valueOf(maxCapacity);

            if (id.isEmpty() && brand.isEmpty() && model.isEmpty() && maxCapacityAux.isEmpty() && airline.isEmpty()) {
                return new Response("Los campos no deben estar vacios", Status.BAD_REQUEST);
            }
            try {
                if (maxCapacity < 0 || maxCapacity > 1000) {
                    return new Response("Capacidad del avion invalida", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Capacidad del avion debe ser numerica", Status.BAD_REQUEST);
            }
            if (!checkId) {
                return new Response("Id se encuentra en el formato incorrecto", Status.BAD_REQUEST);
            }
            StoragePlane storagePlane = StoragePlane.getInstance();
            if (!storagePlane.addPlane(new Plane(id, brand, model, maxCapacity, airline))) {
                return new Response("Avion ya existe con ese ID", Status.BAD_REQUEST);
            }
            return new Response("Avion Registrado de forma exitosa", Status.CREATED);
        } catch (Exception e) {
            return new Response("Error inesperado, contacte al administrador", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static boolean verifyFormatIdAirplane(String id) {
        int[] ExpectedPattern = {1, 1, 0, 0, 0, 0, 0};
        int[] ActualPattern = new int[7];
        char aux;
        int contAuxCapitalLetters = 0;
        int contAuxNumbers = 0;
        if (id.length() != 7) {
            return false;
        }
        for (int i = 0; i < id.length(); i++) {
            aux = id.charAt(i);
            if (Character.isLetter(aux)) {
                ActualPattern[i] = 1;
            } else {
                ActualPattern[i] = 0;
            }
        }
        for (int i = 0; i < 7; i++) {
            if (ActualPattern[i] != ExpectedPattern[i]) {
                return false;
            }
        }
        for (int i = 0; i < id.length(); i++) {
            char auxId = id.charAt(i);
            if (Character.isUpperCase(auxId)) {
                contAuxCapitalLetters++;
            } else if (Character.isDigit(auxId)) {
                contAuxNumbers++;
            }
        }
        if (contAuxCapitalLetters != 2 || contAuxNumbers != 5) {
            return false;
        }

        return true;
    }

    public static void loadFlightIntoComboBox(JComboBox<String> comboBox) {
        StoragePlane storage = StoragePlane.getInstance();
        for (Plane p : storage.getAllPlanes()) {
            comboBox.addItem(String.valueOf(p.getId()));
        }
    }

}
