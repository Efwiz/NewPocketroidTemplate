package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class W1 extends Exception {
    public final transient AbstractC3097u a;

    public W1(AbstractC3097u abstractC3097u, String str) {
        super(str);
        this.a = abstractC3097u;
    }

    public W1(AbstractC3097u abstractC3097u, String str, Exception exc) {
        super(str, exc);
        this.a = abstractC3097u;
    }

    public W1(AbstractC3097u abstractC3097u, String str, Object obj, R10 r10) {
        super((str == null ? "Expected " : str.concat(": expected ")) + obj + ", but found " + r10);
        this.a = abstractC3097u;
    }
}
