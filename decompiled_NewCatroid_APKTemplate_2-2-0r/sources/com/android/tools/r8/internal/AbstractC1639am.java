package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.am, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1639am extends AbstractC1271Pl implements List, RandomAccess {
    public static final /* synthetic */ int b = 0;

    AbstractC1639am() {
    }

    static AbstractC1639am b(int i, Object[] objArr) {
        if (i == 0) {
            return MS.d;
        }
        if (i == 1) {
            return new C3509zV(objArr[0]);
        }
        if (i < objArr.length) {
            objArr = Arrays.copyOf(objArr, i);
        }
        return new MS(objArr);
    }

    public static AbstractC1639am c(Object obj) {
        return new C3509zV(obj);
    }

    public static C1507Xl g() {
        return new C1507Xl(4);
    }

    public static AbstractC1639am i() {
        return MS.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    public final AbstractC1639am a() {
        return this;
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.List
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public AbstractC1639am subList(int i, int i2) {
        AbstractC3123uM.a(i, i2, size());
        int i3 = i2 - i;
        if (i3 == size()) {
            return this;
        }
        if (i3 == 0) {
            return MS.d;
        }
        return i3 == 1 ? new C3509zV(get(i)) : f(i, i2);
    }

    @Override // java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        int i = AbstractC3123uM.a;
        if (obj == this) {
            return true;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            int size = size();
            if (size == list.size()) {
                if (list instanceof RandomAccess) {
                    for (int i2 = 0; i2 < size; i2++) {
                        if (AbstractC3428yK.a(get(i2), list.get(i2))) {
                        }
                    }
                    return true;
                }
                Iterator it = iterator();
                Iterator it2 = list.iterator();
                while (it.hasNext()) {
                    if (it2.hasNext() && AbstractC3428yK.a(it.next(), it2.next())) {
                    }
                }
                return true ^ it2.hasNext();
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    /* JADX INFO: renamed from: f */
    public AbstractC2963s10 iterator() {
        return listIterator(0);
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer consumer) {
        AbstractC3123uM.a(consumer);
        int size = size();
        for (int i = 0; i < size; i++) {
            consumer.accept(get(i));
        }
    }

    @Override // java.util.Collection, java.util.List
    public final int hashCode() {
        int size = size();
        int i = 1;
        for (int i2 = 0; i2 < size; i2++) {
            i = ~(~(get(i2).hashCode() + (i * 31)));
        }
        return i;
    }

    public int indexOf(Object obj) {
        if (obj != null) {
            int size = size();
            for (int i = 0; i < size; i++) {
                if (obj.equals(get(i))) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override // java.util.List
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public AbstractC3253w10 listIterator(int i) {
        return new C1478Wl(this, size(), i);
    }

    public int lastIndexOf(Object obj) {
        if (obj != null) {
            for (int size = size() - 1; size >= 0; size--) {
                if (obj.equals(get(size))) {
                    return size;
                }
            }
        }
        return -1;
    }

    @Override // java.util.List
    public final Object remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final void replaceAll(UnaryOperator unaryOperator) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final void sort(Comparator comparator) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.Collection, java.lang.Iterable, java.util.Set
    public Spliterator spliterator() {
        return AbstractC3142ub.a(size(), 1296, new C1078Im$$ExternalSyntheticLambda0(this), (Comparator) null);
    }

    public static AbstractC1639am a(Object obj, Object obj2) {
        Object[] objArrA = XJ.a(2, new Object[]{obj, obj2});
        return b(objArrA.length, objArrA);
    }

    AbstractC1639am f(int i, int i2) {
        return new C1560Zl(this, i, i2 - i);
    }

    public AbstractC1639am j() {
        return size() <= 1 ? this : new C1534Yl(this);
    }

    public ListIterator listIterator() {
        return listIterator(0);
    }

    public static AbstractC1639am a(Object obj, Object obj2, Object obj3) {
        Object[] objArrA = XJ.a(3, new Object[]{obj, obj2, obj3});
        return b(objArrA.length, objArrA);
    }

    public static AbstractC1639am a(Object obj, Object obj2, Object obj3, Object obj4) {
        Object[] objArrA = XJ.a(4, new Object[]{obj, obj2, obj3, obj4});
        return b(objArrA.length, objArrA);
    }

    public static AbstractC1639am a(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        Object[] objArrA = XJ.a(5, new Object[]{obj, obj2, obj3, obj4, obj5});
        return b(objArrA.length, objArrA);
    }

    public static AbstractC1639am a(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        Object[] objArrA = XJ.a(6, new Object[]{obj, obj2, obj3, obj4, obj5, obj6});
        return b(objArrA.length, objArrA);
    }

    public static AbstractC1639am a(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7) {
        Object[] objArrA = XJ.a(7, new Object[]{obj, obj2, obj3, obj4, obj5, obj6, obj7});
        return b(objArrA.length, objArrA);
    }

    public static AbstractC1639am a(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8) {
        Object[] objArrA = XJ.a(8, new Object[]{obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8});
        return b(objArrA.length, objArrA);
    }

    public static AbstractC1639am a(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9) {
        Object[] objArrA = XJ.a(9, new Object[]{obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9});
        return b(objArrA.length, objArrA);
    }

    public static AbstractC1639am a(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10) {
        Object[] objArrA = XJ.a(10, new Object[]{obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10});
        return b(objArrA.length, objArrA);
    }

    public static AbstractC1639am a(B7 b7, B7 b8, B7 b9, B7 b10, B7 b11, B7 b12, B7 b13, B7 b14, B7 b15, B7 b16, B7 b17) {
        Object[] objArrA = XJ.a(11, new Object[]{b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17});
        return b(objArrA.length, objArrA);
    }

    public static AbstractC1639am a(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10, Object obj11, Object obj12, Object... objArr) {
        AbstractC3123uM.a("the total number of elements must fit in an int", objArr.length <= 2147483635);
        int length = objArr.length + 12;
        Object[] objArr2 = new Object[length];
        objArr2[0] = obj;
        objArr2[1] = obj2;
        objArr2[2] = obj3;
        objArr2[3] = obj4;
        objArr2[4] = obj5;
        objArr2[5] = obj6;
        objArr2[6] = obj7;
        objArr2[7] = obj8;
        objArr2[8] = obj9;
        objArr2[9] = obj10;
        objArr2[10] = obj11;
        objArr2[11] = obj12;
        System.arraycopy(objArr, 0, objArr2, 12, objArr.length);
        Object[] objArrA = XJ.a(length, objArr2);
        return b(objArrA.length, objArrA);
    }

    public static AbstractC1639am a(Collection collection) {
        if (collection instanceof AbstractC1271Pl) {
            AbstractC1639am abstractC1639amA = ((AbstractC1271Pl) collection).a();
            if (!abstractC1639amA.e()) {
                return abstractC1639amA;
            }
            Object[] array = abstractC1639amA.toArray();
            return b(array.length, array);
        }
        Object[] array2 = collection.toArray();
        Object[] objArrA = XJ.a(array2.length, array2);
        return b(objArrA.length, objArrA);
    }

    static AbstractC1639am a(Object[] objArr) {
        return b(objArr.length, objArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    int a(int i, Object[] objArr) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            objArr[i + i2] = get(i2);
        }
        return i + size;
    }
}
