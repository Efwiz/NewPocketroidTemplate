package com.android.tools.r8.internal;

import java.util.ListIterator;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.z4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3479z4 implements InterfaceC2488lo {
    private final ListIterator a;

    C3479z4(C3332x4 c3332x4) {
        this.a = c3332x4.p().listIterator();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(AbstractC2412ko abstractC2412ko, AbstractC2412ko abstractC2412ko2) {
        return abstractC2412ko2 == abstractC2412ko;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2488lo, java.util.ListIterator
    public final boolean hasPrevious() {
        return this.a.hasPrevious();
    }

    @Override // java.util.Iterator
    public final AbstractC2412ko next() {
        return (AbstractC2412ko) this.a.next();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2488lo, java.util.ListIterator
    public final AbstractC2412ko previous() {
        return (AbstractC2412ko) this.a.previous();
    }

    C3479z4(int i, C3332x4 c3332x4) {
        this.a = c3332x4.p().listIterator(i);
    }

    C3479z4(C3332x4 c3332x4, final AbstractC2412ko abstractC2412ko) {
        this(c3332x4);
        a(new Predicate() { // from class: com.android.tools.r8.internal.z4$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3479z4.b(abstractC2412ko, (AbstractC2412ko) obj);
            }
        });
    }
}
