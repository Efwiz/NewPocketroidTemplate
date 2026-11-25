package com.android.tools.r8.startup.diagnostic;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.dex.C0579p$$ExternalSyntheticLambda0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Q0;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.C3362xW;
import com.android.tools.r8.internal.C3510zW;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class MissingStartupProfileItemsDiagnostic implements Diagnostic {
    static final /* synthetic */ boolean c = true;
    private final List a;
    private final Origin b;

    MissingStartupProfileItemsDiagnostic(ArrayList arrayList, Origin origin) {
        if (!c && arrayList.isEmpty()) {
            throw new AssertionError();
        }
        this.a = arrayList;
        this.b = origin;
    }

    private static void a(final StringBuilder sb, AbstractC0744s2 abstractC0744s2) {
        abstractC0744s2.a(new Function() { // from class: com.android.tools.r8.startup.diagnostic.MissingStartupProfileItemsDiagnostic$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MissingStartupProfileItemsDiagnostic.a(sb, (C0767v2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.startup.diagnostic.MissingStartupProfileItemsDiagnostic$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MissingStartupProfileItemsDiagnostic.a(sb, (Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.startup.diagnostic.MissingStartupProfileItemsDiagnostic$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MissingStartupProfileItemsDiagnostic.a(sb, (C0684j2) obj);
            }
        });
        sb.append(abstractC0744s2.r0());
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        StringBuilder sb = new StringBuilder();
        Iterator it = this.a.iterator();
        a(sb, (AbstractC0744s2) it.next());
        while (it.hasNext()) {
            a(sb.append(System.lineSeparator()), (AbstractC0744s2) it.next());
        }
        return sb.toString();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.b;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return Position.UNKNOWN;
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        static final /* synthetic */ boolean d = true;
        private final Q0 a;
        private final Set b = OU.c();
        private Origin c;

        public a(Q0 q0) {
            this.a = q0;
        }

        public static a c() {
            return new a(null);
        }

        public final boolean a(C3362xW c3362xW) {
            Q0 q0 = this.a;
            if (q0 == null || q0.e(c3362xW.e())) {
                return false;
            }
            C0767v2 c0767v2E = c3362xW.e();
            C0759u2 c0759u2 = this.a.a().H;
            C0759u2 c0759u2D0 = c0767v2E.C().D0();
            c0759u2D0.getClass();
            if (c0759u2D0.a(c0759u2.e)) {
                return true;
            }
            this.b.add(c0767v2E);
            return true;
        }

        public final boolean b() {
            return !this.b.isEmpty();
        }

        public final boolean a(C3510zW c3510zW) {
            Q0 q0 = this.a;
            if (q0 == null || q0.a(c3510zW.c())) {
                return false;
            }
            C0684j2 c0684j2C = c3510zW.c();
            C0759u2 c0759u2 = this.a.a().H;
            C0759u2 c0759u2D0 = c0684j2C.C().D0();
            c0759u2D0.getClass();
            if (c0759u2D0.a(c0759u2.e)) {
                return true;
            }
            this.b.add(c0684j2C);
            return true;
        }

        public final a a(Origin origin) {
            this.c = origin;
            return this;
        }

        public final MissingStartupProfileItemsDiagnostic a() {
            if (!d && !b()) {
                throw new AssertionError();
            }
            ArrayList arrayList = new ArrayList(this.b);
            arrayList.sort(new C0579p$$ExternalSyntheticLambda0());
            return new MissingStartupProfileItemsDiagnostic(arrayList, this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ StringBuilder a(StringBuilder sb, C0767v2 c0767v2) {
        return sb.append("Startup class not found: ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ StringBuilder a(StringBuilder sb, Y0 y0) {
        return sb.append("Startup field not found: ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ StringBuilder a(StringBuilder sb, C0684j2 c0684j2) {
        return sb.append("Startup method not found: ");
    }
}
