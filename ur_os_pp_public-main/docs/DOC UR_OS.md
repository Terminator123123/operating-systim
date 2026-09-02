# UR-OS Process Scheduling Simulator in Java

## Overview

The UR-OS simulator is a Java-based application designed to include all the main active components of a computer system in order to replicate the behavior of an operating system and its interaction with hardware elements such as the processor, memory, and file system.

This modular simulator allows users to select and combine different algorithms for each function. The current version, **0.0.3.8**, focuses on the **process scheduling** function. It currently implements the **First Come First Served (FCFS)** scheduling algorithm only. Performance indicators are not included in this version, as they were part of class projects.

This simulator was developed by **Pedro Wightman**, Associate Professor at the **School of Engineering, Science and Technology**, **Universidad del Rosario**, Bogotá, Colombia.

For access to the complete source code, faculty and researchers may request it via email:  
 pedro.wightman@urosario.edu.co 

> **Important**: Any use or application of the code must provide proper credit for authorship.

---

## Project Environment

- **Project type**: NetBeans 20
- **Java version**: Compiled with Java 17
- **Dependencies**: No external libraries used, only the standard JSE SDK package

---

## Project Files

### Core System
- `UR_OS.java` Main class for the execution of the simulation.
- `OS.java`: Class simulating the operating system and coordinating execution.
- `SystemOS.java`: Utility and configuration settings for the simulator.

### CPU and States
- `CPU.java`: Simulates the processor executing the processes.
- `ProcessState.java`: Enum representing process states (Ready, Running, etc.).
- `InterruptType.java`: Enum for types of process-related interrupts.

### Processes and Bursts
- `Process.java`: Defines a process and its associated burst list.
- `ProcessBurst.java`: Single CPU or I/O burst.
- `ProcessBurstList.java`: List of bursts for a process.
- `ProcessBurstType.java`: Enum indicating burst type (CPU or I/O).

### Scheduling Algorithms
- `Scheduler.java`: Abstract base class for scheduling algorithms.
- `FCFS.java`: Implements First Come First Served.
- `RoundRobin.java`: Implements Round Robin.
- `SJF_NP.java`: Non-preemptive Shortest Job First.
- `SJF_P.java`: Preemptive Shortest Job First.
- `MFQ.java`: Multi-Level Feedback Queue.
- `UR_OS.java`: Custom scheduling algorithm (User-Responsive OS).
- `SchedulerType.java`: Enum for available scheduling strategies.

### Queues and Data Structures
- `ReadyQueue.java`: Queue of ready-to-execute processes.
- `IOQueue.java`: Queue for I/O-bound processes.
- `PriorityQueue.java`: Priority-based queue.
- `TieBreakerType.java`: Enum for resolving ties between processes.

### Testing
- `PruebaArray.java`: Testing class for validating system behavior.

