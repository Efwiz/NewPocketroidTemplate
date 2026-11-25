package org.jetbrains.kotlin.js.translate.operation;

import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.reference.AccessTranslationUtils;
import org.jetbrains.kotlin.js.translate.reference.AccessTranslator;
import org.jetbrains.kotlin.js.translate.reference.BackingFieldAccessTranslator;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AssignmentTranslator extends AbstractTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected final KtBinaryExpression expression;
    protected final boolean isVariableReassignment;

    /* JADX WARN: Code duplicated, block: B:24:0x0035  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "context";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/operation/AssignmentTranslator";
        } else if (i == 5) {
            objArr[0] = "context";
        } else if (i == 6) {
            objArr[0] = "left";
        } else if (i != 8) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "context";
        }
        if (i == 2 || i == 3) {
            objArr[1] = "translate";
        } else {
            objArr[1] = "org/jetbrains/kotlin/js/translate/operation/AssignmentTranslator";
        }
        switch (i) {
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                objArr[2] = "<init>";
                break;
            case 6:
                objArr[2] = "createAccessTranslator";
                break;
            case 7:
            case 8:
                objArr[2] = "isValProperty";
                break;
            default:
                objArr[2] = "translate";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static boolean isAssignmentOperator(KtToken ktToken) {
        return OperatorConventions.ASSIGNMENT_OPERATIONS.keySet().contains(ktToken) || PsiUtils.isAssignment(ktToken);
    }

    public static JsExpression translate(KtBinaryExpression ktBinaryExpression, TranslationContext translationContext) {
        CallableDescriptor callableDescriptorForOperationExpression;
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        if (TranslationUtils.hasCorrespondingFunctionIntrinsic(translationContext, ktBinaryExpression) && ((callableDescriptorForOperationExpression = BindingUtils.getCallableDescriptorForOperationExpression(translationContext.bindingContext(), ktBinaryExpression)) == null || callableDescriptorForOperationExpression.getExtensionReceiverParameter() == null)) {
            JsExpression jsExpressionDoTranslate = IntrinsicAssignmentTranslator.doTranslate(ktBinaryExpression, translationContext);
            if (jsExpressionDoTranslate == null) {
                $$$reportNull$$$0(2);
            }
            return jsExpressionDoTranslate;
        }
        JsExpression jsExpressionDoTranslate2 = OverloadedAssignmentTranslator.doTranslate(ktBinaryExpression, translationContext);
        if (jsExpressionDoTranslate2 == null) {
            $$$reportNull$$$0(3);
        }
        return jsExpressionDoTranslate2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected AssignmentTranslator(KtBinaryExpression ktBinaryExpression, TranslationContext translationContext) {
        super(translationContext);
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(4);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(5);
        }
        this.expression = ktBinaryExpression;
        this.isVariableReassignment = BindingUtils.isVariableReassignment(translationContext.bindingContext(), ktBinaryExpression);
    }

    protected final AccessTranslator createAccessTranslator(KtExpression ktExpression, boolean z) {
        if (ktExpression == null) {
            $$$reportNull$$$0(6);
        }
        if (isValProperty(ktExpression, context())) {
            return BackingFieldAccessTranslator.newInstance(PsiUtils.getSimpleName(ktExpression), context());
        }
        return AccessTranslationUtils.getAccessTranslator(ktExpression, context(), z);
    }

    private static boolean isValProperty(KtExpression ktExpression, TranslationContext translationContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(7);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(8);
        }
        KtSimpleNameExpression simpleName = PsiUtils.getSimpleName(ktExpression);
        if (simpleName == null) {
            return false;
        }
        DeclarationDescriptor descriptorForReferenceExpression = BindingUtils.getDescriptorForReferenceExpression(translationContext.bindingContext(), simpleName);
        return (descriptorForReferenceExpression instanceof PropertyDescriptor) && !((PropertyDescriptor) descriptorForReferenceExpression).isVar();
    }
}
