package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;

/* JADX INFO: compiled from: inlineClassMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u001a\u0016\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0000\u001a\u0014\u0010\u0004\u001a\u00020\u0005*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0000¨\u0006\u0006"}, d2 = {"computeUnderlyingType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "inlineClassType", "shouldUseUnderlyingType", "", "backend.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineClassMappingKt {
    public static final KotlinTypeMarker computeUnderlyingType(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker inlineClassType) {
        KotlinTypeMarker unsubstitutedUnderlyingType;
        KotlinTypeMarker representativeUpperBound;
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(inlineClassType, "inlineClassType");
        if (!shouldUseUnderlyingType(typeSystemCommonBackendContext, inlineClassType) || (unsubstitutedUnderlyingType = typeSystemCommonBackendContext.getUnsubstitutedUnderlyingType(inlineClassType)) == null) {
            return null;
        }
        TypeParameterMarker typeParameterClassifier = typeSystemCommonBackendContext.getTypeParameterClassifier(typeSystemCommonBackendContext.typeConstructor(unsubstitutedUnderlyingType));
        return (typeParameterClassifier == null || (representativeUpperBound = typeSystemCommonBackendContext.getRepresentativeUpperBound(typeParameterClassifier)) == null) ? typeSystemCommonBackendContext.getSubstitutedUnderlyingType(inlineClassType) : representativeUpperBound;
    }

    public static final boolean shouldUseUnderlyingType(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker inlineClassType) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(inlineClassType, "inlineClassType");
        KotlinTypeMarker unsubstitutedUnderlyingType = typeSystemCommonBackendContext.getUnsubstitutedUnderlyingType(inlineClassType);
        if (unsubstitutedUnderlyingType == null) {
            return false;
        }
        if (typeSystemCommonBackendContext.isMarkedNullable(inlineClassType)) {
            if (typeSystemCommonBackendContext.isNullableType(unsubstitutedUnderlyingType)) {
                return false;
            }
            if ((unsubstitutedUnderlyingType instanceof SimpleTypeMarker) && typeSystemCommonBackendContext.isPrimitiveType((SimpleTypeMarker) unsubstitutedUnderlyingType)) {
                return false;
            }
        }
        return true;
    }
}
