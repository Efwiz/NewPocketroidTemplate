package com.android.tools.r8.internal;

import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.FieldReference;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ih, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1070Ih implements InterfaceC2249ie {
    AbstractC1070Ih() {
    }

    public C1043Hh a() {
        return null;
    }

    abstract AbstractC1070Ih a(ClassReference classReference);

    static C1009Gh a(ClassReference classReference, String str) {
        return new C1009Gh(classReference, str);
    }

    public static C1043Hh a(FieldReference fieldReference) {
        return new C1043Hh(fieldReference);
    }
}
