package com.android.tools.r8.androidapi;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.utils.EnumC3891f;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class a {
    private final AbstractC0744s2 a;
    private final EnumC3891f b;

    private a(AbstractC0744s2 abstractC0744s2, EnumC3891f enumC3891f) {
        this.a = abstractC0744s2;
        this.b = enumC3891f;
    }

    static a a(AbstractC0744s2 abstractC0744s2, EnumC3891f enumC3891f) {
        return new a(abstractC0744s2, enumC3891f);
    }

    final AbstractC0744s2 b() {
        return this.a;
    }

    final EnumC3891f a() {
        return this.b;
    }
}
