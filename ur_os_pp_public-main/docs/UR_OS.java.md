# File: `UR_OS.java`

## Purpose

This class serves as the **entry point** for launching the UR-OS simulator. It initializes the core system object (`SystemOS`) and starts it in a separate thread. The class also displays version information and a startup message in the console.

## Version

Current version: `0.0.3.8.4`

## Class Declaration

```java
public class UR_OS
```
## Fields

```java
private static String VERSION = "0.0.3.8.4";
```


Stores the current version of the simulator as a static constant.

## Methods

### `public static void main(String[] args)`

Entry point for the Java application. Executes the following actions:

1. Prints a header and the current version to the console.
    
2. Creates an instance of `SystemOS`, which represents the simulated operating system.
    
3. Starts the `SystemOS` instance in a new thread.
    

## Related Components

- `SystemOS.java`: Contains the core logic and main simulation loop for the operating system.
    
- `Thread`: Java's built-in threading class, used to run `SystemOS` concurrently.

