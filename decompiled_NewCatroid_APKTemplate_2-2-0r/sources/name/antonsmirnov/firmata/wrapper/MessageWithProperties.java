package name.antonsmirnov.firmata.wrapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import name.antonsmirnov.firmata.message.Message;

/* JADX INFO: loaded from: classes12.dex */
public class MessageWithProperties implements Serializable {
    private Message message;
    private Map<String, Object> properties = new HashMap();

    public MessageWithProperties(Message message) {
        setMessage(message);
    }

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Object getProperty(String str) {
        return this.properties.get(str);
    }

    public void setProperty(String str, Object obj) {
        this.properties.put(str, obj);
    }

    public String toString() {
        return this.message.toString() + " -> " + this.properties.toString();
    }
}
