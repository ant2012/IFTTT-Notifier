# IFTTT Maker Channel Notifier
Very simple and easy extendable <br/>

### Requirements
- Your IoT Device should run JRE
- You have to connect the Maker Channel on IFTTT

### Getting started

- Fork and import maven project in your best IDE
- Put your IFTTT Maker key to ***app.properties*** external resource
- Inherite your trigger class from ***AbstractTrigger***
```Java
public class MyFancyTrigger extends AbstractTrigger
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
    protected IftttMessage initMessage() {
        return new IftttMessage(value1, value2, value3);
    }
```
- Add your fancy trigger to scheduler in Runner class
```Java
        scheduler.addTrigger(new MyFancyTrigger());
```
- Package and deploy assembly to your IoT controller with installed JRE