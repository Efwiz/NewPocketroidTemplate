package com.android.tools.r8.naming;

import com.android.tools.r8.internal.C1303Qq;
import com.android.tools.r8.internal.InterfaceC0992Fq;
import com.android.tools.r8.utils.C3888e1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class r {
    private final InterfaceC0992Fq a;

    /* JADX INFO: renamed from: $r8$lambda$wBZ5N9SAmxJLRX-vx8hIUeo_fgc, reason: not valid java name */
    public static /* synthetic */ ArrayList m2026$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc() {
        return new ArrayList();
    }

    private r(C1303Qq c1303Qq) {
        this.a = c1303Qq;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static r a(List list) {
        C1303Qq c1303Qq = new C1303Qq(16);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C3634k.b bVar = (C3634k.b) it.next();
            N0 n0 = bVar.c;
            for (int i = n0.a; i <= n0.b; i++) {
                ((List) c1303Qq.computeIfAbsent(Integer.valueOf(i), C3888e1.a(new Supplier() { // from class: com.android.tools.r8.naming.r$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return r.m2026$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc();
                    }
                }))).add(Integer.valueOf((i - n0.a) + bVar.a.a));
            }
        }
        return new r(c1303Qq);
    }

    public final int a(int i) {
        List list = (List) this.a.get(i);
        if (list == null) {
            return 0;
        }
        return ((Integer) list.get(0)).intValue();
    }

    public final void a(int i, Consumer consumer) {
        List list = (List) this.a.get(i);
        if (list != null) {
            list.forEach(consumer);
        }
    }
}
