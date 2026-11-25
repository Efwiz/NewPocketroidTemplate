package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class RG extends AbstractC2309jV {
    static final /* synthetic */ boolean c = true;
    private final Set b;

    public RG(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        if (!c && !enumC0928Dk.b()) {
            throw new AssertionError();
        }
        this.b = (Set) c0702m.a(Collections.emptySet(), new Function() { // from class: com.android.tools.r8.internal.RG$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0856Bg) obj).b();
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        return !this.b.contains(c0729q2.getType());
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoDeadEnumLiteMaps";
    }
}
