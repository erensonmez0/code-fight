package codefight.model.memory;

import codefight.model.ai.Ai;
import codefight.model.aicommand.AiCommand;

import java.util.Random;

/**
 * This class represents the cyclic memory that Code Fight game is played on and saves the AI's commands and their
 * representations.
 *
 * @author Eren Soenmez
 */
public class Memory {
    private static final int DEFAULT_ARGUMENT_VALUE = 0;
    private static final int DEFAULT_STARTING_VALUE = 0;
    private static final int FIRST_INDEX = 0;
    private static final int DIFFERENCE_FROM_INDEX = 1;
    private static final int UPPER_DISPLAY_LIMIT = 10;
    private static final int SPACE_BETWEEN_COLON_AND_LAST_DIGIT = 1;
    private static final int SPACE_BETWEEN_PIPE_AND_LAST_DIGIT = 1;
    private static final int SPACE_UNTIL_COLON = 1;
    private static final String AI_COMMAND_SEPARATOR = " ";
    private static final String COLON_SEPARATOR = ":";
    private static final String PIPE_SEPARATOR = "|";
    private static final int BOMB_CONDITION = 0;
    private final int memorySize;
    private final String unprocessedCommand;
    private final String displayAreaBounds;
    private final String nextAisNextAiCommand;
    private final String otherAisNextAiCommand;
    private final MemoryCell[] memoryCells;
    private final String[] memoryDisplay;
    private final String[] displayWithoutNextCommands;

    /**
     * Constructs a new memory with a valid memory size, unprocessed command symbol, display area bound symbol, next
     * AI's next AI commands symbol, other AI's next AI commands symbol.
     *
     * @param memorySize the size of the memory.
     * @param unprocessedCommand the symbol of the unprocessed command.
     * @param displayAreaBounds the symbol of the display area bounds.
     * @param nextAisNextAiCommand the symbol of the next AI's next AI command.
     * @param otherAisNextAiCommand the symbol of the other AI's next AI command.
     */
    public Memory(int memorySize, String unprocessedCommand, String displayAreaBounds, String nextAisNextAiCommand,
                  String otherAisNextAiCommand) {
        this.memorySize = memorySize;
        this.memoryCells = new MemoryCell[memorySize];
        this.unprocessedCommand = unprocessedCommand;
        this.displayAreaBounds = displayAreaBounds;
        this.nextAisNextAiCommand = nextAisNextAiCommand;
        this.otherAisNextAiCommand = otherAisNextAiCommand;
        this.memoryDisplay = new String[memorySize];
        this.displayWithoutNextCommands = new String[memorySize];
        defaultFillMemoryDisplayCells();
    }

    /**
     * Returns the size of the memory.
     *
     * @return the memory size.
     */
    public int getMemorySize() {
        return memorySize;
    }

    /**
     * Returns the symbol of the next AI's next AI command.
     *
     * @return the next AI's next AI Command symbol.
     */
    public String getNextAisNextAiCommand() {
        return nextAisNextAiCommand;
    }

    /**
     * Sets the symbol of the next AI's next AI command.
     *
     * @param index the position in the memory to update.
     */
    public void setNextAisNextAiCommand(int index) {
        int cyclicIndex = index;
        if (index < FIRST_INDEX || index >= memorySize) {
            cyclicIndex = Math.floorMod(index, memorySize);
        }
        memoryDisplay[cyclicIndex] = nextAisNextAiCommand;
    }

    /**
     * Sets the symbol of the other AI's next AI command.
     *
     * @param index the position in the memory to update.
     */
    public void setOtherAisNextAiCommand(int index) {
        int cyclicIndex = index;
        if (index < FIRST_INDEX || index >= memorySize) {
            cyclicIndex = Math.floorMod(index, memorySize);
        }
        memoryDisplay[cyclicIndex] = otherAisNextAiCommand;
    }

    /**
     * Initializes the memory in a default way, with STOP commands and 0 arguments.
     */
    public void defaultInitializeMemory() {
        for (int i = 0; i < memorySize; i++) {
            memoryCells[i] = new MemoryCell(AiCommand.STOP, DEFAULT_ARGUMENT_VALUE, DEFAULT_ARGUMENT_VALUE);
        }
    }

    /**
     * Initializes the memory with random AI commands and arguments.
     *
     * @param seedNumber the seed for the random number generator.
     */
    public void randomInitializeMemory(int seedNumber) {
        Random random = new Random(seedNumber);
        for (int i = 0; i < memorySize; i++) {
            AiCommand[] aiCommands = AiCommand.values();
            int randomAiCommandsIndex = random.nextInt(aiCommands.length);
            AiCommand randomAiCommand = aiCommands[randomAiCommandsIndex];

            int randomArgumentA = random.nextInt();
            int randomArgumentB = random.nextInt();

            memoryCells[i] = new MemoryCell(randomAiCommand, randomArgumentA, randomArgumentB);
        }
    }

    /**
     * Fills the memory's representation with unprocessed commands.
     */
    public void defaultFillMemoryDisplayCells() {
        for (int i = 0; i < memorySize; i++) {
            memoryDisplay[i] = unprocessedCommand;
            displayWithoutNextCommands[i] = unprocessedCommand;
        }
    }

    /**
     * Returns the memory cell with the given index with a cyclic behaviour.
     *
     * @param index the index of the memory cell to return.
     * @return the memory cell with the given index.
     */
    public MemoryCell getCell(int index) {
        int cyclicIndex = index;
        if (index < FIRST_INDEX || index >= memorySize) {
            cyclicIndex = Math.floorMod(index, memorySize);
        }
        return memoryCells[cyclicIndex];
    }

    /**
     * Sets the memory cell with the given index to a new memory cell with a cyclic behaviour.
     *
     * @param index the index in the memory to update.
     * @param memoryCell the new memory cell to set the memory cell with the given index.
     */
    public void setCell(int index, MemoryCell memoryCell) {
        int cyclicIndex = index;
        if (index < FIRST_INDEX || index >= memorySize) {
            cyclicIndex = Math.floorMod(index, memorySize);
        }
        memoryCells[cyclicIndex] = memoryCell;
    }

    /**
     * Returns the memory cell's symbol from memory's representation with the given index.
     *
     * @param index the index of the memory cell whose symbol's going to be returned.
     * @return the symbol of the memory cell with the given index.
     */
    public String getMemoryDisplayCell(int index) {
        int cyclicIndex = index;
        if (index < FIRST_INDEX || index >= memorySize) {
            cyclicIndex = Math.floorMod(index, memorySize);
        }
        return memoryDisplay[cyclicIndex];
    }

    /**
     * Sets the memory cell's symbol with the given index to a new symbol.
     *
     * @param index the index of the memory cell that's going to be updated.
     * @param symbol the new symbol to set the memory cell's symbol.
     */
    public void setMemoryDisplayCell(int index, String symbol) {
        int cyclicIndex = index;
        if (index < FIRST_INDEX || index >= memorySize) {
            cyclicIndex = Math.floorMod(index, memorySize);
        }
        memoryDisplay[cyclicIndex] = symbol;
    }

    /**
     * Sets the memory cell's symbol with the given index to a new symbol without next AI commands.
     *
     * @param index the index of the memory cell that's going to be updated.
     * @param symbol the new symbol to set the memory cell's symbol.
     */
    public void setDisplayCellWithoutNextCommands(int index, String symbol) {
        int cyclicIndex = index;
        if (index < FIRST_INDEX || index >= memorySize) {
            cyclicIndex = Math.floorMod(index, memorySize);
        }
        displayWithoutNextCommands[cyclicIndex] = symbol;
    }

    /**
     * Clears the memory display from the next AI symbols.
     */
    public void clearCommandSymbols() {
        System.arraycopy(displayWithoutNextCommands, 0, memoryDisplay, 0, this.memorySize);
    }

    /**
     * Checks if an AI's command meets one of the conditions to be an AI-Bomb, returns true if it does.
     *
     * @param index the index in the memory to check.
     * @return True if an AI's command is an AI-Bomb, false otherwise.
     */
    private boolean isAiBomb(int index) {
        int cyclicIndex = index;
        if (index < FIRST_INDEX || index >= memorySize) {
            cyclicIndex = Math.floorMod(index, memorySize);
        }

        boolean isCommandStopBomb = memoryCells[cyclicIndex].aiCommand().equals(AiCommand.STOP);

        boolean isCommandJmpBomb = (memoryCells[cyclicIndex].aiCommand().equals(AiCommand.JMP)
                && memoryCells[cyclicIndex].argumentA() == BOMB_CONDITION);

        boolean isCommandJmzBomb = (memoryCells[cyclicIndex].aiCommand().equals(AiCommand.JMZ)
                && memoryCells[cyclicIndex].argumentA() == BOMB_CONDITION
                && memoryCells[cyclicIndex].argumentB() == BOMB_CONDITION);

        return isCommandStopBomb || isCommandJmpBomb || isCommandJmzBomb;

    }

    /**
     * Places an AI-Bomb symbol or an AI-Command symbol in the memory cell with the given index.
     *
     * @param index the index in the memory where the symbol is going to be placed.
     * @param ai the AI whose symbol is to be placed.
     */
    public void placeSymbol(int index, Ai ai) {
        int cyclicIndex = index;
        if (index < FIRST_INDEX || index >= memorySize) {
            cyclicIndex = Math.floorMod(index, memorySize);
        }
        if (isAiBomb(cyclicIndex)) {
            setMemoryDisplayCell(cyclicIndex, ai.getAiBombSymbol());
            setDisplayCellWithoutNextCommands(cyclicIndex, ai.getAiBombSymbol());
        } else {
            setMemoryDisplayCell(cyclicIndex, ai.getAiCommandSymbol());
            setDisplayCellWithoutNextCommands(cyclicIndex, ai.getAiCommandSymbol());
        }
    }

    @Override
    public String toString() {
        StringBuilder displayOfMemory = new StringBuilder();
        for (String cell : memoryDisplay) {
            displayOfMemory.append(cell);
        }
        return displayOfMemory.toString();
    }

    private String displayModifiedMemory(int index) {
        StringBuilder memoryWithAreaBounds = new StringBuilder();
        int endIndex = (index + UPPER_DISPLAY_LIMIT - DIFFERENCE_FROM_INDEX) % (this.memorySize);
        int endIndexSmall = (index + this.memorySize - DIFFERENCE_FROM_INDEX) % (this.memorySize);

        if (this.memorySize < UPPER_DISPLAY_LIMIT) {
            for (int i = 0; i < this.memorySize; i++) {
                if (i == index) {
                    memoryWithAreaBounds.append(this.displayAreaBounds);
                }

                memoryWithAreaBounds.append(memoryDisplay[i]);

                if (i == endIndexSmall) {
                    memoryWithAreaBounds.append(this.displayAreaBounds);
                }
            }
        } else {
            for (int i = 0; i < this.memorySize; i++) {
                if (i == index) {
                    memoryWithAreaBounds.append(this.displayAreaBounds);
                }

                memoryWithAreaBounds.append(memoryDisplay[i]);

                if (i == endIndex) {
                    memoryWithAreaBounds.append(this.displayAreaBounds);
                }
            }
        }

        return memoryWithAreaBounds.toString();
    }

    private String displayModifiedMemoryCells(int index) {
        StringBuilder modifiedMemoryDisplay = new StringBuilder();
        String lineSeparator = System.lineSeparator();
        int actualUpperLimit = Math.min(UPPER_DISPLAY_LIMIT, this.memorySize);

        // Maximum lengths for every value in the modified display gets calculated.
        int maxCommandLength = DEFAULT_STARTING_VALUE;
        int maxArgumentALength = DEFAULT_STARTING_VALUE;
        int maxArgumentBLength = DEFAULT_STARTING_VALUE;

        for (int i = 0; i < actualUpperLimit; i++) {
            int cyclicIndex = (index + i) % this.memorySize;
            MemoryCell cell = memoryCells[cyclicIndex];

            maxCommandLength = Math.max(maxCommandLength, cell.aiCommand().name().length());
            maxArgumentALength = Math.max(maxArgumentALength, String.valueOf(cell.argumentA()).length());
            maxArgumentBLength = Math.max(maxArgumentBLength, String.valueOf(cell.argumentB()).length());
        }

        int lastIndex = (index + actualUpperLimit - DIFFERENCE_FROM_INDEX) % this.memorySize;
        int maxIndexDisplayLength = String.valueOf(Math.max(index, lastIndex)).length();

        // A detailed display of memory cells gets built and returned.
        for (int i = index; i < index + actualUpperLimit; i++) {
            int cyclicIndex = i % this.memorySize;
            MemoryCell cell = memoryCells[cyclicIndex];

            String symbol = memoryDisplay[cyclicIndex];
            String command = cell.aiCommand().name();
            int argumentA = cell.argumentA();
            int argumentB = cell.argumentB();

            int spacesNeededUntilColon = SPACE_UNTIL_COLON + maxIndexDisplayLength - String.valueOf(cyclicIndex).length();
            int spacesNeededUntilFirstPipe = SPACE_BETWEEN_COLON_AND_LAST_DIGIT + maxCommandLength - command.length();
            int spacesNeededUntilSecondPipe = SPACE_BETWEEN_PIPE_AND_LAST_DIGIT + maxArgumentALength - String.valueOf(argumentA).length();
            int spacesNeededUntilEnd = SPACE_BETWEEN_PIPE_AND_LAST_DIGIT + maxArgumentBLength - String.valueOf(argumentB).length();

            modifiedMemoryDisplay.append(symbol);

            modifiedMemoryDisplay.append(AI_COMMAND_SEPARATOR.repeat(Math.max(0, spacesNeededUntilColon)));
            modifiedMemoryDisplay.append(cyclicIndex).append(COLON_SEPARATOR);

            modifiedMemoryDisplay.append(AI_COMMAND_SEPARATOR.repeat(Math.max(0, spacesNeededUntilFirstPipe)));
            modifiedMemoryDisplay.append(command).append(AI_COMMAND_SEPARATOR).append(PIPE_SEPARATOR);

            modifiedMemoryDisplay.append(AI_COMMAND_SEPARATOR.repeat(Math.max(0, spacesNeededUntilSecondPipe)));
            modifiedMemoryDisplay.append(argumentA).append(AI_COMMAND_SEPARATOR).append(PIPE_SEPARATOR);

            modifiedMemoryDisplay.append(AI_COMMAND_SEPARATOR.repeat(Math.max(0, spacesNeededUntilEnd)));
            modifiedMemoryDisplay.append(argumentB);

            if (i < index + actualUpperLimit - DIFFERENCE_FROM_INDEX) {
                modifiedMemoryDisplay.append(lineSeparator);
            }
        }
        return modifiedMemoryDisplay.toString();
    }

    /**
     * Returns a detailed visual representation of the memory with the help of the given index.
     *
     * @param index the index in the memory to show the detailed visual representation of the memory cells.
     * @return a string showing the modified representation of the memory and it's cells.
     */
    public String displayModifiedMemoryAndCells(int index) {
        StringBuilder modifiedMemoryAndCells = new StringBuilder();
        String lineSeparator = System.lineSeparator();

        modifiedMemoryAndCells.append(displayModifiedMemory(index));
        modifiedMemoryAndCells.append(lineSeparator);
        modifiedMemoryAndCells.append(displayModifiedMemoryCells(index));

        return modifiedMemoryAndCells.toString();
    }
}

