package test;

import me.cryro.event.listening.AbstractListener;

public class TestLisener extends AbstractListener<RandomEVent> {

    public TestLisener() {
        super("test-listener");
    }

    @Override
    public void run(RandomEVent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getSetChange() == 1) {
            System.out.println("EVENT ONE IN Listener is working");
        }

        if (event.getSetChange() == 2) {
            System.out.println("MOST DEF WORKING");
        }
    }
}
