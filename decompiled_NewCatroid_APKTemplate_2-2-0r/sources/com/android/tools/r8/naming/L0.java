package com.android.tools.r8.naming;

import com.android.tools.r8.MapIdEnvironment;
import com.android.tools.r8.MapIdProvider;
import com.android.tools.r8.internal.AbstractC0860Bk;
import com.android.tools.r8.internal.InterfaceC3524zk;
import java.nio.charset.StandardCharsets;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class L0 implements com.android.tools.r8.utils.V {
    private final InterfaceC3524zk a = AbstractC0860Bk.b().a();

    L0() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MapIdProvider a(MapIdProvider mapIdProvider) {
        return mapIdProvider != null ? mapIdProvider : new MapIdProvider() { // from class: com.android.tools.r8.naming.L0$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.MapIdProvider
            public final String get(MapIdEnvironment mapIdEnvironment) {
                return L0.a(mapIdEnvironment);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(MapIdEnvironment mapIdEnvironment) {
        return mapIdEnvironment.getMapHash().substring(0, 7);
    }

    @Override // com.android.tools.r8.utils.V
    public final com.android.tools.r8.utils.V a(String str) {
        this.a.a(str, StandardCharsets.UTF_8);
        return this;
    }
}
