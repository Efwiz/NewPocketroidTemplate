package com.android.tools.r8.retrace;

import java.lang.Throwable;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface StreamSupplier<E extends Throwable> {
    String getNext() throws Throwable;
}
