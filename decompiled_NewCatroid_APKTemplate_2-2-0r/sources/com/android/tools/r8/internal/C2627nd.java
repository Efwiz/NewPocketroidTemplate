package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0674i0;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0649f0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3988x2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2627nd {
    static final /* synthetic */ boolean d = true;
    public static final /* synthetic */ int e = 0;
    private final C3012sl a;
    private final VE b = VE.a();
    private final C0722p1 c;

    public C2627nd(C0702m c0702m, C3012sl c3012sl) {
        this.a = c3012sl;
        this.c = c0702m.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(C0633d0 c0633d0) {
        return !a(c0633d0.b, this.c);
    }

    public final void a(AbstractC0674i0 abstractC0674i0, InterfaceC2794pd interfaceC2794pd) {
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        for (C0729q2 c0729q2 : (ArrayList) abstractC0674i0.c()) {
            a(c0729q2, linkedList, linkedList2, interfaceC2794pd);
            if (!linkedList2.isEmpty()) {
                a(c0729q2, linkedList, linkedList2);
                linkedList.clear();
                linkedList2.clear();
            }
        }
    }

    private void a(C0729q2 c0729q2, LinkedList linkedList, LinkedList linkedList2) {
        Iterator it = linkedList2.iterator();
        while (it.hasNext()) {
            com.android.tools.r8.graph.W0 w0 = (com.android.tools.r8.graph.W0) it.next();
            Iterator<com.android.tools.r8.graph.W0> it2 = c0729q2.P1().iterator();
            while (it2.hasNext()) {
                if (it2.next().getReference().equals(w0.getReference())) {
                    throw new C1367Tb(String.format("Cannot process CovariantReturnType annotation: Class %s already has a method \"%s\"", c0729q2.getType(), w0.r0()));
                }
            }
        }
        Iterator it3 = linkedList.iterator();
        while (it3.hasNext()) {
            com.android.tools.r8.graph.W0 w1 = (com.android.tools.r8.graph.W0) it3.next();
            C0658g0 c0658g0S0 = w1.s0();
            Predicate predicate = new Predicate() { // from class: com.android.tools.r8.internal.nd$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.b((C0633d0) obj);
                }
            };
            c0658g0S0.getClass();
            w1.a(c0658g0S0.a(C3988x2.a(predicate)));
        }
        c0729q2.b((Collection) linkedList2);
    }

    private void a(C0729q2 c0729q2, final LinkedList linkedList, final LinkedList linkedList2, final InterfaceC2794pd interfaceC2794pd) {
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.nd$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(linkedList, linkedList2, interfaceC2794pd, (com.android.tools.r8.graph.o5) obj);
            }
        };
        c0729q2.getClass();
        c0729q2.h(consumer, CM.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List list, List list2, InterfaceC2794pd interfaceC2794pd, com.android.tools.r8.graph.o5 o5Var) {
        for (C0633d0 c0633d0 : o5Var.e().s0().c) {
            if (a(c0633d0.b, this.c)) {
                list.add(o5Var.e());
                if (!d) {
                    C0633d0[] c0633d0Arr = o5Var.e().s0().c;
                    int length = c0633d0Arr.length;
                    int i = 0;
                    while (true) {
                        if (i < length) {
                            if (a(c0633d0Arr[i].b, this.c)) {
                                break;
                            } else {
                                i++;
                            }
                        } else {
                            throw new AssertionError();
                        }
                    }
                }
                HashSet hashSet = new HashSet();
                for (C0633d0 c0633d1 : o5Var.e().s0().c) {
                    if (a(c0633d1.b, this.c)) {
                        a(o5Var.a(), o5Var.e(), c0633d1.b, hashSet);
                    }
                }
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    list2.add(a(o5Var, (C0767v2) it.next(), interfaceC2794pd));
                }
                return;
            }
        }
    }

    private com.android.tools.r8.graph.W0 a(com.android.tools.r8.graph.o5 o5Var, C0767v2 c0767v2, InterfaceC2794pd interfaceC2794pd) {
        C0729q2 c0729q2A = o5Var.a();
        C0684j2 reference = o5Var.getReference();
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        C0722p1 c0722p1 = this.c;
        C0737r2 c0737r2 = reference.h;
        C0737r2 c0737r2A = c0722p1.a(c0737r2.d, c0767v2, c0737r2.f);
        C0725p4 c0725p4P = w0E.f.P();
        c0725p4P.f0();
        c0725p4P.E();
        C0684j2 c0684j2A = this.c.a(c0729q2A.getType(), c0737r2A, reference.C0());
        C1071Ii c1071IiC = C1071Ii.a(this.c).b(c0684j2A).d(reference, c0729q2A.isInterface()).c();
        com.android.tools.r8.graph.W0.a aVarA = com.android.tools.r8.graph.W0.Y1().a(c0684j2A).a(c0725p4P).a(w0E.e1());
        C0658g0 c0658g0S0 = w0E.s0();
        Predicate predicate = new Predicate() { // from class: com.android.tools.r8.internal.nd$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((C0633d0) obj);
            }
        };
        c0658g0S0.getClass();
        com.android.tools.r8.graph.W0 w0A = aVarA.a(c0658g0S0.a(C3988x2.a(predicate))).a(w0E.h.a(CM.b())).a(c1071IiC.a()).b(w0E.I0()).a(w0E.Y0()).a();
        com.android.tools.r8.graph.o5 o5Var2 = new com.android.tools.r8.graph.o5(c0729q2A, w0A);
        this.a.a(o5Var2, this.b);
        interfaceC2794pd.a(o5Var2, o5Var);
        return w0A;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C0633d0 c0633d0) {
        return !a(c0633d0.b, this.c);
    }

    private void a(C0729q2 c0729q2, com.android.tools.r8.graph.W0 w0, com.android.tools.r8.graph.R0 r0, HashSet hashSet) {
        if (!d && !a(r0, this.c)) {
            throw new AssertionError();
        }
        boolean z = false;
        for (C0649f0 c0649f0 : r0.b) {
            String string = c0649f0.a.toString();
            if (r0.a == this.c.j5) {
                if (string.equals("returnType")) {
                    com.android.tools.r8.graph.B2.k kVarL0 = c0649f0.b.L0();
                    if (kVarL0 != null) {
                        hashSet.add((C0767v2) kVarL0.b);
                    } else {
                        throw new C1367Tb(String.format("Expected element \"returnType\" of CovariantReturnType annotation to reference a type (method: \"%s\", was: %s)", w0.r0(), c0649f0.b.getClass().getCanonicalName()));
                    }
                } else if (string.equals("presentAfter")) {
                    z = true;
                }
            } else if (string.equals("value")) {
                com.android.tools.r8.graph.B2.a aVarV0 = c0649f0.b.v0();
                if (aVarV0 != null) {
                    for (com.android.tools.r8.graph.B2 b2 : aVarV0.j1()) {
                        if (!d) {
                            b2.getClass();
                            if (!(b2 instanceof com.android.tools.r8.graph.A2)) {
                                throw new AssertionError();
                            }
                        }
                        a(c0729q2, w0, b2.u0().b, hashSet);
                    }
                } else {
                    throw new C1367Tb(String.format("Expected element \"value\" of CovariantReturnTypes annotation to be an array (method: \"%s\", was: %s)", w0.r0(), c0649f0.b.getClass().getCanonicalName()));
                }
            } else {
                continue;
            }
        }
        if (r0.a == this.c.j5 && !z) {
            throw new C1367Tb(String.format("CovariantReturnType annotation for method \"%s\" is missing mandatory element \"presentAfter\" (class %s)", c0729q2.getType(), w0.r0()));
        }
    }

    public static boolean a(com.android.tools.r8.graph.R0 r0, C0722p1 c0722p1) {
        C0767v2 c0767v2 = r0.a;
        return c0767v2 == c0722p1.j5 || c0767v2 == c0722p1.k5;
    }
}
