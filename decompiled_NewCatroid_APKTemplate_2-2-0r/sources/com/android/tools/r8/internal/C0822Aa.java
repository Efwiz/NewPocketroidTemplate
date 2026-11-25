package com.android.tools.r8.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Aa, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0822Aa {
    static final C0822Aa b = new C0822Aa(Collections.emptySet());
    final Set a;

    C0822Aa(Set set) {
        this.a = Collections.unmodifiableSet(set);
    }

    final C0822Aa a(C0822Aa c0822Aa) {
        if (this.a.isEmpty()) {
            return c0822Aa;
        }
        if (c0822Aa.a.isEmpty()) {
            return this;
        }
        HashSet hashSet = new HashSet(this.a);
        hashSet.addAll(c0822Aa.a);
        return this.a.size() == hashSet.size() ? this : new C0822Aa(hashSet);
    }
}
