package com.android.tools.r8.internal;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class X10 {
    static final /* synthetic */ boolean c = true;
    private final IdentityHashMap a;
    private final IdentityHashMap b;

    private X10() {
        this.a = new IdentityHashMap();
        this.b = new IdentityHashMap();
    }

    final void a(S10 s10, S10 s11) {
        ((Set) this.a.computeIfAbsent(s10, com.android.tools.r8.utils.Z1.a(new NG$$ExternalSyntheticLambda0()))).add(s11);
        ((Set) this.b.computeIfAbsent(s11, com.android.tools.r8.utils.Z1.a(new NG$$ExternalSyntheticLambda0()))).add(s10);
    }

    final void b(S10 s10) {
        IdentityHashMap identityHashMap = this.a;
        Object objEmptySet = Collections.emptySet();
        Object objRemove = identityHashMap.remove(s10);
        if (objRemove != null) {
            objEmptySet = objRemove;
        }
        for (S10 s11 : (Set) objEmptySet) {
            Set set = (Set) this.b.get(s11);
            boolean zRemove = set.remove(s10);
            if (!c && !zRemove) {
                throw new AssertionError();
            }
            if (set.isEmpty()) {
                this.b.remove(s11);
            }
        }
    }

    final Set a(S10 s10) {
        if (!c && this.a.containsKey(s10)) {
            throw new AssertionError();
        }
        IdentityHashMap identityHashMap = this.b;
        Object objEmptySet = Collections.emptySet();
        Object objRemove = identityHashMap.remove(s10);
        if (objRemove != null) {
            objEmptySet = objRemove;
        }
        Set<S10> set = (Set) objEmptySet;
        for (S10 s11 : set) {
            Set set2 = (Set) this.a.get(s11);
            boolean zRemove = set2.remove(s10);
            if (!c && !zRemove) {
                throw new AssertionError();
            }
            if (set2.isEmpty()) {
                this.a.remove(s11);
            }
        }
        return set;
    }
}
