package com.android.tools.r8.internal;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Uy, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1439Uy extends AbstractC1520Xy {
    public EnumC1494Wy b;
    public EnumC1465Vy c;
    private Integer d;
    private String e;
    public C1392Ty f;

    public final void a(AbstractC1520Xy abstractC1520Xy) {
        EnumC1494Wy enumC1494Wy = this.b;
        if (enumC1494Wy == null) {
            AbstractC0936Ds.a(Namer.METADATA_CLASS_KIND);
            throw null;
        }
        EnumC1465Vy enumC1465Vy = this.c;
        if (enumC1465Vy == null) {
            AbstractC0936Ds.a(FirebaseAnalytics.Param.LEVEL);
            throw null;
        }
        abstractC1520Xy.a(enumC1494Wy, enumC1465Vy, this.d, this.e);
        C1392Ty c1392Ty = this.f;
        if (c1392Ty == null) {
            AbstractC0936Ds.a("version");
            throw null;
        }
        int iA = c1392Ty.a();
        C1392Ty c1392Ty2 = this.f;
        if (c1392Ty2 == null) {
            AbstractC0936Ds.a("version");
            throw null;
        }
        int iB = c1392Ty2.b();
        C1392Ty c1392Ty3 = this.f;
        if (c1392Ty3 == null) {
            AbstractC0936Ds.a("version");
            throw null;
        }
        abstractC1520Xy.a(iA, iB, c1392Ty3.c());
        abstractC1520Xy.a();
    }

    public final Integer b() {
        return this.d;
    }

    public final String c() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC1520Xy
    public final void a(EnumC1494Wy enumC1494Wy, EnumC1465Vy enumC1465Vy, Integer num, String str) {
        this.b = enumC1494Wy;
        this.c = enumC1465Vy;
        this.d = num;
        this.e = str;
    }

    @Override // com.android.tools.r8.internal.AbstractC1520Xy
    public final void a(int i, int i2, int i3) {
        this.f = new C1392Ty(i, i2, i3);
    }
}
