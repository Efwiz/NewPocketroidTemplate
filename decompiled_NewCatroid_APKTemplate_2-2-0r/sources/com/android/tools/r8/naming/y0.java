package com.android.tools.r8.naming;

import com.android.tools.r8.Version;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.L3;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class y0 {
    private final String a;
    private final boolean b;
    private final EnumC3891f c;
    private final MapVersion d;
    private final J0 e;

    private y0(String str, boolean z, EnumC3891f enumC3891f, MapVersion mapVersion, J0 j0) {
        this.a = str;
        this.b = z;
        this.c = enumC3891f;
        this.d = mapVersion;
        this.e = j0;
    }

    public final ArrayList a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("# compiler: " + this.a);
        arrayList.add("# compiler_version: 8.1.56");
        if (this.b) {
            arrayList.add("# min_api: " + this.c.d());
        }
        if (Version.isDevelopmentVersion()) {
            arrayList.add("# compiler_hash: " + L3.c.b());
        }
        arrayList.add("# common_typos_disable");
        if (this.d.d(MapVersion.MAP_VERSION_NONE)) {
            arrayList.add("# " + this.d.toMapVersionMappingInformation().p());
        }
        arrayList.add("# pg_map_id: " + this.e.b());
        arrayList.add("# pg_map_hash: SHA-256 " + this.e.a());
        return arrayList;
    }
}
