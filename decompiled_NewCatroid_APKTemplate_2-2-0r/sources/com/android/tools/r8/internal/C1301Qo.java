package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qo, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1301Qo extends AbstractC3324x implements Cloneable {
    protected transient int[] b;
    protected transient int[] c;
    protected transient int d;
    protected transient boolean e;
    protected transient int f;
    protected transient int g;
    protected int h;
    protected transient InterfaceC1019Go i;
    protected transient InterfaceC1277Pr j;
    protected transient InterfaceC3088tr k;

    public C1301Qo(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        int iA = AbstractC3307wk.a(i, 0.75f);
        this.f = iA;
        this.d = iA - 1;
        this.g = AbstractC3307wk.b(iA, 0.75f);
        int i2 = this.f + 1;
        this.b = new int[i2];
        this.c = new int[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int g(int i) {
        int i2;
        int i3;
        int i4 = this.c[i];
        this.h--;
        int[] iArr = this.b;
        loop0: while (true) {
            int i5 = (i + 1) & this.d;
            while (true) {
                i2 = iArr[i5];
                if (i2 != 0) {
                    int iA = AbstractC3307wk.a(i2);
                    int i6 = this.d;
                    int i7 = iA & i6;
                    if (i > i5) {
                        if (i >= i7 && i7 > i5) {
                            break;
                        }
                        i5 = (i5 + 1) & i6;
                    } else {
                        if (i >= i7 || i7 > i5) {
                            break;
                        }
                        i5 = (i5 + 1) & i6;
                    }
                } else {
                    break loop0;
                }
            }
            iArr[i] = i2;
            int[] iArr2 = this.c;
            iArr2[i] = iArr2[i5];
            i = i5;
        }
        iArr[i] = 0;
        if (this.h < this.g / 4 && (i3 = this.f) > 16) {
            f(i3 / 2);
        }
        return i4;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x003e  */
    @Override // com.android.tools.r8.internal.AbstractC3324x, com.android.tools.r8.internal.InterfaceC3162uo
    public final int b(int i, int i2) {
        int i3;
        int i4;
        int i5;
        if (i != 0) {
            int[] iArr = this.b;
            int iA = AbstractC3307wk.a(i) & this.d;
            int i6 = iArr[iA];
            if (i6 != 0) {
                if (i6 != i) {
                    while (true) {
                        iA = (iA + 1) & this.d;
                        int i7 = iArr[iA];
                        if (i7 != 0) {
                            if (i7 == i) {
                            }
                        }
                    }
                }
                i4 = iA;
            }
            i3 = iA;
            this.b[i3] = i;
            this.c[i3] = i2;
            i5 = this.h;
            this.h = i5 + 1;
            if (i5 >= this.g) {
                f(AbstractC3307wk.a(i5 + 2, 0.75f));
            }
            i4 = -1;
        } else if (this.e) {
            i4 = this.f;
        } else {
            this.e = true;
            i3 = this.f;
            this.b[i3] = i;
            this.c[i3] = i2;
            i5 = this.h;
            this.h = i5 + 1;
            if (i5 >= this.g) {
                f(AbstractC3307wk.a(i5 + 2, 0.75f));
            }
            i4 = -1;
        }
        if (i4 < 0) {
            return this.a;
        }
        int[] iArr2 = this.c;
        int i8 = iArr2[i4];
        iArr2[i4] = i2;
        return i8;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        if (this.h == 0) {
            return;
        }
        this.h = 0;
        this.e = false;
        Arrays.fill(this.b, 0);
    }

    public final Object clone() {
        try {
            C1301Qo c1301Qo = (C1301Qo) super.clone();
            c1301Qo.j = null;
            c1301Qo.k = null;
            c1301Qo.i = null;
            c1301Qo.e = this.e;
            c1301Qo.b = (int[]) this.b.clone();
            c1301Qo.c = (int[]) this.c.clone();
            return c1301Qo;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3324x
    public final boolean d(int i) {
        int[] iArr = this.c;
        int[] iArr2 = this.b;
        if (this.e && iArr[this.f] == i) {
            return true;
        }
        int i2 = this.f;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return false;
            }
            if (iArr2[i3] != 0 && iArr[i3] == i) {
                return true;
            }
            i2 = i3;
        }
    }

    protected final void f(int i) {
        int i2;
        int[] iArr = this.b;
        int[] iArr2 = this.c;
        int i3 = i - 1;
        int i4 = i + 1;
        int[] iArr3 = new int[i4];
        int[] iArr4 = new int[i4];
        int i5 = this.f;
        int i6 = this.e ? this.h - 1 : this.h;
        while (true) {
            int i7 = i6 - 1;
            if (i6 == 0) {
                iArr4[i] = iArr2[this.f];
                this.f = i;
                this.d = i3;
                this.g = AbstractC3307wk.b(i, 0.75f);
                this.b = iArr3;
                this.c = iArr4;
                return;
            }
            do {
                i5--;
                i2 = iArr[i5];
            } while (i2 == 0);
            int iA = AbstractC3307wk.a(i2) & i3;
            if (iArr3[iA] != 0) {
                do {
                    iA = (iA + 1) & i3;
                } while (iArr3[iA] != 0);
            }
            iArr3[iA] = iArr[i5];
            iArr4[iA] = iArr2[i5];
            i6 = i7;
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC3162uo
    public final int get(int i) {
        int i2;
        if (i == 0) {
            return this.e ? this.c[this.f] : this.a;
        }
        int[] iArr = this.b;
        int iA = AbstractC3307wk.a(i) & this.d;
        int i3 = iArr[iA];
        if (i3 == 0) {
            return this.a;
        }
        if (i == i3) {
            return this.c[iA];
        }
        do {
            iA = (iA + 1) & this.d;
            i2 = iArr[iA];
            if (i2 == 0) {
                return this.a;
            }
        } while (i != i2);
        return this.c[iA];
    }

    @Override // com.android.tools.r8.internal.AbstractC3324x, java.util.Map
    public final int hashCode() {
        int i;
        int i2 = this.e ? this.h - 1 : this.h;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = i2 - 1;
            if (i2 == 0) {
                break;
            }
            while (true) {
                i = this.b[i4];
                if (i == 0) {
                    i4++;
                }
            }
            i3 += i ^ this.c[i4];
            i4++;
            i2 = i5;
        }
        return this.e ? i3 + this.c[this.f] : i3;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1050Ho
    public final InterfaceC2529mK i() {
        if (this.i == null) {
            this.i = new C1217No(this, 0);
        }
        return this.i;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.h == 0;
    }

    @Override // java.util.Map
    public final Set keySet() {
        if (this.j == null) {
            this.j = new C1163Lo(this, 0);
        }
        return this.j;
    }

    @Override // com.android.tools.r8.internal.AbstractC3324x, java.util.Map
    public final void putAll(Map map) {
        if (0.75f <= 0.5d) {
            int iA = AbstractC3307wk.a(map.size(), 0.75f);
            if (iA > this.f) {
                f(iA);
            }
        } else {
            int iA2 = (int) AbstractC3235vo.a((long) Math.ceil((map.size() + this.h) / 0.75f), 2L, 1073741824L);
            if (iA2 > this.f) {
                f(iA2);
            }
        }
        super.putAll(map);
    }

    @Override // com.android.tools.r8.internal.AbstractC3324x, com.android.tools.r8.internal.InterfaceC3162uo
    public final int remove(int i) {
        int i2;
        if (i == 0) {
            if (!this.e) {
                return this.a;
            }
            this.e = false;
            int[] iArr = this.c;
            int i3 = this.f;
            int i4 = iArr[i3];
            int i5 = this.h - 1;
            this.h = i5;
            if (i5 < this.g / 4 && i3 > 16) {
                f(i3 / 2);
            }
            return i4;
        }
        int[] iArr2 = this.b;
        int iA = AbstractC3307wk.a(i) & this.d;
        int i6 = iArr2[iA];
        if (i6 == 0) {
            return this.a;
        }
        if (i == i6) {
            return g(iA);
        }
        do {
            iA = (iA + 1) & this.d;
            i2 = iArr2[iA];
            if (i2 == 0) {
                return this.a;
            }
        } while (i != i2);
        return g(iA);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.h;
    }

    @Override // java.util.Map
    public final Collection values() {
        if (this.k == null) {
            this.k = new C1080Io(this);
        }
        return this.k;
    }

    static void a(C1301Qo c1301Qo) {
        c1301Qo.e = false;
        int[] iArr = c1301Qo.c;
        int i = c1301Qo.f;
        int i2 = iArr[i];
        int i3 = c1301Qo.h - 1;
        c1301Qo.h = i3;
        if (i3 >= c1301Qo.g / 4 || i <= 16) {
            return;
        }
        c1301Qo.f(i / 2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3162uo
    public final boolean a(int i) {
        int i2;
        if (i == 0) {
            return this.e;
        }
        int[] iArr = this.b;
        int iA = AbstractC3307wk.a(i) & this.d;
        int i3 = iArr[iA];
        if (i3 == 0) {
            return false;
        }
        if (i == i3) {
            return true;
        }
        do {
            iA = (iA + 1) & this.d;
            i2 = iArr[iA];
            if (i2 == 0) {
                return false;
            }
        } while (i != i2);
        return true;
    }

    public C1301Qo() {
        this(16);
    }

    public C1301Qo(C1301Qo c1301Qo) {
        this(c1301Qo.size());
        putAll(c1301Qo);
    }
}
