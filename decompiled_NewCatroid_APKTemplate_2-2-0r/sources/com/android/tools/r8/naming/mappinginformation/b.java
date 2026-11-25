package com.android.tools.r8.naming.mappinginformation;

import com.android.tools.r8.internal.C1333Rt;
import com.android.tools.r8.internal.C1434Ut;
import com.android.tools.r8.naming.MapVersion;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class b extends e {
    static final /* synthetic */ boolean c = true;
    private final MapVersion a;
    private final String b;

    public b(MapVersion mapVersion, String str) {
        this.a = mapVersion;
        this.b = str;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final boolean a(e eVar) {
        return true;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final b b() {
        return this;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final String p() {
        C1333Rt c1333Rt = new C1333Rt();
        c1333Rt.a("id", new C1434Ut("com.android.tools.r8.mapping"));
        c1333Rt.a("version", new C1434Ut(this.a.getName()));
        return c1333Rt.toString();
    }

    public MapVersion q() {
        return this.a;
    }

    public final String r() {
        return this.b;
    }

    public static void a(C1333Rt c1333Rt, int i, Consumer consumer) {
        String strF = c1333Rt.a("version").f();
        if (strF == null) {
            MappingInformationDiagnostics.a(i, "version", "com.android.tools.r8.mapping");
            return;
        }
        MapVersion mapVersionFromName = MapVersion.fromName(strF);
        if (mapVersionFromName == null) {
            mapVersionFromName = MapVersion.MAP_VERSION_UNKNOWN;
        }
        consumer.accept(new b(mapVersionFromName, strF));
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final e b(e eVar) {
        if (!c) {
            eVar.getClass();
            if (!(eVar instanceof b)) {
                throw new AssertionError();
            }
        }
        return this.a.b(eVar.b().q()) ? eVar : this;
    }
}
