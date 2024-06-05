package DataAccessLayer;

import DomainLayer.TypesOfJobs;
import DomainLayer.WeeklySchedule;
import DomainLayer.Worker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataBase {

    static LocalDate nowDate = LocalDate.now(ZoneId.of("Asia/Jerusalem"));
    static LocalDate startDate = nowDate;

    static public Map<Integer, Worker> workers;
    static public List<TypesOfJobs> jobsList;

    static public ArrayList<WeeklySchedule> Weeks;



    public static void AddWorker(Worker w1) {
        workers.put(w1.getId(), w1);
    }
    public static Map<Integer, Worker> GetAllWorkers() {
        return workers;
    }

    public static Worker findWorkerById(int workerId) {
        return workers.get(workerId);
    }

    public static void calculateAllSalaryOfPreviousMonth(){
        LocalDate today = LocalDate.now();
        LocalDate previousMonth = today.minusMonths(1);
        LocalDate tempMonth = LocalDate.of(previousMonth.getYear(), previousMonth.getMonthValue(),1);
        for (Worker w: DataBase.workers.values()) {
            w.monthSalary(tempMonth,tempMonth.plusMonths(1).minusDays(1));
        }
    }
    public static void printAllWorkers() {
        System.out.println("All workers in the database:");
        for (Map.Entry<Integer, Worker> entry : workers.entrySet()) {
            System.out.println("Worker ID: " + entry.getKey() + ", Details: " + entry.getValue());
        }
    }
}
