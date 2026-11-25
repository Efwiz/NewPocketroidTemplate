package org.jetbrains.kotlin.fir.references.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.references.FirDelegateFieldReference;
import org.jetbrains.kotlin.fir.references.impl.FirDelegateFieldReferenceImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirDelegateFieldSymbol;

/* JADX INFO: compiled from: FirDelegateFieldReferenceBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000f\u001a\u00020\u0010R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/references/builder/FirDelegateFieldReferenceBuilder;", "", "()V", "resolvedSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirDelegateFieldSymbol;", "getResolvedSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirDelegateFieldSymbol;", "setResolvedSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirDelegateFieldSymbol;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/references/FirDelegateFieldReference;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDelegateFieldReferenceBuilder {
    public FirDelegateFieldSymbol resolvedSymbol;
    private KtSourceElement source;

    public final KtSourceElement getSource() {
        return this.source;
    }

    public final void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final FirDelegateFieldSymbol getResolvedSymbol() {
        FirDelegateFieldSymbol firDelegateFieldSymbol = this.resolvedSymbol;
        if (firDelegateFieldSymbol != null) {
            return firDelegateFieldSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException("resolvedSymbol");
        return null;
    }

    public final void setResolvedSymbol(FirDelegateFieldSymbol firDelegateFieldSymbol) {
        Intrinsics.checkNotNullParameter(firDelegateFieldSymbol, "<set-?>");
        this.resolvedSymbol = firDelegateFieldSymbol;
    }

    public final FirDelegateFieldReference build() {
        return new FirDelegateFieldReferenceImpl(this.source, getResolvedSymbol());
    }
}
