package ru.ant.iot.ifttt;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;

/**
 * Created by ant on 16.05.2016.
 */
public class IftttMessage {
    private String value1;
    private String value2;
    private String value3;

    public IftttMessage() {
        this("", "", "");
    }

    public IftttMessage(String value1) {
        this(value1, "", "");
    }

    public IftttMessage(String value1, String value2) {
        this(value1, value2, "");
    }

    public IftttMessage(String value1, String value2, String value3) {
        this.value1 = value1!=null ? value1 : "";
        this.value2 = value2!=null ? value2 : "";
        this.value3 = value3!=null ? value3 : "";
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof IftttMessage)) return false;
        IftttMessage o = (IftttMessage) obj;
        return value1.equals(o.value1) && value2.equals(o.value2) && value3.equals(o.value3);
    }

    public String getJson() {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder jsonRoot = factory.createObjectBuilder()
                .add("value1", value1)
                .add("value2", value2)
                .add("value3", value3);
        return jsonRoot.build().toString();
    }

    public boolean isEmpty(){
        return value1.isEmpty() && value2.isEmpty() && value3.isEmpty();
    }

    public String getValue1() {
        return value1;
    }

    public String getValue2() {
        return value2;
    }

    public String getValue3() {
        return value3;
    }
}
