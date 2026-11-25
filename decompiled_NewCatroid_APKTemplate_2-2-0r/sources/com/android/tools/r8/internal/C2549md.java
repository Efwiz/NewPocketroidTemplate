package com.android.tools.r8.internal;

import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.md, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2549md implements Serializable {
    private int a;

    C2549md(int i) {
        this.a = i;
    }

    public final void a(int i) {
        this.a += i;
    }

    public final int b() {
        return this.a;
    }

    public final int c() {
        int i = this.a;
        this.a = 0;
        return i;
    }

    public final void d() {
        this.a = 0;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C2549md) && ((C2549md) obj).a == this.a;
    }

    public final int hashCode() {
        return this.a;
    }

    public final String toString() {
        return Integer.toString(this.a);
    }

    public final int a() {
        int i = this.a - 1;
        this.a = i;
        return i;
    }
}
