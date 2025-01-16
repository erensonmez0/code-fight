package codefight.model.ai;

import codefight.model.memory.MemoryCell;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an AI that can take part in Code Fight game.
 *
 * @author Eren Soenmez
 */
public class Ai {
    private static final int FIRST_INDEX = 0;
    private final String aiCommandSymbol;
    private final String aiBombSymbol;
    private final String aiName;
    private final List<MemoryCell> commands;
    private int currentPosition;
    private int counter = 0;
    private boolean isRunning = true;
    private boolean stopOutput = false;

    /**
     * Constructs a new AI with a valid command symbol, bomb symbol, name, list of commands and current position.
     *
     * @param aiCommandSymbol the command symbol of the AI.
     * @param aiBombSymbol    the bomb symbol of the AI.
     * @param aiName          the name of the AI.
     * @param commands        the list of AI commands that AI has.
     * @param currentPosition the current position that AI is in.
     */
    public Ai(String aiCommandSymbol, String aiBombSymbol, String aiName, List<MemoryCell> commands,
              int currentPosition) {
        this.aiCommandSymbol = aiCommandSymbol;
        this.aiBombSymbol = aiBombSymbol;
        this.aiName = aiName;
        this.commands = commands;
        this.currentPosition = currentPosition;
    }

    /**
     * Returns the symbol representing an AI Command.
     *
     * @return the command symbol of the AI.
     */
    public String getAiCommandSymbol() {
        return this.aiCommandSymbol;
    }

    /**
     * Returns the symbol representing an AI Bomb.
     *
     * @return the bomb symbol of the AI.
     */
    public String getAiBombSymbol() {
        return this.aiBombSymbol;
    }

    /**
     * Returns the name of the AI.
     *
     * @return the AI's name.
     */
    public String getAiName() {
        return this.aiName;
    }

    /**
     * Returns a list of commands of the AI.
     *
     * @return a list containing all commands of AI.
     */
    public List<MemoryCell> getCommands() {
        return new ArrayList<>(commands);
    }

    /**
     * Returns the current position of the AI.
     *
     * @param memorySize The size of the memory.
     * @return the current position that AI is in.
     */
    public int getCurrentPosition(int memorySize) {
        if (currentPosition < FIRST_INDEX) {
            currentPosition = currentPosition + memorySize;
        }
        return currentPosition;
    }

    /**
     * Increments the current position by the given index.
     *
     * @param index The amount to increment the current position.
     * @param memorySize The size of the memory.
     */
    public void incrementCurrentPosition(int index, int memorySize) {
        if (currentPosition + index < FIRST_INDEX || currentPosition + index >= memorySize) {
            currentPosition = (currentPosition + index) % memorySize;
        } else {
            currentPosition = currentPosition + index;
        }
    }

    /**
     * Stops the AI's execution.
     */
    public void stopAi() {
        this.isRunning = false;
    }

    /**
     * Checks if the AI has stopped.
     *
     * @return True if the AI is not running, false otherwise.
     */
    public boolean isStopped() {
        return !this.isRunning;
    }

    /**
     * Returns the number of executed commands by the AI.
     *
     * @return The number of executed commands.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Increments the command execution counter of the AI if that AI's still running.
     */
    public void incrementCounter() {
        if (isRunning) {
            this.counter++;
        }
    }

    /**
     * Checks if the "AI executed commands until stopping" message has already been issued.
     *
     * @return True if the message has been issued, false otherwise.
     */
    public boolean isStopOutput() {
        return stopOutput;
    }

    /**
     * Updates the status to indicate if the "AI executed commands until stopping" message has been issued.
     *
     * @param stopOutput True to indicate the message has already been issued, false otherwise.
     */
    public void setStopOutput(boolean stopOutput) {
        this.stopOutput = stopOutput;
    }
}

