package com.android.tools.r8.internal;

import com.android.tools.r8.diagnostic.DefinitionContext;
import com.android.tools.r8.tracereferences.TraceReferencesConsumer;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: renamed from: com.android.tools.r8.internal.qZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2857qZ implements TraceReferencesConsumer.TracedReference {
    static final /* synthetic */ boolean e = true;
    private final Object a;
    private final DefinitionContext b;
    private final Object c;
    private final boolean d;

    AbstractC2857qZ(Object obj, DefinitionContext definitionContext, Object obj2, boolean z) {
        if (!e && obj2 == null && !z) {
            throw new AssertionError();
        }
        this.a = obj;
        this.b = definitionContext;
        this.c = obj2;
        this.d = z;
    }

    public boolean equals(Object obj) {
        if (obj instanceof AbstractC2857qZ) {
            return this.a.equals(((AbstractC2857qZ) obj).a);
        }
        return false;
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.TracedReference
    public Object getAccessFlags() {
        return this.c;
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.TracedReference
    public Object getReference() {
        return this.a;
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.TracedReference
    public DefinitionContext getReferencedFromContext() {
        return this.b;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.TracedReference
    public boolean isMissingDefinition() {
        return this.d;
    }
}
