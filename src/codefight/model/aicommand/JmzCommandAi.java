package codefight.model.aicommand;

import codefight.model.ai.Ai;
import codefight.model.memory.Memory;
import codefight.model.memory.MemoryCell;

/**
 * This command executes a jump for an AI to the memory cell that's value of the argument A away from the current
 * memory cell, only if the argument B of the memory cell located at the current position plus the value of
 * argument B is zero.
 *
 * @author Eren Soenmez
 */
public class JmzCommandAi implements CommandAi {
    private static final int JUMP_CONDITION = 0;
    private static final int DEFAULT_MOVE = 1;

    @Override
    public void execute(Memory memory, Ai ai) {
        int position = ai.getCurrentPosition(memory.getMemorySize());
        MemoryCell currentCell = memory.getCell(position);
        MemoryCell targetCell = memory.getCell(position + currentCell.argumentB());

        if (targetCell.argumentB() == JUMP_CONDITION) {
            ai.incrementCurrentPosition(currentCell.argumentA(), memory.getMemorySize());
        } else {
            ai.incrementCurrentPosition(DEFAULT_MOVE, memory.getMemorySize());
        }
    }
}

