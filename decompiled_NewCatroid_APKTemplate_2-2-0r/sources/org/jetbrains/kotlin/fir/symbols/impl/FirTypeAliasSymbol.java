package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.mpp.TypeAliasSymbolMarker;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirClassLikeSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Lorg/jetbrains/kotlin/mpp/TypeAliasSymbolMarker;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "(Lorg/jetbrains/kotlin/name/ClassId;)V", "resolvedExpandedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "getResolvedExpandedTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "toLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeAliasSymbol extends FirClassLikeSymbol<FirTypeAlias> implements TypeAliasSymbolMarker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirTypeAliasSymbol(ClassId classId) {
        super(classId, null);
        Intrinsics.checkNotNullParameter(classId, "classId");
    }

    @Override // org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol
    /* JADX INFO: renamed from: toLookupTag */
    public ConeClassLikeLookupTag getLookupTag() {
        return TypeConstructionUtilsKt.toLookupTag(getClassId());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirResolvedTypeRef getResolvedExpandedTypeRef() {
        FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.SUPER_TYPES);
        FirTypeRef expandedTypeRef = ((FirTypeAlias) getFir()).getExpandedTypeRef();
        Intrinsics.checkNotNull(expandedTypeRef, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirResolvedTypeRef");
        return (FirResolvedTypeRef) expandedTypeRef;
    }
}
