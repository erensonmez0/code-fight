package codefight.uicommand;

import codefight.model.CodeFight;
import codefight.model.phase.Phase;

import java.util.ArrayList;
import java.util.List;

/**
 * This command quits a {@link CommandHandler command handler}.
 *
 * @author Eren Soenmez
 */
final class QuitCommand implements Command {
    private static final int MIN_NUMBER_OF_ARGUMENTS = 0;
    private static final int MAX_NUMBER_OF_ARGUMENTS = 0;
    private static final String COMMAND_NAME = "quit";
    private static final String COMMAND_DESCRIPTION = "Quits the game.";
    private static final Phase COMMAND_FIRST_PHASE = Phase.INITIALIZATION;
    private static final Phase COMMAND_SECOND_PHASE = Phase.GAME;
    private static final String SUCCESS_WITHOUT_OUTPUT = null;
    private final CommandHandler commandHandler;

    /**
     * Constructs a new QuitCommand.
     *
     * @param commandHandler the command handler to be quitted.
     */
    QuitCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public CommandResult execute(CodeFight ignored, String[] commandArguments) {
        commandHandler.quit();
        return new CommandResult(CommandResultType.SUCCESS, SUCCESS_WITHOUT_OUTPUT);
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
        commandsPhases.add(COMMAND_FIRST_PHASE);
        commandsPhases.add(COMMAND_SECOND_PHASE);
        return new ArrayList<>(commandsPhases);
    }
}

