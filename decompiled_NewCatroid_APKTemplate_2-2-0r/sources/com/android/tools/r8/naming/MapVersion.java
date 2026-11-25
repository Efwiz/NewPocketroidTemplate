package com.android.tools.r8.naming;

import org.catrobat.catroid.BuildConfig;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public enum MapVersion implements com.android.tools.r8.utils.structural.s<MapVersion> {
    MAP_VERSION_NONE("none"),
    MAP_VERSION_1_0(BuildConfig.VERSION_NAME),
    MAP_VERSION_2_0("2.0"),
    MAP_VERSION_2_1("2.1"),
    MAP_VERSION_2_2("2.2"),
    MAP_VERSION_EXPERIMENTAL("experimental"),
    MAP_VERSION_UNKNOWN("unknown");

    private final String a;
    public static final MapVersion STABLE = MAP_VERSION_2_2;

    MapVersion(String str) {
        this.a = str;
    }

    public static MapVersion fromName(String str) {
        for (MapVersion mapVersion : values()) {
            if (mapVersion.getName().equals(str)) {
                return mapVersion;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.utils.structural.s
    public /* bridge */ /* synthetic */ int compareTo(com.android.tools.r8.utils.structural.s sVar) {
        return compareTo((Enum) sVar);
    }

    public String getName() {
        return this.a;
    }

    @Override // com.android.tools.r8.utils.structural.s, com.android.tools.r8.utils.structural.k
    public /* bridge */ /* synthetic */ boolean isEqualTo(Object obj) {
        return isEqualTo((com.android.tools.r8.utils.structural.s) obj);
    }

    public boolean isUnknown() {
        return this == MAP_VERSION_UNKNOWN;
    }

    public com.android.tools.r8.naming.mappinginformation.b toMapVersionMappingInformation() {
        return new com.android.tools.r8.naming.mappinginformation.b(this, getName());
    }
}
