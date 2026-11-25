package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Tl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1377Tl extends AbstractC1048Hm {
    private final transient EnumSet d;
    private transient int e;

    private C1377Tl(EnumSet enumSet) {
        this.d = enumSet;
    }

    static AbstractC1048Hm a(EnumSet enumSet) {
        int size = enumSet.size();
        if (size != 0) {
            return size != 1 ? new C1377Tl(enumSet) : new AV(AbstractC2734ot.b(enumSet));
        }
        return SS.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.d.contains(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean containsAll(Collection collection) {
        if (collection instanceof C1377Tl) {
            collection = ((C1377Tl) collection).d;
        }
        return this.d.containsAll(collection);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm, java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C1377Tl) {
            obj = ((C1377Tl) obj).d;
        }
        return this.d.equals(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    /* JADX INFO: renamed from: f */
    public final AbstractC2963s10 iterator() {
        Iterator it = this.d.iterator();
        int i = AbstractC3123uM.a;
        it.getClass();
        return it instanceof AbstractC2963s10 ? (AbstractC2963s10) it : new C2810pt(it);
    }

    @Override // java.lang.Iterable
    public final void forEach(Consumer consumer) {
        this.d.forEach(consumer);
    }

    @Override // com.android.tools.r8.internal.AbstractC1048Hm, java.util.Collection, java.util.Set
    public final int hashCode() {
        int i = this.e;
        if (i != 0) {
            return i;
        }
        int iHashCode = this.d.hashCode();
        this.e = iHashCode;
        return iHashCode;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this.d.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.d.size();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        return this.d.spliterator();
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        return this.d.toString();
    }
}
