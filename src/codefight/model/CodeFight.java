package codefight.model;

import codefight.model.ai.Ai;
import codefight.model.ai.BaseAiCommands;
import codefight.model.aicommand.AiCommand;
import codefight.model.memory.InitializationMode;
import codefight.model.memory.Memory;
import codefight.model.memory.MemoryCell;
import codefight.model.phase.Phase;
import codefight.model.phase.PhaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the facade of Code Fight.
 *
 * @author Eren Soenmez
 */
public class CodeFight {
    private static final int AI_COMMAND_INDEX = 0;
    private static final int DEFAULT_VALUE = 0;
    private static final int COUNTER_START = 0;
    private static final int ARGUMENT_A_INDEX = 1;
    private static final int ARGUMENT_B_INDEX = 2;
    private static final int COMMAND_LENGTH_WITH_ARGUMENTS = 3;
    private static final String STOP_COMMAND = "STOP";
    private static final String AI_COMMANDS_SEPARATOR = ",";
    private static final String INIT_MODE_SEPARATOR = " ";
    private final Memory memory;
    private final List<String> aiSymbols;
    private final PhaseHandler phaseHandler;
    private final List<Ai> stoppedAisList = new ArrayList<>();
    private String currentInitMode;
    private int playersNumber = 0;
    private int seedNumber;
    private final List<Ai> readyAiList = new ArrayList<>();
    private final List<Ai> firstInitializedAisList = new ArrayList<>();
    private final Map<String, BaseAiCommands> existingAiBases = new HashMap<>();

    private static final int MIN_NUMBER_OF_AIS = 2;

    /**
     * Constructs a new Code Fight game with a valid memory and symbols of possible AI.
     *
     * @param memory    the memory where game is being played.
     * @param aiSymbols the list of all AI symbols.
     */
    public CodeFight(Memory memory, List<String> aiSymbols) {
        this.memory = memory;
        this.aiSymbols = aiSymbols;
        this.phaseHandler = new PhaseHandler();
        setInitModeDefault();
    }

    /**
     * Resets the Code Fight game that was being played.
     */
    public void resetGame() {
        if (currentInitMode.startsWith(InitializationMode.INIT_MODE_STOP.name())) {
            setInitModeDefault();
        } else {
            setInitModeRandom(this.seedNumber);
        }

        memory.defaultFillMemoryDisplayCells();
        this.playersNumber = DEFAULT_VALUE;
        this.readyAiList.clear();
        this.firstInitializedAisList.clear();
        this.stoppedAisList.clear();
        phaseHandler.reset();
    }

    /**
     * Returns the symbols of a possible AIs.
     *
     * @return the symbols of a possible AIs.
     */
    public List<String> getAiSymbols() {
        return new ArrayList<>(aiSymbols);
    }

    /**
     * Returns the existing AIs map.
     *
     * @return the map of AI names to possible AIs with symbols.
     */
    public Map<String, BaseAiCommands> getExistingAiBases() {
        return new HashMap<>(existingAiBases);
    }

    /**
     * Returns the current phase.
     *
     * @return the current phase of the game.
     */
    public Phase getPhase() {
        return this.phaseHandler.getCurrentPhase();
    }

    /**
     * Returns the list of AIs ready to play the game.
     *
     * @return the list of AI's that are going to participate in the game.
     */
    public List<Ai> getReadyAiList() {
        return new ArrayList<>(readyAiList);
    }

    /**
     * Sets the list of AIs ready to play the game.
     *
     * @param readyAiList the list of AI's that are going to participate in the game.
     */
    public void setReadyAiList(List<Ai> readyAiList) {
        this.readyAiList.addAll(readyAiList);
        this.firstInitializedAisList.addAll(readyAiList);
    }

    /**
     * Returns the list of AIs with which the game was initially started.
     *
     * @return the list of AIs with which the game was initially started.
     */
    public List<Ai> getFirstInitializedAisList() {
        return new ArrayList<>(firstInitializedAisList);
    }

    /**
     * Adds a new AI with specified name and commands list.
     *
     * @param aiName     the name of the that's going to be added.
     * @param aiCommands the commands of the AI that's going to be added.
     * @return true if the AI was successfully added, false otherwise.
     */
    public boolean addAi(String aiName, String aiCommands) {
        List<MemoryCell> commands = new ArrayList<>();
        String[] elements = aiCommands.split(AI_COMMANDS_SEPARATOR);

        if (elements.length % COMMAND_LENGTH_WITH_ARGUMENTS != DEFAULT_VALUE) {
            return false;
        } else if (elements.length == COMMAND_LENGTH_WITH_ARGUMENTS && elements[AI_COMMAND_INDEX].equals(STOP_COMMAND)) {
            return false;
        }

        // Memory cells from given AI commands, argument As and argument Bs get created.
        for (int i = 0; i < elements.length; i += COMMAND_LENGTH_WITH_ARGUMENTS) {
            AiCommand aiCommand;

            if (isValidAiCommand(elements[i].trim())) {
                aiCommand = AiCommand.valueOf(elements[i].trim());
            } else {
                return false;
            }
            int argumentA;
            int argumentB;
            try {
                argumentA = Integer.parseInt(elements[i + ARGUMENT_A_INDEX].trim());
                argumentB = Integer.parseInt(elements[i + ARGUMENT_B_INDEX].trim());
            } catch (NumberFormatException e) {
                return false;
            }
            commands.add(new MemoryCell(aiCommand, argumentA, argumentB));
        }

        if (commands.size() > (getMemorySize() / MIN_NUMBER_OF_AIS) + (getMemorySize() % MIN_NUMBER_OF_AIS)) {
            return false;
        }

        int counter = COUNTER_START;
        for (MemoryCell cell : commands) {
            if (cell.aiCommand().equals(AiCommand.STOP)) {
                counter++;
            }
        }

        if (counter == commands.size()) {
            return false;
        }

        BaseAiCommands newBaseAiCommands = new BaseAiCommands(commands);
        existingAiBases.put(aiName, newBaseAiCommands);
        return true;
    }
    private boolean isValidAiCommand(String command) {
        for (AiCommand aiCommand : AiCommand.values()) {
            if (aiCommand.name().equals(command)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an AI by name during the initialization phase.
     *
     * @param aiName the name of the AI that's going to be removed.
     * @return true if the removal was successful, false otherwise.
     */
    public boolean removeAi(String aiName) {
        if (!this.existingAiBases.containsKey(aiName)) {
            return false;
        }
        this.existingAiBases.remove(aiName);
        return true;
    }

    /**
     * Initializes the memory in a default way, with STOP commands and 0 arguments.
     */
    public void setInitModeDefault() {
        memory.defaultInitializeMemory();
        this.currentInitMode = InitializationMode.INIT_MODE_STOP.toString();
    }

    /**
     * Initializes the memory with random AI commands and arguments.
     *
     * @param seedParameter the seed for the random number generator.
     */
    public void setInitModeRandom(int seedParameter) {
        memory.randomInitializeMemory(seedParameter);
        this.seedNumber = seedParameter;
        this.currentInitMode = InitializationMode.INIT_MODE_RANDOM + INIT_MODE_SEPARATOR + seedParameter;
    }

    /**
     * Returns the current initialization mode of the game.
     *
     * @return the current initialization mode of the game.
     */
    public String getCurrentInitMode() {
        return this.currentInitMode;
    }

    /**
     * Advances the game to next phase, game phase.
     */
    public void startNextPhase() {
        phaseHandler.nextPhase();
    }

    /**
     * Returns the size of the memory.
     *
     * @return the memory size.
     */
    public int getMemorySize() {
        return memory.getMemorySize();
    }
    private Ai getCurrentAi() {
        if (readyAiList.isEmpty()) {
            return null;
        }

        playersNumber = playersNumber % readyAiList.size();
        return readyAiList.get(playersNumber);
    }

    /**
     * Returns a display of the memory's representation.
     *
     * @return a string representation of all memory cell's symbols.
     */
    public String displayMemory() {
        return memory.toString();
    }

    /**
     * Returns a detailed visual representation of the memory with the help of the given index.
     *
     * @param index the index in the memory to show the detailed visual representation of the memory cells.
     * @return a string showing the modified representation of the memory and it's cells.
     */
    public String displayModifiedMemoryAndCells(int index) {
        return memory.displayModifiedMemoryAndCells(index);
    }

    /**
     * Sets the memory cell with the given index to a new memory cell with a cyclic behaviour.
     *
     * @param index      the index in the memory to update.
     * @param memoryCell the new memory cell to set the memory cell with the given index.
     */
    public void setCell(int index, MemoryCell memoryCell) {
        memory.setCell(index, memoryCell);
    }

    /**
     * Sets the symbol of the next and other AI's next AI command.
     *
     * @param otherAiSwitch switches to false if the next AI command symbols set.
     * @param index         the position in the memory to update.
     */
    public void placeNextAiCommandSymbols(boolean otherAiSwitch, int index) {
        if (otherAiSwitch) {
            memory.setNextAisNextAiCommand(index);
        } else {
            memory.setOtherAisNextAiCommand(index);
        }
    }

    /**
     * Sets the memory cell's symbol with the given index to a new symbol.
     *
     * @param index  the index of the memory cell that's going to be updated.
     * @param symbol the new symbol to set the memory cell's symbol.
     */
    public void placeAiSymbol(int index, String symbol) {
        memory.setDisplayCellWithoutNextCommands(index, symbol);
        memory.setMemoryDisplayCell(index, symbol);
    }

    /**
     * Returns the list of all the AIs that are stopped in the game.
     *
     * @return the list of stopped AIs.
     */
    public List<Ai> getStoppedAisList() {
        return new ArrayList<>(stoppedAisList);
    }

    /**
     * Returns the values of the memory cell that's going to the next executed cell by the given AI.
     *
     * @param ai the AI whose next memory cell's values are going to be returned.
     * @return a list of values of the memory cell that's going to be the next executed cell.
     */
    public List<String> getNextMemoryCell(Ai ai) {
        List<String> nextMemoryCell = new ArrayList<>();
        MemoryCell nextCell = this.memory.getCell(ai.getCurrentPosition(getMemorySize()));

        nextMemoryCell.add(nextCell.aiCommand().name());
        nextMemoryCell.add(String.valueOf(nextCell.argumentA()));
        nextMemoryCell.add(String.valueOf(nextCell.argumentB()));
        return new ArrayList<>(nextMemoryCell);
    }

    /**
     * Handles the execution and the movement for the current AI command.
     */
    public void handleMove() {
        Ai currentAi = getCurrentAi();
        if (currentAi == null) {
            return;
        }

        memory.clearCommandSymbols();
        int currentAisPosition = currentAi.getCurrentPosition(getMemorySize());
        MemoryCell cell = this.memory.getCell(currentAisPosition);
        AiCommand command = cell.aiCommand();

        command.executeCommand(this.memory, currentAi);

        if (command == AiCommand.STOP) {
            currentAi.stopAi();
            readyAiList.remove(currentAi);
            this.stoppedAisList.add(currentAi);
        } else {
            playersNumber++;
        }

        currentAi.incrementCounter();
        if (!readyAiList.isEmpty()) {
            playersNumber = playersNumber % readyAiList.size();
        }

        // AI's next command's symbols get placed.
        for (Ai ai : readyAiList) {
            if (ai == getCurrentAi()) {
                memory.setNextAisNextAiCommand(getCurrentAi().getCurrentPosition(getMemorySize()));
            } else {
                memory.setOtherAisNextAiCommand(ai.getCurrentPosition(getMemorySize()));
            }
        }

        if (getCurrentAi() != null && !memory.getMemoryDisplayCell(getCurrentAi().getCurrentPosition(getMemorySize()))
                .equals(memory.getNextAisNextAiCommand())) {
            memory.setNextAisNextAiCommand(getCurrentAi().getCurrentPosition(getMemorySize()));
        }
    }
}

