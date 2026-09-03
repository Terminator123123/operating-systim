/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ur_os;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Multilevel Feedback Queue (MFQ).
 *
 * The structure is the same as the multilevel queue (index 0 is the highest priority level and
 * the CPU always serves the highest non empty level), but here the level of a process is not
 * fixed: it is feedback obtained from the behaviour the process shows during the simulation.
 *
 * Rules implemented:
 *   1. Every new process enters at level 0, the level with the shortest quantum.
 *   2. A process that exhausts its quantum while other processes are waiting is demoted one
 *      level. This is the classic penalty for CPU bound behaviour: the more CPU a process
 *      consumes without releasing it, the longer the quantum it receives and the lower its
 *      priority becomes.
 *   3. A process that returns from an I/O burst is promoted one level (never above level 0).
 *      It released the CPU voluntarily, so it is treated as I/O bound and rewarded with a
 *      higher priority and a shorter quantum, which improves its response time. This rule also
 *      works as an aging mechanism and is what prevents the permanent starvation that the fixed
 *      priority scheduler suffers.
 *   4. The last level is FCFS, so it has no quantum: a process that reaches it runs until it
 *      finishes its current CPU burst and it cannot be demoted any further.
 *
 * A process that exhausts its quantum while no other process is waiting keeps the CPU and keeps
 * its level: demoting it would have no effect other than distorting the indicators, because
 * there is no competitor to give the CPU to.
 *
 * @author prestamour
 */
public class MFQ extends Scheduler{

    int currentScheduler;
    
    private ArrayList<Scheduler> schedulers;
    //This may be a suggestion... you may use the current sschedulers to create the Multilevel Feedback Queue, or you may go with a more tradicional way
    //based on implementing all the queues in this class... it is your choice. Change all you need in this class.
    
    MFQ(OS os){
        super(os);
        currentScheduler = -1;
        schedulers = new ArrayList();
    }
    
    MFQ(OS os, Scheduler... s){ //Received multiple arrays
        this(os);
        schedulers.addAll(Arrays.asList(s));
        if(s.length > 0)
            currentScheduler = 0;
    }
        
    /**
     * Routes the process to a level according to where it comes from. This is the method that
     * implements the feedback of the algorithm.
     */
    @Override
    public void addProcess(Process p){
        if(p == null || schedulers.isEmpty())
            return;

        int level;

        if(p.getState() == ProcessState.NEW){
            level = 0;                                          // Regla 1: entra por el nivel mas alto
        }else if(p.getState() == ProcessState.IO){
            level = clamp(p.getCurrentScheduler() - 1);          // Regla 3: premio por liberar la CPU
        }else{
            level = clamp(p.getCurrentScheduler());              // Viene de la CPU, ya trae su nivel
        }

        p.setCurrentScheduler(level);
        schedulers.get(level).addProcess(p);
    }

    /**
     * Keeps a level index inside the range of existing queues.
     */
    private int clamp(int level){
        if(level < 0)
            return 0;
        if(level >= schedulers.size())
            return schedulers.size() - 1;
        return level;
    }
    
    /**
     * Finds the highest priority level (lowest index) that has at least one process waiting.
     * Returns -1 when every level is empty.
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
            // CPU libre: se sirve el nivel mas alto que tenga procesos.
            defineCurrentScheduler();
            if(currentScheduler >= 0)
                schedulers.get(currentScheduler).getNext(true);
            return;
        }

        // CPU ocupada: se avanza el quantum del nivel al que pertenece el proceso en ejecucion.
        Process running = os.getProcessInCPU();
        if(running == null)
            return;

        int level = clamp(running.getCurrentScheduler());
        Scheduler owner = schedulers.get(level);

        if(!(owner instanceof RoundRobin))
            return; // Regla 4: el ultimo nivel es FCFS, no expropia.

        if(!((RoundRobin) owner).advanceQuantum())
            return; // Al proceso en CPU todavia le queda quantum.

        defineCurrentScheduler();

        if(currentScheduler < 0)
            return; // Nadie mas compite: conserva la CPU y su nivel, con un quantum nuevo.

        // Regla 2: agoto su quantum habiendo competencia, baja un nivel y devuelve la CPU.
        running.setCurrentScheduler(clamp(level + 1));
        os.interrupt(InterruptType.SCHEDULER_CPU_TO_RQ, null);
        addContextSwitch();

        defineCurrentScheduler();
        if(currentScheduler >= 0)
            schedulers.get(currentScheduler).getNext(true);
    }
    
    @Override
    public void newProcess(boolean cpuEmpty) {} //Non-preemtive in this event: the level is assigned in addProcess

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {} //Non-preemtive in this event: the promotion is done in addProcess

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
     * Aggregates the context switches of every level, otherwise the indicator would always be 0
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
            sb.append("Feedback level ");
            sb.append(i);
            sb.append(":\n");
            sb.append(schedulers.get(i).toString());
        }
        return sb.toString();
    }
    
}
