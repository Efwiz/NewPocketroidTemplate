package com.android.tools.r8.internal;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2234iQ {
    private final InterfaceC2600nG a;
    private final H00 b;
    private final C2668o20 c;
    private final C2234iQ d;
    private final List e;
    private final LinkedHashMap f;
    private final List g;

    public C2234iQ(InterfaceC2600nG interfaceC2600nG, H00 h00, C2668o20 c2668o20, C2234iQ c2234iQ, List list) {
        AbstractC0936Ds.c(interfaceC2600nG, "strings");
        AbstractC0936Ds.c(h00, SpecTypePair.NAME_types);
        AbstractC0936Ds.c(c2668o20, "versionRequirements");
        AbstractC0936Ds.c(list, "contextExtensions");
        this.a = interfaceC2600nG;
        this.b = h00;
        this.c = c2668o20;
        this.d = c2234iQ;
        this.e = list;
        this.f = new LinkedHashMap();
        InterfaceC3343xE.a.getClass();
        this.g = C3267wE.a();
    }

    public final List a() {
        return this.g;
    }

    public final InterfaceC2600nG b() {
        return this.a;
    }

    public final H00 c() {
        return this.b;
    }

    public final C2668o20 d() {
        return this.c;
    }

    public final String a(int i) {
        InterfaceC2600nG interfaceC2600nG = this.a;
        AbstractC0936Ds.c(interfaceC2600nG, "<this>");
        String strC = interfaceC2600nG.c(i);
        return interfaceC2600nG.b(i) ? "." + strC : strC;
    }

    public final String b(int i) {
        return this.a.a(i);
    }

    public final Integer c(int i) {
        Integer num = (Integer) this.f.get(Integer.valueOf(i));
        if (num != null) {
            return num;
        }
        C2234iQ c2234iQ = this.d;
        if (c2234iQ != null) {
            return c2234iQ.c(i);
        }
        return null;
    }

    public /* synthetic */ C2234iQ(C0970Eu c0970Eu, H00 h00, C2668o20 c2668o20, List list, int i) {
        this(c0970Eu, h00, c2668o20, (C2234iQ) null, (i & 16) != 0 ? C1940eg.a : list);
    }

    public final C2234iQ a(List list) {
        C2234iQ c2234iQ = new C2234iQ(this.a, this.b, this.c, this, this.e);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C1840dP c1840dP = (C1840dP) it.next();
            c2234iQ.f.put(Integer.valueOf(c1840dP.m()), Integer.valueOf(c1840dP.l()));
        }
        return c2234iQ;
    }
}
