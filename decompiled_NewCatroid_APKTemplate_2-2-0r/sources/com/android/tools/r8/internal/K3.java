package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.InterfaceC3910i3;
import java.io.OutputStreamWriter;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class K3 implements Comparable, InterfaceC2275j1 {
    public abstract Object a(InterfaceC3910i3 interfaceC3910i3, InterfaceC3910i3 interfaceC3910i4);

    public abstract void a(InterfaceC3905h3 interfaceC3905h3, InterfaceC3905h3 interfaceC3905h4);

    public abstract void a(OutputStreamWriter outputStreamWriter);

    public abstract AbstractC0744s2 c();

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return c().a(((K3) obj).c());
    }
}
