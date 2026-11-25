package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3050tI extends L0 implements Cloneable {
    private transient Object[] b;
    private transient int[] c;
    private int d;

    public C3050tI(int i) {
        this.b = new Object[i];
        this.c = new int[i];
    }

    static /* synthetic */ int b(C3050tI c3050tI) {
        int i = c3050tI.d;
        c3050tI.d = i - 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int e(Object obj) {
        Object[] objArr = this.b;
        int i = this.d;
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
            C3050tI c3050tI = (C3050tI) super.clone();
            c3050tI.b = (Object[]) this.b.clone();
            c3050tI.c = (int[]) this.c.clone();
            return c3050tI;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final boolean containsKey(Object obj) {
        return e(obj) != -1;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.d == 0;
    }

    @Override // com.android.tools.r8.internal.GI
    public final InterfaceC2529mK j() {
        return new C2981sI(this, 0);
    }

    @Override // java.util.Map
    public final Set keySet() {
        return new VJ(this.d, this.b);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.d;
    }

    @Override // java.util.Map
    public final Collection values() {
        return AbstractC3314wr.a(new C2879qr(this.c, this.d));
    }

    @Override // com.android.tools.r8.internal.L0
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

    @Override // com.android.tools.r8.internal.InterfaceC3119uI
    public final int a(Object obj) {
        Object[] objArr = this.b;
        int i = this.d;
        while (true) {
            int i2 = i - 1;
            if (i != 0) {
                Object obj2 = objArr[i2];
                if (obj2 == null) {
                    if (obj == null) {
                        return this.c[i2];
                    }
                    i = i2;
                } else {
                    if (obj2.equals(obj)) {
                        return this.c[i2];
                    }
                    i = i2;
                }
            } else {
                return this.a;
            }
        }
    }

    public C3050tI(Map map) {
        this(map.size());
        putAll(map);
    }

    @Override // com.android.tools.r8.internal.L0
    public final int d(Object obj) {
        int iE = e(obj);
        if (iE == -1) {
            return this.a;
        }
        int i = this.c[iE];
        int i2 = (this.d - iE) - 1;
        Object[] objArr = this.b;
        int i3 = iE + 1;
        System.arraycopy(objArr, i3, objArr, iE, i2);
        int[] iArr = this.c;
        System.arraycopy(iArr, i3, iArr, iE, i2);
        int i4 = this.d - 1;
        this.d = i4;
        this.b[i4] = null;
        return i;
    }

    @Override // com.android.tools.r8.internal.L0, com.android.tools.r8.internal.InterfaceC3119uI
    public final int a(int i, Object obj) {
        int iE = e(obj);
        if (iE != -1) {
            int[] iArr = this.c;
            int i2 = iArr[iE];
            iArr[iE] = i;
            return i2;
        }
        int i3 = this.d;
        if (i3 == this.b.length) {
            Object[] objArr = new Object[i3 == 0 ? 2 : i3 * 2];
            int[] iArr2 = new int[i3 != 0 ? i3 * 2 : 2];
            while (true) {
                int i4 = i3 - 1;
                if (i3 == 0) {
                    break;
                }
                objArr[i4] = this.b[i4];
                iArr2[i4] = this.c[i4];
                i3 = i4;
            }
            this.b = objArr;
            this.c = iArr2;
        }
        Object[] objArr2 = this.b;
        int i5 = this.d;
        objArr2[i5] = obj;
        this.c[i5] = i;
        this.d = i5 + 1;
        return this.a;
    }
}
