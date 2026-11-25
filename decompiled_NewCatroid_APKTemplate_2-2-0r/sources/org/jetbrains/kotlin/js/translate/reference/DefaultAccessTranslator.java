package org.jetbrains.kotlin.js.translate.reference;

import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.psi.KtExpression;

/* JADX INFO: loaded from: classes5.dex */
public class DefaultAccessTranslator implements AccessTranslator {
    private final TranslationContext context;
    private final KtExpression expression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "context";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/reference/DefaultAccessTranslator";
        } else if (i != 3) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "setTo";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/reference/DefaultAccessTranslator";
        } else {
            objArr[1] = "translateAsGet";
        }
        if (i != 2) {
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "translateAsSet";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    public DefaultAccessTranslator(KtExpression ktExpression, TranslationContext translationContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        this.expression = ktExpression;
        this.context = translationContext;
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public JsExpression translateAsGet() {
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(this.expression, this.context);
        if (jsExpressionTranslateAsExpression == null) {
            $$$reportNull$$$0(2);
        }
        return jsExpressionTranslateAsExpression;
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public JsExpression translateAsSet(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(3);
        }
        throw new UnsupportedOperationException("This method is not meant to be supported in in DefaultAccessTranslator");
    }

    @Override // org.jetbrains.kotlin.js.translate.reference.AccessTranslator
    public AccessTranslator getCached() {
        throw new UnsupportedOperationException("This method is not meant to be supported in in DefaultAccessTranslator");
    }
}
