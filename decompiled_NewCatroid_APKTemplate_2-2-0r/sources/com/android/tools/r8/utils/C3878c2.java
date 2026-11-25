package com.android.tools.r8.utils;

import com.android.tools.r8.MarkerInfo;
import com.android.tools.r8.MarkerInfoConsumer;
import com.android.tools.r8.MarkerInfoConsumerData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.utils.c2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3878c2 implements MarkerInfoConsumer {
    private final List a;

    public C3878c2(ArrayList arrayList) {
        this.a = arrayList;
    }

    @Override // com.android.tools.r8.MarkerInfoConsumer
    public final void acceptMarkerInfo(MarkerInfoConsumerData markerInfoConsumerData) {
        if (markerInfoConsumerData.hasMarkers()) {
            Iterator<MarkerInfo> it = markerInfoConsumerData.getMarkers().iterator();
            while (it.hasNext()) {
                this.a.add(((C3873b2) it.next()).a());
            }
        }
    }

    @Override // com.android.tools.r8.MarkerInfoConsumer
    public final void finished() {
    }
}
