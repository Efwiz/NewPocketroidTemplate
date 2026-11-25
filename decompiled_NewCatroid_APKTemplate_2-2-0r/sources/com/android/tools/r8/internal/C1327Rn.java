package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Rn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1327Rn {
    static final /* synthetic */ boolean g = true;
    private final C0722p1 a;
    private final C0800x2 b;
    private LinkedHashMap c = new LinkedHashMap();
    private LinkedHashMap d = new LinkedHashMap();
    private C0684j2 e;
    private List f;

    C1327Rn(final C0722p1 c0722p1, C0684j2 c0684j2) {
        this.a = c0722p1;
        this.b = c0684j2.G0().a(new Function() { // from class: com.android.tools.r8.internal.Rn$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1327Rn.a(c0722p1, (C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 a(C0722p1 c0722p1, C0767v2 c0767v2) {
        return c0767v2.W0() ? c0767v2 : c0722p1.Z1;
    }

    public final boolean b() {
        return this.e != null;
    }

    public final void a(com.android.tools.r8.graph.Y0 y0, InterfaceC0989Fn interfaceC0989Fn) {
        C0684j2 c0684j2 = this.e;
        if (c0684j2 == null) {
            this.c.put(y0, interfaceC0989Fn);
        } else if (c0684j2 != this.a.g4.g || (interfaceC0989Fn.h() && interfaceC0989Fn.f().b() == 0)) {
            this.d.put(y0, interfaceC0989Fn);
        } else {
            this.c.put(y0, interfaceC0989Fn);
        }
    }

    public final boolean a(C0684j2 c0684j2, ArrayList arrayList) {
        if (this.e != null) {
            return false;
        }
        this.e = c0684j2;
        this.f = arrayList;
        return true;
    }

    public final C1353Sn a() {
        if (g || b()) {
            return new C1353Sn(this.c, this.d, this.e, this.f, this.b);
        }
        throw new AssertionError();
    }
}
