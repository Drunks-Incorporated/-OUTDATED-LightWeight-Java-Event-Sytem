package test;

import me.cryro.event.Event;
import me.cryro.event.annotations.Cancellable;

@Cancellable
public class RandomEVent extends Event {
    private int setChange;

    public RandomEVent(int setChange) {
        this.setChange = setChange;
    }

    public int getSetChange() {
        return setChange;
    }

    public void setSetChange(int setChange) {
        this.setChange = setChange;
    }
}
