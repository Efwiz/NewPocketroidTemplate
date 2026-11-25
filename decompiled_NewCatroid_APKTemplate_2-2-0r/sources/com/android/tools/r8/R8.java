package com.android.tools.r8;

import com.android.tools.r8.dex.C0408c;
import com.android.tools.r8.dex.C0548k;
import com.android.tools.r8.experimental.graphinfo.GraphConsumer;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0695l;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0738r3;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0791w3;
import com.android.tools.r8.graph.F5;
import com.android.tools.r8.graph.N2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.n5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.internal.AbstractC1265Pg;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC1745cA;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC2151hN;
import com.android.tools.r8.internal.AbstractC2292jE;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.AbstractC3256w3;
import com.android.tools.r8.internal.C0831Aj;
import com.android.tools.r8.internal.C0931Dn;
import com.android.tools.r8.internal.C0959Ek;
import com.android.tools.r8.internal.C0980Fe;
import com.android.tools.r8.internal.C1132Kj;
import com.android.tools.r8.internal.C1158Lj;
import com.android.tools.r8.internal.C1239Oj;
import com.android.tools.r8.internal.C1521Xz;
import com.android.tools.r8.internal.C1687bM;
import com.android.tools.r8.internal.C1695bT;
import com.android.tools.r8.internal.C1826dE;
import com.android.tools.r8.internal.C1892e8;
import com.android.tools.r8.internal.C2044g20;
import com.android.tools.r8.internal.C2079gT;
import com.android.tools.r8.internal.C2114gs;
import com.android.tools.r8.internal.C2141hE;
import com.android.tools.r8.internal.C2281j4;
import com.android.tools.r8.internal.C2487ln;
import com.android.tools.r8.internal.C2750p20;
import com.android.tools.r8.internal.C3012sl;
import com.android.tools.r8.internal.C3033t2;
import com.android.tools.r8.internal.C3254w2;
import com.android.tools.r8.internal.C3334x6;
import com.android.tools.r8.internal.C3448ye;
import com.android.tools.r8.internal.EQ;
import com.android.tools.r8.internal.EnumC3181v3;
import com.android.tools.r8.internal.F1;
import com.android.tools.r8.internal.IP;
import com.android.tools.r8.internal.InterfaceC2866qg;
import com.android.tools.r8.internal.InterfaceC2938rg;
import com.android.tools.r8.internal.InterfaceC3007sg;
import com.android.tools.r8.internal.InterfaceC3077tg;
import com.android.tools.r8.internal.J5;
import com.android.tools.r8.internal.MQ;
import com.android.tools.r8.internal.Q6;
import com.android.tools.r8.internal.S6;
import com.android.tools.r8.internal.UM;
import com.android.tools.r8.internal.W9;
import com.android.tools.r8.internal.WP;
import com.android.tools.r8.internal.WQ;
import com.android.tools.r8.internal.Y8;
import com.android.tools.r8.ir.optimize.C3546c0;
import com.android.tools.r8.ir.optimize.C3553e;
import com.android.tools.r8.ir.optimize.C3576o0;
import com.android.tools.r8.naming.C0;
import com.android.tools.r8.naming.C3630i0;
import com.android.tools.r8.naming.C3663t0;
import com.android.tools.r8.naming.O0;
import com.android.tools.r8.shaking.A4;
import com.android.tools.r8.shaking.C3678a;
import com.android.tools.r8.shaking.C3686a4;
import com.android.tools.r8.shaking.C3690b0;
import com.android.tools.r8.shaking.C3707d4;
import com.android.tools.r8.shaking.C3719f4;
import com.android.tools.r8.shaking.C3720g;
import com.android.tools.r8.shaking.C3728h;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3743k;
import com.android.tools.r8.shaking.C3749l;
import com.android.tools.r8.shaking.C3776p;
import com.android.tools.r8.shaking.C3829y;
import com.android.tools.r8.shaking.D4;
import com.android.tools.r8.shaking.N1;
import com.android.tools.r8.shaking.O1;
import com.android.tools.r8.shaking.S2;
import com.android.tools.r8.shaking.Y3;
import com.android.tools.r8.shaking.g4;
import com.android.tools.r8.shaking.k4;
import com.android.tools.r8.shaking.l4;
import com.android.tools.r8.shaking.x4;
import com.android.tools.r8.synthesis.C3861t;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.R0;
import com.android.tools.r8.utils.R2;
import com.android.tools.r8.utils.S0;
import com.android.tools.r8.utils.Y2;
import com.android.tools.r8.utils.y3;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class R8 {
    static final /* synthetic */ boolean c = true;
    private final y3 a;
    private final A1 b;

    private R8(A1 a1) {
        this.b = a1;
        if (a1.n) {
            System.gc();
        }
        this.a = y3.a(a1, "R8 8.1.56");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0767v2 c0767v2) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(C3945p c3945p, A1 a1, ExecutorService executorService) {
        try {
            new R8(a1).a(c3945p, executorService);
        } finally {
            executorService.shutdown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection c(C0702m c0702m) {
        return ((C0680j) c0702m.f()).b().e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Boolean d(C0702m c0702m) {
        C0791w3.a aVarA = C0791w3.b(c0702m, C0738r3.a(c0702m)).a(((C0680j) c0702m.f()).d());
        aVarA.getClass();
        return Boolean.valueOf(aVarA == C0791w3.a.VALID);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:8:0x0028  */
    public Boolean e(C0702m c0702m) {
        boolean z;
        if (this.b.o1()) {
            C0791w3.a aVarA = C0791w3.b(c0702m, C0738r3.a(c0702m)).a(((C0680j) c0702m.f()).d());
            aVarA.getClass();
            if (aVarA == C0791w3.a.VALID) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean f(C0702m c0702m) {
        return Boolean.valueOf(a(c0702m));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean g(C0702m c0702m) {
        return Boolean.valueOf(a(c0702m));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean h(C0702m c0702m) {
        return Boolean.valueOf(a(c0702m));
    }

    public static void main(final String[] strArr) {
        if (strArr.length == 0) {
            throw new RuntimeException(Y2.a("Invalid invocation.", R8Command.i()));
        }
        R0.a(new S0() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda14
            @Override // com.android.tools.r8.utils.S0
            public final void run() {
                R8.b(strArr);
            }
        });
    }

    public static void run(R8Command r8Command) throws CompilationFailedException {
        a(r8Command.a(), r8Command.b());
    }

    static void a(C0702m<?> c0702m, C3945p c3945p, ExecutorService executorService) throws ExecutionException {
        A1 a1Y = c0702m.Y();
        C0931Dn.a(a1Y.l, c0702m.f().d());
        try {
            com.android.tools.r8.dex.U uA0 = a1Y.A0();
            if (!c && uA0 == null) {
                throw new AssertionError();
            }
            if (a1Y.f instanceof ClassFileConsumer) {
                new C3334x6(c0702m, uA0).a((ClassFileConsumer) a1Y.f, c3945p);
            } else {
                C0548k.a(c0702m, uA0).a(c3945p, executorService);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot write application", e);
        }
    }

    private static N2 b(C0702m c0702m) {
        return c0702m.f().b().b();
    }

    public static void run(R8Command r8Command, final ExecutorService executorService) throws CompilationFailedException {
        final C3945p c3945pA = r8Command.a();
        final A1 a1B = r8Command.b();
        R0.a(r8Command.g(), new R0.a() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda20
            @Override // com.android.tools.r8.utils.R0.a
            public final void run() throws IOException {
                R8.a(c3945pA, a1B, executorService);
            }
        });
    }

    private void b(C0702m c0702m, ExecutorService executorService) {
        if (this.b.o1.isEmpty()) {
            return;
        }
        if (!c && !c0702m.E().h()) {
            throw new AssertionError();
        }
        c0702m.d(((C0680j) c0702m.f()).a(com.android.tools.r8.shaking.Z.a(c0702m, executorService, B5.a((C0702m<? extends C0680j>) c0702m)).b(executorService, this.a)));
    }

    static void a(final C3945p c3945p, final A1 a1) throws CompilationFailedException {
        final ExecutorService executorServiceA = C3879c3.a(a1);
        R0.a(a1.e, new R0.a() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.utils.R0.a
            public final void run() {
                R8.b(c3945p, a1, executorServiceA);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C3945p c3945p, A1 a1, ExecutorService executorService) throws IOException {
        new R8(a1).a(c3945p, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C0702m c0702m, ExecutorService executorService, C1132Kj c1132Kj) {
        c1132Kj.a(c0702m, this.a, executorService);
    }

    /* JADX WARN: Code duplicated, block: B:174:0x0555 A[Catch: all -> 0x068b, TryCatch #2 {all -> 0x068b, blocks: (B:164:0x051e, B:166:0x0526, B:168:0x0536, B:172:0x0541, B:174:0x0555, B:176:0x0560, B:178:0x0581, B:180:0x05a5, B:181:0x05aa, B:183:0x05b7, B:185:0x05bc, B:187:0x05c1, B:189:0x05ca, B:191:0x05cf, B:192:0x05dc, B:194:0x062d, B:197:0x0639, B:198:0x063e, B:199:0x063f), top: B:318:0x051e }] */
    /* JADX WARN: Code duplicated, block: B:175:0x055f  */
    /* JADX WARN: Code duplicated, block: B:178:0x0581 A[Catch: all -> 0x068b, TryCatch #2 {all -> 0x068b, blocks: (B:164:0x051e, B:166:0x0526, B:168:0x0536, B:172:0x0541, B:174:0x0555, B:176:0x0560, B:178:0x0581, B:180:0x05a5, B:181:0x05aa, B:183:0x05b7, B:185:0x05bc, B:187:0x05c1, B:189:0x05ca, B:191:0x05cf, B:192:0x05dc, B:194:0x062d, B:197:0x0639, B:198:0x063e, B:199:0x063f), top: B:318:0x051e }] */
    /* JADX WARN: Code duplicated, block: B:180:0x05a5 A[Catch: all -> 0x068b, TryCatch #2 {all -> 0x068b, blocks: (B:164:0x051e, B:166:0x0526, B:168:0x0536, B:172:0x0541, B:174:0x0555, B:176:0x0560, B:178:0x0581, B:180:0x05a5, B:181:0x05aa, B:183:0x05b7, B:185:0x05bc, B:187:0x05c1, B:189:0x05ca, B:191:0x05cf, B:192:0x05dc, B:194:0x062d, B:197:0x0639, B:198:0x063e, B:199:0x063f), top: B:318:0x051e }] */
    /* JADX WARN: Code duplicated, block: B:183:0x05b7 A[Catch: all -> 0x068b, TryCatch #2 {all -> 0x068b, blocks: (B:164:0x051e, B:166:0x0526, B:168:0x0536, B:172:0x0541, B:174:0x0555, B:176:0x0560, B:178:0x0581, B:180:0x05a5, B:181:0x05aa, B:183:0x05b7, B:185:0x05bc, B:187:0x05c1, B:189:0x05ca, B:191:0x05cf, B:192:0x05dc, B:194:0x062d, B:197:0x0639, B:198:0x063e, B:199:0x063f), top: B:318:0x051e }] */
    /* JADX WARN: Code duplicated, block: B:185:0x05bc A[Catch: all -> 0x068b, TryCatch #2 {all -> 0x068b, blocks: (B:164:0x051e, B:166:0x0526, B:168:0x0536, B:172:0x0541, B:174:0x0555, B:176:0x0560, B:178:0x0581, B:180:0x05a5, B:181:0x05aa, B:183:0x05b7, B:185:0x05bc, B:187:0x05c1, B:189:0x05ca, B:191:0x05cf, B:192:0x05dc, B:194:0x062d, B:197:0x0639, B:198:0x063e, B:199:0x063f), top: B:318:0x051e }] */
    /* JADX WARN: Code duplicated, block: B:187:0x05c1 A[Catch: all -> 0x068b, TryCatch #2 {all -> 0x068b, blocks: (B:164:0x051e, B:166:0x0526, B:168:0x0536, B:172:0x0541, B:174:0x0555, B:176:0x0560, B:178:0x0581, B:180:0x05a5, B:181:0x05aa, B:183:0x05b7, B:185:0x05bc, B:187:0x05c1, B:189:0x05ca, B:191:0x05cf, B:192:0x05dc, B:194:0x062d, B:197:0x0639, B:198:0x063e, B:199:0x063f), top: B:318:0x051e }] */
    /* JADX WARN: Code duplicated, block: B:189:0x05ca A[Catch: all -> 0x068b, TryCatch #2 {all -> 0x068b, blocks: (B:164:0x051e, B:166:0x0526, B:168:0x0536, B:172:0x0541, B:174:0x0555, B:176:0x0560, B:178:0x0581, B:180:0x05a5, B:181:0x05aa, B:183:0x05b7, B:185:0x05bc, B:187:0x05c1, B:189:0x05ca, B:191:0x05cf, B:192:0x05dc, B:194:0x062d, B:197:0x0639, B:198:0x063e, B:199:0x063f), top: B:318:0x051e }] */
    /* JADX WARN: Code duplicated, block: B:191:0x05cf A[Catch: all -> 0x068b, TryCatch #2 {all -> 0x068b, blocks: (B:164:0x051e, B:166:0x0526, B:168:0x0536, B:172:0x0541, B:174:0x0555, B:176:0x0560, B:178:0x0581, B:180:0x05a5, B:181:0x05aa, B:183:0x05b7, B:185:0x05bc, B:187:0x05c1, B:189:0x05ca, B:191:0x05cf, B:192:0x05dc, B:194:0x062d, B:197:0x0639, B:198:0x063e, B:199:0x063f), top: B:318:0x051e }] */
    /* JADX WARN: Code duplicated, block: B:201:0x0648  */
    /* JADX WARN: Code duplicated, block: B:204:0x065e A[Catch: all -> 0x0957, ExecutionException -> 0x095a, TryCatch #0 {ExecutionException -> 0x095a, blocks: (B:19:0x008a, B:21:0x00be, B:22:0x00c3, B:24:0x00cb, B:28:0x00d4, B:30:0x00e0, B:31:0x00e7, B:33:0x00fe, B:34:0x0105, B:36:0x010d, B:37:0x0114, B:39:0x011c, B:40:0x0123, B:42:0x0142, B:43:0x0161, B:92:0x0356, B:94:0x035d, B:97:0x036a, B:98:0x036f, B:99:0x0370, B:101:0x037e, B:103:0x038d, B:106:0x0399, B:107:0x039e, B:108:0x039f, B:110:0x03b5, B:112:0x03d5, B:114:0x03e4, B:116:0x040d, B:120:0x0416, B:122:0x0420, B:124:0x0427, B:126:0x0433, B:128:0x0439, B:130:0x0451, B:131:0x0455, B:134:0x0464, B:137:0x046b, B:138:0x0470, B:140:0x0473, B:141:0x0478, B:132:0x045b, B:142:0x0481, B:144:0x049a, B:146:0x04a3, B:149:0x04af, B:150:0x04b4, B:151:0x04b5, B:153:0x04bb, B:154:0x04c7, B:156:0x04d7, B:157:0x04dc, B:159:0x04e8, B:160:0x04f2, B:163:0x0517, B:202:0x064b, B:204:0x065e, B:206:0x066c, B:207:0x0675, B:212:0x0697, B:214:0x06a6, B:216:0x06b0, B:217:0x06b3, B:219:0x06c6, B:226:0x06e4, B:228:0x0706, B:230:0x0715, B:232:0x072c, B:234:0x074f, B:235:0x0756, B:238:0x0764, B:239:0x076b, B:241:0x077e, B:243:0x0788, B:245:0x0797, B:249:0x07df, B:251:0x07e7, B:253:0x07f1, B:255:0x0805, B:257:0x0815, B:258:0x081c, B:260:0x0824, B:266:0x0853, B:268:0x085c, B:271:0x0876, B:272:0x087b, B:273:0x087c, B:275:0x0884, B:277:0x088c, B:282:0x08a3, B:284:0x08ab, B:285:0x08c3, B:287:0x08c9, B:288:0x08cc, B:290:0x090a, B:293:0x0916, B:294:0x091b, B:295:0x091c, B:297:0x0929, B:298:0x0932, B:280:0x089a, B:246:0x07b9, B:248:0x07c1, B:229:0x0710, B:221:0x06d5, B:224:0x06de, B:225:0x06e3, B:209:0x068c, B:210:0x0692, B:305:0x0951, B:306:0x0956), top: B:317:0x008a, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:206:0x066c A[Catch: all -> 0x0957, ExecutionException -> 0x095a, TryCatch #0 {ExecutionException -> 0x095a, blocks: (B:19:0x008a, B:21:0x00be, B:22:0x00c3, B:24:0x00cb, B:28:0x00d4, B:30:0x00e0, B:31:0x00e7, B:33:0x00fe, B:34:0x0105, B:36:0x010d, B:37:0x0114, B:39:0x011c, B:40:0x0123, B:42:0x0142, B:43:0x0161, B:92:0x0356, B:94:0x035d, B:97:0x036a, B:98:0x036f, B:99:0x0370, B:101:0x037e, B:103:0x038d, B:106:0x0399, B:107:0x039e, B:108:0x039f, B:110:0x03b5, B:112:0x03d5, B:114:0x03e4, B:116:0x040d, B:120:0x0416, B:122:0x0420, B:124:0x0427, B:126:0x0433, B:128:0x0439, B:130:0x0451, B:131:0x0455, B:134:0x0464, B:137:0x046b, B:138:0x0470, B:140:0x0473, B:141:0x0478, B:132:0x045b, B:142:0x0481, B:144:0x049a, B:146:0x04a3, B:149:0x04af, B:150:0x04b4, B:151:0x04b5, B:153:0x04bb, B:154:0x04c7, B:156:0x04d7, B:157:0x04dc, B:159:0x04e8, B:160:0x04f2, B:163:0x0517, B:202:0x064b, B:204:0x065e, B:206:0x066c, B:207:0x0675, B:212:0x0697, B:214:0x06a6, B:216:0x06b0, B:217:0x06b3, B:219:0x06c6, B:226:0x06e4, B:228:0x0706, B:230:0x0715, B:232:0x072c, B:234:0x074f, B:235:0x0756, B:238:0x0764, B:239:0x076b, B:241:0x077e, B:243:0x0788, B:245:0x0797, B:249:0x07df, B:251:0x07e7, B:253:0x07f1, B:255:0x0805, B:257:0x0815, B:258:0x081c, B:260:0x0824, B:266:0x0853, B:268:0x085c, B:271:0x0876, B:272:0x087b, B:273:0x087c, B:275:0x0884, B:277:0x088c, B:282:0x08a3, B:284:0x08ab, B:285:0x08c3, B:287:0x08c9, B:288:0x08cc, B:290:0x090a, B:293:0x0916, B:294:0x091b, B:295:0x091c, B:297:0x0929, B:298:0x0932, B:280:0x089a, B:246:0x07b9, B:248:0x07c1, B:229:0x0710, B:221:0x06d5, B:224:0x06de, B:225:0x06e3, B:209:0x068c, B:210:0x0692, B:305:0x0951, B:306:0x0956), top: B:317:0x008a, outer: #1 }] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v2, types: [com.android.tools.r8.shaking.D4] */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r12v4 */
    /* JADX WARN: Type inference failed for: r22v0, types: [com.android.tools.r8.R8] */
    /* JADX WARN: Type inference failed for: r7v19, types: [com.android.tools.r8.experimental.graphinfo.GraphConsumer] */
    /* JADX WARN: Type inference failed for: r7v27 */
    /* JADX WARN: Type inference failed for: r7v28 */
    private void a(C3945p c3945p, final ExecutorService executorService) throws IOException {
        C3719f4 c3719f4;
        final C0702m c0702m;
        final ExecutorService executorService2;
        C3719f4 c3719f5;
        N2.a aVarC;
        C2079gT c2079gTA;
        MQ mqA;
        Object d4;
        ?? r12;
        ?? r7;
        final com.android.tools.r8.shaking.L lA;
        A1 a1;
        StringConsumer stringConsumer;
        boolean z = c;
        if (!z && this.b.f == null) {
            throw new AssertionError();
        }
        this.b.getClass();
        if (!z && !com.android.tools.r8.utils.E.a(this.b, new Supplier() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda23
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.a();
            }
        })) {
            throw new AssertionError();
        }
        if (this.b.n) {
            System.gc();
            System.gc();
            Runtime runtime = Runtime.getRuntime();
            System.out.println("R8 is running with total memory:" + runtime.totalMemory());
            System.out.println("R8 is running with free memory:" + runtime.freeMemory());
            System.out.println("R8 is running with max memory:" + runtime.maxMemory());
        }
        try {
            try {
                C0408c c0408c = new C0408c(c3945p, this.b, this.a);
                N2 n2I = c0408c.a(executorService).i();
                this.b.a(this.a, n2I);
                N1 n1A = c0408c.a(n2I);
                c3945p.c();
                final C0702m c0702mA = C0702m.a((AbstractC0681j0) n2I, n1A);
                c0702mA.a(C0695l.a((C0702m<?>) c0702mA).a());
                com.android.tools.r8.synthesis.D.a(c0702mA);
                if (!z) {
                    AbstractC3256w3.a(c0702mA, new EnumC3181v3[0]);
                }
                A1 a2 = this.b;
                a2.e0 = a2.X0() && AbstractC1745cA.a(c0702mA);
                if (!this.b.o1.isEmpty()) {
                    O1.a(c0702mA.f());
                }
                C3448ye.a(c0702mA);
                C2114gs.a(c0702mA.f(), this.b);
                C2281j4.a(this.b);
                if (this.b.w) {
                    AbstractC1265Pg.a(c0702mA.a());
                }
                if (this.b.C1()) {
                    EQ.b(c0702mA.a());
                }
                if (this.b.D1()) {
                    C2044g20.d(c0702mA.a());
                }
                Y8.a(c0702mA.a());
                S6 s6A = S6.a(c0702mA);
                Q6.a(c0702mA).a(executorService, s6A);
                s6A.b(c0702mA);
                if (c0702mA.D().d()) {
                    c0702mA.d(((C0680j) c0702mA.f()).a(c0702mA.D().a(((C0680j) c0702mA.f()).b())));
                }
                ArrayList arrayList = new ArrayList();
                this.a.a("Strip unused code");
                this.a.a("Before enqueuer");
                C3719f4 c3719f6 = new C3719f4(c0702mA);
                try {
                    if (this.b.Y0()) {
                        A1 a3 = this.b;
                        if (!com.android.tools.r8.shaking.Y2.a(a3.a, a3.D0().v())) {
                            A1 a4 = this.b;
                            arrayList.add(com.android.tools.r8.shaking.Y2.a(a4.a, a4.B0()));
                        }
                    }
                    AbstractC2151hN abstractC2151hNB = AbstractC2151hN.b(c0702mA);
                    C3743k c3743kA = C3749l.a();
                    B5 b5A = B5.a((C0702m<? extends C0680j>) c0702mA);
                    c0702mA.a(C3686a4.a((C0702m<? extends C0680j>) c0702mA, abstractC2151hNB, b5A, (Iterable<? extends S2>) AbstractC2734ot.a((List) this.b.D0().v(), arrayList)).a(c3743kA).a(executorService));
                    c0702mA.a(c3743kA.a());
                    if (!this.b.o1.isEmpty()) {
                        if (!z && !c0702mA.E().h()) {
                            throw new AssertionError();
                        }
                        c0702mA.a(Y3.a(c0702mA, abstractC2151hNB, b5A, this.b.o1).a(executorService));
                        ((C0680j) c0702mA.f()).m();
                    }
                    C3720g c3720gA = this.b.o1() ? C3728h.a(com.android.tools.r8.shaking.L.a.a) : null;
                    this.a.c();
                    this.a.a("Enqueuer");
                    C3720g c3720g = c3720gA;
                    final C0702m c0702mA2 = a(c3720gA, executorService, c0702mA, abstractC2151hNB, b5A, c3719f6);
                    this.a.c();
                    this.a.a("After enqueuer");
                    if (!z) {
                        c0702mA.b0().a(c0702mA2);
                    }
                    if (!z) {
                        c0702mA.b0().c(c0702mA2);
                    }
                    if (!z) {
                        c0702mA.b0().d(c0702mA2);
                    }
                    if (!z) {
                        c0702mA.b0().b(c0702mA);
                    }
                    if (!z) {
                        AbstractC3256w3.a(c0702mA, new EnumC3181v3[0]);
                    }
                    C3686a4 c3686a4B0 = c0702mA.b0();
                    A1 a5 = this.b;
                    c3686a4B0.getClass();
                    C3686a4.a(a5);
                    if (this.b.a().f) {
                        c0702mA.d().b();
                    }
                    if (this.b.z1 != null) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        PrintStream printStream = new PrintStream(byteArrayOutputStream);
                        C3707d4.a(((C0680j) c0702mA.f()).o(), printStream, new Predicate() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda2
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return R8.a((C0767v2) obj);
                            }
                        });
                        printStream.flush();
                        A1 a6 = this.b;
                        R0.a(a6.e, a6.z1, byteArrayOutputStream.toString());
                        A1 a7 = this.b;
                        R0.a(a7.e, a7.z1);
                    }
                    if (this.b.o1()) {
                        c0702mA.b(new InterfaceC3905h3() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda3
                            @Override // com.android.tools.r8.utils.InterfaceC3905h3
                            public final void accept(Object obj) {
                                R8.a((C0831Aj) obj);
                            }
                        });
                        C0738r3 c0738r3A = C0738r3.a(c0702mA);
                        C0791w3.a(c0702mA, c0738r3A).a(((C0680j) c0702mA.f()).d());
                        if (c0702mA.Y().x1().b()) {
                            c0702mA.Z().f.a();
                        }
                        u5 u5VarA = new k4(c0702mA2).a(executorService, this.a);
                        c0702mA.a(u5VarA, executorService);
                        new C3678a(c0702mA2, ((C3733i) c0702mA2.f()).r()).a();
                        c3720g.a(c0702mA2, u5VarA.e()).a().a(executorService);
                        new C1239Oj(c0702mA, c0738r3A).a(((C0680j) c0702mA.f()).d(), executorService);
                        if (!z && !c0702mA.a(new Supplier() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda4
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                return R8.f(c0702mA2);
                            }
                        })) {
                            throw new AssertionError();
                        }
                    }
                    this.a.c();
                    this.a.c();
                    if (!z && !((C0680j) c0702mA.f()).i()) {
                        throw new AssertionError();
                    }
                    final C0702m<C3733i> c0702mI0 = c0702mA.i0();
                    new C1892e8(c0702mI0).b(executorService);
                    if (!z) {
                        a(((C0680j) c0702mA.f()).d());
                    }
                    if (!z && !c0702mA.a(new Supplier() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda5
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return R8.g(c0702mI0);
                        }
                    })) {
                        throw new AssertionError();
                    }
                    b(c0702mA, executorService);
                    c0702mA.a().c();
                    if (this.b.D0().A() && c0702mA.b(F1.a(executorService, this.a, ((C3733i) c0702mI0.f()).b(), c0702mI0, ((C3733i) c0702mI0.f()).r()))) {
                        c3719f4 = null;
                        new WQ(c0702mA.i0()).a((C2141hE) null, executorService);
                    } else {
                        c3719f4 = null;
                    }
                    new C3576o0(c0702mI0).a(executorService, this.a);
                    new C1826dE(c0702mI0).c(executorService);
                    ((C0680j) c0702mA.f()).o().v().a(c0702mA);
                    boolean z2 = this.b.e0 && c0702mA.F();
                    g4 g4VarA = c3719f6.a(c0702mA.E());
                    if (!z) {
                        AbstractC3256w3.a(c0702mA, new EnumC3181v3[0]);
                    }
                    if (!z2 && this.b.D0().F()) {
                        if (this.b.t) {
                            this.a.a("VerticalClassMerger");
                            A4 a4B = new x4(b(c0702mI0), c0702mI0, executorService, this.a).b();
                            if (a4B != null) {
                                g4VarA = g4VarA.a(a4B);
                            }
                            this.a.c();
                        } else {
                            c0702mA.a(C2750p20.a());
                        }
                        if (!z && c0702mA.g0() == null) {
                            throw new AssertionError();
                        }
                        if (!z) {
                            AbstractC3256w3.a(c0702mA, new EnumC3181v3[0]);
                        }
                        C0959Ek.c(c0702mI0).a(executorService, this.a, g4VarA);
                    }
                    new IP(c0702mI0).a(executorService, this.a);
                    ((C0680j) c0702mA.f()).f().c();
                    if (!z) {
                        c0702mA.a().k();
                    }
                    if (!z && !c0702mA.a(new Supplier() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda6
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return R8.h(c0702mI0);
                        }
                    })) {
                        throw new AssertionError();
                    }
                    if (this.b.S) {
                        c0702mI0.d(new com.android.tools.r8.ir.optimize.R0(c0702mI0).a());
                    }
                    Set setA = ((C3733i) c0702mA.i0().f()).A();
                    if (!z) {
                        AbstractC3256w3.a(c0702mA, new EnumC3181v3[0]);
                    }
                    new UM(c0702mI0, this.a).b(c0702mI0, executorService);
                    if (!z) {
                        AbstractC3256w3.a(c0702mA, EnumC3181v3.a);
                    }
                    c0702mA.a().c();
                    this.a.a("AppliedGraphLens construction");
                    c0702mA.b((AbstractC1476Wj) new C3254w2(c0702mA));
                    this.a.c();
                    if (this.b.F1()) {
                        this.a.a("Post optimization code stripping");
                        try {
                            if (this.b.o1()) {
                                GraphConsumer graphConsumer = this.b.F1;
                                if (c0702mA.b0().g.isEmpty()) {
                                    r12 = c3719f4;
                                    r7 = graphConsumer;
                                } else {
                                    d4 = new D4(graphConsumer);
                                }
                                lA = com.android.tools.r8.shaking.Z.a(c0702mA, executorService, B5.a((C0702m<? extends C0680j>) c0702mA), r7, setA);
                                if (this.b.a(lA.i())) {
                                    C3719f4 c3719f7 = new C3719f4(c0702mA);
                                    c3719f7.a(lA);
                                    c3719f5 = c3719f7;
                                } else {
                                    c3719f5 = c3719f4;
                                }
                                c0702mA.d(lA.a(c0702mA.b0(), executorService, this.a).a());
                                c0702mA.c(new InterfaceC3905h3() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda7
                                    @Override // com.android.tools.r8.utils.InterfaceC3905h3
                                    public final void accept(Object obj) {
                                        this.f$0.a(c0702mI0, executorService, (C1132Kj) obj);
                                    }
                                });
                                if (this.b.o1()) {
                                    l4 l4Var = (l4) c0702mA.a(C3829y.a(), new Function() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda8
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            return R8.a(lA, (C0831Aj) obj);
                                        }
                                    });
                                    C0738r3 c0738r3A2 = C0738r3.a(c0702mA);
                                    u5 u5VarA2 = new k4(c0702mI0, l4Var).a(this.a, setA, executorService);
                                    a1 = this.b;
                                    stringConsumer = a1.y1;
                                    if (stringConsumer != null) {
                                        R0.a(a1.e, stringConsumer);
                                    }
                                    c0702mA.a(u5VarA2, executorService);
                                    new J5(c0702mI0).a();
                                    if (!z) {
                                        C3546c0.b(c0702mI0);
                                    }
                                    if (!z) {
                                        C3546c0.a(c0702mA);
                                    }
                                    if (!z) {
                                        c0702mA.c().a(c0702mI0);
                                    }
                                    if (!z) {
                                        c0702mA.f0();
                                    }
                                    if (!z) {
                                        ((C3733i) c0702mA.i0().f()).G();
                                    }
                                    executorService2 = executorService;
                                    a(c0702mA.b0(), new Supplier() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda9
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            return R8.c(c0702mA);
                                        }
                                    }, r12, c0702mA, lA, false, this.b, this.a, executorService);
                                    C3728h.a(com.android.tools.r8.shaking.L.a.b).a(c0702mA.i0(), u5VarA2.e()).a(executorService2);
                                    c0702m = c0702mA;
                                    new C1239Oj(c0702m, c0738r3A2).a(((C0680j) c0702m.f()).d(), executorService2);
                                    if (!z && !c0702m.a(new Supplier() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda10
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            return R8.d(c0702m);
                                        }
                                    })) {
                                        throw new AssertionError("Could not validate generic signatures");
                                    }
                                    new C3776p(c0702mI0).a(executorService2);
                                } else {
                                    c0702m = c0702mA;
                                    executorService2 = executorService;
                                }
                                this.a.c();
                                if (c0702m.Y().x1().c()) {
                                    if (c0702m.Y().x1().b()) {
                                        c0702m.Z().f.c();
                                    }
                                    final C3012sl c3012sl = new C3012sl(c0702m);
                                    c0702m.d(new InterfaceC3905h3() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda24
                                        @Override // com.android.tools.r8.utils.InterfaceC3905h3
                                        public final void accept(Object obj) {
                                            this.f$0.a(c3012sl, executorService2, (C1158Lj) obj);
                                        }
                                    });
                                    c0702m.b(new InterfaceC3905h3() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda25
                                        @Override // com.android.tools.r8.utils.InterfaceC3905h3
                                        public final void accept(Object obj) {
                                            this.f$0.a(c3012sl, executorService2, (C0831Aj) obj);
                                        }
                                    });
                                }
                            } else {
                                d4 = c3719f4;
                            }
                            r12 = d4;
                            r7 = d4;
                            lA = com.android.tools.r8.shaking.Z.a(c0702mA, executorService, B5.a((C0702m<? extends C0680j>) c0702mA), r7, setA);
                            if (this.b.a(lA.i())) {
                                C3719f4 c3719f8 = new C3719f4(c0702mA);
                                c3719f8.a(lA);
                                c3719f5 = c3719f8;
                            } else {
                                c3719f5 = c3719f4;
                            }
                            c0702mA.d(lA.a(c0702mA.b0(), executorService, this.a).a());
                            c0702mA.c(new InterfaceC3905h3() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda7
                                @Override // com.android.tools.r8.utils.InterfaceC3905h3
                                public final void accept(Object obj) {
                                    this.f$0.a(c0702mI0, executorService, (C1132Kj) obj);
                                }
                            });
                            if (this.b.o1()) {
                                l4 l4Var2 = (l4) c0702mA.a(C3829y.a(), new Function() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda8
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return R8.a(lA, (C0831Aj) obj);
                                    }
                                });
                                C0738r3 c0738r3A3 = C0738r3.a(c0702mA);
                                u5 u5VarA3 = new k4(c0702mI0, l4Var2).a(this.a, setA, executorService);
                                a1 = this.b;
                                stringConsumer = a1.y1;
                                if (stringConsumer != null) {
                                    R0.a(a1.e, stringConsumer);
                                }
                                c0702mA.a(u5VarA3, executorService);
                                new J5(c0702mI0).a();
                                if (!z) {
                                    C3546c0.b(c0702mI0);
                                }
                                if (!z) {
                                    C3546c0.a(c0702mA);
                                }
                                if (!z) {
                                    c0702mA.c().a(c0702mI0);
                                }
                                if (!z) {
                                    c0702mA.f0();
                                }
                                if (!z) {
                                    ((C3733i) c0702mA.i0().f()).G();
                                }
                                executorService2 = executorService;
                                a(c0702mA.b0(), new Supplier() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda9
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        return R8.c(c0702mA);
                                    }
                                }, r12, c0702mA, lA, false, this.b, this.a, executorService);
                                C3728h.a(com.android.tools.r8.shaking.L.a.b).a(c0702mA.i0(), u5VarA3.e()).a(executorService2);
                                c0702m = c0702mA;
                                new C1239Oj(c0702m, c0738r3A3).a(((C0680j) c0702m.f()).d(), executorService2);
                                if (!z) {
                                    throw new AssertionError("Could not validate generic signatures");
                                }
                                new C3776p(c0702mI0).a(executorService2);
                            } else {
                                c0702m = c0702mA;
                                executorService2 = executorService;
                            }
                            this.a.c();
                            if (c0702m.Y().x1().c()) {
                                if (c0702m.Y().x1().b()) {
                                    c0702m.Z().f.c();
                                }
                                final C3012sl c3012sl2 = new C3012sl(c0702m);
                                c0702m.d(new InterfaceC3905h3() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda24
                                    @Override // com.android.tools.r8.utils.InterfaceC3905h3
                                    public final void accept(Object obj) {
                                        this.f$0.a(c3012sl2, executorService2, (C1158Lj) obj);
                                    }
                                });
                                c0702m.b(new InterfaceC3905h3() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda25
                                    @Override // com.android.tools.r8.utils.InterfaceC3905h3
                                    public final void accept(Object obj) {
                                        this.f$0.a(c3012sl2, executorService2, (C0831Aj) obj);
                                    }
                                });
                            }
                        } catch (Throwable th) {
                            this.a.c();
                            throw th;
                        }
                    } else {
                        arrayList = arrayList;
                        c0702m = c0702mA;
                        executorService2 = executorService;
                        c3719f5 = null;
                    }
                    a(c0702m, executorService2);
                    if (((C0680j) c0702m.f()).i() && (mqA = MQ.a(c0702m.i0())) != null) {
                        mqA.a();
                    }
                    C2141hE c2141hEA = AbstractC2292jE.a(c0702m, executorService2);
                    c0702m.b((AbstractC1476Wj) c2141hEA);
                    if (((C0680j) c0702m.f()).i()) {
                        new WQ(c0702m.i0()).a(c2141hEA, executorService2);
                    } else if (!z && this.b.o1()) {
                        throw new AssertionError();
                    }
                    c0702m.a(c0702m.s().b(c0702m));
                    c0702m.a(c0702m.C().c(c0702m));
                    if (((C0680j) c0702m.f()).i()) {
                        C3861t.c(c0702m.i0(), this.a, executorService2);
                    } else {
                        C3861t.b(c0702m, this.a, executorService2);
                    }
                    this.a.a("read -applymapping file");
                    c0702m.X();
                    this.a.c();
                    if (this.b.n1() && (c2079gTA = new C1695bT(c0702m.i0()).a((aVarC = ((C0680j) c0702m.f()).b().b().c()), executorService2, this.a)) != null) {
                        c0702m.a(aVarC.b(), c2079gTA);
                    }
                    if (((C0680j) c0702m.f()).i() && !z) {
                        C1695bT.a(c0702m.i0());
                    }
                    c0702m.a().c();
                    C0738r3 c0738r3A4 = C0738r3.a(c0702m);
                    C0959Ek.b(c0702m).a(executorService2, this.a, c3719f5 != null ? c3719f5.a(c0702m.E()) : null);
                    if (this.b.D0().x()) {
                        this.a.a("apply-mapping");
                        c0702m.a(new C0(c0702m.i0()).a(executorService2, this.a));
                        this.a.c();
                        c0702m.l();
                    } else if (this.b.k1()) {
                        this.a.a("Minification");
                        c0702m.a(new C3630i0(c0702m.i0()).b(executorService2, this.a));
                        this.a.c();
                    }
                    if (!this.b.k1() && c0702m.Y().n1.u0 && ((C0680j) c0702m.f()).b().f().h()) {
                        new C3630i0(c0702m.i0()).a(executorService2, this.a);
                    }
                    if (!z) {
                        a(c0702m, b(c0702m));
                    }
                    if (this.b.L0()) {
                        System.out.println("Finished compilation with method filter: ");
                        ((AbstractC1639am) this.b.r0).forEach(new Consumer() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda26
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                R8.a((String) obj);
                            }
                        });
                        c3945p.b(this.b.e);
                        this.b.G1();
                        if (this.b.m) {
                            this.a.d();
                            return;
                        }
                        return;
                    }
                    if (!z) {
                        b(c0702m).m();
                    }
                    if (!z && !((C0680j) c0702m.f()).d().stream().allMatch(new Predicate() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda27
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return this.f$0.a((C0729q2) obj);
                        }
                    })) {
                        throw new AssertionError();
                    }
                    if ((this.b.o1() || this.b.k1() || this.b.D0().x()) && !z) {
                        c0702m.b0().b(c0702m);
                    }
                    if (!z && !this.b.n1.k0) {
                        c0702m.E().a(c0702m, new C0408c(c3945p.r(), this.b, this.a).b(executorService2));
                    }
                    Consumer consumer = this.b.I1;
                    if (consumer != null) {
                        consumer.accept(arrayList);
                    }
                    c0702m.a(C3663t0.a(c0702m));
                    c0702m.a(O0.a(c0702m));
                    new C3033t2(c0702m).a(executorService2);
                    this.a.a("MinifyKotlinMetadata");
                    new C1521Xz(c0702m).b(executorService2);
                    this.a.c();
                    new C1239Oj(c0702m, c0738r3A4).a(((C0680j) c0702m.f()).d(), executorService2);
                    if (!z && !c0702m.a(new Supplier() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda1
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return this.f$0.e(c0702m);
                        }
                    })) {
                        throw new AssertionError("Could not validate generic signatures");
                    }
                    new C0980Fe(c0702m).a();
                    a((C0702m<?>) c0702m, c3945p, executorService2);
                    if (!z) {
                        c0702m.u().a(this.b);
                    }
                    this.b.v1();
                    c3945p.b(this.b.e);
                    this.b.G1();
                    if (this.b.m) {
                        this.a.d();
                    }
                } catch (Throwable th2) {
                    this.a.c();
                    throw th2;
                }
            } catch (ExecutionException e) {
                throw R0.a(e);
            }
        } catch (Throwable th3) {
            c3945p.b(this.b.e);
            this.b.G1();
            if (this.b.m) {
                this.a.d();
                throw th3;
            }
            throw th3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static void b(String[] strArr) {
        final R8Command r8Command = (R8Command) R8Command.parse(strArr, com.android.tools.r8.origin.a.e).build();
        if (r8Command.isPrintHelp()) {
            R2.a();
            System.out.println(R8Command.i());
        } else if (r8Command.isPrintVersion()) {
            System.out.println("R8 " + Version.getVersionString());
        } else {
            final A1 a1B = r8Command.b();
            final ExecutorService executorServiceA = C3879c3.a(a1B);
            try {
                R0.c(a1B.e, new R0.a() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda18
                    @Override // com.android.tools.r8.utils.R0.a
                    public final void run() throws IOException {
                        R8.a(r8Command, a1B, executorServiceA);
                    }
                });
            } finally {
                executorServiceA.shutdown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean a() {
        return Boolean.valueOf(!this.b.n1.s0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0831Aj c0831Aj) {
        c0831Aj.a(com.android.tools.r8.shaking.L.a.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0702m c0702m, ExecutorService executorService, C1132Kj c1132Kj) {
        c1132Kj.a(c0702m, this.a, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ l4 a(com.android.tools.r8.shaking.L l, C0831Aj c0831Aj) {
        return c0831Aj.a(l.i());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C3012sl c3012sl, ExecutorService executorService, C1158Lj c1158Lj) {
        c1158Lj.a(c3012sl, executorService, this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C3012sl c3012sl, ExecutorService executorService, C0831Aj c0831Aj) {
        c0831Aj.a(c3012sl, executorService, this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str) {
        System.out.println("  - " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0729q2 c0729q2) {
        c0729q2.a(this.b);
        return true;
    }

    private static boolean a(C0702m c0702m) {
        A1.a aVarA = c0702m.Y().a();
        if (aVarA.a && aVarA.c && !c0702m.Y().U0) {
            Iterator it = ((C0680j) c0702m.f()).e().iterator();
            while (it.hasNext()) {
                ((C0729q2) it.next()).k(new Consumer() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda22
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        R8.a((n5) obj);
                    }
                });
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(n5 n5Var) {
        if (!c && n5Var.e().H0().J()) {
            throw new AssertionError("Every member should have been analyzed");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void a(final C0702m c0702m, ExecutorService executorService) {
        D4 d4;
        if (this.b.o1.isEmpty()) {
            return;
        }
        if (!c && c0702m.y() == null) {
            throw new AssertionError();
        }
        GraphConsumer d5 = this.b.G1;
        if (c0702m.y().g.isEmpty()) {
            d4 = 0;
        } else {
            d5 = new D4(d5);
            d4 = d5;
        }
        com.android.tools.r8.shaking.L lA = com.android.tools.r8.shaking.Z.a(c0702m, executorService, B5.a((C0702m<? extends C0680j>) c0702m), d5);
        final N1 n1B = lA.b(executorService, this.a);
        c0702m.d(((C0680j) c0702m.f()).a(n1B));
        a(c0702m.y(), new Supplier() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return R8.a(n1B, c0702m);
            }
        }, d4, c0702m, lA, true, this.b, this.a, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection a(N1 n1, final C0702m c0702m) {
        final ArrayList arrayList = new ArrayList();
        n1.a(new Consumer() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                R8.a(c0702m, arrayList, (C0767v2) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, ArrayList arrayList, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2);
        if (!c && !abstractC0727q0C.e0()) {
            throw new AssertionError();
        }
        arrayList.add(abstractC0727q0C.a0());
    }

    private static void a(final C0702m c0702m, N2 n2) {
        n2.e().forEach(new Consumer() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                R8.a(c0702m, (C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final C0702m c0702m, C0729q2 c0729q2) {
        c0729q2.l(new Consumer() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                R8.a(c0702m, (o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0702m c0702m, o5 o5Var) {
        com.android.tools.r8.graph.W wA1;
        C0684j2 c0684j2A = c0702m.E().a((AbstractC1476Wj) null, o5Var.getReference());
        if (c0684j2A == o5Var.getReference() || (wA1 = o5Var.e().a1()) == null) {
            return;
        }
        if (!wA1.z0() && !wA1.B0()) {
            if (!c && !(wA1 instanceof com.android.tools.r8.graph.Z) && !(wA1 instanceof F5)) {
                throw new AssertionError();
            }
            return;
        }
        if (c) {
            return;
        }
        a(wA1, c0684j2A, o5Var);
    }

    private static void a(com.android.tools.r8.graph.W w, final C0684j2 c0684j2, final o5 o5Var) {
        w.d(new Consumer() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                R8.a(c0684j2, o5Var, (AbstractC1915eM) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final C0684j2 c0684j2, o5 o5Var, AbstractC1915eM abstractC1915eM) {
        abstractC1915eM.getClass();
        if (abstractC1915eM instanceof C1687bM) {
            abstractC1915eM.j().a(new BiConsumer() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda11
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    R8.a(c0684j2, (Integer) obj, (AbstractC1915eM) obj2);
                }
            });
        } else if (!o5Var.e().N0() && !c && abstractC1915eM.h().g() != c0684j2) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0684j2 c0684j2, Integer num, AbstractC1915eM abstractC1915eM) {
        if (!c && !abstractC1915eM.a(c0684j2)) {
            throw new AssertionError();
        }
    }

    private C0702m a(C3720g c3720g, final ExecutorService executorService, C0702m c0702m, AbstractC2151hN abstractC2151hN, B5 b5, C3719f4 c3719f4) throws Throwable {
        this.a.a("Set up enqueuer");
        com.android.tools.r8.shaking.L lA = com.android.tools.r8.shaking.Z.a((C0702m<? extends C0680j>) c0702m, abstractC2151hN, executorService, b5);
        lA.a(c3720g);
        if (c0702m.Y().U) {
            lA.b(new C2487ln(c0702m));
        }
        if (C3553e.a(c0702m.Y())) {
            W9 w9 = new W9(c0702m, com.android.tools.r8.ir.optimize.info.r.b());
            lA.b(w9);
            lA.a((InterfaceC3007sg) w9);
        }
        if (this.b.a(lA.i())) {
            lA.a((InterfaceC3077tg) c3719f4).a((InterfaceC2866qg) c3719f4).a((InterfaceC2938rg) c3719f4);
        }
        this.a.c();
        this.a.a("Trace application");
        C3690b0 c3690b0A = lA.a(c0702m.b0(), executorService, this.a);
        if (!c) {
            abstractC2151hN.d();
        }
        this.a.c();
        this.a.a("Finalize enqueuer result");
        final C0702m c0702mD = c0702m.d(c3690b0A.a());
        if (A1.b()) {
            c0702mD.e(new InterfaceC3905h3() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda15
                @Override // com.android.tools.r8.utils.InterfaceC3905h3
                public final void accept(Object obj) {
                    R8.a(c0702mD, (WP) obj);
                }
            });
        }
        c0702m.c(new InterfaceC3905h3() { // from class: com.android.tools.r8.R8$$ExternalSyntheticLambda16
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.b(c0702mD, executorService, (C1132Kj) obj);
            }
        });
        this.a.c();
        return c0702mD;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, WP wp) {
        wp.a(((C3733i) c0702m.f()).s());
    }

    static void a(C3686a4 c3686a4, Supplier supplier, D4 d4, C0702m c0702m, com.android.tools.r8.shaking.L l, boolean z, A1 a1, y3 y3Var, ExecutorService executorService) {
        if (d4 != null) {
            AbstractC2963s10 it = c3686a4.g.iterator();
            while (it.hasNext()) {
                d4.a(l.g().a((AbstractC0744s2) it.next()), System.out);
            }
        }
        if (c0702m.Y().n1.b0) {
            return;
        }
        ArrayList arrayListA = (z ? com.android.tools.r8.shaking.D.b(c0702m) : com.android.tools.r8.shaking.D.a(c0702m)).a((Collection) supplier.get(), executorService);
        if (arrayListA.isEmpty()) {
            return;
        }
        if (d4 == null) {
            d4 = new D4(null);
            B5 b5A = B5.a((C0702m<? extends C0680j>) c0702m);
            if (z) {
                l = com.android.tools.r8.shaking.Z.a(c0702m, executorService, b5A, (GraphConsumer) d4);
                l.b(executorService, y3Var);
            } else {
                l = com.android.tools.r8.shaking.Z.a(c0702m, executorService, b5A, d4);
                l.a(c3686a4, executorService, y3Var);
            }
        }
        a1.e.error(new com.android.tools.r8.errors.c().a(arrayListA, l.g(), d4).a());
        a1.e.a();
    }

    private static void a(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            for (W0 w0 : ((C0729q2) it.next()).M1()) {
                if (w0.a1() != null && !c) {
                    w0.a1().G0();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(R8Command r8Command, A1 a1, ExecutorService executorService) throws IOException {
        new R8(a1).a(r8Command.a(), executorService);
    }
}
