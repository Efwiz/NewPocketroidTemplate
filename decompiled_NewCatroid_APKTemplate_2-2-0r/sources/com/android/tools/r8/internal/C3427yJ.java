package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yJ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C3427yJ extends CJ {
    final /* synthetic */ BJ f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C3427yJ(BJ bj) {
        super(bj.i);
        this.f = bj;
        this.b = bj.l();
    }

    @Override // com.android.tools.r8.internal.CJ
    final void d() {
        C2841qJ c2841qJ = this.b;
        C2841qJ c2841qJ2 = c2841qJ.d;
        if ((c2841qJ.e & Integer.MIN_VALUE) == 0) {
            while ((c2841qJ2.e & 1073741824) == 0) {
                c2841qJ2 = c2841qJ2.c;
            }
        }
        this.b = c2841qJ2;
        BJ bj = this.f;
        if (bj.e || c2841qJ2 == null) {
            return;
        }
        EJ ej = bj.i;
        Object obj = c2841qJ2.a;
        Object obj2 = bj.c;
        ej.getClass();
        if (((Comparable) obj).compareTo(obj2) >= 0) {
            this.b = null;
        }
    }

    @Override // com.android.tools.r8.internal.CJ
    final void g() {
        C2841qJ c2841qJE = this.a.e();
        this.a = c2841qJE;
        BJ bj = this.f;
        if (bj.d || c2841qJE == null) {
            return;
        }
        EJ ej = bj.i;
        Object obj = c2841qJE.a;
        Object obj2 = bj.b;
        ej.getClass();
        if (((Comparable) obj).compareTo(obj2) < 0) {
            this.a = null;
        }
    }
}
