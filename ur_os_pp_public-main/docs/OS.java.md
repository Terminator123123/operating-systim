# File: OS.java 

## Overview

The `OS` class serves as the core operating system simulation controller. It handles process creation, CPU and I/O management, and inter-component communication through interrupts. It acts as a mediator between the `CPU`, `ReadyQueue` (RQ), and `IOQueue`, orchestrating how and when processes are moved between these components.

## Attributes

- **`rq`** (`ReadyQueue`): The ready queue for processes waiting for CPU time.
- **`ioq`** (`IOQueue`): The I/O queue managing processes doing I/O operations.
- **`process_count`** (`int`): Static counter used to assign unique process IDs.
- **`system`** (`SystemOS`): Reference to the main system, used to get current simulation time.
- **`cpu`** (`CPU`): Reference to the simulated CPU.
- **`SCHEDULER_TYPE`** (`SchedulerType`): Defines the type of scheduler in use. Default is `FCFS` (First-Come, First-Served).
- **`SCHEDULER_TIEBREAKER_TYPE`** (`TieBreakerType`): Defines tie-breaking strategy. Default is `LARGEST_PID`.

## Constructor

```java
public OS(SystemOS system, CPU cpu, IOQueue ioq)
```

Initializes the OS with references to the system, CPU, and IO queue. Sets up the ready queue.

## Methods

- **`public void update()`**  
    Calls `update()` on the ready queue to proceed with process scheduling logic.
    
- **`public boolean isCPUEmpty()`**  
    Returns whether the CPU currently has a running process.
    
- **`public Process getProcessInCPU()`**  
    Returns the process currently loaded in the CPU.
    
- **`public void interrupt(InterruptType t, Process p)`**  
    Handles interrupts in the system and moves processes based on the type of interrupt:
    
    - **`InterruptType.CPU`**: Finalizes or sends the finished CPU process to I/O.
        
    - **`InterruptType.IO`**: Returns I/O-finished process back to the ready queue.
        
    - **`InterruptType.SCHEDULER_CPU_TO_RQ`**: Preemptive CPU-to-RQ transfer.
        
    - **`InterruptType.SCHEDULER_RQ_TO_CPU`**: Loads a new process into the CPU.
        
- **`public void removeProcessFromCPU()`**  
    Removes the current process from the CPU (sets it to null).
    
- **`public void create_process()`**  
    Creates a new process with auto-generated PID and current system time, and adds it to the ready queue.
    
- **`public void create_process(Process p)`**  
    Adds a provided process to the ready queue after assigning it a unique PID.
    
- **`public void showProcesses()`**  
    Prints a list of all processes in the ready queue.


## Usage Example

```java
OS os = new OS(system, cpu, ioq);
os.create_process();
os.update();
```

## Notes

- The OS does not directly execute processes; instead, it coordinates their scheduling and movement between queues and CPU.
    
- Supports preemptive scheduling through interrupts.
    
- Simulation-specific settings (e.g., `FCFS`, `LARGEST_PID`) are hardcoded but could be extended for configurability.