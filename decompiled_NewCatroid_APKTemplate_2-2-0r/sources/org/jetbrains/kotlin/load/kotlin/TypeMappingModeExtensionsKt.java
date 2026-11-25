package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: TypeMappingModeExtensions.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001c\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0006H\u0002\u001a\u0012\u0010\t\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\n"}, d2 = {"getOptimalModeForReturnType", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "isAnnotationMethod", "", "getOptimalModeForSignaturePart", "canBeUsedInSupertypePosition", "getOptimalModeForValueParameter", "backend.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeMappingModeExtensionsKt {
    public static final TypeMappingMode getOptimalModeForValueParameter(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return getOptimalModeForSignaturePart(typeSystemCommonBackendContext, type, true);
    }

    public static final TypeMappingMode getOptimalModeForReturnType(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker type, boolean z) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        if (z) {
            return TypeMappingMode.VALUE_FOR_ANNOTATION;
        }
        return getOptimalModeForSignaturePart(typeSystemCommonBackendContext, type, false);
    }

    private static final TypeMappingMode getOptimalModeForSignaturePart(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker kotlinTypeMarker, boolean z) {
        KotlinTypeMarker kotlinTypeMarkerComputeUnderlyingType;
        if (typeSystemCommonBackendContext.argumentsCount(kotlinTypeMarker) == 0) {
            return TypeMappingMode.DEFAULT;
        }
        boolean zIsInlineClass = typeSystemCommonBackendContext.isInlineClass(typeSystemCommonBackendContext.typeConstructor(kotlinTypeMarker));
        if (zIsInlineClass && InlineClassMappingKt.shouldUseUnderlyingType(typeSystemCommonBackendContext, kotlinTypeMarker) && (kotlinTypeMarkerComputeUnderlyingType = InlineClassMappingKt.computeUnderlyingType(typeSystemCommonBackendContext, kotlinTypeMarker)) != null) {
            return getOptimalModeForSignaturePart(typeSystemCommonBackendContext, kotlinTypeMarkerComputeUnderlyingType, z).dontWrapInlineClassesMode();
        }
        return new TypeMappingMode(false, !zIsInlineClass, false, !z, true, null, false, !z ? new TypeMappingMode(false, false, false, false, true, null, false, null, null, false, 999, null) : null, z ? getOptimalModeForSignaturePart(typeSystemCommonBackendContext, kotlinTypeMarker, false) : null, false, 613, null);
    }
}
