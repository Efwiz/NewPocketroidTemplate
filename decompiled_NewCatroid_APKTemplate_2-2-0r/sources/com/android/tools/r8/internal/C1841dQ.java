package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.utils.AbstractC3918j3;
import com.android.tools.r8.utils.InterfaceC3900g3;
import java.io.UTFDataFormatException;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1841dQ extends AbstractC3918j3 {
    private final InterfaceC3900g3 a;

    C1841dQ(C0759u2 c0759u2) {
        this.a = c0759u2.A0();
    }

    @Override // com.android.tools.r8.utils.AbstractC3918j3
    public final boolean a() {
        return this.a.hasNext();
    }

    @Override // com.android.tools.r8.utils.AbstractC3918j3
    public final int b() throws C2000fQ {
        if (!this.a.hasNext()) {
            throw new NoSuchElementException();
        }
        int i = 0;
        int i2 = 0;
        do {
            try {
                char cA = this.a.a();
                if (cA >= 55296 && cA < 57344) {
                    throw new C2000fQ();
                }
                if (cA < 55296) {
                    return i | (cA << i2);
                }
                i |= (cA & 8191) << i2;
                i2 += 13;
            } catch (UTFDataFormatException unused) {
                throw new C2000fQ();
            }
        } while (this.a.hasNext());
        throw new C2000fQ();
    }
}
