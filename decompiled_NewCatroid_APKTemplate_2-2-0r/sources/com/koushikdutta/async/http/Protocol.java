package com.koushikdutta.async.http;

import io.netty.handler.ssl.ApplicationProtocolNames;
import java.util.Hashtable;
import java.util.Locale;

/* JADX INFO: loaded from: classes10.dex */
public enum Protocol {
    HTTP_1_0("http/1.0"),
    HTTP_1_1(ApplicationProtocolNames.HTTP_1_1),
    SPDY_3(ApplicationProtocolNames.SPDY_3_1) { // from class: com.koushikdutta.async.http.Protocol.1
        @Override // com.koushikdutta.async.http.Protocol
        public boolean needsSpdyConnection() {
            return true;
        }
    },
    HTTP_2("h2-13") { // from class: com.koushikdutta.async.http.Protocol.2
        @Override // com.koushikdutta.async.http.Protocol
        public boolean needsSpdyConnection() {
            return true;
        }
    };

    private static final Hashtable<String, Protocol> protocols;
    private final String protocol;

    public boolean needsSpdyConnection() {
        return false;
    }

    static {
        Protocol protocol = HTTP_1_0;
        Protocol protocol2 = HTTP_1_1;
        Protocol protocol3 = SPDY_3;
        Protocol protocol4 = HTTP_2;
        Hashtable<String, Protocol> hashtable = new Hashtable<>();
        protocols = hashtable;
        hashtable.put(protocol.toString(), protocol);
        hashtable.put(protocol2.toString(), protocol2);
        hashtable.put(protocol3.toString(), protocol3);
        hashtable.put(protocol4.toString(), protocol4);
    }

    Protocol(String str) {
        this.protocol = str;
    }

    public static Protocol get(String str) {
        if (str == null) {
            return null;
        }
        return protocols.get(str.toLowerCase(Locale.US));
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.protocol;
    }
}
