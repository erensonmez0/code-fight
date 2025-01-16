package codefight.uicommand;

import codefight.model.CodeFight;
import codefight.model.phase.Phase;

import java.util.ArrayList;
import java.util.List;

/**
 * This command shows the display of the memory, also can show a partial viewing of the memory's next 10 cells
 * depending on the entered value.
 *
 * @author Eren Soenmez
 */
final class ShowMemoryCommand implements Command {
    private static final int MIN_NUMBER_OF_ARGUMENTS = 0;
    private static final int MAX_NUMBER_OF_ARGUMENTS = 1;
    private static final int STARTING_MEMORY_CELL_INDEX = 0;
    private static final int LOWEST_CELL_TO_DISPLAY = 0;
    private static final int INDEX_DIFFERENCE = 1;
    private static final String COMMAND_NAME = "show-memory";
    private static final String COMMAND_DESCRIPTION = "Shows the memory.";
    private static final Phase COMMAND_PHASE = Phase.GAME;
    private static final String INVALID_ARGUMENT_TYPE = "You can only give a positive integer value";
    private static final String INVALID_ARGUMENT_SIZE_TYPE = "The given value can't be lower than first cell's number"
            + " or bigger than last cell's number.";
    private static final String INVALID_PHASE_MESSAGE = "Memory can't be displayed in this phase.";
    private static final String SUCCESS_MESSAGE_FORMAT = "%s";

    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        if (model.getPhase() != Phase.GAME) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_PHASE_MESSAGE);
        }

        int showMemoryStartingIndex;
        if (commandArguments.length == MIN_NUMBER_OF_ARGUMENTS) {
            return new CommandResult(CommandResultType.SUCCESS,
                    SUCCESS_MESSAGE_FORMAT.formatted(model.displayMemory()));
        } else {
            try {
                showMemoryStartingIndex = Integer.parseInt(commandArguments[STARTING_MEMORY_CELL_INDEX]);
            } catch (NumberFormatException e) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_ARGUMENT_TYPE);
            }

            if (showMemoryStartingIndex < LOWEST_CELL_TO_DISPLAY
                    || showMemoryStartingIndex > model.getMemorySize() - INDEX_DIFFERENCE) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_ARGUMENT_SIZE_TYPE);
            }
            return new CommandResult(CommandResultType.SUCCESS,
                    model.displayModifiedMemoryAndCells(showMemoryStartingIndex));
        }

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
}

