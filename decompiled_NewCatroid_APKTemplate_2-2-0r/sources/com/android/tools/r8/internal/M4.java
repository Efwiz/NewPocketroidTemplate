package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class M4 extends K4 implements InterfaceC2294jG {
    static final /* synthetic */ boolean e = true;
    private final Map d;

    private M4(IdentityHashMap identityHashMap, IdentityHashMap identityHashMap2, IdentityHashMap identityHashMap3) {
        super(identityHashMap, identityHashMap2);
        this.d = identityHashMap3;
    }

    public static M4 d() {
        return new M4(new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap());
    }

    @Override // com.android.tools.r8.internal.K4
    public final void a() {
        super.a();
        this.d.clear();
    }

    public final void b(Object obj, Object obj2) {
        this.d.put(obj, obj2);
    }

    @Override // com.android.tools.r8.internal.J4
    public final Object c(Object obj) {
        Set setA = a(obj);
        if (setA.isEmpty()) {
            return null;
        }
        if (setA.size() == 1) {
            return setA.iterator().next();
        }
        if (e || this.d.containsKey(obj)) {
            return this.d.get(obj);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.K4
    public final Object e(Object obj) {
        Object objE = super.e(obj);
        if (this.d.containsKey(objE) && (a(objE).size() <= 1 || c(objE) == obj)) {
            this.d.remove(objE);
        }
        return objE;
    }

    public final boolean f(Object obj) {
        return this.d.containsKey(obj);
    }

    public final void a(final com.android.tools.r8.utils.F3 f3) {
        a(new BiConsumer() { // from class: com.android.tools.r8.internal.M4$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(f3, (Set) obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.utils.F3 f3, Set set, Object obj) {
        f3.a(set, obj, c(obj));
    }

    public final void a(M4 m4) {
        m4.a(new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.internal.M4$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.F3
            public final void a(Object obj, Object obj2, Object obj3) {
                this.f$0.a((Set) obj, obj2, obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Set set, Object obj, Object obj2) {
        a((Iterable) set, obj);
        if (set.size() > 1) {
            this.d.put(obj, obj2);
        }
    }

    @Override // com.android.tools.r8.internal.K4
    public final Set a(C0684j2 c0684j2) {
        Set setA = super.a(c0684j2);
        this.d.remove(c0684j2);
        return setA;
    }
}
