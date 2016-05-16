package ru.ant.iot.ifttt.common;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ant on 16.05.2016.
 */
public class IftttScheduler extends Loggable {
    private final ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
    private final String SCHEDULER_PROPERTY_TEMPLATE = "IftttScheduler.%1$s.%2$s";

    public void addTrigger(AbstractTrigger trigger) {
        String className = trigger.getClass().getSimpleName();
        int initialDelay = Integer.parseInt(getProp(className, "initialDelay"));
        int delay = Integer.parseInt(getProp(className, "delay"));
        log.info(String.format("Sheduler started for: %1$s; initialDelay=%2$ss; delay=%3$ss", className, initialDelay, delay));
        pool.scheduleWithFixedDelay(trigger, initialDelay, delay, TimeUnit.SECONDS);
    }

    private String getProp(String className, String key) {
        PropertiesManager manager = PropertiesManager.getInstance();
        String fullKey = String.format(SCHEDULER_PROPERTY_TEMPLATE, className, key);
        String defaultKey = String.format(SCHEDULER_PROPERTY_TEMPLATE, "default", key);
        String result = manager.getProperty(fullKey);
        return result != null ? result : manager.getProperty(defaultKey);
    }
}
