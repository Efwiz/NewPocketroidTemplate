package org.jetbrains.kotlin.types.expressions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import kotlin.Pair;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDoWhileExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.KtWhileExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.kotlin.resolve.calls.checkers.AssignmentChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.checkers.NewSchemeOfIntegerOperatorResolutionChecker;
import org.jetbrains.kotlin.resolve.calls.context.CallPosition;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.TemporaryTraceAndCache;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsImpl;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsUtil;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.extensions.AssignResolutionAltererExtension;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: loaded from: classes6.dex */
public class ExpressionTypingVisitorForStatements extends ExpressionTypingVisitor {
    private final BasicExpressionTypingVisitor basic;
    private final ControlStructureTypingVisitor controlStructures;
    private final FunctionsTypingVisitor functions;
    private final PatternMatchingTypingVisitor patterns;
    private final LexicalWritableScope scope;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 16:
            case 17:
            case 18:
            case 21:
            case 22:
            case 23:
            case 24:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 19:
            case 20:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 16:
            case 17:
            case 18:
            case 21:
            case 22:
            case 23:
            case 24:
                i2 = 2;
                break;
            case 19:
            case 20:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "scope";
                break;
            case 2:
                objArr[0] = "basic";
                break;
            case 3:
                objArr[0] = "controlStructures";
                break;
            case 4:
                objArr[0] = "patterns";
                break;
            case 5:
                objArr[0] = "functions";
                break;
            case 6:
            case 15:
            case 26:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
                objArr[0] = "expression";
                break;
            case 7:
            case 25:
                objArr[0] = "context";
                break;
            case 8:
                objArr[0] = "declaration";
                break;
            case 9:
                objArr[0] = "property";
                break;
            case 10:
                objArr[0] = "typeAlias";
                break;
            case 11:
                objArr[0] = "multiDeclaration";
                break;
            case 12:
                objArr[0] = "function";
                break;
            case 13:
                objArr[0] = "klass";
                break;
            case 14:
                objArr[0] = "dcl";
                break;
            case 16:
            case 17:
            case 18:
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[0] = "org/jetbrains/kotlin/types/expressions/ExpressionTypingVisitorForStatements";
                break;
            case 19:
            case 20:
                objArr[0] = "bindingContext";
                break;
            case 27:
                objArr[0] = "rhsDataFlowInfo";
                break;
            case 29:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            default:
                objArr[0] = "facade";
                break;
        }
        switch (i) {
            case 16:
            case 17:
            case 18:
                objArr[1] = "visitAssignmentOperation";
                break;
            case 19:
            case 20:
            default:
                objArr[1] = "org/jetbrains/kotlin/types/expressions/ExpressionTypingVisitorForStatements";
                break;
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[1] = "visitAssignment";
                break;
        }
        switch (i) {
            case 6:
            case 7:
                objArr[2] = "checkAssignmentType";
                break;
            case 8:
                objArr[2] = "visitObjectDeclaration";
                break;
            case 9:
                objArr[2] = "visitProperty";
                break;
            case 10:
                objArr[2] = "visitTypeAlias";
                break;
            case 11:
                objArr[2] = "visitDestructuringDeclaration";
                break;
            case 12:
                objArr[2] = "visitNamedFunction";
                break;
            case 13:
                objArr[2] = "visitClass";
                break;
            case 14:
                objArr[2] = "visitDeclaration";
                break;
            case 15:
                objArr[2] = "visitBinaryExpression";
                break;
            case 16:
            case 17:
            case 18:
            case 21:
            case 22:
            case 23:
            case 24:
                break;
            case 19:
                objArr[2] = "refineTypeFromPropertySetterIfPossible";
                break;
            case 20:
                objArr[2] = "refineTypeByPropertyInType";
                break;
            case 25:
            case 26:
            case 27:
                objArr[2] = "checkPropertyInTypeWithWarnings";
                break;
            case 28:
                objArr[2] = "visitExpression";
                break;
            case 29:
                objArr[2] = "visitKtElement";
                break;
            case 30:
                objArr[2] = "visitWhileExpression";
                break;
            case 31:
                objArr[2] = "visitDoWhileExpression";
                break;
            case 32:
                objArr[2] = "visitForExpression";
                break;
            case 33:
                objArr[2] = "visitAnnotatedExpression";
                break;
            case 34:
                objArr[2] = "visitIfExpression";
                break;
            case 35:
                objArr[2] = "visitWhenExpression";
                break;
            case 36:
                objArr[2] = "visitBlockExpression";
                break;
            case 37:
                objArr[2] = "visitLabeledExpression";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 16:
            case 17:
            case 18:
            case 21:
            case 22:
            case 23:
            case 24:
                throw new IllegalStateException(str2);
            case 19:
            case 20:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExpressionTypingVisitorForStatements(ExpressionTypingInternals expressionTypingInternals, LexicalWritableScope lexicalWritableScope, BasicExpressionTypingVisitor basicExpressionTypingVisitor, ControlStructureTypingVisitor controlStructureTypingVisitor, PatternMatchingTypingVisitor patternMatchingTypingVisitor, FunctionsTypingVisitor functionsTypingVisitor) {
        super(expressionTypingInternals);
        if (expressionTypingInternals == null) {
            $$$reportNull$$$0(0);
        }
        if (lexicalWritableScope == null) {
            $$$reportNull$$$0(1);
        }
        if (basicExpressionTypingVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (controlStructureTypingVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (patternMatchingTypingVisitor == null) {
            $$$reportNull$$$0(4);
        }
        if (functionsTypingVisitor == null) {
            $$$reportNull$$$0(5);
        }
        this.scope = lexicalWritableScope;
        this.basic = basicExpressionTypingVisitor;
        this.controlStructures = controlStructureTypingVisitor;
        this.patterns = patternMatchingTypingVisitor;
        this.functions = functionsTypingVisitor;
    }

    private KotlinType checkAssignmentType(KotlinType kotlinType, KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(6);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(7);
        }
        if (kotlinType != null && !KotlinBuiltIns.isUnit(kotlinType) && !TypeUtils.noExpectedType(expressionTypingContext.expectedType) && !KotlinTypeKt.isError(expressionTypingContext.expectedType) && TypeUtils.equalTypes(expressionTypingContext.expectedType, kotlinType)) {
            expressionTypingContext.trace.report(Errors.ASSIGNMENT_TYPE_MISMATCH.on(ktBinaryExpression, expressionTypingContext.expectedType));
            return null;
        }
        return this.components.dataFlowAnalyzer.checkStatementType(ktBinaryExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitObjectDeclaration(KtObjectDeclaration ktObjectDeclaration, ExpressionTypingContext expressionTypingContext) {
        if (ktObjectDeclaration == null) {
            $$$reportNull$$$0(8);
        }
        LocalClassifierAnalyzer localClassifierAnalyzer = this.components.localClassifierAnalyzer;
        LexicalWritableScope lexicalWritableScope = this.scope;
        localClassifierAnalyzer.processClassOrObject(lexicalWritableScope, ((ExpressionTypingContext) expressionTypingContext.replaceScope(lexicalWritableScope)).replaceContextDependency(ContextDependency.INDEPENDENT), this.scope.getOwnerDescriptor(), ktObjectDeclaration);
        return TypeInfoFactoryKt.createTypeInfo(this.components.dataFlowAnalyzer.checkStatementType(ktObjectDeclaration, expressionTypingContext), expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitProperty(KtProperty ktProperty, ExpressionTypingContext expressionTypingContext) {
        if (ktProperty == null) {
            $$$reportNull$$$0(9);
        }
        Pair<KotlinTypeInfo, VariableDescriptor> pairProcess = this.components.localVariableResolver.process(ktProperty, expressionTypingContext, this.scope, this.facade);
        this.scope.addVariableDescriptor(pairProcess.getSecond());
        return pairProcess.getFirst();
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitTypeAlias(KtTypeAlias ktTypeAlias, ExpressionTypingContext expressionTypingContext) {
        if (ktTypeAlias == null) {
            $$$reportNull$$$0(10);
        }
        TypeAliasDescriptor typeAliasDescriptorResolveTypeAliasDescriptor = this.components.descriptorResolver.resolveTypeAliasDescriptor(expressionTypingContext.scope.getOwnerDescriptor(), expressionTypingContext.scope, ktTypeAlias, expressionTypingContext.trace);
        this.scope.addClassifierDescriptor(typeAliasDescriptorResolveTypeAliasDescriptor);
        ForceResolveUtil.forceResolveAllContents(typeAliasDescriptorResolveTypeAliasDescriptor);
        this.facade.getComponents().declarationsCheckerBuilder.withTrace(expressionTypingContext.trace).checkLocalTypeAliasDeclaration(ktTypeAlias, typeAliasDescriptorResolveTypeAliasDescriptor);
        return TypeInfoFactoryKt.createTypeInfo(this.components.dataFlowAnalyzer.checkStatementType(ktTypeAlias, expressionTypingContext), expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitDestructuringDeclaration(KtDestructuringDeclaration ktDestructuringDeclaration, ExpressionTypingContext expressionTypingContext) {
        if (ktDestructuringDeclaration == null) {
            $$$reportNull$$$0(11);
        }
        this.components.annotationResolver.resolveAnnotationsWithArguments(this.scope, ktDestructuringDeclaration.getModifierList(), expressionTypingContext.trace);
        KtExpression initializer = ktDestructuringDeclaration.getInitializer();
        if (initializer == null) {
            expressionTypingContext.trace.report(Errors.INITIALIZER_REQUIRED_FOR_DESTRUCTURING_DECLARATION.on(ktDestructuringDeclaration));
        }
        ExpressionReceiver expressionReceiver = initializer != null ? ExpressionTypingUtils.getExpressionReceiver(this.facade, initializer, expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT)) : null;
        this.components.destructuringDeclarationResolver.defineLocalVariablesFromDestructuringDeclaration(this.scope, ktDestructuringDeclaration, expressionReceiver, initializer, expressionTypingContext);
        this.components.modifiersChecker.withTrace(expressionTypingContext.trace).checkModifiersForDestructuringDeclaration(ktDestructuringDeclaration);
        this.components.identifierChecker.checkDeclaration(ktDestructuringDeclaration, expressionTypingContext.trace);
        if (expressionReceiver == null) {
            return TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
        }
        return this.facade.getTypeInfo(initializer, expressionTypingContext).replaceType(this.components.dataFlowAnalyzer.checkStatementType(ktDestructuringDeclaration, expressionTypingContext));
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitNamedFunction(KtNamedFunction ktNamedFunction, ExpressionTypingContext expressionTypingContext) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(12);
        }
        return this.functions.visitNamedFunction(ktNamedFunction, expressionTypingContext, ktNamedFunction.get$name() != null, this.scope);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitClass(KtClass ktClass, ExpressionTypingContext expressionTypingContext) {
        if (ktClass == null) {
            $$$reportNull$$$0(13);
        }
        LocalClassifierAnalyzer localClassifierAnalyzer = this.components.localClassifierAnalyzer;
        LexicalWritableScope lexicalWritableScope = this.scope;
        localClassifierAnalyzer.processClassOrObject(lexicalWritableScope, ((ExpressionTypingContext) expressionTypingContext.replaceScope(lexicalWritableScope)).replaceContextDependency(ContextDependency.INDEPENDENT), this.scope.getOwnerDescriptor(), ktClass);
        return TypeInfoFactoryKt.createTypeInfo(this.components.dataFlowAnalyzer.checkStatementType(ktClass, expressionTypingContext), expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitDeclaration(KtDeclaration ktDeclaration, ExpressionTypingContext expressionTypingContext) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(14);
        }
        return TypeInfoFactoryKt.createTypeInfo(this.components.dataFlowAnalyzer.checkStatementType(ktDeclaration, expressionTypingContext), expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitBinaryExpression(KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext) {
        KotlinTypeInfo kotlinTypeInfoVisitAssignmentOperation;
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(15);
        }
        IElementType referencedNameElementType = ktBinaryExpression.getOperationReference().getReferencedNameElementType();
        if (referencedNameElementType == KtTokens.EQ) {
            kotlinTypeInfoVisitAssignmentOperation = visitAssignment(ktBinaryExpression, expressionTypingContext);
        } else if (OperatorConventions.ASSIGNMENT_OPERATIONS.containsKey(referencedNameElementType)) {
            kotlinTypeInfoVisitAssignmentOperation = visitAssignmentOperation(ktBinaryExpression, expressionTypingContext);
        } else {
            return this.facade.getTypeInfo(ktBinaryExpression, expressionTypingContext);
        }
        return this.components.dataFlowAnalyzer.checkType(kotlinTypeInfoVisitAssignmentOperation, ktBinaryExpression, expressionTypingContext);
    }

    protected KotlinTypeInfo visitAssignmentOperation(KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext) {
        KotlinType resultingType;
        ExpressionTypingContext expressionTypingContext2;
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsNameNotFound;
        TemporaryTraceAndCache temporaryTraceAndCacheCreate = TemporaryTraceAndCache.create(expressionTypingContext, "trace to resolve array set method for binary expression", ktBinaryExpression);
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceTraceAndCache(temporaryTraceAndCacheCreate).replaceContextDependency(ContextDependency.INDEPENDENT);
        KtOperationReferenceExpression operationReference = ktBinaryExpression.getOperationReference();
        IElementType referencedNameElementType = operationReference.getReferencedNameElementType();
        KtExpression left = ktBinaryExpression.getLeft();
        KotlinTypeInfo typeInfoOrNullType = ExpressionTypingUtils.getTypeInfoOrNullType(left, expressionTypingContextReplaceContextDependency, this.facade);
        KotlinType type = typeInfoOrNullType.getType();
        KtExpression right = ktBinaryExpression.getRight();
        KtExpression ktExpressionDeparenthesize = left == null ? null : KtPsiUtil.deparenthesize(left);
        if (right == null || ktExpressionDeparenthesize == null) {
            temporaryTraceAndCacheCreate.commit();
            KotlinTypeInfo kotlinTypeInfoClearType = typeInfoOrNullType.clearType();
            if (kotlinTypeInfoClearType == null) {
                $$$reportNull$$$0(16);
            }
            return kotlinTypeInfoClearType;
        }
        if (type == null) {
            KotlinTypeInfo typeInfo = this.facade.getTypeInfo(right, expressionTypingContextReplaceContextDependency.replaceDataFlowInfo(typeInfoOrNullType.getDataFlowInfo()));
            expressionTypingContextReplaceContextDependency.trace.report(Errors.UNRESOLVED_REFERENCE.on(operationReference, operationReference));
            temporaryTraceAndCacheCreate.commit();
            KotlinTypeInfo kotlinTypeInfoClearType2 = typeInfo.clearType();
            if (kotlinTypeInfoClearType2 == null) {
                $$$reportNull$$$0(17);
            }
            return kotlinTypeInfoClearType2;
        }
        if (!ArgumentTypeResolver.isFunctionLiteralOrCallableReference(right, expressionTypingContextReplaceContextDependency) && !expressionTypingContextReplaceContextDependency.languageVersionSettings.supportsFeature(LanguageFeature.NewInference)) {
            this.facade.getTypeInfo(right, expressionTypingContextReplaceContextDependency.replaceContextDependency(ContextDependency.DEPENDENT));
        }
        ExpressionReceiver expressionReceiverCreate = ExpressionReceiver.INSTANCE.create(ktExpressionDeparenthesize, type, expressionTypingContextReplaceContextDependency.trace.getContext());
        Name name2 = OperatorConventions.ASSIGNMENT_OPERATIONS.get(referencedNameElementType);
        TemporaryTraceAndCache temporaryTraceAndCacheCreate2 = TemporaryTraceAndCache.create(expressionTypingContextReplaceContextDependency, "trace to check assignment operation like '+=' for", ktBinaryExpression);
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveBinaryCall = this.components.callResolver.resolveBinaryCall(expressionTypingContextReplaceContextDependency.replaceTraceAndCache(temporaryTraceAndCacheCreate2).replaceScope(this.scope), expressionReceiverCreate, ktBinaryExpression, name2);
        KotlinType resultingType2 = OverloadResolutionResultsUtil.getResultingType(overloadResolutionResultsResolveBinaryCall, expressionTypingContextReplaceContextDependency);
        TemporaryTraceAndCache temporaryTraceAndCacheCreate3 = TemporaryTraceAndCache.create(expressionTypingContextReplaceContextDependency, "trace to check binary operation like '+' for", ktBinaryExpression);
        KtExpression ktExpression = ktExpressionDeparenthesize;
        boolean zCheckLValue = this.basic.checkLValue(TemporaryBindingTrace.create(expressionTypingContextReplaceContextDependency.trace, "Trace for checking assignability"), expressionTypingContextReplaceContextDependency, ktExpressionDeparenthesize, right, ktBinaryExpression, false);
        if (resultingType2 == null || zCheckLValue) {
            ExpressionTypingContext expressionTypingContextReplaceScope = expressionTypingContextReplaceContextDependency.replaceTraceAndCache(temporaryTraceAndCacheCreate3).replaceScope(this.scope);
            if (expressionTypingContextReplaceContextDependency.inferenceSession instanceof BuilderInferenceSession) {
                ((BuilderInferenceSession) expressionTypingContextReplaceContextDependency.inferenceSession).clearCallsInfoByContainingElement(right);
            }
            OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveBinaryCall2 = this.components.callResolver.resolveBinaryCall(expressionTypingContextReplaceScope, expressionReceiverCreate, ktBinaryExpression, OperatorConventions.BINARY_OPERATION_NAMES.get(OperatorConventions.ASSIGNMENT_OPERATION_COUNTERPARTS.get(referencedNameElementType)));
            resultingType = OverloadResolutionResultsUtil.getResultingType(overloadResolutionResultsResolveBinaryCall2, expressionTypingContextReplaceContextDependency);
            expressionTypingContext2 = expressionTypingContextReplaceScope;
            overloadResolutionResultsNameNotFound = overloadResolutionResultsResolveBinaryCall2;
        } else {
            overloadResolutionResultsNameNotFound = OverloadResolutionResultsImpl.nameNotFound();
            expressionTypingContext2 = null;
            resultingType = null;
        }
        KotlinType kotlinType = resultingType2 != null ? resultingType2 : resultingType;
        boolean zAtLeastOneOperation = atLeastOneOperation(overloadResolutionResultsResolveBinaryCall.getResultingCalls(), OperatorNameConventions.REM_ASSIGN);
        boolean zAtLeastOneOperation2 = atLeastOneOperation(overloadResolutionResultsNameNotFound.getResultingCalls(), OperatorNameConventions.REM);
        boolean z = true;
        boolean z2 = overloadResolutionResultsResolveBinaryCall.isSuccess() && overloadResolutionResultsNameNotFound.isSuccess() && (zAtLeastOneOperation == zAtLeastOneOperation2);
        if (resultingType2 == null || ((!overloadResolutionResultsResolveBinaryCall.isSuccess() && overloadResolutionResultsNameNotFound.isSuccess()) || (zAtLeastOneOperation2 && overloadResolutionResultsNameNotFound.isSuccess()))) {
            z = false;
        }
        KotlinTypeInfo kotlinTypeInfoCompletePlusResolution = (z2 || !z) ? completePlusResolution(expressionTypingContext2, ktBinaryExpression, resultingType, ktExpression, typeInfoOrNullType) : null;
        if (!z2 || kotlinTypeInfoCompletePlusResolution == null) {
            if (z) {
                temporaryTraceAndCacheCreate2.commit();
                if (!KotlinTypeChecker.DEFAULT.equalTypes(this.components.builtIns.getUnitType(), resultingType2)) {
                    expressionTypingContextReplaceContextDependency.trace.report(Errors.ASSIGNMENT_OPERATOR_SHOULD_RETURN_UNIT.on(operationReference, overloadResolutionResultsResolveBinaryCall.mo7437getResultingDescriptor(), operationReference));
                }
            } else {
                if (kotlinTypeInfoCompletePlusResolution != null) {
                    typeInfoOrNullType = kotlinTypeInfoCompletePlusResolution;
                }
                temporaryTraceAndCacheCreate3.commit();
                expressionTypingContextReplaceContextDependency.trace.record(BindingContext.VARIABLE_REASSIGNMENT, ktBinaryExpression);
            }
            kotlinTypeInfoCompletePlusResolution = typeInfoOrNullType;
        } else {
            OverloadResolutionResults overloadResolutionResultsAmbiguity = OverloadResolutionResultsUtil.ambiguity(overloadResolutionResultsResolveBinaryCall, overloadResolutionResultsNameNotFound);
            expressionTypingContextReplaceContextDependency.trace.report(Errors.ASSIGN_OPERATOR_AMBIGUITY.on(operationReference, overloadResolutionResultsAmbiguity.getResultingCalls()));
            HashSet hashSet = new HashSet();
            Iterator it = overloadResolutionResultsAmbiguity.getResultingCalls().iterator();
            while (it.hasNext()) {
                hashSet.add(((ResolvedCall) it.next()).getResultingDescriptor());
            }
            expressionTypingContextReplaceContextDependency.trace.record(BindingContext.AMBIGUOUS_REFERENCE_TARGET, operationReference, hashSet);
        }
        temporaryTraceAndCacheCreate.commit();
        KotlinTypeInfo kotlinTypeInfoReplaceType = kotlinTypeInfoCompletePlusResolution.replaceType(checkAssignmentType(kotlinType, ktBinaryExpression, expressionTypingContext));
        if (kotlinTypeInfoReplaceType == null) {
            $$$reportNull$$$0(18);
        }
        return kotlinTypeInfoReplaceType;
    }

    private KotlinTypeInfo completePlusResolution(ExpressionTypingContext expressionTypingContext, KtBinaryExpression ktBinaryExpression, KotlinType kotlinType, KtExpression ktExpression, KotlinTypeInfo kotlinTypeInfo) {
        KotlinType kotlinTypeRefineTypeFromPropertySetterIfPossible;
        KtExpression left = ktBinaryExpression.getLeft();
        KtExpression right = ktBinaryExpression.getRight();
        if (left == null || right == null) {
            return null;
        }
        if (ktExpression instanceof KtArrayAccessExpression) {
            this.basic.resolveImplicitArrayAccessSetMethod((KtArrayAccessExpression) ktExpression, right, expressionTypingContext.replaceScope(this.scope).replaceBindingTrace(TemporaryBindingTrace.create(expressionTypingContext.trace, "trace to resolve array set method for assignment", ktBinaryExpression)), expressionTypingContext.trace);
        }
        KotlinTypeInfo typeInfo = this.facade.getTypeInfo(right, expressionTypingContext.replaceDataFlowInfo(kotlinTypeInfo.getDataFlowInfo()));
        boolean zSupportsFeature = this.components.languageVersionSettings.supportsFeature(LanguageFeature.RefineTypeCheckingOnAssignmentsToJavaFields);
        BindingContext context = expressionTypingContext.trace.getContext();
        KotlinType type = kotlinTypeInfo.getType();
        if (zSupportsFeature) {
            kotlinTypeRefineTypeFromPropertySetterIfPossible = refineTypeByPropertyInType(context, left, type);
        } else {
            kotlinTypeRefineTypeFromPropertySetterIfPossible = refineTypeFromPropertySetterIfPossible(context, left, type);
        }
        KotlinType kotlinType2 = kotlinTypeRefineTypeFromPropertySetterIfPossible;
        Ref<Boolean> refCreate = Ref.create(false);
        this.components.dataFlowAnalyzer.checkType(kotlinType, ktBinaryExpression, expressionTypingContext.replaceExpectedType(kotlinType2).replaceDataFlowInfo(typeInfo.getDataFlowInfo()).replaceCallPosition(new CallPosition.PropertyAssignment(ktExpression, false)), refCreate, true);
        this.basic.checkLValue(expressionTypingContext.trace, expressionTypingContext, left, right, ktBinaryExpression, false);
        if (!zSupportsFeature) {
            checkPropertyInTypeWithWarnings(expressionTypingContext, ktBinaryExpression, kotlinType, typeInfo.getDataFlowInfo(), left, type, kotlinType2);
        }
        if (refCreate.get().booleanValue()) {
            return null;
        }
        return typeInfo;
    }

    private static boolean atLeastOneOperation(Collection<? extends ResolvedCall<FunctionDescriptor>> collection, Name name2) {
        Iterator<? extends ResolvedCall<FunctionDescriptor>> it = collection.iterator();
        while (it.hasNext()) {
            if (((FunctionDescriptor) it.next().get$suspendPropertyDescriptor()).getShortName().equals(name2)) {
                return true;
            }
        }
        return false;
    }

    private static KotlinType refineTypeFromPropertySetterIfPossible(BindingContext bindingContext, KtElement ktElement, KotlinType kotlinType) {
        PropertySetterDescriptor setter;
        if (bindingContext == null) {
            $$$reportNull$$$0(19);
        }
        VariableDescriptor variableDescriptorExtractVariableFromResolvedCall = BindingContextUtils.extractVariableFromResolvedCall(bindingContext, ktElement);
        return (!(variableDescriptorExtractVariableFromResolvedCall instanceof PropertyDescriptor) || (setter = ((PropertyDescriptor) variableDescriptorExtractVariableFromResolvedCall).getSetter()) == null) ? kotlinType : setter.getValueParameters().get(0).getType();
    }

    private static KotlinType refineTypeByPropertyInType(BindingContext bindingContext, KtElement ktElement, KotlinType kotlinType) {
        KotlinType inType;
        if (bindingContext == null) {
            $$$reportNull$$$0(20);
        }
        VariableDescriptor variableDescriptorExtractVariableFromResolvedCall = BindingContextUtils.extractVariableFromResolvedCall(bindingContext, ktElement);
        return (!(variableDescriptorExtractVariableFromResolvedCall instanceof PropertyDescriptor) || (inType = ((PropertyDescriptor) variableDescriptorExtractVariableFromResolvedCall).getInType()) == null) ? kotlinType : inType;
    }

    protected KotlinTypeInfo visitAssignment(final KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext) {
        KotlinType kotlinTypeRefineTypeFromPropertySetterIfPossible;
        KotlinTypeInfo kotlinTypeInfo;
        final ExpressionTypingContext expressionTypingContextReplaceContextDependency = expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceScope(this.scope).replaceContextDependency(ContextDependency.INDEPENDENT);
        final KtExpression left = ktBinaryExpression.getLeft();
        if (left instanceof KtAnnotatedExpression) {
            this.basic.resolveAnnotationsOnExpression((KtAnnotatedExpression) left, expressionTypingContextReplaceContextDependency);
        }
        final KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(left);
        KtExpression right = ktBinaryExpression.getRight();
        if (ktExpressionDeparenthesize instanceof KtArrayAccessExpression) {
            KtArrayAccessExpression ktArrayAccessExpression = (KtArrayAccessExpression) ktExpressionDeparenthesize;
            if (right == null) {
                KotlinTypeInfo kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContextReplaceContextDependency);
                if (kotlinTypeInfoNoTypeInfo == null) {
                    $$$reportNull$$$0(21);
                }
                return kotlinTypeInfoNoTypeInfo;
            }
            KotlinTypeInfo kotlinTypeInfoResolveArrayAccessSetMethod = this.basic.resolveArrayAccessSetMethod(ktArrayAccessExpression, right, expressionTypingContextReplaceContextDependency, expressionTypingContextReplaceContextDependency.trace);
            this.basic.checkLValue(expressionTypingContextReplaceContextDependency.trace, expressionTypingContextReplaceContextDependency, ktArrayAccessExpression, right, ktBinaryExpression, true);
            KotlinTypeInfo kotlinTypeInfoReplaceType = kotlinTypeInfoResolveArrayAccessSetMethod.replaceType(checkAssignmentType(kotlinTypeInfoResolveArrayAccessSetMethod.getType(), ktBinaryExpression, expressionTypingContext));
            if (kotlinTypeInfoReplaceType == null) {
                $$$reportNull$$$0(22);
            }
            return kotlinTypeInfoReplaceType;
        }
        final KotlinTypeInfo typeInfoOrNullType = ExpressionTypingUtils.getTypeInfoOrNullType(ktExpressionDeparenthesize, expressionTypingContextReplaceContextDependency.replaceCallPosition(new CallPosition.PropertyAssignment(ktExpressionDeparenthesize, true)), this.facade);
        final BindingContext context = expressionTypingContextReplaceContextDependency.trace.getContext();
        final KotlinType type = typeInfoOrNullType.getType();
        boolean zSupportsFeature = this.components.languageVersionSettings.supportsFeature(LanguageFeature.RefineTypeCheckingOnAssignmentsToJavaFields);
        if (zSupportsFeature) {
            kotlinTypeRefineTypeFromPropertySetterIfPossible = refineTypeByPropertyInType(context, left, type);
        } else {
            kotlinTypeRefineTypeFromPropertySetterIfPossible = refineTypeFromPropertySetterIfPossible(context, left, type);
        }
        KotlinType kotlinType = kotlinTypeRefineTypeFromPropertySetterIfPossible;
        List<AssignResolutionAltererExtension> instances = AssignResolutionAltererExtension.INSTANCE.getInstances(ktBinaryExpression.getProject());
        if (!instances.isEmpty() && (kotlinTypeInfo = (KotlinTypeInfo) instances.stream().filter(new Predicate() { // from class: org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorForStatements$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AssignResolutionAltererExtension) obj).needOverloadAssign(ktBinaryExpression, type, context);
            }
        }).map(new Function() { // from class: org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorForStatements$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m7483x584d51e4(context, ktBinaryExpression, left, ktExpressionDeparenthesize, typeInfoOrNullType, expressionTypingContextReplaceContextDependency, (AssignResolutionAltererExtension) obj);
            }
        }).filter(new Predicate() { // from class: org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorForStatements$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((KotlinTypeInfo) obj);
            }
        }).findFirst().orElse(null)) != null) {
            if (kotlinTypeInfo == null) {
                $$$reportNull$$$0(23);
            }
            return kotlinTypeInfo;
        }
        DataFlowInfo dataFlowInfo = typeInfoOrNullType.getDataFlowInfo();
        if (right != null) {
            KotlinTypeInfo typeInfo = this.facade.getTypeInfo(right, expressionTypingContextReplaceContextDependency.replaceDataFlowInfo(dataFlowInfo).replaceExpectedType(kotlinType).replaceCallPosition(new CallPosition.PropertyAssignment(left, false)));
            DataFlowInfo dataFlowInfo2 = typeInfo.getDataFlowInfo();
            KotlinType type2 = typeInfo.getType();
            if (ktExpressionDeparenthesize != 0 && kotlinType != 0 && type2 != null) {
                typeInfo = typeInfo.replaceDataFlowInfo(dataFlowInfo2.assign(this.components.dataFlowValueFactory.createDataFlowValue(ktExpressionDeparenthesize, kotlinType, expressionTypingContextReplaceContextDependency), this.components.dataFlowValueFactory.createDataFlowValue(right, type2, expressionTypingContextReplaceContextDependency), this.components.languageVersionSettings));
                NewSchemeOfIntegerOperatorResolutionChecker.checkArgument(kotlinType, right, expressionTypingContextReplaceContextDependency.trace, this.components.moduleDescriptor);
            }
            typeInfoOrNullType = typeInfo;
        }
        if (kotlinType != 0 && left != null) {
            this.basic.checkLValue(expressionTypingContextReplaceContextDependency.trace, expressionTypingContextReplaceContextDependency, left, right, ktBinaryExpression, false);
            CallCheckerContext callCheckerContext = new CallCheckerContext(expressionTypingContextReplaceContextDependency, this.components.deprecationResolver, this.components.moduleDescriptor, this.components.missingSupertypesResolver, this.components.callComponents, expressionTypingContextReplaceContextDependency.trace);
            Iterator<AssignmentChecker> it = this.components.assignmentCheckers.iterator();
            while (it.hasNext()) {
                it.next().check(ktBinaryExpression, callCheckerContext);
            }
        }
        if (!zSupportsFeature) {
            checkPropertyInTypeWithWarnings(expressionTypingContextReplaceContextDependency, ktBinaryExpression, typeInfoOrNullType.getType(), typeInfoOrNullType.getDataFlowInfo(), left, type, kotlinType);
        }
        KotlinTypeInfo kotlinTypeInfoReplaceType2 = typeInfoOrNullType.replaceType(this.components.dataFlowAnalyzer.checkStatementType(ktBinaryExpression, expressionTypingContext));
        if (kotlinTypeInfoReplaceType2 == null) {
            $$$reportNull$$$0(24);
        }
        return kotlinTypeInfoReplaceType2;
    }

    /* JADX INFO: renamed from: lambda$visitAssignment$1$org-jetbrains-kotlin-types-expressions-ExpressionTypingVisitorForStatements, reason: not valid java name */
    /* synthetic */ KotlinTypeInfo m7483x584d51e4(BindingContext bindingContext, KtBinaryExpression ktBinaryExpression, KtExpression ktExpression, KtExpression ktExpression2, KotlinTypeInfo kotlinTypeInfo, ExpressionTypingContext expressionTypingContext, AssignResolutionAltererExtension assignResolutionAltererExtension) {
        return assignResolutionAltererExtension.resolveAssign(bindingContext, ktBinaryExpression, ktExpression, ktExpression2, kotlinTypeInfo, expressionTypingContext, this.components, this.scope);
    }

    private void checkPropertyInTypeWithWarnings(ResolutionContext<?> resolutionContext, KtBinaryExpression ktBinaryExpression, KotlinType kotlinType, DataFlowInfo dataFlowInfo, KtExpression ktExpression, KotlinType kotlinType2, KotlinType kotlinType3) {
        KotlinType kotlinTypeRefineTypeByPropertyInType;
        if (resolutionContext == null) {
            $$$reportNull$$$0(25);
        }
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(26);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(27);
        }
        if (kotlinType == null || kotlinType3 == null || (kotlinTypeRefineTypeByPropertyInType = refineTypeByPropertyInType(resolutionContext.trace.getContext(), ktExpression, kotlinType2)) == null || kotlinType3 == kotlinTypeRefineTypeByPropertyInType || TypeUtils.equalTypes(kotlinType3, kotlinTypeRefineTypeByPropertyInType)) {
            return;
        }
        Ref<Boolean> refCreate = Ref.create(false);
        this.components.dataFlowAnalyzer.checkType(kotlinType, ktBinaryExpression, resolutionContext.replaceExpectedType(kotlinTypeRefineTypeByPropertyInType).replaceDataFlowInfo(dataFlowInfo).replaceCallPosition(new CallPosition.PropertyAssignment(ktExpression, false)), refCreate, false);
        if (refCreate.get().booleanValue()) {
            resolutionContext.trace.report(Errors.TYPE_MISMATCH_WARNING.on(ktBinaryExpression, kotlinTypeRefineTypeByPropertyInType, kotlinType));
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitExpression(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(28);
        }
        return this.facade.getTypeInfo(ktExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitKtElement(KtElement ktElement, ExpressionTypingContext expressionTypingContext) {
        if (ktElement == null) {
            $$$reportNull$$$0(29);
        }
        expressionTypingContext.trace.report(Errors.UNSUPPORTED.on(ktElement, "in a block"));
        return TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitWhileExpression(KtWhileExpression ktWhileExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktWhileExpression == null) {
            $$$reportNull$$$0(30);
        }
        return this.controlStructures.visitWhileExpression(ktWhileExpression, expressionTypingContext, true);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitDoWhileExpression(KtDoWhileExpression ktDoWhileExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktDoWhileExpression == null) {
            $$$reportNull$$$0(31);
        }
        return this.controlStructures.visitDoWhileExpression(ktDoWhileExpression, expressionTypingContext, true);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitForExpression(KtForExpression ktForExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktForExpression == null) {
            $$$reportNull$$$0(32);
        }
        return this.controlStructures.visitForExpression(ktForExpression, expressionTypingContext, true);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitAnnotatedExpression(KtAnnotatedExpression ktAnnotatedExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktAnnotatedExpression == null) {
            $$$reportNull$$$0(33);
        }
        return this.basic.visitAnnotatedExpression(ktAnnotatedExpression, expressionTypingContext, true);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitIfExpression(KtIfExpression ktIfExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktIfExpression == null) {
            $$$reportNull$$$0(34);
        }
        return this.controlStructures.visitIfExpression(ktIfExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitWhenExpression(KtWhenExpression ktWhenExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(35);
        }
        return this.patterns.visitWhenExpression(ktWhenExpression, expressionTypingContext, true);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitBlockExpression(KtBlockExpression ktBlockExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(36);
        }
        return this.components.expressionTypingServices.getBlockReturnedType(ktBlockExpression, expressionTypingContext, true);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitLabeledExpression(KtLabeledExpression ktLabeledExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktLabeledExpression == null) {
            $$$reportNull$$$0(37);
        }
        return this.basic.visitLabeledExpression(ktLabeledExpression, expressionTypingContext, true);
    }
}
