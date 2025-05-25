/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador.validator;

import core.controlador.utils.Response;
import core.controlador.utils.Status;

/**
 *
 * @author Juan Manuel
 */
public class LocationValidator {
   public boolean areFieldsEmpty(String id, String name, String city, String country, double latitude, double longitude) {
        return city.isEmpty() || name.isEmpty() || country.isEmpty() || id.isEmpty() || 
               String.valueOf(latitude).isEmpty() || String.valueOf(longitude).isEmpty();
    }

    public static boolean verifyIdFormat(String idAux) {
        if (idAux.length() != 3) { // Verify the lenght of Id
            return false;
        }

        int upperCaseCount = 0;
        for (int i = 0; i < idAux.length(); i++) {
            if (Character.isUpperCase(idAux.charAt(i))) {
                upperCaseCount++;
            }
        }                                                  // Cont how many capital Letter the word have
        if (upperCaseCount != 3) {
            return false;
        }
        return true;
    }

    public static boolean verifyDecimalOfLongitudAndLatitude(String auxVariable) {
        boolean puntoEncontrado = false;
        int contAux = 0;

        for (int i = 0; i < auxVariable.length(); i++) {
            char caracterAux = auxVariable.charAt(i);
            if (caracterAux == '.' || caracterAux == ',') {
                puntoEncontrado = true;
                continue;
            }
            if (puntoEncontrado && Character.isDigit(caracterAux)) {
                contAux++;
                if (contAux > 4) {
                    return false;
                }
            } else if (puntoEncontrado && !Character.isDigit(caracterAux)) {
                break;
            }
        }

        return true;
    }

    public static Boolean verifyStrings(String variable) {
        for (int i = 0; i < variable.length(); i++) {
            char aux = variable.charAt(i);
            if (!Character.isLetter(aux) && !Character.isWhitespace(aux)) {
                return false;
            }
        }
        return true;
    }
}
