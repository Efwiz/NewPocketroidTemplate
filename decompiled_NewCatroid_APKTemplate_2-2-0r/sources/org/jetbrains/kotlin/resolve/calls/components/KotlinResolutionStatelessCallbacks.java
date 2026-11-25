package org.jetbrains.kotlin.resolve.calls.components;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ExternalComponents.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH&J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H&J\u0018\u0010\u0016\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H&J\u0010\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H&J \u0010\u001b\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001fH&J \u0010\u001b\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u001fH&J\u0010\u0010!\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u000fH&J\u0016\u0010\"\u001a\u00020\u00112\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$H&J\u0010\u0010&\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u000fH&J\u0012\u0010'\u001a\u00020\u00112\b\u0010(\u001a\u0004\u0018\u00010)H&J\u0010\u0010*\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u000fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006+À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionStatelessCallbacks;", "", "createConstraintSystemForOverloadResolution", "Lorg/jetbrains/kotlin/resolve/calls/results/SimpleConstraintSystem;", "constraintInjector", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getScopeTowerForCallableReferenceArgument", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "getVariableCandidateIfInvoke", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "functionCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "isApplicableCallForBuilderInference", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "isBuilderInferenceCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "isDescriptorFromSource", "isHiddenInResolution", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kotlinCall", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "kotlinCallArgument", "isInfixCall", "isOldIntersectionIsEmpty", SpecTypePair.NAME_types, "", "Lorg/jetbrains/kotlin/types/KotlinType;", "isOperatorCall", "isSuperExpression", "receiver", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "isSuperOrDelegatingConstructorCall", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinResolutionStatelessCallbacks {
    SimpleConstraintSystem createConstraintSystemForOverloadResolution(ConstraintInjector constraintInjector, KotlinBuiltIns builtIns);

    ImplicitScopeTower getScopeTowerForCallableReferenceArgument(CallableReferenceKotlinCallArgument argument);

    ResolutionCandidate getVariableCandidateIfInvoke(KotlinCall functionCall);

    boolean isApplicableCallForBuilderInference(CallableDescriptor descriptor, LanguageVersionSettings languageVersionSettings);

    boolean isBuilderInferenceCall(KotlinCallArgument argument, ValueParameterDescriptor parameter);

    boolean isDescriptorFromSource(CallableDescriptor descriptor);

    boolean isHiddenInResolution(DeclarationDescriptor descriptor, KotlinCall kotlinCall, KotlinResolutionCallbacks resolutionCallbacks);

    boolean isHiddenInResolution(DeclarationDescriptor descriptor, KotlinCallArgument kotlinCallArgument, KotlinResolutionCallbacks resolutionCallbacks);

    boolean isInfixCall(KotlinCall kotlinCall);

    boolean isOldIntersectionIsEmpty(Collection<? extends KotlinType> types);

    boolean isOperatorCall(KotlinCall kotlinCall);

    boolean isSuperExpression(SimpleKotlinCallArgument receiver);

    boolean isSuperOrDelegatingConstructorCall(KotlinCall kotlinCall);
}
