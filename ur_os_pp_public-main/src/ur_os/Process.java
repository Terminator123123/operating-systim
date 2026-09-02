/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ur_os;

import java.util.Random;

/**
 *
 * @author super
 */
public class Process implements Comparable{
    public static final int NUM_CPU_CYCLES = 3;
    public static final int MAX_CPU_CYCLES = 10;
    public static final int MAX_IO_CYCLES = 10;
    int pid;
    int time_init;
    int time_finished;
    ProcessBurstList pbl;
    ProcessState state;
    int currentScheduler;
    int priority;
    Random r;
    private int responseTime;


    public Process() {
        r = new Random();
        pid = -1;
        time_init = 0;
        time_finished = -1;
        pbl = new ProcessBurstList();
        pbl.generateRandomBursts(NUM_CPU_CYCLES, MAX_CPU_CYCLES, MAX_IO_CYCLES);
        //pbl.generateSimpleBursts(); //Generates process with 3 bursts (CPU, IO, CPU) with 5 cycles each
        state = ProcessState.NEW;
        currentScheduler = 0;
        priority = 0;
    }
    
    public Process(boolean auto) {
        pid = -1;
        time_init = 0;
        time_finished = -1;
        pbl = new ProcessBurstList();
        if(auto){
            pbl.generateRandomBursts(NUM_CPU_CYCLES, MAX_CPU_CYCLES, MAX_IO_CYCLES);
            //pbl.generateSimpleBursts(); //Generates process with 3 bursts (CPU, IO, CPU) with 5 cycles each
            priority = r.nextInt(10);
        }
        state = ProcessState.NEW;
    }
    
    public Process(int pid, int time_init) {
        this();
        this.pid = pid;
        this.time_init = time_init;
    }
    
    public Process(Process p) {
        this.pid = p.pid;
        this.time_init = p.time_init;
        this.pbl = new ProcessBurstList(p.getPBL());
    }

    public boolean advanceBurst(){
        return pbl.advanceBurst();
    }
    
    public boolean isFinished(){
        return pbl.isFinished();
    }
    private int contextSwitches = 0;
    
    public int getContextSwitches() {
    return contextSwitches;
}
    public void increaseContextSwitches() {
    contextSwitches++;
}
    private int firstExecutionTime = -1;

    public void setFirstExecutionTime(int time) {
        if (firstExecutionTime == -1) { // Solo se asigna la primera vez
            this.firstExecutionTime = time;
        }
    }


    public Integer getFirstExecutionTime() {
        return firstExecutionTime;
    }

    public int getResponseTime() {
        if (firstExecutionTime == -1) { 
            return -1; // Indicar que aún no ha sido ejecutado
        }
        return firstExecutionTime - time_init;
    }


    public void setTime_finished(int time_finished) {
        this.time_finished = time_finished;
    }
    
    public void addBurst(ProcessBurst pb){
        pbl.addBurst(pb);
    }
    
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getTime_init() {
        return time_init;
    }

    public void setTime_init(int time_init) {
        this.time_init = time_init;
    }
    
    public ProcessBurstList getPBL(){
        return pbl;
    }

    public ProcessState getState() {
        return state;
    }

    public int getTime_finished() {
        return time_finished;
    }

    public int getTotalExecutionTime(){
        return pbl.getTotalExecutionTime();
    }
    
    public void setState(ProcessState state) {
        this.state = state;
    }
        
    
    public int getRemainingTimeInCurrentBurst(){
        return pbl.getRemainingTimeInCurrentBurst();
    }
    
    
    public boolean isCurrentBurstCPU(){
        return pbl.isCurrentBurstCPU();
    }

    public ProcessBurstList getPbl() {
        return pbl;
    }

    public void setPbl(ProcessBurstList pbl) {
        this.pbl = pbl;
    }

    public int getCurrentScheduler() {
        return currentScheduler;
    }

    public void setCurrentScheduler(int currentScheduler) {
        this.currentScheduler = currentScheduler;
    }
    
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public String toString(){
        return "PID: "+pid+" t: "+time_init+" "+pbl;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Process){
            Process p = (Process)o;
            return this.getPid() - p.getPid();
        }
        
        return -1;
    }
    public int getIoCompletionTime() {
        return pbl.getNextIOCompletionTime();
    }

    public int getBurstTime() {
        return pbl.getTotalExecutionTime();  // Usa un método adecuado de ProcessBurstList
    }

    @Override
    public boolean equals(Object o){
    
        if(o instanceof Process){
            Process p = (Process)o;
            return this.getPid() == p.getPid();
        }
        
        return false;
        
    }

    public void setResponseTime(int responseTime) {
        if (this.responseTime == -1) { // Solo se establece una vez
            this.responseTime = responseTime;
        }
    }
}
