package com.android.tools.r8.shaking;

import com.android.tools.r8.InputDependencyGraphConsumer;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.utils.StringDiagnostic;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class N2 {
    private static final AbstractC1639am f = AbstractC1639am.a("protomapping", "target", "maximuminlinedcodelength");
    private static final AbstractC1639am g = AbstractC1639am.a("runtype", "laststageoutput");
    private static final AbstractC1639am h = AbstractC1639am.a("forceprocessing", "dontpreverify", "experimentalshrinkunusedprotofields", "filterlibraryjarswithorginalprogramjars", "dontskipnonpubliclibraryclasses", "dontskipnonpubliclibraryclassmembers", "invokebasemethod", "overloadaggressively", "mergeinterfacesaggressively", "android", "allowruntypeandignoreoptimizationpasses", "dontshrinkduringoptimization", "convert_proto_enum_to_string", "adaptkotlinmetadata");
    private static final AbstractC1639am i = AbstractC1639am.a("isclassnamestring", "whyarenotsimple");
    private static final List j = AbstractC1639am.c("outjars");
    private static final List k = AbstractC1639am.c(ArchiveStreamFactory.DUMP);
    private static final List l = AbstractC1639am.c("useuniqueclassmembernames");
    private static final AbstractC1639am m = AbstractC1639am.a("assumenoexternalsideeffects", "assumenoescapingparameters", "assumenoexternalreturnvalues");
    private static final List n = AbstractC1639am.c("skipnonpubliclibraryclasses");
    public static final /* synthetic */ int o = 0;
    private final L2.a a;
    private final C0722p1 b;
    private final Q2 c;
    private final com.android.tools.r8.utils.O2 d;
    private final InputDependencyGraphConsumer e;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    static class a {
        final String a;
        final List b;

        a(String str, List<O3> list) {
            this.a = str;
            this.b = list;
        }

        static a a(String str) {
            return new a(str, AbstractC1639am.i());
        }

        final boolean b() {
            if (!this.a.contains("<") && !this.a.contains(">")) {
                return false;
            }
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < this.a.length(); i3++) {
                char cCharAt = this.a.charAt(i3);
                if (cCharAt == '<') {
                    i++;
                }
                if (cCharAt == '>') {
                    i2++;
                }
            }
            return (i == i2 && i == this.b.size()) ? false : true;
        }

        final boolean a() {
            return !this.b.isEmpty() && this.b.stream().anyMatch(new Predicate() { // from class: com.android.tools.r8.shaking.N2$a$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((O3) obj).d();
                }
            });
        }
    }

    public N2(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        this(c0722p1, o2, Q2.a().a(false).b(false).c(false).d(false).a());
    }

    private static InputDependencyGraphConsumer a() {
        return new M2();
    }

    private void e() {
        if (this.a.n() && this.a.o()) {
            this.d.a(new StringDiagnostic("-keepparameternames is not supported", this.a.i(), this.a.j()));
            throw null;
        }
        if (this.a.m()) {
            if (this.a.o()) {
                this.d.info(new StringDiagnostic("Build is not being obfuscated due to the use of -addconfigurationdebugging"));
                this.a.c();
            }
            if (this.a.l()) {
                this.d.info(new StringDiagnostic("Applying the obfuscation map (-applymapping) is disabled due to the use of -addconfigurationdebugging"));
                this.a.a((Path) null);
            }
        }
    }

    public L2 b() {
        e();
        return this.a.a();
    }

    public L2 c() {
        e();
        return this.a.b();
    }

    public final L2.a d() {
        return this.a;
    }

    public void a(Path path) {
        a(AbstractC1639am.c(new V2(path)));
    }

    public void a(T2 t2) {
        a(AbstractC1639am.c(t2));
    }

    public final void a(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            T2 t2 = (T2) it.next();
            try {
                new P2(this, t2).j();
            } catch (F3 e) {
                this.d.error(e);
            } catch (IOException e2) {
                this.d.error(new StringDiagnostic("Failed to read file: " + e2.getMessage(), t2.getOrigin()));
            }
        }
        this.d.a();
    }

    public N2(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, Q2 q2) {
        this(c0722p1, o2, q2, null);
    }

    public N2(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, Q2 q2, InputDependencyGraphConsumer inputDependencyGraphConsumer) {
        this.a = L2.a(c0722p1, o2);
        this.b = c0722p1;
        this.c = q2;
        this.d = o2;
        this.e = inputDependencyGraphConsumer == null ? a() : inputDependencyGraphConsumer;
    }
}
