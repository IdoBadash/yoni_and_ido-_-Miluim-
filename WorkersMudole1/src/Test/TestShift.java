package Test;

import DomainLayer.Shift;
import DomainLayer.TypesOfJobs;
import DomainLayer.Worker;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestShift {

    @Test
    public void testConstructor() {
        LocalDate date = LocalDate.now();
        Shift shift = new Shift("Monday", "Morning", 2, 1, 1, date);

        assertEquals("Monday", shift.getDay());
        assertEquals("Morning", shift.getTiming());
        assertEquals(2, shift.getCashiers());
        assertEquals(1, shift.getStorekeeper());
        assertEquals(1, shift.getManagers());
        assertEquals(date, shift.getDate());
    }

    @Test
    public void testAddingAndRemovingWorker() {
        List<TypesOfJobs> jobs1 = new ArrayList<>();
        jobs1.add(TypesOfJobs.CASHIER);
        List<TypesOfJobs> jobs2 = new ArrayList<>();
        jobs2.add(TypesOfJobs.CASHIER);
        jobs2.add(TypesOfJobs.MANAGER);

        Worker worker1 = new Worker(32, "John Doe", "123456789", 30, "Full-time", LocalDate.now(),jobs1 );
        Worker worker2 = new Worker(1, "John Doe", "123456789", 20, "Full-time", LocalDate.now(), jobs2);
        LocalDate date = LocalDate.now();
        Shift shift = new Shift("Monday", "Morning", 2, 1, 1, date);

        shift.addWorker(worker1);
        shift.addWorker(worker2);
        // Check if workers are added or not to the cashier&manager list

        assertTrue(shift.getcahsierslist().contains(worker1));
        assertTrue(shift.getcahsierslist().contains(worker2));
        assertFalse(shift.getmanagerlist().contains(worker1));


        // Remove worker1 from the shift
        shift.removeWorkerFromOneRoleInShift(TypesOfJobs.CASHIER, worker1);

        // Check if worker1 is removed from the cashier list
        assertFalse(shift.getcahsierslist().contains(worker1));
    }

}
