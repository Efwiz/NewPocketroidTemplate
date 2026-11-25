package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;

/* JADX INFO: compiled from: DeclarationApproximationUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a8\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u0007\u001a\u0014\u0010\n\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¨\u0006\f"}, d2 = {"approximateDeclarationType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "containingCallableVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "isLocal", "", "isInlineFunction", "stripEnhancedNullability", "substituteAlternativesInPublicType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationApproximationUtilsKt {
    public static /* synthetic */ FirTypeRef approximateDeclarationType$default(FirTypeRef firTypeRef, FirSession firSession, Visibility visibility, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 8) != 0) {
            z2 = false;
        }
        boolean z4 = z2;
        if ((i & 16) != 0) {
            z3 = true;
        }
        return approximateDeclarationType(firTypeRef, firSession, visibility, z, z4, z3);
    }

    public static final FirTypeRef approximateDeclarationType(FirTypeRef firTypeRef, FirSession session, Visibility visibility, boolean z, boolean z2, boolean z3) {
        ConeKotlinType type;
        TypeApproximatorConfiguration.PublicDeclaration.SaveAnonymousTypes saveAnonymousTypes;
        TypeApproximatorConfiguration.AllFlexibleSameValue allFlexibleSameValue;
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
        if (firResolvedTypeRef == null || (type = firResolvedTypeRef.getType()) == null) {
            return firTypeRef;
        }
        if (z) {
            allFlexibleSameValue = TypeApproximatorConfiguration.LocalDeclaration.INSTANCE;
        } else {
            if (z) {
                throw new NoWhenBranchMatchedException();
            }
            boolean zShouldApproximateAnonymousTypesOfNonLocalDeclaration = TypeUtilsKt.shouldApproximateAnonymousTypesOfNonLocalDeclaration(visibility, z2);
            if (zShouldApproximateAnonymousTypesOfNonLocalDeclaration) {
                saveAnonymousTypes = TypeApproximatorConfiguration.PublicDeclaration.ApproximateAnonymousTypes.INSTANCE;
            } else if (!zShouldApproximateAnonymousTypesOfNonLocalDeclaration) {
                saveAnonymousTypes = TypeApproximatorConfiguration.PublicDeclaration.SaveAnonymousTypes.INSTANCE;
            } else {
                throw new NoWhenBranchMatchedException();
            }
            allFlexibleSameValue = saveAnonymousTypes;
        }
        if (!z) {
            type = substituteAlternativesInPublicType(type, session);
        }
        ConeKotlinType coneKotlinTypeApproximateToSuperType = TypeComponentsKt.getTypeApproximator(session).approximateToSuperType(type, (TypeApproximatorConfiguration) allFlexibleSameValue);
        if (coneKotlinTypeApproximateToSuperType != null) {
            type = coneKotlinTypeApproximateToSuperType;
        }
        FirTypeRef firTypeRefWithReplacedConeType$default = TypeUtilsKt.withReplacedConeType$default(firTypeRef, type, null, 2, null);
        if (z3) {
            firTypeRefWithReplacedConeType$default = TypeUtilsKt.withoutEnhancedNullability(firTypeRefWithReplacedConeType$default);
        }
        return firTypeRefWithReplacedConeType$default;
    }

    private static final ConeKotlinType substituteAlternativesInPublicType(ConeKotlinType coneKotlinType, FirSession firSession) {
        final ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(firSession);
        return new AbstractConeSubstitutor(typeContext) { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.DeclarationApproximationUtilsKt$substituteAlternativesInPublicType$substitutor$1
            {
                super(typeContext);
            }

            @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
            public ConeKotlinType substituteType(ConeKotlinType type) {
                ConeKotlinType alternativeType;
                Intrinsics.checkNotNullParameter(type, "type");
                if ((type instanceof ConeIntersectionType) && (alternativeType = ((ConeIntersectionType) type).getAlternativeType()) != null) {
                    return substituteOrSelf(alternativeType);
                }
                return null;
            }
        }.substituteOrSelf(coneKotlinType);
    }
}
