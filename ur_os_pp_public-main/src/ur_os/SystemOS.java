/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ur_os;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import static ur_os.SchedulerType.FAIR;
import static ur_os.SchedulerType.PRIORITY;
import static ur_os.SchedulerType.RR;
import ur_os.CreateFile;
import static ur_os.CreateFile.returnFile;



/**
 *
 * @author super
 */
public final class SystemOS implements Runnable{
    
    
    
    private static int clock = 0;
    private static final int MAX_SIM_CYCLES = 1000;
    private static final int MAX_SIM_PROC_CREATION_TIME = 50;
    private static final double PROB_PROC_CREATION = 0.1;
    private static Random r = new Random(1235);
    private int CPUUtilization = 0; // Tiempo que la CPU estuvo ocupada
    private int cpucount = 0; // Contador de ciclos de CPU utilizados
    private ArrayList<Process> processes; // Lista de procesos
    private ArrayList<Integer> execution; // Lista de ejecución de procesos
    private OS os;
    private CPU cpu;
    private IOQueue ioq;
    private ReadyQueue readyqueue;
    private SchedulerType selectedScheduler;
    
    private int simulation;
    boolean menu = false;
 
    public SystemOS() {
        this(false);
    }
    
    public SystemOS(boolean menu) {
        cpu = new CPU();
        ioq = new IOQueue();
        if(menu){
            menu();
        }else{
            selectedScheduler = SchedulerType.FCFS;
            simulation = 3; //Simpler2
        }
        
        readyqueue = new ReadyQueue(os,selectedScheduler);
        os = new OS(this, cpu, ioq , selectedScheduler );
        cpu.setOS(os);
        ioq.setOS(os);
        execution = new ArrayList();
        processes = new ArrayList();
        
        simulation(simulation);

    }
    
    public int getTime(){
        return clock;
    }
    
    public ArrayList<Process> getProcessAtI(int i){
        ArrayList<Process> ps = new ArrayList();
        
        for (Process process : processes) {
            if(process.getTime_init() == i){
                ps.add(process);
            }
        }
        
        return ps;
    }

    public void initSimulationQueue(){
        double tp;
        Process p;
        for (int i = 0; i < MAX_SIM_PROC_CREATION_TIME; i++) {
            tp = r.nextDouble();
            if(PROB_PROC_CREATION >= tp){
                p = new Process();
                p.setTime_init(clock);
                processes.add(p);
            }
            clock++;
        }
        clock = 0;
    }
    
    
    public void simulation(int simulation){
        switch(simulation){
            case 1 -> {
                initSimulationQueue();
                showProcesses();
            }
            case 2 -> {
                initSimulationQueueSimpler();
                showProcesses();
            }
            case 3 -> {
                initSimulationQueueSimpler2();
                showProcesses();
            }
            case 4 -> {
                initSimulationQueueSimpler3();
                showProcesses();
            }
        }
    }
    
    public void initSimulationQueueSimple(){
        Process p;
        int cont = 0;
        for (int i = 0; i < MAX_SIM_PROC_CREATION_TIME; i++) {
            if(i % 4 == 0){
                p = new Process(cont++,-1);
                p.setTime_init(clock);
                processes.add(p);
            }
            clock++;
        }
        clock = 0;
    }
    
    public void initSimulationQueueSimpler(){
        
        Process p = new Process(false);
        p.setPriority(0);
        ProcessBurst temp = new ProcessBurst(5,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(4,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(3,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(0);
        processes.add(p);
        
        
        p = new Process(false);
        p.setPriority(1);
        temp = new ProcessBurst(3,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(5,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(6,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(2);
        processes.add(p);
        
        p = new Process(false);
        p.setPriority(2);
        temp = new ProcessBurst(7,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(3,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(5,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(6);
        processes.add(p);
        
        p = new Process(false);
        p.setPriority(3);
        temp = new ProcessBurst(4,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(3,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(7,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(8);
        processes.add(p);
        
        clock = 0;
    }
    
    public void initSimulationQueueSimpler2(){
        
        Process p = new Process(false);
        p.setPriority(0);
        ProcessBurst temp = new ProcessBurst(15,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(12,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(21,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(0);
        p.setPid(0);
        processes.add(p);
        
        
        p = new Process(false);
        p.setPriority(0);
        temp = new ProcessBurst(8,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(4,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(16,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(2);
        p.setPid(1);
        processes.add(p);
        
        p = new Process(false);
        p.setPriority(1);
        temp = new ProcessBurst(10,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(5,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(12,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(6);
        p.setPid(2);
        processes.add(p);
        
        p = new Process(false);
        p.setPriority(1);
        temp = new ProcessBurst(9,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(6,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(17,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(8);
        p.setPid(3);
        processes.add(p);
        
        clock = 0;
    }
    

    public void initSimulationQueueSimpler3(){ //quiz
        
        Process p = new Process(false);
        p.setPriority(0);
        ProcessBurst temp = new ProcessBurst(6,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(2,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(4,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(0);
        processes.add(p);
        
        
        p = new Process(false);
        p.setPriority(1);
        temp = new ProcessBurst(2,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(5,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(5,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(3);
        processes.add(p);
        
        p = new Process(false);
        p.setPriority(2);
        temp = new ProcessBurst(3,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(1,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(6,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(5);
        processes.add(p);
        
        p = new Process(false);
        p.setPriority(3);
        temp = new ProcessBurst(4,ProcessBurstType.CPU);    
        p.addBurst(temp);
        temp = new ProcessBurst(4,ProcessBurstType.IO);    
        p.addBurst(temp);
        temp = new ProcessBurst(7,ProcessBurstType.CPU);    
        p.addBurst(temp);
        p.setTime_init(8);
        processes.add(p);
        
        clock = 0;
    }
    
    public boolean isSimulationFinished(){
        
        boolean finished = true;
        
        for (Process p : processes) {
            finished = finished && p.isFinished();
        }
        
        return finished;
    
    }
    
    public void menu() {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("===== Menu =====");
        System.out.println("1. FCFS");
        System.out.println("2. SJF No Preventivo");
        System.out.println("3. SJF Preventivo");
        System.out.println("4. Round Robin");
        System.out.println("5. Por Prioridades");
        System.out.println("6. MFQ");
        System.out.println("7. Fair Scheduler");
        System.out.print("Select the algorithm (1-7): ");

        int schedulerChoice = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        selectedScheduler = switch (schedulerChoice) {
            case 1 -> SchedulerType.FCFS;
            case 2 -> SchedulerType.SJF_NP;
            case 3 -> SchedulerType.SJF_P;
            case 4 -> SchedulerType.RR;
            case 5 -> SchedulerType.PRIORITY;
            case 6 -> SchedulerType.MFQ;
            case 7 -> SchedulerType.FAIR;
            default -> throw new IllegalArgumentException("Scheduler inválido");
        };

        readyqueue = new ReadyQueue(os, selectedScheduler);  

        System.out.println("\n===== Simulation Menu =====");
        System.out.println("1. initSimulationQueue");
        System.out.println("2. initSimulationQueueSimpler");
        System.out.println("3. initSimulationQueueSimpler2");
        System.out.print("Select the simulation (1-3): ");
        simulation = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
    }


    
    @Override
    public void run() {
        
        double tp;
        ArrayList<Process> ps;
        
        System.out.println("***SIMULATION START***");
        
        int i=0;
        Process temp_exec;
        int tempID;
        while(!isSimulationFinished() && i < MAX_SIM_CYCLES){//MAX_SIM_CYCLES is the maximum simulation time, to avoid infinite loops
            System.out.println("***Clock: "+i+"***");
            System.out.println(cpu);
            System.out.println(ioq);
            
            if(i == 32){ //This allows you to stop the simulation at a given time (i) if a debugging stoping point is located here
                int a=0;
            }
            
            //Crear procesos, si aplica en el ciclo actual
            ps = getProcessAtI(i);
            for (Process p : ps) {
                os.create_process(p);
            } //If the scheduler is preemtive, this action will trigger the extraction from the CPU, is any process is there.
            
            //Actualizar el OS, quien va actualizar el Scheduler            

            os.update();
            //os.update() prepares the system for execution. It runs at the beginning of the cycle.
            
                        
            clock++;
            
            temp_exec = cpu.getProcess();
            if (temp_exec == null) {
                tempID = -1;
                cpucount++;
            } else {
                tempID = temp_exec.getPid();
                if (temp_exec.getFirstExecutionTime() == -1) {
                    temp_exec.setFirstExecutionTime(clock);
                }
            }
            execution.add(tempID);
            
            //Actualizar la CPU
            cpu.update();
            
            
            ///Actualizar la IO
            ioq.update();
            
            //Las actualizaciones de CPU y IO pueden generar interrupciones que actualizan a cola de listos, cuando salen los procesos
            
            System.out.println("After the cycle: ");
            System.out.println(cpu);
            System.out.println(ioq);
            i++;

        }
        
        
        try (PrintWriter pw = new PrintWriter(new FileWriter(returnFile()))) {
            pw.println("***SIMULATION FINISHES***");

            // os.showProcesses(); // Si quieres imprimir procesos, hazlo también aquí

            pw.println("***Process Execution***");
            for (Integer num : execution) {
                pw.print(num + " ");
            }
            pw.println();

            pw.println("***Performance Indicators***");
            pw.println("Total execution cycles: " + clock);
            pw.println("CPU Utilization: " + this.calcCPUUtilization());
            pw.println("Throughput: " + this.calcThroughput());
            pw.println("Average Turnaround Time: " + this.calcTurnaroundTime());
            pw.println("Average Waiting Time: " + this.calcAvgWaitingTime());
            pw.println("Average Context Switches (solo Gantt): " + this.calcAvgContextSwitches());
            pw.println("Average Context Switches (completo): " + this.calcAvgContextSwitches2());
            pw.println("Average Response Time: " + this.calcResponseTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        System.out.println("***SIMULATION FINISHES***");

        // os.showProcesses(); // Si quieres imprimir procesos, hazlo también aquí

        System.out.println("***Process Execution***");
        for (Integer num : execution) {
            System.out.print(num + " ");
        }
        System.out.println();

        System.out.println("***Performance Indicators***");
        System.out.println("Total execution cycles: " + clock);
        System.out.println("CPU Utilization: " + this.calcCPUUtilization());
        System.out.println("Throughput: " + this.calcThroughput());
        System.out.println("Average Turnaround Time: " + this.calcTurnaroundTime());
        System.out.println("Average Waiting Time: " + this.calcAvgWaitingTime());
        System.out.println("Average Context Switches (solo Gantt): " + this.calcAvgContextSwitches());
        System.out.println("Average Context Switches (completo): " + this.calcAvgContextSwitches2());
        System.out.println("Average Response Time: " + this.calcResponseTime());
        
        
        System.out.println("*********Comparation:************************");
        compareFiles("C:\\Users\\jesus\\Desktop\\ur_os_pp_public-main\\FCFS.txt", "C:\\Users\\jesus\\Desktop\\ur_os_pp_public-main\\FCFS.txt");
        
    }
    
    public void showProcesses() {
        System.out.println("Process list:");
        StringBuilder sb = new StringBuilder();
        
        for (Process process : processes) {
            sb.append(process);
            sb.append("\n");
        }
        
        System.out.println(sb.toString());
    }
    
    public double calcCPUUtilization() {
        
        if (clock == 0) return 0;
        
        int busyCycles = clock - cpucount; // cpucount cuenta los ciclos con CPU vacía
        return ((double) busyCycles / clock);
    }
    
    public double calcTurnaroundTime() {
        
        double totalTurnaround = 0;
        int finishedCount = 0;
        
        for (Process p : processes) {
            if (p.getState() == ProcessState.FINISHED) {
                totalTurnaround += (p.getTime_finished() - p.getTime_init());
                finishedCount++;
            }
        }
        
        if (finishedCount == 0) return 0;
        
        return totalTurnaround / finishedCount;
    }
    
    public double calcThroughput() {
        if (processes.isEmpty() || clock == 0) return 0;
    
        int finishedCount = 0;
        for (Process p : processes) {
            if (p.getState() == ProcessState.FINISHED) {
                finishedCount++;
            }
        }
        
        return (double) finishedCount / clock; // Procesos terminados por unidad de tiempo
    }
    
    public double calcAvgWaitingTime() {
        
        double totalWaiting = 0;
        int finishedCount = 0;
        
        for (Process p : processes) {
            if (p.getState() == ProcessState.FINISHED) {
                int turnaround = p.getTime_finished() - p.getTime_init();
                // Waiting = Turnaround - (tiempo total en CPU + tiempo total en IO)
                totalWaiting += (turnaround - p.getBurstTime());
                finishedCount++;
            }
        }
           
        if (finishedCount == 0) return 0;
        
        return totalWaiting / finishedCount;
    }

    //Everytime a process is taken out from memory, when a interruption occurs
    public double calcAvgContextSwitches() { //solo gantt

        if (processes.isEmpty() || execution.isEmpty()) {
            return 0;
        }

        int switches = 0;
        int prevPID = -1; // -1 = "CPU vacía antes de empezar", nunca choca con un PID real

        for (int actPID : execution) {
            if (actPID != prevPID) {
                if (actPID != -1) {
                    switches++;
                }
                prevPID = actPID;
            }
        }

        return (double) switches / processes.size();
    }


    //Just context switches based on the execution timeline
    public double calcAvgContextSwitches2() {

        if (processes.isEmpty() || execution.isEmpty()) {
            return 0;
        }

        int switches = 0;
        int prevPID = -1;

        // Cambios que aparecen directamente en el Gantt
        for (int actPID : execution) {
            if (actPID != prevPID) {
                if (actPID != -1) {
                    switches++;
                }
                prevPID = actPID;
            }
        }


        int schedulerSwitches = os.getTotalContextSwitches();


        return (double) (switches + schedulerSwitches-1) / processes.size();
    }
    
    
    public double calcResponseTime() {
        
        double total = 0;
        int startedCount = 0;
        
        for (Process p : processes) {
            int rt = p.getResponseTime();
            if (rt != -1) { // -1 significa que el proceso nunca llegó a ejecutarse en CPU
                total += rt;
                startedCount++;
            }
        }
        
        if (startedCount == 0) return 0;
        
        return (total / startedCount)-1;

    }

    public void compareFiles(String filePath1, String filePath2) {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath1));
             BufferedReader reader2 = new BufferedReader(new FileReader(filePath2))) {

            String line1, line2;
            int lineNum = 1;
            boolean differenceFound = false;

            while ((line1 = reader1.readLine()) != null | (line2 = reader2.readLine()) != null) {
                if (line1 == null || line2 == null || !line1.equals(line2)) {
                    System.out.println("Difference at line " + lineNum + ":");
                    System.out.println("File1: " + (line1 != null ? line1 : "[EOF]"));
                    System.out.println("File2: " + (line2 != null ? line2 : "[EOF]"));
                    differenceFound = true;
                }
                lineNum++;
            }
            if (!differenceFound) {
                System.out.println("The files are identical.");
            }

        } catch (IOException e) {
            System.err.println("Error comparing files: " + e.getMessage());
        }
    }
    
    
    
}