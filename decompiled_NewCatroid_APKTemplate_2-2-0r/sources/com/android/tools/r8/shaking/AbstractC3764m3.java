package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.TextPosition;
import java.util.Collections;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.m3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3764m3 {
    /* JADX WARN: Multi-variable type inference failed */
    public static C3732h3 a(Origin origin, TextPosition textPosition, C0767v2 c0767v2) {
        C3732h3.a aVarA = C3732h3.I().a(EnumC3758l3.KEEP);
        aVarA.i = I2.b;
        aVarA.a = origin;
        aVarA.b = textPosition;
        return ((C3732h3.a) ((C3732h3.a) ((C3732h3.a) aVarA.a(AbstractC3833y2.b().a(false, G3.a(c0767v2)).a())).c(Collections.singletonList(C3775o3.b()))).a("-keepkotlinmetadata")).f();
    }
}
