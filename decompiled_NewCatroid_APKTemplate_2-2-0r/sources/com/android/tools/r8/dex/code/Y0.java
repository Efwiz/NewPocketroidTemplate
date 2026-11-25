package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class Y0 extends L {
    static final /* synthetic */ boolean g = true;
    public final int e;
    public final int f;

    Y0(InterfaceC0409a interfaceC0409a) {
        super(interfaceC0409a);
        this.e = B1.a(interfaceC0409a);
        this.f = B1.a(interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return ((this.e << 16) | this.f) ^ getClass().hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.Y0$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Y0) obj).e;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.Y0$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Y0) obj).f;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new Y0$$ExternalSyntheticLambda2());
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (Y0) b1, new Y0$$ExternalSyntheticLambda2());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public String b(com.android.tools.r8.utils.P2 p2) {
        return b(RegisterSpec.PREFIX + this.e + ", v" + this.f);
    }

    Y0(int i, int i2) {
        boolean z = g;
        if (!z && (i < 0 || i > 65535)) {
            throw new AssertionError();
        }
        if (!z && (i2 < 0 || i2 > 65535)) {
            throw new AssertionError();
        }
        this.e = i;
        this.f = i2;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public String a(com.android.tools.r8.utils.P2 p2) {
        return a(RegisterSpec.PREFIX + this.e + ", v" + this.f);
    }
}
