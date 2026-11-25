package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.to, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3085to extends AbstractC3324x implements Cloneable {
    private transient int[] b;
    private transient int[] c;
    private int d;

    public C3085to() {
        int[] iArr = AbstractC2949rr.a;
        this.b = iArr;
        this.c = iArr;
    }

    static /* synthetic */ int b(C3085to c3085to) {
        int i = c3085to.d;
        c3085to.d = i - 1;
        return i;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        this.d = 0;
    }

    public final Object clone() {
        try {
            C3085to c3085to = (C3085to) super.clone();
            c3085to.b = (int[]) this.b.clone();
            c3085to.c = (int[]) this.c.clone();
            return c3085to;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC3162uo
    public final int get(int i) {
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

    @Override // com.android.tools.r8.internal.InterfaceC1050Ho
    public final InterfaceC2529mK i() {
        return new C3015so(this, 0);
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.d == 0;
    }

    @Override // java.util.Map
    /* JADX INFO: renamed from: l, reason: merged with bridge method [inline-methods] */
    public final InterfaceC3088tr values() {
        return new C3238vr(new C2879qr(this.c, this.d));
    }

    @Override // com.android.tools.r8.internal.AbstractC3324x, com.android.tools.r8.internal.InterfaceC3162uo
    public final int remove(int i) {
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
        int i4 = this.c[i2];
        int i5 = (this.d - i2) - 1;
        int[] iArr2 = this.b;
        int i6 = i2 + 1;
        System.arraycopy(iArr2, i6, iArr2, i2, i5);
        int[] iArr3 = this.c;
        System.arraycopy(iArr3, i6, iArr3, i2, i5);
        this.d--;
        return i4;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.d;
    }

    static int a(C3085to c3085to, int i) {
        int[] iArr = c3085to.b;
        int i2 = c3085to.d;
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

    @Override // com.android.tools.r8.internal.AbstractC3324x, com.android.tools.r8.internal.InterfaceC3162uo
    public final int b(int i, int i2) {
        int i3;
        int[] iArr = this.b;
        int i4 = this.d;
        while (true) {
            i3 = i4 - 1;
            if (i4 == 0) {
                i3 = -1;
                break;
            }
            if (iArr[i3] == i) {
                break;
            }
            i4 = i3;
        }
        if (i3 != -1) {
            int[] iArr2 = this.c;
            int i5 = iArr2[i3];
            iArr2[i3] = i2;
            return i5;
        }
        int i6 = this.d;
        if (i6 == this.b.length) {
            int[] iArr3 = new int[i6 == 0 ? 2 : i6 * 2];
            int[] iArr4 = new int[i6 != 0 ? i6 * 2 : 2];
            while (true) {
                int i7 = i6 - 1;
                if (i6 == 0) {
                    break;
                }
                iArr3[i7] = this.b[i7];
                iArr4[i7] = this.c[i7];
                i6 = i7;
            }
            this.b = iArr3;
            this.c = iArr4;
        }
        int[] iArr5 = this.b;
        int i8 = this.d;
        iArr5[i8] = i;
        this.c[i8] = i2;
        this.d = i8 + 1;
        return this.a;
    }

    @Override // com.android.tools.r8.internal.AbstractC3324x
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

    @Override // java.util.Map
    public final InterfaceC1277Pr keySet() {
        return new C2879qr(this.b, this.d);
    }

    public C3085to(int i) {
        this.b = new int[i];
        this.c = new int[i];
    }

    @Override // com.android.tools.r8.internal.InterfaceC3162uo
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
}
