package codefight.uicommand;

import codefight.model.ai.Ai;
import codefight.model.CodeFight;
import codefight.model.phase.Phase;

import java.util.ArrayList;
import java.util.List;

/**
 * This command executes the specified number of turns, and if there's any AI that has stopped, it displays the
 * stopped AI along with its number of executed commands until stopping.
 *
 * @author Eren Soenmez
 */
final class NextCommand implements Command {
    private static final int MIN_NUMBER_OF_ARGUMENTS = 0;
    private static final int MAX_NUMBER_OF_ARGUMENTS = 1;
    private static final int NUMBER_OF_TURNS_INDEX = 0;
    private static final String COMMAND_NAME = "next";
    private static final String COMMAND_DESCRIPTION = "Executes command or commands.";
    private static final Phase COMMAND_PHASE = Phase.GAME;
    private static final int NON_POSITIVE_INTEGER_VALUE = 0;
    private static final boolean IS_ALREADY_GIVEN_IN_OUTPUT = true;
    private static final String INVALID_ARGUMENT_TYPE = "You can only give a positive integer value";
    private static final String INVALID_PHASE_MESSAGE = "You can't execute AI commands in this phase.";
    private static final String SUCCESS_FORMAT = "%s executed %d steps until stopping.";
    private static final String SUCCESS_WITHOUT_OUTPUT = null;

    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        if (model.getPhase() != Phase.GAME) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_PHASE_MESSAGE);
        }

        if (commandArguments.length == MIN_NUMBER_OF_ARGUMENTS) {
            model.handleMove();
        } else {
            int numberOfCommandExecutions;
            try {
                numberOfCommandExecutions = Integer.parseInt(commandArguments[NUMBER_OF_TURNS_INDEX]);
            } catch (NumberFormatException e) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_ARGUMENT_TYPE);
            }
            if (numberOfCommandExecutions <= NON_POSITIVE_INTEGER_VALUE) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_ARGUMENT_TYPE);
            }
            for (int i = 0; i < numberOfCommandExecutions; i++) {
                model.handleMove();
            }
        }

        StringBuilder successMessageBuilder = new StringBuilder();
        List<Ai> stoppedAis = model.getStoppedAisList();
        boolean isFirstOutput = true;

        for (Ai stoppedAi : stoppedAis) {
            if (!stoppedAi.isStopOutput()) {
                if (!isFirstOutput) {
                    successMessageBuilder.append(System.lineSeparator());
                }

                successMessageBuilder.append(String.format(SUCCESS_FORMAT,
                        stoppedAi.getAiName(), stoppedAi.getCounter()));
                stoppedAi.setStopOutput(IS_ALREADY_GIVEN_IN_OUTPUT);
                isFirstOutput = false;
            }
        }

        if (!stoppedAis.isEmpty() && successMessageBuilder.length() > MIN_NUMBER_OF_ARGUMENTS) {
            return new CommandResult(CommandResultType.SUCCESS, successMessageBuilder.toString());
        } else {
            return new CommandResult(CommandResultType.SUCCESS, SUCCESS_WITHOUT_OUTPUT);
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

