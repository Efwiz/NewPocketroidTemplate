package com.android.tools.r8.internal;

import com.android.tools.r8.naming.MapVersion;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Vb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1442Vb extends com.android.tools.r8.naming.mappinginformation.d {
    private static final C1442Vb a = new C1442Vb();
    public static final MapVersion b = MapVersion.MAP_VERSION_1_0;

    private C1442Vb() {
    }

    public static void a(MapVersion mapVersion, Consumer consumer) {
        if (mapVersion.a(b)) {
            consumer.accept(a);
        }
    }

    public static C1442Vb q() {
        return a;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final com.android.tools.r8.naming.mappinginformation.e b(com.android.tools.r8.naming.mappinginformation.e eVar) {
        return eVar;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final boolean k() {
        return true;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final String p() {
        C1333Rt c1333Rt = new C1333Rt();
        c1333Rt.a("id", new C1434Ut("com.android.tools.r8.synthesized"));
        return c1333Rt.toString();
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final boolean a(com.android.tools.r8.naming.mappinginformation.e eVar) {
        return !eVar.k();
    }
}
