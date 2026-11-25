package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: InferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a'\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u00012\u0006\u0010\t\u001a\u00020\nH\u0002\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0000(\u0000\u001a\n\u0010\f\u001a\u00020\r*\u00020\u0001\u001a\u0012\u0010\u000e\u001a\u00020\r*\u00020\u00012\u0006\u0010\t\u001a\u00020\n\u001a\u0012\u0010\u000f\u001a\u00020\r*\u00020\u00012\u0006\u0010\t\u001a\u00020\n\u001a\u001a\u0010\u0010\u001a\u00020\r*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u0001\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004ò\u0001\u0004\n\u00020\u000b¨\u0006\u0014"}, d2 = {"receiverType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "getReceiverType", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "returnType", "getReturnType", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "isKClassType", "", "isKMutableProperty", "isKProperty", "isTypeMismatchDueToNullability", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "actualType", "expectedType", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InferenceUtilsKt {
    private static final ClassId classId(ConeKotlinType coneKotlinType, FirSession firSession) {
        if (coneKotlinType instanceof ConeClassLikeType) {
            return TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) coneKotlinType, firSession, null, 2, null).getLookupTag().getClassId();
        }
        return null;
    }

    public static final boolean isKProperty(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ClassId classId = classId(coneKotlinType, session);
        if (classId == null || !Intrinsics.areEqual(classId.getPackageFqName(), StandardClassIds.INSTANCE.getBASE_REFLECT_PACKAGE())) {
            return false;
        }
        String identifier = classId.getShortClassName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        return StringsKt.startsWith$default(identifier, StandardNames.K_PROPERTY_PREFIX, false, 2, (Object) null);
    }

    public static final boolean isKMutableProperty(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ClassId classId = classId(coneKotlinType, session);
        if (classId == null || !Intrinsics.areEqual(classId.getPackageFqName(), StandardClassIds.INSTANCE.getBASE_REFLECT_PACKAGE())) {
            return false;
        }
        String identifier = classId.getShortClassName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        return StringsKt.startsWith$default(identifier, StandardNames.K_MUTABLE_PROPERTY_PREFIX, false, 2, (Object) null);
    }

    public static final boolean isKClassType(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(coneKotlinType), StandardClassIds.INSTANCE.getKClass());
    }

    public static final ConeKotlinType getReturnType(FirAnonymousFunction firAnonymousFunction) {
        Intrinsics.checkNotNullParameter(firAnonymousFunction, "<this>");
        FirTypeRef returnTypeRef = firAnonymousFunction.getReturnTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (type instanceof ConeKotlinType) {
            return type;
        }
        return null;
    }

    public static final ConeKotlinType getReceiverType(FirAnonymousFunction firAnonymousFunction) {
        FirTypeRef typeRef;
        Intrinsics.checkNotNullParameter(firAnonymousFunction, "<this>");
        FirReceiverParameter receiverParameter = firAnonymousFunction.getReceiverParameter();
        if (receiverParameter == null || (typeRef = receiverParameter.getTypeRef()) == null) {
            return null;
        }
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (type instanceof ConeKotlinType) {
            return type;
        }
        return null;
    }

    public static final boolean isTypeMismatchDueToNullability(ConeTypeContext coneTypeContext, ConeKotlinType actualType, ConeKotlinType expectedType) {
        Intrinsics.checkNotNullParameter(coneTypeContext, "<this>");
        Intrinsics.checkNotNullParameter(actualType, "actualType");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        ConeKotlinType coneKotlinType = actualType;
        return coneTypeContext.isNullableType(coneKotlinType) && !coneTypeContext.isNullableType(expectedType) && AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) coneTypeContext, (KotlinTypeMarker) coneKotlinType, (KotlinTypeMarker) TypeUtilsKt.withNullability$default(expectedType, ConeNullability.NULLABLE, coneTypeContext, null, false, 12, null), false, 8, (Object) null);
    }
}
