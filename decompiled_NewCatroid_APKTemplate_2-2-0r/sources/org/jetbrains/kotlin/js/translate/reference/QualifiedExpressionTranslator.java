package org.jetbrains.kotlin.js.translate.reference;

import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.ErrorReportingUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: loaded from: classes5.dex */
public final class QualifiedExpressionTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 11 || i == 7 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 11 || i == 7 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 6:
            case 10:
            case 13:
            case 14:
                objArr[0] = "context";
                break;
            case 2:
            case 7:
            case 8:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/js/translate/reference/QualifiedExpressionTranslator";
                break;
            case 3:
            case 12:
            default:
                objArr[0] = "expression";
                break;
            case 5:
            case 9:
                objArr[0] = "selector";
                break;
        }
        if (i == 2) {
            objArr[1] = "getAccessTranslator";
        } else if (i == 11) {
            objArr[1] = "invokeCallExpressionTranslator";
        } else if (i == 7 || i == 8) {
            objArr[1] = "dispatchToCorrectTranslator";
        } else {
            objArr[1] = "org/jetbrains/kotlin/js/translate/reference/QualifiedExpressionTranslator";
        }
        switch (i) {
            case 2:
            case 7:
            case 8:
            case 11:
                break;
            case 3:
            case 4:
                objArr[2] = "translateQualifiedExpression";
                break;
            case 5:
            case 6:
                objArr[2] = "dispatchToCorrectTranslator";
                break;
            case 9:
            case 10:
                objArr[2] = "invokeCallExpressionTranslator";
                break;
            case 12:
            case 13:
                objArr[2] = "translateReceiver";
                break;
            case 14:
                objArr[2] = "isFullQualifierForExpression";
                break;
            default:
                objArr[2] = "getAccessTranslator";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 11 && i != 7 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private QualifiedExpressionTranslator() {
    }

    public static AccessTranslator getAccessTranslator(KtQualifiedExpression ktQualifiedExpression, TranslationContext translationContext, boolean z) {
        if (ktQualifiedExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        JsExpression jsExpressionTranslateReceiver = translateReceiver(ktQualifiedExpression, translationContext);
        if (z && jsExpressionTranslateReceiver != null) {
            jsExpressionTranslateReceiver = translationContext.defineTemporary(jsExpressionTranslateReceiver);
        }
        KtExpression selector = PsiUtils.getSelector(ktQualifiedExpression);
        if (selector instanceof KtSimpleNameExpression) {
            VariableAccessTranslator variableAccessTranslatorNewInstance = VariableAccessTranslator.newInstance(translationContext, (KtSimpleNameExpression) selector, jsExpressionTranslateReceiver);
            if (variableAccessTranslatorNewInstance == null) {
                $$$reportNull$$$0(2);
            }
            return variableAccessTranslatorNewInstance;
        }
        if (selector instanceof KtCallExpression) {
            return new QualifiedExpressionWithCallSelectorAccessTranslator((KtCallExpression) selector, jsExpressionTranslateReceiver, translationContext);
        }
        throw new AssertionError("Unexpected qualified expression: " + ktQualifiedExpression.getText());
    }

    public static JsNode translateQualifiedExpression(KtQualifiedExpression ktQualifiedExpression, TranslationContext translationContext) {
        if (ktQualifiedExpression == null) {
            $$$reportNull$$$0(3);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(4);
        }
        return dispatchToCorrectTranslator(CallUtilKt.getResolvedCall(ktQualifiedExpression, translationContext.bindingContext()) != null ? translateReceiver(ktQualifiedExpression, translationContext) : null, PsiUtils.getSelector(ktQualifiedExpression), translationContext);
    }

    private static JsNode dispatchToCorrectTranslator(JsExpression jsExpression, KtExpression ktExpression, TranslationContext translationContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(5);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(6);
        }
        if (ReferenceTranslator.canBePropertyAccess(ktExpression, translationContext)) {
            JsExpression jsExpressionTranslateAsGet = VariableAccessTranslator.newInstance(translationContext, (KtSimpleNameExpression) ktExpression, jsExpression).translateAsGet();
            if (jsExpressionTranslateAsGet == null) {
                $$$reportNull$$$0(7);
            }
            return jsExpressionTranslateAsGet;
        }
        if (ktExpression instanceof KtCallExpression) {
            return invokeCallExpressionTranslator(jsExpression, (KtCallExpression) ktExpression, translationContext);
        }
        if (ktExpression instanceof KtSimpleNameExpression) {
            JsExpression jsExpressionTranslateSimpleName = ReferenceTranslator.translateSimpleName((KtSimpleNameExpression) ktExpression, translationContext);
            if (jsExpressionTranslateSimpleName == null) {
                $$$reportNull$$$0(8);
            }
            return jsExpressionTranslateSimpleName;
        }
        throw new AssertionError("Unexpected qualified expression: " + ktExpression.getText());
    }

    static JsNode invokeCallExpressionTranslator(JsExpression jsExpression, KtCallExpression ktCallExpression, TranslationContext translationContext) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(9);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(10);
        }
        try {
            JsNode jsNodeTranslate = CallExpressionTranslator.translate(ktCallExpression, jsExpression, translationContext);
            if (jsNodeTranslate == null) {
                $$$reportNull$$$0(11);
            }
            return jsNodeTranslate;
        } catch (RuntimeException e) {
            throw ErrorReportingUtils.reportErrorWithLocation(ktCallExpression, e);
        }
    }

    private static JsExpression translateReceiver(KtQualifiedExpression ktQualifiedExpression, TranslationContext translationContext) {
        if (ktQualifiedExpression == null) {
            $$$reportNull$$$0(12);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(13);
        }
        KtExpression receiverExpression = ktQualifiedExpression.getReceiverExpression();
        if (isFullQualifierForExpression(receiverExpression, translationContext)) {
            return null;
        }
        return Translation.translateAsExpression(receiverExpression, translationContext);
    }

    private static boolean isFullQualifierForExpression(KtExpression ktExpression, TranslationContext translationContext) {
        if (translationContext == null) {
            $$$reportNull$$$0(14);
        }
        if (ktExpression == null) {
            return false;
        }
        if ((ktExpression instanceof KtReferenceExpression) && (BindingUtils.getDescriptorForReferenceExpression(translationContext.bindingContext(), (KtReferenceExpression) ktExpression) instanceof PackageViewDescriptor)) {
            return true;
        }
        if (ktExpression instanceof KtQualifiedExpression) {
            return isFullQualifierForExpression(((KtQualifiedExpression) ktExpression).getSelectorExpression(), translationContext);
        }
        return false;
    }
}
