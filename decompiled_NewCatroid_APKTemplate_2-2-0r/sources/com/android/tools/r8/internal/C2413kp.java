package com.android.tools.r8.internal;

import androidx.core.view.InputDeviceCompat;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2413kp extends D implements Cloneable {
    protected transient C1481Wo b;
    protected int c;
    protected transient C1481Wo d;
    protected transient C1481Wo e;
    protected transient InterfaceC2842qK f;
    protected transient InterfaceC1357Sr g;
    protected transient ZJ h;
    protected transient boolean i;
    protected Comparator j;
    protected transient InterfaceC3391xr k;
    private transient boolean[] l;

    public C2413kp() {
        k();
        this.b = null;
        this.c = 0;
    }

    private void k() {
        this.l = new boolean[48];
    }

    private void m() {
        Comparator comparator = this.j;
        if (comparator == null || (comparator instanceof InterfaceC3391xr)) {
            this.k = (InterfaceC3391xr) comparator;
        } else {
            this.k = new C1354So(this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:85:0x019d  */
    /* JADX WARN: Code duplicated, block: B:86:0x01a0  */
    /* JADX WARN: Code duplicated, block: B:88:0x01a4  */
    /* JADX WARN: Code duplicated, block: B:89:0x01a7  */
    @Override // com.android.tools.r8.internal.AbstractC3470z, com.android.tools.r8.internal.InterfaceC2730op
    public final Object a(int i, Object obj) {
        C1481Wo c1481Wo;
        C1481Wo c1481Wo2;
        C1481Wo c1481Wo3;
        C1481Wo c1481Wo4;
        C1481Wo c1481Wo5;
        this.i = false;
        C1481Wo c1481Wo6 = this.b;
        if (c1481Wo6 == null) {
            this.c++;
            c1481Wo = new C1481Wo(i, this.a);
            this.d = c1481Wo;
            this.e = c1481Wo;
            this.b = c1481Wo;
            this.i = true;
        } else {
            int i2 = 0;
            C1481Wo c1481Wo7 = null;
            C1481Wo c1481Wo8 = null;
            C1481Wo c1481Wo9 = c1481Wo6;
            while (true) {
                int iC = c(i, c1481Wo6.a);
                if (iC == 0) {
                    c1481Wo = c1481Wo6;
                } else {
                    if (((byte) c1481Wo6.e) != 0) {
                        i2 = 0;
                        c1481Wo9 = c1481Wo6;
                        c1481Wo7 = c1481Wo8;
                    }
                    boolean[] zArr = this.l;
                    int i3 = i2 + 1;
                    boolean z = iC > 0;
                    zArr[i2] = z;
                    if (z) {
                        if (c1481Wo6.e()) {
                            this.c++;
                            c1481Wo2 = new C1481Wo(i, this.a);
                            this.i = true;
                            C1481Wo c1481Wo10 = c1481Wo6.d;
                            if (c1481Wo10 == null) {
                                this.e = c1481Wo2;
                            }
                            c1481Wo2.c = c1481Wo6;
                            c1481Wo2.d = c1481Wo10;
                            c1481Wo6.e &= Integer.MAX_VALUE;
                            c1481Wo6.d = c1481Wo2;
                            break;
                        }
                        c1481Wo3 = c1481Wo6.d;
                        i2 = i3;
                        C1481Wo c1481Wo11 = c1481Wo3;
                        c1481Wo8 = c1481Wo6;
                        c1481Wo6 = c1481Wo11;
                    } else {
                        if (c1481Wo6.c()) {
                            this.c++;
                            c1481Wo2 = new C1481Wo(i, this.a);
                            this.i = true;
                            C1481Wo c1481Wo12 = c1481Wo6.c;
                            if (c1481Wo12 == null) {
                                this.d = c1481Wo2;
                            }
                            c1481Wo2.d = c1481Wo6;
                            c1481Wo2.c = c1481Wo12;
                            c1481Wo6.e &= -1073741825;
                            c1481Wo6.c = c1481Wo2;
                            break;
                        }
                        c1481Wo3 = c1481Wo6.c;
                        i2 = i3;
                        C1481Wo c1481Wo13 = c1481Wo3;
                        c1481Wo8 = c1481Wo6;
                        c1481Wo6 = c1481Wo13;
                    }
                }
            }
            int i4 = 0;
            C1481Wo c1481Wo14 = c1481Wo9;
            while (c1481Wo14 != c1481Wo2) {
                boolean z2 = this.l[i4];
                if (z2) {
                    int i5 = c1481Wo14.e;
                    c1481Wo14.e = ((((byte) i5) + 1) & 255) | (i5 & InputDeviceCompat.SOURCE_ANY);
                } else {
                    int i6 = c1481Wo14.e;
                    c1481Wo14.e = ((((byte) i6) - 1) & 255) | (i6 & InputDeviceCompat.SOURCE_ANY);
                }
                i4++;
                c1481Wo14 = z2 ? c1481Wo14.d : c1481Wo14.c;
            }
            byte b = (byte) c1481Wo9.e;
            if (b == -2) {
                c1481Wo4 = c1481Wo9.c;
                if (((byte) c1481Wo4.e) == -1) {
                    if (c1481Wo4.e()) {
                        c1481Wo4.b(false);
                        c1481Wo9.a(c1481Wo4);
                    } else {
                        c1481Wo9.c = c1481Wo4.d;
                    }
                    c1481Wo4.d = c1481Wo9;
                    c1481Wo4.a(0);
                    c1481Wo9.a(0);
                } else {
                    c1481Wo5 = c1481Wo4.d;
                    c1481Wo4.d = c1481Wo5.c;
                    c1481Wo5.c = c1481Wo4;
                    c1481Wo9.c = c1481Wo5.d;
                    c1481Wo5.d = c1481Wo9;
                    byte b2 = (byte) c1481Wo5.e;
                    if (b2 == -1) {
                        c1481Wo4.a(0);
                        c1481Wo9.a(1);
                    } else if (b2 == 0) {
                        c1481Wo4.a(0);
                        c1481Wo9.a(0);
                    } else {
                        c1481Wo4.a(-1);
                        c1481Wo9.a(0);
                    }
                    c1481Wo5.a(0);
                    if (c1481Wo5.c()) {
                        c1481Wo4.b(c1481Wo5);
                        c1481Wo5.a(false);
                    }
                    if (c1481Wo5.e()) {
                        c1481Wo9.a(c1481Wo5);
                        c1481Wo5.b(false);
                    }
                    c1481Wo4 = c1481Wo5;
                }
                if (c1481Wo7 == null) {
                    this.b = c1481Wo4;
                } else if (c1481Wo7.c == c1481Wo9) {
                    c1481Wo7.c = c1481Wo4;
                } else {
                    c1481Wo7.d = c1481Wo4;
                }
            } else if (b == 2) {
                c1481Wo4 = c1481Wo9.d;
                if (((byte) c1481Wo4.e) == 1) {
                    if (c1481Wo4.c()) {
                        c1481Wo4.a(false);
                        c1481Wo9.b(c1481Wo4);
                    } else {
                        c1481Wo9.d = c1481Wo4.c;
                    }
                    c1481Wo4.c = c1481Wo9;
                    c1481Wo4.a(0);
                    c1481Wo9.a(0);
                } else {
                    c1481Wo5 = c1481Wo4.c;
                    c1481Wo4.c = c1481Wo5.d;
                    c1481Wo5.d = c1481Wo4;
                    c1481Wo9.d = c1481Wo5.c;
                    c1481Wo5.c = c1481Wo9;
                    byte b3 = (byte) c1481Wo5.e;
                    if (b3 == 1) {
                        c1481Wo4.a(0);
                        c1481Wo9.a(-1);
                    } else if (b3 == 0) {
                        c1481Wo4.a(0);
                        c1481Wo9.a(0);
                    } else {
                        c1481Wo4.a(1);
                        c1481Wo9.a(0);
                    }
                    c1481Wo5.a(0);
                    if (c1481Wo5.c()) {
                        c1481Wo9.b(c1481Wo5);
                        c1481Wo5.a(false);
                    }
                    if (c1481Wo5.e()) {
                        c1481Wo4.a(c1481Wo5);
                        c1481Wo5.b(false);
                    }
                    c1481Wo4 = c1481Wo5;
                }
                if (c1481Wo7 == null) {
                    this.b = c1481Wo4;
                } else if (c1481Wo7.c == c1481Wo9) {
                    c1481Wo7.c = c1481Wo4;
                } else {
                    c1481Wo7.d = c1481Wo4;
                }
            }
            c1481Wo = c1481Wo2;
        }
        Object obj2 = c1481Wo.b;
        c1481Wo.b = obj;
        return obj2;
    }

    @Override // com.android.tools.r8.internal.D, com.android.tools.r8.internal.InterfaceC2947rp
    public final InterfaceC2842qK b() {
        if (this.f == null) {
            this.f = new C1429Uo(this);
        }
        return this.f;
    }

    final int c(int i, int i2) {
        InterfaceC3391xr interfaceC3391xr = this.k;
        return interfaceC3391xr == null ? Integer.compare(i, i2) : ((C1354So) interfaceC3391xr).a.j.compare(Integer.valueOf(i), Integer.valueOf(i2));
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

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp, java.util.SortedMap
    public final InterfaceC3391xr comparator() {
        return this.k;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        C2333jp c2333jp = new C2333jp(this, 0);
        int i = this.c;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            Object obj2 = c2333jp.a().b;
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

    final C1481Wo d(int i) {
        C1481Wo c1481Wo = this.b;
        while (c1481Wo != null) {
            int iC = c(i, c1481Wo.a);
            if (iC == 0) {
                break;
            }
            if (iC < 0) {
                c1481Wo = (c1481Wo.e & 1073741824) != 0 ? null : c1481Wo.c;
            } else if ((c1481Wo.e & Integer.MIN_VALUE) == 0) {
                c1481Wo = c1481Wo.d;
            }
        }
        return c1481Wo;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2730op
    public final Object get(int i) {
        C1481Wo c1481WoD = d(i);
        return c1481WoD == null ? this.a : c1481WoD.b;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.c == 0;
    }

    @Override // com.android.tools.r8.internal.D, java.util.Map, java.util.SortedMap
    public final InterfaceC1357Sr keySet() {
        if (this.g == null) {
            this.g = new C1563Zo(this, 0);
        }
        return this.g;
    }

    /* JADX INFO: renamed from: l, reason: merged with bridge method [inline-methods] */
    public final C2413kp clone() {
        try {
            C2413kp c2413kp = (C2413kp) super.clone();
            c2413kp.g = null;
            c2413kp.h = null;
            c2413kp.f = null;
            c2413kp.k();
            if (this.c == 0) {
                return c2413kp;
            }
            C1481Wo c1481Wo = new C1481Wo();
            C1481Wo c1481Wo2 = new C1481Wo();
            C1481Wo c1481Wo3 = this.b;
            c1481Wo.e &= -1073741825;
            c1481Wo.c = c1481Wo3;
            c1481Wo2.a((C1481Wo) null);
            C1481Wo c1481Wo4 = c1481Wo2;
            loop0: while (true) {
                if (c1481Wo.c()) {
                    while (c1481Wo.e()) {
                        c1481Wo = c1481Wo.d;
                        if (c1481Wo == null) {
                            break loop0;
                        }
                        c1481Wo4 = c1481Wo4.d;
                    }
                    c1481Wo = c1481Wo.d;
                    c1481Wo4 = c1481Wo4.d;
                } else {
                    C1481Wo c1481WoClone = c1481Wo.c.clone();
                    c1481WoClone.a(c1481Wo4.c);
                    c1481WoClone.b(c1481Wo4);
                    c1481Wo4.e &= -1073741825;
                    c1481Wo4.c = c1481WoClone;
                    c1481Wo = c1481Wo.c;
                    c1481Wo4 = c1481WoClone;
                }
                if (!c1481Wo.e()) {
                    C1481Wo c1481WoClone2 = c1481Wo.d.clone();
                    c1481WoClone2.b(c1481Wo4.d);
                    c1481WoClone2.a(c1481Wo4);
                    c1481Wo4.e &= Integer.MAX_VALUE;
                    c1481Wo4.d = c1481WoClone2;
                }
            }
            c1481Wo4.d = null;
            C1481Wo c1481Wo5 = c1481Wo2.c;
            c2413kp.b = c1481Wo5;
            c2413kp.d = c1481Wo5;
            while (true) {
                C1481Wo c1481Wo6 = c2413kp.d.c;
                if (c1481Wo6 == null) {
                    break;
                }
                c2413kp.d = c1481Wo6;
            }
            c2413kp.e = c2413kp.b;
            while (true) {
                C1481Wo c1481Wo7 = c2413kp.e.d;
                if (c1481Wo7 == null) {
                    return c2413kp;
                }
                c2413kp.e = c1481Wo7;
            }
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3470z
    public final Object remove(int i) {
        C1481Wo c1481Wo;
        C1481Wo c1481Wo2;
        C1481Wo c1481Wo3;
        this.i = false;
        C1481Wo c1481Wo4 = this.b;
        if (c1481Wo4 == null) {
            return this.a;
        }
        boolean z = false;
        C1481Wo c1481Wo5 = null;
        while (true) {
            int iC = c(i, c1481Wo4.a);
            if (iC == 0) {
                if (c1481Wo4.c == null) {
                    C1481Wo c1481Wo6 = c1481Wo4.d;
                    if ((c1481Wo4.e & Integer.MIN_VALUE) == 0) {
                        while ((c1481Wo6.e & 1073741824) == 0) {
                            c1481Wo6 = c1481Wo6.c;
                        }
                    }
                    this.d = c1481Wo6;
                }
                if (c1481Wo4.d == null) {
                    this.e = c1481Wo4.d();
                }
                if (!c1481Wo4.e()) {
                    C1481Wo c1481Wo7 = c1481Wo4.d;
                    if (c1481Wo7.c()) {
                        c1481Wo7.c = c1481Wo4.c;
                        c1481Wo7.a(c1481Wo4.c());
                        if (!c1481Wo7.c()) {
                            c1481Wo7.d().d = c1481Wo7;
                        }
                        if (c1481Wo5 == null) {
                            this.b = c1481Wo7;
                        } else if (z) {
                            c1481Wo5.d = c1481Wo7;
                        } else {
                            c1481Wo5.c = c1481Wo7;
                        }
                        c1481Wo7.a((byte) c1481Wo4.e);
                        c1481Wo5 = c1481Wo7;
                        z = true;
                    } else {
                        while (true) {
                            c1481Wo = c1481Wo7.c;
                            if (c1481Wo.c()) {
                                break;
                            }
                            c1481Wo7 = c1481Wo;
                        }
                        if (c1481Wo.e()) {
                            c1481Wo7.a(c1481Wo);
                        } else {
                            c1481Wo7.c = c1481Wo.d;
                        }
                        c1481Wo.c = c1481Wo4.c;
                        if (!c1481Wo4.c()) {
                            c1481Wo4.d().d = c1481Wo;
                            c1481Wo.a(false);
                        }
                        c1481Wo.d = c1481Wo4.d;
                        c1481Wo.b(false);
                        if (c1481Wo5 == null) {
                            this.b = c1481Wo;
                        } else if (z) {
                            c1481Wo5.d = c1481Wo;
                        } else {
                            c1481Wo5.c = c1481Wo;
                        }
                        c1481Wo.a((byte) c1481Wo4.e);
                        c1481Wo5 = c1481Wo7;
                        z = false;
                    }
                } else if (!c1481Wo4.c()) {
                    c1481Wo4.d().d = c1481Wo4.d;
                    if (c1481Wo5 == null) {
                        this.b = c1481Wo4.c;
                    } else if (z) {
                        c1481Wo5.d = c1481Wo4.c;
                    } else {
                        c1481Wo5.c = c1481Wo4.c;
                    }
                } else if (c1481Wo5 == null) {
                    this.b = z ? c1481Wo4.d : c1481Wo4.c;
                } else if (z) {
                    c1481Wo5.b(c1481Wo4.d);
                } else {
                    c1481Wo5.a(c1481Wo4.c);
                }
                while (c1481Wo5 != null) {
                    if (c1481Wo5 == this.b) {
                        c1481Wo2 = null;
                    } else {
                        C1481Wo c1481Wo8 = c1481Wo5;
                        C1481Wo c1481Wo9 = c1481Wo8;
                        while (true) {
                            if (c1481Wo8.e()) {
                                c1481Wo2 = c1481Wo8.d;
                                if (c1481Wo2 != null && c1481Wo2.c == c1481Wo5) {
                                    break;
                                }
                                while (!c1481Wo9.c()) {
                                    c1481Wo9 = c1481Wo9.c;
                                }
                                c1481Wo2 = c1481Wo9.c;
                                break;
                            }
                            if (c1481Wo9.c()) {
                                C1481Wo c1481Wo10 = c1481Wo9.c;
                                if (c1481Wo10 == null || c1481Wo10.d != c1481Wo5) {
                                    while (!c1481Wo8.e()) {
                                        c1481Wo8 = c1481Wo8.d;
                                    }
                                    c1481Wo10 = c1481Wo8.d;
                                }
                                c1481Wo2 = c1481Wo10;
                                break;
                            }
                            c1481Wo9 = c1481Wo9.c;
                            c1481Wo8 = c1481Wo8.d;
                        }
                    }
                    if (!z) {
                        z = (c1481Wo2 == null || c1481Wo2.c == c1481Wo5) ? false : true;
                        int i2 = c1481Wo5.e;
                        int i3 = ((((byte) i2) + 1) & 255) | (i2 & InputDeviceCompat.SOURCE_ANY);
                        c1481Wo5.e = i3;
                        byte b = (byte) i3;
                        if (b == 1) {
                            break;
                        }
                        if (b == 2) {
                            C1481Wo c1481Wo11 = c1481Wo5.d;
                            byte b2 = (byte) c1481Wo11.e;
                            if (b2 == -1) {
                                C1481Wo c1481Wo12 = c1481Wo11.c;
                                c1481Wo11.c = c1481Wo12.d;
                                c1481Wo12.d = c1481Wo11;
                                c1481Wo5.d = c1481Wo12.c;
                                c1481Wo12.c = c1481Wo5;
                                byte b3 = (byte) c1481Wo12.e;
                                if (b3 == 1) {
                                    c1481Wo11.a(0);
                                    c1481Wo5.a(-1);
                                } else if (b3 == 0) {
                                    c1481Wo11.a(0);
                                    c1481Wo5.a(0);
                                } else {
                                    c1481Wo11.a(1);
                                    c1481Wo5.a(0);
                                }
                                c1481Wo12.a(0);
                                if (c1481Wo12.c()) {
                                    c1481Wo5.b(c1481Wo12);
                                    c1481Wo12.a(false);
                                }
                                if (c1481Wo12.e()) {
                                    c1481Wo11.a(c1481Wo12);
                                    c1481Wo12.b(false);
                                }
                                if (c1481Wo2 == null) {
                                    this.b = c1481Wo12;
                                } else if (z) {
                                    c1481Wo2.d = c1481Wo12;
                                } else {
                                    c1481Wo2.c = c1481Wo12;
                                }
                            } else {
                                if (c1481Wo2 == null) {
                                    this.b = c1481Wo11;
                                } else if (z) {
                                    c1481Wo2.d = c1481Wo11;
                                } else {
                                    c1481Wo2.c = c1481Wo11;
                                }
                                if (b2 == 0) {
                                    c1481Wo5.d = c1481Wo11.c;
                                    c1481Wo11.c = c1481Wo5;
                                    c1481Wo11.a(-1);
                                    c1481Wo5.a(1);
                                    break;
                                }
                                if (c1481Wo11.c()) {
                                    c1481Wo5.b(true);
                                    c1481Wo11.a(false);
                                } else {
                                    c1481Wo5.d = c1481Wo11.c;
                                }
                                c1481Wo11.c = c1481Wo5;
                                c1481Wo5.a(0);
                                c1481Wo11.a(0);
                            }
                        } else {
                            continue;
                        }
                        c1481Wo5 = c1481Wo2;
                    } else {
                        z = (c1481Wo2 == null || c1481Wo2.c == c1481Wo5) ? false : true;
                        int i4 = c1481Wo5.e;
                        int i5 = ((((byte) i4) - 1) & 255) | (i4 & InputDeviceCompat.SOURCE_ANY);
                        c1481Wo5.e = i5;
                        byte b4 = (byte) i5;
                        if (b4 == -1) {
                            break;
                        }
                        if (b4 == -2) {
                            C1481Wo c1481Wo13 = c1481Wo5.c;
                            byte b5 = (byte) c1481Wo13.e;
                            if (b5 == 1) {
                                C1481Wo c1481Wo14 = c1481Wo13.d;
                                c1481Wo13.d = c1481Wo14.c;
                                c1481Wo14.c = c1481Wo13;
                                c1481Wo5.c = c1481Wo14.d;
                                c1481Wo14.d = c1481Wo5;
                                byte b6 = (byte) c1481Wo14.e;
                                if (b6 == -1) {
                                    c1481Wo13.a(0);
                                    c1481Wo5.a(1);
                                } else if (b6 == 0) {
                                    c1481Wo13.a(0);
                                    c1481Wo5.a(0);
                                } else {
                                    c1481Wo13.a(-1);
                                    c1481Wo5.a(0);
                                }
                                c1481Wo14.a(0);
                                if (c1481Wo14.c()) {
                                    c1481Wo13.b(c1481Wo14);
                                    c1481Wo14.a(false);
                                }
                                if (c1481Wo14.e()) {
                                    c1481Wo5.a(c1481Wo14);
                                    c1481Wo14.b(false);
                                }
                                if (c1481Wo2 == null) {
                                    this.b = c1481Wo14;
                                } else if (z) {
                                    c1481Wo2.d = c1481Wo14;
                                } else {
                                    c1481Wo2.c = c1481Wo14;
                                }
                            } else {
                                if (c1481Wo2 == null) {
                                    this.b = c1481Wo13;
                                } else if (z) {
                                    c1481Wo2.d = c1481Wo13;
                                } else {
                                    c1481Wo2.c = c1481Wo13;
                                }
                                if (b5 == 0) {
                                    c1481Wo5.c = c1481Wo13.d;
                                    c1481Wo13.d = c1481Wo5;
                                    c1481Wo13.a(1);
                                    c1481Wo5.a(-1);
                                    break;
                                }
                                if (c1481Wo13.e()) {
                                    c1481Wo5.a(true);
                                    c1481Wo13.b(false);
                                } else {
                                    c1481Wo5.c = c1481Wo13.d;
                                }
                                c1481Wo13.d = c1481Wo5;
                                c1481Wo5.a(0);
                                c1481Wo13.a(0);
                            }
                        } else {
                            continue;
                        }
                        c1481Wo5 = c1481Wo2;
                    }
                }
                this.i = true;
                this.c--;
                return c1481Wo4.b;
            }
            z = iC > 0;
            if (z) {
                c1481Wo3 = (c1481Wo4.e & Integer.MIN_VALUE) != 0 ? null : c1481Wo4.d;
                if (c1481Wo3 == null) {
                    return this.a;
                }
            } else {
                c1481Wo3 = (c1481Wo4.e & 1073741824) != 0 ? null : c1481Wo4.c;
                if (c1481Wo3 == null) {
                    return this.a;
                }
            }
            C1481Wo c1481Wo15 = c1481Wo3;
            c1481Wo5 = c1481Wo4;
            c1481Wo4 = c1481Wo15;
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.B, java.util.Map
    public final ZJ values() {
        if (this.h == null) {
            this.h = new C1455Vo(this);
        }
        return this.h;
    }

    @Override // java.util.SortedMap
    public final Comparator comparator() {
        return this.k;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp c(int i) {
        return new C2181hp(this, 0, true, i, false);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final int d() {
        if (this.b != null) {
            return this.e.a;
        }
        throw new NoSuchElementException();
    }

    public C2413kp(InterfaceC3391xr interfaceC3391xr) {
        this();
        this.j = interfaceC3391xr;
        m();
    }

    public C2413kp(InterfaceC1456Vp interfaceC1456Vp) {
        this(interfaceC1456Vp.comparator());
        putAll(interfaceC1456Vp);
    }

    public C2413kp(int[] iArr, Object[] objArr, int i) {
        this((InterfaceC3391xr) null);
        if (iArr.length == objArr.length) {
            for (int i2 = 0; i2 < iArr.length; i2++) {
                a(iArr[i2], objArr[i2]);
            }
            return;
        }
        throw new IllegalArgumentException("The key array and the value array have different lengths (" + iArr.length + " and " + objArr.length + Constant.AFTER_QUTO);
    }

    public C2413kp(int[] iArr, Object[] objArr) {
        this(iArr, objArr, 0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1456Vp
    public final InterfaceC1456Vp b(int i) {
        return new C2181hp(this, i, false, 0, true);
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
        return new C2181hp(this, i, false, i2, false);
    }
}
