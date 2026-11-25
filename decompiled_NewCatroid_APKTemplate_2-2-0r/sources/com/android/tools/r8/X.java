package com.android.tools.r8;

import com.android.tools.r8.internal.InterfaceC3442yY;
import com.android.tools.r8.shaking.N2;
import com.android.tools.r8.shaking.T2;
import com.android.tools.r8.shaking.U2;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.O2;
import com.android.tools.r8.utils.S2;
import com.android.tools.r8.utils.StringDiagnostic;
import com.android.tools.r8.utils.Y2;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class X implements DataResourceProvider.Visitor {
    static final /* synthetic */ boolean f = true;
    private final Supplier a;
    private final O2 b;
    private final ArrayList c = new ArrayList();
    private final ArrayList d = new ArrayList();
    private S2 e;

    public X(O2 o2, InterfaceC3442yY interfaceC3442yY) {
        this.a = interfaceC3442yY;
        this.b = o2;
    }

    private void a(DataEntryResource dataEntryResource, Consumer consumer) {
        try {
            InputStream byteStream = dataEntryResource.getByteStream();
            try {
                consumer.accept(new U2(dataEntryResource.getOrigin(), byteStream));
                if (byteStream != null) {
                    byteStream.close();
                }
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
            this.b.error(new StringDiagnostic("Failed to open input: " + e.getMessage(), dataEntryResource.getOrigin()));
        } catch (Exception e2) {
            this.b.error(new ExceptionDiagnostic(e2, dataEntryResource.getOrigin()));
        }
    }

    @Override // com.android.tools.r8.DataResourceProvider.Visitor
    public final void visit(DataDirectoryResource dataDirectoryResource) {
    }

    @Override // com.android.tools.r8.DataResourceProvider.Visitor
    public final void visit(DataEntryResource dataEntryResource) {
        if (!dataEntryResource.getName().startsWith("META-INF/proguard") ? false : dataEntryResource.getName().substring(17).startsWith("/")) {
            if (!f && a(dataEntryResource)) {
                throw new AssertionError();
            }
            final ArrayList arrayList = this.c;
            Objects.requireNonNull(arrayList);
            a(dataEntryResource, new Consumer() { // from class: com.android.tools.r8.X$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add((T2) obj);
                }
            });
            return;
        }
        if (a(dataEntryResource)) {
            if (!f) {
                if (dataEntryResource.getName().startsWith("META-INF/proguard") ? dataEntryResource.getName().substring(17).startsWith("/") : false) {
                    throw new AssertionError();
                }
            }
            final ArrayList arrayList2 = this.d;
            Objects.requireNonNull(arrayList2);
            a(dataEntryResource, new Consumer() { // from class: com.android.tools.r8.X$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList2.add((T2) obj);
                }
            });
        }
    }

    private boolean a(DataEntryResource dataEntryResource) {
        S2 s2A;
        if (!dataEntryResource.getName().startsWith("META-INF/com.android.tools/r8")) {
            return false;
        }
        String strSubstring = dataEntryResource.getName().substring(29);
        if (strSubstring.startsWith("/")) {
            return true;
        }
        if (!strSubstring.startsWith("-from-") && !strSubstring.startsWith("-upto-")) {
            return false;
        }
        S2 s2E = S2.e();
        if (strSubstring.startsWith("-from-")) {
            String strSubstring2 = strSubstring.substring(6);
            char[] cArr = Y2.a;
            int iIndexOf = strSubstring2.indexOf(45);
            int iIndexOf2 = strSubstring2.indexOf(47);
            if (iIndexOf == -1) {
                iIndexOf = iIndexOf2;
            } else if (iIndexOf2 != -1) {
                iIndexOf = Math.min(iIndexOf, iIndexOf2);
            }
            if (iIndexOf == -1) {
                return false;
            }
            try {
                s2E = S2.a(strSubstring2.substring(0, iIndexOf));
                strSubstring = strSubstring2.substring(iIndexOf);
            } catch (IllegalArgumentException unused) {
                return false;
            }
        }
        if (strSubstring.startsWith("-upto-")) {
            String strSubstring3 = strSubstring.substring(6);
            int iIndexOf3 = strSubstring3.indexOf(47);
            if (iIndexOf3 == -1) {
                return false;
            }
            try {
                s2A = S2.a(strSubstring3.substring(0, iIndexOf3));
            } catch (IllegalArgumentException unused2) {
                return false;
            }
        } else {
            s2A = null;
        }
        if (this.e == null) {
            this.e = (S2) this.a.get();
        }
        if (this.e.b(s2E)) {
            return s2A == null || s2A.a(this.e);
        }
        return false;
    }

    final void a(N2 n2) {
        for (T2 t2 : !this.d.isEmpty() ? this.d : this.c) {
            try {
                n2.a(t2);
            } catch (Exception e) {
                this.b.error(new ExceptionDiagnostic(e, t2.getOrigin()));
            }
        }
    }
}
