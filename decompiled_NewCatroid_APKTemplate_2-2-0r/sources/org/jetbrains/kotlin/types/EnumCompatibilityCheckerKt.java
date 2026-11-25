package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: enumCompatibilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007\u001a\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0002\u001a\f\u0010\u000b\u001a\u00020\u0007*\u00020\u0007H\u0002¨\u0006\f"}, d2 = {"checkEnumsForCompatibility", "", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "reportOn", "Lorg/jetbrains/kotlin/psi/KtElement;", "typeA", "Lorg/jetbrains/kotlin/types/KotlinType;", "typeB", "isIncompatibleEnums", "", "representativeTypeForTypeParameter", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EnumCompatibilityCheckerKt {
    public static final void checkEnumsForCompatibility(ResolutionContext<?> context, KtElement reportOn, KotlinType typeA, KotlinType typeB) {
        DiagnosticFactory2<KtElement, KotlinType, KotlinType> diagnosticFactory2;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(typeA, "typeA");
        Intrinsics.checkNotNullParameter(typeB, "typeB");
        if (isIncompatibleEnums(typeA, typeB)) {
            if (context.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitComparisonOfIncompatibleEnums)) {
                diagnosticFactory2 = Errors.INCOMPATIBLE_ENUM_COMPARISON_ERROR;
            } else {
                diagnosticFactory2 = Errors.INCOMPATIBLE_ENUM_COMPARISON;
            }
            context.trace.report(diagnosticFactory2.on(reportOn, typeA, typeB));
        }
    }

    private static final boolean isIncompatibleEnums(KotlinType kotlinType, KotlinType kotlinType2) {
        if (!TypeUtilsKt.isEnum(kotlinType) && !TypeUtilsKt.isEnum(kotlinType2)) {
            return false;
        }
        if ((TypeUtils.isNullableType(kotlinType) && TypeUtils.isNullableType(kotlinType2)) || TypeUtilsKt.isNothingOrNullableNothing(kotlinType) || TypeUtilsKt.isNothingOrNullableNothing(kotlinType2)) {
            return false;
        }
        KotlinType kotlinTypeRepresentativeTypeForTypeParameter = representativeTypeForTypeParameter(kotlinType);
        KotlinType kotlinTypeRepresentativeTypeForTypeParameter2 = representativeTypeForTypeParameter(kotlinType2);
        ClassifierDescriptor this$0 = kotlinTypeRepresentativeTypeForTypeParameter.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null) {
            return false;
        }
        ClassifierDescriptor this$1 = kotlinTypeRepresentativeTypeForTypeParameter2.getConstructor().getThis$0();
        ClassDescriptor classDescriptor2 = this$1 instanceof ClassDescriptor ? (ClassDescriptor) this$1 : null;
        return (classDescriptor2 == null || DescriptorUtils.isSubclass(classDescriptor, classDescriptor2) || DescriptorUtils.isSubclass(classDescriptor2, classDescriptor)) ? false : true;
    }

    private static final KotlinType representativeTypeForTypeParameter(KotlinType kotlinType) {
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        return this$0 instanceof TypeParameterDescriptor ? TypeUtilsKt.getRepresentativeUpperBound((TypeParameterDescriptor) this$0) : kotlinType;
    }
}
