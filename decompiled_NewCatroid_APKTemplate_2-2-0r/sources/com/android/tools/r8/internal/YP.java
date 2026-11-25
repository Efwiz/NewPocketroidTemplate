package com.android.tools.r8.internal;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class YP {
    public static final WO a(FO fo, H00 h00) {
        AbstractC0936Ds.c(fo, "<this>");
        AbstractC0936Ds.c(h00, "typeTable");
        if (fo.F()) {
            WO woT = fo.t();
            AbstractC0936Ds.b(woT, "returnType");
            return woT;
        }
        if (fo.G()) {
            return h00.a(fo.u());
        }
        throw new IllegalStateException("No returnType in ProtoBuf.Property".toString());
    }

    public static final WO a(C2303jP c2303jP, H00 h00) {
        AbstractC0936Ds.c(h00, "typeTable");
        if (c2303jP.t()) {
            WO woN = c2303jP.n();
            AbstractC0936Ds.b(woN, ModuleXmlParser.TYPE);
            return woN;
        }
        if (c2303jP.u()) {
            return h00.a(c2303jP.o());
        }
        throw new IllegalStateException("No type in ProtoBuf.ValueParameter".toString());
    }
}
