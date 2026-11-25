package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.utils.InterfaceC3905h3;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class CW implements InterfaceC2275j1, Comparable<CW> {
    public abstract <E1 extends Exception, E2 extends Exception> void a(InterfaceC3905h3<? super C3362xW, E1> interfaceC3905h3, InterfaceC3905h3<? super C3510zW, E2> interfaceC3905h4) throws Exception;

    public abstract void a(Appendable appendable);

    public abstract AbstractC0744s2 c();

    @Override // java.lang.Comparable
    public final int compareTo(CW cw) {
        return c().a(cw.c());
    }
}
