package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class G9 extends CQ {
    private boolean b;

    protected G9(int i, CQ cq) {
        super(i, cq);
    }

    @Override // com.android.tools.r8.internal.CQ
    public final AbstractC2509m2 a(String str, boolean z) {
        if (this.b) {
            throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
        }
        B9.d(49, str);
        return new C2831q9(super.a(str, z), true);
    }

    @Override // com.android.tools.r8.internal.CQ
    public final void a(T3 t3) {
        if (!this.b) {
            super.a(t3);
            return;
        }
        throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
    }

    @Override // com.android.tools.r8.internal.CQ
    public final void a() {
        if (!this.b) {
            this.b = true;
            super.a();
            return;
        }
        throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
    }

    @Override // com.android.tools.r8.internal.CQ
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        if (!this.b) {
            int iC = new D00(i).c();
            if (iC == 19) {
                AbstractC3187v9.a(i);
                B9.d(49, str);
                return new C2831q9(super.a(i, c00, str, z), true);
            }
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(iC));
        }
        throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
    }
}
