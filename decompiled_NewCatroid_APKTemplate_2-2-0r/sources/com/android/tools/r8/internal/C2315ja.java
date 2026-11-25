package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.inspector.ClassInspector;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.Reference;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ja, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2315ja implements ClassInspector {
    private final AbstractC0727q0 a;
    private ClassReference b = null;

    C2315ja(C0729q2 c0729q2) {
        this.a = c0729q2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Consumer consumer, com.android.tools.r8.graph.T0 t0) {
        consumer.accept(new C1156Lh(this, t0));
    }

    @Override // com.android.tools.r8.inspector.ClassInspector
    public final void forEachField(final Consumer consumer) {
        AbstractC0727q0 abstractC0727q0 = this.a;
        Consumer consumer2 = new Consumer() { // from class: com.android.tools.r8.internal.ja$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, (com.android.tools.r8.graph.T0) obj);
            }
        };
        abstractC0727q0.getClass();
        abstractC0727q0.c(consumer2, CM.b());
    }

    @Override // com.android.tools.r8.inspector.ClassInspector
    public final void forEachMethod(final Consumer consumer) {
        this.a.f(new Consumer() { // from class: com.android.tools.r8.internal.ja$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, (com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.inspector.ClassInspector
    public final ClassReference getClassReference() {
        if (this.b == null) {
            this.b = Reference.classFromDescriptor(this.a.d.b1());
        }
        return this.b;
    }

    @Override // com.android.tools.r8.inspector.ClassInspector
    public final String getSourceFile() {
        C0759u2 c0759u2M1 = this.a.m1();
        if (c0759u2M1 == null) {
            return null;
        }
        return c0759u2M1.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Consumer consumer, com.android.tools.r8.graph.W0 w0) {
        consumer.accept(new IE(this, w0));
    }
}
