package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.UpperBoundChecker;
import org.jetbrains.kotlin.resolve.UpperBoundViolatedReporter;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeWithEnhancementKt;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;

/* JADX INFO: compiled from: WarningAwareUpperBoundChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004Jh\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00142 \b\u0002\u0010\u0015\u001a\u001a\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00170\u0016J\\\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u001e\u0010\u0015\u001a\u001a\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00170\u0016H\u0016J \u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/WarningAwareUpperBoundChecker;", "Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;", "typeChecker", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeChecker;", "(Lorg/jetbrains/kotlin/types/checker/KotlinTypeChecker;)V", "checkBounds", "", "argumentReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "argumentType", "Lorg/jetbrains/kotlin/types/KotlinType;", "typeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "typeAliasUsageElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "withOnlyCheckForWarning", "", "diagnosticForTypeAliases", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory3;", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "checkBoundsOfExpandedTypeAlias", ModuleXmlParser.TYPE, "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WarningAwareUpperBoundChecker extends UpperBoundChecker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WarningAwareUpperBoundChecker(KotlinTypeChecker typeChecker) {
        super(typeChecker);
        Intrinsics.checkNotNullParameter(typeChecker, "typeChecker");
    }

    @Override // org.jetbrains.kotlin.resolve.UpperBoundChecker
    public void checkBoundsOfExpandedTypeAlias(KotlinType type, KtExpression expression, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(trace, "trace");
        List<TypeParameterDescriptor> parameters = type.getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        Iterator<TypeProjection> it = type.getArguments().iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i + 1;
            KotlinType nullableAnyType = it.next().getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
            TypeParameterDescriptor typeParameterDescriptor = parameters.get(i);
            Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "get(...)");
            TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(type);
            Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
            checkBounds$default(this, null, nullableAnyType, typeParameterDescriptor, typeSubstitutorCreate, trace, expression, true, null, 128, null);
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.resolve.UpperBoundChecker
    public void checkBounds(KtTypeReference argumentReference, KotlinType argumentType, TypeParameterDescriptor typeParameterDescriptor, TypeSubstitutor substitutor, BindingTrace trace, KtElement typeAliasUsageElement, DiagnosticFactory3<KtElement, KotlinType, KotlinType, ClassifierDescriptor> diagnosticForTypeAliases) {
        Intrinsics.checkNotNullParameter(argumentType, "argumentType");
        Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameterDescriptor");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(diagnosticForTypeAliases, "diagnosticForTypeAliases");
        checkBounds(argumentReference, argumentType, typeParameterDescriptor, substitutor, trace, typeAliasUsageElement, false, diagnosticForTypeAliases);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void checkBounds$default(WarningAwareUpperBoundChecker warningAwareUpperBoundChecker, KtTypeReference ktTypeReference, KotlinType kotlinType, TypeParameterDescriptor typeParameterDescriptor, TypeSubstitutor typeSubstitutor, BindingTrace bindingTrace, KtElement ktElement, boolean z, DiagnosticFactory3 diagnosticFactory3, int i, Object obj) {
        DiagnosticFactory3 diagnosticFactory4;
        KtElement ktElement2 = (i & 32) != 0 ? null : ktElement;
        boolean z2 = (i & 64) != 0 ? false : z;
        if ((i & 128) != 0) {
            DiagnosticFactory3<KtElement, KotlinType, KotlinType, ClassifierDescriptor> UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION = Errors.UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION;
            Intrinsics.checkNotNullExpressionValue(UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION, "UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION");
            diagnosticFactory4 = UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION;
        } else {
            diagnosticFactory4 = diagnosticFactory3;
        }
        warningAwareUpperBoundChecker.checkBounds(ktTypeReference, kotlinType, typeParameterDescriptor, typeSubstitutor, bindingTrace, ktElement2, z2, diagnosticFactory4);
    }

    public final void checkBounds(KtTypeReference argumentReference, KotlinType argumentType, TypeParameterDescriptor typeParameterDescriptor, TypeSubstitutor substitutor, BindingTrace trace, KtElement typeAliasUsageElement, boolean withOnlyCheckForWarning, DiagnosticFactory3<KtElement, KotlinType, KotlinType, ClassifierDescriptor> diagnosticForTypeAliases) {
        Intrinsics.checkNotNullParameter(argumentType, "argumentType");
        Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameterDescriptor");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(diagnosticForTypeAliases, "diagnosticForTypeAliases");
        if (typeParameterDescriptor.getUpperBounds().isEmpty()) {
            return;
        }
        UpperBoundViolatedReporter upperBoundViolatedReporter = new UpperBoundViolatedReporter(trace, argumentType, typeParameterDescriptor, null, diagnosticForTypeAliases, 8, null);
        DiagnosticFactory2<KtTypeReference, KotlinType, KotlinType> UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS = ErrorsJvm.UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS;
        Intrinsics.checkNotNullExpressionValue(UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS, "UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS");
        DiagnosticFactory3<KtElement, KotlinType, KotlinType, ClassifierDescriptor> diagnosticFactory3 = ErrorsJvm.UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION_BASED_ON_JAVA_ANNOTATIONS;
        Intrinsics.checkNotNullExpressionValue(diagnosticFactory3, "UPPER_BOUND_VIOLATED_IN_…BASED_ON_JAVA_ANNOTATIONS");
        UpperBoundViolatedReporter upperBoundViolatedReporter2 = new UpperBoundViolatedReporter(trace, argumentType, typeParameterDescriptor, UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS, diagnosticFactory3);
        for (KotlinType kotlinType : typeParameterDescriptor.getUpperBounds()) {
            if (!withOnlyCheckForWarning) {
                Intrinsics.checkNotNull(kotlinType);
                if (checkBound(kotlinType, argumentType, argumentReference, substitutor, typeAliasUsageElement, upperBoundViolatedReporter)) {
                }
            }
            Intrinsics.checkNotNull(kotlinType);
            KotlinType enhancementDeeply = TypeWithEnhancementKt.getEnhancementDeeply(kotlinType);
            if (enhancementDeeply != null) {
                KotlinType enhancementDeeply2 = TypeWithEnhancementKt.getEnhancementDeeply(argumentType);
                checkBound(enhancementDeeply, enhancementDeeply2 == null ? argumentType : enhancementDeeply2, argumentReference, substitutor, typeAliasUsageElement, upperBoundViolatedReporter2);
            }
        }
    }
}
