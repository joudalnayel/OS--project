import java.util.*;

public class MultilevelQueueScheduler {
	
    public static void main(String[] args) {
    	
        Scanner kb = new Scanner(System.in);
        int choice,numProcesses,priority,arrivalTime,burstTime;
        String processID;
        List<PCB> Q1 = new ArrayList<>();
        List<PCB> Q2 = new ArrayList<>();
         
        do {
            System.out.println("Multilevel Queue Scheduling Algorithm");
            System.out.println("1. Enter process information.");
            System.out.println("2. Report detailed information about each process and different scheduling criteria.");
            System.out.println("3. Exit the program.");
            System.out.print("Enter your choice: ");
            choice = kb.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the total number of processes in the system: ");
                    numProcesses = kb.nextInt();

                    for (int i = 1; i <= numProcesses; i++) {
                        System.out.println("Enter information for Process " + i + ":");
                        System.out.print("Process ID: ");
                        processID = kb.next();

                        System.out.print("Priority (1 or 2): ");
                        priority = kb.nextInt();

                        System.out.print("Arrival time: ");
                        arrivalTime = kb.nextInt();

                        System.out.print("CPU burst time: ");
                        burstTime = kb.nextInt();

                        PCB process = new PCB(processID, priority, arrivalTime, burstTime);

                        if (priority == 1) {
                            Q1.add(process);
                        } else if (priority == 2) {
                            Q2.add(process);
                        } else {
                            System.out.println("Invalid priority. Process not added.");
                        }
                    }
                    System.out.println("Process information entered successfully.");
                    break;
                case 2:
                   
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (choice != 3);

    }

}
