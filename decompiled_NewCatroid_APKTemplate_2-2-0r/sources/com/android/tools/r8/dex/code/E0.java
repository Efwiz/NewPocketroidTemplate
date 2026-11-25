package com.android.tools.r8.dex.code;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class E0 extends J {
    static final /* synthetic */ boolean f = true;
    public byte e;

    E0(int i, InterfaceC0409a interfaceC0409a) {
        super(interfaceC0409a);
        this.e = (byte) i;
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a((int) this.e, (int) ((E0) b1).e);
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a((int) this.e);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return this.e ^ getClass().hashCode();
    }

    @Override // com.android.tools.r8.dex.code.B1
    public String a(com.android.tools.r8.utils.P2 p2) {
        return a(":label_" + (u() + this.e));
    }

    protected E0(int i) {
        if (!f && (-128 > i || i > 127)) {
            throw new AssertionError();
        }
        this.e = (byte) i;
    }
}
