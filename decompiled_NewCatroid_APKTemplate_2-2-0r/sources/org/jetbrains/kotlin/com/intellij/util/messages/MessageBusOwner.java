package org.jetbrains.kotlin.com.intellij.util.messages;

/* JADX INFO: loaded from: classes3.dex */
public interface MessageBusOwner {
    Object createListener(ListenerDescriptor listenerDescriptor);

    boolean isDisposed();

    default boolean isParentLazyListenersIgnored() {
        return false;
    }
}
