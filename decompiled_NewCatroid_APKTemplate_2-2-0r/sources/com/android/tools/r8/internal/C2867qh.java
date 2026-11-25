package com.android.tools.r8.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2867qh {
    public static final /* synthetic */ int b = 0;
    private final Map a;

    static {
        new C2867qh(0);
    }

    C2867qh() {
        this.a = new HashMap();
    }

    public static C2867qh a() {
        return new C2867qh();
    }

    public final C1012Gj a(int i, B0 b0) {
        return (C1012Gj) this.a.get(new C2798ph(i, b0));
    }

    private C2867qh(int i) {
        this.a = Collections.emptyMap();
    }

    public final void a(C1012Gj c1012Gj) {
        this.a.put(new C2798ph(c1012Gj.d.a, c1012Gj.a), c1012Gj);
    }
}
