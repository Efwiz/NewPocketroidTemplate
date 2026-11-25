package com.android.tools.r8.dex;

import com.android.tools.r8.DataResourceProvider;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.ProgramResourceProvider;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.errors.UnsupportedMainDexListUsageDiagnostic;
import com.android.tools.r8.graph.AbstractC0674i0;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0612b4;
import com.android.tools.r8.graph.C0696l0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.N2;
import com.android.tools.r8.internal.C1036Hf;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.shaking.L1;
import com.android.tools.r8.shaking.N1;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.H0;
import com.android.tools.r8.utils.O0;
import com.android.tools.r8.utils.StringDiagnostic;
import com.android.tools.r8.utils.X1;
import com.android.tools.r8.utils.y3;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.dex.c, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0408c {
    static final /* synthetic */ boolean f = true;
    private final A1 a;
    private final C0722p1 b;
    private final y3 c;
    private final C3945p d;
    private C0696l0 e;

    public C0408c(C3945p c3945p, A1 a1, y3 y3Var) {
        this.a = a1;
        this.b = a1.a;
        this.c = y3Var;
        this.d = c3945p;
    }

    public C0612b4 a() throws IOException {
        return a((com.android.tools.r8.d0) null);
    }

    public final C0612b4 b(ExecutorService executorService) {
        return a(this.d.m(), executorService, O0.c());
    }

    public C0612b4 a(com.android.tools.r8.d0 d0Var) throws IOException {
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        try {
            return a(d0Var, executorServiceNewSingleThreadExecutor);
        } finally {
            executorServiceNewSingleThreadExecutor.shutdown();
        }
    }

    public final C0612b4 a(ExecutorService executorService) throws IOException {
        return a(this.d.m(), executorService);
    }

    public final C0612b4 a(com.android.tools.r8.d0 d0Var, ExecutorService executorService) throws IOException {
        return a(d0Var, executorService, this.a.u0());
    }

    public final C0612b4 a(com.android.tools.r8.d0 d0Var, ExecutorService executorService, O0 o0) {
        boolean z = f;
        if (!z) {
            C3945p c3945p = this.d;
            A1 a1 = this.a;
            if (a1.Y0()) {
                if (!a1.B0().e(EnumC3891f.L)) {
                    if (!z && !a1.o1.isEmpty()) {
                        throw new AssertionError();
                    }
                    if (!z && a1.w1 != null) {
                        throw new AssertionError();
                    }
                    if (!z && c3945p.o()) {
                        throw new AssertionError();
                    }
                }
            }
        }
        a(o0);
        if (this.a.n1.m0) {
            this.d.q();
        }
        this.c.a("DexApplication.read");
        C0612b4.a aVarA = AbstractC0681j0.a(this.a, this.c);
        try {
            try {
                try {
                    ArrayList arrayList = new ArrayList();
                    a(d0Var, aVarA, executorService, arrayList);
                    C0406a c0406a = new C0406a(this, executorService, arrayList);
                    c0406a.b();
                    C3879c3.a(arrayList);
                    C0696l0 c0696l0A = c0406a.a();
                    this.e = c0696l0A;
                    aVarA.a(c0696l0A);
                    c0406a.a(aVarA);
                    Iterator<ProgramResourceProvider> it = this.d.l().iterator();
                    while (it.hasNext()) {
                        DataResourceProvider dataResourceProvider = it.next().getDataResourceProvider();
                        if (dataResourceProvider != null) {
                            aVarA.a(dataResourceProvider);
                        }
                    }
                    this.c.c();
                    return aVarA.b();
                } catch (ResourceException e) {
                    this.a.e.a(new StringDiagnostic(e.getMessage(), e.getOrigin()));
                    throw null;
                }
            } catch (ExecutionException e2) {
                throw new RuntimeException(e2);
            }
        } catch (Throwable th) {
            this.c.c();
            throw th;
        }
    }

    private void a(O0 o0) {
        C1036Hf c1036Hf = this.a.l0;
        if (c1036Hf == null || !o0.a(c1036Hf)) {
            return;
        }
        Path pathB = o0.b();
        this.c.a("ApplicationReader.dump");
        this.d.a(pathB, c1036Hf, this.a);
        this.c.c();
        StringDiagnostic stringDiagnostic = new StringDiagnostic("Dumped compilation inputs to: " + pathB);
        if (!o0.d()) {
            this.a.e.info(stringDiagnostic);
        } else {
            this.a.e.a(stringDiagnostic);
            throw null;
        }
    }

    public final N1 a(AbstractC0681j0 abstractC0681j0) {
        return a(abstractC0681j0, this.e.f());
    }

    public final N1 a(N2 n2) {
        return a((AbstractC0681j0) n2, true);
    }

    private N1 a(AbstractC0681j0 abstractC0681j0, boolean z) {
        L1 l1A = N1.f().a();
        if (this.d.o()) {
            for (com.android.tools.r8.d0 d0Var : this.d.k()) {
                if (z) {
                    this.a.e.warning(new UnsupportedMainDexListUsageDiagnostic(d0Var.getOrigin()));
                }
                a(abstractC0681j0, l1A, X1.a(d0Var, this.b));
            }
            if (!this.d.j().isEmpty()) {
                if (z) {
                    this.a.e.warning(new UnsupportedMainDexListUsageDiagnostic(Origin.unknown()));
                }
                a(abstractC0681j0, l1A, (Iterable) this.d.j().stream().map(new Function() { // from class: com.android.tools.r8.dex.c$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.a((String) obj);
                    }
                }).collect(Collectors.toList()));
            }
        }
        return l1A.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0767v2 a(String str) {
        return this.b.e(C3970u0.H(str));
    }

    private void a(AbstractC0681j0 abstractC0681j0, L1 l1, Iterable iterable) {
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            C0767v2 c0767v2 = (C0767v2) it.next();
            C0729q2 c0729q2D = abstractC0681j0.d(c0767v2);
            if (c0729q2D != null) {
                l1.a(c0729q2D);
            } else {
                A1 a1 = this.a;
                a1.getClass();
                a1.e.warning(new StringDiagnostic("Application does not contain `" + c0767v2.r0() + "` as referenced in main-dex-list."));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(H0 h0) {
        throw new C1367Tb("Dex file with version '" + h0.b() + "' cannot be used with min sdk level '" + this.a.B0() + "'.");
    }

    private void a(final com.android.tools.r8.d0 d0Var, final C0612b4.a aVar, ExecutorService executorService, ArrayList arrayList) {
        if (d0Var == null) {
            return;
        }
        arrayList.add(executorService.submit(new Runnable() { // from class: com.android.tools.r8.dex.c$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(d0Var, aVar);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.d0 d0Var, AbstractC0674i0 abstractC0674i0) {
        try {
            String strA = d0Var.a();
            A1 a1 = this.a;
            abstractC0674i0.a(C3605b.a(strA, (DiagnosticsHandler) a1.e, a1.t1().b, this.a.n1.J0, false));
        } catch (ResourceException | IOException e) {
            throw new C1367Tb("Failure to read proguard map file", e, d0Var.getOrigin(), Position.UNKNOWN);
        }
    }
}
