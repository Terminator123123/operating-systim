package ur_os;

public class SJF_NP extends Scheduler{

    SJF_NP(OS os){
        super(os);
    }

    @Override
    public void getNext(boolean cpuEmpty) {

        if (!cpuEmpty || processes.isEmpty()) {
            return;
        }

        Process candidato = processes.get(0);
        for (Process p : processes) {
            if (p.getRemainingTimeInCurrentBurst() < candidato.getRemainingTimeInCurrentBurst()) {
                candidato = p;
            } else if (p.getRemainingTimeInCurrentBurst() == candidato.getRemainingTimeInCurrentBurst() && p != candidato) {
                candidato = tieBreaker(candidato, p);
            }
        }

        processes.remove(candidato);
        os.interrupt(InterruptType.SCHEDULER_RQ_TO_CPU, candidato);
    }

    @Override
    public void newProcess(boolean cpuEmpty) {} //Non-preemtive

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {} //Non-preemtive

}