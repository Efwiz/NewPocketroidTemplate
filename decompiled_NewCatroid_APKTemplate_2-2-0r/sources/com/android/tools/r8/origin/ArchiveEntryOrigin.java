package com.android.tools.r8.origin;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class ArchiveEntryOrigin extends Origin {
    final String e;

    public ArchiveEntryOrigin(String str, Origin origin) {
        super(origin);
        this.e = str;
    }

    public String getEntryName() {
        return this.e;
    }

    @Override // com.android.tools.r8.origin.Origin
    public String part() {
        return this.e;
    }
}
