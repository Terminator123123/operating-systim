# FCFS (First-Come, First-Served) Scheduler

The `FCFS` class implements the **First-Come, First-Served** scheduling algorithm by extending the abstract `Scheduler` class. This is a **non-preemptive** scheduling method where the first process to arrive is the first one to be executed.

## Inheritance

- Inherits from: `Scheduler`
    

## Constructor

- `FCFS(OS os)`
    
    - Initializes the scheduler with a reference to the `OS`.
        

## Overridden Methods

- `void getNext(boolean cpuEmpty)`
    
    - If the CPU is empty and there are processes in the queue, this method selects the first process in the list and sends it to the CPU via an interrupt.
        
    - **Parameters:**
        
        - `cpuEmpty` – indicates whether the CPU is currently free.
            
- `void newProcess(boolean cpuEmpty)`
    
    - No operation. FCFS is non-preemptive, so it doesn't need to act when a new process is added.
        
- `void IOReturningProcess(boolean cpuEmpty)`
    
    - No operation. FCFS is non-preemptive, so it doesn't need to act when a process returns from I/O.
        

## Behavior

- As a non-preemptive scheduler, FCFS does not interrupt a running process.
    
- Processes are handled strictly in the order of their arrival.
    
- The scheduling decision is made only when the CPU becomes empty.
    

## Usage Example

```java
OS os = new OS(system, cpu, ioq);
Scheduler scheduler = new FCFS(os);
```

## Notes

- This scheduler is simple and fair in terms of arrival order but may suffer from the **convoy effect**, where short processes wait behind long ones.
    
- Ideal for environments where simplicity is more important than response time.