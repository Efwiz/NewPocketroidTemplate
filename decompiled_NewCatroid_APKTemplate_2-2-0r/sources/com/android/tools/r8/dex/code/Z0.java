package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class Z0 extends L {
    static final /* synthetic */ boolean l = true;
    public final byte e;
    public final byte f;
    public final byte g;
    public final byte h;
    public final byte i;
    public final byte j;
    public com.android.tools.r8.graph.I3 k;

    Z0(int i, InterfaceC0409a interfaceC0409a, com.android.tools.r8.graph.I3[] i3Arr) {
        super(interfaceC0409a);
        this.j = (byte) (i & 15);
        this.e = (byte) ((i >> 4) & 15);
        this.k = i3Arr[B1.a(interfaceC0409a)];
        short sD = B1.d(interfaceC0409a);
        this.h = (byte) (sD & 15);
        this.i = (byte) ((sD >> 4) & 15);
        short sD2 = B1.d(interfaceC0409a);
        this.f = (byte) (sD2 & 15);
        this.g = (byte) ((sD2 >> 4) & 15);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return (((((((this.k.hashCode() << 24) | (this.e << 20)) | (this.f << 16)) | (this.g << 12)) | (this.h << 8)) | (this.i << 4)) | this.j) ^ getClass().hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.Z0$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Z0) obj).e;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.Z0$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Z0) obj).f;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.Z0$$ExternalSyntheticLambda3
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Z0) obj).g;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.Z0$$ExternalSyntheticLambda4
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Z0) obj).h;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.Z0$$ExternalSyntheticLambda5
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Z0) obj).i;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.Z0$$ExternalSyntheticLambda6
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Z0) obj).j;
            }
        }).c(new Function() { // from class: com.android.tools.r8.dex.code.Z0$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Z0) obj).k;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new Z0$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        StringBuilder sb = new StringBuilder();
        a(sb, " ");
        sb.append(" ");
        sb.append(p2.a(this.k));
        return b(sb.toString());
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (Z0) b1, new Z0$$ExternalSyntheticLambda0());
    }

    private void a(StringBuilder sb, String str) {
        sb.append("{ ");
        int[] iArr = {this.f, this.g, this.h, this.i, this.j};
        for (int i = 0; i < this.e; i++) {
            if (i != 0) {
                sb.append(str);
            }
            sb.append(RegisterSpec.PREFIX).append(iArr[i]);
        }
        sb.append(" }");
    }

    Z0(int i, com.android.tools.r8.graph.I3 i3, int i2, int i4, int i5, int i6, int i7) {
        boolean z = l;
        if (!z && (i < 0 || i > 15)) {
            throw new AssertionError();
        }
        if (!z && (i2 < 0 || i2 > 15)) {
            throw new AssertionError();
        }
        if (!z && (i4 < 0 || i4 > 15)) {
            throw new AssertionError();
        }
        if (!z && (i5 < 0 || i5 > 15)) {
            throw new AssertionError();
        }
        if (!z && (i6 < 0 || i6 > 15)) {
            throw new AssertionError();
        }
        if (!z && (i7 < 0 || i7 > 15)) {
            throw new AssertionError();
        }
        this.e = (byte) i;
        this.k = i3;
        this.f = (byte) i2;
        this.g = (byte) i4;
        this.h = (byte) i5;
        this.i = (byte) i6;
        this.j = (byte) i7;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        StringBuilder sb = new StringBuilder();
        a(sb, ", ");
        sb.append(", ");
        sb.append(this.k.q0());
        return a(sb.toString());
    }
}
