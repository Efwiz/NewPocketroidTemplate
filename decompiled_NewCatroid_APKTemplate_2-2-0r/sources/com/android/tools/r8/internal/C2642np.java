package com.android.tools.r8.internal;

import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.np, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2642np extends B implements Cloneable {
    private transient int[] b = AbstractC2949rr.a;
    private transient Object[] c = WJ.a;
    private int d;

    static /* synthetic */ int b(C2642np c2642np) {
        int i = c2642np.d;
        c2642np.d = i - 1;
        return i;
    }

    @Override // com.android.tools.r8.internal.AbstractC3470z, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
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
            C2642np c2642np = (C2642np) super.clone();
            c2642np.b = (int[]) this.b.clone();
            c2642np.c = (Object[]) this.c.clone();
            return c2642np;
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
            Object obj2 = this.c[i2];
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

    @Override // com.android.tools.r8.internal.InterfaceC2730op
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

    @Override // java.util.Map
    public final Set keySet() {
        return new C2879qr(this.b, this.d);
    }

    @Override // com.android.tools.r8.internal.AbstractC3470z
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

    @Override // com.android.tools.r8.internal.B, java.util.Map
    public final ZJ values() {
        return AbstractC1756cK.a(new VJ(this.d, this.c));
    }

    static int a(C2642np c2642np, int i) {
        int[] iArr = c2642np.b;
        int i2 = c2642np.d;
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

    @Override // com.android.tools.r8.internal.InterfaceC2947rp
    public final InterfaceC2529mK b() {
        return new C2566mp(this, 0);
    }

    @Override // com.android.tools.r8.internal.B, com.android.tools.r8.internal.InterfaceC2730op
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

    @Override // com.android.tools.r8.internal.AbstractC3470z, com.android.tools.r8.internal.InterfaceC2730op
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
