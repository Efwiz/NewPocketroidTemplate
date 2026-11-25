package com.android.tools.r8.graph;

import com.android.tools.r8.internal.OU;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.graph.k4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0693k4 {
    static final /* synthetic */ boolean f = true;
    private final IdentityHashMap a = new IdentityHashMap();
    private final ArrayList b = new ArrayList();
    private final ArrayList c = new ArrayList();
    private final Set d = OU.c();
    private int e;

    public final void a(InterfaceC0669h4 interfaceC0669h4) {
        if (!f && !interfaceC0669h4.h()) {
            throw new AssertionError();
        }
        this.a.putIfAbsent(interfaceC0669h4.f().getReference(), interfaceC0669h4);
    }

    public final void a(C0662g4 c0662g4) {
        this.b.add(c0662g4);
    }

    public final C0693k4 a(W0 w0) {
        this.c.add(w0);
        return this;
    }

    public final C0693k4 a(C0767v2 c0767v2) {
        this.d.add(c0767v2);
        return this;
    }

    public final C0693k4 a(int i) {
        this.e = i;
        return this;
    }

    public final AbstractC0701l4.a a() {
        return new AbstractC0701l4.a(this.a, this.b, this.c, this.e);
    }
}
