package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.h0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2123h0 extends MD {
    final /* synthetic */ C2273j0 a;

    C2123h0(C2273j0 c2273j0) {
        this.a = c2273j0;
    }

    @Override // com.android.tools.r8.internal.MD
    final Map a() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.MD, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        Set setEntrySet = this.a.c.entrySet();
        int i = AbstractC3123uM.a;
        setEntrySet.getClass();
        try {
            return setEntrySet.contains(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new C2195i0(this.a);
    }

    @Override // com.android.tools.r8.internal.MD, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!contains(obj)) {
            return false;
        }
        AbstractC2889r0.a(this.a.d, ((Map.Entry) obj).getKey());
        return true;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        Spliterator spliterator = this.a.c.entrySet().spliterator();
        final C2273j0 c2273j0 = this.a;
        return AbstractC3142ub.a(spliterator, new Function() { // from class: com.android.tools.r8.internal.h0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return c2273j0.a((Map.Entry) obj);
            }
        });
    }
}
