package me.cryro.event.manager;

import me.cryro.event.Event;
import me.cryro.event.annotations.EventSubscriber;
import me.cryro.event.listening.AbstractListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author cryro
 *
 * <p>Sorry this is such a mess, I will add more commits and fix code whenever I can</p>
 */
public class EventManager {
    private final ConcurrentLinkedQueue<AbstractListener> abstractListeners = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<Method, Object> methods = new ConcurrentHashMap<>();


    /**
     * @param listener either a "this" statement or an object that extends "AbstractListener"
     * @author CryroByte
     * @since 4/4/22 @ 2:21 AM
     *
     * <p>Registers Events and puts them into a list to be iterated through when dispatch is called</p>
     */
    public void register(Object listener) {
        Objects.requireNonNull(listener, "You cannot register a null event!");

        if (listener.getClass().getSuperclass().getName().equals(AbstractListener.class.getName()) && !abstractListeners.contains((AbstractListener) listener)) {
            abstractListeners.add((AbstractListener) listener);
            return;
        }

        for (Method method : listener.getClass().getMethods()) {
            if (method.isAnnotationPresent(EventSubscriber.class) && method.getParameterCount() == 1) {
                if (!methods.containsKey(method)) {
                    methods.put(method, listener);
                }
            }
        }

    }

    public void unregister(Object listener) {
        Objects.requireNonNull(listener, "You cannot unregister a null event!");

        if (listener.getClass().isInstance(AbstractListener.class)) {
            abstractListeners.remove(listener);
        }

        if (methods.containsValue(listener)) {
            methods.forEach(methods::remove);
        }
    }

    public void clearEvents() {
        clearAbstractEvents();

        clearMethodEvents();
    }

    private void clearAbstractEvents() {
        if (!abstractListeners.isEmpty()) {
            abstractListeners.clear();
        }
    }

    private void clearMethodEvents() {
        if (!methods.isEmpty()) {
            methods.clear();
        }
    }

    /**
     * @param event event
     * @return event
     * @author Cryrbyte
     * @since 4/4/22 @ 3:03 AM
     *
     * <p>iterates through a list of listeners to find if one had the correct event and does the same thing through a map</p>
     */
    public Event dispatch(Event event) {
        Objects.requireNonNull(event, "You cannot dispatch a null event!");

        if (!abstractListeners.isEmpty()) {
            for (AbstractListener listener : abstractListeners) {
                if (listener.getEvent().getName().equals(event.getClass().getName())) {
                    listener.run(event);
                }
            }
        }

        for (Map.Entry<Method, Object> entry : methods.entrySet()) {
            Object listener = entry.getValue();
            Method method = entry.getKey();

            if (Arrays.stream(method.getParameters()).anyMatch(parameter -> parameter.getType().getName().equals(event.getClass().getName()))) {
                try {
                    method.invoke(listener, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return event;
    }

}
