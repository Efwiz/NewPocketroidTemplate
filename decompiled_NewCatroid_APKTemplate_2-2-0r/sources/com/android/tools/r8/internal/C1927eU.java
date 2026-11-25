package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1927eU {
    private final ArrayList a = new ArrayList();
    private final ArrayList b = new ArrayList();

    public final C1927eU a(C2238iU c2238iU) {
        this.a.add(c2238iU);
        return this;
    }

    public final C1927eU a(C2082gU c2082gU) {
        this.b.add(c2082gU);
        return this;
    }

    public final C2308jU a() {
        return new C2308jU(this.a, this.b);
    }
}
