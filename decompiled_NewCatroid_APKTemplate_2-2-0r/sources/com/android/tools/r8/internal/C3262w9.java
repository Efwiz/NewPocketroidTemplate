package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.w9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3262w9 extends AbstractC2252ii {
    private boolean c;

    protected C3262w9(int i, AbstractC2252ii abstractC2252ii) {
        super(i, abstractC2252ii);
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final AbstractC2509m2 a(String str, boolean z) {
        if (this.c) {
            throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
        }
        B9.d(49, str);
        return new C2831q9(super.a(str, z), true);
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final void a(T3 t3) {
        if (!this.c) {
            super.a(t3);
            return;
        }
        throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final void a() {
        if (!this.c) {
            this.c = true;
            super.a();
            return;
        }
        throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        if (!this.c) {
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
