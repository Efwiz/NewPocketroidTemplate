package com.android.tools.r8.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C2732or;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.EnumC1968f20;
import com.android.tools.r8.internal.InterfaceC0841Ar;
import com.android.tools.r8.internal.LQ;
import com.android.tools.r8.internal.WH;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.dex.code.c3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0423c3 extends B1 {
    private final int e;
    private final int[] f;
    private final com.android.tools.r8.graph.Y0[] g;

    public C0423c3(int i, int[] iArr, com.android.tools.r8.graph.Y0[] y0Arr) {
        this.e = i;
        this.f = iArr;
        this.g = y0Arr;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String A() {
        return "record-field-values*";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        for (com.android.tools.r8.graph.Y0 y0 : this.g) {
            y0.a(c0702m, l);
        }
    }

    @Override // com.android.tools.r8.dex.code.B1
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this, new c3$$ExternalSyntheticLambda1());
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int hashCode() {
        return (C0423c3.class.hashCode() * 31) + Arrays.hashCode(this.g);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final boolean l() {
        return true;
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int r() {
        return 258;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String t() {
        return "RecordFieldValues";
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int v() {
        throw new C3405y10("DexRecordFieldValues instructions should always be rewritten into NewArray");
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final int z() {
        return 2;
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String b(com.android.tools.r8.utils.P2 p2) {
        StringBuilder sb = new StringBuilder(RegisterSpec.PREFIX);
        sb.append(this.e).append(" { ");
        for (int i = 0; i < this.f.length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(RegisterSpec.PREFIX).append(this.f[i]);
        }
        sb.append(" }");
        return b(sb.toString());
    }

    @Override // com.android.tools.r8.dex.code.B1
    final int a(B1 b1, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (C0423c3) b1, new c3$$ExternalSyntheticLambda1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.d(new Function() { // from class: com.android.tools.r8.dex.code.c3$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0423c3) obj).g;
            }
        });
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final String a(com.android.tools.r8.utils.P2 p2) {
        return b(p2);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        throw new C3405y10("DexRecordFieldValues instructions should always be rewritten into NewArray");
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(J5 j5) {
        j5.a(this.g);
    }

    @Override // com.android.tools.r8.dex.code.B1
    public final void a(C2102gl c2102gl) {
        C2732or c2732or = new C2732or(16);
        int i = 0;
        while (true) {
            int[] iArr = this.f;
            if (i >= iArr.length) {
                break;
            }
            c2732or.a(iArr[i]);
            i++;
        }
        com.android.tools.r8.graph.Y0[] y0Arr = this.g;
        int i2 = this.e;
        c2102gl.getClass();
        ArrayList arrayList = new ArrayList();
        InterfaceC0841Ar interfaceC0841ArListIterator = c2732or.listIterator(0);
        while (interfaceC0841ArListIterator.hasNext()) {
            arrayList.add(c2102gl.b(Integer.valueOf(((com.android.tools.r8.internal.K) interfaceC0841ArListIterator).t()).intValue(), EnumC1968f20.OBJECT));
        }
        c2102gl.a(new LQ(y0Arr, c2102gl.a(i2, AbstractC3472z00.a(c2102gl.o.a().c2, WH.b(), (C0702m<?>) c2102gl.o), 2), arrayList));
    }
}
