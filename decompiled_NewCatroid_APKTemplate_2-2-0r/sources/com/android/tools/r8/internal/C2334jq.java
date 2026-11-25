package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C2334jq extends AbstractC2643nq {
    final /* synthetic */ C2567mq f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C2334jq(C2567mq c2567mq) {
        super(c2567mq.i);
        this.f = c2567mq;
        this.b = c2567mq.k();
    }

    @Override // com.android.tools.r8.internal.AbstractC2643nq
    final void d() {
        C1718bq c1718bq = this.b;
        C1718bq c1718bq2 = c1718bq.d;
        if ((c1718bq.e & Integer.MIN_VALUE) == 0) {
            while ((c1718bq2.e & 1073741824) == 0) {
                c1718bq2 = c1718bq2.c;
            }
        }
        this.b = c1718bq2;
        C2567mq c2567mq = this.f;
        if (c2567mq.e || c1718bq2 == null || c2567mq.i.c(c1718bq2.a, c2567mq.c) < 0) {
            return;
        }
        this.b = null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2643nq
    final void g() {
        C1718bq c1718bqD = this.a.d();
        this.a = c1718bqD;
        C2567mq c2567mq = this.f;
        if (c2567mq.d || c1718bqD == null || c2567mq.i.c(c1718bqD.a, c2567mq.b) >= 0) {
            return;
        }
        this.a = null;
    }
}
