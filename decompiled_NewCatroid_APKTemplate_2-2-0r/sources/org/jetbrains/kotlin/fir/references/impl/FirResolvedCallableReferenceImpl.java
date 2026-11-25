package org.jetbrains.kotlin.fir.references.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.references.FirResolvedCallableReference;
import org.jetbrains.kotlin.fir.resolve.calls.ResolvedCallArgument;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirResolvedCallableReferenceImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001BI\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0016\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000f¢\u0006\u0002\u0010\u0010J5\u0010\u001b\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u001d\"\u0004\b\u0001\u0010\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\u001d\u0012\u0004\u0012\u0002H\u001e0 2\u0006\u0010!\u001a\u0002H\u001eH\u0016¢\u0006\u0002\u0010\"J)\u0010#\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u001e2\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u001e0%2\u0006\u0010!\u001a\u0002H\u001eH\u0016¢\u0006\u0002\u0010&R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R$\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fj\u0002`\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0018\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/fir/references/impl/FirResolvedCallableReferenceImpl;", "Lorg/jetbrains/kotlin/fir/references/FirResolvedCallableReference;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "name", "Lorg/jetbrains/kotlin/name/Name;", "resolvedSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "inferredTypeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "mappedArguments", "", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceMappedArguments;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Ljava/util/List;Ljava/util/Map;)V", "getInferredTypeArguments", "()Ljava/util/List;", "getMappedArguments", "()Ljava/util/Map;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getResolvedSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/references/impl/FirResolvedCallableReferenceImpl;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedCallableReferenceImpl extends FirResolvedCallableReference {
    private final List<ConeKotlinType> inferredTypeArguments;
    private final Map<FirValueParameter, ResolvedCallArgument> mappedArguments;
    private final Name name;
    private final FirBasedSymbol<?> resolvedSymbol;
    private final KtSourceElement source;

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirResolvedCallableReferenceImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.references.FirResolvedCallableReference, org.jetbrains.kotlin.fir.references.FirResolvedNamedReference, org.jetbrains.kotlin.fir.references.FirNamedReference, org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirResolvedCallableReference, org.jetbrains.kotlin.fir.references.FirResolvedNamedReference, org.jetbrains.kotlin.fir.references.FirNamedReference
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirResolvedCallableReference, org.jetbrains.kotlin.fir.references.FirResolvedNamedReference
    public FirBasedSymbol<?> getResolvedSymbol() {
        return this.resolvedSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirResolvedCallableReference
    public List<ConeKotlinType> getInferredTypeArguments() {
        return this.inferredTypeArguments;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirResolvedCallableReference
    public Map<FirValueParameter, ResolvedCallArgument> getMappedArguments() {
        return this.mappedArguments;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FirResolvedCallableReferenceImpl(KtSourceElement ktSourceElement, Name name2, FirBasedSymbol<?> resolvedSymbol, List<ConeKotlinType> inferredTypeArguments, Map<FirValueParameter, ? extends ResolvedCallArgument> mappedArguments) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(resolvedSymbol, "resolvedSymbol");
        Intrinsics.checkNotNullParameter(inferredTypeArguments, "inferredTypeArguments");
        Intrinsics.checkNotNullParameter(mappedArguments, "mappedArguments");
        this.source = ktSourceElement;
        this.name = name2;
        this.resolvedSymbol = resolvedSymbol;
        this.inferredTypeArguments = inferredTypeArguments;
        this.mappedArguments = mappedArguments;
    }
}
