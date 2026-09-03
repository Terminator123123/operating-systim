# Deliverable 2 — Technical Annex

**Universidad del Rosario · School of Science and Engineering · Operating Systems**
UR-OS — Process Planning

Students: Carlos Arturo Galvis Mojica · Shalem Shaged · Jesús Esteban Peña Ávila

---

## Notice on the use of artificial intelligence

**This annex was written with the assistance of an artificial intelligence tool.** It exists as a
quick technical summary of what had to be implemented for the second deliverable and of the
design decisions that were taken, so that the whole team shares the same reference before the
presentation.

The assistance of the AI tool was used to **refine and complete a working draft** of the
multilevel schedulers: the structure of the simulator, the scheduling policies to be applied and
the criteria for each queue were defined by the team beforehand, and the tool was used to tighten
that draft into its final form and to document it. The formal report, the analysis of the results
and the presentation remain the work of the team.

---

## 1. Scope of the second deliverable

| Requirement | Status |
| --- | --- |
| Priority Queue (multiple queues) | Implemented — `PriorityQueue.java` |
| Multilevel Feedback Queue (MFQ) | Implemented — `MFQ.java` |
| Extra scheduler | Not applicable — required only for groups of four members |
| Report | Extends the Deliverable 1 report |
| Presentation in English (10 min) | Pending |

The three schedulers of the first deliverable (SJF-NP, SJF-P and Round Robin) were left
untouched. This was verified by rebuilding the version of the project as it stood before this
work and running both scenarios again: every indicator matches exactly, so the numbers reported
in Deliverable 1 remain valid.

---

## 2. What was implemented

### 2.1 `PriorityQueue.java` — multilevel queue with fixed priorities

The scheduler holds one sub-scheduler per priority level, index 0 being the highest priority. The
simulator builds it with four Round Robin queues whose quanta are 9, 6, 3 and 2 cycles
(`ReadyQueue.java`).

- **`addProcess(Process p)`** routes every process to the queue that matches its priority and
  records that level in the process through `setCurrentScheduler()`. The parent's own process
  list is never used: the processes live inside the sub-schedulers.
- **`defineCurrentScheduler()`** returns the highest priority queue that has at least one process
  waiting, or `-1` when every queue is empty.
- **`getNext(boolean cpuEmpty)`** serves the highest non-empty queue when the CPU is free. When
  the CPU is busy, it advances the quantum counter of the level that owns the running process and
  only reconsiders the CPU when that quantum expires.

**Preemption policy and its justification.** A running process is *not* expelled the moment a
higher priority process arrives; it is allowed to finish its current quantum, and the priority
comparison happens when the quantum expires. Every level is already a Round Robin, so the CPU is
reevaluated periodically anyway, and expelling on every arrival would add context switches
without changing the order in which the queues are served. The cost is bounded: a high priority
process waits at most one quantum of the level currently running.

When the quantum expires, the process returns to the CPU only if the best waiting candidate
belongs to a queue of equal or higher priority. If the only processes waiting are of lower
priority, the running process keeps the CPU — this is what fixed priority scheduling means, and
it is also the source of the starvation observed in the results.

### 2.2 `MFQ.java` — multilevel feedback queue

Same structure, but the level of a process is feedback obtained from its behaviour instead of a
fixed attribute. The simulator builds it with Round Robin (q = 3), Round Robin (q = 6) and FCFS.

1. Every new process enters at level 0, the level with the shortest quantum.
2. A process that exhausts its quantum while other processes are waiting is **demoted** one level.
   The more CPU a process consumes without releasing it, the longer the quantum it receives and
   the lower its priority becomes.
3. A process returning from an I/O burst is **promoted** one level, never above level 0. It
   released the CPU voluntarily, so it is treated as I/O bound and rewarded with a higher
   priority and a shorter quantum. This rule also acts as an aging mechanism and is what prevents
   the permanent starvation that the fixed priority scheduler suffers.
4. The last level is FCFS and therefore has no quantum: a process that reaches it runs until its
   current CPU burst ends and cannot be demoted further.

A process that exhausts its quantum while no other process is waiting keeps the CPU and keeps its
level, since demoting it would only distort the indicators with no competitor to hand the CPU to.

### 2.3 `RoundRobin.java` — support for multiqueue operation

The `multiqueue` field already existed in the class but was never used. It now has a meaning: a
Round Robin that is one level of a multilevel scheduler must not expel the running process on its
own, because it can only choose a replacement from its own level, which would break the priority
policy between queues.

The method `advanceQuantum()` was added for this. It advances the quantum counter of the process
in the CPU and reports whether the quantum has just expired, without touching the CPU, leaving
the decision of which process runs next to the parent scheduler. The behaviour of the standalone
Round Robin of Deliverable 1 is unchanged.

### 2.4 Secondary corrections

- `ReadyQueue.java`: the `FAIR` branch assigned no scheduler, leaving `s` as `null` and throwing a
  `NullPointerException` if option 7 of the menu was selected. It now falls back to FCFS.
- `SystemOS.java`: the final call to `compareFiles()` used absolute paths pointing at a specific
  machine (`C:\Users\jesus\...`), which failed on any other computer. It now uses the file
  generated by the run itself.

---

## 3. Results

Both scenarios included in the simulator were executed. The first three rows reproduce
Deliverable 1 and are shown for comparison.

### 3.1 Simpler scenario

| Algorithm | Cycles | CPU Util. | Throughput | Avg. Turnaround | Avg. Waiting | CS (Gantt) | CS (complete) | Avg. Response |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| FCFS | 40 | 1.0000 | 0.1000 | 26.75 | 13.00 | 2.00 | 1.75 | 3.00 |
| SJF-NP | 43 | 0.9302 | 0.0930 | 22.75 | 9.00 | 2.00 | 1.75 | 6.25 |
| SJF-P | 43 | 0.9302 | 0.0930 | 22.75 | 9.00 | 2.25 | 2.75 | 5.50 |
| RR (q=4) | 40 | 1.0000 | 0.1000 | 29.50 | 15.75 | 3.25 | 4.25 | 2.00 |
| **PRIORITY** | **40** | **1.0000** | **0.1000** | **22.75** | **9.00** | **2.25** | **2.25** | **5.25** |
| **MFQ** | **40** | **1.0000** | **0.1000** | **28.75** | **15.00** | **3.50** | **4.75** | **0.50** |

### 3.2 Simpler2 scenario

| Algorithm | Cycles | CPU Util. | Throughput | Avg. Turnaround | Avg. Waiting | CS (Gantt) | CS (complete) | Avg. Response |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| FCFS | 108 | 1.0000 | 0.0370 | 81.25 | 47.50 | 2.00 | 1.75 | 13.75 |
| SJF-NP | 108 | 1.0000 | 0.0370 | 76.75 | 43.00 | 2.00 | 1.75 | 13.50 |
| SJF-P | 108 | 1.0000 | 0.0370 | 75.75 | 42.00 | 2.25 | 3.50 | 3.75 |
| RR (q=4) | 108 | 1.0000 | 0.0370 | 87.75 | 54.00 | 7.25 | 12.25 | 4.00 |
| **PRIORITY** | **110** | **0.9818** | **0.0364** | **73.00** | **39.25** | **3.25** | **4.25** | **29.75** |
| **MFQ** | **108** | **1.0000** | **0.0370** | **84.75** | **51.00** | **4.75** | **7.50** | **0.50** |

### 3.3 Reading of the results

**PRIORITY obtains the best turnaround and waiting times of every algorithm tested in Simpler2**
(73.00 and 39.25). This is not a coincidence: in that scenario the two processes with priority 0
are also the ones that arrive first, so serving them to completion before touching the priority 1
queue behaves almost like a non-preemptive policy applied to the right processes.

**That same behaviour produces its worst indicator: a response time of 29.75**, by far the highest
of the whole comparison. Processes 2 and 3, of priority 1, wait for the two priority 0 processes
to finish their first CPU burst and their I/O burst before touching the CPU for the first time.
It also explains why this is the only configuration that does not reach 100% CPU utilization and
needs 110 cycles instead of 108: the Gantt chart shows a two cycle idle stretch while the ready
processes available at that moment belong to a queue the policy is not serving. This is textbook starvation of
low priority processes, and it is the reason feedback between queues exists.

**MFQ produces the opposite profile: a response time of 0.50 in both scenarios**, the best of every
algorithm tested. Since every new process enters at level 0 with a quantum of three cycles, all
four processes reach the CPU within the first cycles of the simulation. The Gantt chart of
Simpler2 shows this clearly: the four processes rotate in blocks of three cycles at the beginning
and only then start to spread out as the CPU bound ones are demoted.

**MFQ pays for that responsiveness in turnaround, waiting time and overhead** (84.75, 51.00 and
7.50 context switches in Simpler2, against 73.00, 39.25 and 4.25 for PRIORITY). Rotating every
process through the short quantum of level 0 fragments the execution and lengthens the time each
process spends in the system. It is still better than plain Round Robin on the same scenario
(87.75 turnaround and 12.25 context switches), because the demotion rule moves CPU bound processes
to levels with longer quanta and eventually to FCFS, which stops the rotation from continuing
indefinitely.

The comparison between the two therefore reduces to the trade-off the two algorithms were designed
around: fixed priorities give excellent throughput for the processes that matter at the cost of
starving the rest, while feedback gives every process a fast first response and guarantees
progress, at the cost of a higher scheduling overhead.

---

## 4. Note on the Deliverable 1 tables

While rebuilding the previous version to check for regressions, three cells of the "Context
Complete" column in the Deliverable 1 report were found not to match what the code produces:

| Scenario | Algorithm | Reported | Produced by the code |
| --- | --- | --- | --- |
| Simpler | SJF-NP | 2.00 | 1.75 |
| Simpler | RR | 3.25 | 4.25 |
| Simpler2 | RR | 7.50 | 12.25 |

Every other value in both tables matches. This is independent of the work done for this
deliverable — the original version of the code produces exactly the same numbers — so it is worth
correcting in the second report rather than carrying it forward.

---

## 5. How to build and run

The project is an Ant based NetBeans project (`build.xml` + `nbproject/`). The `.iml` file and the
`out/` directory belong to IntelliJ and are not used by NetBeans.

1. Open NetBeans, then `File > Open Project…` and select the `ur_os_pp_public-main` folder.
2. `Clean and Build` (F11), then `Run` (F6). The main class `ur_os.UR_OS` is already configured.
3. The console asks for the scheduler (1 FCFS, 2 SJF-NP, 3 SJF-P, 4 RR, 5 PRIORITY, 6 MFQ) and
   then for the scenario (2 = Simpler, 3 = Simpler2).
4. Each run writes a `<SCHEDULER>.txt` file with the Gantt chart and the indicators in the
   project directory.

The project targets Java 17 (`javac.source=17`) and builds without warnings other than the
pre-existing unchecked-operation notices.
