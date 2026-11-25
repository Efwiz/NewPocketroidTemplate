package org.jetbrains.kotlin.fir.symbols.impl;

import io.netty.util.internal.StringUtil;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.mpp.TypeParameterSymbolMarker;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: FirTypeParameterSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u001b\u001a\u00020\rH\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u0015\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;", "()V", "containingDeclarationSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getContainingDeclarationSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "isReified", "", "()Z", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeTypeParameterLookupTag;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "resolvedBounds", "", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "getResolvedBounds", "()Ljava/util/List;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", "toLookupTag", "toString", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeParameterSymbol extends FirClassifierSymbol<FirTypeParameter> implements TypeParameterSymbolMarker {
    private final ConeTypeParameterLookupTag lookupTag;

    public FirTypeParameterSymbol() {
        super(null);
        this.lookupTag = new ConeTypeParameterLookupTag(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Name getName() {
        return ((FirTypeParameter) getFir()).getName();
    }

    @Override // org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol
    /* JADX INFO: renamed from: toLookupTag, reason: from getter */
    public ConeTypeParameterLookupTag getLookupTag() {
        return this.lookupTag;
    }

    public String toString() {
        return isBound() ? Reflection.getOrCreateKotlinClass(getClass()).getSimpleName() + StringUtil.SPACE + getName().asString() : Reflection.getOrCreateKotlinClass(getClass()).getSimpleName() + " <unbound>";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final List<FirResolvedTypeRef> getResolvedBounds() {
        FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.TYPES);
        List bounds = ((FirTypeParameter) getFir()).getBounds();
        Intrinsics.checkNotNull(bounds, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.fir.types.FirResolvedTypeRef>");
        return bounds;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Variance getVariance() {
        return ((FirTypeParameter) getFir()).getVariance();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isReified() {
        return ((FirTypeParameter) getFir()).isReified();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirBasedSymbol<?> getContainingDeclarationSymbol() {
        return ((FirTypeParameter) getFir()).getContainingDeclarationSymbol();
    }
}
