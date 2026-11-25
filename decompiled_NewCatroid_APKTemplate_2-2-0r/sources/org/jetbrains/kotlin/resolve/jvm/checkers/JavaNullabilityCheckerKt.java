package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.TypeWithEnhancement;

/* JADX INFO: compiled from: JavaNullabilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u001a\f\u0010\u0003\u001a\u00020\u0001*\u00020\u0004H\u0002\u001a\f\u0010\u0005\u001a\u0004\u0018\u00010\u0001*\u00020\u0004¨\u0006\u0006"}, d2 = {"enhancementFromJava", "Lorg/jetbrains/kotlin/resolve/jvm/checkers/EnhancedNullabilityInfo;", "Lorg/jetbrains/kotlin/types/TypeWithEnhancement;", "enhancementFromKotlin", "Lorg/jetbrains/kotlin/types/KotlinType;", "mustNotBeNull", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaNullabilityCheckerKt {
    private static final EnhancedNullabilityInfo enhancementFromKotlin(KotlinType kotlinType) {
        return new EnhancedNullabilityInfo(kotlinType, false);
    }

    private static final EnhancedNullabilityInfo enhancementFromJava(TypeWithEnhancement typeWithEnhancement) {
        return new EnhancedNullabilityInfo(typeWithEnhancement.getEnhancement(), true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final EnhancedNullabilityInfo mustNotBeNull(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        if (!KotlinTypeKt.isError(kotlinType) && !FlexibleTypesKt.isFlexible(kotlinType) && !TypeUtils.acceptsNullable(kotlinType)) {
            return enhancementFromKotlin(kotlinType);
        }
        if (FlexibleTypesKt.isFlexible(kotlinType) && !TypeUtils.acceptsNullable(FlexibleTypesKt.asFlexibleType(kotlinType).getUpperBound())) {
            return enhancementFromKotlin(kotlinType);
        }
        if (kotlinType instanceof TypeWithEnhancement) {
            TypeWithEnhancement typeWithEnhancement = (TypeWithEnhancement) kotlinType;
            if (mustNotBeNull(typeWithEnhancement.getEnhancement()) != null) {
                return enhancementFromJava(typeWithEnhancement);
            }
        }
        return null;
    }
}
