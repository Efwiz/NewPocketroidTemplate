package com.android.tools.r8.graph;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.InterfaceC0841Ar;
import com.android.tools.r8.internal.InterfaceC0992Fq;
import com.android.tools.r8.utils.C3970u0;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class X implements com.android.tools.r8.utils.structural.x<X> {
    public static final /* synthetic */ int d = 0;
    public final C0759u2 a;
    public final C0767v2 b;
    public final C0759u2 c;

    public X(C0759u2 c0759u2, C0759u2 c0759u3, C0767v2 c0767v2) {
        this.a = c0759u2;
        this.b = c0767v2;
        this.c = c0759u3;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof X)) {
            return false;
        }
        X x = (X) obj;
        return this.a == x.a && this.b == x.b && this.c == x.c;
    }

    public final int hashCode() {
        int iHashCode = (this.b.hashCode() * 13) + (this.a.hashCode() * 7);
        C0759u2 c0759u2 = this.c;
        return c0759u2 != null ? iHashCode + (c0759u2.hashCode() * 31) : iHashCode;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.X$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                X.a(zVar);
            }
        };
    }

    public final String toString() {
        return c(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.graph.X$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((X) obj).a;
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.X$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((X) obj).b;
            }
        }).h(new Function() { // from class: com.android.tools.r8.graph.X$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((X) obj).c;
            }
        });
    }

    public final String c(int i) {
        int iB = AbstractC0403b.b(i);
        if (iB == 0) {
            return "";
        }
        if (iB == 1) {
            return this.a.toString();
        }
        if (iB != 2) {
            throw new C3405y10();
        }
        C0759u2 c0759u2 = this.a;
        C0759u2 c0759u3 = this.c;
        return c0759u2 + ":" + (c0759u3 == null ? this.b : C3970u0.b(c0759u3.toString()));
    }

    public static boolean a(InterfaceC0992Fq interfaceC0992Fq, InterfaceC0992Fq interfaceC0992Fq2) {
        if (interfaceC0992Fq == null) {
            return interfaceC0992Fq2 == null;
        }
        if (interfaceC0992Fq2 == null || interfaceC0992Fq.keySet().size() != interfaceC0992Fq2.keySet().size()) {
            return false;
        }
        InterfaceC0841Ar it = interfaceC0992Fq.keySet().iterator();
        while (it.hasNext()) {
            int iIntValue = ((Integer) it.next()).intValue();
            if (!((X) interfaceC0992Fq.get(iIntValue)).equals(interfaceC0992Fq2.get(iIntValue))) {
                return false;
            }
        }
        return true;
    }
}
