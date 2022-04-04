package test;

import me.cryro.event.Event;
import me.cryro.event.annotations.Cancellable;

@Cancellable
public class AnotherEvent extends Event {
    private int face;

    public AnotherEvent(int face) {
        this.face = face;
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        this.face = face;
    }
}
