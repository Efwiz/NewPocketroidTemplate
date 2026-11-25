package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;

/* JADX INFO: compiled from: ResolutionMode.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u001a!\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b\u0006\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u0005\u001a\u0014\u0010\n\u001a\u0004\u0018\u00010\b*\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f\u001a\n\u0010\r\u001a\u00020\u0001*\u00020\u000e¨\u0006\u000f"}, d2 = {"withExpectedType", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "coneType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "mayBeCoercionToUnitApplied", "", "withExpectedTypeNullable", "expectedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "expectedTypeMismatchIsReportedInChecker", "expectedType", "components", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "mode", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResolutionModeKt {
    public static final FirTypeRef expectedType(ResolutionMode resolutionMode, BodyResolveComponents components) {
        Intrinsics.checkNotNullParameter(resolutionMode, "<this>");
        Intrinsics.checkNotNullParameter(components, "components");
        if (resolutionMode instanceof ResolutionMode.WithExpectedType) {
            ResolutionMode.WithExpectedType withExpectedType = (ResolutionMode.WithExpectedType) resolutionMode;
            return withExpectedType.getFromCast() ? null : withExpectedType.getExpectedTypeRef();
        }
        if (resolutionMode instanceof ResolutionMode.ContextIndependent ? true : resolutionMode instanceof ResolutionMode.AssignmentLValue ? true : resolutionMode instanceof ResolutionMode.ReceiverResolution) {
            return components.getNoExpectedType();
        }
        return null;
    }

    public static /* synthetic */ ResolutionMode withExpectedType$default(FirTypeRef firTypeRef, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return withExpectedType(firTypeRef, z);
    }

    public static final ResolutionMode withExpectedType(FirTypeRef expectedTypeRef, boolean z) {
        Intrinsics.checkNotNullParameter(expectedTypeRef, "expectedTypeRef");
        if (expectedTypeRef instanceof FirResolvedTypeRef) {
            return new ResolutionMode.WithExpectedType((FirResolvedTypeRef) expectedTypeRef, false, z, false, false, false, 58, null);
        }
        return ResolutionMode.ContextIndependent.INSTANCE;
    }

    public static /* synthetic */ ResolutionMode withExpectedTypeNullable$default(ConeKotlinType coneKotlinType, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return withExpectedTypeNullable(coneKotlinType, z);
    }

    public static final ResolutionMode withExpectedTypeNullable(ConeKotlinType coneKotlinType, boolean z) {
        ResolutionMode resolutionModeWithExpectedType;
        return (coneKotlinType == null || (resolutionModeWithExpectedType = withExpectedType(coneKotlinType, z)) == null) ? ResolutionMode.ContextDependent.INSTANCE : resolutionModeWithExpectedType;
    }

    public static /* synthetic */ ResolutionMode withExpectedType$default(ConeKotlinType coneKotlinType, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return withExpectedType(coneKotlinType, z);
    }

    public static final ResolutionMode mode(FirDeclarationStatus firDeclarationStatus) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<this>");
        return new ResolutionMode.WithStatus(firDeclarationStatus);
    }

    public static final ResolutionMode withExpectedType(ConeKotlinType coneType, boolean z) {
        Intrinsics.checkNotNullParameter(coneType, "coneType");
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(coneType);
        return new ResolutionMode.WithExpectedType(firResolvedTypeRefBuilder.build(), z, false, false, false, false, 60, null);
    }
}
