package com.android.tools.r8.ir.regalloc;

import com.android.tools.r8.graph.X;
import com.android.tools.r8.internal.S10;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class a implements Comparable {
    static final /* synthetic */ boolean f = true;
    final S10 a;
    final X b;
    final int c;
    final int d;
    final int e;

    a(S10 s10, int i, int i2, int i3) {
        if (!f && !s10.A()) {
            throw new AssertionError();
        }
        this.a = s10;
        this.b = s10.s();
        this.c = i;
        this.d = i2;
        this.e = i3;
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final int compareTo(a aVar) {
        int i = this.d;
        int i2 = aVar.d;
        return i != i2 ? Integer.compare(i, i2) : Integer.compare(this.e, aVar.e);
    }

    public final String toString() {
        return this.b + " @ r" + this.c + ": " + new e(this.d, this.e);
    }
}
