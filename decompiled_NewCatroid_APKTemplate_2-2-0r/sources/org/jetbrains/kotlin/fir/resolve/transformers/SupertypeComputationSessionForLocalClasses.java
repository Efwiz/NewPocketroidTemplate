package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirSupertypesResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J\u001b\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00042\u0006\u0010\n\u001a\u00020\u0007H\u0010¢\u0006\u0002\b\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationSessionForLocalClasses;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationSession;", "()V", "getResolvedSuperTypeRefsForOutOfSessionDeclaration", "", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "classLikeDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "supertypeRefs", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "declaration", "supertypeRefs$resolve", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SupertypeComputationSessionForLocalClasses extends SupertypeComputationSession {
    @Override // org.jetbrains.kotlin.fir.resolve.transformers.SupertypeComputationSession
    protected List<FirResolvedTypeRef> getResolvedSuperTypeRefsForOutOfSessionDeclaration(FirClassLikeDeclaration classLikeDeclaration) {
        Intrinsics.checkNotNullParameter(classLikeDeclaration, "classLikeDeclaration");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(classLikeDeclaration, FirResolvePhase.SUPER_TYPES);
        return super.getResolvedSuperTypeRefsForOutOfSessionDeclaration(classLikeDeclaration);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.SupertypeComputationSession
    public List<FirTypeRef> supertypeRefs$resolve(FirClassLikeDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!declaration.getSymbol().getClassId().isLocal()) {
            FirLazyDeclarationResolverKt.lazyResolveToPhase(declaration, FirResolvePhase.SUPER_TYPES);
        }
        return super.supertypeRefs$resolve(declaration);
    }
}
