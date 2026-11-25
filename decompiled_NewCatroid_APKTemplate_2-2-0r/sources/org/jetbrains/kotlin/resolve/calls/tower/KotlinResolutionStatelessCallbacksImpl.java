package org.jetbrains.kotlin.resolve.calls.tower;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceUtilKt;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector;
import org.jetbrains.kotlin.resolve.calls.inference.components.SimpleConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeIntersector;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: KotlinResolutionStatelessCallbacksImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J \u0010 \u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020$H\u0016J \u0010 \u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020!2\u0006\u0010%\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010&\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020\u0016H\u0016J\u0016\u0010'\u001a\u00020\u00182\f\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)H\u0016J\u0010\u0010+\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020\u0016H\u0016J\u0012\u0010,\u001a\u00020\u00182\b\u0010-\u001a\u0004\u0018\u00010.H\u0016J\u0010\u0010/\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020\u0016H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/KotlinResolutionStatelessCallbacksImpl;", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionStatelessCallbacks;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "(Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V", "createConstraintSystemForOverloadResolution", "Lorg/jetbrains/kotlin/resolve/calls/results/SimpleConstraintSystem;", "constraintInjector", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getScopeTowerForCallableReferenceArgument", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "getVariableCandidateIfInvoke", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "functionCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "isApplicableCallForBuilderInference", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "isBuilderInferenceCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "isDescriptorFromSource", "isHiddenInResolution", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kotlinCall", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "kotlinCallArgument", "isInfixCall", "isOldIntersectionIsEmpty", SpecTypePair.NAME_types, "", "Lorg/jetbrains/kotlin/types/KotlinType;", "isOperatorCall", "isSuperExpression", "receiver", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "isSuperOrDelegatingConstructorCall", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinResolutionStatelessCallbacksImpl implements KotlinResolutionStatelessCallbacks {
    private final DeprecationResolver deprecationResolver;
    private final KotlinTypeRefiner kotlinTypeRefiner;
    private final LanguageVersionSettings languageVersionSettings;

    public KotlinResolutionStatelessCallbacksImpl(DeprecationResolver deprecationResolver, LanguageVersionSettings languageVersionSettings, KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.deprecationResolver = deprecationResolver;
        this.languageVersionSettings = languageVersionSettings;
        this.kotlinTypeRefiner = kotlinTypeRefiner;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks
    public boolean isDescriptorFromSource(CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return DescriptorToSourceUtils.descriptorToDeclaration(descriptor) != null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks
    public boolean isInfixCall(KotlinCall kotlinCall) {
        Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
        return (kotlinCall instanceof PSIKotlinCallImpl) && CallResolverUtilKt.isInfixCall(((PSIKotlinCallImpl) kotlinCall).getPsiCall());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks
    public boolean isOperatorCall(KotlinCall kotlinCall) {
        Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
        return (kotlinCall instanceof PSIKotlinCallForInvoke) || ((kotlinCall instanceof PSIKotlinCallImpl) && CallResolverUtilKt.isConventionCall(((PSIKotlinCallImpl) kotlinCall).getPsiCall()));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks
    public boolean isSuperOrDelegatingConstructorCall(KotlinCall kotlinCall) {
        Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
        return (kotlinCall instanceof PSIKotlinCallImpl) && CallResolverUtilKt.isSuperOrDelegatingConstructorCall(((PSIKotlinCallImpl) kotlinCall).getPsiCall());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks
    public boolean isHiddenInResolution(DeclarationDescriptor descriptor, KotlinCall kotlinCall, KotlinResolutionCallbacks resolutionCallbacks) {
        BindingTrace trace;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        DeprecationResolver deprecationResolver = this.deprecationResolver;
        PSIKotlinCall pSIKotlinCall = kotlinCall instanceof PSIKotlinCall ? (PSIKotlinCall) kotlinCall : null;
        Call psiCall = pSIKotlinCall != null ? pSIKotlinCall.getPsiCall() : null;
        KotlinResolutionCallbacksImpl kotlinResolutionCallbacksImpl = resolutionCallbacks instanceof KotlinResolutionCallbacksImpl ? (KotlinResolutionCallbacksImpl) resolutionCallbacks : null;
        return DeprecationResolver.isHiddenInResolution$default(deprecationResolver, descriptor, psiCall, (kotlinResolutionCallbacksImpl == null || (trace = kotlinResolutionCallbacksImpl.getTrace()) == null) ? null : trace.getContext(), (kotlinCall instanceof PSIKotlinCallImpl) && NewResolutionOldInferenceKt.isCallWithSuperReceiver(((PSIKotlinCallImpl) kotlinCall).getPsiCall()), false, 16, null);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks
    public boolean isHiddenInResolution(DeclarationDescriptor descriptor, KotlinCallArgument kotlinCallArgument, KotlinResolutionCallbacks resolutionCallbacks) {
        BindingTrace trace;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(kotlinCallArgument, "kotlinCallArgument");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        DeprecationResolver deprecationResolver = this.deprecationResolver;
        KtExpression psiExpression = NewCallArgumentsKt.getPsiExpression(NewCallArgumentsKt.getPsiCallArgument(kotlinCallArgument));
        KotlinResolutionCallbacksImpl kotlinResolutionCallbacksImpl = resolutionCallbacks instanceof KotlinResolutionCallbacksImpl ? (KotlinResolutionCallbacksImpl) resolutionCallbacks : null;
        return deprecationResolver.isHiddenInResolution(descriptor, (KtElement) psiExpression, (kotlinResolutionCallbacksImpl == null || (trace = kotlinResolutionCallbacksImpl.getTrace()) == null) ? null : trace.getContext(), false, false);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks
    public boolean isSuperExpression(SimpleKotlinCallArgument receiver) {
        return (receiver != null ? NewCallArgumentsKt.getPsiExpression(receiver) : null) instanceof KtSuperExpression;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks
    public ImplicitScopeTower getScopeTowerForCallableReferenceArgument(CallableReferenceKotlinCallArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        return ((CallableReferenceKotlinCallArgumentImpl) argument).getScopeTowerForResolution();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks
    public ResolutionCandidate getVariableCandidateIfInvoke(KotlinCall functionCall) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        PSIKotlinCallForInvoke pSIKotlinCallForInvoke = functionCall instanceof PSIKotlinCallForInvoke ? (PSIKotlinCallForInvoke) functionCall : null;
        if (pSIKotlinCallForInvoke != null) {
            return pSIKotlinCallForInvoke.getVariableCall();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks
    public boolean isBuilderInferenceCall(KotlinCallArgument argument, ValueParameterDescriptor parameter) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        return BuilderInferenceUtilKt.isBuilderInferenceCall(parameter, NewCallArgumentsKt.getPsiCallArgument(argument).getValueArgument(), this.languageVersionSettings);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks
    public boolean isApplicableCallForBuilderInference(CallableDescriptor descriptor, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return BuilderInferenceUtilKt.isApplicableCallForBuilderInference(descriptor, languageVersionSettings);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks
    public boolean isOldIntersectionIsEmpty(Collection<? extends KotlinType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return TypeIntersector.intersectTypes(types) == null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionStatelessCallbacks
    public SimpleConstraintSystem createConstraintSystemForOverloadResolution(ConstraintInjector constraintInjector, KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(constraintInjector, "constraintInjector");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        return new SimpleConstraintSystemImpl(constraintInjector, builtIns, this.kotlinTypeRefiner, this.languageVersionSettings);
    }
}
