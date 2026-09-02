# File: ProcessBurst.java

## Overview

The `ProcessBurst` class represents a single burst of activity in a process's lifecycle. A burst can either be of type CPU or IO and is characterized by the number of cycles it requires to complete.

## Attributes

- `int cycles`: Total number of cycles the burst requires.
- `int remainingCycles`: Number of cycles left for the burst to finish.
- `ProcessBurstType type`: Type of burst (`CPU` or `IO`).
- `boolean finished`: Indicates whether the burst has finished execution.

## Constructors

- `ProcessBurst(int cycles, ProcessBurstType type)` Creates a burst with the given number of cycles and type.
- `ProcessBurst(ProcessBurst p)` Copy constructor that duplicates another `ProcessBurst`.

## Methods

- `int getCycles()` Returns the total number of cycles for the burst.
- `void setCycles(int cycles)` Sets the total number of cycles.       
- `ProcessBurstType getType()` Returns the type of the burst.
- `void setType(ProcessBurstType type) Sets the type of the burst.
- `boolean advanceBurst()` Decrements the remaining cycles. Returns `true` if the burst finishes.
- `int getRemainingCycles()` Returns how many cycles remain to complete the burst.
- `boolean isFinishes()` Returns whether the burst is finished.
- `String toString()` Returns a string representation of the burst in the format:

```
C: [total cycles] RC: [remaining cycles] T: [type] F: [finished flag]
```


## Notes

- This class is commonly used inside `ProcessBurstList` to model a sequence of CPU and IO bursts for a process.
    
- The `advanceBurst()` method should be called to simulate the progression of time during execution.