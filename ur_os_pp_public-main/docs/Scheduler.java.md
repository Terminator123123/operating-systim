# File: `Scheduler.java`

## Purpose
The `Scheduler` class is an abstract base class for implementing different scheduling policies in an operating system simulation. It manages processes waiting in the ready queue and is responsible for selecting the next process to execute.

---

## Class Definition

```java
public abstract class Scheduler
```

---

## Attributes

- `OS os`: Reference to the OS instance managing the scheduler.
    
- `LinkedList<Process> processes`: Queue of processes waiting to be scheduled.
    
- `int totalContextSwitches`: Counter for the number of context switches performed.
    

---

## Constructor

- `Scheduler(OS os)`
    
    - Initializes the scheduler with a reference to the OS and an empty process queue.
        

---

## Methods

- `void getNext()`
    
    - Wrapper for `getNext(false)`.
        
- `abstract void getNext(boolean cpuEmpty)`
    
    - Selects the next process based on the scheduler's strategy.
        
- `abstract void newProcess(boolean cpuEmpty)`
    
    - Defines behavior when a new process is added (used by preemptive schedulers).
        
- `abstract void IOReturningProcess(boolean cpuEmpty)`
    
    - Defines behavior when a process returns from IO (used by preemptive schedulers).
        
- `void addContextSwitch()`
    
    - Increments the context switch counter.
        
- `int getTotalContextSwitches()`
    
    - Returns the number of context switches performed.
        
- `boolean isEmpty()`
    
    - Returns `true` if the process list is empty.
        
- `Process tieBreaker(Process p1, Process p2)`
    
    - Resolves ties between two processes using the policy defined in `os.SCHEDULER_TIEBREAKER_TYPE`.
        
- `void addProcess(Process p)`
    
    - Adds a process to the scheduler and updates its state appropriately.
        
- `void update()`
    
    - Invokes `getNext` to possibly schedule a new process.
        
- `Process removeProcess(Process p)`
    
    - Removes and returns a specific process from the scheduler.
        
- `String toString()`
    
    - Returns a string representation of the processes in the scheduler.
        

---

## Tie-Breaker Policies

Handled via `tieBreaker(Process p1, Process p2)` and depend on the type set in `SCHEDULER_TIEBREAKER_TYPE`. Supported types:

- `LARGEST_PID`
    
- `SMALLEST_PID`
    
- `PRIORITY`
    
- `PRIORITY_LARGEST_PID`
    
- `PRIORITY_SMALLEST_PID`
    

Each of these policies determines how to resolve conflicts between two equally suitable processes.

---