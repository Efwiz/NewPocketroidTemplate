package com.android.tools.r8.utils;

/* JADX INFO: renamed from: com.android.tools.r8.utils.m1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3932m1 {
    static final /* synthetic */ boolean b = true;
    private int a;

    public C3932m1() {
    }

    public void a(int i) {
        if (!b && i <= 0) {
            throw new AssertionError();
        }
        this.a -= i;
    }

    public int b() {
        int i = this.a;
        d(1);
        return i;
    }

    public final int c(int i) {
        int i2 = this.a;
        e(i);
        return i2;
    }

    public void d(int i) {
        if (!b && i < 0) {
            throw new AssertionError();
        }
        this.a += i;
    }

    public void e(int i) {
        this.a = i;
    }

    public C3932m1(int i) {
        e(i);
    }

    public int a() {
        return this.a;
    }

    public final int b(int i) {
        int i2 = this.a;
        d(i);
        return i2;
    }

    public void c() {
        d(1);
    }

    public int d() {
        d(1);
        return a();
    }

    public void a(boolean z) {
        if (z) {
            c();
        }
    }
}
