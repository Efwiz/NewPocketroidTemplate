package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0564k$$ExternalSyntheticLambda22;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Vk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1451Vk {
    private final ArrayList a = new ArrayList();
    private final ArrayList b = new ArrayList();
    private boolean c = false;

    public final ArrayList a() {
        return this.a;
    }

    public final void b() {
        this.c = true;
    }

    public final P20 c() {
        this.a.sort(new JV$$ExternalSyntheticLambda3());
        this.b.sort(new C0564k$$ExternalSyntheticLambda22());
        return new P20(AbstractC1639am.a(this.a), AbstractC1639am.a(this.b), this.c);
    }

    public final void a(C0767v2 c0767v2) {
        this.b.add(c0767v2);
    }
}
