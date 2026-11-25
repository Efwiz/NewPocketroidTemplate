package com.android.tools.r8.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.s3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0745s3 {
    private final Map a;

    private C0745s3(HashMap map) {
        this.a = map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C0745s3 a(List list) {
        final HashMap map = new HashMap();
        list.forEach(new Consumer() { // from class: com.android.tools.r8.graph.s3$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0745s3.a(map, (C0706m3.f) obj);
            }
        });
        return new C0745s3(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Map map, C0706m3.f fVar) {
        C0706m3.e eVar = fVar.b;
        if (eVar != null && eVar.b() && fVar.b.m()) {
            map.put(fVar.a, fVar.b);
        } else if (!fVar.c.isEmpty() && fVar.c.get(0).m()) {
            map.put(fVar.a, fVar.c.get(0));
        } else {
            map.put(fVar.a, null);
        }
    }
}
