package org.jetbrains.kotlin.types.expressions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.Sets;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.contracts.EffectSystem;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.NewSchemeOfIntegerOperatorResolutionChecker;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SmartCastManager;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SmartCastResult;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstantChecker;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.resolve.constants.TypedCompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: loaded from: classes6.dex */
public class DataFlowAnalyzer {
    private final Iterable<AdditionalTypeChecker> additionalTypeCheckers;
    private final KotlinBuiltIns builtIns;
    private final ConstantExpressionEvaluator constantExpressionEvaluator;
    private final DataFlowValueFactory dataFlowValueFactory;
    private final EffectSystem effectSystem;
    private final ExpressionTypingFacade facade;
    private final KotlinTypeChecker kotlinTypeChecker;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModuleDescriptor module;
    private final SmartCastManager smartCastManager;

    /* JADX WARN: Code duplicated, block: B:11:0x001f A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 25 && i != 48 && i != 56 && i != 59 && i != 63) {
            switch (i) {
                default:
                    switch (i) {
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 15:
                case 16:
                case 17:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 25 && i != 48 && i != 56 && i != 59 && i != 63) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "constantExpressionEvaluator";
                break;
            case 2:
                objArr[0] = "module";
                break;
            case 3:
                objArr[0] = "builtIns";
                break;
            case 4:
            case 47:
                objArr[0] = "facade";
                break;
            case 5:
            case 55:
                objArr[0] = "languageVersionSettings";
                break;
            case 6:
                objArr[0] = "effectSystem";
                break;
            case 7:
                objArr[0] = "factory";
                break;
            case 8:
                objArr[0] = "smartCastManager";
                break;
            case 9:
                objArr[0] = "kotlinTypeChecker";
                break;
            case 10:
                objArr[0] = "descriptor";
                break;
            case 11:
            case 13:
            case 50:
            case 52:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 12:
            case 14:
                objArr[0] = "lookupElement";
                break;
            case 15:
            case 16:
            case 17:
            case 25:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 48:
            case 56:
            case 59:
            case 63:
                objArr[0] = "org/jetbrains/kotlin/types/expressions/DataFlowAnalyzer";
                break;
            case 18:
            case 20:
            case 23:
            case 27:
            case 38:
            case 41:
            case 43:
            case 45:
            case 49:
            case 58:
            case 61:
                objArr[0] = "expression";
                break;
            case 19:
            case 21:
            case 24:
            case 44:
            case 46:
            case 57:
            case 62:
                objArr[0] = "context";
                break;
            case 22:
                objArr[0] = "typeInfo";
                break;
            case 26:
            case 37:
                objArr[0] = "expressionType";
                break;
            case 28:
            case 36:
            case 39:
            case 51:
            case 53:
                objArr[0] = "c";
                break;
            case 29:
                objArr[0] = "hasError";
                break;
            case 35:
                objArr[0] = "expressionToCheck";
                break;
            case 40:
                objArr[0] = "trace";
                break;
            case 42:
                objArr[0] = "expectedType";
                break;
            case 54:
                objArr[0] = "dataFlowValue";
                break;
            case 60:
                objArr[0] = "value";
                break;
            default:
                objArr[0] = "additionalTypeCheckers";
                break;
        }
        if (i == 25) {
            objArr[1] = "checkType";
        } else if (i == 48) {
            objArr[1] = "illegalStatementType";
        } else if (i == 56) {
            objArr[1] = "getAllPossibleTypes";
        } else if (i == 59) {
            objArr[1] = "createCheckedTypeInfo";
        } else if (i != 63) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                    objArr[1] = "extractDataFlowInfoFromCondition";
                    break;
                default:
                    switch (i) {
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                            objArr[1] = "checkTypeInternal";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/types/expressions/DataFlowAnalyzer";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "createCompileTimeConstantTypeInfo";
        }
        switch (i) {
            case 10:
                objArr[2] = "getOverriddenDescriptorFromClass";
                break;
            case 11:
            case 12:
                objArr[2] = "typeHasOverriddenEquals";
                break;
            case 13:
            case 14:
                objArr[2] = "typeHasEqualsFromAny";
                break;
            case 15:
            case 16:
            case 17:
            case 25:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 48:
            case 56:
            case 59:
            case 63:
                break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 35:
            case 36:
                objArr[2] = "checkType";
                break;
            case 26:
            case 27:
            case 28:
            case 29:
                objArr[2] = "checkTypeInternal";
                break;
            case 37:
            case 38:
            case 39:
                objArr[2] = "checkPossibleCast";
                break;
            case 40:
            case 41:
            case 42:
                objArr[2] = "recordExpectedType";
                break;
            case 43:
            case 44:
                objArr[2] = "checkStatementType";
                break;
            case 45:
            case 46:
            case 47:
                objArr[2] = "illegalStatementType";
                break;
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
                objArr[2] = "getAllPossibleTypes";
                break;
            case 57:
            case 58:
                objArr[2] = "createCheckedTypeInfo";
                break;
            case 60:
            case 61:
            case 62:
                objArr[2] = "createCompileTimeConstantTypeInfo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 25 && i != 48 && i != 56 && i != 59 && i != 63) {
            switch (i) {
                case 15:
                case 16:
                case 17:
                    break;
                default:
                    switch (i) {
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    public DataFlowAnalyzer(Iterable<AdditionalTypeChecker> iterable, ConstantExpressionEvaluator constantExpressionEvaluator, ModuleDescriptor moduleDescriptor, KotlinBuiltIns kotlinBuiltIns, ExpressionTypingFacade expressionTypingFacade, LanguageVersionSettings languageVersionSettings, EffectSystem effectSystem, DataFlowValueFactory dataFlowValueFactory, SmartCastManager smartCastManager, KotlinTypeChecker kotlinTypeChecker) {
        if (iterable == null) {
            $$$reportNull$$$0(0);
        }
        if (constantExpressionEvaluator == null) {
            $$$reportNull$$$0(1);
        }
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (kotlinBuiltIns == null) {
            $$$reportNull$$$0(3);
        }
        if (expressionTypingFacade == null) {
            $$$reportNull$$$0(4);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(5);
        }
        if (effectSystem == null) {
            $$$reportNull$$$0(6);
        }
        if (dataFlowValueFactory == null) {
            $$$reportNull$$$0(7);
        }
        if (smartCastManager == null) {
            $$$reportNull$$$0(8);
        }
        if (kotlinTypeChecker == null) {
            $$$reportNull$$$0(9);
        }
        this.additionalTypeCheckers = iterable;
        this.constantExpressionEvaluator = constantExpressionEvaluator;
        this.module = moduleDescriptor;
        this.builtIns = kotlinBuiltIns;
        this.facade = expressionTypingFacade;
        this.languageVersionSettings = languageVersionSettings;
        this.effectSystem = effectSystem;
        this.dataFlowValueFactory = dataFlowValueFactory;
        this.smartCastManager = smartCastManager;
        this.kotlinTypeChecker = kotlinTypeChecker;
    }

    private FunctionDescriptor getOverriddenDescriptorFromClass(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(10);
        }
        if (functionDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            return functionDescriptor;
        }
        Collection<? extends FunctionDescriptor> overriddenDescriptors = functionDescriptor.getOverriddenDescriptors();
        if (overriddenDescriptors.isEmpty()) {
            return functionDescriptor;
        }
        for (FunctionDescriptor functionDescriptor2 : overriddenDescriptors) {
            DeclarationDescriptor containingModule = functionDescriptor2.getContainingModule();
            if (DescriptorUtils.isClass(containingModule) || DescriptorUtils.isObject(containingModule)) {
                return getOverriddenDescriptorFromClass(functionDescriptor2);
            }
        }
        return null;
    }

    private boolean typeHasOverriddenEquals(KotlinType kotlinType, KtElement ktElement) {
        if (kotlinType == null) {
            $$$reportNull$$$0(11);
        }
        if (ktElement == null) {
            $$$reportNull$$$0(12);
        }
        if (KotlinBuiltIns.isString(kotlinType)) {
            return false;
        }
        for (SimpleFunctionDescriptor simpleFunctionDescriptor : kotlinType.getMemberScope().getContributedFunctions(OperatorNameConventions.EQUALS, new KotlinLookupLocation(ktElement))) {
            KotlinType returnType = simpleFunctionDescriptor.getReturnType();
            if (returnType != null && KotlinBuiltIns.isBoolean(returnType) && simpleFunctionDescriptor.getValueParameters().size() == 1 && KotlinBuiltIns.isNullableAny(simpleFunctionDescriptor.getValueParameters().iterator().next().getType())) {
                FunctionDescriptor overriddenDescriptorFromClass = getOverriddenDescriptorFromClass(simpleFunctionDescriptor);
                if (overriddenDescriptorFromClass == null) {
                    return false;
                }
                return !KotlinBuiltIns.isAnyOrNullableAny(((ClassifierDescriptor) overriddenDescriptorFromClass.getContainingModule()).getDefaultType());
            }
        }
        return false;
    }

    public boolean typeHasEqualsFromAny(KotlinType kotlinType, KtElement ktElement) {
        if (kotlinType == null) {
            $$$reportNull$$$0(13);
        }
        if (ktElement == null) {
            $$$reportNull$$$0(14);
        }
        if (kotlinType.getConstructor().isFinal()) {
            return !typeHasOverriddenEquals(kotlinType, ktElement);
        }
        return false;
    }

    public DataFlowInfo extractDataFlowInfoFromCondition(final KtExpression ktExpression, final boolean z, final ExpressionTypingContext expressionTypingContext) {
        if (ktExpression == null) {
            DataFlowInfo dataFlowInfo = expressionTypingContext.dataFlowInfo;
            if (dataFlowInfo == null) {
                $$$reportNull$$$0(15);
            }
            return dataFlowInfo;
        }
        final Ref ref = new Ref(null);
        ktExpression.accept(new KtVisitorVoid() { // from class: org.jetbrains.kotlin.types.expressions.DataFlowAnalyzer.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                objArr[0] = "expression";
                objArr[1] = "org/jetbrains/kotlin/types/expressions/DataFlowAnalyzer$1";
                if (i == 1) {
                    objArr[2] = "visitBinaryExpression";
                } else if (i == 2) {
                    objArr[2] = "visitUnaryExpression";
                } else if (i == 3) {
                    objArr[2] = "visitExpression";
                } else if (i != 4) {
                    objArr[2] = "visitIsExpression";
                } else {
                    objArr[2] = "visitParenthesizedExpression";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitIsExpression(KtIsExpression ktIsExpression) {
                if (ktIsExpression == null) {
                    $$$reportNull$$$0(0);
                }
                if ((!z || ktIsExpression.isNegated()) && (z || !ktIsExpression.isNegated())) {
                    return;
                }
                ref.set(expressionTypingContext.trace.get(BindingContext.DATAFLOW_INFO_AFTER_CONDITION, ktIsExpression));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitBinaryExpression(KtBinaryExpression ktBinaryExpression) {
                KtExpression right;
                KotlinType type;
                KotlinType type2;
                Boolean boolValueOf;
                boolean z2 = true;
                if (ktBinaryExpression == null) {
                    $$$reportNull$$$0(1);
                }
                IElementType operationToken = ktBinaryExpression.getOperationToken();
                if (!OperatorConventions.BOOLEAN_OPERATIONS.containsKey(operationToken)) {
                    DataFlowInfo dataFlowInfo2 = DataFlowAnalyzer.this.facade.getTypeInfo(ktBinaryExpression, expressionTypingContext).getDataFlowInfo();
                    KtExpression left = ktBinaryExpression.getLeft();
                    if (left == null || (right = ktBinaryExpression.getRight()) == null || (type = expressionTypingContext.trace.getContext().getType(left)) == null || (type2 = expressionTypingContext.trace.getContext().getType(right)) == null) {
                        return;
                    }
                    DataFlowValue dataFlowValueCreateDataFlowValue = DataFlowAnalyzer.this.dataFlowValueFactory.createDataFlowValue(left, type, expressionTypingContext);
                    DataFlowValue dataFlowValueCreateDataFlowValue2 = DataFlowAnalyzer.this.dataFlowValueFactory.createDataFlowValue(right, type2, expressionTypingContext);
                    if (operationToken == KtTokens.EQEQ || operationToken == KtTokens.EQEQEQ) {
                        boolValueOf = true;
                    } else if (operationToken == KtTokens.EXCLEQ || operationToken == KtTokens.EXCLEQEQEQ) {
                        boolValueOf = false;
                    } else {
                        boolValueOf = (operationToken == KtTokens.ELVIS && DataFlowAnalyzer.this.languageVersionSettings.supportsFeature(LanguageFeature.BooleanElvisBoundSmartCasts) && (right instanceof KtConstantExpression) && KotlinBuiltIns.isBoolean(type2)) ? Boolean.valueOf(KtPsiUtil.isFalseConstant(right)) : null;
                    }
                    if (boolValueOf != null) {
                        if (boolValueOf.booleanValue() == z) {
                            if (operationToken != KtTokens.EQEQEQ && operationToken != KtTokens.EXCLEQEQEQ && !DataFlowAnalyzer.this.typeHasEqualsFromAny(type, ktExpression)) {
                                z2 = false;
                            }
                            ref.set(expressionTypingContext.dataFlowInfo.equate(dataFlowValueCreateDataFlowValue, dataFlowValueCreateDataFlowValue2, z2, DataFlowAnalyzer.this.languageVersionSettings).and(dataFlowInfo2));
                            return;
                        }
                        ref.set(expressionTypingContext.dataFlowInfo.disequate(dataFlowValueCreateDataFlowValue, dataFlowValueCreateDataFlowValue2, DataFlowAnalyzer.this.languageVersionSettings).and(dataFlowInfo2));
                        return;
                    }
                    ref.set(dataFlowInfo2);
                    return;
                }
                DataFlowInfo dataFlowInfoExtractDataFlowInfoFromCondition = DataFlowAnalyzer.this.extractDataFlowInfoFromCondition(ktBinaryExpression.getLeft(), z, expressionTypingContext);
                KtExpression right2 = ktBinaryExpression.getRight();
                if (right2 != null) {
                    z2 = operationToken == KtTokens.ANDAND;
                    DataFlowAnalyzer dataFlowAnalyzer = DataFlowAnalyzer.this;
                    boolean z3 = z;
                    DataFlowInfo dataFlowInfoExtractDataFlowInfoFromCondition2 = dataFlowAnalyzer.extractDataFlowInfoFromCondition(right2, z3, z2 == z3 ? expressionTypingContext.replaceDataFlowInfo(dataFlowInfoExtractDataFlowInfoFromCondition) : expressionTypingContext);
                    if (z2 == z) {
                        dataFlowInfoExtractDataFlowInfoFromCondition = dataFlowInfoExtractDataFlowInfoFromCondition.and(dataFlowInfoExtractDataFlowInfoFromCondition2);
                    } else {
                        dataFlowInfoExtractDataFlowInfoFromCondition = dataFlowInfoExtractDataFlowInfoFromCondition.or(dataFlowInfoExtractDataFlowInfoFromCondition2);
                    }
                }
                ref.set(dataFlowInfoExtractDataFlowInfoFromCondition);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitUnaryExpression(KtUnaryExpression ktUnaryExpression) {
                if (ktUnaryExpression == null) {
                    $$$reportNull$$$0(2);
                }
                if (ktUnaryExpression.getOperationReference().getReferencedNameElementType() == KtTokens.EXCL) {
                    KtExpression baseExpression = ktUnaryExpression.getBaseExpression();
                    if (baseExpression != null) {
                        ref.set(DataFlowAnalyzer.this.extractDataFlowInfoFromCondition(baseExpression, !z, expressionTypingContext));
                        return;
                    }
                    return;
                }
                visitExpression(ktUnaryExpression);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitExpression(KtExpression ktExpression2) {
                if (ktExpression2 == null) {
                    $$$reportNull$$$0(3);
                }
                ref.set(DataFlowAnalyzer.this.facade.getTypeInfo(ktExpression2, expressionTypingContext).getDataFlowInfo());
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitParenthesizedExpression(KtParenthesizedExpression ktParenthesizedExpression) {
                if (ktParenthesizedExpression == null) {
                    $$$reportNull$$$0(4);
                }
                KtExpression expression = ktParenthesizedExpression.getExpression();
                if (expression != null) {
                    expression.accept(this);
                }
            }
        });
        DataFlowInfo dataFlowInfoExtractDataFlowInfoFromCondition = this.effectSystem.extractDataFlowInfoFromCondition(ktExpression, z, expressionTypingContext.trace, DescriptorUtils.getContainingModule(expressionTypingContext.scope.getOwnerDescriptor()));
        if (ref.get() == null) {
            DataFlowInfo dataFlowInfoAnd = expressionTypingContext.dataFlowInfo.and(dataFlowInfoExtractDataFlowInfoFromCondition);
            if (dataFlowInfoAnd == null) {
                $$$reportNull$$$0(16);
            }
            return dataFlowInfoAnd;
        }
        DataFlowInfo dataFlowInfoAnd2 = expressionTypingContext.dataFlowInfo.and((DataFlowInfo) ref.get()).and(dataFlowInfoExtractDataFlowInfoFromCondition);
        if (dataFlowInfoAnd2 == null) {
            $$$reportNull$$$0(17);
        }
        return dataFlowInfoAnd2;
    }

    public KotlinType checkType(KotlinType kotlinType, KtExpression ktExpression, ResolutionContext resolutionContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(18);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(19);
        }
        return checkType(kotlinType, ktExpression, resolutionContext, null, true);
    }

    public KotlinType checkType(KotlinType kotlinType, KtExpression ktExpression, ResolutionContext resolutionContext, boolean z) {
        if (ktExpression == null) {
            $$$reportNull$$$0(20);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(21);
        }
        return checkType(kotlinType, ktExpression, resolutionContext, null, z);
    }

    public KotlinTypeInfo checkType(KotlinTypeInfo kotlinTypeInfo, KtExpression ktExpression, ResolutionContext resolutionContext) {
        if (kotlinTypeInfo == null) {
            $$$reportNull$$$0(22);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(23);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(24);
        }
        KotlinTypeInfo kotlinTypeInfoReplaceType = kotlinTypeInfo.replaceType(checkType(kotlinTypeInfo.getType(), ktExpression, resolutionContext));
        if (kotlinTypeInfoReplaceType == null) {
            $$$reportNull$$$0(25);
        }
        return kotlinTypeInfoReplaceType;
    }

    private KotlinType checkTypeInternal(KotlinType kotlinType, KtExpression ktExpression, ResolutionContext resolutionContext, Ref<Boolean> ref, boolean z) {
        if (kotlinType == null) {
            $$$reportNull$$$0(26);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(27);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(28);
        }
        if (ref == null) {
            $$$reportNull$$$0(29);
        }
        if (!TypeUtils.noExpectedType(resolutionContext.expectedType) && TypeUtilsKt.contains(kotlinType, new Function1() { // from class: org.jetbrains.kotlin.types.expressions.DataFlowAnalyzer$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((UnwrappedType) obj) instanceof StubTypeForBuilderInference);
            }
        }) && (resolutionContext.inferenceSession instanceof BuilderInferenceSession)) {
            ((BuilderInferenceSession) resolutionContext.inferenceSession).addExpectedTypeConstraint(ktExpression, kotlinType, resolutionContext.expectedType);
        }
        if (TypeUtils.noExpectedType(resolutionContext.expectedType) || !resolutionContext.expectedType.getConstructor().isDenotable() || this.kotlinTypeChecker.isSubtypeOf(kotlinType, resolutionContext.expectedType)) {
            if (kotlinType == null) {
                $$$reportNull$$$0(30);
            }
            return kotlinType;
        }
        if ((ktExpression instanceof KtConstantExpression) && z) {
            ref.set(Boolean.valueOf(new CompileTimeConstantChecker(resolutionContext, this.module, true).checkConstantExpressionType(this.constantExpressionEvaluator.evaluateToConstantValue(ktExpression, resolutionContext.trace, resolutionContext.expectedType), (KtConstantExpression) ktExpression, resolutionContext.expectedType)));
            if (kotlinType == null) {
                $$$reportNull$$$0(31);
            }
            return kotlinType;
        }
        if ((ktExpression instanceof KtWhenExpression) && !((KtWhenExpression) ktExpression).getEntries().isEmpty()) {
            if (kotlinType == null) {
                $$$reportNull$$$0(32);
            }
            return kotlinType;
        }
        SmartCastResult smartCastResultCheckPossibleCast = checkPossibleCast(kotlinType, ktExpression, resolutionContext);
        if (smartCastResultCheckPossibleCast != null) {
            KotlinType resultType = smartCastResultCheckPossibleCast.getResultType();
            if (resultType == null) {
                $$$reportNull$$$0(33);
            }
            return resultType;
        }
        if (z && !DiagnosticUtilsKt.reportTypeMismatchDueToTypeProjection(resolutionContext, ktExpression, resolutionContext.expectedType, kotlinType) && !DiagnosticUtilsKt.reportTypeMismatchDueToScalaLikeNamedFunctionSyntax(resolutionContext, ktExpression, resolutionContext.expectedType, kotlinType)) {
            resolutionContext.trace.report(Errors.TYPE_MISMATCH.on(ktExpression, resolutionContext.expectedType, kotlinType));
        }
        ref.set(true);
        if (kotlinType == null) {
            $$$reportNull$$$0(34);
        }
        return kotlinType;
    }

    public KotlinType checkType(KotlinType kotlinType, KtExpression ktExpression, ResolutionContext resolutionContext, Ref<Boolean> ref, boolean z) {
        if (ktExpression == null) {
            $$$reportNull$$$0(35);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(36);
        }
        if (ref == null) {
            ref = Ref.create(false);
        } else {
            ref.set(false);
        }
        KtExpression ktExpressionSafeDeparenthesize = KtPsiUtil.safeDeparenthesize(ktExpression);
        recordExpectedType(resolutionContext.trace, ktExpressionSafeDeparenthesize, resolutionContext.expectedType);
        if (kotlinType == null) {
            return null;
        }
        KotlinType kotlinTypeCheckTypeInternal = checkTypeInternal(kotlinType, ktExpressionSafeDeparenthesize, resolutionContext, ref, z);
        if (Boolean.FALSE.equals(ref.get())) {
            Iterator<AdditionalTypeChecker> it = this.additionalTypeCheckers.iterator();
            while (it.hasNext()) {
                it.next().checkType(ktExpressionSafeDeparenthesize, kotlinType, kotlinTypeCheckTypeInternal, resolutionContext);
            }
        }
        return kotlinTypeCheckTypeInternal;
    }

    public SmartCastResult checkPossibleCast(KotlinType kotlinType, KtExpression ktExpression, ResolutionContext resolutionContext) {
        if (kotlinType == null) {
            $$$reportNull$$$0(37);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(38);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(39);
        }
        return this.smartCastManager.checkAndRecordPossibleCast(this.dataFlowValueFactory.createDataFlowValue(ktExpression, kotlinType, (ResolutionContext<?>) resolutionContext), resolutionContext.expectedType, ktExpression, resolutionContext, null, false, null);
    }

    public void recordExpectedType(BindingTrace bindingTrace, KtExpression ktExpression, KotlinType kotlinType) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(40);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(41);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(42);
        }
        if (kotlinType != TypeUtils.NO_EXPECTED_TYPE) {
            if (kotlinType == TypeUtils.UNIT_EXPECTED_TYPE) {
                kotlinType = this.builtIns.getUnitType();
            }
            bindingTrace.record(BindingContext.EXPECTED_EXPRESSION_TYPE, ktExpression, kotlinType);
        }
    }

    public KotlinType checkStatementType(KtExpression ktExpression, ResolutionContext resolutionContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(43);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(44);
        }
        if (!TypeUtils.noExpectedType(resolutionContext.expectedType) && !KotlinBuiltIns.isUnit(resolutionContext.expectedType) && !KotlinTypeKt.isError(resolutionContext.expectedType)) {
            resolutionContext.trace.report(Errors.EXPECTED_TYPE_MISMATCH.on(ktExpression, resolutionContext.expectedType));
            return null;
        }
        return this.builtIns.getUnitType();
    }

    public KotlinTypeInfo illegalStatementType(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext, ExpressionTypingInternals expressionTypingInternals) {
        if (ktExpression == null) {
            $$$reportNull$$$0(45);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(46);
        }
        if (expressionTypingInternals == null) {
            $$$reportNull$$$0(47);
        }
        expressionTypingInternals.checkStatementType(ktExpression, expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT));
        if (!expressionTypingContext.isDebuggerContext) {
            expressionTypingContext.trace.report(Errors.EXPRESSION_EXPECTED.on(ktExpression, ktExpression));
        }
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
        if (kotlinTypeInfoNoTypeInfo == null) {
            $$$reportNull$$$0(48);
        }
        return kotlinTypeInfoNoTypeInfo;
    }

    public static Collection<KotlinType> getAllPossibleTypes(KtExpression ktExpression, KotlinType kotlinType, ResolutionContext resolutionContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(49);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(50);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(51);
        }
        return getAllPossibleTypes(kotlinType, resolutionContext, resolutionContext.dataFlowValueFactory.createDataFlowValue(ktExpression, kotlinType, (ResolutionContext<?>) resolutionContext), resolutionContext.languageVersionSettings);
    }

    public static Collection<KotlinType> getAllPossibleTypes(KotlinType kotlinType, ResolutionContext resolutionContext, DataFlowValue dataFlowValue, LanguageVersionSettings languageVersionSettings) {
        if (kotlinType == null) {
            $$$reportNull$$$0(52);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(53);
        }
        if (dataFlowValue == null) {
            $$$reportNull$$$0(54);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(55);
        }
        HashSet hashSetNewHashSet = Sets.newHashSet(kotlinType);
        hashSetNewHashSet.addAll(resolutionContext.dataFlowInfo.getStableTypes(dataFlowValue, languageVersionSettings));
        if (hashSetNewHashSet == null) {
            $$$reportNull$$$0(56);
        }
        return hashSetNewHashSet;
    }

    public KotlinTypeInfo createCheckedTypeInfo(KotlinType kotlinType, ResolutionContext<?> resolutionContext, KtExpression ktExpression) {
        if (resolutionContext == null) {
            $$$reportNull$$$0(57);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(58);
        }
        KotlinTypeInfo kotlinTypeInfoCheckType = checkType(TypeInfoFactoryKt.createTypeInfo(kotlinType, resolutionContext), ktExpression, resolutionContext);
        if (kotlinTypeInfoCheckType == null) {
            $$$reportNull$$$0(59);
        }
        return kotlinTypeInfoCheckType;
    }

    public KotlinTypeInfo createCompileTimeConstantTypeInfo(CompileTimeConstant<?> compileTimeConstant, KtExpression ktExpression, ExpressionTypingContext expressionTypingContext) {
        KotlinType type;
        if (compileTimeConstant == null) {
            $$$reportNull$$$0(60);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(61);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(62);
        }
        if (compileTimeConstant instanceof IntegerValueTypeConstant) {
            IntegerValueTypeConstant integerValueTypeConstant = (IntegerValueTypeConstant) compileTimeConstant;
            if (expressionTypingContext.contextDependency == ContextDependency.INDEPENDENT) {
                type = integerValueTypeConstant.getType(expressionTypingContext.expectedType);
                this.constantExpressionEvaluator.updateNumberType(type, ktExpression, expressionTypingContext.statementFilter, expressionTypingContext.trace);
            } else {
                type = integerValueTypeConstant.getUnknownIntegerType();
            }
        } else {
            type = ((TypedCompileTimeConstant) compileTimeConstant).getType();
        }
        NewSchemeOfIntegerOperatorResolutionChecker.checkArgument(expressionTypingContext.expectedType, ktExpression, expressionTypingContext.trace, this.module);
        KotlinTypeInfo kotlinTypeInfoCreateCheckedTypeInfo = createCheckedTypeInfo(type, expressionTypingContext, ktExpression);
        if (kotlinTypeInfoCreateCheckedTypeInfo == null) {
            $$$reportNull$$$0(63);
        }
        return kotlinTypeInfoCreateCheckedTypeInfo;
    }
}
