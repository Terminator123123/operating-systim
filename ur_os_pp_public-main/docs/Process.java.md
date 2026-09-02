# File: `Process.java`
## Overview

The `Process` class models a process within the UR-OS simulator. It contains all relevant attributes and behaviors associated with a process, such as its lifecycle, scheduling parameters, and bursts of CPU and IO activity.

---

## Fields

- `public static final int NUM_CPU_CYCLES`: Default number of CPU bursts.
- `public static final int MAX_CPU_CYCLES`: Maximum length of a CPU burst.
- `public static final int MAX_IO_CYCLES`: Maximum length of an IO burst.
- `int pid`: Process identifier.
- `int time_init`: Clock cycle at which the process is created.
- `int time_finished`: Clock cycle at which the process finishes execution.
- `ProcessBurstList pbl`: Sequence of CPU and IO bursts the process will go through.
- `ProcessState state`: Current state of the process (e.g., NEW, READY, RUNNING, etc.).
- `int currentScheduler`: Identifier for the current scheduler managing the process.
- `int priority`: Priority value of the process.
- `Random r`: Random number generator used for burst generation.

---

## Constructors

- `Process()`: Creates a process with randomized burst structure and default attributes.
- `Process(boolean auto)`: Creates a process optionally generating random bursts and priority.
- `Process(int pid, int time_init)`: Creates a process with given ID and initial time.
- `Process(Process p)`: Copy constructor.

---

## Methods

- `boolean advanceBurst()`: Advances to the next burst.
- `boolean isFinished()`: Checks whether the process has completed all its bursts.
- `void setTime_finished(int time_finished)`: Sets the finish time.
- `void addBurst(ProcessBurst pb)`: Adds a burst to the burst list.
- `int getPid()`, `void setPid(int pid)`: Getter and setter for process ID.
- `int getTime_init()`, `void setTime_init(int time_init)`: Getter and setter for initial time.
- `int getTime_finished()`: Returns the time the process finished.
- `ProcessBurstList getPBL()`: Gets the burst list.
- `void setPbl(ProcessBurstList pbl)`: Sets the burst list.
- `ProcessState getState()`, `void setState(ProcessState state)`: Gets and sets the state of the process.
- `int getTotalExecutionTime()`: Returns the total duration of all bursts.
- `int getRemainingTimeInCurrentBurst()`: Time left in the current burst.
- `boolean isCurrentBurstCPU()`: Checks if the current burst is a CPU burst.
- `int getCurrentScheduler()`, `void setCurrentScheduler(int currentScheduler)`: Gets or sets the scheduler ID.
- `int getPriority()`, `void setPriority(int priority)`: Gets or sets the priority of the process.
- `String toString()`: Returns a string representation of the process.
- `int compareTo(Object o)`: Compares two processes by PID.
- `boolean equals(Object o)`: Checks if two processes are equal by PID.

---

## Notes

- The `ProcessBurstList` and `ProcessBurst` classes are used to store and manage the sequence of CPU and IO bursts.
- Processes start in the `NEW` state and are managed by the OS and scheduler throughout their lifecycle.

