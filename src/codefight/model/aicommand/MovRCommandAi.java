package codefight.model.aicommand;

import codefight.model.ai.Ai;
import codefight.model.memory.Memory;
import codefight.model.memory.MemoryCell;

/**
 * This command copies the memory cell that's located at the current position plus the value of argument A, and prints
 * the memory cell to the memory cell that's located at the current position plus the value of argument B.
 *
 * @author Eren Soenmez
 */
public class MovRCommandAi implements CommandAi {
    private static final int DEFAULT_MOVE = 1;

    @Override
    public void execute(Memory memory, Ai ai) {
        int position = ai.getCurrentPosition(memory.getMemorySize());
        MemoryCell currentCell = memory.getCell(position);

        memory.setCell(position + currentCell.argumentB(),
                memory.getCell(position + currentCell.argumentA()));

        memory.placeSymbol(position + currentCell.argumentB(), ai);
        ai.incrementCurrentPosition(DEFAULT_MOVE, memory.getMemorySize());
    }
}

