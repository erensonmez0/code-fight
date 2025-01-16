package codefight.uicommand;

import codefight.model.ai.Ai;
import codefight.model.CodeFight;
import codefight.model.phase.Phase;

import java.util.ArrayList;
import java.util.List;

/**
 * This command ends the game. It also displays the running and stopped AI's and resets the game to its initial state.
 *
 * @author Eren Soenmez
 */
final class EndGameCommand implements Command {
    private static final int MIN_NUMBER_OF_ARGUMENTS = 0;
    private static final int MAX_NUMBER_OF_ARGUMENTS = 0;
    private static final int LAST_INDEX_DIFFERENCE = 1;
    private static final String COMMAND_NAME = "end-game";
    private static final String COMMAND_DESCRIPTION = "Ends the current game.";
    private static final Phase COMMAND_PHASE = Phase.GAME;
    private static final String AI_NAMES_SEPARATOR = ", ";
    private static final String FORMAT_RUNNING_AIS = "Running AIs: ";
    private static final String FORMAT_STOPPED_AIS = "Stopped AIs: ";
    private static final String INVALID_PHASE_MESSAGE = "You can't end the game in this phase.";

    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        if (model.getPhase() != Phase.GAME) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_PHASE_MESSAGE);
        }

        List<Ai> runningAisList = new ArrayList<>(model.getReadyAiList());
        StringBuilder successMessageBuilder = new StringBuilder();
        List<Ai> readyAiList = new ArrayList<>(model.getFirstInitializedAisList());
        readyAiList.removeAll(runningAisList);

        if (!runningAisList.isEmpty()) {
            buildSuccessMessage(successMessageBuilder, FORMAT_RUNNING_AIS, runningAisList);
        }

        if (!readyAiList.isEmpty()) {

            if (!runningAisList.isEmpty()) {
                successMessageBuilder.append(System.lineSeparator());
            }

            buildSuccessMessage(successMessageBuilder, FORMAT_STOPPED_AIS, readyAiList);
        }

        model.resetGame();
        return new CommandResult(CommandResultType.SUCCESS, successMessageBuilder.toString());
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

    private void buildSuccessMessage(StringBuilder successMessageBuilder, String outputFormat, List<Ai> givenList) {
        successMessageBuilder.append(outputFormat);
        for (int i = 0; i < givenList.size(); i++) {
            Ai ai = givenList.get(i);
            successMessageBuilder.append(ai.getAiName());

            if (i < givenList.size() - LAST_INDEX_DIFFERENCE) {
                successMessageBuilder.append(AI_NAMES_SEPARATOR);
            }
        }
    }
}

