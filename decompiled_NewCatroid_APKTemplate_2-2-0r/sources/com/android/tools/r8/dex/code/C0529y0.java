package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.y0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0529y0 extends R2 {
    static final /* synthetic */ boolean h = true;
    public final int e;
    public final long f;
    public final short[] g;

    C0529y0(int i, InterfaceC0409a interfaceC0409a) {
        super(i, interfaceC0409a);
        char cA = B1.a(interfaceC0409a);
        this.e = cA;
        long jB = B1.b(interfaceC0409a);
        this.f = jB;
        if (!h && ((long) cA) * jB >= 2147483647L) {
            throw new AssertionError();
        }
        this.g = new short[((int) ((jB * ((long) cA)) + 1)) / 2];
        int i2 = 0;
        while (true) {
            short[] sArr = this.g;
            if (i2 >= sArr.length) {
                return;
            }
            sArr[i2] = B1.e(interfaceC0409a);
            i2++;
        }
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        int i = (this.e + 17036887) * 31;
        long j = this.f;
        return Arrays.hashCode(this.g) + ((i + ((int) (j ^ (j >>> 32)))) * 31);
    }

    @Override // com.android.tools.r8.dex.code.J, com.android.tools.r8.dex.code.B1
    public final int z() {
        return this.g.length + 4;
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new C0530y0$$ExternalSyntheticLambda0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.dex.code.y0$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((C0529y0) obj).e;
            }
        }).a(new ToLongFunction() { // from class: com.android.tools.r8.dex.code.y0$$ExternalSyntheticLambda2
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((C0529y0) obj).f;
            }
        }).i(new Function() { // from class: com.android.tools.r8.dex.code.y0$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0529y0) obj).g;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        return super.b(p2) + "[FillArrayPayload], width: " + this.e + ", size:  " + this.f;
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (C0529y0) b1, new C0530y0$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        B1.a(3, shortBuffer, 0);
        shortBuffer.put((short) this.e);
        B1.a(this.f, shortBuffer);
        for (short s : this.g) {
            shortBuffer.put(s);
        }
    }

    public C0529y0(int i, long j, short[] sArr) {
        this.e = i;
        this.f = j;
        this.g = sArr;
    }

    @Override // com.android.tools.r8.dex.code.R2, com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        int i;
        StringBuilder sb = new StringBuilder("    .array-data ");
        sb.append(com.android.tools.r8.utils.Y2.a(this.e, 1));
        sb.append("  # ");
        sb.append(this.e);
        sb.append("\n");
        int i2 = this.e;
        int i3 = 0;
        if (i2 == 1) {
            for (int i4 = 0; i4 < this.g.length; i4++) {
                for (int i5 = 0; i5 < 2; i5++) {
                    int i6 = (this.g[i4] >> (i5 * 8)) & 255;
                    if ((i4 * 2) + i5 < this.f) {
                        sb.append("      ");
                        sb.append(com.android.tools.r8.utils.Y2.a(i6, 2));
                        sb.append("  # ");
                        sb.append(i6);
                        sb.append("\n");
                    }
                }
            }
        } else {
            if (!h && i2 != 2 && i2 != 4 && i2 != 8) {
                throw new AssertionError();
            }
            loop2: while (true) {
                long j = 0;
                do {
                    short[] sArr = this.g;
                    if (i3 >= sArr.length) {
                        break loop2;
                    }
                    long unsignedLong = Short.toUnsignedLong(sArr[i3]);
                    i = this.e;
                    j |= unsignedLong << ((i3 % (i / 2)) * 16);
                    i3++;
                } while ((i3 * 2) % i != 0);
                sb.append("      ");
                sb.append(com.android.tools.r8.utils.Y2.a(j, this.e * 2));
                sb.append("  # ");
                sb.append(j);
                sb.append("\n");
            }
        }
        sb.append("    .end array-data");
        return sb.toString();
    }
}
