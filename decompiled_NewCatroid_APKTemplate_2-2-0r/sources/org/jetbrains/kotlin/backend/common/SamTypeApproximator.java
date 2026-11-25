package org.jetbrains.kotlin.backend.common;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: SamTypeFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u000f\u001a\u0004\u0018\u00010\n*\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/SamTypeApproximator;", "", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "getSamTypeForValueParameter", "Lorg/jetbrains/kotlin/types/KotlinType;", "valueParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "carefulApproximationOfContravariantProjection", "", "removeExternalProjections", "backend-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamTypeApproximator {
    private final TypeApproximator typeApproximator;

    public SamTypeApproximator(KotlinBuiltIns builtIns, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.typeApproximator = new TypeApproximator(builtIns, languageVersionSettings);
    }

    public final KotlinType getSamTypeForValueParameter(ValueParameterDescriptor valueParameter, boolean carefulApproximationOfContravariantProjection) {
        KotlinType type;
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        KotlinType varargElementType = valueParameter.getVarargElementType();
        if (varargElementType != null) {
            type = valueParameter.getOriginal().getVarargElementType();
        } else {
            varargElementType = valueParameter.getType();
            Intrinsics.checkNotNullExpressionValue(varargElementType, "getType(...)");
            type = valueParameter.getOriginal().getType();
        }
        if (KotlinTypeKt.isError(varargElementType)) {
            return null;
        }
        Intrinsics.checkNotNull(type);
        if (KotlinTypeKt.isError(type)) {
            return null;
        }
        if (KotlinBuiltIns.isNothing(varargElementType)) {
            varargElementType = TypeUtilsKt.replaceArgumentsWithNothing(type);
        }
        KotlinType kotlinType = varargElementType;
        KotlinTypeMarker kotlinTypeMarkerApproximateToSubType = this.typeApproximator.approximateToSubType(kotlinType, TypeApproximatorConfiguration.UpperBoundAwareIntersectionTypeApproximator.INSTANCE);
        if (kotlinTypeMarkerApproximateToSubType != null) {
            kotlinType = kotlinTypeMarkerApproximateToSubType;
        }
        Intrinsics.checkNotNull(kotlinType, "null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
        return removeExternalProjections((KotlinType) kotlinType, carefulApproximationOfContravariantProjection);
    }

    private final KotlinType removeExternalProjections(KotlinType kotlinType, boolean z) {
        TypeArgumentMarker typeArgumentMarkerAsTypeProjection;
        SimpleType simpleTypeUpperIfFlexible;
        List<TypeProjection> arguments = kotlinType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        int i = 0;
        for (Object obj : arguments) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeProjection typeProjection = (TypeProjection) obj;
            if (z && typeProjection.getProjectionKind() == Variance.IN_VARIANCE) {
                List<TypeParameterDescriptor> parameters = kotlinType.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) CollectionsKt.getOrNull(parameters, i);
                if (typeParameterDescriptor == null) {
                    return null;
                }
                List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
                Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
                KotlinType kotlinType2 = (KotlinType) CollectionsKt.singleOrNull((List) upperBounds);
                if (kotlinType2 == null || (simpleTypeUpperIfFlexible = FlexibleTypesKt.upperIfFlexible(kotlinType2)) == null) {
                    return null;
                }
                SimpleType simpleType = simpleTypeUpperIfFlexible;
                if (!TypeUtilsKt.isNullableAny(simpleType)) {
                    return null;
                }
                typeArgumentMarkerAsTypeProjection = TypeUtilsKt.asTypeProjection(simpleType);
            } else {
                typeArgumentMarkerAsTypeProjection = (TypeProjection) new TypeProjectionImpl(Variance.INVARIANT, typeProjection.getNullableAnyType());
            }
            arrayList.add(typeArgumentMarkerAsTypeProjection);
            i = i2;
        }
        return TypeSubstitutionKt.replace$default(kotlinType, arrayList, null, null, 6, null);
    }
}
