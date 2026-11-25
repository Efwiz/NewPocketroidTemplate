package org.jetbrains.kotlin.fir.resolve.transformers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: PhaseUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006¨\u0006\b"}, d2 = {"ensureResolvedTypeDeclaration", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "requiredPhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PhaseUtilsKt {
    public static /* synthetic */ void ensureResolvedTypeDeclaration$default(ConeKotlinType coneKotlinType, FirSession firSession, FirResolvePhase firResolvePhase, int i, Object obj) {
        if ((i & 2) != 0) {
            firResolvePhase = FirResolvePhase.INSTANCE.getDECLARATIONS();
        }
        ensureResolvedTypeDeclaration(coneKotlinType, firSession, firResolvePhase);
    }

    public static final void ensureResolvedTypeDeclaration(ConeKotlinType coneKotlinType, FirSession useSiteSession, FirResolvePhase requiredPhase) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(requiredPhase, "requiredPhase");
        if (coneKotlinType instanceof ConeClassLikeType) {
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) coneKotlinType;
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), useSiteSession);
            if (symbol != null) {
                FirLazyDeclarationResolverKt.lazyResolveToPhase(symbol, requiredPhase);
            }
            FirClassLikeSymbol<?> symbol2 = LookupTagUtilsKt.toSymbol(TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, useSiteSession, null, 2, null).getLookupTag(), useSiteSession);
            if (symbol2 != null) {
                FirLazyDeclarationResolverKt.lazyResolveToPhase(symbol2, requiredPhase);
            }
        }
    }

    public static /* synthetic */ void ensureResolvedTypeDeclaration$default(FirTypeRef firTypeRef, FirSession firSession, FirResolvePhase firResolvePhase, int i, Object obj) {
        if ((i & 2) != 0) {
            firResolvePhase = FirResolvePhase.INSTANCE.getDECLARATIONS();
        }
        ensureResolvedTypeDeclaration(firTypeRef, firSession, firResolvePhase);
    }

    public static final void ensureResolvedTypeDeclaration(FirTypeRef firTypeRef, FirSession useSiteSession, FirResolvePhase requiredPhase) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(requiredPhase, "requiredPhase");
        FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeKotlinType coneKotlinType = type instanceof ConeKotlinType ? type : null;
        if (coneKotlinType != null) {
            ensureResolvedTypeDeclaration(coneKotlinType, useSiteSession, requiredPhase);
        }
    }
}
