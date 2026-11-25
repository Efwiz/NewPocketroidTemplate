package com.android.tools.r8.androidapi;

import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.EnumC3891f;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class i implements h {
    static final /* synthetic */ boolean b = true;
    private static final i a = new i();

    private i() {
    }

    @Override // com.android.tools.r8.androidapi.h
    public final boolean J() {
        return true;
    }

    @Override // com.android.tools.r8.androidapi.h
    public final AbstractC3938n2 a(EnumC3891f enumC3891f) {
        if (b) {
            return AbstractC3938n2.c;
        }
        throw new AssertionError("Cannot compute relationship for not set");
    }

    @Override // com.android.tools.r8.androidapi.h
    public final AbstractC3938n2 b(EnumC3891f enumC3891f) {
        if (b) {
            return AbstractC3938n2.c;
        }
        throw new AssertionError("Cannot compute relationship for not set");
    }

    @Override // com.android.tools.r8.androidapi.h
    public final AbstractC3938n2 d(h hVar) {
        if (b) {
            return AbstractC3938n2.c;
        }
        throw new AssertionError("Cannot compute relationship for not set");
    }

    public final boolean equals(Object obj) {
        return this == obj;
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }
}
