package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EJ extends R0 implements Cloneable {
    protected transient C2841qJ b;
    protected int c;
    protected transient C2841qJ d;
    protected transient C2841qJ e;
    protected transient InterfaceC2842qK f;
    protected transient InterfaceC2842qK g;
    protected transient ZJ h;
    protected transient boolean i;
    private transient boolean[] j;
    private transient C2841qJ[] k;

    public EJ() {
        l();
        this.b = null;
        this.c = 0;
    }

    static int c(Object obj, Object obj2) {
        return ((Comparable) obj).compareTo(obj2);
    }

    private void l() {
        this.j = new boolean[64];
        this.k = new C2841qJ[64];
    }

    @Override // com.android.tools.r8.internal.R0
    /* JADX INFO: renamed from: b */
    public final R0 subMap(Object obj, Object obj2) {
        return new BJ(this, obj, false, obj2, false);
    }

    @Override // com.android.tools.r8.internal.P0, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        this.c = 0;
        this.b = null;
        this.f = null;
        this.h = null;
        this.g = null;
        this.e = null;
        this.d = null;
    }

    public final Object clone() {
        try {
            EJ ej = (EJ) super.clone();
            ej.g = null;
            ej.h = null;
            ej.f = null;
            ej.l();
            if (this.c != 0) {
                C2841qJ c2841qJ = new C2841qJ();
                C2841qJ c2841qJ2 = new C2841qJ();
                C2841qJ c2841qJ3 = this.b;
                c2841qJ.e &= -1073741825;
                c2841qJ.c = c2841qJ3;
                c2841qJ2.a((C2841qJ) null);
                C2841qJ c2841qJ4 = c2841qJ2;
                loop0: while (true) {
                    if (c2841qJ.d()) {
                        while (c2841qJ.f()) {
                            c2841qJ = c2841qJ.d;
                            if (c2841qJ == null) {
                                break loop0;
                            }
                            c2841qJ4 = c2841qJ4.d;
                        }
                        c2841qJ = c2841qJ.d;
                        c2841qJ4 = c2841qJ4.d;
                    } else {
                        C2841qJ c2841qJClone = c2841qJ.c.clone();
                        c2841qJClone.a(c2841qJ4.c);
                        c2841qJClone.b(c2841qJ4);
                        c2841qJ4.e &= -1073741825;
                        c2841qJ4.c = c2841qJClone;
                        c2841qJ = c2841qJ.c;
                        c2841qJ4 = c2841qJClone;
                    }
                    if (!c2841qJ.f()) {
                        C2841qJ c2841qJClone2 = c2841qJ.d.clone();
                        c2841qJClone2.b(c2841qJ4.d);
                        c2841qJClone2.a(c2841qJ4);
                        c2841qJ4.e &= Integer.MAX_VALUE;
                        c2841qJ4.d = c2841qJClone2;
                    }
                }
                c2841qJ4.d = null;
                C2841qJ c2841qJ5 = c2841qJ2.c;
                ej.b = c2841qJ5;
                ej.d = c2841qJ5;
                while (true) {
                    C2841qJ c2841qJ6 = ej.d.c;
                    if (c2841qJ6 == null) {
                        break;
                    }
                    ej.d = c2841qJ6;
                }
                ej.e = ej.b;
                while (true) {
                    C2841qJ c2841qJ7 = ej.e.d;
                    if (c2841qJ7 == null) {
                        break;
                    }
                    ej.e = c2841qJ7;
                }
            }
            return ej;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // java.util.SortedMap
    public final Comparator comparator() {
        return null;
    }

    @Override // com.android.tools.r8.internal.P0, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final boolean containsKey(Object obj) {
        return f(obj) != null;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        DJ dj = new DJ(this, 0);
        int i = this.c;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            Object obj2 = dj.a().b;
            if (obj2 == null) {
                if (obj == null) {
                    return true;
                }
            } else if (obj2.equals(obj)) {
                return true;
            }
            i = i2;
        }
    }

    @Override // com.android.tools.r8.internal.R0
    /* JADX INFO: renamed from: d */
    public final R0 headMap(Object obj) {
        return new BJ(this, null, true, obj, false);
    }

    @Override // com.android.tools.r8.internal.R0
    /* JADX INFO: renamed from: e */
    public final InterfaceC2842qK keySet() {
        if (this.g == null) {
            this.g = new C3051tJ(this, 0);
        }
        return this.g;
    }

    final C2841qJ f(Object obj) {
        C2841qJ c2841qJ = this.b;
        while (c2841qJ != null) {
            int iCompareTo = ((Comparable) obj).compareTo(c2841qJ.a);
            if (iCompareTo == 0) {
                break;
            }
            if (iCompareTo < 0) {
                c2841qJ = (c2841qJ.e & 1073741824) != 0 ? null : c2841qJ.c;
            } else if ((c2841qJ.e & Integer.MIN_VALUE) == 0) {
                c2841qJ = c2841qJ.d;
            }
        }
        return c2841qJ;
    }

    @Override // java.util.SortedMap
    public final Object firstKey() {
        if (this.b != null) {
            return this.d.a;
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        C2841qJ c2841qJF = f(obj);
        return c2841qJF == null ? this.a : c2841qJF.b;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.c == 0;
    }

    @Override // com.android.tools.r8.internal.R0
    public final InterfaceC2842qK k() {
        if (this.f == null) {
            this.f = new C2694oJ(this);
        }
        return this.f;
    }

    @Override // java.util.SortedMap
    public final Object lastKey() {
        if (this.b != null) {
            return this.e.a;
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.P0, java.util.Map
    public final Object put(Object obj, Object obj2) {
        int i;
        C2841qJ c2841qJ;
        C2841qJ c2841qJ2;
        C2841qJ c2841qJ3;
        int i2 = 0;
        this.i = false;
        C2841qJ c2841qJ4 = this.b;
        if (c2841qJ4 == null) {
            this.c++;
            c2841qJ4 = new C2841qJ(obj, this.a);
            this.d = c2841qJ4;
            this.e = c2841qJ4;
            this.b = c2841qJ4;
        } else {
            int i3 = 0;
            while (true) {
                int iCompareTo = ((Comparable) obj).compareTo(c2841qJ4.a);
                if (iCompareTo == 0) {
                    while (true) {
                        int i4 = i3 - 1;
                        if (i3 == 0) {
                            break;
                        }
                        this.k[i4] = null;
                        i3 = i4;
                    }
                } else {
                    this.k[i3] = c2841qJ4;
                    boolean[] zArr = this.j;
                    i = i3 + 1;
                    boolean z = iCompareTo > 0;
                    zArr[i3] = z;
                    if (z) {
                        if (c2841qJ4.f()) {
                            this.c++;
                            c2841qJ = new C2841qJ(obj, this.a);
                            C2841qJ c2841qJ5 = c2841qJ4.d;
                            if (c2841qJ5 == null) {
                                this.e = c2841qJ;
                            }
                            c2841qJ.c = c2841qJ4;
                            c2841qJ.d = c2841qJ5;
                            c2841qJ4.e &= Integer.MAX_VALUE;
                            c2841qJ4.d = c2841qJ;
                            break;
                        }
                        c2841qJ4 = c2841qJ4.d;
                        i3 = i;
                    } else {
                        if (c2841qJ4.d()) {
                            this.c++;
                            c2841qJ = new C2841qJ(obj, this.a);
                            C2841qJ c2841qJ6 = c2841qJ4.c;
                            if (c2841qJ6 == null) {
                                this.d = c2841qJ;
                            }
                            c2841qJ.d = c2841qJ4;
                            c2841qJ.c = c2841qJ6;
                            c2841qJ4.e &= -1073741825;
                            c2841qJ4.c = c2841qJ;
                            break;
                        }
                        c2841qJ4 = c2841qJ4.c;
                        i3 = i;
                    }
                }
                Object obj3 = c2841qJ4.b;
                c2841qJ4.b = obj2;
                return obj3;
            }
            c2841qJ4 = c2841qJ;
            this.i = true;
            while (i3 > 0 && !this.k[i3].b()) {
                int i5 = i3 - 1;
                if (this.j[i5]) {
                    C2841qJ c2841qJ7 = this.k[i5];
                    C2841qJ c2841qJ8 = c2841qJ7.c;
                    if (c2841qJ7.d() || c2841qJ8.b()) {
                        if (this.j[i3]) {
                            c2841qJ2 = this.k[i3];
                        } else {
                            C2841qJ[] c2841qJArr = this.k;
                            C2841qJ c2841qJ9 = c2841qJArr[i3];
                            C2841qJ c2841qJ10 = c2841qJ9.c;
                            c2841qJ9.c = c2841qJ10.d;
                            c2841qJ10.d = c2841qJ9;
                            c2841qJArr[i5].d = c2841qJ10;
                            if (c2841qJ10.f()) {
                                c2841qJ10.e &= Integer.MAX_VALUE;
                                c2841qJ9.a(c2841qJ10);
                            }
                            c2841qJ2 = c2841qJ10;
                        }
                        C2841qJ c2841qJ11 = this.k[i5];
                        c2841qJ11.a(false);
                        c2841qJ2.a(true);
                        c2841qJ11.d = c2841qJ2.c;
                        c2841qJ2.c = c2841qJ11;
                        if (i3 < 2) {
                            this.b = c2841qJ2;
                        } else {
                            int i6 = i3 - 2;
                            if (this.j[i6]) {
                                this.k[i6].d = c2841qJ2;
                            } else {
                                this.k[i6].c = c2841qJ2;
                            }
                        }
                        if (!c2841qJ2.d()) {
                            break;
                        }
                        c2841qJ2.b(false);
                        c2841qJ11.b(c2841qJ2);
                        break;
                    }
                    this.k[i3].a(true);
                    c2841qJ8.a(true);
                    this.k[i5].a(false);
                    i3 -= 2;
                } else {
                    C2841qJ c2841qJ12 = this.k[i5];
                    C2841qJ c2841qJ13 = c2841qJ12.d;
                    if (c2841qJ12.f() || c2841qJ13.b()) {
                        if (this.j[i3]) {
                            C2841qJ[] c2841qJArr2 = this.k;
                            C2841qJ c2841qJ14 = c2841qJArr2[i3];
                            C2841qJ c2841qJ15 = c2841qJ14.d;
                            c2841qJ14.d = c2841qJ15.c;
                            c2841qJ15.c = c2841qJ14;
                            c2841qJArr2[i5].c = c2841qJ15;
                            if (c2841qJ15.d()) {
                                c2841qJ15.b(false);
                                c2841qJ14.b(c2841qJ15);
                            }
                            c2841qJ3 = c2841qJ15;
                        } else {
                            c2841qJ3 = this.k[i3];
                        }
                        C2841qJ c2841qJ16 = this.k[i5];
                        c2841qJ16.a(false);
                        c2841qJ3.a(true);
                        c2841qJ16.c = c2841qJ3.d;
                        c2841qJ3.d = c2841qJ16;
                        if (i3 < 2) {
                            this.b = c2841qJ3;
                        } else {
                            int i7 = i3 - 2;
                            if (this.j[i7]) {
                                this.k[i7].d = c2841qJ3;
                            } else {
                                this.k[i7].c = c2841qJ3;
                            }
                        }
                        if (!c2841qJ3.f()) {
                            break;
                        }
                        c2841qJ3.e &= Integer.MAX_VALUE;
                        c2841qJ16.a(c2841qJ3);
                        break;
                    }
                    this.k[i3].a(true);
                    c2841qJ13.a(true);
                    this.k[i5].a(false);
                    i3 -= 2;
                }
            }
            i2 = i;
        }
        this.b.a(true);
        while (true) {
            int i8 = i2 - 1;
            if (i2 == 0) {
                break;
            }
            this.k[i8] = null;
            i2 = i8;
        }
        Object obj4 = c2841qJ4.b;
        c2841qJ4.b = obj2;
        return obj4;
    }

    /* JADX WARN: Code duplicated, block: B:114:0x0225  */
    /* JADX WARN: Code duplicated, block: B:116:0x0241  */
    /* JADX WARN: Code duplicated, block: B:120:0x0271  */
    /* JADX WARN: Code duplicated, block: B:121:0x0274  */
    /* JADX WARN: Code duplicated, block: B:123:0x027b  */
    /* JADX WARN: Code duplicated, block: B:124:0x0280  */
    /* JADX WARN: Code duplicated, block: B:127:0x028a  */
    /* JADX WARN: Code duplicated, block: B:129:0x029b  */
    /* JADX WARN: Code duplicated, block: B:131:0x02a7  */
    /* JADX WARN: Code duplicated, block: B:133:0x02bd  */
    /* JADX WARN: Code duplicated, block: B:134:0x02c0  */
    /* JADX WARN: Code duplicated, block: B:136:0x02c8  */
    /* JADX WARN: Code duplicated, block: B:137:0x02cd  */
    /* JADX WARN: Code duplicated, block: B:140:0x02dd  */
    /* JADX WARN: Code duplicated, block: B:155:0x0312  */
    /* JADX WARN: Code duplicated, block: B:157:0x032e  */
    /* JADX WARN: Code duplicated, block: B:161:0x035c  */
    /* JADX WARN: Code duplicated, block: B:162:0x035f  */
    /* JADX WARN: Code duplicated, block: B:164:0x0366  */
    /* JADX WARN: Code duplicated, block: B:165:0x036b  */
    /* JADX WARN: Code duplicated, block: B:168:0x0375  */
    /* JADX WARN: Code duplicated, block: B:86:0x01a7  */
    /* JADX WARN: Code duplicated, block: B:88:0x01ae  */
    /* JADX WARN: Code duplicated, block: B:90:0x01ba  */
    /* JADX WARN: Code duplicated, block: B:92:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:93:0x01d3  */
    /* JADX WARN: Code duplicated, block: B:95:0x01db  */
    /* JADX WARN: Code duplicated, block: B:96:0x01e0  */
    /* JADX WARN: Code duplicated, block: B:99:0x01f0  */
    @Override // java.util.Map
    public final Object remove(Object obj) {
        int i;
        C2841qJ c2841qJ;
        C2841qJ c2841qJ2;
        C2841qJ c2841qJ3;
        int i2;
        C2841qJ[] c2841qJArr;
        int i3;
        C2841qJ[] c2841qJArr2;
        int i4;
        C2841qJ c2841qJ4;
        C2841qJ c2841qJ5;
        int i5;
        C2841qJ[] c2841qJArr3;
        int i6;
        C2841qJ[] c2841qJArr4;
        int i7;
        this.i = false;
        C2841qJ c2841qJ6 = this.b;
        if (c2841qJ6 == null) {
            return this.a;
        }
        int i8 = 0;
        while (true) {
            int iCompareTo = ((Comparable) obj).compareTo(c2841qJ6.a);
            if (iCompareTo == 0) {
                if (c2841qJ6.c == null) {
                    C2841qJ c2841qJ7 = c2841qJ6.d;
                    if ((c2841qJ6.e & Integer.MIN_VALUE) == 0) {
                        while ((c2841qJ7.e & 1073741824) == 0) {
                            c2841qJ7 = c2841qJ7.c;
                        }
                    }
                    this.d = c2841qJ7;
                }
                if (c2841qJ6.d == null) {
                    this.e = c2841qJ6.e();
                }
                if (!c2841qJ6.f()) {
                    C2841qJ c2841qJ8 = c2841qJ6.d;
                    if (c2841qJ8.d()) {
                        c2841qJ8.c = c2841qJ6.c;
                        c2841qJ8.b(c2841qJ6.d());
                        if (!c2841qJ8.d()) {
                            c2841qJ8.e().d = c2841qJ8;
                        }
                        if (i8 == 0) {
                            this.b = c2841qJ8;
                        } else {
                            int i9 = i8 - 1;
                            if (this.j[i9]) {
                                this.k[i9].d = c2841qJ8;
                            } else {
                                this.k[i9].c = c2841qJ8;
                            }
                        }
                        boolean zB = c2841qJ8.b();
                        c2841qJ8.a(c2841qJ6.b());
                        c2841qJ6.a(zB);
                        this.j[i8] = true;
                        this.k[i8] = c2841qJ8;
                        i8++;
                    } else {
                        int i10 = i8 + 1;
                        while (true) {
                            this.j[i10] = false;
                            i = i10 + 1;
                            this.k[i10] = c2841qJ8;
                            c2841qJ = c2841qJ8.c;
                            if (c2841qJ.d()) {
                                break;
                            }
                            c2841qJ8 = c2841qJ;
                            i10 = i;
                        }
                        this.j[i8] = true;
                        this.k[i8] = c2841qJ;
                        if (c2841qJ.f()) {
                            c2841qJ8.a(c2841qJ);
                        } else {
                            c2841qJ8.c = c2841qJ.d;
                        }
                        c2841qJ.c = c2841qJ6.c;
                        if (!c2841qJ6.d()) {
                            c2841qJ6.e().d = c2841qJ;
                            c2841qJ.b(false);
                        }
                        C2841qJ c2841qJ9 = c2841qJ6.d;
                        c2841qJ.e &= Integer.MAX_VALUE;
                        c2841qJ.d = c2841qJ9;
                        boolean zB2 = c2841qJ.b();
                        c2841qJ.a(c2841qJ6.b());
                        c2841qJ6.a(zB2);
                        if (i8 == 0) {
                            this.b = c2841qJ;
                        } else {
                            int i11 = i8 - 1;
                            if (this.j[i11]) {
                                this.k[i11].d = c2841qJ;
                            } else {
                                this.k[i11].c = c2841qJ;
                            }
                        }
                        i8 = i;
                    }
                } else if (!c2841qJ6.d()) {
                    c2841qJ6.e().d = c2841qJ6.d;
                    if (i8 == 0) {
                        this.b = c2841qJ6.c;
                    } else {
                        int i12 = i8 - 1;
                        if (this.j[i12]) {
                            this.k[i12].d = c2841qJ6.c;
                        } else {
                            this.k[i12].c = c2841qJ6.c;
                        }
                    }
                } else if (i8 == 0) {
                    this.b = c2841qJ6.c;
                } else {
                    int i13 = i8 - 1;
                    if (this.j[i13]) {
                        this.k[i13].b(c2841qJ6.d);
                    } else {
                        this.k[i13].a(c2841qJ6.c);
                    }
                }
                if (c2841qJ6.b()) {
                    int i14 = i8;
                    while (true) {
                        if (i8 > 0) {
                            int i15 = i8 - 1;
                            if ((this.j[i15] && !this.k[i15].f()) || (!this.j[i15] && !this.k[i15].d())) {
                                C2841qJ c2841qJ10 = this.j[i15] ? this.k[i15].d : this.k[i15].c;
                                if (!c2841qJ10.b()) {
                                    c2841qJ10.a(true);
                                } else if (this.j[i15]) {
                                    c2841qJ2 = this.k[i15].c;
                                    if (!c2841qJ2.b()) {
                                        c2841qJ2.a(true);
                                        this.k[i15].a(false);
                                        c2841qJArr2 = this.k;
                                        C2841qJ c2841qJ11 = c2841qJArr2[i15];
                                        c2841qJ11.c = c2841qJ2.d;
                                        c2841qJ2.d = c2841qJ11;
                                        if (i8 < 2) {
                                            this.b = c2841qJ2;
                                        } else {
                                            i4 = i8 - 2;
                                            if (this.j[i4]) {
                                                c2841qJArr2[i4].d = c2841qJ2;
                                            } else {
                                                c2841qJArr2[i4].c = c2841qJ2;
                                            }
                                        }
                                        c2841qJArr2[i8] = c2841qJ11;
                                        this.j[i8] = true;
                                        c2841qJArr2[i15] = c2841qJ2;
                                        int i16 = i8 + 1;
                                        if (i14 == i8) {
                                            i14++;
                                        }
                                        c2841qJ2 = c2841qJArr2[i8].c;
                                        i8 = i16;
                                    }
                                    if (c2841qJ2.d()) {
                                        c2841qJ2.a(false);
                                        i8--;
                                    } else {
                                        c2841qJ2.a(false);
                                        i8--;
                                    }
                                    if (c2841qJ2.d()) {
                                        c2841qJ3 = c2841qJ2.d;
                                        c2841qJ3.a(true);
                                        c2841qJ2.a(false);
                                        c2841qJ2.d = c2841qJ3.c;
                                        c2841qJ3.c = c2841qJ2;
                                        this.k[i8 - 1].c = c2841qJ3;
                                        if (c2841qJ3.d()) {
                                            c2841qJ3.b(false);
                                            c2841qJ3.c.b(c2841qJ3);
                                        }
                                        c2841qJ2 = c2841qJ3;
                                    } else {
                                        c2841qJ3 = c2841qJ2.d;
                                        c2841qJ3.a(true);
                                        c2841qJ2.a(false);
                                        c2841qJ2.d = c2841qJ3.c;
                                        c2841qJ3.c = c2841qJ2;
                                        this.k[i8 - 1].c = c2841qJ3;
                                        if (c2841qJ3.d()) {
                                            c2841qJ3.b(false);
                                            c2841qJ3.c.b(c2841qJ3);
                                        }
                                        c2841qJ2 = c2841qJ3;
                                    }
                                    i2 = i8 - 1;
                                    c2841qJ2.a(this.k[i2].b());
                                    this.k[i2].a(true);
                                    c2841qJ2.c.a(true);
                                    c2841qJArr = this.k;
                                    C2841qJ c2841qJ12 = c2841qJArr[i2];
                                    c2841qJ12.c = c2841qJ2.d;
                                    c2841qJ2.d = c2841qJ12;
                                    if (i8 < 2) {
                                        this.b = c2841qJ2;
                                    } else {
                                        i3 = i8 - 2;
                                        if (this.j[i3]) {
                                            c2841qJArr[i3].d = c2841qJ2;
                                        } else {
                                            c2841qJArr[i3].c = c2841qJ2;
                                        }
                                    }
                                    if (c2841qJ2.f()) {
                                        c2841qJ2.e &= Integer.MAX_VALUE;
                                        this.k[i2].a(c2841qJ2);
                                    }
                                } else {
                                    c2841qJ4 = this.k[i15].d;
                                    if (!c2841qJ4.b()) {
                                        c2841qJ4.a(true);
                                        this.k[i15].a(false);
                                        c2841qJArr4 = this.k;
                                        C2841qJ c2841qJ13 = c2841qJArr4[i15];
                                        c2841qJ13.d = c2841qJ4.c;
                                        c2841qJ4.c = c2841qJ13;
                                        if (i8 < 2) {
                                            this.b = c2841qJ4;
                                        } else {
                                            i7 = i8 - 2;
                                            if (this.j[i7]) {
                                                c2841qJArr4[i7].d = c2841qJ4;
                                            } else {
                                                c2841qJArr4[i7].c = c2841qJ4;
                                            }
                                        }
                                        c2841qJArr4[i8] = c2841qJ13;
                                        this.j[i8] = false;
                                        c2841qJArr4[i15] = c2841qJ4;
                                        int i17 = i8 + 1;
                                        if (i14 == i8) {
                                            i14++;
                                        }
                                        c2841qJ4 = c2841qJArr4[i8].d;
                                        i8 = i17;
                                    }
                                    if (c2841qJ4.d()) {
                                        c2841qJ4.a(false);
                                        i8--;
                                    } else {
                                        c2841qJ4.a(false);
                                        i8--;
                                    }
                                    if (c2841qJ4.f()) {
                                        c2841qJ5 = c2841qJ4.c;
                                        c2841qJ5.a(true);
                                        c2841qJ4.a(false);
                                        c2841qJ4.c = c2841qJ5.d;
                                        c2841qJ5.d = c2841qJ4;
                                        this.k[i8 - 1].d = c2841qJ5;
                                        if (c2841qJ5.f()) {
                                            c2841qJ5.e &= Integer.MAX_VALUE;
                                            c2841qJ5.d.a(c2841qJ5);
                                        }
                                        c2841qJ4 = c2841qJ5;
                                    } else {
                                        c2841qJ5 = c2841qJ4.c;
                                        c2841qJ5.a(true);
                                        c2841qJ4.a(false);
                                        c2841qJ4.c = c2841qJ5.d;
                                        c2841qJ5.d = c2841qJ4;
                                        this.k[i8 - 1].d = c2841qJ5;
                                        if (c2841qJ5.f()) {
                                            c2841qJ5.e &= Integer.MAX_VALUE;
                                            c2841qJ5.d.a(c2841qJ5);
                                        }
                                        c2841qJ4 = c2841qJ5;
                                    }
                                    i5 = i8 - 1;
                                    c2841qJ4.a(this.k[i5].b());
                                    this.k[i5].a(true);
                                    c2841qJ4.d.a(true);
                                    c2841qJArr3 = this.k;
                                    C2841qJ c2841qJ14 = c2841qJArr3[i5];
                                    c2841qJ14.d = c2841qJ4.c;
                                    c2841qJ4.c = c2841qJ14;
                                    if (i8 < 2) {
                                        this.b = c2841qJ4;
                                    } else {
                                        i6 = i8 - 2;
                                        if (this.j[i6]) {
                                            c2841qJArr3[i6].d = c2841qJ4;
                                        } else {
                                            c2841qJArr3[i6].c = c2841qJ4;
                                        }
                                    }
                                    if (c2841qJ4.d()) {
                                        c2841qJ4.b(false);
                                        this.k[i5].b(c2841qJ4);
                                    }
                                }
                            } else if (this.j[i15]) {
                                c2841qJ4 = this.k[i15].d;
                                if (!c2841qJ4.b()) {
                                    c2841qJ4.a(true);
                                    this.k[i15].a(false);
                                    c2841qJArr4 = this.k;
                                    C2841qJ c2841qJ15 = c2841qJArr4[i15];
                                    c2841qJ15.d = c2841qJ4.c;
                                    c2841qJ4.c = c2841qJ15;
                                    if (i8 < 2) {
                                        this.b = c2841qJ4;
                                    } else {
                                        i7 = i8 - 2;
                                        if (this.j[i7]) {
                                            c2841qJArr4[i7].d = c2841qJ4;
                                        } else {
                                            c2841qJArr4[i7].c = c2841qJ4;
                                        }
                                    }
                                    c2841qJArr4[i8] = c2841qJ15;
                                    this.j[i8] = false;
                                    c2841qJArr4[i15] = c2841qJ4;
                                    int i18 = i8 + 1;
                                    if (i14 == i8) {
                                        i14++;
                                    }
                                    c2841qJ4 = c2841qJArr4[i8].d;
                                    i8 = i18;
                                }
                                if ((c2841qJ4.d() || c2841qJ4.c.b()) && (c2841qJ4.f() || c2841qJ4.d.b())) {
                                    c2841qJ4.a(false);
                                    i8--;
                                } else {
                                    if (c2841qJ4.f() || c2841qJ4.d.b()) {
                                        c2841qJ5 = c2841qJ4.c;
                                        c2841qJ5.a(true);
                                        c2841qJ4.a(false);
                                        c2841qJ4.c = c2841qJ5.d;
                                        c2841qJ5.d = c2841qJ4;
                                        this.k[i8 - 1].d = c2841qJ5;
                                        if (c2841qJ5.f()) {
                                            c2841qJ5.e &= Integer.MAX_VALUE;
                                            c2841qJ5.d.a(c2841qJ5);
                                        }
                                        c2841qJ4 = c2841qJ5;
                                    }
                                    i5 = i8 - 1;
                                    c2841qJ4.a(this.k[i5].b());
                                    this.k[i5].a(true);
                                    c2841qJ4.d.a(true);
                                    c2841qJArr3 = this.k;
                                    C2841qJ c2841qJ16 = c2841qJArr3[i5];
                                    c2841qJ16.d = c2841qJ4.c;
                                    c2841qJ4.c = c2841qJ16;
                                    if (i8 < 2) {
                                        this.b = c2841qJ4;
                                    } else {
                                        i6 = i8 - 2;
                                        if (this.j[i6]) {
                                            c2841qJArr3[i6].d = c2841qJ4;
                                        } else {
                                            c2841qJArr3[i6].c = c2841qJ4;
                                        }
                                    }
                                    if (c2841qJ4.d()) {
                                        c2841qJ4.b(false);
                                        this.k[i5].b(c2841qJ4);
                                    }
                                }
                            } else {
                                c2841qJ2 = this.k[i15].c;
                                if (!c2841qJ2.b()) {
                                    c2841qJ2.a(true);
                                    this.k[i15].a(false);
                                    c2841qJArr2 = this.k;
                                    C2841qJ c2841qJ17 = c2841qJArr2[i15];
                                    c2841qJ17.c = c2841qJ2.d;
                                    c2841qJ2.d = c2841qJ17;
                                    if (i8 < 2) {
                                        this.b = c2841qJ2;
                                    } else {
                                        i4 = i8 - 2;
                                        if (this.j[i4]) {
                                            c2841qJArr2[i4].d = c2841qJ2;
                                        } else {
                                            c2841qJArr2[i4].c = c2841qJ2;
                                        }
                                    }
                                    c2841qJArr2[i8] = c2841qJ17;
                                    this.j[i8] = true;
                                    c2841qJArr2[i15] = c2841qJ2;
                                    int i19 = i8 + 1;
                                    if (i14 == i8) {
                                        i14++;
                                    }
                                    c2841qJ2 = c2841qJArr2[i8].c;
                                    i8 = i19;
                                }
                                if ((c2841qJ2.d() || c2841qJ2.c.b()) && (c2841qJ2.f() || c2841qJ2.d.b())) {
                                    c2841qJ2.a(false);
                                    i8--;
                                } else {
                                    if (c2841qJ2.d() || c2841qJ2.c.b()) {
                                        c2841qJ3 = c2841qJ2.d;
                                        c2841qJ3.a(true);
                                        c2841qJ2.a(false);
                                        c2841qJ2.d = c2841qJ3.c;
                                        c2841qJ3.c = c2841qJ2;
                                        this.k[i8 - 1].c = c2841qJ3;
                                        if (c2841qJ3.d()) {
                                            c2841qJ3.b(false);
                                            c2841qJ3.c.b(c2841qJ3);
                                        }
                                        c2841qJ2 = c2841qJ3;
                                    }
                                    i2 = i8 - 1;
                                    c2841qJ2.a(this.k[i2].b());
                                    this.k[i2].a(true);
                                    c2841qJ2.c.a(true);
                                    c2841qJArr = this.k;
                                    C2841qJ c2841qJ18 = c2841qJArr[i2];
                                    c2841qJ18.c = c2841qJ2.d;
                                    c2841qJ2.d = c2841qJ18;
                                    if (i8 < 2) {
                                        this.b = c2841qJ2;
                                    } else {
                                        i3 = i8 - 2;
                                        if (this.j[i3]) {
                                            c2841qJArr[i3].d = c2841qJ2;
                                        } else {
                                            c2841qJArr[i3].c = c2841qJ2;
                                        }
                                    }
                                    if (c2841qJ2.f()) {
                                        c2841qJ2.e &= Integer.MAX_VALUE;
                                        this.k[i2].a(c2841qJ2);
                                    }
                                }
                            }
                        }
                        i8 = i14;
                        C2841qJ c2841qJ19 = this.b;
                        if (c2841qJ19 != null) {
                            c2841qJ19.a(true);
                        }
                    }
                }
                this.i = true;
                this.c--;
                while (true) {
                    int i20 = i8 - 1;
                    if (i8 == 0) {
                        return c2841qJ6.b;
                    }
                    this.k[i20] = null;
                    i8 = i20;
                }
            } else {
                boolean[] zArr = this.j;
                boolean z = iCompareTo > 0;
                zArr[i8] = z;
                this.k[i8] = c2841qJ6;
                i8++;
                if (z) {
                    c2841qJ6 = (c2841qJ6.e & Integer.MIN_VALUE) != 0 ? null : c2841qJ6.d;
                    if (c2841qJ6 == null) {
                        while (true) {
                            int i21 = i8 - 1;
                            if (i8 == 0) {
                                return this.a;
                            }
                            this.k[i21] = null;
                            i8 = i21;
                        }
                    }
                } else {
                    c2841qJ6 = (c2841qJ6.e & 1073741824) != 0 ? null : c2841qJ6.c;
                    if (c2841qJ6 == null) {
                        while (true) {
                            int i22 = i8 - 1;
                            if (i8 == 0) {
                                return this.a;
                            }
                            this.k[i22] = null;
                            i8 = i22;
                        }
                    }
                }
            }
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.c;
    }

    @Override // java.util.Map, java.util.SortedMap
    public final Collection values() {
        if (this.h == null) {
            this.h = new C2770pJ(this);
        }
        return this.h;
    }

    @Override // com.android.tools.r8.internal.R0
    /* JADX INFO: renamed from: e */
    public final R0 tailMap(Object obj) {
        return new BJ(this, obj, false, null, true);
    }
}
