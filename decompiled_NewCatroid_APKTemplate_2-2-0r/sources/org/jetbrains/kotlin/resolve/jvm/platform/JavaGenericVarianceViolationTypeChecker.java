package org.jetbrains.kotlin.resolve.jvm.platform;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.load.java.lazy.types.RawTypeImpl;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.checker.TypeCheckingProcedure;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: JavaGenericVarianceViolationTypeChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/platform/JavaGenericVarianceViolationTypeChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/AdditionalTypeChecker;", "()V", "checkType", "", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "expressionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expressionTypeWithSmartCast", "c", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaGenericVarianceViolationTypeChecker implements AdditionalTypeChecker {
    public static final JavaGenericVarianceViolationTypeChecker INSTANCE = new JavaGenericVarianceViolationTypeChecker();

    private JavaGenericVarianceViolationTypeChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker
    public void checkType(KtExpression expression, KotlinType expressionType, KotlinType expressionTypeWithSmartCast, ResolutionContext<?> c) {
        KotlinType kotlinTypeFindCorrespondingSupertype;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(expressionType, "expressionType");
        Intrinsics.checkNotNullParameter(expressionTypeWithSmartCast, "expressionTypeWithSmartCast");
        Intrinsics.checkNotNullParameter(c, "c");
        KotlinType expectedType = c.expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
        if (TypeUtils.noExpectedType(expectedType) || ErrorUtils.INSTANCE.containsErrorType(expectedType) || ErrorUtils.INSTANCE.containsUninferredTypeVariable(expectedType) || expectedType.getArguments().isEmpty() || !FlexibleTypesKt.isFlexible(expectedType)) {
            return;
        }
        SimpleType lowerBound = FlexibleTypesKt.asFlexibleType(expectedType).getLowerBound();
        SimpleType upperBound = FlexibleTypesKt.asFlexibleType(expectedType).getUpperBound();
        if (Intrinsics.areEqual(lowerBound.getConstructor(), upperBound.getConstructor()) || (expectedType.unwrap() instanceof RawTypeImpl) || (kotlinTypeFindCorrespondingSupertype = TypeCheckingProcedure.findCorrespondingSupertype(expressionTypeWithSmartCast, lowerBound)) == null) {
            return;
        }
        lowerBound.getArguments().size();
        upperBound.getArguments().size();
        lowerBound.getArguments().size();
        kotlinTypeFindCorrespondingSupertype.getArguments().size();
        List<TypeParameterDescriptor> parameters = lowerBound.getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<TypeParameterDescriptor> parameters2 = upperBound.getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
        List<TypeProjection> arguments = lowerBound.getArguments();
        Iterator<Integer> it = CollectionsKt.getIndices(kotlinTypeFindCorrespondingSupertype.getArguments()).iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            TypeProjection typeProjection = arguments.get(iNextInt);
            if (parameters.get(iNextInt).getVariance() == Variance.INVARIANT && parameters2.get(iNextInt).getVariance() == Variance.OUT_VARIANCE && typeProjection.getProjectionKind() != Variance.OUT_VARIANCE && !KotlinTypeChecker.DEFAULT.equalTypes(kotlinTypeFindCorrespondingSupertype.getArguments().get(iNextInt).getNullableAnyType(), typeProjection.getNullableAnyType())) {
                c.trace.report(ErrorsJvm.JAVA_TYPE_MISMATCH.on(expression, expressionTypeWithSmartCast, expectedType));
            }
        }
    }
}
