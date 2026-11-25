package com.android.tools.r8.internal;

import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.MethodReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class GE implements InterfaceC2249ie {
    GE() {
    }

    FE a() {
        return null;
    }

    abstract GE a(ClassReference classReference);

    static EE a(ClassReference classReference, String str) {
        return new EE(classReference, str);
    }

    static FE a(MethodReference methodReference) {
        return new FE(methodReference);
    }
}
