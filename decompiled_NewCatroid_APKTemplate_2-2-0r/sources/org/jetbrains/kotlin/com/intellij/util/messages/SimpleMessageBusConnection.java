package org.jetbrains.kotlin.com.intellij.util.messages;

/* JADX INFO: loaded from: classes3.dex */
public interface SimpleMessageBusConnection {
    <L> void subscribe(Topic<L> topic, L l) throws IllegalStateException;
}
