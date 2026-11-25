package com.android.tools.r8.internal;

import io.netty.util.internal.StringUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.y9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3415y9 extends ME {
    final /* synthetic */ AbstractC1899eF F;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C3415y9(int i, int i2, AbstractC1899eF abstractC1899eF, String str, String str2) {
        super(i, i2, str, str2, null);
        this.F = abstractC1899eF;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.tools.r8.internal.V1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.tools.r8.internal.V1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.tools.r8.internal.eF] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.android.tools.r8.internal.ME, com.android.tools.r8.internal.y9] */
    @Override // com.android.tools.r8.internal.ME, com.android.tools.r8.internal.AbstractC1899eF
    public final void c() {
        boolean z;
        int i = this.A;
        int i2 = this.z;
        AbstractC1899eF abstractC1899eF = this.F;
        boolean z2 = false;
        if (abstractC1899eF instanceof A9) {
            A9 a9 = (A9) abstractC1899eF;
            z = !a9.e();
            if (a9.f() && !a9.d()) {
                z2 = true;
            }
        } else {
            z = false;
        }
        ?? c3337x9 = z2 ? new C3337x9(new F4()) : new V1(new F4());
        try {
            if (z) {
                c3337x9.a(this);
            } else {
                c3337x9.b(this);
            }
            c3337x9 = this.F;
            if (c3337x9 != 0) {
                this.A = i;
                this.z = i2;
                a(c3337x9);
            }
        } catch (W1 | IndexOutOfBoundsException e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter((Writer) stringWriter, true);
            AbstractC3187v9.a((ME) this, (V1) c3337x9, printWriter);
            printWriter.close();
            throw new IllegalArgumentException(e.getMessage() + StringUtil.SPACE + stringWriter.toString(), e);
        }
    }
}
