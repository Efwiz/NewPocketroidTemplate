package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2573mw extends AbstractC2509m2 {
    final /* synthetic */ C2651nw.a b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C2573mw(int i, C2651nw.a aVar) {
        super(i);
        this.b = aVar;
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, Object obj) {
        this.b.a(str, obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, String str2, String str3) {
        this.b.a(str, str2, str3);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str, String str2) {
        C2651nw.a aVarA = this.b.a(str, str2);
        if (aVarA == this.b) {
            return this;
        }
        boolean z = C2651nw.h;
        if (aVarA == null) {
            return null;
        }
        return new C2573mw(aVarA.b(), aVarA);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str) {
        C2651nw.a aVarA = this.b.a(str);
        if (aVarA == this.b) {
            return this;
        }
        boolean z = C2651nw.h;
        if (aVarA == null) {
            return null;
        }
        return new C2573mw(aVarA.b(), aVarA);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        this.b.a();
    }
}
