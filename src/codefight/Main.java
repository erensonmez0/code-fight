package codefight;

import codefight.model.CodeFight;
import codefight.model.memory.Memory;
import codefight.uicommand.CommandHandler;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main entry class to run application.
 *
 * @author Eren Soenmez
 */
public final class Main {
    private static final int MEMORY_SIZE_INDEX = 0;
    private static final int UNPROCESSED_COMMAND_INDEX = 1;
    private static final int DISPLAY_AREA_BOUNDS_INDEX = 2;
    private static final int NEXT_AI_COMMAND_INDEX = 3;
    private static final int OTHER_AI_NEXT_COMMANDS_INDEX = 4;
    private static final int NUMBER_OF_FIXED_ARGUMENTS = 5;
    private static final int NUMBER_OF_ARGUMENTS_PER_AI = 2;
    private static final int MEMORY_SIZE_MIN_BOUND = 7;
    private static final int MEMORY_SIZE_MAX_BOUND = 1337;
    private static final int SYMBOLS_PER_AI = 2;
    private static final String GREETING_MESSAGE = "Welcome to CodeFight 2024. Enter 'help' for more details.";
    private static final String INVALID_WHITESPACE = " ";
    private static final String ERROR_PREFIX = "Error, ";
    private static final String ERROR_NOT_EXPECTED_ARGS_LENGTH = ERROR_PREFIX + "wrong number of arguments.%n";
    private static final String ERROR_INVALID_MEMORY_SIZE = ERROR_PREFIX
            + "Memory size must be a number between 7 and 1337.%n";
    private static final String ERROR_DUPLICATE_SYMBOL = ERROR_PREFIX + "Symbols cannot be the same.%n";
    private static final String ERROR_INVALID_SYMBOL = ERROR_PREFIX + "Invalid symbol.%n";
    private static final String ERROR_MAX_NUMBER_OF_AIS = ERROR_PREFIX + "Limit for maximum amount of AI's exceeded.%n";
    private static final String UTILITY_CLASS_CONSTRUCTOR_MESSAGE = "Utility classes cannot be instantiated";

    private Main() {
        throw new UnsupportedOperationException(UTILITY_CLASS_CONSTRUCTOR_MESSAGE);
    }

    /**
     * Starts the program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if ((args.length - NUMBER_OF_FIXED_ARGUMENTS) % NUMBER_OF_ARGUMENTS_PER_AI != 0
                || (args.length - NUMBER_OF_FIXED_ARGUMENTS) <= NUMBER_OF_ARGUMENTS_PER_AI) {
            System.err.printf(ERROR_NOT_EXPECTED_ARGS_LENGTH);
            return;
        }

        int memorySize;
        try {
            memorySize = Integer.parseInt(args[MEMORY_SIZE_INDEX]);
        } catch (NumberFormatException e) {
            System.err.printf(ERROR_INVALID_MEMORY_SIZE);
            return;
        }
        if (memorySize < MEMORY_SIZE_MIN_BOUND || memorySize > MEMORY_SIZE_MAX_BOUND) {
            System.err.printf(ERROR_INVALID_MEMORY_SIZE);
            return;
        }

        int numberOfAis = (args.length - NUMBER_OF_FIXED_ARGUMENTS) / SYMBOLS_PER_AI;
        if (numberOfAis > memorySize) {
            System.err.printf(ERROR_MAX_NUMBER_OF_AIS);
            return;
        }

        List<String> symbolList = new ArrayList<>();
        for (int i = UNPROCESSED_COMMAND_INDEX; i < args.length; i++) {
            String symbol = args[i];
            if (symbol.contains(INVALID_WHITESPACE) || symbolList.contains(symbol)) {
                System.err.printf(symbol.contains(INVALID_WHITESPACE) ? ERROR_INVALID_SYMBOL : ERROR_DUPLICATE_SYMBOL);
                return;
            }
            symbolList.add(symbol);
        }

        List<String> aiSymbols = new ArrayList<>(Arrays.asList(args).subList(NUMBER_OF_FIXED_ARGUMENTS, args.length));

        Memory memory = new Memory(memorySize, args[UNPROCESSED_COMMAND_INDEX], args[DISPLAY_AREA_BOUNDS_INDEX],
                args[NEXT_AI_COMMAND_INDEX], args[OTHER_AI_NEXT_COMMANDS_INDEX]);

        CodeFight codeFight = new CodeFight(memory, aiSymbols);
        System.out.println(GREETING_MESSAGE);
        CommandHandler commandHandler = new CommandHandler(codeFight);
        commandHandler.handleUserInput();
    }
}
