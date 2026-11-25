package com.android.tools.r8.internal;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sy, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3025sy {
    private final AbstractC3025sy a;

    public AbstractC3025sy() {
        this(0);
    }

    public AbstractC1200My a(int i, String str, int i2, EnumC1364Sy enumC1364Sy) {
        AbstractC0936Ds.c(str, "name");
        AbstractC0936Ds.c(enumC1364Sy, "variance");
        AbstractC3025sy abstractC3025sy = this.a;
        if (abstractC3025sy != null) {
            return abstractC3025sy.a(i, str, i2, enumC1364Sy);
        }
        return null;
    }

    public AbstractC1254Oy b(int i) {
        AbstractC3025sy abstractC3025sy = this.a;
        if (abstractC3025sy != null) {
            return abstractC3025sy.b(i);
        }
        return null;
    }

    public AbstractC1254Oy c(int i) {
        AbstractC3025sy abstractC3025sy = this.a;
        if (abstractC3025sy != null) {
            return abstractC3025sy.c(i);
        }
        return null;
    }

    public AbstractC3025sy(AbstractC3025sy abstractC3025sy) {
        this.a = abstractC3025sy;
    }

    public AbstractC1254Oy a(int i) {
        AbstractC3025sy abstractC3025sy = this.a;
        if (abstractC3025sy != null) {
            return abstractC3025sy.a(i);
        }
        return null;
    }

    public void b() {
        AbstractC3025sy abstractC3025sy = this.a;
        if (abstractC3025sy != null) {
            abstractC3025sy.b();
        }
    }

    public AbstractC1520Xy c() {
        AbstractC3025sy abstractC3025sy = this.a;
        if (abstractC3025sy != null) {
            return abstractC3025sy.c();
        }
        return null;
    }

    public AbstractC1338Ry a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        AbstractC3025sy abstractC3025sy = this.a;
        if (abstractC3025sy != null) {
            return abstractC3025sy.a(i, str);
        }
        return null;
    }

    public AbstractC1804cy a() {
        AbstractC3025sy abstractC3025sy = this.a;
        if (abstractC3025sy != null) {
            return abstractC3025sy.a();
        }
        return null;
    }

    public /* synthetic */ AbstractC3025sy(int i) {
        this((AbstractC3025sy) null);
    }

    public InterfaceC2956ry a(C2576my c2576my) {
        AbstractC0936Ds.c(c2576my, ModuleXmlParser.TYPE);
        AbstractC3025sy abstractC3025sy = this.a;
        if (abstractC3025sy != null) {
            return abstractC3025sy.a(c2576my);
        }
        return null;
    }
}
