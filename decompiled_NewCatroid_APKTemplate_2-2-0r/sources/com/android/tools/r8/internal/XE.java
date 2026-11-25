package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class XE {
    public static final XE b = new XE();
    static final /* synthetic */ boolean c = true;
    private final InterfaceC0992Fq a;

    private XE() {
        this.a = new C1303Qq();
    }

    public final AbstractC3053tL a(int i) {
        return (AbstractC3053tL) this.a.getOrDefault(Integer.valueOf(i), T1.a);
    }

    public XE(C1303Qq c1303Qq) {
        if (!c && c1303Qq.isEmpty()) {
            throw new AssertionError();
        }
        this.a = c1303Qq;
    }
}
