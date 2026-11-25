package io.github.rosemoe.sora.event;

import io.github.rosemoe.sora.event.Event;

/* JADX INFO: loaded from: classes11.dex */
public interface EventReceiver<T extends Event> {
    void onReceive(T t, Unsubscribe unsubscribe);
}
