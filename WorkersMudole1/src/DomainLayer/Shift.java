package DomainLayer;

import java.time.LocalDate;
import java.util.*;



public class Shift {
    private String dayOfWeek;
    private String morningOrNight;
    private LocalDate date;
    private int numofcashiers;
    private int numofstorekeeper;
    private int numofmanagers;
    private List<Worker> cashierslist;
    private List<Worker> storekeeperslist;
    private List<Worker> managerslist;
    private Set<Worker> shiftWorker;

    // Constructor
    public Shift(String day, String timing, int numofcashiers, int numofstorekeeper, int numofmanagers, LocalDate date) {
        this.dayOfWeek = day;
        this.morningOrNight = timing;
        this.numofcashiers = numofcashiers;
        this.numofstorekeeper = numofstorekeeper;
        this.numofmanagers = numofmanagers;
        this.cashierslist = new ArrayList<Worker>();
        this.storekeeperslist = new ArrayList<Worker>();
        this.managerslist = new ArrayList<Worker>();
        this.date = date;
        this.shiftWorker = new HashSet<Worker>();
    }


    public void addWorker(Worker w) {
            if (w.isCashier() && !cashierslist.contains(w)) {
                cashierslist.add(w);
            }
            if (w.isStorekeeper() && !storekeeperslist.contains(w)) {
                storekeeperslist.add(w);
            }
            if (w.isManager() && !managerslist.contains(w)) {
                managerslist.add(w);
            }
            shiftWorker.add(w);
        }


    // Method to check if the number of workers for each job role matches the specified quantity
    public void checkWorkerQuantities() {
        int currentCashiers = cashierslist.size();
        int currentStorekeepers = storekeeperslist.size();
        int currentManagers = managerslist.size();

        // Check if any adjustments are needed
        boolean adjustmentsNeeded = false;

        // Check for excess or insufficient workers for each job role
        if (currentCashiers != this.numofcashiers) {
            adjustmentsNeeded = true;
        }

        if (currentStorekeepers != this.numofstorekeeper) {
            adjustmentsNeeded = true;
        }

        if (currentManagers != this.numofmanagers) {
            adjustmentsNeeded = true;
        }

        // Print worker quantity issues if adjustments are needed
        if (adjustmentsNeeded) {
            // Print day and timing of the shift
            System.out.println("The shift of " + this.getDay() + " " + this.getTiming() + ":");

            // Print excess or insufficient workers for each job role
            printWorkerQuantities();

            System.out.println();
        }
    }

    public void removeWorkerFromOneRoleInShift(TypesOfJobs role,Worker w){
        if (!workerInMultipleLists(w))
            shiftWorker.remove(w);
        if (role == TypesOfJobs.CASHIER){
            this.cashierslist.remove(w);
            System.out.println(w.getName() + "");
        }
        if (role == TypesOfJobs.MANAGER){
            this.managerslist.remove(w);
        }
        if (role == TypesOfJobs.STOREKEEPER){
            this.storekeeperslist.remove(w);
        }
    }

    // Method to check if a worker is present in multiple job lists
    private boolean workerInMultipleLists(Worker worker) {
        int count = 0;

        // Check if the worker is present in cashierslist
        if (cashierslist.contains(worker)) {
            count++;
        }

        // Check if the worker is present in storekeeperslist
        if (storekeeperslist.contains(worker)) {
            count++;
        }

        // Check if the worker is present in managerslist
        if (managerslist.contains(worker)) {
            count++;
        }

        // Return true if the worker is present in more than one job list, false otherwise
        return count > 1;
    }


    // Method to print excess or insufficient workers for each job role
    private void printWorkerQuantities() {
        int currentCashiers = cashierslist.size();
        int currentStorekeepers = storekeeperslist.size();
        int currentManagers = managerslist.size();

        if (currentCashiers < this.numofcashiers) {
            System.out.println("More cashiers needed. Need to add " + (this.numofcashiers - currentCashiers) + " more.");
        } else if (currentCashiers > this.numofcashiers) {
            System.out.println("Excess cashiers. Need to remove " + (currentCashiers - this.numofcashiers) + " more.");
        }

        if (currentStorekeepers < this.numofstorekeeper) {
            System.out.println("More storekeepers needed. Need to add " + (this.numofstorekeeper - currentStorekeepers) + " more.");
        } else if (currentStorekeepers > this.numofstorekeeper) {
            System.out.println("Excess storekeepers. Need to remove " + (currentStorekeepers - this.numofstorekeeper) + " more.");
        }

        if (currentManagers < this.numofmanagers) {
            System.out.println("More managers needed. Need to add " + (this.numofmanagers - currentManagers) + " more.");
        } else if (currentManagers > this.numofmanagers) {
            System.out.println("Excess managers. Need to remove " + (currentManagers - this.numofmanagers) + " more.");
        }

        // Check for workers in multiple job lists after printing worker quantities
    }



    public void removeExcessWorkers() {
        // Randomly shuffle the lists to ensure randomness in selection for removal
        Collections.shuffle(cashierslist);
        Collections.shuffle(storekeeperslist);
        Collections.shuffle(managerslist);

        // Remove excess workers from each job role list if there are more than needed
        if (cashierslist.size() > numofcashiers) {
            cashierslist.subList(numofcashiers, cashierslist.size()).clear();
        }
        if (storekeeperslist.size() > numofstorekeeper) {
            storekeeperslist.subList(numofstorekeeper, storekeeperslist.size()).clear();
        }
        if (managerslist.size() > numofmanagers) {
            managerslist.subList(numofmanagers, managerslist.size()).clear();
        }
    }

    public void showShiftArrangement() {
        System.out.println(date);
        System.out.println("Day: " + getDay() + ", Time: " + getTiming());

        System.out.println("Cashiers Needed: " + getCashiers() + " Cashiers Assigned: " + getcahsierslist().size());
        for (Worker worker : getcahsierslist()) {
            System.out.print(worker.getName() + " " + worker.getId()  + "\n");
        }

        System.out.println("Storekeepers Needed: " + getStorekeeper() + " Storekeepers Assigned: " + getStorekeeperlist().size());
        for (Worker worker : getStorekeeperlist()) {
            System.out.print(worker.getName() + " " + worker.getId()  + "\n");
        }

        System.out.println("Managers Needed: " + getManagers() + " Managers Assigned: " + getmanagerlist().size());
        for (Worker worker : getmanagerlist()) {
            System.out.print(worker.getName() + " " + worker.getId() + "\n");
        }
        System.out.println();
    }



    public LocalDate getDate(){return this.date;}
    public String getDay() {
        return dayOfWeek;
    }

    public void setDay(String day) {
        this.dayOfWeek = day;
    }

    public String getTiming() {
        return morningOrNight;
    }

    public void setTiming(String timing) {
        this.morningOrNight = timing;
    }

    public int getCashiers() {
        return numofcashiers;
    }

    public void setCashiers(int cashiers) {
        this.numofcashiers = cashiers;
    }

    public int getStorekeeper() {
        return numofstorekeeper;
    }

    public void setStorekeeper(int storekeeper) {
        this.numofstorekeeper = storekeeper;
    }

    public int getManagers() {
        return numofmanagers;
    }

    public void setManagers(int managers) {
        this.numofmanagers = managers;
    }

    public ArrayList<Worker> getStorekeeperlist() {
        return (ArrayList<Worker>) storekeeperslist;
    }

    public ArrayList<Worker> getmanagerlist() {
        return (ArrayList<Worker>) managerslist;
    }

    public ArrayList<Worker> getcahsierslist() {
        return (ArrayList<Worker>) cashierslist;
    }

    // Method to check if a worker is in the shift
    public boolean isWorkerInShift(Worker worker) {
        return cashierslist.contains(worker) || storekeeperslist.contains(worker) || managerslist.contains(worker);
    }

    public double payForOneShift(Worker w){
        int hours = 8;
        boolean was = false;
        for (int i = 0; i < this.managerslist.size(); i++) {
            if (w.getId() == this.managerslist.get(i).getId()) {
                was = true;
                break;
            }
        }
        for (int i = 0; i < this.cashierslist.size(); i++) {
            if (w.getId() == this.cashierslist.get(i).getId()) {
                was = true;
                break;
            }
        }
        for (int i = 0; i < this.storekeeperslist.size(); i++) {
            if (w.getId() == this.storekeeperslist.get(i).getId()) {
                was = true;
                break;
            }
        }
        if (was)
            return hours * w.getPay();
        return 0;
    }

    public void duplicateAlart(){
        System.out.println("Worker with more then one role:");
        int counter = 0;
        for (Worker w:this.shiftWorker) {
            if (workerInMultipleLists(w)){
                System.out.println(w.getName() + " " + w.getId());
                counter++;
            }
        }
        if (counter == 0)
            System.out.println("No duplicate in this shift");

    }

    // Method to display shift details
    @Override
    public String toString() {
        return this.getDay() +" "+ this.getTiming();
    }
}