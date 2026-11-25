package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.C2179hn;
import com.android.tools.r8.internal.EnumC2523mE;
import com.android.tools.r8.utils.C3988x2;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.v0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3591v0 {
    static final /* synthetic */ boolean k = true;
    private LinkedHashMap a;
    private LinkedHashMap b;
    private LinkedHashMap c;
    private LinkedHashSet d;
    private LinkedHashMap e;
    private LinkedHashMap f;
    private C2179hn g;
    private LinkedHashMap h;
    private LinkedHashMap i;
    private final int j;

    public C3591v0(int i) {
        this.j = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(EnumC2523mE enumC2523mE, AbstractC3584s0 abstractC3584s0) {
        return abstractC3584s0.b == enumC2523mE;
    }

    public final void b(final Y0 y0) {
        LinkedHashMap linkedHashMap = this.e;
        if (linkedHashMap != null) {
            linkedHashMap.keySet().removeIf(new Predicate() { // from class: com.android.tools.r8.ir.optimize.v0$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C3591v0.b(y0, (y0) obj);
                }
            });
        }
    }

    public final void c() {
        int iD = d();
        if (!k && iD > this.j) {
            throw new AssertionError();
        }
        if (iD == this.j) {
            a(1);
        }
    }

    public final int d() {
        int iA = a(this.a) + a(this.b) + a(this.c);
        LinkedHashSet linkedHashSet = this.d;
        return iA + (linkedHashSet != null ? linkedHashSet.size() : 0) + a(this.e) + a(this.f) + a(this.h) + a(this.i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(EnumC2523mE enumC2523mE, int i, AbstractC3584s0 abstractC3584s0) {
        return abstractC3584s0.b == enumC2523mE && abstractC3584s0.a(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(Y0 y0, y0 y0Var) {
        return y0Var.a == y0;
    }

    public final void a(final Y0 y0) {
        LinkedHashMap linkedHashMap = this.h;
        if (linkedHashMap != null) {
            linkedHashMap.keySet().removeIf(new Predicate() { // from class: com.android.tools.r8.ir.optimize.v0$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C3591v0.a(y0, (y0) obj);
                }
            });
        }
    }

    public final void b(Y0 y0, x0 x0Var) {
        LinkedHashMap linkedHashMap;
        c();
        if (!k && (linkedHashMap = this.f) != null && linkedHashMap.containsKey(y0)) {
            throw new AssertionError();
        }
        if (this.f == null) {
            this.f = new LinkedHashMap();
        }
        this.f.put(y0, x0Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Y0 y0, y0 y0Var) {
        return y0Var.a == y0;
    }

    public final void a() {
        this.h = null;
        this.i = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(LinkedHashMap linkedHashMap, final LinkedHashMap linkedHashMap2) {
        linkedHashMap.entrySet().removeIf(new Predicate() { // from class: com.android.tools.r8.ir.optimize.v0$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3591v0.a(linkedHashMap2, (Map.Entry) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Map map, Map.Entry entry) {
        return map.get(entry.getKey()) != entry.getValue();
    }

    public final void b() {
        this.g = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(LinkedHashSet linkedHashSet, LinkedHashSet linkedHashSet2) {
        Objects.requireNonNull(linkedHashSet2);
        linkedHashSet.removeIf(C3988x2.a(new v0$$ExternalSyntheticLambda5(linkedHashSet2)));
    }

    public final void a(int i) {
        boolean z = k;
        if (!z && i <= 0) {
            throw new AssertionError();
        }
        if (!z && i >= d()) {
            throw new AssertionError();
        }
        int iA = a(i, this.a);
        LinkedHashSet linkedHashSet = this.d;
        if (linkedHashSet != null && iA != 0) {
            Iterator it = linkedHashSet.iterator();
            while (it.hasNext() && iA > 0) {
                it.next();
                it.remove();
                iA--;
            }
        }
        int iA2 = a(a(a(a(a(a(iA, this.e), this.f), this.b), this.c), this.h), this.i);
        if (!z && iA2 != 0) {
            throw new AssertionError();
        }
    }

    private static int a(int i, LinkedHashMap linkedHashMap) {
        Set setKeySet = linkedHashMap != null ? linkedHashMap.keySet() : null;
        if (setKeySet != null && i != 0) {
            Iterator it = setKeySet.iterator();
            while (it.hasNext() && i > 0) {
                it.next();
                it.remove();
                i--;
            }
        }
        return i;
    }

    public final void a(final EnumC2523mE enumC2523mE) {
        LinkedHashMap linkedHashMap = this.a;
        if (linkedHashMap != null) {
            linkedHashMap.keySet().removeIf(new Predicate() { // from class: com.android.tools.r8.ir.optimize.v0$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C3591v0.a(enumC2523mE, (AbstractC3584s0) obj);
                }
            });
        }
    }

    public final void a(final EnumC2523mE enumC2523mE, final int i) {
        LinkedHashMap linkedHashMap = this.a;
        if (linkedHashMap != null) {
            linkedHashMap.keySet().removeIf(new Predicate() { // from class: com.android.tools.r8.ir.optimize.v0$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C3591v0.a(enumC2523mE, i, (AbstractC3584s0) obj);
                }
            });
        }
    }

    public final void a(AbstractC3584s0 abstractC3584s0, x0 x0Var) {
        c();
        if (this.a == null) {
            this.a = new LinkedHashMap();
        }
        this.a.put(abstractC3584s0, x0Var);
    }

    public final void a(Y0 y0, x0 x0Var) {
        c();
        if (this.c == null) {
            this.c = new LinkedHashMap();
        }
        this.c.put(y0, x0Var);
    }

    public final void a(y0 y0Var, z0 z0Var) {
        LinkedHashMap linkedHashMap;
        c();
        if (!k && (linkedHashMap = this.b) != null && linkedHashMap.containsKey(y0Var)) {
            throw new AssertionError();
        }
        if (this.e == null) {
            this.e = new LinkedHashMap();
        }
        this.e.put(y0Var, z0Var);
    }

    private static int a(LinkedHashMap linkedHashMap) {
        if (linkedHashMap != null) {
            return linkedHashMap.size();
        }
        return 0;
    }
}
