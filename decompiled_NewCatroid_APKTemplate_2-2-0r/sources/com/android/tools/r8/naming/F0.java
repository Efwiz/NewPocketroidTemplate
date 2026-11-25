package com.android.tools.r8.naming;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class F0 extends RuntimeException {
    private final int a;
    private final int b;
    private final boolean c;
    private final String d;

    F0(G0 g0, String str) {
        this(g0, str, false);
    }

    @Override // java.lang.Throwable
    public final String getMessage() {
        if (this.c) {
            return "Parse error [" + this.a + ":eol] " + this.d;
        }
        return "Parse error [" + this.a + ":" + this.b + "] " + this.d;
    }

    F0(G0 g0, String str, boolean z) {
        super(str);
        this.a = g0.i;
        this.b = g0.j;
        this.c = z;
        this.d = str;
    }
}
