package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.startup.StartupClassBuilder;
import com.android.tools.r8.utils.C3882d0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3293wW implements InterfaceC1965f1, StartupClassBuilder {
    static final /* synthetic */ boolean c = true;
    private final C0722p1 a;
    private C0767v2 b;

    C3293wW(C0722p1 c0722p1) {
        this.a = c0722p1;
    }

    public final C3293wW a(C0767v2 c0767v2) {
        this.b = c0767v2;
        return this;
    }

    @Override // com.android.tools.r8.startup.StartupClassBuilder
    public final StartupClassBuilder setClassReference(ClassReference classReference) {
        if (!c && this.a == null) {
            throw new AssertionError();
        }
        C0722p1 c0722p1 = this.a;
        int i = C3882d0.b;
        this.b = c0722p1.e(classReference.getDescriptor());
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1965f1
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final C3362xW build() {
        return new C3362xW(this.b);
    }
}
