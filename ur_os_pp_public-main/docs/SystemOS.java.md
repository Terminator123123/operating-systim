
# File: `SystemOS.java`

## Purpose

`SystemOS` is the **core simulation engine** of the UR-OS simulator. It emulates the behavior of an operating system managing processes over time, including CPU scheduling, I/O operations, and process state transitions. It also gathers and prints performance metrics at the end of the simulation.

This class implements the `Runnable` interface so it can be executed in a separate thread, as started from `UR_OS.java`.

---

## Class Declaration

```java
public class SystemOS implements Runnable
```

---

## Constants

- `MAX_SIM_CYCLES = 1000`: Maximum number of simulation steps (clocks).
- `MAX_SIM_PROC_CREATION_TIME = 50`: Max simulation time allowed to create new processes.
- `PROB_PROC_CREATION = 0.1`: Probability of creating a process at each step in random generation mode.

---

## Fields

- `clock`: Global simulation clock.
- `Random r`: Random generator (seeded with 1235).
- `CPU cpu`: Simulated CPU component.
- `IOQueue ioq`: Simulated I/O component.
- `OS os`: Operating system manager.
- `ArrayList<Process> processes`: All processes scheduled for the simulation.
- `ArrayList<Integer> execution`: Stores the PID of the process executed at each clock tick.

---

## Constructor

```java
public SystemOS()
```

Initializes the OS environment with default components:
- Instantiates `CPU`, `IOQueue`, and `OS`.
- Connects CPU and IO components to the OS.
- Calls one of the `initSimulationQueue*` methods to preload processes (currently `initSimulationQueueSimpler2()`).
- Displays the list of loaded processes with `showProcesses()`.

---


## Simulation Initialization Methods

* `initSimulationQueue()`:
  Uses randomness to decide process creation based on a probability threshold.

* `initSimulationQueueSimple()`:
  Creates a process every 4 time units with static parameters.

* `initSimulationQueueSimpler()`:
  Manually adds 4 processes with hardcoded CPU and I/O burst sequences and varying priorities.

* `initSimulationQueueSimpler2()`:
  Similar to `Simpler`, but with different burst durations and initialization times.

---

## Simulation Control

### `run()`

Main simulation loop. Executes up to `MAX_SIM_CYCLES` or until all processes finish.

Steps in each cycle:
1. Logs current clock cycle.
2. Adds new processes scheduled for the current time.
3. Updates OS, CPU, and IO state.
4. Records execution trace.
5. Checks for simulation end.

After simulation ends:
- Prints execution trace.
- Outputs unimplemented performance metrics.

---

## Helper Methods

* `isSimulationFinished()`:
  Checks if all processes in the system have finished.

* `getProcessAtI(int i)`:
  Returns all processes scheduled to start at time `i`.

* `showProcesses()`:
  Prints the list of all processes and their configurations.

---

## Performance Metrics

The following methods are placeholders; currently, they return zero or dummy values:

- `calcCPUUtilization()`
- `calcTurnaroundTime()`
- `calcThroughput()`
- `calcAvgWaitingTime()`
- `calcAvgContextSwitches()`
- `calcAvgContextSwitches2()`
- `calcResponseTime()`

---

## Console Output Example

```text
******SIMULATION START******
******Clock: 0******
...
******SIMULATION FINISHES******
******Process Execution******
0 1 2 2 3 ...
******Performance Indicators******
Total execution cycles: 87
CPU Utilization: 0.0
Throughput: 0.0
...
```

---

## Related Components

- `Process.java`: Describes a process and its state.
- `CPU.java`: Simulates execution of CPU bursts.
- `IOQueue.java`: Manages I/O-bound processes.
- `OS.java`: Central controller, interacts with CPU, IO, and Scheduler.
- `ProcessBurst.java`, `ProcessBurstType.java`: Used to define execution cycles (CPU/IO) per process.
