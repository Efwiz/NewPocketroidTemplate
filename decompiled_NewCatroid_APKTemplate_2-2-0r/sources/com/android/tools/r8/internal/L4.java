package com.android.tools.r8.internal;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface L4 extends I4 {
    void a(BiConsumer biConsumer);

    Map e();

    Object get(Object obj);

    Object getOrDefault(Object obj, Object obj2);

    Set keySet();

    /* JADX INFO: renamed from: values */
    Set mo1078values();
}
