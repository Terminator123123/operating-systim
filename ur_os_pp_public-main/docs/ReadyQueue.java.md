# `ReadyQueue` 

The `ReadyQueue` class is a container for scheduling processes in an operating system simulation. It acts as a mediator between the OS and the scheduling logic, selecting the appropriate scheduling algorithm based on configuration.

---

## Fields

- `Scheduler s`  
    Reference to the scheduler strategy used (e.g., FCFS, SJF, Round Robin).
    
- `OS os`  
    Reference to the OS that owns this `ReadyQueue`.
    

---

## Constructors

- `ReadyQueue(OS os)`  
    Initializes a `ReadyQueue` using the scheduler type specified by the OS. The supported types include:
    
    - `FCFS`
        
    - `SJF_NP`
        
    - `SJF_P`
        
    - `RR`
        
    - `PRIORITY`
        
    - `MFQ`
        
    - `FAIR` (currently unimplemented)
        
- `ReadyQueue(OS os, Scheduler s)`  
    Initializes a `ReadyQueue` with a specific scheduler instance.
    

---

## Methods

- `void addProcess(Process p)`  
    Adds a process to the queue by delegating to the current scheduler.
    
- `Process removeProcess(Process p)`  
    Removes a specified process from the scheduler’s queue.
    
- `void update()`  
    Updates the scheduler state (used to fetch the next process if necessary).
    
- `String toString()`  
    Returns a string representation of the current state of the scheduler’s process queue.
    
- `int getTotalContextSwitches()`  
    Returns the number of context switches that have occurred so far, as tracked by the scheduler.
    

---

## Notes

- The scheduler logic is encapsulated in subclasses of `Scheduler`, making `ReadyQueue` an abstraction over the scheduling mechanism.
    
- The system is easily extendable to include more scheduling strategies by adding new cases to the constructor switch block.
    

---