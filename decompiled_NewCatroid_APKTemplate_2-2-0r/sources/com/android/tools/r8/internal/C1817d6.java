package com.android.tools.r8.internal;

import java.util.Collections;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.d6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1817d6 {
    static final /* synthetic */ boolean c = true;
    private Set a = Collections.emptySet();
    private boolean b;

    public final void a(Set set) {
        boolean z = c;
        if (!z && set == null) {
            throw new AssertionError();
        }
        if (!z && set.isEmpty()) {
            throw new AssertionError();
        }
        this.a = set;
    }

    public final C1817d6 b() {
        this.b = true;
        return this;
    }

    public final C1890e6 a() {
        if (!c && this.a.isEmpty() && !this.b) {
            throw new AssertionError();
        }
        return new C1890e6(this.a, this.b);
    }
}
