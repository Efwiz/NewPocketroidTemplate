package com.android.tools.r8.graph;

import androidx.exifinterface.media.ExifInterface;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC2619nY;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.BS;
import com.android.tools.r8.internal.C1229Oa;
import com.android.tools.r8.internal.C1303Qq;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.C1513Xr;
import com.android.tools.r8.internal.C1946em;
import com.android.tools.r8.internal.C1969f3;
import com.android.tools.r8.internal.C2037fz;
import com.android.tools.r8.internal.C3011sk;
import com.android.tools.r8.internal.C3017sq;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.WH;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.TypeReference;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.AbstractC3962s0;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.C3970u0;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.codegen.AssertCodegenUtilKt;
import org.jetbrains.kotlin.codegen.ImplementationBodyCodegen;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: renamed from: com.android.tools.r8.graph.p1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0722p1 {
    public static final C0767v2 S5;
    public static final C0759u2 T5;
    private static final IdentityHashMap U5;
    private static final AbstractC1639am V5;
    private static final AbstractC1639am W5;
    private static final AbstractC1639am X5;
    static final /* synthetic */ boolean Y5 = true;
    public final C0759u2 A;
    public final C0759u2 A0;
    public final C0767v2 A1;
    public final C0767v2 A2;
    public final C0767v2 A3;
    public final T1 A4;
    public final C0767v2 A5;
    public final C0759u2 B;
    public final C0759u2 B0;
    public final C0767v2 B1;
    public final C0767v2 B2;
    public final C0767v2 B3;
    public final V1 B4;
    public final C0767v2 B5;
    public final C0759u2 C;
    public final C0759u2 C0;
    public final C0767v2 C1;
    public final C0767v2 C2;
    public final C0767v2 C3;
    public final C0666h1 C4;
    public final R1 C5;
    public final C0759u2 D;
    public final C0759u2 D0;
    public final C0767v2 D1;
    public final C0767v2 D2;
    public final C0767v2 D3;
    public final C0698l1 D4;
    public final X1 D5;
    public final C0759u2 E;
    public final C0759u2 E0;
    public final C0767v2 E1;
    public final C0767v2 E2;
    public final C0767v2 E3;
    public final C0627c1 E4;
    public final C0595a2 E5;
    public final C0759u2 F;
    public final C0759u2 F0;
    public final C0767v2 F1;
    public final C0767v2 F2;
    public final C0767v2 F3;
    public final C0609b1 F4;
    private final com.android.tools.r8.synthesis.L F5;
    public final C0759u2 G;
    public final C0759u2 G0;
    public final C0767v2 G1;
    public final C0767v2 G2;
    public final C0767v2 G3;
    public final C0634d1 G4;
    public final C3011sk G5;
    public final C0759u2 H;
    public final C0759u2 H0;
    public final C0767v2 H1;
    public final C0767v2 H2;
    public final C0767v2 H3;
    public final C0641e1 H4;
    public final AbstractC2178hm H5;
    public final C0759u2 I;
    public final C0759u2 I0;
    public final C0767v2 I1;
    public final C0767v2 I2;
    public final C0767v2 I3;
    public final C0652f1 I4;
    public final C0684j2 I5;
    public final C0759u2 J;
    public final C0759u2 J0;
    public final C0767v2 J1;
    public final C0767v2 J2;
    public final C0767v2 J3;
    public final J1 J4;
    public final C0684j2 J5;
    public final C0759u2 K;
    public final C0759u2 K0;
    public final C0767v2 K1;
    public final C0767v2 K2;
    public final C0767v2 K3;
    public final AbstractC1639am K4;
    public final C0684j2 K5;
    public final C0759u2 L;
    public final C0759u2 L0;
    public final C0767v2 L1;
    public final C0767v2 L2;
    public final C0767v2 L3;
    public final C0759u2 L4;
    public AbstractC2178hm L5;
    public final C0759u2 M;
    public final C0759u2 M0;
    public final C0767v2 M1;
    public final C0767v2 M2;
    public final C0767v2 M3;
    public final C0737r2 M4;
    public Set<C0684j2> M5;
    public final C0759u2 N;
    public final C0759u2 N0;
    public final C0767v2 N1;
    public final C0767v2 N2;
    public final C0767v2 N3;
    public final C0759u2 N4;
    public final AbstractC1048Hm N5;
    public final C0759u2 O;
    public final C0759u2 O0;
    public final C0767v2 O1;
    public final C0767v2 O2;
    public final C0767v2 O3;
    public final C0767v2 O4;
    public AbstractC1048Hm O5;
    public final C0759u2 P;
    public final C0759u2 P0;
    public final C0767v2 P1;
    public final C0767v2 P2;
    public final C0767v2 P3;
    public final C0737r2 P4;
    public Set<C0767v2> P5;
    public final C0759u2 Q;
    public final C0759u2 Q0;
    public final C0767v2 Q1;
    public final C0767v2 Q2;
    public final C0767v2 Q3;
    public final C0767v2 Q4;
    public AbstractC1048Hm Q5;
    public final C0759u2 R;
    public final C0759u2 R0;
    public final C0767v2 R1;
    public final C0767v2 R2;
    public final C0767v2 R3;
    public final C0767v2 R4;
    private boolean R5;
    public final C0759u2 S;
    public final C0759u2 S0;
    public final C0767v2 S1;
    public final C0767v2 S2;
    public final C0767v2 S3;
    public final C0767v2 S4;
    public final C0759u2 T;
    public final C0759u2 T0;
    public final C0767v2 T1;
    public final C0767v2 T2;
    public final C0767v2 T3;
    public final C0767v2 T4;
    public final C0759u2 U;
    public final C0759u2 U0;
    public final C0767v2 U1;
    public final C0767v2 U2;
    public final C0767v2 U3;
    public final C0767v2 U4;
    public final C0759u2 V;
    public final C0759u2 V0;
    public final C0767v2 V1;
    public final C0767v2 V2;
    public final C0767v2 V3;
    public final C0767v2 V4;
    public final C0759u2 W;
    public final C0759u2 W0;
    public final C0767v2 W1;
    public final C0767v2 W2;
    public final Z1 W3;
    public final C0767v2 W4;
    public final C0759u2 X;
    public final C0759u2 X0;
    public final C0767v2 X1;
    public final C0767v2 X2;
    public final Z1 X3;
    public final C0767v2 X4;
    public final C0759u2 Y;
    public final C0759u2 Y0;
    public final C0767v2 Y1;
    public final C0767v2 Y2;
    public final a Y3;
    public final C0767v2 Y4;
    public final C0759u2 Z;
    public final C0759u2 Z0;
    public final C0767v2 Z1;
    public final C0767v2 Z2;
    public final C0742s1 Z3;
    public final C0767v2 Z4;
    public final C0759u2 a0;
    public final C0759u2 a1;
    public final C0767v2 a2;
    public final C0767v2 a3;
    public final C0749t1 a4;
    public final C0767v2 a5;
    public final C0759u2 b0;
    public final C0759u2 b1;
    public final C0767v2 b2;
    public final C0767v2 b3;
    public final C0807y1 b4;
    public final C0759u2 b5;
    public final C0759u2 c0;
    public final C0759u2 c1;
    public final C0767v2 c2;
    public final C0767v2 c3;
    public final B1 c4;
    public final C0759u2 c5;
    public final C0759u2 d0;
    public final C0759u2 d1;
    public final C0767v2 d2;
    public final C0767v2 d3;
    public final P1 d4;
    public final C0759u2 d5;
    public final C0759u2 e0;
    public final C0759u2 e1;
    public final C0767v2 e2;
    public final AbstractC1639am e3;
    public final C0628c2 e4;
    public final C0759u2 e5;
    public final C0759u2 f0;
    public final C0759u2 f1;
    public final C0767v2 f2;
    public AbstractC1639am f3;
    public final S1 f4;
    public final C0759u2 f5;
    public final C0759u2 g0;
    public final C0759u2 g1;
    public final C0767v2 g2;
    public final C0767v2 g3;
    public final b g4;
    public final C0767v2 g5;
    public final C0759u2 h0;
    public final C0759u2 h1;
    public final C0767v2 h2;
    public final C0767v2 h3;
    public final C0736r1 h4;
    public final C0767v2 h5;
    public final C0759u2 i0;
    public final C0759u2 i1;
    public final C0767v2 i2;
    public final C0767v2 i3;
    public final W1 i4;
    public final C0767v2 i5;
    public final C0759u2 j0;
    public final C0759u2 j1;
    public final C0767v2 j2;
    public final C0767v2 j3;
    public final Y1 j4;
    public final C0767v2 j5;
    public final C0759u2 k0;
    public final C0759u2 k1;
    public final C0767v2 k2;
    public final C0767v2 k3;
    public final c k4;
    public final C0767v2 k5;
    public final C0759u2 l0;
    public final C0759u2 l1;
    public final C0767v2 l2;
    public final C0767v2 l3;
    public final C0610b2 l4;
    public final C0767v2 l5;
    public final C0759u2 m0;
    public final C0759u2 m1;
    public final C0767v2 m2;
    public final C0767v2 m3;
    public final C0786w1 m4;
    public final C0767v2 m5;
    public final C0759u2 n0;
    public final C0759u2 n1;
    public final C0767v2 n2;
    public final C0767v2 n3;
    public final d n4;
    public final C0767v2 n5;
    public final C0759u2 o0;
    public final C0759u2 o1;
    public final C0767v2 o2;
    public final C0767v2 o3;
    public final C0710n1 o4;
    public final C0767v2 o5;
    public final C0759u2 p0;
    public final C0759u2 p1;
    public final C0767v2 p2;
    public final C0767v2 p3;
    public final C0757u1 p4;
    public final C0767v2 p5;
    public final C0759u2 q0;
    public final C0759u2 q1;
    public final C0767v2 q2;
    public final C0767v2 q3;
    public final C0765v1 q4;
    public final C0767v2 q5;
    public final C0759u2 r0;
    public final C0759u2 r1;
    public final C0767v2 r2;
    public final C0767v2 r3;
    public final C0798x1 r4;
    public final C0767v2 r5;
    public final C0759u2 s0;
    public final C0759u2 s1;
    public final C0767v2 s2;
    public final C0767v2 s3;
    public final F1 s4;
    public final C0767v2 s5;
    public final C0759u2 t0;
    public final C0759u2 t1;
    public final C0767v2 t2;
    public final C0767v2 t3;
    public final E1 t4;
    public final C0767v2 t5;
    public final C0759u2 u0;
    public final C0759u2 u1;
    public final C0767v2 u2;
    public final C0767v2 u3;
    public final G1 u4;
    public final C0767v2 u5;
    public final C0759u2 v0;
    public final C0767v2 v1;
    public final C0767v2 v2;
    public final C0767v2 v3;
    public final Q1 v4;
    public final C0767v2 v5;
    public final C0759u2 w;
    public final C0759u2 w0;
    public final C0767v2 w1;
    public final C0767v2 w2;
    public final C0767v2 w3;
    public final A1 w4;
    public final C0767v2 w5;
    public final C0759u2 x;
    public final C0759u2 x0;
    public final C0767v2 x1;
    public final C0767v2 x2;
    public final C0767v2 x3;
    public final U1 x4;
    public final C0767v2 x5;
    public final C0759u2 y;
    public final C0759u2 y0;
    public final C0767v2 y1;
    public final C0767v2 y2;
    public final C0767v2 y3;
    public final C0716o1 y4;
    public final C0767v2 y5;
    public final C0759u2 z;
    public final C0759u2 z0;
    public final C0767v2 z1;
    public final C0767v2 z2;
    public final C0767v2 z3;
    public final C2037fz z4;
    public final C0767v2 z5;
    private final Set a = OU.c();
    private final ConcurrentHashMap b = new ConcurrentHashMap();
    private final ConcurrentHashMap c = new ConcurrentHashMap();
    private final ConcurrentHashMap d = new ConcurrentHashMap();
    private final ConcurrentHashMap e = new ConcurrentHashMap();
    private final ConcurrentHashMap f = new ConcurrentHashMap();
    private final ConcurrentHashMap g = new ConcurrentHashMap();
    private final ConcurrentHashMap h = new ConcurrentHashMap();
    private final C1303Qq i = new C1303Qq();
    private final C1303Qq j = new C1303Qq();
    private final C1303Qq k = new C1303Qq();
    private final C1303Qq l = new C1303Qq();
    private final C1303Qq m = new C1303Qq();
    private final E0 n = new E0();
    private final G0 o = new G0();
    private final HashMap p = new HashMap();
    private final HashMap q = new HashMap();
    public final B0.b r = a(0, 0);
    public final B0.b s = a(1, 1);
    private final ConcurrentHashMap t = new ConcurrentHashMap();
    private final ConcurrentHashMap u = new ConcurrentHashMap();
    public final com.android.tools.r8.utils.O1 v = com.android.tools.r8.utils.O1.e();

    /* JADX INFO: renamed from: com.android.tools.r8.graph.p1$b */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public class b {
        public final Y0 a;
        public final Y0 b;
        public final C0684j2 c;
        public final C0684j2 d;
        public final C0684j2 e;
        public final C0684j2 f;
        public final C0684j2 g;
        public final C0684j2 h;
        public final C0684j2 i;
        public final C0684j2 j;
        public final C0684j2 k;
        public final C0684j2 l;
        public final C0684j2 m;
        public final C0684j2 n;

        private b(C0722p1 c0722p1) {
            this.a = c0722p1.a(c0722p1.Z1, c0722p1.A1, "$r8$classId");
            this.b = c0722p1.a(c0722p1.Z1, c0722p1.A1, "$r8$clinit");
            C0767v2 c0767v2 = c0722p1.Z1;
            this.d = c0722p1.a(c0767v2, c0722p1.b(c0722p1.v1, c0767v2), Namer.EQUALS_METHOD_NAME);
            this.f = c0722p1.a(c0722p1.Z1, c0722p1.b(c0722p1.A1, new C0767v2[0]), "hashCode");
            C0767v2 c0767v3 = c0722p1.Z1;
            this.c = c0722p1.a(c0767v3, c0722p1.b(c0767v3, new C0767v2[0]), c0722p1.d0);
            C0759u2 c0759u2 = c0722p1.H0;
            C0759u2 c0759u3 = c0722p1.l0;
            C0759u2 c0759u4 = c0722p1.J0;
            C0759u2[] c0759u2Arr = C0759u2.f;
            this.e = c0722p1.a(c0759u2, c0759u3, c0759u4, c0759u2Arr);
            this.g = c0722p1.a(c0722p1.H0, c0722p1.Y0, c0722p1.D1.e, c0759u2Arr);
            this.h = c0722p1.a(c0722p1.H0, c0722p1.m0, c0722p1.D1.e, c0759u2Arr);
            this.i = c0722p1.a(c0722p1.H0, c0722p1.h0, c0722p1.G0, c0759u2Arr);
            this.j = c0722p1.a(c0722p1.H0, c0722p1.M, c0722p1.E, c0759u2Arr);
            this.k = c0722p1.a(c0722p1.H0, c0722p1.N, c0722p1.E, c0759u2Arr);
            this.l = c0722p1.a(c0722p1.H0, c0722p1.L, c0722p1.E, c0759u2Arr);
            this.m = c0722p1.a(c0722p1.H0, c0722p1.L, c0722p1.E, new C0759u2[]{c0722p1.C});
            this.n = c0722p1.a(c0722p1.H0, c0722p1.L, c0722p1.E, new C0759u2[]{c0722p1.C, c0722p1.B});
        }

        public boolean a(C0684j2 c0684j2) {
            return c0684j2.b(this.c) || c0684j2.b(this.e) || c0684j2.b(this.g) || c0684j2.b(this.h) || c0684j2.b(this.i) || c0684j2.b(this.f) || c0684j2.b(this.d) || c0684j2.b(this.j) || c0684j2.b(this.k) || c0684j2.b(this.l) || c0684j2.b(this.m) || c0684j2.b(this.n);
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.p1$c */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public class c extends O1 {
        public final Y0 a;
        public final C0684j2 b;
        public final C0684j2 c;
        public final C0684j2 d;
        public final C0684j2 e;
        public final C0684j2 f;
        public final C0684j2 g;
        public final C0684j2 h;
        public final C0684j2 i;
        public final C0684j2 j;
        public final C0684j2 k;
        public final C0684j2 l;
        public final C0684j2 m;
        public final C0684j2 n;
        public final C0684j2 o;
        public final C0684j2 p;
        public final C0684j2 q;
        public final C0684j2 r;
        public final C0684j2 s;
        public final C0684j2 t;
        public final C0684j2 u;

        private c(C0722p1 c0722p1) {
            this.a = c0722p1.a(c0722p1.X1, c0722p1.z3, "CASE_INSENSITIVE_ORDER");
            C0767v2 c0767v2 = c0722p1.X1;
            this.e = c0722p1.a(c0767v2, c0722p1.b(c0722p1.D1, c0767v2), c0722p1.Y0);
            C0767v2 c0767v3 = c0722p1.X1;
            this.u = c0722p1.a(c0767v3, c0722p1.b(c0767v3, new C0767v2[0]), c0722p1.f0);
            C0759u2 c0759u2 = c0722p1.G0;
            C0759u2 c0759u3 = c0722p1.O;
            C0759u2 c0759u4 = c0722p1.w;
            C0759u2[] c0759u2Arr = C0759u2.f;
            this.b = c0722p1.a(c0759u2, c0759u3, c0759u4, c0759u2Arr);
            this.c = c0722p1.a(c0722p1.G0, c0722p1.P, c0722p1.B, c0759u2Arr);
            C0759u2[] c0759u2Arr2 = {c0722p1.F0};
            C0759u2 c0759u5 = c0722p1.G0;
            C0759u2[] c0759u2Arr3 = {c0759u5};
            C0759u2[] c0759u2Arr4 = {c0722p1.H0};
            C0759u2[] c0759u2Arr5 = {c0722p1.B};
            this.d = c0722p1.a(c0759u5, c0722p1.Q, c0759u5, c0759u2Arr3);
            this.f = c0722p1.a(c0722p1.G0, c0722p1.R, c0722p1.w, c0759u2Arr2);
            this.g = c0722p1.a(c0722p1.G0, c0722p1.S, c0722p1.w, c0759u2Arr3);
            this.h = c0722p1.a(c0722p1.G0, c0722p1.T, c0722p1.w, c0759u2Arr3);
            this.i = c0722p1.a(c0722p1.G0, c0722p1.U, c0722p1.w, c0759u2Arr4);
            this.j = c0722p1.a(c0722p1.G0, c0722p1.X, c0722p1.w, c0759u2Arr3);
            this.k = c0722p1.a(c0722p1.G0, c0722p1.Y, c0722p1.w, c0759u2Arr2);
            this.m = c0722p1.a(c0722p1.G0, c0722p1.Z, c0722p1.B, c0759u2Arr3);
            this.l = c0722p1.a(c0722p1.G0, c0722p1.Z, c0722p1.B, c0759u2Arr5);
            this.o = c0722p1.a(c0722p1.G0, c0722p1.a0, c0722p1.B, c0759u2Arr3);
            this.n = c0722p1.a(c0722p1.G0, c0722p1.a0, c0722p1.B, c0759u2Arr5);
            this.p = c0722p1.a(c0722p1.G0, c0722p1.b0, c0722p1.B, c0759u2Arr3);
            this.q = c0722p1.a(c0722p1.G0, c0722p1.c0, c0722p1.B, c0759u2Arr3);
            this.r = c0722p1.a(c0722p1.X1, c0722p1.b(c0722p1.A1, new C0767v2[0]), c0722p1.V);
            C0759u2 c0759u6 = c0722p1.G0;
            this.s = c0722p1.a(c0759u6, c0722p1.g0, c0759u6, c0759u2Arr4);
            C0759u2 c0759u7 = c0722p1.G0;
            this.t = c0722p1.a(c0759u7, c0722p1.h0, c0759u7, c0759u2Arr);
            C0759u2 c0759u8 = c0722p1.G0;
            c0722p1.a(c0759u8, c0722p1.i0, c0759u8, c0759u2Arr);
        }

        @Override // com.android.tools.r8.graph.O1
        public final void a(Consumer consumer) {
            consumer.accept(this.a);
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.p1$d */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public class d {
        public final C0684j2 a;
        public final C0684j2 b;
        public final C0684j2 c;

        private d(C0722p1 c0722p1) {
            this.a = c0722p1.a(c0722p1.S0, c0722p1.c("addSuppressed"), c0722p1.E, new C0759u2[]{c0722p1.S0});
            C0759u2 c0759u2 = c0722p1.S0;
            C0759u2 c0759u2C = c0722p1.c("getSuppressed");
            C0759u2 c0759u3 = c0722p1.g1;
            C0759u2[] c0759u2Arr = C0759u2.f;
            this.c = c0722p1.a(c0759u2, c0759u2C, c0759u3, c0759u2Arr);
            C0759u2 c0759u4 = c0722p1.S0;
            C0759u2 c0759u2C2 = c0722p1.c("initCause");
            C0759u2 c0759u5 = c0722p1.S0;
            c0722p1.a(c0759u4, c0759u2C2, c0759u5, new C0759u2[]{c0759u5});
            this.b = c0722p1.a(c0722p1.S0, c0722p1.c("getMessage"), c0722p1.G0, c0759u2Arr);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public C0722p1() {
        C0759u2 c0759u2C = c("Z");
        this.w = c0759u2C;
        C0759u2 c0759u2C2 = c("B");
        this.x = c0759u2C2;
        C0759u2 c0759u2C3 = c("C");
        this.y = c0759u2C3;
        C0759u2 c0759u2C4 = c("D");
        this.z = c0759u2C4;
        C0759u2 c0759u2C5 = c("F");
        this.A = c0759u2C5;
        C0759u2 c0759u2C6 = c("I");
        this.B = c0759u2C6;
        C0759u2 c0759u2C7 = c("J");
        this.C = c0759u2C7;
        C0759u2 c0759u2C8 = c(ExifInterface.LATITUDE_SOUTH);
        this.D = c0759u2C8;
        C0759u2 c0759u2C9 = c(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        this.E = c0759u2C9;
        this.F = c("/");
        this.G = c("Ljava/");
        this.H = c("Lj$/");
        C0759u2 c0759u2C10 = c("[Z");
        C0759u2 c0759u2C11 = c("[B");
        C0759u2 c0759u2C12 = c("[C");
        C0759u2 c0759u2C13 = c("[D");
        C0759u2 c0759u2C14 = c("[F");
        C0759u2 c0759u2C15 = c("[I");
        C0759u2 c0759u2C16 = c("[J");
        C0759u2 c0759u2C17 = c("[S");
        C0759u2 c0759u2C18 = c("Ljava/lang/Boolean;");
        C0759u2 c0759u2C19 = c("Ljava/lang/Byte;");
        C0759u2 c0759u2C20 = c("Ljava/lang/Character;");
        C0759u2 c0759u2C21 = c("Ljava/lang/Double;");
        this.I = c0759u2C21;
        C0759u2 c0759u2C22 = c("Ljava/lang/Float;");
        C0759u2 c0759u2C23 = c("Ljava/lang/Integer;");
        C0759u2 c0759u2C24 = c("Ljava/lang/Long;");
        this.J = c0759u2C24;
        C0759u2 c0759u2C25 = c("Ljava/lang/Short;");
        C0759u2 c0759u2C26 = c("Ljava/lang/Number;");
        this.K = c0759u2C26;
        C0759u2 c0759u2C27 = c("Ljava/lang/Void;");
        this.L = c("wait");
        this.M = c("notify");
        this.N = c("notifyAll");
        C0759u2 c0759u2C28 = c("booleanValue");
        C0759u2 c0759u2C29 = c("byteValue");
        C0759u2 c0759u2C30 = c("charValue");
        C0759u2 c0759u2C31 = c("shortValue");
        C0759u2 c0759u2C32 = c("intValue");
        C0759u2 c0759u2C33 = c("longValue");
        C0759u2 c0759u2C34 = c("floatValue");
        C0759u2 c0759u2C35 = c("doubleValue");
        this.O = c("isEmpty");
        this.P = c("length");
        this.Q = c("concat");
        this.R = c("contains");
        this.S = c("startsWith");
        this.T = c("endsWith");
        this.U = c(Namer.EQUALS_METHOD_NAME);
        this.V = c("hashCode");
        this.W = c("identityHashCode");
        this.X = c("equalsIgnoreCase");
        this.Y = c("contentEquals");
        this.Z = c("indexOf");
        this.a0 = c("lastIndexOf");
        this.b0 = c("compareTo");
        this.c0 = c("compareToIgnoreCase");
        this.d0 = c("clone");
        this.e0 = c("substring");
        this.f0 = c("trim");
        this.g0 = c("valueOf");
        c(CompoundEntry.NAME_values);
        this.h0 = c("toString");
        this.i0 = c("intern");
        this.j0 = c("convert");
        this.k0 = c("wrappedValue");
        this.l0 = c("getClass");
        this.m0 = c("finalize");
        this.n0 = c("ordinal");
        this.o0 = c("name");
        this.p0 = c("desiredAssertionStatus");
        this.q0 = c("forName");
        this.r0 = c("getName");
        this.s0 = c("getCanonicalName");
        this.t0 = c("getSimpleName");
        this.u0 = c("getTypeName");
        this.v0 = c(TypeProxy.SilentConstruction.Appender.GET_DECLARED_CONSTRUCTOR_METHOD_NAME);
        this.w0 = c("getField");
        this.x0 = c("getDeclaredField");
        this.y0 = c("getMethod");
        this.z0 = c("getDeclaredMethod");
        this.A0 = c(TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME);
        this.B0 = c(AssertCodegenUtilKt.ASSERTIONS_DISABLED_FIELD_NAME);
        this.C0 = c("invoke");
        this.D0 = c("invokeExact");
        C0759u2 c0759u2C36 = c("Ljava/lang/RuntimeException;");
        C0759u2 c0759u2C37 = c("Ljava/lang/AssertionError;");
        this.E0 = c0759u2C37;
        C0759u2 c0759u2C38 = c("Ljava/lang/CharSequence;");
        this.F0 = c0759u2C38;
        C0759u2 c0759u2C39 = c("[Ljava/lang/CharSequence;");
        C0759u2 c0759u2C40 = c("Ljava/lang/String;");
        this.G0 = c0759u2C40;
        C0759u2 c0759u2C41 = c("[Ljava/lang/String;");
        C0759u2 c0759u2C42 = c(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR);
        this.H0 = c0759u2C42;
        C0759u2 c0759u2C43 = c("Ljava/lang/Record;");
        C0759u2 c0759u2C44 = c("Lcom/android/tools/r8/RecordTag;");
        C0759u2 c0759u2C45 = c("[Ljava/lang/Object;");
        this.I0 = c0759u2C45;
        C0759u2 c0759u2C46 = c("Ljava/lang/Class;");
        this.J0 = c0759u2C46;
        C0759u2 c0759u2C47 = c("Ljava/lang/ClassLoader;");
        this.K0 = c0759u2C47;
        C0759u2 c0759u2C48 = c("Ljava/lang/AutoCloseable;");
        C0759u2 c0759u2C49 = c("[Ljava/lang/Class;");
        this.L0 = c0759u2C49;
        C0759u2 c0759u2C50 = c("Ljava/lang/reflect/Constructor;");
        this.M0 = c0759u2C50;
        C0759u2 c0759u2C51 = c("Ljava/lang/reflect/Field;");
        this.N0 = c0759u2C51;
        C0759u2 c0759u2C52 = c("Ljava/lang/reflect/Method;");
        this.O0 = c0759u2C52;
        C0759u2 c0759u2C53 = c("Ljava/lang/Enum;");
        this.P0 = c0759u2C53;
        C0759u2 c0759u2C54 = c("Ljava/lang/System;");
        C0759u2 c0759u2C55 = c("Ljava/lang/annotation/Annotation;");
        C0759u2 c0759u2C56 = c("Ljava/util/Objects;");
        C0759u2 c0759u2C57 = c("Ljava/util/Collections;");
        C0759u2 c0759u2C58 = c("Ljava/lang/Iterable;");
        C0759u2 c0759u2C59 = c("Ljava/lang/Math;");
        C0759u2 c0759u2C60 = c("Ljava/lang/StrictMath;");
        C0759u2 c0759u2C61 = c("Ljava/io/Closeable;");
        C0759u2 c0759u2C62 = c("Ljava/util/zip/ZipFile;");
        C0759u2 c0759u2C63 = c("Ljava/nio/Buffer;");
        C0759u2 c0759u2C64 = c("Ljava/nio/ByteBuffer;");
        C0759u2 c0759u2C65 = c("Ljava/nio/MappedByteBuffer;");
        C0759u2 c0759u2C66 = c("Ljava/nio/CharBuffer;");
        C0759u2 c0759u2C67 = c("Ljava/nio/ShortBuffer;");
        C0759u2 c0759u2C68 = c("Ljava/nio/IntBuffer;");
        C0759u2 c0759u2C69 = c("Ljava/nio/LongBuffer;");
        C0759u2 c0759u2C70 = c("Ljava/nio/FloatBuffer;");
        C0759u2 c0759u2C71 = c("Ljava/nio/DoubleBuffer;");
        C0759u2 c0759u2C72 = c("Ljava/lang/StringBuilder;");
        C0759u2 c0759u2C73 = c("Ljava/lang/StringBuffer;");
        C0759u2 c0759u2C74 = c("Ljava/lang/invoke/VarHandle;");
        C0759u2 c0759u2C75 = c("Ljava/lang/invoke/MethodHandle;");
        C0759u2 c0759u2C76 = c("Ljava/lang/invoke/MethodHandles;");
        C0759u2 c0759u2C77 = c("Ljava/lang/invoke/MethodHandles$Lookup;");
        C0759u2 c0759u2C78 = c("Ljava/lang/invoke/MethodType;");
        C0759u2 c0759u2C79 = c("Ljava/lang/reflect/InvocationHandler;");
        C0759u2 c0759u2C80 = c("Ljava/lang/reflect/Proxy;");
        C0759u2 c0759u2C81 = c("Ljava/util/ServiceLoader;");
        C0759u2 c0759u2C82 = c("Ljava/util/ServiceConfigurationError;");
        C0759u2 c0759u2C83 = c("Ljava/util/List;");
        this.Q0 = c0759u2C83;
        C0759u2 c0759u2C84 = c("Ljava/util/Set;");
        C0759u2 c0759u2C85 = c("Ljava/util/Map;");
        C0759u2 c0759u2C86 = c("Ljava/util/Map$Entry;");
        C0759u2 c0759u2C87 = c("Ljava/util/Collection;");
        C0759u2 c0759u2C88 = c("Ljava/util/Comparator;");
        C0759u2 c0759u2C89 = c("Ljava/util/concurrent/Callable;");
        C0759u2 c0759u2C90 = c("Ljava/util/function/Supplier;");
        C0759u2 c0759u2C91 = c("Ljava/util/function/Predicate;");
        C0759u2 c0759u2C92 = c("Ljava/util/function/Consumer;");
        C0759u2 c0759u2C93 = c("Ljava/lang/Runnable;");
        C0759u2 c0759u2C94 = c("Ljava/util/Optional;");
        C0759u2 c0759u2C95 = c("Ljava/util/OptionalDouble;");
        C0759u2 c0759u2C96 = c("Ljava/util/OptionalInt;");
        C0759u2 c0759u2C97 = c("Ljava/util/OptionalLong;");
        C0759u2 c0759u2C98 = c("Ljava/util/stream/Stream;");
        this.R0 = c("Ljava/util/Arrays;");
        C0759u2 c0759u2C99 = c("Ljava/lang/ThreadLocal;");
        C0759u2 c0759u2C100 = c("Ljava/util/concurrent/ConcurrentHashMap;");
        C0759u2 c0759u2C101 = c("Ljava/util/concurrent/ConcurrentHashMap$KeySetView;");
        C0759u2 c0759u2C102 = c("Ljava/lang/Throwable;");
        this.S0 = c0759u2C102;
        C0759u2 c0759u2C103 = c("Ljava/lang/IllegalAccessError;");
        C0759u2 c0759u2C104 = c("Ljava/lang/IllegalArgumentException;");
        C0759u2 c0759u2C105 = c("Ljava/lang/IncompatibleClassChangeError;");
        C0759u2 c0759u2C106 = c("Ljava/lang/ExceptionInInitializerError;");
        C0759u2 c0759u2C107 = c("Ljava/lang/NoClassDefFoundError;");
        C0759u2 c0759u2C108 = c("Ljava/lang/NoSuchFieldError;");
        C0759u2 c0759u2C109 = c("Ljava/lang/NullPointerException;");
        this.T0 = c0759u2C109;
        C0759u2 c0759u2C110 = c("Ljava/lang/ReflectiveOperationException;");
        C0759u2 c0759u2C111 = c("Lkotlin/Metadata;");
        C0759u2 c0759u2C112 = c("Lkotlin/jvm/JvmName;");
        this.U0 = c("Ljava/util/concurrent/atomic/AtomicIntegerFieldUpdater;");
        this.V0 = c("Ljava/util/concurrent/atomic/AtomicLongFieldUpdater;");
        C0759u2 c0759u2C113 = c("Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;");
        this.W0 = c0759u2C113;
        this.X0 = c("newUpdater");
        this.Y0 = c("<init>");
        this.Z0 = c(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME);
        this.a1 = c("$r8$constructor");
        this.b1 = c("$r8$init$synthetic");
        this.c1 = c("this");
        this.d1 = c(JvmAbi.INSTANCE_FIELD);
        this.e1 = c("lambda$");
        c(ImplementationBodyCodegen.ENUM_VALUES_FIELD_NAME);
        this.f1 = c("ENABLED");
        this.g1 = c("[Ljava/lang/Throwable;");
        this.h1 = c("value");
        this.i1 = c(Namer.METADATA_CLASS_KIND);
        this.j1 = c("versionHash");
        this.k1 = c("apiLevel");
        this.l1 = c("Ldalvik/annotation/optimization/");
        this.m1 = c("get");
        this.n1 = c(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX);
        this.o1 = c("compareAndSet");
        this.p1 = c("weakCompareAndSet");
        this.q1 = c("getVolatile");
        this.r1 = c("setVolatile");
        this.s1 = c("setRelease");
        this.t1 = c("lookup");
        this.u1 = c("privateLookupIn");
        C0767v2 c0767v2B = b(c0759u2C);
        this.v1 = c0767v2B;
        C0767v2 c0767v2B2 = b(c0759u2C2);
        this.w1 = c0767v2B2;
        C0767v2 c0767v2B3 = b(c0759u2C3);
        this.x1 = c0767v2B3;
        C0767v2 c0767v2B4 = b(c0759u2C4);
        this.y1 = c0767v2B4;
        C0767v2 c0767v2B5 = b(c0759u2C5);
        this.z1 = c0767v2B5;
        C0767v2 c0767v2B6 = b(c0759u2C6);
        this.A1 = c0767v2B6;
        C0767v2 c0767v2B7 = b(c0759u2C7);
        this.B1 = c0767v2B7;
        C0767v2 c0767v2B8 = b(c0759u2C8);
        this.C1 = c0767v2B8;
        C0767v2 c0767v2B9 = b(c0759u2C9);
        this.D1 = c0767v2B9;
        this.E1 = b(c0759u2C10);
        this.F1 = b(c0759u2C11);
        this.G1 = b(c0759u2C12);
        this.H1 = b(c0759u2C13);
        this.I1 = b(c0759u2C14);
        this.J1 = b(c0759u2C15);
        this.K1 = b(c0759u2C16);
        this.L1 = b(c0759u2C17);
        C0767v2 c0767v2B10 = b(c0759u2C18);
        this.M1 = c0767v2B10;
        C0767v2 c0767v2B11 = b(c0759u2C19);
        this.N1 = c0767v2B11;
        C0767v2 c0767v2B12 = b(c0759u2C20);
        this.O1 = c0767v2B12;
        C0767v2 c0767v2B13 = b(c0759u2C21);
        this.P1 = c0767v2B13;
        C0767v2 c0767v2B14 = b(c0759u2C22);
        this.Q1 = c0767v2B14;
        C0767v2 c0767v2B15 = b(c0759u2C23);
        this.R1 = c0767v2B15;
        C0767v2 c0767v2B16 = b(c0759u2C24);
        this.S1 = c0767v2B16;
        C0767v2 c0767v2B17 = b(c0759u2C25);
        this.T1 = c0767v2B17;
        C0767v2 c0767v2B18 = b(c0759u2C26);
        this.U1 = c0767v2B18;
        C0767v2 c0767v2B19 = b(c0759u2C27);
        this.V1 = b(c0759u2C38);
        this.W1 = b(c0759u2C39);
        C0767v2 c0767v2B20 = b(c0759u2C40);
        this.X1 = c0767v2B20;
        this.Y1 = b(c0759u2C41);
        C0767v2 c0767v2B21 = b(c0759u2C42);
        this.Z1 = c0767v2B21;
        this.a2 = b(c0759u2C43);
        this.b2 = b(c0759u2C44);
        C0767v2 c0767v2B22 = b(c0759u2C45);
        this.c2 = c0767v2B22;
        this.d2 = b(c0759u2C49);
        C0767v2 c0767v2B23 = b(c0759u2C53);
        this.e2 = c0767v2B23;
        this.f2 = b(c0759u2C55);
        this.g2 = b(c0759u2C56);
        this.h2 = b(c0759u2C57);
        this.i2 = b(c0759u2C58);
        this.j2 = b(c0759u2C59);
        this.k2 = b(c0759u2C60);
        this.l2 = b(c0759u2C113);
        this.m2 = b(c0759u2C46);
        this.n2 = g();
        this.o2 = b(c0759u2C47);
        this.p2 = b(c0759u2C50);
        this.q2 = b(c0759u2C51);
        this.r2 = b(c0759u2C52);
        C0767v2 c0767v2B24 = b(c0759u2C48);
        this.s2 = c0767v2B24;
        this.t2 = b(c0759u2C61);
        this.u2 = b(c0759u2C62);
        C0767v2 c0767v2B25 = b(c0759u2C72);
        this.v2 = c0767v2B25;
        C0767v2 c0767v2B26 = b(c0759u2C73);
        this.w2 = c0767v2B26;
        this.x2 = b("Ljava/lang/annotation/RetentionPolicy;");
        this.y2 = b("Ljava/lang/reflect/Array;");
        C0767v2 c0767v2B27 = b(c0759u2C54);
        this.z2 = c0767v2B27;
        this.A2 = b("Ljava/io/PrintStream;");
        this.B2 = b(c0759u2C74);
        C0767v2 c0767v2B28 = b(c0759u2C75);
        this.C2 = c0767v2B28;
        this.D2 = b(c0759u2C76);
        this.E2 = b(c0759u2C77);
        C0767v2 c0767v2B29 = b(c0759u2C78);
        this.F2 = c0767v2B29;
        this.G2 = b(c0759u2C79);
        this.H2 = b(c0759u2C80);
        this.I2 = b(c0759u2C81);
        this.J2 = b(c0759u2C82);
        this.K2 = b(c0759u2C83);
        this.L2 = b(c0759u2C84);
        this.M2 = b(c0759u2C85);
        this.N2 = b(c0759u2C86);
        this.O2 = b("Ljava/util/AbstractMap$SimpleEntry;");
        this.P2 = b(c0759u2C87);
        this.Q2 = b(c0759u2C88);
        C0767v2 c0767v2B30 = b(c0759u2C89);
        this.R2 = b(c0759u2C90);
        this.S2 = b(c0759u2C91);
        this.T2 = b(c0759u2C92);
        this.U2 = b(c0759u2C93);
        this.V2 = b(c0759u2C94);
        this.W2 = b(c0759u2C95);
        this.X2 = b(c0759u2C96);
        this.Y2 = b(c0759u2C97);
        this.Z2 = b(c0759u2C98);
        this.a3 = b(c0759u2C99);
        this.b3 = b(c0759u2C100);
        this.c3 = b(c0759u2C101);
        this.d3 = b(c0759u2C63);
        this.e3 = AbstractC1639am.a(b(c0759u2C64), b(c0759u2C65), b(c0759u2C66), b(c0759u2C67), b(c0759u2C68), b(c0759u2C69), b(c0759u2C70), b(c0759u2C71));
        this.f3 = d();
        this.g3 = b("Ljava/util/function/DoubleConsumer;");
        this.h3 = b("Ljava/util/function/LongConsumer;");
        this.i3 = b("Ljava/util/function/IntConsumer;");
        this.j3 = b("Ljava/lang/annotation/Retention;");
        this.k3 = b(c0759u2C36);
        this.l3 = b(c0759u2C37);
        C0767v2 c0767v2B31 = b(c0759u2C102);
        this.m3 = c0767v2B31;
        this.n3 = b(c0759u2C103);
        this.o3 = b(c0759u2C104);
        this.p3 = b(c0759u2C105);
        b(c0759u2C106);
        this.q3 = b(c0759u2C107);
        b(c0759u2C108);
        this.r3 = b("Ljava/lang/NoSuchMethodError;");
        C0767v2 c0767v2B32 = b(c0759u2C109);
        this.s3 = c0767v2B32;
        b(c0759u2C110);
        this.t3 = b(c0759u2C111);
        this.u3 = b(c0759u2C112);
        this.v3 = b("Ljava/io/File;");
        this.w3 = b("Ljava/math/BigInteger;");
        this.x3 = b("Ljava/nio/ByteOrder;");
        this.y3 = b("Ljava/util/Collections;");
        this.z3 = b("Ljava/util/Comparator;");
        this.A3 = b("Ljava/util/concurrent/TimeUnit;");
        this.B3 = b("Ljava/util/List;");
        this.C3 = b("Ljava/util/Locale;");
        this.D3 = b("Ljava/util/logging/Level;");
        this.E3 = b("Ljava/util/logging/Logger;");
        this.F3 = b("Ljava/util/Set;");
        C0767v2 c0767v2B33 = b("Landroid/app/Activity;");
        this.G3 = b("Landroid/app/Fragment;");
        this.H3 = b("Landroid/app/ZygotePreload;");
        this.I3 = b("Landroid/os/Build;");
        this.J3 = b("Landroid/os/Build$VERSION;");
        this.K3 = b("Landroid/os/Bundle;");
        this.L3 = b("Landroid/os/Parcelable$Creator;");
        this.M3 = b("Landroid/system/OsConstants;");
        this.N3 = b("Landroid/util/Log;");
        this.O3 = b("Landroid/util/Property;");
        this.P3 = b("Landroid/view/View;");
        this.Q3 = b("Landroid/util/SparseArray;");
        this.R3 = b("Landroid/content/res/TypedArray;");
        this.S3 = b("Landroid/content/ContentProviderClient;");
        this.T3 = b("Landroid/drm/DrmManagerClient;");
        this.U3 = b("Landroid/media/MediaDrm;");
        this.V3 = b("Landroid/media/MediaMetadataRetriever;");
        Z1 z1 = new Z1(this, c0767v2B25);
        this.W3 = z1;
        Z1 z2 = new Z1(this, c0767v2B26);
        this.X3 = z2;
        a aVar = new a();
        this.Y3 = aVar;
        this.Z3 = new C0742s1(this);
        this.a4 = new C0749t1(this);
        C0807y1 c0807y1 = new C0807y1(this);
        this.b4 = c0807y1;
        B1 b1 = new B1(this);
        this.c4 = b1;
        P1 p1 = new P1(this);
        this.d4 = p1;
        this.e4 = new C0628c2(this);
        S1 s1 = new S1(this);
        this.f4 = s1;
        b bVar = new b();
        this.g4 = bVar;
        this.h4 = new C0736r1(this);
        this.i4 = new W1(this);
        this.j4 = new Y1(this);
        c cVar = new c();
        this.k4 = cVar;
        this.l4 = new C0610b2(this);
        this.m4 = new C0786w1(this);
        this.n4 = new d();
        this.o4 = new C0710n1(this);
        C0757u1 c0757u1 = new C0757u1(this);
        this.p4 = c0757u1;
        this.q4 = new C0765v1(this);
        this.r4 = new C0798x1(this);
        new C0690k1(this);
        this.s4 = new F1(this);
        this.t4 = new E1(this);
        this.u4 = new G1(this);
        new D1(this);
        this.v4 = new Q1(this);
        this.w4 = new A1(this);
        this.x4 = new U1(this);
        this.y4 = new C0716o1(this);
        this.A4 = new T1(this);
        this.B4 = new V1(this);
        C0659g1 c0659g1 = new C0659g1(this);
        C0666h1 c0666h1 = new C0666h1(this);
        this.C4 = c0666h1;
        C0675i1 c0675i1 = new C0675i1(this);
        C0683j1 c0683j1 = new C0683j1(this);
        C0704m1 c0704m1 = new C0704m1(this);
        this.D4 = new C0698l1(this);
        this.E4 = new C0627c1(this);
        this.F4 = new C0609b1(this);
        this.G4 = new C0634d1(this);
        this.H4 = new C0641e1(this);
        this.I4 = new C0652f1(this);
        C1 c1 = new C1(this);
        H1 h1 = new H1(this);
        I1 i1 = new I1(this);
        this.J4 = new J1(this);
        this.K4 = AbstractC1639am.a(aVar, c0807y1, b1, p1, cVar, c0659g1, c0666h1, c0675i1, c0683j1, c0704m1, c1, h1, i1, new K1(this), new L1(this), new M1(this), new N1(this));
        this.L4 = c("$closeResource");
        this.M4 = b(c0767v2B9, c0767v2B31, c0767v2B24);
        C0759u2 c0759u2C114 = c("$deserializeLambda$");
        this.N4 = c0759u2C114;
        C0767v2 c0767v2B34 = b("Ljava/lang/invoke/SerializedLambda;");
        this.O4 = c0767v2B34;
        C0737r2 c0737r2B = b(c0767v2B21, c0767v2B34);
        this.P4 = c0737r2B;
        c("SourceFile");
        this.Q4 = b("Ldalvik/annotation/AnnotationDefault;");
        this.R4 = b("Ldalvik/annotation/EnclosingClass;");
        this.S4 = b("Ldalvik/annotation/EnclosingMethod;");
        this.T4 = b("Ldalvik/annotation/InnerClass;");
        this.U4 = b("Ldalvik/annotation/MemberClasses;");
        this.V4 = b("Ldalvik/annotation/MethodParameters;");
        this.W4 = b("Ldalvik/annotation/Signature;");
        this.X4 = b("Ldalvik/annotation/NestHost;");
        this.Y4 = b("Ldalvik/annotation/NestMembers;");
        this.Z4 = b("Ldalvik/annotation/PermittedSubclasses;");
        this.a5 = b("Ldalvik/annotation/Record;");
        this.b5 = c("componentNames");
        this.c5 = c("componentTypes");
        this.d5 = c("componentSignatures");
        this.e5 = c("componentAnnotationVisibilities");
        this.f5 = c("componentAnnotations");
        this.g5 = b("Ldalvik/annotation/SourceDebugExtension;");
        this.h5 = b("Ldalvik/annotation/Throws;");
        this.i5 = b("Lcom/android/tools/r8/annotations/SynthesizedClassV2;");
        this.j5 = b("Ldalvik/annotation/codegen/CovariantReturnType;");
        this.k5 = b("Ldalvik/annotation/codegen/CovariantReturnType$CovariantReturnTypes;");
        this.l5 = b("Ldalvik/annotation/optimization/ReachabilitySensitive;");
        C0767v2 c0767v2B35 = b("Ljava/lang/invoke/LambdaMetafactory;");
        this.m5 = b("Ljava/lang/invoke/ConstantBootstraps;");
        C0767v2 c0767v2B36 = b("Ljava/lang/invoke/CallSite;");
        this.n5 = c0767v2B36;
        C0767v2 c0767v2B37 = b("Ljava/lang/invoke/MethodHandles$Lookup;");
        this.o5 = c0767v2B37;
        this.p5 = b("Ljava/lang/runtime/ObjectMethods;");
        this.q5 = b("Ljava/lang/invoke/TypeDescriptor;");
        this.r5 = b("Ljava/util/Iterator;");
        this.s5 = b("Ljava/util/ListIterator;");
        this.t5 = b("Ljava/util/Enumeration;");
        this.u5 = b("Ljava/io/Serializable;");
        this.v5 = b("Ljava/io/Externalizable;");
        this.w5 = b("Ljava/lang/Cloneable;");
        this.x5 = b("Ljava/lang/Comparable;");
        this.y5 = b("Ljava/lang/invoke/StringConcatFactory;");
        this.z5 = b("Lsun/misc/Unsafe;");
        this.A5 = b("Lcom/android/tools/r8/DesugarVarHandle;");
        this.B5 = b("Lcom/android/tools/r8/DesugarMethodHandlesLookup;");
        this.C5 = new R1(this);
        this.D5 = new X1(this);
        this.E5 = new C0595a2(this);
        this.F5 = new com.android.tools.r8.synthesis.L();
        C3011sk c3011skA = C3011sk.a(AbstractC2178hm.e().a(c0767v2B, c0767v2B10).a(c0767v2B2, c0767v2B11).a(c0767v2B3, c0767v2B12).a(c0767v2B8, c0767v2B17).a(c0767v2B6, c0767v2B15).a(c0767v2B7, c0767v2B16).a(c0767v2B5, c0767v2B14).a(c0767v2B4, c0767v2B13).a());
        this.G5 = c3011skA;
        this.H5 = AbstractC2178hm.e().a(c0767v2B10, a(c0759u2C28, c0767v2B)).a(c0767v2B11, a(c0759u2C29, c0767v2B2)).a(c0767v2B12, a(c0759u2C30, c0767v2B3)).a(c0767v2B17, a(c0759u2C31, c0767v2B8)).a(c0767v2B15, a(c0759u2C32, c0767v2B6)).a(c0767v2B16, a(c0759u2C33, c0767v2B7)).a(c0767v2B14, a(c0759u2C34, c0767v2B5)).a(c0767v2B13, a(c0759u2C35, c0767v2B4)).a();
        this.I5 = a(c0767v2B35, b(c0767v2B36, c0767v2B37, c0767v2B20, c0767v2B29, c0767v2B29, c0767v2B28, c0767v2B29), c("metafactory"));
        this.J5 = a(c0767v2B35, b(c0767v2B36, c0767v2B37, c0767v2B20, c0767v2B29, c0767v2B22), c("altMetafactory"));
        this.K5 = a(c0767v2B21, c0737r2B, c0759u2C114);
        this.L5 = b();
        this.M5 = AbstractC1048Hm.g().a((Set) z2.s).a((Set) z1.s).a();
        this.N5 = AbstractC1048Hm.g().a((Object[]) new C0684j2[]{c0757u1.e, c0757u1.g, c0757u1.b, bVar.e, s1.e, s1.f, s1.g, cVar.s}).a((Set) z2.s).a((Set) z1.s).a();
        this.O5 = AbstractC1048Hm.g().a(a()).a();
        this.P5 = AbstractC1048Hm.g().a((Object[]) new C0767v2[]{c0767v2B33, c0767v2B30, c0767v2B23, c0767v2B32, c0767v2B21, c0767v2B26, c0767v2B25, c0767v2B20}).a(c3011skA.values()).a();
        this.Q5 = AbstractC1048Hm.a(c0767v2B10, c0767v2B11, c0767v2B12, c0767v2B13, c0767v2B14, c0767v2B15, c0767v2B16, c0767v2B18, c0767v2B17, c0767v2B19, c0767v2B23, c0767v2B27, c0767v2B32, c0767v2B21, c0767v2B26, c0767v2B25, c0767v2B20);
        this.R5 = false;
        this.z4 = new C2037fz(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ F0 a(F0 f0) {
        return f0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Optional b(C0684j2 c0684j2, Predicate predicate, C0737r2 c0737r2) {
        return Optional.of(a(c0684j2.e, c0737r2, c0684j2.f)).filter(predicate);
    }

    private AbstractC1639am d() {
        C1507Xl c1507XlG = AbstractC1639am.g();
        for (String str : V5) {
            Iterator it = W5.iterator();
            while (it.hasNext()) {
                c1507XlG.a(e("L" + str + ((String) it.next())));
            }
        }
        Iterator it2 = X5.iterator();
        while (it2.hasNext()) {
            c1507XlG.a(e((String) it2.next()));
        }
        return c1507XlG.a();
    }

    private C0767v2 g() {
        C0767v2 c0767v2D = d(c(C3970u0.H(Package.class.getName())));
        C0767v2 c0767v2B = c0767v2D.M0() ? c0767v2D.b(this) : c0767v2D;
        if (c0767v2B.O0()) {
            this.a.add(c0767v2B);
        }
        return c0767v2D;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 h(C0767v2 c0767v2) {
        return c0767v2;
    }

    public final C0767v2 c(C0767v2 c0767v2) {
        if (Y5 || c0767v2.W0()) {
            return (C0767v2) this.G5.get(c0767v2);
        }
        throw new AssertionError();
    }

    public final C0767v2 e(C0767v2 c0767v2) {
        return (C0767v2) this.G5.g().get(c0767v2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final C0684j2 f(C0767v2 c0767v2) {
        C0684j2 c0684j2 = (C0684j2) this.H5.get((C0767v2) this.G5.getOrDefault(c0767v2, c0767v2));
        if (c0684j2 != null) {
            return c0684j2;
        }
        throw new C3405y10("Invalid primitive type descriptor: " + c0767v2);
    }

    public final boolean i() {
        return this.R5;
    }

    public final com.android.tools.r8.synthesis.L j() {
        return this.F5;
    }

    public final void k() {
        boolean z = Y5;
        if (!z && !this.t.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.u.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.v.isEmpty()) {
            throw new AssertionError();
        }
    }

    public C0737r2 a(C0767v2 c0767v2, C0800x2 c0800x2) {
        C0767v2[] c0767v2Arr = c0800x2.a;
        StringBuilder sb = new StringBuilder();
        sb.append(c0767v2.c1());
        for (C0767v2 c0767v3 : c0767v2Arr) {
            sb.append(c0767v3.c1());
        }
        return a(c(sb.toString()), c0767v2, c0800x2);
    }

    public C0767v2 e(String str) {
        return c(c(str));
    }

    public synchronized Collection<com.android.tools.r8.dex.U> h() {
        HashSet hashSet;
        hashSet = new HashSet();
        Iterator it = this.b.keySet().iterator();
        while (it.hasNext()) {
            com.android.tools.r8.dex.U uA = com.android.tools.r8.dex.U.a((C0759u2) it.next());
            if (uA != null) {
                hashSet.add(uA);
            }
        }
        return hashSet;
    }

    public final void i(C0767v2 c0767v2) {
        if (c0767v2.M0()) {
            c0767v2 = c0767v2.b(this);
        }
        if (c0767v2.O0()) {
            this.a.add(c0767v2);
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.p1$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public class a extends AbstractC0728q1 {
        public final Y0 a;
        public final Y0 b;
        public final Y0 c;
        public final C0684j2 d;
        public final C0684j2 e;
        public final C0684j2 f;
        public final C0684j2 g;

        private a(C0722p1 c0722p1) {
            C0767v2 c0767v2 = c0722p1.M1;
            this.a = c0722p1.a(c0767v2, c0767v2, "FALSE");
            C0767v2 c0767v3 = c0722p1.M1;
            this.b = c0722p1.a(c0767v3, c0767v3, "TRUE");
            this.c = c0722p1.a(c0722p1.M1, c0722p1.m2, "TYPE");
            this.d = c0722p1.a(c0722p1.M1, c0722p1.b(c0722p1.v1, new C0767v2[0]), "booleanValue");
            this.e = c0722p1.a(c0722p1.M1, c0722p1.b(c0722p1.v1, c0722p1.X1), "parseBoolean");
            C0767v2 c0767v4 = c0722p1.M1;
            this.f = c0722p1.a(c0767v4, c0722p1.b(c0767v4, c0722p1.v1), "valueOf");
            this.g = c0722p1.a(c0722p1.M1, c0722p1.b(c0722p1.X1, new C0767v2[0]), "toString");
        }

        @Override // com.android.tools.r8.graph.O1
        public final void a(Consumer consumer) {
            consumer.accept(this.a);
            consumer.accept(this.b);
            consumer.accept(this.c);
        }

        @Override // com.android.tools.r8.graph.AbstractC0728q1
        public final Y0 a() {
            return this.c;
        }
    }

    public C0759u2 c(String str) {
        return (C0759u2) a(this.c, new C0759u2(str));
    }

    public final C0767v2 e(C0759u2 c0759u2) {
        return (C0767v2) this.d.get(c0759u2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final C0684j2 b(C0767v2 c0767v2) {
        C0767v2 c0767v3 = (C0767v2) this.G5.getOrDefault(c0767v2, c0767v2);
        C0767v2 c0767v2E = e(c0767v3);
        if (c0767v2E != null) {
            return a(c0767v3, b(c0767v3, c0767v2E), this.g0);
        }
        throw new C3405y10("Invalid primitive type descriptor: " + c0767v2);
    }

    public final C0759u2 c(int i, byte[] bArr) {
        return (C0759u2) this.c.get(new C0759u2(i, bArr));
    }

    public final D0 e(int i) {
        D0 d0;
        synchronized (this.m) {
            d0 = (D0) this.m.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return new D0(((Integer) obj).intValue());
                }
            });
        }
        return d0;
    }

    public synchronized C0767v2 c(C0759u2 c0759u2) {
        return d(c0759u2);
    }

    public final C0759u2 f(String str) {
        return (C0759u2) this.c.get(new C0759u2(str));
    }

    public final C0737r2 c(C0684j2 c0684j2) {
        return a(c0684j2.h, c0684j2.e);
    }

    public final G0 f() {
        return this.o;
    }

    public final B0.b c(int i) {
        B0.b bVar;
        synchronized (this.k) {
            bVar = (B0.b) this.k.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda11
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return new B0.b(((Integer) obj).intValue());
                }
            });
        }
        return bVar;
    }

    public final E0 e() {
        return this.n;
    }

    public final boolean g(C0767v2 c0767v2) {
        return this.a.contains(c0767v2);
    }

    public static boolean a(AbstractC0594a1 abstractC0594a1) {
        return U5.containsKey(abstractC0594a1);
    }

    public final void c() {
        this.t.clear();
        this.u.clear();
        this.v.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Optional a(C0684j2 c0684j2, Predicate predicate, C0737r2 c0737r2) {
        return Optional.of(a(c0684j2.e, c0737r2, c0684j2.f)).filter(predicate);
    }

    public final AbstractC0728q1 d(C0767v2 c0767v2) {
        if (!Y5 && !c0767v2.W0() && !c0767v2.Y0()) {
            throw new AssertionError();
        }
        char cW0 = c0767v2.D0().w0();
        if (cW0 == 'F') {
            return this.b4;
        }
        if (cW0 == 'S') {
            return this.j4;
        }
        if (cW0 == 'V') {
            return this.e4;
        }
        if (cW0 == 'Z') {
            return this.Y3;
        }
        if (cW0 == 'I') {
            return this.c4;
        }
        if (cW0 == 'J') {
            return this.d4;
        }
        switch (cW0) {
            case 'B':
                return this.Z3;
            case 'C':
                return this.a4;
            case 'D':
                return this.m4;
            default:
                throw new C3405y10("Unknown type " + c0767v2);
        }
    }

    private AbstractC2178hm b() {
        C1946em c1946emE = AbstractC2178hm.e();
        S1 s1 = this.f4;
        Iterator<E> it = AbstractC1639am.a(s1.e, s1.f, s1.g).iterator();
        while (it.hasNext()) {
            c1946emE.a((C0684j2) it.next(), new int[]{0});
        }
        return c1946emE.a();
    }

    public final B0.b a(int i, int i2) {
        return c(new B0.b((i2 * 15) + i + 14).c);
    }

    private C0684j2 a(C0759u2 c0759u2, C0767v2 c0767v2) {
        return a((C0767v2) this.G5.get(c0767v2), b(c0767v2, new C0767v2[0]), c0759u2);
    }

    public final Set a() {
        return (Set) this.G5.entrySet().stream().map(new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((Map.Entry) obj);
            }
        }).collect(Collectors.toSet());
    }

    public final C0759u2 b(int i, byte[] bArr) {
        return (C0759u2) a(this.c, new C0759u2(i, bArr));
    }

    private Y0 b(final C0767v2 c0767v2, final C0767v2 c0767v3, String str, final Predicate predicate) {
        return (Y0) a(new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0767v2, c0767v3, predicate, (C0759u2) obj);
            }
        }, str, (C0767v2) null, 0);
    }

    private C0767v2 b(String str) {
        C0767v2 c0767v2D = d(c(str));
        C0767v2 c0767v2B = c0767v2D.M0() ? c0767v2D.b(this) : c0767v2D;
        if (c0767v2B.O0()) {
            this.a.add(c0767v2B);
        }
        return c0767v2D;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0684j2 a(Map.Entry entry) {
        C0767v2 c0767v2 = (C0767v2) entry.getKey();
        C0759u2 c0759u2 = ((C0767v2) entry.getValue()).e;
        return a(c0759u2, this.g0, c0759u2, new C0759u2[]{c0767v2.e});
    }

    private C0767v2 b(C0759u2 c0759u2) {
        C0767v2 c0767v2D = d(c0759u2);
        C0767v2 c0767v2B = c0767v2D.M0() ? c0767v2D.b(this) : c0767v2D;
        if (c0767v2B.O0()) {
            this.a.add(c0767v2B);
        }
        return c0767v2D;
    }

    public void a(boolean z) {
        this.R5 = z;
    }

    private static AbstractC0594a1 a(ConcurrentHashMap concurrentHashMap, I3 i3) {
        if (!Y5 && U5.containsKey(i3)) {
            throw new AssertionError();
        }
        AbstractC0594a1 abstractC0594a1 = (AbstractC0594a1) concurrentHashMap.putIfAbsent(i3, i3);
        return abstractC0594a1 == null ? i3 : abstractC0594a1;
    }

    public final void b(Consumer consumer) {
        this.a.forEach(consumer);
    }

    public final C0759u2 a(int i, byte[] bArr) {
        C0759u2 c0759u2B = b(i, bArr);
        if (com.android.tools.r8.dex.U.a(c0759u2B.e)) {
            this.b.put(c0759u2B, c0759u2B);
        }
        return c0759u2B;
    }

    public C0737r2 b(C0767v2 c0767v2, C0767v2... c0767v2Arr) {
        C0800x2 c0800x2S0 = c0767v2Arr.length == 0 ? C0800x2.s0() : new C0800x2(c0767v2Arr);
        StringBuilder sb = new StringBuilder();
        sb.append(c0767v2.c1());
        for (C0767v2 c0767v3 : c0767v2Arr) {
            sb.append(c0767v3.c1());
        }
        return a(c(sb.toString()), c0767v2, c0800x2S0);
    }

    public final void a(String str) {
        C0759u2 c0759u2C = c(str);
        if (com.android.tools.r8.dex.U.a(c0759u2C.e)) {
            this.b.put(c0759u2C, c0759u2C);
        }
    }

    private C0767v2 d(C0759u2 c0759u2) {
        boolean z = Y5;
        if (!z && c0759u2 == null) {
            throw new AssertionError();
        }
        C0767v2 c0767v2 = (C0767v2) this.d.get(c0759u2);
        if (c0767v2 == null) {
            c0767v2 = new C0767v2(c0759u2);
            if (!z && !c0767v2.M0() && !c0767v2.O0() && !c0767v2.W0() && !c0767v2.Y0()) {
                throw new AssertionError(c0759u2.toString());
            }
            if (!z && U5.containsKey(c0767v2)) {
                throw new AssertionError();
            }
            this.d.put(c0759u2, c0767v2);
        }
        return c0767v2;
    }

    public final Object a(Function function, String str, C0767v2 c0767v2, int i) {
        int i2 = 0;
        while (true) {
            if (!Y5 && i2 >= 1000) {
                throw new AssertionError();
            }
            int i3 = i + i2;
            StringBuilder sbAppend = new StringBuilder().append(str);
            if (c0767v2 != null) {
                sbAppend.append('$').append(c0767v2.r0().replace('.', '$'));
            }
            if (i3 > 0) {
                sbAppend.append("$").append(i3);
            }
            Optional optional = (Optional) function.apply(c(sbAppend.toString()));
            if (optional.isPresent()) {
                return optional.get();
            }
            i2++;
        }
    }

    public final B0.a b(int i) {
        B0.a aVar;
        synchronized (this.j) {
            aVar = (B0.a) this.j.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return new B0.a(((Integer) obj).intValue());
                }
            });
        }
        return aVar;
    }

    public final boolean b(C0684j2 c0684j2) {
        return c0684j2.f == this.Y0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1513Xr b(C0702m c0702m, C0767v2 c0767v2) {
        C1513Xr c1513XrG = ((C0680j) c0702m.f()).g(c0767v2);
        return C1229Oa.a((C0702m<? extends C0680j>) c0702m, c1513XrG, c1513XrG);
    }

    public final synchronized C0767v2 d(String str) {
        C0767v2 c0767v2D;
        c0767v2D = d(c(str));
        C0767v2 c0767v2B = c0767v2D.M0() ? c0767v2D.b(this) : c0767v2D;
        if (c0767v2B.O0()) {
            this.a.add(c0767v2B);
        }
        return c0767v2D;
    }

    public final C0684j2 a(String str, C0767v2 c0767v2, C0737r2 c0737r2, C0767v2 c0767v3, Predicate predicate) {
        if (Y5 || c0767v2 != null) {
            return a(str, c0767v2, c0737r2, c0767v3, predicate, 0);
        }
        throw new AssertionError();
    }

    static {
        C0767v2 c0767v2 = new C0767v2(new C0759u2("NULL"));
        S5 = c0767v2;
        C0759u2 c0759u2 = new C0759u2(GrsBaseInfo.CountryCodeSource.UNKNOWN);
        T5 = c0759u2;
        U5 = new IdentityHashMap(AbstractC2178hm.a(c0767v2, c0767v2, c0759u2, c0759u2));
        V5 = AbstractC1639am.a("androidx/", "android/support/");
        W5 = AbstractC1639am.a("multidex/MultiDex$V14$ElementConstructor;", "multidex/MultiDex$V14$ICSElementConstructor;", "multidex/MultiDex$V14$JBMR11ElementConstructor;", "multidex/MultiDex$V14$JBMR2ElementConstructor;", "multidex/MultiDex$V14;", "multidex/MultiDex$V19;", "multidex/MultiDex$V21_PLUS;", "multidex/MultiDex$V4;", "multidex/MultiDexApplication;", "multidex/MultiDexExtractor$1;", "multidex/MultiDexExtractor$ExtractedDex;", "multidex/MultiDexExtractor;", "multidex/MultiDex;", "multidex/ZipUtil;", "multidex/ZipUtil$CentralDirectory;");
        X5 = AbstractC1639am.a("Landroidx/multidex/instrumentation/BuildConfig;", "Landroid/test/runner/MultiDexTestRunner;");
    }

    public final C0684j2 a(C0737r2 c0737r2, C0767v2 c0767v2, String str, Predicate predicate) {
        return a(str, null, c0737r2, c0767v2, predicate, 0);
    }

    public final C0684j2 a(String str, C0737r2 c0737r2, C0767v2 c0767v2, Predicate predicate, int i) {
        return a(str, null, c0737r2, c0767v2, predicate, i);
    }

    private C0684j2 a(String str, C0767v2 c0767v2, final C0737r2 c0737r2, final C0767v2 c0767v3, final Predicate predicate, int i) {
        return (C0684j2) a(new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0767v3, c0737r2, predicate, (C0759u2) obj);
            }
        }, str, c0767v2, i);
    }

    public final C0 d(int i) {
        C0 c0;
        synchronized (this.l) {
            c0 = (C0) this.l.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda14
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return new C0(((Integer) obj).intValue());
                }
            });
        }
        return c0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Optional a(C0767v2 c0767v2, C0737r2 c0737r2, Predicate predicate, C0759u2 c0759u2) {
        C0684j2 c0684j2A = a(c0767v2, c0737r2, c0759u2);
        if (predicate.test(c0684j2A)) {
            return Optional.of(c0684j2A);
        }
        return Optional.empty();
    }

    public final AbstractC0717o2 a(final C0737r2 c0737r2, String str, final Predicate predicate) {
        return (AbstractC0717o2) a(new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0722p1.a(c0737r2, predicate, (C0759u2) obj);
            }
        }, str, (C0767v2) null, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Optional a(C0737r2 c0737r2, Predicate predicate, C0759u2 c0759u2) {
        int i = AbstractC0717o2.a;
        C0711n2 c0711n2 = new C0711n2(c0737r2, c0759u2);
        if (predicate.test(c0711n2)) {
            return Optional.of(c0711n2);
        }
        return Optional.empty();
    }

    public final Y0 a(C0767v2 c0767v2, C0767v2 c0767v3, String str, Predicate predicate) {
        return b(c0767v2, c0767v3, str, predicate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Optional a(C0767v2 c0767v2, C0767v2 c0767v3, Predicate predicate, C0759u2 c0759u2) {
        Y0 y0A = a(c0767v2, c0767v3, c0759u2);
        return predicate.test(y0A) ? Optional.of(y0A) : Optional.empty();
    }

    public final C0684j2 a(C0767v2 c0767v2) {
        return a(c0767v2, b(this.D1, new C0767v2[0]), this.Z0);
    }

    public final C0684j2 a(C0767v2 c0767v2, C0767v2... c0767v2Arr) {
        return a(c0767v2, b(this.D1, c0767v2Arr), this.Y0);
    }

    public final C0684j2 a(final C0684j2 c0684j2, List list, final Predicate predicate, Consumer consumer) {
        if (Y5 || c0684j2.b(this)) {
            return a(c0684j2.h, list, new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda16
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a(c0684j2, predicate, (C0737r2) obj);
                }
            }, consumer);
        }
        throw new AssertionError();
    }

    public final C0684j2 a(final C0684j2 c0684j2, final C0767v2 c0767v2, final Predicate predicate) {
        if (Y5 || c0684j2.b(this)) {
            return a(c0684j2.h, AbstractC1639am.c(new Supplier() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda12
                @Override // java.util.function.Supplier
                public final Object get() {
                    return C0722p1.h(c0767v2);
                }
            }), new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda13
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.b(c0684j2, predicate, (C0737r2) obj);
                }
            }, C3912j0.b());
        }
        throw new AssertionError();
    }

    private C0684j2 a(C0737r2 c0737r2, List list, Function function, Consumer consumer) {
        Optional optional = (Optional) function.apply(c0737r2);
        if (optional.isPresent()) {
            return (C0684j2) optional.get();
        }
        if (!Y5 && list.isEmpty()) {
            throw new AssertionError();
        }
        ArrayDeque arrayDequeA = AbstractC3962s0.a(new C0815z1(this, c0737r2, (Supplier) list.iterator().next(), Collections.emptySet()));
        int i = 0;
        while (true) {
            boolean z = Y5;
            if (!z) {
                int i2 = i + 1;
                if (i >= 100) {
                    throw new AssertionError();
                }
                i = i2;
            }
            if (!z && arrayDequeA.isEmpty()) {
                throw new AssertionError();
            }
            C0815z1 c0815z1 = (C0815z1) arrayDequeA.removeFirst();
            C0767v2 c0767v2 = (C0767v2) c0815z1.b.get();
            c0815z1.c.add(c0767v2);
            C0722p1 c0722p1 = c0815z1.d;
            C0737r2 c0737r3 = c0815z1.a;
            c0722p1.getClass();
            int size = c0737r3.f.size();
            C0767v2[] c0767v2Arr = new C0767v2[size + 1];
            C0800x2 c0800x2 = c0737r3.f;
            System.arraycopy(c0800x2.a, 0, c0767v2Arr, 0, c0800x2.size());
            c0767v2Arr[size] = c0767v2;
            C0737r2 c0737r2B = c0722p1.b(c0737r3.e, c0767v2Arr);
            Optional optional2 = (Optional) function.apply(c0737r2B);
            if (optional2.isPresent()) {
                if (!z && c0815z1.c.isEmpty()) {
                    throw new AssertionError();
                }
                consumer.accept(c0815z1.c);
                return (C0684j2) optional2.get();
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayDequeA.addLast(new C0815z1(this, c0737r2B, (Supplier) it.next(), c0815z1.c));
            }
        }
    }

    public C0767v2 a(ClassReference classReference) {
        return e(classReference.getDescriptor());
    }

    public C0767v2 a(int i, C0767v2 c0767v2) {
        if (!Y5 && i <= 0) {
            throw new AssertionError();
        }
        return e(AbstractC2619nY.a(i, "[") + c0767v2.b1());
    }

    public Y0 a(C0767v2 c0767v2, C0767v2 c0767v3, C0759u2 c0759u2) {
        return (Y0) a(this.e, new Y0(c0767v2, c0767v3, c0759u2, this.R5));
    }

    public Y0 a(C0767v2 c0767v2, C0767v2 c0767v3, String str) {
        return a(c0767v2, c0767v3, c(str));
    }

    public Y0 a(FieldReference fieldReference) {
        return a(e(fieldReference.getHolderClass().getDescriptor()), e(fieldReference.getFieldType().getDescriptor()), fieldReference.getFieldName());
    }

    public final C0737r2 a(C0759u2 c0759u2, C0767v2 c0767v2, C0800x2 c0800x2) {
        return (C0737r2) a(this.f, new C0737r2(c0759u2, c0767v2, c0800x2));
    }

    public C0737r2 a(C0767v2 c0767v2, List<C0767v2> list) {
        return b(c0767v2, (C0767v2[]) list.toArray(C0767v2.g));
    }

    public final C0737r2 a(C0737r2 c0737r2, C0767v2 c0767v2) {
        C0767v2[] c0767v2Arr = new C0767v2[c0737r2.f.size() + 1];
        c0767v2Arr[0] = c0767v2;
        C0800x2 c0800x2 = c0737r2.f;
        System.arraycopy(c0800x2.a, 0, c0767v2Arr, 1, c0800x2.size());
        return b(c0737r2.e, c0767v2Arr);
    }

    public final C0737r2 a(C0737r2 c0737r2, Function function, AbstractMap abstractMap) {
        C0767v2[] c0767v2Arr;
        if (!Y5 && abstractMap == null) {
            throw new AssertionError();
        }
        C0737r2 c0737r2B = (C0737r2) abstractMap.get(c0737r2);
        if (c0737r2B == null) {
            C0767v2 c0767v2 = (C0767v2) function.apply(c0737r2.e);
            C0767v2[] c0767v2Arr2 = c0737r2.f.a;
            C3017sq c3017sq = new C3017sq();
            for (int i = 0; i < c0767v2Arr2.length; i++) {
                C0767v2 c0767v3 = (C0767v2) function.apply(c0767v2Arr2[i]);
                if (c0767v3 != c0767v2Arr2[i]) {
                    c3017sq.put(Integer.valueOf(i), c0767v3);
                }
            }
            if (!c3017sq.isEmpty()) {
                c0767v2Arr2 = (C0767v2[]) com.android.tools.r8.utils.B.a(C0767v2[].class, c0767v2Arr2, c3017sq);
            }
            C0767v2 c0767v4 = c0737r2.e;
            if (c0767v2 == c0767v4 && c0767v2Arr2 == c0737r2.f.a) {
                c0737r2B = c0737r2;
            } else {
                boolean z = Y5;
                if (!z && c0767v2 != c0767v4 && c0767v2.equals(c0767v4)) {
                    throw new AssertionError();
                }
                if (!z && c0767v2Arr2 != (c0767v2Arr = c0737r2.f.a) && Arrays.equals(c0767v2Arr2, c0767v2Arr)) {
                    throw new AssertionError();
                }
                c0737r2B = b(c0767v2, c0767v2Arr2);
            }
            abstractMap.put(c0737r2, c0737r2B);
        }
        return c0737r2B;
    }

    public C0684j2 a(C0767v2 c0767v2, C0737r2 c0737r2, C0759u2 c0759u2) {
        return (C0684j2) a(this.g, new C0684j2(c0767v2, c0737r2, c0759u2, this.R5));
    }

    public C0684j2 a(C0767v2 c0767v2, C0737r2 c0737r2, String str) {
        return a(c0767v2, c0737r2, c(str));
    }

    public C0684j2 a(MethodReference methodReference) {
        C0759u2 c0759u2C;
        final C0759u2[] c0759u2Arr = new C0759u2[methodReference.getFormalTypes().size()];
        com.android.tools.r8.utils.V1.a(methodReference.getFormalTypes(), new com.android.tools.r8.utils.U1() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda15
            @Override // com.android.tools.r8.utils.U1
            public final void accept(Object obj, int i) {
                this.f$0.a(c0759u2Arr, (TypeReference) obj, i);
            }
        });
        C0759u2 c0759u2C2 = c(methodReference.getHolderClass().getDescriptor());
        C0759u2 c0759u2C3 = c(methodReference.getMethodName());
        if (methodReference.getReturnType() == null) {
            c0759u2C = this.E;
        } else {
            c0759u2C = c(methodReference.getReturnType().getDescriptor());
        }
        return a(c0759u2C2, c0759u2C3, c0759u2C, c0759u2Arr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0759u2[] c0759u2Arr, TypeReference typeReference, int i) {
        c0759u2Arr[i] = c(typeReference.getDescriptor());
    }

    public final C0699l2 a(EnumC0691k2 enumC0691k2, AbstractC0642e2 abstractC0642e2, boolean z, C0684j2 c0684j2) {
        return (C0699l2) a(this.h, new C0699l2(enumC0691k2, abstractC0642e2, z, c0684j2));
    }

    public static C0721p0 a(C0759u2 c0759u2, C0737r2 c0737r2, C0699l2 c0699l2, List list) {
        return new C0721p0(c0759u2, c0737r2, c0699l2, list);
    }

    public C0684j2 a(C0759u2 c0759u2, C0759u2 c0759u3, C0759u2 c0759u4, C0759u2[] c0759u2Arr) {
        C0767v2 c0767v2C = c(c0759u2);
        C0767v2 c0767v2C2 = c(c0759u4);
        C0767v2[] c0767v2Arr = new C0767v2[c0759u2Arr.length];
        for (int i = 0; i < c0759u2Arr.length; i++) {
            c0767v2Arr[i] = c(c0759u2Arr[i]);
        }
        return a(c0767v2C, b(c0767v2C2, c0767v2Arr), c0759u3);
    }

    public final A0 a(int i) {
        A0 a0;
        synchronized (this.i) {
            a0 = (A0) this.i.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda8
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return new A0(((Integer) obj).intValue());
                }
            });
        }
        return a0;
    }

    public final B0.c a(C0759u2 c0759u2) {
        B0.c cVar;
        synchronized (this.p) {
            cVar = (B0.c) this.p.computeIfAbsent(c0759u2, new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return new B0.c((C0759u2) obj);
                }
            });
        }
        return cVar;
    }

    public final F0 a(AbstractC1915eM abstractC1915eM) {
        F0 f0;
        synchronized (this.q) {
            f0 = (F0) this.q.computeIfAbsent(new F0(abstractC1915eM), new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C0722p1.a((F0) obj);
                }
            });
        }
        return f0;
    }

    public final boolean a(C0684j2 c0684j2) {
        return c0684j2.f == this.Z0;
    }

    public final BS a(final C0767v2 c0767v2, final WH wh, final C0702m c0702m) {
        AbstractC3472z00 abstractC3472z00A;
        if (c0767v2.M0()) {
            BS bs = (BS) this.t.get(c0767v2);
            if (bs != null) {
                return bs.a(wh);
            }
            abstractC3472z00A = AbstractC3472z00.a(c0767v2.a(this), WH.i(), c0702m, true);
        } else {
            abstractC3472z00A = null;
        }
        final AbstractC3472z00 abstractC3472z00 = abstractC3472z00A;
        return ((BS) this.t.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0767v2, c0702m, wh, abstractC3472z00, (C0767v2) obj);
            }
        })).a(wh);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BS a(C0767v2 c0767v2, C0702m c0702m, WH wh, AbstractC3472z00 abstractC3472z00, C0767v2 c0767v3) {
        AbstractC3938n2 abstractC3938n2A;
        AbstractC0727q0 abstractC0727q0C;
        if (c0767v2.O0()) {
            if (!c0702m.q()) {
                return C1229Oa.a(c0767v2, wh);
            }
            if (!Y5 && !c0702m.f().h()) {
                throw new AssertionError();
            }
            if (!C0702m.P && !c0767v2.O0()) {
                throw new AssertionError();
            }
            if (!c0702m.q() || (abstractC0727q0C = c0702m.c(c0767v2)) == null) {
                abstractC3938n2A = AbstractC3938n2.c;
            } else {
                abstractC3938n2A = AbstractC3938n2.a(abstractC0727q0C.isInterface());
            }
            if (abstractC3938n2A.d()) {
                return C1229Oa.a(this.Z1, wh, (C0702m<? extends C0680j>) c0702m.h0(), C1513Xr.c(c0767v2));
            }
            return C1229Oa.b(c0767v2, wh, (C0702m<? extends C0680j>) c0702m.h0());
        }
        if (Y5 || c0767v2.M0()) {
            return C1969f3.a(abstractC3472z00, wh);
        }
        throw new AssertionError();
    }

    public final C1513Xr a(final C0702m c0702m, C0767v2 c0767v2) {
        return (C1513Xr) this.u.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.graph.p1$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0722p1.b(c0702m, (C0767v2) obj);
            }
        });
    }

    public final synchronized void a(Consumer consumer) {
        new ArrayList(this.d.values()).forEach(consumer);
    }
}
