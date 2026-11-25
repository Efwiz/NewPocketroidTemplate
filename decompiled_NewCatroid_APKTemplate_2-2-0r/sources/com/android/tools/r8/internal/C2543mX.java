package com.android.tools.r8.internal;

import com.android.tools.r8.utils.AbstractC3946p0;
import com.android.tools.r8.utils.InterfaceC3925l0;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2543mX extends AbstractC3946p0 {
    final /* synthetic */ Map d;
    final /* synthetic */ Map e;
    final /* synthetic */ Set f;

    C2543mX(IdentityHashMap identityHashMap, IdentityHashMap identityHashMap2, LinkedHashSet linkedHashSet) {
        this.d = identityHashMap;
        this.e = identityHashMap2;
        this.f = linkedHashSet;
    }

    @Override // com.android.tools.r8.utils.AbstractC3955r0
    protected final List a(Collection collection) {
        return null;
    }

    @Override // com.android.tools.r8.utils.AbstractC3946p0
    protected final com.android.tools.r8.utils.D3 a(InterfaceC3925l0 interfaceC3925l0, Function function) {
        Set set = (Set) this.d.get((IX) interfaceC3925l0.c());
        if (set != null) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                function.apply((IX) this.e.get((IX) it.next()));
            }
        }
        return com.android.tools.r8.utils.D3.e();
    }

    @Override // com.android.tools.r8.utils.AbstractC3946p0
    public final com.android.tools.r8.utils.D3 a(InterfaceC3925l0 interfaceC3925l0) {
        this.f.add((IX) interfaceC3925l0.c());
        return com.android.tools.r8.utils.D3.e();
    }
}
