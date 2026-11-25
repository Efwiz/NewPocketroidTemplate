package com.android.tools.r8.internal;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class GZ extends EZ {
    private final C1862dk a;
    private final EZ b;
    private final Type c;

    GZ(C1862dk c1862dk, EZ ez, Type type) {
        this.a = c1862dk;
        this.b = ez;
        this.c = type;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) {
        return this.b.a(c1486Wt);
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) {
        EZ ezA = this.b;
        Type type = this.c;
        if (obj != null && (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class))) {
            type = obj.getClass();
        }
        if (type != this.c) {
            ezA = this.a.a(I00.a(type));
            if (ezA instanceof FS) {
                EZ ez = this.b;
                if (!(ez instanceof FS)) {
                    ezA = ez;
                }
            }
        }
        ezA.a(c1799cu, obj);
    }
}
