package Test;

import static org.junit.Assert.*;
import DomainLayer.Shift;
import DomainLayer.TypesOfJobs;
import DomainLayer.WeeklySchedule;
import DomainLayer.Worker;
import org.junit.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestWeeklySchedule {

    @Test
    public void testAddWorkerForWeek() {


        // Creating workers
        List<TypesOfJobs> jobs1 = new ArrayList<>();
        jobs1.add(TypesOfJobs.CASHIER);
        Worker worker1 = new Worker(2065, "John Doe", "123456789", 30, "Full-time", LocalDate.now(), jobs1);

        List<TypesOfJobs> jobs2 = new ArrayList<>();
        jobs2.add(TypesOfJobs.CASHIER);
        jobs2.add(TypesOfJobs.MANAGER);
        Worker worker2 = new Worker(2198, "Jane Smith", "987654321", 25, "Part-time", LocalDate.now(), jobs2);

        List<TypesOfJobs> jobs3 = new ArrayList<>();
        jobs3.add(TypesOfJobs.STOREKEEPER);
        Worker worker3 = new Worker(2389, "Emily Johnson", "1122334455", 28, "Full-time", LocalDate.now(), jobs3);


        LocalDate startDate = LocalDate.of(2024, 6, 2);
        LocalDate endDate = LocalDate.of(2024, 6, 6);
        WeeklySchedule week = new WeeklySchedule();
        Shift[][] shifts = new Shift[5][2];

        shifts[0][0] = new Shift("Sunday", "Morning", 2, 1, 1, startDate);
        shifts[0][1] = new Shift("Sunday", "Night", 1, 1, 1, startDate);
        shifts[1][0] = new Shift("Monday", "Morning", 3, 2, 1, startDate.plusDays(1));
        shifts[1][1] = new Shift("Monday", "Night", 2, 2, 1, startDate.plusDays(1));
        shifts[2][0] = new Shift("Tuesday", "Morning", 1, 1, 1, startDate.plusDays(2));
        shifts[2][1] = new Shift("Tuesday", "Night", 2, 1, 1, startDate.plusDays(2));
        shifts[3][0] = new Shift("Wednesday", "Morning", 3, 2, 1, startDate.plusDays(3));
        shifts[3][1] = new Shift("Wednesday", "Night", 2, 2, 2, startDate.plusDays(3));
        shifts[4][0] = new Shift("Thursday", "Morning", 2, 1, 1, startDate.plusDays(4));
        shifts[4][1] = new Shift("Thursday", "Night", 1, 1, 1, startDate.plusDays(4));

        week.setId(1);
        week.setShifts(shifts);
        week.setFromDate(startDate);
        week.setToDate(endDate);

        for (int i = 0 ; i < 5 ; i++){
            for (int j = 0; j < 2; j++) {
                worker1.addAvailableShift(week.getShifts()[i][j]);
                if (j == 0)
                    worker2.addAvailableShift(week.getShifts()[i][j]);
                else
                    worker3.addAvailableShift(week.getShifts()[i][j]);
            }
        }
        week.addWorkerForWeek(worker1);
        week.addWorkerForWeek(worker2);
        week.addWorkerForWeek(worker3);

        week.showWeeklyArrangement();
        assertFalse(week.getShifts()[0][0].getcahsierslist().contains(worker3));
        assertFalse(week.getShifts()[0][1].getcahsierslist().contains(worker3));
        assertTrue(week.getShifts()[0][1].getStorekeeperlist().contains(worker3));

        assertTrue(week.getShifts()[1][0].getcahsierslist().contains(worker2));
        assertFalse(week.getShifts()[1][1].getmanagerlist().contains(worker2));
        assertFalse(week.getShifts()[1][1].getStorekeeperlist().contains(worker2));

        assertTrue(week.getShifts()[3][0].getcahsierslist().contains(worker1));
        assertFalse(week.getShifts()[3][1].getmanagerlist().contains(worker1));
        assertFalse(week.getShifts()[3][1].getStorekeeperlist().contains(worker1));

        // remove
        week.getShifts()[0][1].removeWorkerFromOneRoleInShift(TypesOfJobs.STOREKEEPER,worker3);
        assertTrue(!week.getShifts()[0][1].getStorekeeperlist().contains(worker3));

        week.getShifts()[1][0].removeWorkerFromOneRoleInShift(TypesOfJobs.CASHIER,worker2);
        assertTrue(!week.getShifts()[1][0].getcahsierslist().contains(worker2));

        week.getShifts()[3][0].removeWorkerFromOneRoleInShift(TypesOfJobs.CASHIER,worker1);
    }

}
