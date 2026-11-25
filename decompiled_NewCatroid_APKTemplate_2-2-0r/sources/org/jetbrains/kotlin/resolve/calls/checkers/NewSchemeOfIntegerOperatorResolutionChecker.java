package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.ErrorValue;
import org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.resolve.constants.TypedCompileTimeConstant;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: NewSchemeOfIntegerOperatorResolutionChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J(\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J(\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u001c\u001a\u00020\u001b*\u0006\u0012\u0002\b\u00030\tH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/NewSchemeOfIntegerOperatorResolutionChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "literalOperatorsFqNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "checkArgument", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "argument", "Lorg/jetbrains/kotlin/psi/KtExpression;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "checkArgumentImpl", "Lorg/jetbrains/kotlin/types/SimpleType;", "argumentExpression", "needToCheck", "", "isIntOperator", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewSchemeOfIntegerOperatorResolutionChecker implements CallChecker {
    public static final NewSchemeOfIntegerOperatorResolutionChecker INSTANCE = new NewSchemeOfIntegerOperatorResolutionChecker();
    private static final Set<FqName> literalOperatorsFqNames;

    private NewSchemeOfIntegerOperatorResolutionChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        KotlinType type;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            ResolvedValueArgument value = entry.getValue();
            Intrinsics.checkNotNull(key);
            if (ArgumentsUtilsKt.isVararg(key)) {
                type = key.getVarargElementType();
                if (type == null) {
                }
            } else {
                type = key.getType();
            }
            SimpleType simpleTypeLowerIfFlexible = FlexibleTypesKt.lowerIfFlexible(type.unwrap());
            if (needToCheck(simpleTypeLowerIfFlexible)) {
                Iterator<ValueArgument> it = value.getArguments().iterator();
                while (it.hasNext()) {
                    KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(it.next().getArgumentExpression());
                    if (ktExpressionDeparenthesize != null) {
                        checkArgumentImpl(simpleTypeLowerIfFlexible, ktExpressionDeparenthesize, context.getTrace(), context.getModuleDescriptor());
                    }
                }
            }
        }
    }

    @JvmStatic
    public static final void checkArgument(KotlinType expectedType, KtExpression argument, BindingTrace trace, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        NewSchemeOfIntegerOperatorResolutionChecker newSchemeOfIntegerOperatorResolutionChecker = INSTANCE;
        if (newSchemeOfIntegerOperatorResolutionChecker.needToCheck(expectedType)) {
            SimpleType simpleTypeLowerIfFlexible = FlexibleTypesKt.lowerIfFlexible(expectedType);
            KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(argument);
            Intrinsics.checkNotNull(ktExpressionDeparenthesize);
            newSchemeOfIntegerOperatorResolutionChecker.checkArgumentImpl(simpleTypeLowerIfFlexible, ktExpressionDeparenthesize, trace, moduleDescriptor);
        }
    }

    public final boolean needToCheck(KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        if (TypeUtils.noExpectedType(expectedType)) {
            return false;
        }
        return TypeUtilsKt.isPrimitiveNumberOrNullableType(FlexibleTypesKt.lowerIfFlexible(expectedType));
    }

    private final void checkArgumentImpl(SimpleType expectedType, KtExpression argumentExpression, BindingTrace trace, ModuleDescriptor moduleDescriptor) {
        SimpleType approximatedType;
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(argumentExpression, context);
        if (resolvedCall != null && isIntOperator(resolvedCall)) {
            KtElement callElement = resolvedCall.getCall().getCallElement();
            KtExpression ktExpression = callElement instanceof KtExpression ? (KtExpression) callElement : null;
            if (ktExpression == null) {
                return;
            }
            KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(ktExpression);
            Intrinsics.checkNotNull(ktExpressionDeparenthesize);
            if (ktExpressionDeparenthesize instanceof KtConstantExpression) {
                return;
            }
            if (ktExpressionDeparenthesize instanceof KtUnaryExpression) {
                IElementType operationToken = ((KtUnaryExpression) ktExpressionDeparenthesize).getOperationToken();
                if (Intrinsics.areEqual(operationToken, KtTokens.PLUS) || Intrinsics.areEqual(operationToken, KtTokens.MINUS)) {
                    return;
                }
            }
            CompileTimeConstant compileTimeConstant = (CompileTimeConstant) context.get(BindingContext.COMPILE_TIME_VALUE, argumentExpression);
            if (compileTimeConstant == null) {
                return;
            }
            if (compileTimeConstant instanceof IntegerValueTypeConstant) {
                TypeConstructor constructor = ((IntegerValueTypeConstant) compileTimeConstant).getUnknownIntegerType().getConstructor();
                IntegerLiteralTypeConstructor integerLiteralTypeConstructor = constructor instanceof IntegerLiteralTypeConstructor ? (IntegerLiteralTypeConstructor) constructor : null;
                if (integerLiteralTypeConstructor == null) {
                    return;
                } else {
                    approximatedType = integerLiteralTypeConstructor.getApproximatedType();
                }
            } else {
                if (!(compileTimeConstant instanceof TypedCompileTimeConstant)) {
                    return;
                }
                KotlinType returnType = resolvedCall.getResultingDescriptor().getReturnType();
                SimpleType simpleTypeLowerIfFlexible = returnType != null ? FlexibleTypesKt.lowerIfFlexible(returnType) : null;
                if (simpleTypeLowerIfFlexible != null) {
                    approximatedType = simpleTypeLowerIfFlexible;
                } else {
                    ConstantValue constantValue = ((TypedCompileTimeConstant) compileTimeConstant).getConstantValue();
                    if (constantValue instanceof ErrorValue) {
                        return;
                    }
                    Object value = constantValue.getValue();
                    Long l = value instanceof Long ? (Long) value : null;
                    if (l == null) {
                        return;
                    } else {
                        approximatedType = new IntegerLiteralTypeConstructor(l.longValue(), moduleDescriptor, compileTimeConstant.getParameters()).getApproximatedType();
                    }
                }
            }
            if (Intrinsics.areEqual(approximatedType.getConstructor(), expectedType.getConstructor())) {
                return;
            }
            if (TypeUtilsKt.isInt(approximatedType) && TypeUtilsKt.isLong(TypeUtilsKt.makeNotNullable(expectedType))) {
                return;
            }
            trace.report(Errors.INTEGER_OPERATOR_RESOLVE_WILL_CHANGE.on(argumentExpression, approximatedType));
        }
    }

    private final boolean isIntOperator(ResolvedCall<?> resolvedCall) {
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        SimpleFunctionDescriptor simpleFunctionDescriptor = resultingDescriptor instanceof SimpleFunctionDescriptor ? (SimpleFunctionDescriptor) resultingDescriptor : null;
        if (simpleFunctionDescriptor == null) {
            return false;
        }
        return literalOperatorsFqNames.contains(DescriptorUtilsKt.getFqNameSafe(simpleFunctionDescriptor));
    }

    static {
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"plus", "minus", "times", "div", "rem", "plus", "minus", "times", "div", "rem", "shl", "shr", "ushr", "and", "or", "xor", "unaryPlus", "unaryMinus", "inv"});
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = listListOf.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(FqName.fromSegments(CollectionsKt.listOf((Object[]) new String[]{IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "Int", (String) it.next()})));
        }
        literalOperatorsFqNames = linkedHashSet;
    }
}
