package PresentationLayer.CLI;


import java.util.Scanner;

public class OpenPageUI {

    public static void initial_system() {

        Scanner scanner = new Scanner(System.in);
        boolean continueMenu = true;
        System.out.print("Enter your choice: ");
        while (continueMenu) {
            System.out.println("Menu:");
            System.out.println("1. HR");
            System.out.println("2. Worker");
            System.out.println("00. Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    HrUI.HrMenu();
                    break;
                case "2":
                    WorkerUI.WorkerMenu();
                    break;
                case "00":
                    continueMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Bye Bye...");
    }
}
