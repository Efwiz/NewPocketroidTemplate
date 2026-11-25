package com.koushikdutta.async.http;

/* JADX INFO: loaded from: classes10.dex */
public interface RequestLine {
    String getMethod();

    ProtocolVersion getProtocolVersion();

    String getUri();
}
