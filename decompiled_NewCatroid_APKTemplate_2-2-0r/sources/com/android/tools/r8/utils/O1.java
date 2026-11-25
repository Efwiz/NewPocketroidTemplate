package com.android.tools.r8.utils;

import com.android.tools.r8.internal.C1513Xr;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class O1 extends LinkedHashMap {
    private final int a;
    private final int b;

    private O1() {
        super(8, 0.75f);
        this.a = 8;
        this.b = 8;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Map a(Object obj) {
        return new N1(this.b);
    }

    public static O1 e() {
        return new O1();
    }

    @Override // java.util.LinkedHashMap
    protected final boolean removeEldestEntry(Map.Entry entry) {
        return size() > this.a;
    }

    public final void a(Object obj, Object obj2, C1513Xr c1513Xr) {
        ((Map) computeIfAbsent(obj, new Function() { // from class: com.android.tools.r8.utils.O1$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj3) {
                return this.f$0.a(obj3);
            }
        })).putIfAbsent(obj2, c1513Xr);
    }
}
