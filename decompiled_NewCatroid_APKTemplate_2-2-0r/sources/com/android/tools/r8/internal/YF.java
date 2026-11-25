package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class YF extends H0 {
    final VF c;

    YF(VF vf) {
        this.c = vf;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, Map.Entry entry) {
        consumer.accept(entry.getKey());
    }

    @Override // com.android.tools.r8.internal.H0, com.android.tools.r8.internal.InterfaceC1600aG
    public final Set I() {
        return this.c.keySet();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public final int b(Object obj) {
        Collection collection = (Collection) RD.a(this.c.b(), obj);
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    @Override // com.android.tools.r8.internal.H0
    final int c() {
        return this.c.b().size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        this.c.clear();
    }

    @Override // com.android.tools.r8.internal.H0, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        return this.c.containsKey(obj);
    }

    @Override // com.android.tools.r8.internal.H0
    final Iterator d() {
        throw new AssertionError("should never be called");
    }

    @Override // com.android.tools.r8.internal.H0
    final Iterator e() {
        return new XF(this.c.b().entrySet().iterator());
    }

    @Override // java.lang.Iterable, com.android.tools.r8.internal.InterfaceC1600aG
    public final void forEach(final Consumer consumer) {
        int i = AbstractC3123uM.a;
        consumer.getClass();
        this.c.a().forEach(new Consumer() { // from class: com.android.tools.r8.internal.YF$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                YF.a(consumer, (Map.Entry) obj);
            }
        });
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new FD(this.c.a().iterator());
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return this.c.size();
    }

    @Override // java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC1600aG
    public final Spliterator spliterator() {
        return AbstractC3142ub.a(this.c.a().spliterator(), new YF$$ExternalSyntheticLambda1());
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public final int b(int i, Object obj) {
        AbstractC2625nb.a(i, "occurrences");
        if (i == 0) {
            return b(obj);
        }
        Collection collection = (Collection) RD.a(this.c.b(), obj);
        if (collection == null) {
            return 0;
        }
        int size = collection.size();
        if (i >= size) {
            collection.clear();
        } else {
            Iterator it = collection.iterator();
            for (int i2 = 0; i2 < i; i2++) {
                it.next();
                it.remove();
            }
        }
        return size;
    }
}
