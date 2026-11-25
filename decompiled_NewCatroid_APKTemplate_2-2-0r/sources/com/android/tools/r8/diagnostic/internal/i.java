package com.android.tools.r8.diagnostic.internal;

import com.android.tools.r8.diagnostic.DefinitionContext;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import java.util.Collection;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class i {
    final C1507Xl a = AbstractC1639am.g();

    i() {
    }

    abstract i a();

    public final i a(DefinitionContext definitionContext) {
        this.a.a(definitionContext);
        return a();
    }

    public final i a(Collection collection) {
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.diagnostic.internal.i$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((DefinitionContext) obj);
            }
        });
        return a();
    }
}
