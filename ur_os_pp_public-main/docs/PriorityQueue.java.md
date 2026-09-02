# `PriorityQueue` Class Documentation

The `PriorityQueue` class extends the `Scheduler` class to implement a multi-level priority-based scheduling system using multiple internal schedulers. Each level can have a different scheduler policy.

---

## Fields

- `int currentScheduler`  
    Tracks the index of the currently active scheduler.
    
- `ArrayList<Scheduler> schedulers`  
    Stores the different levels of schedulers, each handling processes of a specific priority.
    

---

## Constructors

- `PriorityQueue(OS os)`  
    Initializes an empty list of schedulers and sets `currentScheduler` to -1.
    
- `PriorityQueue(OS os, Scheduler... s)`  
    Initializes the priority queue with a variable number of scheduler instances. Sets the `currentScheduler` to 0 if schedulers are provided.
    

---

## Methods

- `void addProcess(Process p)`  
    Intended to override the parent class's method to define how incoming processes are distributed among internal schedulers based on priority. Not yet implemented.
    
- `void defineCurrentScheduler()`  
    Suggested method for determining which internal scheduler should be active next. Helps in selecting the scheduler that has processes to execute.
    
- `void getNext(boolean cpuEmpty)`  
    Suggests logic for determining the next process to run:
    
    - If the CPU is empty, it should select the next scheduler with available processes.
        
    - If the CPU is occupied and the active scheduler is preemptive, it may check if a higher-priority process should preempt the current one.
        
- `void newProcess(boolean cpuEmpty)`  
    Empty implementation. Since the event is non-preemptive, no action is needed by default.
    
- `void IOReturningProcess(boolean cpuEmpty)`  
    Empty implementation. Since the event is non-preemptive, no action is needed by default.
    

---

## Notes

- The class relies on cooperation with the `OS` class and a set of scheduler instances to simulate multi-level feedback or fixed-priority scheduling.
    
- Intended for complex scheduling policies that can combine round-robin, FCFS, or other scheduling algorithms at different priority levels.
    
- To function properly, `addProcess()` and `getNext()` should be fully implemented.
    

---