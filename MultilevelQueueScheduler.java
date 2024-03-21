import java.util.*;

public class MultilevelQueueScheduler {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int priority,arrivalTime,cpuBurstTime,ProcessID,startTime,terminationTime,turnAroundTime,waitingTime,responseTime;

        // The program will create two arrays of type PCB to represent Q1 and Q2:
        List<ProcessControlBlock> q1 = new ArrayList<>();
        List<ProcessControlBlock> q2 = new ArrayList<>();

        // The program will prompt the user to enter the number of processes (P):
        System.out.print("Enter the number of processes: ");
        int numProcesses = kb.nextInt();

        // The program will prompt the user to enter the priority, arrival time, and CPU burst of each process:
        
        for (int i = 1; i <= numProcesses; i++) {
            System.out.println("Enter process "+i+" information:");
            System.out.print("Priority (1 or 2): ");
            priority = kb.nextInt();
            System.out.print("Arrival time: ");
            arrivalTime = kb.nextInt();
            System.out.print("CPU burst time: ");
            cpuBurstTime = kb.nextInt();
        }
        
        // The program will create a process (object) of type PCB for each process and initialize its attributes: 
        for (int i = 1; i <= numProcesses; i++) {
            System.out.println("Enter process " + i + " information:");
            System.out.print("Please enter the Process ID: ");
            ProcessID = kb.nextInt();
            System.out.print("Please enter the Priority (1 or 2): ");
            priority = kb.nextInt();
            System.out.print("Please enter the Arrival time: ");
            arrivalTime = kb.nextInt();
            System.out.print("Please enter the CPU burst time: ");
            cpuBurstTime = kb.nextInt();
            System.out.print("Please enter the start time: ");
            startTime = kb.nextInt();
            System.out.print("Please enter the termination time: ");
            terminationTime = kb.nextInt();
            System.out.print("Please enter the Turn around time: ");
            turnAroundTime = kb.nextInt();
            System.out.print("Please enter the Waiting time: ");
            waitingTime = kb.nextInt();
            System.out.print("Please enter the Response time: ");
            responseTime = kb.nextInt();

            ProcessControlBlock pcb = new ProcessControlBlock(ProcessID, priority, arrivalTime, cpuBurstTime, startTime, terminationTime, turnAroundTime, waitingTime, responseTime);

            if (priority == 1) {
                q1.add(pcb);
            } else if (priority == 2) {
                q2.add(pcb);
            } else {
                System.out.println("Invalid priority. Process not added.");
            }
        }
    }
}