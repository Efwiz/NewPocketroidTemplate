package com.koushikdutta.async.http.socketio;

import com.koushikdutta.async.util.HashList;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes10.dex */
public class EventEmitter {
    HashList<EventCallback> callbacks = new HashList<>();

    interface OnceCallback extends EventCallback {
    }

    void onEvent(String str, JSONArray jSONArray, Acknowledge acknowledge) {
        ArrayList<EventCallback> arrayList = this.callbacks.get(str);
        if (arrayList == null) {
            return;
        }
        Iterator<EventCallback> it = arrayList.iterator();
        while (it.hasNext()) {
            EventCallback next = it.next();
            next.onEvent(jSONArray, acknowledge);
            if (next instanceof OnceCallback) {
                it.remove();
            }
        }
    }

    public void addListener(String str, EventCallback eventCallback) {
        on(str, eventCallback);
    }

    public void once(String str, final EventCallback eventCallback) {
        on(str, new OnceCallback() { // from class: com.koushikdutta.async.http.socketio.EventEmitter.1
            @Override // com.koushikdutta.async.http.socketio.EventCallback
            public void onEvent(JSONArray jSONArray, Acknowledge acknowledge) {
                eventCallback.onEvent(jSONArray, acknowledge);
            }
        });
    }

    public void on(String str, EventCallback eventCallback) {
        this.callbacks.add(str, eventCallback);
    }

    public void removeListener(String str, EventCallback eventCallback) {
        ArrayList<EventCallback> arrayList = this.callbacks.get(str);
        if (arrayList == null) {
            return;
        }
        arrayList.remove(eventCallback);
    }
}
