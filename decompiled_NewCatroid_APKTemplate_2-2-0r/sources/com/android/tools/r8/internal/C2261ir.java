package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ir, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2261ir extends I implements Cloneable {
    protected transient C1431Uq b;
    protected int c;
    protected transient C1431Uq d;
    protected transient C1431Uq e;
    protected transient InterfaceC2842qK f;
    protected transient InterfaceC1357Sr g;
    protected transient InterfaceC2703oS h;
    protected transient boolean i;
    private transient boolean[] j;
    private transient C1431Uq[] k;

    public C2261ir() {
        k();
        this.b = null;
        this.c = 0;
    }

    private void k() {
        this.j = new boolean[64];
        this.k = new C1431Uq[64];
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object a(int i, Object obj) {
        int i2;
        C1431Uq c1431Uq;
        C1431Uq c1431Uq2;
        C1431Uq c1431Uq3;
        int i3 = 0;
        this.i = false;
        C1431Uq c1431Uq4 = this.b;
        if (c1431Uq4 == null) {
            this.c++;
            c1431Uq4 = new C1431Uq(i, this.a);
            this.d = c1431Uq4;
            this.e = c1431Uq4;
            this.b = c1431Uq4;
        } else {
            int i4 = 0;
            while (true) {
                int iCompare = Integer.compare(i, c1431Uq4.a);
                if (iCompare == 0) {
                    while (true) {
                        int i5 = i4 - 1;
                        if (i4 == 0) {
                            break;
                        }
                        this.k[i5] = null;
                        i4 = i5;
                    }
                } else {
                    this.k[i4] = c1431Uq4;
                    boolean[] zArr = this.j;
                    i2 = i4 + 1;
                    boolean z = iCompare > 0;
                    zArr[i4] = z;
                    if (z) {
                        if (c1431Uq4.f()) {
                            this.c++;
                            c1431Uq = new C1431Uq(i, this.a);
                            C1431Uq c1431Uq5 = c1431Uq4.d;
                            if (c1431Uq5 == null) {
                                this.e = c1431Uq;
                            }
                            c1431Uq.c = c1431Uq4;
                            c1431Uq.d = c1431Uq5;
                            c1431Uq4.e &= Integer.MAX_VALUE;
                            c1431Uq4.d = c1431Uq;
                            break;
                        }
                        c1431Uq4 = c1431Uq4.d;
                        i4 = i2;
                    } else {
                        if (c1431Uq4.d()) {
                            this.c++;
                            c1431Uq = new C1431Uq(i, this.a);
                            C1431Uq c1431Uq6 = c1431Uq4.c;
                            if (c1431Uq6 == null) {
                                this.d = c1431Uq;
                            }
                            c1431Uq.d = c1431Uq4;
                            c1431Uq.c = c1431Uq6;
                            c1431Uq4.e &= -1073741825;
                            c1431Uq4.c = c1431Uq;
                            break;
                        }
                        c1431Uq4 = c1431Uq4.c;
                        i4 = i2;
                    }
                }
                Object obj2 = c1431Uq4.b;
                c1431Uq4.b = obj;
                return obj2;
            }
            c1431Uq4 = c1431Uq;
            this.i = true;
            while (i4 > 0 && !this.k[i4].b()) {
                int i6 = i4 - 1;
                if (this.j[i6]) {
                    C1431Uq c1431Uq7 = this.k[i6];
                    C1431Uq c1431Uq8 = c1431Uq7.c;
                    if (c1431Uq7.d() || c1431Uq8.b()) {
                        if (this.j[i4]) {
                            c1431Uq2 = this.k[i4];
                        } else {
                            C1431Uq[] c1431UqArr = this.k;
                            C1431Uq c1431Uq9 = c1431UqArr[i4];
                            C1431Uq c1431Uq10 = c1431Uq9.c;
                            c1431Uq9.c = c1431Uq10.d;
                            c1431Uq10.d = c1431Uq9;
                            c1431UqArr[i6].d = c1431Uq10;
                            if (c1431Uq10.f()) {
                                c1431Uq10.e &= Integer.MAX_VALUE;
                                c1431Uq9.a(c1431Uq10);
                            }
                            c1431Uq2 = c1431Uq10;
                        }
                        C1431Uq c1431Uq11 = this.k[i6];
                        c1431Uq11.a(false);
                        c1431Uq2.a(true);
                        c1431Uq11.d = c1431Uq2.c;
                        c1431Uq2.c = c1431Uq11;
                        if (i4 < 2) {
                            this.b = c1431Uq2;
                        } else {
                            int i7 = i4 - 2;
                            if (this.j[i7]) {
                                this.k[i7].d = c1431Uq2;
                            } else {
                                this.k[i7].c = c1431Uq2;
                            }
                        }
                        if (!c1431Uq2.d()) {
                            break;
                        }
                        c1431Uq2.b(false);
                        c1431Uq11.b(c1431Uq2);
                        break;
                    }
                    this.k[i4].a(true);
                    c1431Uq8.a(true);
                    this.k[i6].a(false);
                    i4 -= 2;
                } else {
                    C1431Uq c1431Uq12 = this.k[i6];
                    C1431Uq c1431Uq13 = c1431Uq12.d;
                    if (c1431Uq12.f() || c1431Uq13.b()) {
                        if (this.j[i4]) {
                            C1431Uq[] c1431UqArr2 = this.k;
                            C1431Uq c1431Uq14 = c1431UqArr2[i4];
                            C1431Uq c1431Uq15 = c1431Uq14.d;
                            c1431Uq14.d = c1431Uq15.c;
                            c1431Uq15.c = c1431Uq14;
                            c1431UqArr2[i6].c = c1431Uq15;
                            if (c1431Uq15.d()) {
                                c1431Uq15.b(false);
                                c1431Uq14.b(c1431Uq15);
                            }
                            c1431Uq3 = c1431Uq15;
                        } else {
                            c1431Uq3 = this.k[i4];
                        }
                        C1431Uq c1431Uq16 = this.k[i6];
                        c1431Uq16.a(false);
                        c1431Uq3.a(true);
                        c1431Uq16.c = c1431Uq3.d;
                        c1431Uq3.d = c1431Uq16;
                        if (i4 < 2) {
                            this.b = c1431Uq3;
                        } else {
                            int i8 = i4 - 2;
                            if (this.j[i8]) {
                                this.k[i8].d = c1431Uq3;
                            } else {
                                this.k[i8].c = c1431Uq3;
                            }
                        }
                        if (!c1431Uq3.f()) {
                            break;
                        }
                        c1431Uq3.e &= Integer.MAX_VALUE;
                        c1431Uq16.a(c1431Uq3);
                        break;
                    }
                    this.k[i4].a(true);
                    c1431Uq13.a(true);
                    this.k[i6].a(false);
                    i4 -= 2;
                }
            }
            i3 = i2;
        }
        this.b.a(true);
        while (true) {
            int i9 = i3 - 1;
            if (i3 == 0) {
                break;
            }
            this.k[i9] = null;
            i3 = i9;
        }
        Object obj3 = c1431Uq4.b;
        c1431Uq4.b = obj;
        return obj3;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr b(int i) {
        return new C2028fr(this, i, false, 0, true);
    }

    final int c(int i, int i2) {
        return Integer.compare(i, i2);
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
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
            C2261ir c2261ir = (C2261ir) super.clone();
            c2261ir.g = null;
            c2261ir.h = null;
            c2261ir.f = null;
            c2261ir.k();
            if (this.c != 0) {
                C1431Uq c1431Uq = new C1431Uq();
                C1431Uq c1431Uq2 = new C1431Uq();
                C1431Uq c1431Uq3 = this.b;
                c1431Uq.e &= -1073741825;
                c1431Uq.c = c1431Uq3;
                c1431Uq2.a((C1431Uq) null);
                C1431Uq c1431Uq4 = c1431Uq2;
                loop0: while (true) {
                    if (c1431Uq.d()) {
                        while (c1431Uq.f()) {
                            c1431Uq = c1431Uq.d;
                            if (c1431Uq == null) {
                                break loop0;
                            }
                            c1431Uq4 = c1431Uq4.d;
                        }
                        c1431Uq = c1431Uq.d;
                        c1431Uq4 = c1431Uq4.d;
                    } else {
                        C1431Uq c1431UqClone = c1431Uq.c.clone();
                        c1431UqClone.a(c1431Uq4.c);
                        c1431UqClone.b(c1431Uq4);
                        c1431Uq4.e &= -1073741825;
                        c1431Uq4.c = c1431UqClone;
                        c1431Uq = c1431Uq.c;
                        c1431Uq4 = c1431UqClone;
                    }
                    if (!c1431Uq.f()) {
                        C1431Uq c1431UqClone2 = c1431Uq.d.clone();
                        c1431UqClone2.b(c1431Uq4.d);
                        c1431UqClone2.a(c1431Uq4);
                        c1431Uq4.e &= Integer.MAX_VALUE;
                        c1431Uq4.d = c1431UqClone2;
                    }
                }
                c1431Uq4.d = null;
                C1431Uq c1431Uq5 = c1431Uq2.c;
                c2261ir.b = c1431Uq5;
                c2261ir.d = c1431Uq5;
                while (true) {
                    C1431Uq c1431Uq6 = c2261ir.d.c;
                    if (c1431Uq6 == null) {
                        break;
                    }
                    c2261ir.d = c1431Uq6;
                }
                c2261ir.e = c2261ir.b;
                while (true) {
                    C1431Uq c1431Uq7 = c2261ir.e.d;
                    if (c1431Uq7 == null) {
                        break;
                    }
                    c2261ir.e = c1431Uq7;
                }
            }
            return c2261ir;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC3391xr comparator() {
        return null;
    }

    @Override // java.util.SortedMap
    public final /* bridge */ /* synthetic */ Comparator comparator() {
        return null;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        C2183hr c2183hr = new C2183hr(this, 0);
        int i = this.c;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            if (c2183hr.a().b == obj) {
                return true;
            }
            i = i2;
        }
    }

    final C1431Uq d(int i) {
        C1431Uq c1431Uq = this.b;
        while (c1431Uq != null) {
            int iCompare = Integer.compare(i, c1431Uq.a);
            if (iCompare == 0) {
                break;
            }
            if (iCompare < 0) {
                c1431Uq = (c1431Uq.e & 1073741824) != 0 ? null : c1431Uq.c;
            } else if ((c1431Uq.e & Integer.MIN_VALUE) == 0) {
                c1431Uq = c1431Uq.d;
            }
        }
        return c1431Uq;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0023  */
    final C1431Uq e(int i) {
        C1431Uq c1431Uq;
        C1431Uq c1431Uq2 = this.b;
        int iCompare = 0;
        C1431Uq c1431Uq3 = c1431Uq2;
        while (c1431Uq2 != null) {
            iCompare = Integer.compare(i, c1431Uq2.a);
            if (iCompare == 0) {
                break;
            }
            if (iCompare < 0) {
                if ((c1431Uq2.e & 1073741824) != 0) {
                    c1431Uq = null;
                } else {
                    c1431Uq = c1431Uq2.c;
                }
            } else if ((c1431Uq2.e & Integer.MIN_VALUE) != 0) {
                c1431Uq = null;
            } else {
                c1431Uq = c1431Uq2.d;
            }
            C1431Uq c1431Uq4 = c1431Uq;
            c1431Uq3 = c1431Uq2;
            c1431Uq2 = c1431Uq4;
        }
        return iCompare == 0 ? c1431Uq2 : c1431Uq3;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3087tq
    public final Object get(int i) {
        C1431Uq c1431UqD = d(i);
        return c1431UqD == null ? this.a : c1431UqD.b;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.c == 0;
    }

    @Override // com.android.tools.r8.internal.I, com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC1357Sr keySet() {
        if (this.g == null) {
            this.g = new C1512Xq(this, 0);
        }
        return this.g;
    }

    /* JADX WARN: Code duplicated, block: B:114:0x0222  */
    /* JADX WARN: Code duplicated, block: B:116:0x023e  */
    /* JADX WARN: Code duplicated, block: B:120:0x026e  */
    /* JADX WARN: Code duplicated, block: B:121:0x0271  */
    /* JADX WARN: Code duplicated, block: B:123:0x0278  */
    /* JADX WARN: Code duplicated, block: B:124:0x027d  */
    /* JADX WARN: Code duplicated, block: B:127:0x0287  */
    /* JADX WARN: Code duplicated, block: B:129:0x0298  */
    /* JADX WARN: Code duplicated, block: B:131:0x02a4  */
    /* JADX WARN: Code duplicated, block: B:133:0x02ba  */
    /* JADX WARN: Code duplicated, block: B:134:0x02bd  */
    /* JADX WARN: Code duplicated, block: B:136:0x02c5  */
    /* JADX WARN: Code duplicated, block: B:137:0x02ca  */
    /* JADX WARN: Code duplicated, block: B:140:0x02da  */
    /* JADX WARN: Code duplicated, block: B:155:0x030f  */
    /* JADX WARN: Code duplicated, block: B:157:0x032b  */
    /* JADX WARN: Code duplicated, block: B:161:0x0359  */
    /* JADX WARN: Code duplicated, block: B:162:0x035c  */
    /* JADX WARN: Code duplicated, block: B:164:0x0363  */
    /* JADX WARN: Code duplicated, block: B:165:0x0368  */
    /* JADX WARN: Code duplicated, block: B:168:0x0372  */
    /* JADX WARN: Code duplicated, block: B:86:0x01a4  */
    /* JADX WARN: Code duplicated, block: B:88:0x01ab  */
    /* JADX WARN: Code duplicated, block: B:90:0x01b7  */
    /* JADX WARN: Code duplicated, block: B:92:0x01cd  */
    /* JADX WARN: Code duplicated, block: B:93:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:95:0x01d8  */
    /* JADX WARN: Code duplicated, block: B:96:0x01dd  */
    /* JADX WARN: Code duplicated, block: B:99:0x01ed  */
    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object remove(int i) {
        int i2;
        C1431Uq c1431Uq;
        C1431Uq c1431Uq2;
        C1431Uq c1431Uq3;
        int i3;
        C1431Uq[] c1431UqArr;
        int i4;
        C1431Uq[] c1431UqArr2;
        int i5;
        C1431Uq c1431Uq4;
        C1431Uq c1431Uq5;
        int i6;
        C1431Uq[] c1431UqArr3;
        int i7;
        C1431Uq[] c1431UqArr4;
        int i8;
        this.i = false;
        C1431Uq c1431Uq6 = this.b;
        if (c1431Uq6 == null) {
            return this.a;
        }
        int i9 = 0;
        while (true) {
            int iCompare = Integer.compare(i, c1431Uq6.a);
            if (iCompare == 0) {
                if (c1431Uq6.c == null) {
                    C1431Uq c1431Uq7 = c1431Uq6.d;
                    if ((c1431Uq6.e & Integer.MIN_VALUE) == 0) {
                        while ((c1431Uq7.e & 1073741824) == 0) {
                            c1431Uq7 = c1431Uq7.c;
                        }
                    }
                    this.d = c1431Uq7;
                }
                if (c1431Uq6.d == null) {
                    this.e = c1431Uq6.e();
                }
                if (!c1431Uq6.f()) {
                    C1431Uq c1431Uq8 = c1431Uq6.d;
                    if (c1431Uq8.d()) {
                        c1431Uq8.c = c1431Uq6.c;
                        c1431Uq8.b(c1431Uq6.d());
                        if (!c1431Uq8.d()) {
                            c1431Uq8.e().d = c1431Uq8;
                        }
                        if (i9 == 0) {
                            this.b = c1431Uq8;
                        } else {
                            int i10 = i9 - 1;
                            if (this.j[i10]) {
                                this.k[i10].d = c1431Uq8;
                            } else {
                                this.k[i10].c = c1431Uq8;
                            }
                        }
                        boolean zB = c1431Uq8.b();
                        c1431Uq8.a(c1431Uq6.b());
                        c1431Uq6.a(zB);
                        this.j[i9] = true;
                        this.k[i9] = c1431Uq8;
                        i9++;
                    } else {
                        int i11 = i9 + 1;
                        while (true) {
                            this.j[i11] = false;
                            i2 = i11 + 1;
                            this.k[i11] = c1431Uq8;
                            c1431Uq = c1431Uq8.c;
                            if (c1431Uq.d()) {
                                break;
                            }
                            c1431Uq8 = c1431Uq;
                            i11 = i2;
                        }
                        this.j[i9] = true;
                        this.k[i9] = c1431Uq;
                        if (c1431Uq.f()) {
                            c1431Uq8.a(c1431Uq);
                        } else {
                            c1431Uq8.c = c1431Uq.d;
                        }
                        c1431Uq.c = c1431Uq6.c;
                        if (!c1431Uq6.d()) {
                            c1431Uq6.e().d = c1431Uq;
                            c1431Uq.b(false);
                        }
                        C1431Uq c1431Uq9 = c1431Uq6.d;
                        c1431Uq.e &= Integer.MAX_VALUE;
                        c1431Uq.d = c1431Uq9;
                        boolean zB2 = c1431Uq.b();
                        c1431Uq.a(c1431Uq6.b());
                        c1431Uq6.a(zB2);
                        if (i9 == 0) {
                            this.b = c1431Uq;
                        } else {
                            int i12 = i9 - 1;
                            if (this.j[i12]) {
                                this.k[i12].d = c1431Uq;
                            } else {
                                this.k[i12].c = c1431Uq;
                            }
                        }
                        i9 = i2;
                    }
                } else if (!c1431Uq6.d()) {
                    c1431Uq6.e().d = c1431Uq6.d;
                    if (i9 == 0) {
                        this.b = c1431Uq6.c;
                    } else {
                        int i13 = i9 - 1;
                        if (this.j[i13]) {
                            this.k[i13].d = c1431Uq6.c;
                        } else {
                            this.k[i13].c = c1431Uq6.c;
                        }
                    }
                } else if (i9 == 0) {
                    this.b = c1431Uq6.c;
                } else {
                    int i14 = i9 - 1;
                    if (this.j[i14]) {
                        this.k[i14].b(c1431Uq6.d);
                    } else {
                        this.k[i14].a(c1431Uq6.c);
                    }
                }
                if (c1431Uq6.b()) {
                    int i15 = i9;
                    while (true) {
                        if (i9 > 0) {
                            int i16 = i9 - 1;
                            if ((this.j[i16] && !this.k[i16].f()) || (!this.j[i16] && !this.k[i16].d())) {
                                C1431Uq c1431Uq10 = this.j[i16] ? this.k[i16].d : this.k[i16].c;
                                if (!c1431Uq10.b()) {
                                    c1431Uq10.a(true);
                                } else if (this.j[i16]) {
                                    c1431Uq2 = this.k[i16].c;
                                    if (!c1431Uq2.b()) {
                                        c1431Uq2.a(true);
                                        this.k[i16].a(false);
                                        c1431UqArr2 = this.k;
                                        C1431Uq c1431Uq11 = c1431UqArr2[i16];
                                        c1431Uq11.c = c1431Uq2.d;
                                        c1431Uq2.d = c1431Uq11;
                                        if (i9 < 2) {
                                            this.b = c1431Uq2;
                                        } else {
                                            i5 = i9 - 2;
                                            if (this.j[i5]) {
                                                c1431UqArr2[i5].d = c1431Uq2;
                                            } else {
                                                c1431UqArr2[i5].c = c1431Uq2;
                                            }
                                        }
                                        c1431UqArr2[i9] = c1431Uq11;
                                        this.j[i9] = true;
                                        c1431UqArr2[i16] = c1431Uq2;
                                        int i17 = i9 + 1;
                                        if (i15 == i9) {
                                            i15++;
                                        }
                                        c1431Uq2 = c1431UqArr2[i9].c;
                                        i9 = i17;
                                    }
                                    if (c1431Uq2.d()) {
                                        c1431Uq2.a(false);
                                        i9--;
                                    } else {
                                        c1431Uq2.a(false);
                                        i9--;
                                    }
                                    if (c1431Uq2.d()) {
                                        c1431Uq3 = c1431Uq2.d;
                                        c1431Uq3.a(true);
                                        c1431Uq2.a(false);
                                        c1431Uq2.d = c1431Uq3.c;
                                        c1431Uq3.c = c1431Uq2;
                                        this.k[i9 - 1].c = c1431Uq3;
                                        if (c1431Uq3.d()) {
                                            c1431Uq3.b(false);
                                            c1431Uq3.c.b(c1431Uq3);
                                        }
                                        c1431Uq2 = c1431Uq3;
                                    } else {
                                        c1431Uq3 = c1431Uq2.d;
                                        c1431Uq3.a(true);
                                        c1431Uq2.a(false);
                                        c1431Uq2.d = c1431Uq3.c;
                                        c1431Uq3.c = c1431Uq2;
                                        this.k[i9 - 1].c = c1431Uq3;
                                        if (c1431Uq3.d()) {
                                            c1431Uq3.b(false);
                                            c1431Uq3.c.b(c1431Uq3);
                                        }
                                        c1431Uq2 = c1431Uq3;
                                    }
                                    i3 = i9 - 1;
                                    c1431Uq2.a(this.k[i3].b());
                                    this.k[i3].a(true);
                                    c1431Uq2.c.a(true);
                                    c1431UqArr = this.k;
                                    C1431Uq c1431Uq12 = c1431UqArr[i3];
                                    c1431Uq12.c = c1431Uq2.d;
                                    c1431Uq2.d = c1431Uq12;
                                    if (i9 < 2) {
                                        this.b = c1431Uq2;
                                    } else {
                                        i4 = i9 - 2;
                                        if (this.j[i4]) {
                                            c1431UqArr[i4].d = c1431Uq2;
                                        } else {
                                            c1431UqArr[i4].c = c1431Uq2;
                                        }
                                    }
                                    if (c1431Uq2.f()) {
                                        c1431Uq2.e &= Integer.MAX_VALUE;
                                        this.k[i3].a(c1431Uq2);
                                    }
                                } else {
                                    c1431Uq4 = this.k[i16].d;
                                    if (!c1431Uq4.b()) {
                                        c1431Uq4.a(true);
                                        this.k[i16].a(false);
                                        c1431UqArr4 = this.k;
                                        C1431Uq c1431Uq13 = c1431UqArr4[i16];
                                        c1431Uq13.d = c1431Uq4.c;
                                        c1431Uq4.c = c1431Uq13;
                                        if (i9 < 2) {
                                            this.b = c1431Uq4;
                                        } else {
                                            i8 = i9 - 2;
                                            if (this.j[i8]) {
                                                c1431UqArr4[i8].d = c1431Uq4;
                                            } else {
                                                c1431UqArr4[i8].c = c1431Uq4;
                                            }
                                        }
                                        c1431UqArr4[i9] = c1431Uq13;
                                        this.j[i9] = false;
                                        c1431UqArr4[i16] = c1431Uq4;
                                        int i18 = i9 + 1;
                                        if (i15 == i9) {
                                            i15++;
                                        }
                                        c1431Uq4 = c1431UqArr4[i9].d;
                                        i9 = i18;
                                    }
                                    if (c1431Uq4.d()) {
                                        c1431Uq4.a(false);
                                        i9--;
                                    } else {
                                        c1431Uq4.a(false);
                                        i9--;
                                    }
                                    if (c1431Uq4.f()) {
                                        c1431Uq5 = c1431Uq4.c;
                                        c1431Uq5.a(true);
                                        c1431Uq4.a(false);
                                        c1431Uq4.c = c1431Uq5.d;
                                        c1431Uq5.d = c1431Uq4;
                                        this.k[i9 - 1].d = c1431Uq5;
                                        if (c1431Uq5.f()) {
                                            c1431Uq5.e &= Integer.MAX_VALUE;
                                            c1431Uq5.d.a(c1431Uq5);
                                        }
                                        c1431Uq4 = c1431Uq5;
                                    } else {
                                        c1431Uq5 = c1431Uq4.c;
                                        c1431Uq5.a(true);
                                        c1431Uq4.a(false);
                                        c1431Uq4.c = c1431Uq5.d;
                                        c1431Uq5.d = c1431Uq4;
                                        this.k[i9 - 1].d = c1431Uq5;
                                        if (c1431Uq5.f()) {
                                            c1431Uq5.e &= Integer.MAX_VALUE;
                                            c1431Uq5.d.a(c1431Uq5);
                                        }
                                        c1431Uq4 = c1431Uq5;
                                    }
                                    i6 = i9 - 1;
                                    c1431Uq4.a(this.k[i6].b());
                                    this.k[i6].a(true);
                                    c1431Uq4.d.a(true);
                                    c1431UqArr3 = this.k;
                                    C1431Uq c1431Uq14 = c1431UqArr3[i6];
                                    c1431Uq14.d = c1431Uq4.c;
                                    c1431Uq4.c = c1431Uq14;
                                    if (i9 < 2) {
                                        this.b = c1431Uq4;
                                    } else {
                                        i7 = i9 - 2;
                                        if (this.j[i7]) {
                                            c1431UqArr3[i7].d = c1431Uq4;
                                        } else {
                                            c1431UqArr3[i7].c = c1431Uq4;
                                        }
                                    }
                                    if (c1431Uq4.d()) {
                                        c1431Uq4.b(false);
                                        this.k[i6].b(c1431Uq4);
                                    }
                                }
                            } else if (this.j[i16]) {
                                c1431Uq4 = this.k[i16].d;
                                if (!c1431Uq4.b()) {
                                    c1431Uq4.a(true);
                                    this.k[i16].a(false);
                                    c1431UqArr4 = this.k;
                                    C1431Uq c1431Uq15 = c1431UqArr4[i16];
                                    c1431Uq15.d = c1431Uq4.c;
                                    c1431Uq4.c = c1431Uq15;
                                    if (i9 < 2) {
                                        this.b = c1431Uq4;
                                    } else {
                                        i8 = i9 - 2;
                                        if (this.j[i8]) {
                                            c1431UqArr4[i8].d = c1431Uq4;
                                        } else {
                                            c1431UqArr4[i8].c = c1431Uq4;
                                        }
                                    }
                                    c1431UqArr4[i9] = c1431Uq15;
                                    this.j[i9] = false;
                                    c1431UqArr4[i16] = c1431Uq4;
                                    int i19 = i9 + 1;
                                    if (i15 == i9) {
                                        i15++;
                                    }
                                    c1431Uq4 = c1431UqArr4[i9].d;
                                    i9 = i19;
                                }
                                if ((c1431Uq4.d() || c1431Uq4.c.b()) && (c1431Uq4.f() || c1431Uq4.d.b())) {
                                    c1431Uq4.a(false);
                                    i9--;
                                } else {
                                    if (c1431Uq4.f() || c1431Uq4.d.b()) {
                                        c1431Uq5 = c1431Uq4.c;
                                        c1431Uq5.a(true);
                                        c1431Uq4.a(false);
                                        c1431Uq4.c = c1431Uq5.d;
                                        c1431Uq5.d = c1431Uq4;
                                        this.k[i9 - 1].d = c1431Uq5;
                                        if (c1431Uq5.f()) {
                                            c1431Uq5.e &= Integer.MAX_VALUE;
                                            c1431Uq5.d.a(c1431Uq5);
                                        }
                                        c1431Uq4 = c1431Uq5;
                                    }
                                    i6 = i9 - 1;
                                    c1431Uq4.a(this.k[i6].b());
                                    this.k[i6].a(true);
                                    c1431Uq4.d.a(true);
                                    c1431UqArr3 = this.k;
                                    C1431Uq c1431Uq16 = c1431UqArr3[i6];
                                    c1431Uq16.d = c1431Uq4.c;
                                    c1431Uq4.c = c1431Uq16;
                                    if (i9 < 2) {
                                        this.b = c1431Uq4;
                                    } else {
                                        i7 = i9 - 2;
                                        if (this.j[i7]) {
                                            c1431UqArr3[i7].d = c1431Uq4;
                                        } else {
                                            c1431UqArr3[i7].c = c1431Uq4;
                                        }
                                    }
                                    if (c1431Uq4.d()) {
                                        c1431Uq4.b(false);
                                        this.k[i6].b(c1431Uq4);
                                    }
                                }
                            } else {
                                c1431Uq2 = this.k[i16].c;
                                if (!c1431Uq2.b()) {
                                    c1431Uq2.a(true);
                                    this.k[i16].a(false);
                                    c1431UqArr2 = this.k;
                                    C1431Uq c1431Uq17 = c1431UqArr2[i16];
                                    c1431Uq17.c = c1431Uq2.d;
                                    c1431Uq2.d = c1431Uq17;
                                    if (i9 < 2) {
                                        this.b = c1431Uq2;
                                    } else {
                                        i5 = i9 - 2;
                                        if (this.j[i5]) {
                                            c1431UqArr2[i5].d = c1431Uq2;
                                        } else {
                                            c1431UqArr2[i5].c = c1431Uq2;
                                        }
                                    }
                                    c1431UqArr2[i9] = c1431Uq17;
                                    this.j[i9] = true;
                                    c1431UqArr2[i16] = c1431Uq2;
                                    int i110 = i9 + 1;
                                    if (i15 == i9) {
                                        i15++;
                                    }
                                    c1431Uq2 = c1431UqArr2[i9].c;
                                    i9 = i110;
                                }
                                if ((c1431Uq2.d() || c1431Uq2.c.b()) && (c1431Uq2.f() || c1431Uq2.d.b())) {
                                    c1431Uq2.a(false);
                                    i9--;
                                } else {
                                    if (c1431Uq2.d() || c1431Uq2.c.b()) {
                                        c1431Uq3 = c1431Uq2.d;
                                        c1431Uq3.a(true);
                                        c1431Uq2.a(false);
                                        c1431Uq2.d = c1431Uq3.c;
                                        c1431Uq3.c = c1431Uq2;
                                        this.k[i9 - 1].c = c1431Uq3;
                                        if (c1431Uq3.d()) {
                                            c1431Uq3.b(false);
                                            c1431Uq3.c.b(c1431Uq3);
                                        }
                                        c1431Uq2 = c1431Uq3;
                                    }
                                    i3 = i9 - 1;
                                    c1431Uq2.a(this.k[i3].b());
                                    this.k[i3].a(true);
                                    c1431Uq2.c.a(true);
                                    c1431UqArr = this.k;
                                    C1431Uq c1431Uq18 = c1431UqArr[i3];
                                    c1431Uq18.c = c1431Uq2.d;
                                    c1431Uq2.d = c1431Uq18;
                                    if (i9 < 2) {
                                        this.b = c1431Uq2;
                                    } else {
                                        i4 = i9 - 2;
                                        if (this.j[i4]) {
                                            c1431UqArr[i4].d = c1431Uq2;
                                        } else {
                                            c1431UqArr[i4].c = c1431Uq2;
                                        }
                                    }
                                    if (c1431Uq2.f()) {
                                        c1431Uq2.e &= Integer.MAX_VALUE;
                                        this.k[i3].a(c1431Uq2);
                                    }
                                }
                            }
                        }
                        i9 = i15;
                        C1431Uq c1431Uq19 = this.b;
                        if (c1431Uq19 != null) {
                            c1431Uq19.a(true);
                        }
                    }
                }
                this.i = true;
                this.c--;
                while (true) {
                    int i20 = i9 - 1;
                    if (i9 == 0) {
                        return c1431Uq6.b;
                    }
                    this.k[i20] = null;
                    i9 = i20;
                }
            } else {
                boolean[] zArr = this.j;
                boolean z = iCompare > 0;
                zArr[i9] = z;
                this.k[i9] = c1431Uq6;
                i9++;
                if (z) {
                    c1431Uq6 = (c1431Uq6.e & Integer.MIN_VALUE) != 0 ? null : c1431Uq6.d;
                    if (c1431Uq6 == null) {
                        while (true) {
                            int i21 = i9 - 1;
                            if (i9 == 0) {
                                return this.a;
                            }
                            this.k[i21] = null;
                            i9 = i21;
                        }
                    }
                } else {
                    c1431Uq6 = (c1431Uq6.e & 1073741824) != 0 ? null : c1431Uq6.c;
                    if (c1431Uq6 == null) {
                        while (true) {
                            int i22 = i9 - 1;
                            if (i9 == 0) {
                                return this.a;
                            }
                            this.k[i22] = null;
                            i9 = i22;
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

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC2703oS values() {
        if (this.h == null) {
            this.h = new C1383Tq(this);
        }
        return this.h;
    }

    @Override // com.android.tools.r8.internal.I, com.android.tools.r8.internal.InterfaceC0992Fq
    public final InterfaceC2842qK c() {
        if (this.f == null) {
            this.f = new C1356Sq(this);
        }
        return this.f;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final int d() {
        if (this.b != null) {
            return this.e.a;
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr c(int i) {
        return new C2028fr(this, 0, true, i, false);
    }

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC3087tq
    public final boolean a(int i) {
        return d(i) != null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final int a() {
        if (this.b != null) {
            return this.d.a;
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr a(int i, int i2) {
        return new C2028fr(this, i, false, i2, false);
    }
}
