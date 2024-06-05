
import DomainLayer.TypesOfJobs;
import DomainLayer.WeeklySchedule;
import DomainLayer.Worker;
import DataAccessLayer.DataBase;
import PresentationLayer.CLI.HrUI;
import PresentationLayer.CLI.OpenPageUI;
import PresentationLayer.CLI.WorkerUI;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        // Initial DataBase for Task 2
        DataBase.workers = new HashMap<>();
        DataBase.Weeks = new ArrayList<>();
        //Random making for now
//        for (int i = 0; i < 2; i++) {
//            Worker Temp = Worker.createRandomWorker();
//            DataBase.workers.put(Temp.getId(), Temp);
//        }

        Worker worker1 = new Worker(
                2065,
                "Yehonatan",
                "ACCT-123456",
                30,
                "Full-time",
                LocalDate.of(2024, 9, 2),
                Arrays.asList(TypesOfJobs.CASHIER, TypesOfJobs.STOREKEEPER)
        );

        // Create the second Worker with specific details
        Worker worker2 = new Worker(
                2069,
                "Ido Badash",
                "ACCT-654321",
                55000,
                "Full-time",
                LocalDate.of(2024, 9, 1),
                Arrays.asList(TypesOfJobs.MANAGER)
        );

        Worker worker3 = new Worker(
                2060,
                "Yaki naftali",
                "ACCT-654321",
                17,
                "Full-time",
                LocalDate.of(2024, 9, 3),
                Arrays.asList(TypesOfJobs.MANAGER)
        );



        DataBase.workers.put(worker1.getId(), worker1);
        DataBase.workers.put(worker2.getId(), worker2);
        DataBase.workers.put(worker3.getId(), worker3);

        // Print the created workers
        DataBase.printAllWorkers();
        System.out.println();

        OpenPageUI.initial_system();

    }
}

