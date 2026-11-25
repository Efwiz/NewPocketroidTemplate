package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class CQ {
    protected CQ a;

    protected CQ() {
        this(589824, null);
    }

    public AbstractC2509m2 a(String str, boolean z) {
        CQ cq = this.a;
        if (cq != null) {
            return cq.a(str, z);
        }
        return null;
    }

    protected CQ(int i, CQ cq) {
        if (i != 589824 && i != 524288 && i != 458752 && i != 393216 && i != 327680 && i != 262144 && i != 17432576) {
            throw new IllegalArgumentException(AbstractC1857dg.a("Unsupported api ", i));
        }
        if (i == 17432576) {
            AbstractC1258Pc.a(this);
        }
        this.a = cq;
    }

    public AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        CQ cq = this.a;
        if (cq != null) {
            return cq.a(i, c00, str, z);
        }
        return null;
    }

    public void a(T3 t3) {
        CQ cq = this.a;
        if (cq != null) {
            cq.a(t3);
        }
    }

    public void a() {
        CQ cq = this.a;
        if (cq != null) {
            cq.a();
        }
    }
}
