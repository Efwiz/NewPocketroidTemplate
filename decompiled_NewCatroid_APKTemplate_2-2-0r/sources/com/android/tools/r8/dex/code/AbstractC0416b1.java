package com.android.tools.r8.dex.code;

import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.b1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC0416b1 extends N {
    static final /* synthetic */ boolean g = true;
    public final short e;
    public final long f;

    AbstractC0416b1(int i, InterfaceC0409a interfaceC0409a) {
        super(interfaceC0409a);
        this.e = (short) i;
        this.f = B1.c(interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return ((((int) this.f) << 8) | this.e) ^ getClass().hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.b1$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((AbstractC0416b1) obj).e;
            }
        }).a(new ToLongFunction() { // from class: com.android.tools.r8.dex.code.b1$$ExternalSyntheticLambda1
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((AbstractC0416b1) obj).f;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new b1$$ExternalSyntheticLambda2());
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (AbstractC0416b1) b1, new b1$$ExternalSyntheticLambda2());
    }

    public AbstractC0416b1(int i, long j) {
        if (!g && (i < 0 || i > 255)) {
            throw new AssertionError();
        }
        this.e = (short) i;
        this.f = j;
    }
}
