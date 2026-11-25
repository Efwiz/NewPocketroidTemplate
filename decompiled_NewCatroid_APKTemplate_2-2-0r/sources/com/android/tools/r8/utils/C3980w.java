package com.android.tools.r8.utils;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.DataResourceConsumer;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.origin.Origin;
import java.util.ArrayList;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.w, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3980w extends ClassFileConsumer.ForwardingConsumer {
    static final /* synthetic */ boolean f = true;
    private ArrayList c;
    final /* synthetic */ ClassFileConsumer d;
    final /* synthetic */ C3989y e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C3980w(C3989y c3989y, ClassFileConsumer classFileConsumer, ClassFileConsumer classFileConsumer2) {
        super(classFileConsumer);
        this.e = c3989y;
        this.d = classFileConsumer2;
        this.c = new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C3985x c3985x) {
        this.e.a.a(c3985x.b, Origin.unknown(), c3985x.a);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.android.tools.r8.utils.x.<init>(java.util.Set, byte[], com.android.tools.r8.utils.x-IA):void, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:309)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isArgUnused(ProcessVariables.java:146)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.lambda$isVarUnused$0(ProcessVariables.java:131)
        	at jadx.core.utils.ListUtils.allMatch(ListUtils.java:224)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isVarUnused(ProcessVariables.java:131)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.processBlock(ProcessVariables.java:82)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:93)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.removeUnusedResults(ProcessVariables.java:73)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.visit(ProcessVariables.java:48)
        */
    @Override // com.android.tools.r8.ClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ClassFileConsumer
    public final void accept(com.android.tools.r8.ByteDataView r3, java.lang.String r4, com.android.tools.r8.DiagnosticsHandler r5) {
        /*
            r2 = this;
            super.accept(r3, r4, r5)
            byte[] r3 = r3.copyByteData()
            monitor-enter(r2)
            java.util.ArrayList r5 = r2.c     // Catch: java.lang.Throwable -> L19
            com.android.tools.r8.utils.x r0 = new com.android.tools.r8.utils.x     // Catch: java.lang.Throwable -> L19
            java.util.Set r4 = java.util.Collections.singleton(r4)     // Catch: java.lang.Throwable -> L19
            r1 = 0
            r0.<init>(r4, r3)     // Catch: java.lang.Throwable -> L19
            r5.add(r0)     // Catch: java.lang.Throwable -> L19
            monitor-exit(r2)
            return
        L19:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.utils.C3980w.accept(com.android.tools.r8.ByteDataView, java.lang.String, com.android.tools.r8.DiagnosticsHandler):void");
    }

    @Override // com.android.tools.r8.ClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer, com.android.tools.r8.DataResourceConsumer
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
        super.finished(diagnosticsHandler);
        C3989y c3989y = this.e;
        if (c3989y.b) {
            if (f) {
                return;
            }
            getDataResourceConsumer();
        } else {
            c3989y.b = true;
            this.c.forEach(new Consumer() { // from class: com.android.tools.r8.utils.w$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C3985x) obj);
                }
            });
            this.c = null;
        }
    }

    @Override // com.android.tools.r8.ClassFileConsumer.ForwardingConsumer, com.android.tools.r8.ProgramConsumer
    public final DataResourceConsumer getDataResourceConsumer() {
        ClassFileConsumer classFileConsumer = this.d;
        return new C3973v(this, classFileConsumer != null ? classFileConsumer.getDataResourceConsumer() : null);
    }
}
