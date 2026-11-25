package name.antonsmirnov.firmata.message;

import java.io.Serializable;

/* JADX INFO: loaded from: classes12.dex */
public abstract class Message implements Serializable {
    public boolean equals(Object obj) {
        return obj != null && obj.getClass().equals(getClass());
    }
}
