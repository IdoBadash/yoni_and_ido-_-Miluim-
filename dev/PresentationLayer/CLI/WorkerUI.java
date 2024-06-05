package PresentationLayer.CLI;

import DataAccessLayer.DataBase;
import DomainLayer.WeeklySchedule;
import DomainLayer.Worker;

import java.util.Scanner;

import static DataAccessLayer.DataBase.*;
import static DomainLayer.WeeklySchedule.TempWeek;
import static DomainLayer.WeeklySchedule.isBeforeWednesday;


public class WorkerUI {

    public static void WorkerMenu() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Worker ID: ");
            int workerId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Worker w1 = findWorkerById(workerId);
            if (w1 == null) {
                System.out.println("Worker with ID " + workerId + " not found.");
                return;
            }

            boolean exit = false;
            while (!exit) {
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. Submit available shifts for the next week");
                System.out.println("2. My Salaries");
                System.out.println("3. Show me the most updated schedule");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        if (isBeforeWednesday()) {
                            if (WeeklySchedule.TempWeek == null) //or if today is after wendsday!
                                System.out.print("Wait for HR to set A week");
                            else {
                                w1.askShiftsAvailability(WeeklySchedule.TempWeek);
                                System.out.println(w1.getAvailableShifts());
                                WeeklySchedule.TempWeek.addWorkerForWeek(w1);
                            }
                        } else {
                            System.out.println("Hi " + w1.getName() + " you can't submit shifts for next week anymore!");
                        }
                        break;
                    case 2:
                        w1.monthSalaryByWorker();
                        break;
                    case 3:
                        if (Weeks.size() > 0){
                            Weeks.get(Weeks.size() -1 ).showWeeklyArrangement();
                            break;
                        }
                        System.out.println("There is no data base yet");
                        break;
                    case 4:
                        exit = true;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

