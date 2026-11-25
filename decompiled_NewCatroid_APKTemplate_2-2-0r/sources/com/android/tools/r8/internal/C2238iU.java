package com.android.tools.r8.internal;

import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.utils.C3970u0;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2238iU extends AbstractC2004fU {
    private ClassReference a;

    private C2238iU(ClassReference classReference) {
        this.a = classReference;
    }

    @Override // com.android.tools.r8.internal.AbstractC2004fU
    protected final C1434Ut a() {
        return new C1434Ut("throws(" + this.a.getDescriptor() + Constant.AFTER_QUTO);
    }

    public final void b(ClassReference classReference) {
        this.a = classReference;
    }

    @Override // com.android.tools.r8.internal.AbstractC2004fU
    public final boolean a(CT ct) {
        return this.a.equals(ct.d());
    }

    public final ClassReference b() {
        return this.a;
    }

    public static C2238iU a(String str) {
        if (C3970u0.z(str)) {
            return new C2238iU(Reference.classFromDescriptor(str));
        }
        throw new C1367Tb("Unexpected throws-descriptor: " + str);
    }

    public static C2238iU a(ClassReference classReference) {
        return new C2238iU(classReference);
    }
}
