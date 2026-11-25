package com.android.tools.r8.dex.code;

import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.a1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0411a1 extends L {
    static final /* synthetic */ boolean h = true;
    public final short e;
    public final char f;
    public com.android.tools.r8.graph.I3 g;

    AbstractC0411a1(int i, InterfaceC0409a interfaceC0409a, com.android.tools.r8.graph.I3[] i3Arr) {
        super(interfaceC0409a);
        this.e = (short) i;
        this.g = i3Arr[B1.a(interfaceC0409a)];
        this.f = B1.a(interfaceC0409a);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return (((this.f << 24) | (this.g.hashCode() << 4)) | this.e) ^ getClass().hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.a1$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((AbstractC0411a1) obj).e;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.a1$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((AbstractC0411a1) obj).f;
            }
        }).c(new Function() { // from class: com.android.tools.r8.dex.code.a1$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC0411a1) obj).g;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new a1$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (AbstractC0411a1) b1, new a1$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        StringBuilder sb = new StringBuilder("{ v");
        char c = this.f;
        sb.append((int) c);
        if (this.e != 1) {
            sb.append(" .. v").append((c + this.e) - 1);
        }
        sb.append(" } ");
        sb.append(p2.a(this.g));
        return b(sb.toString());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        StringBuilder sb = new StringBuilder("{ v");
        char c = this.f;
        sb.append((int) c);
        if (this.e != 1) {
            sb.append(" .. v").append((c + this.e) - 1);
        }
        sb.append(" }, ");
        sb.append(this.g.q0());
        return a(sb.toString());
    }

    AbstractC0411a1(int i, int i2, com.android.tools.r8.graph.I3 i3) {
        boolean z = h;
        if (!z && (i < 0 || i > 65535)) {
            throw new AssertionError();
        }
        if (!z && (i2 < 0 || i2 > 255)) {
            throw new AssertionError();
        }
        this.f = (char) i;
        this.e = (short) i2;
        this.g = i3;
    }
}
