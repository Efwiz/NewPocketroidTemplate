package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.I3;
import java.util.function.ToIntFunction;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class K0<T extends com.android.tools.r8.graph.I3> extends K {
    static final /* synthetic */ boolean g = true;
    public final short e;
    public T f;

    K0(int i, InterfaceC0409a interfaceC0409a, com.android.tools.r8.graph.I3[] i3Arr) {
        super(interfaceC0409a);
        this.e = (short) i;
        this.f = (T) i3Arr[B1.a(interfaceC0409a)];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.K0$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((K0) obj).e;
            }
        }).a((com.android.tools.r8.utils.structural.y) new K0$$ExternalSyntheticLambda1(this));
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (K0) b1, (com.android.tools.r8.utils.structural.y<K0>) new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.dex.code.K0$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                this.f$0.b(zVar);
            }
        });
    }

    abstract void a(com.android.tools.r8.utils.structural.z zVar);

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return ((this.f.hashCode() << 8) | this.e) ^ getClass().hashCode();
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a((int) this.e);
        oVar.a(this, new K0$$ExternalSyntheticLambda1(this));
    }

    @Override // com.android.tools.r8.dex.code.B1
    public String a(com.android.tools.r8.utils.P2 p2) {
        return a(RegisterSpec.PREFIX + ((int) this.e) + ", " + this.f.q0());
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected K0(int i, com.android.tools.r8.graph.I3 i3) {
        if (!g && (i < 0 || i > 255)) {
            throw new AssertionError();
        }
        this.e = (short) i;
        this.f = i3;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public String b(com.android.tools.r8.utils.P2 p2) {
        return b(RegisterSpec.PREFIX + ((int) this.e) + ", " + p2.a(this.f));
    }
}
