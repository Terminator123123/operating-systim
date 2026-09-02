# `ProcessBurstList.java`

## Overview

Represents a list of CPU and I/O bursts for a process. Handles execution order, progression, and generation of bursts for simulations.

---

## Fields

- `private static final int BURSTS_SIMPLE_SIM`  
    Constant defining the fixed number of cycles used in simple burst simulation.
    
- `ArrayList<ProcessBurst> bursts`  
    List storing all the bursts (CPU and I/O) of the process.
    
- `Random r`  
    Random number generator used for generating random bursts.
    
- `int currentBurst`  
    Index of the currently active burst in the list.
    
- `boolean finished`  
    Flag indicating whether all bursts in the list have been completed.
    

---

## Constructors

- `ProcessBurstList()`  
    Initializes an empty burst list with a new random generator.
    
- `ProcessBurstList(ProcessBurstList b)`  
    Copy constructor that deep-copies another burst list.
    
- `ProcessBurstList(ArrayList<ProcessBurst> b)`  
    Constructs a burst list from an existing list of `ProcessBurst` objects.
    

---

## Methods

- `boolean isFinished()`  
    Returns whether the entire burst list has been completed.
    
- `boolean advanceBurst()`  
    Advances the current burst by one cycle. Moves to the next burst if the current one finishes.
    
- `void addBurst(ProcessBurst b)`  
    Adds a burst to the list, ensuring valid CPU/IO alternation and starting with a CPU burst.
    
- `private ArrayList<ProcessBurst> getList()`  
    Returns the internal list of bursts (used internally or by copy constructors).
    
- `int getRemainingTimeInCurrentBurst()`  
    Returns the number of cycles remaining in the current burst.
    
- `boolean isCurrentBurstCPU()`  
    Returns `true` if the current burst is of type CPU.
    
- `void generateSimpleBursts()`  
    Generates a simple sequence of 3 fixed-length bursts: CPU → IO → CPU.
    
- `void generateRandomBursts(int numCPUBursts, int maxCPUCycles, int maxIOCycles)`  
    Generates a randomized list of bursts alternating between CPU and IO, with constraints on maximum cycles.
    
- `int getTotalExecutionTime()`  
    Calculates and returns the total number of cycles across all bursts.
    
- `String toString()`  
    Returns a formatted string representation of the burst list and current burst index.