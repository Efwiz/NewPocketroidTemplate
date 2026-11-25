package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2708oW extends S10 {
    static final /* synthetic */ boolean r = true;
    private final C2617nW[] q;

    public C2708oW(C2617nW... c2617nWArr) {
        super(-1, AbstractC3472z00.f(), null);
        this.q = c2617nWArr;
        if (!r && c2617nWArr.length < 2) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.S10
    public final boolean T() {
        return true;
    }

    @Override // com.android.tools.r8.internal.S10
    public final boolean Y() {
        return false;
    }

    @Override // com.android.tools.r8.internal.S10
    public final void a(boolean z) {
        if (!r && z) {
            throw new AssertionError();
        }
    }

    public final C2617nW[] j0() {
        return this.q;
    }

    @Override // com.android.tools.r8.internal.S10
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (C2617nW c2617nW : this.q) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(c2617nW);
        }
        sb.append(']');
        return sb.toString();
    }

    @Override // com.android.tools.r8.internal.S10
    public final AbstractC3472z00 w() {
        throw new C3405y10();
    }
}
