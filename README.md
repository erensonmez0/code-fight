# Code Fight

Code Fight is a turn-based game inspired by the classic Core War, designed for at least two AI programs to compete in a shared memory space. The objective is for each AI to manipulate the shared memory to cause other AIs to stop execution, with the last remaining AI declared the winner.

---

## Table of Contents

- [Overview](#overview)
- [Game Features](#game-features)
- [Setup](#setup)
- [Usage](#usage)
    - [Commands](#commands)
- [Examples](#examples)
- [Development Notes](#development-notes)
- [Testing](#testing)
- [License](#license)

---

## Overview

This program simulates the execution of multiple AI programs in a shared memory space. Each program consists of commands that can read, write, or manipulate the shared memory. The memory is circular, and the game ends when only one AI remains operational.

Key goals include:
- Understanding AI interaction in constrained environments.
- Demonstrating algorithmic efficiency.
- Exploring memory manipulation and state control.

---

## Game Features

1. **Memory Management**: A circular memory space where AIs compete.
2. **AI Commands**:
    - STOP: Ends the AI's execution.
    - MOV_R, MOV_I: Copy memory values.
    - ADD, ADD_R: Perform arithmetic operations.
    - JMP, JMZ: Jump to specific memory cells.
    - CMP: Compare memory values.
    - SWAP: Swap memory values.
3. **Game Phases**:
    - Initialization: Memory setup and AI loading.
    - Execution: Sequential command execution for all AIs.
4. **Customizable Simulation**: Input parameters control memory size, AI definitions, and initialization modes.

---

## Setup

1. **Prerequisites**: Ensure you have Java SE 17 installed.
2. **Compilation**:
   ```bash
   javac *.java
---
## Execution

To execute the program, use the following command:

   ```bash
    java CodeFight [memory_size] [symbols...]
   ```
**Symbols:** Custom symbols representing memory states and AI types must be provided during execution.

---

## Usage

## Commands

Once the program is running, the following commands are available:

- **add-ai [name] [commands]:**  Registers a new AI with a series of commands.
- **remove-ai [name]:** Removes an existing AI.
- **set-init-mode [mode] [seed]:** Sets the memory initialization mode (INIT_MODE_RANDOM or INIT_MODE_STOP).
- **start-game [AI_names...]:** Begins the game with specified AIs.
- **next [steps]:** Executes commands for the specified number of steps.
- **show-memory:** Displays the current memory state.
- **show-ai [name]:** Displays the state of a specific AI.
- **end-game:** Ends the game and shows the status of all AIs.
- **quit:** Exits the program.

---

## Examples

**Start a Game**


```bash
java CodeFight 1337 # ? _ ^ G g B b
   ```

**Add AIs**

```bash
> add-ai Sleepy ADD,10,-1,MOV_I,2,-1,JMP,-2,0,STOP,13,37
> add-ai Dwarf ADD_R,4,3,MOV_I,2,2,JMP,-2,0,STOP,0,0
   ```

**Show Memory**

```bash
> show-memory
GG_G###g########BB^B######b#####
   ```

**Run Game**

```bash
> start-game Sleepy Dwarf
Game started.
> next 50
Sleepy executed 12 steps until stopping.
Dwarf executed 24 steps until stopping.

   ```
---

## Development Notes

- **Memory Initialization:** Implemented with **Random** for random initialization.
- **Command Execution:** Circular memory ensures wrap-around for commands.
- **Error Handling:** Strict checks on input formats and argument validity.

---

## Testing

To test the program, use the following command:
```bash
java CodeFightTest
   ```

---

## License

This project is developed as part of a coursework assignment at KIT. Redistribution or plagiarism is prohibited.
