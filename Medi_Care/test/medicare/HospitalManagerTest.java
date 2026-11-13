/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medicare;


public class HospitalManagerTest {
package medicare;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalManagerTest {

    @Test void testRegisterPatient() {
        HospitalManager m = new HospitalManager();
        assertTrue(m.registerPatient(new Patient("P100", "A", "B", 20, "Female", "Flu", PatientCategory.OUTPATIENT)));
        assertEquals(1, m.getAllPatients().size());
    }
    @Test void testSearchPatient() {
        HospitalManager m = new HospitalManager();
        m.registerPatient(new Patient("P101", "Lulamo", "Siweya", 21, "Female", "Flu", PatientCategory.OUTPATIENT));
        assertNotNull(m.findPatient("P101"));
        assertNull(m.findPatient("P999"));
    }
    @Test void testUpdatePatient() {
        HospitalManager m = new HospitalManager();
        m.registerPatient(new Patient("P102", "A", "B", 20, "Female", "Flu", PatientCategory.OUTPATIENT));
        assertTrue(m.updatePatient("P102", "A", "C", 21, "Female", "Cold"));
        assertEquals("C", m.findPatient("P102").getLastName());
    }
    @Test void testDeletePatient() {
        HospitalManager m = new HospitalManager();
        m.registerPatient(new Patient("P103", "A", "B", 20, "Female", "Flu", PatientCategory.OUTPATIENT));
        assertTrue(m.deletePatient("P103"));
        assertNull(m.findPatient("P103"));
    }
    @Test void testAllocateBed() {
        HospitalManager m = new HospitalManager();
        m.registerPatient(new Inpatient("P104", "A", "B", 30, "Male", "Surgery", 1, null));
        assertTrue(m.allocateBed("P104", "B01"));
        assertEquals(1, m.getOccupiedBedCount());
    }
    @Test void testReleaseBed() {
        HospitalManager m = new HospitalManager();
        m.registerPatient(new Inpatient("P105", "A", "B", 30, "Male", "Surgery", 1, null));
        m.allocateBed("P105", "B02");
        assertTrue(m.releaseBed("B02"));
        assertEquals(0, m.getOccupiedBedCount());
    }
    @Test void testPreventDuplicateIds() {
        HospitalManager m = new HospitalManager();
        m.registerPatient(new Patient("P106", "A", "B", 20, "Female", "Flu", PatientCategory.OUTPATIENT));
        assertFalse(m.registerPatient(new Patient("P106", "X", "Y", 25, "Male", "Cold", PatientCategory.OUTPATIENT)));
    }
    @Test void testPreventAllocatingOccupiedBed() {
        HospitalManager m = new HospitalManager();
        m.registerPatient(new Inpatient("P107", "A", "B", 30, "Male", "Surgery", 1, null));
        m.registerPatient(new Inpatient("P108", "C", "D", 32, "Female", "Surgery", 1, null));
        m.allocateBed("P107", "B03");
        assertFalse(m.allocateBed("P108", "B03"));
    }
    @Test void testPreventAllocationWhenAllBedsOccupied() {
        HospitalManager m = new HospitalManager();
        for (int i = 1; i <= 20; i++) {
            String id = String.format("P%03d", i);
            m.registerPatient(new Inpatient(id, "A", "B", 30, "Male", "Surgery", 1, null));
            assertTrue(m.allocateBed(id, String.format("B%02d", i)));
        }
        m.registerPatient(new Inpatient("P121", "Extra", "Patient", 30, "Male", "Surgery", 1, null));
        assertEquals(20, m.getOccupiedBedCount());
        assertFalse(m.allocateBed("P121", "B01"));
        assertEquals(100.0, m.getOccupancyPercentage(), 0.01);
    }
    @Test void testSortBySurname() {
        HospitalManager m = new HospitalManager();
        m.registerPatient(new Patient("P201", "A", "Zulu", 20, "Female", "Flu", PatientCategory.OUTPATIENT));
        m.registerPatient(new Patient("P202", "B", "Adams", 22, "Male", "Cold", PatientCategory.OUTPATIENT));
        m.sortBySurname();
        assertEquals("Adams", m.getAllPatients().get(0).getLastName());
        m.sortByPatientId();
        assertEquals("P201", m.getAllPatients().get(0).getPatientId());
    }
    @Test void testOnlyInpatientCanGetBed() {
        HospitalManager m = new HospitalManager();
        m.registerPatient(new Patient("P203", "A", "B", 20, "Female", "Flu", PatientCategory.OUTPATIENT));
        assertFalse(m.allocateBed("P203", "B05"));
    }
}    
}
