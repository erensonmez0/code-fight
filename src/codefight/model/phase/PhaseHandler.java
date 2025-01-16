package codefight.model.phase;

/**
 * This class handles the execution of phases.
 *
 * @author Eren Soenmez
 */
public class PhaseHandler {
    private Phase currentPhase = Phase.INITIALIZATION;

    /**
     * Sets the current phase to next phase.
     */
    public void nextPhase() {
        currentPhase = Phase.values()[(currentPhase.ordinal() + 1) % Phase.values().length];
    }

    /**
     * Resets the current phase to its initial state.
     */
    public void reset() {
        this.currentPhase = Phase.INITIALIZATION;
    }

    /**
     * Returns the current phase.
     *
     * @return the current phase.
     */
    public Phase getCurrentPhase() {
        return currentPhase;
    }

}

