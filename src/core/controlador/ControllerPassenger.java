/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controlador;

import core.controlador.utils.Response;
import core.controlador.utils.Status;
import core.modelo.Passenger;
import core.modelo.storage.StoragePassenger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.swing.JComboBox;

public class ControllerPassenger {

    public static Response registerPassenger(String idPassenger, String firstname, String lastname, String year, String day, String month, String phoneCode, String phone, String country) {
        try {
            if (idPassenger == null || idPassenger.trim().isEmpty()) {
                return new Response("Id no puede estar vacío", Status.BAD_REQUEST);
            }

            long idAux;
            try {
                idAux = Long.parseLong(idPassenger);
            } catch (NumberFormatException e) {
                return new Response("Id debe ser numérico", Status.BAD_REQUEST);
            }

            if (idAux < 0) {
                return new Response("Id debe ser mayor o igual a cero", Status.BAD_REQUEST);
            }

            if (String.valueOf(idAux).length() > 15) {
                return new Response("Id debe tener menos de 15 dígitos", Status.BAD_REQUEST);
            }

            if (year.isEmpty() || month.isEmpty() || day.isEmpty()) {
                return new Response("La fecha de nacimiento no puede estar vacía", Status.BAD_REQUEST);
            }

            int yearAux, monthAux, dayAux;
            try {
                yearAux = Integer.parseInt(year);
                monthAux = Integer.parseInt(month);
                dayAux = Integer.parseInt(day);
            } catch (NumberFormatException e) {
                return new Response("La fecha de nacimiento debe ser numérica", Status.BAD_REQUEST);
            }

            if (year.length() > 4) {
                return new Response("Fecha del año inválida", Status.BAD_REQUEST);
            }

            if (monthAux < 1 || monthAux > 12) {
                return new Response("Mes inválido", Status.BAD_REQUEST);
            }

            if (phoneCode.isEmpty() || phone.isEmpty()) {
                return new Response("El código de número y el número no pueden estar vacíos", Status.BAD_REQUEST);
            }

            int phoneCodeAux;
            long phoneAux;
            try {
                phoneCodeAux = Integer.parseInt(phoneCode);
                phoneAux = Long.parseLong(phone);
            } catch (NumberFormatException e) {
                return new Response("El código y el número deben ser numéricos", Status.BAD_REQUEST);
            }

            if (phoneCode.length() > 3 && phoneCodeAux < 0) {
                return new Response("Código del número celular inválido", Status.BAD_REQUEST);
            }

            if (phone.length() > 11 && phoneAux < 0) {
                return new Response("Número inválido", Status.BAD_REQUEST);
            }
            if (!validateDate(yearAux, dayAux, monthAux)) {
                return new Response("Dia invalido para ese mes", Status.BAD_REQUEST);
            }

            if (firstname.isEmpty() || lastname.isEmpty()) {
                return new Response("El nombre y el apellido no pueden estar vacíos", Status.BAD_REQUEST);
            }

            LocalDate birthDate = LocalDate.of(yearAux, monthAux, dayAux);
            StoragePassenger storagePassenger = StoragePassenger.getInstance();

            if (!storagePassenger.addPassenger(new Passenger(idAux, firstname, lastname, birthDate, phoneCodeAux, phoneAux, country))) {
                return new Response("Pasajero ya existe con ese ID", Status.BAD_REQUEST);
            }

            return new Response("Pasajero registrado de forma exitosa", Status.CREATED);
        } catch (Exception e) {
            return new Response("Error inesperado, contacte al administrador", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response updatePassenger(String id, String firstnameN, String lastnameN,
            String yearN, String monthN, String dayN, String countryPhoneCodeN, String phoneN, String countryN) {
        try {
            long idLong;
            int yearInt;
            int monthInt;
            int dayInt;
            int countryPhoneCodeInt;
            long phoneLong;
            
            idLong = Long.parseLong(id);

            if (firstnameN.equals("")) {
                return new Response("Firstname must be not empty", Status.BAD_REQUEST);
            }
            if (lastnameN.equals("")) {
                return new Response("Lastname must be not empty", Status.BAD_REQUEST);
            }
            
            try {
                yearInt = Integer.parseInt(yearN);
                if (yearN.length() > 4) {
                    return new Response("Date of year invalid", Status.BAD_REQUEST);
                }
                if (yearInt > LocalDateTime.now().getYear()) {
                    return new Response("Invalid year", Status.BAD_REQUEST);
                }
                
            } catch (NumberFormatException e) {
                if (yearN.equals("")) {
                    return new Response("Year must be not empty", Status.BAD_REQUEST);
                }
                
                return new Response("Year must be just numeric", Status.BAD_REQUEST);
            }
            try {
                monthInt = Integer.parseInt(monthN);
                if (monthInt > 12) {
                    return new Response("Month invalid", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Month must be selected", Status.BAD_REQUEST);
            }
            try {
                dayInt = Integer.parseInt(dayN);
                if (dayInt > 31) {
                    return new Response("Month invalid", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Day must be selected", Status.BAD_REQUEST);
            }

            try {
                countryPhoneCodeInt = Integer.parseInt(countryPhoneCodeN);
                if (countryPhoneCodeN.length() > 3) {
                    return new Response("Country phone code must have 3 or less than 3 digits", Status.BAD_REQUEST);
                }
                if (countryPhoneCodeInt < 0) {
                    return new Response("Country phone code must be greater than 0", Status.BAD_REQUEST);
                }

            } catch (NumberFormatException e) {
                if (countryPhoneCodeN.equals("")) {
                    return new Response("Country phone code must be not empty", Status.BAD_REQUEST);
                }
                return new Response("Country phone code must be numeric", Status.BAD_REQUEST);
            }

            try {
                phoneLong = Long.parseLong(phoneN);
                if (phoneLong < 0) {
                    return new Response("Phone must be greater than 0", Status.BAD_REQUEST);
                }
                if (phoneN.length() > 11) {
                    return new Response("Phone must have less than 11 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                if (phoneN.equals("")) {
                    return new Response("Phone must be not empty", Status.BAD_REQUEST);
                }
                return new Response("Phone number must be numeric", Status.BAD_REQUEST);
            }

            if (countryN.equals("")) {
                return new Response("Country must be not empty", Status.BAD_REQUEST);
            }

            LocalDate birthDate = LocalDate.of(yearInt, monthInt, dayInt);
            StoragePassenger storagePass = StoragePassenger.getInstance();
            Passenger passenger = storagePass.getPassenger(idLong);
            
            if(passenger == null){
                return new Response("Passenger not found", Status.BAD_REQUEST);
            }else{
                passenger.setFirstname(firstnameN);
                passenger.setLastname(lastnameN);
                passenger.setBirthDate(birthDate);
                passenger.setCountryPhoneCode(countryPhoneCodeInt);
                passenger.setPhone(phoneLong);
                passenger.setCountry(countryN);
                return new Response("Passenger info was updated successfully", Status.OK);
            }

        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }

    }  

    public static boolean verificationLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static boolean validateDate(int yearAux, int dayAux, int monthAux) {
        int[] dayPerMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (verificationLeapYear(yearAux)) {
            dayPerMonth[1] = 29;
        }

        if (dayAux < 1 || dayAux > dayPerMonth[monthAux - 1]) {
            return false;
        }
        return true;
    }

    public static void loadPassengerIdsIntoComboBox(JComboBox<String> comboBox) {
        StoragePassenger storage = StoragePassenger.getInstance();

        for (Passenger p : storage.passengerOrderById()) {
            comboBox.addItem(String.valueOf(p.getId()));
        }
    }

    public static boolean verifyString(String check) {
        if (check == null || check.isEmpty()) {
            return false;
        }
        return true;
    }

    private static boolean verifyIdInArray(String id) {
        try {
            long idAux = Long.parseLong(id);
            StoragePassenger storage = StoragePassenger.getInstance();
            return storage.getPassenger(idAux) != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
