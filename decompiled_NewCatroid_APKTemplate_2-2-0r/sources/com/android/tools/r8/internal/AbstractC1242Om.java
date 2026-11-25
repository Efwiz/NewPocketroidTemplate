package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Om, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1242Om extends AbstractC1272Pm implements NavigableSet, IV {
    public static final /* synthetic */ int f = 0;
    final transient Comparator d;
    transient AbstractC1242Om e;

    AbstractC1242Om(Comparator comparator) {
        this.d = comparator;
    }

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
    public static AbstractC1242Om a(Comparable[] comparableArr) {
        C2838qG c2838qG = C2838qG.a;
        int length = comparableArr.length;
        Object[] objArr = (Comparable[]) comparableArr.clone();
        if (length == 0) {
            return c2838qG.equals(c2838qG) ? TS.h : new TS(MS.d, c2838qG);
        }
        XJ.a(length, objArr);
        Arrays.sort(objArr, 0, length, c2838qG);
        int i = 1;
        for (int i2 = 1; i2 < length; i2++) {
            Object obj = objArr[i2];
            if (c2838qG.compare(obj, objArr[i - 1]) != 0) {
                objArr[i] = obj;
                i++;
            }
        }
        Arrays.fill(objArr, i, length, (Object) null);
        return new TS(AbstractC1639am.b(i, objArr), c2838qG);
    }

    abstract AbstractC1242Om a(Object obj, boolean z);

    @Override // java.util.SortedSet, com.android.tools.r8.internal.IV
    public final Comparator comparator() {
        return this.d;
    }

    @Override // java.util.NavigableSet
    public final NavigableSet descendingSet() {
        AbstractC1242Om abstractC1242Om = this.e;
        if (abstractC1242Om != null) {
            return abstractC1242Om;
        }
        AbstractC1242Om abstractC1242OmL = l();
        this.e = abstractC1242OmL;
        abstractC1242OmL.e = this;
        return abstractC1242OmL;
    }

    @Override // java.util.NavigableSet
    public final NavigableSet headSet(Object obj, boolean z) {
        int i = AbstractC3123uM.a;
        obj.getClass();
        TS ts = (TS) this;
        return ts.e(0, ts.b(obj, z));
    }

    abstract int indexOf(Object obj);

    abstract AbstractC1242Om l();

    @Override // java.util.NavigableSet
    public final Object pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.NavigableSet
    public final Object pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.NavigableSet
    public final NavigableSet subSet(Object obj, boolean z, Object obj2, boolean z2) {
        int i = AbstractC3123uM.a;
        obj.getClass();
        obj2.getClass();
        AbstractC3123uM.a(this.d.compare(obj, obj2) <= 0);
        TS ts = (TS) ((TS) this).a(obj, z);
        return ts.e(0, ts.b(obj2, z2));
    }

    @Override // java.util.NavigableSet
    public final NavigableSet tailSet(Object obj, boolean z) {
        int i = AbstractC3123uM.a;
        obj.getClass();
        return a(obj, z);
    }

    @Override // java.util.NavigableSet, java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        int i = AbstractC3123uM.a;
        obj.getClass();
        return a(obj, true);
    }

    @Override // java.util.NavigableSet, java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        int i = AbstractC3123uM.a;
        obj.getClass();
        TS ts = (TS) this;
        return ts.e(0, ts.b(obj, false));
    }

    @Override // java.util.NavigableSet, java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        int i = AbstractC3123uM.a;
        obj.getClass();
        obj2.getClass();
        AbstractC3123uM.a(this.d.compare(obj, obj2) <= 0);
        TS ts = (TS) ((TS) this).a(obj, true);
        return ts.e(0, ts.b(obj2, false));
    }
}
