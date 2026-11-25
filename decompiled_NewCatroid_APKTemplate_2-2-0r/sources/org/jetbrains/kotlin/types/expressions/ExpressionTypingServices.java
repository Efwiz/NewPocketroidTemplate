package org.jetbrains.kotlin.types.expressions;

import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.AbstractFilteringTrace;
import org.jetbrains.kotlin.resolve.AnnotationChecker;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.FunctionDescriptorUtil;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.StatementFilterKt;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.tower.KotlinResolutionCallbacksImpl;
import org.jetbrains.kotlin.resolve.calls.tower.LambdaContextInfo;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.resolve.scopes.LocalRedeclarationChecker;
import org.jetbrains.kotlin.resolve.scopes.TraceBasedLocalRedeclarationChecker;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: loaded from: classes6.dex */
public class ExpressionTypingServices {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final AnnotationChecker annotationChecker;
    private final ExpressionTypingComponents expressionTypingComponents;
    private final ExpressionTypingFacade expressionTypingFacade;
    private final StatementFilter statementFilter;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 12 || i == 19 || i == 28 || i == 31 || i == 42 || i == 46 || i == 52 || i == 53) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 12 || i == 19 || i == 28 || i == 31 || i == 42 || i == 46 || i == 52 || i == 53) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "annotationChecker";
                break;
            case 2:
                objArr[0] = "statementFilter";
                break;
            case 3:
                objArr[0] = "facade";
                break;
            case 4:
            case 5:
            case 12:
            case 19:
            case 28:
            case 31:
            case 42:
            case 46:
            case 52:
            case 53:
                objArr[0] = "org/jetbrains/kotlin/types/expressions/ExpressionTypingServices";
                break;
            case 6:
            case 13:
            case 20:
            case 32:
            case 59:
                objArr[0] = "scope";
                break;
            case 7:
            case 14:
            case 21:
            case 29:
            case 33:
            case 43:
                objArr[0] = "expression";
                break;
            case 8:
            case 15:
            case 22:
            case 34:
                objArr[0] = "expectedType";
                break;
            case 9:
            case 16:
            case 23:
            case 35:
            case 41:
            case 49:
                objArr[0] = "dataFlowInfo";
                break;
            case 10:
            case 17:
            case 24:
            case 36:
                objArr[0] = "inferenceSession";
                break;
            case 11:
            case 18:
            case 25:
            case 37:
            case 47:
                objArr[0] = "trace";
                break;
            case 26:
                objArr[0] = "contextExpression";
                break;
            case 27:
                objArr[0] = "contextDependency";
                break;
            case 30:
                objArr[0] = "resolutionContext";
                break;
            case 38:
                objArr[0] = "functionInnerScope";
                break;
            case 39:
            case 50:
            case 58:
                objArr[0] = "function";
                break;
            case 40:
            case 51:
                objArr[0] = "functionDescriptor";
                break;
            case 44:
            case 61:
            case 65:
                objArr[0] = "coercionStrategyForLastExpression";
                break;
            case 45:
            case 55:
            case 56:
            case 57:
            case 62:
            case 64:
            case 68:
            case 70:
                objArr[0] = "context";
                break;
            case 48:
                objArr[0] = "outerScope";
                break;
            case 54:
                objArr[0] = "bodyExpressionTypeInfo";
                break;
            case 60:
                objArr[0] = "block";
                break;
            case 63:
            case 67:
            case 69:
                objArr[0] = "statementExpression";
                break;
            case 66:
                objArr[0] = "blockLevelVisitor";
                break;
            default:
                objArr[0] = "components";
                break;
        }
        if (i == 4) {
            objArr[1] = "getLanguageVersionSettings";
        } else if (i == 5) {
            objArr[1] = "getStatementFilter";
        } else if (i == 12) {
            objArr[1] = "safeGetType";
        } else if (i == 19 || i == 28 || i == 31) {
            objArr[1] = "getTypeInfo";
        } else if (i == 42 || i == 46) {
            objArr[1] = "getBlockReturnedType";
        } else if (i == 52 || i == 53) {
            objArr[1] = "getBodyExpressionType";
        } else {
            objArr[1] = "org/jetbrains/kotlin/types/expressions/ExpressionTypingServices";
        }
        switch (i) {
            case 4:
            case 5:
            case 12:
            case 19:
            case 28:
            case 31:
            case 42:
            case 46:
            case 52:
            case 53:
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "safeGetType";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 29:
            case 30:
                objArr[2] = "getTypeInfo";
                break;
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
                objArr[2] = "getType";
                break;
            case 38:
            case 39:
            case 40:
            case 41:
                objArr[2] = "checkFunctionReturnType";
                break;
            case 43:
            case 44:
            case 45:
                objArr[2] = "getBlockReturnedType";
                break;
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
                objArr[2] = "getBodyExpressionType";
                break;
            case 54:
            case 55:
                objArr[2] = "updateLambdaContextInfoForAnonymousFunction";
                break;
            case 56:
                objArr[2] = "updateContextFromNILambdaInfo";
                break;
            case 57:
            case 58:
                objArr[2] = "getNewInferenceLambdaInfo";
                break;
            case 59:
            case 60:
            case 61:
            case 62:
                objArr[2] = "getBlockReturnedTypeWithWritableScope";
                break;
            case 63:
            case 64:
            case 65:
            case 66:
                objArr[2] = "getTypeOfLastExpressionInBlock";
                break;
            case 67:
            case 68:
                objArr[2] = "isCallableReferenceShouldBeAttemptedToBeProcessedByNI";
                break;
            case 69:
            case 70:
                objArr[2] = "createDontCareTypeInfoForNILambda";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 12 && i != 19 && i != 28 && i != 31 && i != 42 && i != 46 && i != 52 && i != 53) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ KtExpression lambda$getTypeInfo$0(KtExpression ktExpression, KtExpression ktExpression2, KtExpression ktExpression3) {
        if (ktExpression3 == ktExpression) {
            return ktExpression2;
        }
        return null;
    }

    public ExpressionTypingServices(ExpressionTypingComponents expressionTypingComponents, AnnotationChecker annotationChecker, StatementFilter statementFilter, ExpressionTypingVisitorDispatcher.ForDeclarations forDeclarations) {
        if (expressionTypingComponents == null) {
            $$$reportNull$$$0(0);
        }
        if (annotationChecker == null) {
            $$$reportNull$$$0(1);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(2);
        }
        if (forDeclarations == null) {
            $$$reportNull$$$0(3);
        }
        this.expressionTypingComponents = expressionTypingComponents;
        this.annotationChecker = annotationChecker;
        this.statementFilter = statementFilter;
        this.expressionTypingFacade = forDeclarations;
    }

    public LanguageVersionSettings getLanguageVersionSettings() {
        LanguageVersionSettings languageVersionSettings = this.expressionTypingComponents.languageVersionSettings;
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(4);
        }
        return languageVersionSettings;
    }

    public StatementFilter getStatementFilter() {
        StatementFilter statementFilter = this.statementFilter;
        if (statementFilter == null) {
            $$$reportNull$$$0(5);
        }
        return statementFilter;
    }

    public KotlinType safeGetType(LexicalScope lexicalScope, KtExpression ktExpression, KotlinType kotlinType, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession, BindingTrace bindingTrace) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(6);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(7);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(8);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(9);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(10);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(11);
        }
        KotlinType type = getType(lexicalScope, ktExpression, kotlinType, dataFlowInfo, inferenceSession, bindingTrace);
        if (type == null) {
            type = ErrorUtils.createErrorType(ErrorTypeKind.NO_RECORDED_TYPE, ktExpression.getText());
        }
        if (type == null) {
            $$$reportNull$$$0(12);
        }
        return type;
    }

    public KotlinTypeInfo getTypeInfo(LexicalScope lexicalScope, KtExpression ktExpression, KotlinType kotlinType, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession, BindingTrace bindingTrace, boolean z) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(13);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(14);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(15);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(16);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(17);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(18);
        }
        KotlinTypeInfo typeInfo = getTypeInfo(lexicalScope, ktExpression, kotlinType, dataFlowInfo, inferenceSession, bindingTrace, z, ktExpression, ContextDependency.INDEPENDENT);
        if (typeInfo == null) {
            $$$reportNull$$$0(19);
        }
        return typeInfo;
    }

    public KotlinTypeInfo getTypeInfo(LexicalScope lexicalScope, final KtExpression ktExpression, KotlinType kotlinType, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession, BindingTrace bindingTrace, boolean z, final KtExpression ktExpression2, ContextDependency contextDependency) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(20);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(21);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(22);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(23);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(24);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(25);
        }
        if (ktExpression2 == null) {
            $$$reportNull$$$0(26);
        }
        if (contextDependency == null) {
            $$$reportNull$$$0(27);
        }
        ExpressionTypingContext expressionTypingContextNewContext = ExpressionTypingContext.newContext(bindingTrace, lexicalScope, dataFlowInfo, kotlinType, contextDependency, this.statementFilter, getLanguageVersionSettings(), this.expressionTypingComponents.dataFlowValueFactory, inferenceSession);
        if (ktExpression2 != ktExpression) {
            expressionTypingContextNewContext = expressionTypingContextNewContext.replaceExpressionContextProvider(new Function1() { // from class: org.jetbrains.kotlin.types.expressions.ExpressionTypingServices$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ExpressionTypingServices.lambda$getTypeInfo$0(ktExpression, ktExpression2, (KtExpression) obj);
                }
            });
        }
        KotlinTypeInfo typeInfo = this.expressionTypingFacade.getTypeInfo(ktExpression, expressionTypingContextNewContext, z);
        if (typeInfo == null) {
            $$$reportNull$$$0(28);
        }
        return typeInfo;
    }

    public KotlinTypeInfo getTypeInfo(KtExpression ktExpression, ResolutionContext resolutionContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(29);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(30);
        }
        KotlinTypeInfo typeInfo = this.expressionTypingFacade.getTypeInfo(ktExpression, ExpressionTypingContext.newContext(resolutionContext));
        if (typeInfo == null) {
            $$$reportNull$$$0(31);
        }
        return typeInfo;
    }

    public KotlinType getType(LexicalScope lexicalScope, KtExpression ktExpression, KotlinType kotlinType, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession, BindingTrace bindingTrace) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(32);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(33);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(34);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(35);
        }
        if (inferenceSession == null) {
            $$$reportNull$$$0(36);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(37);
        }
        return getTypeInfo(lexicalScope, ktExpression, kotlinType, dataFlowInfo, inferenceSession, bindingTrace, false).getType();
    }

    public void checkFunctionReturnType(LexicalScope lexicalScope, KtDeclarationWithBody ktDeclarationWithBody, FunctionDescriptor functionDescriptor, DataFlowInfo dataFlowInfo, KotlinType kotlinType, BindingTrace bindingTrace, ExpressionTypingContext expressionTypingContext) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(38);
        }
        if (ktDeclarationWithBody == null) {
            $$$reportNull$$$0(39);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(40);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(41);
        }
        if (kotlinType == null) {
            kotlinType = functionDescriptor.getReturnType();
            if (!ktDeclarationWithBody.hasBlockBody() && !ktDeclarationWithBody.hasDeclaredReturnType()) {
                kotlinType = TypeUtils.NO_EXPECTED_TYPE;
            }
        }
        if (kotlinType == null) {
            kotlinType = TypeUtils.NO_EXPECTED_TYPE;
        }
        checkFunctionReturnType(ktDeclarationWithBody, ExpressionTypingContext.newContext(bindingTrace, lexicalScope, dataFlowInfo, kotlinType, getLanguageVersionSettings(), this.expressionTypingComponents.dataFlowValueFactory, expressionTypingContext != null ? expressionTypingContext.inferenceSession : InferenceSession.INSTANCE.getDefault()));
    }

    void checkFunctionReturnType(KtDeclarationWithBody ktDeclarationWithBody, ExpressionTypingContext expressionTypingContext) {
        KtExpression bodyExpression = ktDeclarationWithBody.getBodyExpression();
        if (bodyExpression == null) {
            return;
        }
        boolean zHasBlockBody = ktDeclarationWithBody.hasBlockBody();
        if (zHasBlockBody) {
            expressionTypingContext = expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE);
        }
        this.expressionTypingFacade.getTypeInfo(bodyExpression, expressionTypingContext, zHasBlockBody);
    }

    public KotlinTypeInfo getBlockReturnedType(KtBlockExpression ktBlockExpression, ExpressionTypingContext expressionTypingContext, boolean z) {
        KotlinTypeInfo blockReturnedType = getBlockReturnedType(ktBlockExpression, z ? CoercionStrategy.COERCION_TO_UNIT : CoercionStrategy.NO_COERCION, expressionTypingContext);
        if (blockReturnedType == null) {
            $$$reportNull$$$0(42);
        }
        return blockReturnedType;
    }

    public KotlinTypeInfo getBlockReturnedType(KtBlockExpression ktBlockExpression, CoercionStrategy coercionStrategy, ExpressionTypingContext expressionTypingContext) {
        KotlinTypeInfo blockReturnedTypeWithWritableScope;
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(43);
        }
        if (coercionStrategy == null) {
            $$$reportNull$$$0(44);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(45);
        }
        List<KtExpression> listFilterStatements = StatementFilterKt.filterStatements(this.statementFilter, ktBlockExpression);
        DeclarationDescriptor ownerDescriptor = expressionTypingContext.scope.getOwnerDescriptor();
        LexicalWritableScope lexicalWritableScope = new LexicalWritableScope(expressionTypingContext.scope, ownerDescriptor, false, new TraceBasedLocalRedeclarationChecker(expressionTypingContext.trace, this.expressionTypingComponents.overloadChecker), LexicalScopeKind.CODE_BLOCK);
        if (listFilterStatements.isEmpty()) {
            blockReturnedTypeWithWritableScope = this.expressionTypingComponents.dataFlowAnalyzer.createCheckedTypeInfo(this.expressionTypingComponents.builtIns.getUnitType(), expressionTypingContext, ktBlockExpression);
        } else {
            blockReturnedTypeWithWritableScope = getBlockReturnedTypeWithWritableScope(lexicalWritableScope, listFilterStatements, coercionStrategy, expressionTypingContext.replaceStatementFilter(this.statementFilter));
        }
        lexicalWritableScope.freeze();
        if (ownerDescriptor instanceof ScriptDescriptor) {
            expressionTypingContext.trace.record(BindingContext.SCRIPT_SCOPE, (ScriptDescriptor) ownerDescriptor, lexicalWritableScope);
        }
        if (blockReturnedTypeWithWritableScope == null) {
            $$$reportNull$$$0(46);
        }
        return blockReturnedTypeWithWritableScope;
    }

    public KotlinType getBodyExpressionType(BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, KtDeclarationWithBody ktDeclarationWithBody, FunctionDescriptor functionDescriptor, InferenceSession inferenceSession) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(47);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(48);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(49);
        }
        if (ktDeclarationWithBody == null) {
            $$$reportNull$$$0(50);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(51);
        }
        KtExpression bodyExpression = ktDeclarationWithBody.getBodyExpression();
        ExpressionTypingContext expressionTypingContextNewContext = ExpressionTypingContext.newContext(bindingTrace, FunctionDescriptorUtil.getFunctionInnerScope(lexicalScope, functionDescriptor, bindingTrace, this.expressionTypingComponents.overloadChecker), dataFlowInfo, TypeUtils.NO_EXPECTED_TYPE, getLanguageVersionSettings(), this.expressionTypingComponents.dataFlowValueFactory, inferenceSession);
        KotlinResolutionCallbacksImpl.LambdaInfo newInferenceLambdaInfo = getNewInferenceLambdaInfo(expressionTypingContextNewContext, ktDeclarationWithBody);
        ExpressionTypingContext expressionTypingContextUpdateContextFromNILambdaInfo = updateContextFromNILambdaInfo(newInferenceLambdaInfo, expressionTypingContextNewContext);
        KotlinTypeInfo typeInfo = this.expressionTypingFacade.getTypeInfo(bodyExpression, expressionTypingContextUpdateContextFromNILambdaInfo, ktDeclarationWithBody.hasBlockBody());
        updateLambdaContextInfoForAnonymousFunction(newInferenceLambdaInfo, typeInfo, expressionTypingContextUpdateContextFromNILambdaInfo);
        KotlinType type = typeInfo.getType();
        if (type != null) {
            if (type == null) {
                $$$reportNull$$$0(52);
            }
            return type;
        }
        ErrorType errorTypeCreateErrorType = ErrorUtils.createErrorType(ErrorTypeKind.RETURN_TYPE_FOR_FUNCTION, new String[0]);
        if (errorTypeCreateErrorType == null) {
            $$$reportNull$$$0(53);
        }
        return errorTypeCreateErrorType;
    }

    private static void updateLambdaContextInfoForAnonymousFunction(KotlinResolutionCallbacksImpl.LambdaInfo lambdaInfo, KotlinTypeInfo kotlinTypeInfo, ExpressionTypingContext expressionTypingContext) {
        if (kotlinTypeInfo == null) {
            $$$reportNull$$$0(54);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(55);
        }
        if (lambdaInfo == null) {
            return;
        }
        LambdaContextInfo lastExpressionInfo = lambdaInfo.getLastExpressionInfo();
        lastExpressionInfo.setTypeInfo(kotlinTypeInfo);
        lastExpressionInfo.setDataFlowInfoAfter(null);
        lastExpressionInfo.setLexicalScope(expressionTypingContext.scope);
        lastExpressionInfo.setTrace(expressionTypingContext.trace);
    }

    private static ExpressionTypingContext updateContextFromNILambdaInfo(KotlinResolutionCallbacksImpl.LambdaInfo lambdaInfo, ExpressionTypingContext expressionTypingContext) {
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(56);
        }
        return lambdaInfo != null ? expressionTypingContext.replaceContextDependency(lambdaInfo.getContextDependency()).replaceExpectedType(lambdaInfo.getExpectedType()) : expressionTypingContext;
    }

    public static KotlinResolutionCallbacksImpl.LambdaInfo getNewInferenceLambdaInfo(ExpressionTypingContext expressionTypingContext, KtElement ktElement) {
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(57);
        }
        if (ktElement == null) {
            $$$reportNull$$$0(58);
        }
        if (ktElement instanceof KtFunction) {
            return (KotlinResolutionCallbacksImpl.LambdaInfo) expressionTypingContext.trace.get(BindingContext.NEW_INFERENCE_LAMBDA_INFO, (KtFunction) ktElement);
        }
        return null;
    }

    KotlinTypeInfo getBlockReturnedTypeWithWritableScope(LexicalWritableScope lexicalWritableScope, List<? extends KtElement> list, CoercionStrategy coercionStrategy, ExpressionTypingContext expressionTypingContext) {
        KotlinTypeInfo typeInfo;
        if (lexicalWritableScope == null) {
            $$$reportNull$$$0(59);
        }
        if (list == null) {
            $$$reportNull$$$0(60);
        }
        if (coercionStrategy == null) {
            $$$reportNull$$$0(61);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(62);
        }
        if (list.isEmpty()) {
            return TypeInfoFactoryKt.createTypeInfo(this.expressionTypingComponents.builtIns.getUnitType(), expressionTypingContext);
        }
        ExpressionTypingVisitorDispatcher.ForBlock forBlock = new ExpressionTypingVisitorDispatcher.ForBlock(this.expressionTypingComponents, this.annotationChecker, lexicalWritableScope);
        ExpressionTypingContext expressionTypingContextReplaceExpectedType = expressionTypingContext.replaceScope(lexicalWritableScope).replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE);
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
        DataFlowInfo jumpFlowInfo = expressionTypingContextReplaceExpectedType.dataFlowInfo;
        Iterator<? extends KtElement> it = list.iterator();
        boolean z = true;
        boolean jumpOutPossible = false;
        while (it.hasNext()) {
            ProgressManager.checkCanceled();
            expressionTypingContextReplaceExpectedType = expressionTypingContextReplaceExpectedType.replaceBindingTrace(new EffectsFilteringTrace(expressionTypingContext.trace));
            KtElement next = it.next();
            if (next instanceof KtExpression) {
                KtExpression ktExpression = (KtExpression) next;
                if (!it.hasNext()) {
                    typeInfo = getTypeOfLastExpressionInBlock(ktExpression, expressionTypingContextReplaceExpectedType.replaceExpectedType(expressionTypingContext.expectedType), coercionStrategy, forBlock);
                    if (typeInfo.getType() != null && (ktExpression.getLightParent() instanceof KtBlockExpression)) {
                        typeInfo = typeInfo.replaceDataFlowInfo(typeInfo.getDataFlowInfo().assign(this.expressionTypingComponents.dataFlowValueFactory.createDataFlowValue((KtBlockExpression) ktExpression.getLightParent(), typeInfo.getType(), expressionTypingContext), this.expressionTypingComponents.dataFlowValueFactory.createDataFlowValue(ktExpression, typeInfo.getType(), expressionTypingContext), this.expressionTypingComponents.languageVersionSettings));
                    }
                } else {
                    typeInfo = forBlock.getTypeInfo(ktExpression, expressionTypingContextReplaceExpectedType.replaceContextDependency(ContextDependency.INDEPENDENT), true);
                }
                kotlinTypeInfoNoTypeInfo = typeInfo;
                DataFlowInfo dataFlowInfo = kotlinTypeInfoNoTypeInfo.getDataFlowInfo();
                if (!jumpOutPossible) {
                    jumpFlowInfo = kotlinTypeInfoNoTypeInfo.getJumpFlowInfo();
                    jumpOutPossible = kotlinTypeInfoNoTypeInfo.getJumpOutPossible();
                }
                if (dataFlowInfo != expressionTypingContextReplaceExpectedType.dataFlowInfo) {
                    expressionTypingContextReplaceExpectedType = expressionTypingContextReplaceExpectedType.replaceDataFlowInfo(dataFlowInfo);
                }
                forBlock = new ExpressionTypingVisitorDispatcher.ForBlock(this.expressionTypingComponents, this.annotationChecker, lexicalWritableScope);
                DeclarationDescriptor ownerDescriptor = lexicalWritableScope.getOwnerDescriptor();
                if (z && (ownerDescriptor instanceof FunctionDescriptor)) {
                    this.expressionTypingComponents.contractParsingServices.checkContractAndRecordIfPresent(ktExpression, expressionTypingContext.trace, (FunctionDescriptor) ownerDescriptor);
                    z = false;
                }
            }
        }
        return kotlinTypeInfoNoTypeInfo.replaceJumpOutPossible(jumpOutPossible).replaceJumpFlowInfo(jumpFlowInfo);
    }

    private KotlinTypeInfo getTypeOfLastExpressionInBlock(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext, CoercionStrategy coercionStrategy, ExpressionTypingInternals expressionTypingInternals) {
        IElementType operationToken;
        KotlinTypeInfo kotlinTypeInfoCreateDontCareTypeInfoForNILambda;
        KotlinType kotlinType;
        KotlinTypeInfo kotlinTypeInfoCreateDontCareTypeInfoForNILambda2;
        if (ktExpression == null) {
            $$$reportNull$$$0(63);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(64);
        }
        if (coercionStrategy == null) {
            $$$reportNull$$$0(65);
        }
        if (expressionTypingInternals == null) {
            $$$reportNull$$$0(66);
        }
        boolean z = false;
        boolean z2 = true;
        boolean z3 = expressionTypingContext.expectedType != TypeUtils.NO_EXPECTED_TYPE && (expressionTypingContext.expectedType == TypeUtils.UNIT_EXPECTED_TYPE || (coercionStrategy == CoercionStrategy.COERCION_TO_UNIT && KotlinBuiltIns.isUnit(expressionTypingContext.expectedType)));
        if (isCallableReferenceShouldBeAttemptedToBeProcessedByNI(ktExpression, expressionTypingContext, z3) && (kotlinTypeInfoCreateDontCareTypeInfoForNILambda2 = createDontCareTypeInfoForNILambda(ktExpression, expressionTypingContext)) != null) {
            return kotlinTypeInfoCreateDontCareTypeInfoForNILambda2;
        }
        if (expressionTypingContext.expectedType != TypeUtils.NO_EXPECTED_TYPE) {
            if (z3) {
                kotlinType = TypeUtils.UNIT_EXPECTED_TYPE;
            } else {
                kotlinType = expressionTypingContext.expectedType;
            }
            return expressionTypingInternals.getTypeInfo(ktExpression, expressionTypingContext.replaceExpectedType(kotlinType), true);
        }
        if ((KtPsiUtil.deparenthesize(ktExpression) instanceof KtLambdaExpression) && expressionTypingContext.contextDependency == ContextDependency.DEPENDENT && (kotlinTypeInfoCreateDontCareTypeInfoForNILambda = createDontCareTypeInfoForNILambda(ktExpression, expressionTypingContext)) != null) {
            return kotlinTypeInfoCreateDontCareTypeInfoForNILambda;
        }
        KotlinTypeInfo typeInfo = expressionTypingInternals.getTypeInfo(ktExpression, expressionTypingContext, true);
        if (coercionStrategy != CoercionStrategy.COERCION_TO_UNIT) {
            return typeInfo;
        }
        if ((ktExpression instanceof KtDeclaration) && (!(ktExpression instanceof KtNamedFunction) || ktExpression.get$name() != null)) {
            z = true;
        }
        if (!(ktExpression instanceof KtBinaryExpression) || ((operationToken = ((KtBinaryExpression) ktExpression).getOperationToken()) != KtTokens.EQ && !OperatorConventions.ASSIGNMENT_OPERATIONS.containsKey(operationToken))) {
            z2 = z;
        }
        return z2 ? typeInfo.replaceType(this.expressionTypingComponents.builtIns.getUnitType()) : typeInfo;
    }

    private static boolean isCallableReferenceShouldBeAttemptedToBeProcessedByNI(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext, boolean z) {
        if (ktExpression == null) {
            $$$reportNull$$$0(67);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(68);
        }
        if (!(ktExpression instanceof KtCallableReferenceExpression)) {
            return false;
        }
        if (expressionTypingContext.expectedType == TypeUtils.NO_EXPECTED_TYPE) {
            return true;
        }
        return !z && (ktExpression.getLightParent().getLightParent() instanceof KtFunctionLiteral);
    }

    private static KotlinTypeInfo createDontCareTypeInfoForNILambda(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext) {
        KtFunctionLiteral ktFunctionLiteral;
        KotlinResolutionCallbacksImpl.LambdaInfo lambdaInfo;
        if (ktExpression == null) {
            $$$reportNull$$$0(69);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(70);
        }
        if (!expressionTypingContext.languageVersionSettings.supportsFeature(LanguageFeature.NewInference) || (expressionTypingContext.inferenceSession instanceof BuilderInferenceSession) || (ktFunctionLiteral = (KtFunctionLiteral) PsiUtilsKt.getNonStrictParentOfType(ktExpression, KtFunctionLiteral.class)) == null || (lambdaInfo = (KotlinResolutionCallbacksImpl.LambdaInfo) expressionTypingContext.trace.getContext().get(BindingContext.NEW_INFERENCE_LAMBDA_INFO, ktFunctionLiteral)) == null) {
            return null;
        }
        lambdaInfo.getLastExpressionInfo().setLexicalScope(expressionTypingContext.scope);
        lambdaInfo.getLastExpressionInfo().setTrace(expressionTypingContext.trace);
        return new KotlinTypeInfo(TypeUtils.DONT_CARE, expressionTypingContext.dataFlowInfo);
    }

    private static class EffectsFilteringTrace extends AbstractFilteringTrace {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "slice", "org/jetbrains/kotlin/types/expressions/ExpressionTypingServices$EffectsFilteringTrace", "shouldBeHiddenFromParent"));
        }

        public EffectsFilteringTrace(BindingTrace bindingTrace) {
            super(bindingTrace, "Effects filtering trace");
        }

        @Override // org.jetbrains.kotlin.resolve.AbstractFilteringTrace
        protected <K, V> boolean shouldBeHiddenFromParent(WritableSlice<K, V> writableSlice, K k) {
            if (writableSlice == null) {
                $$$reportNull$$$0(0);
            }
            return writableSlice == BindingContext.EXPRESSION_EFFECTS;
        }
    }

    public LocalRedeclarationChecker createLocalRedeclarationChecker(BindingTrace bindingTrace) {
        return new TraceBasedLocalRedeclarationChecker(bindingTrace, this.expressionTypingComponents.overloadChecker);
    }
}
