package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.inspector.MethodInspector;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.references.TypeReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class IE implements MethodInspector {
    private final C2315ja a;
    private final com.android.tools.r8.graph.W0 b;
    private MethodReference c;

    public IE(C2315ja c2315ja, com.android.tools.r8.graph.W0 w0) {
        this.a = c2315ja;
        this.b = w0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ TypeReference a(C0767v2 c0767v2) {
        return Reference.typeFromDescriptor(c0767v2.b1());
    }

    @Override // com.android.tools.r8.inspector.MethodInspector
    public final MethodReference getMethodReference() {
        if (this.c == null) {
            this.c = Reference.method(this.a.getClassReference(), this.b.getReference().f.toString(), com.android.tools.r8.utils.V1.a((Collection) Arrays.asList(this.b.getReference().h.f.a), new Function() { // from class: com.android.tools.r8.internal.IE$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return IE.a((C0767v2) obj);
                }
            }), this.b.getReference().h.e.Y0() ? null : Reference.typeFromDescriptor(this.b.getReference().h.e.b1()));
        }
        return this.c;
    }
}
