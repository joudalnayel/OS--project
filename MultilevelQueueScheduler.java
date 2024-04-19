import java.util.*;
import java.io.*;


public class MultilevelQueueScheduler {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		int choice, numProcesses, priority, arrivalTime, burstTime;

		String processID;
      //create two arrays of type PCB to represent Q1 and Q2.
		List<PCB> Q1 = new ArrayList<>();   
      List<PCB> Q2 = new ArrayList<>();
      
   System.out.println("Welcome to the Multilevel Queue Scheduling Algorithm:");

do {
    System.out.println();
    System.out.println("1. Enter processes information.");
    System.out.println("2. Report detailed information about each process and different scheduling criteria.");
    System.out.println("3. Exit the program.");
    System.out.println();
    System.out.print("Enter your choice: ");

    choice = input.nextInt();

    switch (choice) {
        case 1:
            System.out.print("Enter the total number of processes in the system: ");
            numProcesses = input.nextInt();

            for (int i = 1; i <= numProcesses; i++) {
                System.out.println("Enter information for Process " + i + ":");
                System.out.print("Process ID: ");
                processID = input.next();

                System.out.print("Priority (1 or 2): ");
                priority = input.nextInt();

                if (priority != 1 && priority != 2) {
                    System.out.println("Invalid priority. Process not added.");
                    continue;
                }


                System.out.print("Arrival time: ");
                arrivalTime = input.nextInt();

                System.out.print("CPU burst time: ");
                burstTime = input.nextInt();

                PCB process = new PCB("P" + processID, priority, arrivalTime, burstTime);

                if (priority == 1) {
                    Q1.add(process);
                } else if (priority == 2) {
                    Q2.add(process);
                } else {
                    System.out.println("Invalid priority. Process not added.");
                }
            }

            System.out.println("Process information is entered successfully.");
            break;
            
            case 2:
         //schedule processes execution in the CPU and output on the console a report of each process in system 

			    if (!Q1.isEmpty() || !Q2.isEmpty()) {
			        scheduleProcesses(Q1, Q2);

                  System.out.println("Scheduling order of processes: [");//NEW

			        for (int i = 0; i < Q1.size(); i++) {
			            PCB process = Q1.get(i);
			            System.out.println( process.getProcessID() + "|");//NEW
			        }

			     /*   if (!Q2.isEmpty()) {
			            if (!Q1.isEmpty()) {
			                schedulingOrder += " | ";
			            }*/ //NEW
                     
			            for (int i = 0; i < Q2.size(); i++) {
			                PCB process = Q2.get(i);
			                 System.out.println( process.getProcessID() + "|") ;
			            }//NEW
			        

			          System.out.println( " ]") ;


			        System.out.println("Detailed Process Information for Q1:");
			        System.out.println("-------------------------------------");
			        printDetailedInfo(Q1);
			        System.out.println("Detailed Process Information for Q2:");
			        System.out.println("-------------------------------------");
			        printDetailedInfo(Q2);

			        System.out.println("Average times for all processes in the system:");
			        System.out.println("-------------------------------------");

			        displayAverage(Q1, Q2);

    
                   writeToFile(Q1, "Report.txt");
                   appendToFile(Q2, "Report.txt");   
                   
			    } else {
			        System.out.println("Sorry, there are no processes in the system!.");
			        System.out.println();
			    }

			    break;
               case 3:
                    System.out.println("Exiting the program.");
                    break;
			}// DO
                }   while (choice != 3);//WHILE
        
                }//MAIN 
	

	public static void displayAverage(List<PCB> Q1, List<PCB> Q2) {

		int totalTurnaroundTime = 0;
		int totalWaitingTime = 0;
		int totalResponseTime = 0;
		int numProcesses = Q1.size() + Q2.size();

		totalTurnaroundTime = calculateTurnaroundTime(Q1, totalTurnaroundTime);
		totalWaitingTime = calculateWaitingTime(Q1, totalWaitingTime);
		totalResponseTime = calculateResponseTime(Q1, totalResponseTime);
		totalTurnaroundTime = calculateTurnaroundTime(Q2, totalTurnaroundTime);
		totalWaitingTime = calculateWaitingTime(Q2, totalWaitingTime);
		totalResponseTime = calculateResponseTime(Q2, totalResponseTime);

		double avgTurnaroundTime = (double) totalTurnaroundTime / numProcesses;
		double avgWaitingTime = (double) totalWaitingTime / numProcesses;
		double avgResponseTime = (double) totalResponseTime / numProcesses;

		System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
		System.out.println("Average Waiting Time: " + avgWaitingTime);
		System.out.println("Average Response Time: " + avgResponseTime);

	}

	private static int calculateTurnaroundTime(List<PCB> queue, int totalTurnaroundTime) {

		for (int i = 0; i < queue.size(); i++) {
			totalTurnaroundTime += queue.get(i).getTurnaroundTime();
		}
		return totalTurnaroundTime;

	}

	private static int calculateWaitingTime(List<PCB> queue, int totalWaitingTime) {

		for (int i = 0; i < queue.size(); i++) {
			totalWaitingTime += queue.get(i).getWaitingTime();
		}
		return totalWaitingTime;

	}

	private static int calculateResponseTime(List<PCB> queue, int totalResponseTime) {

		for (int i = 0; i < queue.size(); i++) {
			totalResponseTime += queue.get(i).getResponseTime();
		}
		return totalResponseTime;

	}
// FROM HERE WE DONT KNOW 
	public static void scheduleProcesses(List<PCB> Q1, List<PCB> Q2) {
	    int currentTime = 0;
	    currentTime = processQueueRR(Q1, currentTime, 3);
	    currentTime = processQueueSJF(Q2, currentTime);
	}

	private static int processQueueRR(List<PCB> queue, int currentTime, int quantum) {
	    int i = 0;
	    while (i < queue.size()) {
	        PCB process = queue.get(i);
	        process.setStartTime(currentTime);

	        int remainingTime = process.getBurstTime();

	        if (remainingTime <= quantum) {
	            currentTime += remainingTime;
	            process.setTerminationTime(currentTime);
	            queue.remove(i);
	        } else {
	            currentTime += quantum;
	            remainingTime -= quantum;
	            i++;
	        }

	        process.setTurnaroundTime(process.getTerminationTime() - process.getArrivalTime());
	        process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
	        process.setResponseTime(process.getStartTime() - process.getArrivalTime());
	    }

	    return currentTime;
	}

	private static int processQueueSJF(List<PCB> queue, int currentTime) {
	    queue.sort(Comparator.comparingInt(PCB::getBurstTime));

	    for (int i = 0; i < queue.size(); i++) {  // Changed the condition to <
	        PCB process = queue.get(i);
	        process.setStartTime(currentTime);

	        currentTime += process.getBurstTime();
	        process.setTerminationTime(currentTime);

	        process.setTurnaroundTime(process.getTerminationTime() - process.getArrivalTime());
	        process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
	        process.setResponseTime(process.getStartTime() - process.getArrivalTime());
	    }

	    return currentTime;
	}
// TO HERE

	public static void printDetailedInfo(List<PCB> queue) {

		for (int i = 0; i < queue.size(); i++) {
			PCB process = queue.get(i);
			System.out.println("Process ID: " + process.getProcessID());
			System.out.println("Priority: " + process.getPriority());
			System.out.println("Arrival time: " + process.getArrivalTime());
			System.out.println("CPU burst: " + process.getBurstTime());
			System.out.println("Start time: " + process.getStartTime());
			System.out.println("Termination time: " + process.getTerminationTime());
			System.out.println("Turnaround time: " + process.getTurnaroundTime());
			System.out.println("Waiting time: " + process.getWaitingTime());
			System.out.println("Response time: " + process.getResponseTime());
			System.out.println();
			System.out.println();
		}

	}

	public static void writeToFile( List<PCB> queue, String report) {// NEW 
        try (PrintWriter writer = new PrintWriter(new FileWriter(report, true))) {
        	
        	for (int i = 0; i < queue.size(); i++) {
               // writer.println(schedulingOrder);// NEW
                
                PCB process = queue.get(i);
                writer.println("Process ID: " + process.getProcessID());
                writer.println("Priority: " + process.getPriority());
                writer.println("Arrival time: " + process.getArrivalTime());
                writer.println("CPU burst: " + process.getBurstTime());
                writer.println("Start time: " + process.getStartTime());
                writer.println("Termination time: " + process.getTerminationTime());
                writer.println("Turnaround time: " + process.getTurnaroundTime());
                writer.println("Waiting time: " + process.getWaitingTime());
                writer.println("Response time: " + process.getResponseTime());
                writer.println();
                writer.println();
            }
            System.out.println("Detailed process information for queue has been written to " + report);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

	public static void appendToFile(List<PCB> queue, String Report) {
		
		try (PrintWriter writer = new PrintWriter(new FileWriter(Report, true))) {
			for (int i = 0; i < queue.size(); i++) {
				PCB process = queue.get(i);
				
				writer.println("Process ID: " + process.getProcessID());
				writer.println("Priority: " + process.getPriority());
				writer.println("Arrival time: " + process.getArrivalTime());
				writer.println("CPU burst: " + process.getBurstTime());
				writer.println("Start time: " + process.getStartTime());
				writer.println("Termination time: " + process.getTerminationTime());
				writer.println("Turnaround time: " + process.getTurnaroundTime());
				writer.println("Waiting time: " + process.getWaitingTime());
				writer.println("Response time: " + process.getResponseTime());
				writer.println();
			}
			System.out.println("Detailed process information for queue has been appended to " + Report);
		} catch (IOException e) {
			System.out.println("An error occurred while appending to the file: " + e.getMessage());
		}
	}
	


	public class Scheduler {
	    private int nextPID1 = 0;
	    private int nextPID2 = 0;
	    private int clock = 0;
	    private int quantum = 3; // for Round Robin
	    private List<PCB> q1 = new ArrayList<>(); // for high priority
	    private List<PCB> q2 = new ArrayList<>(); // for low priority
	    private String SchChart = "";

	    public int getClock() {
	        return clock;
	    }

	    public void seq() {
	        List<PCB> rQ1 = new ArrayList<>();
	        List<PCB> rQ2 = new ArrayList<>();
	        PCB nextArrivalPCB = nextPCB();
	        PCB runningPCB = null;
	        clock = 0;
	        int cpuCount = 0;

	        while (nextArrivalPCB != null || !rQ1.isEmpty() || !rQ2.isEmpty() || runningPCB != null) {
	            while (nextArrivalPCB != null && clock == nextArrivalPCB.getArrivalTime()) {
	                if (nextArrivalPCB.getPriority() == 1) { // adding the high priority first
	                    rQ1.add(nextArrivalPCB);
	                } else {
	                    rQ2.add(nextArrivalPCB);
	                    sortByBurstTime(rQ2);
	                }
	                nextArrivalPCB = nextPCB();
	            }

	            if (runningPCB == null) {
	                if (!rQ1.isEmpty()) { // starting from Q1
	                    runningPCB = execute(rQ1.remove(0));
	                } else if (!rQ2.isEmpty()) {
	                    runningPCB = execute(rQ2.remove(0));
	                }
	            }

	            if (runningPCB != null && runningPCB.getPriority() == 1 && cpuCount == quantum && !rQ1.isEmpty()) {
	                rQ1.add(runningPCB); // for RR
	                runningPCB = execute(rQ1.remove(0));
	                cpuCount = 0;
	            } else if (runningPCB != null && runningPCB.getPriority() == 2 && !rQ1.isEmpty()) {
	                rQ2.add(runningPCB);
	                sortByBurstTime(rQ2); // for SJF
	                runningPCB = execute(rQ1.remove(0));
	                cpuCount = 0;
	            }

	            clock++;
	            if (runningPCB != null) {
	                runningPCB.setResponseTime(runningPCB.responseTime + 1);
	                cpuCount++;
	                if (runningPCB.responseTime == runningPCB.getBurstTime()) {
	                    terminate(runningPCB);
	                    runningPCB = null;
	                    cpuCount = 0;
	                }
	            }
	        }
	        
	    }

	    private void sortByBurstTime(List<PCB> q) { // for SJF
	        Collections.sort(q, Comparator.comparingInt(PCB::getBurstTime));
	    }

	    private void sortByArrivalTime(List<PCB> q) {
	        Collections.sort(q, Comparator.comparingInt(PCB::getArrivalTime));
	    }

	    public PCB execute(PCB pcb) {
	        if (pcb.responseTime == 0) {
	            pcb.setStartTime(clock);
	        }
	        SchChart += pcb.getProcessID() + " | ";
	        return pcb;
	    }

	    public PCB terminate(PCB pcb) {
	        pcb.setTerminationTime(clock);
	        pcb.setWaitingTime(pcb.getTerminationTime() - pcb.getArrivalTime() - pcb.getBurstTime());
	        return pcb;
	    }

	    public PCB nextPCB() {
	        PCB p1 = null;
	        PCB p2 = null;
	        if (nextPID1 < q1.size()) {
	            p1 = q1.get(nextPID1);
	        }

	        if (nextPID2 < q2.size()) {
	            p2 = q2.get(nextPID2);
	        }

	        if (p1 == null && p2 == null)
	            return null;
	        else if (p1 != null && p2 != null) {
	            if (p1.getArrivalTime() <= p2.getArrivalTime()) {
	                nextPID1++;
	                return p1;
	            } else {
	                nextPID2++;
	                return p2;
	            }
	        } else if (p1 == null) {
	            nextPID2++;
	            return p2;
	        } else {
	            nextPID1++;
	            return p1;
	        }
	    }

	    public List<PCB> getQ1() {
	        return q1;
	    }

	    public List<PCB> getQ2() {
	        return q2;
	    }

	    public void setQ1(List<PCB> q) {
	        nextPID1 = 0;
	        sortByArrivalTime(q);
	        q1 = q;
	        for (PCB p : q1) {
	            p.setStartTime(0);
	            p.setTerminationTime(0);
	            p.setWaitingTime(0);
	            p.setResponseTime(0);
	        }
	    }

	    public void setQ2(List<PCB> q) {
	        nextPID1 = 0;
	        sortByArrivalTime(q);
	        q2 = q;
	        for (PCB p : q2) {
	            p.setStartTime(0);
	            p.setTerminationTime(0);
	            p.setWaitingTime(0);
	            p.setResponseTime(0);
	        }
	    }

	    public double getAverageTurnAround() {
	        double totalTurnAround = 0;
	        for (PCB p : getQ1()) {
	            totalTurnAround += p.getTurnaroundTime();
	        }
	        for (PCB p : getQ2()) {
	            totalTurnAround += p.getTurnaroundTime();
	        }
	        return totalTurnAround / (q1.size() + q2.size());
	    }

	    public double getAverageWait() {
	        double totalWait = 0;
	        for (PCB p : getQ1()) {
	            totalWait += p.getWaitingTime();
	        }
	        for (PCB p : getQ2()) {
	            totalWait += p.getWaitingTime();
	        }
	        return totalWait / (q1.size() + q2.size());
	    }

	    public double getAverageResponse() {
	        double totalResponse = 0;
	        for (PCB p : getQ1()) {
	            totalResponse += p.getResponseTime();
	        }
	        for (PCB p : getQ2()) {
	            totalResponse += p.getResponseTime();
	        }
	        return totalResponse / (q1.size() + q2.size());
	    }

	}
	
}



