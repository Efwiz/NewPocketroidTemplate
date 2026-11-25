package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1193Mr implements Iterable, InterfaceC2032fv {
    private final int a;
    private final int b;
    private final int c = 1;

    public AbstractC1193Mr(int i, int i2) {
        this.a = i;
        this.b = DN.a(i, i2, 1);
    }

    public final int a() {
        return this.a;
    }

    public final int b() {
        return this.b;
    }

    @Override // java.lang.Iterable
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public final C1220Nr iterator() {
        return new C1220Nr(this.a, this.b, this.c);
    }
}
