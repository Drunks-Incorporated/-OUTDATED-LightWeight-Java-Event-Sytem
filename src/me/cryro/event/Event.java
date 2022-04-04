package me.cryro.event;

import me.cryro.event.annotations.Cancellable;
import me.cryro.event.exceptions.IllegalEventStateChange;

/**
 * @author cryro
 */
public class Event {
    private boolean cancelled;

    public boolean isCancelled() {
        return this.getClass().isAnnotationPresent(Cancellable.class) && cancelled;
    }

    public void setCancelled(boolean cancelled) {
        if (this.getClass().isAnnotationPresent(Cancellable.class)) {
            this.cancelled = cancelled;
            return;
        }

        throw new IllegalEventStateChange("You can't change " + this.getClass().getName() + " state without the right Annotation!");
    }
}
