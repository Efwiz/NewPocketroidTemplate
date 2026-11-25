package com.android.tools.r8.internal;

import java.util.ArrayList;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fy, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2036fy extends AbstractC2425ky {
    private EnumC2344jy b;
    private EnumC2269iy c;
    private final ArrayList d;
    private C2120gy e;

    public C2036fy(EnumC2344jy enumC2344jy, EnumC2269iy enumC2269iy) {
        AbstractC0936Ds.c(enumC2344jy, ModuleXmlParser.TYPE);
        this.b = enumC2344jy;
        this.c = enumC2269iy;
        this.d = new ArrayList(1);
    }

    public final void a(C2120gy c2120gy) {
        this.e = c2120gy;
    }

    @Override // com.android.tools.r8.internal.AbstractC2425ky
    public final AbstractC2191hy b() {
        return (AbstractC2191hy) AbstractC2909rH.a(new C2120gy(), this.d);
    }

    public final C2120gy d() {
        return this.e;
    }

    public final ArrayList e() {
        return this.d;
    }

    public final EnumC2269iy f() {
        return this.c;
    }

    public final EnumC2344jy g() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC2425ky
    public final AbstractC2191hy a() {
        C2120gy c2120gy = new C2120gy();
        this.e = c2120gy;
        return c2120gy;
    }

    public final void a(AbstractC2425ky abstractC2425ky) {
        AbstractC2191hy abstractC2191hyA;
        for (C2120gy c2120gy : this.d) {
            AbstractC2191hy abstractC2191hyB = abstractC2425ky.b();
            if (abstractC2191hyB != null) {
                c2120gy.a(abstractC2191hyB);
            }
        }
        C2120gy c2120gy2 = this.e;
        if (c2120gy2 != null && (abstractC2191hyA = abstractC2425ky.a()) != null) {
            c2120gy2.a(abstractC2191hyA);
        }
        abstractC2425ky.c();
    }
}
