package com.android.tools.r8.diagnostic.internal;

import com.android.tools.r8.diagnostic.DefinitionContext;
import com.android.tools.r8.diagnostic.internal.b;
import com.android.tools.r8.origin.Origin;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class b<B extends b<B>> {
    static final /* synthetic */ boolean b = true;
    Origin a;

    b() {
    }

    public abstract DefinitionContext a();

    public b a(Origin origin) {
        this.a = origin;
        return b();
    }

    abstract b b();
}
