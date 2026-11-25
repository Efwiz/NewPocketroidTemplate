package org.jetbrains.kotlin.resolve.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: PrimitiveNumericComparisonCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J2\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0016\u001a\u00020\u0017J\u0018\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u0014H\u0002J\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u0014*\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0002J\u000e\u0010\u001c\u001a\u0004\u0018\u00010\u0014*\u00020\u0014H\u0002J\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013*\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u0017H\u0002J\u0010\u0010\u001f\u001a\u00020 *\u0006\u0012\u0002\b\u00030\nH\u0002J\f\u0010!\u001a\u00020\u0014*\u00020\u0014H\u0002R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/PrimitiveNumericComparisonCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "comparisonOperatorTokens", "", "Lorg/jetbrains/kotlin/lexer/KtSingleValueToken;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "inferPrimitiveNumericComparisonType", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "leftTypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "rightTypes", "comparison", "Lorg/jetbrains/kotlin/psi/KtExpression;", "leastCommonPrimitiveNumericType", "t1", "t2", "findPrimitiveOrNullablePrimitiveType", "getPrimitiveTypeOrSupertype", "getStableTypesForExpression", "expression", "isStandardComparison", "", "promoteIntegerTypeToIntIfRequired", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveNumericComparisonCallChecker implements CallChecker {
    public static final PrimitiveNumericComparisonCallChecker INSTANCE = new PrimitiveNumericComparisonCallChecker();
    private static final Set<KtSingleValueToken> comparisonOperatorTokens = SetsKt.setOf((Object[]) new KtSingleValueToken[]{KtTokens.EQEQ, KtTokens.EXCLEQ, KtTokens.LT, KtTokens.LTEQ, KtTokens.GT, KtTokens.GTEQ});

    private PrimitiveNumericComparisonCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        KtExpression left;
        KtExpression right;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        KtElement callElement = resolvedCall.getCall().getCallElement();
        KtBinaryExpression ktBinaryExpression = callElement instanceof KtBinaryExpression ? (KtBinaryExpression) callElement : null;
        if (ktBinaryExpression == null || !CollectionsKt.contains(comparisonOperatorTokens, ktBinaryExpression.getOperationReference().getReferencedNameElementType()) || !isStandardComparison(resolvedCall) || (left = ktBinaryExpression.getLeft()) == null || (right = ktBinaryExpression.getRight()) == null) {
            return;
        }
        inferPrimitiveNumericComparisonType(context.getTrace(), getStableTypesForExpression(context, left), getStableTypesForExpression(context, right), ktBinaryExpression);
    }

    public final void inferPrimitiveNumericComparisonType(BindingTrace trace, List<? extends KotlinType> leftTypes, List<? extends KotlinType> rightTypes, KtExpression comparison) {
        KotlinType kotlinTypeFindPrimitiveOrNullablePrimitiveType;
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(leftTypes, "leftTypes");
        Intrinsics.checkNotNullParameter(rightTypes, "rightTypes");
        Intrinsics.checkNotNullParameter(comparison, "comparison");
        KotlinType kotlinTypeFindPrimitiveOrNullablePrimitiveType2 = findPrimitiveOrNullablePrimitiveType(leftTypes);
        if (kotlinTypeFindPrimitiveOrNullablePrimitiveType2 == null || (kotlinTypeFindPrimitiveOrNullablePrimitiveType = findPrimitiveOrNullablePrimitiveType(rightTypes)) == null) {
            return;
        }
        KotlinType kotlinTypeMakeNotNullable = TypeUtilsKt.makeNotNullable(kotlinTypeFindPrimitiveOrNullablePrimitiveType2);
        KotlinType kotlinTypeMakeNotNullable2 = TypeUtilsKt.makeNotNullable(kotlinTypeFindPrimitiveOrNullablePrimitiveType);
        trace.record(BindingContext.PRIMITIVE_NUMERIC_COMPARISON_INFO, comparison, new PrimitiveNumericComparisonInfo(leastCommonPrimitiveNumericType(kotlinTypeMakeNotNullable, kotlinTypeMakeNotNullable2), kotlinTypeMakeNotNullable, kotlinTypeMakeNotNullable2, kotlinTypeFindPrimitiveOrNullablePrimitiveType2, kotlinTypeFindPrimitiveOrNullablePrimitiveType));
    }

    private final boolean isStandardComparison(ResolvedCall<?> resolvedCall) {
        return resolvedCall.getExtensionReceiver() == null && resolvedCall.getDispatchReceiver() != null && KotlinBuiltIns.isUnderKotlinPackage(resolvedCall.getResultingDescriptor());
    }

    private final KotlinType leastCommonPrimitiveNumericType(KotlinType t1, KotlinType t2) {
        KotlinType kotlinTypePromoteIntegerTypeToIntIfRequired = promoteIntegerTypeToIntIfRequired(t1);
        KotlinType kotlinTypePromoteIntegerTypeToIntIfRequired2 = promoteIntegerTypeToIntIfRequired(t2);
        if (TypeUtilsKt.isDouble(kotlinTypePromoteIntegerTypeToIntIfRequired) || TypeUtilsKt.isDouble(kotlinTypePromoteIntegerTypeToIntIfRequired2)) {
            SimpleType doubleType = TypeUtilsKt.getBuiltIns(t1).getDoubleType();
            Intrinsics.checkNotNullExpressionValue(doubleType, "getDoubleType(...)");
            return doubleType;
        }
        if (TypeUtilsKt.isFloat(kotlinTypePromoteIntegerTypeToIntIfRequired) || TypeUtilsKt.isFloat(kotlinTypePromoteIntegerTypeToIntIfRequired2)) {
            SimpleType floatType = TypeUtilsKt.getBuiltIns(t1).getFloatType();
            Intrinsics.checkNotNullExpressionValue(floatType, "getFloatType(...)");
            return floatType;
        }
        if (TypeUtilsKt.isLong(kotlinTypePromoteIntegerTypeToIntIfRequired) || TypeUtilsKt.isLong(kotlinTypePromoteIntegerTypeToIntIfRequired2)) {
            SimpleType longType = TypeUtilsKt.getBuiltIns(t1).getLongType();
            Intrinsics.checkNotNullExpressionValue(longType, "getLongType(...)");
            return longType;
        }
        if (TypeUtilsKt.isInt(kotlinTypePromoteIntegerTypeToIntIfRequired) || TypeUtilsKt.isInt(kotlinTypePromoteIntegerTypeToIntIfRequired2)) {
            SimpleType intType = TypeUtilsKt.getBuiltIns(t1).getIntType();
            Intrinsics.checkNotNullExpressionValue(intType, "getIntType(...)");
            return intType;
        }
        throw new AssertionError("Unexpected types: t1=" + t1 + ", t2=" + t2);
    }

    private final KotlinType promoteIntegerTypeToIntIfRequired(KotlinType kotlinType) {
        if (!TypeUtilsKt.isPrimitiveNumberType(kotlinType)) {
            throw new AssertionError("Primitive number type expected: " + kotlinType);
        }
        if (!TypeUtilsKt.isByte(kotlinType) && !TypeUtilsKt.isShort(kotlinType)) {
            return kotlinType;
        }
        SimpleType intType = TypeUtilsKt.getBuiltIns(kotlinType).getIntType();
        Intrinsics.checkNotNullExpressionValue(intType, "getIntType(...)");
        return intType;
    }

    private final List<KotlinType> getStableTypesForExpression(CallCheckerContext callCheckerContext, KtExpression ktExpression) {
        DataFlowInfo dataFlowInfo;
        KotlinType type = callCheckerContext.getTrace().getContext().getType(ktExpression);
        if (type == null) {
            return CollectionsKt.emptyList();
        }
        DataFlowValueFactory dataFlowValueFactory = callCheckerContext.getDataFlowValueFactory();
        BindingContext context = callCheckerContext.getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        DataFlowValue dataFlowValueCreateDataFlowValue = dataFlowValueFactory.createDataFlowValue(ktExpression, type, context, callCheckerContext.getResolutionContext().scope.getOwnerDescriptor());
        KotlinTypeInfo kotlinTypeInfo = (KotlinTypeInfo) callCheckerContext.getTrace().get(BindingContext.EXPRESSION_TYPE_INFO, ktExpression);
        if (kotlinTypeInfo == null || (dataFlowInfo = kotlinTypeInfo.getDataFlowInfo()) == null) {
            return CollectionsKt.emptyList();
        }
        return CollectionsKt.plus((Collection) CollectionsKt.listOf(type), (Iterable) dataFlowInfo.getStableTypes(dataFlowValueCreateDataFlowValue, callCheckerContext.getLanguageVersionSettings()));
    }

    private final KotlinType findPrimitiveOrNullablePrimitiveType(List<? extends KotlinType> list) {
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            KotlinType primitiveTypeOrSupertype = INSTANCE.getPrimitiveTypeOrSupertype((KotlinType) it.next());
            if (primitiveTypeOrSupertype != null) {
                return primitiveTypeOrSupertype;
            }
        }
        return null;
    }

    private final KotlinType getPrimitiveTypeOrSupertype(KotlinType kotlinType) {
        if (kotlinType.getConstructor().getThis$0() instanceof TypeParameterDescriptor) {
            Iterator<T> it = TypeUtilsKt.immediateSupertypes(kotlinType).iterator();
            while (it.hasNext()) {
                KotlinType primitiveTypeOrSupertype = INSTANCE.getPrimitiveTypeOrSupertype((KotlinType) it.next());
                if (primitiveTypeOrSupertype != null) {
                    return primitiveTypeOrSupertype;
                }
            }
        } else if (TypeUtilsKt.isPrimitiveNumberOrNullableType(kotlinType)) {
            return kotlinType;
        }
        return null;
    }
}
