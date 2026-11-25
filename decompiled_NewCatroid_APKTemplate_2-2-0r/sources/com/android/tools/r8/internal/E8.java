package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class E8 extends L8 {
    static final /* synthetic */ boolean e = true;
    final K8[] c;
    final K8[] d;

    E8(int i, int i2) {
        super(0, null);
        this.c = new K8[i];
        this.d = new K8[i2];
    }

    @Override // com.android.tools.r8.internal.L8
    final E8 a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.L8
    public final int b() {
        return this.c.length - 1;
    }

    @Override // com.android.tools.r8.internal.L8
    public final H8 c() {
        if (e || this.d.length > 0) {
            return b(this.d.length - 1);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.L8
    public final int d() {
        return this.d.length;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("stack: [");
        K8[] k8Arr = this.d;
        int length = k8Arr.length;
        String str = "";
        int i = 0;
        String str2 = "";
        int i2 = 0;
        while (i2 < length) {
            sb.append(str2).append(k8Arr[i2]);
            i2++;
            str2 = ", ";
        }
        sb.append("] locals: [");
        while (true) {
            K8[] k8Arr2 = this.c;
            if (i >= k8Arr2.length) {
                return sb.append(']').toString();
            }
            if (k8Arr2[i] != null) {
                sb.append(str).append(i).append(':').append(this.c[i]);
                str = ", ";
            }
            i++;
        }
    }

    private static K8 a(C0767v2 c0767v2) {
        return c0767v2.b1().equals("NULL") ? new I8(EnumC1812d20.OBJECT) : new J8(c0767v2);
    }

    @Override // com.android.tools.r8.internal.L8
    public final H8 b(int i) {
        return H8.a(i, this.d[i]);
    }

    E8(L8 l8) {
        this(l8.b() + 1, l8.d());
        l8.a(this);
    }

    @Override // com.android.tools.r8.internal.L8
    public final H8 a(int i) {
        K8 k8;
        K8[] k8Arr = this.c;
        if (i < k8Arr.length && (k8 = k8Arr[i]) != null) {
            return new H8(i, k8);
        }
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    E8(C0767v2[] c0767v2Arr, C0767v2[] c0767v2Arr2, AbstractC1915eM abstractC1915eM) {
        super(0, null);
        if (!e && abstractC1915eM == null) {
            throw new AssertionError();
        }
        this.c = new K8[c0767v2Arr.length];
        this.d = new K8[c0767v2Arr2.length];
        for (int i = 0; i < c0767v2Arr.length; i++) {
            K8[] k8Arr = this.c;
            C0767v2 c0767v2 = c0767v2Arr[i];
            k8Arr[i] = c0767v2 == null ? null : a(c0767v2);
        }
        for (int i2 = 0; i2 < c0767v2Arr2.length; i2++) {
            if (!e && c0767v2Arr2[i2] == null) {
                throw new AssertionError();
            }
            this.d[i2] = a(c0767v2Arr2[i2]);
        }
    }

    @Override // com.android.tools.r8.internal.L8
    final void a(E8 e8) {
        int i = 0;
        int i2 = 0;
        while (true) {
            K8[] k8Arr = this.c;
            if (i2 >= k8Arr.length) {
                break;
            }
            K8[] k8Arr2 = e8.c;
            if (i2 >= k8Arr2.length) {
                break;
            }
            k8Arr2[i2] = k8Arr[i2];
            i2++;
        }
        while (true) {
            K8[] k8Arr3 = this.d;
            if (i >= k8Arr3.length) {
                return;
            }
            K8[] k8Arr4 = e8.d;
            if (i >= k8Arr4.length) {
                return;
            }
            k8Arr4[i] = k8Arr3[i];
            i++;
        }
    }
}
