package codefight.uicommand;

import codefight.model.ai.Ai;
import codefight.model.CodeFight;
import codefight.model.ai.BaseAiCommands;
import codefight.model.aicommand.AiCommand;
import codefight.model.memory.MemoryCell;
import codefight.model.phase.Phase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This command starts the game with the given AI's and as an output it displays the AI's that game was started with.
 *
 * @author Eren Soenmez
 */
final class StartGameCommand implements Command {
    private static final int MIN_NUMBER_OF_ARGUMENTS = 2;
    private static final int MAX_NUMBER_OF_ARGUMENTS = Integer.MAX_VALUE;
    private static final int SINGLE_AI_NUMBER = 1;
    private static final int DEFAULT_VALUE = 0;
    private static final int FIRST_AI_INDEX = 0;
    private static final int COUNTER_START = 0;
    private static final String COMMAND_NAME = "start-game";
    private static final String COMMAND_DESCRIPTION = "Starts the game.";
    private static final Phase COMMAND_PHASE = Phase.INITIALIZATION;
    private static final String DUPLICATE_AI_DELIMITER = "#";
    private static final int ADD_TO_COUNT = 1;
    private static final int LAST_INDEX_DIFFERENCE = 1;
    private static final int SYMBOLS_PER_AI = 2;
    private static final int EVEN_NUMBER_DIVIDER = 2;
    private static final String INVALID_PHASE_MESSAGE = "You can't start the game in this phase.";
    private static final String INVALID_ARGUMENTS_COUNT_FORMAT = "wrong number of arguments for command 'start-game'!";
    private static final String INVALID_AI_MESSAGE = "This AI or AI's were not added with add-ai.";
    private static final String INVALID_LONG_AI_MESSAGE = "The AI with the biggest number of commands can't be placed in "
            + "other places other than the last place.";
    private static final String SUCCESS_MESSAGE = "Game started.";
    private final List<String> aiCommandSymbolList = new ArrayList<>();
    private final List<String> aiBombSymbolList = new ArrayList<>();
    private final Map<String, Integer> aiNameCount = new HashMap<>();

    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        CommandResult validationResult = validateArgumentsEntry(model, commandArguments);
        if (validationResult != null) {
            return validationResult;
        }

        this.aiNameCount.putAll(countAiNames(commandArguments));
        Map<String, Integer> aiNameReadCount = new HashMap<>();

        getAiCommandSymbols(model);

        int defaultMemorySectionsSize = model.getMemorySize() / commandArguments.length;
        int extraMemory = model.getMemorySize() % commandArguments.length;
        List<Ai> readyAiList = new ArrayList<>();

        int currentAisStartingCell = 0;
        boolean otherAisSwitch = true;

        // Iterates the given AI names and creates AIs and places their symbols.
        for (int index = 0; index < commandArguments.length; index++) {
            String aiName = commandArguments[index];

            if (!model.getExistingAiBases().containsKey(aiName)) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_AI_MESSAGE);
            }

            int memorySectionsSize = defaultMemorySectionsSize;
            if (commandArguments.length - index <= extraMemory) {
                memorySectionsSize++;
            }

            if (validateLargestAiPlacement(model, commandArguments, index)) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_LONG_AI_MESSAGE);
            }

            // Collects the variables for an AI and creates an AI with them.
            String uniqueAiName = getUniqueAiName(aiNameReadCount, aiName);
            BaseAiCommands existingAi = model.getExistingAiBases().get(aiName);
            int startingPositionForAi = currentAisStartingCell;

            readyAiList.add(createAi(this.aiCommandSymbolList.get(index), this.aiBombSymbolList.get(index), uniqueAiName,
                    existingAi.commands(), startingPositionForAi + additionToStartingPosition(model, aiName)));

            // AI symbols get placed.
            placeAiCommandsAndSymbols(model, index, readyAiList, startingPositionForAi);
            placeNextSymbols(model, otherAisSwitch, aiName, startingPositionForAi);
            if (index == FIRST_AI_INDEX) {
                otherAisSwitch = false;
            }
            currentAisStartingCell += memorySectionsSize;
        }


        model.setReadyAiList(readyAiList);
        model.startNextPhase();
        return new CommandResult(CommandResultType.SUCCESS, SUCCESS_MESSAGE);
    }

    @Override
    public int getMinNumberOfArguments() {
        return MIN_NUMBER_OF_ARGUMENTS;
    }

    @Override
    public int getMaxNumberOfArguments() {
        return MAX_NUMBER_OF_ARGUMENTS;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public List<Phase> getCommandPhase() {
        List<Phase> commandsPhases = new ArrayList<>();
        commandsPhases.add(COMMAND_PHASE);
        return new ArrayList<>(commandsPhases);
    }

    private CommandResult validateArgumentsEntry(CodeFight model, String[] commandArguments) {
        if (model.getPhase() != Phase.INITIALIZATION) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_PHASE_MESSAGE);
        }
        int numberOfAis = (model.getAiSymbols().size() / SYMBOLS_PER_AI);
        if (commandArguments.length > numberOfAis) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_ARGUMENTS_COUNT_FORMAT);
        }

        return null;
    }

    private void getAiCommandSymbols(CodeFight model) {
        for (int i = 0; i < model.getAiSymbols().size(); i++) {
            if (i % EVEN_NUMBER_DIVIDER == DEFAULT_VALUE) {
                this.aiCommandSymbolList.add(model.getAiSymbols().get(i));
            } else {
                this.aiBombSymbolList.add(model.getAiSymbols().get(i));
            }
        }
    }

    private Map<String, Integer> countAiNames(String[] commandArguments) {
        Map<String, Integer> aiNameCount = new HashMap<>();
        for (String aiName : commandArguments) {
            aiNameCount.put(aiName, aiNameCount.getOrDefault(aiName, DEFAULT_VALUE) + ADD_TO_COUNT);
        }
        return new HashMap<>(aiNameCount);
    }

    private String getUniqueAiName(Map<String, Integer> aiNameReadCount, String aiName) {
        int readCount = aiNameReadCount.getOrDefault(aiName, DEFAULT_VALUE);
        String uniqueAiName = aiName;
        if (this.aiNameCount.get(aiName) > SINGLE_AI_NUMBER) {
            uniqueAiName += DUPLICATE_AI_DELIMITER + readCount;
        }
        aiNameReadCount.put(aiName, readCount + ADD_TO_COUNT);
        return uniqueAiName;
    }

    private Ai createAi(String aiCommandSymbol, String aiBombSymbol, String aiName, List<MemoryCell> commands,
                        int currentPosition) {
        return new Ai(aiCommandSymbol, aiBombSymbol, aiName, commands, currentPosition);
    }

    private int additionToStartingPosition(CodeFight model, String aiName) {
        int startingCounter = COUNTER_START;
        BaseAiCommands existingAi = model.getExistingAiBases().get(aiName);
        for (MemoryCell cell : existingAi.commands()) {
            if (cell.aiCommand() != AiCommand.STOP) {
                return startingCounter;
            }
            startingCounter++;
        }
        return startingCounter;
    }

    private void placeAiCommandsAndSymbols(CodeFight model, int index, List<Ai> readyAiList, int startingPositionForAi) {
        for (int j = 0; j < readyAiList.get(index).getCommands().size(); j++) {
            model.placeAiSymbol(startingPositionForAi + j,
                    readyAiList.get(index).getAiCommandSymbol());

            model.setCell(startingPositionForAi + j,
                    readyAiList.get(index).getCommands().get(j));
        }
    }

    private void placeNextSymbols(CodeFight model, boolean otherAisSwitch, String aiName, int startingPositionForAi) {
        int startIndexForAI = startingPositionForAi + additionToStartingPosition(model, aiName);
        model.placeNextAiCommandSymbols(otherAisSwitch, startIndexForAI);
    }

    private boolean validateLargestAiPlacement(CodeFight model, String[] commandArguments, int index) {

        int numberOfCommandsOfAi = model.getExistingAiBases().get(commandArguments[index]).commands().size();

        return index < commandArguments.length - LAST_INDEX_DIFFERENCE

                && numberOfCommandsOfAi >= (model.getMemorySize() / commandArguments.length)
                + (model.getMemorySize() % commandArguments.length)

                && numberOfCommandsOfAi > model.getExistingAiBases()
                .get(commandArguments[commandArguments.length - LAST_INDEX_DIFFERENCE]).commands().size();
    }
}

