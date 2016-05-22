package ru.ant.iot.ifttt;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by ant on 22.05.2016.
 */
public abstract class IftttTrigger implements Runnable {
    Logger log = Logger.getLogger(getClass());
    protected IftttMessage msg;

    public abstract String getIftttEventName();

    protected abstract IftttMessage initMessage();

    public String getIftttEventUrl() {
        return String.format("http://maker.ifttt.com/trigger/%1$s/with/key/%2$s", getIftttEventName(), getIftttMakerKey());
    }

    protected abstract String getIftttMakerKey();

    public void run() {
        msg = initMessage();
        if(triggerConditionIsMet()) sendMessageToIfttt();
    }

    protected abstract boolean triggerConditionIsMet();

    protected void sendMessageToIfttt() {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(getIftttEventUrl());
        try {
            String json = msg.getJson();
            post.setEntity(new StringEntity(json));
            post.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=UTF-8");
            client.execute(post);
            log.info("Message [" + json + "] to ifttt maker url [" + getIftttEventUrl() + "] was sent");
        } catch (IOException e) {
            log.error(e);
        }
    }
}
