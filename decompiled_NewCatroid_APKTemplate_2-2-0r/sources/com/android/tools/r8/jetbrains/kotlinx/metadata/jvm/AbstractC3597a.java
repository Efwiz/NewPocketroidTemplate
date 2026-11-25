package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.C2576my;
import com.android.tools.r8.internal.CS;
import com.android.tools.r8.internal.InterfaceC1337Rx;

/* JADX INFO: renamed from: com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.a, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3597a extends c implements InterfaceC1337Rx {
    public static final C2576my b = new C2576my(CS.a(AbstractC3597a.class));

    public /* synthetic */ AbstractC3597a() {
        this(null);
    }

    public abstract void a(int i);

    public final void b() {
        AbstractC3597a abstractC3597a = (AbstractC3597a) a();
        if (abstractC3597a != null) {
            abstractC3597a.b();
        }
    }

    public abstract void b(String str);

    @Override // com.android.tools.r8.internal.InterfaceC2653ny
    public final C2576my getType() {
        return b;
    }

    public AbstractC3597a(AbstractC3597a abstractC3597a) {
        super(abstractC3597a);
    }
}
