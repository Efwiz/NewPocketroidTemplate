package com.android.tools.r8.internal;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Oy, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1254Oy {
    private final AbstractC1254Oy a;

    public AbstractC1254Oy() {
        this(0);
    }

    public void a(String str) {
        AbstractC0936Ds.c(str, "name");
        AbstractC1254Oy abstractC1254Oy = this.a;
        if (abstractC1254Oy != null) {
            abstractC1254Oy.a(str);
        }
    }

    public void b(String str) {
        AbstractC0936Ds.c(str, "name");
        AbstractC1254Oy abstractC1254Oy = this.a;
        if (abstractC1254Oy != null) {
            abstractC1254Oy.b(str);
        }
    }

    public void c(int i) {
        AbstractC1254Oy abstractC1254Oy = this.a;
        if (abstractC1254Oy != null) {
            abstractC1254Oy.c(i);
        }
    }

    public AbstractC1254Oy(AbstractC1254Oy abstractC1254Oy) {
        this.a = abstractC1254Oy;
    }

    public AbstractC1254Oy a(int i, EnumC1364Sy enumC1364Sy) {
        AbstractC1254Oy abstractC1254Oy = this.a;
        if (abstractC1254Oy != null) {
            return abstractC1254Oy.a(i, enumC1364Sy);
        }
        return null;
    }

    public void b() {
        AbstractC1254Oy abstractC1254Oy = this.a;
        if (abstractC1254Oy != null) {
            abstractC1254Oy.b();
        }
    }

    public AbstractC1254Oy a(int i) {
        AbstractC1254Oy abstractC1254Oy = this.a;
        if (abstractC1254Oy != null) {
            return abstractC1254Oy.a(i);
        }
        return null;
    }

    public AbstractC1254Oy b(int i) {
        AbstractC1254Oy abstractC1254Oy = this.a;
        if (abstractC1254Oy != null) {
            return abstractC1254Oy.b(i);
        }
        return null;
    }

    public AbstractC1254Oy a(int i, String str) {
        AbstractC1254Oy abstractC1254Oy = this.a;
        if (abstractC1254Oy != null) {
            return abstractC1254Oy.a(i, str);
        }
        return null;
    }

    public /* synthetic */ AbstractC1254Oy(int i) {
        this((AbstractC1254Oy) null);
    }

    public InterfaceC1090Iy a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        AbstractC1254Oy abstractC1254Oy = this.a;
        if (abstractC1254Oy != null) {
            return abstractC1254Oy.a(c2576my);
        }
        return null;
    }

    public void a() {
        AbstractC1254Oy abstractC1254Oy = this.a;
        if (abstractC1254Oy != null) {
            abstractC1254Oy.a();
        }
    }
}
