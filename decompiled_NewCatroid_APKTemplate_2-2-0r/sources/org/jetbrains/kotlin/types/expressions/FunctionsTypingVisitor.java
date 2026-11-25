package org.jetbrains.kotlin.types.expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTreeVisitor;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.ReservedCheckingKt;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.resolve.AnnotationResolver;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DeclarationsCheckerBuilder;
import org.jetbrains.kotlin.resolve.FunctionDescriptorResolver;
import org.jetbrains.kotlin.resolve.FunctionDescriptorUtil;
import org.jetbrains.kotlin.resolve.IdentifierChecker;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.tower.KotlinResolutionCallbacksImpl;
import org.jetbrains.kotlin.resolve.checkers.TrailingCommaChecker;
import org.jetbrains.kotlin.resolve.checkers.UnderscoreChecker;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElementKt;
import org.jetbrains.kotlin.types.CommonSupertypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: FunctionsTypingVisitor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u001e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\nH\u0002J(\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J$\u0010\u001c\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u001d\u001a\u0004\u0018\u00010\fH\u0002J,\u0010\u001e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001f\u001a\u0004\u0018\u00010\fH\u0002J\u0018\u0010 \u001a\u00020!2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u001a\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010$\u001a\u00020#2\u0006\u0010\u0007\u001a\u00020%2\u0006\u0010&\u001a\u00020\u0017H\u0016J(\u0010$\u001a\u00020#2\u0006\u0010\u0007\u001a\u00020%2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010'\u001a\u00020\u001b2\b\u0010(\u001a\u0004\u0018\u00010)J\f\u0010*\u001a\u00020\u001b*\u00020\fH\u0002J\f\u0010+\u001a\u00020\u001b*\u00020\fH\u0002¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/FunctionsTypingVisitor;", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingVisitor;", "facade", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingInternals;", "(Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingInternals;)V", "checkTypesForReturnStatements", "", "function", "Lorg/jetbrains/kotlin/psi/KtDeclarationWithBody;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "actualReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "collectReturns", "", "Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "", "functionLiteral", "Lorg/jetbrains/kotlin/psi/KtFunctionLiteral;", "computeReturnType", "expression", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "functionTypeExpected", "", "computeReturnTypeBasedOnReturnExpressions", "typeOfBodyExpression", "computeUnsafeReturnType", "expectedReturnType", "createFunctionLiteralDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/AnonymousFunctionDescriptor;", "visitLambdaExpression", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "visitNamedFunction", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "data", "isDeclaration", "statementScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalWritableScope;", "isBuiltinFunctionalType", "isSuspendFunctionType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionsTypingVisitor extends ExpressionTypingVisitor {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionsTypingVisitor(ExpressionTypingInternals facade) {
        super(facade);
        Intrinsics.checkNotNullParameter(facade, "facade");
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitNamedFunction(KtNamedFunction function, ExpressionTypingContext data) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(data, "data");
        return visitNamedFunction(function, data, false, null);
    }

    public final KotlinTypeInfo visitNamedFunction(KtNamedFunction function, ExpressionTypingContext context, boolean isDeclaration, LexicalWritableScope statementScope) {
        SimpleFunctionDescriptor simpleFunctionDescriptorResolveFunctionExpressionDescriptor;
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!isDeclaration) {
            if (!function.getTypeParameters().isEmpty()) {
                context.trace.report(Errors.TYPE_PARAMETERS_NOT_ALLOWED.on(function));
            }
            if (function.get$name() != null) {
                BindingTrace bindingTrace = context.trace;
                DiagnosticFactory0<PsiElement> diagnosticFactory0 = Errors.ANONYMOUS_FUNCTION_WITH_NAME;
                PsiElement nameIdentifier = function.mo7392getNameIdentifier();
                Intrinsics.checkNotNull(nameIdentifier);
                bindingTrace.report(diagnosticFactory0.on(nameIdentifier));
            }
            for (KtParameter ktParameter : function.getValueParameters()) {
                if (ktParameter.hasDefaultValue()) {
                    context.trace.report(Errors.ANONYMOUS_FUNCTION_PARAMETER_WITH_DEFAULT_VALUE.on(ktParameter));
                }
                if (ktParameter.isVarArg()) {
                    context.trace.report(Errors.USELESS_VARARG_ON_PARAMETER.on(ktParameter));
                }
            }
        }
        if (isDeclaration) {
            FunctionDescriptorResolver functionDescriptorResolver = this.components.functionDescriptorResolver;
            DeclarationDescriptor ownerDescriptor = context.scope.getOwnerDescriptor();
            LexicalScope scope = context.scope;
            Intrinsics.checkNotNullExpressionValue(scope, "scope");
            BindingTrace trace = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            DataFlowInfo dataFlowInfo = context.dataFlowInfo;
            Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
            simpleFunctionDescriptorResolveFunctionExpressionDescriptor = functionDescriptorResolver.resolveFunctionDescriptor(ownerDescriptor, scope, function, trace, dataFlowInfo, context.inferenceSession);
            Intrinsics.checkNotNull(statementScope);
            statementScope.addFunctionDescriptor(simpleFunctionDescriptorResolveFunctionExpressionDescriptor);
        } else {
            FunctionDescriptorResolver functionDescriptorResolver2 = this.components.functionDescriptorResolver;
            DeclarationDescriptor ownerDescriptor2 = context.scope.getOwnerDescriptor();
            LexicalScope scope2 = context.scope;
            Intrinsics.checkNotNullExpressionValue(scope2, "scope");
            BindingTrace trace2 = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace2, "trace");
            DataFlowInfo dataFlowInfo2 = context.dataFlowInfo;
            Intrinsics.checkNotNullExpressionValue(dataFlowInfo2, "dataFlowInfo");
            KotlinType expectedType = context.expectedType;
            Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
            simpleFunctionDescriptorResolveFunctionExpressionDescriptor = functionDescriptorResolver2.resolveFunctionExpressionDescriptor(ownerDescriptor2, scope2, function, trace2, dataFlowInfo2, expectedType, context.inferenceSession);
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor = simpleFunctionDescriptorResolveFunctionExpressionDescriptor;
        ForceResolveUtil.forceResolveAllContents(simpleFunctionDescriptor.getAnnotations());
        SimpleFunctionDescriptor simpleFunctionDescriptor2 = simpleFunctionDescriptor;
        LexicalScope functionInnerScope = FunctionDescriptorUtil.getFunctionInnerScope(context.scope, simpleFunctionDescriptor2, context.trace, this.components.overloadChecker);
        Intrinsics.checkNotNullExpressionValue(functionInnerScope, "getFunctionInnerScope(...)");
        if (!function.hasDeclaredReturnType() && !function.hasBlockBody()) {
            ForceResolveUtil.forceResolveAllContents(simpleFunctionDescriptor.getReturnType());
        } else {
            this.components.expressionTypingServices.checkFunctionReturnType(functionInnerScope, function, simpleFunctionDescriptor2, context.dataFlowInfo, null, context.trace, context);
        }
        ValueParameterResolver valueParameterResolver = this.components.valueParameterResolver;
        List<KtParameter> valueParameters = function.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> valueParameters2 = simpleFunctionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
        DataFlowInfo dataFlowInfo3 = context.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo3, "dataFlowInfo");
        BindingTrace trace3 = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace3, "trace");
        valueParameterResolver.resolveValueParameters(valueParameters, valueParameters2, functionInnerScope, dataFlowInfo3, trace3, context.inferenceSession);
        KtNamedFunction ktNamedFunction = function;
        this.components.modifiersChecker.withTrace(context.trace).checkModifiersForLocalDeclaration(ktNamedFunction, simpleFunctionDescriptor);
        IdentifierChecker identifierChecker = this.components.identifierChecker;
        BindingTrace trace4 = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace4, "trace");
        identifierChecker.checkDeclaration(ktNamedFunction, trace4);
        DeclarationsCheckerBuilder declarationsCheckerBuilder = this.components.declarationsCheckerBuilder;
        BindingTrace trace5 = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace5, "trace");
        declarationsCheckerBuilder.withTrace(trace5).checkFunction(function, simpleFunctionDescriptor);
        if (isDeclaration) {
            ExpressionTypingContext expressionTypingContext = context;
            return TypeInfoFactoryKt.createTypeInfo(this.components.dataFlowAnalyzer.checkStatementType(function, expressionTypingContext), expressionTypingContext);
        }
        boolean zSupportsFeature = this.components.languageVersionSettings.supportsFeature(LanguageFeature.NewInference);
        KotlinBuiltIns builtIns = this.components.builtIns;
        Intrinsics.checkNotNullExpressionValue(builtIns, "builtIns");
        KotlinType kotlinTypeCreateFunctionType$default = FunctionsTypingVisitorKt.createFunctionType$default(simpleFunctionDescriptor, builtIns, false, false, 4, null);
        if (zSupportsFeature) {
            KotlinType expectedType2 = context.expectedType;
            Intrinsics.checkNotNullExpressionValue(expectedType2, "expectedType");
            if (!TypeUtilsKt.contains(expectedType2, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.types.expressions.FunctionsTypingVisitor.visitNamedFunction.2
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it == TypeUtils.NO_EXPECTED_TYPE);
                }
            })) {
                KotlinBuiltIns builtIns2 = this.components.builtIns;
                Intrinsics.checkNotNullExpressionValue(builtIns2, "builtIns");
                this.components.dataFlowAnalyzer.checkType(FunctionsTypingVisitorKt.createFunctionType(simpleFunctionDescriptor, builtIns2, false, true), function, context);
            }
            return TypeInfoFactoryKt.createTypeInfo(kotlinTypeCreateFunctionType$default, context);
        }
        KotlinTypeInfo kotlinTypeInfoCreateCheckedTypeInfo = this.components.dataFlowAnalyzer.createCheckedTypeInfo(kotlinTypeCreateFunctionType$default, context, function);
        Intrinsics.checkNotNull(kotlinTypeInfoCreateCheckedTypeInfo);
        return kotlinTypeInfoCreateCheckedTypeInfo;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitLambdaExpression(KtLambdaExpression expression, ExpressionTypingContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!this.components.languageVersionSettings.supportsFeature(LanguageFeature.YieldIsNoMoreReserved)) {
            BindingTrace trace = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace, "trace");
            ReservedCheckingKt.checkReservedYieldBeforeLambda(expression, trace);
        }
        if (!expression.getFunctionLiteral().hasBody()) {
            return null;
        }
        KotlinType expectedType = context.expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
        boolean zIsBuiltinFunctionalType = isBuiltinFunctionalType(expectedType);
        boolean zIsSuspendFunctionType = isSuspendFunctionType(expectedType);
        AnonymousFunctionDescriptor anonymousFunctionDescriptorCreateFunctionLiteralDescriptor = createFunctionLiteralDescriptor(expression, context);
        List<KtParameter> valueParameters = expression.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        for (KtParameter ktParameter : valueParameters) {
            IdentifierChecker identifierChecker = this.components.identifierChecker;
            Intrinsics.checkNotNull(ktParameter);
            BindingTrace trace2 = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace2, "trace");
            identifierChecker.checkDeclaration(ktParameter, trace2);
            BindingTrace trace3 = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace3, "trace");
            LanguageVersionSettings languageVersionSettings = this.components.languageVersionSettings;
            Intrinsics.checkNotNullExpressionValue(languageVersionSettings, "languageVersionSettings");
            UnderscoreChecker.INSTANCE.checkNamed(ktParameter, trace3, languageVersionSettings, true);
        }
        KtParameterList valueParameterList = expression.getFunctionLiteral().getValueParameterList();
        if ((valueParameterList != null ? (KotlinPlaceHolderStub) valueParameterList.getStub() : null) == null) {
            TrailingCommaChecker trailingCommaChecker = TrailingCommaChecker.INSTANCE;
            PsiElement trailingComma = valueParameterList != null ? valueParameterList.getTrailingComma() : null;
            BindingTrace trace4 = context.trace;
            Intrinsics.checkNotNullExpressionValue(trace4, "trace");
            LanguageVersionSettings languageVersionSettings2 = context.languageVersionSettings;
            Intrinsics.checkNotNullExpressionValue(languageVersionSettings2, "languageVersionSettings");
            trailingCommaChecker.check(trailingComma, trace4, languageVersionSettings2);
        }
        anonymousFunctionDescriptorCreateFunctionLiteralDescriptor.setReturnType(computeReturnType(expression, context, anonymousFunctionDescriptorCreateFunctionLiteralDescriptor, zIsBuiltinFunctionalType));
        KotlinBuiltIns builtIns = this.components.builtIns;
        Intrinsics.checkNotNullExpressionValue(builtIns, "builtIns");
        KotlinType kotlinTypeCreateFunctionType$default = FunctionsTypingVisitorKt.createFunctionType$default(anonymousFunctionDescriptorCreateFunctionLiteralDescriptor, builtIns, zIsSuspendFunctionType, false, 4, null);
        Intrinsics.checkNotNull(kotlinTypeCreateFunctionType$default);
        KotlinType kotlinTypeInterceptType = this.components.typeResolutionInterceptor.interceptType(expression, context, kotlinTypeCreateFunctionType$default);
        if (context.inferenceSession instanceof BuilderInferenceSession) {
            ((BuilderInferenceSession) context.inferenceSession).addExpression(expression);
        }
        if (zIsBuiltinFunctionalType) {
            return TypeInfoFactoryKt.createTypeInfo(kotlinTypeInterceptType, context);
        }
        return this.components.dataFlowAnalyzer.createCheckedTypeInfo(kotlinTypeInterceptType, context, expression);
    }

    private final AnonymousFunctionDescriptor createFunctionLiteralDescriptor(KtLambdaExpression expression, ExpressionTypingContext context) {
        KtFunctionLiteral functionLiteral = expression.getFunctionLiteral();
        Intrinsics.checkNotNullExpressionValue(functionLiteral, "getFunctionLiteral(...)");
        DeclarationDescriptor ownerDescriptor = context.scope.getOwnerDescriptor();
        AnnotationResolver annotationResolver = this.components.annotationResolver;
        LexicalScope scope = context.scope;
        Intrinsics.checkNotNullExpressionValue(scope, "scope");
        List<KtAnnotationEntry> annotationEntries = KtPsiUtilKt.getAnnotationEntries(expression);
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        Annotations annotationsResolveAnnotationsWithArguments = annotationResolver.resolveAnnotationsWithArguments(scope, annotationEntries, trace);
        CallableMemberDescriptor.Kind kind = CallableMemberDescriptor.Kind.DECLARATION;
        SourceElement sourceElement = KotlinSourceElementKt.toSourceElement(functionLiteral);
        KotlinType expectedType = context.expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
        AnonymousFunctionDescriptor anonymousFunctionDescriptorInterceptFunctionLiteralDescriptor = this.facade.getComponents().typeResolutionInterceptor.interceptFunctionLiteralDescriptor(expression, context, new AnonymousFunctionDescriptor(ownerDescriptor, annotationsResolveAnnotationsWithArguments, kind, sourceElement, isSuspendFunctionType(expectedType)));
        FunctionDescriptorResolver functionDescriptorResolver = this.components.functionDescriptorResolver;
        DeclarationDescriptor ownerDescriptor2 = context.scope.getOwnerDescriptor();
        LexicalScope scope2 = context.scope;
        Intrinsics.checkNotNullExpressionValue(scope2, "scope");
        BindingTrace trace2 = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace2, "trace");
        KotlinType expectedType2 = context.expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType2, "expectedType");
        DataFlowInfo dataFlowInfo = context.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        functionDescriptorResolver.initializeFunctionDescriptorAndExplicitReturnType(ownerDescriptor2, scope2, functionLiteral, anonymousFunctionDescriptorInterceptFunctionLiteralDescriptor, trace2, expectedType2, dataFlowInfo, context.inferenceSession);
        Iterator<ValueParameterDescriptor> it = anonymousFunctionDescriptorInterceptFunctionLiteralDescriptor.getValueParameters().iterator();
        while (it.hasNext()) {
            ForceResolveUtil.forceResolveAllContents(it.next().getAnnotations());
        }
        BindingContextUtils.recordFunctionDeclarationToDescriptor(context.trace, functionLiteral, anonymousFunctionDescriptorInterceptFunctionLiteralDescriptor);
        return anonymousFunctionDescriptorInterceptFunctionLiteralDescriptor;
    }

    private final boolean isBuiltinFunctionalType(KotlinType kotlinType) {
        return !TypeUtils.noExpectedType(kotlinType) && FunctionTypesKt.isBuiltinFunctionalType(kotlinType);
    }

    private final boolean isSuspendFunctionType(KotlinType kotlinType) {
        return !TypeUtils.noExpectedType(kotlinType) && FunctionTypesKt.isSuspendFunctionType(kotlinType);
    }

    private final KotlinType computeReturnType(KtLambdaExpression expression, ExpressionTypingContext context, SimpleFunctionDescriptorImpl functionDescriptor, boolean functionTypeExpected) {
        KotlinType returnTypeFromFunctionType;
        if (functionTypeExpected) {
            KotlinType expectedType = context.expectedType;
            Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
            returnTypeFromFunctionType = FunctionTypesKt.getReturnTypeFromFunctionType(expectedType);
        } else {
            returnTypeFromFunctionType = null;
        }
        KotlinType kotlinTypeComputeUnsafeReturnType = computeUnsafeReturnType(expression, context, functionDescriptor, returnTypeFromFunctionType);
        if (!expression.getFunctionLiteral().hasDeclaredReturnType() && functionTypeExpected) {
            Intrinsics.checkNotNull(returnTypeFromFunctionType);
            if (!TypeUtils.noExpectedType(returnTypeFromFunctionType) && KotlinBuiltIns.isUnit(returnTypeFromFunctionType)) {
                SimpleType unitType = this.components.builtIns.getUnitType();
                Intrinsics.checkNotNullExpressionValue(unitType, "getUnitType(...)");
                return unitType;
            }
        }
        if (kotlinTypeComputeUnsafeReturnType != null) {
            return kotlinTypeComputeUnsafeReturnType;
        }
        SimpleType CANNOT_INFER_FUNCTION_PARAM_TYPE = TypeUtils.CANNOT_INFER_FUNCTION_PARAM_TYPE;
        Intrinsics.checkNotNullExpressionValue(CANNOT_INFER_FUNCTION_PARAM_TYPE, "CANNOT_INFER_FUNCTION_PARAM_TYPE");
        return CANNOT_INFER_FUNCTION_PARAM_TYPE;
    }

    private final KotlinType computeUnsafeReturnType(KtLambdaExpression expression, ExpressionTypingContext context, SimpleFunctionDescriptorImpl functionDescriptor, KotlinType expectedReturnType) {
        KtFunctionLiteral functionLiteral = expression.getFunctionLiteral();
        Intrinsics.checkNotNullExpressionValue(functionLiteral, "getFunctionLiteral(...)");
        if (expectedReturnType == null) {
            SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
            Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
            expectedReturnType = NO_EXPECTED_TYPE;
        }
        LexicalScope functionInnerScope = FunctionDescriptorUtil.getFunctionInnerScope(context.scope, functionDescriptor, context.trace, this.components.overloadChecker);
        Intrinsics.checkNotNullExpressionValue(functionInnerScope, "getFunctionInnerScope(...)");
        ExpressionTypingContext expressionTypingContextReplaceExpectedType = context.replaceScope(functionInnerScope).replaceExpectedType(expectedReturnType);
        Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceExpectedType, "replaceExpectedType(...)");
        ExpressionTypingContext expressionTypingContext = expressionTypingContextReplaceExpectedType;
        context.trace.record(BindingContext.EXPECTED_RETURN_TYPE, functionLiteral, expectedReturnType);
        KotlinResolutionCallbacksImpl.LambdaInfo lambdaInfo = (KotlinResolutionCallbacksImpl.LambdaInfo) context.trace.get(BindingContext.NEW_INFERENCE_LAMBDA_INFO, expression.getFunctionLiteral());
        if (lambdaInfo == null && context.languageVersionSettings.supportsFeature(LanguageFeature.NewInference)) {
            ExpressionTypingContext expressionTypingContextReplaceContextDependency = expressionTypingContext.replaceContextDependency(ContextDependency.INDEPENDENT);
            Intrinsics.checkNotNullExpressionValue(expressionTypingContextReplaceContextDependency, "replaceContextDependency(...)");
            expressionTypingContext = expressionTypingContextReplaceContextDependency;
        }
        ExpressionTypingServices expressionTypingServices = this.components.expressionTypingServices;
        KtBlockExpression bodyExpression = functionLiteral.getBodyExpression();
        Intrinsics.checkNotNull(bodyExpression);
        KotlinTypeInfo blockReturnedType = expressionTypingServices.getBlockReturnedType(bodyExpression, CoercionStrategy.COERCION_TO_UNIT, expressionTypingContext);
        Intrinsics.checkNotNullExpressionValue(blockReturnedType, "getBlockReturnedType(...)");
        KotlinType type = blockReturnedType.getType();
        if (lambdaInfo != null) {
            lambdaInfo.getLastExpressionInfo().setDataFlowInfoAfter(blockReturnedType.getDataFlowInfo());
        }
        return computeReturnTypeBasedOnReturnExpressions(functionLiteral, context, type);
    }

    private final KotlinType computeReturnTypeBasedOnReturnExpressions(KtFunctionLiteral functionLiteral, ExpressionTypingContext context, KotlinType typeOfBodyExpression) {
        KotlinType type;
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        Collection<KtReturnExpression> collectionCollectReturns = collectReturns(functionLiteral, trace);
        Iterator<KtReturnExpression> it = collectionCollectReturns.iterator();
        boolean z = false;
        while (it.hasNext()) {
            KtExpression returnedExpression = it.next().getReturnedExpression();
            if (returnedExpression == null) {
                z = true;
            } else {
                Intrinsics.checkNotNull(arrayListNewArrayList);
                CollectionsKt.addIfNotNull(arrayListNewArrayList, context.trace.getType(returnedExpression));
            }
        }
        if (z) {
            Iterator<KtReturnExpression> it2 = collectionCollectReturns.iterator();
            while (it2.hasNext()) {
                KtExpression returnedExpression2 = it2.next().getReturnedExpression();
                if (returnedExpression2 != null && ((type = context.trace.getType(returnedExpression2)) == null || !KotlinBuiltIns.isUnit(type))) {
                    context.trace.report(Errors.RETURN_TYPE_MISMATCH.on(returnedExpression2, this.components.builtIns.getUnitType()));
                }
            }
            return this.components.builtIns.getUnitType();
        }
        Intrinsics.checkNotNull(arrayListNewArrayList);
        ArrayList arrayList = arrayListNewArrayList;
        CollectionsKt.addIfNotNull(arrayList, typeOfBodyExpression);
        if (arrayListNewArrayList.isEmpty()) {
            return null;
        }
        ArrayList<KotlinType> arrayList2 = arrayListNewArrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            for (KotlinType kotlinType : arrayList2) {
                Intrinsics.checkNotNull(kotlinType);
                if (TypeUtilsKt.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.types.expressions.FunctionsTypingVisitor$computeReturnTypeBasedOnReturnExpressions$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(UnwrappedType it3) {
                        Intrinsics.checkNotNullParameter(it3, "it");
                        return Boolean.valueOf(it3.getConstructor() instanceof TypeVariableTypeConstructor);
                    }
                })) {
                    return null;
                }
            }
        }
        return CommonSupertypes.commonSupertype(arrayList);
    }

    private final Collection<KtReturnExpression> collectReturns(KtFunctionLiteral functionLiteral, BindingTrace trace) {
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        KtBlockExpression bodyExpression = functionLiteral.getBodyExpression();
        if (bodyExpression != null) {
        }
        Intrinsics.checkNotNull(arrayListNewArrayList);
        ArrayList arrayList = new ArrayList();
        for (Object obj : arrayListNewArrayList) {
            KtSimpleNameExpression targetLabel = ((KtReturnExpression) obj).getTargetLabel();
            if (Intrinsics.areEqual(targetLabel != null ? (PsiElement) trace.get(BindingContext.LABEL_TARGET, targetLabel) : null, functionLiteral)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final void checkTypesForReturnStatements(KtDeclarationWithBody function, BindingTrace trace, KotlinType actualReturnType) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(actualReturnType, "actualReturnType");
        if (function.hasBlockBody()) {
            return;
        }
        if (!((function instanceof KtNamedFunction) && ((KtNamedFunction) function).mo7396getTypeReference() == null) && (!(function instanceof KtPropertyAccessor) || ((KtPropertyAccessor) function).getReturnTypeReference() == null)) {
            return;
        }
        for (KtReturnExpression ktReturnExpression : collectReturns(function, trace)) {
            KtExpression returnedExpression = ktReturnExpression.getReturnedExpression();
            if (returnedExpression == null) {
                if (!TypeUtilsKt.isUnit(actualReturnType)) {
                    trace.report(Errors.RETURN_TYPE_MISMATCH.on(ktReturnExpression, actualReturnType));
                }
            } else {
                KotlinType type = trace.getType(returnedExpression);
                if (type != null && !KotlinTypeChecker.DEFAULT.isSubtypeOf(type, actualReturnType)) {
                    trace.report(Errors.TYPE_MISMATCH.on(returnedExpression, type, actualReturnType));
                }
            }
        }
    }

    private final List<KtReturnExpression> collectReturns(final KtDeclarationWithBody function, final BindingTrace trace) {
        KtExpression bodyExpression = function.getBodyExpression();
        if (bodyExpression == null) {
            return kotlin.collections.CollectionsKt.emptyList();
        }
        final ArrayList arrayList = new ArrayList();
        bodyExpression.accept(new KtTreeVisitor<Boolean>() { // from class: org.jetbrains.kotlin.types.expressions.FunctionsTypingVisitor.collectReturns.3
            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public /* bridge */ /* synthetic */ Void visitAnonymousInitializer(KtAnonymousInitializer ktAnonymousInitializer, Object obj) {
                return visitAnonymousInitializer(ktAnonymousInitializer, ((Boolean) obj).booleanValue());
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public /* bridge */ /* synthetic */ Void visitNamedFunction(KtNamedFunction ktNamedFunction, Object obj) {
                return visitNamedFunction(ktNamedFunction, ((Boolean) obj).booleanValue());
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public /* bridge */ /* synthetic */ Void visitPropertyAccessor(KtPropertyAccessor ktPropertyAccessor, Object obj) {
                return visitPropertyAccessor(ktPropertyAccessor, ((Boolean) obj).booleanValue());
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public /* bridge */ /* synthetic */ Void visitReturnExpression(KtReturnExpression ktReturnExpression, Object obj) {
                return visitReturnExpression(ktReturnExpression, ((Boolean) obj).booleanValue());
            }

            public Void visitReturnExpression(KtReturnExpression expression, boolean insideActualFunction) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                KtSimpleNameExpression targetLabel = expression.getTargetLabel();
                PsiElement psiElement = targetLabel != null ? (PsiElement) trace.get(BindingContext.LABEL_TARGET, targetLabel) : null;
                if (Intrinsics.areEqual(psiElement, function) || (psiElement == null && insideActualFunction)) {
                    arrayList.add(expression);
                }
                return (Void) super.visitReturnExpression(expression, Boolean.valueOf(insideActualFunction));
            }

            public Void visitNamedFunction(KtNamedFunction function2, boolean data) {
                Intrinsics.checkNotNullParameter(function2, "function");
                return (Void) super.visitNamedFunction(function2, false);
            }

            public Void visitPropertyAccessor(KtPropertyAccessor accessor, boolean data) {
                Intrinsics.checkNotNullParameter(accessor, "accessor");
                return (Void) super.visitPropertyAccessor(accessor, false);
            }

            public Void visitAnonymousInitializer(KtAnonymousInitializer initializer, boolean data) {
                Intrinsics.checkNotNullParameter(initializer, "initializer");
                return (Void) super.visitAnonymousInitializer(initializer, false);
            }
        }, true);
        return arrayList;
    }
}
