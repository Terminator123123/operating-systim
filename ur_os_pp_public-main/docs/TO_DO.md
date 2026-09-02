# ✅ Project Checklist

## 🧠 Core System
- [x] `UR_OS.java`: Clase principal que ejecuta la simulación.
- [x] `OS.java`: Simula el sistema operativo y coordina la ejecución.
- [x] `SystemOS.java`: Contiene utilidades y configuraciones del simulador.

## 🖥️ CPU and States
- [x] `CPU.java`: Simulación del procesador que ejecuta procesos.
- [ ] `ProcessState.java`: Enum con los estados del proceso (Ready, Running, etc.).
- [ ] `InterruptType.java`: Enum con tipos de interrupciones relacionadas con procesos.

## 🧩 Processes and Bursts
- [x] `Process.java`: Define un proceso y su lista de ráfagas asociadas.
- [x] `ProcessBurst.java`: Representa una ráfaga de CPU o I/O.
- [x] `ProcessBurstList.java`: Lista de ráfagas de un proceso.
- [ ] `ProcessBurstType.java`: Enum para distinguir entre ráfaga de CPU o I/O.

## 🧮 Scheduling Algorithms
- [x] `Scheduler.java`: Clase base abstracta para algoritmos de planificación.
- [x] `FCFS.java`: Implementación de First Come First Served.
- [ ] `RoundRobin.java`: Implementación de Round Robin.
- [ ] `SJF_NP.java`: Implementación de Shortest Job First no-preemptivo.
- [ ] `SJF_P.java`: Implementación de Shortest Job First preemptivo.
- [ ] `MFQ.java`: Implementación de Multi-Level Feedback Queue.
- [ ] `SchedulerType.java`: Enum para los tipos de planificación disponibles.

## 📚 Queues and Data Structures
- [x] `ReadyQueue.java`: Cola de procesos listos para ejecutar.
- [x] `IOQueue.java`: Cola de procesos en espera por I/O.
- [x] `PriorityQueue.java`: Cola basada en prioridades.
- [ ] `TieBreakerType.java`: Enum para resolución de empates entre procesos.

## 🧪 Testing
- [ ] `PruebaArray.java`: Clase de pruebas para validar el comportamiento del sistema.
