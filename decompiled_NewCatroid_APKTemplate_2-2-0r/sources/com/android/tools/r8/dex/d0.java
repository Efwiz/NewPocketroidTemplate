package com.android.tools.r8.dex;

import com.android.tools.r8.DataDirectoryResource;
import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.V5;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.shaking.C3840z3;
import com.android.tools.r8.shaking.L2;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.StringDiagnostic;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class d0 {
    static final /* synthetic */ boolean f = true;
    private final C0702m a;
    private final C0722p1 b;
    private final AbstractC1476Wj c;
    private final AbstractC3649o0 d;
    private final A1 e;

    public d0(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = c0702m.E();
        this.d = c0702m.z();
        this.e = c0702m.Y();
    }

    public static boolean c(DataEntryResource dataEntryResource) {
        return dataEntryResource.getName().startsWith("META-INF/services/");
    }

    public final DataDirectoryResource a(DataDirectoryResource dataDirectoryResource) {
        if (this.e.D0() == null) {
            if (f || this.e.n1.v0) {
                return null;
            }
            throw new AssertionError();
        }
        if (!this.e.D0().i().a(dataDirectoryResource.getName())) {
            return null;
        }
        Y y = new Y(this, dataDirectoryResource.getName());
        return DataDirectoryResource.fromName(y.d() ? y.c() : dataDirectoryResource.getName(), dataDirectoryResource.getOrigin());
    }

    public final DataEntryResource b(DataEntryResource dataEntryResource) {
        String name2;
        if (a(dataEntryResource, this.e, new Function() { // from class: com.android.tools.r8.dex.d0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((L2) obj).c();
            }
        })) {
            c0 b0Var = dataEntryResource.getName().startsWith("META-INF/services/") ? new b0(this, dataEntryResource.getName()) : new X(this, dataEntryResource.getName());
            name2 = b0Var.d() ? b0Var.c() : dataEntryResource.getName();
        } else {
            name2 = dataEntryResource.getName();
        }
        if (!f && name2 == null) {
            throw new AssertionError();
        }
        byte[] bArrA = a(dataEntryResource, this.e, new Function() { // from class: com.android.tools.r8.dex.d0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((L2) obj).b();
            }
        }) ? a(dataEntryResource) : null;
        if (bArrA != null) {
            return DataEntryResource.fromBytes(bArrA, name2, dataEntryResource.getOrigin());
        }
        return !name2.equals(dataEntryResource.getName()) ? dataEntryResource.withName(name2) : dataEntryResource;
    }

    private static boolean a(DataEntryResource dataEntryResource, A1 a1, Function function) {
        L2 l2D0 = a1.D0();
        if (l2D0 == null) {
            if (f || a1.n1.v0) {
                return false;
            }
            throw new AssertionError();
        }
        C3840z3 c3840z3 = (C3840z3) function.apply(l2D0);
        return c3840z3.b() && !dataEntryResource.getName().toLowerCase().endsWith(".class") && c3840z3.a(dataEntryResource.getName());
    }

    private byte[] a(DataEntryResource dataEntryResource) {
        try {
            InputStream byteStream = dataEntryResource.getByteStream();
            try {
                Z z = new Z(this, new String(V5.a(byteStream), Charset.defaultCharset()));
                if (!z.d()) {
                    byteStream.close();
                    return null;
                }
                byte[] bytes = z.c().getBytes(Charset.defaultCharset());
                byteStream.close();
                return bytes;
            } catch (Throwable th) {
                if (byteStream != null) {
                    try {
                        byteStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (ResourceException e) {
            this.e.e.error(new StringDiagnostic("Failed to open input: " + e.getMessage(), dataEntryResource.getOrigin()));
            return null;
        } catch (Exception e2) {
            this.e.e.error(new ExceptionDiagnostic(e2, dataEntryResource.getOrigin()));
            return null;
        }
    }
}
