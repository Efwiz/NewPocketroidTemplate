package org.jetbrains.kotlin.js.translate.reference;

import java.util.LinkedHashMap;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;

/* JADX INFO: loaded from: classes5.dex */
public final class AccessTranslationUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 8 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 8 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 7:
            case 10:
                objArr[0] = "context";
                break;
            case 2:
            default:
                objArr[0] = "referenceExpression";
                break;
            case 4:
            case 5:
            case 8:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/js/translate/reference/AccessTranslationUtils";
                break;
            case 6:
            case 9:
                objArr[0] = "expression";
                break;
        }
        if (i == 4 || i == 5) {
            objArr[1] = "getAccessTranslator";
        } else if (i == 8) {
            objArr[1] = "getArrayAccessTranslator";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/reference/AccessTranslationUtils";
        } else {
            objArr[1] = "translateAsGet";
        }
        switch (i) {
            case 4:
            case 5:
            case 8:
            case 11:
                break;
            case 6:
            case 7:
                objArr[2] = "getArrayAccessTranslator";
                break;
            case 9:
            case 10:
                objArr[2] = "translateAsGet";
                break;
            default:
                objArr[2] = "getAccessTranslator";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 8 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private AccessTranslationUtils() {
    }

    public static AccessTranslator getAccessTranslator(KtExpression ktExpression, TranslationContext translationContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        return getAccessTranslator(ktExpression, translationContext, false);
    }

    public static AccessTranslator getAccessTranslator(KtExpression ktExpression, TranslationContext translationContext, boolean z) {
        if (ktExpression == null) {
            $$$reportNull$$$0(2);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(3);
        }
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(ktExpression);
        if (ktExpressionDeparenthesize instanceof KtQualifiedExpression) {
            AccessTranslator accessTranslator = QualifiedExpressionTranslator.getAccessTranslator((KtQualifiedExpression) ktExpressionDeparenthesize, translationContext, z);
            if (accessTranslator == null) {
                $$$reportNull$$$0(4);
            }
            return accessTranslator;
        }
        if (ktExpressionDeparenthesize instanceof KtSimpleNameExpression) {
            AccessTranslator accessTranslator2 = ReferenceTranslator.getAccessTranslator((KtSimpleNameExpression) ktExpressionDeparenthesize, translationContext);
            if (accessTranslator2 == null) {
                $$$reportNull$$$0(5);
            }
            return accessTranslator2;
        }
        if (ktExpressionDeparenthesize instanceof KtArrayAccessExpression) {
            return getArrayAccessTranslator((KtArrayAccessExpression) ktExpressionDeparenthesize, translationContext, z);
        }
        return new DefaultAccessTranslator(ktExpressionDeparenthesize, translationContext);
    }

    private static AccessTranslator getArrayAccessTranslator(KtArrayAccessExpression ktArrayAccessExpression, TranslationContext translationContext, boolean z) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(6);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(7);
        }
        if (z) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (KtExpression ktExpression : ktArrayAccessExpression.getIndexExpressions()) {
                linkedHashMap.put(ktExpression, translationContext.cacheExpressionIfNeeded(Translation.translateAsExpression(ktExpression, translationContext)));
            }
            translationContext = translationContext.innerContextWithAliasesForExpressions(linkedHashMap);
        }
        ArrayAccessTranslator arrayAccessTranslatorNewInstance = ArrayAccessTranslator.newInstance(ktArrayAccessExpression, translationContext);
        if (arrayAccessTranslatorNewInstance == null) {
            $$$reportNull$$$0(8);
        }
        return arrayAccessTranslatorNewInstance;
    }

    public static JsExpression translateAsGet(KtExpression ktExpression, TranslationContext translationContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(9);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(10);
        }
        JsExpression jsExpressionTranslateAsGet = getAccessTranslator(ktExpression, translationContext).translateAsGet();
        if (jsExpressionTranslateAsGet == null) {
            $$$reportNull$$$0(11);
        }
        return jsExpressionTranslateAsGet;
    }
}
