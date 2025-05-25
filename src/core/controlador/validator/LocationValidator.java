/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador.validator;

/**
 *
 * @author Juan Manuel
 */
public class LocationValidator {
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
