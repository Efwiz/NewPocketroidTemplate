package com.android.tools.r8.utils;

import com.android.tools.r8.MarkerInfoConsumerData;
import com.android.tools.r8.origin.Origin;
import java.util.Collection;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.utils.a2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3868a2 implements MarkerInfoConsumerData {
    private final Origin a;
    private final List b;

    public C3868a2(List list, Origin origin) {
        this.a = origin;
        this.b = list;
    }

    @Override // com.android.tools.r8.MarkerInfoConsumerData
    public final Origin getInputOrigin() {
        return this.a;
    }

    @Override // com.android.tools.r8.MarkerInfoConsumerData
    public final Collection getMarkers() {
        return this.b;
    }

    @Override // com.android.tools.r8.MarkerInfoConsumerData
    public final boolean hasMarkers() {
        return !this.b.isEmpty();
    }
}
