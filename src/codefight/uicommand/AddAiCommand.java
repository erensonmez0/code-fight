package codefight.uicommand;

import codefight.model.CodeFight;
import codefight.model.phase.Phase;

import java.util.ArrayList;
import java.util.List;

/**
 * This command adds an AI with its list of commands, as an output it displays the name of AI that's added.
 *
 * @author Eren Soenmez
 */
final class AddAiCommand implements Command {
    private static final int AI_NAME_INDEX = 0;
    private static final int AI_COMMANDS_INDEX = 1;
    private static final int MIN_NUMBER_OF_ARGUMENTS = 2;
    private static final int MAX_NUMBER_OF_ARGUMENTS = 2;
    private static final String COMMAND_NAME = "add-ai";
    private static final String COMMAND_DESCRIPTION = "Adds an AI.";
    private static final Phase COMMAND_PHASE = Phase.INITIALIZATION;
    private static final String INVALID_WHITESPACE = " ";
    private static final String INVALID_PHASE_MESSAGE = "You can't add an AI in this phase.";
    private static final String INVALID_AI_NAME_MESSAGE = "Invalid name, AI name can't contain any spaces.";
    private static final String DUPLICATE_AI_MESSAGE = "This AI already exists.";
    private static final String INVALID_AI_COMMANDS_FORMAT_MESSAGE = "Illegal format for the AI Commands list.";
    private static final String SUCCESS_MESSAGE_FORMAT = "%s";
    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        if (model.getPhase() != Phase.INITIALIZATION) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_PHASE_MESSAGE);
        }

        String aiName = commandArguments[AI_NAME_INDEX];
        String aiCommands = commandArguments[AI_COMMANDS_INDEX];

        if (aiName.contains(INVALID_WHITESPACE)) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_AI_NAME_MESSAGE);
        }

        if (model.getExistingAiBases().containsKey(aiName)) {
            return new CommandResult(CommandResultType.FAILURE, DUPLICATE_AI_MESSAGE);
        }

        if (!model.addAi(aiName, aiCommands)) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_AI_COMMANDS_FORMAT_MESSAGE);

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
