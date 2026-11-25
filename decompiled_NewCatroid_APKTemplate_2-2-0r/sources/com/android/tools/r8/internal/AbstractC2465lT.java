package com.android.tools.r8.internal;

import com.android.tools.r8.naming.MapVersion;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2465lT extends com.android.tools.r8.naming.mappinginformation.d {
    public static final MapVersion a = MapVersion.MAP_VERSION_2_2;

    public static boolean a(MapVersion mapVersion) {
        return mapVersion.a(a);
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final AbstractC2465lT h() {
        return this;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final boolean n() {
        return true;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final String p() {
        C1333Rt c1333Rt = new C1333Rt();
        c1333Rt.a("id", new C1434Ut("com.android.tools.r8.residualsignature"));
        c1333Rt.a("signature", new C1434Ut(r()));
        return c1333Rt.toString();
    }

    public abstract boolean q();

    protected abstract String r();

    public static void a(MapVersion mapVersion, C1333Rt c1333Rt, Consumer consumer) {
        if (a(mapVersion)) {
            AbstractC1249Ot abstractC1249OtA = c1333Rt.a("signature");
            if (abstractC1249OtA == null) {
                throw new C1367Tb("Expected 'signature' to be present: " + c1333Rt);
            }
            String strF = abstractC1249OtA.f();
            if (strF.contains("(")) {
                consumer.accept(C2385kT.a(strF));
            } else {
                consumer.accept(C2307jT.a(strF));
            }
        }
    }
}
