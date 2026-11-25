package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3510zW extends CW implements InterfaceC2197i1 {
    private final C0684j2 a;

    C3510zW(C0684j2 c0684j2) {
        this.a = c0684j2;
    }

    public static C3440yW d() {
        return new C3440yW(null);
    }

    @Override // com.android.tools.r8.internal.CW
    public final void a(InterfaceC3905h3 interfaceC3905h3, InterfaceC3905h3 interfaceC3905h4) throws Throwable {
        interfaceC3905h4.accept(this);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.a == ((C3510zW) obj).a;
    }

    @Override // com.android.tools.r8.internal.CW
    /* JADX INFO: renamed from: getReference, reason: merged with bridge method [inline-methods] */
    public C0684j2 c() {
        return this.a;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.android.tools.r8.internal.CW
    public final void a(Appendable appendable) throws IOException {
        appendable.append(this.a.q0());
    }
}
