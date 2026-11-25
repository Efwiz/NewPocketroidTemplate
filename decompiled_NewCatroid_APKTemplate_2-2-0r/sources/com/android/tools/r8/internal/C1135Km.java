package com.android.tools.r8.internal;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.IntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Km, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1135Km extends AbstractC1160Ll {
    final /* synthetic */ C1161Lm c;

    C1135Km(C1161Lm c1161Lm) {
        this.c = c1161Lm;
    }

    @Override // java.util.List
    /* JADX INFO: renamed from: k, reason: merged with bridge method [inline-methods] */
    public final Map.Entry get(int i) {
        return new AbstractMap.SimpleImmutableEntry(this.c.d.e.a().get(i), this.c.d.f.get(i));
    }

    @Override // com.android.tools.r8.internal.AbstractC1639am, com.android.tools.r8.internal.AbstractC1271Pl, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        return AbstractC3142ub.a(k().size(), 1297, new IntFunction() { // from class: com.android.tools.r8.internal.Km$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return this.f$0.get(i);
            }
        }, (Comparator) null);
    }

    @Override // com.android.tools.r8.internal.AbstractC1160Ll
    final AbstractC1271Pl k() {
        return this.c;
    }
}
