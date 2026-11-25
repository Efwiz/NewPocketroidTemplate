package com.android.tools.r8.dex.code;

import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class L0 extends K {
    static final /* synthetic */ boolean g = true;
    public final short e;
    public final char f;

    L0(int i, InterfaceC0409a interfaceC0409a) {
        super(interfaceC0409a);
        this.e = (short) i;
        this.f = B1.a(interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return ((this.f << '\b') | this.e) ^ getClass().hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.L0$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((L0) obj).e;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.L0$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((L0) obj).f;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new L0$$ExternalSyntheticLambda2());
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (L0) b1, new L0$$ExternalSyntheticLambda2());
    }

    L0(int i, int i2) {
        boolean z = g;
        if (!z && (i < 0 || i > 255)) {
            throw new AssertionError();
        }
        if (!z && (i2 < 0 || i2 > 65535)) {
            throw new AssertionError();
        }
        this.e = (short) i;
        this.f = (char) i2;
    }
}
