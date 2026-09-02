package ur_os;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SJF_P extends Scheduler{

    SJF_P(OS os){
        super(os);
    }

    @Override
    public void newProcess(boolean cpuEmpty){
        if (!cpuEmpty) {// cuando un proceso entra en cola, se interruimpe el actual y lo manda a cola
            os.interrupt(InterruptType.SCHEDULER_CPU_TO_RQ, null);
            addContextSwitch();
        }
    }

    @Override
    public void IOReturningProcess(boolean cpuEmpty){
        if (!cpuEmpty) { // ocurre igual, cuando un proceso sale de I/O lo sacan de la cpu y se ingresa quien tiene menor burst
            os.interrupt(InterruptType.SCHEDULER_CPU_TO_RQ, null);
            addContextSwitch();
        }
    }

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