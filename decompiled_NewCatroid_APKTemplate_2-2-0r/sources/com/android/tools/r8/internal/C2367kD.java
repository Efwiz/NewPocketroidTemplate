package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2367kD extends C1213Nk {
    private final Function j;

    public C2367kD(C0722p1 c0722p1, Function function) {
        super(c0722p1);
        this.j = function;
    }

    @Override // com.android.tools.r8.internal.AbstractC3402y0
    final C0767v2 a(String str) {
        return (C0767v2) this.j.apply(str);
    }
}
