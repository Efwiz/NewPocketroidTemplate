package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.ObjIntConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.w0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3250w0 extends H0 implements Serializable {
    private transient Map c;
    private transient long d;

    protected AbstractC3250w0(HashMap map) {
        AbstractC3123uM.a(map.isEmpty());
        this.c = map;
    }

    static /* synthetic */ void a(AbstractC3250w0 abstractC3250w0) {
        abstractC3250w0.d--;
    }

    @Override // com.android.tools.r8.internal.H0
    final int c() {
        return this.c.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public void clear() {
        Iterator it = this.c.values().iterator();
        while (it.hasNext()) {
            ((C2549md) it.next()).d();
        }
        this.c.clear();
        this.d = 0L;
    }

    @Override // com.android.tools.r8.internal.H0
    final Iterator d() {
        return new C2960s0(this, this.c.entrySet().iterator());
    }

    @Override // com.android.tools.r8.internal.H0
    final Iterator e() {
        return new C3098u0(this, this.c.entrySet().iterator());
    }

    @Override // com.android.tools.r8.internal.H0, com.android.tools.r8.internal.InterfaceC1600aG
    public Set entrySet() {
        return super.entrySet();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        return AbstractC0968Es.a(this.d);
    }

    static /* synthetic */ void a(AbstractC3250w0 abstractC3250w0, long j) {
        abstractC3250w0.d -= j;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public int b(Object obj) {
        C2549md c2549md = (C2549md) RD.a(this.c, obj);
        if (c2549md == null) {
            return 0;
        }
        return c2549md.b();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public void a(final ObjIntConsumer objIntConsumer) {
        int i = AbstractC3123uM.a;
        objIntConsumer.getClass();
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.w0$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AbstractC3250w0.a(objIntConsumer, obj, (C2549md) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public int b(int i, Object obj) {
        if (i == 0) {
            return b(obj);
        }
        boolean z = i > 0;
        int i2 = AbstractC3123uM.a;
        if (z) {
            C2549md c2549md = (C2549md) this.c.get(obj);
            if (c2549md == null) {
                return 0;
            }
            int iB = c2549md.b();
            if (iB <= i) {
                this.c.remove(obj);
                i = iB;
            }
            c2549md.a(-i);
            this.d -= (long) i;
            return iB;
        }
        throw new IllegalArgumentException(AbstractC2619nY.a("occurrences cannot be negative: %s", Integer.valueOf(i)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ObjIntConsumer objIntConsumer, Object obj, C2549md c2549md) {
        objIntConsumer.accept(obj, c2549md.b());
    }

    @Override // com.android.tools.r8.internal.H0, com.android.tools.r8.internal.InterfaceC1600aG
    public int a(Object obj, int i) {
        if (i == 0) {
            return b(obj);
        }
        int i2 = 0;
        boolean z = i > 0;
        int i3 = AbstractC3123uM.a;
        if (z) {
            C2549md c2549md = (C2549md) this.c.get(obj);
            if (c2549md == null) {
                this.c.put(obj, new C2549md(i));
            } else {
                int iB = c2549md.b();
                long j = ((long) iB) + ((long) i);
                if (j <= 2147483647L) {
                    c2549md.a(i);
                    i2 = iB;
                } else {
                    throw new IllegalArgumentException(AbstractC2619nY.a("too many occurrences: %s", Long.valueOf(j)));
                }
            }
            this.d += (long) i;
            return i2;
        }
        throw new IllegalArgumentException(AbstractC2619nY.a("occurrences cannot be negative: %s", Integer.valueOf(i)));
    }

    @Override // com.android.tools.r8.internal.H0, com.android.tools.r8.internal.InterfaceC1600aG
    public int a(Object obj) {
        AbstractC2625nb.a(0, "count");
        C2549md c2549md = (C2549md) this.c.remove(obj);
        int iC = c2549md != null ? c2549md.c() : 0;
        this.d += (long) (0 - iC);
        return iC;
    }
}
