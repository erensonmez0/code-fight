package codefight.uicommand;

import codefight.model.CodeFight;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Arrays;

/**
 * This class handles the user input and executes the commands.
 *
 * @author Programmieren-Team
 * @author Eren Soenmez
 */
public class CommandHandler {
    private static final String COMMAND_SEPARATOR_REGEX = " +";
    private static final String ERROR_PREFIX = "Error, ";
    private static final String COMMAND_NOT_FOUND_FORMAT = "command '%s' not found!";
    private static final String WRONG_ARGUMENTS_COUNT_FORMAT = "wrong number of arguments for command '%s'!";
    private static final String INVALID_RESULT_TYPE_FORMAT = "Unexpected value: %s";
    private static final String QUIT_COMMAND_NAME = "quit";
    private static final String HELP_COMMAND_NAME = "help";
    private static final String ADD_AI_COMMAND_NAME = "add-ai";
    private static final String REMOVE_AI_COMMAND_NAME = "remove-ai";
    private static final String SET_INIT_MODE_COMMAND_NAME = "set-init-mode";
    private static final String START_GAME_COMMAND_NAME = "start-game";
    private static final String NEXT_COMMAND_NAME = "next";
    private static final String SHOW_MEMORY_COMMAND_NAME = "show-memory";
    private static final String SHOW_AI_COMMAND_NAME = "show-ai";
    private static final String END_GAME_COMMAND_NAME = "end-game";
    private final CodeFight codeFight;
    private final Map<String, Command> commands;
    private boolean running = false;

    /**
     * Constructs a new CommandHandler.
     *
     * @param codeFight the code fight game that this instance manages
     */
    public CommandHandler(CodeFight codeFight) {
        this.codeFight = Objects.requireNonNull(codeFight);
        this.commands = new HashMap<>();
        this.initCommands();
    }

    /**
     * Starts the interaction with the user.
     */
    public void handleUserInput() {
        this.running = true;

        try (Scanner scanner = new Scanner(System.in)) {
            while (running && scanner.hasNextLine()) {
                executeCommand(scanner.nextLine());
            }
        }
    }

    /**
     * Returns a sorted list for all available commands.
     *
     * @return a list of Command objects, sorted by lexicography.
     */


    /**
     * Returns a sorted list for all available commands.
     *
     * @return a list of Command objects, sorted by lexicography.
     */
    public List<Command> getCommands() {
        List<Command> commandList = new ArrayList<>(commands.values());
        commandList.sort((command1, command2) -> command1.getCommandName().compareTo(command2.getCommandName()));
        return commandList;
    }

    /**
     * Quits the interaction with the user.
     */
    public void quit() {
        this.running = false;
    }

    private void executeCommand(String commandWithArguments) {
        String[] splittedCommand = commandWithArguments.trim().split(COMMAND_SEPARATOR_REGEX);
        String commandName = splittedCommand[0];
        String[] commandArguments = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);

        executeCommand(commandName, commandArguments);
    }

    private void executeCommand(String commandName, String[] commandArguments) {
        if (!commands.containsKey(commandName)) {
            System.err.println(ERROR_PREFIX + COMMAND_NOT_FOUND_FORMAT.formatted(commandName));
        } else if (commands.get(commandName).getMaxNumberOfArguments() < commandArguments.length
                || commands.get(commandName).getMinNumberOfArguments() > commandArguments.length) {
            System.err.println(ERROR_PREFIX + WRONG_ARGUMENTS_COUNT_FORMAT.formatted(commandName));
        } else {
            CommandResult result = commands.get(commandName).execute(codeFight, commandArguments);
            String output = switch (result.getType()) {
                case SUCCESS -> result.getMessage();
                case FAILURE -> ERROR_PREFIX + result.getMessage();
            };
            if (output != null) {
                switch (result.getType()) {
                    case SUCCESS -> System.out.println(output);
                    case FAILURE -> System.err.println(output);
                    default -> throw new IllegalStateException(INVALID_RESULT_TYPE_FORMAT.formatted(result.getType()));
                }
            }
        }
    }

    private void initCommands() {
        this.addCommand(QUIT_COMMAND_NAME, new QuitCommand(this));
        this.addCommand(HELP_COMMAND_NAME, new HelpCommand(this));
        this.addCommand(ADD_AI_COMMAND_NAME, new AddAiCommand());
        this.addCommand(REMOVE_AI_COMMAND_NAME, new RemoveAiCommand());
        this.addCommand(SET_INIT_MODE_COMMAND_NAME, new SetInitModeCommand());
        this.addCommand(START_GAME_COMMAND_NAME, new StartGameCommand());
        this.addCommand(NEXT_COMMAND_NAME, new NextCommand());
        this.addCommand(SHOW_MEMORY_COMMAND_NAME, new ShowMemoryCommand());
        this.addCommand(SHOW_AI_COMMAND_NAME, new ShowAiCommand());
        this.addCommand(END_GAME_COMMAND_NAME, new EndGameCommand());
    }

    private void addCommand(String commandName, Command command) {
        this.commands.put(commandName, command);
    }

}

