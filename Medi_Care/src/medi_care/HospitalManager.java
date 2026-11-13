/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medi_care;

public class HospitalManager {
 package medicare;

import java.util.*;

public class HospitalManager {
    private ArrayList<Patient> patients = new ArrayList<>();
    private Bed[][] ward = new Bed[4][5];

    public HospitalManager() {
        int n = 1;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                ward[r][c] = new Bed(String.format("B%02d", n++));
            }
        }
    }

    public boolean registerPatient(Patient p) {
        if (findPatient(p.getPatientId()) != null) return false;
        patients.add(p);
        return true;
    }

    public Patient findPatient(String id) {
        for (Patient p : patients) if (p.getPatientId().equalsIgnoreCase(id)) return p;
        return null;
    }

    public boolean updatePatient(String id, String first, String last, int age, String gender, String cond) {
        Patient p = findPatient(id);
        if (p == null) return false;
        p.setFirstName(first); p.setLastName(last); p.setAge(age); p.setGender(gender); p.setMedicalCondition(cond);
        return true;
    }

    public boolean deletePatient(String id) {
        Patient p = findPatient(id);
        if (p == null) return false;
        if (p instanceof Inpatient) {
            String bed = ((Inpatient) p).getBedNumber();
            if (bed != null) releaseBed(bed);
        }
        patients.remove(p);
        return true;
    }

    public ArrayList<Patient> getAllPatients() { return patients; }

    public void displayAllPatients() {
        if (patients.isEmpty()) { System.out.println("No patients registered yet."); return; }
        for (Patient p : patients) p.displayDetails();
    }

    public void sortBySurname() { patients.sort(Comparator.comparing(Patient::getLastName, String.CASE_INSENSITIVE_ORDER)); }
    public void sortByPatientId() { patients.sort(Comparator.comparing(Patient::getPatientId, String.CASE_INSENSITIVE_ORDER)); }

    public Bed findBed(String bedNo) {
        for (Bed[] row : ward) for (Bed b : row) if (b.getBedNumber().equalsIgnoreCase(bedNo)) return b;
        return null;
    }

    public boolean allocateBed(String patientId, String bedNo) {
        Patient p = findPatient(patientId);
        if (p == null || !(p instanceof Inpatient)) return false;
        Bed b = findBed(bedNo);
        if (b == null || b.isOccupied()) return false;
        if (((Inpatient) p).getBedNumber() != null) return false;
        b.occupy(patientId);
        ((Inpatient) p).setBedNumber(b.getBedNumber());
        return true;
    }

    public boolean releaseBed(String bedNo) {
        Bed b = findBed(bedNo);
        if (b == null || !b.isOccupied()) return false;
        Patient p = findPatient(b.getPatientId());
        if (p instanceof Inpatient) ((Inpatient) p).setBedNumber(null);
        b.release();
        return true;
    }

    public void displayWardLayout() {
        System.out.println("\n--- Ward Layout (4 x 5) ---");
        for (Bed[] row : ward) {
            for (Bed b : row) System.out.print(b.getBedNumber() + (b.isOccupied() ? "[X] " : "[ ] "));
            System.out.println();
        }
        System.out.println("[X] = Occupied, [ ] = Available");
    }

    public void displayAvailableBeds() {
        System.out.print("Available beds: ");
        for (Bed[] row : ward) for (Bed b : row) if (!b.isOccupied()) System.out.print(b.getBedNumber() + " ");
        System.out.println();
    }

    public void displayOccupiedBeds() {
        System.out.print("Occupied beds: ");
        for (Bed[] row : ward) for (Bed b : row) if (b.isOccupied()) System.out.print(b.getBedNumber() + " (Patient " + b.getPatientId() + ") ");
        System.out.println();
    }

    public int getOccupiedBedCount() {
        int c = 0; for (Bed[] row : ward) for (Bed b : row) if (b.isOccupied()) c++; return c;
    }
    public int getAvailableBedCount() { return 20 - getOccupiedBedCount(); }
    public double getOccupancyPercentage() { return (getOccupiedBedCount() / 20.0) * 100; }

    public void generateReport() {
        System.out.println("\n===== WARD REPORT =====");
        System.out.println("Total registered patients: " + patients.size());
        System.out.println("Total occupied beds: " + getOccupiedBedCount());
        System.out.println("Total available beds: " + getAvailableBedCount());
        System.out.printf("Ward occupancy: %.1f%%\n", getOccupancyPercentage());
        System.out.println("\n-- All Patients --"); displayAllPatients();
        System.out.println("\n-- Available Beds --"); displayAvailableBeds();
        System.out.println("\n-- Occupied Beds --"); displayOccupiedBeds();
    }
}   
}
