package org.jetbrains.kotlin.js.translate.initializer;

import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: loaded from: classes5.dex */
public final class InitializerUtils {
    /* JADX WARN: Code duplicated, block: B:20:0x002c  */
    /* JADX WARN: Code duplicated, block: B:21:0x002f  */
    /* JADX WARN: Code duplicated, block: B:22:0x0034  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 7) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "descriptor";
        } else if (i == 2) {
            objArr[0] = "value";
        } else if (i == 3) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/initializer/InitializerUtils";
        } else if (i == 5) {
            objArr[0] = "descriptor";
        } else if (i == 6) {
            objArr[0] = "value";
        } else if (i != 7) {
            objArr[0] = "context";
        } else {
            objArr[0] = "org/jetbrains/kotlin/js/translate/initializer/InitializerUtils";
        }
        if (i == 3) {
            objArr[1] = "generateInitializerForProperty";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/initializer/InitializerUtils";
        } else {
            objArr[1] = "generateInitializerForDelegate";
        }
        if (i != 3) {
            if (i == 4 || i == 5 || i == 6) {
                objArr[2] = "generateInitializerForDelegate";
            } else if (i != 7) {
                objArr[2] = "generateInitializerForProperty";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private InitializerUtils() {
    }

    public static JsStatement generateInitializerForProperty(TranslationContext translationContext, PropertyDescriptor propertyDescriptor, JsExpression jsExpression) {
        if (translationContext == null) {
            $$$reportNull$$$0(0);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(2);
        }
        JsExpression jsExpressionAssignmentToBackingField = TranslationUtils.assignmentToBackingField(translationContext, propertyDescriptor, jsExpression);
        jsExpressionAssignmentToBackingField.setSource(PsiSourceElementKt.getPsi(propertyDescriptor.getSourceElement()));
        JsStatement jsStatementMakeStmt = jsExpressionAssignmentToBackingField.makeStmt();
        if (jsStatementMakeStmt == null) {
            $$$reportNull$$$0(3);
        }
        return jsStatementMakeStmt;
    }

    public static JsStatement generateInitializerForDelegate(TranslationContext translationContext, PropertyDescriptor propertyDescriptor, JsExpression jsExpression) {
        if (translationContext == null) {
            $$$reportNull$$$0(4);
        }
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(6);
        }
        JsStatement jsStatementDefineSimpleProperty = JsAstUtils.defineSimpleProperty(translationContext.getNameForBackingField(propertyDescriptor), jsExpression, propertyDescriptor.getSourceElement());
        if (jsStatementDefineSimpleProperty == null) {
            $$$reportNull$$$0(7);
        }
        return jsStatementDefineSimpleProperty;
    }
}
