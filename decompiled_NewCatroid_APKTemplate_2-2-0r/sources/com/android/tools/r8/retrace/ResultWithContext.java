package com.android.tools.r8.retrace;

import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface ResultWithContext<T> {
    void forEach(Consumer<T> consumer);

    RetraceStackTraceContext getContext();

    List<T> getLines();

    boolean isEmpty();
}
