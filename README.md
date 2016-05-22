# IFTTT Maker Channel Notifier
Very simple and easy extendable <br/>

### Requirements
- Your IoT Device should run JRE
- You have to connect the Maker Channel on IFTTT

### Getting started

- Fork and import maven project in your best IDE
- Inherite your trigger class from ***IftttTrigger***
```Java
public class MyFancyTrigger extends IftttTrigger
```
- Implement abstract methods
```Java
    @Override
    public String getIftttEventName() {
        return "MyEventOnIFTTT";
    }
```
```Java
    @Override
    protected String getIftttMakerKey() {
        return "XXXXXX";
    }
```
```Java
    @Override
    protected boolean triggerConditionIsMet() {
        return [myCondition];
    }
```
```Java
    @Override
    protected IftttMessage initMessage() {
        return new IftttMessage(value1, value2, value3);
    }
```
- Ifttt trigger is Runnable, so just add it to scheduler pool
```Java
    Executors.newScheduledThreadPool(1)
        .scheduleWithFixedDelay(new MyFancyTrigger(), initialDelay, delay, TimeUnit);
```
  or just take [TriggerPoolManager](https://github.com/ant2012/ant-commons/blob/master/src/main/java/ru/ant/common/TriggerPoolManager.java) class from ant-commons package
- Package and deploy assembly to your IoT controller with installed JRE
