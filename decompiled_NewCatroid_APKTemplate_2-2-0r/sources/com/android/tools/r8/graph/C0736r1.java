package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1639am;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: renamed from: com.android.tools.r8.graph.r1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0736r1 {
    public final AbstractC1639am a;

    public C0736r1(C0722p1 c0722p1) {
        C0767v2 c0767v2 = c0722p1.d3;
        C0684j2 c0684j2A = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.A1), "position");
        C0767v2 c0767v3 = c0722p1.d3;
        C0684j2 c0684j2A2 = c0722p1.a(c0767v3, c0722p1.b(c0767v3, c0722p1.A1), "limit");
        C0767v2 c0767v4 = c0722p1.d3;
        C0684j2 c0684j2A3 = c0722p1.a(c0767v4, c0722p1.b(c0767v4, new C0767v2[0]), "mark");
        C0767v2 c0767v5 = c0722p1.d3;
        C0684j2 c0684j2A4 = c0722p1.a(c0767v5, c0722p1.b(c0767v5, new C0767v2[0]), "reset");
        C0767v2 c0767v6 = c0722p1.d3;
        C0684j2 c0684j2A5 = c0722p1.a(c0767v6, c0722p1.b(c0767v6, new C0767v2[0]), LineReader.CLEAR);
        C0767v2 c0767v7 = c0722p1.d3;
        C0684j2 c0684j2A6 = c0722p1.a(c0767v7, c0722p1.b(c0767v7, new C0767v2[0]), "flip");
        C0767v2 c0767v8 = c0722p1.d3;
        this.a = AbstractC1639am.a(c0684j2A, c0684j2A2, c0684j2A3, c0684j2A4, c0684j2A5, c0684j2A6, c0722p1.a(c0767v8, c0722p1.b(c0767v8, new C0767v2[0]), "rewind"));
    }
}
