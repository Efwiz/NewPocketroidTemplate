package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.n8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2591n8 implements InterfaceC1153Le, InterfaceC2262is, InterfaceC1344Se {
    public static AbstractC2591n8 a(C0702m c0702m, AbstractC2151hN abstractC2151hN, C0825Ad c0825Ad, D7 d7) {
        return C2533mN.a(c0702m, abstractC2151hN, new C2441l8(c0825Ad, d7));
    }

    public abstract void a();

    public abstract Set b();

    public static AbstractC2591n8 a(C0702m c0702m, com.android.tools.r8.shaking.O o, AbstractC2151hN abstractC2151hN, D7 d7, BiConsumer biConsumer) {
        return C2533mN.a(c0702m, abstractC2151hN, new C2517m8(o, d7, biConsumer));
    }
}
