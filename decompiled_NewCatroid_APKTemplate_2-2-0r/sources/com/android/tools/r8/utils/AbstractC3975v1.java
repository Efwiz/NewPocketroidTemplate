package com.android.tools.r8.utils;

import com.android.tools.r8.ByteDataView;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.GlobalSyntheticsConsumer;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.references.Reference;
import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: renamed from: com.android.tools.r8.utils.v1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3975v1 extends AbstractC3983w1 {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final GlobalSyntheticsConsumer b;
    private final ConcurrentHashMap c = new ConcurrentHashMap();

    public AbstractC3975v1(GlobalSyntheticsConsumer globalSyntheticsConsumer, C0702m c0702m) {
        this.a = c0702m;
        this.b = globalSyntheticsConsumer;
    }

    abstract ProgramResource.Kind a();

    @Override // com.android.tools.r8.utils.AbstractC3983w1
    public void a(final C0702m c0702m) {
        IdentityHashMap identityHashMapB = c0702m.D().b(c0702m);
        IdentityHashMap identityHashMap = new IdentityHashMap();
        for (C0767v2 c0767v2 : this.c.keySet()) {
            Set set = (Set) identityHashMapB.get(c0767v2);
            boolean z = d;
            if (!z && set == null) {
                throw new AssertionError();
            }
            if (!z && set.isEmpty()) {
                throw new AssertionError();
            }
            Iterator it = set.iterator();
            while (it.hasNext()) {
                ((Set) identityHashMap.computeIfAbsent((C0767v2) it.next(), new Function() { // from class: com.android.tools.r8.utils.v1$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return AbstractC3975v1.a((C0767v2) obj);
                    }
                })).add(c0767v2);
            }
        }
        identityHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.v1$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c0702m, (C0767v2) obj, (Set) obj2);
            }
        });
        this.b.finished(c0702m.Y().e);
    }

    @Override // com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
        throw new C3405y10("Unexpected call to non-internal finished.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C0767v2 c0767v2) {
        return T2.b(new C0767v2[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0702m c0702m, C0767v2 c0767v2, Set set) {
        byte[] bArrA;
        final C3959r1 c3959r1 = new C3959r1(a());
        set.forEach(new Consumer() { // from class: com.android.tools.r8.utils.v1$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c3959r1, (C0767v2) obj);
            }
        });
        try {
            bArrA = c3959r1.a();
        } catch (IOException e) {
            c0702m.Y().e.error(new ExceptionDiagnostic(e));
            bArrA = null;
        }
        if (bArrA != null) {
            this.b.accept(ByteDataView.of(bArrA), Reference.classFromDescriptor(c0767v2.b1()), c0702m.Y().e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C3959r1 c3959r1, C0767v2 c0767v2) {
        String strB1 = c0767v2.b1();
        byte[] bArr = (byte[]) this.c.get(c0767v2);
        c3959r1.getClass();
        c3959r1.b.add(new C3979v2(C3959r1.a(strB1), bArr));
    }
}
