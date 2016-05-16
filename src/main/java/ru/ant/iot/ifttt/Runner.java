package ru.ant.iot.ifttt;

import ru.ant.iot.ifttt.common.IftttScheduler;

/**
 * Created by Ant on 15.05.2016.
 */
public class Runner {

    public static void main(String[] args){
        IftttScheduler scheduler = new IftttScheduler();

        scheduler.addTrigger(new NewIpTrigger());
        //scheduler.addTrigger(new YourTriggerClassName());
    }

}
