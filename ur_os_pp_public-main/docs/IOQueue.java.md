# `IOQueue` Class Documentation

The `IOQueue` class handles I/O-bound processes in an operating system simulation. It manages processes waiting for I/O completion and handles their state transitions.

---

## Fields

- `OS os`  
    A reference to the operating system that owns this queue.
    
- `ArrayList<Process> processes`  
    List of processes currently in the I/O queue.
    

---

## Constructors

- `IOQueue()`  
    Initializes an empty I/O queue without a reference to the operating system.
    
- `IOQueue(OS os)`  
    Initializes an empty I/O queue and associates it with the given OS.
    

---

## Methods

- `void setOS(OS os)`  
    Assigns an operating system instance to the I/O queue.
    
- `void addProcess(Process p)`  
    Adds a process to the I/O queue and sets its state to `NEW_IO`.
    
- `void update()`  
    Iterates over the I/O queue, updates burst progress for each process:
    
    - If a process is no longer in `NEW_IO`, it attempts to advance its burst.
        
    - If the burst finishes, the process is removed and the OS is notified via an I/O interrupt.
        
    - If a process is still in `NEW_IO`, its state is updated to `IO`.
        
- `void removeProcess(Process p)`  
    Removes the specified process from the I/O queue.
    
- `String toString()`  
    Returns a string representation of all processes in the I/O queue. Returns "IO: Empty" if the queue is empty.
    

---

## Notes

- The queue uses a simple `ArrayList` for storage and relies on process state and burst information to control flow.
    
- I/O handling relies on the `advanceBurst()` method of `Process`, which determines when a process has completed its I/O operation.
    

---