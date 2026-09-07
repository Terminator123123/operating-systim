package ur_os;

import java.io.File;
import static ur_os.CreateFile.createSchedulerFile;
import static ur_os.SchedulerType.FCFS;

public class ReadyQueue {
    
    
    Scheduler s;
    OS os;
    

    public ReadyQueue(OS os, SchedulerType schedulerType) {
        this.os = os;

        switch (schedulerType) {
            case FCFS:
                s = new FCFS(os);
                createSchedulerFile("FCFS");
                
                break;
            case SJF_NP:
                s = new SJF_NP(os);
                createSchedulerFile("SJF_NP");
                break;
            case SJF_P:
                s = new SJF_P(os);
                createSchedulerFile("SJF_P");
                break;
            case RR:
                s = new RoundRobin(os, 4);
                createSchedulerFile("RR");
                break;
            case PRIORITY:
                s = new PriorityQueue(os,
                        new RoundRobin(os, 9, true),
                        new RoundRobin(os, 6, true),
                        new RoundRobin(os, 3, true),
                        new RoundRobin(os, 2, true));
                createSchedulerFile("PRIORITY");
                break;
            case MFQ:
                s = new MFQ(os,
                        new RoundRobin(os, 3, true),
                        new RoundRobin(os, 6, true),
                        new FCFS(os));
                createSchedulerFile("MFQ");
                break;
            case FAIR:
                s = new FAIR(os, 0);
                createSchedulerFile("FAIR");
                break;
        }

    }

    public void addProcess(Process p) {
        s.addProcess(p);
    }

    public Process removeProcess(Process p) {
        return s.removeProcess(p);
    }

    public void update() {
        s.update();
    }

    public String toString() {
        return s.toString();
    }

    public int getTotalContextSwitches() {
        return s.getTotalContextSwitches();
    }
}
