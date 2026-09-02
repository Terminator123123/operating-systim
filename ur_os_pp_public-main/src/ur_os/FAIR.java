package ur_os;
import java.util.PriorityQueue;
import java.util.Comparator;

public class FAIR extends Scheduler {
    private int someValue;

    public FAIR(OS os, int someValue) {
        super(os);
        this.someValue = someValue;
    }

    @Override
    public void newProcess(boolean cpuEmpty) {
        
    }

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {
        
    }
    @Override
    public void getNext(boolean cpuEmpty) {
        //Insert code here
    }
}
