package org.jetbrains.kotlin.resolve.inline;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.coroutines.CoroutineUtilKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.resolve.AnalyzerExtensions;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.KtDescriptorUtilKt;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: InlineAnalyzerExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J \u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u0015\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000bH\u0002J*\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0011\u001a\u00020\u001c2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0002J \u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\"\u001a\u00020\t2\u0006\u0010 \u001a\u00020!2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010#\u001a\u00020\t2\u0006\u0010$\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/resolve/inline/InlineAnalyzerExtension;", "Lorg/jetbrains/kotlin/resolve/AnalyzerExtensions$AnalyzerExtension;", "reasonableInlineRules", "", "Lorg/jetbrains/kotlin/resolve/inline/ReasonableInlineRule;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Ljava/lang/Iterable;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "checkDefaultValue", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "parameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "ktParameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "checkDefaults", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "function", "Lorg/jetbrains/kotlin/psi/KtFunction;", "checkHasInlinableAndNullability", "checkInlinableParameter", "", "parameter", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "expression", "Lorg/jetbrains/kotlin/psi/KtElement;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "checkModalityAndOverrides", "callableDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "functionOrProperty", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "notSupportedInInlineCheck", "process", "descriptor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineAnalyzerExtension implements AnalyzerExtensions.AnalyzerExtension {
    private final LanguageVersionSettings languageVersionSettings;
    private final Iterable<ReasonableInlineRule> reasonableInlineRules;

    /* JADX WARN: Multi-variable type inference failed */
    public InlineAnalyzerExtension(Iterable<? extends ReasonableInlineRule> reasonableInlineRules, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(reasonableInlineRules, "reasonableInlineRules");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.reasonableInlineRules = reasonableInlineRules;
        this.languageVersionSettings = languageVersionSettings;
    }

    @Override // org.jetbrains.kotlin.resolve.AnalyzerExtensions.AnalyzerExtension
    public void process(CallableMemberDescriptor descriptor, KtCallableDeclaration functionOrProperty, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(functionOrProperty, "functionOrProperty");
        Intrinsics.checkNotNullParameter(trace, "trace");
        checkModalityAndOverrides(descriptor, functionOrProperty, trace);
        notSupportedInInlineCheck(functionOrProperty, trace);
        if (descriptor instanceof FunctionDescriptor) {
            boolean z = functionOrProperty instanceof KtNamedFunction;
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
            checkDefaults(functionDescriptor, (KtNamedFunction) functionOrProperty, trace);
            checkHasInlinableAndNullability(functionDescriptor, (KtFunction) functionOrProperty, trace);
            return;
        }
        boolean z2 = descriptor instanceof PropertyDescriptor;
        boolean z3 = functionOrProperty instanceof KtProperty;
        if (Intrinsics.areEqual(trace.get(BindingContext.BACKING_FIELD_REQUIRED, (PropertyDescriptor) descriptor), (Object) true) || ((KtProperty) functionOrProperty).getDelegateExpression() != null) {
            trace.report(Errors.INLINE_PROPERTY_WITH_BACKING_FIELD.on(functionOrProperty));
        }
    }

    private final void notSupportedInInlineCheck(KtCallableDeclaration functionOrProperty, final BindingTrace trace) {
        functionOrProperty.acceptChildren(new KtVisitorVoid() { // from class: org.jetbrains.kotlin.resolve.inline.InlineAnalyzerExtension$notSupportedInInlineCheck$visitor$1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitKtElement(KtElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                super.visitKtElement(element);
                element.acceptChildren(this);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitClass(KtClass klass) {
                Intrinsics.checkNotNullParameter(klass, "klass");
                trace.report(Errors.NOT_YET_SUPPORTED_IN_INLINE.on(klass, "Local classes"));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitNamedFunction(KtNamedFunction function) {
                Intrinsics.checkNotNullParameter(function, "function");
                if (function.getMyParent().getMyParent() instanceof KtObjectDeclaration) {
                    super.visitNamedFunction(function);
                } else {
                    trace.report(Errors.NOT_YET_SUPPORTED_IN_INLINE.on(function, "Local functions"));
                }
            }
        });
    }

    private final void checkDefaults(FunctionDescriptor functionDescriptor, KtFunction function, BindingTrace trace) {
        List<KtParameter> valueParameters = function.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        for (ValueParameterDescriptor valueParameterDescriptor : functionDescriptor.getValueParameters()) {
            Intrinsics.checkNotNull(valueParameterDescriptor);
            if (ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor)) {
                KtParameter ktParameter = valueParameters.get(valueParameterDescriptor.getIndex());
                boolean z = !valueParameterDescriptor.declaresDefaultValue() && DescriptorUtilsKt.declaresOrInheritsDefaultValue(valueParameterDescriptor);
                ValueParameterDescriptor valueParameterDescriptor2 = valueParameterDescriptor;
                Intrinsics.checkNotNull(ktParameter);
                if (checkInlinableParameter(valueParameterDescriptor2, ktParameter, functionDescriptor, null) || z) {
                    if (z || !this.languageVersionSettings.supportsFeature(LanguageFeature.InlineDefaultFunctionalParameters)) {
                        trace.report(Errors.NOT_YET_SUPPORTED_IN_INLINE.on(ktParameter, "Functional parameters with inherited default values"));
                    } else {
                        checkDefaultValue(trace, valueParameterDescriptor, ktParameter);
                    }
                }
                if (functionDescriptor.isSuspend() && InlineUtil.isInlineParameterExceptNullability(valueParameterDescriptor2) && CoroutineUtilKt.getHasSuspendFunctionType(valueParameterDescriptor)) {
                    trace.report(Errors.NOT_YET_SUPPORTED_IN_INLINE.on(ktParameter, "Suspend functional parameters with default values"));
                }
            }
        }
    }

    private final void checkDefaultValue(BindingTrace trace, ValueParameterDescriptor parameterDescriptor, KtParameter ktParameter) {
        KtExpression defaultValue = ktParameter.getDefaultValue();
        if (defaultValue == null || InlineUtil.isInlinableParameterExpression(KtPsiUtil.deparenthesize(defaultValue))) {
            return;
        }
        trace.report(Errors.INVALID_DEFAULT_FUNCTIONAL_PARAMETER_FOR_INLINE.on(defaultValue, defaultValue, parameterDescriptor));
    }

    private final void checkModalityAndOverrides(CallableMemberDescriptor callableDescriptor, KtCallableDeclaration functionOrProperty, BindingTrace trace) {
        KtTypeParameter modifier;
        if ((callableDescriptor.getContainingDeclaration() instanceof PackageFragmentDescriptor) || DescriptorVisibilities.isPrivate(callableDescriptor.getVisibilityImpl())) {
            return;
        }
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        boolean zIsEmpty = overriddenDescriptors.isEmpty();
        if (!zIsEmpty) {
            List<KtTypeParameter> typeParameters = functionOrProperty.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            for (TypeParameterDescriptor typeParameterDescriptor : callableDescriptor.getTypeParameters()) {
                if (typeParameterDescriptor.isReified()) {
                    KtTypeParameter ktTypeParameter = typeParameters.get(typeParameterDescriptor.getIndex());
                    KtModifierList modifierList = ktTypeParameter.getModifierList();
                    if (modifierList == null || (modifier = modifierList.getModifier(KtTokens.REIFIED_KEYWORD)) == null) {
                        modifier = ktTypeParameter;
                    }
                    trace.report(Errors.REIFIED_TYPE_PARAMETER_IN_OVERRIDE.on(modifier));
                }
            }
        }
        if (KtDescriptorUtilKt.isEffectivelyFinal(callableDescriptor, true)) {
            if (!zIsEmpty) {
                trace.report(Errors.OVERRIDE_BY_INLINE.on(functionOrProperty));
            }
            if (KtDescriptorUtilKt.isEffectivelyFinal(callableDescriptor, false)) {
                return;
            }
            trace.report(Errors.DECLARATION_CANT_BE_INLINED_WARNING.on(functionOrProperty));
            return;
        }
        trace.report(Errors.DECLARATION_CANT_BE_INLINED.on(functionOrProperty));
    }

    private final void checkHasInlinableAndNullability(FunctionDescriptor functionDescriptor, KtFunction function, BindingTrace trace) {
        KtFunction modifier;
        List<KtParameter> valueParameters = function.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> valueParameters2 = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
        boolean zCheckInlinableParameter = false;
        for (Pair pair : CollectionsKt.zip(valueParameters, valueParameters2)) {
            KtParameter ktParameter = (KtParameter) pair.component1();
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) pair.component2();
            Intrinsics.checkNotNull(valueParameterDescriptor);
            Intrinsics.checkNotNull(ktParameter);
            zCheckInlinableParameter |= checkInlinableParameter(valueParameterDescriptor, ktParameter, functionDescriptor, trace);
        }
        if (zCheckInlinableParameter) {
            return;
        }
        FunctionDescriptor functionDescriptor2 = functionDescriptor;
        if (InlineOnlyKt.isInlineWithReified(functionDescriptor2) || InlineOnlyKt.isInlineOnly(functionDescriptor2) || functionDescriptor.get$isExpect() || functionDescriptor.isSuspend()) {
            return;
        }
        Iterable<ReasonableInlineRule> iterable = this.reasonableInlineRules;
        if (!(iterable instanceof Collection) || !((Collection) iterable).isEmpty()) {
            Iterator<ReasonableInlineRule> it = iterable.iterator();
            while (it.hasNext()) {
                BindingContext context = trace.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                if (it.next().isInlineReasonable(functionDescriptor, function, context)) {
                    return;
                }
            }
        }
        KotlinType returnType = functionDescriptor.getReturnType();
        if (returnType == null || !InlineClassesUtilsKt.needsMfvcFlattening(returnType)) {
            KtModifierList modifierList = function.getModifierList();
            if (modifierList == null || (modifier = modifierList.getModifier(KtTokens.INLINE_KEYWORD)) == null) {
                modifier = function;
            }
            Intrinsics.checkNotNull(modifier);
            trace.report(Errors.NOTHING_TO_INLINE.on(modifier));
        }
    }

    private final boolean checkInlinableParameter(ParameterDescriptor parameter, KtElement expression, CallableDescriptor functionDescriptor, BindingTrace trace) {
        if (!InlineUtil.isInlineParameterExceptNullability(parameter)) {
            return false;
        }
        if (!parameter.getType().getIsMarkedNullable()) {
            return true;
        }
        if (trace == null) {
            return false;
        }
        trace.report(Errors.NULLABLE_INLINE_PARAMETER.on(expression, expression, functionDescriptor));
        return false;
    }
}
