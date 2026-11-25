package com.android.tools.r8.dex.code;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class J0 extends K {
    static final /* synthetic */ boolean f = true;
    public short e;

    J0(InterfaceC0409a interfaceC0409a) {
        super(interfaceC0409a);
        this.e = B1.e(interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a((int) this.e, (int) ((J0) b1).e);
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

    @Override // com.android.tools.r8.dex.code.B1
    public String b(com.android.tools.r8.utils.P2 p2) {
        short s = this.e;
        return b(((int) s) + " " + f(s));
    }

    protected J0(int i) {
        if (!f && (-32768 > i || i > 32767)) {
            throw new AssertionError();
        }
        this.e = (short) i;
    }
}
