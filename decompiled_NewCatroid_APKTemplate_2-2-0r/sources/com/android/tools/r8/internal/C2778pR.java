package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2778pR extends AbstractC2581n1 implements Cloneable {
    private transient Object[] b = WJ.a;
    private transient int[] c = AbstractC2949rr.a;
    private int d;

    static /* synthetic */ int b(C2778pR c2778pR) {
        int i = c2778pR.d;
        c2778pR.d = i - 1;
        return i;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        int i = this.d;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                this.d = 0;
                return;
            } else {
                this.b[i2] = null;
                i = i2;
            }
        }
    }

    public final Object clone() {
        try {
            C2778pR c2778pR = (C2778pR) super.clone();
            c2778pR.b = (Object[]) this.b.clone();
            c2778pR.c = (int[]) this.c.clone();
            return c2778pR;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final boolean containsKey(Object obj) {
        int i;
        Object[] objArr = this.b;
        int i2 = this.d;
        while (true) {
            i = i2 - 1;
            if (i2 == 0) {
                i = -1;
                break;
            }
            if (objArr[i] == obj) {
                break;
            }
            i2 = i;
        }
        return i != -1;
    }

    @Override // com.android.tools.r8.internal.CR
    public final InterfaceC2529mK f() {
        return new C2702oR(this, 0);
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.d == 0;
    }

    @Override // java.util.Map
    public final Set keySet() {
        return new C2612nS(this.d, this.b);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.d;
    }

    @Override // java.util.Map
    public final Collection values() {
        return AbstractC3314wr.a(new C2879qr(this.c, this.d));
    }

    static int a(C2778pR c2778pR, Object obj) {
        Object[] objArr = c2778pR.b;
        int i = c2778pR.d;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return -1;
            }
            if (objArr[i2] == obj) {
                return i2;
            }
            i = i2;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2581n1
    public final int d(Object obj) {
        int i;
        Object[] objArr = this.b;
        int i2 = this.d;
        while (true) {
            i = i2 - 1;
            if (i2 == 0) {
                i = -1;
                break;
            }
            if (objArr[i] == obj) {
                break;
            }
            i2 = i;
        }
        if (i == -1) {
            return this.a;
        }
        int i3 = this.c[i];
        int i4 = (this.d - i) - 1;
        Object[] objArr2 = this.b;
        int i5 = i + 1;
        System.arraycopy(objArr2, i5, objArr2, i, i4);
        int[] iArr = this.c;
        System.arraycopy(iArr, i5, iArr, i, i4);
        int i6 = this.d - 1;
        this.d = i6;
        this.b[i6] = null;
        return i3;
    }

    @Override // com.android.tools.r8.internal.AbstractC2581n1, com.android.tools.r8.internal.InterfaceC2849qR
    public final int a(int i, Object obj) {
        int i2;
        Object[] objArr = this.b;
        int i3 = this.d;
        while (true) {
            i2 = i3 - 1;
            if (i3 == 0) {
                i2 = -1;
                break;
            }
            if (objArr[i2] == obj) {
                break;
            }
            i3 = i2;
        }
        if (i2 != -1) {
            int[] iArr = this.c;
            int i4 = iArr[i2];
            iArr[i2] = i;
            return i4;
        }
        int i5 = this.d;
        if (i5 == this.b.length) {
            Object[] objArr2 = new Object[i5 == 0 ? 2 : i5 * 2];
            int[] iArr2 = new int[i5 != 0 ? i5 * 2 : 2];
            while (true) {
                int i6 = i5 - 1;
                if (i5 == 0) {
                    break;
                }
                objArr2[i6] = this.b[i6];
                iArr2[i6] = this.c[i6];
                i5 = i6;
            }
            this.b = objArr2;
            this.c = iArr2;
        }
        Object[] objArr3 = this.b;
        int i7 = this.d;
        objArr3[i7] = obj;
        this.c[i7] = i;
        this.d = i7 + 1;
        return this.a;
    }

    @Override // com.android.tools.r8.internal.AbstractC2581n1
    public final boolean d(int i) {
        int i2 = this.d;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return false;
            }
            if (this.c[i3] == i) {
                return true;
            }
            i2 = i3;
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2849qR
    public final int a(Object obj) {
        Object[] objArr = this.b;
        int i = this.d;
        while (true) {
            int i2 = i - 1;
            if (i != 0) {
                if (objArr[i2] == obj) {
                    return this.c[i2];
                }
                i = i2;
            } else {
                return this.a;
            }
        }
    }
}
