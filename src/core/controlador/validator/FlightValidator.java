/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador.validator;

/**
 *
 * @author Juan Manuel
 */
public class FlightValidator {
    public static boolean verifyFormatIdAirplane(String id) {
        int[] ExpectedPattern = {1, 1, 1, 0, 0, 0};
        int[] ActualPattern = new int[6];
        int contAuxCapitalLetters = 0;
        int contAuxNumbers = 0;
        if (id.length() != 6) return false;

        for (int i = 0; i < id.length(); i++) {
            char aux = id.charAt(i);
            ActualPattern[i] = Character.isLetter(aux) ? 1 : 0;
            if (Character.isUpperCase(aux)) contAuxCapitalLetters++;
            else if (Character.isDigit(aux)) contAuxNumbers++;
        }

        for (int i = 0; i < 6; i++) {
            if (ActualPattern[i] != ExpectedPattern[i]) return false;
        }

        return contAuxCapitalLetters == 3 && contAuxNumbers == 3;
    }

    public static boolean verifyDayWithMonth(int month, int day, int year) {
        int[] dayPerMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isLeapYear(year)) dayPerMonth[1] = 29;
        return day >= 1 && day <= dayPerMonth[month - 1];
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static boolean verifyLengthYear(String year) {
        return year.length() <= 4;
    }
}
