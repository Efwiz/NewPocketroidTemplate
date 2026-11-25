package com.android.tools.r8.internal;

import com.android.tools.r8.utils.EnumC3891f;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3383xj extends AbstractC2817q {
    public C3383xj(String str, String str2, String str3, String str4) throws Exception {
        super(str, str2, str3, str4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC3229vj abstractC3229vj, com.android.tools.r8.graph.T0 t0, EY.a aVar) {
        abstractC3229vj.a.put(t0, aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(PrintStream printStream, EY.d dVar) {
        a(printStream, dVar);
    }

    private static void a(PrintStream printStream, EY.d dVar) {
        final C3153uj c3153uj = new C3153uj(dVar.d(), dVar.a());
        dVar.a(new BiConsumer() { // from class: com.android.tools.r8.internal.xj$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3383xj.a(c3153uj, (com.android.tools.r8.graph.T0) obj, (EY.a) obj2);
            }
        });
        dVar.b(new BiConsumer() { // from class: com.android.tools.r8.internal.xj$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3383xj.a(c3153uj, (com.android.tools.r8.graph.W0) obj, (EY.c) obj2);
            }
        });
        printStream.println(c3153uj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC3229vj abstractC3229vj, com.android.tools.r8.graph.W0 w0, EY.c cVar) {
        if (w0.f.X()) {
            return;
        }
        abstractC3229vj.getClass();
        if (!AbstractC3229vj.f && w0.u1()) {
            throw new AssertionError();
        }
        if (w0.A1()) {
            abstractC3229vj.b.put(w0, cVar);
        } else {
            abstractC3229vj.c.put(w0, cVar);
        }
    }

    final EnumC3891f a() {
        return a("apis.html");
    }

    public EnumC3891f a(String str) throws Exception {
        final PrintStream printStream = new PrintStream(Files.newOutputStream(this.g.resolve(str), new OpenOption[0]));
        new GY(this.c, this.h).b(this.f, this.e).a(new Consumer() { // from class: com.android.tools.r8.internal.xj$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(printStream, (EY.d) obj);
            }
        });
        return AbstractC2817q.i;
    }

    public static void a(String[] strArr) throws Exception {
        AbstractC2817q.a(strArr);
    }
}
