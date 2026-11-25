package com.android.tools.r8.internal;

import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class BJ extends R0 {
    Object b;
    Object c;
    boolean d;
    boolean e;
    protected transient InterfaceC2842qK f;
    protected transient InterfaceC2842qK g;
    protected transient ZJ h;
    final /* synthetic */ EJ i;

    public BJ(EJ ej, Object obj, boolean z, Object obj2, boolean z2) {
        this.i = ej;
        if (!z && !z2) {
            ej.getClass();
            if (EJ.c(obj, obj2) > 0) {
                throw new IllegalArgumentException("Start key (" + obj + ") is larger than end key (" + obj2 + Constant.AFTER_QUTO);
            }
        }
        this.b = obj;
        this.d = z;
        this.c = obj2;
        this.e = z2;
        this.a = ej.a;
    }

    @Override // com.android.tools.r8.internal.R0
    /* JADX INFO: renamed from: b */
    public final R0 subMap(Object obj, Object obj2) {
        boolean z = this.e;
        if (z && this.d) {
            return new BJ(this.i, obj, false, obj2, false);
        }
        if (!z) {
            EJ ej = this.i;
            Object obj3 = this.c;
            ej.getClass();
            if (((Comparable) obj2).compareTo(obj3) >= 0) {
                obj2 = this.c;
            }
        }
        Object obj4 = obj2;
        if (!this.d) {
            EJ ej2 = this.i;
            Object obj5 = this.b;
            ej2.getClass();
            if (((Comparable) obj).compareTo(obj5) <= 0) {
                obj = this.b;
            }
        }
        Object obj6 = obj;
        return (this.e || this.d || obj6 != this.b || obj4 != this.c) ? new BJ(this.i, obj6, false, obj4, false) : this;
    }

    @Override // com.android.tools.r8.internal.P0, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        C3427yJ c3427yJ = new C3427yJ(this);
        while (c3427yJ.hasNext()) {
            c3427yJ.a();
            c3427yJ.remove();
        }
    }

    @Override // java.util.SortedMap
    public final Comparator comparator() {
        this.i.getClass();
        return null;
    }

    @Override // com.android.tools.r8.internal.P0, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final boolean containsKey(Object obj) {
        return f(obj) && this.i.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        C3427yJ c3427yJ = new C3427yJ(this);
        while (c3427yJ.hasNext()) {
            Object obj2 = c3427yJ.a().b;
            if (obj2 == null) {
                if (obj == null) {
                    return true;
                }
            } else if (obj2.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.R0
    /* JADX INFO: renamed from: d */
    public final R0 headMap(Object obj) {
        if (this.e) {
            return new BJ(this.i, this.b, this.d, obj, false);
        }
        EJ ej = this.i;
        Object obj2 = this.c;
        ej.getClass();
        return ((Comparable) obj).compareTo(obj2) < 0 ? new BJ(this.i, this.b, this.d, obj, false) : this;
    }

    @Override // com.android.tools.r8.internal.R0
    /* JADX INFO: renamed from: e */
    public final InterfaceC2842qK keySet() {
        if (this.g == null) {
            this.g = new C3272wJ(this, 0);
        }
        return this.g;
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0014  */
    /* JADX WARN: Code duplicated, block: B:8:0x0018  */
    final boolean f(Object obj) {
        Object obj2;
        if (this.d) {
            if (!this.e) {
                EJ ej = this.i;
                obj2 = this.c;
                ej.getClass();
                if (((Comparable) obj).compareTo(obj2) < 0) {
                }
            }
            return true;
        }
        EJ ej2 = this.i;
        Object obj3 = this.b;
        ej2.getClass();
        if (((Comparable) obj).compareTo(obj3) >= 0) {
            if (!this.e) {
                EJ ej3 = this.i;
                obj2 = this.c;
                ej3.getClass();
                if (((Comparable) obj).compareTo(obj2) < 0) {
                }
            }
            return true;
        }
        return false;
    }

    @Override // java.util.SortedMap
    public final Object firstKey() {
        C2841qJ c2841qJL = l();
        if (c2841qJL != null) {
            return c2841qJL.a;
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        C2841qJ c2841qJF;
        return (!f(obj) || (c2841qJF = this.i.f(obj)) == null) ? this.a : c2841qJF.b;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        C2841qJ c2841qJ = this.i.d;
        return !(l() != null);
    }

    @Override // com.android.tools.r8.internal.R0
    public final InterfaceC2842qK k() {
        if (this.f == null) {
            this.f = new C3120uJ(this);
        }
        return this.f;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0036  */
    public final C2841qJ l() {
        C2841qJ c2841qJ;
        C2841qJ c2841qJ2;
        EJ ej = this.i;
        C2841qJ c2841qJ3 = ej.b;
        if (c2841qJ3 == null) {
            return null;
        }
        if (this.d) {
            c2841qJ = ej.d;
        } else {
            Object obj = this.b;
            int iCompareTo = 0;
            C2841qJ c2841qJ4 = c2841qJ3;
            while (c2841qJ3 != null) {
                iCompareTo = ((Comparable) obj).compareTo(c2841qJ3.a);
                if (iCompareTo == 0) {
                    break;
                }
                if (iCompareTo < 0) {
                    if ((c2841qJ3.e & 1073741824) != 0) {
                        c2841qJ2 = null;
                    } else {
                        c2841qJ2 = c2841qJ3.c;
                    }
                } else if ((c2841qJ3.e & Integer.MIN_VALUE) != 0) {
                    c2841qJ2 = null;
                } else {
                    c2841qJ2 = c2841qJ3.d;
                }
                C2841qJ c2841qJ5 = c2841qJ2;
                c2841qJ4 = c2841qJ3;
                c2841qJ3 = c2841qJ5;
            }
            c2841qJ = iCompareTo == 0 ? c2841qJ3 : c2841qJ4;
            EJ ej2 = this.i;
            Object obj2 = c2841qJ.a;
            Object obj3 = this.b;
            ej2.getClass();
            if (((Comparable) obj2).compareTo(obj3) < 0) {
                C2841qJ c2841qJ6 = c2841qJ.d;
                if ((c2841qJ.e & Integer.MIN_VALUE) == 0) {
                    c2841qJ = c2841qJ6;
                    while ((c2841qJ.e & 1073741824) == 0) {
                        c2841qJ = c2841qJ.c;
                    }
                } else {
                    c2841qJ = c2841qJ6;
                }
            }
        }
        if (c2841qJ != null) {
            if (!this.e) {
                EJ ej3 = this.i;
                Object obj4 = c2841qJ.a;
                Object obj5 = this.c;
                ej3.getClass();
                if (((Comparable) obj4).compareTo(obj5) >= 0) {
                }
            }
            return c2841qJ;
        }
        return null;
    }

    @Override // java.util.SortedMap
    public final Object lastKey() {
        C2841qJ c2841qJM = m();
        if (c2841qJM != null) {
            return c2841qJM.a;
        }
        throw new NoSuchElementException();
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0035  */
    public final C2841qJ m() {
        C2841qJ c2841qJE;
        C2841qJ c2841qJ;
        EJ ej = this.i;
        C2841qJ c2841qJ2 = ej.b;
        if (c2841qJ2 == null) {
            return null;
        }
        if (this.e) {
            c2841qJE = ej.e;
        } else {
            Object obj = this.c;
            int iCompareTo = 0;
            C2841qJ c2841qJ3 = c2841qJ2;
            while (c2841qJ2 != null) {
                iCompareTo = ((Comparable) obj).compareTo(c2841qJ2.a);
                if (iCompareTo == 0) {
                    break;
                }
                if (iCompareTo < 0) {
                    if ((c2841qJ2.e & 1073741824) != 0) {
                        c2841qJ = null;
                    } else {
                        c2841qJ = c2841qJ2.c;
                    }
                } else if ((c2841qJ2.e & Integer.MIN_VALUE) != 0) {
                    c2841qJ = null;
                } else {
                    c2841qJ = c2841qJ2.d;
                }
                C2841qJ c2841qJ4 = c2841qJ;
                c2841qJ3 = c2841qJ2;
                c2841qJ2 = c2841qJ4;
            }
            c2841qJE = iCompareTo == 0 ? c2841qJ2 : c2841qJ3;
            EJ ej2 = this.i;
            Object obj2 = c2841qJE.a;
            Object obj3 = this.c;
            ej2.getClass();
            if (((Comparable) obj2).compareTo(obj3) >= 0) {
                c2841qJE = c2841qJE.e();
            }
        }
        if (c2841qJE != null) {
            if (!this.d) {
                EJ ej3 = this.i;
                Object obj4 = c2841qJE.a;
                Object obj5 = this.b;
                ej3.getClass();
                if (((Comparable) obj4).compareTo(obj5) < 0) {
                }
            }
            return c2841qJE;
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.P0, java.util.Map
    public final Object put(Object obj, Object obj2) {
        this.i.i = false;
        if (f(obj)) {
            return this.i.i ? this.a : this.i.put(obj, obj2);
        }
        StringBuilder sbAppend = new StringBuilder("Key (").append(obj).append(") out of range [");
        boolean z = this.d;
        String strValueOf = Parameters.DEFAULT_OPTION_PREFIXES;
        StringBuilder sbAppend2 = sbAppend.append(z ? Parameters.DEFAULT_OPTION_PREFIXES : String.valueOf(this.b)).append(", ");
        if (!this.e) {
            strValueOf = String.valueOf(this.c);
        }
        throw new IllegalArgumentException(sbAppend2.append(strValueOf).append(Constant.AFTER_QUTO).toString());
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        this.i.i = false;
        if (f(obj)) {
            return this.i.i ? this.i.remove(obj) : this.a;
        }
        return this.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        C3427yJ c3427yJ = new C3427yJ(this);
        int i = 0;
        while (c3427yJ.hasNext()) {
            i++;
            c3427yJ.a();
        }
        return i;
    }

    @Override // java.util.Map, java.util.SortedMap
    public final Collection values() {
        if (this.h == null) {
            this.h = new C3202vJ(this);
        }
        return this.h;
    }

    @Override // com.android.tools.r8.internal.R0
    /* JADX INFO: renamed from: e */
    public final R0 tailMap(Object obj) {
        if (this.d) {
            return new BJ(this.i, obj, false, this.c, this.e);
        }
        EJ ej = this.i;
        Object obj2 = this.b;
        ej.getClass();
        return ((Comparable) obj).compareTo(obj2) > 0 ? new BJ(this.i, obj, false, this.c, this.e) : this;
    }
}
