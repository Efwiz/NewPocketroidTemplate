package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Hm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1048Hm extends AbstractC1271Pl implements Set {
    public static final /* synthetic */ int c = 0;
    private transient AbstractC1639am b;

    AbstractC1048Hm() {
    }

    public static AbstractC1048Hm a(Comparable comparable, Comparable comparable2) {
        return a(2, 2, comparable, comparable2);
    }

    public static AbstractC1048Hm c(Object obj) {
        return new AV(obj);
    }

    public static C0862Bm g() {
        return new C0862Bm(0);
    }

    public static AbstractC1048Hm k() {
        return SS.h;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof AbstractC1048Hm) && j() && ((AbstractC1048Hm) obj).j() && hashCode() != obj.hashCode()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            try {
                if (size() == set.size() && containsAll(set)) {
                    return true;
                }
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        return OU.a((Set) this);
    }

    AbstractC1639am i() {
        Object[] array = toArray();
        return new HS(this, AbstractC1639am.b(array.length, array));
    }

    boolean j() {
        return this instanceof C1377Tl;
    }

    public static AbstractC1048Hm a(AbstractC0744s2 abstractC0744s2, AbstractC0744s2 abstractC0744s3, AbstractC0744s2 abstractC0744s4) {
        return a(3, 3, abstractC0744s2, abstractC0744s3, abstractC0744s4);
    }

    static int j(int i) {
        return AbstractC1112Jr.a(i, RoundingMode.UNNECESSARY) * 13;
    }

    static int k(int i) {
        int iMax = Math.max(i, 2);
        if (iMax >= 751619276) {
            AbstractC3123uM.a("collection too large", iMax < 1073741824);
            return 1073741824;
        }
        int iHighestOneBit = Integer.highestOneBit(iMax - 1) << 1;
        while (((double) iHighestOneBit) * 0.7d < iMax) {
            iHighestOneBit <<= 1;
        }
        return iHighestOneBit;
    }

    public static AbstractC1048Hm a(Comparable comparable, Comparable comparable2, Comparable comparable3, Comparable comparable4) {
        return a(4, 4, comparable, comparable2, comparable3, comparable4);
    }

    public static AbstractC1048Hm a(Comparable comparable, Comparable comparable2, Comparable comparable3, Comparable comparable4, Comparable comparable5, Comparable comparable6, Object... objArr) {
        AbstractC3123uM.a("the total number of elements must fit in an int", objArr.length <= 2147483641);
        int length = objArr.length + 6;
        Object[] objArr2 = new Object[length];
        objArr2[0] = comparable;
        objArr2[1] = comparable2;
        objArr2[2] = comparable3;
        objArr2[3] = comparable4;
        objArr2[4] = comparable5;
        objArr2[5] = comparable6;
        System.arraycopy(objArr, 0, objArr2, 6, objArr.length);
        return a(length, length, objArr2);
    }

    private static AbstractC1048Hm a(int i, int i2, Object... objArr) {
        if (i == 0) {
            return SS.h;
        }
        if (i != 1) {
            AbstractC1016Gm c0988Fm = new C0988Fm(i2);
            for (int i3 = 0; i3 < i; i3++) {
                Object obj = objArr[i3];
                int i4 = AbstractC3123uM.a;
                obj.getClass();
                c0988Fm = c0988Fm.a(obj);
            }
            return c0988Fm.c().a();
        }
        return new AV(objArr[0]);
    }

    public static AbstractC1048Hm a(Collection collection) {
        if ((collection instanceof AbstractC1048Hm) && !(collection instanceof SortedSet)) {
            AbstractC1048Hm abstractC1048Hm = (AbstractC1048Hm) collection;
            if (!abstractC1048Hm.e()) {
                return abstractC1048Hm;
            }
        } else if (collection instanceof EnumSet) {
            return C1377Tl.a(EnumSet.copyOf((EnumSet) collection));
        }
        Object[] array = collection.toArray();
        if (collection instanceof Set) {
            return a(array.length, array.length, array);
        }
        int length = array.length;
        return a(length, Math.max(4, AbstractC1112Jr.b(length, RoundingMode.CEILING)), array);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    public final AbstractC1639am a() {
        AbstractC1639am abstractC1639am = this.b;
        if (abstractC1639am != null) {
            return abstractC1639am;
        }
        AbstractC1639am abstractC1639amI = i();
        this.b = abstractC1639amI;
        return abstractC1639amI;
    }

    static boolean a(Object[] objArr) {
        int iA = AbstractC1112Jr.a(objArr.length, RoundingMode.UNNECESSARY) * 13;
        int i = 0;
        while (i < objArr.length && objArr[i] != null) {
            i++;
            if (i > iA) {
                return true;
            }
        }
        int length = objArr.length - 1;
        while (length > i && objArr[length] != null) {
            if (((objArr.length - 1) - length) + i > iA) {
                return true;
            }
            length--;
        }
        int i2 = iA / 2;
        int i3 = i + 1;
        while (true) {
            int i4 = i3 + i2;
            if (i4 > length) {
                return false;
            }
            for (int i5 = 0; i5 < i2; i5++) {
                if (objArr[i3 + i5] == null) {
                    i3 = i4;
                }
            }
            return true;
        }
    }
}
