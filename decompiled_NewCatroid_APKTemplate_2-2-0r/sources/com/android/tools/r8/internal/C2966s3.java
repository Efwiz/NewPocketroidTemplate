package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.InterfaceC3910i3;
import java.io.IOException;
import java.io.OutputStreamWriter;

/* JADX INFO: renamed from: com.android.tools.r8.internal.s3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2966s3 extends K3 implements InterfaceC2041g1 {
    private final C0767v2 a;

    C2966s3(C0767v2 c0767v2) {
        this.a = c0767v2;
    }

    public static C2895r3 d() {
        return new C2895r3(null);
    }

    @Override // com.android.tools.r8.internal.K3
    public final void a(InterfaceC3905h3 interfaceC3905h3, InterfaceC3905h3 interfaceC3905h4) throws Throwable {
        interfaceC3905h3.accept(this);
    }

    @Override // com.android.tools.r8.internal.K3
    public final AbstractC0744s2 c() {
        return this.a;
    }

    public final ClassReference e() {
        return Reference.classFromDescriptor(this.a.b1());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.a == ((C2966s3) obj).a;
    }

    public final C0767v2 f() {
        return this.a;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a.b1();
    }

    @Override // com.android.tools.r8.internal.K3
    public final Object a(InterfaceC3910i3 interfaceC3910i3, InterfaceC3910i3 interfaceC3910i4) {
        return interfaceC3910i3.apply(this);
    }

    @Override // com.android.tools.r8.internal.K3
    public final void a(OutputStreamWriter outputStreamWriter) throws IOException {
        outputStreamWriter.write(this.a.b1());
    }
}
