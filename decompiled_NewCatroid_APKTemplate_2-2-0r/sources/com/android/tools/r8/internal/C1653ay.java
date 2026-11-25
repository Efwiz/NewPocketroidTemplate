package com.android.tools.r8.internal;

import java.util.ArrayList;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ay, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1653ay extends AbstractC1804cy {
    private final ArrayList b = new ArrayList(1);

    @Override // com.android.tools.r8.internal.AbstractC1804cy
    public final AbstractC2425ky a(EnumC2344jy enumC2344jy, EnumC2269iy enumC2269iy) {
        AbstractC0936Ds.c(enumC2344jy, ModuleXmlParser.TYPE);
        return (AbstractC2425ky) AbstractC2909rH.a(new C2036fy(enumC2344jy, enumC2269iy), this.b);
    }

    public final ArrayList b() {
        return this.b;
    }

    public final void a(AbstractC1804cy abstractC1804cy) {
        for (C2036fy c2036fy : this.b) {
            AbstractC2425ky abstractC2425kyA = abstractC1804cy.a(c2036fy.g(), c2036fy.f());
            if (abstractC2425kyA != null) {
                c2036fy.a(abstractC2425kyA);
            }
        }
        abstractC1804cy.a();
    }
}
