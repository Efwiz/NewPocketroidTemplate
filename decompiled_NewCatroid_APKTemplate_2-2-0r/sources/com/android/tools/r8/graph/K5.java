package com.android.tools.r8.graph;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class K5 extends J5 {
    private Object e;

    public K5(C0702m c0702m, InterfaceC0593a0 interfaceC0593a0, Boolean bool) {
        super(c0702m, interfaceC0593a0);
        this.e = bool;
    }

    public final void a(Boolean bool) {
        this.e = bool;
        b();
    }

    public final Object f() {
        return this.e;
    }
}
