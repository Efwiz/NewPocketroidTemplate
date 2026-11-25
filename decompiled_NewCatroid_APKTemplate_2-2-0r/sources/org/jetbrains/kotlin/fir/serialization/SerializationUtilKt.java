package org.jetbrains.kotlin.fir.serialization;

import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: serializationUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004\u001a\u001a\u0010\u0006\u001a\u00020\u0007*\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"shouldBeSerialized", "", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "actualizedExpectDeclaration", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "suspendFunctionTypeToFunctionTypeWithContinuation", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "continuationClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "fir-serialization"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SerializationUtilKt {
    public static final ConeClassLikeType suspendFunctionTypeToFunctionTypeWithContinuation(ConeKotlinType coneKotlinType, FirSession session, ClassId continuationClassId) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(continuationClassId, "continuationClassId");
        if (!FunctionalTypeUtilsKt.isSuspendOrKSuspendFunctionType(coneKotlinType, session)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        FunctionTypeKind.Function function = FunctionalTypeUtilsKt.isReflectFunctionType(coneKotlinType, session) ? FunctionTypeKind.KFunction.INSTANCE : FunctionTypeKind.Function.INSTANCE;
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(coneKotlinType.getType(), session);
        ConeTypeProjection[] typeArguments = coneKotlinTypeFullyExpandedType.getTypeArguments();
        return new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(new ClassId(function.getPackageFqName(), function.numberedClassName(typeArguments.length))), (ConeTypeProjection[]) CollectionsKt.plus((Collection<? extends ConeClassLikeType>) CollectionsKt.plus((Collection<? extends ConeClassLikeType>) ArraysKt.dropLast(typeArguments, 1), TypeConstructionUtilsKt.constructClassType$default(TypeConstructionUtilsKt.toLookupTag(continuationClassId), new ConeTypeProjection[]{(ConeTypeProjection) ArraysKt.last(typeArguments)}, false, null, 4, null)), session.getBuiltinTypes().getNullableAnyType().getType()).toArray(new ConeTypeProjection[0]), ConeTypeUtilsKt.isNullable(coneKotlinTypeFullyExpandedType), coneKotlinTypeFullyExpandedType.getAttributes());
    }

    public static final boolean shouldBeSerialized(FirMemberDeclaration firMemberDeclaration, Set<? extends FirDeclaration> set) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return (firMemberDeclaration.getStatus().isExpect() && set != null && set.contains(firMemberDeclaration)) ? false : true;
    }
}
