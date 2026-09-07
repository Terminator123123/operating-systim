package ur_os;

import java.util.HashMap;

public class FAIR extends Scheduler {

    private HashMap<Integer, Double> tau;
    private HashMap<Integer, Integer> lastBurst;
    private double alpha = 0.5;

    public FAIR(OS os, int value) {
        super(os);
        tau = new HashMap<>();
        lastBurst = new HashMap<>();
    }

    @Override
    public void addProcess(Process p) {

        int pid = p.getPid();

        int currentBurst = p.getRemainingTimeInCurrentBurst();

        if (!tau.containsKey(pid)) {

            tau.put(pid, (double) currentBurst);
            lastBurst.put(pid, currentBurst);

        } else {

            double oldTau = tau.get(pid);
            int last = lastBurst.get(pid);

            double newTau = alpha * last + (1 - alpha) * oldTau;

            tau.put(pid, newTau);
            lastBurst.put(pid, currentBurst);
        }

        super.addProcess(p);
    }

    @Override
    public void newProcess(boolean cpuEmpty) {

        if (cpuEmpty) {
            getNext(true);
        }
    }

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {

        if (cpuEmpty) {
            getNext(true);
        }
    }

    @Override
    public void getNext(boolean cpuEmpty) {

        if (!cpuEmpty)
            return;

        if (processes.isEmpty())
            return;

        Process best = processes.get(0);

        for (Process p : processes) {

            double tauP = tau.get(p.getPid());
            double tauBest = tau.get(best.getPid());

            if (tauP < tauBest) {
                best = p;
            }
            else if (tauP == tauBest) {
                best = tieBreaker(best, p);
            }
        }

        processes.remove(best);

        System.out.println("FAIR (SJF_EST) -> PID "
                + best.getPid()
                + " tau=" + tau.get(best.getPid()));

        os.interrupt(InterruptType.SCHEDULER_RQ_TO_CPU, best);
    }
}