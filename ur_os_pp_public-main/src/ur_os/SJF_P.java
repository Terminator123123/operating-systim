package ur_os;


public class SJF_P extends Scheduler{

    SJF_P(OS os){
        super(os);
    }

    @Override
    public void addProcess(Process p) {
        // Un proceso nuevo o que vuelve de I/O solo puede desalojar al proceso
        // actual si su rafaga restante es estrictamente menor (SRTF).
        boolean canPreempt = (p.getState() == ProcessState.NEW
                || p.getState() == ProcessState.IO)
                && !os.isCPUEmpty()
                && p.getRemainingTimeInCurrentBurst()
                   < os.getProcessInCPU().getRemainingTimeInCurrentBurst();

        // Agregar primero el proceso entrante garantiza que, si hay desalojo,
        // la siguiente seleccion compare al entrante con el proceso extraido.
        p.setState(ProcessState.READY);
        processes.add(p);

        if (canPreempt) {
            os.interrupt(InterruptType.SCHEDULER_CPU_TO_RQ, null);
            addContextSwitch();
        }
    }

    @Override
    public void newProcess(boolean cpuEmpty) {}

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {}

    @Override
    public void getNext(boolean cpuEmpty) {

        if (!cpuEmpty || processes.isEmpty()) {
            return; // ya hay alguien en CPU, o no hay nadie esperando: nada que decidir
        }

        // Elegir, entre todos los que están en la cola, el de menor tiempo restante de burst
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
}