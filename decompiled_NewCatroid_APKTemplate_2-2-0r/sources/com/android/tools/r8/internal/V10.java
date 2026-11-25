package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class V10 {
    static final /* synthetic */ boolean b = true;
    private final IdentityHashMap a = new IdentityHashMap();

    public final U10 a(S10 s10) {
        return (U10) this.a.computeIfAbsent(s10, new Function() { // from class: com.android.tools.r8.internal.V10$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new U10((S10) obj);
            }
        });
    }

    public final void b() {
        com.android.tools.r8.utils.O3 o3D = com.android.tools.r8.utils.O3.d((Iterable) this.a.values());
        while (o3D.c()) {
            for (Set set : new C2993sU(new Function() { // from class: com.android.tools.r8.internal.V10$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((U10) obj).a();
                }
            }).a((U10) o3D.f())) {
                Iterator it = set.iterator();
                if (!b && !it.hasNext()) {
                    throw new AssertionError();
                }
                U10 u10 = (U10) it.next();
                while (it.hasNext()) {
                    U10 u11 = (U10) it.next();
                    for (U10 u12 : u11.c) {
                        u12.d.remove(u11);
                        u12.d.add(u10);
                        u10.c.add(u12);
                    }
                    u11.c.clear();
                    for (U10 u13 : u11.d) {
                        u13.c.remove(u11);
                        u13.c.add(u10);
                        u10.d.add(u13);
                    }
                    u11.d.clear();
                    u10.b.addAll(u11.b);
                    this.a.put(u11.a, u10);
                }
                o3D.b((Iterable) set);
            }
        }
    }

    public static void a(U10 u10, U10 u11) {
        u11.c.add(u10);
        u10.d.add(u11);
    }

    public final Collection a() {
        return this.a.values();
    }
}
