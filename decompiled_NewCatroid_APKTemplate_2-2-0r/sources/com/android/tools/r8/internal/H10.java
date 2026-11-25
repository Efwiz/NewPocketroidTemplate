package com.android.tools.r8.internal;

import java.lang.reflect.Method;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class H10 extends K10 {
    final /* synthetic */ Method a;
    final /* synthetic */ int b;

    H10(Method method, int i) {
        this.a = method;
        this.b = i;
    }

    @Override // com.android.tools.r8.internal.K10
    public final Object b(Class cls) {
        K10.a(cls);
        return this.a.invoke(null, cls, Integer.valueOf(this.b));
    }
}
