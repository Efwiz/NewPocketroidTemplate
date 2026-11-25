package org.jetbrains.kotlin.resolve.calls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.javax.inject.Inject;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.StatementFilter;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.resolve.calls.context.CallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.CheckArgumentTypesMode;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderImplKt;
import org.jetbrains.kotlin.resolve.calls.model.MutableDataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsUtil;
import org.jetbrains.kotlin.resolve.calls.util.ResolveArgumentsMode;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstructor;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.types.FunctionPlaceholders;
import org.jetbrains.kotlin.types.FunctionPlaceholdersKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolver;
import org.jetbrains.kotlin.types.expressions.DoubleColonLHS;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;

/* JADX INFO: loaded from: classes6.dex */
public class ArgumentTypeResolver {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KotlinBuiltIns builtIns;
    private final ConstantExpressionEvaluator constantExpressionEvaluator;
    private DoubleColonExpressionResolver doubleColonExpressionResolver;
    private ExpressionTypingServices expressionTypingServices;
    private final FunctionPlaceholders functionPlaceholders;
    private final KotlinTypeChecker kotlinTypeChecker;
    private final ModuleDescriptor moduleDescriptor;
    private final ReflectionTypes reflectionTypes;
    private final TypeResolver typeResolver;

    /* JADX WARN: Code duplicated, block: B:9:0x0016  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 49 && i != 50 && i != 58 && i != 59) {
            switch (i) {
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 49 && i != 50 && i != 58 && i != 59) {
            switch (i) {
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
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
                objArr[0] = "builtIns";
                break;
            case 2:
                objArr[0] = "reflectionTypes";
                break;
            case 3:
                objArr[0] = "constantExpressionEvaluator";
                break;
            case 4:
                objArr[0] = "functionPlaceholders";
                break;
            case 5:
                objArr[0] = "moduleDescriptor";
                break;
            case 6:
                objArr[0] = "kotlinTypeChecker";
                break;
            case 7:
                objArr[0] = "expressionTypingServices";
                break;
            case 8:
                objArr[0] = "doubleColonExpressionResolver";
                break;
            case 9:
                objArr[0] = "actualType";
                break;
            case 10:
            case 71:
            case 75:
                objArr[0] = "expectedType";
                break;
            case 11:
            case 12:
            case 14:
            case 18:
            case 22:
            case 26:
            case 30:
            case 34:
            case 43:
            case 47:
            case 52:
            case 56:
            case 65:
            case 67:
                objArr[0] = "context";
                break;
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 23:
            case 25:
            case 27:
            case 29:
            case 31:
            case 33:
            case 42:
            case 45:
            case 54:
            case 68:
            case 72:
            case 77:
                objArr[0] = "expression";
                break;
            case 16:
            case 20:
            case 24:
            case 28:
            case 32:
            case 70:
            case 74:
                objArr[0] = "statementFilter";
                break;
            case 35:
            case 48:
            case 57:
            case 66:
                objArr[0] = "resolveArgumentsMode";
                break;
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 49:
            case 50:
            case 58:
            case 59:
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver";
                break;
            case 44:
                objArr[0] = "constant";
                break;
            case 46:
            case 51:
                objArr[0] = "callableReferenceExpression";
                break;
            case 53:
                objArr[0] = "overloadResolutionResults";
                break;
            case 55:
                objArr[0] = "functionLiteral";
                break;
            case 60:
                objArr[0] = "function";
                break;
            case 61:
            case 63:
                objArr[0] = "scope";
                break;
            case 62:
            case 64:
            case 69:
            case 73:
                objArr[0] = "trace";
                break;
            case 76:
                objArr[0] = "targetType";
                break;
            default:
                objArr[0] = "typeResolver";
                break;
        }
        if (i != 49 && i != 50) {
            if (i != 58 && i != 59) {
                switch (i) {
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                        objArr[1] = "getArgumentTypeInfo";
                        break;
                    default:
                        objArr[1] = "org/jetbrains/kotlin/resolve/calls/ArgumentTypeResolver";
                        break;
                }
            } else {
                objArr[1] = "getFunctionLiteralTypeInfo";
            }
        } else {
            objArr[1] = "getCallableReferenceTypeInfo";
        }
        switch (i) {
            case 7:
                objArr[2] = "setExpressionTypingServices";
                break;
            case 8:
                objArr[2] = "setDoubleColonExpressionResolver";
                break;
            case 9:
            case 10:
                objArr[2] = "isSubtypeOfForArgumentType";
                break;
            case 11:
                objArr[2] = "checkTypesWithNoCallee";
                break;
            case 12:
                objArr[2] = "checkTypesForFunctionArgumentsWithNoCallee";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[2] = "isFunctionLiteralArgument";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
                objArr[2] = "isCallableReferenceArgument";
                break;
            case 21:
            case 22:
            case 23:
            case 24:
                objArr[2] = "isFunctionLiteralOrCallableReference";
                break;
            case 25:
            case 26:
            case 27:
            case 28:
                objArr[2] = "getFunctionLiteralArgumentIfAny";
                break;
            case 29:
            case 30:
            case 31:
            case 32:
                objArr[2] = "getCallableReferenceExpressionIfAny";
                break;
            case 33:
                objArr[2] = "isCollectionLiteralArgument";
                break;
            case 34:
            case 35:
                objArr[2] = "getArgumentTypeInfo";
                break;
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 49:
            case 50:
            case 58:
            case 59:
                break;
            case 42:
            case 43:
                objArr[2] = "convertSignedConstantToUnsigned";
                break;
            case 44:
                objArr[2] = "constantCanBeConvertedToUnsigned";
                break;
            case 45:
            case 46:
            case 47:
            case 48:
                objArr[2] = "getCallableReferenceTypeInfo";
                break;
            case 51:
            case 52:
                objArr[2] = "getShapeTypeOfCallableReference";
                break;
            case 53:
                objArr[2] = "isSingleAndPossibleTransformToSuccess";
                break;
            case 54:
            case 55:
            case 56:
            case 57:
                objArr[2] = "getFunctionLiteralTypeInfo";
                break;
            case 60:
            case 61:
            case 62:
                objArr[2] = "getShapeTypeOfFunctionLiteral";
                break;
            case 63:
            case 64:
                objArr[2] = "resolveTypeRefWithDefault";
                break;
            case 65:
            case 66:
                objArr[2] = "analyzeArgumentsAndRecordTypes";
                break;
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
                objArr[2] = "updateResultArgumentTypeIfNotDenotable";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 49 && i != 50 && i != 58 && i != 59) {
            switch (i) {
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public ArgumentTypeResolver(TypeResolver typeResolver, KotlinBuiltIns kotlinBuiltIns, ReflectionTypes reflectionTypes, ConstantExpressionEvaluator constantExpressionEvaluator, FunctionPlaceholders functionPlaceholders, ModuleDescriptor moduleDescriptor, KotlinTypeChecker kotlinTypeChecker) {
        if (typeResolver == null) {
            $$$reportNull$$$0(0);
        }
        if (kotlinBuiltIns == null) {
            $$$reportNull$$$0(1);
        }
        if (reflectionTypes == null) {
            $$$reportNull$$$0(2);
        }
        if (constantExpressionEvaluator == null) {
            $$$reportNull$$$0(3);
        }
        if (functionPlaceholders == null) {
            $$$reportNull$$$0(4);
        }
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        if (kotlinTypeChecker == null) {
            $$$reportNull$$$0(6);
        }
        this.typeResolver = typeResolver;
        this.builtIns = kotlinBuiltIns;
        this.reflectionTypes = reflectionTypes;
        this.constantExpressionEvaluator = constantExpressionEvaluator;
        this.functionPlaceholders = functionPlaceholders;
        this.moduleDescriptor = moduleDescriptor;
        this.kotlinTypeChecker = kotlinTypeChecker;
    }

    @Inject
    public void setExpressionTypingServices(ExpressionTypingServices expressionTypingServices) {
        if (expressionTypingServices == null) {
            $$$reportNull$$$0(7);
        }
        this.expressionTypingServices = expressionTypingServices;
    }

    @Inject
    public void setDoubleColonExpressionResolver(DoubleColonExpressionResolver doubleColonExpressionResolver) {
        if (doubleColonExpressionResolver == null) {
            $$$reportNull$$$0(8);
        }
        this.doubleColonExpressionResolver = doubleColonExpressionResolver;
    }

    public boolean isSubtypeOfForArgumentType(KotlinType kotlinType, KotlinType kotlinType2) {
        if (kotlinType == null) {
            $$$reportNull$$$0(9);
        }
        if (kotlinType2 == null) {
            $$$reportNull$$$0(10);
        }
        if (FunctionPlaceholdersKt.isFunctionPlaceholder(kotlinType)) {
            return this.kotlinTypeChecker.isSubtypeOf(ConstraintSystemBuilderImplKt.createTypeForFunctionPlaceholder(kotlinType, kotlinType2), kotlinType2);
        }
        return this.kotlinTypeChecker.isSubtypeOf(kotlinType, kotlinType2);
    }

    public void checkTypesWithNoCallee(CallResolutionContext<?> callResolutionContext) {
        if (callResolutionContext == null) {
            $$$reportNull$$$0(11);
        }
        if (callResolutionContext.checkArguments != CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS) {
            return;
        }
        Iterator<? extends ValueArgument> it = callResolutionContext.call.getValueArguments().iterator();
        while (it.hasNext()) {
            KtExpression argumentExpression = it.next().getArgumentExpression();
            if (argumentExpression != null && !(argumentExpression instanceof KtLambdaExpression)) {
                checkArgumentTypeWithNoCallee(callResolutionContext, argumentExpression);
            }
        }
        checkTypesForFunctionArgumentsWithNoCallee(callResolutionContext);
        for (KtTypeProjection ktTypeProjection : callResolutionContext.call.getTypeArguments()) {
            KtTypeReference typeReference = ktTypeProjection.getTypeReference();
            if (typeReference == null) {
                callResolutionContext.trace.report(Errors.PROJECTION_ON_NON_CLASS_TYPE_ARGUMENT.on(ktTypeProjection));
            } else {
                this.typeResolver.resolveType(callResolutionContext.scope, typeReference, callResolutionContext.trace, true);
            }
        }
    }

    private void checkTypesForFunctionArgumentsWithNoCallee(CallResolutionContext<?> callResolutionContext) {
        if (callResolutionContext == null) {
            $$$reportNull$$$0(12);
        }
        if (callResolutionContext.checkArguments != CheckArgumentTypesMode.CHECK_VALUE_ARGUMENTS) {
            return;
        }
        Iterator<? extends ValueArgument> it = callResolutionContext.call.getValueArguments().iterator();
        while (it.hasNext()) {
            KtExpression argumentExpression = it.next().getArgumentExpression();
            if (argumentExpression != null && isFunctionLiteralArgument(argumentExpression, callResolutionContext)) {
                checkArgumentTypeWithNoCallee(callResolutionContext, argumentExpression);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [org.jetbrains.kotlin.resolve.calls.context.ResolutionContext] */
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
    private void checkArgumentTypeWithNoCallee(CallResolutionContext<?> callResolutionContext, KtExpression ktExpression) {
        this.expressionTypingServices.getTypeInfo(ktExpression, callResolutionContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE));
        updateResultArgumentTypeIfNotDenotable(callResolutionContext, ktExpression);
    }

    public static boolean isFunctionLiteralArgument(KtExpression ktExpression, ResolutionContext resolutionContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(13);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(14);
        }
        return isFunctionLiteralArgument(ktExpression, resolutionContext.statementFilter);
    }

    private static boolean isFunctionLiteralArgument(KtExpression ktExpression, StatementFilter statementFilter) {
        if (ktExpression == null) {
            $$$reportNull$$$0(15);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(16);
        }
        return getFunctionLiteralArgumentIfAny(ktExpression, statementFilter) != null;
    }

    public static boolean isCallableReferenceArgument(KtExpression ktExpression, ResolutionContext resolutionContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(17);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(18);
        }
        return isCallableReferenceArgument(ktExpression, resolutionContext.statementFilter);
    }

    private static boolean isCallableReferenceArgument(KtExpression ktExpression, StatementFilter statementFilter) {
        if (ktExpression == null) {
            $$$reportNull$$$0(19);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(20);
        }
        return getCallableReferenceExpressionIfAny(ktExpression, statementFilter) != null;
    }

    public static boolean isFunctionLiteralOrCallableReference(KtExpression ktExpression, ResolutionContext resolutionContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(21);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(22);
        }
        return isFunctionLiteralOrCallableReference(ktExpression, resolutionContext.statementFilter);
    }

    public static boolean isFunctionLiteralOrCallableReference(KtExpression ktExpression, StatementFilter statementFilter) {
        if (ktExpression == null) {
            $$$reportNull$$$0(23);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(24);
        }
        return isFunctionLiteralArgument(ktExpression, statementFilter) || isCallableReferenceArgument(ktExpression, statementFilter);
    }

    public static KtFunction getFunctionLiteralArgumentIfAny(KtExpression ktExpression, ResolutionContext resolutionContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(25);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(26);
        }
        return getFunctionLiteralArgumentIfAny(ktExpression, resolutionContext.statementFilter);
    }

    public static KtFunction getFunctionLiteralArgumentIfAny(KtExpression ktExpression, StatementFilter statementFilter) {
        if (ktExpression == null) {
            $$$reportNull$$$0(27);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(28);
        }
        KtExpression lastElementDeparenthesized = KtPsiUtil.getLastElementDeparenthesized(ktExpression, statementFilter);
        if (lastElementDeparenthesized instanceof KtLambdaExpression) {
            return ((KtLambdaExpression) lastElementDeparenthesized).getFunctionLiteral();
        }
        if (lastElementDeparenthesized instanceof KtFunction) {
            return (KtFunction) lastElementDeparenthesized;
        }
        return null;
    }

    public static KtCallableReferenceExpression getCallableReferenceExpressionIfAny(KtExpression ktExpression, ResolutionContext resolutionContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(29);
        }
        if (resolutionContext == null) {
            $$$reportNull$$$0(30);
        }
        return getCallableReferenceExpressionIfAny(ktExpression, resolutionContext.statementFilter);
    }

    public static KtCallableReferenceExpression getCallableReferenceExpressionIfAny(KtExpression ktExpression, StatementFilter statementFilter) {
        if (ktExpression == null) {
            $$$reportNull$$$0(31);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(32);
        }
        KtExpression lastElementDeparenthesized = KtPsiUtil.getLastElementDeparenthesized(ktExpression, statementFilter);
        if (lastElementDeparenthesized instanceof KtCallableReferenceExpression) {
            return (KtCallableReferenceExpression) lastElementDeparenthesized;
        }
        return null;
    }

    public static boolean isCollectionLiteralArgument(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(33);
        }
        return ktExpression instanceof KtCollectionLiteralExpression;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v3, types: [org.jetbrains.kotlin.resolve.calls.context.ResolutionContext] */
    /* JADX WARN: Type inference failed for: r8v5, types: [org.jetbrains.kotlin.resolve.calls.context.ResolutionContext] */
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
    public KotlinTypeInfo getArgumentTypeInfo(KtExpression ktExpression, CallResolutionContext<?> callResolutionContext, ResolveArgumentsMode resolveArgumentsMode, boolean z) {
        if (callResolutionContext == null) {
            $$$reportNull$$$0(34);
        }
        if (resolveArgumentsMode == null) {
            $$$reportNull$$$0(35);
        }
        if (ktExpression == null) {
            KotlinTypeInfo kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(callResolutionContext);
            if (kotlinTypeInfoNoTypeInfo == null) {
                $$$reportNull$$$0(36);
            }
            return kotlinTypeInfoNoTypeInfo;
        }
        KtFunction functionLiteralArgumentIfAny = getFunctionLiteralArgumentIfAny(ktExpression, callResolutionContext);
        if (functionLiteralArgumentIfAny != null) {
            KotlinTypeInfo functionLiteralTypeInfo = getFunctionLiteralTypeInfo(ktExpression, functionLiteralArgumentIfAny, callResolutionContext, resolveArgumentsMode, z);
            if (functionLiteralTypeInfo == null) {
                $$$reportNull$$$0(37);
            }
            return functionLiteralTypeInfo;
        }
        KtCallableReferenceExpression callableReferenceExpressionIfAny = getCallableReferenceExpressionIfAny(ktExpression, callResolutionContext);
        if (callableReferenceExpressionIfAny != null) {
            KotlinTypeInfo callableReferenceTypeInfo = getCallableReferenceTypeInfo(ktExpression, callableReferenceExpressionIfAny, callResolutionContext, resolveArgumentsMode);
            if (callableReferenceTypeInfo == null) {
                $$$reportNull$$$0(38);
            }
            return callableReferenceTypeInfo;
        }
        if (isCollectionLiteralInsideAnnotation(ktExpression, callResolutionContext)) {
            KotlinTypeInfo typeInfo = this.expressionTypingServices.getTypeInfo(ktExpression, callResolutionContext.replaceContextDependency(ContextDependency.INDEPENDENT));
            if (typeInfo == null) {
                $$$reportNull$$$0(39);
            }
            return typeInfo;
        }
        UnsignedTypes unsignedTypes = UnsignedTypes.INSTANCE;
        if (UnsignedTypes.isUnsignedType(callResolutionContext.expectedType)) {
            convertSignedConstantToUnsigned(ktExpression, callResolutionContext);
        }
        KotlinTypeInfo recordedTypeInfo = BindingContextUtils.getRecordedTypeInfo(ktExpression, callResolutionContext.trace.getContext());
        if (recordedTypeInfo != null) {
            if (recordedTypeInfo == null) {
                $$$reportNull$$$0(40);
            }
            return recordedTypeInfo;
        }
        KotlinTypeInfo typeInfo2 = this.expressionTypingServices.getTypeInfo(ktExpression, ((CallResolutionContext) callResolutionContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE)).replaceContextDependency(ContextDependency.DEPENDENT));
        if (typeInfo2 == null) {
            $$$reportNull$$$0(41);
        }
        return typeInfo2;
    }

    private void convertSignedConstantToUnsigned(KtExpression ktExpression, CallResolutionContext<?> callResolutionContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(42);
        }
        if (callResolutionContext == null) {
            $$$reportNull$$$0(43);
        }
        CompileTimeConstant compileTimeConstant = (CompileTimeConstant) callResolutionContext.trace.get(BindingContext.COMPILE_TIME_VALUE, ktExpression);
        if ((compileTimeConstant instanceof IntegerValueTypeConstant) && constantCanBeConvertedToUnsigned(compileTimeConstant)) {
            IntegerValueTypeConstant integerValueTypeConstantConvertToUnsignedConstant = IntegerValueTypeConstant.convertToUnsignedConstant((IntegerValueTypeConstant) compileTimeConstant, this.moduleDescriptor);
            callResolutionContext.trace.record(BindingContext.COMPILE_TIME_VALUE, ktExpression, integerValueTypeConstantConvertToUnsignedConstant);
            updateResultArgumentTypeIfNotDenotable(callResolutionContext.trace, callResolutionContext.statementFilter, callResolutionContext.expectedType, integerValueTypeConstantConvertToUnsignedConstant.getUnknownIntegerType(), ktExpression);
        }
    }

    public static boolean constantCanBeConvertedToUnsigned(CompileTimeConstant<?> compileTimeConstant) {
        if (compileTimeConstant == null) {
            $$$reportNull$$$0(44);
        }
        return !compileTimeConstant.isError() && compileTimeConstant.getParameters().isPure();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1, types: [org.jetbrains.kotlin.resolve.calls.context.ResolutionContext] */
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
    public KotlinTypeInfo getCallableReferenceTypeInfo(KtExpression ktExpression, KtCallableReferenceExpression ktCallableReferenceExpression, CallResolutionContext<?> callResolutionContext, ResolveArgumentsMode resolveArgumentsMode) {
        if (ktExpression == null) {
            $$$reportNull$$$0(45);
        }
        if (ktCallableReferenceExpression == null) {
            $$$reportNull$$$0(46);
        }
        if (callResolutionContext == null) {
            $$$reportNull$$$0(47);
        }
        if (resolveArgumentsMode == null) {
            $$$reportNull$$$0(48);
        }
        if (resolveArgumentsMode == ResolveArgumentsMode.SHAPE_FUNCTION_ARGUMENTS) {
            KotlinTypeInfo kotlinTypeInfoCreateTypeInfo = TypeInfoFactoryKt.createTypeInfo(getShapeTypeOfCallableReference(ktCallableReferenceExpression, callResolutionContext, true));
            if (kotlinTypeInfoCreateTypeInfo == null) {
                $$$reportNull$$$0(49);
            }
            return kotlinTypeInfoCreateTypeInfo;
        }
        KotlinTypeInfo typeInfo = this.expressionTypingServices.getTypeInfo(ktExpression, callResolutionContext.replaceContextDependency(ContextDependency.INDEPENDENT));
        if (typeInfo == null) {
            $$$reportNull$$$0(50);
        }
        return typeInfo;
    }

    public KotlinType getShapeTypeOfCallableReference(KtCallableReferenceExpression ktCallableReferenceExpression, CallResolutionContext<?> callResolutionContext, boolean z) {
        if (ktCallableReferenceExpression == null) {
            $$$reportNull$$$0(51);
        }
        if (callResolutionContext == null) {
            $$$reportNull$$$0(52);
        }
        Pair<DoubleColonLHS, OverloadResolutionResults<?>> pairResolveCallableReference = this.doubleColonExpressionResolver.resolveCallableReference(ktCallableReferenceExpression, ExpressionTypingContext.newContext(callResolutionContext), ResolveArgumentsMode.SHAPE_FUNCTION_ARGUMENTS);
        DoubleColonLHS first = pairResolveCallableReference.getFirst();
        OverloadResolutionResults<?> second = pairResolveCallableReference.getSecond();
        if (second == null) {
            return null;
        }
        if (!isSingleAndPossibleTransformToSuccess(second)) {
            if (z) {
                return this.functionPlaceholders.createFunctionPlaceholderType(Collections.emptyList(), false);
            }
            return FunctionTypesKt.createFunctionType(this.builtIns, Annotations.INSTANCE.getEMPTY(), null, Collections.emptyList(), Collections.emptyList(), null, TypeUtils.DONT_CARE);
        }
        ResolvedCall resultingCall = OverloadResolutionResultsUtil.getResultingCall(second, callResolutionContext);
        if (resultingCall == null) {
            return null;
        }
        return DoubleColonExpressionResolver.INSTANCE.createKCallableTypeForReference(resultingCall.getResultingDescriptor(), first, this.reflectionTypes, callResolutionContext.scope.getOwnerDescriptor());
    }

    private static boolean isSingleAndPossibleTransformToSuccess(OverloadResolutionResults<?> overloadResolutionResults) {
        ResolvedCall resolvedCall;
        if (overloadResolutionResults == null) {
            $$$reportNull$$$0(53);
        }
        return overloadResolutionResults.isSingleResult() && (resolvedCall = (ResolvedCall) CollectionsKt.singleOrNull(overloadResolutionResults.getResultingCalls())) != null && resolvedCall.getStatus().possibleTransformToSuccess();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v1, types: [org.jetbrains.kotlin.resolve.calls.context.ResolutionContext] */
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
    public KotlinTypeInfo getFunctionLiteralTypeInfo(KtExpression ktExpression, KtFunction ktFunction, CallResolutionContext<?> callResolutionContext, ResolveArgumentsMode resolveArgumentsMode, boolean z) {
        if (ktExpression == null) {
            $$$reportNull$$$0(54);
        }
        if (ktFunction == null) {
            $$$reportNull$$$0(55);
        }
        if (callResolutionContext == null) {
            $$$reportNull$$$0(56);
        }
        if (resolveArgumentsMode == null) {
            $$$reportNull$$$0(57);
        }
        if (resolveArgumentsMode == ResolveArgumentsMode.SHAPE_FUNCTION_ARGUMENTS) {
            KotlinTypeInfo kotlinTypeInfoCreateTypeInfo = TypeInfoFactoryKt.createTypeInfo(getShapeTypeOfFunctionLiteral(ktFunction, callResolutionContext.scope, callResolutionContext.trace, true, z), callResolutionContext);
            if (kotlinTypeInfoCreateTypeInfo == null) {
                $$$reportNull$$$0(58);
            }
            return kotlinTypeInfoCreateTypeInfo;
        }
        KotlinTypeInfo typeInfo = this.expressionTypingServices.getTypeInfo(ktExpression, callResolutionContext.replaceContextDependency(ContextDependency.INDEPENDENT));
        if (typeInfo == null) {
            $$$reportNull$$$0(59);
        }
        return typeInfo;
    }

    public KotlinType getShapeTypeOfFunctionLiteral(KtFunction ktFunction, final LexicalScope lexicalScope, BindingTrace bindingTrace, boolean z, boolean z2) {
        if (ktFunction == null) {
            $$$reportNull$$$0(60);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(61);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(62);
        }
        boolean z3 = ktFunction instanceof KtFunctionLiteral;
        if (ktFunction.getValueParameterList() == null && z3) {
            if (z) {
                return this.functionPlaceholders.createFunctionPlaceholderType(Collections.emptyList(), false);
            }
            return FunctionTypesKt.createFunctionType(this.builtIns, Annotations.INSTANCE.getEMPTY(), null, Collections.emptyList(), Collections.emptyList(), null, TypeUtils.DONT_CARE);
        }
        List<KtParameter> valueParameters = ktFunction.getValueParameters();
        final TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(bindingTrace, "trace to resolve function literal parameter types");
        ArrayList arrayList = new ArrayList(valueParameters.size());
        ArrayList arrayList2 = new ArrayList(valueParameters.size());
        for (KtParameter ktParameter : valueParameters) {
            arrayList.add(resolveTypeRefWithDefault(ktParameter.mo7396getTypeReference(), lexicalScope, temporaryBindingTraceCreate, TypeUtils.DONT_CARE));
            Name nameAsName = ktParameter.getNameAsName();
            if (nameAsName == null) {
                nameAsName = SpecialNames.NO_NAME_PROVIDED;
            }
            arrayList2.add(nameAsName);
        }
        KotlinType kotlinTypeResolveTypeRefWithDefault = resolveTypeRefWithDefault(ktFunction.mo7396getTypeReference(), lexicalScope, temporaryBindingTraceCreate, TypeUtils.DONT_CARE);
        KotlinType kotlinTypeResolveTypeRefWithDefault2 = resolveTypeRefWithDefault(ktFunction.mo7393getReceiverTypeReference(), lexicalScope, temporaryBindingTraceCreate, null);
        List list = (List) ktFunction.getContextReceivers().stream().map(new Function() { // from class: org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m7426xc5bca928(lexicalScope, temporaryBindingTraceCreate, (KtContextReceiver) obj);
            }
        }).collect(Collectors.toList());
        if (z && z3) {
            return this.functionPlaceholders.createFunctionPlaceholderType(arrayList, true);
        }
        return FunctionTypesKt.createFunctionType(this.builtIns, Annotations.INSTANCE.getEMPTY(), kotlinTypeResolveTypeRefWithDefault2, list, arrayList, arrayList2, kotlinTypeResolveTypeRefWithDefault, z2);
    }

    /* JADX INFO: renamed from: lambda$getShapeTypeOfFunctionLiteral$0$org-jetbrains-kotlin-resolve-calls-ArgumentTypeResolver, reason: not valid java name */
    /* synthetic */ KotlinType m7426xc5bca928(LexicalScope lexicalScope, TemporaryBindingTrace temporaryBindingTrace, KtContextReceiver ktContextReceiver) {
        return resolveTypeRefWithDefault(ktContextReceiver.typeReference(), lexicalScope, temporaryBindingTrace, null);
    }

    public KotlinType resolveTypeRefWithDefault(KtTypeReference ktTypeReference, LexicalScope lexicalScope, BindingTrace bindingTrace, KotlinType kotlinType) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(63);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(64);
        }
        return ktTypeReference != null ? this.typeResolver.resolveType(lexicalScope, ktTypeReference, bindingTrace, true) : kotlinType;
    }

    public void analyzeArgumentsAndRecordTypes(CallResolutionContext<?> callResolutionContext, ResolveArgumentsMode resolveArgumentsMode) {
        if (callResolutionContext == null) {
            $$$reportNull$$$0(65);
        }
        if (resolveArgumentsMode == null) {
            $$$reportNull$$$0(66);
        }
        MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments = callResolutionContext.dataFlowInfoForArguments;
        for (ValueArgument valueArgument : callResolutionContext.call.getValueArguments()) {
            KtExpression argumentExpression = valueArgument.getArgumentExpression();
            if (argumentExpression != null && !isCollectionLiteralInsideAnnotation(argumentExpression, callResolutionContext)) {
                mutableDataFlowInfoForArguments.updateInfo(valueArgument, getArgumentTypeInfo(argumentExpression, (CallResolutionContext) callResolutionContext.replaceDataFlowInfo(mutableDataFlowInfoForArguments.getInfo(valueArgument)), resolveArgumentsMode, false).getDataFlowInfo());
            }
        }
    }

    public KotlinType updateResultArgumentTypeIfNotDenotable(ResolutionContext resolutionContext, KtExpression ktExpression) {
        if (resolutionContext == null) {
            $$$reportNull$$$0(67);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(68);
        }
        return updateResultArgumentTypeIfNotDenotable(resolutionContext.trace, resolutionContext.statementFilter, resolutionContext.expectedType, ktExpression);
    }

    public KotlinType updateResultArgumentTypeIfNotDenotable(BindingTrace bindingTrace, StatementFilter statementFilter, KotlinType kotlinType, KtExpression ktExpression) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(69);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(70);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(71);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(72);
        }
        KotlinType type = bindingTrace.getType(ktExpression);
        if (type != null) {
            return updateResultArgumentTypeIfNotDenotable(bindingTrace, statementFilter, kotlinType, type, ktExpression);
        }
        return null;
    }

    public KotlinType updateResultArgumentTypeIfNotDenotable(BindingTrace bindingTrace, StatementFilter statementFilter, KotlinType kotlinType, KotlinType kotlinType2, KtExpression ktExpression) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(73);
        }
        if (statementFilter == null) {
            $$$reportNull$$$0(74);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(75);
        }
        if (kotlinType2 == null) {
            $$$reportNull$$$0(76);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(77);
        }
        TypeConstructor constructor = kotlinType2.getConstructor();
        if (constructor.isDenotable()) {
            return null;
        }
        if (constructor instanceof IntegerValueTypeConstructor) {
            KotlinType primitiveNumberType = TypeUtils.getPrimitiveNumberType((IntegerValueTypeConstructor) constructor, kotlinType);
            this.constantExpressionEvaluator.updateNumberType(primitiveNumberType, ktExpression, statementFilter, bindingTrace);
            return primitiveNumberType;
        }
        if (!(constructor instanceof IntegerLiteralTypeConstructor)) {
            return null;
        }
        KotlinType primitiveNumberType2 = TypeUtils.getPrimitiveNumberType((IntegerLiteralTypeConstructor) constructor, kotlinType);
        this.constantExpressionEvaluator.updateNumberType(primitiveNumberType2, ktExpression, statementFilter, bindingTrace);
        return primitiveNumberType2;
    }

    private static boolean isCollectionLiteralInsideAnnotation(KtExpression ktExpression, CallResolutionContext<?> callResolutionContext) {
        return (ktExpression instanceof KtCollectionLiteralExpression) && (callResolutionContext.call.getCallElement() instanceof KtAnnotationEntry);
    }
}
