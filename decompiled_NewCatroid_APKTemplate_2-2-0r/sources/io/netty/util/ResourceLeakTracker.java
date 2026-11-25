package io.netty.util;

/* JADX INFO: loaded from: classes11.dex */
public interface ResourceLeakTracker<T> {
    boolean close(T t);

    void record();

    void record(Object obj);
}
