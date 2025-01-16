package codefight.uicommand;

import codefight.model.CodeFight;
import codefight.model.phase.Phase;

import java.util.ArrayList;
import java.util.List;

/**
 * This command displays a list of available commands for each phase along with their descriptions.
 *
 * @author Eren Soenmez
 */
final class HelpCommand implements Command {
    private static final int MIN_NUMBER_OF_ARGUMENTS = 0;
    private static final int MAX_NUMBER_OF_ARGUMENTS = 0;
    private static final String COMMAND_NAME = "help";
    private static final String COMMAND_DESCRIPTION = "Prints information about the commands.";
    private static final Phase COMMAND_FIRST_PHASE = Phase.INITIALIZATION;
    private static final Phase COMMAND_SECOND_PHASE = Phase.GAME;
    private static final String SUCCESS_FORMAT = "%s: %s";
    private final CommandHandler commandHandler;

    /**
     * Constructs a new HelpCommand.
     *
     * @param commandHandler the command handler to give "help" information.
     */
    HelpCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        StringBuilder successMessageBuilder = new StringBuilder();

        setAvailableCommands(model.getPhase(), successMessageBuilder);
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
        commandsPhases.add(COMMAND_FIRST_PHASE);
        commandsPhases.add(COMMAND_SECOND_PHASE);
        return new ArrayList<>(commandsPhases);
    }

    private void setAvailableCommands(Phase gamePhase, StringBuilder successMessageBuilder) {
        List<Command> allUICommands = commandHandler.getCommands();
        boolean isFirstOutput = true;
        for (Command command : allUICommands) {
            if (command.getCommandPhase().contains(gamePhase)) {
                if (!isFirstOutput) {
                    successMessageBuilder.append(System.lineSeparator());
                }
                successMessageBuilder.append(String.format(SUCCESS_FORMAT,
                        command.getCommandName(), command.getCommandDescription()));
                isFirstOutput = false;
            }
        }
    }
}

