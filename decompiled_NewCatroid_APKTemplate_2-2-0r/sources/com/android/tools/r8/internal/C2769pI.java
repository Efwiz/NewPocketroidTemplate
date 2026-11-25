package com.android.tools.r8.internal;

import java.util.Collection;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2769pI extends J0 implements Cloneable {
    private transient Object[] a = WJ.a;
    private transient boolean[] b = AbstractC1816d5.a;
    private int c;

    static /* synthetic */ int b(C2769pI c2769pI) {
        int i = c2769pI.c;
        c2769pI.c = i - 1;
        return i;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final void clear() {
        int i = this.c;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                this.c = 0;
                return;
            } else {
                this.a[i2] = null;
                i = i2;
            }
        }
    }

    public final Object clone() {
        try {
            C2769pI c2769pI = (C2769pI) super.clone();
            c2769pI.a = (Object[]) this.a.clone();
            c2769pI.b = (boolean[]) this.b.clone();
            return c2769pI;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final boolean containsKey(Object obj) {
        return d(obj) != -1;
    }

    @Override // com.android.tools.r8.internal.J0
    /* JADX INFO: renamed from: e */
    public final InterfaceC2529mK keySet() {
        return new VJ(this.c, this.a);
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.c == 0;
    }

    @Override // com.android.tools.r8.internal.J0
    public final InterfaceC2529mK k() {
        return new C2693oI(this, 0);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.c;
    }

    @Override // java.util.Map
    public final Collection values() {
        return AbstractC2132h5.a(new C1740c5(this.b, this.c));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int d(Object obj) {
        Object[] objArr = this.a;
        int i = this.c;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return -1;
            }
            Object obj2 = objArr[i2];
            if (obj2 == null) {
                if (obj == null) {
                    return i2;
                }
                i = i2;
            } else {
                if (obj2.equals(obj)) {
                    return i2;
                }
                i = i2;
            }
        }
    }

    public final boolean b(Object obj) {
        Object[] objArr = this.a;
        int i = this.c;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            Object obj2 = objArr[i2];
            if (obj2 == null) {
                if (obj == null) {
                    return this.b[i2];
                }
                i = i2;
            } else {
                if (obj2.equals(obj)) {
                    return this.b[i2];
                }
                i = i2;
            }
        }
    }

    @Override // com.android.tools.r8.internal.J0, com.android.tools.r8.internal.InterfaceC2840qI
    public final boolean c(Object obj) {
        int iD = d(obj);
        if (iD == -1) {
            return false;
        }
        boolean z = this.b[iD];
        int i = (this.c - iD) - 1;
        Object[] objArr = this.a;
        int i2 = iD + 1;
        System.arraycopy(objArr, i2, objArr, iD, i);
        boolean[] zArr = this.b;
        System.arraycopy(zArr, i2, zArr, iD, i);
        int i3 = this.c - 1;
        this.c = i3;
        this.a[i3] = null;
        return z;
    }

    @Override // com.android.tools.r8.internal.J0
    public final boolean a(boolean z) {
        int i = this.c;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return false;
            }
            if (this.b[i2] == z) {
                return true;
            }
            i = i2;
        }
    }

    @Override // com.android.tools.r8.internal.J0
    public final boolean a(Object obj, boolean z) {
        int iD = d(obj);
        if (iD != -1) {
            boolean[] zArr = this.b;
            boolean z2 = zArr[iD];
            zArr[iD] = z;
            return z2;
        }
        int i = this.c;
        if (i == this.a.length) {
            Object[] objArr = new Object[i == 0 ? 2 : i * 2];
            boolean[] zArr2 = new boolean[i != 0 ? i * 2 : 2];
            while (true) {
                int i2 = i - 1;
                if (i == 0) {
                    break;
                }
                objArr[i2] = this.a[i2];
                zArr2[i2] = this.b[i2];
                i = i2;
            }
            this.a = objArr;
            this.b = zArr2;
        }
        Object[] objArr2 = this.a;
        int i3 = this.c;
        objArr2[i3] = obj;
        this.b[i3] = z;
        this.c = i3 + 1;
        return false;
    }
}
