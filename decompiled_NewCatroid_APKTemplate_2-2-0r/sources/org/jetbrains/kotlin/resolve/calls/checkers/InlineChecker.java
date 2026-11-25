package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.contracts.parsing.PsiContractsUtilsKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryForDeprecation1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtOperationExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: InlineChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0017\u001a\u00020\u00182\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J,\u0010\u001f\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010%H\u0002J(\u0010&\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010'\u001a\u00020!2\u0006\u0010(\u001a\u00020!2\u0006\u0010)\u001a\u00020%H\u0002J \u0010*\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020!2\u0006\u0010,\u001a\u00020%H\u0002J\u0018\u0010-\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020%H\u0002J*\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u0002002\u0006\u0010$\u001a\u00020\u00072\b\u00101\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J*\u00102\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020!2\u0006\u0010$\u001a\u00020\u00072\b\u00101\u001a\u0004\u0018\u00010\u0007H\u0002J(\u00103\u001a\u00020\u00182\u0006\u00104\u001a\u00020!2\u0006\u00105\u001a\u0002062\u0006\u0010$\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J(\u00107\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020!2\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\tH\u0002J2\u0010;\u001a\u00020\u00182\u0006\u0010<\u001a\u00020!2\u0006\u0010$\u001a\u00020\u00072\b\u00101\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u00105\u001a\u000206H\u0002J\"\u0010=\u001a\u0004\u0018\u00010!2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020%2\u0006\u0010>\u001a\u00020\u000eH\u0002J\u0010\u0010?\u001a\u00020\u000e2\u0006\u0010@\u001a\u00020AH\u0002J\u0010\u0010B\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020%H\u0002J\u0010\u0010C\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020!H\u0002J\u001e\u0010D\u001a\u00020\u0018*\u00020\u001e2\u0006\u0010$\u001a\u00020\u00072\b\u00101\u001a\u0004\u0018\u00010\u0007H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R+\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006E"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/InlineChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "inlinableKtParameters", "", "Lorg/jetbrains/kotlin/psi/KtElement;", "inlinableParameters", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "inlineFunEffectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "isEffectivelyPrivateApiFunction", "", "<set-?>", "supportDefaultValueInline", "getSupportDefaultValueInline", "()Z", "setSupportDefaultValueInline", "(Z)V", "supportDefaultValueInline$delegate", "Lkotlin/properties/ReadWriteProperty;", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "checkCallWithReceiver", "targetDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "checkLambdaInvokeOrExtensionCall", "lambdaDescriptor", "callDescriptor", "receiverExpression", "checkNonLocalReturn", "inlinableParameterDescriptor", "parameterUsage", "checkNotInDefaultParameter", "checkPrivateClassMemberAccess", "declarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "replacementForReport", "checkRecursion", "checkSuperCalls", "callableDescriptor", "call", "Lorg/jetbrains/kotlin/psi/Call;", "checkValueParameter", "targetArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "targetParameterDescriptor", "checkVisibilityAndAccess", "calledDescriptor", "getCalleeDescriptor", "unwrapVariableAsFunction", "isDefinedInInlineFunction", "startDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "isInsideCall", "isInvokeOrInlineExtension", "reportDeprecationOnReplacement", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineChecker implements CallChecker {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(InlineChecker.class), "supportDefaultValueInline", "getSupportDefaultValueInline()Z"))};
    private final FunctionDescriptor descriptor;
    private final List<KtElement> inlinableKtParameters;
    private final List<ValueParameterDescriptor> inlinableParameters;
    private final EffectiveVisibility inlineFunEffectiveVisibility;
    private final boolean isEffectivelyPrivateApiFunction;

    /* JADX INFO: renamed from: supportDefaultValueInline$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty supportDefaultValueInline;

    public InlineChecker(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.descriptor = descriptor;
        InlineUtil.isInline(descriptor);
        DescriptorVisibility visibility = descriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        this.inlineFunEffectiveVisibility = EffectiveVisibilityUtilsKt.effectiveVisibility((DeclarationDescriptorWithVisibility) descriptor, visibility, true);
        this.isEffectivelyPrivateApiFunction = DescriptorUtilsKt.isEffectivelyPrivateApi(descriptor);
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : valueParameters) {
            if (InlineUtil.isInlineParameter((ValueParameterDescriptor) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        this.inlinableParameters = arrayList2;
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            SourceElement source = ((ValueParameterDescriptor) it.next()).getSource();
            KotlinSourceElement kotlinSourceElement = source instanceof KotlinSourceElement ? (KotlinSourceElement) source : null;
            KtElement psi = kotlinSourceElement != null ? kotlinSourceElement.getPsi() : null;
            if (psi != null) {
                arrayList3.add(psi);
            }
        }
        this.inlinableKtParameters = arrayList3;
        this.supportDefaultValueInline = Delegates.INSTANCE.notNull();
    }

    private final boolean getSupportDefaultValueInline() {
        return ((Boolean) this.supportDefaultValueInline.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    private final void setSupportDefaultValueInline(boolean z) {
        this.supportDefaultValueInline.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        KtExpression calleeExpression = call.getCalleeExpression();
        if (calleeExpression == null) {
            return;
        }
        setSupportDefaultValueInline(context.getLanguageVersionSettings().supportsFeature(LanguageFeature.InlineDefaultFunctionalParameters));
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Intrinsics.checkNotNull(resultingDescriptor);
        if (PsiContractsUtilsKt.isFromContractDsl(resultingDescriptor)) {
            return;
        }
        checkCallWithReceiver(context, resultingDescriptor, resolvedCall.getDispatchReceiver(), calleeExpression);
        checkCallWithReceiver(context, resultingDescriptor, resolvedCall.getExtensionReceiver(), calleeExpression);
        if (CollectionsKt.contains(this.inlinableParameters, resultingDescriptor) && checkNotInDefaultParameter(context, calleeExpression) && !isInsideCall(calleeExpression)) {
            context.getTrace().report(Errors.USAGE_IS_NOT_INLINABLE.on(calleeExpression, calleeExpression, this.descriptor));
        }
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            ResolvedValueArgument value = entry.getValue();
            if (!(value instanceof DefaultValueArgument)) {
                for (ValueArgument valueArgument : value.getArguments()) {
                    Intrinsics.checkNotNull(valueArgument);
                    Intrinsics.checkNotNull(key);
                    checkValueParameter(context, resultingDescriptor, valueArgument, key);
                }
            }
        }
        ReceiverValue dispatchReceiver = call.getDispatchReceiver();
        ExpressionReceiver expressionReceiver = dispatchReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) dispatchReceiver : null;
        KtExpression ktExpression = calleeExpression;
        KtExpression expression = expressionReceiver != null ? expressionReceiver.getExpression() : null;
        checkVisibilityAndAccess(resultingDescriptor, ktExpression, expression, context, call);
        checkRecursion(context, resultingDescriptor, ktExpression, expression);
    }

    private final boolean checkNotInDefaultParameter(CallCheckerContext context, KtExpression expression) {
        boolean z;
        if (!getSupportDefaultValueInline()) {
            return true;
        }
        KtExpression ktExpression = expression;
        KtParameter ktParameter = (KtParameter) PsiTreeUtil.getParentOfType(ktExpression, KtParameter.class, true);
        if (ktParameter != null) {
            boolean zContains = this.inlinableKtParameters.contains(ktParameter);
            z = !zContains;
            if (zContains) {
                context.getTrace().report(Errors.NOT_SUPPORTED_INLINE_PARAMETER_IN_INLINE_PARAMETER_DEFAULT_VALUE.on(ktExpression, expression, this.descriptor));
            }
        } else {
            z = true;
        }
        return z;
    }

    private final boolean isInsideCall(KtExpression expression) {
        KtToken operationToken;
        KtExpression parentCallIfPresent = KtPsiUtil.getParentCallIfPresent(expression);
        if ((parentCallIfPresent instanceof KtBinaryExpression) && ((operationToken = KtPsiUtil.getOperationToken((KtOperationExpression) parentCallIfPresent)) == KtTokens.EQ || operationToken == KtTokens.ANDAND || operationToken == KtTokens.OROR)) {
            return false;
        }
        if (parentCallIfPresent != null) {
            KtExpression lightParent = expression;
            while (lightParent != parentCallIfPresent) {
                if (lightParent instanceof KtBinaryExpressionWithTypeRHS) {
                    return false;
                }
                lightParent = lightParent.getLightParent();
                Intrinsics.checkNotNullExpressionValue(lightParent, "getParent(...)");
            }
        }
        return parentCallIfPresent != null;
    }

    private final void checkValueParameter(CallCheckerContext context, CallableDescriptor targetDescriptor, ValueArgument targetArgument, ValueParameterDescriptor targetParameterDescriptor) {
        CallableDescriptor calleeDescriptor;
        KtExpression argumentExpression = targetArgument.getArgumentExpression();
        if (argumentExpression != null && (calleeDescriptor = getCalleeDescriptor(context, argumentExpression, false)) != null && CollectionsKt.contains(this.inlinableParameters, calleeDescriptor) && checkNotInDefaultParameter(context, argumentExpression)) {
            if (InlineUtil.isInline(targetDescriptor) && InlineUtil.isInlineParameter(targetParameterDescriptor)) {
                if (InlineUtil.allowsNonLocalReturns(calleeDescriptor) && !InlineUtil.allowsNonLocalReturns(targetParameterDescriptor)) {
                    context.getTrace().report(Errors.NON_LOCAL_RETURN_NOT_ALLOWED.on(argumentExpression, argumentExpression));
                    return;
                } else {
                    checkNonLocalReturn(context, calleeDescriptor, argumentExpression);
                    return;
                }
            }
            context.getTrace().report(Errors.USAGE_IS_NOT_INLINABLE.on(argumentExpression, argumentExpression, this.descriptor));
        }
    }

    private final void checkCallWithReceiver(CallCheckerContext context, CallableDescriptor targetDescriptor, ReceiverValue receiver, KtExpression expression) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        if (receiver == null) {
            return;
        }
        if (receiver instanceof ExpressionReceiver) {
            expression = ((ExpressionReceiver) receiver).getExpression();
            extensionReceiverParameter = getCalleeDescriptor(context, expression, true);
        } else if (receiver instanceof ExtensionReceiver) {
            CallableDescriptor declarationDescriptor = ((ExtensionReceiver) receiver).getDeclarationDescriptor();
            Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "<get-declarationDescriptor>(...)");
            extensionReceiverParameter = declarationDescriptor.getExtensionReceiverParameter();
        } else {
            expression = null;
            extensionReceiverParameter = null;
        }
        if (CollectionsKt.contains(this.inlinableParameters, extensionReceiverParameter)) {
            Intrinsics.checkNotNull(extensionReceiverParameter);
            Intrinsics.checkNotNull(expression);
            checkLambdaInvokeOrExtensionCall(context, extensionReceiverParameter, targetDescriptor, expression);
        }
    }

    private final CallableDescriptor getCalleeDescriptor(CallCheckerContext context, KtExpression expression, boolean unwrapVariableAsFunction) {
        if (!(expression instanceof KtSimpleNameExpression) && !(expression instanceof KtThisExpression)) {
            return null;
        }
        BindingContext context2 = context.getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(expression, context2);
        if (unwrapVariableAsFunction && (resolvedCall instanceof VariableAsFunctionResolvedCall)) {
            return ((VariableAsFunctionResolvedCall) resolvedCall).getVariableCall().getResultingDescriptor();
        }
        if (resolvedCall != null) {
            return resolvedCall.getResultingDescriptor();
        }
        return null;
    }

    private final void checkLambdaInvokeOrExtensionCall(CallCheckerContext context, CallableDescriptor lambdaDescriptor, CallableDescriptor callDescriptor, KtExpression receiverExpression) {
        if (!isInvokeOrInlineExtension(callDescriptor)) {
            if (InlineUtil.isInline(callDescriptor) && !context.getLanguageVersionSettings().supportsFeature(LanguageFeature.ForbidExtensionCallsOnInlineFunctionalParameters)) {
                context.getTrace().report(Errors.USAGE_IS_NOT_INLINABLE_WARNING.on(receiverExpression, receiverExpression, this.descriptor));
                return;
            } else {
                context.getTrace().report(Errors.USAGE_IS_NOT_INLINABLE.on(receiverExpression, receiverExpression, this.descriptor));
                return;
            }
        }
        checkNonLocalReturn(context, lambdaDescriptor, receiverExpression);
    }

    private final void checkRecursion(CallCheckerContext context, CallableDescriptor targetDescriptor, KtElement expression, KtElement replacementForReport) {
        if (targetDescriptor.getOriginal() == this.descriptor) {
            context.getTrace().report(Errors.RECURSION_IN_INLINE.on(expression, expression, this.descriptor));
            reportDeprecationOnReplacement(context, expression, replacementForReport);
        }
    }

    private final boolean isInvokeOrInlineExtension(CallableDescriptor descriptor) {
        if (!(descriptor instanceof SimpleFunctionDescriptor)) {
            return false;
        }
        DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (!Intrinsics.areEqual(descriptor.getShortName(), OperatorNameConventions.INVOKE) || !(containingDeclaration instanceof ClassDescriptor)) {
            return false;
        }
        SimpleType defaultType = ((ClassDescriptor) containingDeclaration).getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        return FunctionTypesKt.isBuiltinFunctionalType(defaultType);
    }

    private final void checkVisibilityAndAccess(CallableDescriptor calledDescriptor, KtElement expression, KtElement replacementForReport, CallCheckerContext context, Call call) {
        EffectiveVisibility.Public publicEffectiveVisibility;
        CallableDescriptor callableDescriptor = calledDescriptor;
        if (isDefinedInInlineFunction(callableDescriptor)) {
            publicEffectiveVisibility = EffectiveVisibility.Public.INSTANCE;
        } else {
            DescriptorVisibility visibility = calledDescriptor.getVisibility();
            Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
            publicEffectiveVisibility = EffectiveVisibilityUtilsKt.effectiveVisibility((DeclarationDescriptorWithVisibility) callableDescriptor, visibility, true);
        }
        boolean publicApi = publicEffectiveVisibility.getPublicApi();
        boolean publicApi2 = this.inlineFunEffectiveVisibility.getPublicApi();
        if (publicApi2 && !publicApi && calledDescriptor.getVisibility() != DescriptorVisibilities.LOCAL) {
            context.getTrace().report(Errors.NON_PUBLIC_CALL_FROM_PUBLIC_INLINE.on(expression, calledDescriptor, this.descriptor));
            reportDeprecationOnReplacement(context, expression, replacementForReport);
        } else {
            checkPrivateClassMemberAccess(calledDescriptor, expression, replacementForReport, context);
            if (publicApi2) {
                checkSuperCalls(calledDescriptor, call, expression, context);
            }
        }
        boolean z = calledDescriptor instanceof ConstructorDescriptor;
        if (!(z && (expression instanceof KtConstructorCalleeExpression)) && publicApi2 && this.inlineFunEffectiveVisibility.toVisibility() != Visibilities.Protected.INSTANCE && publicEffectiveVisibility.toVisibility() == Visibilities.Protected.INSTANCE) {
            if (z) {
                context.getTrace().report(Errors.PROTECTED_CONSTRUCTOR_CALL_FROM_PUBLIC_INLINE.on(context.getLanguageVersionSettings(), expression, calledDescriptor));
            } else {
                context.getTrace().report(Errors.PROTECTED_CALL_FROM_PUBLIC_INLINE.on(context.getLanguageVersionSettings(), expression, calledDescriptor));
            }
            reportDeprecationOnReplacement(context, expression, replacementForReport);
        }
    }

    private final void checkPrivateClassMemberAccess(DeclarationDescriptor declarationDescriptor, KtElement expression, KtElement replacementForReport, CallCheckerContext context) {
        if (this.isEffectivelyPrivateApiFunction) {
            return;
        }
        if (DescriptorUtilsKt.isInsidePrivateClass(declarationDescriptor)) {
            context.getTrace().report(Errors.PRIVATE_CLASS_MEMBER_FROM_INLINE.on(expression, declarationDescriptor, this.descriptor));
            reportDeprecationOnReplacement(context, expression, replacementForReport);
        } else if (DescriptorUtilsKt.isMemberOfCompanionOfPrivateClass(declarationDescriptor)) {
            context.getTrace().report(Errors.PRIVATE_CLASS_MEMBER_FROM_INLINE_WARNING.on(expression, declarationDescriptor, this.descriptor));
            reportDeprecationOnReplacement(context, expression, replacementForReport);
        }
    }

    private final void checkSuperCalls(CallableDescriptor callableDescriptor, Call call, KtElement expression, CallCheckerContext context) {
        KotlinType kotlinType;
        KtSuperExpression superCallExpression = CallResolverUtilKt.getSuperCallExpression(call);
        if (superCallExpression == null || (kotlinType = (KotlinType) context.getTrace().get(BindingContext.THIS_TYPE_FOR_SUPER_EXPRESSION, superCallExpression)) == null) {
            return;
        }
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility = this$0 instanceof DeclarationDescriptorWithVisibility ? (DeclarationDescriptorWithVisibility) this$0 : null;
        if (declarationDescriptorWithVisibility == null || isDefinedInInlineFunction(declarationDescriptorWithVisibility)) {
            return;
        }
        BindingTrace trace = context.getTrace();
        DiagnosticFactoryForDeprecation1<PsiElement, CallableDescriptor> diagnosticFactoryForDeprecation1 = Errors.SUPER_CALL_FROM_PUBLIC_INLINE;
        LanguageVersionSettings languageVersionSettings = context.getLanguageVersionSettings();
        KtSuperExpression lightParent = expression.getLightParent().getLightParent();
        if (lightParent == null) {
            lightParent = superCallExpression;
        }
        trace.report(diagnosticFactoryForDeprecation1.on(languageVersionSettings, lightParent, callableDescriptor));
    }

    private final boolean isDefinedInInlineFunction(DeclarationDescriptorWithVisibility startDescriptor) {
        while (startDescriptor != null) {
            if (startDescriptor.getContainingDeclaration() == this.descriptor) {
                return true;
            }
            startDescriptor = (DeclarationDescriptorWithVisibility) DescriptorUtils.getParentOfType(startDescriptor, DeclarationDescriptorWithVisibility.class);
        }
        return false;
    }

    private final void checkNonLocalReturn(CallCheckerContext context, CallableDescriptor inlinableParameterDescriptor, KtExpression parameterUsage) {
        if (InlineUtil.allowsNonLocalReturns(inlinableParameterDescriptor) && !InlineUtil.checkNonLocalReturnUsage(this.descriptor, parameterUsage, context.getResolutionContext())) {
            context.getTrace().report(Errors.NON_LOCAL_RETURN_NOT_ALLOWED.on(parameterUsage, parameterUsage));
        }
    }

    private final void reportDeprecationOnReplacement(CallCheckerContext callCheckerContext, KtElement ktElement, KtElement ktElement2) {
        if (ktElement.getIsPhysical() || ktElement2 == null) {
            return;
        }
        callCheckerContext.getTrace().report(Errors.DEPRECATED_IMPLICIT_NON_PUBLIC_API_ACCESS.on(ktElement2));
    }
}
