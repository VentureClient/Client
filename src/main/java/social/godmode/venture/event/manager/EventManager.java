package social.godmode.venture.event.manager;

import lombok.Getter;
import social.godmode.venture.event.Event;
import social.godmode.venture.event.data.EventInfo;
import social.godmode.venture.event.manager.sender.EventSender;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * The manager for all events.
 *
 * @author Nick
 * */
@Getter
public class EventManager {

    /** The registered objects */
    private static final List<Object> registeredObjects = new ArrayList<>();

    /** The registered methods */
    private static final List<EventSender> registeredSenders = new ArrayList<>();

    /**
     * Register an object to the event manager.
     *
     * @param object The object to register.
     * */
    public static void register(Object object) {
        if(isRegistered(object))
            return;

        registeredObjects.add(object);
    }

    /**
     * unregister an object from the event manager.
     *
     * @param object The object to unregister.
     * */
    public static void unregister(Object object) {
        if(!isRegistered(object))
            return;

        registeredObjects.remove(object);
    }

    /**
     * Checks if an object is registered.
     *
     * @param object The object to check.
     * */
    public static boolean isRegistered(Object object) {
        return registeredObjects.contains(object);
    }

    /**
     * Runs all events.
     *
     * @param event The event to run.
     * */
    public static void runEvent(Event event) {
        List<Object> registeredObjects = new ArrayList<>(EventManager.registeredObjects);

        for (Object object : registeredObjects) {
            for (Method method : getMethods(object.getClass())) {

                if(method.getParameterTypes().length == 1
                        && method.isAnnotationPresent(EventInfo.class)
                        && (method.getParameterTypes()[0].equals(event.getClass()) || method.getParameterTypes()[0].equals(Event.class))) {
                    new EventSender(event, method, object);
                }
            }
        }
    }

    /**
     * Gets all methods from a class.
     *
     * @param clazz The class to get the methods from.
     * @return The methods.
     * */
    public static Method[] getMethods(Class<?> clazz) {
        return clazz.getMethods();
    }

}
