package org.jetbrains.kotlin.com.intellij.util.messages;

import org.jetbrains.kotlin.com.intellij.openapi.Disposable;

/* JADX INFO: loaded from: classes3.dex */
public interface MessageBusConnection extends Disposable, SimpleMessageBusConnection {
    void deliverImmediately();
}
