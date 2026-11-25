package com.android.tools.r8.internal;

import com.android.tools.r8.diagnostic.DefinitionContext;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.tracereferences.TraceReferencesConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2711oZ extends AbstractC2857qZ implements TraceReferencesConsumer.TracedField {
    public C2711oZ(AbstractC0735r0 abstractC0735r0, DefinitionContext definitionContext) {
        this(abstractC0735r0.y(), definitionContext, new C0829Ah(abstractC0735r0.getAccessFlags()));
    }

    public final String toString() {
        return ((FieldReference) super.getReference()).toString();
    }

    public C2711oZ(FieldReference fieldReference, DefinitionContext definitionContext, TraceReferencesConsumer.FieldAccessFlags fieldAccessFlags) {
        super(fieldReference, definitionContext, fieldAccessFlags, fieldAccessFlags == null);
    }
}
