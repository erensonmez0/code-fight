package codefight.model.aicommand;

import codefight.model.ai.Ai;
import codefight.model.memory.Memory;
import codefight.model.memory.MemoryCell;

/**
 * This command adds the value of argument A to the argument B of the memory cell located at the current position
 * plus the value of argument B.
 *
 * @author Eren Soenmez
 */
public class AddRCommandAi implements CommandAi {
    private static final int DEFAULT_MOVE = 1;

    @Override
    public void execute(Memory memory, Ai ai) {
        int position = ai.getCurrentPosition(memory.getMemorySize());
        MemoryCell currentCell = memory.getCell(position);
        MemoryCell targetCell = memory.getCell(position + currentCell.argumentB());

        memory.setCell(position + currentCell.argumentB(),
                new MemoryCell(targetCell.aiCommand(), targetCell.argumentA(), targetCell.argumentB() + currentCell.argumentA()));

        memory.placeSymbol(position + currentCell.argumentB(), ai);
        ai.incrementCurrentPosition(DEFAULT_MOVE, memory.getMemorySize());
    }
}

