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
            if (!verifyString(idPassenger)) {
                return new Response("Id no puede estar vacío", Status.BAD_REQUEST);
            }

            long idAux;
            try {
                idAux = Long.parseLong(idPassenger);
            } catch (NumberFormatException e) {
                return new Response("Id debe ser numérico", Status.BAD_REQUEST);
            }

            if (idAux < 0 || String.valueOf(idAux).length() > 15) {
                return new Response("Id inválido", Status.BAD_REQUEST);
            }

            if (!verifyString(year) || !verifyString(month) || !verifyString(day)) {
                return new Response("Fecha de nacimiento incompleta", Status.BAD_REQUEST);
            }

            int yearAux, monthAux, dayAux;
            try {
                yearAux = Integer.parseInt(year);
                monthAux = Integer.parseInt(month);
                dayAux = Integer.parseInt(day);
            } catch (NumberFormatException e) {
                return new Response("Fecha de nacimiento debe ser numérica", Status.BAD_REQUEST);
            }

            if (!verifyString(phoneCode) || !verifyString(phone)) {
                return new Response("Teléfono incompleto", Status.BAD_REQUEST);
            }

            int phoneCodeAux;
            long phoneAux;
            try {
                phoneCodeAux = Integer.parseInt(phoneCode);
                phoneAux = Long.parseLong(phone);
            } catch (NumberFormatException e) {
                return new Response("Teléfono debe ser numérico", Status.BAD_REQUEST);
            }

            if (phoneCode.length() > 3 || phoneCodeAux <= 0 || phone.length() > 11 || phoneAux < 0) {
                return new Response("Teléfono inválido", Status.BAD_REQUEST);
            }

            if (!validateDate(yearAux, dayAux, monthAux)) {
                return new Response("Día inválido para ese mes", Status.BAD_REQUEST);
            }

            if (!verifyString(firstname) || !verifyString(lastname)) {
                return new Response("Nombre y apellido requeridos", Status.BAD_REQUEST);
            }

            LocalDate birthDate = LocalDate.of(yearAux, monthAux, dayAux);
            StoragePassenger storagePassenger = StoragePassenger.getInstance();

            if (!storagePassenger.addPassenger(new Passenger(idAux, firstname, lastname, birthDate, phoneCodeAux, phoneAux, country))) {
                return new Response("Pasajero ya existe con ese ID", Status.BAD_REQUEST);
            }

            return new Response("Pasajero registrado exitosamente", Status.CREATED);
        } catch (Exception e) {
            return new Response("Error inesperado, contacte al administrador", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response updatePassenger(String id, String firstname, String lastname,
                                           String year, String month, String day,
                                           String phoneCode, String phone, String country) {
        try {
            if (!verifyString(id) || !verifyIdInArray(id)) {
                return new Response("Pasajero no encontrado", Status.NOT_FOUND);
            }

            if (!verifyString(firstname) || !verifyString(lastname)) {
                return new Response("Nombre y apellido requeridos", Status.BAD_REQUEST);
            }

            int yearInt, monthInt, dayInt;
            try {
                yearInt = Integer.parseInt(year);
                if (year.length() > 4 || yearInt > LocalDateTime.now().getYear()) {
                    return new Response("Año inválido", Status.BAD_REQUEST);
                }
                monthInt = Integer.parseInt(month);
                if (monthInt < 1 || monthInt > 12) {
                    return new Response("Mes inválido", Status.BAD_REQUEST);
                }
                dayInt = Integer.parseInt(day);
            } catch (NumberFormatException e) {
                return new Response("Fecha inválida", Status.BAD_REQUEST);
            }

            if (!validateDate(yearInt, dayInt, monthInt)) {
                return new Response("Día inválido para ese mes", Status.BAD_REQUEST);
            }

            int phoneCodeInt;
            long phoneLong;
            try {
                phoneCodeInt = Integer.parseInt(phoneCode);
                phoneLong = Long.parseLong(phone);
            } catch (NumberFormatException e) {
                return new Response("Teléfono inválido", Status.BAD_REQUEST);
            }

            if (phoneCode.length() > 3 || phoneCodeInt <= 0 || phone.length() > 11 || phoneLong < 0) {
                return new Response("Teléfono inválido", Status.BAD_REQUEST);
            }

            if (!verifyString(country)) {
                return new Response("País no puede estar vacío", Status.BAD_REQUEST);
            }

            long idLong = Long.parseLong(id);
            LocalDate birthDate = LocalDate.of(yearInt, monthInt, dayInt);

            StoragePassenger storage = StoragePassenger.getInstance();
            Passenger passenger = storage.getPassenger(idLong);
            if (passenger == null) {
                return new Response("Pasajero no encontrado", Status.NOT_FOUND);
            }

            passenger.setFirstname(firstname);
            passenger.setLastname(lastname);
            passenger.setBirthDate(birthDate);
            passenger.setCountryPhoneCode(phoneCodeInt);
            passenger.setPhone(phoneLong);
            passenger.setCountry(country);

            return new Response("Información del pasajero actualizada exitosamente", Status.OK);
        } catch (Exception ex) {
            return new Response("Error inesperado", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static void loadPassengerIdsIntoComboBox(JComboBox<String> comboBox) {
        StoragePassenger storage = StoragePassenger.getInstance();
        for (Passenger p : storage.passengerOrderById()) {
            comboBox.addItem(String.valueOf(p.getId()));
        }
    }

    public static boolean verifyString(String check) {
        return check != null && !check.trim().isEmpty();
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

    public static boolean verificationLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static boolean validateDate(int yearAux, int dayAux, int monthAux) {
        int[] dayPerMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (verificationLeapYear(yearAux)) {
            dayPerMonth[1] = 29;
        }
        return dayAux >= 1 && dayAux <= dayPerMonth[monthAux - 1];
    }
}