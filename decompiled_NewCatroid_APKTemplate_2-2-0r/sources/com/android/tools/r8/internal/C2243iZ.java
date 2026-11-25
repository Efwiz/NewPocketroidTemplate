package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2243iZ extends AbstractC2509m2 {
    private final ZM b;

    public C2243iZ(AbstractC2509m2 abstractC2509m2, ZY zy) {
        super(589824, abstractC2509m2);
        this.b = zy;
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, Object obj) {
        this.b.a(str, obj);
        super.a(str, obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, String str2, String str3) {
        this.b.a(str, str2, str3);
        super.a(str, str2, str3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str, String str2) {
        return new C2243iZ(super.a(str, str2), this.b.a(str, str2));
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str) {
        return new C2243iZ(super.a(str), this.b.a(str));
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        this.b.getClass();
        super.a();
    }
}
