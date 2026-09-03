/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Multilevel Queue scheduler (fixed priorities).
 *
 * The scheduler holds one sub-scheduler per priority level. Index 0 is the highest priority.
 * A process is permanently assigned to the queue that matches its priority: there is no
 * feedback between levels (that is what MFQ does).
 *
 * Policy between queues: the CPU always serves the highest priority queue that has processes.
 * Inside a queue, the sub-scheduler (a Round Robin with its own quantum) decides the order.
 *
 * Preemption policy: a process that is already running is NOT expelled the moment a higher
 * priority process arrives; it is allowed to finish its current quantum, and the priority
 * comparison is made when that quantum expires. The reason is that every level is already a
 * Round Robin, so the CPU is reevaluated periodically anyway; expelling on every arrival would
 * add context switches without changing the order in which the queues are served. The cost of
 * this decision is bounded: a high priority process waits at most one quantum of the level that
 * is currently running.
 *
 * @author prestamour
 */
public class PriorityQueue extends Scheduler{

    int currentScheduler;
    
    private ArrayList<Scheduler> schedulers;
    
    PriorityQueue(OS os){
        super(os);
        currentScheduler = -1;
        schedulers = new ArrayList();
    }
    
    PriorityQueue(OS os, Scheduler... s){ //Received multiple arrays
        this(os);
        schedulers.addAll(Arrays.asList(s));
        if(s.length > 0)
            currentScheduler = 0;
    }
    
    
    /**
     * Every process is routed to the queue that matches its priority. The parent's list of
     * processes is never used: the processes live inside the sub-schedulers.
     */
    @Override
    public void addProcess(Process p){
        if(p == null || schedulers.isEmpty())
            return;

        int level = levelForPriority(p.getPriority());
        p.setCurrentScheduler(level);
        schedulers.get(level).addProcess(p);
    }

    /**
     * Maps the priority of a process to a valid queue index. Priorities outside the range of
     * available queues are clamped to the closest level.
     */
    private int levelForPriority(int priority){
        if(priority < 0)
            return 0;
        if(priority >= schedulers.size())
            return schedulers.size() - 1;
        return priority;
    }

    /**
     * Finds the highest priority queue (lowest index) that has at least one process waiting.
     * Returns -1 when every queue is empty. The result is also stored in currentScheduler so
     * the caller can know which level is being served.
     */
    void defineCurrentScheduler(){
        currentScheduler = -1;
        for (int i = 0; i < schedulers.size(); i++) {
            if(!schedulers.get(i).isEmpty()){
                currentScheduler = i;
                return;
            }
        }
    }
    
   
    @Override
    public void getNext(boolean cpuEmpty) {

        if(schedulers.isEmpty())
            return;

        if (cpuEmpty) {
            // CPU libre: se sirve la cola de mayor prioridad que tenga procesos.
            defineCurrentScheduler();
            if(currentScheduler >= 0)
                schedulers.get(currentScheduler).getNext(true);
            return;
        }

        // CPU ocupada: se avanza el quantum del nivel al que pertenece el proceso en ejecucion.
        Process running = os.getProcessInCPU();
        if(running == null)
            return;

        int level = levelForPriority(running.getCurrentScheduler());
        Scheduler owner = schedulers.get(level);

        if(!(owner instanceof RoundRobin))
            return; // Un nivel no expropiativo (FCFS) deja terminar la rafaga completa.

        if(!((RoundRobin) owner).advanceQuantum())
            return; // Al proceso en CPU todavia le queda quantum.

        // El quantum se agoto. Se busca el mejor candidato entre TODAS las colas.
        defineCurrentScheduler();

        if(currentScheduler < 0)
            return; // Nadie mas espera: el proceso en CPU sigue con un quantum nuevo.

        if(currentScheduler > level)
            return; // Solo esperan procesos de menor prioridad: la prioridad fija manda y el
                    // proceso en CPU conserva la CPU.

        // Hay un candidato de prioridad igual o mayor: se devuelve el proceso actual a su cola
        // y se carga el siguiente segun el orden de prioridades.
        os.interrupt(InterruptType.SCHEDULER_CPU_TO_RQ, null);
        addContextSwitch();

        defineCurrentScheduler();
        if(currentScheduler >= 0)
            schedulers.get(currentScheduler).getNext(true);
    }
    
    @Override
    public void newProcess(boolean cpuEmpty) {} //Non-preemtive in this event: see the class comment

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {} //Non-preemtive in this event: see the class comment

    @Override
    public boolean isEmpty(){
        for (Scheduler s : schedulers) {
            if(!s.isEmpty())
                return false;
        }
        return true;
    }

    @Override
    public Process removeProcess(Process p){
        for (Scheduler s : schedulers) {
            s.removeProcess(p);
        }
        return p;
    }

    /**
     * The context switches produced by this scheduler are the ones it counts itself plus the
     * ones counted by every level. Without this override the indicator would always report 0,
     * because the processes never sit in the parent's own list.
     */
    @Override
    public int getTotalContextSwitches(){
        int total = super.getTotalContextSwitches();
        for (Scheduler s : schedulers) {
            total += s.getTotalContextSwitches();
        }
        return total;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < schedulers.size(); i++) {
            sb.append("Priority queue ");
            sb.append(i);
            sb.append(":\n");
            sb.append(schedulers.get(i).toString());
        }
        return sb.toString();
    }
    
}
