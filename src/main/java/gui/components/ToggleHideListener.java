package gui.components;

/**
 * ToggleHideListeners are used to detect
 * when a HidableDecorator's hidden state
 * is changed.
 * 
 * @author Matt Crow
 */
@FunctionalInterface
public interface ToggleHideListener {
    /**
     * Fired whenever the hidden state of a HidableDecorator
     * this is listening to changes.
     * 
     * @param isHidden the new state of the HidableDecorator 
     */
    public abstract void hideStateToggled(boolean isHidden);
}
