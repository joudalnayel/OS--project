
public class ProcessControlBlock {
    int processId,priority,arrivalTime,cpuBurstTime,startTime,terminationTime,turnAroundTime,waitingTime,responseTime;

    public ProcessControlBlock(int processId, int priority, int arrivalTime, int cpuBurstTime,
            int startTime, int terminationTime, int turnAroundTime, int waitingTime, int responseTime) {
        this.processId = processId;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.cpuBurstTime = cpuBurstTime;
        this.startTime = startTime;
        this.terminationTime = terminationTime;
        this.turnAroundTime = turnAroundTime;
        this.waitingTime = waitingTime;
        this.responseTime = responseTime;
    }

    
}