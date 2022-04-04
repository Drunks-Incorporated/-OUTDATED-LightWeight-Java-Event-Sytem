package test;

import me.cryro.event.annotations.EventSubscriber;
import me.cryro.event.manager.EventManager;

public class Main {

    public static EventManager manager = new EventManager();

    public static void main(String[] args) {
        manager.register(new TestLisener());
        manager.register(new Main());


        AnotherEvent event = new AnotherEvent(0);

        event.setCancelled(true);

        event.setFace(1);


        RandomEVent eVent = new RandomEVent(0);

        eVent.setSetChange(1);


        manager.dispatch(event);
        manager.dispatch(eVent);

        event.setFace(2);

        eVent.setSetChange(2);

        manager.dispatch(event);
        manager.dispatch(eVent);
    }

    @EventSubscriber
    public void event(AnotherEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getFace() == 1) {
            System.out.println("EVENT ONE IN main is working");
        }

        if (event.getFace() == 2) {
            System.out.println("MOST DEF WORKING main");
        }
    }
}
