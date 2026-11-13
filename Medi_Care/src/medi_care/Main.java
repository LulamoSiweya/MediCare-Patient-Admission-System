/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medi_care;


public class Main {
package medicare;

import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static HospitalManager manager = new HospitalManager();

    public static void main(String[] args) {
        manager.registerPatient(new Inpatient("P001", "Lulamo", "Siweya", 21, "Female", "Flu", 1, null));
        manager.registerPatient(new Patient("P002", "Thandi", "Dube", 30, "Female", "Check-up", PatientCategory.OUTPATIENT));
        manager.registerPatient(new Patient("P003", "Sipho", "Nkosi", 45, "Male", "Fracture", PatientCategory.EMERGENCY));

        while (true) {
            System.out.println("\n===== MediCare Hospital - Patient Admission System =====");
            System.out.println("1. Register a new patient");
            System.out.println("2. Search for a patient by ID");
            System.out.println("3. Update a patient");
            System.out.println("4. Delete a patient");
            System.out.println("5. Display all patients");
            System.out.println("6. Allocate a bed to an inpatient");
            System.out.println("7. Release a bed");
            System.out.println("8. Display ward layout");
            System.out.println("9. Display available beds");
            System.out.println("10. Display occupied beds");
            System.out.println("11. Generate ward report");
            System.out.println("12. Sort patients by surname");
            System.out.println("13. Sort patients by Patient ID");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try { choice = Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { System.out.println("Please enter a number."); continue; }

            if (choice == 0) { System.out.println("Goodbye! Stay safe."); break; }
            switch (choice) {
                case 1: register(); break;
                case 2: System.out.print("Enter Patient ID: "); Patient p = manager.findPatient(sc.nextLine()); if (p == null) System.out.println("Patient not found."); else p.displayDetails(); break;
                case 3: update(); break;
                case 4: System.out.print("Enter Patient ID to delete: "); System.out.println(manager.deletePatient(sc.nextLine()) ? "Patient deleted." : "Patient not found."); break;
                case 5: manager.displayAllPatients(); break;
                case 6: System.out.print("Enter Inpatient ID: "); String pid = sc.nextLine(); System.out.print("Enter Bed (e.g. B01): "); String bed = sc.nextLine(); System.out.println(manager.allocateBed(pid, bed) ? "Bed allocated!" : "Could not allocate bed. Check: patient must be Inpatient, bed must be free."); break;
                case 7: System.out.print("Enter Bed to release (e.g. B01): "); System.out.println(manager.releaseBed(sc.nextLine()) ? "Bed released." : "Bed not found or already free."); break;
                case 8: manager.displayWardLayout(); break;
                case 9: manager.displayAvailableBeds(); break;
                case 10: manager.displayOccupiedBeds(); break;
                case 11: manager.generateReport(); break;
                case 12: manager.sortBySurname(); System.out.println("Sorted by surname."); manager.displayAllPatients(); break;
                case 13: manager.sortByPatientId(); System.out.println("Sorted by Patient ID."); manager.displayAllPatients(); break;
                default: System.out.println("Invalid option, try again.");
            }
        }
    }

    private static void register() {
        System.out.print("Patient ID: "); String id = sc.nextLine();
        if (manager.findPatient(id) != null) { System.out.println("Error: That Patient ID already exists."); return; }
        System.out.print("First Name: "); String first = sc.nextLine();
        System.out.print("Last Name: "); String last = sc.nextLine();
        System.out.print("Age: "); int age = Integer.parseInt(sc.nextLine());
        System.out.print("Gender: "); String gender = sc.nextLine();
        System.out.print("Medical Condition: "); String cond = sc.nextLine();
        System.out.print("Category (1=Inpatient, 2=Outpatient, 3=Emergency): "); int c = Integer.parseInt(sc.nextLine());
        if (c == 1) {
            System.out.print("Ward Number: "); int ward = Integer.parseInt(sc.nextLine());
            manager.registerPatient(new Inpatient(id, first, last, age, gender, cond, ward, null));
            System.out.println("Inpatient registered. Now allocate a bed from the menu.");
        } else if (c == 2) {
            manager.registerPatient(new Patient(id, first, last, age, gender, cond, PatientCategory.OUTPATIENT));
            System.out.println("Outpatient registered.");
        } else if (c == 3) {
            manager.registerPatient(new Patient(id, first, last, age, gender, cond, PatientCategory.EMERGENCY));
            System.out.println("Emergency patient registered.");
        } else System.out.println("Invalid category.");
    }

    private static void update() {
        System.out.print("Enter Patient ID to update: "); String id = sc.nextLine();
        System.out.print("New First Name: "); String first = sc.nextLine();
        System.out.print("New Last Name: "); String last = sc.nextLine();
        System.out.print("New Age: "); int age = Integer.parseInt(sc.nextLine());
        System.out.print("New Gender: "); String gender = sc.nextLine();
        System.out.print("New Medical Condition: "); String cond = sc.nextLine();
        System.out.println(manager.updatePatient(id, first, last, age, gender, cond) ? "Patient updated." : "Patient not found.");
    }
}    
}
