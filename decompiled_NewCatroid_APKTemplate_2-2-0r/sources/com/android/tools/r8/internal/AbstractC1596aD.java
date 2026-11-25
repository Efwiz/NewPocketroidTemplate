package com.android.tools.r8.internal;

import java.util.function.IntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1596aD {
    static final /* synthetic */ boolean e = true;
    protected final IntFunction a;
    protected final IntFunction b;
    protected AbstractC1476Wj c;
    protected Object d;

    protected AbstractC1596aD(AbstractC1476Wj abstractC1476Wj, IntFunction intFunction, IntFunction intFunction2) {
        this.c = abstractC1476Wj;
        this.a = intFunction;
        this.b = intFunction2;
        this.d = intFunction2.apply(2);
    }
}
