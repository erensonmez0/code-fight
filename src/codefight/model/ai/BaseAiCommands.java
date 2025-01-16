package codefight.model.ai;

import codefight.model.memory.MemoryCell;


import java.util.List;

/**
 * This class represents the base of an AI that has the AI's name and it's commands.
 *
 * @param commands the list of AI commands that AI has.
 *
 * @author Eren Soenmez
 */
public record BaseAiCommands(List<MemoryCell> commands) {
}
