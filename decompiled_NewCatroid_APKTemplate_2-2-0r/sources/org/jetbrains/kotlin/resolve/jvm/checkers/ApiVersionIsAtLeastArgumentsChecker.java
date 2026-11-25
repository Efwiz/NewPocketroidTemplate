package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.jvm.JvmConstantsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ApiVersionIsAtLeastArgumentsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/ApiVersionIsAtLeastArgumentsChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "isApiVersionIsAtLeast", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ApiVersionIsAtLeastArgumentsChecker implements CallChecker {
    public static final ApiVersionIsAtLeastArgumentsChecker INSTANCE = new ApiVersionIsAtLeastArgumentsChecker();

    private ApiVersionIsAtLeastArgumentsChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        KtExpression ktExpressionDeparenthesize;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
        if (isApiVersionIsAtLeast(resultingDescriptor)) {
            BindingContext context2 = context.getTrace().getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
            boolean zSupportsFeature = context.getLanguageVersionSettings().supportsFeature(LanguageFeature.InlineConstVals);
            Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
            Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
            Iterator<Map.Entry<ValueParameterDescriptor, ResolvedValueArgument>> it = valueArguments.entrySet().iterator();
            while (it.hasNext()) {
                Iterator<ValueArgument> it2 = it.next().getValue().getArguments().iterator();
                while (it2.hasNext()) {
                    KtExpression argumentExpression = it2.next().getArgumentExpression();
                    if (argumentExpression != null && (ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(argumentExpression)) != null && JvmConstantsKt.getCompileTimeConstant(ktExpressionDeparenthesize, context2, false, zSupportsFeature) == null) {
                        context.getTrace().report(ErrorsJvm.API_VERSION_IS_AT_LEAST_ARGUMENT_SHOULD_BE_CONSTANT.on(ktExpressionDeparenthesize));
                    }
                }
            }
        }
    }

    private final boolean isApiVersionIsAtLeast(CallableDescriptor descriptor) {
        KotlinType returnType;
        FunctionDescriptor functionDescriptor = descriptor instanceof FunctionDescriptor ? (FunctionDescriptor) descriptor : null;
        if (functionDescriptor == null || !Intrinsics.areEqual(functionDescriptor.getShortName().asString(), "apiVersionIsAtLeast") || (returnType = functionDescriptor.getReturnType()) == null || !KotlinBuiltIns.isBoolean(returnType)) {
            return false;
        }
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (!KotlinBuiltIns.isInt(((ValueParameterDescriptor) it.next()).getType())) {
                    return false;
                }
            }
        }
        DeclarationDescriptor containingDeclaration = functionDescriptor.getDeclarationDescriptor();
        PackageFragmentDescriptor packageFragmentDescriptor = containingDeclaration instanceof PackageFragmentDescriptor ? (PackageFragmentDescriptor) containingDeclaration : null;
        if (packageFragmentDescriptor == null) {
            return false;
        }
        return Intrinsics.areEqual(packageFragmentDescriptor.getFqName().asString(), "kotlin.internal");
    }
}
