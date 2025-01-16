package codefight.uicommand;

import codefight.model.ai.Ai;
import codefight.model.CodeFight;
import codefight.model.phase.Phase;

import java.util.ArrayList;
import java.util.List;

/**
 * This command shows the display of the given AI. If the AI's still running, this command shows the position and the
 * next command that's going to be executed. If the AI has stopped, this command shows the number of executed commands
 * until stopping.
 *
 * @author Eren Soenmez
 */
final class ShowAiCommand implements Command {
    private static final int MIN_NUMBER_OF_ARGUMENTS = 1;
    private static final int MAX_NUMBER_OF_ARGUMENTS = 1;
    private static final int AI_NAME_INDEX = 0;
    private static final int AI_COMMAND_INDEX = 0;
    private static final int ARGUMENT_A_INDEX = 1;
    private static final int ARGUMENT_B_INDEX = 2;
    private static final int STOP_COMMAND_INCLUDED = 1;
    private static final String COMMAND_NAME = "show-ai";
    private static final String COMMAND_DESCRIPTION = "Shows an AI with it's current game's information.";
    private static final Phase COMMAND_PHASE = Phase.GAME;
    private static final String INVALID_PHASE_MESSAGE = "AI's can't be displayed in this phase.";
    private static final String INVALID_AI_NAME = "This AI is not included in this game.";
    private static final String SUCCESS_FORMAT_RUNNING = "%s (RUNNING@%d)";
    private static final String SUCCESS_FORMAT_NEXT_COMMAND = "Next Command: %s|%s|%s @%d";
    private static final String SUCCESS_FORMAT_STOPPED = "%s (STOPPED@%d)";

    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        if (model.getPhase() != Phase.GAME) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_PHASE_MESSAGE);
        }

        String aiName = commandArguments[AI_NAME_INDEX];

        List<Ai> readyAiList = new ArrayList<>(model.getFirstInitializedAisList());
        StringBuilder successMessageBuilder = new StringBuilder();
        String lineSeparator = System.lineSeparator();
        for (Ai ai : readyAiList) {
            if (ai.getAiName().equals(aiName)) {
                if (!ai.isStopped()) {

                    successMessageBuilder.append(String.format(SUCCESS_FORMAT_RUNNING, aiName, ai.getCounter()));
                    successMessageBuilder.append(lineSeparator);
                    successMessageBuilder.append(String.format(SUCCESS_FORMAT_NEXT_COMMAND,
                            model.getNextMemoryCell(ai).get(AI_COMMAND_INDEX),
                            model.getNextMemoryCell(ai).get(ARGUMENT_A_INDEX),
                            model.getNextMemoryCell(ai).get(ARGUMENT_B_INDEX),
                            ai.getCurrentPosition(model.getMemorySize()) % model.getMemorySize()));

                    return new CommandResult(CommandResultType.SUCCESS, successMessageBuilder.toString());
                } else {
                    successMessageBuilder.append(String.format(SUCCESS_FORMAT_STOPPED, aiName, ai.getCounter() + STOP_COMMAND_INCLUDED));
                }
                return new CommandResult(CommandResultType.SUCCESS, successMessageBuilder.toString());
            }
        }
        return new CommandResult(CommandResultType.FAILURE, INVALID_AI_NAME);
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

