/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os;

/**
 *
 * @author prestamour
 */
public class RoundRobin extends Scheduler{

    int q;
    int cont;
    boolean multiqueue;
    
    RoundRobin(OS os){
        super(os);
        q = 5;
        cont=0;
    }
    
    RoundRobin(OS os, int q){
        this(os);
        this.q = q;
    }

    RoundRobin(OS os, int q, boolean multiqueue){
        this(os);
        this.q = q;
        this.multiqueue = multiqueue;
    }
    

    
    void resetCounter(){
        cont=0;
    }
   
    @Override
    public void getNext(boolean cpuEmpty) {

        if (cpuEmpty) {
            // CPU libre: si hay alguien esperando, entra el primero de la fila (FIFO)
            if (!processes.isEmpty()) {
                Process candidato = processes.poll(); // saca y remueve el primero
                os.interrupt(InterruptType.SCHEDULER_RQ_TO_CPU, candidato);
                resetCounter(); // arranca su quantum desde cero
            }
            return;
        }

        // CPU ocupada: avanzamos el contador de ciclos del proceso actual
        cont++;

        if (cont == q) {
            // Se agotó el quantum del proceso en CPU
            if (!processes.isEmpty()) {
                // Hay competencia: expulsar al actual y cargar al siguiente de la fila
                Process siguiente = processes.poll();
                os.interrupt(InterruptType.SCHEDULER_CPU_TO_RQ, siguiente);
                addContextSwitch();
            }
            // Si no hay nadie esperando, el proceso actual sigue en CPU sin interrupción
            resetCounter();
        }
    }
    
    
    @Override
    public void newProcess(boolean cpuEmpty) {} //Non-preemtive in this event

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {} //Non-preemtive in this event
    
}
