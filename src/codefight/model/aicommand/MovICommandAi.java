package codefight.model.aicommand;

import codefight.model.ai.Ai;
import codefight.model.memory.Memory;
import codefight.model.memory.MemoryCell;

/**
 * This command copies the memory cell that's located at the current position plus the value of argument A, and prints
 * the memory cell to the memory cell that's located at the current position plus the value of argument B plus
 * te argument B of the destination memory cell.
 *
 * @author Eren Soenmez
 */
public class MovICommandAi implements CommandAi {
    private static final int DEFAULT_MOVE = 1;
    private static final int MAX_INT = Integer.MAX_VALUE;

    @Override
    public void execute(Memory memory, Ai ai) {
        int position = ai.getCurrentPosition(memory.getMemorySize());
        MemoryCell currentCell = memory.getCell(position);

        long distanceToIndirect = position + currentCell.argumentB();
        long argumentBForTarget = memory.getCell(position + currentCell.argumentB()).argumentB();
        long totalValue = distanceToIndirect + argumentBForTarget;

        int movITargetCell;
        if (totalValue > MAX_INT) {
            movITargetCell = (int) (distanceToIndirect % memory.getMemorySize()) + (int) (argumentBForTarget % memory.getMemorySize());
        } else {
            movITargetCell = (int) totalValue;
        }

        memory.setCell(movITargetCell, memory.getCell(position + currentCell.argumentA()));


        memory.placeSymbol(movITargetCell, ai);
        ai.incrementCurrentPosition(DEFAULT_MOVE, memory.getMemorySize());
    }
}

