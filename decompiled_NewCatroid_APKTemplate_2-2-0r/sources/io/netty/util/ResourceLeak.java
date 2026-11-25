package io.netty.util;

/* JADX INFO: loaded from: classes11.dex */
@Deprecated
public interface ResourceLeak {
    boolean close();

    void record();

    void record(Object obj);
}
