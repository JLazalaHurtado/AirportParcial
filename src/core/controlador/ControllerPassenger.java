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

            if (phoneCode.length() > 3 && phoneCode.equals("0")) {
                return new Response("Código del número celular inválido", Status.BAD_REQUEST);
            }

            if (phone.length() > 10) {
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

    public static Response updatePassengerInfo(String id, String firstName, String lastName, String year, String month, String day, String phoneCode, String phone, String country) {
        try {
            if (!verifyString(id)) {
                return new Response("Id no puede estar vacío", Status.BAD_REQUEST);
            }

            if (!verifyIdInArray(id)) {
                return new Response("Pasajero no encontrado", Status.NOT_FOUND);
            }

            if (!verifyString(firstName)) {
                return new Response("Nombre no puede estar vacío", Status.BAD_REQUEST);
            }

            if (!verifyString(lastName)) {
                return new Response("Apellido no puede estar vacío", Status.BAD_REQUEST);
            }

            if (!verifyString(year)) {
                return new Response("Año no puede estar vacío", Status.BAD_REQUEST);
            }

            if (!verifyString(month)) {
                return new Response("Mes no puede estar vacío", Status.BAD_REQUEST);
            }

            if (!verifyString(day)) {
                return new Response("Día no puede estar vacío", Status.BAD_REQUEST);
            }

            if (!verifyString(phoneCode)) {
                return new Response("Código de teléfono no puede estar vacío", Status.BAD_REQUEST);
            }

            if (!verifyString(phone)) {
                return new Response("Número de teléfono no puede estar vacío", Status.BAD_REQUEST);
            }

            if (!verifyString(country)) {
                return new Response("País no puede estar vacío", Status.BAD_REQUEST);
            }

            long idAux = Long.parseLong(id);
            int yearAux = Integer.parseInt(year);
            int monthAux = Integer.parseInt(month);
            int dayAux = Integer.parseInt(day);
            int phoneCodeAux = Integer.parseInt(phoneCode);
            long phoneAux = Long.parseLong(phone);

            if (!validateDate(yearAux, dayAux, monthAux)) {
                return new Response("Día inválido para el mes ingresado", Status.BAD_REQUEST);
            }

            if (phoneCode.length() > 3 || phoneCode.equals("0")) {
                return new Response("Código de número telefónico inválido", Status.BAD_REQUEST);
            }

            if (phone.length() > 10) {
                return new Response("Número telefónico inválido por la cantidad de dígitos", Status.BAD_REQUEST);
            }

            LocalDate birthDate = LocalDate.of(yearAux, monthAux, dayAux);
            Passenger updatedPassenger = new Passenger(idAux, firstName, lastName, birthDate, phoneCodeAux, phoneAux, country);

            StoragePassenger storagePassenger = StoragePassenger.getInstance();
            Passenger existingPassenger = storagePassenger.getPassenger(idAux);

            if (existingPassenger == null) {
                return new Response("No se pudo encontrar pasajero con ese ID", Status.NOT_FOUND);
            }

            return new Response("Pasajero actualizado exitosamente", Status.CREATED);
        } catch (Exception e) {
            return new Response("Error inesperado, contacte al administrador", Status.INTERNAL_SERVER_ERROR);
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
