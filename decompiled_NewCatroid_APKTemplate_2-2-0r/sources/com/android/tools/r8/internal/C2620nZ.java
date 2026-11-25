package com.android.tools.r8.internal;

import com.android.tools.r8.diagnostic.DefinitionContext;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.tracereferences.TraceReferencesConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2620nZ extends AbstractC2857qZ implements TraceReferencesConsumer.TracedClass {
    public C2620nZ(C0767v2 c0767v2, DefinitionContext definitionContext) {
        this(c0767v2.B0(), definitionContext, null);
    }

    public final String toString() {
        return ((ClassReference) super.getReference()).getTypeName();
    }

    public C2620nZ(DefinitionContext definitionContext, AbstractC0727q0 abstractC0727q0) {
        this(abstractC0727q0.Z0(), definitionContext, new I9(abstractC0727q0.getAccessFlags()));
    }

    public C2620nZ(ClassReference classReference, DefinitionContext definitionContext, TraceReferencesConsumer.ClassAccessFlags classAccessFlags) {
        super(classReference, definitionContext, classAccessFlags, classAccessFlags == null);
    }
}
