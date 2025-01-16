package codefight.model.aicommand;

import codefight.model.ai.Ai;
import codefight.model.memory.Memory;
import codefight.model.memory.MemoryCell;

/**
 * This command swaps the argument A of the memory cell that's located at the current position plus the value of
 * argument A and argument B of the memory cell that's located at the current position plus the value of argument B.
 *
 * @author Eren Soenmez
 */
public class SwapCommandAi implements CommandAi {
    private static final int DEFAULT_MOVE = 1;
    @Override
    public void execute(Memory memory, Ai ai) {
        int position = ai.getCurrentPosition(memory.getMemorySize());
        MemoryCell currentCell = memory.getCell(position);

        int argumentAFirst = memory.getCell(position + currentCell.argumentA()).argumentA();
        int argumentBSecond = memory.getCell(position + currentCell.argumentB()).argumentB();

        memory.setCell(position + currentCell.argumentA(),
                new MemoryCell(
                        memory.getCell(position + currentCell.argumentA()).aiCommand(),
                        argumentBSecond,
                        memory.getCell(position + currentCell.argumentA()).argumentB()));

        memory.setCell(position + currentCell.argumentB(),
                new MemoryCell(
                        memory.getCell(position + currentCell.argumentB()).aiCommand(),
                        memory.getCell(position + currentCell.argumentB()).argumentA(),
                        argumentAFirst));

        memory.placeSymbol(position + currentCell.argumentA(), ai);
        memory.placeSymbol(position + currentCell.argumentB(), ai);
        ai.incrementCurrentPosition(DEFAULT_MOVE, memory.getMemorySize());
    }
}

