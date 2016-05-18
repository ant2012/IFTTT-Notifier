package ru.ant.iot.ifttt;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import ru.ant.common.App;
import ru.ant.common.Loggable;

import java.io.IOException;

/**
 * Created by ant on 16.05.2016.
 */
public abstract class IftttTrigger extends Loggable implements Runnable {
    private IftttMessage preveousMsg = new IftttMessage();
    private String iftttMakerKey;

    public abstract String getIftttEventName();

    protected abstract IftttMessage initMessage();

    public String getIftttEventUrl() {
        return String.format("http://maker.ifttt.com/trigger/%1$s/with/key/%2$s", getIftttEventName(), getIftttMakerKey());
    }

    private String getIftttMakerKey() {
        if(iftttMakerKey!=null) return iftttMakerKey;
        return iftttMakerKey = App.getProperty("ifttt.maker.key");
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
            post.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=UTF-8");
            client.execute(post);
            log.info("Message [" + json + "] to ifttt maker url [" + getIftttEventUrl() + "] was sent");
        } catch (IOException e) {
            log.error(e);
        }
    }
}
