package DomainLayer;

import DataAccessLayer.DataBase;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

public class WeeklySchedule {
    private int id;
    private Shift[][] shifts;
    private LocalDate fromDate;
    private LocalDate toDate;

    static public WeeklySchedule TempWeek = null;

    // Constructor
    public WeeklySchedule(int id, LocalDate fromDate, LocalDate toDate) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.shifts = fillShiftsManually(fromDate); // Use the manual method
    }

    public WeeklySchedule(WeeklySchedule week) {
        this.id = week.id;
        this.fromDate = week.fromDate;
        this.toDate = week.toDate;
        this.shifts = week.shifts;
    }
    // dif constractor just for Test
    public WeeklySchedule(){

    }

    // Method to fill in the shift information for the week manually
    private Shift[][] fillShiftsManually(LocalDate fromDate) {
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"};
        String[] timings = {"Morning", "Night"};
        Shift[][] tempShifts = new Shift[5][2];
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.println("Enter details for " + daysOfWeek[i] + " (" + timings[j] + " shift):");

                System.out.print("Number of cashiers: ");
                int cashiers = scanner.nextInt();

                System.out.print("Number of storekeepers: ");
                int storekeepers = scanner.nextInt();

                System.out.print("Number of managers: ");
                int managers = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                tempShifts[i][j] = new Shift(daysOfWeek[i], timings[j], cashiers, storekeepers, managers,fromDate.plusDays(i));
            }
        }
        return tempShifts;
    }

    // Method to print the schedule for the week
    public void PrintWeekQuantities() {
        System.out.println(this.fromDate +  " - " + this.toDate);
        String[] shiftTimes = {"Morning", "Night"};

        System.out.println("Shift     |                Sunday                        |                Monday                        |                Tuesday                       |                Wednesday                     |               Thursday                      |");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (int s = 0; s < 2; s++) {
            System.out.printf("%-10s| ", shiftTimes[s]);
            for (int day = 0; day < 5; day++) {
                Shift shift = shifts[day][s];
                System.out.printf("Cashiers: %-2d, Storekeepers: %-2d, Managers: %-2d | ",
                        shift.getCashiers(),
                        shift.getStorekeeper(),
                        shift.getManagers());
            }
            System.out.println();
        }
        System.out.println();
    }

    // Method to modify shift quantities
    public void modifyShiftQuantities() {
        Scanner scanner = new Scanner(System.in);
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"};
        String[] shiftTimes = {"Morning", "Night"};

        System.out.println("Do you want to modify shift quantities? (yes/no)");
        String response = scanner.nextLine().trim().toLowerCase();

        if (!response.equals("yes")) {
            return;
        }

        System.out.println("Enter the day you want to modify (Sunday to Thursday): ");
        String day = scanner.nextLine();

        System.out.println("Enter the shift time you want to modify (Morning/Night): ");
        String shiftTime = scanner.nextLine();

        int dayIndex = -1;
        int shiftIndex = -1;

        for (int i = 0; i < days.length; i++) {
            if (days[i].equalsIgnoreCase(day)) {
                dayIndex = i;
                break;
            }
        }

        for (int j = 0; j < shiftTimes.length; j++) {
            if (shiftTimes[j].equalsIgnoreCase(shiftTime)) {
                shiftIndex = j;
                break;
            }
        }

        if (dayIndex == -1 || shiftIndex == -1) {
            System.out.println("Invalid day or shift time. Please try again.");
            return;
        }

        System.out.println("Enter the new number of cashiers: ");
        int cashiers = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the new number of storekeepers: ");
        int storekeepers = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the new number of managers: ");
        int managers = Integer.parseInt(scanner.nextLine());

        shifts[dayIndex][shiftIndex].setCashiers(cashiers);
        shifts[dayIndex][shiftIndex].setStorekeeper(storekeepers);
        shifts[dayIndex][shiftIndex].setManagers(managers);

        System.out.println("Shift quantities updated successfully, check it out:");
        PrintWeekQuantities();
    }

    // Method to add a worker to their preferred shifts
    public void addWorkerForWeek(Worker worker) {
        for (Shift preferredShift : worker.getAvailableShifts()) {
            for (int i = 0; i < shifts.length; i++) {
                for (int j = 0; j < shifts[i].length; j++) {
                    Shift currentShift = shifts[i][j];
                    if (currentShift.getDay().equals(preferredShift.getDay()) &&
                            currentShift.getTiming().equals(preferredShift.getTiming())) {
                        currentShift.addWorker(worker);
                    }
                }
            }
        }
    }
    // Method to check worker quantities for all shifts in the weekly schedule
    public void checkWorkerQuantitiesForAllShifts() {
        for (int i = 0; i < shifts.length; i++) {
            for (int j = 0; j < shifts[i].length; j++) {
                Shift currentShift = shifts[i][j];
                currentShift.checkWorkerQuantities();
            }
        }
    }

    // Method to remove excess workers from each shift in the weekly schedule
    public void removeExcessWorkersFromAllShifts() {
        for (int i = 0; i < shifts.length; i++) {
            for (int j = 0; j < shifts[i].length; j++) {
                shifts[i][j].removeExcessWorkers();
            }
        }
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFromDate(){return this.fromDate;}

    public LocalDate getToDate(){return this.toDate;}

    public Shift[][] getShifts() {
        return shifts;
    }

    public void setToDate(LocalDate date){
        this.toDate = date;
    }
    public void setFromDate(LocalDate date){
        this.fromDate = date;
    }
    public void setShifts(Shift[][] shifts) {
        this.shifts = shifts;
    }

    public static WeeklySchedule GetWeekTemplate() {
        return TempWeek;
    }

    public static void SetWeekTemp(WeeklySchedule w) {
        TempWeek = w;
    }



    public static void createNewWeekAndRequirements() {
        if (TempWeek == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the ID for the new weekly schedule: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter the start date (YYYY-MM-DD): ");
            LocalDate fromDate = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter the end date (YYYY-MM-DD): ");
            LocalDate toDate = LocalDate.parse(scanner.nextLine());

            WeeklySchedule newSchedule = new WeeklySchedule(id, fromDate, toDate);
            WeeklySchedule.TempWeek = newSchedule;

            System.out.println("New weekly schedule created successfully.");
            newSchedule.PrintWeekQuantities();
        }
    }

    public void showWeeklyArrangement() {
        WeeklySchedule tempWeek = this;
        if (tempWeek != null) {
            Shift[][] shifts = tempWeek.getShifts();
            for (int i = 0; i < shifts.length; i++) {
                for (int j = 0; j < shifts[i].length; j++) {
                    shifts[i][j].showShiftArrangement();
                }
            }
        } else {
            System.out.println("No temporary week arrangement available.");
        }
    }



    public static void arrangWeek(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Chose number:\n" +
                    "1. remove more duplicated worker\n" +
                    "2. randomly remove access\n" +
                    "3. publish arrangement\n" +
                    "4. exit");
            int chose = scanner.nextInt();
            switch (chose){
                case 1: {
                    System.out.println("Please chose day by number:\n" +
                            "1. Sunday\n" +
                            "2. Monday\n" +
                            "3. Tuesday\n" +
                            "4. Wednesday\n" +
                            "5. Thursday\n");

                    int day = scanner.nextInt() - 1;
                    System.out.println("Chose time by number:\n" +
                            "1. morning\n" +
                            "2. night");
                    int time = scanner.nextInt() - 1;
                    if (TempWeek != null) {
                        TempWeek.getShifts()[day][time].showShiftArrangement();
                    }
                    System.out.println("Chose Id to remove:");
                    int id = scanner.nextInt();
                    System.out.println("In which role?: ");
                    TypesOfJobs[] allJobs = TypesOfJobs.values();
                    for (int i = 1; i <= allJobs.length; i++) {
                        System.out.println(i + ". " + allJobs[i - 1]);
                    }
                    int role = scanner.nextInt() - 1;
                    TempWeek.getShifts()[day][time].removeWorkerFromOneRoleInShift(allJobs[role], DataBase.findWorkerById(id));
                }
                case 2:{
                    TempWeek.removeExcessWorkersFromAllShifts();
                    break;
                }
                case 3:{
                    if(TempWeek!=null) {
                        WeeklySchedule finalWeek = new WeeklySchedule(TempWeek);
                        DataBase.Weeks.add(finalWeek);
                        TempWeek = null;
                        return;
                    }
                }
                case 4:
                    return;
            }
        }
    }

    public static boolean isBeforeWednesday() {
        LocalDate today = LocalDate.now();
        return (today.getDayOfWeek().getValue() % 7) + 1 < (DayOfWeek.WEDNESDAY.getValue() % 7) + 1;
    }
}
