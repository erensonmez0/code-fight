package codefight.model.aicommand;

import codefight.model.ai.Ai;
import codefight.model.memory.Memory;
import codefight.model.memory.MemoryCell;

/**
 * This command executes a jump for an AI to the memory cell located at the current position
 * plus the value of argument A.
 *
 * @author Eren Soenmez
 */
public class JmpCommandAi implements CommandAi {
    @Override
    public void execute(Memory memory, Ai ai) {
        int position = ai.getCurrentPosition(memory.getMemorySize());
        MemoryCell currentCell = memory.getCell(position);

        ai.incrementCurrentPosition(currentCell.argumentA(), memory.getMemorySize());
    }
}

