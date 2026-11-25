package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.impl.VariableDescriptorWithInitializerImpl;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtVariableDeclaration;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.storage.NullableLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.WrappedTypeFactory;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.expressions.PreliminaryDeclarationVisitor;

/* JADX INFO: compiled from: VariableTypeAndInitializerResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 72\u00020\u0001:\u00017B[\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015¢\u0006\u0002\u0010\u0017J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J@\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J8\u0010+\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020$2\u0006\u0010-\u001a\u00020.2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J>\u0010/\u001a\u00020\u001b2\u0006\u0010!\u001a\u0002002\u0006\u0010#\u001a\u00020$2\u0006\u00101\u001a\u0002022\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010\u001c\u001a\u00020\u001dJ@\u00103\u001a\u0004\u0018\u00010\u001b2\u0006\u0010!\u001a\u0002002\u0006\u0010#\u001a\u00020$2\u0006\u00101\u001a\u0002022\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010\u001c\u001a\u00020\u001dJ>\u00104\u001a\u0002052\u0006\u0010!\u001a\u0002002\u0006\u0010,\u001a\u00020$2\u0006\u00101\u001a\u0002022\u0006\u0010%\u001a\u00020&2\u0006\u00106\u001a\u00020\u001b2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/resolve/VariableTypeAndInitializerResolver;", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "expressionTypingServices", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;", "typeResolver", "Lorg/jetbrains/kotlin/resolve/TypeResolver;", "constantExpressionEvaluator", "Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;", "delegatedPropertyResolver", "Lorg/jetbrains/kotlin/resolve/DelegatedPropertyResolver;", "wrappedTypeFactory", "Lorg/jetbrains/kotlin/types/WrappedTypeFactory;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "declarationReturnTypeSanitizer", "Lorg/jetbrains/kotlin/resolve/DeclarationReturnTypeSanitizer;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "anonymousTypeTransformers", "", "Lorg/jetbrains/kotlin/resolve/DeclarationSignatureAnonymousTypeTransformer;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;Lorg/jetbrains/kotlin/resolve/TypeResolver;Lorg/jetbrains/kotlin/resolve/constants/evaluate/ConstantExpressionEvaluator;Lorg/jetbrains/kotlin/resolve/DelegatedPropertyResolver;Lorg/jetbrains/kotlin/types/WrappedTypeFactory;Lorg/jetbrains/kotlin/types/TypeApproximator;Lorg/jetbrains/kotlin/resolve/DeclarationReturnTypeSanitizer;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Ljava/lang/Iterable;)V", "approximateType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "local", "", "resolveDelegatedPropertyType", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "scopeForInitializer", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "inferenceSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "resolveInitializerType", "scope", "initializer", "Lorg/jetbrains/kotlin/psi/KtExpression;", "resolveType", "Lorg/jetbrains/kotlin/descriptors/impl/VariableDescriptorWithInitializerImpl;", "variable", "Lorg/jetbrains/kotlin/psi/KtVariableDeclaration;", "resolveTypeNullable", "setConstantForVariableIfNeeded", "", "variableType", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VariableTypeAndInitializerResolver {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Iterable<DeclarationSignatureAnonymousTypeTransformer> anonymousTypeTransformers;
    private final ConstantExpressionEvaluator constantExpressionEvaluator;
    private final DeclarationReturnTypeSanitizer declarationReturnTypeSanitizer;
    private final DelegatedPropertyResolver delegatedPropertyResolver;
    private final ExpressionTypingServices expressionTypingServices;
    private final LanguageVersionSettings languageVersionSettings;
    private final StorageManager storageManager;
    private final TypeApproximator typeApproximator;
    private final TypeResolver typeResolver;
    private final WrappedTypeFactory wrappedTypeFactory;

    @JvmStatic
    public static final SimpleType getTypeForPropertyWithoutReturnType(String str) {
        return INSTANCE.getTypeForPropertyWithoutReturnType(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public VariableTypeAndInitializerResolver(StorageManager storageManager, ExpressionTypingServices expressionTypingServices, TypeResolver typeResolver, ConstantExpressionEvaluator constantExpressionEvaluator, DelegatedPropertyResolver delegatedPropertyResolver, WrappedTypeFactory wrappedTypeFactory, TypeApproximator typeApproximator, DeclarationReturnTypeSanitizer declarationReturnTypeSanitizer, LanguageVersionSettings languageVersionSettings, Iterable<? extends DeclarationSignatureAnonymousTypeTransformer> anonymousTypeTransformers) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(expressionTypingServices, "expressionTypingServices");
        Intrinsics.checkNotNullParameter(typeResolver, "typeResolver");
        Intrinsics.checkNotNullParameter(constantExpressionEvaluator, "constantExpressionEvaluator");
        Intrinsics.checkNotNullParameter(delegatedPropertyResolver, "delegatedPropertyResolver");
        Intrinsics.checkNotNullParameter(wrappedTypeFactory, "wrappedTypeFactory");
        Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
        Intrinsics.checkNotNullParameter(declarationReturnTypeSanitizer, "declarationReturnTypeSanitizer");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(anonymousTypeTransformers, "anonymousTypeTransformers");
        this.storageManager = storageManager;
        this.expressionTypingServices = expressionTypingServices;
        this.typeResolver = typeResolver;
        this.constantExpressionEvaluator = constantExpressionEvaluator;
        this.delegatedPropertyResolver = delegatedPropertyResolver;
        this.wrappedTypeFactory = wrappedTypeFactory;
        this.typeApproximator = typeApproximator;
        this.declarationReturnTypeSanitizer = declarationReturnTypeSanitizer;
        this.languageVersionSettings = languageVersionSettings;
        this.anonymousTypeTransformers = anonymousTypeTransformers;
    }

    /* JADX INFO: compiled from: VariableTypeAndInitializerResolver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/VariableTypeAndInitializerResolver$Companion;", "", "()V", "getTypeForPropertyWithoutReturnType", "Lorg/jetbrains/kotlin/types/SimpleType;", "property", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final SimpleType getTypeForPropertyWithoutReturnType(String property) {
            Intrinsics.checkNotNullParameter(property, "property");
            return ErrorUtils.createErrorType(ErrorTypeKind.RETURN_TYPE_FOR_PROPERTY, property);
        }
    }

    public final KotlinType resolveType(VariableDescriptorWithInitializerImpl variableDescriptor, LexicalScope scopeForInitializer, KtVariableDeclaration variable, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession, BindingTrace trace, boolean local) {
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        Intrinsics.checkNotNullParameter(scopeForInitializer, "scopeForInitializer");
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        Intrinsics.checkNotNullParameter(inferenceSession, "inferenceSession");
        Intrinsics.checkNotNullParameter(trace, "trace");
        KotlinType kotlinTypeResolveTypeNullable = resolveTypeNullable(variableDescriptor, scopeForInitializer, variable, dataFlowInfo, inferenceSession, trace, local);
        if (kotlinTypeResolveTypeNullable != null) {
            return kotlinTypeResolveTypeNullable;
        }
        if (local) {
            trace.report(Errors.VARIABLE_WITH_NO_TYPE_NO_INITIALIZER.on(variable));
        }
        Companion companion = INSTANCE;
        String strAsString = variableDescriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return companion.getTypeForPropertyWithoutReturnType(strAsString);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final KotlinType resolveTypeNullable(final VariableDescriptorWithInitializerImpl variableDescriptor, final LexicalScope scopeForInitializer, final KtVariableDeclaration variable, final DataFlowInfo dataFlowInfo, final InferenceSession inferenceSession, final BindingTrace trace, final boolean local) {
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        Intrinsics.checkNotNullParameter(scopeForInitializer, "scopeForInitializer");
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        Intrinsics.checkNotNullParameter(inferenceSession, "inferenceSession");
        Intrinsics.checkNotNullParameter(trace, "trace");
        KtTypeReference typeReference = variable.mo7396getTypeReference();
        if (typeReference != null) {
            return this.typeResolver.resolveType(scopeForInitializer, typeReference, trace, true);
        }
        if (!variable.hasInitializer() && (variable instanceof KtProperty) && (variableDescriptor instanceof VariableDescriptorWithAccessors)) {
            KtProperty ktProperty = (KtProperty) variable;
            if (ktProperty.hasDelegateExpression()) {
                return resolveDelegatedPropertyType(ktProperty, (VariableDescriptorWithAccessors) variableDescriptor, scopeForInitializer, dataFlowInfo, inferenceSession, trace, local);
            }
        }
        if (!variable.hasInitializer()) {
            return null;
        }
        if (!local) {
            return this.wrappedTypeFactory.createRecursionIntolerantDeferredType(trace, new Function0<KotlinType>() { // from class: org.jetbrains.kotlin.resolve.VariableTypeAndInitializerResolver.resolveTypeNullable.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final KotlinType invoke() {
                    PreliminaryDeclarationVisitor.Companion companion = PreliminaryDeclarationVisitor.INSTANCE;
                    KtVariableDeclaration ktVariableDeclaration = variable;
                    BindingTrace bindingTrace = trace;
                    LanguageVersionSettings languageVersionSettings = this.expressionTypingServices.getLanguageVersionSettings();
                    Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "getLanguageVersionSettings(...)");
                    companion.createForDeclaration(ktVariableDeclaration, bindingTrace, languageVersionSettings);
                    VariableTypeAndInitializerResolver variableTypeAndInitializerResolver = this;
                    LexicalScope lexicalScope = scopeForInitializer;
                    KtExpression initializer = variable.getInitializer();
                    Intrinsics.checkNotNull(initializer);
                    KotlinType kotlinTypeTransformAnonymousTypeIfNeeded = DescriptorResolver.transformAnonymousTypeIfNeeded(variableDescriptor, variable, variableTypeAndInitializerResolver.resolveInitializerType(lexicalScope, initializer, dataFlowInfo, inferenceSession, trace, local), trace, this.anonymousTypeTransformers, this.languageVersionSettings);
                    Intrinsics.checkNotNullExpressionValue(kotlinTypeTransformAnonymousTypeIfNeeded, "transformAnonymousTypeIfNeeded(...)");
                    return kotlinTypeTransformAnonymousTypeIfNeeded;
                }
            });
        }
        KtExpression initializer = variable.getInitializer();
        Intrinsics.checkNotNull(initializer);
        return resolveInitializerType(scopeForInitializer, initializer, dataFlowInfo, inferenceSession, trace, local);
    }

    public final void setConstantForVariableIfNeeded(final VariableDescriptorWithInitializerImpl variableDescriptor, final LexicalScope scope, final KtVariableDeclaration variable, final DataFlowInfo dataFlowInfo, final KotlinType variableType, final InferenceSession inferenceSession, final BindingTrace trace) {
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        Intrinsics.checkNotNullParameter(variableType, "variableType");
        Intrinsics.checkNotNullParameter(inferenceSession, "inferenceSession");
        Intrinsics.checkNotNullParameter(trace, "trace");
        if (!variable.hasInitializer() || variable.isVar()) {
            return;
        }
        variableDescriptor.setCompileTimeInitializerFactory((Function0) new Function0<NullableLazyValue<? extends ConstantValue<?>>>() { // from class: org.jetbrains.kotlin.resolve.VariableTypeAndInitializerResolver.setConstantForVariableIfNeeded.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final NullableLazyValue<? extends ConstantValue<?>> invoke() {
                StorageManager storageManager = VariableTypeAndInitializerResolver.this.storageManager;
                final VariableDescriptorWithInitializerImpl variableDescriptorWithInitializerImpl = variableDescriptor;
                final KotlinType kotlinType = variableType;
                final KtVariableDeclaration ktVariableDeclaration = variable;
                final VariableTypeAndInitializerResolver variableTypeAndInitializerResolver = VariableTypeAndInitializerResolver.this;
                final LexicalScope lexicalScope = scope;
                final DataFlowInfo dataFlowInfo2 = dataFlowInfo;
                final InferenceSession inferenceSession2 = inferenceSession;
                final BindingTrace bindingTrace = trace;
                return storageManager.createRecursionTolerantNullableLazyValue(new Function0<ConstantValue<? extends Object>>() { // from class: org.jetbrains.kotlin.resolve.VariableTypeAndInitializerResolver.setConstantForVariableIfNeeded.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final ConstantValue<? extends Object> invoke() {
                        if (!DescriptorUtils.shouldRecordInitializerForProperty(variableDescriptorWithInitializerImpl, kotlinType)) {
                            return null;
                        }
                        KtExpression initializer = ktVariableDeclaration.getInitializer();
                        ExpressionTypingServices expressionTypingServices = variableTypeAndInitializerResolver.expressionTypingServices;
                        LexicalScope lexicalScope2 = lexicalScope;
                        Intrinsics.checkNotNull(initializer);
                        KotlinType kotlinTypeSafeGetType = expressionTypingServices.safeGetType(lexicalScope2, initializer, kotlinType, dataFlowInfo2, inferenceSession2, bindingTrace);
                        Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeGetType, "safeGetType(...)");
                        CompileTimeConstant<?> compileTimeConstantEvaluateExpression = variableTypeAndInitializerResolver.constantExpressionEvaluator.evaluateExpression(initializer, bindingTrace, kotlinTypeSafeGetType);
                        if (compileTimeConstantEvaluateExpression == null) {
                            return null;
                        }
                        if (compileTimeConstantEvaluateExpression.getUsesNonConstValAsConstant() && variableDescriptorWithInitializerImpl.isConst()) {
                            bindingTrace.report(Errors.NON_CONST_VAL_USED_IN_CONSTANT_EXPRESSION.on(initializer));
                        }
                        return compileTimeConstantEvaluateExpression.toConstantValue(kotlinTypeSafeGetType);
                    }
                }, null);
            }
        });
    }

    private final KotlinType resolveDelegatedPropertyType(final KtProperty property, final VariableDescriptorWithAccessors variableDescriptor, final LexicalScope scopeForInitializer, final DataFlowInfo dataFlowInfo, final InferenceSession inferenceSession, final BindingTrace trace, final boolean local) {
        return this.wrappedTypeFactory.createRecursionIntolerantDeferredType(trace, new Function0<KotlinType>() { // from class: org.jetbrains.kotlin.resolve.VariableTypeAndInitializerResolver.resolveDelegatedPropertyType.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinType invoke() {
                ErrorType errorTypeCreateErrorType;
                KtExpression delegateExpression = property.getDelegateExpression();
                Intrinsics.checkNotNull(delegateExpression);
                KotlinType getValueMethodReturnType = this.delegatedPropertyResolver.getGetValueMethodReturnType(variableDescriptor, delegateExpression, this.delegatedPropertyResolver.resolveDelegateExpression(delegateExpression, property, variableDescriptor, scopeForInitializer, trace, dataFlowInfo, inferenceSession), trace, scopeForInitializer, dataFlowInfo, inferenceSession);
                if (getValueMethodReturnType == null || (errorTypeCreateErrorType = this.approximateType(getValueMethodReturnType, local)) == null) {
                    ErrorTypeKind errorTypeKind = ErrorTypeKind.TYPE_FOR_DELEGATION;
                    String text = delegateExpression.getText();
                    Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                    errorTypeCreateErrorType = ErrorUtils.createErrorType(errorTypeKind, text);
                }
                KotlinType kotlinTypeTransformAnonymousTypeIfNeeded = DescriptorResolver.transformAnonymousTypeIfNeeded(variableDescriptor, property, errorTypeCreateErrorType, trace, this.anonymousTypeTransformers, this.languageVersionSettings);
                Intrinsics.checkNotNullExpressionValue(kotlinTypeTransformAnonymousTypeIfNeeded, "transformAnonymousTypeIfNeeded(...)");
                return kotlinTypeTransformAnonymousTypeIfNeeded;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinType resolveInitializerType(LexicalScope scope, KtExpression initializer, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession, BindingTrace trace, boolean local) {
        KotlinType kotlinTypeSafeGetType = this.expressionTypingServices.safeGetType(scope, initializer, TypeUtils.NO_EXPECTED_TYPE, dataFlowInfo, inferenceSession, trace);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeGetType, "safeGetType(...)");
        return this.declarationReturnTypeSanitizer.sanitizeReturnType(approximateType(kotlinTypeSafeGetType, local), this.wrappedTypeFactory, trace, this.languageVersionSettings);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UnwrappedType approximateType(KotlinType type, boolean local) {
        return this.typeApproximator.approximateDeclarationType(type, local);
    }
}
