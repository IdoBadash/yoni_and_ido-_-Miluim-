package PresentationLayer.CLI;

import DataAccessLayer.DataBase;
import DomainLayer.*;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import static DataAccessLayer.DataBase.calculateAllSalaryOfPreviousMonth;
import static DomainLayer.WeeklySchedule.*;
import static DomainLayer.Worker.editEmployeeDetails;

public class HrUI {

    public static void HrMenu() {
        System.out.println("Welcome to supermarket Lee!");
        int choice;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your choice:");
            System.out.println("1. Register new worker");
            System.out.println("2. Edit employee details");
            System.out.println("3. Show all workers");
            System.out.println("4. Create new week and fill requirements");
            System.out.println("5. Workers salary report");
            System.out.println("6. Show weeks arrangement");
            System.out.println("7. Submit temp week as final and publish to workers");
            System.out.println("8. Exit");

                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        Worker temp = Worker.createWorker();
                        temp.addWorker();
                        break;
                    case 2:
                        editEmployeeDetails();
                        break;
                    case 3:
                        DataBase.printAllWorkers();
                        break;
                    case 4:
                        createNewWeekAndRequirements();
                        break;
                    case 5:
                        calculateAllSalaryOfPreviousMonth();
                        break;
                    case 6:
                        System.out.println("1. Current week\n" +
                                "2. Old week");
                        int choice6 = scanner.nextInt();
                        switch (choice6){
                            case 1:
                                if(WeeklySchedule.TempWeek!=null) {
                                    TempWeek.showWeeklyArrangement();
                                }
                                else
                                    System.out.println("You didnt set requirements (Press 4 - make a new next week)");
                                break;

                            case 2:
                                Scanner strScanner = new Scanner(System.in);
                                System.out.println("Enter the first day of week yyyy-mm-dd");
                                LocalDate date = LocalDate.parse(strScanner.nextLine());
                                for (int i = 0; i < DataBase.Weeks.size()-1; i++) {
                                    if (DataBase.Weeks.get(i).getFromDate().equals(date)) {
                                        DataBase.Weeks.get(i).showWeeklyArrangement();
                                        break;
                                    }

                                }
                                break;
                        }
                        break;
                    case 7:
                       arrangWeek();
                       break;
                    case 8:
                        System.out.println("Exiting... Goodbye!");
                        return; // Exit the menu loop
                    default:
                        System.out.println("Invalid choice, please enter 1-8.");
                }
        }
    }
}
