package org.jetbrains.kotlin.types.expressions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtBreakExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtCatchClause;
import org.jetbrains.kotlin.psi.KtContinueExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDoWhileExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFinallySection;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtLoopExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtThrowExpression;
import org.jetbrains.kotlin.psi.KtTreeVisitor;
import org.jetbrains.kotlin.psi.KtTryExpression;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtWhileExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.ModifierCheckerCore;
import org.jetbrains.kotlin.resolve.ModifiersChecker;
import org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.tower.KotlinResolutionCallbacksImpl;
import org.jetbrains.kotlin.resolve.calls.tower.LambdaContextInfo;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.checkers.TrailingCommaChecker;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.serialization.deserialization.SuspendFunctionTypeUtilKt;
import org.jetbrains.kotlin.types.CommonSupertypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;

/* JADX INFO: loaded from: classes6.dex */
public class ControlStructureTypingVisitor extends ExpressionTypingVisitor {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* JADX WARN: Code duplicated, block: B:13:0x0023  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 3 && i != 5 && i != 6 && i != 7 && i != 14 && i != 26 && i != 36) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 2 && i != 3 && i != 5 && i != 6 && i != 7 && i != 14 && i != 26 && i != 36) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 12:
            case 35:
                objArr[0] = "context";
                break;
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 14:
            case 22:
            case 23:
            case 24:
            case 26:
            case 36:
                objArr[0] = "org/jetbrains/kotlin/types/expressions/ControlStructureTypingVisitor";
                break;
            case 4:
            case 13:
                objArr[0] = "ifExpression";
                break;
            case 8:
                objArr[0] = "presentBranch";
                break;
            case 9:
                objArr[0] = "presentScope";
                break;
            case 10:
                objArr[0] = "presentInfo";
                break;
            case 11:
                objArr[0] = "otherInfo";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 28:
            case 29:
            case 31:
            case 32:
                objArr[0] = "expression";
                break;
            case 20:
            case 21:
            case 25:
                objArr[0] = "tryExpression";
                break;
            case 27:
                objArr[0] = "catchParameter";
                break;
            case 30:
                objArr[0] = "containingFunInfo";
                break;
            case 33:
                objArr[0] = "descriptor";
                break;
            case 34:
                objArr[0] = "function";
                break;
            default:
                objArr[0] = "facade";
                break;
        }
        if (i == 2 || i == 3) {
            objArr[1] = "checkCondition";
        } else if (i == 5 || i == 6 || i == 7) {
            objArr[1] = "processIfBranches";
        } else if (i == 14) {
            objArr[1] = "getTypeInfoWhenOnlyOneBranchIsPresent";
        } else if (i == 26) {
            objArr[1] = "processTryBranchesWithNewDataFlowAlgorithm";
        } else if (i != 36) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                    objArr[1] = "processTryBranches";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/types/expressions/ControlStructureTypingVisitor";
                    break;
            }
        } else {
            objArr[1] = "getFunctionExpectedReturnType";
        }
        switch (i) {
            case 1:
                objArr[2] = "checkCondition";
                break;
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 14:
            case 22:
            case 23:
            case 24:
            case 26:
            case 36:
                break;
            case 4:
                objArr[2] = "visitIfExpression";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[2] = "getTypeInfoWhenOnlyOneBranchIsPresent";
                break;
            case 15:
                objArr[2] = "visitWhileExpression";
                break;
            case 16:
                objArr[2] = "containsJumpOutOfLoop";
                break;
            case 17:
                objArr[2] = "visitDoWhileExpression";
                break;
            case 18:
                objArr[2] = "visitForExpression";
                break;
            case 19:
                objArr[2] = "visitTryExpression";
                break;
            case 20:
                objArr[2] = "resolveTryExpressionWithNewInference";
                break;
            case 21:
                objArr[2] = "processTryBranches";
                break;
            case 25:
                objArr[2] = "processTryBranchesWithNewDataFlowAlgorithm";
                break;
            case 27:
                objArr[2] = "resolveAndCheckCatchParameter";
                break;
            case 28:
                objArr[2] = "visitThrowExpression";
                break;
            case 29:
                objArr[2] = "visitReturnExpression";
                break;
            case 30:
                objArr[2] = "isClassInitializer";
                break;
            case 31:
                objArr[2] = "visitBreakExpression";
                break;
            case 32:
                objArr[2] = "visitContinueExpression";
                break;
            case 33:
            case 34:
            case 35:
                objArr[2] = "getFunctionExpectedReturnType";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 5 && i != 6 && i != 7 && i != 14 && i != 26 && i != 36) {
            switch (i) {
                case 22:
                case 23:
                case 24:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ Boolean lambda$isCatchBranchesReturnsNothing$3(Boolean bool) {
        return bool;
    }

    static /* synthetic */ Boolean lambda$processTryBranchesWithNewDataFlowAlgorithm$1(Boolean bool) {
        return bool;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected ControlStructureTypingVisitor(ExpressionTypingInternals expressionTypingInternals) {
        super(expressionTypingInternals);
        if (expressionTypingInternals == null) {
            $$$reportNull$$$0(0);
        }
    }

    private DataFlowInfo checkCondition(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext) {
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(1);
        }
        if (ktExpression != null) {
            ExpressionTypingContext expressionTypingContextReplaceContextDependency = expressionTypingContext.replaceExpectedType(this.components.builtIns.getBooleanType()).replaceContextDependency(ContextDependency.INDEPENDENT);
            DataFlowInfo dataFlowInfo = this.components.dataFlowAnalyzer.checkType(this.facade.getTypeInfo(ktExpression, expressionTypingContextReplaceContextDependency), ktExpression, expressionTypingContextReplaceContextDependency).getDataFlowInfo();
            if (dataFlowInfo == null) {
                $$$reportNull$$$0(2);
            }
            return dataFlowInfo;
        }
        DataFlowInfo dataFlowInfo2 = expressionTypingContext.dataFlowInfo;
        if (dataFlowInfo2 == null) {
            $$$reportNull$$$0(3);
        }
        return dataFlowInfo2;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitIfExpression(KtIfExpression ktIfExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktIfExpression == null) {
            $$$reportNull$$$0(4);
        }
        KtExpression condition = ktIfExpression.getCondition();
        DataFlowInfo dataFlowInfoCheckCondition = checkCondition(condition, expressionTypingContext);
        boolean z = condition != null && containsJumpOutOfLoop(condition, expressionTypingContext);
        KtExpression ktExpression = ktIfExpression.getElse();
        KtExpression then = ktIfExpression.getThen();
        LexicalWritableScope lexicalWritableScopeNewWritableScopeImpl = ExpressionTypingUtils.newWritableScopeImpl(expressionTypingContext, LexicalScopeKind.THEN, this.components.overloadChecker);
        LexicalWritableScope lexicalWritableScopeNewWritableScopeImpl2 = ExpressionTypingUtils.newWritableScopeImpl(expressionTypingContext, LexicalScopeKind.ELSE, this.components.overloadChecker);
        DataFlowInfo dataFlowInfoAnd = this.components.dataFlowAnalyzer.extractDataFlowInfoFromCondition(condition, true, expressionTypingContext).and(dataFlowInfoCheckCondition);
        DataFlowInfo dataFlowInfoAnd2 = this.components.dataFlowAnalyzer.extractDataFlowInfoFromCondition(condition, false, expressionTypingContext).and(dataFlowInfoCheckCondition);
        if (ktExpression == null) {
            if (then != null) {
                KotlinTypeInfo typeInfoWhenOnlyOneBranchIsPresent = getTypeInfoWhenOnlyOneBranchIsPresent(then, lexicalWritableScopeNewWritableScopeImpl, dataFlowInfoAnd, dataFlowInfoAnd2, expressionTypingContext, ktIfExpression);
                return typeInfoWhenOnlyOneBranchIsPresent.getJumpOutPossible() ? typeInfoWhenOnlyOneBranchIsPresent.replaceJumpOutPossible(true).replaceJumpFlowInfo(dataFlowInfoCheckCondition) : typeInfoWhenOnlyOneBranchIsPresent;
            }
            return TypeInfoFactoryKt.createTypeInfo(this.components.builtIns.getUnitType(), dataFlowInfoAnd.or(dataFlowInfoAnd2));
        }
        if (then == null) {
            return getTypeInfoWhenOnlyOneBranchIsPresent(ktExpression, lexicalWritableScopeNewWritableScopeImpl2, dataFlowInfoAnd2, dataFlowInfoAnd, expressionTypingContext, ktIfExpression);
        }
        KtPsiFactory ktPsiFactory = new KtPsiFactory(ktIfExpression.getProject(), false);
        Call callCreateCallForSpecialConstruction = ControlStructureTypingUtils.createCallForSpecialConstruction(ktIfExpression, ktIfExpression, Lists.newArrayList(ktPsiFactory.wrapInABlockWrapper(then), ktPsiFactory.wrapInABlockWrapper(ktExpression)));
        return processIfBranches(ktIfExpression, expressionTypingContext, dataFlowInfoCheckCondition, z, ktExpression, then, this.components.controlStructureTypingUtils.resolveSpecialConstructionAsCall(callCreateCallForSpecialConstruction, ControlStructureTypingUtils.ResolveConstruct.IF, Lists.newArrayList("thenBranch", "elseBranch"), Lists.newArrayList(false, false), expressionTypingContext, ControlStructureTypingUtils.createDataFlowInfoForArgumentsForIfCall(callCreateCallForSpecialConstruction, dataFlowInfoCheckCondition, dataFlowInfoAnd, dataFlowInfoAnd2)));
    }

    private KotlinTypeInfo processIfBranches(KtIfExpression ktIfExpression, ExpressionTypingContext expressionTypingContext, DataFlowInfo dataFlowInfo, boolean z, KtExpression ktExpression, KtExpression ktExpression2, ResolvedCall<FunctionDescriptor> resolvedCall) {
        boolean jumpOutPossible;
        DataFlowInfo dataFlowInfoOr;
        DataFlowInfo dataFlowInfo2;
        BindingContext context = expressionTypingContext.trace.getContext();
        KotlinTypeInfo recordedTypeInfo = BindingContextUtils.getRecordedTypeInfo(ktExpression2, context);
        KotlinTypeInfo recordedTypeInfo2 = BindingContextUtils.getRecordedTypeInfo(ktExpression, context);
        ArgumentTypeResolver.isFunctionLiteralOrCallableReference(ktExpression2, expressionTypingContext);
        ArgumentTypeResolver.isFunctionLiteralOrCallableReference(ktExpression2, expressionTypingContext);
        if (recordedTypeInfo == null && recordedTypeInfo2 == null) {
            KotlinTypeInfo kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
            if (kotlinTypeInfoNoTypeInfo == null) {
                $$$reportNull$$$0(5);
            }
            return kotlinTypeInfoNoTypeInfo;
        }
        KotlinType returnType = ((FunctionDescriptor) resolvedCall.getResultingDescriptor()).getReturnType();
        if (recordedTypeInfo2 == null) {
            jumpOutPossible = recordedTypeInfo.getJumpOutPossible() | z;
            dataFlowInfo2 = recordedTypeInfo.getDataFlowInfo();
        } else if (recordedTypeInfo == null) {
            jumpOutPossible = recordedTypeInfo2.getJumpOutPossible() | z;
            dataFlowInfo2 = recordedTypeInfo2.getDataFlowInfo();
        } else {
            KotlinType type = recordedTypeInfo.getType();
            KotlinType type2 = recordedTypeInfo2.getType();
            DataFlowInfo dataFlowInfo3 = recordedTypeInfo.getDataFlowInfo();
            DataFlowInfo dataFlowInfo4 = recordedTypeInfo2.getDataFlowInfo();
            if (returnType != null && type != null && type2 != null) {
                DataFlowValue dataFlowValueCreateDataFlowValue = this.components.dataFlowValueFactory.createDataFlowValue(ktIfExpression, returnType, expressionTypingContext);
                dataFlowInfo3 = dataFlowInfo3.assign(dataFlowValueCreateDataFlowValue, this.components.dataFlowValueFactory.createDataFlowValue(ktExpression2, type, expressionTypingContext), this.components.languageVersionSettings);
                dataFlowInfo4 = dataFlowInfo4.assign(dataFlowValueCreateDataFlowValue, this.components.dataFlowValueFactory.createDataFlowValue(ktExpression, type2, expressionTypingContext), this.components.languageVersionSettings);
            }
            boolean z2 = false;
            jumpOutPossible = (recordedTypeInfo.getJumpOutPossible() || recordedTypeInfo2.getJumpOutPossible()) | z;
            boolean z3 = type != null && KotlinBuiltIns.isNothing(type);
            if (type2 != null && KotlinBuiltIns.isNothing(type2)) {
                z2 = true;
            }
            if (type == null && type2 == null) {
                dataFlowInfoOr = dataFlowInfo3.or(dataFlowInfo4);
            } else if (type == null || (z3 && !z2)) {
                dataFlowInfoOr = dataFlowInfo4;
            } else {
                dataFlowInfoOr = (type2 == null || (z2 && !z3)) ? dataFlowInfo3 : dataFlowInfo3.or(dataFlowInfo4);
            }
            if ((type == null && z2) || (type2 == null && z3)) {
                KotlinTypeInfo kotlinTypeInfoNoTypeInfo2 = TypeInfoFactoryKt.noTypeInfo(dataFlowInfoOr);
                if (kotlinTypeInfoNoTypeInfo2 == null) {
                    $$$reportNull$$$0(6);
                }
                return kotlinTypeInfoNoTypeInfo2;
            }
            dataFlowInfo2 = dataFlowInfoOr;
        }
        KotlinType kotlinTypeCheckType = this.components.dataFlowAnalyzer.checkType(returnType, ktIfExpression, expressionTypingContext);
        if (z) {
            dataFlowInfo = expressionTypingContext.dataFlowInfo;
        }
        KotlinTypeInfo kotlinTypeInfoCreateTypeInfo = TypeInfoFactoryKt.createTypeInfo(kotlinTypeCheckType, dataFlowInfo2, jumpOutPossible, dataFlowInfo);
        if (kotlinTypeInfoCreateTypeInfo == null) {
            $$$reportNull$$$0(7);
        }
        return kotlinTypeInfoCreateTypeInfo;
    }

    private KotlinTypeInfo getTypeInfoWhenOnlyOneBranchIsPresent(KtExpression ktExpression, LexicalWritableScope lexicalWritableScope, DataFlowInfo dataFlowInfo, DataFlowInfo dataFlowInfo2, ExpressionTypingContext expressionTypingContext, KtIfExpression ktIfExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(8);
        }
        if (lexicalWritableScope == null) {
            $$$reportNull$$$0(9);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(10);
        }
        if (dataFlowInfo2 == null) {
            $$$reportNull$$$0(11);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(12);
        }
        if (ktIfExpression == null) {
            $$$reportNull$$$0(13);
        }
        KotlinTypeInfo blockReturnedTypeWithWritableScope = this.components.expressionTypingServices.getBlockReturnedTypeWithWritableScope(lexicalWritableScope, Collections.singletonList(ktExpression), CoercionStrategy.NO_COERCION, expressionTypingContext.replaceDataFlowInfo(dataFlowInfo).replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT));
        KotlinType type = blockReturnedTypeWithWritableScope.getType();
        if (type == null || !KotlinBuiltIns.isNothing(type)) {
            dataFlowInfo2 = blockReturnedTypeWithWritableScope.getDataFlowInfo().or(dataFlowInfo2);
        }
        KotlinTypeInfo kotlinTypeInfoReplaceDataFlowInfo = this.components.dataFlowAnalyzer.checkType(blockReturnedTypeWithWritableScope.replaceType(this.components.builtIns.getUnitType()), ktIfExpression, expressionTypingContext).replaceDataFlowInfo(dataFlowInfo2);
        if (kotlinTypeInfoReplaceDataFlowInfo == null) {
            $$$reportNull$$$0(14);
        }
        return kotlinTypeInfoReplaceDataFlowInfo;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitWhileExpression(KtWhileExpression ktWhileExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktWhileExpression == null) {
            $$$reportNull$$$0(15);
        }
        return visitWhileExpression(ktWhileExpression, expressionTypingContext, false);
    }

    public KotlinTypeInfo visitWhileExpression(KtWhileExpression ktWhileExpression, ExpressionTypingContext expressionTypingContext, boolean z) {
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo;
        if (!z) {
            return this.components.dataFlowAnalyzer.illegalStatementType(ktWhileExpression, expressionTypingContext, this.facade);
        }
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT);
        PreliminaryLoopVisitor preliminaryLoopVisitorVisitLoop = PreliminaryLoopVisitor.visitLoop(ktWhileExpression);
        ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = expressionTypingContextReplaceContextDependency.replaceDataFlowInfo(preliminaryLoopVisitorVisitLoop.clearDataFlowInfoForAssignedLocalVariables(expressionTypingContextReplaceContextDependency.dataFlowInfo, this.components.languageVersionSettings));
        KtExpression condition = ktWhileExpression.getCondition();
        DataFlowInfo dataFlowInfoCheckCondition = checkCondition(condition, expressionTypingContextReplaceDataFlowInfo);
        KtExpression body = ktWhileExpression.getBody();
        DataFlowInfo dataFlowInfoAnd = this.components.dataFlowAnalyzer.extractDataFlowInfoFromCondition(condition, true, expressionTypingContextReplaceDataFlowInfo).and(dataFlowInfoCheckCondition);
        if (body != null) {
            kotlinTypeInfoNoTypeInfo = this.components.expressionTypingServices.getBlockReturnedTypeWithWritableScope(ExpressionTypingUtils.newWritableScopeImpl(expressionTypingContextReplaceDataFlowInfo, LexicalScopeKind.WHILE_BODY, this.components.overloadChecker), Collections.singletonList(body), CoercionStrategy.NO_COERCION, expressionTypingContextReplaceDataFlowInfo.replaceDataFlowInfo(dataFlowInfoAnd));
        } else {
            kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(dataFlowInfoAnd);
        }
        if (!containsJumpOutOfLoop(ktWhileExpression, expressionTypingContextReplaceDataFlowInfo)) {
            dataFlowInfoCheckCondition = this.components.dataFlowAnalyzer.extractDataFlowInfoFromCondition(condition, false, expressionTypingContextReplaceDataFlowInfo).and(dataFlowInfoCheckCondition);
        }
        if (body != null && KtPsiUtil.isTrueConstant(condition)) {
            dataFlowInfoCheckCondition = dataFlowInfoCheckCondition.and(preliminaryLoopVisitorVisitLoop.clearDataFlowInfoForAssignedLocalVariables(kotlinTypeInfoNoTypeInfo.getJumpFlowInfo(), this.components.languageVersionSettings));
        }
        return this.components.dataFlowAnalyzer.checkType(kotlinTypeInfoNoTypeInfo.replaceType(this.components.builtIns.getUnitType()), ktWhileExpression, expressionTypingContext).replaceDataFlowInfo(dataFlowInfoCheckCondition);
    }

    private boolean containsJumpOutOfLoop(final KtExpression ktExpression, final ExpressionTypingContext expressionTypingContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(16);
        }
        final boolean[] zArr = {false};
        ktExpression.accept(new KtTreeVisitor<List<KtLoopExpression>>() { // from class: org.jetbrains.kotlin.types.expressions.ControlStructureTypingVisitor.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "expression";
                } else if (i != 2) {
                    objArr[0] = "breakExpression";
                } else {
                    objArr[0] = "loopExpression";
                }
                objArr[1] = "org/jetbrains/kotlin/types/expressions/ControlStructureTypingVisitor$1";
                if (i == 1) {
                    objArr[2] = "visitContinueExpression";
                } else if (i != 2) {
                    objArr[2] = "visitBreakExpression";
                } else {
                    objArr[2] = "visitLoopExpression";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public Void visitBreakExpression(KtBreakExpression ktBreakExpression, List<KtLoopExpression> list) {
                if (ktBreakExpression == null) {
                    $$$reportNull$$$0(0);
                }
                KtSimpleNameExpression targetLabel = ktBreakExpression.getTargetLabel();
                PsiElement psiElement = targetLabel != null ? (PsiElement) expressionTypingContext.trace.get(BindingContext.LABEL_TARGET, targetLabel) : null;
                if (list.isEmpty() || psiElement == ktExpression || (targetLabel == null && list.get(list.size() - 1) == ktExpression)) {
                    zArr[0] = true;
                }
                return null;
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public Void visitContinueExpression(KtContinueExpression ktContinueExpression, List<KtLoopExpression> list) {
                if (ktContinueExpression == null) {
                    $$$reportNull$$$0(1);
                }
                KtSimpleNameExpression targetLabel = ktContinueExpression.getTargetLabel();
                if (targetLabel == null) {
                    return null;
                }
                PsiElement psiElement = (PsiElement) expressionTypingContext.trace.get(BindingContext.LABEL_TARGET, targetLabel);
                if (!(psiElement instanceof KtLoopExpression) || list.contains(psiElement)) {
                    return null;
                }
                zArr[0] = true;
                return null;
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitor
            public Void visitLoopExpression(KtLoopExpression ktLoopExpression, List<KtLoopExpression> list) {
                if (ktLoopExpression == null) {
                    $$$reportNull$$$0(2);
                }
                ArrayList arrayListNewArrayList = Lists.newArrayList(list);
                arrayListNewArrayList.add(ktLoopExpression);
                return (Void) super.visitLoopExpression(ktLoopExpression, arrayListNewArrayList);
            }
        }, ktExpression instanceof KtLoopExpression ? Lists.newArrayList((KtLoopExpression) ktExpression) : Lists.newArrayList());
        return zArr[0];
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitDoWhileExpression(KtDoWhileExpression ktDoWhileExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktDoWhileExpression == null) {
            $$$reportNull$$$0(17);
        }
        return visitDoWhileExpression(ktDoWhileExpression, expressionTypingContext, false);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public KotlinTypeInfo visitDoWhileExpression(KtDoWhileExpression ktDoWhileExpression, ExpressionTypingContext expressionTypingContext, boolean z) {
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo;
        List<KtExpression> listSingletonList;
        LexicalScope lexicalScope;
        DataFlowInfo dataFlowInfoAnd;
        if (!z) {
            return this.components.dataFlowAnalyzer.illegalStatementType(ktDoWhileExpression, expressionTypingContext, this.facade);
        }
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT);
        KtExpression body = ktDoWhileExpression.getBody();
        LexicalScope lexicalScope2 = expressionTypingContextReplaceContextDependency.scope;
        PreliminaryLoopVisitor preliminaryLoopVisitorVisitLoop = PreliminaryLoopVisitor.visitLoop(ktDoWhileExpression);
        ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = expressionTypingContextReplaceContextDependency.replaceDataFlowInfo(preliminaryLoopVisitorVisitLoop.clearDataFlowInfoForAssignedLocalVariables(expressionTypingContextReplaceContextDependency.dataFlowInfo, this.components.languageVersionSettings));
        if (body instanceof KtLambdaExpression) {
            kotlinTypeInfoNoTypeInfo = this.facade.getTypeInfo(body, expressionTypingContextReplaceDataFlowInfo);
            lexicalScope = lexicalScope2;
        } else if (body != null) {
            LexicalWritableScope lexicalWritableScopeNewWritableScopeImpl = ExpressionTypingUtils.newWritableScopeImpl(expressionTypingContextReplaceDataFlowInfo, LexicalScopeKind.DO_WHILE_BODY, this.components.overloadChecker);
            if (body instanceof KtBlockExpression) {
                listSingletonList = ((KtBlockExpression) body).getStatements();
            } else {
                listSingletonList = Collections.singletonList(body);
            }
            kotlinTypeInfoNoTypeInfo = this.components.expressionTypingServices.getBlockReturnedTypeWithWritableScope(lexicalWritableScopeNewWritableScopeImpl, listSingletonList, CoercionStrategy.NO_COERCION, expressionTypingContextReplaceDataFlowInfo);
            lexicalScope = lexicalWritableScopeNewWritableScopeImpl;
        } else {
            kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContextReplaceDataFlowInfo);
            lexicalScope = lexicalScope2;
        }
        KtExpression condition = ktDoWhileExpression.getCondition();
        DataFlowInfo dataFlowInfoCheckCondition = checkCondition(condition, expressionTypingContextReplaceDataFlowInfo.replaceScope(lexicalScope));
        if (!containsJumpOutOfLoop(ktDoWhileExpression, expressionTypingContextReplaceDataFlowInfo)) {
            dataFlowInfoAnd = this.components.dataFlowAnalyzer.extractDataFlowInfoFromCondition(condition, false, expressionTypingContextReplaceDataFlowInfo).and(dataFlowInfoCheckCondition);
        } else {
            dataFlowInfoAnd = expressionTypingContextReplaceDataFlowInfo.dataFlowInfo;
        }
        if (body != null) {
            dataFlowInfoAnd = dataFlowInfoAnd.and(preliminaryLoopVisitorVisitLoop.clearDataFlowInfoForAssignedLocalVariables(kotlinTypeInfoNoTypeInfo.getJumpFlowInfo(), this.components.languageVersionSettings));
        }
        return this.components.dataFlowAnalyzer.checkType(kotlinTypeInfoNoTypeInfo.replaceType(this.components.builtIns.getUnitType()), ktDoWhileExpression, expressionTypingContext).replaceDataFlowInfo(dataFlowInfoAnd);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitForExpression(KtForExpression ktForExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktForExpression == null) {
            $$$reportNull$$$0(18);
        }
        return visitForExpression(ktForExpression, expressionTypingContext, false);
    }

    public KotlinTypeInfo visitForExpression(KtForExpression ktForExpression, ExpressionTypingContext expressionTypingContext, boolean z) {
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo;
        if (!z) {
            return this.components.dataFlowAnalyzer.illegalStatementType(ktForExpression, expressionTypingContext, this.facade);
        }
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT);
        ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = expressionTypingContextReplaceContextDependency.replaceDataFlowInfo(PreliminaryLoopVisitor.visitLoop(ktForExpression).clearDataFlowInfoForAssignedLocalVariables(expressionTypingContextReplaceContextDependency.dataFlowInfo, this.components.languageVersionSettings));
        KtExpression loopRange = ktForExpression.getLoopRange();
        KotlinType kotlinTypeCreateErrorType = null;
        if (loopRange != null) {
            ExpressionReceiver expressionReceiver = ExpressionTypingUtils.getExpressionReceiver(this.facade, loopRange, expressionTypingContextReplaceDataFlowInfo);
            kotlinTypeInfoNoTypeInfo = this.facade.getTypeInfo(loopRange, expressionTypingContextReplaceDataFlowInfo);
            if (expressionReceiver != null) {
                kotlinTypeCreateErrorType = this.components.forLoopConventionsChecker.checkIterableConvention(expressionReceiver, expressionTypingContextReplaceDataFlowInfo);
            }
        } else {
            kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContextReplaceDataFlowInfo);
        }
        KotlinTypeInfo kotlinTypeInfo = kotlinTypeInfoNoTypeInfo;
        LexicalWritableScope lexicalWritableScopeNewWritableScopeImpl = ExpressionTypingUtils.newWritableScopeImpl(expressionTypingContextReplaceDataFlowInfo, LexicalScopeKind.FOR, this.components.overloadChecker);
        KtParameter loopParameter = ktForExpression.getLoopParameter();
        if (loopParameter != null) {
            VariableDescriptor variableDescriptorCreateLoopParameterDescriptor = createLoopParameterDescriptor(loopParameter, kotlinTypeCreateErrorType, expressionTypingContextReplaceDataFlowInfo);
            ModifiersChecker.ModifiersCheckingProcedure modifiersCheckingProcedureWithTrace = this.components.modifiersChecker.withTrace(expressionTypingContextReplaceDataFlowInfo.trace);
            modifiersCheckingProcedureWithTrace.checkModifiersForLocalDeclaration(loopParameter, variableDescriptorCreateLoopParameterDescriptor);
            this.components.identifierChecker.checkDeclaration(loopParameter, expressionTypingContextReplaceDataFlowInfo.trace);
            lexicalWritableScopeNewWritableScopeImpl.addVariableDescriptor(variableDescriptorCreateLoopParameterDescriptor);
            KtDestructuringDeclaration destructuringDeclaration = loopParameter.getDestructuringDeclaration();
            if (destructuringDeclaration != null) {
                if (kotlinTypeCreateErrorType == null) {
                    kotlinTypeCreateErrorType = ErrorUtils.createErrorType(ErrorTypeKind.NO_TYPE_FOR_LOOP_RANGE, new String[0]);
                }
                TransientReceiver transientReceiver = new TransientReceiver(kotlinTypeCreateErrorType);
                this.components.annotationResolver.resolveAnnotationsWithArguments(lexicalWritableScopeNewWritableScopeImpl, loopParameter.getModifierList(), expressionTypingContextReplaceDataFlowInfo.trace);
                this.components.destructuringDeclarationResolver.defineLocalVariablesFromDestructuringDeclaration(lexicalWritableScopeNewWritableScopeImpl, destructuringDeclaration, transientReceiver, loopRange, expressionTypingContextReplaceDataFlowInfo);
                modifiersCheckingProcedureWithTrace.checkModifiersForDestructuringDeclaration(destructuringDeclaration);
                this.components.identifierChecker.checkDeclaration(destructuringDeclaration, expressionTypingContextReplaceDataFlowInfo.trace);
            }
        }
        KtExpression body = ktForExpression.getBody();
        return this.components.dataFlowAnalyzer.checkType((body != null ? this.components.expressionTypingServices.getBlockReturnedTypeWithWritableScope(lexicalWritableScopeNewWritableScopeImpl, Collections.singletonList(body), CoercionStrategy.NO_COERCION, expressionTypingContextReplaceDataFlowInfo.replaceDataFlowInfo(kotlinTypeInfo.getDataFlowInfo())) : kotlinTypeInfo).replaceType(this.components.builtIns.getUnitType()), ktForExpression, expressionTypingContext).replaceDataFlowInfo(kotlinTypeInfo.getDataFlowInfo());
    }

    private VariableDescriptor createLoopParameterDescriptor(KtParameter ktParameter, KotlinType kotlinType, ExpressionTypingContext expressionTypingContext) {
        VariableDescriptor variableDescriptorResolveLocalVariableDescriptor;
        this.components.modifiersChecker.withTrace(expressionTypingContext.trace).checkParameterHasNoValOrVar(ktParameter, Errors.VAL_OR_VAR_ON_LOOP_PARAMETER);
        KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
        if (ktTypeReferenceMo7396getTypeReference != null) {
            variableDescriptorResolveLocalVariableDescriptor = this.components.descriptorResolver.resolveLocalVariableDescriptor(expressionTypingContext.scope, ktParameter, expressionTypingContext.trace);
            KotlinType type = variableDescriptorResolveLocalVariableDescriptor.getType();
            if (kotlinType != null && !KotlinTypeChecker.DEFAULT.isSubtypeOf(kotlinType, type)) {
                expressionTypingContext.trace.report(Errors.TYPE_MISMATCH_IN_FOR_LOOP.on(ktTypeReferenceMo7396getTypeReference, kotlinType, type));
            }
        } else {
            if (kotlinType == null) {
                kotlinType = ErrorUtils.createErrorType(ErrorTypeKind.NO_TYPE_FOR_LOOP_PARAMETER, new String[0]);
            }
            variableDescriptorResolveLocalVariableDescriptor = this.components.descriptorResolver.resolveLocalVariableDescriptor(ktParameter, kotlinType, expressionTypingContext.trace, expressionTypingContext.scope);
        }
        ExpressionTypingUtils.checkVariableShadowing(expressionTypingContext.scope, expressionTypingContext.trace, variableDescriptorResolveLocalVariableDescriptor);
        return variableDescriptorResolveLocalVariableDescriptor;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitTryExpression(KtTryExpression ktTryExpression, final ExpressionTypingContext expressionTypingContext) {
        if (ktTryExpression == null) {
            $$$reportNull$$$0(19);
        }
        ktTryExpression.getCatchClauses().forEach(new Consumer() { // from class: org.jetbrains.kotlin.types.expressions.ControlStructureTypingVisitor$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ControlStructureTypingVisitor.lambda$visitTryExpression$0(expressionTypingContext, (KtCatchClause) obj);
            }
        });
        if (expressionTypingContext.languageVersionSettings.supportsFeature(LanguageFeature.NewInference)) {
            return resolveTryExpressionWithNewInference(ktTryExpression, expressionTypingContext);
        }
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = expressionTypingContext.replaceContextDependency(ContextDependency.INDEPENDENT);
        KtBlockExpression tryBlock = ktTryExpression.getTryBlock();
        List<KtCatchClause> catchClauses = ktTryExpression.getCatchClauses();
        KtFinallySection finallyBlock = ktTryExpression.getFinallyBlock();
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        for (KtCatchClause ktCatchClause : catchClauses) {
            KtParameter catchParameter = ktCatchClause.getCatchParameter();
            KtExpression catchBody = ktCatchClause.getCatchBody();
            if (catchParameter != null) {
                VariableDescriptor variableDescriptorResolveAndCheckCatchParameter = resolveAndCheckCatchParameter(catchParameter, expressionTypingContextReplaceContextDependency);
                if (catchBody != null) {
                    LexicalWritableScope lexicalWritableScopeNewWritableScopeImpl = ExpressionTypingUtils.newWritableScopeImpl(expressionTypingContextReplaceContextDependency, LexicalScopeKind.CATCH, this.components.overloadChecker);
                    lexicalWritableScopeNewWritableScopeImpl.addVariableDescriptor(variableDescriptorResolveAndCheckCatchParameter);
                    KotlinType type = this.facade.getTypeInfo(catchBody, (ExpressionTypingContext) expressionTypingContextReplaceContextDependency.replaceScope(lexicalWritableScopeNewWritableScopeImpl)).getType();
                    if (type != null) {
                        arrayList.add(type);
                        if (KotlinBuiltIns.isNothing(type)) {
                        }
                    }
                }
            }
            z = false;
        }
        KotlinTypeInfo typeInfo = this.facade.getTypeInfo(tryBlock, expressionTypingContextReplaceContextDependency);
        ExpressionTypingContext cleanedContextFromTryWithAssignmentsToVar = getCleanedContextFromTryWithAssignmentsToVar(ktTryExpression, z, expressionTypingContextReplaceContextDependency);
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(cleanedContextFromTryWithAssignmentsToVar);
        if (finallyBlock != null) {
            kotlinTypeInfoNoTypeInfo = this.facade.getTypeInfo(finallyBlock.getFinalExpression(), cleanedContextFromTryWithAssignmentsToVar);
        } else if (z) {
            kotlinTypeInfoNoTypeInfo = typeInfo;
        }
        KotlinType type2 = typeInfo.getType();
        if (type2 != null) {
            arrayList.add(type2);
        }
        if (arrayList.isEmpty()) {
            return kotlinTypeInfoNoTypeInfo.clearType();
        }
        return kotlinTypeInfoNoTypeInfo.replaceType(CommonSupertypes.commonSupertype(arrayList));
    }

    static /* synthetic */ void lambda$visitTryExpression$0(ExpressionTypingContext expressionTypingContext, KtCatchClause ktCatchClause) {
        KtParameterList parameterList = ktCatchClause.getParameterList();
        if (parameterList == null || parameterList.getStub() != 0) {
            return;
        }
        TrailingCommaChecker.INSTANCE.check(parameterList.getTrailingComma(), expressionTypingContext.trace, expressionTypingContext.languageVersionSettings);
    }

    private KotlinTypeInfo resolveTryExpressionWithNewInference(KtTryExpression ktTryExpression, ExpressionTypingContext expressionTypingContext) {
        MutableDataFlowInfoForArguments mutableDataFlowInfoForArgumentsCreateDataFlowInfoForArgumentsOfTryCall;
        if (ktTryExpression == null) {
            $$$reportNull$$$0(20);
        }
        KtBlockExpression tryBlock = ktTryExpression.getTryBlock();
        List<KtCatchClause> catchClauses = ktTryExpression.getCatchClauses();
        KtFinallySection finallyBlock = ktTryExpression.getFinallyBlock();
        DataFlowInfo dataFlowInfo = expressionTypingContext.dataFlowInfo;
        ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = expressionTypingContext.replaceDataFlowInfo(PreliminaryLoopVisitor.visitTryBlock(ktTryExpression).clearDataFlowInfoForAssignedLocalVariables(dataFlowInfo, this.components.languageVersionSettings));
        DataFlowInfo dataFlowInfo2 = expressionTypingContextReplaceDataFlowInfo.dataFlowInfo;
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        ArrayList arrayListNewArrayList2 = Lists.newArrayList();
        for (KtCatchClause ktCatchClause : catchClauses) {
            KtParameter catchParameter = ktCatchClause.getCatchParameter();
            KtExpression catchBody = ktCatchClause.getCatchBody();
            if (catchParameter != null) {
                VariableDescriptor variableDescriptorResolveAndCheckCatchParameter = resolveAndCheckCatchParameter(catchParameter, expressionTypingContext);
                if (catchBody != null) {
                    arrayListNewArrayList.add(catchBody);
                    arrayListNewArrayList2.add(new Pair(catchBody, variableDescriptorResolveAndCheckCatchParameter));
                }
            }
        }
        KtBlockExpression finalExpression = finallyBlock != null ? finallyBlock.getFinalExpression() : null;
        ArrayList arrayListNewArrayList3 = Lists.newArrayList(tryBlock);
        arrayListNewArrayList3.addAll(arrayListNewArrayList);
        Call callCreateCallForSpecialConstruction = ControlStructureTypingUtils.createCallForSpecialConstruction(ktTryExpression, ktTryExpression, arrayListNewArrayList3);
        if (this.components.languageVersionSettings.supportsFeature(LanguageFeature.NewDataFlowForTryExpressions)) {
            mutableDataFlowInfoForArgumentsCreateDataFlowInfoForArgumentsOfTryCall = ControlStructureTypingUtils.createDataFlowInfoForArgumentsOfTryCall(callCreateCallForSpecialConstruction, dataFlowInfo, dataFlowInfo2);
        } else {
            mutableDataFlowInfoForArgumentsCreateDataFlowInfoForArgumentsOfTryCall = ControlStructureTypingUtils.createDataFlowInfoForArgumentsOfTryCall(callCreateCallForSpecialConstruction, dataFlowInfo, dataFlowInfo);
        }
        KotlinType returnType = ((FunctionDescriptor) this.components.controlStructureTypingUtils.resolveTryAsCall(callCreateCallForSpecialConstruction, arrayListNewArrayList2, expressionTypingContext, mutableDataFlowInfoForArgumentsCreateDataFlowInfoForArgumentsOfTryCall).getResultingDescriptor()).getReturnType();
        BindingContext context = expressionTypingContext.trace.getContext();
        if (this.components.languageVersionSettings.supportsFeature(LanguageFeature.NewDataFlowForTryExpressions)) {
            return processTryBranchesWithNewDataFlowAlgorithm(ktTryExpression, tryBlock, expressionTypingContextReplaceDataFlowInfo, dataFlowInfo2, arrayListNewArrayList, finalExpression, context, returnType);
        }
        return processTryBranches(ktTryExpression, tryBlock, expressionTypingContext, arrayListNewArrayList, finalExpression, context, returnType);
    }

    private KotlinTypeInfo processTryBranches(KtTryExpression ktTryExpression, KtBlockExpression ktBlockExpression, ExpressionTypingContext expressionTypingContext, List<KtExpression> list, KtBlockExpression ktBlockExpression2, BindingContext bindingContext, KotlinType kotlinType) {
        DataFlowInfo empty;
        if (ktTryExpression == null) {
            $$$reportNull$$$0(21);
        }
        KotlinTypeInfo recordedTypeInfo = BindingContextUtils.getRecordedTypeInfo(ktBlockExpression, bindingContext);
        if (recordedTypeInfo != null) {
            empty = recordedTypeInfo.getDataFlowInfo();
        } else {
            empty = DataFlowInfo.INSTANCE.getEMPTY();
        }
        boolean zIsCatchBranchesReturnsNothing = isCatchBranchesReturnsNothing(list, bindingContext);
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = getCleanedContextFromTryWithAssignmentsToVar(ktTryExpression, zIsCatchBranchesReturnsNothing, expressionTypingContext).replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT);
        KotlinTypeInfo kotlinTypeInfoCreateTypeInfo = TypeInfoFactoryKt.createTypeInfo(kotlinType, expressionTypingContextReplaceContextDependency);
        if (ktBlockExpression2 != null) {
            KotlinTypeInfo kotlinTypeInfoReplaceType = this.facade.getTypeInfo(ktBlockExpression2, expressionTypingContextReplaceContextDependency).replaceType(kotlinType);
            if (kotlinTypeInfoReplaceType == null) {
                $$$reportNull$$$0(22);
            }
            return kotlinTypeInfoReplaceType;
        }
        if (!zIsCatchBranchesReturnsNothing || recordedTypeInfo == null) {
            if (kotlinTypeInfoCreateTypeInfo == null) {
                $$$reportNull$$$0(23);
            }
            return kotlinTypeInfoCreateTypeInfo;
        }
        KotlinTypeInfo kotlinTypeInfoCreateTypeInfo2 = TypeInfoFactoryKt.createTypeInfo(this.components.dataFlowAnalyzer.checkType(kotlinType, ktTryExpression, expressionTypingContextReplaceContextDependency), empty);
        if (kotlinTypeInfoCreateTypeInfo2 == null) {
            $$$reportNull$$$0(24);
        }
        return kotlinTypeInfoCreateTypeInfo2;
    }

    private KotlinTypeInfo processTryBranchesWithNewDataFlowAlgorithm(KtTryExpression ktTryExpression, KtBlockExpression ktBlockExpression, ExpressionTypingContext expressionTypingContext, DataFlowInfo dataFlowInfo, List<KtExpression> list, KtBlockExpression ktBlockExpression2, BindingContext bindingContext, KotlinType kotlinType) {
        DataFlowInfo dataFlowInfo2;
        if (ktTryExpression == null) {
            $$$reportNull$$$0(25);
        }
        List<Boolean> listWhichCatchBranchesReturnNothing = whichCatchBranchesReturnNothing(list, bindingContext);
        ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = expressionTypingContext.replaceDataFlowInfo(PreliminaryLoopVisitor.visitCatchBlocks(ktTryExpression, mapNot(listWhichCatchBranchesReturnNothing)).clearDataFlowInfoForAssignedLocalVariables(dataFlowInfo, this.components.languageVersionSettings));
        KotlinTypeInfo recordedTypeInfo = BindingContextUtils.getRecordedTypeInfo(ktBlockExpression, bindingContext);
        boolean zAll = CollectionsKt.all(listWhichCatchBranchesReturnNothing, new Function1() { // from class: org.jetbrains.kotlin.types.expressions.ControlStructureTypingVisitor$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ControlStructureTypingVisitor.lambda$processTryBranchesWithNewDataFlowAlgorithm$1((Boolean) obj);
            }
        });
        if (recordedTypeInfo == null) {
            dataFlowInfo2 = DataFlowInfo.INSTANCE.getEMPTY();
        } else if (zAll) {
            dataFlowInfo2 = recordedTypeInfo.getDataFlowInfo();
        } else {
            dataFlowInfo2 = expressionTypingContextReplaceDataFlowInfo.dataFlowInfo;
        }
        if (ktBlockExpression2 != null) {
            dataFlowInfo2 = this.facade.getTypeInfo(ktBlockExpression2, expressionTypingContext.replaceDataFlowInfo(PreliminaryLoopVisitor.visitCatchBlocks(ktTryExpression).clearDataFlowInfoForAssignedLocalVariables(dataFlowInfo, this.components.languageVersionSettings)).replaceContextDependency(ContextDependency.INDEPENDENT).replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE)).getDataFlowInfo().and(dataFlowInfo2);
        }
        KotlinTypeInfo kotlinTypeInfoCreateTypeInfo = TypeInfoFactoryKt.createTypeInfo(this.components.dataFlowAnalyzer.checkType(kotlinType, ktTryExpression, expressionTypingContextReplaceDataFlowInfo), dataFlowInfo2);
        if (kotlinTypeInfoCreateTypeInfo == null) {
            $$$reportNull$$$0(26);
        }
        return kotlinTypeInfoCreateTypeInfo;
    }

    private static List<Boolean> mapNot(List<Boolean> list) {
        return CollectionsKt.map(list, new Function1() { // from class: org.jetbrains.kotlin.types.expressions.ControlStructureTypingVisitor$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(!((Boolean) obj).booleanValue());
            }
        });
    }

    private static boolean isCatchBranchesReturnsNothing(List<KtExpression> list, BindingContext bindingContext) {
        return CollectionsKt.all(whichCatchBranchesReturnNothing(list, bindingContext), new Function1() { // from class: org.jetbrains.kotlin.types.expressions.ControlStructureTypingVisitor$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ControlStructureTypingVisitor.lambda$isCatchBranchesReturnsNothing$3((Boolean) obj);
            }
        });
    }

    private static List<Boolean> whichCatchBranchesReturnNothing(List<KtExpression> list, final BindingContext bindingContext) {
        return (List) list.stream().map(new Function() { // from class: org.jetbrains.kotlin.types.expressions.ControlStructureTypingVisitor$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return BindingContextUtils.getRecordedTypeInfo((KtExpression) obj, bindingContext);
            }
        }).map(new Function() { // from class: org.jetbrains.kotlin.types.expressions.ControlStructureTypingVisitor$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ControlStructureTypingVisitor.lambda$whichCatchBranchesReturnNothing$5((KotlinTypeInfo) obj);
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ Boolean lambda$whichCatchBranchesReturnNothing$5(KotlinTypeInfo kotlinTypeInfo) {
        boolean z = true;
        if (kotlinTypeInfo == null) {
            return true;
        }
        KotlinType type = kotlinTypeInfo.getType();
        if (type != null && !KotlinBuiltIns.isNothing(type)) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    private VariableDescriptor resolveAndCheckCatchParameter(KtParameter ktParameter, ExpressionTypingContext expressionTypingContext) {
        if (ktParameter == null) {
            $$$reportNull$$$0(27);
        }
        checkCatchParameterDeclaration(ktParameter, expressionTypingContext);
        VariableDescriptor variableDescriptorResolveLocalVariableDescriptor = this.components.descriptorResolver.resolveLocalVariableDescriptor(expressionTypingContext.scope, ktParameter, expressionTypingContext.trace);
        KotlinType type = variableDescriptorResolveLocalVariableDescriptor.getType();
        checkCatchParameterType(ktParameter, type, expressionTypingContext);
        this.components.dataFlowAnalyzer.checkType(type, ktParameter, expressionTypingContext.replaceExpectedType(this.components.builtIns.getThrowable().getDefaultType()));
        return variableDescriptorResolveLocalVariableDescriptor;
    }

    private ExpressionTypingContext getCleanedContextFromTryWithAssignmentsToVar(KtTryExpression ktTryExpression, boolean z, ExpressionTypingContext expressionTypingContext) {
        ExpressionTypingContext expressionTypingContextReplaceExpectedType = expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE);
        return (z || !this.facade.getComponents().languageVersionSettings.supportsFeature(LanguageFeature.SoundSmartCastsAfterTry)) ? expressionTypingContextReplaceExpectedType : expressionTypingContextReplaceExpectedType.replaceDataFlowInfo(PreliminaryLoopVisitor.visitTryBlock(ktTryExpression).clearDataFlowInfoForAssignedLocalVariables(expressionTypingContextReplaceExpectedType.dataFlowInfo, this.components.languageVersionSettings));
    }

    private static void checkCatchParameterType(KtParameter ktParameter, KotlinType kotlinType, ExpressionTypingContext expressionTypingContext) {
        TypeParameterDescriptor typeParameterDescriptorOrNull = TypeUtils.getTypeParameterDescriptorOrNull(kotlinType);
        if (typeParameterDescriptorOrNull != null) {
            if (typeParameterDescriptorOrNull.isReified()) {
                expressionTypingContext.trace.report(Errors.REIFIED_TYPE_IN_CATCH_CLAUSE.on(ktParameter));
            } else {
                expressionTypingContext.trace.report(Errors.TYPE_PARAMETER_IN_CATCH_CLAUSE.on(ktParameter));
            }
        }
    }

    private void checkCatchParameterDeclaration(KtParameter ktParameter, ExpressionTypingContext expressionTypingContext) {
        this.components.identifierChecker.checkDeclaration(ktParameter, expressionTypingContext.trace);
        this.components.modifiersChecker.withTrace(expressionTypingContext.trace).checkParameterHasNoValOrVar(ktParameter, Errors.VAL_OR_VAR_ON_CATCH_PARAMETER);
        ModifierCheckerCore.INSTANCE.check(ktParameter, expressionTypingContext.trace, null, this.components.languageVersionSettings);
        if (ktParameter.hasDefaultValue()) {
            expressionTypingContext.trace.report(Errors.CATCH_PARAMETER_WITH_DEFAULT_VALUE.on(ktParameter));
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitThrowExpression(KtThrowExpression ktThrowExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktThrowExpression == null) {
            $$$reportNull$$$0(28);
        }
        KtExpression thrownExpression = ktThrowExpression.getThrownExpression();
        if (thrownExpression != null) {
            this.facade.getTypeInfo(thrownExpression, expressionTypingContext.replaceExpectedType(this.components.builtIns.getThrowable().getDefaultType()).replaceContextDependency(ContextDependency.INDEPENDENT));
        }
        return this.components.dataFlowAnalyzer.createCheckedTypeInfo(this.components.builtIns.getNothingType(), expressionTypingContext, ktThrowExpression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitReturnExpression(KtReturnExpression ktReturnExpression, ExpressionTypingContext expressionTypingContext) {
        SimpleFunctionDescriptor simpleFunctionDescriptor;
        ResolvedCall<? extends CallableDescriptor> resolvedCall;
        KotlinResolutionCallbacksImpl.LambdaInfo newInferenceLambdaInfo;
        LambdaContextInfo lambdaContextInfo;
        if (ktReturnExpression == null) {
            $$$reportNull$$$0(29);
        }
        KtElement ktElementResolveControlLabel = LabelResolver.INSTANCE.resolveControlLabel(ktReturnExpression, expressionTypingContext);
        KtExpression returnedExpression = ktReturnExpression.getReturnedExpression();
        KotlinType functionExpectedReturnType = TypeUtils.NO_EXPECTED_TYPE;
        SimpleType nothingType = this.components.builtIns.getNothingType();
        KtDeclaration ktDeclaration = (KtDeclaration) expressionTypingContext.getContextParentOfType(ktReturnExpression, KtDeclaration.class);
        if (ktDeclaration instanceof KtParameter) {
            expressionTypingContext.trace.report(Errors.RETURN_NOT_ALLOWED.on(ktReturnExpression));
        }
        if (ktReturnExpression.getTargetLabel() == null) {
            while (ktDeclaration instanceof KtDestructuringDeclaration) {
                ktDeclaration = (KtDeclaration) expressionTypingContext.getContextParentOfType(ktDeclaration, KtDeclaration.class);
            }
            org.jetbrains.kotlin.com.intellij.openapi.util.Pair<FunctionDescriptor, PsiElement> containingFunctionSkipFunctionLiterals = BindingContextUtils.getContainingFunctionSkipFunctionLiterals((DeclarationDescriptor) expressionTypingContext.trace.get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktDeclaration), false);
            FunctionDescriptor first = containingFunctionSkipFunctionLiterals.getFirst();
            if (first != null) {
                if (!InlineUtil.checkNonLocalReturnUsage(first, ktReturnExpression, expressionTypingContext) || isClassInitializer(containingFunctionSkipFunctionLiterals)) {
                    expressionTypingContext.trace.report(Errors.RETURN_NOT_ALLOWED.on(ktReturnExpression));
                    nothingType = ErrorUtils.createErrorType(ErrorTypeKind.RETURN_NOT_ALLOWED, new String[0]);
                }
                functionExpectedReturnType = getFunctionExpectedReturnType(first, (KtElement) containingFunctionSkipFunctionLiterals.getSecond(), expressionTypingContext);
                newInferenceLambdaInfo = ExpressionTypingServices.getNewInferenceLambdaInfo(expressionTypingContext, (KtElement) containingFunctionSkipFunctionLiterals.getSecond());
            } else {
                expressionTypingContext.trace.report(Errors.RETURN_NOT_ALLOWED.on(ktReturnExpression));
                nothingType = ErrorUtils.createErrorType(ErrorTypeKind.RETURN_NOT_ALLOWED, new String[0]);
                newInferenceLambdaInfo = null;
            }
        } else if (ktElementResolveControlLabel == null || (simpleFunctionDescriptor = (SimpleFunctionDescriptor) expressionTypingContext.trace.get(BindingContext.FUNCTION, ktElementResolveControlLabel)) == null) {
            newInferenceLambdaInfo = null;
        } else {
            functionExpectedReturnType = getFunctionExpectedReturnType(simpleFunctionDescriptor, ktElementResolveControlLabel, expressionTypingContext);
            KotlinResolutionCallbacksImpl.LambdaInfo newInferenceLambdaInfo2 = ExpressionTypingServices.getNewInferenceLambdaInfo(expressionTypingContext, ktElementResolveControlLabel);
            if (!InlineUtil.checkNonLocalReturnUsage(simpleFunctionDescriptor, ktReturnExpression, expressionTypingContext)) {
                expressionTypingContext.trace.report(Errors.RETURN_NOT_ALLOWED.on(ktReturnExpression));
                nothingType = ErrorUtils.createErrorType(ErrorTypeKind.RETURN_NOT_ALLOWED, new String[0]);
            } else if ((ktElementResolveControlLabel instanceof KtFunctionLiteral) && Objects.equals(ktReturnExpression.getLabelName(), "suspend") && (resolvedCall = CallUtilKt.getResolvedCall(KtPsiUtil.getParentCallIfPresent((KtFunction) ktElementResolveControlLabel), expressionTypingContext.trace.getContext())) != null && !KtPsiUtil.isLabeledFunctionLiteral((KtFunctionLiteral) ktElementResolveControlLabel) && Objects.equals(DescriptorUtilsKt.fqNameOrNull(resolvedCall.getResultingDescriptor()), SuspendFunctionTypeUtilKt.KOTLIN_SUSPEND_BUILT_IN_FUNCTION_FQ_NAME)) {
                expressionTypingContext.trace.report(Errors.RETURN_FOR_BUILT_IN_SUSPEND.on(ktReturnExpression));
            }
            newInferenceLambdaInfo = newInferenceLambdaInfo2;
        }
        if (returnedExpression == null) {
            if (!TypeUtils.noExpectedType(functionExpectedReturnType) && !KotlinBuiltIns.isUnit(functionExpectedReturnType) && !TypeUtils.isDontCarePlaceholder(functionExpectedReturnType)) {
                expressionTypingContext.trace.report(Errors.RETURN_TYPE_MISMATCH.on(ktReturnExpression, functionExpectedReturnType));
            }
            if (newInferenceLambdaInfo != null) {
                newInferenceLambdaInfo.getReturnStatements().add(new Pair<>(ktReturnExpression, null));
            }
        } else if (newInferenceLambdaInfo != null) {
            KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(returnedExpression);
            if ((ktExpressionDeparenthesize instanceof KtLambdaExpression) || (ktExpressionDeparenthesize instanceof KtCallableReferenceExpression)) {
                lambdaContextInfo = new LambdaContextInfo(new KotlinTypeInfo(TypeUtils.DONT_CARE, expressionTypingContext.dataFlowInfo), null, expressionTypingContext.scope, expressionTypingContext.trace);
            } else {
                lambdaContextInfo = new LambdaContextInfo(this.facade.getTypeInfo(returnedExpression, expressionTypingContext.replaceExpectedType(newInferenceLambdaInfo.getExpectedType()).replaceContextDependency(newInferenceLambdaInfo.getContextDependency())), null, expressionTypingContext.scope, expressionTypingContext.trace);
            }
            newInferenceLambdaInfo.getReturnStatements().add(new Pair<>(ktReturnExpression, lambdaContextInfo));
        } else {
            this.facade.getTypeInfo(returnedExpression, expressionTypingContext.replaceExpectedType(functionExpectedReturnType).replaceContextDependency(ContextDependency.INDEPENDENT));
        }
        return this.components.dataFlowAnalyzer.createCheckedTypeInfo(nothingType, expressionTypingContext, ktReturnExpression);
    }

    private static boolean isClassInitializer(org.jetbrains.kotlin.com.intellij.openapi.util.Pair<FunctionDescriptor, PsiElement> pair) {
        if (pair == null) {
            $$$reportNull$$$0(30);
        }
        return (pair.getFirst() instanceof ConstructorDescriptor) && !(pair.getSecond() instanceof KtSecondaryConstructor);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitBreakExpression(KtBreakExpression ktBreakExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktBreakExpression == null) {
            $$$reportNull$$$0(31);
        }
        LabelResolver.INSTANCE.resolveControlLabel(ktBreakExpression, expressionTypingContext);
        return this.components.dataFlowAnalyzer.createCheckedTypeInfo(this.components.builtIns.getNothingType(), expressionTypingContext, ktBreakExpression).replaceJumpOutPossible(true);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitContinueExpression(KtContinueExpression ktContinueExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktContinueExpression == null) {
            $$$reportNull$$$0(32);
        }
        LabelResolver.INSTANCE.resolveControlLabel(ktContinueExpression, expressionTypingContext);
        return this.components.dataFlowAnalyzer.createCheckedTypeInfo(this.components.builtIns.getNothingType(), expressionTypingContext, ktContinueExpression).replaceJumpOutPossible(true);
    }

    private static KotlinType getFunctionExpectedReturnType(FunctionDescriptor functionDescriptor, KtElement ktElement, ExpressionTypingContext expressionTypingContext) {
        KotlinType returnType;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(33);
        }
        if (ktElement == null) {
            $$$reportNull$$$0(34);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(35);
        }
        if (ktElement instanceof KtSecondaryConstructor) {
            returnType = DescriptorUtilsKt.getBuiltIns(functionDescriptor).getUnitType();
        } else if (ktElement instanceof KtFunction) {
            KtFunction ktFunction = (KtFunction) ktElement;
            KotlinType kotlinType = (KotlinType) expressionTypingContext.trace.get(BindingContext.EXPECTED_RETURN_TYPE, ktFunction);
            returnType = (kotlinType != null || (ktFunction.mo7396getTypeReference() == null && !ktFunction.hasBlockBody())) ? kotlinType : functionDescriptor.getReturnType();
        } else {
            returnType = functionDescriptor.getReturnType();
        }
        if (returnType == null) {
            returnType = TypeUtils.NO_EXPECTED_TYPE;
        }
        if (returnType == null) {
            $$$reportNull$$$0(36);
        }
        return returnType;
    }
}
