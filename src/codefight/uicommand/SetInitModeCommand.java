package codefight.uicommand;

import codefight.model.CodeFight;
import codefight.model.memory.InitializationMode;
import codefight.model.phase.Phase;

import java.util.ArrayList;
import java.util.List;

/**
 * This command sets the initialization mode to "stop" or "random" depending on the input. If the initialization mode
 * hasn't changed, it does not return anything as an output. Otherwise, it displays from which initialization mode the
 * game has changed to which initialization mode.
 *
 * @author Eren Soenmez
 */
final class SetInitModeCommand implements Command {
    private static final int MIN_NUMBER_OF_ARGUMENTS = 1;
    private static final int MAX_NUMBER_OF_ARGUMENTS = 2;
    private static final int INIT_MODE_INDEX = 0;
    private static final int SEED_NUMBER_INDEX = 1;
    private static final String COMMAND_NAME = "set-init-mode";
    private static final String COMMAND_DESCRIPTION = "Sets the initialization mode.";
    private static final Phase COMMAND_PHASE = Phase.INITIALIZATION;
    private static final int MIN_NUMBER_OF_PARAMETER = -1337;
    private static final int MAX_NUMBER_OF_PARAMETER = 1337;
    private static final String INVALID_PHASE_MESSAGE = "You can't set the initialization mode in this phase.";
    private static final String INVALID_NUMBER_OF_ARGUMENTS = "INIT_MODE_STOP can't have any integer value as parameter.";
    private static final String INVALID_MODE_MESSAGE = "You can only choose INIT_MODE_RANDOM or INIT_MODE_STOP"
            + " as your initialization mode.";
    private static final String INVALID_PARAMETER_MESSAGE = "Parameter can only be an "
            + "integer value between -1337 and 1337.";
    private static final String SUCCESS_MESSAGE_FORMAT = "Changed init mode from %s to %s";

    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        if (model.getPhase() != Phase.INITIALIZATION) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_PHASE_MESSAGE);
        }

        String oldMode = model.getCurrentInitMode();
        InitializationMode mode = null;
        String modeArgument = commandArguments[INIT_MODE_INDEX];

        for (InitializationMode newMode : InitializationMode.values()) {
            if (newMode.name().equals(modeArgument)) {
                mode = newMode;
                break;
            }
        }

        if (mode == null) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_MODE_MESSAGE);
        }

        if (mode == InitializationMode.INIT_MODE_STOP) {
            model.setInitModeDefault();
            if (commandArguments.length != MIN_NUMBER_OF_ARGUMENTS) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_NUMBER_OF_ARGUMENTS);
            }
        } else if (mode == InitializationMode.INIT_MODE_RANDOM) {

            int seedParameter;
            try {
                seedParameter = Integer.parseInt(commandArguments[SEED_NUMBER_INDEX]);
            } catch (NumberFormatException e) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_PARAMETER_MESSAGE);
            }

            if (seedParameter < MIN_NUMBER_OF_PARAMETER || seedParameter > MAX_NUMBER_OF_PARAMETER) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_PARAMETER_MESSAGE);
            }

            model.setInitModeRandom(seedParameter);
        }


        String newMode = model.getCurrentInitMode();
        if (!oldMode.equals(newMode)) {
            return new CommandResult(CommandResultType.SUCCESS,
                    String.format(SUCCESS_MESSAGE_FORMAT, oldMode, newMode));
        } else {
            return new CommandResult(CommandResultType.SUCCESS, null);
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

