package com.android.tools.r8.androidapi;

import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.EnumC3891f;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class j implements h {
    private static final j a = new j();

    private j() {
    }

    @Override // com.android.tools.r8.androidapi.h
    public final boolean H() {
        return true;
    }

    @Override // com.android.tools.r8.androidapi.h
    public final AbstractC3938n2 a(EnumC3891f enumC3891f) {
        return AbstractC3938n2.c;
    }

    @Override // com.android.tools.r8.androidapi.h
    public final AbstractC3938n2 b(EnumC3891f enumC3891f) {
        return AbstractC3938n2.c;
    }

    @Override // com.android.tools.r8.androidapi.h
    public final AbstractC3938n2 d(h hVar) {
        return AbstractC3938n2.c;
    }

    public final boolean equals(Object obj) {
        return this == obj;
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }

    public final String toString() {
        return GrsBaseInfo.CountryCodeSource.UNKNOWN;
    }
}
