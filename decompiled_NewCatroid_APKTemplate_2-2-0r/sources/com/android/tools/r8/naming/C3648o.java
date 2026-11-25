package com.android.tools.r8.naming;

/* JADX INFO: renamed from: com.android.tools.r8.naming.o, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3648o {
    private int a;
    private int b;

    private C3648o() {
        this.a = Integer.MAX_VALUE;
        this.b = Integer.MIN_VALUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(N0 n0) {
        if (n0 != null) {
            this.a = Math.min(this.a, n0.a);
            this.b = Math.max(this.b, n0.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int b() {
        if (c()) {
            return this.a;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c() {
        return this.a < Integer.MAX_VALUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a() {
        if (c()) {
            return this.b;
        }
        return -1;
    }
}
