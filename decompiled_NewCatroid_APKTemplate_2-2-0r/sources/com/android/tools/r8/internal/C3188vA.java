package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3188vA {
    static final /* synthetic */ boolean b = true;
    private final C0702m a;

    public C3188vA(C0702m c0702m) {
        this.a = c0702m;
    }

    public final void a() {
        C0767v2 c0767v2A;
        if (!b && !this.a.Y().C1.d()) {
            throw new AssertionError();
        }
        for (C0729q2 c0729q2 : this.a.f().d()) {
            boolean zContainsKey = this.a.Y().C1.v().containsKey(c0729q2.d);
            if (c0729q2.b1() != null && (c0767v2A = c0729q2.b1().a()) != null && this.a.Y().C1.v().containsKey(c0767v2A) != zContainsKey) {
                c0729q2.M0();
            }
            if (!c0729q2.e1().isEmpty()) {
                ArrayList arrayList = new ArrayList();
                for (com.android.tools.r8.graph.J3 j3 : c0729q2.e1()) {
                    if (this.a.Y().C1.v().containsKey(j3.b()) == zContainsKey) {
                        arrayList.add(j3);
                    }
                }
                if (arrayList.size() != c0729q2.e1().size()) {
                    c0729q2.a((List) arrayList);
                }
            }
        }
    }
}
