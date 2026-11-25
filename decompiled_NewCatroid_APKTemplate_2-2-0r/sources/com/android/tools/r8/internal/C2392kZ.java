package com.android.tools.r8.internal;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.PackageReference;
import com.android.tools.r8.tracereferences.TraceReferencesConsumer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2392kZ implements TraceReferencesConsumer {
    private final HashSet a = new HashSet();
    private final HashMap b = new HashMap();
    private final HashMap c = new HashMap();
    private final HashSet d = new HashSet();

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(ClassReference classReference) {
        return new HashSet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set b(ClassReference classReference) {
        return new HashSet();
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer
    public final void acceptField(TraceReferencesConsumer.TracedField tracedField, DiagnosticsHandler diagnosticsHandler) {
        ((Set) this.b.computeIfAbsent(tracedField.getReference().getHolderClass(), new Function() { // from class: com.android.tools.r8.internal.kZ$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2392kZ.a((ClassReference) obj);
            }
        })).add(tracedField);
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer
    public final void acceptMethod(TraceReferencesConsumer.TracedMethod tracedMethod, DiagnosticsHandler diagnosticsHandler) {
        ((Set) this.c.computeIfAbsent(tracedMethod.getReference().getHolderClass(), new Function() { // from class: com.android.tools.r8.internal.kZ$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2392kZ.b((ClassReference) obj);
            }
        })).add(tracedMethod);
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer
    public final void acceptPackage(PackageReference packageReference, DiagnosticsHandler diagnosticsHandler) {
        this.d.add(packageReference);
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer
    public final void acceptType(TraceReferencesConsumer.TracedClass tracedClass, DiagnosticsHandler diagnosticsHandler) {
        this.a.add(tracedClass);
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
    }

    public final C2471lZ a() {
        return new C2471lZ(this.b, this.c, this.a, this.d);
    }
}
