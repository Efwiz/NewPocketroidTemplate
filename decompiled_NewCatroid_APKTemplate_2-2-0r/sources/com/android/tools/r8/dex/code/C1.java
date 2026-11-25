package com.android.tools.r8.dex.code;

import java.nio.ShortBuffer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1 implements InterfaceC0409a {
    static final /* synthetic */ boolean g = true;
    private final int a;
    private final int b;
    private final ShortBuffer c;
    private int e;
    private int d = 0;
    private boolean f = false;

    C1(int i, int i2, ShortBuffer shortBuffer) {
        this.b = i;
        this.a = i2;
        this.c = shortBuffer;
    }

    public final int a() {
        return this.d;
    }

    public final int b() {
        if (this.f) {
            this.f = false;
            return this.e;
        }
        int iC = c();
        this.e = iC & 255;
        this.f = true;
        return (iC >> 8) & 255;
    }

    public final int c() {
        boolean z = g;
        if (!z && this.f) {
            throw new AssertionError("Unread byte in cache.");
        }
        if (!z && this.d >= this.a) {
            throw new AssertionError();
        }
        short s = this.c.get(this.b + this.d);
        this.d++;
        return s;
    }
}
