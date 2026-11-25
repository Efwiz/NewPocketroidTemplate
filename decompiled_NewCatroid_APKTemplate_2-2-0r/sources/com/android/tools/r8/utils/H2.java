package com.android.tools.r8.utils;

import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class H2 {
    final Class a;
    final String b;
    final Consumer c;
    final H2 d;

    private H2(String str, H2 h2, Consumer consumer) {
        this.a = null;
        this.b = str;
        this.d = h2;
        this.c = consumer;
    }

    public abstract Object a(Object obj);
}
