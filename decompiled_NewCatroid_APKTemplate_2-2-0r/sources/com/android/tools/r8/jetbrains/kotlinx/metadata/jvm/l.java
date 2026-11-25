package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.C2576my;
import com.android.tools.r8.internal.CS;
import com.android.tools.r8.internal.InterfaceC0874By;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class l implements InterfaceC0874By {
    public static final C2576my b = new C2576my(CS.a(l.class));
    private final l a;

    public l() {
        this(0);
    }

    public void a(int i, e eVar, j jVar, j jVar2) {
        l lVar = this.a;
        if (lVar != null) {
            lVar.a(i, eVar, jVar, jVar2);
        }
        a();
    }

    public void b(j jVar) {
        l lVar = this.a;
        if (lVar != null) {
            lVar.b(jVar);
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2653ny
    public final C2576my getType() {
        return b;
    }

    public l(l lVar) {
        this.a = lVar;
    }

    public void b() {
        l lVar = this.a;
        if (lVar != null) {
            lVar.b();
        }
    }

    public /* synthetic */ l(int i) {
        this((l) null);
    }

    public final void a() {
        l lVar = this.a;
        if (lVar != null) {
            lVar.a();
        }
    }

    public void a(j jVar) {
        l lVar = this.a;
        if (lVar != null) {
            lVar.a(jVar);
        }
    }
}
