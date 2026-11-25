package org.jetbrains.kotlin.fir.references.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.impl.FirResolvedNamedReferenceImpl;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirResolvedNamedReferenceBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0015\u001a\u00020\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/references/builder/FirResolvedNamedReferenceBuilder;", "", "()V", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "resolvedSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getResolvedSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "setResolvedSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedNamedReferenceBuilder {
    public Name name;
    public FirBasedSymbol<?> resolvedSymbol;
    private KtSourceElement source;

    public final KtSourceElement getSource() {
        return this.source;
    }

    public final void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final Name getName() {
        Name name2 = this.name;
        if (name2 != null) {
            return name2;
        }
        Intrinsics.throwUninitializedPropertyAccessException("name");
        return null;
    }

    public final void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.name = name2;
    }

    public final FirBasedSymbol<?> getResolvedSymbol() {
        FirBasedSymbol<?> firBasedSymbol = this.resolvedSymbol;
        if (firBasedSymbol != null) {
            return firBasedSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException("resolvedSymbol");
        return null;
    }

    public final void setResolvedSymbol(FirBasedSymbol<?> firBasedSymbol) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<set-?>");
        this.resolvedSymbol = firBasedSymbol;
    }

    public final FirResolvedNamedReference build() {
        return new FirResolvedNamedReferenceImpl(this.source, getName(), getResolvedSymbol());
    }
}
