package com.android.tools.r8.internal;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cy, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0914Cy {
    private final AbstractC0914Cy a;

    public AbstractC0914Cy() {
        this(0);
    }

    public AbstractC1200My a(int i, String str, int i2, EnumC1364Sy enumC1364Sy) {
        AbstractC0936Ds.c(str, "name");
        AbstractC0936Ds.c(enumC1364Sy, "variance");
        AbstractC0914Cy abstractC0914Cy = this.a;
        if (abstractC0914Cy != null) {
            return abstractC0914Cy.a(i, str, i2, enumC1364Sy);
        }
        return null;
    }

    public AbstractC1254Oy b(int i) {
        AbstractC0914Cy abstractC0914Cy = this.a;
        if (abstractC0914Cy != null) {
            return abstractC0914Cy.b(i);
        }
        return null;
    }

    public AbstractC1254Oy c(int i) {
        AbstractC0914Cy abstractC0914Cy = this.a;
        if (abstractC0914Cy != null) {
            return abstractC0914Cy.c(i);
        }
        return null;
    }

    public AbstractC0914Cy(AbstractC0914Cy abstractC0914Cy) {
        this.a = abstractC0914Cy;
    }

    public AbstractC1254Oy a(int i) {
        AbstractC0914Cy abstractC0914Cy = this.a;
        if (abstractC0914Cy != null) {
            return abstractC0914Cy.a(i);
        }
        return null;
    }

    public AbstractC1520Xy b() {
        AbstractC0914Cy abstractC0914Cy = this.a;
        if (abstractC0914Cy != null) {
            return abstractC0914Cy.b();
        }
        return null;
    }

    public AbstractC1338Ry a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        AbstractC0914Cy abstractC0914Cy = this.a;
        if (abstractC0914Cy != null) {
            return abstractC0914Cy.a(i, str);
        }
        return null;
    }

    public InterfaceC0874By a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        AbstractC0914Cy abstractC0914Cy = this.a;
        if (abstractC0914Cy != null) {
            return abstractC0914Cy.a(c2576my);
        }
        return null;
    }

    public /* synthetic */ AbstractC0914Cy(int i) {
        this((AbstractC0914Cy) null);
    }

    public void a() {
        AbstractC0914Cy abstractC0914Cy = this.a;
        if (abstractC0914Cy != null) {
            abstractC0914Cy.a();
        }
    }
}
