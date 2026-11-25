package org.jetbrains.kotlin.com.intellij.util.messages.impl;

import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface MessageDeliveryListener {
    void messageDelivered(Topic<?> topic, String str, Object obj, long j);
}
