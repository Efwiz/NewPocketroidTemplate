package org.jetbrains.kotlin.fir.references.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.references.FirResolvedCallableReference;
import org.jetbrains.kotlin.fir.references.impl.FirResolvedCallableReferenceImpl;
import org.jetbrains.kotlin.fir.resolve.calls.ResolvedCallArgument;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirResolvedCallableReferenceBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010#\u001a\u00020$R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R*\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tj\u0002`\fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0018X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/fir/references/builder/FirResolvedCallableReferenceBuilder;", "", "()V", "inferredTypeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getInferredTypeArguments", "()Ljava/util/List;", "mappedArguments", "", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceMappedArguments;", "getMappedArguments", "()Ljava/util/Map;", "setMappedArguments", "(Ljava/util/Map;)V", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "resolvedSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getResolvedSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "setResolvedSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/references/FirResolvedCallableReference;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedCallableReferenceBuilder {
    private final List<ConeKotlinType> inferredTypeArguments = new ArrayList();
    public Map<FirValueParameter, ? extends ResolvedCallArgument> mappedArguments;
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

    public final List<ConeKotlinType> getInferredTypeArguments() {
        return this.inferredTypeArguments;
    }

    public final Map<FirValueParameter, ResolvedCallArgument> getMappedArguments() {
        Map map = this.mappedArguments;
        if (map != null) {
            return map;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mappedArguments");
        return null;
    }

    public final void setMappedArguments(Map<FirValueParameter, ? extends ResolvedCallArgument> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.mappedArguments = map;
    }

    public final FirResolvedCallableReference build() {
        return new FirResolvedCallableReferenceImpl(this.source, getName(), getResolvedSymbol(), this.inferredTypeArguments, getMappedArguments());
    }
}
