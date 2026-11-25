package org.jetbrains.kotlin.contracts;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.contracts.model.Computation;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESValue;
import org.jetbrains.kotlin.contracts.model.MutableContextInfo;
import org.jetbrains.kotlin.contracts.model.functors.EqualsFunctor;
import org.jetbrains.kotlin.contracts.model.structure.ESCalls;
import org.jetbrains.kotlin.contracts.model.structure.ESConstants;
import org.jetbrains.kotlin.contracts.model.structure.ESReturns;
import org.jetbrains.kotlin.contracts.model.structure.UNKNOWN_COMPUTATION;
import org.jetbrains.kotlin.contracts.model.visitors.InfoCollector;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.ConditionalDataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;

/* JADX INFO: compiled from: EffectSystem.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ(\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J(\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\"\u0010\u001e\u001a\u00020\u00102\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030 2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J*\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u00122\b\u0010$\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\"\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010\u001d\u001a\u00020\u00122\u0006\u0010'\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\"\u0010(\u001a\u00020)2\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030 2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/contracts/EffectSystem;", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;)V", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getDataFlowValueFactory", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "extractDataFlowInfoFromCondition", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "condition", "Lorg/jetbrains/kotlin/psi/KtExpression;", "value", "", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getContextInfoWhen", "Lorg/jetbrains/kotlin/contracts/model/MutableContextInfo;", "observedEffect", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "expression", "getDataFlowInfoForFinishedCall", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "getDataFlowInfoWhenEquals", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ConditionalDataFlowInfo;", "leftExpression", "rightExpression", "getNonTrivialComputation", "Lorg/jetbrains/kotlin/contracts/model/Computation;", "trace", "recordDefiniteInvocations", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EffectSystem {
    private final KotlinBuiltIns builtIns;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final LanguageVersionSettings languageVersionSettings;

    public EffectSystem(LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory, KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.languageVersionSettings = languageVersionSettings;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.builtIns = builtIns;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final DataFlowValueFactory getDataFlowValueFactory() {
        return this.dataFlowValueFactory;
    }

    public final KotlinBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    public final DataFlowInfo getDataFlowInfoForFinishedCall(ResolvedCall<?> resolvedCall, BindingTrace bindingTrace, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        if (!this.languageVersionSettings.supportsFeature(LanguageFeature.UseReturnsEffect)) {
            return DataFlowInfo.INSTANCE.getEMPTY();
        }
        KtElement callElement = resolvedCall.getCall().getCallElement();
        KtCallExpression ktCallExpression = callElement instanceof KtCallExpression ? (KtCallExpression) callElement : null;
        if (ktCallExpression != null && !(ktCallExpression instanceof KtDeclaration)) {
            return ContextInfoToDataFlowInfoKt.toDataFlowInfo(getContextInfoWhen(new ESReturns(ESConstants.INSTANCE.getWildcard()), ktCallExpression, bindingTrace, moduleDescriptor), this.languageVersionSettings, this.builtIns);
        }
        return DataFlowInfo.INSTANCE.getEMPTY();
    }

    public final ConditionalDataFlowInfo getDataFlowInfoWhenEquals(KtExpression leftExpression, KtExpression rightExpression, BindingTrace bindingTrace, ModuleDescriptor moduleDescriptor) {
        Computation nonTrivialComputation;
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        if (!this.languageVersionSettings.supportsFeature(LanguageFeature.UseReturnsEffect)) {
            return ConditionalDataFlowInfo.INSTANCE.getEMPTY();
        }
        if (leftExpression == null || rightExpression == null) {
            return ConditionalDataFlowInfo.INSTANCE.getEMPTY();
        }
        Computation nonTrivialComputation2 = getNonTrivialComputation(leftExpression, bindingTrace, moduleDescriptor);
        if (nonTrivialComputation2 != null && (nonTrivialComputation = getNonTrivialComputation(rightExpression, bindingTrace, moduleDescriptor)) != null) {
            List<ESEffect> listInvokeWithArguments = new EqualsFunctor(false).invokeWithArguments(nonTrivialComputation2, nonTrivialComputation);
            return new ConditionalDataFlowInfo(ContextInfoToDataFlowInfoKt.toDataFlowInfo(new InfoCollector(new ESReturns(ESConstants.INSTANCE.getTrueValue()), this.builtIns).collectFromSchema(listInvokeWithArguments), this.languageVersionSettings, this.builtIns), ContextInfoToDataFlowInfoKt.toDataFlowInfo(new InfoCollector(new ESReturns(ESConstants.INSTANCE.getFalseValue()), this.builtIns).collectFromSchema(listInvokeWithArguments), this.languageVersionSettings, this.builtIns));
        }
        return ConditionalDataFlowInfo.INSTANCE.getEMPTY();
    }

    public final void recordDefiniteInvocations(ResolvedCall<?> resolvedCall, BindingTrace bindingTrace, ModuleDescriptor moduleDescriptor) {
        KtLambdaExpression lambda;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.UseCallsInPlaceEffect)) {
            KtElement callElement = resolvedCall.getCall().getCallElement();
            KtCallExpression ktCallExpression = callElement instanceof KtCallExpression ? (KtCallExpression) callElement : null;
            if (ktCallExpression == null || (ktCallExpression instanceof KtDeclaration)) {
                return;
            }
            for (ESEffect eSEffect : getContextInfoWhen(new ESReturns(ESConstants.INSTANCE.getWildcard()), ktCallExpression, bindingTrace, moduleDescriptor).getFiredEffects()) {
                ESCalls eSCalls = eSEffect instanceof ESCalls ? (ESCalls) eSEffect : null;
                if (eSCalls != null) {
                    ESValue callable = eSCalls.getCallable();
                    ESLambda eSLambda = callable instanceof ESLambda ? (ESLambda) callable : null;
                    if (eSLambda != null && (lambda = eSLambda.getLambda()) != null) {
                        bindingTrace.record(BindingContext.LAMBDA_INVOCATIONS, lambda, eSCalls.getKind());
                    }
                }
            }
        }
    }

    public final DataFlowInfo extractDataFlowInfoFromCondition(KtExpression condition, boolean value, BindingTrace bindingTrace, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        if (!this.languageVersionSettings.supportsFeature(LanguageFeature.UseReturnsEffect)) {
            return DataFlowInfo.INSTANCE.getEMPTY();
        }
        if (condition == null) {
            return DataFlowInfo.INSTANCE.getEMPTY();
        }
        return ContextInfoToDataFlowInfoKt.toDataFlowInfo(getContextInfoWhen(new ESReturns(ESConstants.INSTANCE.booleanValue(value)), condition, bindingTrace, moduleDescriptor), this.languageVersionSettings, moduleDescriptor.getBuiltIns());
    }

    private final MutableContextInfo getContextInfoWhen(ESEffect observedEffect, KtExpression expression, BindingTrace bindingTrace, ModuleDescriptor moduleDescriptor) {
        Sequence sequenceFilter = SequencesKt.filter(PsiUtilsKt.getParentsWithSelf(expression), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.contracts.EffectSystem$getContextInfoWhen$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof KtExpression);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        Iterator it = sequenceFilter.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(bindingTrace.getContext().get(BindingContext.IS_CONTRACT_DECLARATION_BLOCK, (KtExpression) it.next()), (Object) true)) {
                return MutableContextInfo.INSTANCE.getEMPTY();
            }
        }
        Computation nonTrivialComputation = getNonTrivialComputation(expression, bindingTrace, moduleDescriptor);
        return nonTrivialComputation == null ? MutableContextInfo.INSTANCE.getEMPTY() : new InfoCollector(observedEffect, this.builtIns).collectFromSchema(nonTrivialComputation.getEffects());
    }

    private final Computation getNonTrivialComputation(KtExpression expression, BindingTrace trace, ModuleDescriptor moduleDescriptor) {
        Computation computationExtractOrGetCached = new EffectsExtractingVisitor(trace, moduleDescriptor, this.dataFlowValueFactory, this.languageVersionSettings).extractOrGetCached(expression);
        if (Intrinsics.areEqual(computationExtractOrGetCached, UNKNOWN_COMPUTATION.INSTANCE)) {
            return null;
        }
        return computationExtractOrGetCached;
    }
}
