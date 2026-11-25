package com.artemis.link;

/* JADX INFO: loaded from: classes8.dex */
public interface LinkListener {
    void onLinkEstablished(int i, int i2);

    void onLinkKilled(int i, int i2);

    void onTargetChanged(int i, int i2, int i3);

    void onTargetDead(int i, int i2);
}
