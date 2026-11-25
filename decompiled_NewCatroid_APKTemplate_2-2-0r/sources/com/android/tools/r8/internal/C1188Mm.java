package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.function.BiConsumer;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1188Mm extends AbstractC1215Nm implements NavigableMap {
    private static final Comparator h;
    private static final C1188Mm i;
    private final transient TS e;
    private final transient AbstractC1639am f;
    private transient C1188Mm g;

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    static {
        C2838qG c2838qG = C2838qG.a;
        h = c2838qG;
        TS ts = c2838qG.equals(c2838qG) ? TS.h : new TS(MS.d, c2838qG);
        int i2 = AbstractC1639am.b;
        i = new C1188Mm(ts, MS.d, null);
    }

    C1188Mm(TS ts, AbstractC1639am abstractC1639am, C1188Mm c1188Mm) {
        this.e = ts;
        this.f = abstractC1639am;
        this.g = c1188Mm;
    }

    @Override // java.util.NavigableMap
    public final Map.Entry ceilingEntry(Object obj) {
        C1188Mm c1188Mm;
        C1188Mm c1188Mm2;
        TS ts = this.e;
        int i2 = AbstractC3123uM.a;
        obj.getClass();
        int iC = ts.c(obj, true);
        int size = this.f.size();
        if (iC == 0 && size == this.f.size()) {
            c1188Mm2 = this;
        } else {
            if (iC == size) {
                Comparator comparator = this.e.d;
                C2838qG c2838qG = C2838qG.a;
                if (c2838qG.equals(comparator)) {
                    c1188Mm2 = i;
                } else {
                    int i3 = AbstractC1242Om.f;
                    c1188Mm = new C1188Mm(c2838qG.equals(comparator) ? TS.h : new TS(MS.d, comparator), MS.d, null);
                }
            } else {
                c1188Mm = new C1188Mm(this.e.e(iC, size), this.f.subList(iC, size), null);
            }
            c1188Mm2 = c1188Mm;
        }
        return c1188Mm2.firstEntry();
    }

    @Override // java.util.NavigableMap
    public final Object ceilingKey(Object obj) {
        return RD.a(ceilingEntry(obj));
    }

    @Override // java.util.SortedMap
    public final Comparator comparator() {
        return this.e.d;
    }

    @Override // java.util.NavigableMap
    public final NavigableSet descendingKeySet() {
        TS ts = this.e;
        AbstractC1242Om abstractC1242Om = ts.e;
        if (abstractC1242Om != null) {
            return abstractC1242Om;
        }
        AbstractC1242Om abstractC1242OmL = ts.l();
        ts.e = abstractC1242OmL;
        abstractC1242OmL.e = ts;
        return abstractC1242OmL;
    }

    @Override // java.util.NavigableMap
    public final NavigableMap descendingMap() {
        C1188Mm c1188Mm = this.g;
        if (c1188Mm != null) {
            return c1188Mm;
        }
        if (isEmpty()) {
            Comparator comparator = this.e.d;
            OK okA = (comparator instanceof OK ? (OK) comparator : new C1063Ib(comparator)).a();
            C2838qG c2838qG = C2838qG.a;
            if (c2838qG.equals(okA)) {
                return i;
            }
            int i2 = AbstractC1242Om.f;
            return new C1188Mm(c2838qG.equals(okA) ? TS.h : new TS(MS.d, okA), MS.d, null);
        }
        TS ts = this.e;
        AbstractC1242Om abstractC1242OmL = ts.e;
        if (abstractC1242OmL == null) {
            abstractC1242OmL = ts.l();
            ts.e = abstractC1242OmL;
            abstractC1242OmL.e = ts;
        }
        return new C1188Mm((TS) abstractC1242OmL, this.f.j(), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map, java.util.SortedMap
    public final Set entrySet() {
        return super.entrySet();
    }

    @Override // java.util.NavigableMap
    public final Map.Entry firstEntry() {
        if (isEmpty()) {
            return null;
        }
        return (Map.Entry) super.entrySet().a().get(0);
    }

    @Override // java.util.SortedMap
    public final Object firstKey() {
        return this.e.first();
    }

    @Override // java.util.NavigableMap
    public final Map.Entry floorEntry(Object obj) {
        C1188Mm c1188Mm;
        TS ts = this.e;
        int i2 = AbstractC3123uM.a;
        obj.getClass();
        int iB = ts.b(obj, true);
        if (iB == this.f.size()) {
            c1188Mm = this;
        } else if (iB == 0) {
            Comparator comparator = this.e.d;
            C2838qG c2838qG = C2838qG.a;
            if (c2838qG.equals(comparator)) {
                c1188Mm = i;
            } else {
                int i3 = AbstractC1242Om.f;
                c1188Mm = new C1188Mm(c2838qG.equals(comparator) ? TS.h : new TS(MS.d, comparator), MS.d, null);
            }
        } else {
            c1188Mm = new C1188Mm(this.e.e(0, iB), this.f.subList(0, iB), null);
        }
        return c1188Mm.lastEntry();
    }

    @Override // java.util.NavigableMap
    public final Object floorKey(Object obj) {
        return RD.a(floorEntry(obj));
    }

    @Override // java.util.Map
    public final void forEach(BiConsumer biConsumer) {
        AbstractC3123uM.a(biConsumer);
        AbstractC1639am abstractC1639amA = this.e.a();
        for (int i2 = 0; i2 < this.f.size(); i2++) {
            biConsumer.accept(abstractC1639amA.get(i2), this.f.get(i2));
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map
    public final Object get(Object obj) {
        int iIndexOf = this.e.indexOf(obj);
        if (iIndexOf == -1) {
            return null;
        }
        return this.f.get(iIndexOf);
    }

    @Override // java.util.NavigableMap
    public final NavigableMap headMap(Object obj, boolean z) {
        TS ts = this.e;
        int i2 = AbstractC3123uM.a;
        obj.getClass();
        int iB = ts.b(obj, z);
        if (iB == this.f.size()) {
            return this;
        }
        if (iB != 0) {
            return new C1188Mm(this.e.e(0, iB), this.f.subList(0, iB), null);
        }
        Comparator comparator = this.e.d;
        C2838qG c2838qG = C2838qG.a;
        if (c2838qG.equals(comparator)) {
            return i;
        }
        int i3 = AbstractC1242Om.f;
        return new C1188Mm(c2838qG.equals(comparator) ? TS.h : new TS(MS.d, comparator), MS.d, null);
    }

    @Override // java.util.NavigableMap
    public final Map.Entry higherEntry(Object obj) {
        C1188Mm c1188Mm;
        C1188Mm c1188Mm2;
        TS ts = this.e;
        int i2 = AbstractC3123uM.a;
        obj.getClass();
        int iC = ts.c(obj, false);
        int size = this.f.size();
        if (iC == 0 && size == this.f.size()) {
            c1188Mm2 = this;
        } else {
            if (iC == size) {
                Comparator comparator = this.e.d;
                C2838qG c2838qG = C2838qG.a;
                if (c2838qG.equals(comparator)) {
                    c1188Mm2 = i;
                } else {
                    int i3 = AbstractC1242Om.f;
                    c1188Mm = new C1188Mm(c2838qG.equals(comparator) ? TS.h : new TS(MS.d, comparator), MS.d, null);
                }
            } else {
                c1188Mm = new C1188Mm(this.e.e(iC, size), this.f.subList(iC, size), null);
            }
            c1188Mm2 = c1188Mm;
        }
        return c1188Mm2.firstEntry();
    }

    @Override // java.util.NavigableMap
    public final Object higherKey(Object obj) {
        return RD.a(higherEntry(obj));
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm k() {
        if (!isEmpty()) {
            return new C1161Lm(this);
        }
        int i2 = AbstractC1048Hm.c;
        return SS.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map, java.util.SortedMap
    public final Set keySet() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1048Hm l() {
        throw new AssertionError("should never be called");
    }

    @Override // java.util.NavigableMap
    public final Map.Entry lastEntry() {
        if (isEmpty()) {
            return null;
        }
        return (Map.Entry) super.entrySet().a().get(this.f.size() - 1);
    }

    @Override // java.util.SortedMap
    public final Object lastKey() {
        return this.e.last();
    }

    @Override // java.util.NavigableMap
    public final Map.Entry lowerEntry(Object obj) {
        C1188Mm c1188Mm;
        C1188Mm c1188Mm2;
        TS ts = this.e;
        int i2 = AbstractC3123uM.a;
        obj.getClass();
        int iB = ts.b(obj, false);
        if (iB == this.f.size()) {
            c1188Mm2 = this;
        } else {
            if (iB == 0) {
                Comparator comparator = this.e.d;
                C2838qG c2838qG = C2838qG.a;
                if (c2838qG.equals(comparator)) {
                    c1188Mm2 = i;
                } else {
                    int i3 = AbstractC1242Om.f;
                    c1188Mm = new C1188Mm(c2838qG.equals(comparator) ? TS.h : new TS(MS.d, comparator), MS.d, null);
                }
            } else {
                c1188Mm = new C1188Mm(this.e.e(0, iB), this.f.subList(0, iB), null);
            }
            c1188Mm2 = c1188Mm;
        }
        return c1188Mm2.lastEntry();
    }

    @Override // java.util.NavigableMap
    public final Object lowerKey(Object obj) {
        return RD.a(lowerEntry(obj));
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final AbstractC1271Pl m() {
        throw new AssertionError("should never be called");
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    /* JADX INFO: renamed from: n */
    public final AbstractC1048Hm entrySet() {
        return super.entrySet();
    }

    @Override // java.util.NavigableMap
    public final NavigableSet navigableKeySet() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    final boolean o() {
        return this.e.e() || this.f.e();
    }

    @Override // java.util.NavigableMap
    public final Map.Entry pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.NavigableMap
    public final Map.Entry pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    /* JADX INFO: renamed from: q */
    public final AbstractC1048Hm keySet() {
        return this.e;
    }

    @Override // java.util.Map
    public final int size() {
        return this.f.size();
    }

    @Override // java.util.NavigableMap
    public final NavigableMap subMap(Object obj, boolean z, Object obj2, boolean z2) {
        C1188Mm c1188Mm;
        int i2 = AbstractC3123uM.a;
        obj.getClass();
        obj2.getClass();
        if (!(this.e.d.compare(obj, obj2) <= 0)) {
            throw new IllegalArgumentException(AbstractC2619nY.a("expected fromKey <= toKey but %s > %s", obj, obj2));
        }
        int iB = this.e.b(obj2, z2);
        if (iB == this.f.size()) {
            c1188Mm = this;
        } else if (iB == 0) {
            Comparator comparator = this.e.d;
            C2838qG c2838qG = C2838qG.a;
            if (c2838qG.equals(comparator)) {
                c1188Mm = i;
            } else {
                int i3 = AbstractC1242Om.f;
                c1188Mm = new C1188Mm(c2838qG.equals(comparator) ? TS.h : new TS(MS.d, comparator), MS.d, null);
            }
        } else {
            c1188Mm = new C1188Mm(this.e.e(0, iB), this.f.subList(0, iB), null);
        }
        int iC = c1188Mm.e.c(obj, z);
        int size = c1188Mm.f.size();
        if (iC == 0 && size == c1188Mm.f.size()) {
            return c1188Mm;
        }
        if (iC != size) {
            return new C1188Mm(c1188Mm.e.e(iC, size), c1188Mm.f.subList(iC, size), null);
        }
        Comparator comparator2 = c1188Mm.e.d;
        C2838qG c2838qG2 = C2838qG.a;
        if (c2838qG2.equals(comparator2)) {
            return i;
        }
        int i4 = AbstractC1242Om.f;
        return new C1188Mm(c2838qG2.equals(comparator2) ? TS.h : new TS(MS.d, comparator2), MS.d, null);
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm
    /* JADX INFO: renamed from: t */
    public final AbstractC1271Pl values() {
        return this.f;
    }

    @Override // java.util.NavigableMap
    public final NavigableMap tailMap(Object obj, boolean z) {
        C1188Mm c1188Mm;
        TS ts = this.e;
        int i2 = AbstractC3123uM.a;
        obj.getClass();
        int iC = ts.c(obj, z);
        int size = this.f.size();
        if (iC == 0 && size == this.f.size()) {
            return this;
        }
        if (iC == size) {
            Comparator comparator = this.e.d;
            C2838qG c2838qG = C2838qG.a;
            if (c2838qG.equals(comparator)) {
                return i;
            }
            int i3 = AbstractC1242Om.f;
            c1188Mm = new C1188Mm(c2838qG.equals(comparator) ? TS.h : new TS(MS.d, comparator), MS.d, null);
        } else {
            c1188Mm = new C1188Mm(this.e.e(iC, size), this.f.subList(iC, size), null);
        }
        return c1188Mm;
    }

    @Override // com.android.tools.r8.internal.AbstractC2178hm, java.util.Map, java.util.SortedMap
    public final Collection values() {
        return this.f;
    }

    /* JADX WARN: Code duplicated, block: B:9:0x001d  */
    /* JADX WARN: Multi-variable type inference failed */
    public static C1188Mm a(IdentityHashMap identityHashMap) {
        boolean zEquals;
        Comparator comparator = h;
        OK ok = (OK) comparator;
        if (identityHashMap instanceof SortedMap) {
            Comparator comparator2 = ((SortedMap) identityHashMap).comparator();
            if (comparator2 != null) {
                zEquals = ok.equals(comparator2);
            } else if (ok == comparator) {
                zEquals = true;
            } else {
                zEquals = false;
            }
        } else {
            zEquals = false;
        }
        if (zEquals && (identityHashMap instanceof C1188Mm)) {
            C1188Mm c1188Mm = (C1188Mm) identityHashMap;
            if (!c1188Mm.o()) {
                return c1188Mm;
            }
        }
        Map.Entry[] entryArr = (Map.Entry[]) AbstractC2734ot.a(identityHashMap.entrySet(), (Object[]) AbstractC2178hm.d);
        int length = entryArr.length;
        if (length == 0) {
            C2838qG c2838qG = C2838qG.a;
            if (c2838qG.equals(ok)) {
                return i;
            }
            int i2 = AbstractC1242Om.f;
            return new C1188Mm(c2838qG.equals(ok) ? TS.h : new TS(MS.d, ok), MS.d, null);
        }
        if (length == 1) {
            Object key = entryArr[0].getKey();
            Object value = entryArr[0].getValue();
            C3509zV c3509zV = new C3509zV(key);
            ok.getClass();
            return new C1188Mm(new TS(c3509zV, ok), new C3509zV(value), null);
        }
        Object[] objArr = new Object[length];
        Object[] objArr2 = new Object[length];
        if (zEquals) {
            for (int i3 = 0; i3 < length; i3++) {
                Object key2 = entryArr[i3].getKey();
                Object value2 = entryArr[i3].getValue();
                AbstractC2625nb.a(key2, value2);
                objArr[i3] = key2;
                objArr2[i3] = value2;
            }
        } else {
            Arrays.sort(entryArr, 0, length, new C1107Jm(ok));
            Object key3 = entryArr[0].getKey();
            objArr[0] = key3;
            Object value3 = entryArr[0].getValue();
            objArr2[0] = value3;
            AbstractC2625nb.a(objArr[0], value3);
            int i4 = 1;
            while (i4 < length) {
                Object key4 = entryArr[i4].getKey();
                Object value4 = entryArr[i4].getValue();
                AbstractC2625nb.a(key4, value4);
                objArr[i4] = key4;
                objArr2[i4] = value4;
                boolean z = ok.compare(key3, key4) != 0;
                Map.Entry entry = entryArr[i4 - 1];
                Map.Entry entry2 = entryArr[i4];
                if (!z) {
                    throw AbstractC2178hm.a(ASTExpr.DEFAULT_MAP_KEY_NAME, entry, entry2);
                }
                i4++;
                key3 = key4;
            }
        }
        return new C1188Mm(new TS(new MS(objArr), ok), new MS(objArr2), null);
    }

    @Override // java.util.NavigableMap, java.util.SortedMap
    public final SortedMap headMap(Object obj) {
        C1188Mm c1188Mm;
        TS ts;
        TS ts2 = this.e;
        int i2 = AbstractC3123uM.a;
        obj.getClass();
        int iB = ts2.b(obj, false);
        if (iB == this.f.size()) {
            return this;
        }
        if (iB == 0) {
            Comparator comparator = this.e.d;
            C2838qG c2838qG = C2838qG.a;
            if (c2838qG.equals(comparator)) {
                return i;
            }
            int i3 = AbstractC1242Om.f;
            if (c2838qG.equals(comparator)) {
                ts = TS.h;
            } else {
                ts = new TS(MS.d, comparator);
            }
            c1188Mm = new C1188Mm(ts, MS.d, null);
        } else {
            c1188Mm = new C1188Mm(this.e.e(0, iB), this.f.subList(0, iB), null);
        }
        return c1188Mm;
    }

    @Override // java.util.NavigableMap, java.util.SortedMap
    public final SortedMap tailMap(Object obj) {
        C1188Mm c1188Mm;
        TS ts;
        TS ts2 = this.e;
        int i2 = AbstractC3123uM.a;
        obj.getClass();
        int iC = ts2.c(obj, true);
        int size = this.f.size();
        if (iC == 0 && size == this.f.size()) {
            return this;
        }
        if (iC == size) {
            Comparator comparator = this.e.d;
            C2838qG c2838qG = C2838qG.a;
            if (c2838qG.equals(comparator)) {
                return i;
            }
            int i3 = AbstractC1242Om.f;
            if (c2838qG.equals(comparator)) {
                ts = TS.h;
            } else {
                ts = new TS(MS.d, comparator);
            }
            c1188Mm = new C1188Mm(ts, MS.d, null);
        } else {
            c1188Mm = new C1188Mm(this.e.e(iC, size), this.f.subList(iC, size), null);
        }
        return c1188Mm;
    }

    @Override // java.util.NavigableMap, java.util.SortedMap
    public final SortedMap subMap(Object obj, Object obj2) {
        C1188Mm c1188Mm;
        TS ts;
        TS ts2;
        int i2 = AbstractC3123uM.a;
        obj.getClass();
        obj2.getClass();
        if (this.e.d.compare(obj, obj2) <= 0) {
            int iB = this.e.b(obj2, false);
            if (iB == this.f.size()) {
                c1188Mm = this;
            } else if (iB == 0) {
                Comparator comparator = this.e.d;
                C2838qG c2838qG = C2838qG.a;
                if (c2838qG.equals(comparator)) {
                    c1188Mm = i;
                } else {
                    int i3 = AbstractC1242Om.f;
                    if (c2838qG.equals(comparator)) {
                        ts = TS.h;
                    } else {
                        ts = new TS(MS.d, comparator);
                    }
                    c1188Mm = new C1188Mm(ts, MS.d, null);
                }
            } else {
                c1188Mm = new C1188Mm(this.e.e(0, iB), this.f.subList(0, iB), null);
            }
            int iC = c1188Mm.e.c(obj, true);
            int size = c1188Mm.f.size();
            if (iC == 0 && size == c1188Mm.f.size()) {
                return c1188Mm;
            }
            if (iC == size) {
                Comparator comparator2 = c1188Mm.e.d;
                C2838qG c2838qG2 = C2838qG.a;
                if (c2838qG2.equals(comparator2)) {
                    return i;
                }
                int i4 = AbstractC1242Om.f;
                if (c2838qG2.equals(comparator2)) {
                    ts2 = TS.h;
                } else {
                    ts2 = new TS(MS.d, comparator2);
                }
                return new C1188Mm(ts2, MS.d, null);
            }
            return new C1188Mm(c1188Mm.e.e(iC, size), c1188Mm.f.subList(iC, size), null);
        }
        throw new IllegalArgumentException(AbstractC2619nY.a("expected fromKey <= toKey but %s > %s", obj, obj2));
    }
}
