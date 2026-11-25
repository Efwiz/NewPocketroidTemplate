package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3222vc extends H0 implements Serializable {
    private final transient ConcurrentMap c;

    C3222vc(ConcurrentHashMap concurrentHashMap) {
        AbstractC3123uM.a(concurrentHashMap.isEmpty(), "the backing map (%s) must be empty", concurrentHashMap);
        this.c = concurrentHashMap;
    }

    public static C3222vc f() {
        return new C3222vc(new ConcurrentHashMap());
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public final int b(Object obj) {
        AtomicInteger atomicInteger = (AtomicInteger) RD.a((Map) this.c, obj);
        if (atomicInteger == null) {
            return 0;
        }
        return atomicInteger.get();
    }

    @Override // com.android.tools.r8.internal.H0
    final int c() {
        return this.c.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        this.c.clear();
    }

    @Override // com.android.tools.r8.internal.H0
    final Iterator d() {
        throw new AssertionError("should never be called");
    }

    @Override // com.android.tools.r8.internal.H0
    final Iterator e() {
        return new C3073tc(this, new C3003sc(this));
    }

    @Override // com.android.tools.r8.internal.H0, java.util.AbstractCollection, java.util.Collection
    public final boolean isEmpty() {
        return this.c.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new C1900eG(this, entrySet().iterator());
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        Iterator it = this.c.values().iterator();
        long j = 0;
        while (it.hasNext()) {
            j += (long) ((AtomicInteger) it.next()).get();
        }
        return AbstractC0968Es.a(j);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final Object[] toArray() {
        int size = size();
        AbstractC2625nb.a(size, "arraySize");
        ArrayList arrayList = new ArrayList(AbstractC0968Es.a(((long) size) + 5 + ((long) (size / 10))));
        for (ZF zf : super.entrySet()) {
            Object objB = zf.b();
            for (int iA = zf.a(); iA > 0; iA--) {
                arrayList.add(objB);
            }
        }
        return arrayList.toArray();
    }

    @Override // com.android.tools.r8.internal.H0, com.android.tools.r8.internal.InterfaceC1600aG
    public final int a(Object obj, int i) {
        AtomicInteger atomicInteger;
        int i2;
        AtomicInteger atomicInteger2;
        int i3 = AbstractC3123uM.a;
        obj.getClass();
        if (i == 0) {
            return b(obj);
        }
        if (i <= 0) {
            throw new IllegalArgumentException(new StringBuilder(48).append("occurences must be positive but was: ").append(i).toString());
        }
        do {
            atomicInteger = (AtomicInteger) RD.a((Map) this.c, obj);
            if (atomicInteger == null && (atomicInteger = (AtomicInteger) this.c.putIfAbsent(obj, new AtomicInteger(i))) == null) {
                return 0;
            }
            do {
                i2 = atomicInteger.get();
                if (i2 == 0) {
                    atomicInteger2 = new AtomicInteger(i);
                    if (this.c.putIfAbsent(obj, atomicInteger2) == null) {
                        break;
                    }
                } else {
                    try {
                    } catch (ArithmeticException unused) {
                        throw new IllegalArgumentException(new StringBuilder(65).append("Overflow adding ").append(i).append(" occurrences to a count of ").append(i2).toString());
                    }
                }
            } while (!atomicInteger.compareAndSet(i2, AbstractC1112Jr.b(i2, i)));
            return i2;
        } while (!this.c.replace(obj, atomicInteger, atomicInteger2));
        return 0;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1600aG
    public final int b(int i, Object obj) {
        int i2;
        int iMax;
        if (i == 0) {
            return b(obj);
        }
        if (i > 0) {
            AtomicInteger atomicInteger = (AtomicInteger) RD.a((Map) this.c, obj);
            if (atomicInteger == null) {
                return 0;
            }
            do {
                i2 = atomicInteger.get();
                if (i2 == 0) {
                    return 0;
                }
                iMax = Math.max(0, i2 - i);
            } while (!atomicInteger.compareAndSet(i2, iMax));
            if (iMax == 0) {
                this.c.remove(obj, atomicInteger);
            }
            return i2;
        }
        throw new IllegalArgumentException(new StringBuilder(48).append("occurences must be positive but was: ").append(i).toString());
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        int size = size();
        AbstractC2625nb.a(size, "arraySize");
        ArrayList arrayList = new ArrayList(AbstractC0968Es.a(((long) size) + 5 + ((long) (size / 10))));
        for (ZF zf : super.entrySet()) {
            Object objB = zf.b();
            for (int iA = zf.a(); iA > 0; iA--) {
                arrayList.add(objB);
            }
        }
        return arrayList.toArray(objArr);
    }

    @Override // com.android.tools.r8.internal.H0
    public final Set b() {
        return new C3143uc(this, 0);
    }

    @Override // com.android.tools.r8.internal.H0, com.android.tools.r8.internal.InterfaceC1600aG
    public final int a(Object obj) {
        int i;
        int i2 = AbstractC3123uM.a;
        obj.getClass();
        AbstractC2625nb.a(0, "count");
        AtomicInteger atomicInteger = (AtomicInteger) RD.a((Map) this.c, obj);
        if (atomicInteger == null) {
            return 0;
        }
        do {
            i = atomicInteger.get();
            if (i == 0) {
                return 0;
            }
        } while (!atomicInteger.compareAndSet(i, 0));
        this.c.remove(obj, atomicInteger);
        return i;
    }

    @Override // com.android.tools.r8.internal.H0, com.android.tools.r8.internal.InterfaceC1600aG
    public final boolean a(int i, Object obj) {
        int i2 = AbstractC3123uM.a;
        obj.getClass();
        AbstractC2625nb.a(i, "oldCount");
        AbstractC2625nb.a(0, "newCount");
        AtomicInteger atomicInteger = (AtomicInteger) RD.a((Map) this.c, obj);
        if (atomicInteger == null) {
            return i == 0;
        }
        int i3 = atomicInteger.get();
        if (i3 == i) {
            if (i3 == 0) {
                this.c.remove(obj, atomicInteger);
                return true;
            }
            if (atomicInteger.compareAndSet(i3, 0)) {
                this.c.remove(obj, atomicInteger);
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.H0
    final Set a() {
        return new C2934rc(this.c.keySet());
    }
}
