package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC2753p4;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C1944ek;
import com.android.tools.r8.internal.C3405y10;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

/* JADX INFO: renamed from: com.android.tools.r8.graph.p0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0721p0 extends I3 implements com.android.tools.r8.utils.structural.x {
    static final /* synthetic */ boolean k = true;
    public final C0759u2 d;
    public final C0737r2 e;
    public final C0699l2 f;
    public final List g;
    private C0684j2 i;
    private S0 h = null;
    private int j = -1;

    C0721p0(C0759u2 c0759u2, C0737r2 c0737r2, C0699l2 c0699l2, List list) {
        boolean z = k;
        if (!z && c0759u2 == null) {
            throw new AssertionError();
        }
        if (!z && c0737r2 == null) {
            throw new AssertionError();
        }
        if (!z && c0699l2 == null) {
            throw new AssertionError();
        }
        if (!z && list == null) {
            throw new AssertionError();
        }
        this.d = c0759u2;
        this.e = c0737r2;
        this.f = c0699l2;
        this.g = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.h(new Function() { // from class: com.android.tools.r8.graph.p0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0721p0) obj).i;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.graph.p0$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((C0721p0) obj).j;
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.p0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0721p0) obj).d;
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.p0$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0721p0) obj).e;
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.p0$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0721p0) obj).f;
            }
        }).f(new Function() { // from class: com.android.tools.r8.graph.p0$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0721p0) obj).g;
            }
        });
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final boolean c(Object obj) {
        return false;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.p0$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C0721p0.a(zVar);
            }
        };
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final String q0() {
        return toString();
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final int s0() {
        return System.identityHashCode(this);
    }

    public final S0 t0() {
        if (this.h == null) {
            int i = 3;
            B2[] b2Arr = new B2[this.g.size() + 3];
            b2Arr[0] = new G2(this.f);
            b2Arr[1] = new B2.j(this.d);
            b2Arr[2] = new H2(this.e);
            Iterator it = this.g.iterator();
            while (it.hasNext()) {
                b2Arr[i] = (B2) it.next();
                i++;
            }
            this.h = new S0(b2Arr);
        }
        return this.h;
    }

    public final String toString() {
        StringBuilder sbAppend = new StringBuilder("CallSite: { Name: ").append(this.d.r0()).append(", Proto: ").append(this.e.r0()).append(", ").append(this.f.r0());
        Iterator it = this.g.iterator();
        String str = ", Args: ";
        while (it.hasNext()) {
            sbAppend.append(str).append(((AbstractC0594a1) it.next()).r0());
            str = ", ";
        }
        sbAppend.append('}');
        return sbAppend.toString();
    }

    public final String u0() {
        C0715o0 c0715o0 = new C0715o0(this);
        try {
            c0715o0.a = new ByteArrayOutputStream();
            c0715o0.b = new ObjectOutputStream(c0715o0.a);
            c0715o0.a(this.d);
            c0715o0.a(this.e);
            C0699l2 c0699l2 = this.f;
            c0715o0.b.writeShort(c0699l2.d.a());
            if (c0699l2.d.b()) {
                Y0 y0T0 = c0699l2.t0();
                c0715o0.a(y0T0.e.e);
                c0715o0.a(y0T0.h.e);
                c0715o0.a(y0T0.f);
            } else {
                C0684j2 c0684j2U0 = c0699l2.u0();
                c0715o0.a(c0684j2U0.e.e);
                c0715o0.a(c0684j2U0.h);
                c0715o0.a(c0684j2U0.f);
            }
            c0715o0.a(this.g);
            c0715o0.b.close();
            MessageDigest messageDigest = MessageDigest.getInstance(McElieceCCA2KeyGenParameterSpec.SHA1);
            messageDigest.update(c0715o0.a.toByteArray());
            return AbstractC2753p4.a().b().a(messageDigest.digest());
        } catch (IOException | NoSuchAlgorithmException unused) {
            throw new C3405y10("Cannot get SHA-1 message digest");
        }
    }

    public static C0721p0 a(M3 m3, C0767v2 c0767v2, String str, String str2, C1944ek c1944ek, Object[] objArr) {
        if (c1944ek.d() != 6 && c1944ek.d() != 8) {
            throw new C1367Tb("Bootstrap handle invalid: tag == " + c1944ek.d());
        }
        C0699l2 c0699l2A = C0699l2.a(c1944ek, m3, c0767v2);
        ArrayList arrayList = new ArrayList();
        for (Object obj : objArr) {
            arrayList.add(B2.a(obj, m3, c0767v2));
        }
        return m3.a(str, str2, c0699l2A, arrayList);
    }

    public final void a(int i, C0684j2 c0684j2) {
        boolean z = k;
        if (!z && c0684j2 == null) {
            throw new AssertionError();
        }
        if (!z && i < 0) {
            throw new AssertionError();
        }
        if (!z && this.i != null) {
            throw new AssertionError();
        }
        if (!z && this.j != -1) {
            throw new AssertionError();
        }
        this.i = c0684j2;
        this.j = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.I3, com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        S0 s0T0 = t0();
        s0T0.getClass();
        v.a(s0T0);
    }

    @Override // com.android.tools.r8.graph.I3
    public final int a(C0645e5 c0645e5) {
        return c0645e5.a(this);
    }
}
