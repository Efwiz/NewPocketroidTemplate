package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3449yf implements Iterator {
    static final /* synthetic */ boolean c = true;
    private C3332x4 a;
    final /* synthetic */ C0827Af b;

    C3449yf(C0827Af c0827Af, C3332x4 c3332x4) {
        this.b = c0827Af;
        this.a = c3332x4;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a != null;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            return null;
        }
        C3332x4 c3332x4 = this.a;
        if (c3332x4.v() == 0) {
            this.a = null;
        } else {
            C0827Af c0827Af = this.b;
            C3332x4 c3332x5 = this.a;
            if (!C0827Af.f && c0827Af.e) {
                throw new AssertionError();
            }
            C3332x4 c3332x6 = c0827Af.b[c3332x5.v()];
            this.a = c3332x6;
            if (!c && c3332x6 == c3332x4) {
                throw new AssertionError();
            }
        }
        return c3332x4;
    }
}
