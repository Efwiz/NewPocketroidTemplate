package com.android.tools.r8.graph;

import com.android.tools.r8.internal.CM;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.utils.C3970u0;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.graph.n0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0709n0 {
    final AbstractC0681j0 a;
    final com.android.tools.r8.utils.A1 b;

    AbstractC0709n0(AbstractC0681j0 abstractC0681j0, com.android.tools.r8.utils.A1 a1) {
        this.a = abstractC0681j0;
        this.b = a1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ PrintStream a(PrintStream printStream, AbstractC0727q0 abstractC0727q0) {
        return printStream;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(PrintStream printStream) {
    }

    abstract void b(C0729q2 c0729q2, PrintStream printStream);

    public void b(final PrintStream printStream) throws IOException {
        e(printStream);
        a(new InterfaceC0703m0() { // from class: com.android.tools.r8.graph.n0$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.graph.InterfaceC0703m0
            public final PrintStream a(AbstractC0727q0 abstractC0727q0) {
                return AbstractC0709n0.a(printStream, abstractC0727q0);
            }
        }, new Consumer() { // from class: com.android.tools.r8.graph.n0$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC0709n0.a((PrintStream) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public abstract void a(PrintStream printStream, T0 t0);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public abstract void a(PrintStream printStream, o5 o5Var);

    abstract void c(C0729q2 c0729q2, PrintStream printStream);

    void c(PrintStream printStream) {
    }

    void d(PrintStream printStream) {
    }

    public final void e(PrintStream printStream) {
        Collection<com.android.tools.r8.dex.U> collectionH = this.a.e.h();
        System.out.println("Number of markers: " + collectionH.size());
        Iterator<com.android.tools.r8.dex.U> it = collectionH.iterator();
        while (it.hasNext()) {
            printStream.println(it.next().toString());
        }
    }

    public static InterfaceC0703m0 a(final C3605b c3605b, final Path path, final String str) {
        return new InterfaceC0703m0() { // from class: com.android.tools.r8.graph.n0$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.graph.InterfaceC0703m0
            public final PrintStream a(AbstractC0727q0 abstractC0727q0) {
                return AbstractC0709n0.a(c3605b, path, str, abstractC0727q0);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PrintStream a(C3605b c3605b, Path path, String str, AbstractC0727q0 abstractC0727q0) throws IOException {
        Path pathResolve = path.resolve(C3970u0.a(abstractC0727q0.d.b1(), c3605b).replace('.', File.separatorChar) + str);
        Path parent = pathResolve.getParent();
        if (parent != null) {
            Files.createDirectories(parent, new FileAttribute[0]);
        }
        return new PrintStream(Files.newOutputStream(pathResolve, new OpenOption[0]));
    }

    public final void a(InterfaceC0703m0 interfaceC0703m0, Consumer consumer) {
        for (C0729q2 c0729q2 : this.a.e()) {
            if (a(c0729q2)) {
                PrintStream printStreamA = interfaceC0703m0.a(c0729q2);
                try {
                    a(c0729q2, printStreamA);
                    consumer.accept(printStreamA);
                } catch (Throwable th) {
                    consumer.accept(printStreamA);
                    throw th;
                }
            }
        }
    }

    private boolean a(C0729q2 c0729q2) {
        if (this.b.L0()) {
            C0792w4 c0792w4Y = c0729q2.Y();
            final com.android.tools.r8.utils.A1 a1 = this.b;
            Objects.requireNonNull(a1);
            if (!c0792w4Y.d(new Predicate() { // from class: com.android.tools.r8.graph.n0$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return a1.a((W0) obj);
                }
            })) {
                return false;
            }
        }
        return true;
    }

    private void a(C0729q2 c0729q2, final PrintStream printStream) {
        c(c0729q2, printStream);
        d(printStream);
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.graph.n0$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(printStream, (T0) obj);
            }
        };
        c0729q2.getClass();
        c0729q2.c(consumer, CM.b());
        c(printStream);
        c0729q2.l(new Consumer() { // from class: com.android.tools.r8.graph.n0$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(printStream, (o5) obj);
            }
        });
        b(c0729q2, printStream);
    }
}
