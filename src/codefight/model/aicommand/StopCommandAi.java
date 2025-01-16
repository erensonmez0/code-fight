package codefight.model.aicommand;

import codefight.model.ai.Ai;
import codefight.model.memory.Memory;

/**
 * This command stops the AI that executed this command and that AI doesn't win the Code Fight game.
 *
 * @author Eren Soenmez
 */
public class StopCommandAi implements CommandAi {
    private static final int STOP_MOVE = 0;

    @Override
    public void execute(Memory memory, Ai ai) {

        ai.incrementCurrentPosition(STOP_MOVE, memory.getMemorySize());
    }
}

