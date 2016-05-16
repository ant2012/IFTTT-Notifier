package ru.ant.iot.ifttt.common;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * Created by ant on 16.05.2016.
 */
public abstract class AbstractTrigger extends Loggable implements Runnable {
    private IftttMessage preveousMsg = new IftttMessage();

    private String iftttMakerKey;


    public abstract String getIftttEventName();

    protected abstract IftttMessage initMessage();

    public String getIftttEventUrl() {
        return String.format("http://maker.ifttt.com/trigger/%1$s/with/key/%2$s", getIftttEventName(), getIftttMakerKey());
    }

    private String getIftttMakerKey() {
        if(iftttMakerKey!=null) return iftttMakerKey;
        return iftttMakerKey = PropertiesManager.getInstance().getProperty("ifttt_maker_key");
    }

    public void run() {
        IftttMessage msg = initMessage();

        if(msg.equals(preveousMsg)) return;
        preveousMsg = msg;
        if(preveousMsg.isEmpty()) return;

        sendMessageToIfttt();
    }

    private void sendMessageToIfttt() {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(getIftttEventUrl());
        try {
            String json = preveousMsg.getJson();
            post.setEntity(new StringEntity(json));
            post.setHeader("Content-type", "application/json");
            client.execute(post);
            log.info("Message [" + json + "] to ifttt maker url [" + getIftttEventUrl() + "] was sent");
        } catch (IOException e) {
            log.error(e);
        }
    }
}
