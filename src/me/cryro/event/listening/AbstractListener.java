package me.cryro.event.listening;

import me.cryro.event.Event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author cryro
 * @param <E> extends Event
 */
public abstract class AbstractListener<E extends Event> {
    private final String identifier;
    private Class<E> event;

    public AbstractListener(String identifier) {
        this.identifier = identifier;

        Type generic = this.getClass().getGenericSuperclass();
        if (generic instanceof ParameterizedType) {
            for (Type type : ((ParameterizedType) generic).getActualTypeArguments()) {
                if (!(type instanceof Class) || !Event.class.isAssignableFrom((Class) type)) continue;
                this.event = (Class) type;
                break;
            }
        }
    }

    public AbstractListener() {
        this.identifier = null;

        Type generic = this.getClass().getGenericSuperclass();
        if (generic instanceof ParameterizedType) {
            for (Type type : ((ParameterizedType) generic).getActualTypeArguments()) {
                if (!(type instanceof Class) || !Event.class.isAssignableFrom((Class) type)) continue;
                this.event = (Class) type;
                break;
            }
        }
    }

    public Class<E> getEvent() {
        return event;
    }

    public String getIdentifier() {
        return identifier;
    }

    public abstract void run(E event);
}
