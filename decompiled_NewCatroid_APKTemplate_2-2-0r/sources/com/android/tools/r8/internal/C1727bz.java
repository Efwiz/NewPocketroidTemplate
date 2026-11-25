package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1727bz {
    public final C0767v2 a;
    final /* synthetic */ C2037fz b;

    private C1727bz(C2037fz c2037fz) {
        this.b = c2037fz;
        new C3050tI((Map) IntStream.rangeClosed(0, 22).boxed().collect(Collectors.toMap(new Function() { // from class: com.android.tools.r8.internal.bz$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((Integer) obj);
            }
        }, Function.identity())));
        C0767v2 c0767v2E = c2037fz.a.e("Lkotlin/jvm/internal/Lambda;");
        this.a = c0767v2E;
        C0722p1 c0722p1 = c2037fz.a;
        c0722p1.a(c0767v2E, c0722p1.b(c0722p1.D1, c0722p1.A1), c2037fz.a.Y0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0767v2 a(Integer num) {
        return this.b.a.e("Lkotlin/jvm/functions/Function" + num + ";");
    }
}
