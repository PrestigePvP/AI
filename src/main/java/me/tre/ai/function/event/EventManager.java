package me.tre.ai.function.event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class EventManager {


    private final class MethodData {

        private final Object source;

        private final Method target;

        private final EventPriority priority;


        public MethodData(Object source, Method target, EventPriority priority) {
            this.source = source;
            this.target = target;
            this.priority = priority;
        }


        public EventPriority getPriority() {
            return priority;
        }


        public Object getSource() {
            return source;
        }


        public Method getTarget() {
            return target;
        }

    }


    private static final Map<Class<? extends Event>, List<MethodData>> REGISTRY_MAP = new HashMap<>();


    public final Event call(final Event event) {
        List<MethodData> dataList = REGISTRY_MAP.get(event.getClass());

        if (dataList != null)
            for (final MethodData data : dataList)
                invoke(data, event);

        return event;
    }


    public void cleanMap(boolean onlyEmptyEntries) {
        Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet()
                .iterator();

        while (mapIterator.hasNext())
            if (!onlyEmptyEntries || mapIterator.next().getValue().isEmpty())
                mapIterator.remove();
    }


    private void invoke(MethodData data, Event argument) {
        try {
            data.getTarget().invoke(data.getSource(), argument);
        } catch (Exception ignored) {
        }
    }


    private boolean isMethodBad(Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventHandler.class);
    }


    private boolean isMethodBad(Method method, Class<? extends Event> eventClass) {
        return isMethodBad(method) || !method.getParameterTypes()[0].equals(eventClass);
    }



    private void register(Method method, Object object) {
        Class<? extends Event> indexClass = (Class<? extends Event>) method.getParameterTypes()[0];
        final MethodData data = new MethodData(object, method, method.getAnnotation(EventHandler.class).priority());


        if (!data.getTarget().isAccessible())
            data.getTarget().setAccessible(true);

        if (REGISTRY_MAP.containsKey(indexClass)) {
            if (!REGISTRY_MAP.get(indexClass).contains(data)) {
                REGISTRY_MAP.get(indexClass).add(data);
                sortListValue(indexClass);
            }
        } else
            REGISTRY_MAP.put(indexClass, new CopyOnWriteArrayList<MethodData>() {
                private final long serialVersionUID = 666L;

                {
                    add(data);
                }
            });
    }


    public void register(Object object) {
        for (final Method method : object.getClass().getDeclaredMethods()) {
            if (isMethodBad(method))
                continue;

            register(method, object);
        }
    }


    public void register(Object object, Class<? extends Event> eventClass) {
        for (final Method method : object.getClass().getDeclaredMethods()) {
            if (isMethodBad(method, eventClass))
                continue;

            register(method, object);
        }
    }


    public void removeEntry(Class<? extends Event> indexClass) {
        Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet()
                .iterator();

        while (mapIterator.hasNext())
            if (mapIterator.next().getKey().equals(indexClass)) {
                mapIterator.remove();
                break;
            }
    }


    private void sortListValue(Class<? extends Event> indexClass) {
        List<MethodData> sortedList = new CopyOnWriteArrayList<>();

        for (final EventPriority priority : EventPriority.VALUE_ARRAY)
            sortedList.addAll(REGISTRY_MAP.get(indexClass).stream().filter(data -> data.getPriority() == priority).collect(Collectors.toList()));

        // Overwriting the existing entry.
        REGISTRY_MAP.put(indexClass, sortedList);
    }


    public void unregister(Object object) {
        for (final List<MethodData> dataList : REGISTRY_MAP.values())
            for (final MethodData data : dataList)
                if (data.getSource().equals(object))
                    dataList.remove(data);

        cleanMap(true);
    }


    public void unregister(Object object, Class<? extends Event> eventClass) {
        if (REGISTRY_MAP.containsKey(eventClass)) {
            REGISTRY_MAP.get(eventClass).stream().filter(data -> data.getSource().equals(object)).forEach(data -> REGISTRY_MAP.get(eventClass).remove(data));

            cleanMap(true);
        }
    }

}
