package io.netty.util.internal;

/* JADX INFO: loaded from: classes11.dex */
public interface LongCounter {
    void add(long j);

    void decrement();

    void increment();

    long value();
}
