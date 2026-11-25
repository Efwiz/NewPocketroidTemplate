package com.android.tools.r8.internal;

import com.android.tools.r8.diagnostic.DefinitionContext;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.tracereferences.TraceReferencesConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2786pZ extends AbstractC2857qZ implements TraceReferencesConsumer.TracedMethod {
    public C2786pZ(com.android.tools.r8.graph.W0 w0, DefinitionContext definitionContext) {
        this(w0.getReference().E0(), definitionContext, new AE(w0.getAccessFlags()));
    }

    public final String toString() {
        return ((MethodReference) super.getReference()).toString();
    }

    public C2786pZ(MethodReference methodReference, DefinitionContext definitionContext, TraceReferencesConsumer.MethodAccessFlags methodAccessFlags) {
        super(methodReference, definitionContext, methodAccessFlags, methodAccessFlags == null);
    }
}
