package com.android.tools.r8.dex.code;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.B7;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C3089ts;
import com.android.tools.r8.internal.C3405y10;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.nio.ShortBuffer;
import okhttp3.internal.ws.WebSocketProtocol;
import org.slf4j.Marker;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class B1 implements InterfaceC0419c, com.android.tools.r8.utils.structural.x<B1> {
    public static final B1[] b = new B1[0];
    public static final int[] c = new int[0];
    static final /* synthetic */ boolean d = true;
    private int a;

    B1(InterfaceC0409a interfaceC0409a) {
        this.a = ((C1) interfaceC0409a).a() - 1;
    }

    protected static short d(int i, int i2) {
        return (short) (((i & 255) << 8) | (i2 & 255));
    }

    protected static int e(int i, int i2) {
        return ((i & 15) << 4) | (i2 & 15);
    }

    static int f(InterfaceC0409a interfaceC0409a) {
        return ((a(interfaceC0409a) << 16) & (-65536)) | (a(interfaceC0409a) & 65535);
    }

    static byte g(InterfaceC0409a interfaceC0409a) {
        return (byte) ((C1) interfaceC0409a).b();
    }

    public abstract String A();

    public int[] B() {
        return null;
    }

    public final boolean C() {
        return this.a >= 0;
    }

    public boolean D() {
        return false;
    }

    public boolean E() {
        return false;
    }

    public boolean F() {
        return false;
    }

    public boolean G() {
        return this instanceof C0447h2;
    }

    public boolean I() {
        return false;
    }

    public boolean K() {
        return this instanceof C0529y0;
    }

    public boolean L() {
        return !K() && (this instanceof R2);
    }

    public boolean M() {
        return false;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    abstract int a(B1 b1, com.android.tools.r8.utils.structural.a aVar);

    public abstract String a(com.android.tools.r8.utils.P2 p2);

    public void a(J5 j5) {
    }

    public abstract void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer);

    public abstract void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var);

    public abstract void a(C2102gl c2102gl);

    public P b() {
        return null;
    }

    public abstract String b(com.android.tools.r8.utils.P2 p2);

    abstract void b(com.android.tools.r8.utils.structural.o oVar);

    public C0410a0 c() {
        return null;
    }

    public C0430e0 d() {
        return null;
    }

    public C0435f0 e() {
        return null;
    }

    public final boolean equals(Object obj) {
        return com.android.tools.r8.utils.structural.k.a(this, obj);
    }

    public com.android.tools.r8.graph.Y0 getField() {
        return null;
    }

    public C0447h2 h() {
        return null;
    }

    public abstract int hashCode();

    public A1 i() {
        return null;
    }

    public Y1 j() {
        return null;
    }

    public Z1 k() {
        return null;
    }

    public boolean l() {
        return false;
    }

    @Override // com.android.tools.r8.dex.code.InterfaceC0419c
    public final B7 m() {
        return null;
    }

    @Override // com.android.tools.r8.dex.code.InterfaceC0419c
    public final B1 o() {
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        throw new C3405y10();
    }

    public C0721p0 q() {
        return null;
    }

    int r() {
        return v();
    }

    public C0684j2 s() {
        return null;
    }

    public abstract String t();

    public String toString() {
        return b(com.android.tools.r8.utils.P2.a());
    }

    public int u() {
        return this.a;
    }

    public abstract int v();

    public int x() {
        return 0;
    }

    public abstract int z();

    static long b(InterfaceC0409a interfaceC0409a) {
        return (a(interfaceC0409a) & WebSocketProtocol.PAYLOAD_SHORT_MAX) | ((((long) a(interfaceC0409a)) & WebSocketProtocol.PAYLOAD_SHORT_MAX) << 16);
    }

    static long c(InterfaceC0409a interfaceC0409a) {
        return b(interfaceC0409a) | (b(interfaceC0409a) << 32);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
        B1 b1 = (B1) xVar;
        int iA = aVar.a(r(), b1.r());
        if (iA != 0) {
            return iA;
        }
        int iA2 = aVar.a(u(), b1.u());
        return iA2 != 0 ? iA2 : a(b1, aVar);
    }

    public void g(int i) {
        this.a = i;
    }

    static short d(InterfaceC0409a interfaceC0409a) {
        return (short) ((C1) interfaceC0409a).b();
    }

    static short e(InterfaceC0409a interfaceC0409a) {
        return (short) ((C1) interfaceC0409a).c();
    }

    final String f(int i) {
        return com.android.tools.r8.utils.Y2.a(u() + i, 2) + " (" + c(i) + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return l();
    }

    protected B1() {
        this.a = -1;
    }

    static String c(int i) {
        return i >= 0 ? Marker.ANY_NON_NULL_MARKER + i : Integer.toString(i);
    }

    static String d(int i) {
        return com.android.tools.r8.utils.Y2.a(i, 2);
    }

    final String b(String str) {
        StringBuilder sb = new StringBuilder();
        com.android.tools.r8.utils.Y2.a(sb, com.android.tools.r8.utils.Y2.a(u(), 2), 6);
        sb.append(": ");
        com.android.tools.r8.utils.Y2.b(sb, t(), 20);
        if (str == null) {
            str = "";
        }
        sb.append(str);
        return sb.toString();
    }

    static char a(InterfaceC0409a interfaceC0409a) {
        return (char) (((C1) interfaceC0409a).c() & 65535);
    }

    protected static void a(int i, ShortBuffer shortBuffer, int i2) {
        shortBuffer.put((short) (((i & 255) << 8) | (i2 & 255)));
    }

    protected static void a(int i, int i2, ShortBuffer shortBuffer, int i3) {
        shortBuffer.put((short) (((i & 15) << 12) | ((i2 & 15) << 8) | (i3 & 255)));
    }

    protected static void a(long j, ShortBuffer shortBuffer) {
        shortBuffer.put((short) (j & WebSocketProtocol.PAYLOAD_SHORT_MAX));
        shortBuffer.put((short) ((j >> 16) & WebSocketProtocol.PAYLOAD_SHORT_MAX));
    }

    protected static void a(com.android.tools.r8.graph.I3 i3, ShortBuffer shortBuffer, C0645e5 c0645e5) {
        int iA = i3.a(c0645e5);
        if (!d && iA != (65535 & iA)) {
            throw new AssertionError();
        }
        shortBuffer.put((short) iA);
    }

    final String a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        if (str != null) {
            com.android.tools.r8.utils.Y2.b(sb, A(), 20);
            sb.append(str);
        } else {
            sb.append(A());
        }
        return sb.toString();
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(r());
        oVar.a(u());
        b(oVar);
    }

    public String a(B1 b1) {
        throw new C3089ts("Instruction " + b1 + " is not a payload user");
    }

    public String a(com.android.tools.r8.utils.P2 p2, B1 b1) {
        throw new C3089ts("Instruction " + b1 + " is not a payload user");
    }
}
