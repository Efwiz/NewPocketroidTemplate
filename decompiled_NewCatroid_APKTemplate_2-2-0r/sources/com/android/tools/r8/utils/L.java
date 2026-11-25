package com.android.tools.r8.utils;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class L {
    L() {
    }

    public boolean a() {
        return false;
    }

    public final boolean b() {
        return a() || e();
    }

    public boolean c() {
        return d() || e();
    }

    public boolean d() {
        return false;
    }

    public boolean e() {
        return false;
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }
}
