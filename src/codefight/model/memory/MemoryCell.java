package codefight.model.memory;

import codefight.model.aicommand.AiCommand;

/**
 * This class represents a memory cell that includes an AI-Command, argument A and argument B.
 *
 * @param aiCommand AI-Command of an AI's command.
 * @param argumentA argument A of an AI's command.
 * @param argumentB argument B of an AI's command.
 *
 * @author Eren Soenmez
 */
public record MemoryCell(AiCommand aiCommand, int argumentA, int argumentB) {
}

