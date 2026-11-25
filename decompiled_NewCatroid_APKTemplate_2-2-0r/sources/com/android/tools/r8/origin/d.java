package com.android.tools.r8.origin;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class d extends Origin {
    d() {
        super();
    }

    @Override // com.android.tools.r8.origin.Origin
    final List c(int i) {
        return new ArrayList(i);
    }

    @Override // com.android.tools.r8.origin.Origin, java.lang.Comparable
    public final /* bridge */ /* synthetic */ int compareTo(Origin origin) {
        return super.compareTo(origin);
    }

    @Override // com.android.tools.r8.origin.Origin
    public final String part() {
        return "";
    }
}
