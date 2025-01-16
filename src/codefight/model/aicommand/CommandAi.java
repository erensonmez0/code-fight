package codefight.model.aicommand;

import codefight.model.ai.Ai;
import codefight.model.memory.Memory;

/**
 * This interface represents an executable AI command.
 *
 * @author Eren Soenmez
 */
public interface CommandAi {

    /**
     * Executes the command.
     *
     * @param memory the memory where the game is being played.
     * @param ai the AI that's going to execute an AI command in a memory cell.
     */
    void execute(Memory memory, Ai ai);
}

