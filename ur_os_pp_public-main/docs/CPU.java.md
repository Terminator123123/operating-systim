# Documentation: `CPU.java`

## Overview

The `CPU` class simulates the behavior of a central processing unit (CPU) in a simplified operating system simulation. It maintains a reference to the process currently executing, handles burst execution, and communicates with the operating system through interrupts.

## Attributes

- `Process p`: The process currently being executed by the CPU. `null` if no process is running.
    
- `OS os`: A reference to the `OS` object that manages this CPU. Used to trigger interrupts upon burst completion or preemption.
    

## Constructors

- `CPU()`: Initializes the CPU without associating it with an OS.
    
- `CPU(OS os)`: Initializes the CPU and sets the associated operating system.
    

## Methods

- `void setOS(OS os)` Sets the operating system associated with the CPU.
- `void addProcess(Process p) Loads a process into the CPU for execution and updates its state to `CPU`.
-  `Process getProcess()` Returns the current process in the CPU. Returns `null` if the CPU is empty.
- `boolean isEmpty()` Checks whether the CPU is currently idle (i.e., no process is being executed).
- `void update()` Advances the CPU burst if a process is currently loaded. Delegates to `advanceBurst()`.
- `void advanceBurst()` Advances the current burst of the loaded process. If the burst completes, the CPU is cleared and an interrupt is raised to notify the OS (`InterruptType.CPU`).
-  `void removeProcess()` Removes the current process from the CPU (sets it to `null`).
-  `Process extractProcess()` Returns the current process and removes it from the CPU in one step.
-  `String toString()` Returns a string representing the state of the CPU, indicating either the current process or that it is empty.

## Usage Context

The `CPU` class is an integral part of the simulation engine in the `SystemOS` framework. It collaborates with the `OS`, `ReadyQueue`, and `IOQueue` to model the scheduling and execution of processes. It is updated on each simulation cycle and plays a key role in burst execution and process lifecycle management.

## Example Use

The CPU is manipulated primarily by the `OS` and `SystemOS` classes:

```java
cpu.addProcess(p); // Load process
cpu.update();      // Execute one time unit
Process finished = cpu.extractProcess(); // Preempt or complete
```

## Remarks

- The CPU can only execute one process at a time.
    
- It relies on the `OS` to handle what happens after a burst finishes.
    
- Interrupts ensure the modular interaction between CPU and OS logic.
    

---