package com.android.tools.r8.graph;

import java.util.ArrayList;
import java.util.function.BiFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class W3 implements BiFunction {
    private final S3 a;
    private int b;

    private W3(S3 s3) {
        this.b = 0;
        this.a = s3;
    }

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        ArrayList arrayList = this.a.c;
        int i = this.b;
        this.b = i + 1;
        return ((W) arrayList.get(i)).v0();
    }
}
