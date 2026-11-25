package com.hivemq.client.internal.checkpoint;

/* JADX INFO: loaded from: classes10.dex */
public interface Confirmable {
    boolean confirm();

    long getId();
}
