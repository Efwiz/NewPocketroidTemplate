package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.contracts.description.AbstractContractProvider;
import org.jetbrains.kotlin.contracts.description.CallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.ContractDescription;
import org.jetbrains.kotlin.contracts.description.ContractProviderKey;
import org.jetbrains.kotlin.contracts.description.EffectDeclaration;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCatchClause;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMapping;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatch;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;
import org.jetbrains.kotlin.types.expressions.CaptureKind;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: CapturingInClosureChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J(\u0010\u0012\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\bH\u0002J&\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u00172\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J(\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\t\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010 \u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010!\u001a\u00020\"2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u0014H\u0002J\u0018\u0010#\u001a\u00020\"2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010$\u001a\u00020\"2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010%\u001a\u00020\"2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0018\u0010&\u001a\u00020\"2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0018\u0010'\u001a\u00020\"2\u0006\u0010(\u001a\u00020\u00142\u0006\u0010)\u001a\u00020\rH\u0002J\u0010\u0010*\u001a\u00020\"2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010+\u001a\u00020\"2\u0006\u0010\u0015\u001a\u00020,H\u0002J\u0010\u0010-\u001a\u00020\"2\u0006\u0010\f\u001a\u00020\rH\u0002¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/CapturingInClosureChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "checkCapturingInClosure", "variable", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "checkFieldInExactlyOnceLambdaInitialization", "scopeContainer", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "nameElement", "getCalleeDescriptorAndParameter", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "argument", "Lorg/jetbrains/kotlin/psi/KtFunction;", "getCaptureKind", "Lorg/jetbrains/kotlin/types/expressions/CaptureKind;", "variableParent", "isArgument", "", "isCapturedVariable", "isCatchBlockParameter", "isCrossinlineParameter", "isExactlyOnceContract", "isExactlyOnceParameter", "function", "parameter", "isForLoopParameter", "isLhsOfAssignment", "Lorg/jetbrains/kotlin/psi/KtExpression;", "isValInWhen", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CapturingInClosureChecker implements CallChecker {
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        ResolvedCall<VariableDescriptor> variableCall;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        VariableAsFunctionResolvedCall variableAsFunctionResolvedCall = resolvedCall instanceof VariableAsFunctionResolvedCall ? (VariableAsFunctionResolvedCall) resolvedCall : null;
        if (variableAsFunctionResolvedCall != null && (variableCall = variableAsFunctionResolvedCall.getVariableCall()) != null) {
            resolvedCall = variableCall;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        VariableDescriptor variableDescriptor = resultingDescriptor instanceof VariableDescriptor ? (VariableDescriptor) resultingDescriptor : null;
        if (variableDescriptor != null) {
            checkCapturingInClosure(variableDescriptor, context.getTrace(), context.getScope());
            checkFieldInExactlyOnceLambdaInitialization(variableDescriptor, context.getTrace(), context.getScope().getOwnerDescriptor(), reportOn);
        }
    }

    private final void checkCapturingInClosure(VariableDescriptor variable, BindingTrace trace, LexicalScope scope) {
        DeclarationDescriptor containingDeclaration = variable.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        DeclarationDescriptor ownerDescriptor = scope.getOwnerDescriptor();
        if (!isCapturedVariable(containingDeclaration, ownerDescriptor) || trace.get(BindingContext.CAPTURED_IN_CLOSURE, variable) == CaptureKind.NOT_INLINE) {
            return;
        }
        WritableSlice<VariableDescriptor, CaptureKind> writableSlice = BindingContext.CAPTURED_IN_CLOSURE;
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        trace.record(writableSlice, variable, getCaptureKind(context, ownerDescriptor, containingDeclaration, variable));
    }

    private final void checkFieldInExactlyOnceLambdaInitialization(VariableDescriptor variable, BindingTrace trace, DeclarationDescriptor scopeContainer, PsiElement nameElement) {
        if ((variable instanceof PropertyDescriptor) && (scopeContainer instanceof AnonymousFunctionDescriptor) && !((PropertyDescriptor) variable).isVar()) {
            Intrinsics.checkNotNull(nameElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtExpression");
            if (isLhsOfAssignment((KtExpression) nameElement)) {
                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(scopeContainer);
                KtFunction ktFunction = psiElementDescriptorToDeclaration instanceof KtFunction ? (KtFunction) psiElementDescriptorToDeclaration : null;
                if (ktFunction == null) {
                    return;
                }
                AnonymousFunctionDescriptor anonymousFunctionDescriptor = (AnonymousFunctionDescriptor) scopeContainer;
                if ((anonymousFunctionDescriptor.getContainingModule() instanceof ConstructorDescriptor) || (anonymousFunctionDescriptor.getContainingModule() instanceof PropertyDescriptor)) {
                    BindingContext context = trace.getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                    if (isExactlyOnceContract(context, ktFunction) && trace.getContext().get(BindingContext.CAPTURED_IN_CLOSURE, variable) != CaptureKind.NOT_INLINE) {
                        BindingContext context2 = trace.getContext();
                        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                        Pair<CallableDescriptor, ValueParameterDescriptor> calleeDescriptorAndParameter = getCalleeDescriptorAndParameter(context2, ktFunction);
                        if (calleeDescriptorAndParameter == null) {
                            return;
                        }
                        CallableDescriptor callableDescriptorComponent1 = calleeDescriptorAndParameter.component1();
                        ValueParameterDescriptor valueParameterDescriptorComponent2 = calleeDescriptorAndParameter.component2();
                        if (callableDescriptorComponent1 instanceof FunctionDescriptor) {
                            if (((FunctionDescriptor) callableDescriptorComponent1).isInline() && !valueParameterDescriptorComponent2.getIsCrossinline() && InlineUtil.isInlineParameter(valueParameterDescriptorComponent2)) {
                                return;
                            }
                            trace.report(Errors.CAPTURED_VAL_INITIALIZATION.on(nameElement, variable));
                        }
                    }
                }
            }
        }
    }

    private final boolean isLhsOfAssignment(KtExpression nameElement) {
        PsiElement parent = nameElement.getLightParent();
        KtBinaryExpression ktBinaryExpression = parent instanceof KtBinaryExpression ? (KtBinaryExpression) parent : null;
        return ktBinaryExpression != null && Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.EQ) && Intrinsics.areEqual(ktBinaryExpression.getLeft(), nameElement);
    }

    private final boolean isCapturedVariable(DeclarationDescriptor variableParent, DeclarationDescriptor scopeContainer) {
        if (!(variableParent instanceof FunctionDescriptor) || Intrinsics.areEqual(scopeContainer, variableParent)) {
            return false;
        }
        if (!(variableParent instanceof ConstructorDescriptor)) {
            return true;
        }
        ClassifierDescriptorWithTypeParameters declarationDescriptor = ((ConstructorDescriptor) variableParent).getContainingModule();
        Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "getContainingDeclaration(...)");
        if (Intrinsics.areEqual(scopeContainer, declarationDescriptor)) {
            return false;
        }
        return ((scopeContainer instanceof PropertyDescriptor) && Intrinsics.areEqual(((PropertyDescriptor) scopeContainer).getContainingModule(), declarationDescriptor)) ? false : true;
    }

    private final CaptureKind getCaptureKind(BindingContext context, DeclarationDescriptor scopeContainer, DeclarationDescriptor variableParent, VariableDescriptor variable) {
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(scopeContainer);
        if (!InlineUtil.canBeInlineArgument(psiElementDescriptorToDeclaration)) {
            return CaptureKind.NOT_INLINE;
        }
        Intrinsics.checkNotNull(psiElementDescriptorToDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFunction");
        KtFunction ktFunction = (KtFunction) psiElementDescriptorToDeclaration;
        if (InlineUtil.isInlinedArgument(ktFunction, context, false) && !isCrossinlineParameter(context, ktFunction)) {
            DeclarationDescriptor declarationDescriptor = scopeContainer.getContainingModule();
            if (declarationDescriptor != null) {
                return (!isCapturedVariable(variableParent, declarationDescriptor) || getCaptureKind(context, declarationDescriptor, variableParent, variable) == CaptureKind.INLINE_ONLY) ? CaptureKind.INLINE_ONLY : CaptureKind.NOT_INLINE;
            }
            throw new IllegalStateException(("parent is null for " + scopeContainer).toString());
        }
        if (!isExactlyOnceContract(context, ktFunction)) {
            return CaptureKind.NOT_INLINE;
        }
        if (isArgument(variable, variableParent) || CapturingInClosureCheckerKt.findDestructuredVariable(variable, variableParent) != null || isForLoopParameter(variable) || isCatchBlockParameter(variable) || isValInWhen(variable)) {
            return CaptureKind.NOT_INLINE;
        }
        return CaptureKind.EXACTLY_ONCE_EFFECT;
    }

    private final boolean isArgument(VariableDescriptor variable, DeclarationDescriptor variableParent) {
        return (variable instanceof ValueParameterDescriptor) && (variableParent instanceof CallableDescriptor) && ((CallableDescriptor) variableParent).getValueParameters().contains(variable);
    }

    private final boolean isValInWhen(VariableDescriptor variable) {
        KtElement psi;
        LocalVariableDescriptor localVariableDescriptor = variable instanceof LocalVariableDescriptor ? (LocalVariableDescriptor) variable : null;
        SourceElement source = localVariableDescriptor != null ? localVariableDescriptor.getSourceElement() : null;
        KotlinSourceElement kotlinSourceElement = source instanceof KotlinSourceElement ? (KotlinSourceElement) source : null;
        if (kotlinSourceElement == null || (psi = kotlinSourceElement.getPsi()) == null) {
            return false;
        }
        PsiElement parent = psi.getLightParent();
        KtWhenExpression ktWhenExpression = parent instanceof KtWhenExpression ? (KtWhenExpression) parent : null;
        return ktWhenExpression != null && Intrinsics.areEqual(ktWhenExpression.getSubjectVariable(), psi);
    }

    private final boolean isCatchBlockParameter(VariableDescriptor variable) {
        KtElement psi;
        KtParameterList parameterList;
        List<KtParameter> parameters;
        LocalVariableDescriptor localVariableDescriptor = variable instanceof LocalVariableDescriptor ? (LocalVariableDescriptor) variable : null;
        SourceElement source = localVariableDescriptor != null ? localVariableDescriptor.getSourceElement() : null;
        KotlinSourceElement kotlinSourceElement = source instanceof KotlinSourceElement ? (KotlinSourceElement) source : null;
        if (kotlinSourceElement == null || (psi = kotlinSourceElement.getPsi()) == null) {
            return false;
        }
        PsiElement myParent = psi.getLightParent().getLightParent();
        if (!(myParent instanceof KtCatchClause) || (parameterList = ((KtCatchClause) myParent).getParameterList()) == null || (parameters = parameterList.getParameters()) == null) {
            return false;
        }
        Intrinsics.checkNotNull(parameters);
        return CollectionsKt.contains(parameters, psi);
    }

    private final boolean isForLoopParameter(VariableDescriptor variable) {
        KtElement psi;
        LocalVariableDescriptor localVariableDescriptor = variable instanceof LocalVariableDescriptor ? (LocalVariableDescriptor) variable : null;
        SourceElement source = localVariableDescriptor != null ? localVariableDescriptor.getSourceElement() : null;
        KotlinSourceElement kotlinSourceElement = source instanceof KotlinSourceElement ? (KotlinSourceElement) source : null;
        if (kotlinSourceElement != null && (psi = kotlinSourceElement.getPsi()) != null) {
            if (psi.getLightParent() instanceof KtForExpression) {
                PsiElement parent = psi.getLightParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtForExpression");
                return Intrinsics.areEqual(((KtForExpression) parent).getLoopParameter(), psi);
            }
            if (psi.getLightParent() instanceof KtDestructuringDeclaration) {
                PsiElement myParent = psi.getLightParent().getLightParent();
                KtParameter ktParameter = myParent instanceof KtParameter ? (KtParameter) myParent : null;
                if (ktParameter == null) {
                    return false;
                }
                PsiElement parent2 = ktParameter.getLightParent();
                KtForExpression ktForExpression = parent2 instanceof KtForExpression ? (KtForExpression) parent2 : null;
                if (ktForExpression == null) {
                    return false;
                }
                return Intrinsics.areEqual(ktForExpression.getLoopParameter(), ktParameter);
            }
        }
        return false;
    }

    private final boolean isExactlyOnceParameter(DeclarationDescriptor function, VariableDescriptor parameter) {
        AbstractContractProvider abstractContractProvider;
        ContractDescription contractDescription;
        Object next;
        if (!(function instanceof CallableDescriptor) || !(parameter instanceof ValueParameterDescriptor) || (abstractContractProvider = (AbstractContractProvider) ((CallableDescriptor) function).getUserData(ContractProviderKey.INSTANCE)) == null || (contractDescription = abstractContractProvider.getContractDescription()) == null) {
            return false;
        }
        List<EffectDeclaration> effects = contractDescription.getEffects();
        ArrayList arrayList = new ArrayList();
        for (Object obj : effects) {
            if (obj instanceof CallsEffectDeclaration) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((CallsEffectDeclaration) next).getVariableReference().getDescriptor(), ((ValueParameterDescriptor) parameter).getOriginal()));
        CallsEffectDeclaration callsEffectDeclaration = (CallsEffectDeclaration) next;
        return callsEffectDeclaration != null && callsEffectDeclaration.getKind() == EventOccurrencesRange.EXACTLY_ONCE;
    }

    private final boolean isExactlyOnceContract(BindingContext bindingContext, KtFunction argument) {
        Pair<CallableDescriptor, ValueParameterDescriptor> calleeDescriptorAndParameter = getCalleeDescriptorAndParameter(bindingContext, argument);
        if (calleeDescriptorAndParameter == null) {
            return false;
        }
        return isExactlyOnceParameter(calleeDescriptorAndParameter.component1(), calleeDescriptorAndParameter.component2());
    }

    private final Pair<CallableDescriptor, ValueParameterDescriptor> getCalleeDescriptorAndParameter(BindingContext bindingContext, KtFunction argument) {
        ResolvedCall<? extends CallableDescriptor> resolvedCall;
        KtFunction ktFunction = argument;
        KtExpression parentCallIfPresent = KtPsiUtil.getParentCallIfPresent(ktFunction);
        if (parentCallIfPresent == null || (resolvedCall = CallUtilKt.getResolvedCall(parentCallIfPresent, bindingContext)) == null) {
            return null;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        ValueArgument valueArgumentForExpression = CallUtilKt.getValueArgumentForExpression(call, ktFunction);
        if (valueArgumentForExpression == null) {
            return null;
        }
        ArgumentMapping argumentMapping = resolvedCall.getArgumentMapping(valueArgumentForExpression);
        ArgumentMatch argumentMatch = argumentMapping instanceof ArgumentMatch ? (ArgumentMatch) argumentMapping : null;
        if (argumentMatch == null) {
            return null;
        }
        return TuplesKt.to(resultingDescriptor, argumentMatch.getValueParameter());
    }

    private final boolean isCrossinlineParameter(BindingContext bindingContext, KtFunction argument) {
        ValueParameterDescriptor second;
        Pair<CallableDescriptor, ValueParameterDescriptor> calleeDescriptorAndParameter = getCalleeDescriptorAndParameter(bindingContext, argument);
        return (calleeDescriptorAndParameter == null || (second = calleeDescriptorAndParameter.getSecond()) == null || !second.getIsCrossinline()) ? false : true;
    }
}
