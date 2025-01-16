package codefight.model.aicommand;

import codefight.model.ai.Ai;
import codefight.model.memory.Memory;
import codefight.model.memory.MemoryCell;

/**
 * This command compares the argument A of the memory cell that's located at the current position plus the value
 * of argument A and the argument B of the memory cell that's located at the current position plus the value of
 * argument B. If the compared values are not the same, it skips the next memory cell and doesn't execute the command
 * inside. Makes a default move otherwise.
 *
 * @author Eren Soenmez
 */
public class CmpCommandAi implements CommandAi {
    private static final int DEFAULT_MOVE = 1;
    private static final int VALUE_TO_SKIP_NEXT_MOVE = 2;

    @Override
    public void execute(Memory memory, Ai ai) {
        int position = ai.getCurrentPosition(memory.getMemorySize());
        MemoryCell currentCell = memory.getCell(position);
        MemoryCell targetCellA = memory.getCell(position + currentCell.argumentA());
        MemoryCell targetCellB = memory.getCell(position + currentCell.argumentB());

        if (targetCellA.argumentA() == targetCellB.argumentB()) {
            ai.incrementCurrentPosition(DEFAULT_MOVE, memory.getMemorySize());
        } else {
            ai.incrementCurrentPosition(VALUE_TO_SKIP_NEXT_MOVE, memory.getMemorySize());
        }
    }
}

