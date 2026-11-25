package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.He, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1035He implements N6 {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final C1179Me b;

    public C1035He(C0702m c0702m) {
        this.a = c0702m;
        this.b = new C1179Me(c0702m);
    }

    public static C1035He a(C0702m c0702m) {
        if (!c && !c0702m.Y().C1.d()) {
            throw new AssertionError();
        }
        if (c0702m.Y().C1.n().isEmpty()) {
            return null;
        }
        return new C1035He(c0702m);
    }

    @Override // com.android.tools.r8.internal.N6
    public final String a() {
        return "$retargeter";
    }

    @Override // com.android.tools.r8.internal.N6
    public final void a(C1150Lb c1150Lb, S6 s6) {
        Iterator it = this.a.Y().C1.n().values().iterator();
        while (it.hasNext()) {
            this.b.b((C2404kg) it.next(), s6);
        }
    }
}
