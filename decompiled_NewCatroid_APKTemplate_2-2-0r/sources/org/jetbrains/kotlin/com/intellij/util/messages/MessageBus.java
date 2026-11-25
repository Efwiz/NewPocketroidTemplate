package org.jetbrains.kotlin.com.intellij.util.messages;

import org.jetbrains.kotlin.com.intellij.openapi.Disposable;

/* JADX INFO: loaded from: classes3.dex */
public interface MessageBus extends Disposable {
    MessageBusConnection connect();

    MessageBusConnection connect(Disposable disposable);

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    void dispose();

    SimpleMessageBusConnection simpleConnect();

    <L> L syncPublisher(Topic<L> topic);
}
