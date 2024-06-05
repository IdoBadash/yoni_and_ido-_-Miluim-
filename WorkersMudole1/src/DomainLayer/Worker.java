package DomainLayer;

import DataAccessLayer.DataBase;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Worker {
    private int id;
    private String name;
    private String bankAccount;
    private double pay;
    private LocalDate dateOfStart;
    private List<TypesOfJobs> typesOfJobs;
    private List<Shift> availableShifts;
    private String workAgreement;

    public Worker(int id, String name, String bankAccount, double pay, String workAgreement, LocalDate dateOfStart, List<TypesOfJobs> jobs) {
        this.id = id;
        this.name = name;
        this.bankAccount = bankAccount;
        this.pay = pay;
        this.workAgreement = workAgreement;
        this.dateOfStart = dateOfStart;
        this.typesOfJobs = jobs;
        this.availableShifts = new ArrayList<>();
    }

//    public static Worker createRandomWorker() {
//        Random random = new Random();
//        String[] names = {"Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Henry", "Ivy", "Jack", "Yoni"};
//        String name = names[random.nextInt(names.length)];
//        int id = 1000 + random.nextInt(9000);
//        String bankAccount = "ACCT-" + (100000 + random.nextInt(900000));
//        double pay = 0;
//        String workAgreement = "Full-time";
//        LocalDate dateOfStart = LocalDate.of(2024, 9, 1);
//
//        List<TypesOfJobs> jobTypes = new ArrayList<>();
//        TypesOfJobs[] allJobs = TypesOfJobs.values();
//        while (jobTypes.size() < random.nextInt(3) + 1) {
//            TypesOfJobs jobType = allJobs[random.nextInt(allJobs.length)];
//            if (!jobTypes.contains(jobType)) {
//                jobTypes.add(jobType);
//            }
//        }
//
//        return new Worker(id, name, bankAccount, pay, workAgreement, dateOfStart, jobTypes);
//    }

    public static Worker createWorker() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Worker ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Worker Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Bank Account: ");
        String bankAccount = scanner.nextLine();

        System.out.print("Enter Pay: ");
        double pay = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Date of Start (yyyy-mm-dd): ");
        LocalDate dateOfStart = LocalDate.parse(scanner.nextLine());

        String workAgreement = "Full-time";

        List<TypesOfJobs> jobTypes = new ArrayList<>();
        System.out.println("Enter role: ");
        TypesOfJobs[] allJobs = TypesOfJobs.values();
        for (int i = 1; i <= allJobs.length; i++) {
            System.out.println(i + ". " + allJobs[i - 1]);
        }

        System.out.print("Select job type by number: ");
        int jobIndex = scanner.nextInt() - 1;
        jobTypes.add(allJobs[jobIndex]);

        return new Worker(id, name, bankAccount, pay, workAgreement, dateOfStart, jobTypes);
    }

    public static void editEmployeeDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Worker ID to edit details: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Worker worker = DataBase.findWorkerById(id);
        if (worker == null) {
            System.out.println("Worker not found.");
            return;
        }

        System.out.println("Editing details for: " + worker);
        System.out.println("1. Edit Name");
        System.out.println("2. Edit Bank Account");
        System.out.println("3. Edit Pay");
        System.out.println("4. Edit Work Agreement");
        System.out.println("5. Add Job Type");
        System.out.println("6. Remove Job Type");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter new name: ");
                worker.setName(scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new bank account: ");
                worker.setBankAccount(scanner.nextLine());
                break;
            case 3:
                System.out.print("Enter new pay: ");
                worker.setPay(scanner.nextDouble());
                break;
            case 4:
                System.out.print("Enter new work agreement: ");
                worker.setWorkAgreement(scanner.nextLine());
                break;
            case 5:
                System.out.println("Available job types to add:");
                TypesOfJobs[] allJobs = TypesOfJobs.values();
                for (int i = 0; i < allJobs.length; i++) {
                    System.out.println((i + 1) + ". " + allJobs[i]);
                }
                System.out.print("Select job type to add by number: ");
                int addJobIndex = scanner.nextInt() - 1;
                if (addJobIndex >= 0 && addJobIndex < allJobs.length) {
                    worker.addJobType(allJobs[addJobIndex]);
                } else {
                    System.out.println("Invalid job type selected.");
                }
                break;
            case 6:
                System.out.println("Current job types:");
                List<TypesOfJobs> currentJobs = worker.getTypesOfJobs();
                for (int i = 0; i < currentJobs.size(); i++) {
                    System.out.println((i + 1) + ". " + currentJobs.get(i));
                }
                System.out.print("Select job type to remove by number: ");
                int removeJobIndex = scanner.nextInt() - 1;
                if (removeJobIndex >= 0 && removeJobIndex < currentJobs.size()) {
                    worker.removeJobType(currentJobs.get(removeJobIndex));
                } else {
                    System.out.println("Invalid job type selected.");
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
        System.out.println("Worker details updated successfully: " + worker);
    }

    public void monthSalaryByWorker() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chose month and year (yyyy-mm)");
        while (true) {
            String str = "-01";
            LocalDate startOfMonth;
            LocalDate endOfMonth;
            try {
                startOfMonth = LocalDate.parse(scanner.nextLine() + str);
                endOfMonth = startOfMonth.plusMonths(1);
                monthSalary(startOfMonth,endOfMonth);
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid date (yyyy-mm)");
                continue;
            }

        }
    }
    public void monthSalary(LocalDate startOfMonth, LocalDate endOfMonth){
            if (DataBase.Weeks.size() == 0) {
                System.out.println("There is no data base yet");
                return;
            }
            if (DataBase.Weeks.get(0).getFromDate().isAfter(endOfMonth.minusDays(1))) {
                System.out.println("The Shop is open since " + DataBase.Weeks.get(0).getFromDate());
                return;
            }
            if (DataBase.Weeks.get(DataBase.Weeks.size() -1).getToDate().isBefore(startOfMonth)) {
                System.out.println("This month has not yet been entered in the system");
                return;
            }
            double monthSalary = 0;
            for (int i = 0; i < DataBase.Weeks.size(); i++) {
                for (int j = 0; j < 5; j++) {
                    if (DataBase.Weeks.get(i).getShifts()[j][0].getDate().isBefore(dateOfStart)) {
                        continue;
                    } else if (DataBase.Weeks.get(i).getShifts()[j][0].getDate().isAfter(dateOfStart.minusDays(1))
                            && DataBase.Weeks.get(i).getShifts()[j][0].getDate().isBefore(dateOfStart.plusMonths(1))) {
                        for (int k = 0; k < 2; k++) {
                            monthSalary += DataBase.Weeks.get(i).getShifts()[j][k].payForOneShift(this);
                        }
                    } else {
                        System.out.println(this.name + "salary for " + startOfMonth + " to " + endOfMonth.minusDays(1) + " is: " + monthSalary);
                        return;
                    }

                }
            }
        }

    public void addWorker() {
        DataBase.AddWorker(this);
        System.out.println("Worker registered successfully: " + this.getName());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public String getWorkAgreement() {
        return workAgreement;
    }

    public void setWorkAgreement(String workAgreement) {
        this.workAgreement = workAgreement;
    }

    public LocalDate getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(LocalDate dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public List<TypesOfJobs> getTypesOfJobs() {
        return new ArrayList<>(typesOfJobs);
    }

    public List<Shift> getAvailableShifts() {
        return new ArrayList<>(availableShifts);
    }

    public void setAvailableShifts(List<Shift> availableShifts) {
        this.availableShifts = new ArrayList<>(availableShifts);
    }

    public void addAvailableShift(Shift shift) {
        availableShifts.add(shift);
    }

    public void removeAvailableShift(Shift shift) {
        availableShifts.remove(shift);
    }

    public void askShiftsAvailability(WeeklySchedule schedule) {
        availableShifts.clear();
        Scanner scanner = new Scanner(System.in);
        Shift[][] shifts = schedule.getShifts();
        for (Shift[] dayShifts : shifts) {
            for (Shift shift : dayShifts) {
                System.out.println("Are you available for the shift on " + shift.getDay() + " (" + shift.getTiming() + ")? (yes/no): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                    this.addAvailableShift(shift);
                }
            }
        }
    }

    public void addJobType(TypesOfJobs jobType) {
        if (!typesOfJobs.contains(jobType)) {
            typesOfJobs.add(jobType);
        }
    }

    public void removeJobType(TypesOfJobs jobType) {
        typesOfJobs.remove(jobType);
    }

    public boolean isCashier() {
        return typesOfJobs.contains(TypesOfJobs.CASHIER);
    }

    public boolean isStorekeeper() {
        return typesOfJobs.contains(TypesOfJobs.STOREKEEPER);
    }

    public boolean isManager() {
        return typesOfJobs.contains(TypesOfJobs.MANAGER);
    }

    @Override
    public String toString() {
        return "" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", bankAccount='" + bankAccount + '\'' +
                ", pay=" + pay +
                ", workAgreement='" + workAgreement + '\'' +
                ", dateOfStart=" + dateOfStart +
                ", jobTypes=" + typesOfJobs +
                "";
    }
}
