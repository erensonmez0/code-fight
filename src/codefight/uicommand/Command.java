package codefight.uicommand;

import codefight.model.CodeFight;
import codefight.model.phase.Phase;

import java.util.List;

/**
 * This interface represents an executable command.
 *
 * @author Eren Soenmez
 */
public interface Command {

    /**
     * Executes the command.
     *
     * @param model            the model to execute the command on
     * @param commandArguments the arguments of the command
     * @return the result of the command
     */
    CommandResult execute(CodeFight model, String[] commandArguments);

    /**
     * Returns the minimum number of arguments that the command expects.
     *
     * @return the minimum number of arguments that the command expects
     */
    int getMinNumberOfArguments();

    /**
     * Returns the minimum number of arguments that the command expects.
     *
     * @return the minimum number of arguments that the command expects
     */
    int getMaxNumberOfArguments();

    /**
     * Returns the name of the command.
     *
     * @return the name of the command.
     */
    String getCommandName();

    /**
     * Returns a short description of the command.
     *
     * @return a short description of the command.
     */
    String getCommandDescription();

    /**
     * Returns a list that contains the phases of the game that the command can be executed in.
     *
     * @return a list that contains the phases of the game that the command can be executed in.
     */
    List<Phase> getCommandPhase();
}
