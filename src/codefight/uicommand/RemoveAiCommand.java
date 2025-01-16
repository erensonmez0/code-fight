package codefight.uicommand;

import codefight.model.CodeFight;
import codefight.model.phase.Phase;

import java.util.ArrayList;
import java.util.List;

/**
 * This command removes an AI from the game.
 *
 * @author Eren Soenmez
 */
final class RemoveAiCommand implements Command {
    private static final int MIN_NUMBER_OF_ARGUMENTS = 1;
    private static final int MAX_NUMBER_OF_ARGUMENTS = 1;
    private static final int AI_NAME_INDEX = 0;
    private static final String COMMAND_NAME = "remove-ai";
    private static final String COMMAND_DESCRIPTION = "Removes an AI.";
    private static final Phase COMMAND_PHASE = Phase.INITIALIZATION;
    private static final String INVALID_PHASE_MESSAGE = "You can't remove an AI in this phase.";
    private static final String INVALID_NON_EXISTENT_AI = "This AI does not exist.";
    private static final String SUCCESS_MESSAGE_FORMAT = "%s";

    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        if (model.getPhase() != Phase.INITIALIZATION) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_PHASE_MESSAGE);
        }

        String aiName = commandArguments[AI_NAME_INDEX];
        if (!model.removeAi(aiName)) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_NON_EXISTENT_AI);
        } else {
            return new CommandResult(CommandResultType.SUCCESS, SUCCESS_MESSAGE_FORMAT.formatted(aiName));
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

