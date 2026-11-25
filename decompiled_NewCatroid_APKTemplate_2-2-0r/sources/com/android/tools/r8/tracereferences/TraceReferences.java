package com.android.tools.r8.tracereferences;

import com.android.tools.r8.ClassFileResourceProvider;
import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.ProgramResourceProvider;
import com.android.tools.r8.Version;
import com.android.tools.r8.dex.C0408c;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.shaking.N1;
import com.android.tools.r8.synthesis.y;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.AbstractC3920k0;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.R0;
import com.android.tools.r8.utils.S0;
import com.android.tools.r8.utils.Y2;
import com.android.tools.r8.utils.y3;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class TraceReferences {
    static final /* synthetic */ boolean a = true;

    static void b(final TraceReferencesCommand traceReferencesCommand, final A1 a1) throws CompilationFailedException {
        R0.a(traceReferencesCommand.d(), new R0.a() { // from class: com.android.tools.r8.tracereferences.TraceReferences$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.R0.a
            public final void run() {
                TraceReferences.c(traceReferencesCommand, a1);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(TraceReferencesCommand traceReferencesCommand, A1 a1) {
        final C3945p.a aVarB = C3945p.b();
        AbstractC1639am abstractC1639amC = traceReferencesCommand.c();
        Objects.requireNonNull(aVarB);
        abstractC1639amC.forEach(new TraceReferences$$ExternalSyntheticLambda2(aVarB));
        traceReferencesCommand.f().forEach(new TraceReferences$$ExternalSyntheticLambda3(aVarB));
        traceReferencesCommand.e().forEach(new Consumer() { // from class: com.android.tools.r8.tracereferences.TraceReferences$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                aVarB.a((ProgramResourceProvider) obj);
            }
        });
        final HashSet hashSet = new HashSet();
        traceReferencesCommand.f().forEach(new Consumer() { // from class: com.android.tools.r8.tracereferences.TraceReferences$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TraceReferences.a(hashSet, (ClassFileResourceProvider) obj);
            }
        });
        for (ProgramResourceProvider programResourceProvider : traceReferencesCommand.e()) {
            Consumer consumer = new Consumer() { // from class: com.android.tools.r8.tracereferences.TraceReferences$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    hashSet.remove((String) obj);
                }
            };
            for (ProgramResource programResource : programResourceProvider.getProgramResources()) {
                if (programResource.getKind() == ProgramResource.Kind.DEX) {
                    if (!a && programResource.getClassDescriptors() != null) {
                        throw new AssertionError();
                    }
                    Iterator<C0729q2> it = new C0408c(C3945p.b().c(AbstractC1639am.c(programResource.getBytes())).a(), new A1(), y3.b()).a().d().iterator();
                    while (it.hasNext()) {
                        consumer.accept(it.next().getType().b1());
                    }
                } else {
                    if (!a && programResource.getClassDescriptors() == null) {
                        throw new AssertionError();
                    }
                    programResource.getClassDescriptors().forEach(consumer);
                }
            }
        }
        C0702m c0702mA = C0702m.a(C0680j.a(new C0408c(aVarB.a(), a1, y3.b()).a().i(), C1121Ka.a(), N1.f(), y.e()));
        AbstractC3920k0.a(c0702mA);
        new k(c0702mA, traceReferencesCommand.d(), new Predicate() { // from class: com.android.tools.r8.tracereferences.TraceReferences$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return TraceReferences.a(hashSet, (C0767v2) obj);
            }
        }).a(traceReferencesCommand.a());
    }

    public static void main(final String[] strArr) {
        if (strArr.length == 0) {
            throw new RuntimeException(Y2.a("Invalid invocation.", h.a()));
        }
        R0.a(new S0() { // from class: com.android.tools.r8.tracereferences.TraceReferences$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.S0
            public final void run() throws CompilationFailedException {
                TraceReferences.run(strArr);
            }
        });
    }

    public static void run(TraceReferencesCommand traceReferencesCommand) throws CompilationFailedException {
        b(traceReferencesCommand, traceReferencesCommand.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, ClassFileResourceProvider classFileResourceProvider) {
        set.addAll(classFileResourceProvider.getClassDescriptors());
    }

    public static void run(String... strArr) throws CompilationFailedException {
        TraceReferencesCommand traceReferencesCommandBuild = TraceReferencesCommand.parse(strArr, com.android.tools.r8.origin.a.e).build();
        if (traceReferencesCommandBuild.isPrintHelp()) {
            System.out.println(h.a());
        } else {
            if (!traceReferencesCommandBuild.isPrintVersion()) {
                run(traceReferencesCommandBuild);
                return;
            }
            System.out.println("tracereferences " + Version.getVersionString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, C0767v2 c0767v2) {
        return set.contains(c0767v2.b1());
    }
}
