package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class IK extends TF {
    static final /* synthetic */ boolean e = true;
    private final C0702m b;
    private final EnumC0928Dk c;
    private final IdentityHashMap d = new IdentityHashMap();

    public IK(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        this.b = c0702m;
        this.c = enumC0928Dk;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Iterable b(com.android.tools.r8.graph.B5 b5, C0729q2 c0729q2) {
        return b5.a(c0729q2.getType());
    }

    @Override // com.android.tools.r8.internal.TF
    public final Collection a(C2688oE c2688oE, Object obj) {
        HK hk;
        com.android.tools.r8.graph.B5 b5 = (com.android.tools.r8.graph.B5) obj;
        if (!c2688oE.l()) {
            return AbstractC1639am.c(c2688oE);
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            Set setA = a(c0729q2);
            Set setA2 = a(b5, c0729q2);
            Iterator it2 = arrayList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    hk = null;
                    break;
                }
                hk = (HK) it2.next();
                hk.getClass();
                Iterator it3 = setA2.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        Iterator it4 = setA.iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                Iterator it5 = hk.d.iterator();
                                while (true) {
                                    if (!it5.hasNext()) {
                                        Iterator it6 = hk.c.iterator();
                                        while (true) {
                                            if (!it6.hasNext()) {
                                                break;
                                            }
                                            C0729q2 c0729q3 = (C0729q2) it6.next();
                                            if (c0729q3 != c0729q2 && setA2.contains(c0729q3)) {
                                                break;
                                            }
                                        }
                                    } else {
                                        C0729q2 c0729q4 = (C0729q2) it5.next();
                                        if (c0729q4 != c0729q2 && setA.contains(c0729q4)) {
                                            break;
                                        }
                                    }
                                }
                            } else {
                                C0729q2 c0729q5 = (C0729q2) it4.next();
                                if (!hk.a.contains(c0729q5) && hk.d.contains(c0729q5)) {
                                    break;
                                }
                            }
                        }
                    } else {
                        C0729q2 c0729q6 = (C0729q2) it3.next();
                        if (!hk.a.contains(c0729q6) && hk.c.contains(c0729q6)) {
                            break;
                        }
                    }
                }
            }
            if (hk != null) {
                hk.a(setA, setA2, c0729q2);
            } else {
                hk = new HK(setA, setA2, c0729q2);
                arrayList.add(hk);
            }
            this.d.put(c0729q2, hk.a);
        }
        LinkedList linkedList = new LinkedList();
        Iterator it7 = arrayList.iterator();
        while (it7.hasNext()) {
            C2688oE c2688oE2 = ((HK) it7.next()).a;
            if (!c2688oE2.m()) {
                linkedList.add(c2688oE2);
            } else {
                if (!e && c2688oE2.isEmpty()) {
                    throw new AssertionError();
                }
                this.d.remove(c2688oE2.b().getFirst());
            }
        }
        return linkedList;
    }

    @Override // com.android.tools.r8.internal.WL
    public final void d() {
        this.d.clear();
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "OnlyDirectlyConnectedOrUnrelatedInterfaces";
    }

    @Override // com.android.tools.r8.internal.WL
    public final boolean j() {
        return !this.b.Y().O0().b(this.c);
    }

    private Set a(C0729q2 c0729q2) {
        return a(c0729q2, new IK$$ExternalSyntheticLambda1());
    }

    private Set a(final com.android.tools.r8.graph.B5 b5, C0729q2 c0729q2) {
        return a(c0729q2, new Function() { // from class: com.android.tools.r8.internal.IK$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return IK.b(b5, (C0729q2) obj);
            }
        });
    }

    private Set a(C0729q2 c0729q2, final Function function) {
        final com.android.tools.r8.utils.O3 o3B = com.android.tools.r8.utils.O3.b((Set) new LinkedHashSet());
        o3B.d(c0729q2);
        o3B.a(new Consumer() { // from class: com.android.tools.r8.internal.IK$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o3B, function, (C0729q2) obj);
            }
        });
        if (e || !o3B.e(c0729q2)) {
            return o3B.a();
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.utils.O3 o3, Function function, C0729q2 c0729q2) {
        C2688oE c2688oE = (C2688oE) this.d.get(c0729q2);
        if (c2688oE != null) {
            o3.a((Iterable) c2688oE);
        }
        Iterator it = ((Iterable) function.apply(c0729q2)).iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2A = C0729q2.a(this.b.c((C0767v2) it.next()));
            if (c0729q2A != null) {
                o3.c(c0729q2A);
            }
        }
    }

    @Override // com.android.tools.r8.internal.TF
    public final Object a(Collection collection, ExecutorService executorService) {
        return com.android.tools.r8.graph.B5.a((C0702m<? extends C0680j>) this.b);
    }
}
