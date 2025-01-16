package codefight.model.aicommand;

import codefight.model.ai.Ai;
import codefight.model.memory.Memory;
import codefight.model.memory.MemoryCell;

/**
 * This command adds the values of argument A and argument B and saves it in the argument B.
 *
 * @author Eren Soenmez
 */
public class AddCommandAi implements CommandAi {
    private static final int DEFAULT_MOVE = 1;
    private static final int MAX_INT = Integer.MAX_VALUE;

    @Override
    public void execute(Memory memory, Ai ai) {
        int position = ai.getCurrentPosition(memory.getMemorySize());
        MemoryCell currentCell = memory.getCell(position);

        long valueArgumentA = currentCell.argumentA();
        long valueArgumentB = currentCell.argumentB();
        long totalValue = valueArgumentA + valueArgumentB;

        int sumOfArguments;
        if (totalValue > MAX_INT) {
            sumOfArguments = (int) (valueArgumentA % memory.getMemorySize()) + (int) (valueArgumentB % memory.getMemorySize());
        } else {
            sumOfArguments = (int) totalValue;
        }

        memory.setCell(position, new MemoryCell(currentCell.aiCommand(), currentCell.argumentA(), sumOfArguments));
        memory.placeSymbol(position, ai);
        ai.incrementCurrentPosition(DEFAULT_MOVE, memory.getMemorySize());
    }
}

