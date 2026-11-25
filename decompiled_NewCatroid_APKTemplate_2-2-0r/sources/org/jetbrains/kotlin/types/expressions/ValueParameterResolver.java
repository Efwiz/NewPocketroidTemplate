package org.jetbrains.kotlin.types.expressions;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorResolver;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.checkers.NewSchemeOfIntegerOperatorResolutionChecker;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeImpl;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: ValueParameterResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002JD\u0010\u0013\u001a\u00020\f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00152\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/ValueParameterResolver;", "", "expressionTypingServices", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;", "constantExpressionEvaluator", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "(Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;)V", "resolveDefaultValue", "", "valueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "parameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "resolveValueParameters", "valueParameters", "", "valueParameterDescriptors", "declaringScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "inferenceSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ValueParameterResolver {
    private final ConstantExpressionEvaluator constantExpressionEvaluator;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final ExpressionTypingServices expressionTypingServices;
    private final LanguageVersionSettings languageVersionSettings;

    public ValueParameterResolver(ExpressionTypingServices expressionTypingServices, ConstantExpressionEvaluator constantExpressionEvaluator, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory) {
        Intrinsics.checkNotNullParameter(expressionTypingServices, "expressionTypingServices");
        Intrinsics.checkNotNullParameter(constantExpressionEvaluator, "constantExpressionEvaluator");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        this.expressionTypingServices = expressionTypingServices;
        this.constantExpressionEvaluator = constantExpressionEvaluator;
        this.languageVersionSettings = languageVersionSettings;
        this.dataFlowValueFactory = dataFlowValueFactory;
    }

    public final void resolveValueParameters(List<? extends KtParameter> valueParameters, List<? extends ValueParameterDescriptor> valueParameterDescriptors, LexicalScope declaringScope, DataFlowInfo dataFlowInfo, BindingTrace trace, InferenceSession inferenceSession) {
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        Intrinsics.checkNotNullParameter(valueParameterDescriptors, "valueParameterDescriptors");
        Intrinsics.checkNotNullParameter(declaringScope, "declaringScope");
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        Intrinsics.checkNotNullParameter(trace, "trace");
        ExpressionTypingContext expressionTypingContextNewContext = ExpressionTypingContext.newContext(trace, new LexicalScopeImpl(declaringScope, declaringScope.getOwnerDescriptor(), false, null, CollectionsKt.emptyList(), LexicalScopeKind.DEFAULT_VALUE, null, null, 192, null), dataFlowInfo, TypeUtils.NO_EXPECTED_TYPE, this.languageVersionSettings, this.dataFlowValueFactory, inferenceSession);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextNewContext, "newContext(...)");
        for (Pair pair : CollectionsKt.zip(valueParameterDescriptors, valueParameters)) {
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) pair.component1();
            KtParameter ktParameter = (KtParameter) pair.component2();
            ForceResolveUtil.forceResolveAllContents(valueParameterDescriptor.getAnnotations());
            resolveDefaultValue(valueParameterDescriptor, ktParameter, expressionTypingContextNewContext);
        }
    }

    private final void resolveDefaultValue(ValueParameterDescriptor valueParameterDescriptor, KtParameter parameter, ExpressionTypingContext context) {
        KtExpression defaultValue;
        if (valueParameterDescriptor.declaresDefaultValue() && (defaultValue = parameter.getDefaultValue()) != null) {
            KotlinType type = valueParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            this.expressionTypingServices.getTypeInfo(defaultValue, context.replaceExpectedType(type));
            BindingTrace trace = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            NewSchemeOfIntegerOperatorResolutionChecker.checkArgument(type, defaultValue, trace, this.constantExpressionEvaluator.getModule());
            if (DescriptorUtils.isAnnotationClass(DescriptorResolver.getContainingClass(context.scope))) {
                ConstantExpressionEvaluator constantExpressionEvaluator = this.constantExpressionEvaluator;
                BindingTrace trace2 = context.trace;
                Intrinsics.checkNotNullExpressionValue(trace2, "trace");
                CompileTimeConstant<?> compileTimeConstantEvaluateExpression = constantExpressionEvaluator.evaluateExpression(defaultValue, trace2, type);
                if ((compileTimeConstantEvaluateExpression == null || compileTimeConstantEvaluateExpression.getUsesNonConstValAsConstant()) && !KotlinTypeKt.isError(type)) {
                    context.trace.report(Errors.ANNOTATION_PARAMETER_DEFAULT_VALUE_MUST_BE_CONSTANT.on(defaultValue));
                }
            }
        }
    }
}
