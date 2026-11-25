package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.AbstractC0936Ds;
import com.android.tools.r8.internal.C2576my;
import com.android.tools.r8.internal.CS;
import com.android.tools.r8.internal.InterfaceC2956ry;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class f implements InterfaceC2956ry {
    public static final C2576my b = new C2576my(CS.a(f.class));
    private final f a;

    public f() {
        this(0);
    }

    public void a(j jVar) {
        f fVar = this.a;
        if (fVar != null) {
            fVar.a(jVar);
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2653ny
    public final C2576my getType() {
        return b;
    }

    public f(f fVar) {
        this.a = fVar;
    }

    public void a(String str) {
        AbstractC0936Ds.c(str, "internalName");
        f fVar = this.a;
        if (fVar != null) {
            fVar.a(str);
        }
    }

    public final void a() {
        f fVar = this.a;
        if (fVar != null) {
            fVar.a();
        }
    }

    public /* synthetic */ f(int i) {
        this((f) null);
    }
}
