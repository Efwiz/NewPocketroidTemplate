package com.koushikdutta.async.http.socketio;

import android.net.Uri;
import com.koushikdutta.async.http.AsyncHttpPost;
import net.bytebuddy.description.type.TypeDescription;

/* JADX INFO: loaded from: classes10.dex */
public class SocketIORequest extends AsyncHttpPost {
    Config config;
    String endpoint;
    String query;

    public SocketIORequest(String str) {
        this(str, "");
    }

    public Config getConfig() {
        return this.config;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public String getQuery() {
        return this.query;
    }

    public SocketIORequest(String str, String str2) {
        this(str, str2, null);
    }

    public SocketIORequest(String str, String str2, String str3) {
        this(str, str2, str3, null);
    }

    public SocketIORequest(String str, String str2, String str3, Config config) {
        super(Uri.parse(str + (str3 == null ? "" : TypeDescription.Generic.OfWildcardType.SYMBOL + str3)).buildUpon().encodedPath("/socket.io/1/").build().toString());
        this.config = config == null ? new Config() : config;
        this.endpoint = str2;
        this.query = str3;
    }

    public static class Config {
        boolean randomizeReconnectDelay = false;
        long reconnectDelay = 1000;
        long reconnectDelayMax = 0;

        public void setRandomizeReconnectDelay(boolean z) {
            this.randomizeReconnectDelay = z;
        }

        public boolean isRandomizeReconnectDelay() {
            return this.randomizeReconnectDelay;
        }

        public void setReconnectDelay(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("reconnectDelay must be >= 0");
            }
            this.reconnectDelay = j;
        }

        public long getReconnectDelay() {
            return this.reconnectDelay;
        }

        public void setReconnectDelayMax(long j) {
            if (this.reconnectDelay < 0) {
                throw new IllegalArgumentException("reconnectDelayMax must be >= 0");
            }
            this.reconnectDelayMax = j;
        }

        public long getReconnectDelayMax() {
            return this.reconnectDelayMax;
        }
    }
}
