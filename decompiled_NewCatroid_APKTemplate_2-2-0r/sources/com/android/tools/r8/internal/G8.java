package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class G8 extends L8 {
    static final /* synthetic */ boolean d = true;
    private final H8 c;

    G8(L8 l8, K8 k8) {
        super(l8.b + 1, l8);
        H8 h8A = H8.a(l8.d(), k8);
        this.c = h8A;
        if (d) {
            return;
        }
        int i = h8A.a;
        if (100000 > i || i >= 200000) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.L8
    final void a(E8 e8) {
        this.a.a(e8);
        int iB = H8.b(this.c.a);
        K8[] k8Arr = e8.d;
        if (iB < k8Arr.length) {
            k8Arr[H8.b(this.c.a)] = this.c.d;
        }
    }

    @Override // com.android.tools.r8.internal.L8
    public final H8 b(int i) {
        return i == H8.b(this.c.a) ? this.c : this.a.b(i);
    }

    @Override // com.android.tools.r8.internal.L8
    public final H8 c() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.L8
    public final int d() {
        return H8.b(this.c.a) + 1;
    }

    public final String toString() {
        return this.a.toString() + "; push(" + this.c.d + Constant.AFTER_QUTO;
    }
}
