package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3306wj {
    String a = System.lineSeparator();
    StringBuilder b = new StringBuilder();
    String c = "";

    AbstractC3306wj() {
    }

    final void a(String str) {
        this.b.append(str);
    }

    final void b(String str) {
        this.b.append(this.c);
        this.b.append(str);
        this.b.append(this.a);
    }

    final void c(String str) {
        this.b.append(str);
        this.b.append(this.a);
    }

    final void d(String str) {
        this.b.append(this.c);
        this.b.append(str);
    }

    final void e(String str) {
        this.c = str;
    }

    public final String toString() {
        return this.b.toString();
    }
}
