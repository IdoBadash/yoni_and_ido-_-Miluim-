package Test;

import DomainLayer.TypesOfJobs;
import DomainLayer.Worker;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestWorker {

    private Worker worker;
    @Before
    public void setUp() {
        worker = new Worker(1, "John Doe", "123456789", 1000.0, "Full-time", LocalDate.now(), new ArrayList<>());
    }

    public void testConstructor() {
        assertEquals(1, worker.getId());
        assertEquals("John Doe", worker.getName());
        assertEquals("123456789", worker.getBankAccount());
        assertEquals(1000.0, worker.getPay(), 0.001);
        assertEquals("Full-time", worker.getWorkAgreement());
        assertNotNull(worker.getDateOfStart());
        assertTrue(worker.getTypesOfJobs().isEmpty());
    }

    @Test
    public void testSetterMethods() {
        worker.setId(2);
        assertEquals(2, worker.getId());

        worker.setName("Jane Smith");
        assertEquals("Jane Smith", worker.getName());

        worker.setBankAccount("987654321");
        assertEquals("987654321", worker.getBankAccount());

        worker.setPay(1500.0);
        assertEquals(1500.0, worker.getPay(), 0.001);

        worker.setWorkAgreement("Part-time");
        assertEquals("Part-time", worker.getWorkAgreement());

        LocalDate newDate = LocalDate.of(2023, 1, 1);
        worker.setDateOfStart(newDate);
        assertEquals(newDate, worker.getDateOfStart());
    }

    @Test
    public void testAddAndRemoveJobType() {
        worker.addJobType(TypesOfJobs.CASHIER);
        assertTrue(worker.getTypesOfJobs().contains(TypesOfJobs.CASHIER));

        worker.removeJobType(TypesOfJobs.CASHIER);
        assertFalse(worker.getTypesOfJobs().contains(TypesOfJobs.CASHIER));
    }
}
