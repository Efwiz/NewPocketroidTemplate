package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3017sq extends G implements Cloneable {
    private transient int[] b;
    private transient Object[] c;
    private int d;

    public C3017sq() {
        this.b = AbstractC2949rr.a;
        this.c = WJ.a;
    }

    static /* synthetic */ int b(C3017sq c3017sq) {
        int i = c3017sq.d;
        c3017sq.d = i - 1;
        return i;
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        int i = this.d;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                this.d = 0;
                return;
            } else {
                this.c[i2] = null;
                i = i2;
            }
        }
    }

    public final Object clone() {
        try {
            C3017sq c3017sq = (C3017sq) super.clone();
            c3017sq.b = (int[]) this.b.clone();
            c3017sq.c = (Object[]) this.c.clone();
            return c3017sq;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        int i = this.d;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            if (this.c[i2] == obj) {
                return true;
            }
            i = i2;
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC3087tq
    public final Object get(int i) {
        int[] iArr = this.b;
        int i2 = this.d;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return this.a;
            }
            if (iArr[i3] == i) {
                return this.c[i3];
            }
            i2 = i3;
        }
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.d == 0;
    }

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC1277Pr keySet() {
        return new C2879qr(this.b, this.d);
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object remove(int i) {
        int i2;
        int[] iArr = this.b;
        int i3 = this.d;
        while (true) {
            i2 = i3 - 1;
            if (i3 == 0) {
                i2 = -1;
                break;
            }
            if (iArr[i2] == i) {
                break;
            }
            i3 = i2;
        }
        if (i2 == -1) {
            return this.a;
        }
        Object obj = this.c[i2];
        int i4 = (this.d - i2) - 1;
        int[] iArr2 = this.b;
        int i5 = i2 + 1;
        System.arraycopy(iArr2, i5, iArr2, i2, i4);
        Object[] objArr = this.c;
        System.arraycopy(objArr, i5, objArr, i2, i4);
        int i6 = this.d - 1;
        this.d = i6;
        this.c[i6] = null;
        return obj;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.d;
    }

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC2703oS values() {
        return AbstractC2922rS.a(new C2612nS(this.d, this.c));
    }

    static int a(C3017sq c3017sq, int i) {
        int[] iArr = c3017sq.b;
        int i2 = c3017sq.d;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return -1;
            }
            if (iArr[i3] == i) {
                return i3;
            }
            i2 = i3;
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC0992Fq
    public final InterfaceC2529mK c() {
        return new C2948rq(this, 0);
    }

    public C3017sq(int i) {
        this.b = new int[i];
        this.c = new Object[i];
    }

    @Override // com.android.tools.r8.internal.G, com.android.tools.r8.internal.InterfaceC3087tq
    public final boolean a(int i) {
        int i2;
        int[] iArr = this.b;
        int i3 = this.d;
        while (true) {
            i2 = i3 - 1;
            if (i3 == 0) {
                i2 = -1;
                break;
            }
            if (iArr[i2] == i) {
                break;
            }
            i3 = i2;
        }
        return i2 != -1;
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object a(int i, Object obj) {
        int i2;
        int[] iArr = this.b;
        int i3 = this.d;
        while (true) {
            i2 = i3 - 1;
            if (i3 == 0) {
                i2 = -1;
                break;
            }
            if (iArr[i2] == i) {
                break;
            }
            i3 = i2;
        }
        if (i2 != -1) {
            Object[] objArr = this.c;
            Object obj2 = objArr[i2];
            objArr[i2] = obj;
            return obj2;
        }
        int i4 = this.d;
        if (i4 == this.b.length) {
            int[] iArr2 = new int[i4 == 0 ? 2 : i4 * 2];
            Object[] objArr2 = new Object[i4 != 0 ? i4 * 2 : 2];
            while (true) {
                int i5 = i4 - 1;
                if (i4 == 0) {
                    break;
                }
                iArr2[i5] = this.b[i5];
                objArr2[i5] = this.c[i5];
                i4 = i5;
            }
            this.b = iArr2;
            this.c = objArr2;
        }
        int[] iArr3 = this.b;
        int i6 = this.d;
        iArr3[i6] = i;
        this.c[i6] = obj;
        this.d = i6 + 1;
        return this.a;
    }
}
