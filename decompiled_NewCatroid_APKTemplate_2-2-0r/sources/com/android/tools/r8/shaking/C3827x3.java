package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C2769pI;
import com.android.tools.r8.internal.InterfaceC1912eK;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.x3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3827x3 {
    private final com.android.tools.r8.internal.J0 a;

    /* JADX INFO: renamed from: com.android.tools.r8.shaking.x3$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private final C2769pI a;

        private a() {
            this.a = new C2769pI();
        }

        public a a(boolean z, C3821w3 c3821w3) {
            this.a.a(c3821w3, z);
            return this;
        }

        C3827x3 a() {
            return new C3827x3(this.a);
        }
    }

    private C3827x3(C2769pI c2769pI) {
        this.a = c2769pI;
    }

    public static a a() {
        return new a();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C3827x3)) {
            return false;
        }
        C3827x3 c3827x3 = (C3827x3) obj;
        if (((C2769pI) this.a).size() != ((C2769pI) c3827x3.a).size()) {
            return false;
        }
        InterfaceC1912eK it = this.a.k().iterator();
        InterfaceC1912eK it2 = c3827x3.a.k().iterator();
        while (it.hasNext()) {
            if (!((com.android.tools.r8.internal.I0) it.next()).equals((com.android.tools.r8.internal.I0) it2.next())) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        InterfaceC1912eK it = this.a.k().iterator();
        int iHashCode = 0;
        while (it.hasNext()) {
            com.android.tools.r8.internal.I0 i0 = (com.android.tools.r8.internal.I0) it.next();
            iHashCode = (iHashCode * (i0.getBooleanValue() ? 1 : 2) * 13) + ((C3821w3) i0.getKey()).hashCode();
        }
        return iHashCode;
    }

    public boolean a(C0767v2 c0767v2) {
        String strI0 = c0767v2.I0();
        InterfaceC1912eK it = this.a.k().iterator();
        while (it.hasNext()) {
            com.android.tools.r8.internal.I0 i0 = (com.android.tools.r8.internal.I0) it.next();
            if (((C3821w3) i0.getKey()).a(strI0)) {
                return !i0.getBooleanValue();
            }
        }
        return false;
    }
}
