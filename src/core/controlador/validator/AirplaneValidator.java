/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador.validator;

/**
 *
 * @author Juan Manuel
 */
public class AirplaneValidator {
    public static boolean areFieldsEmpty(String id, String brand, String model, String capacity, String airline) {
        return id.isEmpty() || brand.isEmpty() || model.isEmpty() || capacity.isEmpty() || airline.isEmpty();
    }

    public static boolean isValidCapacity(int maxCapacity) {
        return maxCapacity >= 0 && maxCapacity <= 1000;
    }

    public static boolean verifyFormatIdAirplane(String id) {
        int[] ExpectedPattern = {1, 1, 0, 0, 0, 0, 0};
        int[] ActualPattern = new int[7];
        int contAuxCapitalLetters = 0;
        int contAuxNumbers = 0;

        if (id.length() != 7) return false;

        for (int i = 0; i < id.length(); i++) {
            ActualPattern[i] = Character.isLetter(id.charAt(i)) ? 1 : 0;
        }
        for (int i = 0; i < 7; i++) {
            if (ActualPattern[i] != ExpectedPattern[i]) return false;
        }

        for (int i = 0; i < id.length(); i++) {
            char auxId = id.charAt(i);
            if (Character.isUpperCase(auxId)) contAuxCapitalLetters++;
            else if (Character.isDigit(auxId)) contAuxNumbers++;
        }

        return contAuxCapitalLetters == 2 && contAuxNumbers == 5;
    }
}
