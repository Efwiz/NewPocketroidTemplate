package org.jetbrains.kotlin.types.expressions;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.FunctionExpressionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.ObservableBindingTrace;
import org.jetbrains.kotlin.resolve.OverloadChecker;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.resolve.scopes.TraceBasedLocalRedeclarationChecker;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;

/* JADX INFO: loaded from: classes6.dex */
public class ExpressionTypingUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 6 || i == 22) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 6 || i == 22) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 23:
            case 24:
                objArr[0] = "expression";
                break;
            case 2:
            case 21:
            default:
                objArr[0] = "facade";
                break;
            case 4:
            case 6:
            case 22:
                objArr[0] = "org/jetbrains/kotlin/types/expressions/ExpressionTypingUtils";
                break;
            case 5:
                objArr[0] = "typeInfo";
                break;
            case 7:
            case 20:
                objArr[0] = "context";
                break;
            case 8:
                objArr[0] = "scopeKind";
                break;
            case 9:
                objArr[0] = "overloadChecker";
                break;
            case 10:
                objArr[0] = "project";
                break;
            case 11:
            case 15:
            case 17:
                objArr[0] = "trace";
                break;
            case 12:
                objArr[0] = "argumentName";
                break;
            case 13:
                objArr[0] = "argumentType";
                break;
            case 14:
                objArr[0] = "scope";
                break;
            case 16:
                objArr[0] = "variableDescriptor";
                break;
            case 18:
                objArr[0] = "expressionToWatch";
                break;
            case 19:
                objArr[0] = "mismatchFound";
                break;
        }
        if (i == 4) {
            objArr[1] = "safeGetExpressionReceiver";
        } else if (i == 6) {
            objArr[1] = "safeGetType";
        } else if (i != 22) {
            objArr[1] = "org/jetbrains/kotlin/types/expressions/ExpressionTypingUtils";
        } else {
            objArr[1] = "getTypeInfoOrNullType";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "safeGetExpressionReceiver";
                break;
            case 4:
            case 6:
            case 22:
                break;
            case 5:
                objArr[2] = "safeGetType";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "newWritableScopeImpl";
                break;
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[2] = "createFakeExpressionOfType";
                break;
            case 14:
            case 15:
            case 16:
                objArr[2] = "checkVariableShadowing";
                break;
            case 17:
            case 18:
            case 19:
                objArr[2] = "makeTraceInterceptingTypeMismatch";
                break;
            case 20:
            case 21:
                objArr[2] = "getTypeInfoOrNullType";
                break;
            case 23:
                objArr[2] = "isBinaryExpressionDependentOnExpectedType";
                break;
            case 24:
                objArr[2] = "isUnaryExpressionDependentOnExpectedType";
                break;
            default:
                objArr[2] = "getExpressionReceiver";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 6 && i != 22) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static ExpressionReceiver getExpressionReceiver(ExpressionTypingFacade expressionTypingFacade, KtExpression ktExpression, ExpressionTypingContext expressionTypingContext) {
        if (expressionTypingFacade == null) {
            $$$reportNull$$$0(0);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(1);
        }
        KotlinType type = expressionTypingFacade.getTypeInfo(ktExpression, expressionTypingContext).getType();
        if (type == null) {
            return null;
        }
        return ExpressionReceiver.INSTANCE.create(ktExpression, type, expressionTypingContext.trace.getContext());
    }

    public static ExpressionReceiver safeGetExpressionReceiver(ExpressionTypingFacade expressionTypingFacade, KtExpression ktExpression, ExpressionTypingContext expressionTypingContext) {
        if (expressionTypingFacade == null) {
            $$$reportNull$$$0(2);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(3);
        }
        ExpressionReceiver expressionReceiverCreate = ExpressionReceiver.INSTANCE.create(ktExpression, safeGetType(expressionTypingFacade.safeGetTypeInfo(ktExpression, expressionTypingContext)), expressionTypingContext.trace.getContext());
        if (expressionReceiverCreate == null) {
            $$$reportNull$$$0(4);
        }
        return expressionReceiverCreate;
    }

    public static KotlinType safeGetType(KotlinTypeInfo kotlinTypeInfo) {
        if (kotlinTypeInfo == null) {
            $$$reportNull$$$0(5);
        }
        KotlinType type = kotlinTypeInfo.getType();
        if (type == null) {
            $$$reportNull$$$0(6);
        }
        return type;
    }

    public static LexicalWritableScope newWritableScopeImpl(ExpressionTypingContext expressionTypingContext, LexicalScopeKind lexicalScopeKind, OverloadChecker overloadChecker) {
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(7);
        }
        if (lexicalScopeKind == null) {
            $$$reportNull$$$0(8);
        }
        if (overloadChecker == null) {
            $$$reportNull$$$0(9);
        }
        return new LexicalWritableScope(expressionTypingContext.scope, expressionTypingContext.scope.getOwnerDescriptor(), false, new TraceBasedLocalRedeclarationChecker(expressionTypingContext.trace, overloadChecker), lexicalScopeKind);
    }

    public static KtExpression createFakeExpressionOfType(Project project, BindingTrace bindingTrace, String str, KotlinType kotlinType) {
        if (project == null) {
            $$$reportNull$$$0(10);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(11);
        }
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(13);
        }
        KtExpression ktExpressionCreateExpression = new KtPsiFactory(project, false).createExpression(str);
        bindingTrace.recordType(ktExpressionCreateExpression, kotlinType);
        bindingTrace.record(BindingContext.PROCESSED, ktExpressionCreateExpression);
        return ktExpressionCreateExpression;
    }

    public static void checkVariableShadowing(LexicalScope lexicalScope, BindingTrace bindingTrace, VariableDescriptor variableDescriptor) {
        PsiElement psiElementDescriptorToDeclaration;
        PsiElement psiElementDescriptorToDeclaration2;
        if (lexicalScope == null) {
            $$$reportNull$$$0(14);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(15);
        }
        if (variableDescriptor == null) {
            $$$reportNull$$$0(16);
        }
        VariableDescriptor variableDescriptorFindLocalVariable = ScopeUtilsKt.findLocalVariable(lexicalScope, variableDescriptor.getShortName());
        if (variableDescriptorFindLocalVariable == null) {
            return;
        }
        DeclarationDescriptor containingDeclaration = variableDescriptor.getContainingDeclaration();
        if (isLocal(containingDeclaration, variableDescriptorFindLocalVariable)) {
            if ((!(variableDescriptor instanceof ParameterDescriptor) || (isFunctionLiteral(containingDeclaration) && containingDeclaration.getContainingDeclaration() == variableDescriptorFindLocalVariable.getContainingDeclaration())) && (psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(variableDescriptor)) != null) {
                if ((psiElementDescriptorToDeclaration instanceof KtDestructuringDeclarationEntry) && (psiElementDescriptorToDeclaration.getLightParent().getLightParent() instanceof KtParameter) && (psiElementDescriptorToDeclaration2 = DescriptorToSourceUtils.descriptorToDeclaration(variableDescriptorFindLocalVariable)) != null && psiElementDescriptorToDeclaration2.getLightParent().equals(psiElementDescriptorToDeclaration.getLightParent().getLightParent().getLightParent())) {
                    return;
                }
                bindingTrace.report(Errors.NAME_SHADOWING.on(psiElementDescriptorToDeclaration, variableDescriptor.getShortName().asString()));
            }
        }
    }

    public static ObservableBindingTrace makeTraceInterceptingTypeMismatch(BindingTrace bindingTrace, final KtElement ktElement, final boolean[] zArr) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(17);
        }
        if (ktElement == null) {
            $$$reportNull$$$0(18);
        }
        if (zArr == null) {
            $$$reportNull$$$0(19);
        }
        return new ObservableBindingTrace(bindingTrace) { // from class: org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "diagnostic", "org/jetbrains/kotlin/types/expressions/ExpressionTypingUtils$1", "report"));
            }

            @Override // org.jetbrains.kotlin.resolve.ObservableBindingTrace, org.jetbrains.kotlin.diagnostics.DiagnosticSink
            public void report(Diagnostic diagnostic) {
                if (diagnostic == null) {
                    $$$reportNull$$$0(0);
                }
                DiagnosticFactory<?> factory = diagnostic.getFactory();
                if (Errors.TYPE_MISMATCH_ERRORS.contains(factory) && diagnostic.getPsiElement() == ktElement) {
                    zArr[0] = true;
                }
                if (Errors.TYPE_INFERENCE_ERRORS.contains(factory) && PsiTreeUtil.isAncestor(ktElement, diagnostic.getPsiElement(), false)) {
                    zArr[0] = true;
                }
                super.report(diagnostic);
            }
        };
    }

    public static KotlinTypeInfo getTypeInfoOrNullType(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext, ExpressionTypingInternals expressionTypingInternals) {
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo;
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(20);
        }
        if (expressionTypingInternals == null) {
            $$$reportNull$$$0(21);
        }
        if (ktExpression != null) {
            kotlinTypeInfoNoTypeInfo = expressionTypingInternals.getTypeInfo(ktExpression, expressionTypingContext);
        } else {
            kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
        }
        if (kotlinTypeInfoNoTypeInfo == null) {
            $$$reportNull$$$0(22);
        }
        return kotlinTypeInfoNoTypeInfo;
    }

    public static boolean isBinaryExpressionDependentOnExpectedType(KtBinaryExpression ktBinaryExpression) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(23);
        }
        IElementType referencedNameElementType = ktBinaryExpression.getOperationReference().getReferencedNameElementType();
        return referencedNameElementType == KtTokens.IDENTIFIER || OperatorConventions.BINARY_OPERATION_NAMES.containsKey(referencedNameElementType) || referencedNameElementType == KtTokens.ELVIS;
    }

    public static boolean isUnaryExpressionDependentOnExpectedType(KtUnaryExpression ktUnaryExpression) {
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(24);
        }
        return ktUnaryExpression.getOperationReference().getReferencedNameElementType() == KtTokens.EXCLEXCL;
    }

    public static boolean isExclExclExpression(KtExpression ktExpression) {
        return (ktExpression instanceof KtUnaryExpression) && ((KtUnaryExpression) ktExpression).getOperationReference().getReferencedNameElementType() == KtTokens.EXCLEXCL;
    }

    public static boolean isLocal(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
        if (declarationDescriptor2 instanceof ValueParameterDescriptor) {
            return true;
        }
        DeclarationDescriptor containingDeclaration = declarationDescriptor2.getContainingDeclaration();
        if (!(containingDeclaration instanceof FunctionDescriptor)) {
            return false;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) containingDeclaration;
        while (declarationDescriptor != null) {
            if (declarationDescriptor == functionDescriptor) {
                return true;
            }
            declarationDescriptor = declarationDescriptor.getContainingDeclaration();
        }
        return false;
    }

    public static boolean dependsOnExpectedType(KtExpression ktExpression) {
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(ktExpression);
        if (ktExpressionDeparenthesize == null || (ktExpressionDeparenthesize instanceof KtBinaryExpressionWithTypeRHS)) {
            return false;
        }
        if (ktExpressionDeparenthesize instanceof KtBinaryExpression) {
            return isBinaryExpressionDependentOnExpectedType((KtBinaryExpression) ktExpressionDeparenthesize);
        }
        if (ktExpressionDeparenthesize instanceof KtUnaryExpression) {
            return isUnaryExpressionDependentOnExpectedType((KtUnaryExpression) ktExpressionDeparenthesize);
        }
        return true;
    }

    private ExpressionTypingUtils() {
    }

    public static boolean isFunctionLiteral(DeclarationDescriptor declarationDescriptor) {
        return declarationDescriptor instanceof AnonymousFunctionDescriptor;
    }

    public static boolean isLocalFunction(DeclarationDescriptor declarationDescriptor) {
        return declarationDescriptor != null && declarationDescriptor.getClass() == SimpleFunctionDescriptorImpl.class && ((SimpleFunctionDescriptorImpl) declarationDescriptor).getVisibility() == DescriptorVisibilities.LOCAL;
    }

    public static boolean isFunctionExpression(DeclarationDescriptor declarationDescriptor) {
        return declarationDescriptor instanceof FunctionExpressionDescriptor;
    }
}
