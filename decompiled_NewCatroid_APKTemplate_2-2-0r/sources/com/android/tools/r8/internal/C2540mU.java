package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2540mU implements W5 {
    private final C2466lU a;
    private W5 b;
    int c;

    /* synthetic */ C2540mU(C2615nU c2615nU, int i) {
        this(c2615nU);
    }

    @Override // com.android.tools.r8.internal.W5
    public final byte f() {
        if (!this.b.hasNext()) {
            this.b = this.a.next().iterator();
        }
        this.c--;
        return this.b.f();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.c > 0;
    }

    @Override // java.util.Iterator
    public final Object next() {
        return Byte.valueOf(f());
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private C2540mU(C2615nU c2615nU) {
        C2466lU c2466lU = new C2466lU(c2615nU, 0);
        this.a = c2466lU;
        this.b = c2466lU.next().iterator();
        this.c = c2615nU.size();
    }
}
