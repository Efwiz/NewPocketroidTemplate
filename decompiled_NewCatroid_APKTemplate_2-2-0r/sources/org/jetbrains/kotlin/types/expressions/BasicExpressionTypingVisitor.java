package org.jetbrains.kotlin.types.expressions;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.lexer.KtKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.parsing.ParseUtilsKt;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtEscapeStringTemplateEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtInstanceExpressionWithLabel;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtOperationExpression;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPrefixExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateEntryWithExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.ReservedCheckingKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.ObservableBindingTrace;
import org.jetbrains.kotlin.resolve.PossiblyBareType;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.TypeResolutionContext;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.checkers.EqualityCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.RttiExpressionChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.RttiExpressionInformation;
import org.jetbrains.kotlin.resolve.calls.checkers.RttiOperation;
import org.jetbrains.kotlin.resolve.calls.context.ContextDependency;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession;
import org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArgumentsImpl;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsImpl;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResultsUtil;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.Nullability;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tasks.OldResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.checkers.UnderscoreChecker;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstantChecker;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.ErrorValue;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.resolve.constants.TypedCompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.UnsignedErrorValueTypeConstant;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ContextReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.types.CastDiagnosticsUtil;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeIntersector;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.WrappedTypeFactory;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.types.expressions.unqualifiedSuper.UnqualifiedSuperKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: loaded from: classes6.dex */
public class BasicExpressionTypingVisitor extends ExpressionTypingVisitor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final TokenSet BARE_TYPES_ALLOWED = TokenSet.create(KtTokens.AS_KEYWORD, KtTokens.AS_SAFE);

    /* JADX WARN: Code duplicated, block: B:18:0x0038 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 43 && i != 44 && i != 64 && i != 66 && i != 89 && i != 95 && i != 100 && i != 79 && i != 80) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                default:
                                    switch (i) {
                                        default:
                                            switch (i) {
                                                case 119:
                                                case 120:
                                                case 121:
                                                    break;
                                                default:
                                                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                                                    break;
                                            }
                                        case 85:
                                        case 86:
                                        case 87:
                                            str = "@NotNull method %s.%s must not return null";
                                            break;
                                    }
                                case 69:
                                case 70:
                                case 71:
                                case 72:
                                case 73:
                                case 74:
                                    str = "@NotNull method %s.%s must not return null";
                                    break;
                            }
                        case 28:
                        case 29:
                        case 30:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 43 && i != 44 && i != 64 && i != 66 && i != 89 && i != 95 && i != 100 && i != 79 && i != 80) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 28:
                        case 29:
                        case 30:
                            i2 = 2;
                            break;
                        default:
                            switch (i) {
                                case 69:
                                case 70:
                                case 71:
                                case 72:
                                case 73:
                                case 74:
                                    i2 = 2;
                                    break;
                                default:
                                    switch (i) {
                                        case 85:
                                        case 86:
                                        case 87:
                                            i2 = 2;
                                            break;
                                        default:
                                            switch (i) {
                                                case 119:
                                                case 120:
                                                case 121:
                                                    i2 = 2;
                                                    break;
                                                default:
                                                    i2 = 3;
                                                    break;
                                            }
                                            break;
                                    }
                                    break;
                            }
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
            case 2:
            case 3:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 18:
            case 19:
            case 22:
            case 23:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 40:
            case 41:
            case 45:
            case 54:
            case 56:
            case 57:
            case 60:
            case 61:
            case 67:
            case 90:
            case 91:
            case 102:
            case 103:
            case 104:
            case 126:
                objArr[0] = "expression";
                break;
            case 4:
            case 6:
            case 12:
            case 20:
            case 39:
            case 42:
            case 46:
            case 48:
            case 51:
            case 55:
            case 58:
            case 62:
            case 65:
            case 77:
            case 84:
            case 88:
            case 94:
            case 98:
            case 108:
            case 112:
            case 115:
            case 124:
            case 128:
                objArr[0] = "context";
                break;
            case 11:
                objArr[0] = "elementType";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 28:
            case 29:
            case 30:
            case 43:
            case 44:
            case 64:
            case 66:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 79:
            case 80:
            case 85:
            case 86:
            case 87:
            case 89:
            case 95:
            case 100:
            case 119:
            case 120:
            case 121:
                objArr[0] = "org/jetbrains/kotlin/types/expressions/BasicExpressionTypingVisitor";
                break;
            case 21:
                objArr[0] = "targetType";
                break;
            case 24:
            case 47:
                objArr[0] = "trace";
                break;
            case 25:
                objArr[0] = "result";
                break;
            case 26:
                objArr[0] = "supertypes";
                break;
            case 27:
                objArr[0] = "superTypeQualifier";
                break;
            case 49:
                objArr[0] = "expressionWithParenthesis";
                break;
            case 50:
                objArr[0] = "operationExpression";
                break;
            case 52:
                objArr[0] = "descriptor";
                break;
            case 53:
                objArr[0] = "propertyResolvedCall";
                break;
            case 59:
                objArr[0] = "receiver";
                break;
            case 63:
            case 82:
                objArr[0] = "operationSign";
                break;
            case 68:
                objArr[0] = "contextWithExpectedType";
                break;
            case 75:
                objArr[0] = "left";
                break;
            case 76:
                objArr[0] = "dataFlowInfo";
                break;
            case 78:
                objArr[0] = "languageVersionSettings";
                break;
            case 81:
                objArr[0] = "callElement";
                break;
            case 83:
                objArr[0] = "leftArgument";
                break;
            case 92:
                objArr[0] = "klass";
                break;
            case 93:
                objArr[0] = "declaration";
                break;
            case 96:
                objArr[0] = "property";
                break;
            case 97:
                objArr[0] = "name";
                break;
            case 99:
                objArr[0] = "binaryExpression";
                break;
            case 101:
                objArr[0] = "dcl";
                break;
            case 105:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 106:
            case 110:
            case 114:
            case 116:
                objArr[0] = "arrayAccessExpression";
                break;
            case 107:
            case 111:
                objArr[0] = "rightHandSide";
                break;
            case 109:
            case 113:
            case 118:
                objArr[0] = "traceForResolveResult";
                break;
            case 117:
                objArr[0] = "oldContext";
                break;
            case 122:
                objArr[0] = "arrayTypeInfo";
                break;
            case 123:
                objArr[0] = "indices";
                break;
            case 125:
            case 127:
            default:
                objArr[0] = "facade";
                break;
        }
        if (i == 43 || i == 44) {
            objArr[1] = "visitLabeledExpression";
        } else if (i == 64) {
            objArr[1] = "visitComparison";
        } else if (i == 66) {
            objArr[1] = "visitBooleanOperationExpression";
        } else if (i == 89) {
            objArr[1] = "assignmentIsNotAnExpressionError";
        } else if (i == 95) {
            objArr[1] = "declarationInIllegalContext";
        } else if (i == 100) {
            objArr[1] = "getTypeInfoForBinaryCall";
        } else if (i != 79 && i != 80) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    objArr[1] = "getDefaultType";
                    break;
                default:
                    switch (i) {
                        case 28:
                        case 29:
                        case 30:
                            objArr[1] = "resolveToReceiver";
                            break;
                        default:
                            switch (i) {
                                case 69:
                                case 70:
                                case 71:
                                case 72:
                                case 73:
                                case 74:
                                    objArr[1] = "visitElvisExpression";
                                    break;
                                default:
                                    switch (i) {
                                        case 85:
                                        case 86:
                                        case 87:
                                            objArr[1] = "checkInExpression";
                                            break;
                                        default:
                                            switch (i) {
                                                case 119:
                                                case 120:
                                                case 121:
                                                    objArr[1] = "resolveArrayAccessSpecialMethod";
                                                    break;
                                                default:
                                                    objArr[1] = "org/jetbrains/kotlin/types/expressions/BasicExpressionTypingVisitor";
                                                    break;
                                            }
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "establishSubtypingForTypeRHS";
        }
        switch (i) {
            case 1:
                objArr[2] = "isLValueOrUnsafeReceiver";
                break;
            case 2:
                objArr[2] = "isLValue";
                break;
            case 3:
            case 4:
                objArr[2] = "isDangerousWithNull";
                break;
            case 5:
            case 6:
                objArr[2] = "checkNull";
                break;
            case 7:
                objArr[2] = "visitSimpleNameExpression";
                break;
            case 8:
                objArr[2] = "visitParenthesizedExpression";
                break;
            case 9:
                objArr[2] = "visitConstantExpression";
                break;
            case 10:
            case 11:
            case 12:
                objArr[2] = "checkUnderscores";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 28:
            case 29:
            case 30:
            case 43:
            case 44:
            case 64:
            case 66:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 79:
            case 80:
            case 85:
            case 86:
            case 87:
            case 89:
            case 95:
            case 100:
            case 119:
            case 120:
            case 121:
                break;
            case 18:
                objArr[2] = "visitBinaryWithTypeRHSExpression";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "checkBinaryWithTypeRHS";
                break;
            case 22:
                objArr[2] = "visitThisExpression";
                break;
            case 23:
                objArr[2] = "visitSuperExpression";
                break;
            case 24:
            case 25:
            case 26:
            case 27:
                objArr[2] = "checkResolvedExplicitlyQualifiedSupertype";
                break;
            case 31:
                objArr[2] = "visitBlockExpression";
                break;
            case 32:
                objArr[2] = "visitClassLiteralExpression";
                break;
            case 33:
                objArr[2] = "visitCallableReferenceExpression";
                break;
            case 34:
                objArr[2] = "visitObjectLiteralExpression";
                break;
            case 35:
                objArr[2] = "visitQualifiedExpression";
                break;
            case 36:
                objArr[2] = "visitCallExpression";
                break;
            case 37:
                objArr[2] = "visitUnaryExpression";
                break;
            case 38:
            case 39:
                objArr[2] = "visitExclExclExpression";
                break;
            case 40:
            case 41:
            case 42:
                objArr[2] = "visitLabeledExpression";
                break;
            case 45:
            case 46:
                objArr[2] = "isKnownToBeNotNull";
                break;
            case 47:
            case 48:
            case 49:
            case 50:
                objArr[2] = "checkLValue";
                break;
            case 51:
            case 52:
            case 53:
            case 54:
                objArr[2] = "checkPropertySetterCall";
                break;
            case 55:
                objArr[2] = "createCallCheckerContext";
                break;
            case 56:
                objArr[2] = "visitBinaryExpression";
                break;
            case 57:
            case 58:
                objArr[2] = "visitIdentityEquality";
                break;
            case 59:
            case 60:
                objArr[2] = "findEqualsWithNullableAnyParameter";
                break;
            case 61:
            case 62:
            case 63:
                objArr[2] = "visitComparison";
                break;
            case 65:
                objArr[2] = "visitBooleanOperationExpression";
                break;
            case 67:
            case 68:
                objArr[2] = "visitElvisExpression";
                break;
            case 75:
            case 76:
            case 77:
            case 78:
                objArr[2] = "establishSubtypingForTypeRHS";
                break;
            case 81:
            case 82:
            case 83:
            case 84:
                objArr[2] = "checkInExpression";
                break;
            case 88:
                objArr[2] = "isResolutionSuccessfulWithOnlyInputTypesWarnings";
                break;
            case 90:
                objArr[2] = "visitArrayAccessExpression";
                break;
            case 91:
                objArr[2] = "visitCollectionLiteralExpression";
                break;
            case 92:
                objArr[2] = "visitClass";
                break;
            case 93:
            case 94:
                objArr[2] = "declarationInIllegalContext";
                break;
            case 96:
                objArr[2] = "visitProperty";
                break;
            case 97:
            case 98:
            case 99:
                objArr[2] = "getTypeInfoForBinaryCall";
                break;
            case 101:
                objArr[2] = "visitDeclaration";
                break;
            case 102:
                objArr[2] = "visitStringTemplateExpression";
                break;
            case 103:
                objArr[2] = "checkLiteralPrefixAndSuffix";
                break;
            case 104:
                objArr[2] = "visitAnnotatedExpression";
                break;
            case 105:
                objArr[2] = "visitKtElement";
                break;
            case 106:
            case 107:
            case 108:
            case 109:
                objArr[2] = "resolveArrayAccessSetMethod";
                break;
            case 110:
            case 111:
            case 112:
            case 113:
                objArr[2] = "resolveImplicitArrayAccessSetMethod";
                break;
            case 114:
            case 115:
                objArr[2] = "resolveArrayAccessGetMethod";
                break;
            case 116:
            case 117:
            case 118:
                objArr[2] = "resolveArrayAccessSpecialMethod";
                break;
            case 122:
            case 123:
            case 124:
            case 125:
                objArr[2] = "computeAccumulatedInfoForArrayAccessExpression";
                break;
            case 126:
            case 127:
            case 128:
                objArr[2] = "getTypeInfo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 43 && i != 44 && i != 64 && i != 66 && i != 89 && i != 95 && i != 100 && i != 79 && i != 80) {
            switch (i) {
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    break;
                default:
                    switch (i) {
                        case 28:
                        case 29:
                        case 30:
                            break;
                        default:
                            switch (i) {
                                case 69:
                                case 70:
                                case 71:
                                case 72:
                                case 73:
                                case 74:
                                    break;
                                default:
                                    switch (i) {
                                        case 85:
                                        case 86:
                                        case 87:
                                            break;
                                        default:
                                            switch (i) {
                                                case 119:
                                                case 120:
                                                case 121:
                                                    break;
                                                default:
                                                    throw new IllegalArgumentException(str2);
                                            }
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected BasicExpressionTypingVisitor(ExpressionTypingInternals expressionTypingInternals) {
        super(expressionTypingInternals);
        if (expressionTypingInternals == null) {
            $$$reportNull$$$0(0);
        }
    }

    private static boolean isLValueOrUnsafeReceiver(KtSimpleNameExpression ktSimpleNameExpression) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(1);
        }
        PsiElement psiElementSkipParentsOfType = PsiTreeUtil.skipParentsOfType(ktSimpleNameExpression, KtParenthesizedExpression.class);
        if (psiElementSkipParentsOfType instanceof KtQualifiedExpression) {
            KtQualifiedExpression ktQualifiedExpression = (KtQualifiedExpression) psiElementSkipParentsOfType;
            return ktQualifiedExpression.getOperationSign() == KtTokens.DOT && ktQualifiedExpression.getReceiverExpression() == KtPsiUtil.deparenthesize(ktSimpleNameExpression);
        }
        return isLValue(ktSimpleNameExpression, psiElementSkipParentsOfType);
    }

    public static boolean isLValue(KtSimpleNameExpression ktSimpleNameExpression, PsiElement psiElement) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(2);
        }
        if (!(psiElement instanceof KtBinaryExpression)) {
            return false;
        }
        KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) psiElement;
        if (OperatorConventions.BINARY_OPERATION_NAMES.containsKey(ktBinaryExpression.getOperationToken()) || KtTokens.ALL_ASSIGNMENTS.contains(ktBinaryExpression.getOperationToken())) {
            return PsiTreeUtil.isAncestor(ktBinaryExpression.getLeft(), ktSimpleNameExpression, false);
        }
        return false;
    }

    private static boolean isDangerousWithNull(KtSimpleNameExpression ktSimpleNameExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(3);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(4);
        }
        PsiElement psiElementSkipParentsOfType = PsiTreeUtil.skipParentsOfType(ktSimpleNameExpression, KtParenthesizedExpression.class);
        if (psiElementSkipParentsOfType instanceof KtUnaryExpression) {
            return ((KtUnaryExpression) psiElementSkipParentsOfType).getOperationToken() == KtTokens.EXCLEXCL;
        }
        if (!(psiElementSkipParentsOfType instanceof KtBinaryExpressionWithTypeRHS)) {
            return false;
        }
        KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS = (KtBinaryExpressionWithTypeRHS) psiElementSkipParentsOfType;
        KotlinType kotlinType = (KotlinType) expressionTypingContext.trace.get(BindingContext.TYPE, ktBinaryExpressionWithTypeRHS.getRight());
        return (kotlinType == null || kotlinType.getIsMarkedNullable() || ktBinaryExpressionWithTypeRHS.getOperationReference().getReferencedNameElementType() != KtTokens.AS_KEYWORD) ? false : true;
    }

    private void checkNull(KtSimpleNameExpression ktSimpleNameExpression, ExpressionTypingContext expressionTypingContext, KotlinType kotlinType) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(5);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(6);
        }
        if (kotlinType == null || KotlinTypeKt.isError(kotlinType) || isLValueOrUnsafeReceiver(ktSimpleNameExpression)) {
            return;
        }
        Nullability stableNullability = expressionTypingContext.dataFlowInfo.getStableNullability(this.components.dataFlowValueFactory.createDataFlowValue(ktSimpleNameExpression, kotlinType, expressionTypingContext));
        if (stableNullability.canBeNonNull() || !stableNullability.canBeNull()) {
            return;
        }
        if (isDangerousWithNull(ktSimpleNameExpression, expressionTypingContext)) {
            expressionTypingContext.trace.report(Errors.ALWAYS_NULL.on(ktSimpleNameExpression));
        } else {
            expressionTypingContext.trace.record(BindingContext.SMARTCAST_NULL, ktSimpleNameExpression);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitSimpleNameExpression(KtSimpleNameExpression ktSimpleNameExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(7);
        }
        if (!this.components.languageVersionSettings.supportsFeature(LanguageFeature.YieldIsNoMoreReserved)) {
            ReservedCheckingKt.checkReservedYield(ktSimpleNameExpression, expressionTypingContext.trace);
        }
        KotlinTypeInfo simpleNameExpressionTypeInfo = this.components.callExpressionResolver.getSimpleNameExpressionTypeInfo(ktSimpleNameExpression, null, null, expressionTypingContext);
        checkNull(ktSimpleNameExpression, expressionTypingContext, simpleNameExpressionTypeInfo.getType());
        this.components.constantExpressionEvaluator.evaluateExpression(ktSimpleNameExpression, expressionTypingContext.trace, expressionTypingContext.expectedType);
        return this.components.dataFlowAnalyzer.checkType(simpleNameExpressionTypeInfo, ktSimpleNameExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitParenthesizedExpression(KtParenthesizedExpression ktParenthesizedExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktParenthesizedExpression == null) {
            $$$reportNull$$$0(8);
        }
        KtExpression expression = ktParenthesizedExpression.getExpression();
        if (expression == null) {
            return TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
        }
        KotlinTypeInfo typeInfo = this.facade.getTypeInfo(expression, expressionTypingContext);
        KotlinType type = typeInfo.getType();
        if (type == null) {
            return typeInfo;
        }
        DataFlowValue dataFlowValueCreateDataFlowValue = this.components.dataFlowValueFactory.createDataFlowValue(expression, type, expressionTypingContext);
        return typeInfo.replaceDataFlowInfo(typeInfo.getDataFlowInfo().assign(this.components.dataFlowValueFactory.createDataFlowValue(ktParenthesizedExpression, type, expressionTypingContext), dataFlowValueCreateDataFlowValue, this.components.languageVersionSettings));
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitConstantExpression(KtConstantExpression ktConstantExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(9);
        }
        IElementType elementType = ktConstantExpression.getNode().getElementType();
        if (elementType == KtNodeTypes.CHARACTER_CONSTANT || elementType == KtNodeTypes.INTEGER_CONSTANT || elementType == KtNodeTypes.FLOAT_CONSTANT) {
            checkLiteralPrefixAndSuffix(ktConstantExpression, expressionTypingContext);
        }
        if (elementType == KtNodeTypes.INTEGER_CONSTANT || elementType == KtNodeTypes.FLOAT_CONSTANT) {
            checkUnderscores(ktConstantExpression, elementType, expressionTypingContext);
        }
        CompileTimeConstant<?> compileTimeConstantEvaluateExpression = this.components.constantExpressionEvaluator.evaluateExpression(ktConstantExpression, expressionTypingContext.trace, expressionTypingContext.expectedType);
        if (compileTimeConstantEvaluateExpression instanceof UnsignedErrorValueTypeConstant) {
            ErrorValue.ErrorValueWithMessage errorValue = ((UnsignedErrorValueTypeConstant) compileTimeConstantEvaluateExpression).getErrorValue();
            expressionTypingContext.trace.report(Errors.UNSIGNED_LITERAL_WITHOUT_DECLARATIONS_ON_CLASSPATH.on(ktConstantExpression));
            return TypeInfoFactoryKt.createTypeInfo(errorValue.getType(this.components.moduleDescriptor), expressionTypingContext);
        }
        if (!(compileTimeConstantEvaluateExpression instanceof IntegerValueTypeConstant)) {
            CompileTimeConstantChecker compileTimeConstantChecker = new CompileTimeConstantChecker(expressionTypingContext, this.components.moduleDescriptor, false);
            ConstantValue<?> constantValue = compileTimeConstantEvaluateExpression != null ? ((TypedCompileTimeConstant) compileTimeConstantEvaluateExpression).getConstantValue() : null;
            if (compileTimeConstantChecker.checkConstantExpressionType(constantValue, ktConstantExpression, expressionTypingContext.expectedType)) {
                return TypeInfoFactoryKt.createTypeInfo(constantValue != null ? constantValue.getType(this.components.moduleDescriptor) : getDefaultType(elementType), expressionTypingContext);
            }
        }
        return this.components.dataFlowAnalyzer.createCompileTimeConstantTypeInfo(compileTimeConstantEvaluateExpression, ktConstantExpression, expressionTypingContext);
    }

    private void checkUnderscores(KtConstantExpression ktConstantExpression, IElementType iElementType, ExpressionTypingContext expressionTypingContext) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(10);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(11);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(12);
        }
        if (ktConstantExpression.getText().toLowerCase().contains("_")) {
            if (!this.components.languageVersionSettings.supportsFeature(LanguageFeature.UnderscoresInNumericLiterals)) {
                expressionTypingContext.trace.report(Errors.UNSUPPORTED_FEATURE.on(ktConstantExpression, TuplesKt.to(LanguageFeature.UnderscoresInNumericLiterals, this.components.languageVersionSettings)));
            } else if (ParseUtilsKt.hasIllegalUnderscore(ktConstantExpression.getText(), iElementType)) {
                expressionTypingContext.trace.report(Errors.ILLEGAL_UNDERSCORE.on(ktConstantExpression));
            }
        }
    }

    public KotlinType getDefaultType(IElementType iElementType) {
        KotlinBuiltIns kotlinBuiltIns = this.components.builtIns;
        if (iElementType == KtNodeTypes.INTEGER_CONSTANT) {
            SimpleType intType = kotlinBuiltIns.getIntType();
            if (intType == null) {
                $$$reportNull$$$0(13);
            }
            return intType;
        }
        if (iElementType == KtNodeTypes.FLOAT_CONSTANT) {
            SimpleType doubleType = kotlinBuiltIns.getDoubleType();
            if (doubleType == null) {
                $$$reportNull$$$0(14);
            }
            return doubleType;
        }
        if (iElementType == KtNodeTypes.BOOLEAN_CONSTANT) {
            SimpleType booleanType = kotlinBuiltIns.getBooleanType();
            if (booleanType == null) {
                $$$reportNull$$$0(15);
            }
            return booleanType;
        }
        if (iElementType == KtNodeTypes.CHARACTER_CONSTANT) {
            SimpleType charType = kotlinBuiltIns.getCharType();
            if (charType == null) {
                $$$reportNull$$$0(16);
            }
            return charType;
        }
        if (iElementType == KtNodeTypes.NULL) {
            SimpleType nullableNothingType = kotlinBuiltIns.getNullableNothingType();
            if (nullableNothingType == null) {
                $$$reportNull$$$0(17);
            }
            return nullableNothingType;
        }
        throw new IllegalArgumentException("Unsupported constant type: " + iElementType);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS, ExpressionTypingContext expressionTypingContext) {
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(18);
        }
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT);
        KtExpression left = ktBinaryExpressionWithTypeRHS.getLeft();
        KtTypeReference right = ktBinaryExpressionWithTypeRHS.getRight();
        if (right == null) {
            return this.facade.getTypeInfo(left, expressionTypingContextReplaceContextDependency).clearType();
        }
        DefinitelyNotNullDeprecationKt.reportDeprecatedDefinitelyNotNullSyntax(ktBinaryExpressionWithTypeRHS, right, expressionTypingContext);
        IElementType referencedNameElementType = ktBinaryExpressionWithTypeRHS.getOperationReference().getReferencedNameElementType();
        PossiblyBareType possiblyBareTypeResolvePossiblyBareType = this.components.typeResolver.resolvePossiblyBareType(new TypeResolutionContext(expressionTypingContext.scope, expressionTypingContext.trace, true, BARE_TYPES_ALLOWED.contains(referencedNameElementType), expressionTypingContext.isDebuggerContext), right);
        KotlinTypeInfo typeInfo = this.facade.getTypeInfo(left, expressionTypingContextReplaceContextDependency);
        KotlinType type = typeInfo.getType();
        KotlinType kotlinTypeReconstructBareType = TypeReconstructionUtil.reconstructBareType(right, possiblyBareTypeResolvePossiblyBareType, type, expressionTypingContext.trace, this.components.builtIns);
        if (type != null) {
            checkBinaryWithTypeRHS(ktBinaryExpressionWithTypeRHS, expressionTypingContext, kotlinTypeReconstructBareType, type);
            DataFlowInfo dataFlowInfo = typeInfo.getDataFlowInfo();
            if (referencedNameElementType == KtTokens.AS_KEYWORD) {
                typeInfo = typeInfo.replaceDataFlowInfo(dataFlowInfo.establishSubtyping(this.components.dataFlowValueFactory.createDataFlowValue(left, type, expressionTypingContext), kotlinTypeReconstructBareType, this.components.languageVersionSettings));
            }
        }
        if (referencedNameElementType == KtTokens.AS_SAFE) {
            kotlinTypeReconstructBareType = TypeUtils.makeNullable(kotlinTypeReconstructBareType);
        }
        KotlinTypeInfo kotlinTypeInfoCheckType = this.components.dataFlowAnalyzer.checkType(typeInfo.replaceType(kotlinTypeReconstructBareType), ktBinaryExpressionWithTypeRHS, expressionTypingContext);
        RttiExpressionInformation rttiExpressionInformation = new RttiExpressionInformation(ktBinaryExpressionWithTypeRHS.getLeft(), type, kotlinTypeReconstructBareType, referencedNameElementType == KtTokens.AS_SAFE ? RttiOperation.SAFE_AS : RttiOperation.AS);
        Iterator<RttiExpressionChecker> it = this.components.rttiExpressionCheckers.iterator();
        while (it.hasNext()) {
            it.next().check(rttiExpressionInformation, ktBinaryExpressionWithTypeRHS, expressionTypingContext.trace);
        }
        return kotlinTypeInfoCheckType;
    }

    private void checkBinaryWithTypeRHS(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS, ExpressionTypingContext expressionTypingContext, KotlinType kotlinType, KotlinType kotlinType2) {
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(19);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(20);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(21);
        }
        if (kotlinType2 == null) {
            return;
        }
        KtSimpleNameExpression operationReference = ktBinaryExpressionWithTypeRHS.getOperationReference();
        IElementType referencedNameElementType = operationReference.getReferencedNameElementType();
        if (referencedNameElementType != KtTokens.AS_KEYWORD && referencedNameElementType != KtTokens.AS_SAFE) {
            expressionTypingContext.trace.report(Errors.UNSUPPORTED.on(operationReference, "binary operation with type RHS"));
            return;
        }
        checkForCastImpossibilityOrRedundancy(ktBinaryExpressionWithTypeRHS, kotlinType2, kotlinType, expressionTypingContext);
        if (expressionTypingContext.languageVersionSettings.supportsFeature(LanguageFeature.ProperCheckAnnotationsTargetInTypeUsePositions)) {
            this.components.annotationChecker.check(ktBinaryExpressionWithTypeRHS.getRight(), expressionTypingContext.trace, null);
        }
    }

    private void checkForCastImpossibilityOrRedundancy(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS, KotlinType kotlinType, KotlinType kotlinType2, ExpressionTypingContext expressionTypingContext) {
        if (kotlinType == null || TypeUtils.noExpectedType(kotlinType2) || KotlinTypeKt.isError(kotlinType2) || Boolean.TRUE.equals(expressionTypingContext.trace.get(BindingContext.CAST_TYPE_USED_AS_EXPECTED_TYPE, ktBinaryExpressionWithTypeRHS))) {
            return;
        }
        if (DynamicTypesKt.isDynamic(kotlinType2)) {
            expressionTypingContext.trace.report(Errors.DYNAMIC_NOT_ALLOWED.on(ktBinaryExpressionWithTypeRHS.getRight()));
        } else if (!TypeUtilsKt.isStubType(kotlinType) && !CastDiagnosticsUtil.isCastPossible(kotlinType, kotlinType2, this.components.platformToKotlinClassMapper, this.components.platformSpecificCastChecker)) {
            expressionTypingContext.trace.report(Errors.CAST_NEVER_SUCCEEDS.on(ktBinaryExpressionWithTypeRHS.getOperationReference()));
        } else if (CastDiagnosticsUtil.INSTANCE.castIsUseless(ktBinaryExpressionWithTypeRHS, expressionTypingContext, kotlinType2, kotlinType)) {
            expressionTypingContext.trace.report(Errors.USELESS_CAST.on(ktBinaryExpressionWithTypeRHS));
        } else if (CastDiagnosticsUtil.isCastErased(kotlinType, kotlinType2, KotlinTypeChecker.DEFAULT)) {
            expressionTypingContext.trace.report(Errors.UNCHECKED_CAST.on(ktBinaryExpressionWithTypeRHS, kotlinType, kotlinType2));
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitThisExpression(KtThisExpression ktThisExpression, ExpressionTypingContext expressionTypingContext) {
        KotlinType type;
        if (ktThisExpression == null) {
            $$$reportNull$$$0(22);
        }
        LabelResolver.LabeledReceiverResolutionResult labeledReceiverResolutionResultResolveToReceiver = resolveToReceiver(ktThisExpression, expressionTypingContext, false);
        int i = AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$types$expressions$LabelResolver$LabeledReceiverResolutionResult$Code[labeledReceiverResolutionResultResolveToReceiver.getCode().ordinal()];
        if (i == 2) {
            expressionTypingContext.trace.report(Errors.NO_THIS.on(ktThisExpression));
        } else {
            if (i == 3) {
                ReceiverParameterDescriptor receiverParameterDescriptor = labeledReceiverResolutionResultResolveToReceiver.getReceiverParameterDescriptor();
                expressionTypingContext.trace.record(BindingContext.THIS_REFERENCE_TARGET, ktThisExpression.getInstanceReference(), receiverParameterDescriptor);
                type = receiverParameterDescriptor.getType();
                expressionTypingContext.trace.recordType(ktThisExpression.getInstanceReference(), type);
            }
            return this.components.dataFlowAnalyzer.createCheckedTypeInfo(type, expressionTypingContext, ktThisExpression);
        }
        type = null;
        return this.components.dataFlowAnalyzer.createCheckedTypeInfo(type, expressionTypingContext, ktThisExpression);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.types.expressions.BasicExpressionTypingVisitor$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$types$expressions$LabelResolver$LabeledReceiverResolutionResult$Code;

        static {
            int[] iArr = new int[LabelResolver.LabeledReceiverResolutionResult.Code.values().length];
            $SwitchMap$org$jetbrains$kotlin$types$expressions$LabelResolver$LabeledReceiverResolutionResult$Code = iArr;
            try {
                iArr[LabelResolver.LabeledReceiverResolutionResult.Code.LABEL_RESOLUTION_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$types$expressions$LabelResolver$LabeledReceiverResolutionResult$Code[LabelResolver.LabeledReceiverResolutionResult.Code.NO_THIS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$types$expressions$LabelResolver$LabeledReceiverResolutionResult$Code[LabelResolver.LabeledReceiverResolutionResult.Code.SUCCESS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitSuperExpression(KtSuperExpression ktSuperExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktSuperExpression == null) {
            $$$reportNull$$$0(23);
        }
        LabelResolver.LabeledReceiverResolutionResult labeledReceiverResolutionResultResolveToReceiver = resolveToReceiver(ktSuperExpression, expressionTypingContext, true);
        if (!KtPsiUtil.isLHSOfDot(ktSuperExpression)) {
            expressionTypingContext.trace.report(Errors.SUPER_IS_NOT_AN_EXPRESSION.on(ktSuperExpression, ktSuperExpression.getText()));
            return errorInSuper(ktSuperExpression, expressionTypingContext);
        }
        int i = AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$types$expressions$LabelResolver$LabeledReceiverResolutionResult$Code[labeledReceiverResolutionResultResolveToReceiver.getCode().ordinal()];
        if (i == 1) {
            return errorInSuper(ktSuperExpression, expressionTypingContext);
        }
        if (i == 2) {
            expressionTypingContext.trace.report(Errors.SUPER_NOT_AVAILABLE.on(ktSuperExpression));
            return errorInSuper(ktSuperExpression, expressionTypingContext);
        }
        if (i == 3) {
            KotlinType kotlinTypeCheckPossiblyQualifiedSuper = checkPossiblyQualifiedSuper(ktSuperExpression, expressionTypingContext, labeledReceiverResolutionResultResolveToReceiver.getReceiverParameterDescriptor());
            if (kotlinTypeCheckPossiblyQualifiedSuper != null) {
                expressionTypingContext.trace.recordType(ktSuperExpression.getInstanceReference(), kotlinTypeCheckPossiblyQualifiedSuper);
            }
            return this.components.dataFlowAnalyzer.createCheckedTypeInfo(kotlinTypeCheckPossiblyQualifiedSuper, expressionTypingContext, ktSuperExpression);
        }
        throw new IllegalStateException("Unknown code: " + labeledReceiverResolutionResultResolveToReceiver.getCode());
    }

    private KotlinTypeInfo errorInSuper(KtSuperExpression ktSuperExpression, ExpressionTypingContext expressionTypingContext) {
        KtTypeReference superTypeQualifier = ktSuperExpression.getSuperTypeQualifier();
        if (superTypeQualifier != null) {
            this.components.typeResolver.resolveType(expressionTypingContext.scope, superTypeQualifier, expressionTypingContext.trace, true);
        }
        return TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
    }

    /* JADX WARN: Code duplicated, block: B:26:0x00a4 A[EDGE_INSN: B:26:0x00a4->B:27:0x00a5 BREAK  A[LOOP:0: B:21:0x0083->B:78:?]] */
    private KotlinType checkPossiblyQualifiedSuper(KtSuperExpression ktSuperExpression, ExpressionTypingContext expressionTypingContext, ReceiverParameterDescriptor receiverParameterDescriptor) {
        KotlinType next;
        KotlinType kotlinTypeSubstitute;
        KotlinType kotlinTypeResolveType;
        ClassifierDescriptor classDescriptor;
        KtTypeArgumentList typeArgumentList;
        KotlinType type = receiverParameterDescriptor.getType();
        Collection<KotlinType> collectionMo7438getSupertypes = type.getConstructor().mo7438getSupertypes();
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(type);
        KtTypeReference superTypeQualifier = ktSuperExpression.getSuperTypeQualifier();
        if (superTypeQualifier != null) {
            KtTypeElement typeElement = superTypeQualifier.getTypeElement();
            if (typeElement instanceof KtUserType) {
                KtUserType ktUserType = (KtUserType) typeElement;
                if (ktUserType.getTypeArguments().isEmpty()) {
                    classDescriptor = this.components.typeResolver.resolveClass(expressionTypingContext.scope, ktUserType, expressionTypingContext.trace, expressionTypingContext.isDebuggerContext);
                    typeArgumentList = null;
                    kotlinTypeResolveType = null;
                } else {
                    kotlinTypeResolveType = this.components.typeResolver.resolveType(expressionTypingContext.scope, superTypeQualifier, expressionTypingContext.trace, true);
                    typeArgumentList = ktUserType.getTypeArgumentList();
                    classDescriptor = null;
                }
            } else {
                kotlinTypeResolveType = this.components.typeResolver.resolveType(expressionTypingContext.scope, superTypeQualifier, expressionTypingContext.trace, true);
                classDescriptor = null;
                typeArgumentList = null;
            }
            if (classDescriptor instanceof TypeAliasDescriptor) {
                classDescriptor = ((TypeAliasDescriptor) classDescriptor).getClassDescriptor();
            }
            if (kotlinTypeResolveType != null) {
                if (!collectionMo7438getSupertypes.contains(kotlinTypeResolveType)) {
                    kotlinTypeSubstitute = null;
                    break;
                }
                kotlinTypeSubstitute = kotlinTypeResolveType;
            } else {
                if (!(classDescriptor instanceof ClassDescriptor)) {
                    kotlinTypeSubstitute = null;
                    break;
                }
                ClassDescriptor classDescriptor2 = (ClassDescriptor) classDescriptor;
                Iterator<KotlinType> it = collectionMo7438getSupertypes.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        kotlinTypeSubstitute = null;
                        break;
                    }
                    KotlinType next2 = it.next();
                    if (next2.getConstructor().equals(classDescriptor2.getTypeConstructor())) {
                        kotlinTypeSubstitute = typeSubstitutorCreate.safeSubstitute(next2, Variance.INVARIANT);
                        break;
                    }
                }
            }
            boolean z = (classDescriptor == null || ErrorUtils.isError(classDescriptor)) ? false : true;
            boolean z2 = (kotlinTypeResolveType == null || KotlinTypeKt.isError(kotlinTypeResolveType)) ? false : true;
            if (kotlinTypeSubstitute == null && (z || z2)) {
                expressionTypingContext.trace.report(Errors.NOT_A_SUPERTYPE.on(superTypeQualifier));
            } else if (typeArgumentList != null) {
                expressionTypingContext.trace.report(Errors.TYPE_ARGUMENTS_REDUNDANT_IN_SUPER_QUALIFIER.on(typeArgumentList));
            }
            if (!this.components.languageVersionSettings.supportsFeature(LanguageFeature.QualifiedSupertypeMayBeExtendedByOtherSupertype) && kotlinTypeSubstitute != null && (z || z2)) {
                checkResolvedExplicitlyQualifiedSupertype(expressionTypingContext.trace, kotlinTypeSubstitute, collectionMo7438getSupertypes, superTypeQualifier);
            }
        } else if (UnqualifiedSuperKt.isPossiblyAmbiguousUnqualifiedSuper(ktSuperExpression, collectionMo7438getSupertypes)) {
            Pair<Collection<KotlinType>, Boolean> pairResolveUnqualifiedSuperFromExpressionContext = UnqualifiedSuperKt.resolveUnqualifiedSuperFromExpressionContext(ktSuperExpression, collectionMo7438getSupertypes, this.components.builtIns.getAnyType());
            Collection<KotlinType> first = pairResolveUnqualifiedSuperFromExpressionContext.getFirst();
            if (pairResolveUnqualifiedSuperFromExpressionContext.getSecond().booleanValue()) {
                expressionTypingContext.trace.record(BindingContext.SUPER_EXPRESSION_FROM_ANY_MIGRATION, ktSuperExpression, true);
            }
            if (first.size() == 1) {
                kotlinTypeSubstitute = typeSubstitutorCreate.substitute(first.iterator().next(), Variance.INVARIANT);
            } else if (first.isEmpty()) {
                kotlinTypeSubstitute = this.components.builtIns.getAnyType();
            } else {
                expressionTypingContext.trace.report(Errors.AMBIGUOUS_SUPER.on(ktSuperExpression));
                kotlinTypeSubstitute = null;
            }
        } else {
            if (collectionMo7438getSupertypes.isEmpty()) {
                next = this.components.builtIns.getAnyType();
            } else {
                next = collectionMo7438getSupertypes.iterator().next();
            }
            kotlinTypeSubstitute = typeSubstitutorCreate.substitute(next, Variance.INVARIANT);
        }
        if (kotlinTypeSubstitute != null) {
            if (DescriptorUtils.isInterface(type.getConstructor().getThis$0()) && DescriptorUtils.isClass(kotlinTypeSubstitute.getConstructor().getThis$0())) {
                expressionTypingContext.trace.report(Errors.SUPERCLASS_NOT_ACCESSIBLE_FROM_INTERFACE.on(ktSuperExpression));
            }
            expressionTypingContext.trace.recordType(ktSuperExpression.getInstanceReference(), kotlinTypeSubstitute);
            expressionTypingContext.trace.record(BindingContext.REFERENCE_TARGET, ktSuperExpression.getInstanceReference(), kotlinTypeSubstitute.getConstructor().getThis$0());
            expressionTypingContext.trace.record(BindingContext.THIS_TYPE_FOR_SUPER_EXPRESSION, ktSuperExpression, type);
        }
        BindingContextUtilsKt.recordScope(expressionTypingContext.trace, expressionTypingContext.scope, superTypeQualifier);
        return kotlinTypeSubstitute;
    }

    private static void checkResolvedExplicitlyQualifiedSupertype(BindingTrace bindingTrace, KotlinType kotlinType, Collection<KotlinType> collection, KtTypeReference ktTypeReference) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(24);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(25);
        }
        if (collection == null) {
            $$$reportNull$$$0(26);
        }
        if (ktTypeReference == null) {
            $$$reportNull$$$0(27);
        }
        if (collection.size() > 1) {
            ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
            for (KotlinType kotlinType2 : collection) {
                if (kotlinType2.getConstructor().getThis$0() != this$0 && KotlinTypeChecker.DEFAULT.isSubtypeOf(kotlinType2, kotlinType)) {
                    bindingTrace.report(Errors.QUALIFIED_SUPERTYPE_EXTENDED_BY_OTHER_SUPERTYPE.on(ktTypeReference, kotlinType2));
                    return;
                }
            }
        }
    }

    private LabelResolver.LabeledReceiverResolutionResult resolveToReceiver(KtInstanceExpressionWithLabel ktInstanceExpressionWithLabel, ExpressionTypingContext expressionTypingContext, boolean z) {
        Name labelNameAsName = ktInstanceExpressionWithLabel.getLabelNameAsName();
        ReceiverParameterDescriptor receiverParameterDescriptor = null;
        if (labelNameAsName != null) {
            LabelResolver.LabeledReceiverResolutionResult labeledReceiverResolutionResultResolveThisOrSuperLabel = LabelResolver.INSTANCE.resolveThisOrSuperLabel(ktInstanceExpressionWithLabel, expressionTypingContext, labelNameAsName);
            if (labeledReceiverResolutionResultResolveThisOrSuperLabel.success()) {
                ReceiverParameterDescriptor receiverParameterDescriptor2 = labeledReceiverResolutionResultResolveThisOrSuperLabel.getReceiverParameterDescriptor();
                recordThisOrSuperCallInTraceAndCallExtension(expressionTypingContext, receiverParameterDescriptor2, ktInstanceExpressionWithLabel);
                if (z && !isDeclaredInClass(receiverParameterDescriptor2)) {
                    LabelResolver.LabeledReceiverResolutionResult labeledReceiverResolutionResultLabelResolutionSuccess = LabelResolver.LabeledReceiverResolutionResult.INSTANCE.labelResolutionSuccess(null);
                    if (labeledReceiverResolutionResultLabelResolutionSuccess == null) {
                        $$$reportNull$$$0(28);
                    }
                    return labeledReceiverResolutionResultLabelResolutionSuccess;
                }
            }
            if (labeledReceiverResolutionResultResolveThisOrSuperLabel == null) {
                $$$reportNull$$$0(29);
            }
            return labeledReceiverResolutionResultResolveThisOrSuperLabel;
        }
        List<ReceiverParameterDescriptor> implicitReceiversHierarchy = ScopeUtilsKt.getImplicitReceiversHierarchy(expressionTypingContext.scope);
        if (z) {
            for (ReceiverParameterDescriptor receiverParameterDescriptor3 : implicitReceiversHierarchy) {
                if (isDeclaredInClass(receiverParameterDescriptor3)) {
                    receiverParameterDescriptor = receiverParameterDescriptor3;
                    break;
                }
            }
        } else if (!implicitReceiversHierarchy.isEmpty()) {
            for (ReceiverParameterDescriptor receiverParameterDescriptor4 : implicitReceiversHierarchy) {
                if (!(receiverParameterDescriptor4.getValue() instanceof ContextReceiver)) {
                    receiverParameterDescriptor = receiverParameterDescriptor4;
                    break;
                }
            }
        }
        if (receiverParameterDescriptor != null) {
            expressionTypingContext.trace.record(BindingContext.REFERENCE_TARGET, ktInstanceExpressionWithLabel.getInstanceReference(), receiverParameterDescriptor.getContainingModule());
            recordThisOrSuperCallInTraceAndCallExtension(expressionTypingContext, receiverParameterDescriptor, ktInstanceExpressionWithLabel);
        }
        LabelResolver.LabeledReceiverResolutionResult labeledReceiverResolutionResultLabelResolutionSuccess2 = LabelResolver.LabeledReceiverResolutionResult.INSTANCE.labelResolutionSuccess(receiverParameterDescriptor);
        if (labeledReceiverResolutionResultLabelResolutionSuccess2 == null) {
            $$$reportNull$$$0(30);
        }
        return labeledReceiverResolutionResultLabelResolutionSuccess2;
    }

    private void recordThisOrSuperCallInTraceAndCallExtension(ExpressionTypingContext expressionTypingContext, ReceiverParameterDescriptor receiverParameterDescriptor, KtExpression ktExpression) {
        BindingTrace bindingTrace = expressionTypingContext.trace;
        Call callMakeCall = CallMaker.makeCall(ktExpression, null, null, ktExpression, Collections.emptyList());
        ResolvedCallImpl resolvedCallImplCreate = ResolvedCallImpl.create(OldResolutionCandidate.create(callMakeCall, receiverParameterDescriptor, null, ExplicitReceiverKind.NO_EXPLICIT_RECEIVER, null), TemporaryBindingTrace.create(bindingTrace, "Fake trace for fake 'this' or 'super' resolved call"), TracingStrategy.EMPTY, new DataFlowInfoForArgumentsImpl(expressionTypingContext.dataFlowInfo, callMakeCall));
        resolvedCallImplCreate.markCallAsCompleted();
        bindingTrace.record(BindingContext.RESOLVED_CALL, callMakeCall, resolvedCallImplCreate);
        bindingTrace.record(BindingContext.CALL, ktExpression, callMakeCall);
        if (expressionTypingContext.trace.wantsDiagnostics()) {
            CallCheckerContext callCheckerContextCreateCallCheckerContext = createCallCheckerContext(expressionTypingContext);
            Iterator<CallChecker> it = this.components.callCheckers.iterator();
            while (it.hasNext()) {
                it.next().check(resolvedCallImplCreate, ktExpression, callCheckerContextCreateCallCheckerContext);
            }
        }
    }

    private static boolean isDeclaredInClass(ReceiverParameterDescriptor receiverParameterDescriptor) {
        return receiverParameterDescriptor.getContainingModule() instanceof ClassDescriptor;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitBlockExpression(KtBlockExpression ktBlockExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(31);
        }
        return this.components.expressionTypingServices.getBlockReturnedType(ktBlockExpression, expressionTypingContext, false);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitClassLiteralExpression(KtClassLiteralExpression ktClassLiteralExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktClassLiteralExpression == null) {
            $$$reportNull$$$0(32);
        }
        return this.components.doubleColonExpressionResolver.visitClassLiteralExpression(ktClassLiteralExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitCallableReferenceExpression(KtCallableReferenceExpression ktCallableReferenceExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktCallableReferenceExpression == null) {
            $$$reportNull$$$0(33);
        }
        return this.components.doubleColonExpressionResolver.visitCallableReferenceExpression(ktCallableReferenceExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitObjectLiteralExpression(final KtObjectLiteralExpression ktObjectLiteralExpression, final ExpressionTypingContext expressionTypingContext) {
        KotlinTypeInfo kotlinTypeInfo;
        if (ktObjectLiteralExpression == null) {
            $$$reportNull$$$0(34);
        }
        final KotlinType[] kotlinTypeArr = new KotlinType[1];
        TemporaryBindingTrace temporaryBindingTraceCreate = TemporaryBindingTrace.create(expressionTypingContext.trace, "trace to resolve object literal expression", ktObjectLiteralExpression);
        ObservableBindingTrace observableBindingTrace = new ObservableBindingTrace(temporaryBindingTraceCreate);
        observableBindingTrace.addHandler(BindingContext.CLASS, new ObservableBindingTrace.RecordHandler() { // from class: org.jetbrains.kotlin.types.expressions.BasicExpressionTypingVisitor$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.resolve.ObservableBindingTrace.RecordHandler
            public final void handleRecord(WritableSlice writableSlice, Object obj, Object obj2) {
                this.f$0.m7480x7dbee845(ktObjectLiteralExpression, kotlinTypeArr, expressionTypingContext, writableSlice, (PsiElement) obj, (ClassDescriptor) obj2);
            }
        });
        this.components.localClassifierAnalyzer.processClassOrObject(null, ((ExpressionTypingContext) expressionTypingContext.replaceBindingTrace(observableBindingTrace)).replaceContextDependency(ContextDependency.INDEPENDENT), expressionTypingContext.scope.getOwnerDescriptor(), ktObjectLiteralExpression.getObjectDeclaration());
        temporaryBindingTraceCreate.commit();
        DataFlowInfo dataFlowInfoAnd = expressionTypingContext.dataFlowInfo;
        for (KtSuperTypeListEntry ktSuperTypeListEntry : ktObjectLiteralExpression.getObjectDeclaration().getSuperTypeListEntries()) {
            if ((ktSuperTypeListEntry instanceof KtSuperTypeCallEntry) && (kotlinTypeInfo = (KotlinTypeInfo) expressionTypingContext.trace.get(BindingContext.EXPRESSION_TYPE_INFO, ((KtSuperTypeCallEntry) ktSuperTypeListEntry).getCalleeExpression())) != null) {
                dataFlowInfoAnd = dataFlowInfoAnd.and(kotlinTypeInfo.getDataFlowInfo());
            }
        }
        KotlinTypeInfo kotlinTypeInfoCheckType = this.components.dataFlowAnalyzer.checkType(TypeInfoFactoryKt.createTypeInfo(kotlinTypeArr[0], dataFlowInfoAnd), ktObjectLiteralExpression, expressionTypingContext);
        expressionTypingContext.trace.record(BindingContext.EXPRESSION_TYPE_INFO, ktObjectLiteralExpression, kotlinTypeInfoCheckType);
        expressionTypingContext.trace.record(BindingContext.PROCESSED, ktObjectLiteralExpression);
        return kotlinTypeInfoCheckType;
    }

    /* JADX INFO: renamed from: lambda$visitObjectLiteralExpression$0$org-jetbrains-kotlin-types-expressions-BasicExpressionTypingVisitor, reason: not valid java name */
    /* synthetic */ void m7480x7dbee845(KtObjectLiteralExpression ktObjectLiteralExpression, KotlinType[] kotlinTypeArr, ExpressionTypingContext expressionTypingContext, WritableSlice writableSlice, PsiElement psiElement, final ClassDescriptor classDescriptor) {
        if (writableSlice == BindingContext.CLASS && psiElement == ktObjectLiteralExpression.getObjectDeclaration()) {
            WrappedTypeFactory wrappedTypeFactory = this.components.wrappedTypeFactory;
            BindingTrace bindingTrace = expressionTypingContext.trace;
            classDescriptor.getClass();
            kotlinTypeArr[0] = wrappedTypeFactory.createRecursionIntolerantDeferredType(bindingTrace, new Function0() { // from class: org.jetbrains.kotlin.types.expressions.BasicExpressionTypingVisitor$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return classDescriptor.getDefaultType();
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitQualifiedExpression(KtQualifiedExpression ktQualifiedExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktQualifiedExpression == null) {
            $$$reportNull$$$0(35);
        }
        return this.components.callExpressionResolver.getQualifiedExpressionTypeInfo(ktQualifiedExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitCallExpression(KtCallExpression ktCallExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(36);
        }
        return this.components.callExpressionResolver.getCallExpressionTypeInfo(ktCallExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitUnaryExpression(KtUnaryExpression ktUnaryExpression, ExpressionTypingContext expressionTypingContext) {
        ExpressionTypingContext expressionTypingContext2;
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(37);
        }
        ExpressionTypingContext expressionTypingContextReplaceExpectedType = ExpressionTypingUtils.isUnaryExpressionDependentOnExpectedType(ktUnaryExpression) ? expressionTypingContext : expressionTypingContext.replaceContextDependency(ContextDependency.INDEPENDENT).replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE);
        KtExpression baseExpression = ktUnaryExpression.getBaseExpression();
        if (baseExpression == null) {
            return TypeInfoFactoryKt.noTypeInfo(expressionTypingContextReplaceExpectedType);
        }
        KtSimpleNameExpression operationReference = ktUnaryExpression.getOperationReference();
        IElementType referencedNameElementType = operationReference.getReferencedNameElementType();
        if (referencedNameElementType == KtTokens.EXCLEXCL) {
            return visitExclExclExpression(ktUnaryExpression, expressionTypingContextReplaceExpectedType);
        }
        KotlinTypeInfo kotlinTypeInfoSafeGetTypeInfo = this.facade.safeGetTypeInfo(baseExpression, expressionTypingContextReplaceExpectedType);
        KotlinType kotlinTypeSafeGetType = ExpressionTypingUtils.safeGetType(kotlinTypeInfoSafeGetTypeInfo);
        ExpressionReceiver expressionReceiverCreate = ExpressionReceiver.INSTANCE.create(baseExpression, kotlinTypeSafeGetType, expressionTypingContextReplaceExpectedType.trace.getContext());
        Call callMakeCall = CallMaker.makeCall(expressionReceiverCreate, ktUnaryExpression);
        Name name2 = OperatorConventions.UNARY_OPERATION_NAMES.get(referencedNameElementType);
        if (name2 == null) {
            expressionTypingContextReplaceExpectedType.trace.report(Errors.UNSUPPORTED.on(operationReference, "visitUnaryExpression"));
            return kotlinTypeInfoSafeGetTypeInfo.clearType();
        }
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(baseExpression);
        if ((referencedNameElementType == KtTokens.PLUSPLUS || referencedNameElementType == KtTokens.MINUSMINUS) && (ktExpressionDeparenthesize instanceof KtArrayAccessExpression)) {
            resolveImplicitArrayAccessSetMethod((KtArrayAccessExpression) ktExpressionDeparenthesize, ExpressionTypingUtils.createFakeExpressionOfType(baseExpression.getProject(), expressionTypingContextReplaceExpectedType.trace, "e", kotlinTypeSafeGetType), expressionTypingContextReplaceExpectedType.replaceBindingTrace(TemporaryBindingTrace.create(expressionTypingContextReplaceExpectedType.trace, "trace to resolve array access set method for unary expression", ktUnaryExpression)), expressionTypingContextReplaceExpectedType.trace);
        }
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveCallWithGivenName = this.components.callResolver.resolveCallWithGivenName(expressionTypingContextReplaceExpectedType, callMakeCall, ktUnaryExpression.getOperationReference(), name2);
        if (!overloadResolutionResultsResolveCallWithGivenName.isSuccess()) {
            return kotlinTypeInfoSafeGetTypeInfo.clearType();
        }
        KotlinType returnType = ((FunctionDescriptor) overloadResolutionResultsResolveCallWithGivenName.mo7437getResultingDescriptor()).getReturnType();
        if (referencedNameElementType != KtTokens.PLUSPLUS && referencedNameElementType != KtTokens.MINUSMINUS) {
            expressionTypingContext2 = expressionTypingContext;
        } else if (KotlinBuiltIns.isUnit(returnType)) {
            returnType = ErrorUtils.createErrorType(ErrorTypeKind.UNIT_RETURN_TYPE_FOR_INC_DEC, new String[0]);
            expressionTypingContextReplaceExpectedType.trace.report(Errors.INC_DEC_SHOULD_NOT_RETURN_UNIT.on(operationReference));
            expressionTypingContext2 = expressionTypingContext;
        } else {
            KotlinType type = expressionReceiverCreate.getType();
            if (!KotlinTypeChecker.DEFAULT.isSubtypeOf(returnType, type)) {
                expressionTypingContextReplaceExpectedType.trace.report(Errors.RESULT_TYPE_MISMATCH.on(operationReference, name2.asString(), type, returnType));
            } else {
                expressionTypingContextReplaceExpectedType.trace.record(BindingContext.VARIABLE_REASSIGNMENT, ktUnaryExpression);
                checkLValue(expressionTypingContextReplaceExpectedType.trace, expressionTypingContextReplaceExpectedType, baseExpression, ExpressionTypingUtils.createFakeExpressionOfType(baseExpression.getProject(), expressionTypingContextReplaceExpectedType.trace, "e", kotlinTypeSafeGetType), ktUnaryExpression, false);
            }
            expressionTypingContext2 = expressionTypingContext;
            DataFlowValue dataFlowValueCreateDataFlowValue = this.components.dataFlowValueFactory.createDataFlowValue((ReceiverValue) callMakeCall.getExplicitReceiver(), expressionTypingContext2);
            if (!(ktUnaryExpression instanceof KtPrefixExpression)) {
                kotlinTypeInfoSafeGetTypeInfo = kotlinTypeInfoSafeGetTypeInfo.replaceDataFlowInfo(kotlinTypeInfoSafeGetTypeInfo.getDataFlowInfo().assign(this.components.dataFlowValueFactory.createDataFlowValue(ktUnaryExpression, type, expressionTypingContext2), dataFlowValueCreateDataFlowValue, this.components.languageVersionSettings));
                returnType = type;
            }
        }
        CompileTimeConstant<?> compileTimeConstantEvaluateExpression = this.components.constantExpressionEvaluator.evaluateExpression(ktUnaryExpression, expressionTypingContext2.trace, expressionTypingContext2.expectedType);
        if (compileTimeConstantEvaluateExpression != null) {
            return this.components.dataFlowAnalyzer.createCompileTimeConstantTypeInfo(compileTimeConstantEvaluateExpression, ktUnaryExpression, expressionTypingContext2);
        }
        return this.components.dataFlowAnalyzer.checkType(kotlinTypeInfoSafeGetTypeInfo.replaceType(returnType), ktUnaryExpression, expressionTypingContext2.replaceDataFlowInfo(kotlinTypeInfoSafeGetTypeInfo.getDataFlowInfo()));
    }

    private KotlinTypeInfo visitExclExclExpression(KtUnaryExpression ktUnaryExpression, ExpressionTypingContext expressionTypingContext) {
        KotlinType kotlinTypeMakeNotNullable;
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(38);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(39);
        }
        KtExpression baseExpression = ktUnaryExpression.getBaseExpression();
        KtSimpleNameExpression operationReference = ktUnaryExpression.getOperationReference();
        ResolvedCall<FunctionDescriptor> resolvedCallResolveSpecialConstructionAsCall = this.components.controlStructureTypingUtils.resolveSpecialConstructionAsCall(ControlStructureTypingUtils.createCallForSpecialConstruction(ktUnaryExpression, ktUnaryExpression.getOperationReference(), Collections.singletonList(baseExpression)), ControlStructureTypingUtils.ResolveConstruct.EXCL_EXCL, Collections.singletonList("baseExpr"), Collections.singletonList(true), expressionTypingContext, null);
        KotlinTypeInfo recordedTypeInfo = BindingContextUtils.getRecordedTypeInfo(baseExpression, expressionTypingContext.trace.getContext());
        boolean zIsFunctionLiteralArgument = ArgumentTypeResolver.isFunctionLiteralArgument(baseExpression, expressionTypingContext);
        boolean zIsCallableReferenceArgument = ArgumentTypeResolver.isCallableReferenceArgument(baseExpression, expressionTypingContext);
        if (zIsFunctionLiteralArgument || zIsCallableReferenceArgument) {
            expressionTypingContext.trace.report((zIsFunctionLiteralArgument ? Errors.NOT_NULL_ASSERTION_ON_LAMBDA_EXPRESSION : Errors.NOT_NULL_ASSERTION_ON_CALLABLE_REFERENCE).on(operationReference));
            return recordedTypeInfo != null ? recordedTypeInfo : this.components.expressionTypingServices.getTypeInfo(baseExpression, expressionTypingContext);
        }
        if (recordedTypeInfo == null) {
            return TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
        }
        KotlinType type = recordedTypeInfo.getType();
        if (type == null) {
            return recordedTypeInfo;
        }
        DataFlowInfo dataFlowInfo = recordedTypeInfo.getDataFlowInfo();
        if (isKnownToBeNotNull(baseExpression, type, expressionTypingContext)) {
            expressionTypingContext.trace.report(Errors.UNNECESSARY_NOT_NULL_ASSERTION.on(operationReference, TypeUtils.makeNotNullable(type)));
        } else {
            recordedTypeInfo = recordedTypeInfo.replaceDataFlowInfo(dataFlowInfo.disequate(this.components.dataFlowValueFactory.createDataFlowValue(baseExpression, type, expressionTypingContext), DataFlowValue.nullValue(this.components.builtIns), this.components.languageVersionSettings));
        }
        if (this.components.languageVersionSettings.supportsFeature(LanguageFeature.NewInference)) {
            kotlinTypeMakeNotNullable = ((FunctionDescriptor) resolvedCallResolveSpecialConstructionAsCall.getResultingDescriptor()).getReturnType();
        } else {
            kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(type);
        }
        if (expressionTypingContext.contextDependency == ContextDependency.DEPENDENT) {
            return recordedTypeInfo.replaceType(kotlinTypeMakeNotNullable);
        }
        return this.components.dataFlowAnalyzer.checkType(recordedTypeInfo.replaceType(kotlinTypeMakeNotNullable), ktUnaryExpression, expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE));
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitLabeledExpression(KtLabeledExpression ktLabeledExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktLabeledExpression == null) {
            $$$reportNull$$$0(40);
        }
        return visitLabeledExpression(ktLabeledExpression, expressionTypingContext, false);
    }

    public KotlinTypeInfo visitLabeledExpression(KtLabeledExpression ktLabeledExpression, ExpressionTypingContext expressionTypingContext, boolean z) {
        if (ktLabeledExpression == null) {
            $$$reportNull$$$0(41);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(42);
        }
        KtSimpleNameExpression targetLabel = ktLabeledExpression.getTargetLabel();
        if (!this.components.languageVersionSettings.supportsFeature(LanguageFeature.YieldIsNoMoreReserved)) {
            ReservedCheckingKt.checkReservedYield(targetLabel, expressionTypingContext.trace);
        }
        if (targetLabel != null) {
            UnderscoreChecker.INSTANCE.checkIdentifier(targetLabel.getIdentifier(), expressionTypingContext.trace, this.components.languageVersionSettings);
        }
        KtExpression baseExpression = ktLabeledExpression.getBaseExpression();
        if (baseExpression == null) {
            KotlinTypeInfo kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
            if (kotlinTypeInfoNoTypeInfo == null) {
                $$$reportNull$$$0(43);
            }
            return kotlinTypeInfoNoTypeInfo;
        }
        KotlinTypeInfo typeInfo = this.facade.getTypeInfo(baseExpression, expressionTypingContext, z);
        if (typeInfo == null) {
            $$$reportNull$$$0(44);
        }
        return typeInfo;
    }

    private boolean isKnownToBeNotNull(KtExpression ktExpression, KotlinType kotlinType, ExpressionTypingContext expressionTypingContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(45);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(46);
        }
        if (kotlinType == null) {
            return false;
        }
        if (KotlinTypeKt.isError(kotlinType) && !ErrorUtils.isUninferredTypeVariable(kotlinType)) {
            return false;
        }
        if (TypeUtils.isNullableType(kotlinType)) {
            return expressionTypingContext.dataFlowInfo.getStableNullability(this.components.dataFlowValueFactory.createDataFlowValue(ktExpression, kotlinType, expressionTypingContext)) == Nullability.NOT_NULL;
        }
        return true;
    }

    public boolean checkLValue(BindingTrace bindingTrace, ExpressionTypingContext expressionTypingContext, KtExpression ktExpression, KtExpression ktExpression2, KtOperationExpression ktOperationExpression, boolean z) {
        KtExpression selectorExpression;
        boolean z2;
        BindingTrace bindingTraceCreate;
        ResolvedCall<?> resolvedCall;
        if (bindingTrace == null) {
            $$$reportNull$$$0(47);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(48);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(49);
        }
        if (ktOperationExpression == null) {
            $$$reportNull$$$0(50);
        }
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(ktExpression);
        boolean z3 = true;
        if (ktExpressionDeparenthesize instanceof KtArrayAccessExpression) {
            KtArrayAccessExpression ktArrayAccessExpression = (KtArrayAccessExpression) ktExpressionDeparenthesize;
            if (ktArrayAccessExpression.getArrayExpression() == null || ktExpression2 == null) {
                return false;
            }
            if (z) {
                z2 = true;
                bindingTraceCreate = bindingTrace;
            } else {
                bindingTraceCreate = TemporaryBindingTrace.create(bindingTrace, "Trace for checking set function");
                z2 = resolveArrayAccessSetMethod(ktArrayAccessExpression, ktExpression2, expressionTypingContext.replaceBindingTrace(bindingTraceCreate), bindingTraceCreate).getType() != null;
            }
            IElementType referencedNameElementType = ktOperationExpression.getOperationReference().getReferencedNameElementType();
            if ((KtTokens.AUGMENTED_ASSIGNMENTS.contains(referencedNameElementType) || referencedNameElementType == KtTokens.PLUSPLUS || referencedNameElementType == KtTokens.MINUSMINUS) && (resolvedCall = (ResolvedCall) bindingTraceCreate.get(BindingContext.INDEXED_LVALUE_SET, ktExpressionDeparenthesize)) != null && bindingTrace.wantsDiagnostics()) {
                CallCheckerContext callCheckerContext = new CallCheckerContext(expressionTypingContext, this.components.deprecationResolver, this.components.moduleDescriptor, this.components.missingSupertypesResolver, this.components.callComponents, bindingTrace);
                Iterator<CallChecker> it = this.components.callCheckers.iterator();
                while (it.hasNext()) {
                    it.next().check(resolvedCall, ktExpressionDeparenthesize, callCheckerContext);
                }
                if (bindingTrace.get(BindingContext.INDEXED_LVALUE_SET, ktExpressionDeparenthesize) == null) {
                    bindingTrace.record(BindingContext.INDEXED_LVALUE_SET, ktExpressionDeparenthesize, resolvedCall);
                }
            }
            return z2;
        }
        VariableDescriptor variableDescriptorExtractVariableDescriptorFromReference = BindingContextUtils.extractVariableDescriptorFromReference(bindingTrace.getContext(), ktExpressionDeparenthesize);
        if (ktExpressionDeparenthesize == null) {
            ktExpressionDeparenthesize = ktExpression;
        }
        if ((ktExpressionDeparenthesize instanceof KtQualifiedExpression) && (selectorExpression = ((KtQualifiedExpression) ktExpressionDeparenthesize).getSelectorExpression()) != null) {
            ktExpressionDeparenthesize = selectorExpression;
        }
        if (variableDescriptorExtractVariableDescriptorFromReference instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) variableDescriptorExtractVariableDescriptorFromReference;
            PropertySetterDescriptor setter = propertyDescriptor.getSetter();
            if (propertyDescriptor.isSetterProjectedOut()) {
                bindingTrace.report(Errors.SETTER_PROJECTED_OUT.on(ktExpressionDeparenthesize, propertyDescriptor));
                z3 = false;
            } else if (setter != null) {
                checkPropertySetterCall(expressionTypingContext.replaceBindingTrace(bindingTrace), setter, CallUtilKt.getResolvedCall(ktExpression, expressionTypingContext.trace.getContext()), ktExpressionDeparenthesize);
            }
        }
        if (variableDescriptorExtractVariableDescriptorFromReference == null) {
            bindingTrace.report(Errors.VARIABLE_EXPECTED.on(ktExpressionDeparenthesize));
            return false;
        }
        if (variableDescriptorExtractVariableDescriptorFromReference.isVar()) {
            return z3;
        }
        return false;
    }

    private void checkPropertySetterCall(ExpressionTypingContext expressionTypingContext, PropertySetterDescriptor propertySetterDescriptor, ResolvedCall<?> resolvedCall, KtExpression ktExpression) {
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(51);
        }
        if (propertySetterDescriptor == null) {
            $$$reportNull$$$0(52);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(53);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(54);
        }
        Call call = resolvedCall.getCall();
        ResolvedCallImpl resolvedCallImplCreate = ResolvedCallImpl.create(OldResolutionCandidate.create(call, propertySetterDescriptor, resolvedCall.getDispatchReceiver(), resolvedCall.getExplicitReceiverKind(), null), TemporaryBindingTrace.create(expressionTypingContext.trace, "Trace for fake property setter resolved call"), TracingStrategy.EMPTY, new DataFlowInfoForArgumentsImpl(resolvedCall.getDataFlowInfoForArguments().getResultInfo(), call));
        resolvedCallImplCreate.markCallAsCompleted();
        if (expressionTypingContext.trace.wantsDiagnostics()) {
            CallCheckerContext callCheckerContextCreateCallCheckerContext = createCallCheckerContext(expressionTypingContext);
            Iterator<CallChecker> it = this.components.callCheckers.iterator();
            while (it.hasNext()) {
                it.next().check(resolvedCallImplCreate, ktExpression, callCheckerContextCreateCallCheckerContext);
            }
        }
    }

    private CallCheckerContext createCallCheckerContext(ExpressionTypingContext expressionTypingContext) {
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(55);
        }
        return new CallCheckerContext(expressionTypingContext, this.components.deprecationResolver, this.components.moduleDescriptor, this.components.missingSupertypesResolver, this.components.callComponents);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitBinaryExpression(KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext) {
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo;
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(56);
        }
        ExpressionTypingContext expressionTypingContextReplaceExpectedType = ExpressionTypingUtils.isBinaryExpressionDependentOnExpectedType(ktBinaryExpression) ? expressionTypingContext : expressionTypingContext.replaceContextDependency(ContextDependency.INDEPENDENT).replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE);
        KtOperationReferenceExpression operationReference = ktBinaryExpression.getOperationReference();
        KtExpression left = ktBinaryExpression.getLeft();
        KtExpression right = ktBinaryExpression.getRight();
        IElementType referencedNameElementType = operationReference.getReferencedNameElementType();
        if (referencedNameElementType == KtTokens.IDENTIFIER) {
            kotlinTypeInfoNoTypeInfo = getTypeInfoForBinaryCall(operationReference.getReferencedNameAsName(), expressionTypingContextReplaceExpectedType, ktBinaryExpression);
        } else if (OperatorConventions.BINARY_OPERATION_NAMES.containsKey(referencedNameElementType)) {
            kotlinTypeInfoNoTypeInfo = getTypeInfoForBinaryCall(OperatorConventions.BINARY_OPERATION_NAMES.get(referencedNameElementType), expressionTypingContextReplaceExpectedType, ktBinaryExpression);
        } else {
            if (referencedNameElementType == KtTokens.ELVIS) {
                return visitElvisExpression(ktBinaryExpression, expressionTypingContextReplaceExpectedType);
            }
            if (referencedNameElementType == KtTokens.EQ) {
                kotlinTypeInfoNoTypeInfo = visitAssignment(ktBinaryExpression, expressionTypingContextReplaceExpectedType);
            } else if (OperatorConventions.ASSIGNMENT_OPERATIONS.containsKey(referencedNameElementType)) {
                kotlinTypeInfoNoTypeInfo = visitAssignmentOperation(ktBinaryExpression, expressionTypingContextReplaceExpectedType);
            } else if (OperatorConventions.COMPARISON_OPERATIONS.contains(referencedNameElementType)) {
                kotlinTypeInfoNoTypeInfo = visitComparison(ktBinaryExpression, expressionTypingContextReplaceExpectedType, operationReference);
            } else if (OperatorConventions.EQUALS_OPERATIONS.contains(referencedNameElementType)) {
                kotlinTypeInfoNoTypeInfo = visitEquality(ktBinaryExpression, expressionTypingContextReplaceExpectedType, operationReference, left, right);
            } else if (OperatorConventions.IDENTITY_EQUALS_OPERATIONS.contains(referencedNameElementType)) {
                kotlinTypeInfoNoTypeInfo = visitIdentityEquality(ktBinaryExpression, expressionTypingContextReplaceExpectedType);
            } else if (OperatorConventions.IN_OPERATIONS.contains(referencedNameElementType)) {
                kotlinTypeInfoNoTypeInfo = checkInExpression(ktBinaryExpression, operationReference, CallMaker.makeValueArgument(left, left != null ? left : operationReference), right, expressionTypingContextReplaceExpectedType);
            } else if (OperatorConventions.BOOLEAN_OPERATIONS.containsKey(referencedNameElementType)) {
                kotlinTypeInfoNoTypeInfo = visitBooleanOperationExpression(referencedNameElementType, left, right, expressionTypingContextReplaceExpectedType);
            } else {
                expressionTypingContextReplaceExpectedType.trace.report(Errors.UNSUPPORTED.on(operationReference, "Unknown operation"));
                kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContextReplaceExpectedType);
            }
        }
        CompileTimeConstant<?> compileTimeConstantEvaluateExpression = this.components.constantExpressionEvaluator.evaluateExpression(ktBinaryExpression, expressionTypingContext.trace, expressionTypingContext.expectedType);
        if (compileTimeConstantEvaluateExpression != null) {
            return this.components.dataFlowAnalyzer.createCompileTimeConstantTypeInfo(compileTimeConstantEvaluateExpression, ktBinaryExpression, expressionTypingContext);
        }
        return this.components.dataFlowAnalyzer.checkType(kotlinTypeInfoNoTypeInfo, ktBinaryExpression, expressionTypingContext);
    }

    private KotlinTypeInfo visitIdentityEquality(KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(57);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(58);
        }
        KotlinTypeInfo kotlinTypeInfoCreateTypeInfo = TypeInfoFactoryKt.createTypeInfo(this.components.builtIns.getBooleanType(), expressionTypingContext);
        KtExpression left = ktBinaryExpression.getLeft();
        KtExpression right = ktBinaryExpression.getRight();
        if (left != null && right != null) {
            this.facade.getTypeInfo(left, expressionTypingContext);
            this.facade.getTypeInfo(right, expressionTypingContext);
            EqualityCallChecker.INSTANCE.checkIdentityEquality(ktBinaryExpression, expressionTypingContext);
        }
        return kotlinTypeInfoCreateTypeInfo;
    }

    /* JADX WARN: Multi-variable type inference failed */
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
    private KotlinTypeInfo visitEquality(KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext, KtSimpleNameExpression ktSimpleNameExpression, KtExpression ktExpression, KtExpression ktExpression2) {
        if (ktExpression2 == null || ktExpression == null) {
            ExpressionTypingUtils.getTypeInfoOrNullType(ktExpression2, expressionTypingContext, this.facade);
            ExpressionTypingUtils.getTypeInfoOrNullType(ktExpression, expressionTypingContext, this.facade);
            return TypeInfoFactoryKt.createTypeInfo(this.components.builtIns.getBooleanType(), expressionTypingContext);
        }
        KotlinTypeInfo typeInfoOrNullType = ExpressionTypingUtils.getTypeInfoOrNullType(ktExpression, expressionTypingContext, this.facade);
        ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = expressionTypingContext.replaceDataFlowInfo(typeInfoOrNullType.getDataFlowInfo());
        ExpressionReceiver expressionReceiverCreateReceiverForEquals = createReceiverForEquals(ktExpression, expressionTypingContextReplaceDataFlowInfo);
        List<FunctionDescriptor> listFindEqualsWithNullableAnyParameter = findEqualsWithNullableAnyParameter(expressionReceiverCreateReceiverForEquals, ktBinaryExpression);
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveEqualsCallWithGivenDescriptors = this.components.callResolver.resolveEqualsCallWithGivenDescriptors(expressionTypingContextReplaceDataFlowInfo, ktSimpleNameExpression, expressionReceiverCreateReceiverForEquals, CallMaker.makeCallWithExpressions(ktBinaryExpression, expressionReceiverCreateReceiverForEquals, null, ktSimpleNameExpression, Collections.singletonList(ktExpression2), Call.CallType.DEFAULT, true), listFindEqualsWithNullableAnyParameter);
        if (!overloadResolutionResultsResolveEqualsCallWithGivenDescriptors.isSuccess()) {
            if (overloadResolutionResultsResolveEqualsCallWithGivenDescriptors.isAmbiguity()) {
                expressionTypingContext.trace.report(Errors.OVERLOAD_RESOLUTION_AMBIGUITY.on(ktSimpleNameExpression, overloadResolutionResultsResolveEqualsCallWithGivenDescriptors.getResultingCalls()));
            } else {
                expressionTypingContext.trace.report(Errors.EQUALS_MISSING.on(ktSimpleNameExpression));
            }
        }
        KotlinTypeInfo typeInfo = this.facade.getTypeInfo(ktExpression2, expressionTypingContextReplaceDataFlowInfo);
        KotlinType type = typeInfoOrNullType.getType();
        KotlinType type2 = typeInfo.getType();
        boolean z = expressionTypingContext.inferenceSession instanceof BuilderInferenceSession;
        if (type != null && type2 != null && !TypeIntersector.isIntersectionEmpty(type, type2) && z) {
            expressionTypingContext.trace.record(BindingContext.MARKED_EQUALIY_CALL_PROPER_IN_BUILDER_INFERENCE, ktBinaryExpression);
        }
        return typeInfo.replaceType(this.components.builtIns.getBooleanType());
    }

    private ExpressionReceiver createReceiverForEquals(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext) {
        KotlinType kotlinTypeSafeGetType = ExpressionTypingUtils.safeGetType(this.facade.safeGetTypeInfo(ktExpression, expressionTypingContext));
        if (KotlinBuiltIns.isNothingOrNullableNothing(kotlinTypeSafeGetType)) {
            kotlinTypeSafeGetType = this.components.builtIns.getNullableAnyType();
        }
        return ExpressionReceiver.INSTANCE.create(ktExpression, kotlinTypeSafeGetType, expressionTypingContext.trace.getContext());
    }

    private List<FunctionDescriptor> findEqualsWithNullableAnyParameter(ExpressionReceiver expressionReceiver, KtBinaryExpression ktBinaryExpression) {
        KotlinType type;
        if (expressionReceiver == null) {
            $$$reportNull$$$0(59);
        }
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(60);
        }
        if (KotlinBuiltIns.isNothingOrNullableNothing(expressionReceiver.getType())) {
            type = this.components.builtIns.getNullableAnyType();
        } else {
            type = expressionReceiver.getType();
        }
        return CollectionsKt.filter(type.getMemberScope().getContributedFunctions(OperatorNameConventions.EQUALS, new KotlinLookupLocation(ktBinaryExpression.getOperationReference())), new Function1() { // from class: org.jetbrains.kotlin.types.expressions.BasicExpressionTypingVisitor$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return BasicExpressionTypingVisitor.lambda$findEqualsWithNullableAnyParameter$1((FunctionDescriptor) obj);
            }
        });
    }

    static /* synthetic */ Boolean lambda$findEqualsWithNullableAnyParameter$1(FunctionDescriptor functionDescriptor) {
        if (ErrorUtils.isError(functionDescriptor)) {
            return true;
        }
        if (functionDescriptor.getValueParameters().size() != 1) {
            return false;
        }
        return Boolean.valueOf(KotlinBuiltIns.isNullableAny(functionDescriptor.getValueParameters().get(0).getType()));
    }

    private KotlinTypeInfo visitComparison(KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext, KtSimpleNameExpression ktSimpleNameExpression) {
        SimpleType booleanType;
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(61);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(62);
        }
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(63);
        }
        KotlinTypeInfo typeInfoForBinaryCall = getTypeInfoForBinaryCall(OperatorNameConventions.COMPARE_TO, expressionTypingContext, ktBinaryExpression);
        KotlinType type = typeInfoForBinaryCall.getType();
        if (type == null || KotlinTypeKt.isError(type)) {
            booleanType = null;
        } else if (KotlinTypeChecker.DEFAULT.equalTypes(this.components.builtIns.getIntType(), type)) {
            booleanType = this.components.builtIns.getBooleanType();
        } else {
            expressionTypingContext.trace.report(Errors.COMPARE_TO_TYPE_MISMATCH.on(ktSimpleNameExpression, type));
            booleanType = null;
        }
        KotlinTypeInfo kotlinTypeInfoReplaceType = typeInfoForBinaryCall.replaceType(booleanType);
        if (kotlinTypeInfoReplaceType == null) {
            $$$reportNull$$$0(64);
        }
        return kotlinTypeInfoReplaceType;
    }

    private KotlinTypeInfo visitBooleanOperationExpression(IElementType iElementType, KtExpression ktExpression, KtExpression ktExpression2, ExpressionTypingContext expressionTypingContext) {
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(65);
        }
        SimpleType booleanType = this.components.builtIns.getBooleanType();
        KotlinTypeInfo typeInfoOrNullType = ExpressionTypingUtils.getTypeInfoOrNullType(ktExpression, expressionTypingContext.replaceExpectedType(booleanType), this.facade);
        DataFlowInfo dataFlowInfo = typeInfoOrNullType.getDataFlowInfo();
        LexicalWritableScope lexicalWritableScopeNewWritableScopeImpl = ExpressionTypingUtils.newWritableScopeImpl(expressionTypingContext, LexicalScopeKind.LEFT_BOOLEAN_EXPRESSION, this.facade.getComponents().overloadChecker);
        boolean z = iElementType == KtTokens.ANDAND;
        DataFlowInfo dataFlowInfoAnd = this.components.dataFlowAnalyzer.extractDataFlowInfoFromCondition(ktExpression, z, expressionTypingContext).and(dataFlowInfo);
        if (!z) {
            lexicalWritableScopeNewWritableScopeImpl = ExpressionTypingUtils.newWritableScopeImpl(expressionTypingContext, LexicalScopeKind.RIGHT_BOOLEAN_EXPRESSION, this.facade.getComponents().overloadChecker);
        }
        ExpressionTypingContext expressionTypingContextReplaceExpectedType = expressionTypingContext.replaceDataFlowInfo(dataFlowInfoAnd).replaceScope(lexicalWritableScopeNewWritableScopeImpl).replaceExpectedType(booleanType);
        if (ktExpression2 != null) {
            this.facade.getTypeInfo(ktExpression2, expressionTypingContextReplaceExpectedType);
        }
        KotlinTypeInfo kotlinTypeInfoReplaceType = typeInfoOrNullType.replaceType(booleanType);
        if (kotlinTypeInfoReplaceType == null) {
            $$$reportNull$$$0(66);
        }
        return kotlinTypeInfoReplaceType;
    }

    private KotlinTypeInfo visitElvisExpression(KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(67);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(68);
        }
        ExpressionTypingContext expressionTypingContextReplaceExpectedType = expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE);
        KtExpression left = ktBinaryExpression.getLeft();
        KtExpression right = ktBinaryExpression.getRight();
        if (left == null || right == null) {
            ExpressionTypingUtils.getTypeInfoOrNullType(left, expressionTypingContextReplaceExpectedType, this.facade);
            KotlinTypeInfo kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContextReplaceExpectedType);
            if (kotlinTypeInfoNoTypeInfo == null) {
                $$$reportNull$$$0(69);
            }
            return kotlinTypeInfoNoTypeInfo;
        }
        Call callCreateCallForSpecialConstruction = ControlStructureTypingUtils.createCallForSpecialConstruction(ktBinaryExpression, ktBinaryExpression.getOperationReference(), Lists.newArrayList(left, right));
        ResolvedCall<FunctionDescriptor> resolvedCallResolveSpecialConstructionAsCall = this.components.controlStructureTypingUtils.resolveSpecialConstructionAsCall(callCreateCallForSpecialConstruction, ControlStructureTypingUtils.ResolveConstruct.ELVIS, Lists.newArrayList("left", "right"), Lists.newArrayList(true, false), expressionTypingContext, null);
        KotlinTypeInfo recordedTypeInfo = BindingContextUtils.getRecordedTypeInfo(left, expressionTypingContextReplaceExpectedType.trace.getContext());
        boolean zIsFunctionLiteralArgument = ArgumentTypeResolver.isFunctionLiteralArgument(left, expressionTypingContextReplaceExpectedType);
        boolean zIsCallableReferenceArgument = ArgumentTypeResolver.isCallableReferenceArgument(left, expressionTypingContextReplaceExpectedType);
        boolean zIsCollectionLiteralArgument = ArgumentTypeResolver.isCollectionLiteralArgument(left);
        if (recordedTypeInfo == null && (zIsFunctionLiteralArgument || zIsCallableReferenceArgument || zIsCollectionLiteralArgument)) {
            KotlinTypeInfo kotlinTypeInfoNoTypeInfo2 = TypeInfoFactoryKt.noTypeInfo(expressionTypingContextReplaceExpectedType);
            if (kotlinTypeInfoNoTypeInfo2 == null) {
                $$$reportNull$$$0(70);
            }
            return kotlinTypeInfoNoTypeInfo2;
        }
        KotlinType type = recordedTypeInfo.getType();
        KotlinTypeInfo recordedTypeInfo2 = BindingContextUtils.getRecordedTypeInfo(right, expressionTypingContextReplaceExpectedType.trace.getContext());
        if (recordedTypeInfo2 == null && ArgumentTypeResolver.isFunctionLiteralOrCallableReference(right, expressionTypingContextReplaceExpectedType)) {
            KotlinTypeInfo kotlinTypeInfoNoTypeInfo3 = TypeInfoFactoryKt.noTypeInfo(expressionTypingContextReplaceExpectedType);
            if (kotlinTypeInfoNoTypeInfo3 == null) {
                $$$reportNull$$$0(71);
            }
            return kotlinTypeInfoNoTypeInfo3;
        }
        boolean z = recordedTypeInfo.getJumpOutPossible() || recordedTypeInfo2.getJumpOutPossible();
        KotlinType type2 = recordedTypeInfo2.getType();
        DataFlowInfo info = resolvedCallResolveSpecialConstructionAsCall.getDataFlowInfoForArguments().getInfo(callCreateCallForSpecialConstruction.getValueArguments().get(1));
        KotlinType returnType = ((FunctionDescriptor) resolvedCallResolveSpecialConstructionAsCall.getResultingDescriptor()).getReturnType();
        if (returnType == null || type2 == null || (type == null && KotlinBuiltIns.isNothing(type2))) {
            KotlinTypeInfo kotlinTypeInfoNoTypeInfo4 = TypeInfoFactoryKt.noTypeInfo(info);
            if (kotlinTypeInfoNoTypeInfo4 == null) {
                $$$reportNull$$$0(72);
            }
            return kotlinTypeInfoNoTypeInfo4;
        }
        if (type != null) {
            DataFlowValue dataFlowValueCreateDataFlowValue = this.components.dataFlowValueFactory.createDataFlowValue(left, type, expressionTypingContextReplaceExpectedType);
            DataFlowInfo resultInfo = resolvedCallResolveSpecialConstructionAsCall.getDataFlowInfoForArguments().getResultInfo();
            boolean zIsNothing = KotlinBuiltIns.isNothing(type2);
            DataFlowValue dataFlowValueNullValue = DataFlowValue.nullValue(this.components.builtIns);
            if (zIsNothing || !resultInfo.getStableNullability(dataFlowValueCreateDataFlowValue).canBeNull()) {
                info = info.disequate(dataFlowValueCreateDataFlowValue, dataFlowValueNullValue, this.components.languageVersionSettings);
                if (left instanceof KtBinaryExpressionWithTypeRHS) {
                    info = establishSubtypingForTypeRHS((KtBinaryExpressionWithTypeRHS) left, info, expressionTypingContextReplaceExpectedType, this.components.languageVersionSettings);
                }
            }
            DataFlowValue dataFlowValueCreateDataFlowValue2 = this.components.dataFlowValueFactory.createDataFlowValue(ktBinaryExpression, returnType, expressionTypingContextReplaceExpectedType);
            info = info.assign(dataFlowValueCreateDataFlowValue2, dataFlowValueCreateDataFlowValue, this.components.languageVersionSettings).disequate(dataFlowValueCreateDataFlowValue2, dataFlowValueNullValue, this.components.languageVersionSettings);
            if (!zIsNothing) {
                info = info.or(resultInfo.assign(dataFlowValueCreateDataFlowValue2, this.components.dataFlowValueFactory.createDataFlowValue(right, type2, expressionTypingContextReplaceExpectedType), this.components.languageVersionSettings));
            }
        }
        if (!TypeUtils.isNullableType(type2) && TypeUtils.isNullableType(returnType)) {
            returnType = TypeUtils.makeNotNullable(returnType);
        }
        if (expressionTypingContextReplaceExpectedType.contextDependency == ContextDependency.DEPENDENT) {
            KotlinTypeInfo kotlinTypeInfoCreateTypeInfo = TypeInfoFactoryKt.createTypeInfo(returnType, info);
            if (kotlinTypeInfoCreateTypeInfo == null) {
                $$$reportNull$$$0(73);
            }
            return kotlinTypeInfoCreateTypeInfo;
        }
        KotlinTypeInfo kotlinTypeInfoCreateTypeInfo2 = TypeInfoFactoryKt.createTypeInfo(this.components.dataFlowAnalyzer.checkType(returnType, ktBinaryExpression, expressionTypingContext), info, z, expressionTypingContextReplaceExpectedType.dataFlowInfo);
        if (kotlinTypeInfoCreateTypeInfo2 == null) {
            $$$reportNull$$$0(74);
        }
        return kotlinTypeInfoCreateTypeInfo2;
    }

    private DataFlowInfo establishSubtypingForTypeRHS(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS, DataFlowInfo dataFlowInfo, ExpressionTypingContext expressionTypingContext, LanguageVersionSettings languageVersionSettings) {
        KtExpression left;
        KotlinType type;
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(75);
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(76);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(77);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(78);
        }
        if (ktBinaryExpressionWithTypeRHS.getOperationReference().getReferencedNameElementType() == KtTokens.AS_SAFE && (type = expressionTypingContext.trace.getType((left = ktBinaryExpressionWithTypeRHS.getLeft()))) != null) {
            DataFlowValue dataFlowValueCreateDataFlowValue = this.components.dataFlowValueFactory.createDataFlowValue(left, type, expressionTypingContext);
            KotlinType kotlinType = (KotlinType) expressionTypingContext.trace.get(BindingContext.TYPE, ktBinaryExpressionWithTypeRHS.getRight());
            if (kotlinType != null) {
                DataFlowInfo dataFlowInfoEstablishSubtyping = dataFlowInfo.establishSubtyping(dataFlowValueCreateDataFlowValue, kotlinType, languageVersionSettings);
                if (dataFlowInfoEstablishSubtyping == null) {
                    $$$reportNull$$$0(79);
                }
                return dataFlowInfoEstablishSubtyping;
            }
        }
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(80);
        }
        return dataFlowInfo;
    }

    public KotlinTypeInfo checkInExpression(KtElement ktElement, KtSimpleNameExpression ktSimpleNameExpression, ValueArgument valueArgument, KtExpression ktExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktElement == null) {
            $$$reportNull$$$0(81);
        }
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(82);
        }
        if (valueArgument == null) {
            $$$reportNull$$$0(83);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(84);
        }
        KtExpression argumentExpression = valueArgument.getArgumentExpression();
        ExpressionTypingContext expressionTypingContextReplaceExpectedType = expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE);
        if (ktExpression == null) {
            if (argumentExpression != null) {
                this.facade.getTypeInfo(argumentExpression, expressionTypingContextReplaceExpectedType);
            }
            KotlinTypeInfo kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
            if (kotlinTypeInfoNoTypeInfo == null) {
                $$$reportNull$$$0(85);
            }
            return kotlinTypeInfoNoTypeInfo;
        }
        KotlinTypeInfo typeInfo = this.facade.getTypeInfo(ktExpression, expressionTypingContextReplaceExpectedType);
        DataFlowInfo dataFlowInfo = typeInfo.getDataFlowInfo();
        ExpressionReceiver expressionReceiverSafeGetExpressionReceiver = ExpressionTypingUtils.safeGetExpressionReceiver(this.facade, ktExpression, expressionTypingContextReplaceExpectedType);
        ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = expressionTypingContext.replaceDataFlowInfo(dataFlowInfo);
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveCallWithGivenName = this.components.callResolver.resolveCallWithGivenName(expressionTypingContextReplaceDataFlowInfo, CallMaker.makeCall(ktElement, expressionReceiverSafeGetExpressionReceiver, null, ktSimpleNameExpression, Collections.singletonList(valueArgument), Call.CallType.CONTAINS), ktSimpleNameExpression, OperatorNameConventions.CONTAINS);
        ensureBooleanResult(ktSimpleNameExpression, OperatorNameConventions.CONTAINS, OverloadResolutionResultsUtil.getResultingType(overloadResolutionResultsResolveCallWithGivenName, expressionTypingContext), expressionTypingContext);
        if (argumentExpression != null) {
            typeInfo = typeInfo.replaceDataFlowInfo(this.facade.getTypeInfo(argumentExpression, expressionTypingContextReplaceDataFlowInfo).getDataFlowInfo().and(dataFlowInfo));
        }
        if (overloadResolutionResultsResolveCallWithGivenName.isSuccess() || isResolutionSuccessfulWithOnlyInputTypesWarnings(overloadResolutionResultsResolveCallWithGivenName.getResultingCalls(), expressionTypingContext)) {
            KotlinTypeInfo kotlinTypeInfoReplaceType = typeInfo.replaceType(this.components.builtIns.getBooleanType());
            if (kotlinTypeInfoReplaceType == null) {
                $$$reportNull$$$0(86);
            }
            return kotlinTypeInfoReplaceType;
        }
        KotlinTypeInfo kotlinTypeInfoClearType = typeInfo.clearType();
        if (kotlinTypeInfoClearType == null) {
            $$$reportNull$$$0(87);
        }
        return kotlinTypeInfoClearType;
    }

    private static boolean isResolutionSuccessfulWithOnlyInputTypesWarnings(Collection<? extends ResolvedCall<FunctionDescriptor>> collection, ExpressionTypingContext expressionTypingContext) {
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(88);
        }
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        return collection.stream().allMatch(new Predicate() { // from class: org.jetbrains.kotlin.types.expressions.BasicExpressionTypingVisitor$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return BasicExpressionTypingVisitor.lambda$isResolutionSuccessfulWithOnlyInputTypesWarnings$2((ResolvedCall) obj);
            }
        }) && !expressionTypingContext.languageVersionSettings.supportsFeature(LanguageFeature.StrictOnlyInputTypesChecks);
    }

    static /* synthetic */ boolean lambda$isResolutionSuccessfulWithOnlyInputTypesWarnings$2(ResolvedCall resolvedCall) {
        return (resolvedCall instanceof NewAbstractResolvedCall) && ((NewAbstractResolvedCall) resolvedCall).containsOnlyOnlyInputTypesErrors();
    }

    private boolean ensureBooleanResult(KtExpression ktExpression, Name name2, KotlinType kotlinType, ExpressionTypingContext expressionTypingContext) {
        return ensureBooleanResultWithCustomSubject(ktExpression, kotlinType, FormatHelper.QUOTE + name2 + FormatHelper.QUOTE, expressionTypingContext);
    }

    private boolean ensureBooleanResultWithCustomSubject(KtExpression ktExpression, KotlinType kotlinType, String str, ExpressionTypingContext expressionTypingContext) {
        if (kotlinType == null || this.components.builtIns.isBooleanOrSubtype(kotlinType)) {
            return true;
        }
        expressionTypingContext.trace.report(Errors.RESULT_TYPE_MISMATCH.on(ktExpression, str, this.components.builtIns.getBooleanType(), kotlinType));
        return false;
    }

    private KotlinTypeInfo visitAssignmentOperation(KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext) {
        return assignmentIsNotAnExpressionError(ktBinaryExpression, expressionTypingContext);
    }

    private KotlinTypeInfo visitAssignment(KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext) {
        return assignmentIsNotAnExpressionError(ktBinaryExpression, expressionTypingContext);
    }

    private KotlinTypeInfo assignmentIsNotAnExpressionError(KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext) {
        this.facade.checkStatementType(ktBinaryExpression, expressionTypingContext);
        if (!expressionTypingContext.isDebuggerContext) {
            expressionTypingContext.trace.report(Errors.ASSIGNMENT_IN_EXPRESSION_CONTEXT.on(ktBinaryExpression));
        }
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
        if (kotlinTypeInfoNoTypeInfo == null) {
            $$$reportNull$$$0(89);
        }
        return kotlinTypeInfoNoTypeInfo;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitArrayAccessExpression(KtArrayAccessExpression ktArrayAccessExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(90);
        }
        return this.components.dataFlowAnalyzer.checkType(resolveArrayAccessGetMethod(ktArrayAccessExpression, expressionTypingContext), ktArrayAccessExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitCollectionLiteralExpression(KtCollectionLiteralExpression ktCollectionLiteralExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktCollectionLiteralExpression == null) {
            $$$reportNull$$$0(91);
        }
        return this.components.collectionLiteralResolver.resolveCollectionLiteral(ktCollectionLiteralExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitClass(KtClass ktClass, ExpressionTypingContext expressionTypingContext) {
        if (ktClass == null) {
            $$$reportNull$$$0(92);
        }
        this.components.localClassifierAnalyzer.processClassOrObject(null, expressionTypingContext.replaceContextDependency(ContextDependency.INDEPENDENT), expressionTypingContext.scope.getOwnerDescriptor(), ktClass);
        return declarationInIllegalContext(ktClass, expressionTypingContext);
    }

    private static KotlinTypeInfo declarationInIllegalContext(KtDeclaration ktDeclaration, ExpressionTypingContext expressionTypingContext) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(93);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(94);
        }
        expressionTypingContext.trace.report(Errors.DECLARATION_IN_ILLEGAL_CONTEXT.on(ktDeclaration));
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
        if (kotlinTypeInfoNoTypeInfo == null) {
            $$$reportNull$$$0(95);
        }
        return kotlinTypeInfoNoTypeInfo;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitProperty(KtProperty ktProperty, ExpressionTypingContext expressionTypingContext) {
        if (ktProperty == null) {
            $$$reportNull$$$0(96);
        }
        this.components.localVariableResolver.process(ktProperty, expressionTypingContext, expressionTypingContext.scope, this.facade);
        return declarationInIllegalContext(ktProperty, expressionTypingContext);
    }

    private KotlinTypeInfo getTypeInfoForBinaryCall(Name name2, ExpressionTypingContext expressionTypingContext, KtBinaryExpression ktBinaryExpression) {
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo;
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsNameNotFound;
        if (name2 == null) {
            $$$reportNull$$$0(97);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(98);
        }
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(99);
        }
        KtExpression left = ktBinaryExpression.getLeft();
        if (left != null) {
            kotlinTypeInfoNoTypeInfo = this.facade.getTypeInfo(left, expressionTypingContext.replaceContextDependency(ContextDependency.INDEPENDENT).replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE));
        } else {
            kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
        }
        ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = expressionTypingContext.replaceDataFlowInfo(kotlinTypeInfoNoTypeInfo.getDataFlowInfo());
        if (left != null) {
            overloadResolutionResultsNameNotFound = this.components.callResolver.resolveBinaryCall(expressionTypingContextReplaceDataFlowInfo, ExpressionTypingUtils.safeGetExpressionReceiver(this.facade, left, expressionTypingContext), ktBinaryExpression, name2);
        } else {
            overloadResolutionResultsNameNotFound = OverloadResolutionResultsImpl.nameNotFound();
        }
        if (overloadResolutionResultsNameNotFound.isSingleResult()) {
            kotlinTypeInfoNoTypeInfo = kotlinTypeInfoNoTypeInfo.replaceDataFlowInfo(overloadResolutionResultsNameNotFound.mo7436getResultingCall().getDataFlowInfoForArguments().getResultInfo());
        }
        KotlinTypeInfo kotlinTypeInfoReplaceType = kotlinTypeInfoNoTypeInfo.replaceType(OverloadResolutionResultsUtil.getResultingType(overloadResolutionResultsNameNotFound, expressionTypingContext));
        if (kotlinTypeInfoReplaceType == null) {
            $$$reportNull$$$0(100);
        }
        return kotlinTypeInfoReplaceType;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitDeclaration(KtDeclaration ktDeclaration, ExpressionTypingContext expressionTypingContext) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(101);
        }
        return declarationInIllegalContext(ktDeclaration, expressionTypingContext);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor, org.jetbrains.kotlin.types.expressions.BasicExpressionTypingVisitor$1StringTemplateVisitor] */
    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitStringTemplateExpression(KtStringTemplateExpression ktStringTemplateExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktStringTemplateExpression == null) {
            $$$reportNull$$$0(102);
        }
        ExpressionTypingContext expressionTypingContextReplaceContextDependency = expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT);
        checkLiteralPrefixAndSuffix(ktStringTemplateExpression, expressionTypingContextReplaceContextDependency);
        ?? r1 = new KtVisitorVoid(expressionTypingContextReplaceContextDependency) { // from class: org.jetbrains.kotlin.types.expressions.BasicExpressionTypingVisitor.1StringTemplateVisitor
            private KotlinTypeInfo typeInfo;
            final /* synthetic */ ExpressionTypingContext val$context;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                objArr[0] = "entry";
                objArr[1] = "org/jetbrains/kotlin/types/expressions/BasicExpressionTypingVisitor$1StringTemplateVisitor";
                if (i != 1) {
                    objArr[2] = "visitStringTemplateEntryWithExpression";
                } else {
                    objArr[2] = "visitEscapeStringTemplateEntry";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            {
                this.val$context = expressionTypingContextReplaceContextDependency;
                this.typeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContextReplaceContextDependency);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitStringTemplateEntryWithExpression(KtStringTemplateEntryWithExpression ktStringTemplateEntryWithExpression) {
                if (ktStringTemplateEntryWithExpression == null) {
                    $$$reportNull$$$0(0);
                }
                KtExpression expression = ktStringTemplateEntryWithExpression.getExpression();
                if (expression != null) {
                    this.typeInfo = BasicExpressionTypingVisitor.this.facade.getTypeInfo(expression, this.val$context.replaceDataFlowInfo(this.typeInfo.getDataFlowInfo()));
                }
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitEscapeStringTemplateEntry(KtEscapeStringTemplateEntry ktEscapeStringTemplateEntry) {
                if (ktEscapeStringTemplateEntry == null) {
                    $$$reportNull$$$0(1);
                }
                Diagnostic diagnostic = CompileTimeConstantChecker.escapedStringToCharacter(ktEscapeStringTemplateEntry.getText(), ktEscapeStringTemplateEntry).getDiagnostic();
                if (diagnostic != null) {
                    this.val$context.trace.report(diagnostic);
                }
            }
        };
        for (KtClassOrObject ktClassOrObject : ktStringTemplateExpression.getEntries()) {
            ktClassOrObject.accept(r1);
        }
        this.components.constantExpressionEvaluator.evaluateExpression(ktStringTemplateExpression, expressionTypingContextReplaceContextDependency.trace, expressionTypingContext.expectedType);
        return this.components.dataFlowAnalyzer.checkType(((C1StringTemplateVisitor) r1).typeInfo.replaceType(this.components.builtIns.getStringType()), ktStringTemplateExpression, expressionTypingContext);
    }

    private static void checkLiteralPrefixAndSuffix(PsiElement psiElement, ExpressionTypingContext expressionTypingContext) {
        if (psiElement == null) {
            $$$reportNull$$$0(103);
        }
        if (!(psiElement instanceof StubBasedPsiElement) || ((StubBasedPsiElement) psiElement).getStub() == null) {
            checkLiteralPrefixOrSuffix(PsiTreeUtil.prevLeaf(psiElement), expressionTypingContext);
            checkLiteralPrefixOrSuffix(PsiTreeUtil.nextLeaf(psiElement), expressionTypingContext);
        }
    }

    private static void checkLiteralPrefixOrSuffix(PsiElement psiElement, ExpressionTypingContext expressionTypingContext) {
        if (illegalLiteralPrefixOrSuffix(psiElement)) {
            expressionTypingContext.trace.report(Errors.UNSUPPORTED.on(psiElement, "literal prefixes and suffixes"));
        }
    }

    private static boolean illegalLiteralPrefixOrSuffix(PsiElement psiElement) {
        if (psiElement == null) {
            return false;
        }
        IElementType elementType = psiElement.getNode().getElementType();
        return elementType == KtTokens.IDENTIFIER || elementType == KtTokens.INTEGER_LITERAL || elementType == KtTokens.FLOAT_LITERAL || (elementType instanceof KtKeywordToken);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitAnnotatedExpression(KtAnnotatedExpression ktAnnotatedExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktAnnotatedExpression == null) {
            $$$reportNull$$$0(104);
        }
        return visitAnnotatedExpression(ktAnnotatedExpression, expressionTypingContext, false);
    }

    public KotlinTypeInfo visitAnnotatedExpression(KtAnnotatedExpression ktAnnotatedExpression, ExpressionTypingContext expressionTypingContext, boolean z) {
        resolveAnnotationsOnExpression(ktAnnotatedExpression, expressionTypingContext);
        KtExpression baseExpression = ktAnnotatedExpression.getBaseExpression();
        if (baseExpression == null) {
            return TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
        }
        if (this.components.typeResolutionInterceptor.isEmpty()) {
            return this.facade.getTypeInfo(baseExpression, expressionTypingContext, z);
        }
        KotlinType kotlinTypeInterceptType = this.components.typeResolutionInterceptor.interceptType(baseExpression, expressionTypingContext, expressionTypingContext.expectedType);
        KotlinTypeInfo typeInfo = this.facade.getTypeInfo(baseExpression, kotlinTypeInterceptType == expressionTypingContext.expectedType ? expressionTypingContext : expressionTypingContext.replaceExpectedType(kotlinTypeInterceptType), z);
        KotlinType kotlinTypeInterceptType2 = this.components.typeResolutionInterceptor.interceptType(baseExpression, expressionTypingContext, typeInfo.getType());
        this.components.dataFlowAnalyzer.checkType(kotlinTypeInterceptType2, ktAnnotatedExpression, expressionTypingContext);
        return typeInfo.getType() == kotlinTypeInterceptType2 ? typeInfo : typeInfo.replaceType(kotlinTypeInterceptType2);
    }

    protected void resolveAnnotationsOnExpression(KtAnnotatedExpression ktAnnotatedExpression, ExpressionTypingContext expressionTypingContext) {
        if (isAnnotatedExpressionInBlockLevelBinary(ktAnnotatedExpression)) {
            expressionTypingContext.trace.report(Errors.ANNOTATIONS_ON_BLOCK_LEVEL_EXPRESSION_ON_THE_SAME_LINE.on(ktAnnotatedExpression));
        }
        if (ktAnnotatedExpression.getBaseExpression() instanceof KtObjectLiteralExpression) {
            return;
        }
        this.components.annotationResolver.resolveAnnotationsWithArguments(expressionTypingContext.scope, ktAnnotatedExpression.getAnnotationEntries(), expressionTypingContext.trace);
    }

    private static boolean isAnnotatedExpressionInBlockLevelBinary(KtAnnotatedExpression ktAnnotatedExpression) {
        PsiElement lightParent = ktAnnotatedExpression.getLightParent();
        KtAnnotatedExpression ktAnnotatedExpression2 = ktAnnotatedExpression;
        if (!(lightParent instanceof KtBinaryExpression)) {
            return false;
        }
        while (true) {
            PsiElement psiElement = lightParent;
            KtAnnotatedExpression ktAnnotatedExpression3 = ktAnnotatedExpression2;
            PsiElement psiElement2 = psiElement;
            if (psiElement2 instanceof KtBinaryExpression) {
                if (((KtBinaryExpression) psiElement2).getLeft() != ktAnnotatedExpression3) {
                    return false;
                }
                lightParent = psiElement2.getLightParent();
                ktAnnotatedExpression2 = psiElement2;
            } else {
                return KtPsiUtil.isStatementContainer(psiElement2);
            }
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitKtElement(KtElement ktElement, ExpressionTypingContext expressionTypingContext) {
        if (ktElement == null) {
            $$$reportNull$$$0(105);
        }
        expressionTypingContext.trace.report(Errors.UNSUPPORTED.on(ktElement, getClass().getCanonicalName()));
        return TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
    }

    KotlinTypeInfo resolveArrayAccessSetMethod(KtArrayAccessExpression ktArrayAccessExpression, KtExpression ktExpression, ExpressionTypingContext expressionTypingContext, BindingTrace bindingTrace) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(106);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(107);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(108);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(109);
        }
        return resolveArrayAccessSpecialMethod(ktArrayAccessExpression, ktExpression, expressionTypingContext, bindingTrace, false, false);
    }

    KotlinTypeInfo resolveImplicitArrayAccessSetMethod(KtArrayAccessExpression ktArrayAccessExpression, KtExpression ktExpression, ExpressionTypingContext expressionTypingContext, BindingTrace bindingTrace) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(110);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(111);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(112);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(113);
        }
        return resolveArrayAccessSpecialMethod(ktArrayAccessExpression, ktExpression, expressionTypingContext, bindingTrace, false, true);
    }

    KotlinTypeInfo resolveArrayAccessGetMethod(KtArrayAccessExpression ktArrayAccessExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(114);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(115);
        }
        return resolveArrayAccessSpecialMethod(ktArrayAccessExpression, null, expressionTypingContext, expressionTypingContext.trace, true, false);
    }

    private KotlinTypeInfo resolveArrayAccessSpecialMethod(KtArrayAccessExpression ktArrayAccessExpression, KtExpression ktExpression, ExpressionTypingContext expressionTypingContext, BindingTrace bindingTrace, boolean z, boolean z2) {
        Call callMakeArraySetCall;
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(116);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(117);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(118);
        }
        KtExpression arrayExpression = ktArrayAccessExpression.getArrayExpression();
        if (arrayExpression == null) {
            KotlinTypeInfo kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
            if (kotlinTypeInfoNoTypeInfo == null) {
                $$$reportNull$$$0(119);
            }
            return kotlinTypeInfoNoTypeInfo;
        }
        KotlinTypeInfo kotlinTypeInfoSafeGetTypeInfo = this.facade.safeGetTypeInfo(arrayExpression, expressionTypingContext.replaceExpectedType(TypeUtils.NO_EXPECTED_TYPE).replaceContextDependency(ContextDependency.INDEPENDENT));
        KotlinType kotlinTypeSafeGetType = ExpressionTypingUtils.safeGetType(kotlinTypeInfoSafeGetTypeInfo);
        ExpressionTypingContext expressionTypingContextReplaceDataFlowInfo = expressionTypingContext.replaceDataFlowInfo(kotlinTypeInfoSafeGetTypeInfo.getDataFlowInfo());
        ExpressionReceiver expressionReceiverCreate = ExpressionReceiver.INSTANCE.create(arrayExpression, kotlinTypeSafeGetType, expressionTypingContextReplaceDataFlowInfo.trace.getContext());
        if (z) {
            callMakeArraySetCall = CallMaker.makeArrayGetCall(expressionReceiverCreate, ktArrayAccessExpression, Call.CallType.ARRAY_GET_METHOD);
        } else {
            callMakeArraySetCall = CallMaker.makeArraySetCall(expressionReceiverCreate, ktArrayAccessExpression, ktExpression, Call.CallType.ARRAY_SET_METHOD);
        }
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveCallWithGivenName = this.components.callResolver.resolveCallWithGivenName(expressionTypingContextReplaceDataFlowInfo, callMakeArraySetCall, ktArrayAccessExpression, z ? OperatorNameConventions.GET : OperatorNameConventions.SET);
        KotlinTypeInfo kotlinTypeInfoComputeAccumulatedInfoForArrayAccessExpression = computeAccumulatedInfoForArrayAccessExpression(kotlinTypeInfoSafeGetTypeInfo, ktArrayAccessExpression.getIndexExpressions(), ktExpression, z, expressionTypingContextReplaceDataFlowInfo, this.facade);
        if ((z2 && !overloadResolutionResultsResolveCallWithGivenName.isSuccess()) || !overloadResolutionResultsResolveCallWithGivenName.isSingleResult()) {
            bindingTrace.report((z ? Errors.NO_GET_METHOD : Errors.NO_SET_METHOD).on(ktArrayAccessExpression));
            KotlinTypeInfo kotlinTypeInfoClearType = kotlinTypeInfoComputeAccumulatedInfoForArrayAccessExpression.clearType();
            if (kotlinTypeInfoClearType == null) {
                $$$reportNull$$$0(120);
            }
            return kotlinTypeInfoClearType;
        }
        if (z) {
            bindingTrace.record(BindingContext.INDEXED_LVALUE_GET, ktArrayAccessExpression, overloadResolutionResultsResolveCallWithGivenName.mo7436getResultingCall());
        } else {
            bindingTrace.record(BindingContext.INDEXED_LVALUE_SET, ktArrayAccessExpression, overloadResolutionResultsResolveCallWithGivenName.mo7436getResultingCall());
        }
        KotlinTypeInfo kotlinTypeInfoReplaceType = kotlinTypeInfoComputeAccumulatedInfoForArrayAccessExpression.replaceType(((FunctionDescriptor) overloadResolutionResultsResolveCallWithGivenName.mo7437getResultingDescriptor()).getReturnType());
        if (kotlinTypeInfoReplaceType == null) {
            $$$reportNull$$$0(121);
        }
        return kotlinTypeInfoReplaceType;
    }

    private static KotlinTypeInfo computeAccumulatedInfoForArrayAccessExpression(KotlinTypeInfo kotlinTypeInfo, List<KtExpression> list, KtExpression ktExpression, boolean z, ExpressionTypingContext expressionTypingContext, ExpressionTypingInternals expressionTypingInternals) {
        if (kotlinTypeInfo == null) {
            $$$reportNull$$$0(122);
        }
        if (list == null) {
            $$$reportNull$$$0(123);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(124);
        }
        if (expressionTypingInternals == null) {
            $$$reportNull$$$0(125);
        }
        boolean z2 = !expressionTypingContext.languageVersionSettings.supportsFeature(LanguageFeature.NewInference);
        KotlinTypeInfo typeInfo = !list.isEmpty() ? getTypeInfo(list.get(list.size() - 1), expressionTypingInternals, expressionTypingContext, z2) : null;
        if (!z && ktExpression != null) {
            typeInfo = getTypeInfo(ktExpression, expressionTypingInternals, expressionTypingContext, z2);
        }
        return typeInfo != null ? typeInfo : kotlinTypeInfo;
    }

    private static KotlinTypeInfo getTypeInfo(KtExpression ktExpression, ExpressionTypingInternals expressionTypingInternals, ExpressionTypingContext expressionTypingContext, boolean z) {
        if (ktExpression == null) {
            $$$reportNull$$$0(126);
        }
        if (expressionTypingInternals == null) {
            $$$reportNull$$$0(127);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(128);
        }
        if (z) {
            return expressionTypingInternals.getTypeInfo(ktExpression, expressionTypingContext);
        }
        return BindingContextUtils.getRecordedTypeInfo(ktExpression, expressionTypingContext.trace.getContext());
    }
}
