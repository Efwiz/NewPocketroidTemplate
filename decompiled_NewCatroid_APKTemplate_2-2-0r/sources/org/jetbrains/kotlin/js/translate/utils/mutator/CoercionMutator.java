package org.jetbrains.kotlin.js.translate.utils.mutator;

import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes5.dex */
public class CoercionMutator implements Mutator {
    private final TranslationContext context;
    private final KotlinType targetType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "context";
        } else if (i == 2) {
            objArr[0] = "node";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/utils/mutator/CoercionMutator";
        } else {
            objArr[0] = "targetType";
        }
        if (i == 3 || i == 4) {
            objArr[1] = "mutate";
        } else {
            objArr[1] = "org/jetbrains/kotlin/js/translate/utils/mutator/CoercionMutator";
        }
        if (i == 2) {
            objArr[2] = "mutate";
        } else if (i != 3 && i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public CoercionMutator(KotlinType kotlinType, TranslationContext translationContext) {
        if (kotlinType == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        this.targetType = kotlinType;
        this.context = translationContext;
    }

    @Override // org.jetbrains.kotlin.js.translate.utils.mutator.Mutator
    public JsNode mutate(JsNode jsNode) {
        if (jsNode == null) {
            $$$reportNull$$$0(2);
        }
        if (!(jsNode instanceof JsExpression)) {
            if (jsNode == null) {
                $$$reportNull$$$0(4);
            }
            return jsNode;
        }
        JsExpression jsExpressionCoerce = TranslationUtils.coerce(this.context, (JsExpression) jsNode, this.targetType);
        if (jsExpressionCoerce == null) {
            $$$reportNull$$$0(3);
        }
        return jsExpressionCoerce;
    }
}
