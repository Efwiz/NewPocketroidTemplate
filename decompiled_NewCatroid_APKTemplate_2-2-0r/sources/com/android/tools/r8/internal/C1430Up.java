package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Up, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1430Up extends D implements Cloneable {
    protected transient C1020Gp b;
    protected int c;
    protected transient C1020Gp d;
    protected transient C1020Gp e;
    protected transient InterfaceC2842qK f;
    protected transient InterfaceC1357Sr g;
    protected transient ZJ h;
    protected transient boolean i;
    private transient boolean[] j;
    private transient C1020Gp[] k;

    public C1430Up() {
        k();
        this.b = null;
        this.c = 0;
    }

    private void k() {
        this.j = new boolean[64];
        this.k = new C1020Gp[64];
    }

    @Override // com.android.tools.r8.internal.AbstractC3470z, com.android.tools.r8.internal.InterfaceC2730op
    public final Object a(int i, Object obj) {
        int i2;
        C1020Gp c1020Gp;
        C1020Gp c1020Gp2;
        C1020Gp c1020Gp3;
        int i3 = 0;
        this.i = false;
        C1020Gp c1020Gp4 = this.b;
        if (c1020Gp4 == null) {
            this.c++;
            c1020Gp4 = new C1020Gp(i, this.a);
            this.d = c1020Gp4;
            this.e = c1020Gp4;
            this.b = c1020Gp4;
        } else {
            int i4 = 0;
            while (true) {
                int iCompare = Integer.compare(i, c1020Gp4.a);
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
                    this.k[i4] = c1020Gp4;
                    boolean[] zArr = this.j;
                    i2 = i4 + 1;
                    boolean z = iCompare > 0;
                    zArr[i4] = z;
                    if (z) {
                        if (c1020Gp4.f()) {
                            this.c++;
                            c1020Gp = new C1020Gp(i, this.a);
                            C1020Gp c1020Gp5 = c1020Gp4.d;
                            if (c1020Gp5 == null) {
                                this.e = c1020Gp;
                            }
                            c1020Gp.c = c1020Gp4;
                            c1020Gp.d = c1020Gp5;
                            c1020Gp4.e &= Integer.MAX_VALUE;
                            c1020Gp4.d = c1020Gp;
                            break;
                        }
                        c1020Gp4 = c1020Gp4.d;
                        i4 = i2;
                    } else {
                        if (c1020Gp4.d()) {
                            this.c++;
                            c1020Gp = new C1020Gp(i, this.a);
                            C1020Gp c1020Gp6 = c1020Gp4.c;
                            if (c1020Gp6 == null) {
                                this.d = c1020Gp;
                            }
                            c1020Gp.d = c1020Gp4;
                            c1020Gp.c = c1020Gp6;
                            c1020Gp4.e &= -1073741825;
                            c1020Gp4.c = c1020Gp;
                            break;
                        }
                        c1020Gp4 = c1020Gp4.c;
                        i4 = i2;
                    }
                }
                Object obj2 = c1020Gp4.b;
                c1020Gp4.b = obj;
                return obj2;
            }
            c1020Gp4 = c1020Gp;
            this.i = true;
            while (i4 > 0 && !this.k[i4].b()) {
                int i6 = i4 - 1;
                if (this.j[i6]) {
                    C1020Gp c1020Gp7 = this.k[i6];
                    C1020Gp c1020Gp8 = c1020Gp7.c;
                    if (c1020Gp7.d() || c1020Gp8.b()) {
                        if (this.j[i4]) {
                            c1020Gp2 = this.k[i4];
                        } else {
                            C1020Gp[] c1020GpArr = this.k;
                            C1020Gp c1020Gp9 = c1020GpArr[i4];
                            C1020Gp c1020Gp10 = c1020Gp9.c;
                            c1020Gp9.c = c1020Gp10.d;
                            c1020Gp10.d = c1020Gp9;
                            c1020GpArr[i6].d = c1020Gp10;
                            if (c1020Gp10.f()) {
                                c1020Gp10.e &= Integer.MAX_VALUE;
                                c1020Gp9.a(c1020Gp10);
                            }
                            c1020Gp2 = c1020Gp10;
                        }
                        C1020Gp c1020Gp11 = this.k[i6];
                        c1020Gp11.a(false);
                        c1020Gp2.a(true);
                        c1020Gp11.d = c1020Gp2.c;
                        c1020Gp2.c = c1020Gp11;
                        if (i4 < 2) {
                            this.b = c1020Gp2;
                        } else {
                            int i7 = i4 - 2;
                            if (this.j[i7]) {
                                this.k[i7].d = c1020Gp2;
                            } else {
                                this.k[i7].c = c1020Gp2;
                            }
                        }
                        if (!c1020Gp2.d()) {
                            break;
                        }
                        c1020Gp2.b(false);
                        c1020Gp11.b(c1020Gp2);
                        break;
                    }
                    this.k[i4].a(true);
                    c1020Gp8.a(true);
                    this.k[i6].a(false);
                    i4 -= 2;
                } else {
                    C1020Gp c1020Gp12 = this.k[i6];
                    C1020Gp c1020Gp13 = c1020Gp12.d;
                    if (c1020Gp12.f() || c1020Gp13.b()) {
                        if (this.j[i4]) {
                            C1020Gp[] c1020GpArr2 = this.k;
                            C1020Gp c1020Gp14 = c1020GpArr2[i4];
                            C1020Gp c1020Gp15 = c1020Gp14.d;
                            c1020Gp14.d = c1020Gp15.c;
                            c1020Gp15.c = c1020Gp14;
                            c1020GpArr2[i6].c = c1020Gp15;
                            if (c1020Gp15.d()) {
                                c1020Gp15.b(false);
                                c1020Gp14.b(c1020Gp15);
                            }
                            c1020Gp3 = c1020Gp15;
                        } else {
                            c1020Gp3 = this.k[i4];
                        }
                        C1020Gp c1020Gp16 = this.k[i6];
                        c1020Gp16.a(false);
                        c1020Gp3.a(true);
                        c1020Gp16.c = c1020Gp3.d;
                        c1020Gp3.d = c1020Gp16;
                        if (i4 < 2) {
                            this.b = c1020Gp3;
                        } else {
                            int i8 = i4 - 2;
                            if (this.j[i8]) {
                                this.k[i8].d = c1020Gp3;
                            } else {
                                this.k[i8].c = c1020Gp3;
                            }
                        }
                        if (!c1020Gp3.f()) {
                            break;
                        }
                        c1020Gp3.e &= Integer.MAX_VALUE;
                        c1020Gp16.a(c1020Gp3);
                        break;
                    }
                    this.k[i4].a(true);
                    c1020Gp13.a(true);
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
        Object obj3 = c1020Gp4.b;
        c1020Gp4.b = obj;
        return obj3;
    }

    @Override // com.android.tools.r8.internal.D, com.android.tools.r8.internal.InterfaceC2947rp
    public final InterfaceC2842qK b() {
        if (this.f == null) {
            this.f = new C0965Ep(this);
        }
        return this.f;
    }

    final int c(int i, int i2) {
        return Integer.compare(i, i2);
    }

    @Override // com.android.tools.r8.internal.AbstractC3470z, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
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
            C1430Up c1430Up = (C1430Up) super.clone();
            c1430Up.g = null;
            c1430Up.h = null;
            c1430Up.f = null;
            c1430Up.k();
            if (this.c != 0) {
                C1020Gp c1020Gp = new C1020Gp();
                C1020Gp c1020Gp2 = new C1020Gp();
                C1020Gp c1020Gp3 = this.b;
                c1020Gp.e &= -1073741825;
                c1020Gp.c = c1020Gp3;
                c1020Gp2.a((C1020Gp) null);
                C1020Gp c1020Gp4 = c1020Gp2;
                loop0: while (true) {
                    if (c1020Gp.d()) {
                        while (c1020Gp.f()) {
                            c1020Gp = c1020Gp.d;
                            if (c1020Gp == null) {
                                break loop0;
                            }
                            c1020Gp4 = c1020Gp4.d;
                        }
                        c1020Gp = c1020Gp.d;
                        c1020Gp4 = c1020Gp4.d;
                    } else {
                        C1020Gp c1020GpClone = c1020Gp.c.clone();
                        c1020GpClone.a(c1020Gp4.c);
                        c1020GpClone.b(c1020Gp4);
                        c1020Gp4.e &= -1073741825;
                        c1020Gp4.c = c1020GpClone;
                        c1020Gp = c1020Gp.c;
                        c1020Gp4 = c1020GpClone;
                    }
                    if (!c1020Gp.f()) {
                        C1020Gp c1020GpClone2 = c1020Gp.d.clone();
                        c1020GpClone2.b(c1020Gp4.d);
                        c1020GpClone2.a(c1020Gp4);
                        c1020Gp4.e &= Integer.MAX_VALUE;
                        c1020Gp4.d = c1020GpClone2;
                    }
                }
                c1020Gp4.d = null;
                C1020Gp c1020Gp5 = c1020Gp2.c;
                c1430Up.b = c1020Gp5;
                c1430Up.d = c1020Gp5;
                while (true) {
                    C1020Gp c1020Gp6 = c1430Up.d.c;
                    if (c1020Gp6 == null) {
                        break;
                    }
                    c1430Up.d = c1020Gp6;
                }
                c1430Up.e = c1430Up.b;
                while (true) {
                    C1020Gp c1020Gp7 = c1430Up.e.d;
                    if (c1020Gp7 == null) {
                        break;
                    }
                    c1430Up.e = c1020Gp7;
                }
            }
            return c1430Up;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp, java.util.SortedMap
    public final InterfaceC3391xr comparator() {
        return null;
    }

    @Override // java.util.SortedMap
    public final /* bridge */ /* synthetic */ Comparator comparator() {
        return null;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        C1382Tp c1382Tp = new C1382Tp(this, 0);
        int i = this.c;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            Object obj2 = c1382Tp.a().b;
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

    final C1020Gp d(int i) {
        C1020Gp c1020Gp = this.b;
        while (c1020Gp != null) {
            int iCompare = Integer.compare(i, c1020Gp.a);
            if (iCompare == 0) {
                break;
            }
            if (iCompare < 0) {
                c1020Gp = (c1020Gp.e & 1073741824) != 0 ? null : c1020Gp.c;
            } else if ((c1020Gp.e & Integer.MIN_VALUE) == 0) {
                c1020Gp = c1020Gp.d;
            }
        }
        return c1020Gp;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0023  */
    final C1020Gp e(int i) {
        C1020Gp c1020Gp;
        C1020Gp c1020Gp2 = this.b;
        int iCompare = 0;
        C1020Gp c1020Gp3 = c1020Gp2;
        while (c1020Gp2 != null) {
            iCompare = Integer.compare(i, c1020Gp2.a);
            if (iCompare == 0) {
                break;
            }
            if (iCompare < 0) {
                if ((c1020Gp2.e & 1073741824) != 0) {
                    c1020Gp = null;
                } else {
                    c1020Gp = c1020Gp2.c;
                }
            } else if ((c1020Gp2.e & Integer.MIN_VALUE) != 0) {
                c1020Gp = null;
            } else {
                c1020Gp = c1020Gp2.d;
            }
            C1020Gp c1020Gp4 = c1020Gp;
            c1020Gp3 = c1020Gp2;
            c1020Gp2 = c1020Gp4;
        }
        return iCompare == 0 ? c1020Gp2 : c1020Gp3;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2730op
    public final Object get(int i) {
        C1020Gp c1020GpD = d(i);
        return c1020GpD == null ? this.a : c1020GpD.b;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.c == 0;
    }

    @Override // com.android.tools.r8.internal.D, java.util.Map, java.util.SortedMap
    public final InterfaceC1357Sr keySet() {
        if (this.g == null) {
            this.g = new C1110Jp(this, 0);
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
    @Override // com.android.tools.r8.internal.AbstractC3470z
    public final Object remove(int i) {
        int i2;
        C1020Gp c1020Gp;
        C1020Gp c1020Gp2;
        C1020Gp c1020Gp3;
        int i3;
        C1020Gp[] c1020GpArr;
        int i4;
        C1020Gp[] c1020GpArr2;
        int i5;
        C1020Gp c1020Gp4;
        C1020Gp c1020Gp5;
        int i6;
        C1020Gp[] c1020GpArr3;
        int i7;
        C1020Gp[] c1020GpArr4;
        int i8;
        this.i = false;
        C1020Gp c1020Gp6 = this.b;
        if (c1020Gp6 == null) {
            return this.a;
        }
        int i9 = 0;
        while (true) {
            int iCompare = Integer.compare(i, c1020Gp6.a);
            if (iCompare == 0) {
                if (c1020Gp6.c == null) {
                    C1020Gp c1020Gp7 = c1020Gp6.d;
                    if ((c1020Gp6.e & Integer.MIN_VALUE) == 0) {
                        while ((c1020Gp7.e & 1073741824) == 0) {
                            c1020Gp7 = c1020Gp7.c;
                        }
                    }
                    this.d = c1020Gp7;
                }
                if (c1020Gp6.d == null) {
                    this.e = c1020Gp6.e();
                }
                if (!c1020Gp6.f()) {
                    C1020Gp c1020Gp8 = c1020Gp6.d;
                    if (c1020Gp8.d()) {
                        c1020Gp8.c = c1020Gp6.c;
                        c1020Gp8.b(c1020Gp6.d());
                        if (!c1020Gp8.d()) {
                            c1020Gp8.e().d = c1020Gp8;
                        }
                        if (i9 == 0) {
                            this.b = c1020Gp8;
                        } else {
                            int i10 = i9 - 1;
                            if (this.j[i10]) {
                                this.k[i10].d = c1020Gp8;
                            } else {
                                this.k[i10].c = c1020Gp8;
                            }
                        }
                        boolean zB = c1020Gp8.b();
                        c1020Gp8.a(c1020Gp6.b());
                        c1020Gp6.a(zB);
                        this.j[i9] = true;
                        this.k[i9] = c1020Gp8;
                        i9++;
                    } else {
                        int i11 = i9 + 1;
                        while (true) {
                            this.j[i11] = false;
                            i2 = i11 + 1;
                            this.k[i11] = c1020Gp8;
                            c1020Gp = c1020Gp8.c;
                            if (c1020Gp.d()) {
                                break;
                            }
                            c1020Gp8 = c1020Gp;
                            i11 = i2;
                        }
                        this.j[i9] = true;
                        this.k[i9] = c1020Gp;
                        if (c1020Gp.f()) {
                            c1020Gp8.a(c1020Gp);
                        } else {
                            c1020Gp8.c = c1020Gp.d;
                        }
                        c1020Gp.c = c1020Gp6.c;
                        if (!c1020Gp6.d()) {
                            c1020Gp6.e().d = c1020Gp;
                            c1020Gp.b(false);
                        }
                        C1020Gp c1020Gp9 = c1020Gp6.d;
                        c1020Gp.e &= Integer.MAX_VALUE;
                        c1020Gp.d = c1020Gp9;
                        boolean zB2 = c1020Gp.b();
                        c1020Gp.a(c1020Gp6.b());
                        c1020Gp6.a(zB2);
                        if (i9 == 0) {
                            this.b = c1020Gp;
                        } else {
                            int i12 = i9 - 1;
                            if (this.j[i12]) {
                                this.k[i12].d = c1020Gp;
                            } else {
                                this.k[i12].c = c1020Gp;
                            }
                        }
                        i9 = i2;
                    }
                } else if (!c1020Gp6.d()) {
                    c1020Gp6.e().d = c1020Gp6.d;
                    if (i9 == 0) {
                        this.b = c1020Gp6.c;
                    } else {
                        int i13 = i9 - 1;
                        if (this.j[i13]) {
                            this.k[i13].d = c1020Gp6.c;
                        } else {
                            this.k[i13].c = c1020Gp6.c;
                        }
                    }
                } else if (i9 == 0) {
                    this.b = c1020Gp6.c;
                } else {
                    int i14 = i9 - 1;
                    if (this.j[i14]) {
                        this.k[i14].b(c1020Gp6.d);
                    } else {
                        this.k[i14].a(c1020Gp6.c);
                    }
                }
                if (c1020Gp6.b()) {
                    int i15 = i9;
                    while (true) {
                        if (i9 > 0) {
                            int i16 = i9 - 1;
                            if ((this.j[i16] && !this.k[i16].f()) || (!this.j[i16] && !this.k[i16].d())) {
                                C1020Gp c1020Gp10 = this.j[i16] ? this.k[i16].d : this.k[i16].c;
                                if (!c1020Gp10.b()) {
                                    c1020Gp10.a(true);
                                } else if (this.j[i16]) {
                                    c1020Gp2 = this.k[i16].c;
                                    if (!c1020Gp2.b()) {
                                        c1020Gp2.a(true);
                                        this.k[i16].a(false);
                                        c1020GpArr2 = this.k;
                                        C1020Gp c1020Gp11 = c1020GpArr2[i16];
                                        c1020Gp11.c = c1020Gp2.d;
                                        c1020Gp2.d = c1020Gp11;
                                        if (i9 < 2) {
                                            this.b = c1020Gp2;
                                        } else {
                                            i5 = i9 - 2;
                                            if (this.j[i5]) {
                                                c1020GpArr2[i5].d = c1020Gp2;
                                            } else {
                                                c1020GpArr2[i5].c = c1020Gp2;
                                            }
                                        }
                                        c1020GpArr2[i9] = c1020Gp11;
                                        this.j[i9] = true;
                                        c1020GpArr2[i16] = c1020Gp2;
                                        int i17 = i9 + 1;
                                        if (i15 == i9) {
                                            i15++;
                                        }
                                        c1020Gp2 = c1020GpArr2[i9].c;
                                        i9 = i17;
                                    }
                                    if (c1020Gp2.d()) {
                                        c1020Gp2.a(false);
                                        i9--;
                                    } else {
                                        c1020Gp2.a(false);
                                        i9--;
                                    }
                                    if (c1020Gp2.d()) {
                                        c1020Gp3 = c1020Gp2.d;
                                        c1020Gp3.a(true);
                                        c1020Gp2.a(false);
                                        c1020Gp2.d = c1020Gp3.c;
                                        c1020Gp3.c = c1020Gp2;
                                        this.k[i9 - 1].c = c1020Gp3;
                                        if (c1020Gp3.d()) {
                                            c1020Gp3.b(false);
                                            c1020Gp3.c.b(c1020Gp3);
                                        }
                                        c1020Gp2 = c1020Gp3;
                                    } else {
                                        c1020Gp3 = c1020Gp2.d;
                                        c1020Gp3.a(true);
                                        c1020Gp2.a(false);
                                        c1020Gp2.d = c1020Gp3.c;
                                        c1020Gp3.c = c1020Gp2;
                                        this.k[i9 - 1].c = c1020Gp3;
                                        if (c1020Gp3.d()) {
                                            c1020Gp3.b(false);
                                            c1020Gp3.c.b(c1020Gp3);
                                        }
                                        c1020Gp2 = c1020Gp3;
                                    }
                                    i3 = i9 - 1;
                                    c1020Gp2.a(this.k[i3].b());
                                    this.k[i3].a(true);
                                    c1020Gp2.c.a(true);
                                    c1020GpArr = this.k;
                                    C1020Gp c1020Gp12 = c1020GpArr[i3];
                                    c1020Gp12.c = c1020Gp2.d;
                                    c1020Gp2.d = c1020Gp12;
                                    if (i9 < 2) {
                                        this.b = c1020Gp2;
                                    } else {
                                        i4 = i9 - 2;
                                        if (this.j[i4]) {
                                            c1020GpArr[i4].d = c1020Gp2;
                                        } else {
                                            c1020GpArr[i4].c = c1020Gp2;
                                        }
                                    }
                                    if (c1020Gp2.f()) {
                                        c1020Gp2.e &= Integer.MAX_VALUE;
                                        this.k[i3].a(c1020Gp2);
                                    }
                                } else {
                                    c1020Gp4 = this.k[i16].d;
                                    if (!c1020Gp4.b()) {
                                        c1020Gp4.a(true);
                                        this.k[i16].a(false);
                                        c1020GpArr4 = this.k;
                                        C1020Gp c1020Gp13 = c1020GpArr4[i16];
                                        c1020Gp13.d = c1020Gp4.c;
                                        c1020Gp4.c = c1020Gp13;
                                        if (i9 < 2) {
                                            this.b = c1020Gp4;
                                        } else {
                                            i8 = i9 - 2;
                                            if (this.j[i8]) {
                                                c1020GpArr4[i8].d = c1020Gp4;
                                            } else {
                                                c1020GpArr4[i8].c = c1020Gp4;
                                            }
                                        }
                                        c1020GpArr4[i9] = c1020Gp13;
                                        this.j[i9] = false;
                                        c1020GpArr4[i16] = c1020Gp4;
                                        int i18 = i9 + 1;
                                        if (i15 == i9) {
                                            i15++;
                                        }
                                        c1020Gp4 = c1020GpArr4[i9].d;
                                        i9 = i18;
                                    }
                                    if (c1020Gp4.d()) {
                                        c1020Gp4.a(false);
                                        i9--;
                                    } else {
                                        c1020Gp4.a(false);
                                        i9--;
                                    }
                                    if (c1020Gp4.f()) {
                                        c1020Gp5 = c1020Gp4.c;
                                        c1020Gp5.a(true);
                                        c1020Gp4.a(false);
                                        c1020Gp4.c = c1020Gp5.d;
                                        c1020Gp5.d = c1020Gp4;
                                        this.k[i9 - 1].d = c1020Gp5;
                                        if (c1020Gp5.f()) {
                                            c1020Gp5.e &= Integer.MAX_VALUE;
                                            c1020Gp5.d.a(c1020Gp5);
                                        }
                                        c1020Gp4 = c1020Gp5;
                                    } else {
                                        c1020Gp5 = c1020Gp4.c;
                                        c1020Gp5.a(true);
                                        c1020Gp4.a(false);
                                        c1020Gp4.c = c1020Gp5.d;
                                        c1020Gp5.d = c1020Gp4;
                                        this.k[i9 - 1].d = c1020Gp5;
                                        if (c1020Gp5.f()) {
                                            c1020Gp5.e &= Integer.MAX_VALUE;
                                            c1020Gp5.d.a(c1020Gp5);
                                        }
                                        c1020Gp4 = c1020Gp5;
                                    }
                                    i6 = i9 - 1;
                                    c1020Gp4.a(this.k[i6].b());
                                    this.k[i6].a(true);
                                    c1020Gp4.d.a(true);
                                    c1020GpArr3 = this.k;
                                    C1020Gp c1020Gp14 = c1020GpArr3[i6];
                                    c1020Gp14.d = c1020Gp4.c;
                                    c1020Gp4.c = c1020Gp14;
                                    if (i9 < 2) {
                                        this.b = c1020Gp4;
                                    } else {
                                        i7 = i9 - 2;
                                        if (this.j[i7]) {
                                            c1020GpArr3[i7].d = c1020Gp4;
                                        } else {
                                            c1020GpArr3[i7].c = c1020Gp4;
                                        }
                                    }
                                    if (c1020Gp4.d()) {
                                        c1020Gp4.b(false);
                                        this.k[i6].b(c1020Gp4);
                                    }
                                }
                            } else if (this.j[i16]) {
                                c1020Gp4 = this.k[i16].d;
                                if (!c1020Gp4.b()) {
                                    c1020Gp4.a(true);
                                    this.k[i16].a(false);
                                    c1020GpArr4 = this.k;
                                    C1020Gp c1020Gp15 = c1020GpArr4[i16];
                                    c1020Gp15.d = c1020Gp4.c;
                                    c1020Gp4.c = c1020Gp15;
                                    if (i9 < 2) {
                                        this.b = c1020Gp4;
                                    } else {
                                        i8 = i9 - 2;
                                        if (this.j[i8]) {
                                            c1020GpArr4[i8].d = c1020Gp4;
                                        } else {
                                            c1020GpArr4[i8].c = c1020Gp4;
                                        }
                                    }
                                    c1020GpArr4[i9] = c1020Gp15;
                                    this.j[i9] = false;
                                    c1020GpArr4[i16] = c1020Gp4;
                                    int i19 = i9 + 1;
                                    if (i15 == i9) {
                                        i15++;
                                    }
                                    c1020Gp4 = c1020GpArr4[i9].d;
                                    i9 = i19;
                                }
                                if ((c1020Gp4.d() || c1020Gp4.c.b()) && (c1020Gp4.f() || c1020Gp4.d.b())) {
                                    c1020Gp4.a(false);
                                    i9--;
                                } else {
                                    if (c1020Gp4.f() || c1020Gp4.d.b()) {
                                        c1020Gp5 = c1020Gp4.c;
                                        c1020Gp5.a(true);
                                        c1020Gp4.a(false);
                                        c1020Gp4.c = c1020Gp5.d;
                                        c1020Gp5.d = c1020Gp4;
                                        this.k[i9 - 1].d = c1020Gp5;
                                        if (c1020Gp5.f()) {
                                            c1020Gp5.e &= Integer.MAX_VALUE;
                                            c1020Gp5.d.a(c1020Gp5);
                                        }
                                        c1020Gp4 = c1020Gp5;
                                    }
                                    i6 = i9 - 1;
                                    c1020Gp4.a(this.k[i6].b());
                                    this.k[i6].a(true);
                                    c1020Gp4.d.a(true);
                                    c1020GpArr3 = this.k;
                                    C1020Gp c1020Gp16 = c1020GpArr3[i6];
                                    c1020Gp16.d = c1020Gp4.c;
                                    c1020Gp4.c = c1020Gp16;
                                    if (i9 < 2) {
                                        this.b = c1020Gp4;
                                    } else {
                                        i7 = i9 - 2;
                                        if (this.j[i7]) {
                                            c1020GpArr3[i7].d = c1020Gp4;
                                        } else {
                                            c1020GpArr3[i7].c = c1020Gp4;
                                        }
                                    }
                                    if (c1020Gp4.d()) {
                                        c1020Gp4.b(false);
                                        this.k[i6].b(c1020Gp4);
                                    }
                                }
                            } else {
                                c1020Gp2 = this.k[i16].c;
                                if (!c1020Gp2.b()) {
                                    c1020Gp2.a(true);
                                    this.k[i16].a(false);
                                    c1020GpArr2 = this.k;
                                    C1020Gp c1020Gp17 = c1020GpArr2[i16];
                                    c1020Gp17.c = c1020Gp2.d;
                                    c1020Gp2.d = c1020Gp17;
                                    if (i9 < 2) {
                                        this.b = c1020Gp2;
                                    } else {
                                        i5 = i9 - 2;
                                        if (this.j[i5]) {
                                            c1020GpArr2[i5].d = c1020Gp2;
                                        } else {
                                            c1020GpArr2[i5].c = c1020Gp2;
                                        }
                                    }
                                    c1020GpArr2[i9] = c1020Gp17;
                                    this.j[i9] = true;
                                    c1020GpArr2[i16] = c1020Gp2;
                                    int i110 = i9 + 1;
                                    if (i15 == i9) {
                                        i15++;
                                    }
                                    c1020Gp2 = c1020GpArr2[i9].c;
                                    i9 = i110;
                                }
                                if ((c1020Gp2.d() || c1020Gp2.c.b()) && (c1020Gp2.f() || c1020Gp2.d.b())) {
                                    c1020Gp2.a(false);
                                    i9--;
                                } else {
                                    if (c1020Gp2.d() || c1020Gp2.c.b()) {
                                        c1020Gp3 = c1020Gp2.d;
                                        c1020Gp3.a(true);
                                        c1020Gp2.a(false);
                                        c1020Gp2.d = c1020Gp3.c;
                                        c1020Gp3.c = c1020Gp2;
                                        this.k[i9 - 1].c = c1020Gp3;
                                        if (c1020Gp3.d()) {
                                            c1020Gp3.b(false);
                                            c1020Gp3.c.b(c1020Gp3);
                                        }
                                        c1020Gp2 = c1020Gp3;
                                    }
                                    i3 = i9 - 1;
                                    c1020Gp2.a(this.k[i3].b());
                                    this.k[i3].a(true);
                                    c1020Gp2.c.a(true);
                                    c1020GpArr = this.k;
                                    C1020Gp c1020Gp18 = c1020GpArr[i3];
                                    c1020Gp18.c = c1020Gp2.d;
                                    c1020Gp2.d = c1020Gp18;
                                    if (i9 < 2) {
                                        this.b = c1020Gp2;
                                    } else {
                                        i4 = i9 - 2;
                                        if (this.j[i4]) {
                                            c1020GpArr[i4].d = c1020Gp2;
                                        } else {
                                            c1020GpArr[i4].c = c1020Gp2;
                                        }
                                    }
                                    if (c1020Gp2.f()) {
                                        c1020Gp2.e &= Integer.MAX_VALUE;
                                        this.k[i3].a(c1020Gp2);
                                    }
                                }
                            }
                        }
                        i9 = i15;
                        C1020Gp c1020Gp19 = this.b;
                        if (c1020Gp19 != null) {
                            c1020Gp19.a(true);
                        }
                    }
                }
                this.i = true;
                this.c--;
                while (true) {
                    int i20 = i9 - 1;
                    if (i9 == 0) {
                        return c1020Gp6.b;
                    }
                    this.k[i20] = null;
                    i9 = i20;
                }
            } else {
                boolean[] zArr = this.j;
                boolean z = iCompare > 0;
                zArr[i9] = z;
                this.k[i9] = c1020Gp6;
                i9++;
                if (z) {
                    c1020Gp6 = (c1020Gp6.e & Integer.MIN_VALUE) != 0 ? null : c1020Gp6.d;
                    if (c1020Gp6 == null) {
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
                    c1020Gp6 = (c1020Gp6.e & 1073741824) != 0 ? null : c1020Gp6.c;
                    if (c1020Gp6 == null) {
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

    @Override // com.android.tools.r8.internal.B, java.util.Map
    public final ZJ values() {
        if (this.h == null) {
            this.h = new C0991Fp(this);
        }
        return this.h;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp c(int i) {
        return new C1329Rp(this, 0, true, i, false);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final int d() {
        if (this.b != null) {
            return this.e.a;
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp b(int i) {
        return new C1329Rp(this, i, false, 0, true);
    }

    @Override // com.android.tools.r8.internal.B, com.android.tools.r8.internal.InterfaceC2730op
    public final boolean a(int i) {
        return d(i) != null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final int a() {
        if (this.b != null) {
            return this.d.a;
        }
        throw new NoSuchElementException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp a(int i, int i2) {
        return new C1329Rp(this, i, false, i2, false);
    }
}
