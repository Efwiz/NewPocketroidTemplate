package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertyGetterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertySetterDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPropertyDelegate;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtVariableDeclaration;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.util.UnderscoreUtilKt;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElementKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.DataFlowAnalyzer;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingFacade;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;

/* JADX INFO: compiled from: LocalVariableResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J \u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0010\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J2\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00180\u001f2\u0006\u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&J0\u0010'\u001a\u00020\u00182\u0006\u0010#\u001a\u00020$2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0002J/\u00100\u001a\u0002012\u0006\u0010#\u001a\u00020$2\u0006\u0010(\u001a\u00020)2\b\u00102\u001a\u0004\u0018\u0001032\u0006\u0010.\u001a\u00020/H\u0000¢\u0006\u0002\b4J\u0014\u00105\u001a\u00020\u0014*\u0002062\u0006\u0010.\u001a\u00020/H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/resolve/LocalVariableResolver;", "", "modifiersChecker", "Lorg/jetbrains/kotlin/resolve/ModifiersChecker;", "identifierChecker", "Lorg/jetbrains/kotlin/resolve/IdentifierChecker;", "dataFlowAnalyzer", "Lorg/jetbrains/kotlin/types/expressions/DataFlowAnalyzer;", "annotationResolver", "Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "variableTypeAndInitializerResolver", "Lorg/jetbrains/kotlin/resolve/VariableTypeAndInitializerResolver;", "delegatedPropertyResolver", "Lorg/jetbrains/kotlin/resolve/DelegatedPropertyResolver;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "(Lorg/jetbrains/kotlin/resolve/ModifiersChecker;Lorg/jetbrains/kotlin/resolve/IdentifierChecker;Lorg/jetbrains/kotlin/types/expressions/DataFlowAnalyzer;Lorg/jetbrains/kotlin/resolve/AnnotationResolver;Lorg/jetbrains/kotlin/resolve/VariableTypeAndInitializerResolver;Lorg/jetbrains/kotlin/resolve/DelegatedPropertyResolver;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;)V", "checkLocalVariableDeclaration", "", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "ktProperty", "Lorg/jetbrains/kotlin/psi/KtProperty;", "initializeWithDefaultGetterSetter", "propertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/PropertyDescriptorImpl;", "process", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "property", "typingContext", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "facade", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingFacade;", "resolveLocalVariableDescriptor", "variable", "Lorg/jetbrains/kotlin/psi/KtVariableDeclaration;", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "inferenceSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "resolveLocalVariableDescriptorWithType", "Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableDescriptor;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "resolveLocalVariableDescriptorWithType$frontend", "updateAccessorFlagsFromResolvedCallForDelegatedProperty", "Lorg/jetbrains/kotlin/descriptors/VariableAccessorDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalVariableResolver {
    private final AnnotationResolver annotationResolver;
    private final DataFlowAnalyzer dataFlowAnalyzer;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final DelegatedPropertyResolver delegatedPropertyResolver;
    private final IdentifierChecker identifierChecker;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModifiersChecker modifiersChecker;
    private final VariableTypeAndInitializerResolver variableTypeAndInitializerResolver;

    public LocalVariableResolver(ModifiersChecker modifiersChecker, IdentifierChecker identifierChecker, DataFlowAnalyzer dataFlowAnalyzer, AnnotationResolver annotationResolver, VariableTypeAndInitializerResolver variableTypeAndInitializerResolver, DelegatedPropertyResolver delegatedPropertyResolver, LanguageVersionSettings languageVersionSettings, DataFlowValueFactory dataFlowValueFactory) {
        Intrinsics.checkNotNullParameter(modifiersChecker, "modifiersChecker");
        Intrinsics.checkNotNullParameter(identifierChecker, "identifierChecker");
        Intrinsics.checkNotNullParameter(dataFlowAnalyzer, "dataFlowAnalyzer");
        Intrinsics.checkNotNullParameter(annotationResolver, "annotationResolver");
        Intrinsics.checkNotNullParameter(variableTypeAndInitializerResolver, "variableTypeAndInitializerResolver");
        Intrinsics.checkNotNullParameter(delegatedPropertyResolver, "delegatedPropertyResolver");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(dataFlowValueFactory, "dataFlowValueFactory");
        this.modifiersChecker = modifiersChecker;
        this.identifierChecker = identifierChecker;
        this.dataFlowAnalyzer = dataFlowAnalyzer;
        this.annotationResolver = annotationResolver;
        this.variableTypeAndInitializerResolver = variableTypeAndInitializerResolver;
        this.delegatedPropertyResolver = delegatedPropertyResolver;
        this.languageVersionSettings = languageVersionSettings;
        this.dataFlowValueFactory = dataFlowValueFactory;
    }

    /* JADX WARN: Code duplicated, block: B:39:0x018a  */
    public final Pair<KotlinTypeInfo, VariableDescriptor> process(KtProperty property, ExpressionTypingContext typingContext, LexicalScope scope, ExpressionTypingFacade facade) {
        ExpressionTypingContext expressionTypingContext;
        ExpressionTypingFacade expressionTypingFacade;
        KtProperty ktProperty;
        ExpressionTypingContext expressionTypingContext2;
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo;
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(typingContext, "typingContext");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(facade, "facade");
        ExpressionTypingContext expressionTypingContextReplaceScope = typingContext.replaceContextDependency(ContextDependency.INDEPENDENT).replaceScope(scope);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceScope, "replaceScope(...)");
        ExpressionTypingContext expressionTypingContext3 = expressionTypingContextReplaceScope;
        KtTypeReference ktTypeReferenceMo7393getReceiverTypeReference = property.mo7393getReceiverTypeReference();
        if (ktTypeReferenceMo7393getReceiverTypeReference != null) {
            expressionTypingContext3.trace.report(Errors.LOCAL_EXTENSION_PROPERTY.on(ktTypeReferenceMo7393getReceiverTypeReference));
        }
        KtPropertyAccessor getter = property.getGetter();
        if (getter != null) {
            expressionTypingContext3.trace.report(Errors.LOCAL_VARIABLE_WITH_GETTER.on(getter));
        }
        KtPropertyAccessor setter = property.getSetter();
        if (setter != null) {
            expressionTypingContext3.trace.report(Errors.LOCAL_VARIABLE_WITH_SETTER.on(setter));
        }
        DataFlowInfo dataFlowInfo = expressionTypingContext3.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        InferenceSession inferenceSession = expressionTypingContext3.inferenceSession;
        Intrinsics.checkNotNullExpressionValue(inferenceSession, "inferenceSession");
        BindingTrace trace = expressionTypingContext3.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        VariableDescriptor variableDescriptorResolveLocalVariableDescriptor = resolveLocalVariableDescriptor(scope, property, dataFlowInfo, inferenceSession, trace);
        KtExpression delegateExpression = property.getDelegateExpression();
        if (delegateExpression != null) {
            if (!this.languageVersionSettings.supportsFeature(LanguageFeature.LocalDelegatedProperties)) {
                BindingTrace bindingTrace = expressionTypingContext3.trace;
                DiagnosticFactory1<PsiElement, Pair<LanguageFeature, LanguageVersionSettings>> diagnosticFactory1 = Errors.UNSUPPORTED_FEATURE;
                KtPropertyDelegate delegate = property.getDelegate();
                Intrinsics.checkNotNull(delegate);
                bindingTrace.report(diagnosticFactory1.on(delegate, TuplesKt.to(LanguageFeature.LocalDelegatedProperties, this.languageVersionSettings)));
            }
            if (variableDescriptorResolveLocalVariableDescriptor instanceof VariableDescriptorWithAccessors) {
                DelegatedPropertyResolver delegatedPropertyResolver = this.delegatedPropertyResolver;
                DataFlowInfo dataFlowInfo2 = typingContext.dataFlowInfo;
                Intrinsics.checkNotNullExpressionValue(dataFlowInfo2, "dataFlowInfo");
                VariableDescriptorWithAccessors variableDescriptorWithAccessors = (VariableDescriptorWithAccessors) variableDescriptorResolveLocalVariableDescriptor;
                LexicalScope scope2 = typingContext.scope;
                Intrinsics.checkNotNullExpressionValue(scope2, "scope");
                InferenceSession inferenceSession2 = typingContext.inferenceSession;
                Intrinsics.checkNotNullExpressionValue(inferenceSession2, "inferenceSession");
                BindingTrace trace2 = typingContext.trace;
                Intrinsics.checkNotNullExpressionValue(trace2, "trace");
                expressionTypingContext = expressionTypingContext3;
                expressionTypingFacade = facade;
                delegatedPropertyResolver.resolvePropertyDelegate(dataFlowInfo2, property, variableDescriptorWithAccessors, delegateExpression, scope2, inferenceSession2, trace2);
                VariableAccessorDescriptor getter2 = variableDescriptorWithAccessors.getGetter();
                if (getter2 != null) {
                    BindingTrace trace3 = typingContext.trace;
                    Intrinsics.checkNotNullExpressionValue(trace3, "trace");
                    updateAccessorFlagsFromResolvedCallForDelegatedProperty(getter2, trace3);
                }
                VariableAccessorDescriptor setter2 = variableDescriptorWithAccessors.getSetter();
                if (setter2 != null) {
                    BindingTrace trace4 = typingContext.trace;
                    Intrinsics.checkNotNullExpressionValue(trace4, "trace");
                    updateAccessorFlagsFromResolvedCallForDelegatedProperty(setter2, trace4);
                }
            } else {
                expressionTypingFacade = facade;
                expressionTypingContext = expressionTypingContext3;
            }
        } else {
            expressionTypingContext = expressionTypingContext3;
            expressionTypingFacade = facade;
        }
        KtExpression initializer = property.getInitializer();
        if (initializer != null) {
            KotlinType type = variableDescriptorResolveLocalVariableDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            kotlinTypeInfoNoTypeInfo = expressionTypingFacade.getTypeInfo(initializer, expressionTypingContext.replaceExpectedType(type));
            Intrinsics.checkNotNullExpressionValue(kotlinTypeInfoNoTypeInfo, "getTypeInfo(...)");
            DataFlowInfo dataFlowInfo3 = kotlinTypeInfoNoTypeInfo.getDataFlowInfo();
            KotlinType type2 = kotlinTypeInfoNoTypeInfo.getType();
            if (type2 != null) {
                DataFlowValue dataFlowValueCreateDataFlowValue = this.dataFlowValueFactory.createDataFlowValue(initializer, type2, expressionTypingContext);
                if (!variableDescriptorResolveLocalVariableDescriptor.isVar() && dataFlowValueCreateDataFlowValue.getCanBeBound()) {
                    expressionTypingContext.trace.record(BindingContext.BOUND_INITIALIZER_VALUE, variableDescriptorResolveLocalVariableDescriptor, dataFlowValueCreateDataFlowValue);
                }
                if (property.mo7396getTypeReference() == null) {
                    DataFlowValueFactory dataFlowValueFactory = this.dataFlowValueFactory;
                    BindingContext bindingContext = expressionTypingContext.trace.getContext();
                    Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
                    ktProperty = property;
                    expressionTypingContext2 = expressionTypingContext;
                    kotlinTypeInfoNoTypeInfo = kotlinTypeInfoNoTypeInfo.replaceDataFlowInfo(dataFlowInfo3.assign(dataFlowValueFactory.createDataFlowValueForProperty(ktProperty, variableDescriptorResolveLocalVariableDescriptor, bindingContext, DescriptorUtils.getContainingModuleOrNull(scope.getOwnerDescriptor())), dataFlowValueCreateDataFlowValue, this.languageVersionSettings));
                } else {
                    ktProperty = property;
                    expressionTypingContext2 = expressionTypingContext;
                }
            } else {
                ktProperty = property;
                expressionTypingContext2 = expressionTypingContext;
            }
        } else {
            ktProperty = property;
            expressionTypingContext2 = expressionTypingContext;
            kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContext2);
        }
        checkLocalVariableDeclaration(expressionTypingContext2, variableDescriptorResolveLocalVariableDescriptor, ktProperty);
        return new Pair<>(kotlinTypeInfoNoTypeInfo.replaceType(this.dataFlowAnalyzer.checkStatementType(ktProperty, expressionTypingContext2)), variableDescriptorResolveLocalVariableDescriptor);
    }

    private final void checkLocalVariableDeclaration(ExpressionTypingContext context, VariableDescriptor descriptor, KtProperty ktProperty) {
        ExpressionTypingUtils.checkVariableShadowing(context.scope, context.trace, descriptor);
        KtProperty ktProperty2 = ktProperty;
        this.modifiersChecker.withTrace(context.trace).checkModifiersForLocalDeclaration(ktProperty2, descriptor);
        IdentifierChecker identifierChecker = this.identifierChecker;
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        identifierChecker.checkDeclaration(ktProperty2, trace);
        LateinitModifierApplicabilityChecker lateinitModifierApplicabilityChecker = LateinitModifierApplicabilityChecker.INSTANCE;
        BindingTrace trace2 = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace2, "trace");
        lateinitModifierApplicabilityChecker.checkLateinitModifierApplicability(trace2, ktProperty, descriptor, this.languageVersionSettings);
    }

    private final VariableDescriptor resolveLocalVariableDescriptor(LexicalScope scope, KtVariableDeclaration variable, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession, BindingTrace trace) {
        LocalVariableDescriptor localVariableDescriptor;
        KotlinType kotlinType;
        DeclarationDescriptor ownerDescriptor = scope.getOwnerDescriptor();
        if (KtPsiUtil.isScriptDeclaration(variable)) {
            PropertyDescriptorImpl propertyDescriptorImplCreate = PropertyDescriptorImpl.create(ownerDescriptor, this.annotationResolver.resolveAnnotationsWithArguments(scope, variable.getModifierList(), trace), Modality.FINAL, DescriptorVisibilities.INTERNAL, variable.isVar(), KtPsiUtil.safeName(variable.get$name()), CallableMemberDescriptor.Kind.DECLARATION, KotlinSourceElementKt.toSourceElement(variable), false, false, false, false, false, (variable instanceof KtProperty) && ((KtProperty) variable).hasDelegate());
            Intrinsics.checkNotNullExpressionValue(propertyDescriptorImplCreate, "create(...)");
            PropertyDescriptorImpl propertyDescriptorImpl = propertyDescriptorImplCreate;
            KotlinType kotlinTypeResolveType = this.variableTypeAndInitializerResolver.resolveType(propertyDescriptorImpl, scope, variable, dataFlowInfo, inferenceSession, trace, true);
            Intrinsics.checkNotNull(ownerDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ScriptDescriptor");
            ReceiverParameterDescriptor thisAsReceiverParameter = ((ScriptDescriptor) ownerDescriptor).getThisAsReceiverParameter();
            Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
            propertyDescriptorImplCreate.setType(kotlinTypeResolveType, CollectionsKt.emptyList(), thisAsReceiverParameter, null, CollectionsKt.emptyList());
            initializeWithDefaultGetterSetter(propertyDescriptorImplCreate);
            trace.record(BindingContext.VARIABLE, variable, propertyDescriptorImplCreate);
            kotlinType = kotlinTypeResolveType;
            localVariableDescriptor = propertyDescriptorImpl;
        } else {
            LocalVariableDescriptor localVariableDescriptorResolveLocalVariableDescriptorWithType$frontend = resolveLocalVariableDescriptorWithType$frontend(scope, variable, null, trace);
            localVariableDescriptor = localVariableDescriptorResolveLocalVariableDescriptorWithType$frontend;
            KotlinType kotlinTypeResolveType2 = this.variableTypeAndInitializerResolver.resolveType(localVariableDescriptor, scope, variable, dataFlowInfo, inferenceSession, trace, true);
            localVariableDescriptorResolveLocalVariableDescriptorWithType$frontend.setOutType(kotlinTypeResolveType2);
            kotlinType = kotlinTypeResolveType2;
        }
        if (inferenceSession instanceof BuilderInferenceSession) {
            ((BuilderInferenceSession) inferenceSession).addExpression(variable);
        }
        this.variableTypeAndInitializerResolver.setConstantForVariableIfNeeded(localVariableDescriptor, scope, variable, dataFlowInfo, kotlinType, inferenceSession, trace);
        ForceResolveUtil.forceResolveAllContents(kotlinType.getAnnotations());
        return localVariableDescriptor;
    }

    private final void initializeWithDefaultGetterSetter(PropertyDescriptorImpl propertyDescriptor) {
        PropertyGetterDescriptorImpl getter = propertyDescriptor.getGetter();
        if (getter == null && !DescriptorVisibilities.isPrivate(propertyDescriptor.getVisibility())) {
            getter = DescriptorFactory.createDefaultGetter(propertyDescriptor, Annotations.INSTANCE.getEMPTY());
            getter.initialize(propertyDescriptor.getType());
        }
        PropertySetterDescriptorImpl setter = propertyDescriptor.getSetter();
        if (setter == null && propertyDescriptor.isVar()) {
            setter = DescriptorFactory.createDefaultSetter(propertyDescriptor, Annotations.INSTANCE.getEMPTY(), Annotations.INSTANCE.getEMPTY());
        }
        propertyDescriptor.initialize(getter, setter);
    }

    public final LocalVariableDescriptor resolveLocalVariableDescriptorWithType$frontend(LexicalScope scope, KtVariableDeclaration variable, KotlinType type, BindingTrace trace) {
        Name nameSafeName;
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(trace, "trace");
        boolean z = (variable instanceof KtProperty) && ((KtProperty) variable).hasDelegate();
        boolean zHasModifier = variable.hasModifier(KtTokens.LATEINIT_KEYWORD);
        DeclarationDescriptor ownerDescriptor = scope.getOwnerDescriptor();
        Annotations annotationsResolveAnnotationsWithArguments = this.annotationResolver.resolveAnnotationsWithArguments(scope, variable.getModifierList(), trace);
        if (UnderscoreUtilKt.isSingleUnderscore(variable)) {
            nameSafeName = Name.special("<underscore local var>");
        } else {
            nameSafeName = KtPsiUtil.safeName(variable.get$name());
        }
        LocalVariableDescriptor localVariableDescriptor = new LocalVariableDescriptor(ownerDescriptor, annotationsResolveAnnotationsWithArguments, nameSafeName, type, variable.isVar(), z, zHasModifier, KotlinSourceElementKt.toSourceElement(variable));
        trace.record(BindingContext.VARIABLE, variable, localVariableDescriptor);
        return localVariableDescriptor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void updateAccessorFlagsFromResolvedCallForDelegatedProperty(VariableAccessorDescriptor variableAccessorDescriptor, BindingTrace bindingTrace) {
        if (variableAccessorDescriptor instanceof FunctionDescriptorImpl) {
            ResolvedCall resolvedCall = (ResolvedCall) bindingTrace.getContext().get(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, variableAccessorDescriptor);
            FunctionDescriptor functionDescriptor = resolvedCall != null ? (FunctionDescriptor) resolvedCall.getResultingDescriptor() : null;
            if (functionDescriptor != null) {
                ((FunctionDescriptorImpl) variableAccessorDescriptor).setSuspend(functionDescriptor.isSuspend());
            }
        }
    }
}
