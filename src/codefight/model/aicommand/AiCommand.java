package codefight.model.aicommand;

import codefight.model.ai.Ai;
import codefight.model.memory.Memory;

/**
 * This enum represents the different AI-Commands in Code Fight.
 *
 * @author Eren Soenmez
 */
public enum AiCommand {
    /**
     * The AI-Command STOP.
     */
    STOP(new StopCommandAi()),

    /**
     * The AI-Command MOV_R.
     */
    MOV_R(new MovRCommandAi()),

    /**
     * The AI-Command MOV_I.
     */
    MOV_I(new MovICommandAi()),

    /**
     * The AI-Command ADD.
     */
    ADD(new AddCommandAi()),

    /**
     * The AI-Command ADD_R.
     */
    ADD_R(new AddRCommandAi()),

    /**
     * The AI-Command JMP.
     */
    JMP(new JmpCommandAi()),

    /**
     * The AI-Command JMZ.
     */
    JMZ(new JmzCommandAi()),

    /**
     * The AI-Command CMP.
     */
    CMP(new CmpCommandAi()),

    /**
     * The AI-Command SWAP.
     */
    SWAP(new SwapCommandAi());

    private final CommandAi commandImplementation;
    AiCommand(CommandAi commandImplementation) {
        this.commandImplementation = commandImplementation;
    }

    /**
     * Executes an AI-Command with arguments.
     *
     * @param memory the memory where the game is being played.
     * @param ai the AI that's going to execute an AI command in a memory cell.
     */
    public void executeCommand(Memory memory, Ai ai) {
        this.commandImplementation.execute(memory, ai);
    }
}

